package io.github.NadhifRadityo.Objects.Pool;

import io.github.NadhifRadityo.Objects.ObjectUtils.EqualsProxy;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy;
import io.github.NadhifRadityo.Objects.Pool.BuiltInPool.ArrayListPool;
import io.github.NadhifRadityo.Objects.Pool.BuiltInPool.HashMapPool;
import io.github.NadhifRadityo.Objects.Pool.BuiltInPool.StringBuilderPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.lang.reflect.Field;
import java.util.*;

@SuppressWarnings("unchecked")
public class Pool<T> extends GenericObjectPool<Proxy<T>> {
	private static final Map<Class, Pool> pools = new HashMap<>();
	private static final PoolConfig defConfig;
	static {
		defConfig = new PoolConfig();
		defConfig.setMaxWaitMillis(-1L);

		HashMapPool.addPool();
		ArrayListPool.addPool();
		StringBuilderPool.addPool();
	}

	public static <T> void addPool(Class<T> type, Pool<? extends T> pool) { pools.put(type, pool); }
//	public static <T> void removePool(Class<T> type) { pools.remove(type); }
	public static <T> Pool<? extends T> getPool(Class<T> type) { return pools.get(type); }
	public static boolean containsPool(Class type) { return pools.containsKey(type); }
	public static Map<Class, Pool> getPools() { return Collections.unmodifiableMap(pools); }
	public static PoolConfig getDefaultPoolConfig() { return defConfig.clone(); }

	public static <T> T tryBorrow(Pool<T> pool) { try { return pool.borrow(); } catch(Exception e) { throw new Error(e); } }
	public static <T> T tryBorrow(Class<T> type) { return tryBorrow(getPool(type)); }
	public static <T> void returnObject(Pool<T> pool, T object) { pool.putBack(object); }
	public static <T> void returnObject(Class<T> type, T object) { returnObject(getPool(type), object); }

	public Pool(PoolFactory<T> factory) { super(factory); }
	public Pool(PoolFactory<T> factory, PoolConfig config) { super(factory, config); }
	public Pool(PoolFactory<T> factory, PoolConfig config, AbandonedConfig abandonedConfig) { super(factory, config, abandonedConfig); }

	@Deprecated @Override public EqualsProxy<T> borrowObject(long borrowMaxWaitMillis) { throw new UnsupportedOperationException("Use borrow instead!"); }
	@Deprecated @Override public EqualsProxy<T> borrowObject() { throw new UnsupportedOperationException("Use borrow instead!"); }
	public T borrow(long borrowMaxWaitMillis) throws Exception { return super.borrowObject(borrowMaxWaitMillis).get(); }
	public T borrow() throws Exception { return borrow(getMaxWaitMillis()); }

	@Deprecated @Override public void returnObject(Proxy<T> obj) { throw new UnsupportedOperationException("Use putBack instead!"); }
	public void putBack(T obj) {
		Proxy<T> referencedProxy = getReferencedProxy(getAllObjectsField(this).keySet(), obj);
		if(referencedProxy == null) throw new IllegalStateException("Returned object not currently part of this pool");
		super.returnObject(referencedProxy);
	}

	@Deprecated @Override public void invalidateObject(Proxy<T> obj) { throw new UnsupportedOperationException("Use invalidate instead!"); }
	public void invalidate(T obj) throws Exception {
		Proxy<T> referencedProxy = getReferencedProxy(getAllObjectsField(this).keySet(), obj);
		if(referencedProxy == null) throw new IllegalStateException("Returned object not currently part of this pool");
		super.invalidateObject(referencedProxy);
	}

	@Deprecated @Override public void use(Proxy<T> pooledObject) { throw new UnsupportedOperationException("Use using instead!"); }
	public void using(T obj) {
		Proxy<T> referencedProxy = getReferencedProxy(getAllObjectsField(this).keySet(), obj);
		if(referencedProxy == null) throw new IllegalStateException("Returned object not currently part of this pool");
		super.use(referencedProxy);
	}

	// Hate using Reflection! This why you shouldn't use private. Use protected instead!
	// Except for non-fluid utils class. Extender can implement itself.
	private static final Field allObjectsField;
	static {
		try {
			allObjectsField = GenericObjectPool.class.getDeclaredField("allObjects");
			allObjectsField.setAccessible(true);
		} catch(Exception e) { throw new RuntimeException(e); }
	}

	// Even though I can use getAllObjectsField, that method will create new Set.
	// Since I created pool is for lowering memory usage.
	private static <T> Map<T, PooledObject<T>> getAllObjectsField(GenericObjectPool<T> instance) {
		try { return (Map) allObjectsField.get(instance); } catch(Exception e) { throw new RuntimeException(e); }
	}
	private static <T> Proxy<T> getReferencedProxy(Set<Proxy<T>> sets, T object) {
		for(Proxy<T> set : sets) {
			if(set.get() != object) continue;
//			if(!Objects.equals(set.get(), object)) continue;
//			if(System.identityHashCode(set.get()) != System.identityHashCode(object)) continue;
			return set;
		} return null;
	}
}
