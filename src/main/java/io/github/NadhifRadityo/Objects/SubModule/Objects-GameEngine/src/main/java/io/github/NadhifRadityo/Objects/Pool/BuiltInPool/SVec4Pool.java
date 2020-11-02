package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVec4;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class SVec4Pool extends GenTypePool<SVec4> {

	public static void addPool() {
		if(Pool.containsPool(SVec4.class)) return;
		SVec4Pool pool = new SVec4Pool(new SVec4PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(SVec4.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(SVec4.class)) return;
//		Pool.removePool(SVec4.class);
//	}

	protected SVec4Pool(PoolFactory<SVec4> factory) { super(factory); }
	protected SVec4Pool(PoolFactory<SVec4> factory, PoolConfig<SVec4> config) { super(factory, config); }

	protected static class SVec4PoolFactory extends PoolFactory<SVec4> {
		@Override public SVec4 create() { return new SVec4(); }
		@Override public PooledObject<SVec4> wrap(SVec4 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<SVec4> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<SVec4> p) { p.getObject().reset(); }
	}
}
