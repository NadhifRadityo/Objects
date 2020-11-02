package io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan;

import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan.Objects.VulkanObjectHolder;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Proxy;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Write1TimeProxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

import java.lang.reflect.Field;

import static io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan.VulkanNativeHolder.VKHolder;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class VulkanNativeHolderProvider implements DeadableObject {
	protected final Write1TimeProxy<VKHolder> vkHolder;
	private final Write1TimeProxy<Boolean> isCreated;
	protected boolean isDead = false;

	protected VulkanNativeHolderProvider() {
		this.vkHolder = Pool.tryBorrow(Pool.getPool(Write1TimeProxy.class));
		this.isCreated = Pool.tryBorrow(Pool.getPool(Write1TimeProxy.class));
	}

	protected VKHolder getVKHolder() { assertNotDead(); return vkHolder.get(); }
	public VKContext getVK() { assertNotDead(); return getVKHolder().getVK(); }
	public boolean isCreated() { assertNotDead(); return isCreated.get() != null && isCreated.get(); }
	public void setCreated() { assertNotDead(); isCreated.set(true); }
	public boolean isContextSame(VulkanNativeHolderProvider holder) { return isContextSame(this, holder); }
	public boolean isContextSame(VulkanObjectHolder holder) { return isContextSame(this, holder); }
	protected void assertValid(VulkanNativeHolderProvider holder) { assertContextSame(holder); holder.assertNotDead("holder"); holder.assertCreated(); }
	protected void assertValid(VulkanObjectHolder holder) { assertContextSame(holder); holder.assertNotDead("holder"); holder.assertCreated(); }

	public final void create(ThrowsRunnable arrange, Runnable disarrange) { assertNotDead(); assertNotCreated(); try { arrange.run(); } catch(Exception e) { if(isCreated.get() == null || !isCreated.get()) markInvalidId(this); disarrange.run(); throw e instanceof VKException ? (VKException) e : new VKException(e); } }
	public final void destroy(Runnable disarrange) { assertNotDead(); assertCreated(); try { disarrange.run(); } finally { Pool.returnObject(Write1TimeProxy.class, vkHolder); Pool.returnObject(Write1TimeProxy.class, isCreated); isDead = true; } }

	protected void assertContextSame(VulkanNativeHolderProvider holder) { if(!isContextSame(holder)) newException("Different context!"); }
	protected void assertContextNotSame(VulkanNativeHolderProvider holder) { if(isContextSame(holder)) newException("Same context!"); }
	protected void assertContextSame(VulkanObjectHolder holder) { if(!isContextSame(holder)) newException("Different context!"); }
	protected void assertContextNotSame(VulkanObjectHolder holder) { if(isContextSame(holder)) newException("Same context!"); }
	public void assertCreated() { if(!isCreated()) newException("Program is not created!"); }
	public void assertNotCreated() { if(isCreated()) newException("Program is already created!"); }
	@Override public void setDead() { newException("Illegal operation!"); }
	@Override public boolean isDead() { return isDead; }

	protected static VKException newException(String msg, Exception e) { return VulkanNativeHolder.newException(msg, e); }
	protected static VKException newException(String msg) { return VulkanNativeHolder.newException(msg); }
	protected static VKException newException(Exception e) { return VulkanNativeHolder.newException(e); }
	protected static boolean isContextSame(VulkanNativeHolderProvider holder1, VulkanNativeHolderProvider holder2) { return holder1.getVK().getVK() == holder2.getVK().getVK(); }
	protected static boolean isContextSame(VulkanNativeHolderProvider holder1, VulkanObjectHolder holder2) { return holder1.getVK().getVK() == holder2.getVK().getVK(); }

	private static void markInvalidId(VulkanNativeHolderProvider instance) { ExceptionUtils.doSilentThrowsRunnable(true, () -> {
		Field field = Proxy.class.getDeclaredField("object");
		field.setAccessible(true); field.set(instance.isCreated, true);
	}); }
}
