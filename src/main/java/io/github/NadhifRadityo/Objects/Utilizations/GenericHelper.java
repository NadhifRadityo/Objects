package io.github.NadhifRadityo.Objects.Utilizations;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericHelper<T> {
	
	@SuppressWarnings("unchecked")
	public Class<T> getGeneric(Type t) {
		if(t == null) return null;
		if (t instanceof ParameterizedType)
			return (Class<T>) ((ParameterizedType) t).getActualTypeArguments()[0];
		else return getGeneric(((Class<?>) t).getGenericSuperclass());
	} public Class<T> getGeneric() { return getGeneric(getClass()); }
}
