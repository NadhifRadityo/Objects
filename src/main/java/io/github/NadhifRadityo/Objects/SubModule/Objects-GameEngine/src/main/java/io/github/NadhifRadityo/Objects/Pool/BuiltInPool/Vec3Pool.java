package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class Vec3Pool extends GenTypePool<Vec3> {

	public static void addPool() {
		if(Pool.containsPool(Vec3.class)) return;
		Vec3Pool pool = new Vec3Pool(new Vec3PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(Vec3.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(Vec3.class)) return;
//		Pool.removePool(Vec3.class);
//	}

	protected Vec3Pool(PoolFactory<Vec3> factory) { super(factory); }
	protected Vec3Pool(PoolFactory<Vec3> factory, PoolConfig<Vec3> config) { super(factory, config); }

	protected static class Vec3PoolFactory extends PoolFactory<Vec3> {
		@Override public Vec3 create() { return new Vec3(); }
		@Override public PooledObject<Vec3> wrap(Vec3 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<Vec3> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<Vec3> p) { p.getObject().reset(); }
	}
}
