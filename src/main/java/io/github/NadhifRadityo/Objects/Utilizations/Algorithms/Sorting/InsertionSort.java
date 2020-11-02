package io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting;

import io.github.NadhifRadityo.Objects.Object.Comparator;

public abstract class InsertionSort implements Sorting {
	public static final InsertionSort DEFAULT = new InsertionSort() { };

	@Override public <T> void sort(T[] array, Comparator<? super T> comparator, int start, int end) {
		for(int i = start + 1; i < end; i++) {
			T temp = array[i]; int j = i;
			while(j > start && comparator.compare(temp, array[j - 1]) < 0) {
				array[j] = array[j - 1]; j--;
			} array[j] = temp;
		}
	}
	@Override public void sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		for(int i = start + 1; i < end; i++) {
			int temp = array[i]; int j = i;
			while(j > start && comparator._compare(temp, array[j - 1]) < 0) {
				array[j] = array[j - 1]; j--;
			} array[j] = temp;
		}
	}
	@Override public void sort(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		for(int i = start + 1; i < end; i++) {
			long temp = array[i]; int j = i;
			while(j > start && comparator._compare(temp, array[j - 1]) < 0) {
				array[j] = array[j - 1]; j--;
			} array[j] = temp;
		}
	}
	@Override public void sort(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		for(int i = start + 1; i < end; i++) {
			short temp = array[i]; int j = i;
			while(j > start && comparator._compare(temp, array[j - 1]) < 0) {
				array[j] = array[j - 1]; j--;
			} array[j] = temp;
		}
	}
	@Override public void sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		for(int i = start + 1; i < end; i++) {
			float temp = array[i]; int j = i;
			while(j > start && comparator._compare(temp, array[j - 1]) < 0) {
				array[j] = array[j - 1]; j--;
			} array[j] = temp;
		}
	}
	@Override public void sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		for(int i = start + 1; i < end; i++) {
			double temp = array[i]; int j = i;
			while(j > start && comparator._compare(temp, array[j - 1]) < 0) {
				array[j] = array[j - 1]; j--;
			} array[j] = temp;
		}
	}
	@Override public void sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		for(int i = start + 1; i < end; i++) {
			char temp = array[i]; int j = i;
			while(j > start && comparator._compare(temp, array[j - 1]) < 0) {
				array[j] = array[j - 1]; j--;
			} array[j] = temp;
		}
	}
}
