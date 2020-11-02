package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LMat4;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class LMat4Pool extends GenTypePool<LMat4> {

	public static void addPool() {
		if(Pool.containsPool(LMat4.class)) return;
		LMat4Pool pool = new LMat4Pool(new LMat4PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(LMat4.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(LMat4.class)) return;
//		Pool.removePool(LMat4.class);
//	}

	protected LMat4Pool(PoolFactory<LMat4> factory) { super(factory); }
	protected LMat4Pool(PoolFactory<LMat4> factory, PoolConfig<LMat4> config) { super(factory, config); }

	protected static class LMat4PoolFactory extends PoolFactory<LMat4> {
		@Override public LMat4 create() { return new LMat4(); }
		@Override public PooledObject<LMat4> wrap(LMat4 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<LMat4> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<LMat4> p) { p.getObject().reset(); }
	}
}
