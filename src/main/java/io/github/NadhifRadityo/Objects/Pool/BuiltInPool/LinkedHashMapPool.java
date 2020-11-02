package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.MapUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collector;

@SuppressWarnings("jol")
public class LinkedHashMapPool extends Pool<LinkedHashMap<Object, Object>> {

	public static void addPool() {
		if(Pool.containsPool(LinkedHashMap.class)) return;
		LinkedHashMapPool pool = new LinkedHashMapPool(new LinkedHashMapPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(LinkedHashMap.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(LinkedHashMap.class)) return;
//		Pool.removePool(LinkedHashMap.class);
//	}

	protected LinkedHashMapPool(PoolFactory<LinkedHashMap<Object, Object>> factory) { super(factory); }
	protected LinkedHashMapPool(PoolFactory<LinkedHashMap<Object, Object>> factory, PoolConfig<LinkedHashMap<Object, Object>> config) { super(factory, config); }

	protected static class LinkedHashMapPoolFactory extends PoolFactory<LinkedHashMap<Object, Object>> {
		@Override public LinkedHashMap<Object, Object> create() { return MapUtils.optimizedLinkedHashMap()/*new LinkedHashMap<>()*/; }
		@Override public PooledObject<LinkedHashMap<Object, Object>> wrap(LinkedHashMap<Object, Object> obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<LinkedHashMap<Object, Object>> p) { return p.getObject().isEmpty(); }
		@Override public void passivateObject(PooledObject<LinkedHashMap<Object, Object>> p) { p.getObject().clear(); }
	}

	public static <K, V> Collector<Map.Entry<K, V>, ?, LinkedHashMap<K, V>> getCollectors() { return Collector.of(() -> Pool.tryBorrow(Pool.getPool(LinkedHashMap.class)),
			(m, e) -> m.put(e.getKey(), e.getValue()), (left, right) -> { left.putAll(right); return left; }, Collector.Characteristics.IDENTITY_FINISH); }
}
