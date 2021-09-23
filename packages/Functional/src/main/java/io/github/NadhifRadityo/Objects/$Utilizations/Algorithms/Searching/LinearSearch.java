package io.github.NadhifRadityo.Objects.$Utilizations.Algorithms.Searching;

import io.github.NadhifRadityo.Objects.$Interface.Functional.Comparator;

public abstract class LinearSearch implements Searching {
	public static final LinearSearch DEFAULT = new LinearSearch() { };

	@Override public <T> int search(T[] array, T key, Comparator<? super T> comparator, int start, int end) {
		for(int i = start; i < end; i++)
			if(comparator.compare(array[i], key) == 0)
				return i;
		return -1;
	}
	@Override public int search(int[] array, int key, Comparator.PIntegerComparator comparator, int start, int end) {
		for(int i = start; i < end; i++)
			if(comparator._compare(array[i], key) == 0)
				return i;
		return -1;
	}
	@Override public int search(long[] array, long key, Comparator.PLongComparator comparator, int start, int end) {
		for(int i = start; i < end; i++)
			if(comparator._compare(array[i], key) == 0)
				return i;
		return -1;
	}
	@Override public int search(short[] array, short key, Comparator.PShortComparator comparator, int start, int end) {
		for(int i = start; i < end; i++)
			if(comparator._compare(array[i], key) == 0)
				return i;
		return -1;
	}
	@Override public int search(float[] array, float key, Comparator.PFloatComparator comparator, int start, int end) {
		for(int i = start; i < end; i++)
			if(comparator._compare(array[i], key) == 0)
				return i;
		return -1;
	}
	@Override public int search(double[] array, double key, Comparator.PDoubleComparator comparator, int start, int end) {
		for(int i = start; i < end; i++)
			if(comparator._compare(array[i], key) == 0)
				return i;
		return -1;
	}
	@Override public int search(char[] array, char key, Comparator.PCharacterComparator comparator, int start, int end) {
		for(int i = start; i < end; i++)
			if(comparator._compare(array[i], key) == 0)
				return i;
		return -1;
	}
}
