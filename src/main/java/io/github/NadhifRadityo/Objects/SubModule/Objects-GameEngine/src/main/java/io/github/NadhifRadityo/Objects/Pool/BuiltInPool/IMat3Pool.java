package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IMat3;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class IMat3Pool extends GenTypePool<IMat3> {

	public static void addPool() {
		if(Pool.containsPool(IMat3.class)) return;
		IMat3Pool pool = new IMat3Pool(new IMat3PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(IMat3.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(IMat3.class)) return;
//		Pool.removePool(IMat3.class);
//	}

	protected IMat3Pool(PoolFactory<IMat3> factory) { super(factory); }
	protected IMat3Pool(PoolFactory<IMat3> factory, PoolConfig<IMat3> config) { super(factory, config); }

	protected static class IMat3PoolFactory extends PoolFactory<IMat3> {
		@Override public IMat3 create() { return new IMat3(); }
		@Override public PooledObject<IMat3> wrap(IMat3 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<IMat3> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<IMat3> p) { p.getObject().reset(); }
	}
}
