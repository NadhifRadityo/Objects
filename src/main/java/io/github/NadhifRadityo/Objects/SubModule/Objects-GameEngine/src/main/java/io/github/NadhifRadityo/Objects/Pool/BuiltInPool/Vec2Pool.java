package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class Vec2Pool extends GenTypePool<Vec2> {

	public static void addPool() {
		if(Pool.containsPool(Vec2.class)) return;
		Vec2Pool pool = new Vec2Pool(new Vec2PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(Vec2.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(Vec2.class)) return;
//		Pool.removePool(Vec2.class);
//	}

	protected Vec2Pool(PoolFactory<Vec2> factory) { super(factory); }
	protected Vec2Pool(PoolFactory<Vec2> factory, PoolConfig<Vec2> config) { super(factory, config); }

	protected static class Vec2PoolFactory extends PoolFactory<Vec2> {
		@Override public Vec2 create() { return new Vec2(); }
		@Override public PooledObject<Vec2> wrap(Vec2 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<Vec2> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<Vec2> p) { p.getObject().reset(); }
	}
}
