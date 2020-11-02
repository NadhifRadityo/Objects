package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.TempVec;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferAlgorithm.BestFitAllocationAlgorithm;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferManager;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;

@SuppressWarnings("jol")
public class TempVecPool extends GenTypePool<TempVec> {

	public static void addPool() {
		if(Pool.containsPool(TempVec.class)) return;
		TempVecPool pool = new TempVecPool(new TempVecPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(TempVec.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(TempVec.class)) return;
//		Pool.removePool(TempVec.class);
//	}

	protected TempVecPool(PoolFactory<TempVec> factory) { super(factory); }
	protected TempVecPool(PoolFactory<TempVec> factory, PoolConfig<TempVec> config) { super(factory, config); }

	protected static class TempVecPoolFactory extends PoolFactory<TempVec> {
		private static final int SIZE = 16;
		@Override public TempVec create() { return new TempVec(SIZE, new BufferManager(new BestFitAllocationAlgorithm(), BufferUtils.createByteBuffer(SIZE * TempVec.ITEM_TOTAL_LENGTH))); }
		@Override public PooledObject<TempVec> wrap(TempVec obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<TempVec> p) { TempVec object = p.getObject(); for(int i = 0; i < object.getSize(); i++) if(object.getTypeAt(i) != TempVec.TYPE_NULL) return false; return true; }
		@Override public void passivateObject(PooledObject<TempVec> p) { p.getObject().reset(); }
	}
}
