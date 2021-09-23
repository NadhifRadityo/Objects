package io.github.NadhifRadityo.Objects.$Object.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.$Object.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.$Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.UnsafeUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

@SuppressWarnings("jol")
public abstract class BufferSkeletonPool<B extends Buffer> extends Pool<B> {
	protected BufferSkeletonPool(PoolFactory<B> factory, PoolConfig<B> config) { super(factory, config); }
	protected BufferSkeletonPool(PoolFactory<B> factory) { super(factory); }

	public static <BUFFER extends Buffer> BUFFER newInstance(Class<BUFFER> bufferClass) {
		return BufferUtils.__pointTo(bufferClass, 0L, 0);
	}
	public static boolean validate(Buffer buffer) {
		return BufferUtils.__getAddress(buffer) == UnsafeUtils.NULLPTR &&
				BufferUtils.__getMark(buffer) == -1 &&
				BufferUtils.__getPosition(buffer) == 0 &&
				BufferUtils.__getLimit(buffer) == 0 &&
				BufferUtils.__getCapacity(buffer) == 0;
	}
	public static Buffer assign(Buffer buffer, long address, int capacity) {
		BufferUtils.__pointTo(buffer, address, capacity);
		return buffer;
	}

	public static class ByteBufferSkeletonPool extends BufferSkeletonPool<ByteBuffer> {

		public static void addPool() {
			if(Pool.containsPool(ByteBuffer.class)) return;
			ByteBufferSkeletonPool pool = new ByteBufferSkeletonPool(new ByteBufferSkeletonFactory(), Pool.getDefaultPoolConfig());
			Pool.addPool(ByteBuffer.class, pool); Pool.addPool(BufferUtils.getDirectByteBufferClass(), pool);
		}
//		public static void removePool() {
//			if(!Pool.containsPool(ByteBuffer.class)) return;
//			Pool.removePool(ByteBuffer.class);
//		}

		protected ByteBufferSkeletonPool(PoolFactory<ByteBuffer> factory, PoolConfig<ByteBuffer> config) { super(factory, config); }
		protected ByteBufferSkeletonPool(PoolFactory<ByteBuffer> factory) { super(factory); }

		public static ByteBuffer newInstance() { return newInstance(BufferUtils.getDirectByteBufferClass()); }
		public static boolean validate(ByteBuffer buffer) { return validate((Buffer) buffer); }
		public static ByteBuffer assign(ByteBuffer buffer, long address, int capacity) { assign((Buffer) buffer, address, capacity); return buffer; }

		protected static class ByteBufferSkeletonFactory extends PoolFactory<ByteBuffer> {
			@Override public ByteBuffer create() { return newInstance(); }
			@Override public PooledObject<ByteBuffer> wrap(ByteBuffer obj) { return new PooledObject<>(obj); }
			@Override public boolean validateObject(PooledObject<ByteBuffer> p) { return validate(p.getObject()); }
			@Override public void passivateObject(PooledObject<ByteBuffer> p) { assign(p.getObject(), UnsafeUtils.NULLPTR, 0); }
		}
	}

	public static class ShortBufferSkeletonPool extends BufferSkeletonPool<ShortBuffer> {

		public static void addPool() {
			if(Pool.containsPool(ShortBuffer.class)) return;
			ShortBufferSkeletonPool pool = new ShortBufferSkeletonPool(new ShortBufferSkeletonFactory(), Pool.getDefaultPoolConfig());
			Pool.addPool(ShortBuffer.class, pool); Pool.addPool(BufferUtils.getDirectShortBufferClass(), pool);
		}
//		public static void removePool() {
//			if(!Pool.containsPool(ShortBuffer.class)) return;
//			Pool.removePool(ShortBuffer.class);
//		}

		protected ShortBufferSkeletonPool(PoolFactory<ShortBuffer> factory, PoolConfig<ShortBuffer> config) { super(factory, config); }
		protected ShortBufferSkeletonPool(PoolFactory<ShortBuffer> factory) { super(factory); }

		public static ShortBuffer newInstance() { return newInstance(BufferUtils.getDirectShortBufferClass()); }
		public static boolean validate(ShortBuffer buffer) { return validate((Buffer) buffer); }
		public static ShortBuffer assign(ShortBuffer buffer, long address, int capacity) { assign((Buffer) buffer, address, capacity); return buffer; }

		protected static class ShortBufferSkeletonFactory extends PoolFactory<ShortBuffer> {
			@Override public ShortBuffer create() { return newInstance(); }
			@Override public PooledObject<ShortBuffer> wrap(ShortBuffer obj) { return new PooledObject<>(obj); }
			@Override public boolean validateObject(PooledObject<ShortBuffer> p) { return validate(p.getObject()); }
			@Override public void passivateObject(PooledObject<ShortBuffer> p) { assign(p.getObject(), UnsafeUtils.NULLPTR, 0); }
		}
	}

