package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FMat4;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class FMat4Pool extends GenTypePool<FMat4> {

	public static void addPool() {
		if(Pool.containsPool(FMat4.class)) return;
		FMat4Pool pool = new FMat4Pool(new FMat4PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(FMat4.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(FMat4.class)) return;
//		Pool.removePool(FMat4.class);
//	}

	protected FMat4Pool(PoolFactory<FMat4> factory) { super(factory); }
	protected FMat4Pool(PoolFactory<FMat4> factory, PoolConfig<FMat4> config) { super(factory, config); }

	protected static class FMat4PoolFactory extends PoolFactory<FMat4> {
		@Override public FMat4 create() { return new FMat4(); }
		@Override public PooledObject<FMat4> wrap(FMat4 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<FMat4> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<FMat4> p) { p.getObject().reset(); }
	}
}
