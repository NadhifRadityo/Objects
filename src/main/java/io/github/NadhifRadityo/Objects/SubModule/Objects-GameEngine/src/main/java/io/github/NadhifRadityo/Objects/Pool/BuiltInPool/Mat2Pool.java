package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Mat2;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

@SuppressWarnings("jol")
public class Mat2Pool extends GenTypePool<Mat2> {

	public static void addPool() {
		if(Pool.containsPool(Mat2.class)) return;
		Mat2Pool pool = new Mat2Pool(new Mat2PoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(Mat2.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(Mat2.class)) return;
//		Pool.removePool(Mat2.class);
//	}

	protected Mat2Pool(PoolFactory<Mat2> factory) { super(factory); }
	protected Mat2Pool(PoolFactory<Mat2> factory, PoolConfig<Mat2> config) { super(factory, config); }

	protected static class Mat2PoolFactory extends PoolFactory<Mat2> {
		@Override public Mat2 create() { return new Mat2(); }
		@Override public PooledObject<Mat2> wrap(Mat2 obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<Mat2> p) { return NumberUtils.add(p.getObject().d) == 0; }
		@Override public void passivateObject(PooledObject<Mat2> p) { p.getObject().reset(); }
	}
}
