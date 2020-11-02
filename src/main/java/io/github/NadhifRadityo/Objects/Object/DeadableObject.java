package io.github.NadhifRadityo.Objects.Object;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ClassUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

public interface DeadableObject {
	void setDead();
	boolean isDead();
	default void assertDead() { assertDead("object"); }
	default void assertNotDead() { assertNotDead("object"); }
	default void assertDead(String objname) { if(!isDead()) throw new DeadException("This " + objname + " is not dead!"); }
	default void assertNotDead(String objname) { if(isDead()) throw new DeadException("This " + objname + " is dead!"); }

	@SuppressWarnings("unchecked") @Deprecated
	static void kill(DeadableObject object, ReferencedCallback.BooleanReferencedCallback accept) { ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> {
		if(!object.isDead()) object.setDead(); if(accept == null) return;
		ArrayList<Field> fields = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
			Class<? extends DeadableObject> clazz = object.getClass();
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
			fields.addAll(Arrays.asList(clazz.getFields()));
			for(Field field : fields) {
				if(Modifier.isStatic(field.getModifiers())) continue;
				if(!accept.get(field, object)) continue;
				field.setAccessible(true);
				Object fieldValue = field.get(object);
				Pool pool = Pool.getPool(fieldValue.getClass());
				if(pool != null && pool.isUsing(fieldValue))
					pool.returnObject(fieldValue);
				if(!(fieldValue instanceof DeadableObject)) continue;
				kill((DeadableObject) fieldValue, accept);
				if(Modifier.isFinal(field.getModifiers()))
					ClassUtils.setFinal(object, field, null);
				else field.set(object, null);
			}
		} finally { Pool.returnObject(ArrayList.class, fields); }
	}); } @Deprecated static void kill(DeadableObject object) { kill(object, args -> true); }

	class DeadException extends RuntimeException {
		public DeadException() { super(); }
		public DeadException(String message, Throwable cause) { super(message, cause); }
		public DeadException(String message) { super(message); }
		public DeadException(Throwable cause) { super(cause); }
	}
}
