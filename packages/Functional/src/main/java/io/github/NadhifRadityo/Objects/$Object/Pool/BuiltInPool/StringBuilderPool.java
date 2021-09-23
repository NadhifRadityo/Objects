package io.github.NadhifRadityo.Objects.$Object.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.$Object.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.$Object.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.$Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.UnsafeUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

@SuppressWarnings("jol")
public class StringBuilderPool extends Pool<StringBuilder> {

	public static void addPool() {
		if(Pool.containsPool(StringBuilder.class)) return;
		StringBuilderPool pool = new StringBuilderPool(new StringBuilderPoolFactory(), Pool.getDefaultPoolConfig());
		Pool.addPool(StringBuilder.class, pool);
	}
//	public static void removePool() {
//		if(!Pool.containsPool(StringBuilder.class)) return;
//		Pool.removePool(StringBuilder.class);
//	}

	protected StringBuilderPool(PoolFactory<StringBuilder> factory) { super(factory); }
	protected StringBuilderPool(PoolFactory<StringBuilder> factory, PoolConfig<StringBuilder> config) { super(factory, config); }

	protected static class StringBuilderPoolFactory extends PoolFactory<StringBuilder> {
		private static final UnsafeUtils.Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);
		private static final Class FIELD_TYPECOM_AbstractStringBuilder_value;
		private static final long AFIELD_AbstractStringBuilder_value;

		static { try {
			Field FIELD_AbstractStringBuilder_value = Class.forName("java.lang.AbstractStringBuilder").getDeclaredField("value");
			FIELD_TYPECOM_AbstractStringBuilder_value = FIELD_AbstractStringBuilder_value.getType().getComponentType();
			AFIELD_AbstractStringBuilder_value = unsafe.objectFieldOffset(FIELD_AbstractStringBuilder_value);
		} catch(Exception e) { throw new Error(e); } }

		@Override public StringBuilder create() { return new StringBuilder(); }
		@Override public PooledObject<StringBuilder> wrap(StringBuilder obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<StringBuilder> p) { return p.getObject().length() == 0; }
		@Override public void passivateObject(PooledObject<StringBuilder> p) {
			unsafe.putObject(p.getObject(), AFIELD_AbstractStringBuilder_value, Array.newInstance(FIELD_TYPECOM_AbstractStringBuilder_value, 16));
			p.getObject().setLength(0);
		}
	}
}
