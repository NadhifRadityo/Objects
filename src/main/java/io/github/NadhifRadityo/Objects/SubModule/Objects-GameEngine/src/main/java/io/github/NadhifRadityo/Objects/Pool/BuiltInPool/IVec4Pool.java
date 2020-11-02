package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVec4;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class IVec4Pool extends GenTypePool<IVec4> {

	public static void addPool() {
		if(Pool.containsPool(IVec4.class)) return;
		IVec4Pool pool = new IVec4Pool(new IVec4PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(IVec4.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(IVec4.class)) return;
//		Pool.removePool(IVec4.class);
//	}

	protected IVec4Pool(PoolFactory<IVec4> factory) { super(factory); }
	protected IVec4Pool(PoolFactory<IVec4> factory, PoolConfig<IVec4> config) { super(factory, config); }

	protected static class IVec4PoolFactory extends PoolFactory<IVec4> {
		@Override public IVec4 create() { return new IVec4(); }
		@Override public PooledObject<IVec4> wrap(IVec4 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<IVec4> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<IVec4> p) { p.getObject().reset(); }
	}
}
