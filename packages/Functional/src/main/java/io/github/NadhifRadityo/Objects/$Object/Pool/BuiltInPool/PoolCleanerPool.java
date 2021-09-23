package io.github.NadhifRadityo.Objects.$Object.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolCleaner;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.$Object.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.$Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.UnsafeUtils;

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
		private static final UnsafeUtils.Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);
		private static final long AFIELD_PoolCleaner_pooledObjects;

		static { try {
			AFIELD_PoolCleaner_pooledObjects = unsafe.objectFieldOffset(PoolCleaner.class.getDeclaredField("pooledObjects"));
		} catch(Exception e) { throw new Error(e); } }

		@Override public PoolCleaner create() { return new PoolCleaner(); }
		@Override public PooledObject<PoolCleaner> wrap(PoolCleaner obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<PoolCleaner> p) {
			Map<?, ?> pooledObjects = (Map<?, ?>) unsafe.getObject(p.getObject(), AFIELD_PoolCleaner_pooledObjects);
			return pooledObjects.isEmpty();
		}
		@Override public void passivateObject(PooledObject<PoolCleaner> p) {
			Map<?, ?> pooledObjects = (Map<?, ?>) unsafe.getObject(p.getObject(), AFIELD_PoolCleaner_pooledObjects);
			pooledObjects.clear();
		}
	}
}
