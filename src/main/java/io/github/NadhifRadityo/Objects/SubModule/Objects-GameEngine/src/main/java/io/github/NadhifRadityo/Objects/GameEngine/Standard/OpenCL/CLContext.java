package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVecN;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.RenderBufferObject;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Texture;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.VertexBufferObject;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.PointerBuffer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public abstract class CLContext<ID extends Number> {
	protected final Object cl;

	public CLContext(Object cl) {
		this.cl = cl;
	}

	public Object getCL() { return cl; }
	protected void assertCLNull() { if(cl != null) throw newException("CL is not null"); }
	protected void assertCLNotNull() { if(cl == null) throw newException("CL is null"); }
	protected void assertStatic() { assertCLNull(); }
	protected void assertNotStatic() { assertCLNotNull(); }
	@Override public boolean equals(Object o) { if(this == o) return true; if(o == null) return false;
		if(!o.getClass().getCanonicalName().startsWith(getClass().getCanonicalName())) return false;
		GLContext<?> glContext = (GLContext<?>) o; return getCL().equals(glContext.getGL());
	}

	// ETC*STATIC&NON STATIC*
	public abstract OpenCLNativeHolderProvider<ID> constructNativeProviderInstance();
	public abstract Device<ID> constructDevice(ID id);
	public abstract Platform<ID> constructPlatform(ID id);
	public abstract ImageFormat constructImageFormat(ByteBuffer buffer, boolean deallocate);
	public abstract CommandQueue<ID, ?> constructCommandQueue(Device<ID> device, int properties);
	public abstract Program<ID> constructProgram(String[] code, String options, Device<ID>[] devices);
	public abstract Kernel<ID> constructKernel(Program<ID> program, ID id);
	public abstract Buffer<ID, ?> constructBuffer(int size, java.nio.Buffer buffer, int flags);
	public abstract int clGetError();

	// Info
	public abstract void getInfo(String method, int key, ID id, ID parent, java.nio.Buffer value, java.nio.Buffer valueSizeRet);
	public abstract long getUInt32Long(String method, int key, ID id, ID parent);
	public abstract long getLong(String method, int key, ID id, ID parent);
	public abstract String getString(String method, int key, ID id, ID parent);
	public abstract long[] getLongs(String method, int key, ID id, ID parent, int n);
	public abstract int[] getInts(String method, int key, ID id, ID parent, int n);
	public void getInfo(String method, int key, ID id, long valueSize, java.nio.Buffer value, java.nio.Buffer valueSizeRet) { getInfo(method, key, id, null, value, valueSizeRet); }
	public long getUInt32Long(String method, int key, ID id) { return getUInt32Long(method, key, id, null); }
	public long getLong(String method, int key, ID id) { return getLong(method, key, id, null); }
	public String getString(String method, int key, ID id) { return getString(method, key, id, null); }
	public long[] getLongs(String method, int key, ID id, int n) { return getLongs(method, key, id, null, n); }
	public int[] getInts(String method, int key, ID id, int n) { return getInts(method, key, id, null, n); }

	// Buffer
	public abstract ID createBuffer(int flags, int size, java.nio.Buffer buffer);
	public abstract ID createSubBuffer(ID parent, int flags, int type, PointerBuffer info);
	public abstract ID createImage2D(int flags, ImageFormat imageFormat, int width, int height, int rowPitch, java.nio.Buffer buffer);
	public abstract ID createImage3D(int flags, ImageFormat imageFormat, int width, int height, int depth, int rowPitch, int slicePitch, java.nio.Buffer buffer);
	public abstract ID createFromGLRenderbuffer(RenderBufferObject<? extends Number> bufferObject, int flags);
	public abstract ID createFromGLTexture2D(Texture<? extends Number> texture, int target, int mipMapLevel, int flags);
	public abstract ID createFromGLTexture3D(Texture<? extends Number> texture, int target, int mipMapLevel, int flags);
	public abstract ID createFromGLBuffer(VertexBufferObject<? extends Number> bufferObject, int flags);

	public abstract String METHOD_clGetImageInfo();
	public abstract int CL_BUFFER_CREATE_TYPE_REGION();
	public abstract int CL_IMAGE_FORMAT();
	public abstract int CL_IMAGE_WIDTH();
	public abstract int CL_IMAGE_HEIGHT();
	public abstract int CL_IMAGE_DEPTH();

	// CommandQueue
	public abstract ID createCommandQueue(ID deviceId, int properties);
	public abstract void clReleaseCommandQueue(ID id);
	public abstract void clEnqueueWriteBuffer(ID id, Buffer<ID, ?> writeBuffer, boolean blockingWrite, int offset, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueReadBuffer(ID id, Buffer<ID, ?> readBuffer, boolean blockingRead, int offset, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueCopyBuffer(ID id, Buffer<ID, ?> srcBuffer, Buffer<ID, ?> dstBuffer, int srcOffset, int dstOffset, int size, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueWriteBufferRect(ID id, Buffer<ID, ?> writeBuffer, LVec3 origin, LVec3 host, LVec3 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingWrite, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueReadBufferRect(ID id, Buffer<ID, ?> readBuffer, LVec3 origin, LVec3 host, LVec3 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingRead, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueCopyBufferRect(ID id, Buffer<ID, ?> src, Buffer<ID, ?> dst, LVec3 srcOrigin, LVec3 dstOrigin, LVec3 range, long srcRowPitch, long srcSlicePitch, long dstRowPitch, long dstSlicePitch, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueWriteImage(ID id, Buffer.ImageBuffer<ID, ?> writeImage, int inputRowPitch, int inputSlicePitch, LVec3 origin, LVec3 range, boolean blockingWrite, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueReadImage(ID id, Buffer.ImageBuffer<ID, ?> readImage, int rowPitch, int slicePitch, LVec3 origin, LVec3 range, boolean blockingRead, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueCopyImage(ID id, Buffer.ImageBuffer<ID, ?> srcImage, Buffer.ImageBuffer<ID, ?> dstImage, LVec3 srcOrigin, LVec3 dstOrigin, LVec3 range, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueCopyBufferToImage(ID id, Buffer<ID, ?> srcBuffer, Buffer.ImageBuffer<ID, ?> dstImage, int srcOffset, LVec3 dstOrigin, LVec3 range, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueCopyImageToBuffer(ID id, Buffer.ImageBuffer<ID, ?> srcImage, Buffer<ID, ?> dstBuffer, LVec3 srcOrigin, LVec3 range, int dstOffset, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract ByteBuffer clEnqueueMapBuffer(ID id, Buffer<ID, ?> buffer, int flag, int offset, int size, boolean blockingMap, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract ByteBuffer clEnqueueMapImage(ID id, Buffer.ImageBuffer<ID, ?> image, int flag, LVec3 offset, LVec3 range, boolean blockingMap, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueUnmapMemObject(ID id, Memory<ID, ?> memory, java.nio.Buffer mapped, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueMarker(ID id, PointerBuffer event);
	public abstract void clWaitForEvents(ID id, int length, PointerBuffer eventWaitList);
	public abstract void clEnqueueWaitForEvents(ID id, int length, PointerBuffer eventWaitList);
	public abstract void clEnqueueBarrier(ID id);
	public abstract void clEnqueueTask(ID id, Kernel<ID> kernel, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueNDRangeKernel(ID id, Kernel<ID> kernel, int workDimension, LVecN globalWorkOffset, LVecN globalWorkSize, LVecN localWorkSize, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueAcquireGLObjects(ID id, PointerBuffer clglObjects, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clEnqueueReleaseGLObjects(ID id, PointerBuffer clglObjects, PointerBuffer eventWaitList, PointerBuffer event);
	public abstract void clFinish(ID id);
	public abstract void clFlush(ID id);

	public abstract int CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE();
	public abstract int CL_QUEUE_PROFILING_ENABLE();

	// Device
	public abstract String METHOD_clGetDeviceInfo();
	public abstract int CL_DEVICE_EXTENSIONS();
	public abstract int CL_TRUE();
	public abstract int CL_FALSE();
	public abstract int CL_DEVICE_NAME();
	public abstract int CL_DEVICE_PROFILE();
	public abstract int CL_DEVICE_VENDOR();
	public abstract int CL_DEVICE_VENDOR_ID();
	public abstract int CL_DEVICE_VERSION();
	public abstract int CL_DEVICE_OPENCL_C_VERSION();
	public abstract int CL_DRIVER_VERSION();
	public abstract int CL_DEVICE_TYPE();
	public abstract int CL_DEVICE_ADDRESS_BITS();
	public abstract int CL_DEVICE_PREFERRED_VECTOR_WIDTH_SHORT();
	public abstract int CL_DEVICE_PREFERRED_VECTOR_WIDTH_CHAR();
	public abstract int CL_DEVICE_PREFERRED_VECTOR_WIDTH_INT();
	public abstract int CL_DEVICE_PREFERRED_VECTOR_WIDTH_LONG();
	public abstract int CL_DEVICE_PREFERRED_VECTOR_WIDTH_FLOAT();
	public abstract int CL_DEVICE_PREFERRED_VECTOR_WIDTH_DOUBLE();
	public abstract int CL_DEVICE_NATIVE_VECTOR_WIDTH_SHORT();
	public abstract int CL_DEVICE_NATIVE_VECTOR_WIDTH_CHAR();
	public abstract int CL_DEVICE_NATIVE_VECTOR_WIDTH_INT();
	public abstract int CL_DEVICE_NATIVE_VECTOR_WIDTH_LONG();
	public abstract int CL_DEVICE_NATIVE_VECTOR_WIDTH_HALF();
	public abstract int CL_DEVICE_NATIVE_VECTOR_WIDTH_FLOAT();
	public abstract int CL_DEVICE_NATIVE_VECTOR_WIDTH_DOUBLE();
	public abstract int CL_DEVICE_MAX_COMPUTE_UNITS();
	public abstract int CL_DEVICE_MAX_WORK_GROUP_SIZE();
	public abstract int CL_DEVICE_MAX_CLOCK_FREQUENCY();
	public abstract int CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS();
	public abstract int CL_DEVICE_MAX_WORK_ITEM_SIZES();
	public abstract int CL_DEVICE_MAX_PARAMETER_SIZE();
	public abstract int CL_DEVICE_MAX_MEM_ALLOC_SIZE();
	public abstract int CL_DEVICE_MEM_BASE_ADDR_ALIGN();
	public abstract int CL_DEVICE_GLOBAL_MEM_SIZE();
	public abstract int CL_DEVICE_LOCAL_MEM_SIZE();
	public abstract int CL_DEVICE_HOST_UNIFIED_MEMORY();
	public abstract int CL_DEVICE_MAX_CONSTANT_BUFFER_SIZE();
	public abstract int CL_DEVICE_GLOBAL_MEM_CACHELINE_SIZE();
	public abstract int CL_DEVICE_GLOBAL_MEM_CACHE_SIZE();
	public abstract int CL_DEVICE_MAX_CONSTANT_ARGS();
	public abstract int CL_DEVICE_IMAGE_SUPPORT();
	public abstract int CL_DEVICE_MAX_READ_IMAGE_ARGS();
	public abstract int CL_DEVICE_MAX_WRITE_IMAGE_ARGS();
	public abstract int CL_DEVICE_IMAGE2D_MAX_WIDTH();
	public abstract int CL_DEVICE_IMAGE2D_MAX_HEIGHT();
	public abstract int CL_DEVICE_IMAGE3D_MAX_WIDTH();
	public abstract int CL_DEVICE_IMAGE3D_MAX_HEIGHT();
	public abstract int CL_DEVICE_IMAGE3D_MAX_DEPTH();
	public abstract int CL_DEVICE_MAX_SAMPLERS();
	public abstract int CL_DEVICE_PROFILING_TIMER_RESOLUTION();
	public abstract int CL_DEVICE_EXECUTION_CAPABILITIES();
	public abstract int CL_DEVICE_HALF_FP_CONFIG();
	public abstract int CL_DEVICE_SINGLE_FP_CONFIG();
	public abstract int CL_DEVICE_DOUBLE_FP_CONFIG();
	public abstract int CL_DEVICE_LOCAL_MEM_TYPE();
	public abstract int CL_DEVICE_GLOBAL_MEM_CACHE_TYPE();
	public abstract int CL_DEVICE_QUEUE_PROPERTIES();
	public abstract int CL_DEVICE_AVAILABLE();
	public abstract int CL_DEVICE_COMPILER_AVAILABLE();
	public abstract int CL_DEVICE_ENDIAN_LITTLE();
	public abstract int CL_DEVICE_ERROR_CORRECTION_SUPPORT();
	public abstract int CL_EXEC_KERNEL();
	public abstract int CL_EXEC_NATIVE_KERNEL();
	public abstract int CL_FP_DENORM();
	public abstract int CL_FP_INF_NAN();
	public abstract int CL_FP_ROUND_TO_NEAREST();
	public abstract int CL_FP_ROUND_TO_INF();
	public abstract int CL_FP_ROUND_TO_ZERO();
	public abstract int CL_FP_FMA();
//	public abstract int CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE();
//	public abstract int CL_QUEUE_PROFILING_ENABLE();

	// Memory
	public abstract void clReleaseMemObject(ID id);
//	public abstract int[] getConfigs(int flags);
	public abstract void addListener(ID id, Memory.MemoryListener listener, int priority);
	public abstract void removeListener(ID id, Memory.MemoryListener listener);

	public abstract String METHOD_clGetMemObjectInfo();
	public abstract int CL_MEM_READ_WRITE();
	public abstract int CL_MEM_READ_ONLY();
	public abstract int CL_MEM_WRITE_ONLY();
	public abstract int CL_MEM_USE_HOST_PTR();
	public abstract int CL_MEM_ALLOC_HOST_PTR();
	public abstract int CL_MEM_COPY_HOST_PTR();
	public abstract int CL_MEM_MAP_COUNT();
	public abstract int CL_MEM_SIZE();

	// Platform
	public abstract ID[] getDeviceIDs(ID id, int type);

	public abstract String METHOD_clGetPlatformInfo();
	public abstract int CL_PLATFORM_VERSION();
	public abstract int CL_PLATFORM_NAME();
	public abstract int CL_PLATFORM_PROFILE();
	public abstract int CL_PLATFORM_VENDOR();
	public abstract int CL_PLATFORM_ICD_SUFFIX_KHR();
	public abstract int CL_PLATFORM_EXTENSIONS();
	public abstract int CL_DEVICE_TYPE_ALL();

	// Kernel
	public abstract void clReleaseKernel(ID id);
	public abstract void clSetKernelArg(ID id, int index, int size, java.nio.Buffer buffer);

	public abstract String METHOD_clGetKernelInfo();
	public abstract String METHOD_clGetKernelWorkGroupInfo();
	public abstract int CL_KERNEL_FUNCTION_NAME();
	public abstract int CL_KERNEL_NUM_ARGS();
	public abstract int CL_KERNEL_LOCAL_MEM_SIZE();
	public abstract int CL_KERNEL_WORK_GROUP_SIZE();
	public abstract int CL_KERNEL_COMPILE_WORK_GROUP_SIZE();

	// Program
	public abstract ID createProgram(String[] code);
	public abstract void clBuildProgram(ID id, Device<ID>[] devices, String options);
	public abstract void clReleaseProgram(ID id);
	public abstract void createKernelsInProgram(ID id, int length, PointerBuffer buffer, IntBuffer valueSize);

	public abstract String METHOD_clGetProgramBuildInfo();
	public abstract String METHOD_clGetProgramInfo();
	public abstract int CL_PROGRAM_DEVICES();
	public abstract int CL_PROGRAM_BUILD_LOG();
	public abstract int CL_PROGRAM_BUILD_STATUS();
	public abstract int CL_BUILD_SUCCESS();
	public abstract int CL_PROGRAM_BINARY_SIZES();
	public abstract int CL_PROGRAM_BINARIES();

	public static CLException newException(String msg, Exception e) { throw new CLException(msg, e); }
	public static CLException newException(String msg) { throw new CLException(msg); }
	public static CLException newException(Exception e) { throw new CLException(e); }
}
