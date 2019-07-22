package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.ObjectUtils.EqualsProxy;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.ArrayList;

public class ArrayListPool extends Pool<ArrayList> {
	
	public static void addPool() {
		if(Pool.containsPool(ArrayList.class)) return;
		ArrayListPool pool = new ArrayListPool(new ArrayListPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(ArrayList.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(ArrayList.class)) return;
//		Pool.removePool(ArrayList.class);
//	}
	
	protected ArrayListPool(PoolFactory<ArrayList> factory) { super(factory); }
	protected ArrayListPool(PoolFactory<ArrayList> factory, PoolConfig config) { super(factory, config); }
	protected ArrayListPool(PoolFactory<ArrayList> factory, PoolConfig config, AbandonedConfig abandonedConfig) { super(factory, config, abandonedConfig); }

	protected static class ArrayListPoolFactory extends PoolFactory<ArrayList> {
		@Override public Proxy<ArrayList> create() { return new EqualsProxy<>(new ArrayList()); }
		@Override public PooledObject<Proxy<ArrayList>> wrap(Proxy<ArrayList> obj) { return new DefaultPooledObject<>(obj); }
		@Override public void passivateObject(PooledObject<Proxy<ArrayList>> p) { p.getObject().get().clear(); }
		@Override public boolean validateObject(PooledObject<Proxy<ArrayList>> p) { return p.getObject().get().isEmpty(); }
	}
}
