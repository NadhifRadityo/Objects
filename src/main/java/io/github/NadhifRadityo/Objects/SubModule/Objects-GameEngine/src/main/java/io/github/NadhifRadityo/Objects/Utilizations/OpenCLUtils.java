package io.github.NadhifRadityo.Objects.Utilizations;

import com.jogamp.common.nio.AbstractBuffer;
import com.jogamp.opencl.CLErrorHandler;
import com.sun.istack.internal.Nullable;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.VecN;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.CLContext;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.OpenCLNativeHolder;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.OpenCLNativeHolderProvider;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Platform;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.NativeBuffer;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.PointerBuffer;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Proxy;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Write1TimeProxy;
import io.github.NadhifRadityo.Objects.Pool.BuiltInPool.PointerBufferSkeletonPool;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CL10GL;
import org.lwjgl.opencl.CL11;
import org.lwjgl.opencl.CL12;
import org.lwjgl.opencl.CL12GL;
import org.lwjgl.opencl.CL20;
import org.lwjgl.opencl.CL21;
import org.lwjgl.opencl.CL22;
import org.lwjgl.opencl.CLContextCallback;
import org.lwjgl.opencl.CLContextCallbackI;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.Pointer;
import sun.misc.Unsafe;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.CLContext.newException;
import static org.lwjgl.system.MemoryUtil.NULL;

@SuppressWarnings("ALL")
public class OpenCLUtils extends GenTypeUtils {
	// STATIC
	private static int getIntThenDestroy(IntBuffer buffer, int index) { try { return buffer.get(index); } finally { deallocate(buffer); } }
	private static void deallocate(Buffer buffer) { BufferUtils.deallocate(buffer); }

	protected static final ThreadLocal<WeakReference<CallBuffer>> errorBuffer = new ThreadLocal<>();
	public static CallBuffer getCallBuffer() {
		CallBuffer result = errorBuffer.get() == null || errorBuffer.get().get() == null ? null : errorBuffer.get().get();
		if(result == null) { result = new CallBuffer(); errorBuffer.set(new WeakReference<>(result)); } result.open(); return result;
	}

	protected static final ThreadLocal<WeakReference<Object[]>> tempArgs5 = new ThreadLocal<>();
	protected static final ThreadLocal<WeakReference<Object[]>> tempArgs6 = new ThreadLocal<>();
	protected static Object[] getTempArgs5() {
		Object[] result = tempArgs5.get() == null || tempArgs5.get().get() == null ? null : tempArgs5.get().get();
		if(result == null) { result = new Object[5]; tempArgs5.set(new WeakReference<>(result)); } return result;
	}
	protected static Object[] getTempArgs6() {
		Object[] result = tempArgs6.get() == null || tempArgs6.get().get() == null ? null : tempArgs6.get().get();
		if(result == null) { result = new Object[6]; tempArgs6.set(new WeakReference<>(result)); } return result;
	}

	public static void checkError(IntBuffer errorBuffer) { try {
		errorBuffer.rewind();
		while(errorBuffer.remaining() > 0) {
			int status = errorBuffer.get(); if(status == 0) continue;
			throw newException(String.format("OpenCL error [%d]", status));
		}
	} finally { BufferUtils.empty(errorBuffer, 0, errorBuffer.capacity()); errorBuffer.rewind(); } }

	public static <ID extends Number, PLATFORM extends Platform<ID>> PLATFORM[] getPlatforms(ReferencedCallback.VoidReferencedCallback clGetPlatformIDs, ReferencedCallback<PLATFORM> constructPlatform, Class<PLATFORM> clazz) {
		try(BufferUtils.MemoryStack stack = BufferUtils.stack()) {
			IntBuffer platformCount = stack.allocateIntBuffer(1);
			clGetPlatformIDs.get(null, platformCount);
			PointerBuffer platformIDs = stack.allocatePointerBuffer(platformCount.get(0));
			clGetPlatformIDs.get(platformIDs, null);
			platformIDs.rewind();

			PLATFORM[] result = (PLATFORM[]) Array.newInstance(clazz, platformIDs.capacity());
			for(int i = 0; i < result.length; i++) result[i] = constructPlatform.get(platformIDs.get(i));
			return result;
		}
	}
	public static <ID extends Number, PLATFORM extends Platform<ID>> PLATFORM[] getPlatforms(PLATFORM[] platforms, Class<PLATFORM> clazz, ReferencedCallback.BooleanReferencedCallback... filters) {
		ArrayList<Platform<ID>> result = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		try {
			for(Platform<ID> platform : platforms) {
				boolean accepted = true;
				for(ReferencedCallback.BooleanReferencedCallback filter : filters) if(!filter.get(platform)) { accepted = false; break; }
				if(!accepted) continue; result.add(platform);
			}
			return result.toArray((PLATFORM[]) Array.newInstance(clazz, 0));
		} finally { Pool.returnObject(ArrayList.class, result); }
	}
	public static <ID extends Number, PLATFORM extends Platform<ID>> PLATFORM getLatestPlatform(PLATFORM[] platforms, Class<PLATFORM> clazz, ReferencedCallback.BooleanReferencedCallback... filters) {
		filters = filters != null ? Arrays.copyOf(filters, filters.length + 1) : new ReferencedCallback.BooleanReferencedCallback[1];
		System.arraycopy(filters, 0, filters, 1, filters.length - 1);
		Proxy<PLATFORM> result = Pool.tryBorrow(Pool.getPool(Proxy.class));
		filters[0] = obj -> {
			PLATFORM platform = (PLATFORM) obj[0];
			if(result.get() != null && platform.getVersion().compareTo(result.get().getVersion()) <= 0) return false;
			result.set(platform); return true;
		};
		getPlatforms(platforms, clazz, filters);
		try { return result.get(); } finally { Pool.returnObject(Proxy.class, result); }
	}
	public static <ID extends Number, PLATFORM extends Platform<ID>> PLATFORM getLatestPlatform(PLATFORM[] platforms, Class<PLATFORM> clazz) { return getLatestPlatform(platforms, clazz, (ReferencedCallback.BooleanReferencedCallback[]) null); }
	public static <ID extends Number, PLATFORM extends Platform<ID>> PLATFORM getDefaultPlatform(PLATFORM[] platforms, Class<PLATFORM> clazz) { return getLatestPlatform(platforms, clazz); }

