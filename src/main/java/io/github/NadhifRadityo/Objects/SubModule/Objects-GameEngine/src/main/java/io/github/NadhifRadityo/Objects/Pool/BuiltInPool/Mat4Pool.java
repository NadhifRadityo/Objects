package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Mat4;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class Mat4Pool extends GenTypePool<Mat4> {

	public static void addPool() {
		if(Pool.containsPool(Mat4.class)) return;
		Mat4Pool pool = new Mat4Pool(new Mat4PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(Mat4.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(Mat4.class)) return;
//		Pool.removePool(Mat4.class);
//	}

	protected Mat4Pool(PoolFactory<Mat4> factory) { super(factory); }
	protected Mat4Pool(PoolFactory<Mat4> factory, PoolConfig<Mat4> config) { super(factory, config); }

	protected static class Mat4PoolFactory extends PoolFactory<Mat4> {
		@Override public Mat4 create() { return new Mat4(); }
		@Override public PooledObject<Mat4> wrap(Mat4 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<Mat4> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<Mat4> p) { p.getObject().reset(); }
	}
}
