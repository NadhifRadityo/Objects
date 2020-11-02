package io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting;

import io.github.NadhifRadityo.Objects.Object.Comparator;

public abstract class HeapSort implements Sorting {
	public static final HeapSort DEFAULT = new HeapSort() { };

	@Override public <T> void sort(T[] array, Comparator<? super T> comparator, int start, int end) {
		for(int i = start + (end - start) / 2 - 1; i >= start; i--)
			heapify(array, comparator, end - start, i);
		T temp; for(int i = end - 1; i >= start; i--) {
			temp = array[start];
			array[start] = array[i];
			array[i] = temp;
			heapify(array, comparator, i, 0);
		}
	}
	@Override public void sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		for(int i = start + (end - start) / 2 - 1; i >= start; i--)
			heapify(array, comparator, end - start, i);
		int temp; for(int i = end - 1; i >= start; i--) {
			temp = array[start];
			array[start] = array[i];
			array[i] = temp;
			heapify(array, comparator, i, 0);
		}
	}
	@Override public void sort(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		for(int i = start + (end - start) / 2 - 1; i >= start; i--)
			heapify(array, comparator, end - start, i);
		long temp; for(int i = end - 1; i >= start; i--) {
			temp = array[start];
			array[start] = array[i];
			array[i] = temp;
			heapify(array, comparator, i, 0);
		}
	}
	@Override public void sort(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		for(int i = start + (end - start) / 2 - 1; i >= start; i--)
			heapify(array, comparator, end - start, i);
		short temp; for(int i = end - 1; i >= start; i--) {
			temp = array[start];
			array[start] = array[i];
			array[i] = temp;
			heapify(array, comparator, i, 0);
		}
	}
	@Override public void sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		for(int i = start + (end - start) / 2 - 1; i >= start; i--)
			heapify(array, comparator, end - start, i);
		float temp; for(int i = end - 1; i >= start; i--) {
			temp = array[start];
			array[start] = array[i];
			array[i] = temp;
			heapify(array, comparator, i, 0);
		}
	}
	@Override public void sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		for(int i = start + (end - start) / 2 - 1; i >= start; i--)
			heapify(array, comparator, end - start, i);
		double temp; for(int i = end - 1; i >= start; i--) {
			temp = array[start];
			array[start] = array[i];
			array[i] = temp;
			heapify(array, comparator, i, 0);
		}
	}
	@Override public void sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		for(int i = start + (end - start) / 2 - 1; i >= start; i--)
			heapify(array, comparator, end - start, i);
		char temp; for(int i = end - 1; i >= start; i--) {
			temp = array[start];
			array[start] = array[i];
			array[i] = temp;
			heapify(array, comparator, i, 0);
		}
	}

	protected static <T> void heapify(T[] array, Comparator<? super T> comparator, int n, int i) {
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		if(l < n && comparator.compare(array[l], array[largest]) > 0) largest = l;
		if(r < n && comparator.compare(array[r], array[largest]) > 0) largest = r;
		if(largest == i) return;
		T swap = array[i];
		array[i] = array[largest];
		array[largest] = swap;
		heapify(array, comparator, n, largest);
	}
	protected static void heapify(int[] array, Comparator.PIntegerComparator comparator, int n, int i) {
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		if(l < n && comparator._compare(array[l], array[largest]) > 0) largest = l;
		if(r < n && comparator._compare(array[r], array[largest]) > 0) largest = r;
		if(largest == i) return;
		int swap = array[i];
		array[i] = array[largest];
		array[largest] = swap;
		heapify(array, comparator, n, largest);
	}
	protected static void heapify(long[] array, Comparator.PLongComparator comparator, int n, int i) {
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		if(l < n && comparator._compare(array[l], array[largest]) > 0) largest = l;
		if(r < n && comparator._compare(array[r], array[largest]) > 0) largest = r;
		if(largest == i) return;
		long swap = array[i];
		array[i] = array[largest];
		array[largest] = swap;
		heapify(array, comparator, n, largest);
	}
	protected static void heapify(short[] array, Comparator.PShortComparator comparator, int n, int i) {
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		if(l < n && comparator._compare(array[l], array[largest]) > 0) largest = l;
		if(r < n && comparator._compare(array[r], array[largest]) > 0) largest = r;
		if(largest == i) return;
		short swap = array[i];
		array[i] = array[largest];
		array[largest] = swap;
		heapify(array, comparator, n, largest);
	}
	protected static void heapify(float[] array, Comparator.PFloatComparator comparator, int n, int i) {
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		if(l < n && comparator._compare(array[l], array[largest]) > 0) largest = l;
		if(r < n && comparator._compare(array[r], array[largest]) > 0) largest = r;
		if(largest == i) return;
		float swap = array[i];
		array[i] = array[largest];
		array[largest] = swap;
		heapify(array, comparator, n, largest);
	}
	protected static void heapify(double[] array, Comparator.PDoubleComparator comparator, int n, int i) {
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		if(l < n && comparator._compare(array[l], array[largest]) > 0) largest = l;
		if(r < n && comparator._compare(array[r], array[largest]) > 0) largest = r;
		if(largest == i) return;
		double swap = array[i];
		array[i] = array[largest];
		array[largest] = swap;
		heapify(array, comparator, n, largest);
	}
	protected static void heapify(char[] array, Comparator.PCharacterComparator comparator, int n, int i) {
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		if(l < n && comparator._compare(array[l], array[largest]) > 0) largest = l;
		if(r < n && comparator._compare(array[r], array[largest]) > 0) largest = r;
		if(largest == i) return;
		char swap = array[i];
		array[i] = array[largest];
		array[largest] = swap;
		heapify(array, comparator, n, largest);
	}
}
