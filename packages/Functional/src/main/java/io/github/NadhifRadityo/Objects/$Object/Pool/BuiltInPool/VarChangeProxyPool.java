package io.github.NadhifRadityo.Objects.$Object.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.$Interface.Service.PropertyChangeService;
import io.github.NadhifRadityo.Objects.$Object.Proxy.VarChangeProxy;
import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.$Object.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.$Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.UnsafeUtils;

import java.beans.PropertyChangeSupport;

@SuppressWarnings("jol")
public class VarChangeProxyPool extends Pool<VarChangeProxy<Object>> {

	public static void addPool() {
		if(Pool.containsPool(VarChangeProxy.class)) return;
		VarChangeProxyPool pool = new VarChangeProxyPool(new VarChangeProxyPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(VarChangeProxy.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(VarChangeProxy.class)) return;
//		Pool.removePool(VarChangeProxy.class);
//	}

	protected VarChangeProxyPool(PoolFactory<VarChangeProxy<Object>> factory) { super(factory); }
	protected VarChangeProxyPool(PoolFactory<VarChangeProxy<Object>> factory, PoolConfig<VarChangeProxy<Object>> config) { super(factory, config); }

	protected static class VarChangeProxyPoolFactory extends PoolFactory<VarChangeProxy<Object>> {
		private static final UnsafeUtils.Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);
		private static final long AFIELD_VarChangeProxy_changeSupportField;

		static { try {
			AFIELD_VarChangeProxy_changeSupportField = unsafe.objectFieldOffset(VarChangeProxy.class.getDeclaredField("changeSupport"));
		} catch(Exception e) { throw new Error(e); } }

		@Override public VarChangeProxy<Object> create() { return new VarChangeProxy<>(null); }
		@Override public PooledObject<VarChangeProxy<Object>> wrap(VarChangeProxy<Object> obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<VarChangeProxy<Object>> p) { return p.getObject().get() == null && p.getObject().getPropertyChangeListener().length == 0; }
		@Override public void passivateObject(PooledObject<VarChangeProxy<Object>> p) {
			PropertyChangeService.removeAllPropertyChangeListener((PropertyChangeSupport) unsafe.getObject(p.getObject(), AFIELD_VarChangeProxy_changeSupportField));
			p.getObject().set(null);
		}
	}
}
