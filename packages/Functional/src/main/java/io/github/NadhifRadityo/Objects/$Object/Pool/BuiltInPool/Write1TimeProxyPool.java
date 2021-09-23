package io.github.NadhifRadityo.Objects.$Object.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.$Object.Proxy.Write1TimeProxy;
import io.github.NadhifRadityo.Objects.$Object.Proxy.WriteNTimeProxy;
import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.$Object.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.$Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.UnsafeUtils;

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
		private static final UnsafeUtils.Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);
		private static final long AFIELD_WriteNTimeProxy_writeCount;

		static { try {
			AFIELD_WriteNTimeProxy_writeCount = unsafe.objectFieldOffset(WriteNTimeProxy.class.getDeclaredField("writeCount"));
		} catch(Exception e) { throw new Error(e); } }

		@Override public Write1TimeProxy<Object> create() { return new Write1TimeProxy<>(); }
		@Override public PooledObject<Write1TimeProxy<Object>> wrap(Write1TimeProxy<Object> obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<Write1TimeProxy<Object>> p) { return p.getObject().get() == null && unsafe.getInt(p.getObject(), AFIELD_WriteNTimeProxy_writeCount) == 0; }
		@Override public void passivateObject(PooledObject<Write1TimeProxy<Object>> p) {
			unsafe.putInt(p.getObject(), AFIELD_WriteNTimeProxy_writeCount, 0);
			p.getObject().set(null);
			unsafe.putInt(p.getObject(), AFIELD_WriteNTimeProxy_writeCount, 0);
		}
	}
}
