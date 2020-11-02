package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec1;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class Vec1Pool extends GenTypePool<Vec1> {

	public static void addPool() {
		if(Pool.containsPool(Vec1.class)) return;
		Vec1Pool pool = new Vec1Pool(new Vec1PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(Vec1.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(Vec1.class)) return;
//		Pool.removePool(Vec1.class);
//	}

	protected Vec1Pool(PoolFactory<Vec1> factory) { super(factory); }
	protected Vec1Pool(PoolFactory<Vec1> factory, PoolConfig<Vec1> config) { super(factory, config); }

	protected static class Vec1PoolFactory extends PoolFactory<Vec1> {
		@Override public Vec1 create() { return new Vec1(); }
		@Override public PooledObject<Vec1> wrap(Vec1 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<Vec1> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<Vec1> p) { p.getObject().reset(); }
	}
}
