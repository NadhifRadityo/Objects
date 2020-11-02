package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FVec2;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class FVec2Pool extends GenTypePool<FVec2> {

	public static void addPool() {
		if(Pool.containsPool(FVec2.class)) return;
		FVec2Pool pool = new FVec2Pool(new FVec2PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(FVec2.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(FVec2.class)) return;
//		Pool.removePool(FVec2.class);
//	}

	protected FVec2Pool(PoolFactory<FVec2> factory) { super(factory); }
	protected FVec2Pool(PoolFactory<FVec2> factory, PoolConfig<FVec2> config) { super(factory, config); }

	protected static class FVec2PoolFactory extends PoolFactory<FVec2> {
		@Override public FVec2 create() { return new FVec2(); }
		@Override public PooledObject<FVec2> wrap(FVec2 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<FVec2> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<FVec2> p) { p.getObject().reset(); }
	}
}
