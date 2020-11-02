package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LMat3;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class LMat3Pool extends GenTypePool<LMat3> {

	public static void addPool() {
		if(Pool.containsPool(LMat3.class)) return;
		LMat3Pool pool = new LMat3Pool(new LMat3PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(LMat3.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(LMat3.class)) return;
//		Pool.removePool(LMat3.class);
//	}

	protected LMat3Pool(PoolFactory<LMat3> factory) { super(factory); }
	protected LMat3Pool(PoolFactory<LMat3> factory, PoolConfig<LMat3> config) { super(factory, config); }

	protected static class LMat3PoolFactory extends PoolFactory<LMat3> {
		@Override public LMat3 create() { return new LMat3(); }
		@Override public PooledObject<LMat3> wrap(LMat3 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<LMat3> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<LMat3> p) { p.getObject().reset(); }
	}
}
