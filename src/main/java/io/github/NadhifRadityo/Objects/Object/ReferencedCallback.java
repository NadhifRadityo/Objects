package io.github.NadhifRadityo.Objects.Object;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface ReferencedCallback<T> {
	T get(Object... obj);

	interface VoidReferencedCallback extends ReferencedCallback<Void> { }
	interface ObjectReferencedCallback extends ReferencedCallback<Object> { }
	interface ByteReferencedCallback extends ReferencedCallback<Byte> { }
	interface BooleanReferencedCallback extends ReferencedCallback<Boolean> { }
	interface IntegerReferencedCallback extends ReferencedCallback<Integer> { }
	interface LongReferencedCallback extends ReferencedCallback<Long> { }
	interface ShortReferencedCallback extends ReferencedCallback<Short> { }
	interface FloatReferencedCallback extends ReferencedCallback<Float> { }
	interface DoubleReferencedCallback extends ReferencedCallback<Double> { }
	interface CharacterReferencedCallback extends ReferencedCallback<Character> { }
	interface StringReferencedCallback extends ReferencedCallback<String> { }

	interface ClassReferencedCallback<T> extends ReferencedCallback<Class<? extends T>> { }
	interface MethodReferencedCallback extends ReferencedCallback<Method> { }
	interface FieldReferencedCallback extends ReferencedCallback<Field> { }

	interface PVoidReferencedCallback extends VoidReferencedCallback {
		default Void get(Object... obj) { _get(obj); return null; }
		void _get(Object... obj);
	}
	interface PByteReferencedCallback extends ByteReferencedCallback {
		default Byte get(Object... obj) { return _get(obj); }
		byte _get(Object... obj);
	}
	interface PBooleanReferencedCallback extends BooleanReferencedCallback {
		default Boolean get(Object... obj) { return _get(obj); }
		boolean _get(Object... obj);
	}
	interface PIntegerReferencedCallback extends IntegerReferencedCallback {
		default Integer get(Object... obj) { return _get(obj); }
		int _get(Object... obj);
	}
	interface PLongReferencedCallback extends LongReferencedCallback {
		default Long get(Object... obj) { return _get(obj); }
		long _get(Object... obj);
	}
	interface PShortReferencedCallback extends ShortReferencedCallback {
		default Short get(Object... obj) { return _get(obj); }
		short _get(Object... obj);
	}
	interface PFloatReferencedCallback extends FloatReferencedCallback {
		default Float get(Object... obj) { return _get(obj); }
		float _get(Object... obj);
	}
	interface PDoubleReferencedCallback extends DoubleReferencedCallback {
		default Double get(Object... obj) { return _get(obj); }
		double _get(Object... obj);
	}
	interface PCharacterReferencedCallback extends CharacterReferencedCallback {
		default Character get(Object... obj) { return _get(obj); }
		char _get(Object... obj);
	}
}
