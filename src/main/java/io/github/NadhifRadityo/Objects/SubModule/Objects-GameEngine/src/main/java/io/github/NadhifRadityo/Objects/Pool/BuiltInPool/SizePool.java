package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class SizePool extends GenTypePool<Size> {

	public static void addPool() {
		if(Pool.containsPool(Size.class)) return;
		SizePool pool = new SizePool(new SizePoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(Size.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(Size.class)) return;
//		Pool.removePool(Size.class);
//	}

	protected SizePool(PoolFactory<Size> factory) { super(factory); }
	protected SizePool(PoolFactory<Size> factory, PoolConfig<Size> config) { super(factory, config); }

	protected static class SizePoolFactory extends PoolFactory<Size> {
		@Override public Size create() { return new Size(); }
		@Override public PooledObject<Size> wrap(Size obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<Size> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<Size> p) { p.getObject().reset(); }
	}
}
