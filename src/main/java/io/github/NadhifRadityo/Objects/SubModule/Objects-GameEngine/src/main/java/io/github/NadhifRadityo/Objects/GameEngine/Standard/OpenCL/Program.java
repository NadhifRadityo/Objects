package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.PointerBuffer;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.FileUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.Utilizations.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Program<ID extends Number> extends OpenCLNativeHolder<ID> {
	protected final String[] code;
	protected final String options;
	protected final HashMap<String, Kernel<ID>> kernels;
	protected final ArrayList<Device<ID>> devices;

	protected boolean executable;

	public Program(CLContext<ID> cl, String[] code, String options, Device<ID>[] devices) {
		super(cl);
		this.code = code;
		this.options = options;
		this.kernels = Pool.tryBorrow(Pool.getPool(HashMap.class));
		this.devices = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		if(devices != null) this.devices.addAll(Arrays.asList(devices));
	}
	public Program(CLContext<ID> cl, String[] code) { this(cl, code, "", null); }
	public Program(CLContext<ID> cl, File file, Charset charset, String options, Device<ID>[] devices) throws IOException { this(cl, FileUtils.getFileString(file, charset).split(System.lineSeparator()), options, devices); }
	public Program(CLContext<ID> cl, File file, Charset charset) throws IOException { this(cl, file, charset, "", null); }

	public String[] getCode() { return code; }
	public String getWholeCode() { return StringUtils.merge("\n", code); }
	public Map<String, Kernel<ID>> getKernels() { assertNotDead(); assertCreated(); return Collections.unmodifiableMap(kernels); }
	public Kernel<ID> getKernel(String name) { assertNotDead(); assertCreated(); return kernels.get(name); }

	public Device<ID>[] getCLDevices() { assertNotDead(); assertCreated(); return devices.toArray(new Device[0]); }
	public String getBuildLog(Device<ID> device) { assertNotDead(); assertCreated(); return getCL().getString(getCL().METHOD_clGetProgramBuildInfo(), getCL().CL_PROGRAM_BUILD_LOG(), getId(), device.getId()); }
	public String getBuildLog() { assertNotDead(); assertCreated();
		StringBuilder result = Pool.tryBorrow(Pool.getPool(StringBuilder.class)); try {
			for(int i = 0; i < devices.size(); i++) {
				Device<ID> device = devices.get(i);
				result.append(devices).append(" build log:\n");
				String buildLog = getBuildLog(device).trim();
				result.append(buildLog.isEmpty() ? "    <empty>" : buildLog);
				if(i != devices.size() - 1) result.append("\n");
			} return result.toString();
		} finally { Pool.returnObject(StringBuilder.class, result); }
	}
	public int getBuildStatus(Device<ID> device) { assertNotDead(); assertCreated(); return (int) getCL().getUInt32Long(getCL().METHOD_clGetProgramBuildInfo(), getCL().CL_PROGRAM_BUILD_STATUS(), getId(), device.getId()); }
	public Map<Device<ID>, Integer> getBuildStatus() { assertNotDead(); assertCreated();
		Map<Device<ID>, Integer> result = new HashMap<>();
		for(Device<ID> device : devices) result.put(device, getBuildStatus(device));
		return result;
	}
	public boolean isExecutable() { assertNotDead(); assertCreated();
		if(executable) return true; for(Integer status : getBuildStatus().values())
			if(status == getCL().CL_BUILD_SUCCESS()) return executable = true;
		return false;
	}
	public Map<Device<ID>, byte[]> getBinaries() { assertNotDead(); assertCreated();
		PointerBuffer sizes = BufferUtils.allocatePointerBuffer(devices.size());
		getCL().getInfo(getCL().METHOD_clGetProgramInfo(), getCL().CL_PROGRAM_BINARY_SIZES(), getId(), sizes.capacity() * sizes.elementSize(), sizes.getBuffer(), null);
		int binariesSize = 0; while(sizes.remaining() != 0) binariesSize += (int) sizes.get();
		ByteBuffer binaries = BufferUtils.allocateByteBuffer(binariesSize);
		long address = PrivilegedUtils.doPrivileged(() -> BufferUtils.getAddress(binaries));

		PointerBuffer addresses = BufferUtils.allocatePointerBuffer(sizes.capacity());
		sizes.rewind(); while(sizes.remaining() != 0) { addresses.put(address); address += sizes.get(); } addresses.rewind();
		getCL().getInfo(getCL().METHOD_clGetProgramInfo(), getCL().CL_PROGRAM_BINARIES(), getId(), addresses.capacity() * addresses.elementSize(), addresses.getBuffer(), null); try {
		Map<Device<ID>, byte[]> map = new LinkedHashMap<>(); sizes.rewind(); for(int i = 0; i < devices.size(); i++) {
			byte[] bytes = new byte[(int) sizes.get()]; binaries.get(bytes); map.put(devices.get(i), bytes);
		} return map; } finally { BufferUtils.deallocate(sizes); BufferUtils.deallocate(binaries); BufferUtils.deallocate(addresses); }
	}

	protected void attachKernel(Kernel<ID> kernel) {
		assertNotDead(); assertCreated(); kernel.assertNotDead("kernel"); assertContextSame(kernel);
		kernels.put(kernel.getName(), kernel);
	}
	protected void detachKernel(Kernel<ID> kernel) {
		assertNotDead(); assertCreated(); kernel.assertNotDead("kernel"); assertContextSame(kernel);
		kernels.remove(kernel.getName());
	}

	@Override protected void arrange() throws Exception {
		getInstance().setId(getCL().createProgram(code));
		getCL().clBuildProgram(getId(), devices.toArray(new Device[0]), options);
		devices.clear(); IntBuffer kernelCount = BufferUtils.allocateIntBuffer(1);
		getCL().createKernelsInProgram(getId(), 0, null, kernelCount);
		PointerBuffer kernelIDs = BufferUtils.allocatePointerBuffer(kernelCount.get(0));
		getCL().createKernelsInProgram(getId(), kernelIDs.capacity(), kernelIDs, null);

		try { for(int i = 0; i < kernelIDs.capacity(); i++) { Kernel<ID> kernel = getCL().constructKernel(this, (ID) ((Long) kernelIDs.get(i))); kernels.put(kernel.getName(), kernel);
		} } finally { BufferUtils.deallocate(kernelCount); BufferUtils.deallocate(kernelIDs); }

		PointerBuffer deviceCount = BufferUtils.allocatePointerBuffer(1);
		getCL().getInfo(getCL().METHOD_clGetProgramInfo(), getCL().CL_PROGRAM_DEVICES(), getId(), 0, null, deviceCount.getBuffer());
		int _deviceCount = (int) deviceCount.get(0);
		PointerBuffer deviceIDs = PointerBuffer.wrap(BufferUtils.allocateByteBuffer(_deviceCount));
		getCL().getInfo(getCL().METHOD_clGetProgramInfo(), getCL().CL_PROGRAM_DEVICES(), getId(), _deviceCount, deviceIDs.getBuffer(), null);

		try {
			for(int i = 0; i < deviceIDs.capacity(); i++)
				devices.add(getCL().constructDevice((ID) (Long) deviceIDs.get(i)));
		} finally { BufferUtils.deallocate(deviceCount); BufferUtils.deallocate(deviceIDs); }
	}
	@Override protected void disarrange() {
		for(Kernel<ID> kernel : kernels.values().toArray(new Kernel[0]))
			if(kernel.getId() != null && !kernel.isDead()) kernel.destroy();
//		for(Device<ID> device : devices.toArray(new Device[0]))
//			if(device.getId() != null && !device.isDead()) device.destroy();
		getCL().clReleaseProgram(getId());
		Pool.returnObject(HashMap.class, kernels);
		Pool.returnObject(ArrayList.class, devices);
	}
}
