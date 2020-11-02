package io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan;

import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.PointerBuffer;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ByteUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class InstanceCreateInfo extends VKStruct {

	public InstanceCreateInfo(VKContext vk, ByteBuffer buffer) { super(vk, buffer); }
	public InstanceCreateInfo(VKContext vk) { super(vk); }
	public InstanceCreateInfo(VKContext vk, BufferUtils.MemoryStack stack) { super(vk, stack); }

	@Override public int SIZEOF() { return getVK().INSTANCE_CREATE_INFO_SIZEOF(); }
	/*
	 // Provided by VK_VERSION_1_0
	typedef struct VkInstanceCreateInfo {
		VkStructureType             sType;
		const void*                 pNext;
		VkInstanceCreateFlags       flags;
		const VkApplicationInfo*    pApplicationInfo;
		uint32_t                    enabledLayerCount;
		const char* const*          ppEnabledLayerNames;
		uint32_t                    enabledExtensionCount;
		const char* const*          ppEnabledExtensionNames;
	} VkInstanceCreateInfo;
	 */

	@Value public int sType() { return getInt(getVK().INSTANCE_CREATE_INFO_sType()); }
	@Value public long pNext() { return getLong(getVK().INSTANCE_CREATE_INFO_pNext()); }
	@Value public int flags() { return getInt(getVK().INSTANCE_CREATE_INFO_flags()); }
	@Value public long pApplicationInfo() { return getLong(getVK().INSTANCE_CREATE_INFO_pApplicationInfo()); }
	@Value public ApplicationInfo pApplicationInfoObject() { ApplicationInfo result = new ApplicationInfo(getVK(), BufferUtils.pointTo(getLong(getVK().INSTANCE_CREATE_INFO_pApplicationInfo()), getVK().APPLICATION_INFO_SIZEOF())); result.create(); return result; }
	@Value public int enabledLayerCount() { return getInt(getVK().INSTANCE_CREATE_INFO_enabledLayerCount()); }
	@Value public long ppEnabledLayerNames() { return getLong(getVK().INSTANCE_CREATE_INFO_ppEnabledLayerNames()); }
	@Value public PointerBuffer ppEnabledLayerNamesObject() { return PointerBuffer.wrap(BufferUtils.__pointTo(ppEnabledLayerNames(), enabledLayerCount() * PointerBuffer.ELEMENT_SIZE)); }
	@Value public String[] ppEnabledLayerNamesArray() { String[] result = new String[enabledLayerCount()]; long address = ppEnabledLayerNames(); for(int i = 0; i < result.length; i++) result[i] = ByteUtils.toZeroTerminatedString(unsafe.getLong(address + i * PointerBuffer.ELEMENT_SIZE)); return result; }
	@Value public int enabledExtensionCount() { return getInt(getVK().INSTANCE_CREATE_INFO_enabledExtensionCount()); }
	@Value public long ppEnabledExtensionNames() { return getLong(getVK().INSTANCE_CREATE_INFO_ppEnabledExtensionNames()); }
	@Value public PointerBuffer ppEnabledExtensionNamesObject() { return PointerBuffer.wrap(BufferUtils.__pointTo(ppEnabledExtensionNames(), enabledExtensionCount() * PointerBuffer.ELEMENT_SIZE)); }
	@Value public String[] ppEnabledExtensionNamesArray() { String[] result = new String[enabledExtensionCount()]; long address = ppEnabledExtensionNames(); for(int i = 0; i < result.length; i++) result[i] = ByteUtils.toZeroTerminatedString(unsafe.getLong(address + i * PointerBuffer.ELEMENT_SIZE)); return result; }

	public InstanceCreateInfo sType(int sType) { putInt(getVK().INSTANCE_CREATE_INFO_sType(), sType); return this; }
	public InstanceCreateInfo pNext(long pNext) { putLong(getVK().INSTANCE_CREATE_INFO_pNext(), pNext); return this; }
	public InstanceCreateInfo flags(int flags) { putInt(getVK().INSTANCE_CREATE_INFO_flags(), flags); return this; }
	public InstanceCreateInfo pApplicationInfo(long pApplicationInfo) { putLong(getVK().INSTANCE_CREATE_INFO_pApplicationInfo(), pApplicationInfo); return this; }
	public InstanceCreateInfo pApplicationInfo(ApplicationInfo pApplicationInfo) { return pApplicationInfo(pApplicationInfo.getAddress()); }
	public InstanceCreateInfo enabledLayerCount(int enabledLayerCount) { putInt(getVK().INSTANCE_CREATE_INFO_enabledLayerCount(), enabledLayerCount); return this; }
	public InstanceCreateInfo ppEnabledLayerNames(long ppEnabledLayerNames) { putLong(getVK().INSTANCE_CREATE_INFO_ppEnabledLayerNames(), ppEnabledLayerNames); return this; }
	public InstanceCreateInfo ppEnabledLayerNames(PointerBuffer ppEnabledLayerNames) { ppEnabledLayerNames(BufferUtils.__getAddress(ppEnabledLayerNames)); return enabledLayerCount(ppEnabledLayerNames.capacity()); }
	public InstanceCreateInfo enabledExtensionCount(int enabledExtensionCount) { putInt(getVK().INSTANCE_CREATE_INFO_enabledExtensionCount(), enabledExtensionCount); return this; }
	public InstanceCreateInfo ppEnabledExtensionNames(long ppEnabledExtensionNames) { putLong(getVK().INSTANCE_CREATE_INFO_ppEnabledExtensionNames(), ppEnabledExtensionNames); return this; }
	public InstanceCreateInfo ppEnabledExtensionNames(PointerBuffer ppEnabledExtensionNames) { ppEnabledExtensionNames(BufferUtils.__getAddress(ppEnabledExtensionNames)); return enabledExtensionCount(ppEnabledExtensionNames.capacity()); }
}
