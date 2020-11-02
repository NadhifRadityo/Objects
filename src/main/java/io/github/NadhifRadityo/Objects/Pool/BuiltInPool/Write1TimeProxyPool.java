package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Write1TimeProxy;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.WriteNTimeProxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;

import java.lang.reflect.Field;

@SuppressWarnings("jol")
public class Write1TimeProxyPool extends Pool<Write1TimeProxy<Object>> {

	public static void addPool() {
		if(Pool.containsPool(Write1TimeProxy.class)) return;
		Write1TimeProxyPool pool = new Write1TimeProxyPool(new Write1TimeProxyPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(Write1TimeProxy.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(Write1TimeProxy.class)) return;
//		Pool.removePool(Write1TimeProxy.class);
//	}

	protected Write1TimeProxyPool(PoolFactory<Write1TimeProxy<Object>> factory) { super(factory); }
	protected Write1TimeProxyPool(PoolFactory<Write1TimeProxy<Object>> factory, PoolConfig<Write1TimeProxy<Object>> config) { super(factory, config); }

	protected static class Write1TimeProxyPoolFactory extends PoolFactory<Write1TimeProxy<Object>> {
		private static final Field FIELD_WriteNTimeProxy_writeCount;
		static { try {
			FIELD_WriteNTimeProxy_writeCount = WriteNTimeProxy.class.getDeclaredField("writeCount");
			FIELD_WriteNTimeProxy_writeCount.setAccessible(true);
		} catch(Exception e) { throw new Error(e); } }

		@Override public Write1TimeProxy<Object> create() { return new Write1TimeProxy<>(); }
		@Override public PooledObject<Write1TimeProxy<Object>> wrap(Write1TimeProxy<Object> obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<Write1TimeProxy<Object>> p) { try { return p.getObject().get() == null && ((int) FIELD_WriteNTimeProxy_writeCount.get(p.getObject())) == 0; } catch(Exception e) { throw new Error(e); } }
		@Override public void passivateObject(PooledObject<Write1TimeProxy<Object>> p) { try {
			FIELD_WriteNTimeProxy_writeCount.set(p.getObject(), 0);
			p.getObject().set(null);
			FIELD_WriteNTimeProxy_writeCount.set(p.getObject(), 0);
		} catch(Exception e) { throw new Error(e); } }
	}
}
