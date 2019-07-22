package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class StringBuilderPool extends Pool<StringBuilder> {
	public static void addPool() {
		if(Pool.containsPool(StringBuilder.class)) return;
		StringBuilderPool pool = new StringBuilderPool(new StringBuilderPool.StringBuilderPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(StringBuilder.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(StringBuilder.class)) return;
//		Pool.removePool(StringBuilder.class);
//	}

	protected StringBuilderPool(PoolFactory<StringBuilder> factory) { super(factory); }
	protected StringBuilderPool(PoolFactory<StringBuilder> factory, PoolConfig config) { super(factory, config); }
	protected StringBuilderPool(PoolFactory<StringBuilder> factory, PoolConfig config, AbandonedConfig abandonedConfig) { super(factory, config, abandonedConfig); }

	protected static class StringBuilderPoolFactory extends PoolFactory<StringBuilder> {
		@Override public Proxy<StringBuilder> create() { return new Proxy<>(new StringBuilder()); }
		@Override public PooledObject<Proxy<StringBuilder>> wrap(Proxy<StringBuilder> obj) { return new DefaultPooledObject<>(obj); }
		@Override public void passivateObject(PooledObject<Proxy<StringBuilder>> p) { p.getObject().get().setLength(0); }
		@Override public boolean validateObject(PooledObject<Proxy<StringBuilder>> p) { return p.getObject().get().length() > 0; }
	}
}
