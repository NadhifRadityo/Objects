package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVec4;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class LVec4Pool extends GenTypePool<LVec4> {

	public static void addPool() {
		if(Pool.containsPool(LVec4.class)) return;
		LVec4Pool pool = new LVec4Pool(new LVec4PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(LVec4.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(LVec4.class)) return;
//		Pool.removePool(LVec4.class);
//	}

	protected LVec4Pool(PoolFactory<LVec4> factory) { super(factory); }
	protected LVec4Pool(PoolFactory<LVec4> factory, PoolConfig<LVec4> config) { super(factory, config); }

	protected static class LVec4PoolFactory extends PoolFactory<LVec4> {
		@Override public LVec4 create() { return new LVec4(); }
		@Override public PooledObject<LVec4> wrap(LVec4 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<LVec4> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<LVec4> p) { p.getObject().reset(); }
	}
}
