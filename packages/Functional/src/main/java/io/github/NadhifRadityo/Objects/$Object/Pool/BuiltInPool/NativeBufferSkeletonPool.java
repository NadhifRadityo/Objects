package io.github.NadhifRadityo.Objects.$Object.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.$Object.Buffer.CustomBuffer.NativeBuffer;
import io.github.NadhifRadityo.Objects.$Object.Buffer.CustomBuffer.PointerBuffer;
import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.$Object.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.$Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.UnsafeUtils;

@SuppressWarnings("jol")
public abstract class NativeBufferSkeletonPool<B extends NativeBuffer<B>> extends Pool<B> {
	protected NativeBufferSkeletonPool(PoolFactory<B> factory, PoolConfig<B> config) { super(factory, config); }
	protected NativeBufferSkeletonPool(PoolFactory<B> factory) { super(factory); }

	public static class PointerBufferSkeletonPool extends NativeBufferSkeletonPool<PointerBuffer> {

		public static void addPool() {
			if(Pool.containsPool(PointerBuffer.class)) return;
			PointerBufferSkeletonPool pool = new PointerBufferSkeletonPool(new PointerBufferSkeletonFactory(), Pool.getDefaultPoolConfig());
			Pool.addPool(PointerBuffer.class, pool);
		}
//		public static void removePool() {
//			if(!Pool.containsPool(PointerBuffer.class)) return;
//			Pool.removePool(PointerBuffer.class);
//		}

		protected PointerBufferSkeletonPool(PoolFactory<PointerBuffer> factory, PoolConfig<PointerBuffer> config) { super(factory, config); }
		protected PointerBufferSkeletonPool(PoolFactory<PointerBuffer> factory) { super(factory); }

		public static PointerBuffer newInstance() { return PointerBuffer.wrap(BufferUtils.__pointTo(BufferUtils.getDirectByteBufferClass(), UnsafeUtils.NULLPTR, 0)); }
		public static PointerBuffer assign(PointerBuffer buffer, long address, int size) { BufferSkeletonPool.assign(buffer.getBuffer(), address, size); return buffer; }
		public static boolean validate(PointerBuffer buffer) { return BufferSkeletonPool.validate(buffer.getBuffer()); }

		protected static class PointerBufferSkeletonFactory extends PoolFactory<PointerBuffer> {
			@Override public PointerBuffer create() { return newInstance(); }
			@Override public PooledObject<PointerBuffer> wrap(PointerBuffer obj) { return new PooledObject<>(obj); }
			@Override public boolean validateObject(PooledObject<PointerBuffer> p) { return validate(p.getObject()); }
			@Override public void passivateObject(PooledObject<PointerBuffer> p) { assign(p.getObject(), UnsafeUtils.NULLPTR, 0); }
		}
	}
}
