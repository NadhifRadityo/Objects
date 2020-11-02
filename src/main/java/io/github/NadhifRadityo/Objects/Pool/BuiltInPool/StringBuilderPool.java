package io.github.NadhifRadityo.Objects.Pool.BuiltInPool;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;

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
		private static final Field FIELD_AbstractStringBuilder_value;
		static { try {
			FIELD_AbstractStringBuilder_value = Class.forName("java.lang.AbstractStringBuilder").getDeclaredField("value");
			FIELD_AbstractStringBuilder_value.setAccessible(true);
		} catch(Exception e) { throw new Error(e); } }

		@Override public StringBuilder create() { return new StringBuilder(); }
		@Override public PooledObject<StringBuilder> wrap(StringBuilder obj) { return new PooledObject<>(obj); }
		@Override public boolean validateObject(PooledObject<StringBuilder> p) { return p.getObject().length() == 0; }
		@Override public void passivateObject(PooledObject<StringBuilder> p) { try {
			FIELD_AbstractStringBuilder_value.set(p.getObject(), Array.newInstance(FIELD_AbstractStringBuilder_value.getType().getComponentType(), 16));
			p.getObject().setLength(0);
		} catch(Exception e) { throw new Error(e); } }
	}
}
