package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVec2;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class LVec2Pool extends GenTypePool<LVec2> {

	public static void addPool() {
		if(Pool.containsPool(LVec2.class)) return;
		LVec2Pool pool = new LVec2Pool(new LVec2PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(LVec2.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(LVec2.class)) return;
//		Pool.removePool(LVec2.class);
//	}

	protected LVec2Pool(PoolFactory<LVec2> factory) { super(factory); }
	protected LVec2Pool(PoolFactory<LVec2> factory, PoolConfig<LVec2> config) { super(factory, config); }

	protected static class LVec2PoolFactory extends PoolFactory<LVec2> {
		@Override public LVec2 create() { return new LVec2(); }
		@Override public PooledObject<LVec2> wrap(LVec2 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<LVec2> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<LVec2> p) { p.getObject().reset(); }
	}
}
