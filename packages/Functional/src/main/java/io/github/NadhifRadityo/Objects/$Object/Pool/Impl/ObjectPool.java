package io.github.NadhifRadityo.Objects.$Object.Pool.Impl;

import io.github.NadhifRadityo.Objects.$Utilizations.ListUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("jol")
public class ObjectPool<T, CONFIG extends BasePoolConfig<T, POOLED>, FACTORY extends BasePoolFactory<T, POOLED>, POOLED extends BasePooledObject<T, POOLED>> extends BaseObjectPool<T, CONFIG, FACTORY, POOLED> {
	protected static ThreadLocal<WeakReference<ArrayList<Object>>> tempArrayList = new ThreadLocal<>();
	protected final LinkedBlockingDeque<POOLED> idleObjects;
	protected final Map<IdentityWrapper<T>, POOLED> allObjects;

	protected final AtomicLong createObjectCount;
	protected final AtomicLong makeObjectCount;
	protected final Object evictionLock = new Object();
	protected Iterator<POOLED> evictionIterator = null;

	protected static <T> ArrayList<T> getTempArrayList() {
		ArrayList<Object> result = tempArrayList.get() == null || tempArrayList.get().get() == null ? null : tempArrayList.get().get();
		if(result == null) { result = ListUtils.optimizedArrayList(); tempArrayList.set(new WeakReference<>(result)); } return (ArrayList<T>) result;
	}

	public ObjectPool(CONFIG config, FACTORY factory) {
		super(config, factory);
		this.idleObjects = new LinkedBlockingDeque<>();
		this.allObjects = new ConcurrentHashMap<>();

		this.createObjectCount = new AtomicLong();
		this.makeObjectCount = new AtomicLong();
	}

