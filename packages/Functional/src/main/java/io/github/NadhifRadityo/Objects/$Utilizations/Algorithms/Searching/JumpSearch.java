package io.github.NadhifRadityo.Objects.$Utilizations.Algorithms.Searching;

import io.github.NadhifRadityo.Objects.$Interface.Functional.Comparator;

public abstract class JumpSearch implements Searching {
	public static final JumpSearch DEFAULT = new JumpSearch() { };

	@Override public <T> int search(T[] array, T key, Comparator<? super T> comparator, int start, int end) {
		int blockSize = (int) Math.sqrt(end - start);
		int limit = start + blockSize;
		while(comparator.compare(array[limit], key) < 0 && limit < end - 1)
			limit = Math.min(limit + blockSize, end - 1);
		for(int i = limit - blockSize; i <= limit; i++)
			if(comparator.compare(array[i], key) == 0)
				return i;
		return -1;
	}
	@Override public int search(int[] array, int key, Comparator.PIntegerComparator comparator, int start, int end) {
		int blockSize = (int) Math.sqrt(end - start);
		int limit = start + blockSize;
		while(comparator._compare(array[limit], key) < 0 && limit < end - 1)
			limit = Math.min(limit + blockSize, end - 1);
		for(int i = limit - blockSize; i <= limit; i++)
			if(comparator._compare(array[i], key) == 0)
				return i;
		return -1;
	}
	@Override public int search(long[] array, long key, Comparator.PLongComparator comparator, int start, int end) {
		int blockSize = (int) Math.sqrt(end - start);
		int limit = start + blockSize;
		while(comparator._compare(array[limit], key) < 0 && limit < end - 1)
			limit = Math.min(limit + blockSize, end - 1);
		for(int i = limit - blockSize; i <= limit; i++)
			if(comparator._compare(array[i], key) == 0)
				return i;
		return -1;
	}
	@Override public int search(short[] array, short key, Comparator.PShortComparator comparator, int start, int end) {
		int blockSize = (int) Math.sqrt(end - start);
		int limit = start + blockSize;
		while(comparator._compare(array[limit], key) < 0 && limit < end - 1)
			limit = Math.min(limit + blockSize, end - 1);
		for(int i = limit - blockSize; i <= limit; i++)
			if(comparator._compare(array[i], key) == 0)
				return i;
		return -1;
	}
	@Override public int search(float[] array, float key, Comparator.PFloatComparator comparator, int start, int end) {
		int blockSize = (int) Math.sqrt(end - start);
		int limit = start + blockSize;
		while(comparator._compare(array[limit], key) < 0 && limit < end - 1)
			limit = Math.min(limit + blockSize, end - 1);
		for(int i = limit - blockSize; i <= limit; i++)
			if(comparator._compare(array[i], key) == 0)
				return i;
		return -1;
	}
	@Override public int search(double[] array, double key, Comparator.PDoubleComparator comparator, int start, int end) {
		int blockSize = (int) Math.sqrt(end - start);
		int limit = start + blockSize;
		while(comparator._compare(array[limit], key) < 0 && limit < end - 1)
			limit = Math.min(limit + blockSize, end - 1);
		for(int i = limit - blockSize; i <= limit; i++)
			if(comparator._compare(array[i], key) == 0)
				return i;
		return -1;
	}
	@Override public int search(char[] array, char key, Comparator.PCharacterComparator comparator, int start, int end) {
		int blockSize = (int) Math.sqrt(end - start);
		int limit = start + blockSize;
		while(comparator._compare(array[limit], key) < 0 && limit < end - 1)
			limit = Math.min(limit + blockSize, end - 1);
		for(int i = limit - blockSize; i <= limit; i++)
			if(comparator._compare(array[i], key) == 0)
				return i;
		return -1;
	}
}
