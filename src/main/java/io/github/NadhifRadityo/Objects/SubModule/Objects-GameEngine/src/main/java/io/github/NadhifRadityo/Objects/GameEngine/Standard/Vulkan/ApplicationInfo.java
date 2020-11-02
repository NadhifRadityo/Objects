package io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan;

import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ByteUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class ApplicationInfo extends VKStruct {

	public ApplicationInfo(VKContext vk, ByteBuffer buffer) { super(vk, buffer); }
	public ApplicationInfo(VKContext vk) { super(vk); }
	public ApplicationInfo(VKContext vk, BufferUtils.MemoryStack stack) { super(vk, stack); }

	@Override public int SIZEOF() { return getVK().APPLICATION_INFO_SIZEOF(); }
	/*
	 // Provided by VK_VERSION_1_0
	typedef struct VkApplicationInfo {
		VkStructureType    sType;
		const void*        pNext;
		const char*        pApplicationName;
		uint32_t           applicationVersion;
		const char*        pEngineName;
		uint32_t           engineVersion;
		uint32_t           apiVersion;
	} VkApplicationInfo;
	 */

	@Value public int sType() { return getInt(getVK().APPLICATION_INFO_sType()); }
	@Value public long pNext() { return getLong(getVK().APPLICATION_INFO_pNext()); }
	@Value public long pApplicationName() { return getLong(getVK().APPLICATION_INFO_pApplicationName()); }
	@Value public String pApplicationNameString() { return ByteUtils.toZeroTerminatedString(pApplicationName()); }
	@Value public int applicationVersion() { return getInt(getVK().APPLICATION_INFO_applicationVersion()); }
	@Value public long pEngineName() { return getLong(getVK().APPLICATION_INFO_pEngineName()); }
	@Value public String pEngineNameString() { return ByteUtils.toZeroTerminatedString(pEngineName()); }
	@Value public int engineVersion() { return getInt(getVK().APPLICATION_INFO_engineVersion()); }
	@Value public int apiVersion() { return getInt(getVK().APPLICATION_INFO_apiVersion()); }

	public ApplicationInfo sType(int sType) { putInt(getVK().APPLICATION_INFO_sType(), sType); return this; }
	public ApplicationInfo pNext(long pNext) { putLong(getVK().APPLICATION_INFO_pNext(), pNext); return this; }
	public ApplicationInfo pApplicationName(long pApplicationName) { putLong(getVK().APPLICATION_INFO_pApplicationName(), pApplicationName); return this; }
	public ApplicationInfo pApplicationName(Buffer pApplicationName) { return pApplicationName(BufferUtils.__getAddress(pApplicationName)); }
	public ApplicationInfo applicationVersion(int applicationVersion) { putInt(getVK().APPLICATION_INFO_applicationVersion(), applicationVersion); return this; }
	public ApplicationInfo pEngineName(long pEngineName) { putLong(getVK().APPLICATION_INFO_pEngineName(), pEngineName); return this; }
	public ApplicationInfo pEngineName(Buffer pEngineName) { return pEngineName(BufferUtils.__getAddress(pEngineName)); }
	public ApplicationInfo engineVersion(int engineVersion) { putInt(getVK().APPLICATION_INFO_engineVersion(), engineVersion); return this; }
	public ApplicationInfo apiVersion(int apiVersion) { putInt(getVK().APPLICATION_INFO_apiVersion(), apiVersion); return this; }
}
