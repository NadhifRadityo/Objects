package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.EqualsProxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;

@SuppressWarnings("jol")
public class EqualsProxyPool extends Pool<EqualsProxy<Object>> {

	public static void addPool() {
		if(Pool.containsPool(EqualsProxy.class)) return;
		EqualsProxyPool pool = new EqualsProxyPool(new EqualsProxyPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(EqualsProxy.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(EqualsProxy.class)) return;
//		Pool.removePool(EqualsProxy.class);
//	}

	protected EqualsProxyPool(PoolFactory<EqualsProxy<Object>> factory) { super(factory); }
	protected EqualsProxyPool(PoolFactory<EqualsProxy<Object>> factory, PoolConfig<EqualsProxy<Object>> config) { super(factory, config); }

	protected static class EqualsProxyPoolFactory extends PoolFactory<EqualsProxy<Object>> {
		@Override public EqualsProxy<Object> create() { return new EqualsProxy<>(); }
		@Override public PooledObject<EqualsProxy<Object>> wrap(EqualsProxy<Object> obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<EqualsProxy<Object>> p) { return p.getObject().get() == null; }
		@Override public void passivateObject(PooledObject<EqualsProxy<Object>> p) { p.getObject().set(null); }
	}
}
