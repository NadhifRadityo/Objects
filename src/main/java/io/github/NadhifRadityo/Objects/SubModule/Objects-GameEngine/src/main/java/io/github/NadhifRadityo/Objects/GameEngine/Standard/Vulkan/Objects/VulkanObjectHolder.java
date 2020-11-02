package io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan.VKException;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan.VKContext;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan.VulkanNativeHolder;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Proxy;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Write1TimeProxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;

import java.lang.reflect.Field;

@SuppressWarnings("rawtypes")
public abstract class VulkanObjectHolder implements DeadableObject {
	private final VulkanNativeHolder.VKHolder vkHolder;
	private final Write1TimeProxy<Boolean> isCreated;
	private boolean isDead = false;

	public VulkanObjectHolder(VKContext vk) {
		this.vkHolder = PrivilegedUtils.doPrivileged(() -> VulkanNativeHolder.addObjInstance(this, vk));
		this.isCreated = Pool.tryBorrow(Pool.getPool(Write1TimeProxy.class));
	}

	protected VulkanNativeHolder.VKHolder getVKHolder() { return vkHolder; }
	public VKContext getVK() { return vkHolder.getVK(); }
	public boolean isCreated() { assertNotDead(); return isCreated.get() != null && isCreated.get(); }
	public void setCreated() { assertNotDead(); isCreated.set(true); }
	public boolean isContextSame(VulkanNativeHolder holder) { return isContextSame(this, holder); }
	public boolean isContextSame(VulkanObjectHolder holder) { return isContextSame(this, holder); }
	protected void assertValid(VulkanNativeHolder holder) { assertContextSame(holder); holder.assertNotDead("holder"); holder.assertCreated(); }
	protected void assertValid(VulkanObjectHolder holder) { assertContextSame(holder); holder.assertNotDead("holder"); holder.assertCreated(); }

	public final void create() { assertNotDead(); assertNotCreated(); try { this.arrange(); isCreated.set(true); } catch(Exception e) { if(isCreated.get() == null || !isCreated.get()) markInvalidId(this); destroy(); throw e instanceof VKException ? (VKException) e : new VKException(e); } }
	public final void destroy() { assertNotDead(); assertCreated(); try { if(isCreated.get() != null && isCreated.get()) disarrange(); PrivilegedUtils.doPrivileged(() -> VulkanNativeHolder.removeObjInstance(this)); } finally { Pool.returnObject(Write1TimeProxy.class, isCreated); isDead = true; } }
	protected abstract void arrange() throws Exception;
	protected abstract void disarrange();

	protected void assertContextSame(VulkanNativeHolder holder) { if(!isContextSame(holder)) newException("Different context!"); }
	protected void assertContextNotSame(VulkanNativeHolder holder) { if(isContextSame(holder)) newException("Same context!"); }
	protected void assertContextSame(VulkanObjectHolder holder) { if(!isContextSame(holder)) newException("Different context!"); }
	protected void assertContextNotSame(VulkanObjectHolder holder) { if(isContextSame(holder)) newException("Same context!"); }
	public void assertCreated() { if(!isCreated()) newException("Program is not created!"); }
	public void assertNotCreated() { if(isCreated()) newException("Program is already created!"); }
	@Override public void setDead() { destroy(); }
	@Override public boolean isDead() { return isDead; }

	protected static VKException newException(String msg, Exception e) { return VKContext.newException(msg, e); }
	protected static VKException newException(String msg) { return VKContext.newException(msg); }
	protected static VKException newException(Exception e) { return VKContext.newException(e); }
	protected static boolean isContextSame(VulkanObjectHolder holder1, VulkanNativeHolder holder2) { return holder1.getVK().getVK() == holder2.getVK().getVK(); }
	protected static boolean isContextSame(VulkanObjectHolder holder1, VulkanObjectHolder holder2) { return holder1.getVK().getVK() == holder2.getVK().getVK(); }

	private static void markInvalidId(VulkanObjectHolder instance) { ExceptionUtils.doSilentThrowsRunnable(true, () -> {
		Field field = Proxy.class.getDeclaredField("object");
		field.setAccessible(true); field.set(instance.isCreated, true);
	}); }
}
