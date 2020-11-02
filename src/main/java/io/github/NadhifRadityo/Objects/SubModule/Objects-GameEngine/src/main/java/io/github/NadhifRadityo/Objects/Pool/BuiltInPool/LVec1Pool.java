package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVec1;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class LVec1Pool extends GenTypePool<LVec1> {

	public static void addPool() {
		if(Pool.containsPool(LVec1.class)) return;
		LVec1Pool pool = new LVec1Pool(new LVec1PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(LVec1.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(LVec1.class)) return;
//		Pool.removePool(LVec1.class);
//	}

	protected LVec1Pool(PoolFactory<LVec1> factory) { super(factory); }
	protected LVec1Pool(PoolFactory<LVec1> factory, PoolConfig<LVec1> config) { super(factory, config); }

	protected static class LVec1PoolFactory extends PoolFactory<LVec1> {
		@Override public LVec1 create() { return new LVec1(); }
		@Override public PooledObject<LVec1> wrap(LVec1 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<LVec1> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<LVec1> p) { p.getObject().reset(); }
	}
}
