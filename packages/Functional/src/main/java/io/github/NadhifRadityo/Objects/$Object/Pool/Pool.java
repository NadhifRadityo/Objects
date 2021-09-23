package io.github.NadhifRadityo.Objects.$Object.Pool;

import io.github.NadhifRadityo.Objects.$Object.Pool.Impl.ObjectPool;
import io.github.NadhifRadityo.Objects.$Utilizations.ClassUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.ExceptionUtils;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static io.github.NadhifRadityo.Objects.$Object.Pool.PoolConfig.*;

@SuppressWarnings({"unchecked", "jol"})
public class Pool<T> extends ObjectPool<T, PoolConfig<T>, PoolFactory<T>, PooledObject<T>> {
	protected static final IntObjectHashMap<Pool<?>> pools = new IntObjectHashMap<>();
	protected static final ThreadLocal<IntObjectHashMap<Pool<?>>> localPools = new ThreadLocal<>();
	protected static final PoolConfig<Object> defConfig;
	protected static final Method[] METHODS_$Pool_addPool;
	private static final int POOL_HASHCODE = Pool.class.hashCode();

	static {
		defConfig = new PoolConfig<>();
		JSONObject config = new JSONObject(System.getProperty("pool", "{maxWaitMillis: 100, maxTotal: 200}"));
		defConfig.setLifo(config.optBoolean("lifo", DEFAULT_LIFO));
		defConfig.setMaxIdle(config.optInt("maxIdle", DEFAULT_MAX_IDLE));
		defConfig.setMinIdle(config.optInt("minIdle", DEFAULT_MIN_IDLE));
		defConfig.setMaxTotal(config.optInt("maxTotal", DEFAULT_MAX_TOTAL));
		defConfig.setMaxWaitMillis(config.optLong("maxWaitMillis", DEFAULT_MAX_WAIT_MILLIS));
		defConfig.setBlockWhenExhausted(config.optBoolean("blockWhenExhausted", DEFAULT_BLOCK_WHEN_EXHAUSTED));
		defConfig.setTestOnCreate(config.optBoolean("testOnCreate", DEFAULT_TEST_ON_CREATE));
		defConfig.setTestOnBorrow(config.optBoolean("testOnBorrow", DEFAULT_TEST_ON_BORROW));
		defConfig.setTestOnReturn(config.optBoolean("testOnReturn", DEFAULT_TEST_ON_RETURN));
		defConfig.setTestWhileIdle(config.optBoolean("testWhileIdle", DEFAULT_TEST_WHILE_IDLE));
		defConfig.setNumTestsPerEvictionRun(config.optInt("numTestsPerEvictionRun", DEFAULT_NUM_TESTS_PER_EVICTION_RUN));
		defConfig.setMinEvictableIdleTimeMillis(config.optLong("minEvictableIdleTimeMillis", DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
		defConfig.setTimeBetweenEvictionRunsMillis(config.optLong("timeBetweenEvictionRunsMillis", DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS));
		defConfig.setSoftMinEvictableIdleTimeMillis(config.optLong("softMinEvictableIdleTimeMillis", DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
		defConfig.setEvictionPolicyClassName(config.optString("evictionPolicyClassName", DEFAULT_EVICTION_POLICY_CLASS_NAME));

		List<Method> methods = new ArrayList<>();
		Class<? extends Pool>[] classes = ClassUtils.getClasses(Pool.class.getPackage(), Pool.class);
		for(Class<? extends Pool> clazz : classes) ExceptionUtils.doSilentThrowsRunnable(false, () -> {
			if(Modifier.isAbstract(clazz.getModifiers()) || !Pool.class.isAssignableFrom(clazz)) return;
			methods.add(clazz.getDeclaredMethod("addPool"));
		}); METHODS_$Pool_addPool = methods.toArray(new Method[0]);

		for(Method addPool : METHODS_$Pool_addPool)
			ExceptionUtils.doSilentThrowsRunnable(false, () -> addPool.invoke(null));
	}

	public static void startLocalPool() {
		IntObjectHashMap<Pool<?>> result = localPools.get();
		if(result != null) { result.remove(POOL_HASHCODE); return; }
		result = new IntObjectHashMap<>(); localPools.set(result);
		for(Method addPool : METHODS_$Pool_addPool)
			ExceptionUtils.doSilentThrowsRunnable(false, () -> addPool.invoke(null));
	}
	public static void endLocalPool() {
		IntObjectHashMap<Pool<?>> result = localPools.get();
		if(result == null) return; result.put(POOL_HASHCODE, null);
	}
	protected static IntObjectHashMap<Pool<?>> getPoolMap() {
		IntObjectHashMap<Pool<?>> result = localPools.get();
		return result != null && !result.containsKey(POOL_HASHCODE) ? result : pools;
	}

	public static <T, C extends Class<? extends T>, P extends Pool<? extends T>> void addPool(C type, P pool) { getPoolMap().put(type.hashCode(), pool); }
	public static <T, C extends Class<? extends T>, P extends Pool<? extends T>> P removePool(C type) { return (P) getPoolMap().remove(type.hashCode()); }
	public static <T, C extends Class<? extends T>, P extends Pool<? extends T>> P getPool(C type) { return (P) getPoolMap().get(type.hashCode()); }
	public static boolean containsPool(Class<?> type) { return getPoolMap().containsKey(type.hashCode()); }
	public static MutableIntObjectMap<Pool<?>> getPools() { return getPoolMap().asUnmodifiable(); }
	public static <T> PoolConfig<T> getDefaultPoolConfig() { return (PoolConfig<T>) defConfig; }

	public static <T, C extends Class<? extends T>, V extends T, P extends Pool<? extends T>> T tryBorrow(P pool) { try { T result = pool.borrowObject(); PoolCleaner poolCleaner = PoolCleaner.threadLocal.get(); if(poolCleaner != null) poolCleaner.add(pool, result); return result; } catch(Exception e) { throw new Error(e); } }
	public static <T, C extends Class<? extends T>, V extends T, P extends Pool<? extends T>> T tryBorrow(C type) { return tryBorrow(getPool(type)); }
	public static <T, C extends Class<? extends T>, V extends T, P extends Pool<? extends T>> void returnObject(P pool, V object) { try { PoolCleaner poolCleaner = PoolCleaner.threadLocal.get(); if(poolCleaner != null) poolCleaner.remove(pool, object); pool.returnObject(object); } catch(Exception e) { throw new Error(e); } }
	public static <T, C extends Class<? extends T>, V extends T, P extends Pool<? extends T>> void returnObject(C type, V object) { returnObject(getPool(type), object); }

	public Pool(PoolFactory<T> factory, PoolConfig<T> config) { super(config, factory); }
	public Pool(PoolFactory<T> factory) { this(factory, new PoolConfig<>()); }

	public boolean isUsing(Object object) { return allObjects.containsKey(identityWrapper(object)); }
}
