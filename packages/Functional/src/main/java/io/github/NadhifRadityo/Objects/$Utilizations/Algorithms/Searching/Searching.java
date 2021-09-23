package io.github.NadhifRadityo.Objects.$Utilizations.Algorithms.Searching;

import io.github.NadhifRadityo.Objects.$Interface.Functional.Comparator;
import io.github.NadhifRadityo.Objects.$Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.ListUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public interface Searching {

	<T> int search(T[] array, T key, Comparator<? super T> comparator, int start, int end);
	default <T> int search(T[] array, T key, Comparator<? super T> comparator) { return search(array, key, comparator, 0, array.length); }
	default <T extends Comparable<? super T>> int search(T[] array, T key, int start, int end) { return search(array, key, Comparator.naturalOrder(), start, end); }
	default <T extends Comparable<? super T>> int search(T[] array, T key) { return search(array, key, 0, array.length); }

	@SuppressWarnings("unchecked")
	default <T> int search(List<T> list, T key, Comparator<? super T> comparator, int start, int end) {
		Object[] array;
		if(list instanceof ArrayList) array = ListUtils.getElementData((ArrayList<T>) list);
		else array = list.toArray();
		int result = search(array, key, comparator, start, end);
		if(list instanceof ArrayList) return result;
		ListIterator<T> iterator = list.listIterator();
		for(Object object : array) { iterator.next(); iterator.set((T) object); }
		return result;
	}
	default <T> int search(List<T> list, T key, Comparator<? super T> comparator) { return search(list, key, comparator, 0, list.size()); }
	default <T extends Comparable<? super T>> int search(List<T> list, T key, int start, int end) { return search(list, key, Comparator.naturalOrder(), start, end); }
	default <T extends Comparable<? super T>> int search(List<T> list, T key) { return search(list, key, 0, list.size()); }

	@SuppressWarnings("unchecked")
	default <T> int search(Object object, T key, Comparator<? super T> comparator, int start, int end) {
		if(object.getClass().isArray() && object.getClass().getComponentType().isPrimitive()) {
			Object boxed = __.toObjectArray(object, start, end);
			int length = end - start; return search((T[]) boxed, key, comparator, 0, length);
		}
		if(object.getClass().isArray()) { return search((T[]) object, key, comparator, start, end); }
		if(object instanceof Map) {
			Map<Object, Object> map = (Map<Object, Object>) object;
			Object boxed = __.toObjectArray(map, start, end);
			int length = end - start; return search((T[]) boxed, key, comparator, 0, length);
		}
		if(object instanceof List) { return search((List<T>) object, key, comparator, start, end); }
		throw new IllegalArgumentException("Object is not an array");
	}
	@SuppressWarnings("unchecked")
	default <T> int search(Object object, T key, Comparator<T> comparator) { int length;
		if(object.getClass().isArray()) length = Array.getLength(object);
		else if(object instanceof Map) length = ((Map<Object, Object>) object).size();
		else if(object instanceof List) length = ((List<T>) object).size();
		else throw new IllegalArgumentException("Object is not an array");
		return search(object, key, comparator, 0, length);
	}
	default <T extends Comparable<? super T>> int search(Object object, T key, int start, int end) { if(!(object instanceof Comparable)) throw new IllegalArgumentException("Object is not comparable"); return search(object, key, Comparator.<T>naturalOrder(), start, end); }
	default <T extends Comparable<? super T>> int search(Object object, T key) { if(!(object instanceof Comparable)) throw new IllegalArgumentException("Object is not comparable"); return search(object, key, Comparator.<T>naturalOrder()); }

	int search(int[] array, int key, Comparator.PIntegerComparator comparator, int start, int end);
	int search(long[] array, long key, Comparator.PLongComparator comparator, int start, int end);
	int search(short[] array, short key, Comparator.PShortComparator comparator, int start, int end);
	int search(float[] array, float key, Comparator.PFloatComparator comparator, int start, int end);
	int search(double[] array, double key, Comparator.PDoubleComparator comparator, int start, int end);
	int search(char[] array, char key, Comparator.PCharacterComparator comparator, int start, int end);
	default int search(int[] array, int key, int start, int end) { return search(array, key, Comparator.PIntegerComparator.naturalOrder, start, end); }
	default int search(long[] array, long key, int start, int end) { return search(array, key, Comparator.PLongComparator.naturalOrder, start, end); }
	default int search(short[] array, short key, int start, int end) { return search(array, key, Comparator.PShortComparator.naturalOrder, start, end); }
	default int search(float[] array, float key, int start, int end) { return search(array, key, Comparator.PFloatComparator.naturalOrder, start, end); }
	default int search(double[] array, double key, int start, int end) { return search(array, key, Comparator.PDoubleComparator.naturalOrder, start, end); }
	default int search(char[] array, char key, int start, int end) { return search(array, key, Comparator.PCharacterComparator.naturalOrder, start, end); }
	default int search(int[] array, int key) { return search(array, key, 0, array.length); }
	default int search(long[] array, long key) { return search(array, key, 0, array.length); }
	default int search(short[] array, short key) { return search(array, key, 0, array.length); }
	default int search(float[] array, float key) { return search(array, key, 0, array.length); }
	default int search(double[] array, double key) { return search(array, key, 0, array.length); }
	default int search(char[] array, char key) { return search(array, key, 0, array.length); }

	Searching BINARY_SEARCH = BinarySearch.DEFAULT;
	Searching TERNARY_SEARCH = TernarySearch.DEFAULT;
	Searching JUMP_SEARCH = JumpSearch.DEFAULT;
	Searching LINEAR_SEARCH = LinearSearch.DEFAULT;

	class __ {
		protected static Object toObjectArray(Object array, int start, int end) {
			if(!array.getClass().isArray()) throw new IllegalArgumentException("Object is not an array");
			if(!array.getClass().getComponentType().isPrimitive()) throw new IllegalArgumentException("Array is not primitive");
			ArrayUtils.assertCopyIndex(start, Array.getLength(array), end, Array.getLength(array), 0); int length = end - start;
			Object[] result = ArrayUtils.getTempObjectArray(length); int fit = (length / 8) * 8; int i = 0;
			for(; i < fit; i += 8) {
				result[i    ] = Array.get(array, start + i    );
				result[i + 1] = Array.get(array, start + i + 1);
				result[i + 2] = Array.get(array, start + i + 2);
				result[i + 3] = Array.get(array, start + i + 3);
				result[i + 4] = Array.get(array, start + i + 4);
				result[i + 5] = Array.get(array, start + i + 5);
				result[i + 6] = Array.get(array, start + i + 6);
				result[i + 7] = Array.get(array, start + i + 7);
			} for(; i < length; i++) result[i] = Array.get(array, start + i); return result;
		}
		protected static Object toObjectArray(Set<?> set, int start, int end) {
			ArrayUtils.assertCopyIndex(start, set.size(), end, set.size(), 0); int length = end - start;
			Object[] result = ArrayUtils.getTempObjectArray(set.size()); set.toArray(result);
			if(start != 0) System.arraycopy(result, start, result, 0, length); return result;
		}
		protected static Object toObjectArray(Map<?, ?> map, int start, int end) {
			/*if(map instanceof HashMap && !(map instanceof LinkedHashMap)) {
				ArrayUtils.assertCopyIndex(start, map.size(), end, map.size(), 0);
				Object table = MapUtils.getTable((HashMap) map); int length = end - start;
				if(table == null) return ArrayUtils.getTempObjectArray(-1);
				Object[] result = ArrayUtils.getTempObjectArray(length);
				System.arraycopy(table, start, result, 0, length); return result;
			}*/ return toObjectArray(map.entrySet(), start, end);
		}
	}
}
