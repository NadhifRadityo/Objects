package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVec3;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class IVec3Pool extends GenTypePool<IVec3> {

	public static void addPool() {
		if(Pool.containsPool(IVec3.class)) return;
		IVec3Pool pool = new IVec3Pool(new IVec3PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(IVec3.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(IVec3.class)) return;
//		Pool.removePool(IVec3.class);
//	}

	protected IVec3Pool(PoolFactory<IVec3> factory) { super(factory); }
	protected IVec3Pool(PoolFactory<IVec3> factory, PoolConfig<IVec3> config) { super(factory, config); }

	protected static class IVec3PoolFactory extends PoolFactory<IVec3> {
		@Override public IVec3 create() { return new IVec3(); }
		@Override public PooledObject<IVec3> wrap(IVec3 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<IVec3> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<IVec3> p) { p.getObject().reset(); }
	}
}
