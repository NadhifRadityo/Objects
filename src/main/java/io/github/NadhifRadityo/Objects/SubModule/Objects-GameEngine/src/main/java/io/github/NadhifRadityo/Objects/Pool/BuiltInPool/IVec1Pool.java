package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVec1;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class IVec1Pool extends GenTypePool<IVec1> {

	public static void addPool() {
		if(Pool.containsPool(IVec1.class)) return;
		IVec1Pool pool = new IVec1Pool(new IVec1PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(IVec1.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(IVec1.class)) return;
//		Pool.removePool(IVec1.class);
//	}

	protected IVec1Pool(PoolFactory<IVec1> factory) { super(factory); }
	protected IVec1Pool(PoolFactory<IVec1> factory, PoolConfig<IVec1> config) { super(factory, config); }

	protected static class IVec1PoolFactory extends PoolFactory<IVec1> {
		@Override public IVec1 create() { return new IVec1(); }
		@Override public PooledObject<IVec1> wrap(IVec1 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<IVec1> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<IVec1> p) { p.getObject().reset(); }
	}
}
