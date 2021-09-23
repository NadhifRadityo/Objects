package io.github.NadhifRadityo.Objects.$Object.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.$Object.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.$Utilizations.MapUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;

@SuppressWarnings("jol")
public class HashMapPool extends Pool<HashMap<Object, Object>> {

	public static void addPool() {
		if(Pool.containsPool(HashMap.class)) return;
		HashMapPool pool = new HashMapPool(new HashMapPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(HashMap.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(HashMap.class)) return;
//		Pool.removePool(HashMap.class);
//	}

	protected HashMapPool(PoolFactory<HashMap<Object, Object>> factory) { super(factory); }
	protected HashMapPool(PoolFactory<HashMap<Object, Object>> factory, PoolConfig<HashMap<Object, Object>> config) { super(factory, config); }

	protected static class HashMapPoolFactory extends PoolFactory<HashMap<Object, Object>> {
		@Override public HashMap<Object, Object> create() { return MapUtils.optimizedHashMap()/*new HashMap()*/; }
		@Override public PooledObject<HashMap<Object, Object>> wrap(HashMap<Object, Object> obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<HashMap<Object, Object>> p) { return p.getObject().isEmpty(); }
		@Override public void passivateObject(PooledObject<HashMap<Object, Object>> p) { p.getObject().clear(); }
	}

	public static <K, V> Collector<Map.Entry<K, V>, ?, HashMap<K, V>> getCollectors() { return Collector.of(() -> Pool.tryBorrow(Pool.getPool(HashMap.class)),
			(m, e) -> m.put(e.getKey(), e.getValue()), (left, right) -> { left.putAll(right); return left; }, Collector.Characteristics.IDENTITY_FINISH); }
}
