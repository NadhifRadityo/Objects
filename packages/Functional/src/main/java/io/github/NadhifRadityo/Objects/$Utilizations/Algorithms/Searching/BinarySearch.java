package io.github.NadhifRadityo.Objects.$Utilizations.Algorithms.Searching;

import io.github.NadhifRadityo.Objects.$Interface.Functional.Comparator;

public abstract class BinarySearch implements Searching {
	public static final BinarySearch DEFAULT = new BinarySearch() { };

	@Override public <T> int search(T[] array, T key, Comparator<? super T> comparator, int start, int end) {
		end -= 1; while(start <= end) {
			int mid = (start + end) >>> 1;
			int cmp = comparator.compare(array[mid], key);
			if(cmp == 0) return mid;
			else if(cmp > 0) end = mid - 1;
			else start = mid + 1;
		} return -1;
	}
	@Override public int search(int[] array, int key, Comparator.PIntegerComparator comparator, int start, int end) {
		end -= 1; while(start <= end) {
			int mid = (start + end) >>> 1;
			int cmp = comparator._compare(array[mid], key);
			if(cmp == 0) return mid;
			else if(cmp > 0) end = mid - 1;
			else start = mid + 1;
		} return -1;
	}
	@Override public int search(long[] array, long key, Comparator.PLongComparator comparator, int start, int end) {
		end -= 1; while(start <= end) {
			int mid = (start + end) >>> 1;
			int cmp = comparator._compare(array[mid], key);
			if(cmp == 0) return mid;
			else if(cmp > 0) end = mid - 1;
			else start = mid + 1;
		} return -1;
	}
	@Override public int search(short[] array, short key, Comparator.PShortComparator comparator, int start, int end) {
		end -= 1; while(start <= end) {
			int mid = (start + end) >>> 1;
			int cmp = comparator._compare(array[mid], key);
			if(cmp == 0) return mid;
			else if(cmp > 0) end = mid - 1;
			else start = mid + 1;
		} return -1;
	}
	@Override public int search(float[] array, float key, Comparator.PFloatComparator comparator, int start, int end) {
		end -= 1; while(start <= end) {
			int mid = (start + end) >>> 1;
			int cmp = comparator._compare(array[mid], key);
			if(cmp == 0) return mid;
			else if(cmp > 0) end = mid - 1;
			else start = mid + 1;
		} return -1;
	}
	@Override public int search(double[] array, double key, Comparator.PDoubleComparator comparator, int start, int end) {
		end -= 1; while(start <= end) {
			int mid = (start + end) >>> 1;
			int cmp = comparator._compare(array[mid], key);
			if(cmp == 0) return mid;
			else if(cmp > 0) end = mid - 1;
			else start = mid + 1;
		} return -1;
	}
	@Override public int search(char[] array, char key, Comparator.PCharacterComparator comparator, int start, int end) {
		end -= 1; while(start <= end) {
			int mid = (start + end) >>> 1;
			int cmp = comparator._compare(array[mid], key);
			if(cmp == 0) return mid;
			else if(cmp > 0) end = mid - 1;
			else start = mid + 1;
		} return -1;
	}
}
