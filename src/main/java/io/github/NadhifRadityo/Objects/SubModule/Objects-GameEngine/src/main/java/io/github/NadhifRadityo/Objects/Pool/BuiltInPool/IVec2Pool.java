package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVec2;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class IVec2Pool extends GenTypePool<IVec2> {

	public static void addPool() {
		if(Pool.containsPool(IVec2.class)) return;
		IVec2Pool pool = new IVec2Pool(new IVec2PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(IVec2.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(IVec2.class)) return;
//		Pool.removePool(IVec2.class);
//	}

	protected IVec2Pool(PoolFactory<IVec2> factory) { super(factory); }
	protected IVec2Pool(PoolFactory<IVec2> factory, PoolConfig<IVec2> config) { super(factory, config); }

	protected static class IVec2PoolFactory extends PoolFactory<IVec2> {
		@Override public IVec2 create() { return new IVec2(); }
		@Override public PooledObject<IVec2> wrap(IVec2 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<IVec2> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<IVec2> p) { p.getObject().reset(); }
	}
}