	public static class CharBufferSkeletonPool extends BufferSkeletonPool<CharBuffer> {

		public static void addPool() {
			if(Pool.containsPool(CharBuffer.class)) return;
			CharBufferSkeletonPool pool = new CharBufferSkeletonPool(new CharBufferSkeletonFactory(), Pool.getDefaultPoolConfig());
			Pool.addPool(CharBuffer.class, pool); Pool.addPool(BufferUtils.getDirectCharBufferClass(), pool);
		}
//		public static void removePool() {
//			if(!Pool.containsPool(CharBuffer.class)) return;
//			Pool.removePool(CharBuffer.class);
//		}

		protected CharBufferSkeletonPool(PoolFactory<CharBuffer> factory, PoolConfig<CharBuffer> config) { super(factory, config); }
		protected CharBufferSkeletonPool(PoolFactory<CharBuffer> factory) { super(factory); }

		public static CharBuffer newInstance() { return newInstance(BufferUtils.getDirectCharBufferClass()); }
		public static boolean validate(CharBuffer buffer) { return validate((Buffer) buffer); }
		public static CharBuffer assign(CharBuffer buffer, long address, int capacity) { assign((Buffer) buffer, address, capacity); return buffer; }

		protected static class CharBufferSkeletonFactory extends PoolFactory<CharBuffer> {
			@Override public CharBuffer create() { return newInstance(); }
			@Override public PooledObject<CharBuffer> wrap(CharBuffer obj) { return new PooledObject<>(obj); }
			@Override public boolean validateObject(PooledObject<CharBuffer> p) { return validate(p.getObject()); }
			@Override public void passivateObject(PooledObject<CharBuffer> p) { assign(p.getObject(), UnsafeUtils.NULLPTR, 0); }
		}
	}

	public static class IntBufferSkeletonPool extends BufferSkeletonPool<IntBuffer> {

		public static void addPool() {
			if(Pool.containsPool(IntBuffer.class)) return;
			IntBufferSkeletonPool pool = new IntBufferSkeletonPool(new IntBufferSkeletonFactory(), Pool.getDefaultPoolConfig());
			Pool.addPool(IntBuffer.class, pool); Pool.addPool(BufferUtils.getDirectIntBufferClass(), pool);
		}
//		public static void removePool() {
//			if(!Pool.containsPool(IntBuffer.class)) return;
//			Pool.removePool(IntBuffer.class);
//		}

		protected IntBufferSkeletonPool(PoolFactory<IntBuffer> factory, PoolConfig<IntBuffer> config) { super(factory, config); }
		protected IntBufferSkeletonPool(PoolFactory<IntBuffer> factory) { super(factory); }

		public static IntBuffer newInstance() { return newInstance(BufferUtils.getDirectIntBufferClass()); }
		public static boolean validate(IntBuffer buffer) { return validate((Buffer) buffer); }
		public static IntBuffer assign(IntBuffer buffer, long address, int capacity) { assign((Buffer) buffer, address, capacity); return buffer; }

		protected static class IntBufferSkeletonFactory extends PoolFactory<IntBuffer> {
			@Override public IntBuffer create() { return newInstance(); }
			@Override public PooledObject<IntBuffer> wrap(IntBuffer obj) { return new PooledObject<>(obj); }
			@Override public boolean validateObject(PooledObject<IntBuffer> p) { return validate(p.getObject()); }
			@Override public void passivateObject(PooledObject<IntBuffer> p) { assign(p.getObject(), UnsafeUtils.NULLPTR, 0); }
		}
	}

	public static class LongBufferSkeletonPool extends BufferSkeletonPool<LongBuffer> {

		public static void addPool() {
			if(Pool.containsPool(LongBuffer.class)) return;
			LongBufferSkeletonPool pool = new LongBufferSkeletonPool(new LongBufferSkeletonFactory(), Pool.getDefaultPoolConfig());
			Pool.addPool(LongBuffer.class, pool); Pool.addPool(BufferUtils.getDirectLongBufferClass(), pool);
		}
//		public static void removePool() {
//			if(!Pool.containsPool(LongBuffer.class)) return;
//			Pool.removePool(LongBuffer.class);
//		}

		protected LongBufferSkeletonPool(PoolFactory<LongBuffer> factory, PoolConfig<LongBuffer> config) { super(factory, config); }
		protected LongBufferSkeletonPool(PoolFactory<LongBuffer> factory) { super(factory); }

