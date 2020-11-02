package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL;

import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.OpenGLObjectHolder;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.MapUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.OpenGLNativeHolderProvider.INVALID_ID;

@SuppressWarnings({"unchecked", "unused"})
public abstract class OpenGLNativeHolder<ID extends Number> implements DeadableObject {
	private static final Map<GLContext, GLHolder> glHolders = new HashMap<>();
	protected static <ID extends Number> GLHolder<ID> addInstance(OpenGLNativeHolder<ID> nativeHolder, GLContext<ID> gl) {
		GLHolder<ID> holder = glHolders.get(gl);
		if(holder == null) { holder = new GLHolder<ID>(gl); glHolders.put(gl, holder); }
		holder.addInstance(nativeHolder); return holder;
	}
	protected static <ID extends Number> void removeInstance(OpenGLNativeHolder<ID> nativeHolder) {
		GLHolder<ID> holder = nativeHolder.getGlHolder(); if(holder == null) return;
		holder.removeInstance(nativeHolder);
		if(holder.getInstances().length == 0) {
			glHolders.remove(holder.getGl());
			holder.setDead();
		}
	}

	public static <ID extends Number> BindableNative getBindableNative(GLContext<ID> gl, String identifier) {
		GLHolder<ID> holder = glHolders.get(gl);
		if(holder == null) return null;
		return holder.getBind(identifier);
	}
	public static <ID extends Number> BindableNative getBindableNative(GLContext<ID> gl, Class clazz) { return getBindableNative(gl, clazz.getCanonicalName()); }
	protected static <ID extends Number> void setBindableNative(GLContext<ID> gl, String identifier, BindableNative nativeHolder) {
		GLHolder<ID> holder = glHolders.get(gl);
		if(holder == null) return;
		if(nativeHolder != null) holder.setBind(identifier, nativeHolder);
		else holder.removeBind(identifier);
	}
	protected static <ID extends Number> void setBindableNative(GLContext<ID> gl, Class clazz, BindableNative nativeHolder) { setBindableNative(gl, clazz.getCanonicalName(), nativeHolder); }

	public static <ID extends Number> SlotableNative getSlotableNative(GLContext<ID> gl, String identifier, int slot) {
		GLHolder<ID> holder = glHolders.get(gl);
		if(holder == null) return null;
		return holder.getSlot(identifier, slot);
	}
	public static <ID extends Number> int getSlotableNative(GLContext<ID> gl, String identifier, SlotableNative nativeHolder) {
		for(int i = 0; i < SlotableNative.MAX_SLOTS; i++) if(getSlotableNative(gl, identifier, i) == nativeHolder) return i;
		return SlotableNative.SLOT_NULL;
	}
	protected static <ID extends Number> int setSlotableNative(GLContext<ID> gl, String identifier, int slot, SlotableNative nativeHolder) {
		GLHolder<ID> holder = glHolders.get(gl);
		if(holder == null) return SlotableNative.SLOT_NULL;
		if(nativeHolder != null) return holder.setSlot(identifier, slot, nativeHolder);
		holder.removeSlot(identifier, slot); return SlotableNative.SLOT_NULL;
	}
	protected static <ID extends Number> int setSlotableNative(GLContext<ID> gl, String identifier, SlotableNative nativeHolder) { return setSlotableNative(gl, identifier, SlotableNative.SLOT_NULL, nativeHolder); }

	public static <ID extends Number> GLHolder<ID> addObjInstance(OpenGLObjectHolder objectHolder, GLContext<ID> gl) {
		if(!PrivilegedUtils.isRunningOnPrivileged()) return null;
		GLHolder<ID> holder = glHolders.get(gl);
		if(holder == null) { holder = new GLHolder<ID>(gl); glHolders.put(gl, holder); }
		holder.addObjInstance(objectHolder); return holder;
	}
	public static <ID extends Number> void removeObjInstance(OpenGLObjectHolder objectHolder) {
		if(!PrivilegedUtils.isRunningOnPrivileged()) return;
		GLHolder<ID> holder = glHolders.get(objectHolder.getGL()); if(holder == null) return;
		holder.removeObjInstance(objectHolder);
	}

	public static <ID extends Number> void reset(GLContext<ID> gl) {
		GLHolder<ID> holder = glHolders.get(gl);
		if(holder == null) return;
		holder.resetBind();
		holder.resetSlot();
	}
	public static <ID extends Number> void destroyAll(GLContext<ID> gl) {
		GLHolder<ID> holder = glHolders.get(gl);
		if(holder == null) return;
		holder.destroyAll();
	}

