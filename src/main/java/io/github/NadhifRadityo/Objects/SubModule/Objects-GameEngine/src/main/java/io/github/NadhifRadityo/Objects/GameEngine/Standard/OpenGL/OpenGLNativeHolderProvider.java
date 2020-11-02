package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL;

import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.OpenGLObjectHolder;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Proxy;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Write1TimeProxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

import java.lang.reflect.Field;

import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.OpenGLNativeHolder.GLHolder;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class OpenGLNativeHolderProvider<ID extends Number> implements DeadableObject {
	protected final Write1TimeProxy<GLHolder<ID>> glHolder;
	protected final Write1TimeProxy<ID> id;
	protected boolean isDead = false;

	protected OpenGLNativeHolderProvider() {
		this.glHolder = Pool.tryBorrow(Pool.getPool(Write1TimeProxy.class));
		this.id = Pool.tryBorrow(Pool.getPool(Write1TimeProxy.class));
	}

	protected GLHolder<ID> getGlHolder() { assertNotDead(); return glHolder.get(); }
	public GLContext<ID> getGL() { assertNotDead(); return getGlHolder().getGl(); }
	public ID getId() { assertNotDead(); return id.get(); }
	public void setId(ID id) { assertNotDead(); this.id.set(id); }
	public boolean isContextSame(OpenGLNativeHolderProvider holder) { return isContextSame(this, holder); }
	public boolean isContextSame(OpenGLObjectHolder holder) { return isContextSame(this, holder); }
	protected void assertValid(OpenGLNativeHolderProvider holder) { assertContextSame(holder); holder.assertNotDead("holder"); holder.assertCreated(); }
	protected void assertValid(OpenGLObjectHolder holder) { assertContextSame(holder); holder.assertNotDead("holder"); holder.assertCreated(); }

	public final void create(ThrowsRunnable arrange, Runnable disarrange) { assertNotDead(); assertNotCreated(); try { arrange.run(); } catch(Exception e) { if(id.get() == null) markInvalidId(this); disarrange.run(); throw e instanceof GLException ? (GLException) e : new GLException(e); } }
	public final void destroy(Runnable disarrange) { assertNotDead(); assertCreated(); try { disarrange.run(); } finally { Pool.returnObject(Write1TimeProxy.class, glHolder); Pool.returnObject(Write1TimeProxy.class, id); isDead = true; } }

	protected void assertContextSame(OpenGLNativeHolderProvider holder) { if(!isContextSame(holder)) newException("Different context!"); }
	protected void assertContextNotSame(OpenGLNativeHolderProvider holder) { if(isContextSame(holder)) newException("Same context!"); }
	protected void assertContextSame(OpenGLObjectHolder holder) { if(!isContextSame(holder)) newException("Different context!"); }
	protected void assertContextNotSame(OpenGLObjectHolder holder) { if(isContextSame(holder)) newException("Same context!"); }
	public void assertCreated() { if(getId() == null) newException("Program is not created!"); }
	public void assertNotCreated() { if(getId() != null) newException("Program is already created!"); }
	@Override public void setDead() { newException("Illegal operation!"); }
	@Override public boolean isDead() { return isDead; }

	protected static GLException newException(String msg, Exception e) { return OpenGLNativeHolder.newException(msg, e); }
	protected static GLException newException(String msg) { return OpenGLNativeHolder.newException(msg); }
	protected static GLException newException(Exception e) { return OpenGLNativeHolder.newException(e); }
	protected static <ID extends Number> boolean isContextSame(OpenGLNativeHolderProvider<ID> holder1, OpenGLNativeHolderProvider<ID> holder2) { return holder1.getGL().getGL() == holder2.getGL().getGL(); }
	protected static <ID extends Number> boolean isContextSame(OpenGLNativeHolderProvider<ID> holder1, OpenGLObjectHolder holder2) { return holder1.getGL().getGL() == holder2.getGL().getGL(); }

	static final int INVALID_ID = -1;
	private static void markInvalidId(OpenGLNativeHolderProvider instance) { ExceptionUtils.doSilentThrowsRunnable(true, () -> {
		Field field = Proxy.class.getDeclaredField("object");
		field.setAccessible(true); field.set(instance.id, INVALID_ID);
	}); }
}
