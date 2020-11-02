package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SMat4;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class SMat4Pool extends GenTypePool<SMat4> {

	public static void addPool() {
		if(Pool.containsPool(SMat4.class)) return;
		SMat4Pool pool = new SMat4Pool(new SMat4PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(SMat4.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(SMat4.class)) return;
//		Pool.removePool(SMat4.class);
//	}

	protected SMat4Pool(PoolFactory<SMat4> factory) { super(factory); }
	protected SMat4Pool(PoolFactory<SMat4> factory, PoolConfig<SMat4> config) { super(factory, config); }

	protected static class SMat4PoolFactory extends PoolFactory<SMat4> {
		@Override public SMat4 create() { return new SMat4(); }
		@Override public PooledObject<SMat4> wrap(SMat4 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<SMat4> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<SMat4> p) { p.getObject().reset(); }
	}
}
