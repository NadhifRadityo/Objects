package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVec3;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class SVec3Pool extends GenTypePool<SVec3> {

	public static void addPool() {
		if(Pool.containsPool(SVec3.class)) return;
		SVec3Pool pool = new SVec3Pool(new SVec3PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(SVec3.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(SVec3.class)) return;
//		Pool.removePool(SVec3.class);
//	}

	protected SVec3Pool(PoolFactory<SVec3> factory) { super(factory); }
	protected SVec3Pool(PoolFactory<SVec3> factory, PoolConfig<SVec3> config) { super(factory, config); }

	protected static class SVec3PoolFactory extends PoolFactory<SVec3> {
		@Override public SVec3 create() { return new SVec3(); }
		@Override public PooledObject<SVec3> wrap(SVec3 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<SVec3> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<SVec3> p) { p.getObject().reset(); }
	}
}
