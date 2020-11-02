package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVec2;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class SVec2Pool extends GenTypePool<SVec2> {

	public static void addPool() {
		if(Pool.containsPool(SVec2.class)) return;
		SVec2Pool pool = new SVec2Pool(new SVec2PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(SVec2.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(SVec2.class)) return;
//		Pool.removePool(SVec2.class);
//	}

	protected SVec2Pool(PoolFactory<SVec2> factory) { super(factory); }
	protected SVec2Pool(PoolFactory<SVec2> factory, PoolConfig<SVec2> config) { super(factory, config); }

	protected static class SVec2PoolFactory extends PoolFactory<SVec2> {
		@Override public SVec2 create() { return new SVec2(); }
		@Override public PooledObject<SVec2> wrap(SVec2 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<SVec2> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<SVec2> p) { p.getObject().reset(); }
	}
}
