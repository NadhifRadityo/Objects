package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.BVec4;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.BitwiseUtils;

@SuppressWarnings("jol")
public class BVec4Pool extends GenTypePool<BVec4> {

	public static void addPool() {
		if(Pool.containsPool(BVec4.class)) return;
		BVec4Pool pool = new BVec4Pool(new BVec4PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(BVec4.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(BVec4.class)) return;
//		Pool.removePool(BVec4.class);
//	}

	protected BVec4Pool(PoolFactory<BVec4> factory) { super(factory); }
	protected BVec4Pool(PoolFactory<BVec4> factory, PoolConfig<BVec4> config) { super(factory, config); }

	protected static class BVec4PoolFactory extends PoolFactory<BVec4> {
		@Override public BVec4 create() { return new BVec4(); }
		@Override public PooledObject<BVec4> wrap(BVec4 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<BVec4> p) { return !BitwiseUtils.or(p.getObject().d); }
		@Override public void passivateObject(PooledObject<BVec4> p) { p.getObject().reset(); }
	}
}
