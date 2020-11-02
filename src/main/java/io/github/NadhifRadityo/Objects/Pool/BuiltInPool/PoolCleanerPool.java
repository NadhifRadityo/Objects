package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolCleaner;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;

import java.lang.reflect.Field;
import java.util.Map;

@SuppressWarnings("jol")
public class PoolCleanerPool extends Pool<PoolCleaner> {

	public static void addPool() {
		if(Pool.containsPool(PoolCleaner.class)) return;
		PoolCleanerPool pool = new PoolCleanerPool(new PoolCleanerPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(PoolCleaner.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(PoolCleaner.class)) return;
//		Pool.removePool(PoolCleaner.class);
//	}

	protected PoolCleanerPool(PoolFactory<PoolCleaner> factory) { super(factory); }
	protected PoolCleanerPool(PoolFactory<PoolCleaner> factory, PoolConfig<PoolCleaner> config) { super(factory, config); }

	protected static class PoolCleanerPoolFactory extends PoolFactory<PoolCleaner> {
		private static final Field FIELD_PoolCleaner_pooledObjects;
		static { try {
			FIELD_PoolCleaner_pooledObjects = PoolCleaner.class.getDeclaredField("pooledObjects");
			FIELD_PoolCleaner_pooledObjects.setAccessible(true);
		} catch(Exception e) { throw new Error(e); } }

		@Override public PoolCleaner create() { return new PoolCleaner(); }
		@Override public PooledObject<PoolCleaner> wrap(PoolCleaner obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<PoolCleaner> p) { try {
			Map<?, ?> pooledObjects = (Map<?, ?>) FIELD_PoolCleaner_pooledObjects.get(p.getObject());
			return pooledObjects.isEmpty(); } catch(Exception e) { throw new Error(e); } }
		@Override public void passivateObject(PooledObject<PoolCleaner> p) { try {
			Map<?, ?> pooledObjects = (Map<?, ?>) FIELD_PoolCleaner_pooledObjects.get(p.getObject());
			pooledObjects.clear(); } catch(Exception e) { throw new Error(e); } }
	}
}
