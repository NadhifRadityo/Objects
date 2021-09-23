package io.github.NadhifRadityo.Objects.$Object.Pool;

import io.github.NadhifRadityo.Objects.$Interface.Functional.ExceptionHandler;
import io.github.NadhifRadityo.Objects.$Utilizations.MapUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PoolCleaner {
	protected static final ThreadLocal<PoolCleaner> threadLocal = new ThreadLocal<>();
	public static void start(Pool<?>... accepts) {
		PoolCleaner poolCleaner = threadLocal.get();
		if(poolCleaner != null) { poolCleaner.setAccepts(accepts); return; }
		poolCleaner = Pool.tryBorrow(Pool.getPool(PoolCleaner.class));
		poolCleaner.setAccepts(accepts); threadLocal.set(poolCleaner);
	}
	public static void start() { start((Pool<?>[]) null); }
	public static void end() {
		PoolCleaner poolCleaner = threadLocal.get();
		if(poolCleaner == null) return; poolCleaner.clean();
		Pool.returnObject(PoolCleaner.class, poolCleaner);
		threadLocal.set(null);
	}
	public static void doClean() {
		PoolCleaner poolCleaner = threadLocal.get();
		if(poolCleaner == null) return; poolCleaner.clean();
	}
	public static boolean isAvailable() { return threadLocal.get() != null; }
	public static boolean isAccepted(Class<?> clazz) { PoolCleaner poolCleaner = threadLocal.get(); return poolCleaner != null && poolCleaner.isAccepted(Pool.getPool(clazz)); }

	protected final Map<Pool<?>, List<?>> pooledObjects;
	protected Pool<?>[] accepts;
	protected boolean started;

	public PoolCleaner(Pool<?>... accepts) {
		this.pooledObjects = MapUtils.optimizedHashMap();
		this.accepts = accepts;
	}
	public PoolCleaner() { this((Pool<?>[]) null); }

	public Map<?, List<?>> getPooledObjects() { return Collections.unmodifiableMap(pooledObjects); }
	public Pool<?>[] getAccepts() { return accepts; }
	public boolean isStarted() { return started; }

	public void setAccepts(Pool<?>... accepts) { this.accepts = accepts; }
	public boolean isAccepted(Pool<?> pool) {
		if(accepts == null) return true;
		for(Pool<?> accept : accepts)
			if(pool == accept) return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T, C extends Class<? extends T>, V extends T, P extends Pool<? extends T>> void add(P pool, T object) {
		if(!isAccepted(pool)) return;
		List<T> pools = (List<T>) pooledObjects.computeIfAbsent(pool, (k) -> new ArrayList<>());
		pools.add(object);
	}
	@SuppressWarnings("unchecked")
	public <T, C extends Class<? extends T>, V extends T, P extends Pool<? extends T>> void remove(P pool, T object) {
		if(!isAccepted(pool)) return;
		List<T> pools = (List<T>) pooledObjects.get(pool);
		if(pools == null) return; pools.remove(object);
	}

	public void clean() {
		for(Iterator<Map.Entry<Pool<?>, List<?>>> it = MapUtils.reusableIterator(pooledObjects); it.hasNext(); ) {
			Map.Entry<Pool<?>, List<?>> entry = it.next(); Pool<?> pool = entry.getKey();
			ExceptionHandler exceptionHandler = pool.getConfig().getExceptionHandler();
			for(Object object : entry.getValue()) try { pool.returnObject(object); } catch(Throwable e) {
			if(exceptionHandler != null) try { exceptionHandler.onExceptionOccurred(e); } catch(Throwable ignored) { } }
			entry.getValue().clear();
		}
	}
}
