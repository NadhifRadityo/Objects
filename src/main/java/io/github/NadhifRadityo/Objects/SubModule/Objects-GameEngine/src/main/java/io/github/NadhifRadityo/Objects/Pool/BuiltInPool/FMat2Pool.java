package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FMat2;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class FMat2Pool extends GenTypePool<FMat2> {

	public static void addPool() {
		if(Pool.containsPool(FMat2.class)) return;
		FMat2Pool pool = new FMat2Pool(new FMat2PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(FMat2.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(FMat2.class)) return;
//		Pool.removePool(FMat2.class);
//	}

	protected FMat2Pool(PoolFactory<FMat2> factory) { super(factory); }
	protected FMat2Pool(PoolFactory<FMat2> factory, PoolConfig<FMat2> config) { super(factory, config); }

	protected static class FMat2PoolFactory extends PoolFactory<FMat2> {
		@Override public FMat2 create() { return new FMat2(); }
		@Override public PooledObject<FMat2> wrap(FMat2 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<FMat2> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<FMat2> p) { p.getObject().reset(); }
	}
}
