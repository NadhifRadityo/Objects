package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVec1;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class SVec1Pool extends GenTypePool<SVec1> {

	public static void addPool() {
		if(Pool.containsPool(SVec1.class)) return;
		SVec1Pool pool = new SVec1Pool(new SVec1PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(SVec1.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(SVec1.class)) return;
//		Pool.removePool(SVec1.class);
//	}

	protected SVec1Pool(PoolFactory<SVec1> factory) { super(factory); }
	protected SVec1Pool(PoolFactory<SVec1> factory, PoolConfig<SVec1> config) { super(factory, config); }

	protected static class SVec1PoolFactory extends PoolFactory<SVec1> {
		@Override public SVec1 create() { return new SVec1(); }
		@Override public PooledObject<SVec1> wrap(SVec1 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<SVec1> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<SVec1> p) { p.getObject().reset(); }
	}
}
