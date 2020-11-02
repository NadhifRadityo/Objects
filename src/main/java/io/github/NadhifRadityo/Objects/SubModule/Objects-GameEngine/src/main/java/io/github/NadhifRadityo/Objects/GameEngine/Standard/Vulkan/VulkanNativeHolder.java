package io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan;

import io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan.Objects.VulkanObjectHolder;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"unchecked", "unused"})
public abstract class VulkanNativeHolder implements DeadableObject {
	private static final Map<VKContext, VKHolder> vkHolders = new HashMap<>();
	protected static VKHolder addInstance(VulkanNativeHolder nativeHolder, VKContext vk) {
		VKHolder holder = vkHolders.get(vk);
		if(holder == null) { holder = new VKHolder(vk); vkHolders.put(vk, holder); }
		holder.addInstance(nativeHolder); return holder;
	}
	protected static void removeInstance(VulkanNativeHolder nativeHolder) {
		VKHolder holder = nativeHolder.getVKHolder(); if(holder == null) return;
		holder.removeInstance(nativeHolder);
		if(holder.getInstances().length == 0) {
			vkHolders.remove(holder.getVK());
			holder.setDead();
		}
	}

	public static VKHolder addObjInstance(VulkanObjectHolder objectHolder, VKContext vk) {
		if(!PrivilegedUtils.isRunningOnPrivileged()) return null;
		VKHolder holder = vkHolders.get(vk);
		if(holder == null) { holder = new VKHolder(vk); vkHolders.put(vk, holder); }
		holder.addObjInstance(objectHolder); return holder;
	}
	public static void removeObjInstance(VulkanObjectHolder objectHolder) {
		if(!PrivilegedUtils.isRunningOnPrivileged()) return;
		VKHolder holder = vkHolders.get(objectHolder.getVK()); if(holder == null) return;
		holder.removeObjInstance(objectHolder);
	}

	public static void destroyAll(VKContext vk) {
		VKHolder holder = vkHolders.get(vk);
		if(holder == null) return;
		holder.destroyAll();
	}

	protected static VKException newException(String msg, Exception e) { return VKContext.newException(msg, e); }
	protected static VKException newException(String msg) { return VKContext.newException(msg); }
	protected static VKException newException(Exception e) { return VKContext.newException(e); }

	protected final VulkanNativeHolderProvider instance;

	protected VulkanNativeHolder(VKContext vk) {
		this.instance = vk.constructNativeProviderInstance();
		if((this instanceof VKStruct))
			instance.vkHolder.set(addInstance(null, vk));
		else instance.vkHolder.set(addInstance(this, vk));
	}

	protected VulkanNativeHolderProvider getInstance() { return instance; }
	protected VKHolder getVKHolder() { return instance.getVKHolder(); }
	public VKContext getVK() { return instance.getVK(); }
	public boolean isCreated() { return instance.isCreated(); }
	public boolean isContextSame(VulkanNativeHolder holder) { return instance.isContextSame(holder.getInstance()); }

	public final void create() { instance.create(this::arrange, this::destroy); }
	public final void destroy() { instance.destroy(() -> { if(instance.isCreated()) this.disarrange(); removeInstance(this); }); }
	protected abstract void arrange() throws Exception;
	protected abstract void disarrange();

	protected void assertContextSame(VulkanNativeHolder holder) { instance.assertContextSame(holder.getInstance()); }
	protected void assertContextNotSame(VulkanNativeHolder holder) { instance.assertContextNotSame(holder.getInstance()); }
	protected void assertContextSame(VulkanObjectHolder holder) { instance.assertContextSame(holder); }
	protected void assertContextNotSame(VulkanObjectHolder holder) { instance.assertContextNotSame(holder); }
	public void assertCreated() { instance.assertCreated(); }
	public void assertNotCreated() { instance.assertNotCreated(); }
	@Override public void setDead() { destroy(); }
	@Override public boolean isDead() { return instance.isDead(); }

	@Override public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		VulkanNativeHolder that = (VulkanNativeHolder) o;
		return Objects.equals(this, that);
	}
	@Override public int hashCode() {
		return System.identityHashCode(this);
	}

	public static class VKHolder implements DeadableObject {
		protected final VKContext vk;
		protected final ArrayList<VulkanNativeHolder> instances;
		protected final ArrayList<VulkanObjectHolder> objInstances;
		private boolean isDead = false;

		public VKHolder(VKContext vk) {
			this.vk = vk;
			this.instances = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			this.objInstances = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		}

		public VKContext getVK() { return vk; }
		public void destroyAll() { assertNotDead();
			for(VulkanObjectHolder instance : objInstances.toArray(new VulkanObjectHolder[0]))
				if(!instance.isDead()) instance.destroy();
			for(VulkanNativeHolder instance : instances.toArray(new VulkanNativeHolder[0]))
				if(!instance.isDead() && instance.isCreated()) instance.destroy();
		}

		public VulkanNativeHolder[] getInstances() { assertNotDead(); return instances.toArray(new VulkanNativeHolder[0]); }
		public void addInstance(VulkanNativeHolder instance) { assertNotDead(); if(instance != null) instances.add(instance); }
		public void removeInstance(VulkanNativeHolder instance) { assertNotDead(); instances.remove(instance); }

		public VulkanObjectHolder[] getObjInstances() { assertNotDead(); return objInstances.toArray(new VulkanObjectHolder[0]); }
		public void addObjInstance(VulkanObjectHolder instance) { assertNotDead(); objInstances.add(instance); }
		public void removeObjInstance(VulkanObjectHolder instance) { assertNotDead(); objInstances.remove(instance); }

		@Override public void setDead() { isDead = true;
			Pool.returnObject(ArrayList.class, instances);
			Pool.returnObject(ArrayList.class, objInstances);
		} @Override public boolean isDead() { return isDead; }
	}

	public interface Properties {
		default Map<String, Object> getProperties() {
			Map<String, Object> result = new HashMap<>();
			for(Method method : getClass().getMethods()) {
				if(method.getParameterCount() != 0) continue;
				Value annotation = method.getAnnotation(Value.class);
				if(annotation != null) {
					if(!annotation.enabled()) continue;
					if(!Modifier.isPublic(method.getModifiers())) method.setAccessible(true); String name = annotation.name();
					if(name.isEmpty()) name = method.getName().replaceFirst("get", "").replaceFirst("is", "");
					Object value; try { value = method.invoke(this); } catch(Exception e) { value = e; }
					result.put(name, value); continue;
				}
				if(!Modifier.isPublic(method.getModifiers()) || method.getReturnType() == void.class) continue;
				if((!method.getName().startsWith("get") && !method.getName().startsWith("is")) || method.getName().equals("getProperties")) continue;
				String name = method.getName().replaceFirst("get", "").replaceFirst("is", "");
				Object value; try { value = method.invoke(this); } catch(Exception e) { value = e; }
				result.put(name, value);
			} return result;
		}

		@Retention(RetentionPolicy.RUNTIME)
		@Target(ElementType.METHOD)
		@interface Value {
			boolean enabled() default true;
			String name() default "";
		}
	}
}
