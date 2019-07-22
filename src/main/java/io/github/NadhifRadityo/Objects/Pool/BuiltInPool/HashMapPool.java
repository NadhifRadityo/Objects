package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.ObjectUtils.EqualsProxy;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.HashMap;

public class HashMapPool extends Pool<HashMap> {
	
	public static void addPool() {
		if(Pool.containsPool(HashMap.class)) return;
		HashMapPool pool = new HashMapPool(new HashMapPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(HashMap.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(HashMap.class)) return;
//		Pool.removePool(HashMap.class);
//	}
	
	protected HashMapPool(PoolFactory<HashMap> factory) { super(factory); }
	protected HashMapPool(PoolFactory<HashMap> factory, PoolConfig config) { super(factory, config); }
	protected HashMapPool(PoolFactory<HashMap> factory, PoolConfig config, AbandonedConfig abandonedConfig) { super(factory, config, abandonedConfig); }
	
	protected static class HashMapPoolFactory extends PoolFactory<HashMap> {
		@Override public Proxy<HashMap> create() { return new EqualsProxy<>(new HashMap()); }
		@Override public PooledObject<Proxy<HashMap>> wrap(Proxy<HashMap> obj) { return new DefaultPooledObject<>(obj); }
		@Override public void passivateObject(PooledObject<Proxy<HashMap>> p) { p.getObject().get().clear(); }
		@Override public boolean validateObject(PooledObject<Proxy<HashMap>> p) { return p.getObject().get().isEmpty(); }
	}
}

