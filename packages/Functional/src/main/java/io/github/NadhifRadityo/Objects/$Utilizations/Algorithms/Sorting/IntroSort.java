package io.github.NadhifRadityo.Objects.$Utilizations.Algorithms.Sorting;

import io.github.NadhifRadityo.Objects.$Interface.Functional.Comparator;

import static io.github.NadhifRadityo.Objects.$Utilizations.Algorithms.Sorting.QuickSort.partition;

public abstract class IntroSort implements Sorting {
	public static final IntroSort DEFAULT = new IntroSort() { };

	protected int sizeThreshold;
	protected int depthLimit;
	public IntroSort(int sizeThreshold, int depthLimit) { this.sizeThreshold = sizeThreshold; this.depthLimit = depthLimit; }
	public IntroSort(int sizeThreshold) { this(sizeThreshold, 0); }
	public IntroSort() { this(32); }

	public int getSizeThreshold() { return sizeThreshold; }
	public int getDepthLimit() { return depthLimit; }

	public void setSizeThreshold(int sizeThreshold) { this.sizeThreshold = sizeThreshold; }
	public void setDepthLimit(int depthLimit) { this.depthLimit = depthLimit; }

	@Override public <T> void sort(T[] array, Comparator<? super T> comparator, int start, int end) {
		int depth = (int) Math.floor(Math.log(end - start) / Math.log(2)) * 2;
		sort(array, comparator, sizeThreshold, depth, depthLimit, start, end);
	}
	@Override public void sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		int depth = (int) Math.floor(Math.log(end - start) / Math.log(2)) * 2;
		sort(array, comparator, sizeThreshold, depth, depthLimit, start, end);
	}
	@Override public void sort(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		int depth = (int) Math.floor(Math.log(end - start) / Math.log(2)) * 2;
		sort(array, comparator, sizeThreshold, depth, depthLimit, start, end);
	}
	@Override public void sort(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		int depth = (int) Math.floor(Math.log(end - start) / Math.log(2)) * 2;
		sort(array, comparator, sizeThreshold, depth, depthLimit, start, end);
	}
	@Override public void sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		int depth = (int) Math.floor(Math.log(end - start) / Math.log(2)) * 2;
		sort(array, comparator, sizeThreshold, depth, depthLimit, start, end);
	}
	@Override public void sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		int depth = (int) Math.floor(Math.log(end - start) / Math.log(2)) * 2;
		sort(array, comparator, sizeThreshold, depth, depthLimit, start, end);
	}
	@Override public void sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		int depth = (int) Math.floor(Math.log(end - start) / Math.log(2)) * 2;
		sort(array, comparator, sizeThreshold, depth, depthLimit, start, end);
	}

	protected static <T> void sort(T[] array, Comparator<? super T> comparator, int sizeThreshold, int depth, int depthLimit, int start, int end) {
		while(end - start > sizeThreshold) {
			if(depth <= depthLimit) { HeapSort.DEFAULT.sort(array, comparator, start, end); return; }
			int index = partition(array, comparator, start, end - 1);
			sort(array, comparator, sizeThreshold, depth - 1, depthLimit, index, end);
			end = index;
		} InsertionSort.DEFAULT.sort(array, comparator, start, end);
	}
	protected static void sort(int[] array, Comparator.PIntegerComparator comparator, int sizeThreshold, int depth, int depthLimit, int start, int end) {
		while(end - start > sizeThreshold) {
			if(depth <= depthLimit) { HeapSort.DEFAULT.sort(array, comparator, start, end); return; }
			int index = partition(array, comparator, start, end - 1);
			sort(array, comparator, sizeThreshold, depth - 1, depthLimit, index, end);
			end = index;
		} InsertionSort.DEFAULT.sort(array, comparator, start, end);
	}
	protected static void sort(long[] array, Comparator.PLongComparator comparator, int sizeThreshold, int depth, int depthLimit, int start, int end) {
		while(end - start > sizeThreshold) {
			if(depth <= depthLimit) { HeapSort.DEFAULT.sort(array, comparator, start, end); return; }
			int index = partition(array, comparator, start, end - 1);
			sort(array, comparator, sizeThreshold, depth - 1, depthLimit, index, end);
			end = index;
		} InsertionSort.DEFAULT.sort(array, comparator, start, end);
	}
	protected static void sort(short[] array, Comparator.PShortComparator comparator, int sizeThreshold, int depth, int depthLimit, int start, int end) {
		while(end - start > sizeThreshold) {
			if(depth <= depthLimit) { HeapSort.DEFAULT.sort(array, comparator, start, end); return; }
			int index = partition(array, comparator, start, end - 1);
			sort(array, comparator, sizeThreshold, depth - 1, depthLimit, index, end);
			end = index;
		} InsertionSort.DEFAULT.sort(array, comparator, start, end);
	}
	protected static void sort(float[] array, Comparator.PFloatComparator comparator, int sizeThreshold, int depth, int depthLimit, int start, int end) {
		while(end - start > sizeThreshold) {
			if(depth <= depthLimit) { HeapSort.DEFAULT.sort(array, comparator, start, end); return; }
			int index = partition(array, comparator, start, end - 1);
			sort(array, comparator, sizeThreshold, depth - 1, depthLimit, index, end);
			end = index;
		} InsertionSort.DEFAULT.sort(array, comparator, start, end);
	}
	protected static void sort(double[] array, Comparator.PDoubleComparator comparator, int sizeThreshold, int depth, int depthLimit, int start, int end) {
		while(end - start > sizeThreshold) {
			if(depth <= depthLimit) { HeapSort.DEFAULT.sort(array, comparator, start, end); return; }
			int index = partition(array, comparator, start, end - 1);
			sort(array, comparator, sizeThreshold, depth - 1, depthLimit, index, end);
			end = index;
		} InsertionSort.DEFAULT.sort(array, comparator, start, end);
	}
	protected static void sort(char[] array, Comparator.PCharacterComparator comparator, int sizeThreshold, int depth, int depthLimit, int start, int end) {
		while(end - start > sizeThreshold) {
			if(depth <= depthLimit) { HeapSort.DEFAULT.sort(array, comparator, start, end); return; }
			int index = partition(array, comparator, start, end - 1);
			sort(array, comparator, sizeThreshold, depth - 1, depthLimit, index, end);
			end = index;
		} InsertionSort.DEFAULT.sort(array, comparator, start, end);
	}
}
