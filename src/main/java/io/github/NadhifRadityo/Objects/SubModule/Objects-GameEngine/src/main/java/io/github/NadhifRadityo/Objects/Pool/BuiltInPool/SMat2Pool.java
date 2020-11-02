package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SMat2;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class SMat2Pool extends GenTypePool<SMat2> {

	public static void addPool() {
		if(Pool.containsPool(SMat2.class)) return;
		SMat2Pool pool = new SMat2Pool(new SMat2PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(SMat2.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(SMat2.class)) return;
//		Pool.removePool(SMat2.class);
//	}

	protected SMat2Pool(PoolFactory<SMat2> factory) { super(factory); }
	protected SMat2Pool(PoolFactory<SMat2> factory, PoolConfig<SMat2> config) { super(factory, config); }

	protected static class SMat2PoolFactory extends PoolFactory<SMat2> {
		@Override public SMat2 create() { return new SMat2(); }
		@Override public PooledObject<SMat2> wrap(SMat2 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<SMat2> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<SMat2> p) { p.getObject().reset(); }
	}
}
