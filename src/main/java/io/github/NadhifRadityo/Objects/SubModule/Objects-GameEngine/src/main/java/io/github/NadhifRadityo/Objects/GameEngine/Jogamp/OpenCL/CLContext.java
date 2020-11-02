package io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL;

import com.jogamp.opencl.llb.CL;
import com.jogamp.opencl.llb.impl.CLImageFormatImpl;
import com.jogamp.opencl.llb.impl.CLImpl;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVecN;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Buffer;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.CommandQueue;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Device;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.ImageFormat;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Kernel;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Memory;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.OpenCLNativeHolderProvider;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Platform;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Program;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.RenderBufferObject;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Texture;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.VertexBufferObject;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.PointerBuffer;
import io.github.NadhifRadityo.Objects.Utilizations.ClassUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.OpenCLUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.Utilizations.StringUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.system.MemoryUtil.NULL;

public class CLContext extends io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.CLContext<Long> {

	protected static final CLImpl cl = new CLImpl();

	public CLContext(Long cl) {
		super(cl);
	}

	public long getId() { return (long) super.getCL(); }
	public static io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Platform[] getPlatforms() {
		CLContext dummy = new CLContext(null);
		return OpenCLUtils.getPlatforms(args -> { cl.clGetPlatformIDs(args[0] == null ? 0 : ((PointerBuffer) args[0]).capacity(), OpenCLUtils.JOGAMP_createPointer((PointerBuffer) args[0]), (IntBuffer) args[1]); return null; },
				(args) -> new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Platform(dummy, (Long) args[0]), io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Platform.class);
	}
	public static CLContext createContext(io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Platform platform, io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Platform[] platforms, Device<Long>... devices) {
		CLContext dummy = platform.getCL().getCL() == null && platform.getCL() instanceof CLContext ? (CLContext) platform.getCL() : new CLContext(null);
		long[] deviceIds = new long[devices.length]; for(int i = 0; i < deviceIds.length; i++) deviceIds[i] = devices[i].getId();
		long context = OpenCLUtils.JOGAMP_createContext(cl, platform.getId(), deviceIds, null, 0);
		ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> ClassUtils.setFinal(dummy, io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.CLContext.class.getDeclaredField("cl"), context));
		PrivilegedUtils.doPrivileged(() -> { OpenCLUtils.changeContext(dummy, platform); OpenCLUtils.changeContext(dummy, platforms); OpenCLUtils.changeContext(dummy, devices); });
		return dummy;
	}

	// ETC*STATIC&NON STATIC*
	@Override public OpenCLNativeHolderProvider<Long> constructNativeProviderInstance() { return new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.OpenCLNativeHolderProvider(); }
	@Override public Device<Long> constructDevice(Long id) { return new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Device(this, id); }
	@Override public Platform<Long> constructPlatform(Long id) { return new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Platform(this, id); }
	@Override public ImageFormat constructImageFormat(ByteBuffer buffer, boolean deallocate) { return new ImageFormat(this, buffer, deallocate); }
	@Override public CommandQueue<Long, ?> constructCommandQueue(Device<Long> device, int properties) { return new
			io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.CommandQueue((io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Device) device, properties); }
	@Override public Program<Long> constructProgram(String[] code, String options, Device<Long>[] devices) { return new
			io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Program(this, code, options, (io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Device[]) devices); }
	@Override public Kernel<Long> constructKernel(Program<Long> program, Long id) { return new
			io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Kernel((io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Program) program, id); }
	@Override public Buffer<Long, ?> constructBuffer(int size, java.nio.Buffer buffer, int flags) { return new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL.Buffer<>(this, size, buffer, flags); }
	@Override public int clGetError() { return 0; }

	// Info
	@Override public void getInfo(String method, int key, Long id, Long parent, java.nio.Buffer value, java.nio.Buffer valueSizeRet) { OpenCLUtils.JOGAMP_getInfo(cl, method, id, parent != null ? parent : NULL, key, value, valueSizeRet); }
	@Override public long getUInt32Long(String method, int key, Long id, Long parent) { return OpenCLUtils.JOGAMP_getUInt32Long(cl, method, id, parent != null ? parent : NULL, key, null, null, 0); }
	@Override public long getLong(String method, int key, Long id, Long parent) { return OpenCLUtils.JOGAMP_getLong(cl, method, id, parent != null ? parent : NULL, key, null, null, 0); }
	@Override public String getString(String method, int key, Long id, Long parent) { return OpenCLUtils.JOGAMP_getString(cl, method, id, parent != null ? parent : NULL, key, null, null, 0); }
	@Override public long[] getLongs(String method, int key, Long id, Long parent, int n) { return OpenCLUtils.JOGAMP_getLongs(cl, method, id, parent != null ? parent : NULL, key, n, null, null, 0); }
	@Override public int[] getInts(String method, int key, Long id, Long parent, int n) { return OpenCLUtils.JOGAMP_getInts(cl, method, id, parent != null ? parent : NULL, n, key, null, null, 0); }

	// Buffer
	@Override public Long createBuffer(int flags, int size, java.nio.Buffer buffer) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { return cl.clCreateBuffer(getId(), flags, size, buffer, callBuffer.getErrorBuffer()); } }
	@Override public Long createSubBuffer(Long parent, int flags, int type, PointerBuffer info) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { return cl.clCreateSubBuffer(parent, flags, type, info.getBuffer(), callBuffer.getErrorBuffer()); } }
	@Override public Long createImage2D(int flags, ImageFormat imageFormat, int width, int height, int rowPitch, java.nio.Buffer buffer) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { return cl.clCreateImage2D(getId(), flags, CLImageFormatImpl.create(imageFormat.getBuffer()), width, height, rowPitch, buffer, callBuffer.getErrorBuffer()); } }
	@Override public Long createImage3D(int flags, ImageFormat imageFormat, int width, int height, int depth, int rowPitch, int slicePitch, java.nio.Buffer buffer) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { return cl.clCreateImage3D(getId(), flags, CLImageFormatImpl.create(imageFormat.getBuffer()), width, height, depth, rowPitch, slicePitch, buffer, callBuffer.getErrorBuffer()); } }
	@Override public Long createFromGLRenderbuffer(RenderBufferObject<? extends Number> bufferObject, int flags) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { return cl.clCreateFromGLRenderbuffer(getId(), flags, (Integer) bufferObject.getId(), callBuffer.getErrorBuffer()); } }
	@Override public Long createFromGLTexture2D(Texture<? extends Number> texture, int target, int mipMapLevel, int flags) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { return cl.clCreateFromGLTexture2D(getId(), flags, target, mipMapLevel, (Integer) texture.getId(), callBuffer.getErrorBuffer()); } }
	@Override public Long createFromGLTexture3D(Texture<? extends Number> texture, int target, int mipMapLevel, int flags) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { return cl.clCreateFromGLTexture3D(getId(), flags, target, mipMapLevel, (Integer) texture.getId(), callBuffer.getErrorBuffer()); } }
	@Override public Long createFromGLBuffer(VertexBufferObject<? extends Number> bufferObject, int flags) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { return cl.clCreateFromGLBuffer(getId(), flags, (Integer) bufferObject.getId(), callBuffer.getErrorBuffer()); } }

	@Override public String METHOD_clGetImageInfo() { return "clGetImageInfo"; }
	@Override public int CL_BUFFER_CREATE_TYPE_REGION() { return CL.CL_BUFFER_CREATE_TYPE_REGION; }
	@Override public int CL_IMAGE_FORMAT() { return CL.CL_IMAGE_FORMAT; }
	@Override public int CL_IMAGE_WIDTH() { return CL.CL_IMAGE_WIDTH; }
	@Override public int CL_IMAGE_HEIGHT() { return CL.CL_IMAGE_HEIGHT; }
	@Override public int CL_IMAGE_DEPTH() { return CL.CL_IMAGE_DEPTH; }

	// CommandQueue
	@Override public Long createCommandQueue(Long deviceId, int properties) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { return cl.clCreateCommandQueue(getId(), deviceId, properties, callBuffer.getErrorBuffer()); } }
	@Override public void clReleaseCommandQueue(Long id) { cl.clReleaseCommandQueue(id); }
	@Override public void clEnqueueWriteBuffer(Long id, Buffer<Long, ?> writeBuffer, boolean blockingWrite, int offset, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity(2 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueWriteBuffer(id, writeBuffer.getId(), blockingWrite ? 1 : 0, offset, writeBuffer.getBuffer() == null ? 0 : writeBuffer.getCLSize() - offset, writeBuffer.getBuffer(), eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public void clEnqueueReadBuffer(Long id, Buffer<Long, ?> readBuffer, boolean blockingRead, int offset, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity(2 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueReadBuffer(id, readBuffer.getId(), blockingRead ? 1 : 0, offset, readBuffer.getBuffer() == null ? 0 : readBuffer.getCLSize() - offset, readBuffer.getBuffer(), eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public void clEnqueueCopyBuffer(Long id, Buffer<Long, ?> srcBuffer, Buffer<Long, ?> dstBuffer, int srcOffset, int dstOffset, int size, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity(2 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueCopyBuffer(id, srcBuffer.getId(), dstBuffer.getId(), srcOffset, dstOffset, size, eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public void clEnqueueWriteBufferRect(Long id, Buffer<Long, ?> writeBuffer, LVec3 origin, LVec3 host, LVec3 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingWrite, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity((origin.size + host.size + range.size) * Long.BYTES + 5 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueWriteBufferRect(id, writeBuffer.getId(), blockingWrite ? 1 : 0, callBuffer.pointJogamp(callBuffer.as(origin)), callBuffer.pointJogamp(callBuffer.as(host)), callBuffer.pointJogamp(callBuffer.as(range)), rowPitch, slicePitch, hostRowPitch, hostSlicePitch, writeBuffer.getBuffer(), eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public void clEnqueueReadBufferRect(Long id, Buffer<Long, ?> readBuffer, LVec3 origin, LVec3 host, LVec3 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingRead, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity((origin.size + host.size + range.size) * Long.BYTES + 5 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueReadBufferRect(id, readBuffer.getId(), blockingRead ? 1 : 0, callBuffer.pointJogamp(callBuffer.as(origin)), callBuffer.pointJogamp(callBuffer.as(host)), callBuffer.pointJogamp(callBuffer.as(range)), rowPitch, slicePitch, hostRowPitch, hostSlicePitch, readBuffer.getBuffer(), eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public void clEnqueueCopyBufferRect(Long id, Buffer<Long, ?> src, Buffer<Long, ?> dst, LVec3 srcOrigin, LVec3 dstOrigin, LVec3 range, long srcRowPitch, long srcSlicePitch, long dstRowPitch, long dstSlicePitch, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity((srcOrigin.size + dstOrigin.size + range.size) * Long.BYTES + 5 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueCopyBufferRect(id, src.getId(), dst.getId(), callBuffer.pointJogamp(callBuffer.as(srcOrigin)), callBuffer.pointJogamp(callBuffer.as(dstOrigin)), callBuffer.pointJogamp(callBuffer.as(range)), srcRowPitch, srcSlicePitch, dstRowPitch, dstSlicePitch, eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public void clEnqueueWriteImage(Long id, Buffer.ImageBuffer<Long, ?> writeImage, int inputRowPitch, int inputSlicePitch, LVec3 origin, LVec3 range, boolean blockingWrite, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity((origin.size + range.size) * Long.BYTES + 4 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueWriteImage(id, writeImage.getId(), blockingWrite ? 1 : 0, callBuffer.pointJogamp(callBuffer.as(origin)), callBuffer.pointJogamp(callBuffer.as(range)), inputRowPitch, inputSlicePitch, writeImage.getBuffer(), eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public void clEnqueueReadImage(Long id, Buffer.ImageBuffer<Long, ?> readImage, int rowPitch, int slicePitch, LVec3 origin, LVec3 range, boolean blockingRead, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity((origin.size + range.size) * Long.BYTES + 4 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueReadImage(id, readImage.getId(), blockingRead ? 1 : 0, callBuffer.pointJogamp(callBuffer.as(origin)), callBuffer.pointJogamp(callBuffer.as(range)), rowPitch, slicePitch, readImage.getBuffer(), eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public void clEnqueueCopyImage(Long id, Buffer.ImageBuffer<Long, ?> srcImage, Buffer.ImageBuffer<Long, ?> dstImage, LVec3 srcOrigin, LVec3 dstOrigin, LVec3 range, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity((srcOrigin.size + dstOrigin.size + range.size) * Long.BYTES + 5 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueCopyImage(id, srcImage.getId(), dstImage.getId(), callBuffer.pointJogamp(callBuffer.as(srcOrigin)), callBuffer.pointJogamp(callBuffer.as(dstOrigin)), callBuffer.pointJogamp(callBuffer.as(range)), eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public void clEnqueueCopyBufferToImage(Long id, Buffer<Long, ?> srcBuffer, Buffer.ImageBuffer<Long, ?> dstImage, int srcOffset, LVec3 dstOrigin, LVec3 range, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity((dstOrigin.size + range.size) * Long.BYTES + 4 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueCopyBufferToImage(id, srcBuffer.getId(), dstImage.getId(), srcOffset, callBuffer.pointJogamp(callBuffer.as(dstOrigin)), callBuffer.pointJogamp(callBuffer.as(range)), eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public void clEnqueueCopyImageToBuffer(Long id, Buffer.ImageBuffer<Long, ?> srcImage, Buffer<Long, ?> dstBuffer, LVec3 srcOrigin, LVec3 range, int dstOffset, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity((srcOrigin.size + range.size) * Long.BYTES + 4 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueCopyImageToBuffer(id, srcImage.getId(), dstBuffer.getId(), callBuffer.pointJogamp(callBuffer.as(srcOrigin)), callBuffer.pointJogamp(callBuffer.as(range)), dstOffset, eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public ByteBuffer clEnqueueMapBuffer(Long id, Buffer<Long, ?> buffer, int flag, int offset, int size, boolean blockingMap, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity(2 * PointerBuffer.ELEMENT_SIZE); return cl.clEnqueueMapBuffer(id, buffer.getId(), blockingMap ? 1 : 0, flag, offset, size, eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event), callBuffer.getErrorBuffer()); } }
	@Override public ByteBuffer clEnqueueMapImage(Long id, Buffer.ImageBuffer<Long, ?> image, int flag, LVec3 offset, LVec3 range, boolean blockingMap, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity((offset.size + range.size + 1) * Long.BYTES + 6 * PointerBuffer.ELEMENT_SIZE); return cl.clEnqueueMapImage(id, image.getId(), blockingMap ? 1 : 0, flag, callBuffer.pointJogamp(callBuffer.as(offset)), callBuffer.pointJogamp(callBuffer.as(range)), image instanceof Buffer.ImageBuffer.Image2DBuffer ? callBuffer.pointJogamp(callBuffer.as(((Buffer.ImageBuffer.Image2DBuffer) image).getRowPitch())) : image instanceof Buffer.ImageBuffer.Image3DBuffer ? callBuffer.pointJogamp(callBuffer.as(((Buffer.ImageBuffer.Image3DBuffer) image).getRowPitch())) : null, image instanceof Buffer.ImageBuffer.Image3DBuffer ? callBuffer.pointJogamp(callBuffer.as(((Buffer.ImageBuffer.Image3DBuffer) image).getSlicePitch())) : null, eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event), callBuffer.getErrorBuffer()); } }
	@Override public void clEnqueueUnmapMemObject(Long id, Memory<Long, ?> memory, java.nio.Buffer mapped, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity(2 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueUnmapMemObject(id, memory.getId(), mapped, eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public void clEnqueueMarker(Long id, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clEnqueueMarker(id, callBuffer.wrapJogampPointer(event))); } }
	@Override public void clWaitForEvents(Long id, int length, PointerBuffer eventWaitList) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clWaitForEvents(eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.wrapJogampPointer(eventWaitList))); } }
	@Override public void clEnqueueWaitForEvents(Long id, int length, PointerBuffer eventWaitList) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clEnqueueWaitForEvents(id, eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.wrapJogampPointer(eventWaitList))); } }
	@Override public void clEnqueueBarrier(Long id) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clEnqueueBarrier(id)); } }
	@Override public void clEnqueueTask(Long id, Kernel<Long> kernel, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clEnqueueTask(id, kernel.getId(), eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.wrapJogampPointer(eventWaitList), callBuffer.wrapJogampPointer(event))); } }
	@Override public void clEnqueueNDRangeKernel(Long id, Kernel<Long> kernel, int workDimension, LVecN globalWorkOffset, LVecN globalWorkSize, LVecN localWorkSize, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.ensureCapacity((globalWorkOffset.size + globalWorkSize.size + localWorkSize.size) * Long.BYTES + 5 * PointerBuffer.ELEMENT_SIZE); callBuffer.putError(cl.clEnqueueNDRangeKernel(id, kernel.getId(), workDimension, callBuffer.asJogampPointer(globalWorkOffset), callBuffer.asJogampPointer(globalWorkSize), callBuffer.asJogampPointer(localWorkSize), eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.pointJogamp(eventWaitList), callBuffer.pointJogamp(event))); } }
	@Override public void clEnqueueAcquireGLObjects(Long id, PointerBuffer clglObjects, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clEnqueueAcquireGLObjects(id, clglObjects == null ? 0 : clglObjects.remaining(), callBuffer.wrapJogampPointer(clglObjects), eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.wrapJogampPointer(eventWaitList), callBuffer.wrapJogampPointer(event))); } }
	@Override public void clEnqueueReleaseGLObjects(Long id, PointerBuffer clglObjects, PointerBuffer eventWaitList, PointerBuffer event) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clEnqueueReleaseGLObjects(id, clglObjects == null ? 0 : clglObjects.remaining(), callBuffer.wrapJogampPointer(clglObjects), eventWaitList == null ? 0 : eventWaitList.remaining(), callBuffer.wrapJogampPointer(eventWaitList), callBuffer.wrapJogampPointer(event))); } }
	@Override public void clFinish(Long id) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clFinish(id)); } }
	@Override public void clFlush(Long id) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clFlush(id)); } }

	@Override public int CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE() { return CL.CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE; }
	@Override public int CL_QUEUE_PROFILING_ENABLE() { return CL.CL_QUEUE_PROFILING_ENABLE; }

	// Device
	@Override public String METHOD_clGetDeviceInfo() { return "clGetDeviceInfo"; }
	@Override public int CL_DEVICE_EXTENSIONS() { return CL.CL_DEVICE_EXTENSIONS; }
	@Override public int CL_TRUE() { return CL.CL_TRUE; }
	@Override public int CL_FALSE() { return CL.CL_FALSE; }
	@Override public int CL_DEVICE_NAME() { return CL.CL_DEVICE_NAME; }
	@Override public int CL_DEVICE_PROFILE() { return CL.CL_DEVICE_PROFILE; }
	@Override public int CL_DEVICE_VENDOR() { return CL.CL_DEVICE_VENDOR; }
	@Override public int CL_DEVICE_VENDOR_ID() { return CL.CL_DEVICE_VENDOR_ID; }
	@Override public int CL_DEVICE_VERSION() { return CL.CL_DEVICE_VERSION; }
	@Override public int CL_DEVICE_OPENCL_C_VERSION() { return CL.CL_DEVICE_OPENCL_C_VERSION; }
	@Override public int CL_DRIVER_VERSION() { return CL.CL_DRIVER_VERSION; }
	@Override public int CL_DEVICE_TYPE() { return CL.CL_DEVICE_TYPE; }
	@Override public int CL_DEVICE_ADDRESS_BITS() { return CL.CL_DEVICE_ADDRESS_BITS; }
	@Override public int CL_DEVICE_PREFERRED_VECTOR_WIDTH_SHORT() { return CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_SHORT; }
	@Override public int CL_DEVICE_PREFERRED_VECTOR_WIDTH_CHAR() { return CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_CHAR; }
	@Override public int CL_DEVICE_PREFERRED_VECTOR_WIDTH_INT() { return CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_INT; }
	@Override public int CL_DEVICE_PREFERRED_VECTOR_WIDTH_LONG() { return CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_LONG; }
	@Override public int CL_DEVICE_PREFERRED_VECTOR_WIDTH_FLOAT() { return CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_FLOAT; }
	@Override public int CL_DEVICE_PREFERRED_VECTOR_WIDTH_DOUBLE() { return CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_DOUBLE; }
	@Override public int CL_DEVICE_NATIVE_VECTOR_WIDTH_SHORT() { return CL.CL_DEVICE_NATIVE_VECTOR_WIDTH_SHORT; }
	@Override public int CL_DEVICE_NATIVE_VECTOR_WIDTH_CHAR() { return CL.CL_DEVICE_NATIVE_VECTOR_WIDTH_CHAR; }
	@Override public int CL_DEVICE_NATIVE_VECTOR_WIDTH_INT() { return CL.CL_DEVICE_NATIVE_VECTOR_WIDTH_INT; }
	@Override public int CL_DEVICE_NATIVE_VECTOR_WIDTH_LONG() { return CL.CL_DEVICE_NATIVE_VECTOR_WIDTH_LONG; }
	@Override public int CL_DEVICE_NATIVE_VECTOR_WIDTH_HALF() { return CL.CL_DEVICE_NATIVE_VECTOR_WIDTH_HALF; }
	@Override public int CL_DEVICE_NATIVE_VECTOR_WIDTH_FLOAT() { return CL.CL_DEVICE_NATIVE_VECTOR_WIDTH_FLOAT; }
	@Override public int CL_DEVICE_NATIVE_VECTOR_WIDTH_DOUBLE() { return CL.CL_DEVICE_NATIVE_VECTOR_WIDTH_DOUBLE; }
	@Override public int CL_DEVICE_MAX_COMPUTE_UNITS() { return CL.CL_DEVICE_MAX_COMPUTE_UNITS; }
	@Override public int CL_DEVICE_MAX_WORK_GROUP_SIZE() { return CL.CL_DEVICE_MAX_WORK_GROUP_SIZE; }
	@Override public int CL_DEVICE_MAX_CLOCK_FREQUENCY() { return CL.CL_DEVICE_MAX_CLOCK_FREQUENCY; }
	@Override public int CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS() { return CL.CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS; }
	@Override public int CL_DEVICE_MAX_WORK_ITEM_SIZES() { return CL.CL_DEVICE_MAX_WORK_ITEM_SIZES; }
	@Override public int CL_DEVICE_MAX_PARAMETER_SIZE() { return CL.CL_DEVICE_MAX_PARAMETER_SIZE; }
	@Override public int CL_DEVICE_MAX_MEM_ALLOC_SIZE() { return CL.CL_DEVICE_MAX_MEM_ALLOC_SIZE; }
	@Override public int CL_DEVICE_MEM_BASE_ADDR_ALIGN() { return CL.CL_DEVICE_MEM_BASE_ADDR_ALIGN; }
	@Override public int CL_DEVICE_GLOBAL_MEM_SIZE() { return CL.CL_DEVICE_GLOBAL_MEM_SIZE; }
	@Override public int CL_DEVICE_LOCAL_MEM_SIZE() { return CL.CL_DEVICE_LOCAL_MEM_SIZE; }
	@Override public int CL_DEVICE_HOST_UNIFIED_MEMORY() { return CL.CL_DEVICE_HOST_UNIFIED_MEMORY; }
	@Override public int CL_DEVICE_MAX_CONSTANT_BUFFER_SIZE() { return CL.CL_DEVICE_MAX_CONSTANT_BUFFER_SIZE; }
	@Override public int CL_DEVICE_GLOBAL_MEM_CACHELINE_SIZE() { return CL.CL_DEVICE_GLOBAL_MEM_CACHELINE_SIZE; }
	@Override public int CL_DEVICE_GLOBAL_MEM_CACHE_SIZE() { return CL.CL_DEVICE_GLOBAL_MEM_CACHE_SIZE; }
	@Override public int CL_DEVICE_MAX_CONSTANT_ARGS() { return CL.CL_DEVICE_MAX_CONSTANT_ARGS; }
	@Override public int CL_DEVICE_IMAGE_SUPPORT() { return CL.CL_DEVICE_IMAGE_SUPPORT; }
	@Override public int CL_DEVICE_MAX_READ_IMAGE_ARGS() { return CL.CL_DEVICE_MAX_READ_IMAGE_ARGS; }
	@Override public int CL_DEVICE_MAX_WRITE_IMAGE_ARGS() { return CL.CL_DEVICE_MAX_WRITE_IMAGE_ARGS; }
	@Override public int CL_DEVICE_IMAGE2D_MAX_WIDTH() { return CL.CL_DEVICE_IMAGE2D_MAX_WIDTH; }
	@Override public int CL_DEVICE_IMAGE2D_MAX_HEIGHT() { return CL.CL_DEVICE_IMAGE2D_MAX_HEIGHT; }
	@Override public int CL_DEVICE_IMAGE3D_MAX_WIDTH() { return CL.CL_DEVICE_IMAGE3D_MAX_WIDTH; }
	@Override public int CL_DEVICE_IMAGE3D_MAX_HEIGHT() { return CL.CL_DEVICE_IMAGE3D_MAX_HEIGHT; }
	@Override public int CL_DEVICE_IMAGE3D_MAX_DEPTH() { return CL.CL_DEVICE_IMAGE3D_MAX_DEPTH; }
	@Override public int CL_DEVICE_MAX_SAMPLERS() { return CL.CL_DEVICE_MAX_SAMPLERS; }
	@Override public int CL_DEVICE_PROFILING_TIMER_RESOLUTION() { return CL.CL_DEVICE_PROFILING_TIMER_RESOLUTION; }
	@Override public int CL_DEVICE_EXECUTION_CAPABILITIES() { return CL.CL_DEVICE_EXECUTION_CAPABILITIES; }
	@Override public int CL_DEVICE_HALF_FP_CONFIG() { return CL.CL_DEVICE_HALF_FP_CONFIG; }
	@Override public int CL_DEVICE_SINGLE_FP_CONFIG() { return CL.CL_DEVICE_SINGLE_FP_CONFIG; }
	@Override public int CL_DEVICE_DOUBLE_FP_CONFIG() { return CL.CL_DEVICE_DOUBLE_FP_CONFIG; }
	@Override public int CL_DEVICE_LOCAL_MEM_TYPE() { return CL.CL_DEVICE_LOCAL_MEM_TYPE; }
	@Override public int CL_DEVICE_GLOBAL_MEM_CACHE_TYPE() { return CL.CL_DEVICE_GLOBAL_MEM_CACHE_TYPE; }
	@Override public int CL_DEVICE_QUEUE_PROPERTIES() { return CL.CL_DEVICE_QUEUE_PROPERTIES; }
	@Override public int CL_DEVICE_AVAILABLE() { return CL.CL_DEVICE_AVAILABLE; }
	@Override public int CL_DEVICE_COMPILER_AVAILABLE() { return CL.CL_DEVICE_COMPILER_AVAILABLE; }
	@Override public int CL_DEVICE_ENDIAN_LITTLE() { return CL.CL_DEVICE_ENDIAN_LITTLE; }
	@Override public int CL_DEVICE_ERROR_CORRECTION_SUPPORT() { return CL.CL_DEVICE_ERROR_CORRECTION_SUPPORT; }
	@Override public int CL_EXEC_KERNEL() { return CL.CL_EXEC_KERNEL; }
	@Override public int CL_EXEC_NATIVE_KERNEL() { return CL.CL_EXEC_NATIVE_KERNEL; }
	@Override public int CL_FP_DENORM() { return CL.CL_FP_DENORM; }
	@Override public int CL_FP_INF_NAN() { return CL.CL_FP_INF_NAN; }
	@Override public int CL_FP_ROUND_TO_NEAREST() { return CL.CL_FP_ROUND_TO_NEAREST; }
	@Override public int CL_FP_ROUND_TO_INF() { return CL.CL_FP_ROUND_TO_INF; }
	@Override public int CL_FP_ROUND_TO_ZERO() { return CL.CL_FP_ROUND_TO_ZERO; }
	@Override public int CL_FP_FMA() { return CL.CL_FP_FMA; }
//	@Override public int CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE() { }
//	@Override public int CL_QUEUE_PROFILING_ENABLE() { }

	// Memory
	@Override public void clReleaseMemObject(Long id) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clReleaseMemObject(id)); } }
	//	@Override public int[] getConfigs(int flags) { }
	@Override public void addListener(Long id, Memory.MemoryListener listener, int priority) { }
	@Override public void removeListener(Long id, Memory.MemoryListener listener) { }

	@Override public String METHOD_clGetMemObjectInfo() { return "clGetMemObjectInfo"; }
	@Override public int CL_MEM_READ_WRITE() { return CL.CL_MEM_READ_WRITE; }
	@Override public int CL_MEM_READ_ONLY() { return CL.CL_MEM_READ_ONLY; }
	@Override public int CL_MEM_WRITE_ONLY() { return CL.CL_MEM_WRITE_ONLY; }
	@Override public int CL_MEM_USE_HOST_PTR() { return CL.CL_MEM_USE_HOST_PTR; }
	@Override public int CL_MEM_ALLOC_HOST_PTR() { return CL.CL_MEM_ALLOC_HOST_PTR; }
	@Override public int CL_MEM_COPY_HOST_PTR() { return CL.CL_MEM_COPY_HOST_PTR; }
	@Override public int CL_MEM_MAP_COUNT() { return CL.CL_MEM_MAP_COUNT; }
	@Override public int CL_MEM_SIZE() { return CL.CL_MEM_SIZE; }

	// Platform
	@Override public Long[] getDeviceIDs(Long id, int type) {
		try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) {
			IntBuffer deviceCount = callBuffer.getStack().allocateIntBuffer(1);
			callBuffer.putError(cl.clGetDeviceIDs(id, type, 0, null, deviceCount));
			callBuffer.checkError(); deviceCount.rewind();

			PointerBuffer devicesByte = callBuffer.getStack().allocatePointerBuffer(deviceCount.get(0));
			callBuffer.putError(cl.clGetDeviceIDs(id, type, devicesByte.remaining(), callBuffer.wrapJogampPointer(devicesByte), deviceCount));
			callBuffer.checkError(); deviceCount.rewind();

			Long[] result = new Long[deviceCount.get(0)];
			for(int i = 0; i < result.length; i++) result[i] = devicesByte.get(i); return result;
		}
	}

	@Override public String METHOD_clGetPlatformInfo() { return "clGetPlatformInfo"; }
	@Override public int CL_PLATFORM_VERSION() { return CL.CL_PLATFORM_VERSION; }
	@Override public int CL_PLATFORM_NAME() { return CL.CL_PLATFORM_NAME; }
	@Override public int CL_PLATFORM_PROFILE() { return CL.CL_PLATFORM_PROFILE; }
	@Override public int CL_PLATFORM_VENDOR() { return CL.CL_PLATFORM_VENDOR; }
	@Override public int CL_PLATFORM_ICD_SUFFIX_KHR() { return CL.CL_PLATFORM_ICD_SUFFIX_KHR; }
	@Override public int CL_PLATFORM_EXTENSIONS() { return CL.CL_PLATFORM_EXTENSIONS; }
	@Override public int CL_DEVICE_TYPE_ALL() { return (int) CL.CL_DEVICE_TYPE_ALL; }

	// Kernel
	@Override public void clReleaseKernel(Long id) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clReleaseKernel(id)); } }
	@Override public void clSetKernelArg(Long id, int index, int size, java.nio.Buffer buffer) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clSetKernelArg(id, index, size, buffer)); } }

	@Override public String METHOD_clGetKernelInfo() { return "clGetKernelInfo"; }
	@Override public String METHOD_clGetKernelWorkGroupInfo() { return "clGetKernelWorkGroupInfo"; }
	@Override public int CL_KERNEL_FUNCTION_NAME() { return CL.CL_KERNEL_FUNCTION_NAME; }
	@Override public int CL_KERNEL_NUM_ARGS() { return CL.CL_KERNEL_NUM_ARGS; }
	@Override public int CL_KERNEL_LOCAL_MEM_SIZE() { return CL.CL_KERNEL_LOCAL_MEM_SIZE; }
	@Override public int CL_KERNEL_WORK_GROUP_SIZE() { return CL.CL_KERNEL_WORK_GROUP_SIZE; }
	@Override public int CL_KERNEL_COMPILE_WORK_GROUP_SIZE() { return CL.CL_KERNEL_COMPILE_WORK_GROUP_SIZE; }

	// Program
	@Override public Long createProgram(String[] code) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { return cl.clCreateProgramWithSource(getId(), 1, new String[] { StringUtils.merge(System.lineSeparator(), code) }, null, callBuffer.getErrorBuffer()); } }
	@Override public void clBuildProgram(Long id, Device<Long>[] devices, String options) {
		try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) {
			callBuffer.ensureCapacity((devices != null ? devices.length : 0) * Long.BYTES + (options.length() + 1));
			PointerBuffer deviceIds = null;
			if(devices != null && devices.length > 0) {
				deviceIds = callBuffer.getStack().allocatePointerBuffer(devices.length);
				for(int i = 0; i < deviceIds.capacity(); i++) deviceIds.put(i, devices[i].getId());
			}
			callBuffer.putError(cl.clBuildProgram(id, deviceIds == null ? 0 : deviceIds.capacity(), callBuffer.wrapJogampPointer(deviceIds), options, null));
		}
	}
	@Override public void clReleaseProgram(Long id) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clReleaseProgram(id)); } }
	@Override public void createKernelsInProgram(Long id, int length, PointerBuffer buffer, IntBuffer valueSize) { try(OpenCLUtils.CallBuffer callBuffer = OpenCLUtils.getCallBuffer()) { callBuffer.putError(cl.clCreateKernelsInProgram(id, Math.min(buffer == null ? 0 : buffer.remaining(), length * Long.BYTES), callBuffer.wrapJogampPointer(buffer), valueSize)); } }

	@Override public String METHOD_clGetProgramBuildInfo() { return "clGetProgramBuildInfo"; }
	@Override public String METHOD_clGetProgramInfo() { return "clGetProgramInfo"; }
	@Override public int CL_PROGRAM_DEVICES() { return CL.CL_PROGRAM_DEVICES; }
	@Override public int CL_PROGRAM_BUILD_LOG() { return CL.CL_PROGRAM_BUILD_LOG; }
	@Override public int CL_PROGRAM_BUILD_STATUS() { return CL.CL_PROGRAM_BUILD_STATUS; }
	@Override public int CL_BUILD_SUCCESS() { return CL.CL_BUILD_SUCCESS; }
	@Override public int CL_PROGRAM_BINARY_SIZES() { return CL.CL_PROGRAM_BINARY_SIZES; }
	@Override public int CL_PROGRAM_BINARIES() { return CL.CL_PROGRAM_BINARIES; }
}
