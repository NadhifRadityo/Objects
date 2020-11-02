package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.BVec3;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.BitwiseUtils;

@SuppressWarnings("jol")
public class BVec3Pool extends GenTypePool<BVec3> {

	public static void addPool() {
		if(Pool.containsPool(BVec3.class)) return;
		BVec3Pool pool = new BVec3Pool(new BVec3PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(BVec3.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(BVec3.class)) return;
//		Pool.removePool(BVec3.class);
//	}

	protected BVec3Pool(PoolFactory<BVec3> factory) { super(factory); }
	protected BVec3Pool(PoolFactory<BVec3> factory, PoolConfig<BVec3> config) { super(factory, config); }

	protected static class BVec3PoolFactory extends PoolFactory<BVec3> {
		@Override public BVec3 create() { return new BVec3(); }
		@Override public PooledObject<BVec3> wrap(BVec3 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<BVec3> p) { return !BitwiseUtils.or(p.getObject().d); }
		@Override public void passivateObject(PooledObject<BVec3> p) { p.getObject().reset(); }
	}
}
