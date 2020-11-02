package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Mat3;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class Mat3Pool extends GenTypePool<Mat3> {

	public static void addPool() {
		if(Pool.containsPool(Mat3.class)) return;
		Mat3Pool pool = new Mat3Pool(new Mat3PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(Mat3.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(Mat3.class)) return;
//		Pool.removePool(Mat3.class);
//	}

	protected Mat3Pool(PoolFactory<Mat3> factory) { super(factory); }
	protected Mat3Pool(PoolFactory<Mat3> factory, PoolConfig<Mat3> config) { super(factory, config); }

	protected static class Mat3PoolFactory extends PoolFactory<Mat3> {
		@Override public Mat3 create() { return new Mat3(); }
		@Override public PooledObject<Mat3> wrap(Mat3 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<Mat3> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<Mat3> p) { p.getObject().reset(); }
	}
}
