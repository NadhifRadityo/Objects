package io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting;

import io.github.NadhifRadityo.Objects.Object.Comparator;

public abstract class CountingSort implements Sorting {
	public static final CountingSort DEFAULT = new CountingSort() { };

	@SuppressWarnings({"SuspiciousSystemArraycopy", "unchecked"})
	@Override public <T> void sort(T[] array, Comparator<? super T> comparator, int start, int end) {
		if(!Integer.class.isAssignableFrom(array.getClass().getComponentType())) for(int i = start; i < end; i++)
			if(!Integer.class.isAssignableFrom(array[i].getClass())) throw new IllegalArgumentException("Only works with integer");
		Class<? extends T> componentType = (Class<? extends T>) array.getClass().getComponentType();
		int range = (Integer) array[start];
		for(int i = start + 1; i < end; i++)
			range = Math.max((Integer) array[i], range);
		range++;
		int[] rangeArray = new int[range];
		for(int i = start; i < end; i++) {
			if((Integer) array[i] < 0)
				throw new IllegalArgumentException("Value cannot be less than 0");
			rangeArray[(Integer) array[i]]++;
		} for(int i = 1; i < rangeArray.length; i++)
			rangeArray[i] += rangeArray[i - 1];
		T pivot = componentType.cast(-1);
		Integer[] sorted = new Integer[end - start];
		for(int i = start; i < end; i++) {
			int value = (Integer) array[i];
			int place = rangeArray[value]-- - 1;
			if(comparator.compare(array[i], pivot) < 0)
				place = sorted.length - place - 1;
			sorted[place] = value;
		} System.arraycopy(sorted, 0, array, start, sorted.length);
	}
	@Override public void sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		int range = array[start];
		for(int i = start + 1; i < end; i++)
			range = Math.max(array[i], range);
		range++;
		int[] rangeArray = new int[range];
		for(int i = start; i < end; i++) {
			if(array[i] < 0)
				throw new IllegalArgumentException("Value cannot be less than 0");
			rangeArray[array[i]]++;
		} for(int i = 1; i < rangeArray.length; i++)
			rangeArray[i] += rangeArray[i - 1];
		final int pivot = -1;
		Integer[] sorted = new Integer[end - start];
		for(int i = start; i < end; i++) {
			int value = array[i];
			int place = rangeArray[value]-- - 1;
			if(comparator._compare(array[i], pivot) < 0)
				place = sorted.length - place - 1;
			sorted[place] = value;
		} System.arraycopy(sorted, 0, array, start, sorted.length);
	}
	@Override public void sort(long[] array, Comparator.PLongComparator comparator, int start, int end) { throw new IllegalArgumentException("Only works with integer"); }
	@Override public void sort(short[] array, Comparator.PShortComparator comparator, int start, int end) { throw new IllegalArgumentException("Only works with integer"); }
	@Override public void sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) { throw new IllegalArgumentException("Only works with integer"); }
	@Override public void sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) { throw new IllegalArgumentException("Only works with integer"); }
	@Override public void sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) { throw new IllegalArgumentException("Only works with integer"); }
}
