package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferAlgorithm.BestFitAllocationAlgorithm;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferManager;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.AbstractBuffer;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.NativeBuffer;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.PointerBuffer;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Proxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import sun.misc.Unsafe;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BufferUtils {
	protected static final int TEMP_BUFFERMANAGER_SIZE = Integer.parseInt(System.getProperty("bufferutils.temp.buffermanager.size", "8192"));
	protected static final int TEMP_BYTEBUFFER_SIZE = Integer.parseInt(System.getProperty("bufferutils.temp.bytebuffer.size", "8192"));
	protected static final int TEMP_SHORTBUFFER_SIZE = Integer.parseInt(System.getProperty("bufferutils.temp.shortbuffer.size", "4096"));
	protected static final int TEMP_CHARBUFFER_SIZE = Integer.parseInt(System.getProperty("bufferutils.temp.charbuffer.size", "4096"));
	protected static final int TEMP_INTBUFFER_SIZE = Integer.parseInt(System.getProperty("bufferutils.temp.intbuffer.size", "2048"));
	protected static final int TEMP_LONGBUFFER_SIZE = Integer.parseInt(System.getProperty("bufferutils.temp.longbuffer.size", "1024"));
	protected static final int TEMP_FLOATBUFFER_SIZE = Integer.parseInt(System.getProperty("bufferutils.temp.floatbuffer.size", "2048"));
	protected static final int TEMP_DOUBLEBUFFER_SIZE = Integer.parseInt(System.getProperty("bufferutils.temp.doublebuffer.size", "1024"));
	protected static final Class<ByteBuffer> CLASS_DirectByteBuffer;
	protected static final Class<ShortBuffer> CLASS_DirectShortBuffer;
	protected static final Class<CharBuffer> CLASS_DirectCharBuffer;
	protected static final Class<IntBuffer> CLASS_DirectIntBuffer;
	protected static final Class<LongBuffer> CLASS_DirectLongBuffer;
	protected static final Class<FloatBuffer> CLASS_DirectFloatBuffer;
	protected static final Class<DoubleBuffer> CLASS_DirectDoubleBuffer;
	protected static final ThreadLocal<BufferManager> tempBufferManager = new ThreadLocal<>();
	protected static final ThreadLocal<ByteBuffer> tempByteBuffer = new ThreadLocal<>();
	protected static final ThreadLocal<ShortBuffer> tempShortBuffer = new ThreadLocal<>();
	protected static final ThreadLocal<CharBuffer> tempCharBuffer = new ThreadLocal<>();
	protected static final ThreadLocal<IntBuffer> tempIntBuffer = new ThreadLocal<>();
	protected static final ThreadLocal<LongBuffer> tempLongBuffer = new ThreadLocal<>();
	protected static final ThreadLocal<FloatBuffer> tempFloatBuffer = new ThreadLocal<>();
	protected static final ThreadLocal<DoubleBuffer> tempDoubleBuffer = new ThreadLocal<>();
	private static WeakReference<ByteBuffer> emptyByteBuffer;
	protected static final long AFIELD_Buffer_mark;
	protected static final long AFIELD_Buffer_position;
	protected static final long AFIELD_Buffer_limit;
	protected static final long AFIELD_Buffer_capacity;
	protected static final long AFIELD_Buffer_address;
	protected static final long AFIELD_AbstractBuffer_buffer;
	protected static final long AFIELD_AbstractBuffer_elementSize;
	protected static final long AFIELD_AbstractBuffer_capacity;
	protected static final long AFIELD_AbstractBuffer_position;

	static { try {
		ByteBuffer tempBuffer = createByteBuffer(0);
		CLASS_DirectByteBuffer = (Class<ByteBuffer>) tempBuffer.getClass();
		CLASS_DirectShortBuffer = (Class<ShortBuffer>) tempBuffer.asShortBuffer().getClass();
		CLASS_DirectCharBuffer = (Class<CharBuffer>) tempBuffer.asCharBuffer().getClass();
		CLASS_DirectIntBuffer = (Class<IntBuffer>) tempBuffer.asIntBuffer().getClass();
		CLASS_DirectLongBuffer = (Class<LongBuffer>) tempBuffer.asLongBuffer().getClass();
		CLASS_DirectFloatBuffer = (Class<FloatBuffer>) tempBuffer.asFloatBuffer().getClass();
		CLASS_DirectDoubleBuffer = (Class<DoubleBuffer>) tempBuffer.asDoubleBuffer().getClass();

		if(SystemUtils.JAVA_DETECTION_VERSION > 8) {
			FBufferUtilsImplementation instance = (FBufferUtilsImplementation) FutureJavaUtils.fastCall("getInstance");
			AFIELD_Buffer_mark = instance.getBufferMarkOffset();
			AFIELD_Buffer_position = instance.getBufferPositionOffset();
			AFIELD_Buffer_limit = instance.getBufferLimitOffset();
			AFIELD_Buffer_capacity = instance.getBufferCapacityOffset();
			AFIELD_Buffer_address = instance.getBufferAddressOffset();
			AFIELD_AbstractBuffer_buffer = instance.getAbstractBufferBufferOffset();
			AFIELD_AbstractBuffer_elementSize = instance.getAbstractBufferElementSizeOffset();
			AFIELD_AbstractBuffer_capacity = instance.getAbstractBufferCapacityOffset();
			AFIELD_AbstractBuffer_position = instance.getAbstractBufferPositionOffset();
		} else {
			Unsafe unsafe = UnsafeUtils.R_getUnsafe();
			Field FIELD_Buffer_mark = Buffer.class.getDeclaredField("mark");
			Field FIELD_Buffer_position = Buffer.class.getDeclaredField("position");
			Field FIELD_Buffer_limit = Buffer.class.getDeclaredField("limit");
			Field FIELD_Buffer_capacity = Buffer.class.getDeclaredField("capacity");
			Field FIELD_Buffer_address = Buffer.class.getDeclaredField("address");
			Field FIELD_AbstractBuffer_buffer = AbstractBuffer.class.getDeclaredField("buffer");
			Field FIELD_AbstractBuffer_elementSize = AbstractBuffer.class.getDeclaredField("elementSize");
			Field FIELD_AbstractBuffer_capacity = AbstractBuffer.class.getDeclaredField("capacity");
			Field FIELD_AbstractBuffer_position = AbstractBuffer.class.getDeclaredField("position");
			AFIELD_Buffer_mark = unsafe.objectFieldOffset(FIELD_Buffer_mark);
			AFIELD_Buffer_position = unsafe.objectFieldOffset(FIELD_Buffer_position);
			AFIELD_Buffer_limit = unsafe.objectFieldOffset(FIELD_Buffer_limit);
			AFIELD_Buffer_capacity = unsafe.objectFieldOffset(FIELD_Buffer_capacity);
			AFIELD_Buffer_address = unsafe.objectFieldOffset(FIELD_Buffer_address);
			AFIELD_AbstractBuffer_buffer = unsafe.objectFieldOffset(FIELD_AbstractBuffer_buffer);
			AFIELD_AbstractBuffer_elementSize = unsafe.objectFieldOffset(FIELD_AbstractBuffer_elementSize);
			AFIELD_AbstractBuffer_capacity = unsafe.objectFieldOffset(FIELD_AbstractBuffer_capacity);
			AFIELD_AbstractBuffer_position = unsafe.objectFieldOffset(FIELD_AbstractBuffer_position);
		} deallocate(tempBuffer);
	} catch(Exception e) { throw new RuntimeException(e); } }

	public static Class<ByteBuffer> getDirectByteBufferClass() { return CLASS_DirectByteBuffer; }
	public static Class<ShortBuffer> getDirectShortBufferClass() { return CLASS_DirectShortBuffer; }
	public static Class<CharBuffer> getDirectCharBufferClass() { return CLASS_DirectCharBuffer; }
	public static Class<IntBuffer> getDirectIntBufferClass() { return CLASS_DirectIntBuffer; }
	public static Class<LongBuffer> getDirectLongBufferClass() { return CLASS_DirectLongBuffer; }
	public static Class<FloatBuffer> getDirectFloatBufferClass() { return CLASS_DirectFloatBuffer; }
	public static Class<DoubleBuffer> getDirectDoubleBufferClass() { return CLASS_DirectDoubleBuffer; }

	private static void assertBufferSufficient(Buffer buffer, int size) { if(size >= buffer.capacity()) throw new OutOfMemoryError("Size exceeded the size of temp buffer."); }
	private static BufferManager getTempBufferManager(Thread thread, int size) { BufferManager result = ThreadUtils.getThreadLocalValue(tempBufferManager, thread); if(result == null) { result = new BufferManager(new BestFitAllocationAlgorithm(), TEMP_BUFFERMANAGER_SIZE); ThreadUtils.setThreadLocalValue(tempBufferManager, thread, result); } assertBufferSufficient(result.getBuffer(), size); return result; }
	public static ByteBuffer getTempByteBuffer(Thread thread, int size) { ByteBuffer result = ThreadUtils.getThreadLocalValue(tempByteBuffer, thread); if(result == null) { result = createByteBuffer(TEMP_BYTEBUFFER_SIZE); ThreadUtils.setThreadLocalValue(tempByteBuffer, thread, result); } assertBufferSufficient(result, size); return result; }
	public static ShortBuffer getTempShortBuffer(Thread thread, int size) { ShortBuffer result = ThreadUtils.getThreadLocalValue(tempShortBuffer, thread); if(result == null) { result = createShortBuffer(TEMP_SHORTBUFFER_SIZE); ThreadUtils.setThreadLocalValue(tempShortBuffer, thread, result); } assertBufferSufficient(result, size); return result; }
	public static CharBuffer getTempCharBuffer(Thread thread, int size) { CharBuffer result = ThreadUtils.getThreadLocalValue(tempCharBuffer, thread); if(result == null) { result = createCharBuffer(TEMP_CHARBUFFER_SIZE); ThreadUtils.setThreadLocalValue(tempCharBuffer, thread, result); } assertBufferSufficient(result, size); return result; }
	public static IntBuffer getTempIntBuffer(Thread thread, int size) { IntBuffer result = ThreadUtils.getThreadLocalValue(tempIntBuffer, thread); if(result == null) { result = createIntBuffer(TEMP_INTBUFFER_SIZE); ThreadUtils.setThreadLocalValue(tempIntBuffer, thread, result); } assertBufferSufficient(result, size); return result; }
	public static LongBuffer getTempLongBuffer(Thread thread, int size) { LongBuffer result = ThreadUtils.getThreadLocalValue(tempLongBuffer, thread); if(result == null) { result = createLongBuffer(TEMP_LONGBUFFER_SIZE); ThreadUtils.setThreadLocalValue(tempLongBuffer, thread, result); } assertBufferSufficient(result, size); return result; }
	public static FloatBuffer getTempFloatBuffer(Thread thread, int size) { FloatBuffer result = ThreadUtils.getThreadLocalValue(tempFloatBuffer, thread); if(result == null) { result = createFloatBuffer(TEMP_FLOATBUFFER_SIZE); ThreadUtils.setThreadLocalValue(tempFloatBuffer, thread, result); } assertBufferSufficient(result, size); return result; }
	public static DoubleBuffer getTempDoubleBuffer(Thread thread, int size) { DoubleBuffer result = ThreadUtils.getThreadLocalValue(tempDoubleBuffer, thread); if(result == null) { result = createDoubleBuffer(TEMP_DOUBLEBUFFER_SIZE); ThreadUtils.setThreadLocalValue(tempDoubleBuffer, thread, result); } assertBufferSufficient(result, size); return result; }
	public static ByteBuffer getEmptyByteBuffer() { ByteBuffer result = emptyByteBuffer == null ? null : emptyByteBuffer.get(); if(result == null) { result = createByteBuffer(1024).asReadOnlyBuffer(); emptyByteBuffer = new WeakReference<>(result); } return result; }
	private static BufferManager getTempBufferManager(Thread thread) { return getTempBufferManager(thread, -1); }
	public static ByteBuffer getTempByteBuffer(Thread thread) { return getTempByteBuffer(thread, -1); }
	public static ShortBuffer getTempShortBuffer(Thread thread) { return getTempShortBuffer(thread, -1); }
	public static CharBuffer getTempCharBuffer(Thread thread) { return getTempCharBuffer(thread, -1); }
	public static IntBuffer getTempIntBuffer(Thread thread) { return getTempIntBuffer(thread, -1); }
	public static LongBuffer getTempLongBuffer(Thread thread) { return getTempLongBuffer(thread, -1); }
	public static FloatBuffer getTempFloatBuffer(Thread thread) { return getTempFloatBuffer(thread, -1); }
	public static DoubleBuffer getTempDoubleBuffer(Thread thread) { return getTempDoubleBuffer(thread, -1); }
	private static BufferManager getTempBufferManager(int size) { return getTempBufferManager(Thread.currentThread(), size); }
	public static ByteBuffer getTempByteBuffer(int size) { return getTempByteBuffer(Thread.currentThread(), size); }
	public static ShortBuffer getTempShortBuffer(int size) { return getTempShortBuffer(Thread.currentThread(), size); }
	public static CharBuffer getTempCharBuffer(int size) { return getTempCharBuffer(Thread.currentThread(), size); }
	public static IntBuffer getTempIntBuffer(int size) { return getTempIntBuffer(Thread.currentThread(), size); }
	public static LongBuffer getTempLongBuffer(int size) { return getTempLongBuffer(Thread.currentThread(), size); }
	public static FloatBuffer getTempFloatBuffer(int size) { return getTempFloatBuffer(Thread.currentThread(), size); }
	public static DoubleBuffer getTempDoubleBuffer(int size) { return getTempDoubleBuffer(Thread.currentThread(), size); }
	private static BufferManager getTempBufferManager() { return getTempBufferManager(-1); }
	public static ByteBuffer getTempByteBuffer() { return getTempByteBuffer(-1); }
	public static ShortBuffer getTempShortBuffer() { return getTempShortBuffer(-1); }
	public static CharBuffer getTempCharBuffer() { return getTempCharBuffer(-1); }
	public static IntBuffer getTempIntBuffer() { return getTempIntBuffer(-1); }
	public static LongBuffer getTempLongBuffer() { return getTempLongBuffer(-1); }
	public static FloatBuffer getTempFloatBuffer() { return getTempFloatBuffer(-1); }
	public static DoubleBuffer getTempDoubleBuffer() { return getTempDoubleBuffer(-1); }

	public static Buffer pointTo(Buffer result, long address, int size) { try {
		Unsafe unsafe = UnsafeUtils.R_getUnsafe(); if(unsafe == null) return null;
		unsafe.putInt(result, AFIELD_Buffer_mark, -1);
		unsafe.putInt(result, AFIELD_Buffer_position, 0);
		unsafe.putInt(result, AFIELD_Buffer_limit, size);
		unsafe.putInt(result, AFIELD_Buffer_capacity, size);
		unsafe.putLong(result, AFIELD_Buffer_address, address);
		if(result instanceof ByteBuffer)
			((ByteBuffer) result).order(ByteOrder.nativeOrder());
		return result;
	} catch(Exception e) { throw new Error(e); } }
	public static NativeBuffer pointTo(NativeBuffer result, long address, int size) { try {
		Unsafe unsafe = UnsafeUtils.R_getUnsafe(); if(unsafe == null) return null;
		pointTo(result.getBuffer(), address, size);
		if(result instanceof AbstractBuffer) {
			unsafe.putObject(result, AFIELD_AbstractBuffer_buffer, result.getBuffer());
			unsafe.putInt(result, AFIELD_AbstractBuffer_elementSize, result.elementSize());
			unsafe.putInt(result, AFIELD_AbstractBuffer_capacity, size / result.elementSize());
			unsafe.putInt(result, AFIELD_AbstractBuffer_position, 0);
		} return result;
	} catch(Exception e) { throw new Error(e); } }
	public static <BUFFER extends Buffer> BUFFER pointTo(Class<BUFFER> clazz, long address, int size) { try { Unsafe unsafe = UnsafeUtils.R_getUnsafe(); if(unsafe == null) return null; return (BUFFER) pointTo((Buffer) unsafe.allocateInstance(clazz), address, size); } catch(Exception e) { throw new Error(e); } }
	public static ByteBuffer pointTo(long address, int size) { return pointTo(CLASS_DirectByteBuffer, address, size); }
	public static ByteBuffer _pointTo(long address, int size) { return (ByteBuffer) pointTo((Buffer) Pool.tryBorrow(Pool.getPool(ByteBuffer.class)), address, size); }
	@Deprecated public static Buffer __pointTo(Buffer result, long address, int size) { try {
		Unsafe unsafe = UnsafeUtils.R_getUnsafe(); if(unsafe == null) return null;
		unsafe.putInt(result, AFIELD_Buffer_mark, -1);
		unsafe.putInt(result, AFIELD_Buffer_position, 0);
		unsafe.putInt(result, AFIELD_Buffer_limit, size);
		unsafe.putInt(result, AFIELD_Buffer_capacity, size);
		unsafe.putLong(result, AFIELD_Buffer_address, address);
		if(result instanceof ByteBuffer)
			((ByteBuffer) result).order(ByteOrder.nativeOrder());
		return result;
	} catch(Exception e) { throw new Error(e); } }
	@Deprecated public static NativeBuffer __pointTo(NativeBuffer result, long address, int size) { try {
		Unsafe unsafe = UnsafeUtils.R_getUnsafe(); if(unsafe == null) return null;
		__pointTo(result.getBuffer(), address, size);
		if(result instanceof AbstractBuffer) {
			unsafe.putObject(result, AFIELD_AbstractBuffer_buffer, result.getBuffer());
			unsafe.putInt(result, AFIELD_AbstractBuffer_elementSize, result.elementSize());
			unsafe.putInt(result, AFIELD_AbstractBuffer_capacity, size / result.elementSize());
			unsafe.putInt(result, AFIELD_AbstractBuffer_position, 0);
		} return result;
	} catch(Exception e) { throw new Error(e); } }
	@Deprecated public static <BUFFER extends Buffer> BUFFER __pointTo(Class<BUFFER> clazz, long address, int size) { try { Unsafe unsafe = UnsafeUtils.R_getUnsafe(); if(unsafe == null) return null; return (BUFFER) __pointTo((Buffer) unsafe.allocateInstance(clazz), address, size); } catch(Exception e) { throw new Error(e); } }
	@Deprecated public static ByteBuffer __pointTo(long address, int size) { return __pointTo(CLASS_DirectByteBuffer, address, size); }
	@Deprecated public static ByteBuffer ___pointTo(long address, int size) { return (ByteBuffer) __pointTo((Buffer) Pool.tryBorrow(Pool.getPool(ByteBuffer.class)), address, size); }

	public static int getMark(Buffer buffer) { return buffer == null ? -1 : UnsafeUtils._getInt(buffer, AFIELD_Buffer_mark, -1); }
	public static int getPosition(Buffer buffer) { return buffer == null ? 0 : UnsafeUtils._getInt(buffer, AFIELD_Buffer_position, 0); }
	public static int getLimit(Buffer buffer) { return buffer == null ? 0 : UnsafeUtils._getInt(buffer, AFIELD_Buffer_limit, 0); }
	public static int getCapacity(Buffer buffer) { return buffer == null ? 0 : UnsafeUtils._getInt(buffer, AFIELD_Buffer_capacity, 0); }
	public static long getAddress(Buffer buffer) { return buffer == null ? 0 : UnsafeUtils.getLong(buffer, AFIELD_Buffer_address); }
	public static int getMark(NativeBuffer buffer) { return getMark(buffer == null ? null : buffer.getBuffer()); }
	public static int getPosition(NativeBuffer buffer) { return getPosition(buffer == null ? null : buffer.getBuffer()); }
	public static int getLimit(NativeBuffer buffer) { return getLimit(buffer == null ? null : buffer.getBuffer()); }
	public static int getCapacity(NativeBuffer buffer) { return getCapacity(buffer == null ? null : buffer.getBuffer()); }
	public static long getAddress(NativeBuffer buffer) { return getAddress(buffer == null ? null : buffer.getBuffer()); }
	@Deprecated public static int __getMark(Buffer buffer) { return buffer == null ? -1 : UnsafeUtils.__getInt(buffer, AFIELD_Buffer_mark); }
	@Deprecated public static int __getPosition(Buffer buffer) { return buffer == null ? 0 : UnsafeUtils.__getInt(buffer, AFIELD_Buffer_position); }
	@Deprecated public static int __getLimit(Buffer buffer) { return buffer == null ? 0 : UnsafeUtils.__getInt(buffer, AFIELD_Buffer_limit); }
	@Deprecated public static int __getCapacity(Buffer buffer) { return buffer == null ? 0 : UnsafeUtils.__getInt(buffer, AFIELD_Buffer_capacity); }
	@Deprecated public static long __getAddress(Buffer buffer) { return buffer == null ? 0 : UnsafeUtils.__getLong(buffer, AFIELD_Buffer_address); }
	@Deprecated public static int __getMark(NativeBuffer buffer) { return __getMark(buffer == null ? null : buffer.getBuffer()); }
	@Deprecated public static int __getPosition(NativeBuffer buffer) { return __getPosition(buffer == null ? null : buffer.getBuffer()); }
	@Deprecated public static int __getLimit(NativeBuffer buffer) { return __getLimit(buffer == null ? null : buffer.getBuffer()); }
	@Deprecated public static int __getCapacity(NativeBuffer buffer) { return __getCapacity(buffer == null ? null : buffer.getBuffer()); }
	@Deprecated public static long __getAddress(NativeBuffer buffer) { return __getAddress(buffer == null ? null : buffer.getBuffer()); }

	public static ByteBuffer allocateByteBuffer(int size, BufferManager bufferManager) { return __pointTo(CLASS_DirectByteBuffer, bufferManager._allocate(size), size); }
	public static ShortBuffer allocateShortBuffer(int size, BufferManager bufferManager) { return __pointTo(CLASS_DirectShortBuffer, bufferManager._allocate(size << 1), size); }
	public static CharBuffer allocateCharBuffer(int size, BufferManager bufferManager) { return __pointTo(CLASS_DirectCharBuffer, bufferManager._allocate(size << 1), size); }
	public static IntBuffer allocateIntBuffer(int size, BufferManager bufferManager) { return __pointTo(CLASS_DirectIntBuffer, bufferManager._allocate(size << 2), size); }
	public static LongBuffer allocateLongBuffer(int size, BufferManager bufferManager) { return __pointTo(CLASS_DirectLongBuffer, bufferManager._allocate(size << 3), size); }
	public static FloatBuffer allocateFloatBuffer(int size, BufferManager bufferManager) { return __pointTo(CLASS_DirectFloatBuffer, bufferManager._allocate(size << 2), size); }
	public static DoubleBuffer allocateDoubleBuffer(int size, BufferManager bufferManager) { return __pointTo(CLASS_DirectDoubleBuffer, bufferManager._allocate(size << 3), size); }
	public static PointerBuffer allocatePointerBuffer(int size, BufferManager bufferManager) { return PointerBuffer.wrap(__pointTo(CLASS_DirectByteBuffer, bufferManager._allocate(size * PointerBuffer.ELEMENT_SIZE), size * PointerBuffer.ELEMENT_SIZE)); }

	public static ByteBuffer allocateByteBuffer(int size) { return allocateByteBuffer(size, getTempBufferManager(size)); }
	public static ShortBuffer allocateShortBuffer(int size) { return allocateShortBuffer(size, getTempBufferManager(size << 1)); }
	public static CharBuffer allocateCharBuffer(int size) { return allocateCharBuffer(size, getTempBufferManager(size << 1)); }
	public static IntBuffer allocateIntBuffer(int size) { return allocateIntBuffer(size, getTempBufferManager(size << 2)); }
	public static LongBuffer allocateLongBuffer(int size) { return allocateLongBuffer(size, getTempBufferManager(size << 3)); }
	public static FloatBuffer allocateFloatBuffer(int size) { return allocateFloatBuffer(size, getTempBufferManager(size << 2)); }
	public static DoubleBuffer allocateDoubleBuffer(int size) { return allocateDoubleBuffer(size, getTempBufferManager(size << 3)); }
	public static PointerBuffer allocatePointerBuffer(int size) { return allocatePointerBuffer(size, getTempBufferManager(size * PointerBuffer.ELEMENT_SIZE)); }

	public static ByteBuffer _allocateByteBuffer(int size, BufferManager bufferManager) { return (ByteBuffer) __pointTo((Buffer) Pool.tryBorrow(Pool.getPool(ByteBuffer.class)), bufferManager._allocate(size), size); }
	public static ShortBuffer _allocateShortBuffer(int size, BufferManager bufferManager) { return (ShortBuffer) __pointTo((Buffer) Pool.tryBorrow(Pool.getPool(ShortBuffer.class)), bufferManager._allocate(size << 1), size); }
	public static CharBuffer _allocateCharBuffer(int size, BufferManager bufferManager) { return (CharBuffer) __pointTo((Buffer) Pool.tryBorrow(Pool.getPool(CharBuffer.class)), bufferManager._allocate(size << 1), size); }
	public static IntBuffer _allocateIntBuffer(int size, BufferManager bufferManager) { return (IntBuffer) __pointTo((Buffer) Pool.tryBorrow(Pool.getPool(IntBuffer.class)), bufferManager._allocate(size << 2), size); }
	public static LongBuffer _allocateLongBuffer(int size, BufferManager bufferManager) { return (LongBuffer) __pointTo((Buffer) Pool.tryBorrow(Pool.getPool(LongBuffer.class)), bufferManager._allocate(size << 3), size); }
	public static FloatBuffer _allocateFloatBuffer(int size, BufferManager bufferManager) { return (FloatBuffer) __pointTo((Buffer) Pool.tryBorrow(Pool.getPool(FloatBuffer.class)), bufferManager._allocate(size << 2), size); }
	public static DoubleBuffer _allocateDoubleBuffer(int size, BufferManager bufferManager) { return (DoubleBuffer) __pointTo((Buffer) Pool.tryBorrow(Pool.getPool(DoubleBuffer.class)), bufferManager._allocate(size << 3), size); }
	public static PointerBuffer _allocatePointerBuffer(int size, BufferManager bufferManager) { PointerBuffer result = Pool.tryBorrow(Pool.getPool(PointerBuffer.class)); __pointTo(result, bufferManager._allocate(size * PointerBuffer.ELEMENT_SIZE), size * PointerBuffer.ELEMENT_SIZE); return result; }

	public static ByteBuffer _allocateByteBuffer(int size) { return _allocateByteBuffer(size, getTempBufferManager(size)); }
	public static ShortBuffer _allocateShortBuffer(int size) { return _allocateShortBuffer(size, getTempBufferManager(size << 1)); }
	public static CharBuffer _allocateCharBuffer(int size) { return _allocateCharBuffer(size, getTempBufferManager(size << 1)); }
	public static IntBuffer _allocateIntBuffer(int size) { return _allocateIntBuffer(size, getTempBufferManager(size << 2)); }
	public static LongBuffer _allocateLongBuffer(int size) { return _allocateLongBuffer(size, getTempBufferManager(size << 3)); }
	public static FloatBuffer _allocateFloatBuffer(int size) { return _allocateFloatBuffer(size, getTempBufferManager(size << 2)); }
	public static DoubleBuffer _allocateDoubleBuffer(int size) { return _allocateDoubleBuffer(size, getTempBufferManager(size << 3)); }
	public static PointerBuffer _allocatePointerBuffer(int size) { return _allocatePointerBuffer(size, getTempBufferManager(size * PointerBuffer.ELEMENT_SIZE)); }

	public static MemoryStack stack() { return stack(512); }
	public static MemoryStack stack(int size) { return new MemoryStack(createByteBuffer(size)); }
	public static MemoryStack allocateStack() { return allocateStack(512); }
	public static MemoryStack allocateStack(int size) { return new MemoryStack(allocateByteBuffer(size)); }
	public static ByteBuffer createByteBuffer(int size) { return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder()); }
	public static ShortBuffer createShortBuffer(int size) { return createByteBuffer(size << 1).asShortBuffer(); }
	public static CharBuffer createCharBuffer(int size) { return createByteBuffer(size << 1).asCharBuffer(); }
	public static IntBuffer createIntBuffer(int size) { return createByteBuffer(size << 2).asIntBuffer(); }
	public static LongBuffer createLongBuffer(int size) { return createByteBuffer(size << 3).asLongBuffer(); }
	public static FloatBuffer createFloatBuffer(int size) { return createByteBuffer(size << 2).asFloatBuffer(); }
	public static DoubleBuffer createDoubleBuffer(int size) { return createByteBuffer(size << 3).asDoubleBuffer(); }
	public static PointerBuffer createPointerBuffer(int size) { return PointerBuffer.wrap(createByteBuffer(PointerBuffer.ELEMENT_SIZE * size)); }

	public static int getElementSizeExponent(Buffer buffer) {
		if(buffer instanceof ByteBuffer) return 0;
		else if(buffer instanceof ShortBuffer || buffer instanceof CharBuffer) return 1;
		else if(buffer instanceof FloatBuffer || buffer instanceof IntBuffer) return 2;
		else if(buffer instanceof LongBuffer || buffer instanceof DoubleBuffer) return 3;
		else throw new IllegalStateException("Unsupported buffer type: " + buffer);
	}
	public static int getElementSize(Buffer buffer) { return 1 << getElementSizeExponent(buffer); }
	public static int getByteBytesSize(int size) { return size; }
	public static int getShortBytesSize(int size) { return size << 1; }
	public static int getCharBytesSize(int size) { return size << 1; }
	public static int getIntBytesSize(int size) { return size << 2; }
	public static int getLongBytesSize(int size) { return size << 3; }
	public static int getFloatBytesSize(int size) { return size << 2; }
	public static int getDoubleBytesSize(int size) { return size << 3; }
	public static int getPointerBytesSize(int size) { return PointerBuffer.ELEMENT_SIZE * size; }

	@SuppressWarnings("unchecked")
	public static <B extends Buffer> B slice(B buffer) {
		if(buffer == null) return null;
		if(buffer instanceof ByteBuffer) {
			ByteBuffer bb = (ByteBuffer) buffer;
			return (B) bb.slice().order(bb.order()); // slice and duplicate may change byte order
		} else if(buffer instanceof CharBuffer) return (B) ((CharBuffer) buffer).slice();
		else if(buffer instanceof IntBuffer) return (B) ((IntBuffer) buffer).slice();
		else if(buffer instanceof LongBuffer) return (B) ((LongBuffer) buffer).slice();
		else if(buffer instanceof ShortBuffer) return (B) ((ShortBuffer) buffer).slice();
		else if(buffer instanceof FloatBuffer) return (B) ((FloatBuffer) buffer).slice();
		else if(buffer instanceof DoubleBuffer) return (B) ((DoubleBuffer) buffer).slice();
		throw new IllegalArgumentException("Unexpected buffer type: " + buffer.getClass());
	}
	public static <B extends Buffer> B slice(B buffer, int offset, int size) {
		if(buffer == null) return null;
		int pos = buffer.position(); int limit = buffer.limit(); B slice = null;
		try { buffer.position(offset).limit(offset + size); slice = slice(buffer);
		} finally { buffer.position(pos).limit(limit); } return slice;
	}
	public static <B extends NativeBuffer> B slice(B buffer, int offset, int size) {
		if(buffer == null) return null;
		int pos = buffer.position(); int limit = buffer.limit(); B slice = null; Buffer realBuffer = buffer.getBuffer();
		try { realBuffer.position(offset).limit(offset + size); slice = (B) buffer.slice();
		} finally { realBuffer.position(pos).limit(limit); } return slice;
	}

	public static void copy(Buffer src, int srcOff, Buffer dest, int destOff, int len) {
		srcOff *= getElementSize(src); destOff *= getElementSize(dest);
		UnsafeUtils.__copyMemory(__getAddress(src) + srcOff, __getAddress(dest) + destOff, len);
	}
	public static void copy(NativeBuffer src, int srcOff, Buffer dest, int destOff, int len) { copy(src.getBuffer(), srcOff, dest, destOff, len); }
	public static void copy(Buffer src, int srcOff, NativeBuffer dest, int destOff, int len) { copy(src, srcOff, dest.getBuffer(), destOff, len); }
	public static void copy(NativeBuffer src, int srcOff, NativeBuffer dest, int destOff, int len) { copy(src.getBuffer(), srcOff, dest.getBuffer(), destOff, len); }
	public static void fill(Buffer buffer, int offBuff, Buffer filler, int offFill, int len) {
		int bufferCapacity = buffer.capacity() * getElementSize(buffer);
		int fillerCapacity = filler.capacity() * getElementSize(filler);
		offBuff *= getElementSize(buffer); offFill *= getElementSize(filler);
		len *= getElementSize(buffer);
		ArrayUtils.assertIndex(offBuff, bufferCapacity, len);
		ArrayUtils.assertIndex(offFill, fillerCapacity, 1);
		long buffAddr = __getAddress(buffer);
		long fillAddr = __getAddress(filler);
		int fillerLength = fillerCapacity - offFill;
		int loops = len / fillerLength + (len % fillerLength > 1 ? 1 : 0); for(int i = 0; i < loops; i++) {
			UnsafeUtils.__copyMemory(fillAddr + offFill, buffAddr + offBuff, Math.min(fillerLength, len));
			offBuff += fillerLength; len -= fillerLength;
		}
	}
	public static void fill(NativeBuffer buffer, int offBuff, Buffer filler, int offFill, int len) { fill(buffer.getBuffer(), offBuff, filler, offFill, len); }
	public static void fill(Buffer buffer, int offBuff, NativeBuffer filler, int offFill, int len) { fill(buffer, offBuff, filler.getBuffer(), offFill, len); }
	public static void fill(NativeBuffer buffer, int offBuff, NativeBuffer filler, int offFill, int len) { fill(buffer.getBuffer(), offBuff, filler.getBuffer(), offFill, len); }
	public static void empty(Buffer buffer, int off, int len) { fill(buffer, off, getEmptyByteBuffer(), 0, len); }
	public static void empty(NativeBuffer buffer, int off, int len) { fill(buffer, off, getEmptyByteBuffer(), 0, len); }

	public static void deallocate(Buffer buffer, Thread thread) {
		if(buffer == null || thread == null) return;
		BufferManager bufferManager = getTempBufferManager(thread);
		if(bufferManager.isPartOfBuffer(buffer)) { bufferManager.deallocate(buffer); return; }
		DeallocationHelper.getInstance().deallocate(buffer);
	}
	public static void deallocate(NativeBuffer buffer, Thread thread) { deallocate(buffer.getBuffer(), thread); }
	public static void deallocate(Buffer buffer) { deallocate(buffer, Thread.currentThread()); }
	public static void deallocate(NativeBuffer buffer) { deallocate(buffer.getBuffer()); }

	public static void _deallocate(Buffer buffer, Thread thread) {
		if(buffer == null || thread == null) return;
		BufferManager bufferManager = getTempBufferManager(thread);
		if(bufferManager.isPartOfBuffer(buffer)) { bufferManager.deallocate(buffer); Pool.returnObject(buffer.getClass(), buffer); return; }
		DeallocationHelper.getInstance().deallocate(buffer); Pool.returnObject(buffer.getClass(), buffer);
	}
	public static void _deallocate(NativeBuffer buffer, Thread thread) { _deallocate(buffer.getBuffer(), thread); }
	public static void _deallocate(Buffer buffer) { _deallocate(buffer, Thread.currentThread()); }
	public static void _deallocate(NativeBuffer buffer) { _deallocate(buffer.getBuffer()); }

	// --------------------------------------------------------------------------------
	// https://sourceforge.net/p/tuer/code/HEAD/tree/pre_beta/src/main/java/engine/misc/DeallocationHelper.java#l57
	@SuppressWarnings({"cast", "unchecked", "JavaReflectionMemberAccess", "StatementWithEmptyBody"})
	public static class DeallocationHelper {

		/**
		 * tool responsible for releasing the native memory of a deallocatable byte
		 * buffer
		 */
		public static abstract class Deallocator {
			/**
			 * releases the native memory of a deallocatable byte buffer
			 *
			 * @param directByteBuffer
			 *            deallocatable byte buffer
			 *
			 * @return <code>true</code> if the deallocation is successful,
			 *         otherwise <code>false</code>
			 */
			public abstract boolean run(ByteBuffer directByteBuffer);
		}

		public static class OracleSunOpenJdkDeallocator extends Deallocator {
			private Method directByteBufferCleanerMethod;
			private Method cleanerCleanOrRunMethod;
			private Object unsafeObject;
			private Method invokeCleanerMethod;

			public OracleSunOpenJdkDeallocator() {
				ExceptionUtils.doSilentThrowsRunnable((e) -> ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> {
					Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
					Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
					theUnsafeField.setAccessible(true);
					unsafeObject = theUnsafeField.get(null);
					invokeCleanerMethod = unsafeClass.getMethod("invokeCleaner", java.nio.ByteBuffer.class);
					invokeCleanerMethod.setAccessible(true);
				}), () -> {
					unsafeObject = null; invokeCleanerMethod = null;
					Class<?> directByteBufferClass = Class.forName("java.nio.DirectByteBuffer");
					Method localDirectByteBufferCleanerMethod = directByteBufferClass.getDeclaredMethod("cleaner");
					Class<?> cleanerClass = localDirectByteBufferCleanerMethod.getReturnType();
					Method localCleanerCleanOrRunMethod = Runnable.class.isAssignableFrom(cleanerClass) ?
							Runnable.class.getDeclaredMethod("run") : cleanerClass.getDeclaredMethod("clean");
					localCleanerCleanOrRunMethod.setAccessible(true);
					directByteBufferCleanerMethod = localDirectByteBufferCleanerMethod;
					cleanerCleanOrRunMethod = localCleanerCleanOrRunMethod;
				});
			}

			@Override
			public boolean run(ByteBuffer directByteBuffer) {
				Proxy<Boolean> success = Pool.tryBorrow(Pool.getPool(Proxy.class));
				try { success.set(false); ExceptionUtils.doSilentThrowsRunnable(false, () -> {
					if(directByteBufferCleanerMethod != null && cleanerCleanOrRunMethod != null) {
						Object cleaner = directByteBufferCleanerMethod.invoke(directByteBuffer);
						if(cleaner != null) { cleanerCleanOrRunMethod.invoke(cleaner); success.set(true); }
					} else if(unsafeObject != null && invokeCleanerMethod != null) {
						invokeCleanerMethod.invoke(unsafeObject, directByteBuffer); success.set(true); }
				}); return success.get(); } finally { Pool.returnObject(Proxy.class, success); }
			}
		}

		public static class AndroidDeallocator extends Deallocator {
			private Method directByteBufferFreeMethod;

			public AndroidDeallocator() {
				ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> {
					Class<?> directByteBufferClass = Class.forName("java.nio.DirectByteBuffer");
					Method localDirectByteBufferFreeMethod = directByteBufferClass.getDeclaredMethod("free");
					localDirectByteBufferFreeMethod.setAccessible(true);
					directByteBufferFreeMethod = localDirectByteBufferFreeMethod;
				});
			}

			@Override
			public boolean run(ByteBuffer directByteBuffer) {
				Proxy<Boolean> success = Pool.tryBorrow(Pool.getPool(Proxy.class));
				try { success.set(false); ExceptionUtils.doSilentThrowsRunnable(false, () -> {
						if(directByteBufferFreeMethod == null) return;
						directByteBufferFreeMethod.invoke(directByteBuffer);
						success.set(true);
					}); return success.get();
				} finally { Pool.returnObject(Proxy.class, success); }
			}
		}

		public static class GnuClasspathDeallocator extends Deallocator {
			private Method vmDirectByteBufferFreeMethod;
			private Field bufferAddressField;

			public GnuClasspathDeallocator() {
				ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> {
					Class<?> vmDirectByteBufferClass = Class.forName("java.nio.VMDirectByteBuffer");
					Class<?> gnuClasspathPointerClass = Class.forName("gnu.classpath.Pointer");
					Method localVmDirectByteBufferFreeMethod = vmDirectByteBufferClass.getDeclaredMethod("free", gnuClasspathPointerClass);
					localVmDirectByteBufferFreeMethod.setAccessible(true);
					bufferAddressField = Buffer.class.getDeclaredField("address");
					vmDirectByteBufferFreeMethod = localVmDirectByteBufferFreeMethod;
					bufferAddressField.setAccessible(true);
				});
			}

			@Override
			public boolean run(ByteBuffer directByteBuffer) {
				Proxy<Boolean> success = Pool.tryBorrow(Pool.getPool(Proxy.class));
				try { success.set(false); ExceptionUtils.doSilentThrowsRunnable(false, () -> {
					if(vmDirectByteBufferFreeMethod == null || bufferAddressField == null) return;
					Object address = bufferAddressField.get(directByteBuffer);
					if(address == null) return;
					vmDirectByteBufferFreeMethod.invoke(null, address);
					success.set(true);
				}); return success.get();
				} finally { Pool.returnObject(Proxy.class, success); }
			}
		}

		public static class ApacheHarmonyDeallocator extends Deallocator {
			private Method directByteBufferFreeMethod;

			public ApacheHarmonyDeallocator() {
				ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> {
					Class<?> directByteBufferClass = Class.forName("java.nio.DirectByteBuffer");
					Method localDirectByteBufferFreeMethod = directByteBufferClass.getDeclaredMethod("free");
					localDirectByteBufferFreeMethod.setAccessible(true);
					directByteBufferFreeMethod = localDirectByteBufferFreeMethod;
				});
			}

			@Override
			public boolean run(ByteBuffer directByteBuffer) {
				Proxy<Boolean> success = Pool.tryBorrow(Pool.getPool(Proxy.class));
				try { success.set(false); ExceptionUtils.doSilentThrowsRunnable(false, () -> {
					if(directByteBufferFreeMethod == null) return;
					directByteBufferFreeMethod.invoke(directByteBuffer);
					success.set(true);
				}); return success.get();
				} finally { Pool.returnObject(Proxy.class, success); }
			}
		}

		protected final static DeallocationHelper instance = new DeallocationHelper();
		public static DeallocationHelper getInstance() { return instance; }

		protected final Map<Class<?>, Field> attachmentOrByteBufferFieldMap;
		protected final Set<Class<?>> deallocatableBufferClassSet;
		protected final Deallocator deallocator;

		protected DeallocationHelper() { this(false); }

		/**
		 * Main constructor
		 *
		 * @param ignoreClassesAndFieldsHints
		 *            <code>true</code> if the known implementation details should
		 *            be ignored when looking for the classes and the fields used
		 *            for the native memory of the direct buffers (they are then
		 *            fully recomputed at runtime which is slower but safer),
		 *            otherwise <code>false</code>
		 */
		protected DeallocationHelper(boolean ignoreClassesAndFieldsHints) {
			ArrayList<Buffer> buffersToDelete = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			HashMap<String, String> attachmentOrByteBufferFieldNameMap = Pool.tryBorrow(Pool.getPool(HashMap.class)); try {
				String javaVendor = System.getProperty("java.vendor");
				String javaVersion = System.getProperty("java.version");

				if(!ignoreClassesAndFieldsHints) {
					if(javaVendor.equals("AdoptOpenJDK") || javaVendor.equals("Sun Microsystems Inc.") || javaVendor.equals("Oracle Corporation")) {
						String java14to16DirectBufferAttachmentFieldName = "viewedBuffer";
						String java17to19DirectBufferAttachmentFieldName = "att";
						String byteBufferAsNonByteBufferByteBufferFieldName = "bb";
						String[] directBufferClassnames = new String[] { "java.nio.DirectByteBuffer",
								"java.nio.DirectByteBufferR", "java.nio.DirectCharBufferRS", "java.nio.DirectCharBufferRU",
								"java.nio.DirectCharBufferS", "java.nio.DirectCharBufferU", "java.nio.DirectDoubleBufferRS",
								"java.nio.DirectDoubleBufferRU", "java.nio.DirectDoubleBufferS", "java.nio.DirectDoubleBufferU",
								"java.nio.DirectFloatBufferRS", "java.nio.DirectFloatBufferRU", "java.nio.DirectFloatBufferS",
								"java.nio.DirectFloatBufferU", "java.nio.DirectIntBufferRS", "java.nio.DirectIntBufferRU",
								"java.nio.DirectIntBufferS", "java.nio.DirectIntBufferU", "java.nio.DirectLongBufferRS",
								"java.nio.DirectLongBufferRU", "java.nio.DirectLongBufferS", "java.nio.DirectLongBufferU",
								"java.nio.DirectShortBufferRS", "java.nio.DirectShortBufferRU", "java.nio.DirectShortBufferS",
								"java.nio.DirectShortBufferU" };
						String[] byteBufferAsNonByteBufferClassnames = new String[] { "java.nio.ByteBufferAsCharBufferB",
								"java.nio.ByteBufferAsCharBufferL", "java.nio.ByteBufferAsCharBufferRB",
								"java.nio.ByteBufferAsCharBufferRL", "java.nio.ByteBufferAsDoubleBufferB",
								"java.nio.ByteBufferAsDoubleBufferL", "java.nio.ByteBufferAsDoubleBufferRB",
								"java.nio.ByteBufferAsDoubleBufferRL", "java.nio.ByteBufferAsFloatBufferB",
								"java.nio.ByteBufferAsFloatBufferL", "java.nio.ByteBufferAsFloatBufferRB",
								"java.nio.ByteBufferAsFloatBufferRL", "java.nio.ByteBufferAsIntBufferB",
								"java.nio.ByteBufferAsIntBufferL", "java.nio.ByteBufferAsIntBufferRB",
								"java.nio.ByteBufferAsIntBufferRL", "java.nio.ByteBufferAsLongBufferB",
								"java.nio.ByteBufferAsLongBufferL", "java.nio.ByteBufferAsLongBufferRB",
								"java.nio.ByteBufferAsLongBufferRL", "java.nio.ByteBufferAsShortBufferB",
								"java.nio.ByteBufferAsShortBufferL", "java.nio.ByteBufferAsShortBufferRB",
								"java.nio.ByteBufferAsShortBufferRL" };
						String[] javaVersionElements = System.getProperty("java.version").split("\\.");

						String directBufferAttachmentFieldName;
						if(SystemUtils.JAVA_DETECTION_VERSION <= 6) directBufferAttachmentFieldName = java14to16DirectBufferAttachmentFieldName;
						else directBufferAttachmentFieldName = java17to19DirectBufferAttachmentFieldName;
						for(String directBufferClassname : directBufferClassnames) attachmentOrByteBufferFieldNameMap.put(directBufferClassname, directBufferAttachmentFieldName);
						for(String byteBufferAsNonByteBufferClassname : byteBufferAsNonByteBufferClassnames) attachmentOrByteBufferFieldNameMap.put(byteBufferAsNonByteBufferClassname, byteBufferAsNonByteBufferByteBufferFieldName);
					} else if(javaVendor.equals("The Android Project")) {
						String byteBufferAsNonByteBufferByteBufferFieldName = "byteBuffer";
						String[] byteBufferAsNonByteBufferClassnames = new String[] { "java.nio.ByteBufferAsCharBuffer",
								"java.nio.ByteBufferAsDoubleBuffer", "java.nio.ByteBufferAsFloatBuffer",
								"java.nio.ByteBufferAsIntBuffer", "java.nio.ByteBufferAsLongBuffer",
								"java.nio.ByteBufferAsShortBuffer" };
						for(String byteBufferAsNonByteBufferClassname : byteBufferAsNonByteBufferClassnames) attachmentOrByteBufferFieldNameMap.put(byteBufferAsNonByteBufferClassname, byteBufferAsNonByteBufferByteBufferFieldName);
					} else if(/* javaVendor.equals("Apple Inc.")|| */javaVendor.equals("Free Software Foundation, Inc.")) {
						String byteBufferAsNonByteBufferByteBufferFieldName = "bb";
						String[] byteBufferAsNonByteBufferClassnames = new String[] { "java.nio.CharViewBufferImpl",
								"java.nio.DoubleViewBufferImpl", "java.nio.FloatViewBufferImpl", "java.nio.IntViewBufferImpl",
								"java.nio.LongViewBufferImpl", "java.nio.ShortViewBufferImpl" };
						for(String byteBufferAsNonByteBufferClassname : byteBufferAsNonByteBufferClassnames) attachmentOrByteBufferFieldNameMap.put(byteBufferAsNonByteBufferClassname, byteBufferAsNonByteBufferByteBufferFieldName);
					} else if(javaVendor.contains("Apache")) {
						String byteBufferAsNonByteBufferByteBufferFieldName = "byteBuffer";
						String[] byteBufferAsNonByteBufferClassnames = new String[] { "java.nio.CharToByteBufferAdapter",
								"java.nio.DoubleToByteBufferAdapter", "java.nio.FloatToByteBufferAdapter",
								"java.nio.IntToByteBufferAdapter", "java.nio.LongToByteBufferAdapter",
								"java.nio.ShortToByteBufferAdapter" };
						for(String byteBufferAsNonByteBufferClassname : byteBufferAsNonByteBufferClassnames) attachmentOrByteBufferFieldNameMap.put(byteBufferAsNonByteBufferClassname, byteBufferAsNonByteBufferByteBufferFieldName);
					} else if(javaVendor.equals("Jeroen Frijters")) {// TODO IKVM
					} else if(javaVendor.contains("IBM")) {// TODO J9
					}
				}

				if(!attachmentOrByteBufferFieldNameMap.isEmpty()) {
					ArrayList<String> classnamesToRemove = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
						for(String classname : attachmentOrByteBufferFieldNameMap.keySet())
							try { Class.forName(classname); } catch (ClassNotFoundException cnfe) { classnamesToRemove.add(classname); }
						for(String classnameToRemove : classnamesToRemove) attachmentOrByteBufferFieldNameMap.remove(classnameToRemove);
					} finally { Pool.returnObject(ArrayList.class, classnamesToRemove); }
				}

				attachmentOrByteBufferFieldMap = new HashMap<>();
				if(!attachmentOrByteBufferFieldNameMap.isEmpty())
					for(Map.Entry<String, String> attachmentOrByteBufferFieldNameEntry : attachmentOrByteBufferFieldNameMap.entrySet()) {
						String classname = attachmentOrByteBufferFieldNameEntry.getKey();
						String fieldname = attachmentOrByteBufferFieldNameEntry.getValue();
						ExceptionUtils.doSilentThrowsRunnable((e) -> System.err.println("The class " + classname + " hasn't been found while initializing the deallocator. Java vendor: " + javaVendor + " Java version: " + javaVersion), () -> {
							Class<?> bufferClass = Class.forName(classname);
							Field bufferField = null;
							Class<?> bufferIntermediaryClass = bufferClass;
							ArrayList<Class<?>> intermediaryClassWithoutBufferList = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
								while(bufferIntermediaryClass != null) {
									try { bufferField = bufferIntermediaryClass.getDeclaredField(fieldname);
									} catch (NoSuchFieldException nsfe) {
										if(!bufferIntermediaryClass.equals(Object.class) && !bufferIntermediaryClass.equals(Buffer.class))
											intermediaryClassWithoutBufferList.add(bufferIntermediaryClass);
									} bufferIntermediaryClass = bufferIntermediaryClass.getSuperclass();
								} if(bufferField == null) {
									String superClassesMsg;
									if(intermediaryClassWithoutBufferList.isEmpty()) superClassesMsg = "";
									else if(intermediaryClassWithoutBufferList.size() == 1) superClassesMsg = " and in its super class " + intermediaryClassWithoutBufferList.get(0).getName();
									else { StringBuilder builder = Pool.tryBorrow(Pool.getPool(StringBuilder.class)); try {
											builder.append(" and in its super classes"); int classIndex = 0;
											for(Class<?> intermediaryClassWithoutBuffer : intermediaryClassWithoutBufferList) {
												builder.append(' ').append(intermediaryClassWithoutBuffer.getName());
												if(classIndex < intermediaryClassWithoutBufferList.size() - 1) builder.append(','); classIndex++;
											} superClassesMsg = builder.toString();
										} finally { Pool.returnObject(StringBuilder.class, builder); }
									} System.err.println("The field " + fieldname + " hasn't been found in the class " + classname + superClassesMsg);
								} else attachmentOrByteBufferFieldMap.put(bufferClass, bufferField);
							} finally { Pool.returnObject(ArrayList.class, intermediaryClassWithoutBufferList); }
						});
					}

				if(attachmentOrByteBufferFieldNameMap.isEmpty()) {
					ByteBuffer slicedBigEndianReadOnlyDirectByteBuffer = ((ByteBuffer) ByteBuffer.allocateDirect(2).order(ByteOrder.BIG_ENDIAN).put((byte) 0).put((byte) 0).position(1).limit(2)).slice().asReadOnlyBuffer();
					ByteBuffer slicedBigEndianReadWriteDirectByteBuffer = ((ByteBuffer) ByteBuffer.allocateDirect(2).order(ByteOrder.BIG_ENDIAN).put((byte) 0).put((byte) 0).position(1).limit(2)).slice();
					CharBuffer bigEndianReadOnlyDirectCharBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.BIG_ENDIAN).asReadOnlyBuffer().asCharBuffer();
					CharBuffer bigEndianReadWriteDirectCharBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.BIG_ENDIAN).asCharBuffer();
					DoubleBuffer bigEndianReadOnlyDirectDoubleBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.BIG_ENDIAN).asReadOnlyBuffer().asDoubleBuffer();
					DoubleBuffer bigEndianReadWriteDirectDoubleBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.BIG_ENDIAN).asDoubleBuffer();
					FloatBuffer bigEndianReadOnlyDirectFloatBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.BIG_ENDIAN).asReadOnlyBuffer().asFloatBuffer();
					FloatBuffer bigEndianReadWriteDirectFloatBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.BIG_ENDIAN).asFloatBuffer();
					IntBuffer bigEndianReadOnlyDirectIntBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.BIG_ENDIAN).asReadOnlyBuffer().asIntBuffer();
					IntBuffer bigEndianReadWriteDirectIntBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
					LongBuffer bigEndianReadOnlyDirectLongBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.BIG_ENDIAN).asReadOnlyBuffer().asLongBuffer();
					LongBuffer bigEndianReadWriteDirectLongBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.BIG_ENDIAN).asLongBuffer();
					ShortBuffer bigEndianReadOnlyDirectShortBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.BIG_ENDIAN).asReadOnlyBuffer().asShortBuffer();
					ShortBuffer bigEndianReadWriteDirectShortBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.BIG_ENDIAN).asShortBuffer();
					ByteBuffer slicedLittleEndianReadOnlyDirectByteBuffer = ((ByteBuffer) ByteBuffer.allocateDirect(2).order(ByteOrder.LITTLE_ENDIAN).put((byte) 0).put((byte) 0).position(1).limit(2)).slice().asReadOnlyBuffer();
					ByteBuffer slicedLittleEndianReadWriteDirectByteBuffer = ((ByteBuffer) ByteBuffer.allocateDirect(2).order(ByteOrder.LITTLE_ENDIAN).put((byte) 0).put((byte) 0).position(1).limit(2)).slice();
					CharBuffer littleEndianReadOnlyDirectCharBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.LITTLE_ENDIAN).asReadOnlyBuffer().asCharBuffer();
					CharBuffer littleEndianReadWriteDirectCharBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.LITTLE_ENDIAN).asCharBuffer();
					DoubleBuffer littleEndianReadOnlyDirectDoubleBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.LITTLE_ENDIAN).asReadOnlyBuffer().asDoubleBuffer();
					DoubleBuffer littleEndianReadWriteDirectDoubleBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.LITTLE_ENDIAN).asDoubleBuffer();
					FloatBuffer littleEndianReadOnlyDirectFloatBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.LITTLE_ENDIAN).asReadOnlyBuffer().asFloatBuffer();
					FloatBuffer littleEndianReadWriteDirectFloatBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer();
					IntBuffer littleEndianReadOnlyDirectIntBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.LITTLE_ENDIAN).asReadOnlyBuffer().asIntBuffer();
					IntBuffer littleEndianReadWriteDirectIntBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
					LongBuffer littleEndianReadOnlyDirectLongBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.LITTLE_ENDIAN).asReadOnlyBuffer().asLongBuffer();
					LongBuffer littleEndianReadWriteDirectLongBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.LITTLE_ENDIAN).asLongBuffer();
					ShortBuffer littleEndianReadOnlyDirectShortBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.LITTLE_ENDIAN).asReadOnlyBuffer().asShortBuffer();
					ShortBuffer littleEndianReadWriteDirectShortBuffer = ByteBuffer.allocateDirect(1).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();

					ArrayList<Buffer> buffers = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
						buffers.add(slicedBigEndianReadOnlyDirectByteBuffer); buffers.add(slicedBigEndianReadWriteDirectByteBuffer); buffers.add(bigEndianReadOnlyDirectCharBuffer);
						buffers.add(bigEndianReadWriteDirectCharBuffer); buffers.add(bigEndianReadOnlyDirectDoubleBuffer); buffers.add(bigEndianReadWriteDirectDoubleBuffer);
						buffers.add(bigEndianReadOnlyDirectFloatBuffer); buffers.add(bigEndianReadWriteDirectFloatBuffer); buffers.add(bigEndianReadOnlyDirectIntBuffer);
						buffers.add(bigEndianReadWriteDirectIntBuffer); buffers.add(bigEndianReadOnlyDirectLongBuffer); buffers.add(bigEndianReadWriteDirectLongBuffer);
						buffers.add(bigEndianReadOnlyDirectShortBuffer); buffers.add(bigEndianReadWriteDirectShortBuffer); buffers.add(slicedLittleEndianReadOnlyDirectByteBuffer);
						buffers.add(slicedLittleEndianReadWriteDirectByteBuffer); buffers.add(littleEndianReadOnlyDirectCharBuffer); buffers.add(littleEndianReadWriteDirectCharBuffer);
						buffers.add(littleEndianReadOnlyDirectDoubleBuffer); buffers.add(littleEndianReadWriteDirectDoubleBuffer); buffers.add(littleEndianReadOnlyDirectFloatBuffer);
						buffers.add(littleEndianReadWriteDirectFloatBuffer); buffers.add(littleEndianReadOnlyDirectIntBuffer); buffers.add(littleEndianReadWriteDirectIntBuffer);
						buffers.add(littleEndianReadOnlyDirectLongBuffer); buffers.add(littleEndianReadWriteDirectLongBuffer); buffers.add(littleEndianReadOnlyDirectShortBuffer);
						buffers.add(littleEndianReadWriteDirectShortBuffer);

						for(Buffer buffer : buffers) { Class<?> bufferClass = buffer.getClass();
							if(attachmentOrByteBufferFieldMap.containsKey(bufferClass)) continue;
							Field bufferField = null; Class<?> bufferIntermediaryClass = bufferClass;
							while(bufferIntermediaryClass != null && bufferField == null) {
								for(Field field : bufferIntermediaryClass.getDeclaredFields()) { try {
									field.setAccessible(true); Object fieldValue = field.get(buffer);
									if(!(fieldValue instanceof Buffer)) continue; bufferField = field;
								} catch (Throwable t) { System.out.println("Cannot access the field " + field.getName() + " of the class " + bufferIntermediaryClass.getName()); } }
								bufferIntermediaryClass = bufferIntermediaryClass.getSuperclass();
							} if(bufferField != null) attachmentOrByteBufferFieldMap.put(bufferClass, bufferField);
						} buffersToDelete.addAll(buffers);
					} finally { Pool.returnObject(ArrayList.class, buffers); }
				}

				deallocatableBufferClassSet = new HashSet<>();
				if(javaVendor.equals("AdoptOpenJDK") || javaVendor.equals("Sun Microsystems Inc.") || javaVendor.equals("Oracle Corporation") || javaVendor.equals("The Android Project")) {
					Class<?> directByteBufferClass = null;
					String directByteBufferClassName = "java.nio.DirectByteBuffer";
					try { directByteBufferClass = Class.forName(directByteBufferClassName);
					} catch (ClassNotFoundException cnfe) { System.err.println("The class " + directByteBufferClassName + " hasn't been found while initializing the deallocator. Java vendor: " + javaVendor + " Java version: " + javaVersion); }
					if(directByteBufferClass != null) deallocatableBufferClassSet.add(directByteBufferClass);
				} else if(javaVendor.equals("Apple Inc.") || javaVendor.equals("Free Software Foundation, Inc.")) {
					Class<?> readOnlyDirectByteBufferClass = null;
					String readOnlyDirectByteBufferClassName = "java.nio.DirectByteBufferImpl.ReadOnly";
					try { readOnlyDirectByteBufferClass = Class.forName(readOnlyDirectByteBufferClassName);
					} catch (ClassNotFoundException cnfe) { System.out.println("The class " + readOnlyDirectByteBufferClassName + " hasn't been found while initializing the deallocator. Java vendor: " + javaVendor + " Java version: " + javaVersion); }
					if(readOnlyDirectByteBufferClass != null) deallocatableBufferClassSet.add(readOnlyDirectByteBufferClass);
					Class<?> readWriteDirectByteBufferClass = null;
					String readWriteDirectByteBufferClassName = "java.nio.DirectByteBufferImpl.ReadWrite";
					try { readWriteDirectByteBufferClass = Class.forName(readWriteDirectByteBufferClassName);
					} catch (ClassNotFoundException cnfe) { System.out.println("The class " + readWriteDirectByteBufferClassName + " hasn't been found while initializing the deallocator. Java vendor: " + javaVendor + " Java version: " + javaVersion); }
					if(readWriteDirectByteBufferClass != null) deallocatableBufferClassSet.add(readWriteDirectByteBufferClass);
				} else if(javaVendor.contains("Apache")) {
					Class<?> readOnlyDirectByteBufferClass = null;
					String readOnlyDirectByteBufferClassName = "java.nio.ReadOnlyDirectByteBuffer";
					try { readOnlyDirectByteBufferClass = Class.forName(readOnlyDirectByteBufferClassName);
					} catch (ClassNotFoundException cnfe) { System.out.println("The class " + readOnlyDirectByteBufferClassName + " hasn't been found while initializing the deallocator. Java vendor: " + javaVendor + " Java version: " + javaVersion); }
					if(readOnlyDirectByteBufferClass != null) deallocatableBufferClassSet.add(readOnlyDirectByteBufferClass);
					Class<?> readWriteDirectByteBufferClass = null;
					String readWriteDirectByteBufferClassName = "java.nio.ReadWriteDirectByteBuffer";
					try { readWriteDirectByteBufferClass = Class.forName(readWriteDirectByteBufferClassName);
					} catch (ClassNotFoundException cnfe) { System.out.println("The class " + readWriteDirectByteBufferClassName + " hasn't been found while initializing the deallocator. Java vendor: " + javaVendor + " Java version: " + javaVersion); }
					if(readWriteDirectByteBufferClass != null) deallocatableBufferClassSet.add(readWriteDirectByteBufferClass);
				} else if(javaVendor.equals("Jeroen Frijters")) {// TODO IKVM
				} else if(javaVendor.contains("IBM")) {// TODO J9
				}

				if(deallocatableBufferClassSet.isEmpty()) {
					ByteBuffer dummyReadWriteDirectByteBuffer = ByteBuffer.allocateDirect(1);
					Class<?> readWriteDirectByteBufferClass = dummyReadWriteDirectByteBuffer.getClass();
					deallocatableBufferClassSet.add(readWriteDirectByteBufferClass);
					buffersToDelete.add(dummyReadWriteDirectByteBuffer);
					ByteBuffer dummyReadOnlyDirectByteBuffer = ByteBuffer.allocateDirect(1).asReadOnlyBuffer();
					Class<?> readOnlyDirectByteBufferClass = dummyReadOnlyDirectByteBuffer.getClass();
					deallocatableBufferClassSet.add(readOnlyDirectByteBufferClass);
					buffersToDelete.add(dummyReadOnlyDirectByteBuffer);
				}

				if(javaVendor.equals("AdoptOpenJDK") || javaVendor.equals("Sun Microsystems Inc.") || javaVendor.equals("Oracle Corporation")) deallocator = new OracleSunOpenJdkDeallocator();
				else if(javaVendor.equals("The Android Project")) deallocator = new AndroidDeallocator();
				else if(javaVendor.equals("Apple Inc.") || javaVendor.equals("Free Software Foundation, Inc.")) deallocator = new GnuClasspathDeallocator();
				else if(javaVendor.contains("Apache")) deallocator = new ApacheHarmonyDeallocator();
				else if(javaVendor.equals("Jeroen Frijters")) /* TODO IKVM */ deallocator = null;
				else if(javaVendor.contains("IBM")) /* TODO J9 */ deallocator = null;
				else deallocator = null;

				for(Buffer bufferToDelete : buffersToDelete) deallocate(bufferToDelete);
			} finally { Pool.returnObject(ArrayList.class, buffersToDelete); Pool.returnObject(HashMap.class, attachmentOrByteBufferFieldNameMap); }
		}

		public Deallocator getDeallocator() { return deallocator; }
		public Map.Entry<Class<?>, Field>[] getAttachmentOrByteBufferFieldMap() { return attachmentOrByteBufferFieldMap.entrySet().toArray(new Map.Entry[0]); }
		public Class<?>[] getDeallocatableBufferClassSet() { return deallocatableBufferClassSet.toArray(new Class[0]); }

		public ByteBuffer findDeallocatableBuffer(Buffer buffer) {
			if(buffer == null || !buffer.isDirect()) return null;
			Class<?> bufferClass = buffer.getClass();
			Field attachmentOrByteBufferField = attachmentOrByteBufferFieldMap == null ? null : attachmentOrByteBufferFieldMap.get(bufferClass);
			Buffer attachmentBufferOrByteBuffer = null;
			if(attachmentOrByteBufferField != null) {
				Object attachedObjectOrByteBuffer;
				try { attachmentOrByteBufferField.setAccessible(true); attachedObjectOrByteBuffer = attachmentOrByteBufferField.get(buffer);
				} catch (IllegalArgumentException | IllegalAccessException iae) { attachedObjectOrByteBuffer = null; }
				if(attachedObjectOrByteBuffer instanceof Buffer) attachmentBufferOrByteBuffer = (Buffer) attachedObjectOrByteBuffer;
				else attachmentBufferOrByteBuffer = null;
			}/* else { for(Field field : bufferClass.getDeclaredFields()) { try {
				field.setAccessible(true); Object fieldValue = field.get(buffer);
				if(!(fieldValue instanceof Buffer)) continue; attachmentBufferOrByteBuffer = (Buffer) fieldValue;
			} catch(Throwable ignored) { } } }*/

			if(attachmentBufferOrByteBuffer == null) {
				if(buffer instanceof ByteBuffer && deallocatableBufferClassSet.contains(bufferClass)) return (ByteBuffer) buffer;
				else { System.out.println("No deallocatable buffer has been found for an instance of the class " + bufferClass.getName() + " whereas it is a direct NIO buffer"); return null; }
			} else return findDeallocatableBuffer(attachmentBufferOrByteBuffer);
		}

		public boolean deallocate(Buffer buffer) {
			if(deallocator == null) return false;
			ByteBuffer deallocatableBuffer = findDeallocatableBuffer(buffer);
			if(deallocatableBuffer == null) return false;
			return deallocator.run(deallocatableBuffer);
		}
	}

	public static class MemoryStack implements AutoCloseable {
		protected final BufferManager bufferManager;

		protected MemoryStack(ByteBuffer buffer) {
			this.bufferManager = new BufferManager(new BestFitAllocationAlgorithm(), buffer);
		}

		public BufferManager getBufferManager() { return bufferManager; }
		public ByteBuffer allocateByteBuffer(int size) { return BufferUtils.allocateByteBuffer(size, bufferManager); }
		public ShortBuffer allocateShortBuffer(int size) { return BufferUtils.allocateShortBuffer(size, bufferManager); }
		public CharBuffer allocateCharBuffer(int size) { return BufferUtils.allocateCharBuffer(size, bufferManager); }
		public IntBuffer allocateIntBuffer(int size) { return BufferUtils.allocateIntBuffer(size, bufferManager); }
		public LongBuffer allocateLongBuffer(int size) { return BufferUtils.allocateLongBuffer(size, bufferManager); }
		public FloatBuffer allocateFloatBuffer(int size) { return BufferUtils.allocateFloatBuffer(size, bufferManager); }
		public DoubleBuffer allocateDoubleBuffer(int size) { return BufferUtils.allocateDoubleBuffer(size, bufferManager); }
		public PointerBuffer allocatePointerBuffer(int size) { return BufferUtils.allocatePointerBuffer(size, bufferManager); }
		public void deallocate(Buffer buffer) { if(!bufferManager.isPartOfBuffer(buffer)) return; bufferManager.deallocate(buffer); }
		public void deallocate(NativeBuffer buffer) { if(!bufferManager.isPartOfBuffer(buffer.getBuffer())) return; bufferManager.deallocate(buffer.getBuffer()); }
		public ByteBuffer _allocateByteBuffer(int size) { return BufferUtils._allocateByteBuffer(size, bufferManager); }
		public ShortBuffer _allocateShortBuffer(int size) { return BufferUtils._allocateShortBuffer(size, bufferManager); }
		public CharBuffer _allocateCharBuffer(int size) { return BufferUtils._allocateCharBuffer(size, bufferManager); }
		public IntBuffer _allocateIntBuffer(int size) { return BufferUtils._allocateIntBuffer(size, bufferManager); }
		public LongBuffer _allocateLongBuffer(int size) { return BufferUtils._allocateLongBuffer(size, bufferManager); }
		public FloatBuffer _allocateFloatBuffer(int size) { return BufferUtils._allocateFloatBuffer(size, bufferManager); }
		public DoubleBuffer _allocateDoubleBuffer(int size) { return BufferUtils._allocateDoubleBuffer(size, bufferManager); }
		public PointerBuffer _allocatePointerBuffer(int size) { return BufferUtils._allocatePointerBuffer(size, bufferManager); }
		public void _deallocate(Buffer buffer) { deallocate(buffer); Pool.returnObject(buffer.getClass(), buffer); }
		public void _deallocate(NativeBuffer buffer) { deallocate(buffer); Pool.returnObject(buffer.getClass(), buffer); }

		public ByteBuffer allocateASCII(CharSequence text, boolean nullTerminated) {
			int len = StringUtils.lengthASCII(text, nullTerminated); ByteBuffer result = allocateByteBuffer(len);
			StringUtils.encodeASCII(text, nullTerminated, __getAddress(result)); return result;
		}
		public ByteBuffer allocateASCII(CharSequence text) { return allocateASCII(text, true); }
		public ByteBuffer allocateUTF8(CharSequence text, boolean nullTerminated) {
			int len = StringUtils.lengthUTF8(text, nullTerminated); ByteBuffer result = allocateByteBuffer(len);
			StringUtils.encodeUTF8(text, nullTerminated, __getAddress(result)); return result;
		}
		public ByteBuffer allocateUTF8(CharSequence text) { return allocateUTF8(text, true); }
		public ByteBuffer allocateUTF16(CharSequence text, boolean nullTerminated) {
			int len = StringUtils.lengthUTF16(text, nullTerminated); ByteBuffer result = allocateByteBuffer(len);
			StringUtils.encodeUTF16(text, nullTerminated, __getAddress(result)); return result;
		}
		public ByteBuffer allocateUTF16(CharSequence text) { return allocateUTF16(text, true); }
		public ByteBuffer _allocateASCII(CharSequence text, boolean nullTerminated) {
			int len = StringUtils.lengthASCII(text, nullTerminated); ByteBuffer result = _allocateByteBuffer(len);
			StringUtils.encodeASCII(text, nullTerminated, __getAddress(result)); return result;
		}
		public ByteBuffer _allocateASCII(CharSequence text) { return allocateUTF16(text, true); }
		public ByteBuffer _allocateUTF8(CharSequence text, boolean nullTerminated) {
			int len = StringUtils.lengthUTF8(text, nullTerminated); ByteBuffer result = _allocateByteBuffer(len);
			StringUtils.encodeUTF8(text, nullTerminated, __getAddress(result)); return result;
		}
		public ByteBuffer _allocateUTF8(CharSequence text) { return allocateUTF16(text, true); }
		public ByteBuffer _allocateUTF16(CharSequence text, boolean nullTerminated) {
			int len = StringUtils.lengthUTF16(text, nullTerminated); ByteBuffer result = _allocateByteBuffer(len);
			StringUtils.encodeUTF16(text, nullTerminated, __getAddress(result)); return result;
		}
		public ByteBuffer _allocateUTF16(CharSequence text) { return allocateUTF16(text, true); }

		// If bufferManager is shared, this should not be shared accross threads, because it'll messed up the deallocation
		@Override public void close() { BufferUtils.deallocate(bufferManager.getBuffer()); }
	}

	protected interface FBufferUtilsImplementation extends FutureJavaUtils.FutureJavaImplementation {
		long getBufferMarkOffset() throws Exception;
		long getBufferPositionOffset() throws Exception;
		long getBufferLimitOffset() throws Exception;
		long getBufferCapacityOffset() throws Exception;
		long getBufferAddressOffset() throws Exception;
		long getAbstractBufferBufferOffset() throws Exception;
		long getAbstractBufferElementSizeOffset() throws Exception;
		long getAbstractBufferCapacityOffset() throws Exception;
		long getAbstractBufferPositionOffset() throws Exception;
	}
}