	public static <ID extends Number> void changeContext(CLContext<ID> context, OpenCLNativeHolder<ID>... objects) {
		if(!PrivilegedUtils.isRunningOnPrivileged()) throw new IllegalArgumentException();
		Method METHOD_removeInstance;
		Method METHOD_addInstance;
		Method METHOD_getInstance;
		Field FIELD_clHolder;
		try {
			METHOD_removeInstance = OpenCLNativeHolder.class.getDeclaredMethod("removeInstance", OpenCLNativeHolder.class);
			METHOD_addInstance = OpenCLNativeHolder.class.getDeclaredMethod("addInstance", OpenCLNativeHolder.class, CLContext.class);
			METHOD_getInstance = OpenCLNativeHolder.class.getDeclaredMethod("getInstance");
			FIELD_clHolder = OpenCLNativeHolderProvider.class.getDeclaredField("clHolder");
			METHOD_removeInstance.setAccessible(true);
			METHOD_addInstance.setAccessible(true);
			METHOD_getInstance.setAccessible(true);
			FIELD_clHolder.setAccessible(true);
		} catch(Exception e) { throw new Error(e); }
		for(OpenCLNativeHolder<ID> object : objects) {
			ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> {
				METHOD_removeInstance.invoke(null, object);
				Pool.returnObject(Write1TimeProxy.class, FIELD_clHolder.get(METHOD_getInstance.invoke(object)));
				ClassUtils.setFinal(METHOD_getInstance.invoke(object), FIELD_clHolder, Pool.tryBorrow(Pool.getPool(Write1TimeProxy.class)));
				((Write1TimeProxy<OpenCLNativeHolder.CLHolder<ID>>) FIELD_clHolder.get(METHOD_getInstance.invoke(object))).set((OpenCLNativeHolder.CLHolder<ID>) METHOD_addInstance.invoke(null, object, context));
			});
		}
	}

	// TODO Cleanup
	public static class CallBuffer implements AutoCloseable {
		protected final IntBuffer errorBuffer;
		protected BufferUtils.MemoryStack stack;
		protected final int[] stackOriMap;

		protected int[] errorStatus;
		protected int errorStatusOffset;
		protected boolean capacityEnsured;

		protected int tempBuffersIndex;
		protected Object[] tempBuffers;

		public CallBuffer() {
			this.errorBuffer = BufferUtils.createIntBuffer(8);
			this.stack = BufferUtils.stack(128);
			this.stackOriMap = new int[] { stack.getBufferManager().getTotalSize() };
		}

		public CallBuffer as(int[] status, int offset) {
			this.errorStatus = status;
			this.errorStatusOffset = offset;
			return this;
		}

		public IntBuffer getErrorBuffer() { return errorBuffer; }
		public BufferUtils.MemoryStack getStack() { return stack; }
		public CallBuffer putError(int i) { errorBuffer.put(i); return this; }
		public CallBuffer putError(int index, int i) { errorBuffer.put(index, i); return this; }
		public void checkError() { OpenCLUtils.checkError(errorBuffer); }
		public void consumeError(int[] status, int off) {
			if(status == null) return;
			int len = Math.min(status.length, errorBuffer.capacity()) * Integer.BYTES;
			UnsafeUtils.__copyMemory(BufferUtils.__getAddress(errorBuffer), status, Unsafe.ARRAY_INT_BASE_OFFSET, len);
			BufferUtils.empty(errorBuffer, 0, len);
		}
		public void consumeError(int[] status) {
			consumeError(status, 0);
		}

		public void ensureCapacity(int capacity) {
			if(capacityEnsured) throw new IllegalArgumentException("Can only be ensured one time");
			this.capacityEnsured = true;
			if(stack.getBufferManager().getTotalSize() >= capacity) return;
			stack.close();
			stack = BufferUtils.stack(capacity);
			stackOriMap[0] = stack.getBufferManager().getTotalSize();
		}

		protected void checkTemp(int add) {
			if(tempBuffers == null) { tempBuffers = new Object[Math.min(10, add)]; return; }
			if(tempBuffersIndex + add > tempBuffers.length)
				tempBuffers = Arrays.copyOf(tempBuffers, tempBuffers.length + 10);
		}

		public IntBuffer as(IVecN data) {
			assertEnsuredCapacity(); checkTemp(1);
			IntBuffer result = stack._allocateIntBuffer(data.size);
			tempBuffers[tempBuffersIndex++] = result;
			toBuffer(data, result); return result;
		}
		public LongBuffer as(LVecN data) {
			assertEnsuredCapacity(); checkTemp(1);
			LongBuffer result = stack._allocateLongBuffer(data.size);
			tempBuffers[tempBuffersIndex++] = result;
			toBuffer(data, result); return result;
		}
		public ShortBuffer as(SVecN data) {
			assertEnsuredCapacity(); checkTemp(1);
			ShortBuffer result = stack._allocateShortBuffer(data.size);
			tempBuffers[tempBuffersIndex++] = result;
			toBuffer(data, result); return result;
		}
		public FloatBuffer as(FVecN data) {
			assertEnsuredCapacity(); checkTemp(1);
			FloatBuffer result = stack._allocateFloatBuffer(data.size);
			tempBuffers[tempBuffersIndex++] = result;
			toBuffer(data, result); return result;
		}
		public DoubleBuffer as(VecN data) {
			assertEnsuredCapacity(); checkTemp(1);
			DoubleBuffer result = stack._allocateDoubleBuffer(data.size);
			tempBuffers[tempBuffersIndex++] = result;
			toBuffer(data, result); return result;
		}

		public IntBuffer as(int data) {
			assertEnsuredCapacity(); checkTemp(1);
			IntBuffer result = stack._allocateIntBuffer(1);
			tempBuffers[tempBuffersIndex++] = result;
			result.put(0, data); return result;
		}
		public LongBuffer as(long data) {
			assertEnsuredCapacity(); checkTemp(1);
			LongBuffer result = stack._allocateLongBuffer(1);
			tempBuffers[tempBuffersIndex++] = result;
			result.put(0, data); return result;
		}
		public ShortBuffer as(short data) {
			assertEnsuredCapacity(); checkTemp(1);
			ShortBuffer result = stack._allocateShortBuffer(1);
			tempBuffers[tempBuffersIndex++] = result;
			result.put(0, data); return result;
		}
		public FloatBuffer as(float data) {
			assertEnsuredCapacity(); checkTemp(1);
			FloatBuffer result = stack._allocateFloatBuffer(1);
			tempBuffers[tempBuffersIndex++] = result;
			result.put(0, data); return result;
		}
		public DoubleBuffer as(double data) {
			assertEnsuredCapacity(); checkTemp(1);
			DoubleBuffer result = stack._allocateDoubleBuffer(1);
			tempBuffers[tempBuffersIndex++] = result;
			result.put(0, data); return result;
		}
		public CharBuffer as(char data) {
			assertEnsuredCapacity(); checkTemp(1);
			CharBuffer result = stack._allocateCharBuffer(1);
			tempBuffers[tempBuffersIndex++] = result;
			result.put(0, data); return result;
		}

		// Todo Pool leaked here
		public org.lwjgl.PointerBuffer asLwjglPointer(LVecN data) {
			assertEnsuredCapacity(); checkTemp(1);
			PointerBuffer result = stack._allocatePointerBuffer(data.size);
			tempBuffers[tempBuffersIndex++] = result;
			toBuffer(data, result.getBuffer());
			return wrapLwjglPointer(result);
		}
		public com.jogamp.common.nio.PointerBuffer asJogampPointer(LVecN data) {
			assertEnsuredCapacity(); checkTemp(1);
			PointerBuffer result = stack._allocatePointerBuffer(data.size);
			tempBuffers[tempBuffersIndex++] = result;
			toBuffer(data, result.getBuffer());
			return wrapJogampPointer(result);
		}

		public org.lwjgl.PointerBuffer wrapLwjglPointer(@Nullable PointerBuffer buffer) { checkTemp(1);
			org.lwjgl.PointerBuffer result = Pool.tryBorrow(Pool.getPool(org.lwjgl.PointerBuffer.class));
			tempBuffers[tempBuffersIndex++] = result; if(buffer == null) return PointerBufferSkeletonPool.LWJGLPointerBufferSkeletonPool.pointTo(result, 0, 0, null);
			return LWJGL_wrap(org.lwjgl.PointerBuffer.class, result, BufferUtils.__getAddress(buffer), buffer.capacity(), null);
		}
		public com.jogamp.common.nio.PointerBuffer wrapJogampPointer(@Nullable PointerBuffer buffer) { checkTemp(1);
			com.jogamp.common.nio.PointerBuffer result = Pool.tryBorrow(Pool.getPool(com.jogamp.common.nio.PointerBuffer.class));
			tempBuffers[tempBuffersIndex++] = result; if(buffer == null) return PointerBufferSkeletonPool.JogampPointerBufferSkeletonPool.pointTo(result, 0, 0, result.getBuffer());
			BufferUtils.__pointTo(result.getBuffer(), BufferUtils.__getAddress(buffer.getBuffer()), buffer.getBuffer().capacity());
			return JOGAMP_wrap(com.jogamp.common.nio.PointerBuffer.class, result, PointerBuffer.ELEMENT_SIZE, buffer.capacity(), result.getBuffer());
		}

		public org.lwjgl.PointerBuffer pointLwjgl(Buffer buffer) {
			if(buffer == null) return null;
			assertEnsuredCapacity(); checkTemp(2);
			ByteBuffer temp = stack._allocateByteBuffer(PointerBuffer.ELEMENT_SIZE);
			org.lwjgl.PointerBuffer result = Pool.tryBorrow(Pool.getPool(org.lwjgl.PointerBuffer.class));
			tempBuffers[tempBuffersIndex++] = temp; tempBuffers[tempBuffersIndex++] = result;
			PointerBufferSkeletonPool.LWJGLPointerBufferSkeletonPool.pointTo(result, BufferUtils.__getAddress(temp), 1, null);
			result.put(0, BufferUtils.__getAddress(buffer)); return result;
		}
		public org.lwjgl.PointerBuffer pointLwjgl(NativeBuffer buffer) {
			if(buffer == null) return null;
			assertEnsuredCapacity(); checkTemp(2);
			ByteBuffer temp = stack._allocateByteBuffer(PointerBuffer.ELEMENT_SIZE);
			org.lwjgl.PointerBuffer result = Pool.tryBorrow(Pool.getPool(org.lwjgl.PointerBuffer.class));
			tempBuffers[tempBuffersIndex++] = temp; tempBuffers[tempBuffersIndex++] = result;
			PointerBufferSkeletonPool.LWJGLPointerBufferSkeletonPool.pointTo(result, BufferUtils.__getAddress(temp), 1, null);
			result.put(0, BufferUtils.__getAddress(buffer)); return result;
		}
		public com.jogamp.common.nio.PointerBuffer pointJogamp(Buffer buffer) {
			if(buffer == null) return null;
			assertEnsuredCapacity(); checkTemp(2);
			ByteBuffer temp = stack._allocateByteBuffer(PointerBuffer.ELEMENT_SIZE);
			com.jogamp.common.nio.PointerBuffer result = Pool.tryBorrow(Pool.getPool(com.jogamp.common.nio.PointerBuffer.class));
			tempBuffers[tempBuffersIndex++] = temp; tempBuffers[tempBuffersIndex++] = result;
			PointerBufferSkeletonPool.JogampPointerBufferSkeletonPool.pointTo(result, BufferUtils.__getAddress(temp), 1, result.getBuffer());
			result.put(0, BufferUtils.__getAddress(buffer)); return result;
		}
		public com.jogamp.common.nio.PointerBuffer pointJogamp(NativeBuffer buffer) {
			if(buffer == null) return null;
			assertEnsuredCapacity(); checkTemp(2);
			ByteBuffer temp = stack._allocateByteBuffer(PointerBuffer.ELEMENT_SIZE);
			com.jogamp.common.nio.PointerBuffer result = Pool.tryBorrow(Pool.getPool(com.jogamp.common.nio.PointerBuffer.class));
			tempBuffers[tempBuffersIndex++] = temp; tempBuffers[tempBuffersIndex++] = result;
			PointerBufferSkeletonPool.JogampPointerBufferSkeletonPool.pointTo(result, BufferUtils.__getAddress(temp), 1, result.getBuffer());
			result.put(0, BufferUtils.__getAddress(buffer)); return result;
		}

		protected void assertEnsuredCapacity() {
			if(!capacityEnsured) throw new IllegalArgumentException("Capacity must be ensured");
		}

		public void open() {
			BufferUtils.empty(stack.getBufferManager().getBuffer(), 0, stack.getBufferManager().getTotalSize());
		}
		public void close() {
			try {
				if(errorStatus != null) consumeError(errorStatus, errorStatusOffset); checkError();
				if(tempBuffers != null) for(int i = 0; i < tempBuffersIndex; i++) {
					Object tempBuffer = tempBuffers[i]; if(tempBuffer == null) continue; tempBuffers[i] = null;
					if(tempBuffer instanceof Buffer) stack._deallocate((Buffer) tempBuffer);
					else if(tempBuffer instanceof NativeBuffer) stack._deallocate((NativeBuffer) tempBuffer);
					else Pool.returnObject(tempBuffer.getClass(), tempBuffer);
				}
			} finally {
				this.errorBuffer.rewind();
				this.stack.getBufferManager().updateMap(stackOriMap);
				this.errorStatus = null;
				this.errorStatusOffset = 0;
				this.capacityEnsured = false;
			}
		}
	}

	// LWJGL
	private static final Class[] LWJGL_classToSeek = new Class[] { CL22.class, CL21.class, CL20.class, CL12GL.class, CL12.class, CL11.class, CL10GL.class, CL10.class };
	private static final Class[] LWJGL_paramParentNull = new Class[] { long.class, int.class, long.class, long.class, long.class };
	private static final Class[] LWJGL_paramParentNotNull = new Class[] { long.class, long.class, int.class, long.class, long.class, long.class };
	public static int LWJGL_getInfo(String _methodN, long object, long parent, int key/*, long valueSize*/, Buffer value, Buffer valueSizeRet) { try {
		if(!_methodN.startsWith("n")) _methodN = "n" + _methodN; String methodN = _methodN;
		Class[] paramClass = parent == NULL ? LWJGL_paramParentNull : LWJGL_paramParentNotNull;
		Method method = null;
		for(Class classToSeek : LWJGL_classToSeek) {
			method = ExceptionUtils.doSilentThrowsReferencedCallback(false, (args) -> ClassUtils.getPublicMethod(classToSeek, methodN, paramClass));
			if(method != null) break;
		} if(method == null) throw new NoSuchMethodException(methodN);
		int size = value != null ? value.capacity() * BufferUtils.getElementSize(value) : 0;
		long valueAddress = value != null ? BufferUtils.__getAddress(value) : 0;
		long valueSizeRetAddress = valueSizeRet != null ? BufferUtils.__getAddress(valueSizeRet) : 0;
		Object[] tempArgs;
		if(parent == NULL) { tempArgs = getTempArgs5(); tempArgs[0] = object; tempArgs[1] = key; tempArgs[2] = size; tempArgs[3] = valueAddress; tempArgs[4] = valueSizeRetAddress;
		} else { tempArgs = getTempArgs6(); tempArgs[0] = object; tempArgs[1] = parent; tempArgs[2] = key; tempArgs[3] = size; tempArgs[4] = valueAddress; tempArgs[5] = valueSizeRetAddress; }
		return (int) method.invoke(null, tempArgs);
	} catch(Exception e) { throw new Error(e); } }

	private static final long LWJGL_ADDRESS;
	private static final long LWJGL_BUFFER_MARK;
	private static final long LWJGL_BUFFER_POSITION;
	private static final long LWJGL_BUFFER_LIMIT;
	private static final long LWJGL_BUFFER_CAPACITY;
	private static final long LWJGL_BUFFER_CONTAINER;
	static {
		Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		try {
			LWJGL_ADDRESS = unsafe.objectFieldOffset(Pointer.Default.class.getDeclaredField("address"));
			LWJGL_BUFFER_MARK = unsafe.objectFieldOffset(CustomBuffer.class.getDeclaredField("mark"));
			LWJGL_BUFFER_POSITION = unsafe.objectFieldOffset(CustomBuffer.class.getDeclaredField("position"));
			LWJGL_BUFFER_LIMIT = unsafe.objectFieldOffset(CustomBuffer.class.getDeclaredField("limit"));
			LWJGL_BUFFER_CAPACITY = unsafe.objectFieldOffset(CustomBuffer.class.getDeclaredField("capacity"));
			LWJGL_BUFFER_CONTAINER = unsafe.objectFieldOffset(CustomBuffer.class.getDeclaredField("container"));
		} catch(Throwable e) { throw new Error(e); }
	}
	private static <T extends CustomBuffer<?>> T LWJGL_wrap(Class<? extends T> clazz, T buffer, long address, int size, ByteBuffer container) {
		Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		if(buffer == null) try { buffer = (T) unsafe.allocateInstance(clazz);
		} catch(InstantiationException e) { throw new UnsupportedOperationException(e); }
		if(container != null) BufferUtils.__pointTo(container, address, size * PointerBuffer.ELEMENT_SIZE);
		unsafe.putLong(buffer, LWJGL_ADDRESS, address);
		unsafe.putInt(buffer, LWJGL_BUFFER_MARK, -1);
		unsafe.putInt(buffer, LWJGL_BUFFER_POSITION, 0);
		unsafe.putInt(buffer, LWJGL_BUFFER_LIMIT, size);
		unsafe.putInt(buffer, LWJGL_BUFFER_CAPACITY, size);
		unsafe.putObject(buffer, LWJGL_BUFFER_CONTAINER, container);
		return buffer;
	}
	public static org.lwjgl.PointerBuffer LWJGL_createPointer(PointerBuffer buffer) {
		return buffer == null ? null : LWJGL_wrap(org.lwjgl.PointerBuffer.class, null, BufferUtils.__getAddress(buffer), buffer.capacity(), null);
	}

	public static long LWJGL_getUInt32Long(String method, long object, long parent, int key, LongBuffer buffer, int[] status, int statusOff) {
		boolean deallocateBuffer = false; try(CallBuffer callBuffer = getCallBuffer().as(status, statusOff)) {
			if(buffer == null || buffer.capacity() < 1) { deallocateBuffer = true; buffer = BufferUtils.allocateLongBuffer(1); }
			callBuffer.putError(LWJGL_getInfo(method, object, parent, key/*, 4*/, buffer, null)); return buffer.get(0) & 0xFFFFFFFFL;
		} finally { if(deallocateBuffer) deallocate(buffer); }
	}
	public static long LWJGL_getUInt32Long(String method, long object, int key, LongBuffer buffer, int[] status, int statusOff) { return LWJGL_getUInt32Long(method, object, NULL, key, buffer, status, statusOff); }
	public static long LWJGL_getLong(String method, long object, long parent, int key, LongBuffer buffer, int[] status, int statusOff) {
		boolean deallocateBuffer = false; try(CallBuffer callBuffer = getCallBuffer().as(status, statusOff)) {
			if(buffer == null || buffer.capacity() < 1) { deallocateBuffer = true; buffer = BufferUtils.allocateLongBuffer(1); }
			callBuffer.putError(LWJGL_getInfo(method, object, parent, key/*, 8*/, buffer, null)); return buffer.get(0);
		} finally { if(deallocateBuffer) deallocate(buffer); }
	}
	public static long LWJGL_getLong(String method, long object, int key, LongBuffer buffer, int[] status, int statusOff) { return LWJGL_getLong(method, object, NULL, key, buffer, status, statusOff); }
	public static String LWJGL_getString(String method, long object, long parent, int key, ByteBuffer buffer, int[] status, int statusOff) {
		boolean deallocateBuffer = false; try(CallBuffer callBuffer = getCallBuffer().as(status, statusOff)) {
			if(buffer == null || buffer.capacity() < 8) { deallocateBuffer = true; buffer = BufferUtils.allocateByteBuffer(512); }
			callBuffer.putError(LWJGL_getInfo(method, object, parent, key/*, 0*/, null, buffer));
			int stringBytesLen = (int) buffer.getLong(0);
			if(buffer.capacity() < stringBytesLen) { if(deallocateBuffer) deallocate(buffer); deallocateBuffer = true; buffer = BufferUtils.allocateByteBuffer(stringBytesLen); }
			callBuffer.putError(LWJGL_getInfo(method, object, parent, key/*, buffer.capacity()*/, buffer, null));
			return ByteUtils.toZeroTerminatedString(buffer);
		} finally { if(deallocateBuffer) deallocate(buffer); }
	}
	public static String LWJGL_getString(String method, long object, int key, ByteBuffer buffer, int[] status, int statusOff) { return LWJGL_getString(method, object, NULL, key, buffer, status, statusOff); }
	public static long[] LWJGL_getLongs(String method, long object, long parent, int key, int n, ByteBuffer buffer, int[] status, int statusOff) {
		boolean deallocateBuffer = false; int size = n * (UnsafeUtils.__is32Bit() ? 4 : 8); try(CallBuffer callBuffer = getCallBuffer().as(status, statusOff)) {
			if(buffer == null || buffer.capacity() < size) { deallocateBuffer = true; buffer = BufferUtils.allocateByteBuffer(size); }
			callBuffer.putError(LWJGL_getInfo(method, object, parent, key/*, buffer.capacity()*/, buffer, null)); long[] result = new long[n];
			if(UnsafeUtils.__is32Bit()) { for(int i = 0; i < result.length; i++) result[i] = buffer.getInt();
			} else { for(int i = 0; i < result.length; i++) result[i] = buffer.getLong(); }
			return result;
		} finally { if(deallocateBuffer) deallocate(buffer); }
	}
	public static long[] LWJGL_getLongs(String method, long object, int key, int n, ByteBuffer buffer, int[] status, int statusOff) { return LWJGL_getLongs(method, object, NULL, key, n, buffer, status, statusOff); }
	public static int[] LWJGL_getInts(String method, long object, long parent, int key, int n, ByteBuffer buffer, int[] status, int statusOff) {
		long[] result = LWJGL_getLongs(method, object, parent, key, n, buffer, status, statusOff); int[] array = new int[result.length];
		for(int i = 0; i < array.length; i++) array[i] = (int) result[i]; return array;
	}
	public static int[] LWJGL_getInts(String method, long object, int key, int n, ByteBuffer buffer, int[] status, int statusOff) { return LWJGL_getInts(method, object, NULL, key, n, buffer, status, statusOff); }

	public static long LWJGL_createContext(long platform, long[] devices, Map<Integer, Long> properties, CLContextCallbackI callbackI, int[] status, int statusOff) {
		try(BufferUtils.MemoryStack stack = BufferUtils.allocateStack(); CallBuffer callBuffer = getCallBuffer().as(status, statusOff)) {
			PointerBuffer _deviceBuffer = stack.allocatePointerBuffer(devices.length);
			org.lwjgl.PointerBuffer deviceBuffer = callBuffer.wrapLwjglPointer(_deviceBuffer);
			for(int i = 0; i < devices.length; i++) deviceBuffer.put(i, devices[i]);
			PointerBuffer _propertiesBuffer = stack.allocatePointerBuffer(3 + (properties != null ? properties.size() * 2 : 0));
			org.lwjgl.PointerBuffer propertiesBuffer = callBuffer.wrapLwjglPointer(_propertiesBuffer);
			if(properties != null) for(Map.Entry<Integer, Long> entry : properties.entrySet())
				propertiesBuffer.put(entry.getKey()).put(entry.getValue());
			propertiesBuffer.put(CL10.CL_CONTEXT_PLATFORM).put(platform).put(0).rewind();
			return CL10.clCreateContext(propertiesBuffer, deviceBuffer, CLContextCallback.create(callbackI), 0, callBuffer.getErrorBuffer());
		}
	}
	public static long LWJGL_createContext(long platform, long[] devices, Map<Integer, Long> properties, int[] status, int statusOff) { return LWJGL_createContext(platform, devices, properties, (errinfo, private_info, cb, user_data) -> {}, status, statusOff); }
	public static long LWJGL_createContext(long platform, long[] devices, int[] status, int statusOff) { return LWJGL_createContext(platform, devices, null, status, statusOff); }

	// JOGAMP
	private static final Class[] JOGAMP_paramParentNull = new Class[] { long.class, int.class, long.class, Buffer.class, com.jogamp.common.nio.PointerBuffer.class };
	private static final Class[] JOGAMP_paramParentNotNull = new Class[] { long.class, long.class, int.class, long.class, Buffer.class, com.jogamp.common.nio.PointerBuffer.class };
	private static Object JOGAMP_callMethod(com.jogamp.opencl.llb.CL cl, String methodN, Class[] paramClasses, Object... args) { try {
		if(paramClasses == null) paramClasses = ClassUtils.toClass(args);
		if(paramClasses.length > args.length) args = Arrays.copyOf(args, paramClasses.length);
		if(paramClasses.length < args.length) throw new IllegalArgumentException();
		Method method = ClassUtils.getPublicMethod(cl.getClass(), methodN, paramClasses);
		return method.invoke(cl, args);
	} catch(Exception e) { throw new Error(e); } }
	public static int JOGAMP_getInfo(com.jogamp.opencl.llb.CL cl, String methodN, long object, long parent, int key/*, long valueSize*/, Buffer value, Buffer valueSizeRet) {
		int size = value != null ? value.capacity() * BufferUtils.getElementSize(value) : 0; Object[] tempArgs;
		com.jogamp.common.nio.PointerBuffer _valueSizeRet = valueSizeRet != null ? com.jogamp.common.nio.PointerBuffer.wrap(BufferUtils.__pointTo(BufferUtils.__getAddress(valueSizeRet), valueSizeRet.capacity() * BufferUtils.getElementSize(valueSizeRet))) : null;
		if(parent == NULL) { tempArgs = getTempArgs5(); tempArgs[0] = object; tempArgs[1] = key; tempArgs[2] = size; tempArgs[3] = value; tempArgs[4] = _valueSizeRet;
		} else { tempArgs = getTempArgs6(); tempArgs[0] = object; tempArgs[1] = parent; tempArgs[2] = key; tempArgs[3] = size; tempArgs[4] = value; tempArgs[5] = _valueSizeRet; }
		return (int) JOGAMP_callMethod(cl, methodN, parent == NULL ? JOGAMP_paramParentNull : JOGAMP_paramParentNotNull, tempArgs);
	}

	private static final long JOGAMP_ABSTRACTBUFFER_ELEMENTSIZE;
	private static final long JOGAMP_ABSTRACTBUFFER_CAPACITY;
	private static final long JOGAMP_ABSTRACTBUFFER_POSITION;
	private static final long JOGAMP_ABSTRACTBUFFER_BUFFER;
	static {
		Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		try {
			JOGAMP_ABSTRACTBUFFER_ELEMENTSIZE = unsafe.objectFieldOffset(AbstractBuffer.class.getDeclaredField("elementSize"));
			JOGAMP_ABSTRACTBUFFER_CAPACITY = unsafe.objectFieldOffset(AbstractBuffer.class.getDeclaredField("capacity"));
			JOGAMP_ABSTRACTBUFFER_POSITION = unsafe.objectFieldOffset(AbstractBuffer.class.getDeclaredField("position"));
			JOGAMP_ABSTRACTBUFFER_BUFFER = unsafe.objectFieldOffset(AbstractBuffer.class.getDeclaredField("buffer"));
		} catch(Throwable e) { throw new Error(e); }
	}
	private static <T extends AbstractBuffer<?>> T JOGAMP_wrap(Class<? extends T> clazz, T buffer, int elementSize, int size, Buffer container) {
		Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		if(buffer == null) try { buffer = (T) unsafe.allocateInstance(clazz);
		} catch(InstantiationException e) { throw new UnsupportedOperationException(e); }
		unsafe.putInt(buffer, JOGAMP_ABSTRACTBUFFER_ELEMENTSIZE, elementSize);
		unsafe.putInt(buffer, JOGAMP_ABSTRACTBUFFER_CAPACITY, size);
		unsafe.putInt(buffer, JOGAMP_ABSTRACTBUFFER_POSITION, 0);
		unsafe.putObject(buffer, JOGAMP_ABSTRACTBUFFER_BUFFER, container);
		return buffer;
	}
	public static com.jogamp.common.nio.PointerBuffer JOGAMP_createPointer(PointerBuffer buffer) {
		return buffer == null ? null : JOGAMP_wrap(com.jogamp.common.nio.PointerBuffer.class, null, PointerBuffer.ELEMENT_SIZE, buffer.capacity(), buffer.getBuffer());
	}

	public static long JOGAMP_getUInt32Long(com.jogamp.opencl.llb.CL cl, String method, long object, long parent, int key, LongBuffer buffer, int[] status, int statusOff) {
		boolean deallocateBuffer = false; try(CallBuffer callBuffer = getCallBuffer().as(status, statusOff)) {
			if(buffer == null || buffer.capacity() < 1) { deallocateBuffer = true; buffer = BufferUtils.allocateLongBuffer(1); }
			callBuffer.putError(JOGAMP_getInfo(cl, method, object, parent, key/*, 4*/, buffer, null)); return buffer.get(0) & 0xFFFFFFFFL;
		} finally { if(deallocateBuffer) deallocate(buffer); }
	}
	public static long JOGAMP_getUInt32Long(com.jogamp.opencl.llb.CL cl, String method, long object, int key, LongBuffer buffer, int[] status, int statusOff) { return JOGAMP_getUInt32Long(cl, method, object, NULL, key, buffer, status, statusOff); }
	public static long JOGAMP_getLong(com.jogamp.opencl.llb.CL cl, String method, long object, long parent, int key, LongBuffer buffer, int[] status, int statusOff) {
		boolean deallocateBuffer = false; try(CallBuffer callBuffer = getCallBuffer().as(status, statusOff)) {
			if(buffer == null || buffer.capacity() < 1) { deallocateBuffer = true; buffer = BufferUtils.allocateLongBuffer(1); }
			callBuffer.putError(JOGAMP_getInfo(cl, method, object, parent, key/*, 8*/, buffer, null)); return buffer.get(0);
		} finally { if(deallocateBuffer) deallocate(buffer); }
	}
	public static long JOGAMP_getLong(com.jogamp.opencl.llb.CL cl, String method, long object, int key, LongBuffer buffer, int[] status, int statusOff) { return JOGAMP_getLong(cl, method, object, NULL, key, buffer, status, statusOff); }
	public static String JOGAMP_getString(com.jogamp.opencl.llb.CL cl, String method, long object, long parent, int key, ByteBuffer buffer, int[] status, int statusOff) {
		boolean deallocateBuffer = false; try(CallBuffer callBuffer = getCallBuffer().as(status, statusOff)) {
			if(buffer == null || buffer.capacity() < 8) { deallocateBuffer = true; buffer = BufferUtils.allocateByteBuffer(512); }
			callBuffer.putError(JOGAMP_getInfo(cl, method, object, parent, key/*, 0*/, null, buffer));
			int stringBytesLen = (int) buffer.getLong(0);
			if(buffer.capacity() < stringBytesLen) { if(deallocateBuffer) deallocate(buffer); deallocateBuffer = true; buffer = BufferUtils.allocateByteBuffer(stringBytesLen); }
			callBuffer.putError(JOGAMP_getInfo(cl, method, object, parent, key/*, buffer.capacity()*/, buffer, null));
			return ByteUtils.toZeroTerminatedString(buffer);
		} finally { if(deallocateBuffer) deallocate(buffer); }
	}
	public static String JOGAMP_getString(com.jogamp.opencl.llb.CL cl, String method, long object, int key, ByteBuffer buffer, int[] status, int statusOff) { return JOGAMP_getString(cl, method, object, NULL, key, buffer, status, statusOff); }
	public static long[] JOGAMP_getLongs(com.jogamp.opencl.llb.CL cl, String method, long object, long parent, int key, int n, ByteBuffer buffer, int[] status, int statusOff) {
		boolean deallocateBuffer = false; int size = n * (UnsafeUtils.__is32Bit() ? 4 : 8); try(CallBuffer callBuffer = getCallBuffer().as(status, statusOff)) {
			if(buffer == null || buffer.capacity() < size) { deallocateBuffer = true; buffer = BufferUtils.allocateByteBuffer(size); }
			callBuffer.putError(JOGAMP_getInfo(cl, method, object, parent, key/*, buffer.capacity()*/, buffer, null)); long[] result = new long[n];
			if(UnsafeUtils.__is32Bit()) { for(int i = 0; i < result.length; i++) result[i] = buffer.getInt();
			} else { for(int i = 0; i < result.length; i++) result[i] = buffer.getLong(); }
			return result;
		} finally { if(deallocateBuffer) deallocate(buffer); }
	}
	public static long[] JOGAMP_getLongs(com.jogamp.opencl.llb.CL cl, String method, long object, int key, int n, ByteBuffer buffer, int[] status, int statusOff) { return JOGAMP_getLongs(cl, method, object, NULL, key, n, buffer, status, statusOff); }
	public static int[] JOGAMP_getInts(com.jogamp.opencl.llb.CL cl, String method, long object, long parent, int key, int n, ByteBuffer buffer, int[] status, int statusOff) {
		long[] result = JOGAMP_getLongs(cl, method, object, parent, key, n, buffer, status, statusOff); int[] array = new int[result.length];
		for(int i = 0; i < array.length; i++) array[i] = (int) result[i]; return array;
	}
	public static int[] JOGAMP_getInts(com.jogamp.opencl.llb.CL cl, String method, long object, int key, int n, ByteBuffer buffer, int[] status, int statusOff) { return JOGAMP_getInts(cl, method, object, NULL, key, n, buffer, status, statusOff); }

	public static long JOGAMP_createContext(com.jogamp.opencl.llb.CL cl, long platform, long[] devices, Map<Integer, Long> properties, CLErrorHandler callbackI, int[] status, int statusOff) {
		try(BufferUtils.MemoryStack stack = BufferUtils.allocateStack(); CallBuffer callBuffer = getCallBuffer().as(status, statusOff)) {
			PointerBuffer _deviceBuffer = stack.allocatePointerBuffer(devices.length);
			com.jogamp.common.nio.PointerBuffer deviceBuffer = callBuffer.wrapJogampPointer(_deviceBuffer);
			for(int i = 0; i < devices.length; i++) deviceBuffer.put(i, devices[i]);
			PointerBuffer _propertiesBuffer = stack.allocatePointerBuffer(3 + (properties != null ? properties.size() * 2 : 0));
			com.jogamp.common.nio.PointerBuffer propertiesBuffer = callBuffer.wrapJogampPointer(_propertiesBuffer);
			if(properties != null) for(Map.Entry<Integer, Long> entry : properties.entrySet())
				propertiesBuffer.put(entry.getKey()).put(entry.getValue());
			propertiesBuffer.put(CL10.CL_CONTEXT_PLATFORM).put(platform).put(0).rewind();
			return cl.clCreateContext(propertiesBuffer, deviceBuffer, callbackI, callBuffer.getErrorBuffer());
		}
	}
	public static long JOGAMP_createContext(com.jogamp.opencl.llb.CL cl, long platform, long[] devices, Map<Integer, Long> properties, int[] status, int statusOff) { return JOGAMP_createContext(cl, platform, devices, properties, (errinfo, private_info, cb) -> {}, status, statusOff); }
	public static long JOGAMP_createContext(com.jogamp.opencl.llb.CL cl, long platform, long[] devices, int[] status, int statusOff) { return JOGAMP_createContext(cl, platform, devices, null, status, statusOff); }
}
