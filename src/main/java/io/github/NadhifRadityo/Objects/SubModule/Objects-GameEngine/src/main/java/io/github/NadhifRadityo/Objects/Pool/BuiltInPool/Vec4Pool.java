package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class Vec4Pool extends GenTypePool<Vec4> {

	public static void addPool() {
		if(Pool.containsPool(Vec4.class)) return;
		Vec4Pool pool = new Vec4Pool(new Vec4PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(Vec4.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(Vec4.class)) return;
//		Pool.removePool(Vec4.class);
//	}

	protected Vec4Pool(PoolFactory<Vec4> factory) { super(factory); }
	protected Vec4Pool(PoolFactory<Vec4> factory, PoolConfig<Vec4> config) { super(factory, config); }

	protected static class Vec4PoolFactory extends PoolFactory<Vec4> {
		@Override public Vec4 create() { return new Vec4(); }
		@Override public PooledObject<Vec4> wrap(Vec4 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<Vec4> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<Vec4> p) { p.getObject().reset(); }
	}
}
