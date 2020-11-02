package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FVec1;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class FVec1Pool extends GenTypePool<FVec1> {

	public static void addPool() {
		if(Pool.containsPool(FVec1.class)) return;
		FVec1Pool pool = new FVec1Pool(new FVec1PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(FVec1.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(FVec1.class)) return;
//		Pool.removePool(FVec1.class);
//	}

	protected FVec1Pool(PoolFactory<FVec1> factory) { super(factory); }
	protected FVec1Pool(PoolFactory<FVec1> factory, PoolConfig<FVec1> config) { super(factory, config); }

	protected static class FVec1PoolFactory extends PoolFactory<FVec1> {
		@Override public FVec1 create() { return new FVec1(); }
		@Override public PooledObject<FVec1> wrap(FVec1 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<FVec1> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<FVec1> p) { p.getObject().reset(); }
	}
}
