package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import com.jogamp.common.nio.AbstractBuffer;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.PointerBuffer;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.Utilizations.SystemUtils;
import io.github.NadhifRadityo.Objects.Utilizations.UnsafeUtils;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.Pointer;
import sun.misc.Unsafe;

import java.nio.Buffer;

@SuppressWarnings("jol")
public abstract class PointerBufferSkeletonPool<B> extends Pool<B> {
	protected PointerBufferSkeletonPool(PoolFactory<B> factory, PoolConfig<B> config) { super(factory, config); }
	protected PointerBufferSkeletonPool(PoolFactory<B> factory) { super(factory); }

	private static final Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);

	public static class LWJGLPointerBufferSkeletonPool extends PointerBufferSkeletonPool<org.lwjgl.PointerBuffer> {

		public static void addPool() {
			if(Pool.containsPool(org.lwjgl.PointerBuffer.class)) return;
			LWJGLPointerBufferSkeletonPool pool = new LWJGLPointerBufferSkeletonPool(new LWJGLPointerBufferSkeletonFactory(), Pool.getDefaultPoolConfig());
			Pool.addPool(org.lwjgl.PointerBuffer.class, pool);
		}
//		public static void removePool() {
//			if(!Pool.containsPool(org.lwjgl.PointerBuffer.class)) return;
//			Pool.removePool(org.lwjgl.PointerBuffer.class);
//		}

		protected LWJGLPointerBufferSkeletonPool(PoolFactory<org.lwjgl.PointerBuffer> factory, PoolConfig<org.lwjgl.PointerBuffer> config) { super(factory, config); }
		protected LWJGLPointerBufferSkeletonPool(PoolFactory<org.lwjgl.PointerBuffer> factory) { super(factory); }

		private static final long LWJGL_ADDRESS;
		private static final long LWJGL_BUFFER_MARK;
		private static final long LWJGL_BUFFER_POSITION;
		private static final long LWJGL_BUFFER_LIMIT;
		private static final long LWJGL_BUFFER_CAPACITY;
		private static final long LWJGL_BUFFER_CONTAINER;
		static { try {
			LWJGL_ADDRESS = unsafe.objectFieldOffset(Pointer.Default.class.getDeclaredField("address"));
			LWJGL_BUFFER_MARK = unsafe.objectFieldOffset(CustomBuffer.class.getDeclaredField("mark"));
			LWJGL_BUFFER_POSITION = unsafe.objectFieldOffset(CustomBuffer.class.getDeclaredField("position"));
			LWJGL_BUFFER_LIMIT = unsafe.objectFieldOffset(CustomBuffer.class.getDeclaredField("limit"));
			LWJGL_BUFFER_CAPACITY = unsafe.objectFieldOffset(CustomBuffer.class.getDeclaredField("capacity"));
			LWJGL_BUFFER_CONTAINER = unsafe.objectFieldOffset(CustomBuffer.class.getDeclaredField("container"));
		} catch(Throwable e) { throw new Error(e); } }

		public static org.lwjgl.PointerBuffer newInstance() {
			try { return (org.lwjgl.PointerBuffer) unsafe.allocateInstance(org.lwjgl.PointerBuffer.class);
			} catch(InstantiationException e) { throw new UnsupportedOperationException(e); }
		}
		public static org.lwjgl.PointerBuffer pointTo(org.lwjgl.PointerBuffer buffer, long address, int size, Buffer container) {
			if(container != null) BufferUtils.__pointTo(container, address, size * PointerBuffer.ELEMENT_SIZE);
			unsafe.putLong(buffer, LWJGL_ADDRESS, address);
			unsafe.putInt(buffer, LWJGL_BUFFER_MARK, -1);
			unsafe.putInt(buffer, LWJGL_BUFFER_POSITION, 0);
			unsafe.putInt(buffer, LWJGL_BUFFER_LIMIT, size);
			unsafe.putInt(buffer, LWJGL_BUFFER_CAPACITY, size);
			unsafe.putObject(buffer, LWJGL_BUFFER_CONTAINER, container);
			return buffer;
		}
		public static boolean validate(org.lwjgl.PointerBuffer buffer) {
			return unsafe.getLong(buffer, LWJGL_ADDRESS) == 0 &&
					unsafe.getInt(buffer, LWJGL_BUFFER_MARK) == -1 &&
					unsafe.getInt(buffer, LWJGL_BUFFER_POSITION) == 0 &&
					unsafe.getInt(buffer, LWJGL_BUFFER_LIMIT) == 0 &&
					unsafe.getInt(buffer, LWJGL_BUFFER_CAPACITY) == 0 &&
					unsafe.getObject(buffer, LWJGL_BUFFER_CONTAINER) == null;
		}

		protected static class LWJGLPointerBufferSkeletonFactory extends PoolFactory<org.lwjgl.PointerBuffer> {
			@Override public org.lwjgl.PointerBuffer create() { return pointTo(newInstance(), 0, 0, null); }
			@Override public PooledObject<org.lwjgl.PointerBuffer> wrap(org.lwjgl.PointerBuffer obj) { return new PooledObject<>(obj); }
			@Override public boolean validateObject(PooledObject<org.lwjgl.PointerBuffer> p) { return validate(p.getObject()); }
			@Override public void passivateObject(PooledObject<org.lwjgl.PointerBuffer> p) { pointTo(p.getObject(), 0, 0, null); }
		}
	}

	public static class JogampPointerBufferSkeletonPool extends PointerBufferSkeletonPool<com.jogamp.common.nio.PointerBuffer> {

		public static void addPool() {
			if(Pool.containsPool(com.jogamp.common.nio.PointerBuffer.class)) return;
			JogampPointerBufferSkeletonPool pool = new JogampPointerBufferSkeletonPool(new JogampPointerBufferSkeletonFactory(), Pool.getDefaultPoolConfig());
			Pool.addPool(com.jogamp.common.nio.PointerBuffer.class, pool);
		}
//		public static void removePool() {
//			if(!Pool.containsPool(com.jogamp.common.nio.PointerBuffer.class)) return;
//			Pool.removePool(com.jogamp.common.nio.PointerBuffer.class);
//		}

		protected JogampPointerBufferSkeletonPool(PoolFactory<com.jogamp.common.nio.PointerBuffer> factory, PoolConfig<com.jogamp.common.nio.PointerBuffer> config) { super(factory, config); }
		protected JogampPointerBufferSkeletonPool(PoolFactory<com.jogamp.common.nio.PointerBuffer> factory) { super(factory); }

		private static final long JOGAMP_ABSTRACTBUFFER_ELEMENTSIZE;
		private static final long JOGAMP_ABSTRACTBUFFER_CAPACITY;
		private static final long JOGAMP_ABSTRACTBUFFER_POSITION;
		private static final long JOGAMP_ABSTRACTBUFFER_BUFFER;
		static { try {
			JOGAMP_ABSTRACTBUFFER_ELEMENTSIZE = unsafe.objectFieldOffset(AbstractBuffer.class.getDeclaredField("elementSize"));
			JOGAMP_ABSTRACTBUFFER_CAPACITY = unsafe.objectFieldOffset(AbstractBuffer.class.getDeclaredField("capacity"));
			JOGAMP_ABSTRACTBUFFER_POSITION = unsafe.objectFieldOffset(AbstractBuffer.class.getDeclaredField("position"));
			JOGAMP_ABSTRACTBUFFER_BUFFER = unsafe.objectFieldOffset(AbstractBuffer.class.getDeclaredField("buffer"));
		} catch(Throwable e) { throw new Error(e); } }

		public static com.jogamp.common.nio.PointerBuffer newInstance() {
			try { return (com.jogamp.common.nio.PointerBuffer) unsafe.allocateInstance(com.jogamp.common.nio.PointerBuffer.class);
			} catch(InstantiationException e) { throw new UnsupportedOperationException(e); }
		}
		public static com.jogamp.common.nio.PointerBuffer pointTo(com.jogamp.common.nio.PointerBuffer buffer, long address, int size, Buffer container) {
			if(container != null) BufferUtils.__pointTo(container, address, size * PointerBuffer.ELEMENT_SIZE);
			unsafe.putInt(buffer, JOGAMP_ABSTRACTBUFFER_ELEMENTSIZE, PointerBuffer.ELEMENT_SIZE);
			unsafe.putInt(buffer, JOGAMP_ABSTRACTBUFFER_CAPACITY, size);
			unsafe.putInt(buffer, JOGAMP_ABSTRACTBUFFER_POSITION, 0);
			unsafe.putObject(buffer, JOGAMP_ABSTRACTBUFFER_BUFFER, container);
			return buffer;
		}
		public static boolean validate(com.jogamp.common.nio.PointerBuffer buffer) {
			return unsafe.getInt(buffer, JOGAMP_ABSTRACTBUFFER_ELEMENTSIZE) == PointerBuffer.ELEMENT_SIZE &&
					unsafe.getInt(buffer, JOGAMP_ABSTRACTBUFFER_CAPACITY) == 0 &&
					unsafe.getInt(buffer, JOGAMP_ABSTRACTBUFFER_POSITION) == 0 &&
					BufferSkeletonPool.validate((Buffer) unsafe.getObject(buffer, JOGAMP_ABSTRACTBUFFER_BUFFER));
		}

		protected static class JogampPointerBufferSkeletonFactory extends PoolFactory<com.jogamp.common.nio.PointerBuffer> {
			@Override public com.jogamp.common.nio.PointerBuffer create() { return pointTo(newInstance(), 0, 0, SystemUtils.IS_OS_32BIT ? BufferUtils.__pointTo(BufferUtils.getDirectIntBufferClass(), 0, 0) : BufferUtils.__pointTo(BufferUtils.getDirectLongBufferClass(), 0, 0)); }
			@Override public PooledObject<com.jogamp.common.nio.PointerBuffer> wrap(com.jogamp.common.nio.PointerBuffer obj) { return new PooledObject<>(obj); }
			@Override public boolean validateObject(PooledObject<com.jogamp.common.nio.PointerBuffer> p) { return validate(p.getObject()); }
			@Override public void passivateObject(PooledObject<com.jogamp.common.nio.PointerBuffer> p) { pointTo(p.getObject(), 0, 0, p.getObject().getBuffer()); }
		}
	}
}
