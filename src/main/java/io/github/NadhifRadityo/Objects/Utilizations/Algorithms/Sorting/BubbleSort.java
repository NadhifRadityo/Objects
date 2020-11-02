package io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting;

import io.github.NadhifRadityo.Objects.Object.Comparator;

public abstract class BubbleSort implements Sorting {
	public static final BubbleSort DEFAULT = new BubbleSort() { };

	@Override public <T> void sort(T[] array, Comparator<? super T> comparator, int start, int end) {
		T temp; for(int i = start; i < end - 1; i++)
			for(int j = start; j < end - i - 1; j++) {
				if(comparator.compare(array[j], array[j + 1]) <= 0)
					continue;
				temp = array[j];
				array[j] = array[j + 1];
				array[j + 1] = temp;
			}
	}
	@Override public void sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		int temp; for(int i = start; i < end - 1; i++)
			for(int j = start; j < end - i - 1; j++) {
				if(comparator._compare(array[j], array[j + 1]) <= 0)
					continue;
				temp = array[j];
				array[j] = array[j + 1];
				array[j + 1] = temp;
			}
	}
	@Override public void sort(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		long temp; for(int i = start; i < end - 1; i++)
			for(int j = start; j < end - i - 1; j++) {
				if(comparator._compare(array[j], array[j + 1]) <= 0)
					continue;
				temp = array[j];
				array[j] = array[j + 1];
				array[j + 1] = temp;
			}
	}
	@Override public void sort(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		short temp; for(int i = start; i < end - 1; i++)
			for(int j = start; j < end - i - 1; j++) {
				if(comparator._compare(array[j], array[j + 1]) <= 0)
					continue;
				temp = array[j];
				array[j] = array[j + 1];
				array[j + 1] = temp;
			}
	}
	@Override public void sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		float temp; for(int i = start; i < end - 1; i++)
			for(int j = start; j < end - i - 1; j++) {
				if(comparator._compare(array[j], array[j + 1]) <= 0)
					continue;
				temp = array[j];
				array[j] = array[j + 1];
				array[j + 1] = temp;
			}
	}
	@Override public void sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		double temp; for(int i = start; i < end - 1; i++)
			for(int j = start; j < end - i - 1; j++) {
				if(comparator._compare(array[j], array[j + 1]) <= 0)
					continue;
				temp = array[j];
				array[j] = array[j + 1];
				array[j + 1] = temp;
			}
	}
	@Override public void sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		char temp; for(int i = start; i < end - 1; i++)
			for(int j = start; j < end - i - 1; j++) {
				if(comparator._compare(array[j], array[j + 1]) <= 0)
					continue;
				temp = array[j];
				array[j] = array[j + 1];
				array[j + 1] = temp;
			}
	}
}
