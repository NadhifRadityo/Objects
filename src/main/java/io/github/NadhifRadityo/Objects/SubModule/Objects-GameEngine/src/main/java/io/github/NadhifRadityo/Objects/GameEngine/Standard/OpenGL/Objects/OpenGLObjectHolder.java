package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLException;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.OpenGLNativeHolder;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Proxy;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Write1TimeProxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("rawtypes")
public abstract class OpenGLObjectHolder implements DeadableObject {
	private final OpenGLNativeHolder.GLHolder<? extends Number> glHolder;
	private final Write1TimeProxy<Boolean> isCreated;
	private boolean isDead = false;

	public OpenGLObjectHolder(GLContext<? extends Number> gl) {
		this.glHolder = PrivilegedUtils.doPrivileged(() -> OpenGLNativeHolder.addObjInstance(this, gl));
		this.isCreated = Pool.tryBorrow(Pool.getPool(Write1TimeProxy.class));
	}

	protected OpenGLNativeHolder.GLHolder<? extends Number> getGlHolder() { return glHolder; }
	public GLContext<? extends Number> getGL() { return glHolder.getGl(); }
	public boolean isCreated() { assertNotDead(); return isCreated.get() != null && isCreated.get(); }
	public void setCreated() { assertNotDead(); isCreated.set(true); }
	public boolean isContextSame(OpenGLNativeHolder holder) { return isContextSame(this, holder); }
	public boolean isContextSame(OpenGLObjectHolder holder) { return isContextSame(this, holder); }
	protected void assertValid(OpenGLNativeHolder holder) { assertContextSame(holder); holder.assertNotDead("holder"); holder.assertCreated(); }
	protected void assertValid(OpenGLObjectHolder holder) { assertContextSame(holder); holder.assertNotDead("holder"); holder.assertCreated(); }

	public final void create() { assertNotDead(); assertNotCreated(); try { this.arrange(); isCreated.set(true); } catch(Exception e) { if(isCreated.get() == null || !isCreated.get()) markInvalidId(this); destroy(); throw e instanceof GLException ? (GLException) e : new GLException(e); } }
	public final void destroy() { assertNotDead(); assertCreated(); try { if(isCreated.get() != null && isCreated.get()) disarrange(); PrivilegedUtils.doPrivileged(() -> OpenGLNativeHolder.removeObjInstance(this)); } finally { Pool.returnObject(Write1TimeProxy.class, isCreated); isDead = true; } }
	protected abstract void arrange() throws Exception;
	protected abstract void disarrange();

	protected void assertContextSame(OpenGLNativeHolder holder) { if(!isContextSame(holder)) newException("Different context!"); }
	protected void assertContextNotSame(OpenGLNativeHolder holder) { if(isContextSame(holder)) newException("Same context!"); }
	protected void assertContextSame(OpenGLObjectHolder holder) { if(!isContextSame(holder)) newException("Different context!"); }
	protected void assertContextNotSame(OpenGLObjectHolder holder) { if(isContextSame(holder)) newException("Same context!"); }
	public void assertCreated() { if(!isCreated()) newException("Program is not created!"); }
	public void assertNotCreated() { if(isCreated()) newException("Program is already created!"); }
	@Override public void setDead() { destroy(); }
	@Override public boolean isDead() { return isDead; }

	protected static GLException newException(String msg, Exception e) { return GLContext.newException(msg, e); }
	protected static GLException newException(String msg) { return GLContext.newException(msg); }
	protected static GLException newException(Exception e) { return GLContext.newException(e); }
	protected static boolean isContextSame(OpenGLObjectHolder holder1, OpenGLNativeHolder holder2) { return holder1.getGL().getGL() == holder2.getGL().getGL(); }
	protected static boolean isContextSame(OpenGLObjectHolder holder1, OpenGLObjectHolder holder2) { return holder1.getGL().getGL() == holder2.getGL().getGL(); }

	private static void markInvalidId(OpenGLObjectHolder instance) { ExceptionUtils.doSilentThrowsRunnable(true, () -> {
		Field field = Proxy.class.getDeclaredField("object");
		field.setAccessible(true); field.set(instance.isCreated, true);
	}); }

	protected <ID extends Number> OpenGLNativeHolder.BindableNative getBindableNative(String identifier) { return OpenGLNativeHolder.getBindableNative(getGL(), identifier); }
	protected <ID extends Number> OpenGLNativeHolder.SlotableNative getSlotableNative(String identifier, int slot) { return OpenGLNativeHolder.getSlotableNative(getGL(), identifier, slot); }
}
