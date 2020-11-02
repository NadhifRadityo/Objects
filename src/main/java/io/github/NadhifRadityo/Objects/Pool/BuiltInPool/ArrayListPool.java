package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.ListUtils;

import java.util.ArrayList;
import java.util.stream.Collector;

@SuppressWarnings("jol")
public class ArrayListPool extends Pool<ArrayList<Object>> {

	public static void addPool() {
		if(Pool.containsPool(ArrayList.class)) return;
		ArrayListPool pool = new ArrayListPool(new ArrayListPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(ArrayList.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(ArrayList.class)) return;
//		Pool.removePool(ArrayList.class);
//	}

	protected ArrayListPool(PoolFactory<ArrayList<Object>> factory) { super(factory); }
	protected ArrayListPool(PoolFactory<ArrayList<Object>> factory, PoolConfig<ArrayList<Object>> config) { super(factory, config); }

	protected static class ArrayListPoolFactory extends PoolFactory<ArrayList<Object>> {
		@Override public ArrayList<Object> create() { return ListUtils.optimizedArrayList()/*new ArrayList()*/; }
		@Override public PooledObject<ArrayList<Object>> wrap(ArrayList<Object> obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<ArrayList<Object>> p) { return p.getObject().isEmpty(); }
		@Override public void passivateObject(PooledObject<ArrayList<Object>> p) { p.getObject().clear(); }
	}

	public static <T> Collector<T, ?, ArrayList<T>> getCollectors() { return Collector.of(() -> Pool.tryBorrow(Pool.getPool(ArrayList.class)),
			ArrayList::add, (left, right) -> { left.addAll(right); return left; }, Collector.Characteristics.IDENTITY_FINISH); }
}
