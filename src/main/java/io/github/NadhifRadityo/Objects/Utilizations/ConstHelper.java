package io.github.NadhifRadityo.Objects.Utilizations;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ConstHelper {
	private ConstHelper() {
		
	}
	
	public static Object fromString(Class<?> clazz, String name) throws IllegalArgumentException, IllegalAccessException {
		for(Field field : clazz.getDeclaredFields()) {
			if(isConstField(field) && field.getName().equals(name))
				return field.get(null);
		}
		return null;
	}
	
	public static boolean isConstField(Field field) {
		int mod = field.getModifiers();
		return Modifier.isPublic(mod) && Modifier.isStatic(mod) && Modifier.isFinal(mod);
	}
}