	protected POOLED create() throws Exception {
		long start = System.currentTimeMillis();
		int maxObject;

		boolean create;
		while(true) { synchronized(makeObjectCount) {
			if(createObjectCount.incrementAndGet() <= ((maxObject = config.getMaxTotal()) < 0 ? Integer.MAX_VALUE : maxObject)) {
				makeObjectCount.incrementAndGet(); create = true; break; } createObjectCount.decrementAndGet();
			if(makeObjectCount.get() == 0) { create = false; break; }
			long wait = config.getMaxWaitMillis() - (System.currentTimeMillis() - start);
			if(wait <= 0) { create = false; break; }
			try { makeObjectCount.wait(wait); } catch(InterruptedException ignored) { }
		} }

		if(!create) return null;
		POOLED pooledObject;
		try {
			pooledObject = factory.makeObject();
			if(config.getTestOnCreate() && !factory.validateObject(pooledObject)) {
				createObjectCount.decrementAndGet();
				return null;
			}
			pushCreateWaitTimes(System.currentTimeMillis() - start);
		} catch(Exception e) {
			createObjectCount.decrementAndGet();
			throw e;
		} finally {
			synchronized(makeObjectCount) {
				makeObjectCount.decrementAndGet();
				makeObjectCount.notifyAll();
			}
		}

		if(config.isAbandonedConfig() && config.getLogAbandoned()) {
			pooledObject.setLogAbandoned(true);
			pooledObject.setRequireFullStackTrace(config.getRequireFullStackTrace());
		}

		createdCount.incrementAndGet();
		allObjects.put(newIdentityWrapper(pooledObject.getObject()), pooledObject);
		return pooledObject;
	}
	protected T borrow0(long maxWait) throws Exception {
		long start = System.currentTimeMillis();
		POOLED pooledObject = null;
		boolean created;

		while(pooledObject == null) {
			created = false;
			pooledObject = idleObjects.pollFirst();
			if(pooledObject == null) { pooledObject = create(); if(pooledObject != null) created = true; }
			if(config.getBlockWhenExhausted()) { if(pooledObject == null) {
				if(maxWait < 0) pooledObject = idleObjects.takeFirst();
				else pooledObject = idleObjects.poll(maxWait, TimeUnit.MILLISECONDS);
				if(pooledObject == null) throw new NoSuchElementException("Timeout waiting for idle object");
			} } else if(pooledObject == null) throw new NoSuchElementException("Pool exhausted");
			if(!pooledObject.allocate()) pooledObject = null;
			if(pooledObject == null) continue;
			try { factory.activateObject(pooledObject);
			} catch (Exception e) {
				try { destroy0(pooledObject); } catch (Exception e1) { swallowException(e1); }
				pooledObject = null; if(!created) continue;
				throw new IllegalStateException("Unable to activate object", e);
			}
		}
		updateStatsBorrow(pooledObject, System.currentTimeMillis() - start);
		return pooledObject.getObject();
	}
	protected void return0(Object object) throws Exception {
		long start = System.currentTimeMillis();
		POOLED pooledObject = allObjects.get(identityWrapper(object));

		if(pooledObject == null) {
			if(config.isAbandonedConfig()) return;
			throw new IllegalStateException("Returned object not currently part of this pool");
		}

		BasePooledObject.PooledObjectState state = pooledObject.getState();
		if(state != BasePooledObject.PooledObjectState.ALLOCATED)
			throw new IllegalStateException("Object has already been returned to this pool or is invalid");
		pooledObject.markReturning();

		if(config.getTestOnReturn() && !factory.validateObject(pooledObject)) {
			try { destroy0(pooledObject); } catch (final Exception e) { swallowException(e); }
			try { ensureIdle(1, false); } catch (final Exception e) { swallowException(e); }
			updateStatsReturn(pooledObject, System.currentTimeMillis() - start); return;
		}

		try { factory.passivateObject(pooledObject);
		} catch(final Exception e) { swallowException(e);
			try { destroy0(pooledObject); } catch (final Exception e1) { swallowException(e1); }
			try { ensureIdle(1, false); } catch (final Exception e1) { swallowException(e1); }
			updateStatsReturn(pooledObject, System.currentTimeMillis() - start); return;
		}

		if(!pooledObject.deallocate()) throw new IllegalStateException("Object has already been returned to this pool or is invalid");
		int maxIdleSave = config.getMaxIdle();
		if(isDead() || maxIdleSave >= 0 && idleObjects.size() > maxIdleSave) {
			try { destroy0(pooledObject); } catch (final Exception e) { swallowException(e); }
			try { ensureIdle(1, false); } catch (final Exception e) { swallowException(e); }
		} else {
			if(config.getLifo()) idleObjects.addFirst(pooledObject);
			else idleObjects.addLast(pooledObject);
			if(isDead()) clearObject();
		} updateStatsReturn(pooledObject, System.currentTimeMillis() - start);

	}
	protected void destroy0(POOLED pooledObject) throws Exception {
		long start = System.currentTimeMillis();

		pooledObject.invalidate();
		idleObjects.remove(pooledObject);
		allObjects.remove(identityWrapper(pooledObject.getObject()));
		try { factory.destroyObject(pooledObject);
		} finally {
			pushCreateWaitTimes(System.currentTimeMillis() - start);
			destroyedCount.incrementAndGet();
		}
	}

	protected void ensureIdle(int idleCount, boolean always) throws Exception {
		if(isDead() || idleCount < 1 || (!always && !idleObjects.hasTakeWaiters()))
			return;
		while(idleObjects.size() < idleCount) {
			POOLED pooledObject = create();
			if(pooledObject == null) break;
			if(config.getLifo()) idleObjects.addFirst(pooledObject);
			else idleObjects.addLast(pooledObject);
		} if(isDead()) clearObject();
	}
	protected int getNumTests() {
		int numTestsPerEvictionRun = config.getNumTestsPerEvictionRun();
		if(numTestsPerEvictionRun >= 0) return Math.min(numTestsPerEvictionRun, idleObjects.size());
		return (int) (Math.ceil(idleObjects.size() / Math.abs((double) numTestsPerEvictionRun)));
	}
	protected void removeAbandoned() {
		long start = System.currentTimeMillis();
		long timeout = start - (config.getRemoveAbandonedTimeout() * 1000L);
		ArrayList<POOLED> remove = getTempArrayList();
		for(POOLED pooledObject : allObjects.values()) { synchronized(pooledObject) {
			if(pooledObject.getState() != BasePooledObject.PooledObjectState.ALLOCATED || pooledObject.getLastUsedTime() > timeout)
				continue;
			pooledObject.markAbandoned();
			remove.add(pooledObject);
		} }
		for(Iterator<POOLED> it = ListUtils.reusableIterator(remove); it.hasNext(); ) {
			POOLED pooledObject = it.next();
			if(config.getLogAbandoned()) pooledObject.printStackTrace(config.getLogWriter());
			try { invalidateObject(pooledObject.getObject()); } catch(Exception e) { swallowException(e); }
		}
	}

