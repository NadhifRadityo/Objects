package io.github.NadhifRadityo.Objects.$Object.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.$Object.List.PriorityList;
import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.$Object.Pool.PooledObject;

import java.util.Map;
import java.util.stream.Collector;

@SuppressWarnings("jol")
public class PriorityListPool extends Pool<PriorityList<Object>> {

	public static void addPool() {
		if(Pool.containsPool(PriorityList.class)) return;
		PriorityListPool pool = new PriorityListPool(new PriorityListPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(PriorityList.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(PriorityList.class)) return;
//		Pool.removePool(PriorityList.class);
//	}

	protected PriorityListPool(PoolFactory<PriorityList<Object>> factory) { super(factory); }
	protected PriorityListPool(PoolFactory<PriorityList<Object>> factory, PoolConfig<PriorityList<Object>> config) { super(factory, config); }

	protected static class PriorityListPoolFactory extends PoolFactory<PriorityList<Object>> {
		@Override public PriorityList<Object> create() { return new PriorityList<>(); }
		@Override public PooledObject<PriorityList<Object>> wrap(PriorityList<Object> obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<PriorityList<Object>> p) { return p.getObject().isEmpty(); }
		@Override public void passivateObject(PooledObject<PriorityList<Object>> p) { p.getObject().clear(); }
	}

	public static <T> Collector<Map.Entry<T, Integer>, ?, PriorityList<T>> getCollectors() { return Collector.of(() -> Pool.tryBorrow(Pool.getPool(PriorityList.class)),
			(m, e) -> m.add(e.getKey(), e.getValue()), (left, right) -> { for(Map.Entry<T, Integer> entry : right.getMap()) left.add(entry.getKey(), entry.getValue()); return left; }, Collector.Characteristics.IDENTITY_FINISH); }
}
