package io.github.NadhifRadityo.Objects.Object;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface ReferencedCallback<T> {
	T get(Object... obj);

	public interface VoidReferencedCallback extends ReferencedCallback<Void> { }
	public interface ObjectReferencedCallback extends ReferencedCallback<Object> { }
	public interface ByteReferencedCallback extends ReferencedCallback<Byte> { }
	public interface BooleanReferencedCallback extends ReferencedCallback<Boolean> { }
	public interface IntegerReferencedCallback extends ReferencedCallback<Integer> { }
	public interface FloatReferencedCallback extends ReferencedCallback<Float> { }
	public interface DoubleReferencedCallback extends ReferencedCallback<Double> { }
	public interface LongReferencedCallback extends ReferencedCallback<Long> { }
	public interface ShortReferencedCallback extends ReferencedCallback<Short> { }
	public interface CharacterReferencedCallback extends ReferencedCallback<Character> { }
	public interface StringReferencedCallback extends ReferencedCallback<String> { }
	
	public interface ClassReferencedCallback<T> extends ReferencedCallback<Class<? extends T>> { }
	public interface MethodReferencedCallback extends ReferencedCallback<Method> { }
	public interface FieldReferencedCallback extends ReferencedCallback<Field> { }
}
