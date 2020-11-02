package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import io.github.NadhifRadityo.Objects.Utilizations.BitwiseUtils;

import java.nio.ByteOrder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Device<ID extends Number> extends OpenCLNativeHolder<ID> implements OpenCLNativeHolder.Properties {
	protected Set<String> extensions;

	protected Device(CLContext<ID> cl, ID id) {
		super(cl);
		getInstance().setId(id);
	}

	public String getName() { assertNotDead(); assertCreated(); return getCL().getString(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_NAME(), getId()); }
	public String getProfile() { assertNotDead(); assertCreated(); return getCL().getString(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_PROFILE(), getId()); }
	public String getVendor() { assertNotDead(); assertCreated(); return getCL().getString(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_VENDOR(), getId()); }
	public long getVendorID() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_VENDOR_ID(), getId()); }
	public Version getVersion() { assertNotDead(); assertCreated(); return new Version(getCL().getString(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_VERSION(), getId())); }
	public Version getCVersion() { assertNotDead(); assertCreated(); return new Version(getCL().getString(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_OPENCL_C_VERSION(), getId())); }
	public String getDriverVersion() { assertNotDead(); assertCreated(); return getCL().getString(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DRIVER_VERSION(), getId()); }
	public int getType() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_TYPE(), getId()); }
	public int getAddressBits() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_ADDRESS_BITS(), getId()); }
	public int getPreferredShortVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_PREFERRED_VECTOR_WIDTH_SHORT(), getId()); }
	public int getPreferredCharVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_PREFERRED_VECTOR_WIDTH_CHAR(), getId()); }
	public int getPreferredIntVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_PREFERRED_VECTOR_WIDTH_INT(), getId()); }
	public int getPreferredLongVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_PREFERRED_VECTOR_WIDTH_LONG(), getId()); }
	public int getPreferredFloatVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_PREFERRED_VECTOR_WIDTH_FLOAT(), getId()); }
	public int getPreferredDoubleVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_PREFERRED_VECTOR_WIDTH_DOUBLE(), getId()); }
	public int getNativeCharVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_NATIVE_VECTOR_WIDTH_CHAR(), getId()); }
	public int getNativeShortVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_NATIVE_VECTOR_WIDTH_SHORT(), getId()); }
	public int getNativeIntVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_NATIVE_VECTOR_WIDTH_INT(), getId()); }
	public int getNativeLongVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_NATIVE_VECTOR_WIDTH_LONG(), getId()); }
	public int getNativeHalfVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_NATIVE_VECTOR_WIDTH_HALF(), getId()); }
	public int getNativeFloatVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_NATIVE_VECTOR_WIDTH_FLOAT(), getId()); }
	public int getNativeDoubleVectorWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_NATIVE_VECTOR_WIDTH_DOUBLE(), getId()); }
	public int getMaxComputeUnits() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MAX_COMPUTE_UNITS(), getId()); }
	public int getMaxWorkGroupSize() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MAX_WORK_GROUP_SIZE(), getId()); }
	public int getMaxClockFrequency() { assertNotDead(); assertCreated(); return (int) (getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MAX_CLOCK_FREQUENCY(), getId())); }
	public int getMaxWorkItemDimensions() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS(), getId()); }
	public int[] getMaxWorkItemSizes() { assertNotDead(); assertCreated(); return getCL().getInts(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MAX_WORK_ITEM_SIZES(), getId(), getMaxWorkItemDimensions()); }
	public long getMaxParameterSize() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MAX_PARAMETER_SIZE(), getId()); }
	public long getMaxMemAllocSize() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MAX_MEM_ALLOC_SIZE(), getId()); }
	public long getMemBaseAddrAlign() { assertNotDead(); assertCreated(); return getCL().getUInt32Long(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MEM_BASE_ADDR_ALIGN(), getId()); }
	public long getGlobalMemSize() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_GLOBAL_MEM_SIZE(), getId()); }
	public long getLocalMemSize() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_LOCAL_MEM_SIZE(), getId()); }
	public boolean isMemoryUnified() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_HOST_UNIFIED_MEMORY(), getId()) == getCL().CL_TRUE(); }
	public long getMaxConstantBufferSize() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MAX_CONSTANT_BUFFER_SIZE(), getId()); }
	public long getGlobalMemCachelineSize() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_GLOBAL_MEM_CACHELINE_SIZE(), getId()); }
	public long getGlobalMemCacheSize() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_GLOBAL_MEM_CACHE_SIZE(), getId()); }
	public long getMaxConstantArgs() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MAX_CONSTANT_ARGS(), getId()); }
	public boolean isImageSupportAvailable() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_IMAGE_SUPPORT(), getId()) == getCL().CL_TRUE(); }
	public int getMaxReadImageArgs() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MAX_READ_IMAGE_ARGS(), getId()); }
	public int getMaxWriteImageArgs() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MAX_WRITE_IMAGE_ARGS(), getId()); }
	public int getMaxImage2dWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_IMAGE2D_MAX_WIDTH(), getId()); }
	public int getMaxImage2dHeight() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_IMAGE2D_MAX_HEIGHT(), getId()); }
	public int getMaxImage3dWidth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_IMAGE3D_MAX_WIDTH(), getId()); }
	public int getMaxImage3dHeight() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_IMAGE3D_MAX_HEIGHT(), getId()); }
	public int getMaxImage3dDepth() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_IMAGE3D_MAX_DEPTH(), getId()); }
	public int getMaxSamplers() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_MAX_SAMPLERS(), getId()); }
	public long getProfilingTimerResolution() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_PROFILING_TIMER_RESOLUTION(), getId()); }
	public int[] getExecutionCapabilities() { assertNotDead(); assertCreated(); return BitwiseUtils.extract((int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_EXECUTION_CAPABILITIES(), getId()), getCL().CL_EXEC_KERNEL(), getCL().CL_EXEC_NATIVE_KERNEL()); }
	public int[] getHalfFPConfig() { assertNotDead(); assertCreated(); if(!isHalfFPAvailable()) return new int[0]; return BitwiseUtils.extract((int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_HALF_FP_CONFIG(), getId()), getCL().CL_FP_DENORM(), getCL().CL_FP_INF_NAN(), getCL().CL_FP_ROUND_TO_NEAREST(), getCL().CL_FP_ROUND_TO_INF(), getCL().CL_FP_ROUND_TO_ZERO(), getCL().CL_FP_FMA()); }
	public int[] getSingleFPConfig() { assertNotDead(); assertCreated(); return BitwiseUtils.extract((int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_SINGLE_FP_CONFIG(), getId()), getCL().CL_FP_DENORM(), getCL().CL_FP_INF_NAN(), getCL().CL_FP_ROUND_TO_NEAREST(), getCL().CL_FP_ROUND_TO_INF(), getCL().CL_FP_ROUND_TO_ZERO(), getCL().CL_FP_FMA()); }
	public int[] getDoubleFPConfig() { assertNotDead(); assertCreated(); if(!isDoubleFPAvailable()) return new int[0]; return BitwiseUtils.extract((int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_DOUBLE_FP_CONFIG(), getId()), getCL().CL_FP_DENORM(), getCL().CL_FP_INF_NAN(), getCL().CL_FP_ROUND_TO_NEAREST(), getCL().CL_FP_ROUND_TO_INF(), getCL().CL_FP_ROUND_TO_ZERO(), getCL().CL_FP_FMA()); }
	public int getLocalMemType() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_LOCAL_MEM_TYPE(), getId()); }
	public int getGlobalMemCacheType() { assertNotDead(); assertCreated(); return (int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_GLOBAL_MEM_CACHE_TYPE(), getId()); }
	public int[] getQueueProperties() { assertNotDead(); assertCreated(); return BitwiseUtils.extract((int) getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_QUEUE_PROPERTIES(), getId()), getCL().CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE(), getCL().CL_QUEUE_PROFILING_ENABLE()); }
	public ByteOrder getByteOrder() { return isLittleEndian() ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN; }
	public boolean isAvailable() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_AVAILABLE(), getId()) == getCL().CL_TRUE(); }
	public boolean isCompilerAvailable() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_COMPILER_AVAILABLE(), getId()) == getCL().CL_TRUE(); }
	public boolean isLittleEndian() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_ENDIAN_LITTLE(), getId()) == getCL().CL_TRUE(); }
	public boolean isErrorCorrectionSupported() { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_ERROR_CORRECTION_SUPPORT(), getId()) == getCL().CL_TRUE(); }
	public boolean isHalfFPAvailable() { return isExtensionAvailable("cl_khr_fp16"); }
	public boolean isDoubleFPAvailable() { return isExtensionAvailable("cl_khr_fp64"); }
	public boolean isICDAvailable() { return isExtensionAvailable("cl_khr_icd"); }
	public boolean isGLMemorySharingSupported() { return isExtensionAvailable("cl_khr_gl_sharing") || isExtensionAvailable("cl_APPLE_gl_sharing"); }
	public boolean isExtensionAvailable(String extension) { return getExtensions().contains(extension); }
	public Set<String> getExtensions() { assertNotDead(); assertCreated();
		if(extensions != null) return extensions;
		Set<String> _extensions = new HashSet<>();
		Scanner scanner = new Scanner(getCL().getString(getCL().METHOD_clGetDeviceInfo(), getCL().CL_DEVICE_EXTENSIONS(), getId()));
		while(scanner.hasNext()) _extensions.add(scanner.next());
		return extensions = Collections.unmodifiableSet(_extensions);
	}

	@Override protected void arrange() throws Exception { throw new IllegalAccessException(); }
	@Override protected void disarrange() { throw new IllegalStateException(); }
}
