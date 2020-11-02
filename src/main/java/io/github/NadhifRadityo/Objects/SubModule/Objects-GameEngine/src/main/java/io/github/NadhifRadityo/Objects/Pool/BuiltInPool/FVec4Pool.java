package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FVec4;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class FVec4Pool extends GenTypePool<FVec4> {

	public static void addPool() {
		if(Pool.containsPool(FVec4.class)) return;
		FVec4Pool pool = new FVec4Pool(new FVec4PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(FVec4.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(FVec4.class)) return;
//		Pool.removePool(FVec4.class);
//	}

	protected FVec4Pool(PoolFactory<FVec4> factory) { super(factory); }
	protected FVec4Pool(PoolFactory<FVec4> factory, PoolConfig<FVec4> config) { super(factory, config); }

	protected static class FVec4PoolFactory extends PoolFactory<FVec4> {
		@Override public FVec4 create() { return new FVec4(); }
		@Override public PooledObject<FVec4> wrap(FVec4 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<FVec4> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<FVec4> p) { p.getObject().reset(); }
	}
}
