package io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan;

public abstract class VKContext {
	protected final Object vk;

	public VKContext(Object vk) {
		this.vk = vk;
	}

	public Object getVK() { return vk; }
	protected void assertVKNull() { if(vk != null) throw newException("Vulkan is not null"); }
	protected void assertVKNotNull() { if(vk == null) throw newException("Vulkan is null"); }
	protected void assertStatic() { assertVKNull(); }
	protected void assertNotStatic() { assertVKNotNull(); }
	@Override public boolean equals(Object o) { if(this == o) return true; if(o == null) return false;
		if(!o.getClass().getCanonicalName().startsWith(getClass().getCanonicalName())) return false;
		VKContext vkContext = (VKContext) o; return getVK().equals(vkContext.getVK());
	}

	// ETC*STATIC&NON STATIC*
	public abstract VulkanNativeHolderProvider constructNativeProviderInstance();

	// Info

	// ApplicationInfo
	public abstract int APPLICATION_INFO_SIZEOF();
	public abstract int APPLICATION_INFO_sType();
	public abstract int APPLICATION_INFO_pNext();
	public abstract int APPLICATION_INFO_pApplicationName();
	public abstract int APPLICATION_INFO_applicationVersion();
	public abstract int APPLICATION_INFO_pEngineName();
	public abstract int APPLICATION_INFO_engineVersion();
	public abstract int APPLICATION_INFO_apiVersion();
	public abstract int VK_STRUCTURE_TYPE_APPLICATION_INFO();

	// InstanceCreateInfo
	public abstract int INSTANCE_CREATE_INFO_SIZEOF();
	public abstract int INSTANCE_CREATE_INFO_sType();
	public abstract int INSTANCE_CREATE_INFO_pNext();
	public abstract int INSTANCE_CREATE_INFO_flags();
	public abstract int INSTANCE_CREATE_INFO_pApplicationInfo();
	public abstract int INSTANCE_CREATE_INFO_enabledLayerCount();
	public abstract int INSTANCE_CREATE_INFO_ppEnabledLayerNames();
	public abstract int INSTANCE_CREATE_INFO_enabledExtensionCount();
	public abstract int INSTANCE_CREATE_INFO_ppEnabledExtensionNames();
	public abstract int VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO();

	public static VKException newException(String msg, Exception e) { throw new VKException(msg, e); }
	public static VKException newException(String msg) { throw new VKException(msg); }
	public static VKException newException(Exception e) { throw new VKException(e); }
}