	protected static GLException newException(String msg, Exception e) { return GLContext.newException(msg, e); }
	protected static GLException newException(String msg) { return GLContext.newException(msg); }
	protected static GLException newException(Exception e) { return GLContext.newException(e); }

	protected final OpenGLNativeHolderProvider<ID> instance;

	protected OpenGLNativeHolder(GLContext<ID> gl) {
		this.instance = gl.constructNativeProviderInstance();
		instance.glHolder.set(addInstance(this, gl));
	}

	protected OpenGLNativeHolderProvider<ID> getInstance() { return instance; }
	protected GLHolder<ID> getGlHolder() { return instance.getGlHolder(); }
	public GLContext<ID> getGL() { return instance.getGL(); }
	public ID getId() { return instance.getId(); }
	public boolean isContextSame(OpenGLNativeHolder<ID> holder) { return instance.isContextSame(holder.getInstance()); }

	public final void create() { instance.create(this::arrange, this::destroy); }
	public final void destroy() { instance.destroy(() -> { if(getId().intValue() != INVALID_ID) this.disarrange(); removeInstance(this); }); }
	protected abstract void arrange() throws Exception;
	protected abstract void disarrange();

	protected void assertContextSame(OpenGLNativeHolder<ID> holder) { instance.assertContextSame(holder.getInstance()); }
	protected void assertContextNotSame(OpenGLNativeHolder<ID> holder) { instance.assertContextNotSame(holder.getInstance()); }
	protected void assertContextSame(OpenGLObjectHolder holder) { instance.assertContextSame(holder); }
	protected void assertContextNotSame(OpenGLObjectHolder holder) { instance.assertContextNotSame(holder); }
	public void assertCreated() { instance.assertCreated(); }
	public void assertNotCreated() { instance.assertNotCreated(); }
	@Override public void setDead() { destroy(); }
	@Override public boolean isDead() { return instance.isDead(); }

