package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Proxy;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Write1TimeProxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

import java.lang.reflect.Field;

import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.OpenCLNativeHolder.CLHolder;

public abstract class OpenCLNativeHolderProvider<ID extends Number> implements DeadableObject {
	protected final Write1TimeProxy<CLHolder<ID>> clHolder;
	protected final Write1TimeProxy<ID> id;
	protected boolean isDead = false;

	protected OpenCLNativeHolderProvider() {
		this.clHolder = Pool.tryBorrow(Pool.getPool(Write1TimeProxy.class));
		this.id = Pool.tryBorrow(Pool.getPool(Write1TimeProxy.class));
	}

	protected CLHolder<ID> getClHolder() { return clHolder.get(); }
	public CLContext<ID> getCL() { return clHolder.get().getCl(); }
	public ID getId() { assertNotDead(); return id.get(); }
	public void setId(ID id) { this.id.set(id); }
	public boolean isContextSame(OpenCLNativeHolderProvider holder) { return isContextSame(this, holder); }
	protected void assertValid(OpenCLNativeHolderProvider holder) { assertContextSame(holder); holder.assertNotDead("holder"); holder.assertCreated(); }

	public final void create(ThrowsRunnable arrange, Runnable disarrange) { assertNotDead(); assertNotCreated(); try { arrange.run(); } catch(Exception e) { if(id.get() == null) markInvalidId(this); disarrange.run(); throw e instanceof CLException ? (CLException) e : new CLException(e); } }
	public final void destroy(Runnable disarrange) { assertNotDead(); assertCreated(); try { disarrange.run(); } finally { Pool.returnObject(Write1TimeProxy.class, clHolder); Pool.returnObject(Write1TimeProxy.class, id); isDead = true; } }

	protected void assertContextSame(OpenCLNativeHolderProvider holder) { if(!isContextSame(holder)) newException("Different context!"); }
	protected void assertContextNotSame(OpenCLNativeHolderProvider holder) { if(isContextSame(holder)) newException("Same context!"); }
	public void assertCreated() { if(getId() == null) newException("Program is not created!"); }
	public void assertNotCreated() { if(getId() != null) newException("Program is already created!"); }
	@Override public void setDead() { newException("Illegal operation!"); }
	@Override public boolean isDead() { return isDead; }

	protected static CLException newException(String msg, Exception e) { return OpenCLNativeHolder.newException(msg, e); }
	protected static CLException newException(String msg) { return OpenCLNativeHolder.newException(msg); }
	protected static CLException newException(Exception e) { return OpenCLNativeHolder.newException(e); }
	protected static <ID extends Number> boolean isContextSame(OpenCLNativeHolderProvider<ID> holder1, OpenCLNativeHolderProvider<ID> holder2) { return holder1.getCL() == holder2.getCL(); }

	static final int INVALID_ID = -1;
	private static void markInvalidId(OpenCLNativeHolderProvider instance) { ExceptionUtils.doSilentThrowsRunnable(true, () -> {
		Field field = Proxy.class.getDeclaredField("object");
		field.setAccessible(true); field.set(instance.id, INVALID_ID);
	}); }
}
