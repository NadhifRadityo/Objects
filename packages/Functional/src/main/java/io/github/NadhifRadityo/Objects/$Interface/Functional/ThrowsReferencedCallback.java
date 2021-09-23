package io.github.NadhifRadityo.Objects.$Interface.Functional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface ThrowsReferencedCallback<T> {
	T get(Object... obj) throws Exception;

	interface ThrowsVoidReferencedCallback extends ThrowsReferencedCallback<Void> { }
	interface ThrowsObjectReferencedCallback extends ThrowsReferencedCallback<Object> { }
	interface ThrowsByteReferencedCallback extends ThrowsReferencedCallback<Byte> { }
	interface ThrowsBooleanReferencedCallback extends ThrowsReferencedCallback<Boolean> { }
	interface ThrowsIntegerReferencedCallback extends ThrowsReferencedCallback<Integer> { }
	interface ThrowsLongReferencedCallback extends ThrowsReferencedCallback<Long> { }
	interface ThrowsShortReferencedCallback extends ThrowsReferencedCallback<Short> { }
	interface ThrowsFloatReferencedCallback extends ThrowsReferencedCallback<Float> { }
	interface ThrowsDoubleReferencedCallback extends ThrowsReferencedCallback<Double> { }
	interface ThrowsCharacterReferencedCallback extends ThrowsReferencedCallback<Character> { }
	interface ThrowsStringReferencedCallback extends ThrowsReferencedCallback<String> { }

	interface ThrowsClassReferencedCallback<T> extends ThrowsReferencedCallback<Class<? extends T>> { }
	interface ThrowsMethodReferencedCallback extends ThrowsReferencedCallback<Method> { }
	interface ThrowsFieldReferencedCallback extends ThrowsReferencedCallback<Field> { }

	interface PThrowsVoidReferencedCallback extends ThrowsVoidReferencedCallback {
		default Void get(Object... obj) throws Exception { _get(obj); return null; }
		void _get(Object... obj) throws Exception;
	}
	interface PThrowsByteReferencedCallback extends ThrowsByteReferencedCallback {
		default Byte get(Object... obj) throws Exception { return _get(obj); }
		byte _get(Object... obj) throws Exception;
	}
	interface PThrowsBooleanReferencedCallback extends ThrowsBooleanReferencedCallback {
		default Boolean get(Object... obj) throws Exception { return _get(obj); }
		boolean _get(Object... obj) throws Exception;
	}
	interface PThrowsIntegerReferencedCallback extends ThrowsIntegerReferencedCallback {
		default Integer get(Object... obj) throws Exception { return _get(obj); }
		int _get(Object... obj) throws Exception;
	}
	interface PThrowsLongReferencedCallback extends ThrowsLongReferencedCallback {
		default Long get(Object... obj) throws Exception { return _get(obj); }
		long _get(Object... obj) throws Exception;
	}
	interface PThrowsShortReferencedCallback extends ThrowsShortReferencedCallback {
		default Short get(Object... obj) throws Exception { return _get(obj); }
		short _get(Object... obj) throws Exception;
	}
	interface PThrowsFloatReferencedCallback extends ThrowsFloatReferencedCallback {
		default Float get(Object... obj) throws Exception { return _get(obj); }
		float _get(Object... obj) throws Exception;
	}
	interface PThrowsDoubleReferencedCallback extends ThrowsDoubleReferencedCallback {
		default Double get(Object... obj) throws Exception { return _get(obj); }
		double _get(Object... obj) throws Exception;
	}
	interface PThrowsCharacterReferencedCallback extends ThrowsCharacterReferencedCallback {
		default Character get(Object... obj) throws Exception { return _get(obj); }
		char _get(Object... obj) throws Exception;
	}
}