	@Override public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		OpenGLNativeHolder<?> that = (OpenGLNativeHolder<?>) o;
		return Objects.equals(getId(), that.getId());
	}
	@Override public int hashCode() {
		return Objects.hash(getId());
	}

	public static class GLHolder<ID extends Number> implements DeadableObject {
		protected final GLContext<ID> gl;
		protected final ArrayList<OpenGLNativeHolder<ID>> instances;
		protected final HashMap<String, BindableNative> binds;
		protected final HashMap<String, Map<Integer, SlotableNative>> slots;
		protected final ArrayList<OpenGLObjectHolder> objInstances;
		private boolean isDead = false;

		public GLHolder(GLContext<ID> gl) {
			this.gl = gl;
			this.instances = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			this.binds = Pool.tryBorrow(Pool.getPool(HashMap.class));
			this.slots = Pool.tryBorrow(Pool.getPool(HashMap.class));
			this.objInstances = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		}

		public GLContext<ID> getGl() { return gl; }
		public void destroyAll() { assertNotDead();
			for(OpenGLObjectHolder instance : objInstances.toArray(new OpenGLObjectHolder[0]))
				if(!instance.isDead()) instance.destroy();
			for(OpenGLNativeHolder<ID> instance : instances.toArray(new OpenGLNativeHolder[0]))
				if(!instance.isDead() && instance.getId() != null) instance.destroy();
		}

		public OpenGLNativeHolder<ID>[] getInstances() { assertNotDead(); return instances.toArray(new OpenGLNativeHolder[0]); }
		public void addInstance(OpenGLNativeHolder<ID> instance) { assertNotDead(); instances.add(instance); }
		public void removeInstance(OpenGLNativeHolder<ID> instance) { assertNotDead(); instances.remove(instance); }

		public Iterator<Map.Entry<String, BindableNative>> getBinds() { assertNotDead(); return MapUtils.reusableIterator(binds); }
		public BindableNative getBind(String identifier) { assertNotDead(); return binds.get(identifier); }
		public void setBind(String identifier, BindableNative bindableNative) { assertNotDead(); binds.put(identifier, bindableNative); }
		public void removeBind(String identifier) { assertNotDead(); binds.remove(identifier); }
		public void resetBind() { assertNotDead();
			BindableNative[] bindableNatives = binds.values().toArray(new BindableNative[0]);
			for(BindableNative bindableNative : bindableNatives) bindableNative.unbind();
			binds.clear();
		}

		protected Iterator<Map.Entry<Integer, SlotableNative>> initSlot(String identifier) {
			Map<Integer, SlotableNative> map = slots.get(identifier);
			if(map == null) {
				map = MapUtils.optimizedHashMap(); slots.put(identifier, map);
				for(int i = 0; i < SlotableNative.MAX_SLOTS; i++) map.put(i, null);
			} return MapUtils.reusableIterator(map);
		}
		protected int assertAndSetSlot(String identifier, int slot, SlotableNative slotableNative) { assertNotDead();
			Iterator<Map.Entry<Integer, SlotableNative>> slottedIdentifiers = initSlot(identifier);
			int freeSlot = 0b11111111111111111111111111111111;
			try { while(slottedIdentifiers.hasNext()) {
				Map.Entry<Integer, SlotableNative> entry = slottedIdentifiers.next();
				if(slot != SlotableNative.SLOT_NULL) { if(entry.getKey() == slot) {
					if(entry.getValue() != null) throw new IllegalArgumentException("Slot is used!");
				entry.setValue(slotableNative); return slot; } continue; }
				if(entry.getValue() != null) freeSlot &= ~(1 << entry.getKey());
			} } finally { MapUtils.resetHashMapIterator(slottedIdentifiers); }
			while(slottedIdentifiers.hasNext()) {
				Map.Entry<Integer, SlotableNative> entry = slottedIdentifiers.next();
				if((freeSlot & (1 << entry.getKey())) == 0) continue;
				entry.setValue(slotableNative); return entry.getKey();
			} throw new IllegalArgumentException("No available slot!");
		}
		public Iterator<Map.Entry<String, Map<Integer, SlotableNative>>> getSlots() { assertNotDead(); return MapUtils.reusableIterator(slots); }
		public SlotableNative getSlot(String identifier, int slot) { assertNotDead(); Map<Integer, SlotableNative> slottedIdentifiers = slots.get(identifier); return slottedIdentifiers != null ? slottedIdentifiers.get(slot) : null; }
		public int setSlot(String identifier, int slot, SlotableNative slotableNative) { assertNotDead();
			ArrayUtils.checkIndex(slot, SlotableNative.MAX_SLOTS, 0);
			slot = assertAndSetSlot(identifier, slot, slotableNative); return slot;
		}
		public void removeSlot(String identifier, int slot) { assertNotDead();
			Iterator<Map.Entry<Integer, SlotableNative>> slottedIdentifiers = initSlot(identifier);
			while(slottedIdentifiers.hasNext()) {
				Map.Entry<Integer, SlotableNative> entry = slottedIdentifiers.next();
				if(entry.getKey() != slot) continue; entry.setValue(null); return;
			}
		}
		public void resetSlot() { assertNotDead();
			for(Iterator<Map.Entry<String, Map<Integer, SlotableNative>>> it = MapUtils.reusableIterator(slots); it.hasNext(); ) {
				Map.Entry<String, Map<Integer, SlotableNative>> entry = it.next();
				for(Iterator<Map.Entry<Integer, SlotableNative>> it2 = MapUtils.reusableIterator(entry.getValue()); it2.hasNext(); ) {
					Map.Entry<Integer, SlotableNative> entry2 = it2.next();
					if(entry2.getValue() == null) continue;
					entry2.getValue().deallocateSlot();
				} entry.setValue(null);
			}
		}

		public OpenGLObjectHolder[] getObjInstances() { assertNotDead(); return objInstances.toArray(new OpenGLObjectHolder[0]); }
		public void addObjInstance(OpenGLObjectHolder instance) { assertNotDead(); objInstances.add(instance); }
		public void removeObjInstance(OpenGLObjectHolder instance) { assertNotDead(); objInstances.remove(instance); }

		@Override public void setDead() { isDead = true;
			Pool.returnObject(ArrayList.class, instances);
			Pool.returnObject(HashMap.class, binds);
			Pool.returnObject(HashMap.class, slots);
			Pool.returnObject(ArrayList.class, objInstances);
		} @Override public boolean isDead() { return isDead; }
	}

	public interface BindableNative {
		boolean isBind();
		void bind();
		void unbind();

		default void assertBind() { if(!isBind()) newException("Object is not bind!"); }
		default void assertNotBind() { if(isBind()) newException("Object is bind!"); }
	}
	public interface FinalableNative {
		boolean isFinal();
		void setFinal(boolean isFinal);

		default void assertFinal() { if(!isFinal()) newException("Object is not finalised!"); }
		default void assertNotFinal() { if(isFinal()) newException("Object is finalised!"); }
	}
	public interface SlotableNative {
		final int MAX_SLOTS = 32;
		final int SLOT_NULL = -1;
		int getCurrentSlot();
		void allocateSlot();
		void deallocateSlot();

		default void assertSlot() { if(getCurrentSlot() == SLOT_NULL) newException("Object is not slotted!"); }
		default void assertNotSlot() { if(getCurrentSlot() != SLOT_NULL) newException("Object is slotted!"); }
	}
}
