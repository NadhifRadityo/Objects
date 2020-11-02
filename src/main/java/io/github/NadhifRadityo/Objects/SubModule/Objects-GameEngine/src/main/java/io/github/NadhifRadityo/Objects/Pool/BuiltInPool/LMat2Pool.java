package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LMat2;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class LMat2Pool extends GenTypePool<LMat2> {

	public static void addPool() {
		if(Pool.containsPool(LMat2.class)) return;
		LMat2Pool pool = new LMat2Pool(new LMat2PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(LMat2.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(LMat2.class)) return;
//		Pool.removePool(LMat2.class);
//	}

	protected LMat2Pool(PoolFactory<LMat2> factory) { super(factory); }
	protected LMat2Pool(PoolFactory<LMat2> factory, PoolConfig<LMat2> config) { super(factory, config); }

	protected static class LMat2PoolFactory extends PoolFactory<LMat2> {
		@Override public LMat2 create() { return new LMat2(); }
		@Override public PooledObject<LMat2> wrap(LMat2 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<LMat2> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<LMat2> p) { p.getObject().reset(); }
	}
}
