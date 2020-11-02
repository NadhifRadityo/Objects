package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Platform<ID extends Number> extends OpenCLNativeHolder<ID> implements OpenCLNativeHolder.Properties {
	protected final Version version;
	protected final ArrayList<Device<ID>> devices;
	protected Set<String> extensions;

	protected Platform(CLContext<ID> cl, ID id) {
		super(cl);
		getInstance().setId(id);
		this.version = new Version(getCL().getString(getCL().METHOD_clGetPlatformInfo(), getCL().CL_PLATFORM_VERSION(), id));
		this.devices = Pool.tryBorrow(Pool.getPool(ArrayList.class));

		ID[] deviceIds = getCL().getDeviceIDs(getId(), getCL().CL_DEVICE_TYPE_ALL());
		for(ID deviceId : deviceIds) devices.add(getCL().constructDevice(deviceId));
	}

	public Version getVersion() { return version; }
	public String getName() { assertNotDead(); assertCreated(); return getCL().getString(getCL().METHOD_clGetPlatformInfo(), getCL().CL_PLATFORM_NAME(), getId()); }
	public String getProfile() { assertNotDead(); assertCreated(); return getCL().getString(getCL().METHOD_clGetPlatformInfo(), getCL().CL_PLATFORM_PROFILE(), getId()); }
	public String getVendor() { assertNotDead(); assertCreated(); return getCL().getString(getCL().METHOD_clGetPlatformInfo(), getCL().CL_PLATFORM_VENDOR(), getId()); }
	public String getICDSuffix() { assertNotDead(); assertCreated(); return getCL().getString(getCL().METHOD_clGetPlatformInfo(), getCL().CL_PLATFORM_ICD_SUFFIX_KHR(), getId()); }
	public boolean isExtensionAvailable(String extension) { return getExtensions().contains(extension); }
	public Set<String> getExtensions() { assertNotDead(); assertCreated();
		if(extensions != null) return extensions;
		Set<String> _extensions = new HashSet<>();
		Scanner scanner = new Scanner(getCL().getString(getCL().METHOD_clGetPlatformInfo(), getCL().CL_PLATFORM_EXTENSIONS(), getId()));
		while(scanner.hasNext()) _extensions.add(scanner.next());
		return extensions = Collections.unmodifiableSet(_extensions);
	}

	public boolean isVendorNVidia() { return getVendor().contains("NVIDIA Corporation"); }
	public boolean isVendorAMD() { return getVendor().contains("Advanced Micro Devices"); }

	public Device<ID>[] getDevices() { assertNotDead(); assertCreated(); return devices.toArray(new Device[0]); }
	public Device<ID>[] getDevices(int... types) {
		assertNotDead(); assertCreated(); if(types == null) return getDevices();
		ArrayList<Device<ID>> result = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
			for(Device<ID> device : devices) { int deviceType = device.getType();
				for(int type : types) if(type == deviceType) result.add(device);
			} return result.toArray(new Device[0]);
		} finally { Pool.returnObject(ArrayList.class, result); }
	}

	public Device<ID> getMaxFlopsDevice() { return getMaxFlopsDevice(null); }
	public Device<ID> getMaxFlopsDevice(int... types) {
		Device<ID>[] devices = getDevices(types);
		Device<ID> result = null; int resultFlops = 0;
		for(Device<ID> device : devices) {
			int flops = device.getMaxComputeUnits() * device.getMaxClockFrequency();
			if(result != null && flops <= resultFlops) continue; result = device; resultFlops = flops;
		} return result;
	}

	@Override protected void arrange() throws Exception { throw new IllegalAccessException(); }
	@Override protected void disarrange() {
		boolean isParentCaller = false;
		String parentClassName = CLContext.class.getCanonicalName();
		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		for(int i = 1; i <= 5; i++) {
			if(!traces[i].getClassName().equals(parentClassName) || !traces[i].getMethodName().equals("disarrange")) continue;
			isParentCaller = true; break;
		} if(!isParentCaller) throw new IllegalStateException();

		for(Device<ID> device : devices.toArray(new Device[0]))
			if(device.getId() != null && !device.isDead()) device.destroy();
		Pool.returnObject(ArrayList.class, devices);
	}
}
