package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVec3;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class LVec3Pool extends GenTypePool<LVec3> {

	public static void addPool() {
		if(Pool.containsPool(LVec3.class)) return;
		LVec3Pool pool = new LVec3Pool(new LVec3PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(LVec3.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(LVec3.class)) return;
//		Pool.removePool(LVec3.class);
//	}

	protected LVec3Pool(PoolFactory<LVec3> factory) { super(factory); }
	protected LVec3Pool(PoolFactory<LVec3> factory, PoolConfig<LVec3> config) { super(factory, config); }

	protected static class LVec3PoolFactory extends PoolFactory<LVec3> {
		@Override public LVec3 create() { return new LVec3(); }
		@Override public PooledObject<LVec3> wrap(LVec3 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<LVec3> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<LVec3> p) { p.getObject().reset(); }
	}
}
