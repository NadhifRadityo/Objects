package io.github.NadhifRadityo.Objects.$Object.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.$Object.Proxy.WaitAtomicProxy;
import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.$Object.Pool.PooledObject;

@SuppressWarnings("jol")
public class WaitAtomicProxyPool extends Pool<WaitAtomicProxy<Object>> {

	public static void addPool() {
		if(Pool.containsPool(WaitAtomicProxy.class)) return;
		WaitAtomicProxyPool pool = new WaitAtomicProxyPool(new WaitAtomicProxyPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(WaitAtomicProxy.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(WaitAtomicProxy.class)) return;
//		Pool.removePool(WaitAtomicProxy.class);
//	}

	protected WaitAtomicProxyPool(PoolFactory<WaitAtomicProxy<Object>> factory) { super(factory); }
	protected WaitAtomicProxyPool(PoolFactory<WaitAtomicProxy<Object>> factory, PoolConfig<WaitAtomicProxy<Object>> config) { super(factory, config); }

	protected static class WaitAtomicProxyPoolFactory extends PoolFactory<WaitAtomicProxy<Object>> {
		@Override public WaitAtomicProxy<Object> create() { return new WaitAtomicProxy<>(); }
		@Override public PooledObject<WaitAtomicProxy<Object>> wrap(WaitAtomicProxy<Object> obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<WaitAtomicProxy<Object>> p) { return p.getObject().get() == null; }
		@Override public void passivateObject(PooledObject<WaitAtomicProxy<Object>> p) { p.getObject().set(null); }
	}
}
