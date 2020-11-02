package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FVec3;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class FVec3Pool extends GenTypePool<FVec3> {

	public static void addPool() {
		if(Pool.containsPool(FVec3.class)) return;
		FVec3Pool pool = new FVec3Pool(new FVec3PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(FVec3.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(FVec3.class)) return;
//		Pool.removePool(FVec3.class);
//	}

	protected FVec3Pool(PoolFactory<FVec3> factory) { super(factory); }
	protected FVec3Pool(PoolFactory<FVec3> factory, PoolConfig<FVec3> config) { super(factory, config); }

	protected static class FVec3PoolFactory extends PoolFactory<FVec3> {
		@Override public FVec3 create() { return new FVec3(); }
		@Override public PooledObject<FVec3> wrap(FVec3 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<FVec3> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<FVec3> p) { p.getObject().reset(); }
	}
}
