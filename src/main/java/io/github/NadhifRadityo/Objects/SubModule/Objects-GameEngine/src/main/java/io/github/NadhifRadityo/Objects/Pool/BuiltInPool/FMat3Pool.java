package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FMat3;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class FMat3Pool extends GenTypePool<FMat3> {

	public static void addPool() {
		if(Pool.containsPool(FMat3.class)) return;
		FMat3Pool pool = new FMat3Pool(new FMat3PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(FMat3.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(FMat3.class)) return;
//		Pool.removePool(FMat3.class);
//	}

	protected FMat3Pool(PoolFactory<FMat3> factory) { super(factory); }
	protected FMat3Pool(PoolFactory<FMat3> factory, PoolConfig<FMat3> config) { super(factory, config); }

	protected static class FMat3PoolFactory extends PoolFactory<FMat3> {
		@Override public FMat3 create() { return new FMat3(); }
		@Override public PooledObject<FMat3> wrap(FMat3 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<FMat3> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<FMat3> p) { p.getObject().reset(); }
	}
}
