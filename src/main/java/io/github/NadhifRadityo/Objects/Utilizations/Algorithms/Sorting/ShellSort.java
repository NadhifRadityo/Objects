package io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting;

import io.github.NadhifRadityo.Objects.Object.Comparator;

public abstract class ShellSort implements Sorting {
	public static final ShellSort DEFAULT = new ShellSort() { };

	@Override public <T> void sort(T[] array, Comparator<? super T> comparator, int start, int end) {
		int h = 1; T temp;
		int n = end - start;
		while(h < n / 3) h = 3 * h + 1;
		while(h >= 1) { for(int i = h; i < end; i++)
			for(int j = i; j >= h && (comparator.compare(array[j], array[j - h]) < 0); j -= h) {
				temp = array[j];
				array[j] = array[j - h];
				array[j - h] = temp;
			} h /= 3;
		}
	}
	@Override public void sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		int h = 1; int temp;
		int n = end - start;
		while(h < n / 3) h = 3 * h + 1;
		while(h >= 1) { for(int i = h; i < end; i++)
			for(int j = i; j >= h && (comparator._compare(array[j], array[j - h]) < 0); j -= h) {
				temp = array[j];
				array[j] = array[j - h];
				array[j - h] = temp;
			} h /= 3;
		}
	}
	@Override public void sort(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		int h = 1; long temp;
		int n = end - start;
		while(h < n / 3) h = 3 * h + 1;
		while(h >= 1) { for(int i = h; i < end; i++)
			for(int j = i; j >= h && (comparator._compare(array[j], array[j - h]) < 0); j -= h) {
				temp = array[j];
				array[j] = array[j - h];
				array[j - h] = temp;
			} h /= 3;
		}
	}
	@Override public void sort(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		int h = 1; short temp;
		int n = end - start;
		while(h < n / 3) h = 3 * h + 1;
		while(h >= 1) { for(int i = h; i < end; i++)
			for(int j = i; j >= h && (comparator._compare(array[j], array[j - h]) < 0); j -= h) {
				temp = array[j];
				array[j] = array[j - h];
				array[j - h] = temp;
			} h /= 3;
		}
	}
	@Override public void sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		int h = 1; float temp;
		int n = end - start;
		while(h < n / 3) h = 3 * h + 1;
		while(h >= 1) { for(int i = h; i < end; i++)
			for(int j = i; j >= h && (comparator._compare(array[j], array[j - h]) < 0); j -= h) {
				temp = array[j];
				array[j] = array[j - h];
				array[j - h] = temp;
			} h /= 3;
		}
	}
	@Override public void sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		int h = 1; double temp;
		int n = end - start;
		while(h < n / 3) h = 3 * h + 1;
		while(h >= 1) { for(int i = h; i < end; i++)
			for(int j = i; j >= h && (comparator._compare(array[j], array[j - h]) < 0); j -= h) {
				temp = array[j];
				array[j] = array[j - h];
				array[j - h] = temp;
			} h /= 3;
		}
	}
	@Override public void sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		int h = 1; char temp;
		int n = end - start;
		while(h < n / 3) h = 3 * h + 1;
		while(h >= 1) { for(int i = h; i < end; i++)
			for(int j = i; j >= h && (comparator._compare(array[j], array[j - h]) < 0); j -= h) {
				temp = array[j];
				array[j] = array[j - h];
				array[j - h] = temp;
			} h /= 3;
		}
	}
}
