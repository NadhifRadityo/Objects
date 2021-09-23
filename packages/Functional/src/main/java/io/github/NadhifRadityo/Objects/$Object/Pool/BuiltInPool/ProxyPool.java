package io.github.NadhifRadityo.Objects.$Object.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.$Object.Proxy.Proxy;
import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.$Object.Pool.PooledObject;

@SuppressWarnings("jol")
public class ProxyPool extends Pool<Proxy<Object>> {

	public static void addPool() {
		if(Pool.containsPool(Proxy.class)) return;
		ProxyPool pool = new ProxyPool(new ProxyPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(Proxy.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(Proxy.class)) return;
//		Pool.removePool(Proxy.class);
//	}

	protected ProxyPool(PoolFactory<Proxy<Object>> factory) { super(factory); }
	protected ProxyPool(PoolFactory<Proxy<Object>> factory, PoolConfig<Proxy<Object>> config) { super(factory, config); }

	protected static class ProxyPoolFactory extends PoolFactory<Proxy<Object>> {
		@Override public Proxy<Object> create() { return new Proxy<>(null); }
		@Override public PooledObject<Proxy<Object>> wrap(Proxy<Object> obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<Proxy<Object>> p) { return p.getObject().get() == null; }
		@Override public void passivateObject(PooledObject<Proxy<Object>> p) { p.getObject().set(null); }
	}
}
