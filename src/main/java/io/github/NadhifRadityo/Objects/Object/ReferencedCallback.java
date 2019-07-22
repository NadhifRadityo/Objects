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
	interface FloatReferencedCallback extends ReferencedCallback<Float> { }
	interface DoubleReferencedCallback extends ReferencedCallback<Double> { }
	interface LongReferencedCallback extends ReferencedCallback<Long> { }
	interface ShortReferencedCallback extends ReferencedCallback<Short> { }
	interface CharacterReferencedCallback extends ReferencedCallback<Character> { }
	interface StringReferencedCallback extends ReferencedCallback<String> { }

	interface ClassReferencedCallback<T> extends ReferencedCallback<Class<? extends T>> { }
	interface MethodReferencedCallback extends ReferencedCallback<Method> { }
	interface FieldReferencedCallback extends ReferencedCallback<Field> { }
}
