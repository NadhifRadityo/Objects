package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.Pool.Pool;

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

import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.OpenCLNativeHolderProvider.INVALID_ID;

public abstract class OpenCLNativeHolder<ID extends Number> implements DeadableObject {
	private static final Map<CLContext, CLHolder> clHolders = new HashMap<>();
	protected static <ID extends Number> CLHolder<ID> addInstance(OpenCLNativeHolder<ID> nativeHolder, CLContext<ID> cl) {
		CLHolder<ID> holder = clHolders.get(cl);
		if(holder == null) { holder = new CLHolder<ID>(cl); clHolders.put(cl, holder); }
		holder.addInstance(nativeHolder); return holder;
	}
	protected static <ID extends Number> void removeInstance(OpenCLNativeHolder<ID> nativeHolder) {
		CLHolder<ID> holder = nativeHolder.getClHolder(); if(holder == null) return;
		holder.removeInstance(nativeHolder);
		if(holder.getInstances().length == 0) {
			clHolders.remove(holder.getCl());
			holder.setDead();
		}
	}

	public static <ID extends Number> void destroyAll(CLContext<ID> cl) {
		CLHolder<ID> holder = clHolders.get(cl);
		if(holder == null) return;
		holder.destroyAll();
	}

	protected static CLException newException(String msg, Exception e) { return CLContext.newException(msg, e); }
	protected static CLException newException(String msg) { return CLContext.newException(msg); }
	protected static CLException newException(Exception e) { return CLContext.newException(e); }

	protected final OpenCLNativeHolderProvider<ID> instance;

	protected OpenCLNativeHolder(CLContext<ID> cl) {
		this.instance = cl.constructNativeProviderInstance();
		instance.clHolder.set(addInstance(this, cl));
	}

	protected OpenCLNativeHolderProvider<ID> getInstance() { return instance; }
	protected CLHolder<ID> getClHolder() { return instance.getClHolder(); }
	public CLContext<ID> getCL() { return instance.getCL(); }
	public ID getId() { return instance.getId(); }
	public boolean isContextSame(OpenCLNativeHolder<ID> holder) { return instance.isContextSame(holder.getInstance()); }

	public final void create() { instance.create(this::arrange, this::destroy); }
	public final void destroy() { instance.destroy(() -> { if(getId().intValue() != INVALID_ID) this.disarrange(); removeInstance(this); }); }
	protected abstract void arrange() throws Exception;
	protected abstract void disarrange();

	protected void assertContextSame(OpenCLNativeHolder<ID> holder) { instance.assertContextSame(holder.getInstance()); }
	protected void assertContextNotSame(OpenCLNativeHolder<ID> holder) { instance.assertContextSame(holder.getInstance()); }
	public void assertCreated() { instance.assertCreated(); }
	public void assertNotCreated() { instance.assertNotCreated(); }
	@Override public void setDead() { destroy(); }
	@Override public boolean isDead() { return instance.isDead(); }

	@Override public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		OpenCLNativeHolder<?> that = (OpenCLNativeHolder<?>) o;
		return Objects.equals(getId(), that.getId());
	}
	@Override public int hashCode() {
		return Objects.hash(getId());
	}

	public static class CLHolder<ID extends Number> implements DeadableObject {
		protected final CLContext<ID> cl;
		protected final ArrayList<OpenCLNativeHolder<ID>> instances;
		private boolean isDead = false;

		public CLHolder(CLContext<ID> cl) {
			this.cl = cl;
			this.instances = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		}

		public CLContext<ID> getCl() { return cl; }
		public void destroyAll() {
			for(OpenCLNativeHolder<ID> instance : instances.toArray(new OpenCLNativeHolder[0]))
				if(!instance.isDead() && instance.getId() != null) instance.destroy();
		}

		public OpenCLNativeHolder<ID>[] getInstances() { return instances.toArray(new OpenCLNativeHolder[0]); }
		public void addInstance(OpenCLNativeHolder<ID> instance) { instances.add(instance); }
		public void removeInstance(OpenCLNativeHolder<ID> instance) { instances.remove(instance); }

		@Override public void setDead() { isDead = true;
			Pool.returnObject(ArrayList.class, instances);
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
