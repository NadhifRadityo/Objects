package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.BVec1;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.BitwiseUtils;

@SuppressWarnings("jol")
public class BVec1Pool extends GenTypePool<BVec1> {

	public static void addPool() {
		if(Pool.containsPool(BVec1.class)) return;
		BVec1Pool pool = new BVec1Pool(new BVec1PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(BVec1.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(BVec1.class)) return;
//		Pool.removePool(BVec1.class);
//	}

	protected BVec1Pool(PoolFactory<BVec1> factory) { super(factory); }
	protected BVec1Pool(PoolFactory<BVec1> factory, PoolConfig<BVec1> config) { super(factory, config); }

	protected static class BVec1PoolFactory extends PoolFactory<BVec1> {
		@Override public BVec1 create() { return new BVec1(); }
		@Override public PooledObject<BVec1> wrap(BVec1 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<BVec1> p) { return !BitwiseUtils.or(p.getObject().d); }
		@Override public void passivateObject(PooledObject<BVec1> p) { p.getObject().reset(); }
	}
}
