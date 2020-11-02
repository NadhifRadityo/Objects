package io.github.NadhifRadityo.Objects.Object;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface Comparator<T> {
	int compare(T o1, T o2);

	interface VoidComparator extends Comparator<Void> { }
	interface ObjectComparator extends Comparator<Object> { }
	interface ByteComparator extends Comparator<Byte> { }
	interface BooleanComparator extends Comparator<Boolean> { }
	interface IntegerComparator extends Comparator<Integer> { }
	interface LongComparator extends Comparator<Long> { }
	interface ShortComparator extends Comparator<Short> { }
	interface FloatComparator extends Comparator<Float> { }
	interface DoubleComparator extends Comparator<Double> { }
	interface CharacterComparator extends Comparator<Character> { }
	interface StringComparator extends Comparator<String> { }

	interface ClassComparator<T> extends Comparator<Class<? extends T>> { }
	interface MethodComparator extends Comparator<Method> { }
	interface FieldComparator extends Comparator<Field> { }

	interface PByteComparator extends Comparator.ByteComparator {
		default int compare(Byte o1, Byte o2) { return _compare(o1, o2); }
		int _compare(byte o1, byte o2);

		PByteComparator naturalOrder = Byte::compare;
		PByteComparator reverseOrder = (o1, o2) -> Byte.compare(o2, o1);
	}
	interface PBooleanComparator extends Comparator.BooleanComparator {
		default int compare(Boolean o1, Boolean o2) { return _compare(o1, o2); }
		int _compare(boolean o1, boolean o2);

		PBooleanComparator naturalOrder = Boolean::compare;
		PBooleanComparator reverseOrder = (o1, o2) -> Boolean.compare(o2, o1);
	}
	interface PIntegerComparator extends Comparator.IntegerComparator {
		default int compare(Integer o1, Integer o2) { return _compare(o1, o2); }
		int _compare(int o1, int o2);

		PIntegerComparator naturalOrder = Integer::compare;
		PIntegerComparator reverseOrder = (o1, o2) -> Integer.compare(o2, o1);
	}
	interface PLongComparator extends Comparator.LongComparator {
		default int compare(Long o1, Long o2) { return _compare(o1, o2); }
		int _compare(long o1, long o2);

		PLongComparator naturalOrder = Long::compare;
		PLongComparator reverseOrder = (o1, o2) -> Long.compare(o2, o1);
	}
	interface PShortComparator extends Comparator.ShortComparator {
		default int compare(Short o1, Short o2) { return _compare(o1, o2); }
		int _compare(short o1, short o2);

		PShortComparator naturalOrder = Short::compare;
		PShortComparator reverseOrder = (o1, o2) -> Short.compare(o2, o1);
	}
	interface PFloatComparator extends Comparator.FloatComparator {
		default int compare(Float o1, Float o2) { return _compare(o1, o2); }
		int _compare(float o1, float o2);

		PFloatComparator naturalOrder = Float::compare;
		PFloatComparator reverseOrder = (o1, o2) -> Float.compare(o2, o1);
	}
	interface PDoubleComparator extends Comparator.DoubleComparator {
		default int compare(Double o1, Double o2) { return _compare(o1, o2); }
		int _compare(double o1, double o2);

		PDoubleComparator naturalOrder = Double::compare;
		PDoubleComparator reverseOrder = (o1, o2) -> Double.compare(o2, o1);
	}
	interface PCharacterComparator extends Comparator.CharacterComparator {
		default int compare(Character o1, Character o2) { return _compare(o1, o2); }
		int _compare(char o1, char o2);

		PCharacterComparator naturalOrder = Character::compare;
		PCharacterComparator reverseOrder = (o1, o2) -> Character.compare(o2, o1);
	}

	static <T extends Comparable<? super T>> Comparator<T> naturalOrder() { return (o1, o2) -> o1.compareTo(o2); }
	static <T extends Comparable<? super T>> Comparator<T> reverseOrder() { return (o1, o2) -> o2.compareTo(o1); }
	static <T> Comparator<T> natural(java.util.Comparator<? super T> comparator) { return comparator::compare; }
	static <T> java.util.Comparator<T> natural(Comparator<? super T> comparator) { return comparator::compare; }
	static <T> Comparator<T> reverse(java.util.Comparator<? super T> comparator) { return (o1, o2) -> comparator.compare(o2, o1); }
	static <T> java.util.Comparator<T> reverse(Comparator<? super T> comparator) { return (o1, o2) -> comparator.compare(o2, o1); }
}
