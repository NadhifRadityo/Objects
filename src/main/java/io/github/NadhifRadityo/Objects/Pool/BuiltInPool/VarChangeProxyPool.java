package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.Object.PropertyChangeService;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.VarChangeProxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;

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
		private static final Field changeSupportField;
		static { try {
			changeSupportField = VarChangeProxy.class.getDeclaredField("changeSupport");
			changeSupportField.setAccessible(true);
		} catch(Exception e) { throw new Error(e); } }

		@Override public VarChangeProxy<Object> create() { return new VarChangeProxy<>(null); }
		@Override public PooledObject<VarChangeProxy<Object>> wrap(VarChangeProxy<Object> obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<VarChangeProxy<Object>> p) { return p.getObject().get() == null && p.getObject().getPropertyChangeListener().length == 0; }
		@Override public void passivateObject(PooledObject<VarChangeProxy<Object>> p) {
			ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> PropertyChangeService.removeAllPropertyChangeListener((PropertyChangeSupport) changeSupportField.get(p.getObject())));
			p.getObject().set(null);
		}
	}
}
