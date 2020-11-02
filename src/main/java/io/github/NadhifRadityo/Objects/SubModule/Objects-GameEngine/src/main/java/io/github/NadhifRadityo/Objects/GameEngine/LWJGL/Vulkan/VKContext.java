package io.github.NadhifRadityo.Objects.GameEngine.LWJGL.Vulkan;

import io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan.VulkanNativeHolderProvider;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkInstanceCreateInfo;

public class VKContext extends io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan.VKContext {
	public VKContext() {
		super(null);
	}

	// ETC*STATIC&NON STATIC*
	@Override public VulkanNativeHolderProvider constructNativeProviderInstance() { return new io.github.NadhifRadityo.Objects.GameEngine.LWJGL.Vulkan.VulkanNativeHolderProvider(); }

	// ApplicationInfo
	@Override public int APPLICATION_INFO_SIZEOF() { return VkApplicationInfo.SIZEOF; }
	@Override public int APPLICATION_INFO_sType() { return VkApplicationInfo.STYPE; }
	@Override public int APPLICATION_INFO_pNext() { return VkApplicationInfo.PNEXT; }
	@Override public int APPLICATION_INFO_pApplicationName() { return VkApplicationInfo.PAPPLICATIONNAME; }
	@Override public int APPLICATION_INFO_applicationVersion() { return VkApplicationInfo.APPLICATIONVERSION; }
	@Override public int APPLICATION_INFO_pEngineName() { return VkApplicationInfo.PENGINENAME; }
	@Override public int APPLICATION_INFO_engineVersion() { return VkApplicationInfo.ENGINEVERSION; }
	@Override public int APPLICATION_INFO_apiVersion() { return VkApplicationInfo.APIVERSION; }
	@Override public int VK_STRUCTURE_TYPE_APPLICATION_INFO() { return VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO; }

	// InstanceCreateInfo
	@Override public int INSTANCE_CREATE_INFO_SIZEOF() { return VkInstanceCreateInfo.SIZEOF; }
	@Override public int INSTANCE_CREATE_INFO_sType() { return VkInstanceCreateInfo.STYPE; }
	@Override public int INSTANCE_CREATE_INFO_pNext() { return VkInstanceCreateInfo.PNEXT; }
	@Override public int INSTANCE_CREATE_INFO_flags() { return VkInstanceCreateInfo.FLAGS; }
	@Override public int INSTANCE_CREATE_INFO_pApplicationInfo() { return VkInstanceCreateInfo.PAPPLICATIONINFO; }
	@Override public int INSTANCE_CREATE_INFO_enabledLayerCount() { return VkInstanceCreateInfo.ENABLEDLAYERCOUNT; }
	@Override public int INSTANCE_CREATE_INFO_ppEnabledLayerNames() { return VkInstanceCreateInfo.PPENABLEDLAYERNAMES; }
	@Override public int INSTANCE_CREATE_INFO_enabledExtensionCount() { return VkInstanceCreateInfo.ENABLEDEXTENSIONCOUNT; }
	@Override public int INSTANCE_CREATE_INFO_ppEnabledExtensionNames() { return VkInstanceCreateInfo.PPENABLEDEXTENSIONNAMES; }
	@Override public int VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO() { return VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO; }
}
