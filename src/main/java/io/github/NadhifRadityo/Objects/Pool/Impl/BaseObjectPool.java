package io.github.NadhifRadityo.Objects.Pool.Impl;

import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.Thread.Handler;
import io.github.NadhifRadityo.Objects.Thread.HandlerThread;
import io.github.NadhifRadityo.Objects.Thread.Looper;
import io.github.NadhifRadityo.Objects.Thread.RunnablePost;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

@SuppressWarnings({"jol", "unchecked"})
public abstract class BaseObjectPool<T, CONFIG extends BasePoolConfig<T, POOLED>, FACTORY extends BasePoolFactory<T, POOLED>, POOLED extends BasePooledObject<T, POOLED>> implements DeadableObject {
	private static final ThreadLocal<IdentityWrapper<Object>> identityWrappers = new ThreadLocal<>();
	private static final EvictionConfig evictionConfig = new EvictionConfig(0, 0, 0);
	protected static final HandlerThread handlerThread;
	protected static final Handler handler;

	static {
		handlerThread = new HandlerThread("Pool Eviction Thread");
		handlerThread.setDaemon(true);
		handlerThread.start();
		handler = handlerThread.getThreadHandler();
	}

	protected final CONFIG config;
	protected final FACTORY factory;
	protected final AtomicLong createdCount = new AtomicLong(0);
	protected final AtomicLong borrowedCount = new AtomicLong(0);
	protected final AtomicLong returnedCount = new AtomicLong(0);
	protected final AtomicLong destroyedCount = new AtomicLong(0);
	protected final AtomicLong destroyedByEvictorCount = new AtomicLong(0);
	protected final AtomicLong destroyedByBorrowValidationCount = new AtomicLong(0);
	protected final Stats createWaitTimes = new Stats(20);
	protected final Stats borrowWaitTimes = new Stats(20);
	protected final Stats returnedWaitTimes = new Stats(20);
	protected final Stats destroyedWaitTimes = new Stats(20);
	protected final Stats activeTimes = new Stats(20);
	protected final Stats idleTimes = new Stats(20);

	protected final WeakReference<ClassLoader> factoryClassLoader;
	protected volatile RunnablePost evictor;
	private volatile boolean isDead;

	public BaseObjectPool(CONFIG config, FACTORY factory) {
		this.config = config;
		this.factory = factory;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		this.factoryClassLoader = classLoader == null ? null : new WeakReference<>(classLoader);
		startEvictor(config.getTimeBetweenEvictionRunsMillis());
	}

	public CONFIG getConfig() { return config; }
	public FACTORY getFactory() { return factory; }
	@Override public boolean isDead() { return isDead; }
	@Override public void setDead() { this.isDead = true; }

	public long getCreatedCount() { return createdCount.get(); }
	public long getBorrowedCount() { return borrowedCount.get(); }
	public long getReturnedCount() { return returnedCount.get(); }
	public long getDestroyedCount() { return destroyedCount.get(); }
	public long getDestroyedByEvictorCount() { return destroyedByEvictorCount.get(); }
	public long getDestroyedByBorrowValidationCount() { return destroyedByBorrowValidationCount.get(); }
	public long getCreateWaitTimes() { return createWaitTimes.getMean(); }
	public long getBorrowWaitTimeMillis() { return borrowWaitTimes.getMean(); }
	public long getReturnedWaitTimes() { return returnedWaitTimes.getMean(); }
	public long getDestroyedWaitTimes() { return destroyedWaitTimes.getMean(); }
	public long getActiveTimeMillis() { return activeTimes.getMean(); }
	public long getIdleTimeMillis() { return idleTimes.getMean(); }
	public long getTotalCount() { return getCreatedCount() - getDestroyedCount(); }

	protected long incrementCreatedCount() { return createdCount.incrementAndGet(); }
	protected long incrementBorrowedCount() { return borrowedCount.incrementAndGet(); }
	protected long incrementReturnedCount() { return returnedCount.incrementAndGet(); }
	protected long incrementDestroyedCount() { return destroyedCount.incrementAndGet(); }
	protected long incrementDestroyedByEvictorCount() { return destroyedByEvictorCount.incrementAndGet(); }
	protected long incrementDestroyedByBorrowValidationCount() { return destroyedByBorrowValidationCount.incrementAndGet(); }
	protected void pushCreateWaitTimes(long value) { createWaitTimes.add(value); }
	protected void pushBorrowWaitTimeMillis(long value) { borrowWaitTimes.add(value); }
	protected void pushReturnedWaitTimes(long value) { returnedWaitTimes.add(value); }
	protected void pushDestroyedWaitTimes(long value) { destroyedWaitTimes.add(value); }
	protected void pushActiveTimeMillis(long value) { activeTimes.add(value); }
	protected void pushIdleTimeMillis(long value) { idleTimes.add(value); }

