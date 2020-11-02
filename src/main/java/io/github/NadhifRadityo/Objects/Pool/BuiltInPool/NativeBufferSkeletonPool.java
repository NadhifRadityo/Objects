package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.NativeBuffer;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.PointerBuffer;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.UnsafeUtils;

import java.nio.Buffer;

@SuppressWarnings("jol")
public abstract class NativeBufferSkeletonPool<B extends NativeBuffer<B>> extends Pool<B> {
	protected NativeBufferSkeletonPool(PoolFactory<B> factory, PoolConfig<B> config) { super(factory, config); }
	protected NativeBufferSkeletonPool(PoolFactory<B> factory) { super(factory); }

	protected static boolean validate(Buffer buffer) { return BufferUtils.__getAddress(buffer) == UnsafeUtils.NULLPTR && BufferUtils.__getMark(buffer) == -1 && BufferUtils.__getPosition(buffer) == 0 && BufferUtils.__getLimit(buffer) == 0 && BufferUtils.__getCapacity(buffer) == 0; }
	protected static void passivate(Buffer buffer) { BufferUtils.__pointTo(buffer, 0, 0); }

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

		protected static class PointerBufferSkeletonFactory extends PoolFactory<PointerBuffer> {
			@Override public PointerBuffer create() { return PointerBuffer.wrap(BufferUtils.__pointTo(BufferUtils.getDirectByteBufferClass(), 0, 0)); }
			@Override public PooledObject<PointerBuffer> wrap(PointerBuffer obj) { return new PooledObject<>(obj); }
			@Override public boolean validateObject(PooledObject<PointerBuffer> p) { return validate(p.getObject().getBuffer()); }
			@Override public void passivateObject(PooledObject<PointerBuffer> p) { passivate(p.getObject().getBuffer()); }
		}
	}
}
