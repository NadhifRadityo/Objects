package io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting;

import io.github.NadhifRadityo.Objects.Object.Comparator;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ListUtils;
import io.github.NadhifRadityo.Objects.Utilizations.MapUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public interface Sorting {

	<T> void sort(T[] array, Comparator<? super T> comparator, int start, int end);
	default <T> void sort(T[] array, Comparator<? super T> comparator) { sort(array, comparator, 0, array.length); }
	default <T extends Comparable<? super T>> void sort(T[] array, int start, int end) { sort(array, Comparator.naturalOrder(), start, end); }
	default <T extends Comparable<? super T>> void sort(T[] array) { sort(array, 0, array.length); }

	@SuppressWarnings("unchecked")
	default <T> void sort(List<T> list, Comparator<? super T> comparator, int start, int end) {
		Object[] array;
		if(list instanceof ArrayList) array = ListUtils.getElementData((ArrayList<T>) list);
		else array = list.toArray();
		sort(array, comparator, start, end);
		if(list instanceof ArrayList) return;
		ListIterator<T> iterator = list.listIterator();
		for(Object object : array) { iterator.next(); iterator.set((T) object); }
	}
	default <T> void sort(List<T> list, Comparator<? super T> comparator) { sort(list, comparator, 0, list.size()); }
	default <T extends Comparable<? super T>> void sort(List<T> list, int start, int end) { sort(list, Comparator.naturalOrder(), start, end); }
	default <T extends Comparable<? super T>> void sort(List<T> list) { sort(list, 0, list.size()); }

	@SuppressWarnings("unchecked")
	default <T> void sort(Object object, Comparator<? super T> comparator, int start, int end) {
		if(object.getClass().isArray() && object.getClass().getComponentType().isPrimitive()) {
			Object boxed = __.toObjectArray(object, start, end);
			int length = end - start; sort((T[]) boxed, comparator, 0, length);
			for(int i = 0; i < length; i++) Array.set(object, start + i, Array.get(boxed, i));
			return;
		}
		if(object.getClass().isArray()) { sort((T[]) object, comparator, start, end); return; }
		if(object instanceof Map) {
			Map<Object, Object> map = (Map<Object, Object>) object;
			Object boxed = __.toObjectArray(map, start, end);
			int length = end - start; sort((T[]) boxed, comparator, 0, length);
			map.clear(); for(int i = 0; i < length; i++) {
				Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) Array.get(boxed, i);
				map.put(entry.getKey(), entry.getValue());
			} return;
		}
		if(object instanceof List) { sort((List<T>) object, comparator, start, end); return; }
		throw new IllegalArgumentException("Object is not an array");
	}
	@SuppressWarnings("unchecked")
	default <T> void sort(Object object, Comparator<? super T> comparator) { int length;
		if(object.getClass().isArray()) length = Array.getLength(object);
		else if(object instanceof Map) length = ((Map<Object, Object>) object).size();
		else if(object instanceof List) length = ((List<T>) object).size();
		else throw new IllegalArgumentException("Object is not an array");
		sort(object, comparator, 0, length);
	}
	default void sort(Object object, int start, int end) { if(!(object instanceof Comparable)) throw new IllegalArgumentException("Object is not comparable"); sort(object, Comparator.naturalOrder(), start, end); }
	default void sort(Object object) { if(!(object instanceof Comparable)) throw new IllegalArgumentException("Object is not comparable"); sort(object, Comparator.naturalOrder()); }

	void sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end);
	void sort(long[] array, Comparator.PLongComparator comparator, int start, int end);
	void sort(short[] array, Comparator.PShortComparator comparator, int start, int end);
	void sort(float[] array, Comparator.PFloatComparator comparator, int start, int end);
	void sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end);
	void sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end);
	default void sort(int[] array, int start, int end) { sort(array, Comparator.PIntegerComparator.naturalOrder, start, end); }
	default void sort(long[] array, int start, int end) { sort(array, Comparator.PLongComparator.naturalOrder, start, end); }
	default void sort(short[] array, int start, int end) { sort(array, Comparator.PShortComparator.naturalOrder, start, end); }
	default void sort(float[] array, int start, int end) { sort(array, Comparator.PFloatComparator.naturalOrder, start, end); }
	default void sort(double[] array, int start, int end) { sort(array, Comparator.PDoubleComparator.naturalOrder, start, end); }
	default void sort(char[] array, int start, int end) { sort(array, Comparator.PCharacterComparator.naturalOrder, start, end); }
	default void sort(int[] array) { sort(array, 0, array.length); }
	default void sort(long[] array) { sort(array, 0, array.length); }
	default void sort(short[] array) { sort(array, 0, array.length); }
	default void sort(float[] array) { sort(array, 0, array.length); }
	default void sort(double[] array) { sort(array, 0, array.length); }
	default void sort(char[] array) { sort(array, 0, array.length); }

	Sorting BOGO_SORT = BogoSort.DEFAULT;
	static Sorting BOGO_SORT(long seed) { return new BogoSort(seed) { }; }
	Sorting BUBBLE_SORT = BubbleSort.DEFAULT;
	Sorting BUCKET_SORT = BucketSort.DEFAULT;
	Sorting COUNTING_SORT = CountingSort.DEFAULT;
	Sorting HEAP_SORT = HeapSort.DEFAULT;
	Sorting INSERTION_SORT = InsertionSort.DEFAULT;
	Sorting INTRO_SORT = IntroSort.DEFAULT;
	static Sorting INTRO_SORT(int sizeThreshold, int depthLimit) { return new IntroSort(sizeThreshold, depthLimit) { }; }
	static Sorting INTRO_SORT(int sizeThreshold) { return INTRO_SORT(sizeThreshold, 0); }
	Sorting MERGE_SORT = MergeSort.DEFAULT;
	Sorting QUICK_SORT = QuickSort.DEFAULT;
	Sorting SELECTION_SORT = SelectionSort.DEFAULT;
	Sorting SHELL_SORT = ShellSort.DEFAULT;

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
