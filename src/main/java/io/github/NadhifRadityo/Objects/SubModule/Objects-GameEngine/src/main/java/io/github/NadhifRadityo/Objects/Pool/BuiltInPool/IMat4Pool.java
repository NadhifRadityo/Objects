package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IMat4;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class IMat4Pool extends GenTypePool<IMat4> {

	public static void addPool() {
		if(Pool.containsPool(IMat4.class)) return;
		IMat4Pool pool = new IMat4Pool(new IMat4PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(IMat4.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(IMat4.class)) return;
//		Pool.removePool(IMat4.class);
//	}

	protected IMat4Pool(PoolFactory<IMat4> factory) { super(factory); }
	protected IMat4Pool(PoolFactory<IMat4> factory, PoolConfig<IMat4> config) { super(factory, config); }

	protected static class IMat4PoolFactory extends PoolFactory<IMat4> {
		@Override public IMat4 create() { return new IMat4(); }
		@Override public PooledObject<IMat4> wrap(IMat4 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<IMat4> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<IMat4> p) { p.getObject().reset(); }
	}
}
