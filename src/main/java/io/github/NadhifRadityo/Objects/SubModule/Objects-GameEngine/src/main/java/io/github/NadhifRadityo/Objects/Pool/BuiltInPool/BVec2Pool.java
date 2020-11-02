package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.BVec2;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.BitwiseUtils;

@SuppressWarnings("jol")
public class BVec2Pool extends GenTypePool<BVec2> {

	public static void addPool() {
		if(Pool.containsPool(BVec2.class)) return;
		BVec2Pool pool = new BVec2Pool(new BVec2PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(BVec2.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(BVec2.class)) return;
//		Pool.removePool(BVec2.class);
//	}

	protected BVec2Pool(PoolFactory<BVec2> factory) { super(factory); }
	protected BVec2Pool(PoolFactory<BVec2> factory, PoolConfig<BVec2> config) { super(factory, config); }

	protected static class BVec2PoolFactory extends PoolFactory<BVec2> {
		@Override public BVec2 create() { return new BVec2(); }
		@Override public PooledObject<BVec2> wrap(BVec2 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<BVec2> p) { return !BitwiseUtils.or(p.getObject().d); }
		@Override public void passivateObject(PooledObject<BVec2> p) { p.getObject().reset(); }
	}
}