	public abstract void createObject() throws Exception;
	public abstract T borrowObject() throws Exception;
	public abstract void returnObject(Object object) throws Exception;
	public abstract void invalidateObject(Object object) throws Exception;
	public abstract void clearObject() throws Exception;
	public abstract void evict(EvictionConfig evictionConfig) throws Exception;
	public abstract int getTotalActive();
	public abstract int getTotalIdle();
	public abstract int getTotalWaiters();

	protected long updateStatsBorrow(POOLED pooled, long waitTime) {
		long result = incrementBorrowedCount();
		pushBorrowWaitTimeMillis(waitTime);
		pushIdleTimeMillis(pooled.getIdleTimeMillis());
		return result;
	}
	protected long updateStatsReturn(POOLED pooled, long waitTime) {
		long result = incrementReturnedCount();
		pushReturnedWaitTimes(waitTime);
		pushActiveTimeMillis(pooled.getActiveTimeMillis());
		return result;
	}

	protected void startEvictor(long period) {
		stopEvictor(); if(period < 0) return;
		long evictorShutdownTimeoutMillis = config.getEvictorShutdownTimeoutMillis();
		evictor = handler.postThrowableFixedRateDelayed(() -> {
			ClassLoader savedClassLoader = Thread.currentThread().getContextClassLoader(); try {
			if(factoryClassLoader != null) {
				ClassLoader classLoader = factoryClassLoader.get();
				if(classLoader == null) { Looper.myLooper().getJobRunning().setCancelled(); return; }
				Thread.currentThread().setContextClassLoader(classLoader);
			} evictionConfig.setIdleEvictTime(config.getMinEvictableIdleTimeMillis()); evictionConfig.setIdleSoftEvictTime(config.getSoftMinEvictableIdleTimeMillis()); evictionConfig.setMinIdle(config.getMinIdle());
			evict(evictionConfig); } finally { Thread.currentThread().setContextClassLoader(savedClassLoader); }
		}, evictorShutdownTimeoutMillis, period);
	}
	protected void stopEvictor() {
		if(evictor != null) evictor.setCancelled();
	}

	protected void swallowException(Throwable throwable) {
		ExceptionHandler exceptionHandler = config.getExceptionHandler(); if(exceptionHandler == null) return;
		try { exceptionHandler.onExceptionOccurred(throwable); } catch(Throwable ignored) { }
	}

	protected static <T> IdentityWrapper<T> identityWrapper(T instance) {
		IdentityWrapper<T> result = (IdentityWrapper<T>) identityWrappers.get();
		if(result == null) { result = new IdentityWrapper<>(null); identityWrappers.set((IdentityWrapper<Object>) result); }
		result.setInstance(instance); return result;
	}
	protected static <T> IdentityWrapper<T> newIdentityWrapper(T object) { return new IdentityWrapper<>(object); }

	protected static class IdentityWrapper<T> {
		protected boolean hashCalculated;
		protected int lastHash;
		protected T instance;

		public IdentityWrapper(T instance) { this.instance = instance; }

		public T getInstance() { return instance; }
		public void setInstance(T instance) { this.instance = instance; this.hashCalculated = false; }

		@Override public int hashCode() { if(hashCalculated) return lastHash; hashCalculated = true; return lastHash = System.identityHashCode(instance); }
		@Override public boolean equals(Object other) { return other instanceof IdentityWrapper && ((IdentityWrapper<T>) other).instance == instance; }
		@Override public String toString() { return "IdentityWrapper{" + "instance=" + instance + '}'; }
	}
	protected static class Stats {
		protected AtomicLongArray values;
		protected AtomicInteger index;

		public Stats(int history) {
			setHistory(history);
		}

		public synchronized void setHistory(int history) {
			this.values = new AtomicLongArray(history);
			this.index = new AtomicInteger();
			for(int i = 0; i < history; i++)
				values.set(i, -1);
		}

		public synchronized void add(long value) {
			int currentIndex = index.getAndIncrement();
			values.set(currentIndex, value);
			index.compareAndSet(values.length(), 0);
		}

		public long getMean() {
			long result = 0; int counter = 0;
			for(int i = 0; i < values.length(); i++) {
				long value = values.get(i); if(value == -1) continue; counter++;
				result = (long) (result * ((counter - 1) / (double) counter)) + (long) (value / (double) counter);
			} return result;
		}
	}
}
