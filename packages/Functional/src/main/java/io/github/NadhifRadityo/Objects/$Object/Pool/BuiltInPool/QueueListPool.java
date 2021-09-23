package io.github.NadhifRadityo.Objects.$Object.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.$Object.List.QueueList;
import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.$Object.Pool.PooledObject;

import java.util.Map;
import java.util.stream.Collector;

@SuppressWarnings("jol")
public class QueueListPool extends Pool<QueueList<Object>> {

	public static void addPool() {
		if(Pool.containsPool(QueueList.class)) return;
		QueueListPool pool = new QueueListPool(new QueueListPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(QueueList.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(QueueList.class)) return;
//		Pool.removePool(QueueList.class);
//	}

	protected QueueListPool(PoolFactory<QueueList<Object>> factory) { super(factory); }
	protected QueueListPool(PoolFactory<QueueList<Object>> factory, PoolConfig<QueueList<Object>> config) { super(factory, config); }

	protected static class QueueListPoolFactory extends PoolFactory<QueueList<Object>> {
		@Override public QueueList<Object> create() { return new QueueList<>(); }
		@Override public PooledObject<QueueList<Object>> wrap(QueueList<Object> obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<QueueList<Object>> p) { return p.getObject().isEmpty(); }
		@Override public void passivateObject(PooledObject<QueueList<Object>> p) { p.getObject().clear(); }
	}

	public static <T> Collector<Map.Entry<T, Long>, ?, QueueList<T>> getCollectors() { return Collector.of(() -> Pool.tryBorrow(Pool.getPool(QueueList.class)),
			(m, e) -> m.add(e.getKey(), e.getValue()), (left, right) -> { for(Map.Entry<T, Long> entry : right.getMap()) left.add(entry.getKey(), entry.getValue()); return left; }, Collector.Characteristics.IDENTITY_FINISH); }
}
