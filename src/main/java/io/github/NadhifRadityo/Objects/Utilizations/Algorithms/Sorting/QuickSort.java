package io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting;

import io.github.NadhifRadityo.Objects.Object.Comparator;

public abstract class QuickSort implements Sorting {
	public static final QuickSort DEFAULT = new QuickSort() { };

	@Override public <T> void sort(T[] array, Comparator<? super T> comparator, int start, int end) {
		_sort(array, comparator, start, end - 1);
	}
	@Override public void sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		_sort(array, comparator, start, end - 1);
	}
	@Override public void sort(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		_sort(array, comparator, start, end - 1);
	}
	@Override public void sort(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		_sort(array, comparator, start, end - 1);
	}
	@Override public void sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		_sort(array, comparator, start, end - 1);
	}
	@Override public void sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		_sort(array, comparator, start, end - 1);
	}
	@Override public void sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		_sort(array, comparator, start, end - 1);
	}

	public <T> void _sort(T[] array, Comparator<? super T> comparator, int start, int end) {
		if(start >= end) return;
		int index =  partition(array, comparator, start, end);
		_sort(array, comparator, start, index - 1);
		_sort(array, comparator, index + 1, end);
	}
	public void _sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		if(start >= end) return;
		int index =  partition(array, comparator, start, end);
		_sort(array, comparator, start, index - 1);
		_sort(array, comparator, index + 1, end);
	}
	public void _sort(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		if(start >= end) return;
		int index =  partition(array, comparator, start, end);
		_sort(array, comparator, start, index - 1);
		_sort(array, comparator, index + 1, end);
	}
	public void _sort(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		if(start >= end) return;
		int index =  partition(array, comparator, start, end);
		_sort(array, comparator, start, index - 1);
		_sort(array, comparator, index + 1, end);
	}
	public void _sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		if(start >= end) return;
		int index =  partition(array, comparator, start, end);
		_sort(array, comparator, start, index - 1);
		_sort(array, comparator, index + 1, end);
	}
	public void _sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		if(start >= end) return;
		int index =  partition(array, comparator, start, end);
		_sort(array, comparator, start, index - 1);
		_sort(array, comparator, index + 1, end);
	}
	public void _sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		if(start >= end) return;
		int index =  partition(array, comparator, start, end);
		_sort(array, comparator, start, index - 1);
		_sort(array, comparator, index + 1, end);
	}

	protected static <T> int partition(T[] array, Comparator<? super T> comparator, int start, int end) {
		T temp; T pivot = medianOf3(array, comparator, start, start + (end - start) / 2, end);
		while(start <= end) {
			while(comparator.compare(array[start], pivot) < 0) start++;
			while(comparator.compare(array[end], pivot) > 0) end--;
			if(start > end) continue;
			temp = array[start];
			array[start] = array[end];
			array[end] = temp;
			start++; end--;
		} return start;
	}
	protected static int partition(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		int temp; int pivot = medianOf3(array, comparator, start, start + (end - start) / 2, end);
		while(start <= end) {
			while(comparator.compare(array[start], pivot) < 0) start++;
			while(comparator.compare(array[end], pivot) > 0) end--;
			if(start > end) continue;
			temp = array[start];
			array[start] = array[end];
			array[end] = temp;
			start++; end--;
		} return start;
	}
	protected static int partition(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		long temp; long pivot = medianOf3(array, comparator, start, start + (end - start) / 2, end);
		while(start <= end) {
			while(comparator.compare(array[start], pivot) < 0) start++;
			while(comparator.compare(array[end], pivot) > 0) end--;
			if(start > end) continue;
			temp = array[start];
			array[start] = array[end];
			array[end] = temp;
			start++; end--;
		} return start;
	}
	protected static int partition(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		short temp; short pivot = medianOf3(array, comparator, start, start + (end - start) / 2, end);
		while(start <= end) {
			while(comparator.compare(array[start], pivot) < 0) start++;
			while(comparator.compare(array[end], pivot) > 0) end--;
			if(start > end) continue;
			temp = array[start];
			array[start] = array[end];
			array[end] = temp;
			start++; end--;
		} return start;
	}
	protected static int partition(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		float temp; float pivot = medianOf3(array, comparator, start, start + (end - start) / 2, end);
		while(start <= end) {
			while(comparator.compare(array[start], pivot) < 0) start++;
			while(comparator.compare(array[end], pivot) > 0) end--;
			if(start > end) continue;
			temp = array[start];
			array[start] = array[end];
			array[end] = temp;
			start++; end--;
		} return start;
	}
	protected static int partition(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		double temp; double pivot = medianOf3(array, comparator, start, start + (end - start) / 2, end);
		while(start <= end) {
			while(comparator.compare(array[start], pivot) < 0) start++;
			while(comparator.compare(array[end], pivot) > 0) end--;
			if(start > end) continue;
			temp = array[start];
			array[start] = array[end];
			array[end] = temp;
			start++; end--;
		} return start;
	}
	protected static int partition(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		char temp; char pivot = medianOf3(array, comparator, start, start + (end - start) / 2, end);
		while(start <= end) {
			while(comparator.compare(array[start], pivot) < 0) start++;
			while(comparator.compare(array[end], pivot) > 0) end--;
			if(start > end) continue;
			temp = array[start];
			array[start] = array[end];
			array[end] = temp;
			start++; end--;
		} return start;
	}

	protected static <T> T medianOf3(T[] array, Comparator<? super T> comparator, int start, int middle, int end) {
		return comparator.compare(array[middle], array[start]) < 0 ? (
				comparator.compare(array[end], array[middle]) < 0 ? array[middle] :
				comparator.compare(array[end], array[start]) < 0 ? array[end] : array[start]
			) : (
				comparator.compare(array[end], array[middle]) < 0 ? comparator.compare(array[end], array[start]) < 0 ?
				array[start] : array[end] : array[middle]
			);
	}
	protected static int medianOf3(int[] array, Comparator.PIntegerComparator comparator, int start, int middle, int end) {
		return comparator._compare(array[middle], array[start]) < 0 ? (
				comparator._compare(array[end], array[middle]) < 0 ? array[middle] :
				comparator._compare(array[end], array[start]) < 0 ? array[end] : array[start]
			) : (
				comparator._compare(array[end], array[middle]) < 0 ? comparator._compare(array[end], array[start]) < 0 ?
				array[start] : array[end] : array[middle]
			);
	}
	protected static long medianOf3(long[] array, Comparator.PLongComparator comparator, int start, int middle, int end) {
		return comparator._compare(array[middle], array[start]) < 0 ? (
				comparator._compare(array[end], array[middle]) < 0 ? array[middle] :
				comparator._compare(array[end], array[start]) < 0 ? array[end] : array[start]
			) : (
				comparator._compare(array[end], array[middle]) < 0 ? comparator._compare(array[end], array[start]) < 0 ?
				array[start] : array[end] : array[middle]
			);
	}
	protected static short medianOf3(short[] array, Comparator.PShortComparator comparator, int start, int middle, int end) {
		return comparator._compare(array[middle], array[start]) < 0 ? (
				comparator._compare(array[end], array[middle]) < 0 ? array[middle] :
				comparator._compare(array[end], array[start]) < 0 ? array[end] : array[start]
			) : (
				comparator._compare(array[end], array[middle]) < 0 ? comparator._compare(array[end], array[start]) < 0 ?
				array[start] : array[end] : array[middle]
			);
	}
	protected static float medianOf3(float[] array, Comparator.PFloatComparator comparator, int start, int middle, int end) {
		return comparator._compare(array[middle], array[start]) < 0 ? (
				comparator._compare(array[end], array[middle]) < 0 ? array[middle] :
				comparator._compare(array[end], array[start]) < 0 ? array[end] : array[start]
			) : (
				comparator._compare(array[end], array[middle]) < 0 ? comparator._compare(array[end], array[start]) < 0 ?
				array[start] : array[end] : array[middle]
			);
	}
	protected static double medianOf3(double[] array, Comparator.PDoubleComparator comparator, int start, int middle, int end) {
		return comparator._compare(array[middle], array[start]) < 0 ? (
				comparator._compare(array[end], array[middle]) < 0 ? array[middle] :
				comparator._compare(array[end], array[start]) < 0 ? array[end] : array[start]
			) : (
				comparator._compare(array[end], array[middle]) < 0 ? comparator._compare(array[end], array[start]) < 0 ?
				array[start] : array[end] : array[middle]
			);
	}
	protected static char medianOf3(char[] array, Comparator.PCharacterComparator comparator, int start, int middle, int end) {
		return comparator._compare(array[middle], array[start]) < 0 ? (
				comparator._compare(array[end], array[middle]) < 0 ? array[middle] :
				comparator._compare(array[end], array[start]) < 0 ? array[end] : array[start]
			) : (
				comparator._compare(array[end], array[middle]) < 0 ? comparator._compare(array[end], array[start]) < 0 ?
				array[start] : array[end] : array[middle]
			);
	}
}