	@Override public void createObject() throws Exception {
		POOLED pooledObject = create();
		if(pooledObject == null) return;
		factory.passivateObject(pooledObject);
		if(config.getLifo()) idleObjects.addFirst(pooledObject);
		else idleObjects.addLast(pooledObject);
	}
	@Override public T borrowObject() throws Exception {
		return borrow0(config.getMaxWaitMillis());
	}
	@Override public void returnObject(Object object) throws Exception {
		return0(object);
	}
	@Override public void invalidateObject(Object object) throws Exception {
		POOLED pooledObject = allObjects.get(identityWrapper(object));
		if(pooledObject == null) {
			if(config.isAbandonedConfig()) return;
			throw new IllegalStateException("Returned object not currently part of this pool");
		}
		synchronized(pooledObject) {
			if(pooledObject.getState() != BasePooledObject.PooledObjectState.INVALID)
				destroy0(pooledObject);
		}
		ensureIdle(1, false);
	}
	@Override public void clearObject() throws Exception {
		POOLED pooledObject;
		while((pooledObject = idleObjects.poll()) != null)
			try { destroy0(pooledObject); } catch(Exception e) { swallowException(e); }
	}
	@Override public void evict(EvictionConfig evictionConfig) throws Exception {
		if(idleObjects.size() > 0) {
			POOLED pooledObject;
			EvictionPolicy<T, POOLED> evictionPolicy = config.getEvictionPolicy();
			boolean evict;

			synchronized (evictionLock) {
				boolean testWhileIdle = config.getTestWhileIdle();
				for (int i = 0, m = getNumTests(); i < m; i++) {
					if(evictionIterator == null || !evictionIterator.hasNext())
						evictionIterator = idleObjects.iterator();
					if(!evictionIterator.hasNext()) return;

					try { pooledObject = evictionIterator.next(); } catch (NoSuchElementException ignored) { i--; evictionIterator = null; continue; }
					if(!pooledObject.startEvictionTest()) { i--; continue; }
					try { evict = evictionPolicy.evict(evictionConfig, pooledObject, idleObjects.size());
					} catch (Throwable t) { swallowException(new Exception(t)); evict = false; }

					if(evict) {
						try { destroy0(pooledObject); } catch(Exception e) { swallowException(e); }
						destroyedByEvictorCount.incrementAndGet(); continue;
					}
					if(!testWhileIdle) { pooledObject.endEvictionTest(idleObjects); continue; }
					boolean active = false;
					try { factory.activateObject(pooledObject); active = true;
					} catch (Exception e) {
						try { destroy0(pooledObject); } catch(Exception e1) { swallowException(e1); }
						destroyedByEvictorCount.incrementAndGet();
					}
					if(!active) { pooledObject.endEvictionTest(idleObjects); continue; }
					if(!factory.validateObject(pooledObject)) {
						try { destroy0(pooledObject); } catch(Exception e1) { swallowException(e1); }
						destroyedByEvictorCount.incrementAndGet();
					} else {
						try { factory.passivateObject(pooledObject);
						} catch (Exception e) {
							try { destroy0(pooledObject); } catch(Exception e1) { swallowException(e1); }
							destroyedByEvictorCount.incrementAndGet();
						}
					}
					pooledObject.endEvictionTest(idleObjects);
				}
			}
		}
		if(config.isAbandonedConfig() && config.getRemoveAbandonedOnMaintenance())
			removeAbandoned();
	}
	@Override public int getTotalActive() { return allObjects.size() - idleObjects.size(); }
	@Override public int getTotalIdle() { return idleObjects.size(); }
	@Override public int getTotalWaiters() { return config.getBlockWhenExhausted() ? idleObjects.getTakeQueueLength() : 0; }
}