		public static LongBuffer newInstance() { return newInstance(BufferUtils.getDirectLongBufferClass()); }
		public static boolean validate(LongBuffer buffer) { return validate((Buffer) buffer); }
		public static LongBuffer assign(LongBuffer buffer, long address, int capacity) { assign((Buffer) buffer, address, capacity); return buffer; }

		protected static class LongBufferSkeletonFactory extends PoolFactory<LongBuffer> {
			@Override public LongBuffer create() { return newInstance(); }
			@Override public PooledObject<LongBuffer> wrap(LongBuffer obj) { return new PooledObject<>(obj); }
			@Override public boolean validateObject(PooledObject<LongBuffer> p) { return validate(p.getObject()); }
			@Override public void passivateObject(PooledObject<LongBuffer> p) { assign(p.getObject(), UnsafeUtils.NULLPTR, 0); }
		}
	}

	public static class FloatBufferSkeletonPool extends BufferSkeletonPool<FloatBuffer> {

		public static void addPool() {
			if(Pool.containsPool(FloatBuffer.class)) return;
			FloatBufferSkeletonPool pool = new FloatBufferSkeletonPool(new FloatBufferSkeletonFactory(), Pool.getDefaultPoolConfig());
			Pool.addPool(FloatBuffer.class, pool); Pool.addPool(BufferUtils.getDirectFloatBufferClass(), pool);
		}
//		public static void removePool() {
//			if(!Pool.containsPool(FloatBuffer.class)) return;
//			Pool.removePool(FloatBuffer.class);
//		}

		protected FloatBufferSkeletonPool(PoolFactory<FloatBuffer> factory, PoolConfig<FloatBuffer> config) { super(factory, config); }
		protected FloatBufferSkeletonPool(PoolFactory<FloatBuffer> factory) { super(factory); }

		public static FloatBuffer newInstance() { return newInstance(BufferUtils.getDirectFloatBufferClass()); }
		public static boolean validate(FloatBuffer buffer) { return validate((Buffer) buffer); }
		public static FloatBuffer assign(FloatBuffer buffer, long address, int capacity) { assign((Buffer) buffer, address, capacity); return buffer; }

		protected static class FloatBufferSkeletonFactory extends PoolFactory<FloatBuffer> {
			@Override public FloatBuffer create() { return newInstance(); }
			@Override public PooledObject<FloatBuffer> wrap(FloatBuffer obj) { return new PooledObject<>(obj); }
			@Override public boolean validateObject(PooledObject<FloatBuffer> p) { return validate(p.getObject()); }
			@Override public void passivateObject(PooledObject<FloatBuffer> p) { assign(p.getObject(), UnsafeUtils.NULLPTR, 0); }
		}
	}

	public static class DoubleBufferSkeletonPool extends BufferSkeletonPool<DoubleBuffer> {

		public static void addPool() {
			if(Pool.containsPool(DoubleBuffer.class)) return;
			DoubleBufferSkeletonPool pool = new DoubleBufferSkeletonPool(new DoubleBufferSkeletonFactory(), Pool.getDefaultPoolConfig());
			Pool.addPool(DoubleBuffer.class, pool); Pool.addPool(BufferUtils.getDirectDoubleBufferClass(), pool);
		}
//		public static void removePool() {
//			if(!Pool.containsPool(DoubleBuffer.class)) return;
//			Pool.removePool(DoubleBuffer.class);
//		}

		protected DoubleBufferSkeletonPool(PoolFactory<DoubleBuffer> factory, PoolConfig<DoubleBuffer> config) { super(factory, config); }
		protected DoubleBufferSkeletonPool(PoolFactory<DoubleBuffer> factory) { super(factory); }

		public static DoubleBuffer newInstance() { return newInstance(BufferUtils.getDirectDoubleBufferClass()); }
		public static boolean validate(DoubleBuffer buffer) { return validate((Buffer) buffer); }
		public static DoubleBuffer assign(DoubleBuffer buffer, long address, int capacity) { assign((Buffer) buffer, address, capacity); return buffer; }

		protected static class DoubleBufferSkeletonFactory extends PoolFactory<DoubleBuffer> {
			@Override public DoubleBuffer create() { return newInstance(); }
			@Override public PooledObject<DoubleBuffer> wrap(DoubleBuffer obj) { return new PooledObject<>(obj); }
			@Override public boolean validateObject(PooledObject<DoubleBuffer> p) { return validate(p.getObject()); }
			@Override public void passivateObject(PooledObject<DoubleBuffer> p) { assign(p.getObject(), UnsafeUtils.NULLPTR, 0); }
		}
	}
}
