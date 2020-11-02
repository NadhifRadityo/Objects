package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IMat2;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class IMat2Pool extends GenTypePool<IMat2> {

	public static void addPool() {
		if(Pool.containsPool(IMat2.class)) return;
		IMat2Pool pool = new IMat2Pool(new IMat2PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(IMat2.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(IMat2.class)) return;
//		Pool.removePool(IMat2.class);
//	}

	protected IMat2Pool(PoolFactory<IMat2> factory) { super(factory); }
	protected IMat2Pool(PoolFactory<IMat2> factory, PoolConfig<IMat2> config) { super(factory, config); }

	protected static class IMat2PoolFactory extends PoolFactory<IMat2> {
		@Override public IMat2 create() { return new IMat2(); }
		@Override public PooledObject<IMat2> wrap(IMat2 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<IMat2> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<IMat2> p) { p.getObject().reset(); }
	}
}
