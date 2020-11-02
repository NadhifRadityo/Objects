package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SMat3;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class SMat3Pool extends GenTypePool<SMat3> {

	public static void addPool() {
		if(Pool.containsPool(SMat3.class)) return;
		SMat3Pool pool = new SMat3Pool(new SMat3PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(SMat3.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(SMat3.class)) return;
//		Pool.removePool(SMat3.class);
//	}

	protected SMat3Pool(PoolFactory<SMat3> factory) { super(factory); }
	protected SMat3Pool(PoolFactory<SMat3> factory, PoolConfig<SMat3> config) { super(factory, config); }

	protected static class SMat3PoolFactory extends PoolFactory<SMat3> {
		@Override public SMat3 create() { return new SMat3(); }
		@Override public PooledObject<SMat3> wrap(SMat3 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<SMat3> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<SMat3> p) { p.getObject().reset(); }
	}
}
