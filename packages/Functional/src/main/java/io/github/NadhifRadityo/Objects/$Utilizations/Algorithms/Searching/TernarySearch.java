package io.github.NadhifRadityo.Objects.$Utilizations.Algorithms.Searching;

import io.github.NadhifRadityo.Objects.$Interface.Functional.Comparator;

public abstract class TernarySearch implements Searching {
	public static final TernarySearch DEFAULT = new TernarySearch() { };

	@Override public <T> int search(T[] array, T key, Comparator<? super T> comparator, int start, int end) {
		end -= 1; while(end > start) {
			int cmpLeft = comparator.compare(array[start], key);
			if(cmpLeft == 0) return start;
			int cmpRight = comparator.compare(array[end], key);
			if(cmpRight == 0) return end;
			int leftThird = start + (end - start) / 3 + 1;
			int rightThird = end - (end - start) / 3 - 1;
			if(comparator.compare(array[leftThird], key) <= 0)
				start = leftThird; else end = rightThird;
		} return -1;
	}
	@Override public int search(int[] array, int key, Comparator.PIntegerComparator comparator, int start, int end) {
		end -= 1; while(end > start) {
			int cmpLeft = comparator._compare(array[start], key);
			if(cmpLeft == 0) return start;
			int cmpRight = comparator._compare(array[end], key);
			if(cmpRight == 0) return end;
			int leftThird = start + (end - start) / 3 + 1;
			int rightThird = end - (end - start) / 3 - 1;
			if(comparator.compare(array[leftThird], key) <= 0)
				start = leftThird; else end = rightThird;
		} return -1;
	}
	@Override public int search(long[] array, long key, Comparator.PLongComparator comparator, int start, int end) {
		end -= 1; while(end > start) {
			int cmpLeft = comparator._compare(array[start], key);
			if(cmpLeft == 0) return start;
			int cmpRight = comparator._compare(array[end], key);
			if(cmpRight == 0) return end;
			int leftThird = start + (end - start) / 3 + 1;
			int rightThird = end - (end - start) / 3 - 1;
			if(comparator.compare(array[leftThird], key) <= 0)
				start = leftThird; else end = rightThird;
		} return -1;
	}
	@Override public int search(short[] array, short key, Comparator.PShortComparator comparator, int start, int end) {
		end -= 1; while(end > start) {
			int cmpLeft = comparator._compare(array[start], key);
			if(cmpLeft == 0) return start;
			int cmpRight = comparator._compare(array[end], key);
			if(cmpRight == 0) return end;
			int leftThird = start + (end - start) / 3 + 1;
			int rightThird = end - (end - start) / 3 - 1;
			if(comparator.compare(array[leftThird], key) <= 0)
				start = leftThird; else end = rightThird;
		} return -1;
	}
	@Override public int search(float[] array, float key, Comparator.PFloatComparator comparator, int start, int end) {
		end -= 1; while(end > start) {
			int cmpLeft = comparator._compare(array[start], key);
			if(cmpLeft == 0) return start;
			int cmpRight = comparator._compare(array[end], key);
			if(cmpRight == 0) return end;
			int leftThird = start + (end - start) / 3 + 1;
			int rightThird = end - (end - start) / 3 - 1;
			if(comparator.compare(array[leftThird], key) <= 0)
				start = leftThird; else end = rightThird;
		} return -1;
	}
	@Override public int search(double[] array, double key, Comparator.PDoubleComparator comparator, int start, int end) {
		end -= 1; while(end > start) {
			int cmpLeft = comparator._compare(array[start], key);
			if(cmpLeft == 0) return start;
			int cmpRight = comparator._compare(array[end], key);
			if(cmpRight == 0) return end;
			int leftThird = start + (end - start) / 3 + 1;
			int rightThird = end - (end - start) / 3 - 1;
			if(comparator.compare(array[leftThird], key) <= 0)
				start = leftThird; else end = rightThird;
		} return -1;
	}
	@Override public int search(char[] array, char key, Comparator.PCharacterComparator comparator, int start, int end) {
		end -= 1; while(end > start) {
			int cmpLeft = comparator._compare(array[start], key);
			if(cmpLeft == 0) return start;
			int cmpRight = comparator._compare(array[end], key);
			if(cmpRight == 0) return end;
			int leftThird = start + (end - start) / 3 + 1;
			int rightThird = end - (end - start) / 3 - 1;
			if(comparator.compare(array[leftThird], key) <= 0)
				start = leftThird; else end = rightThird;
		} return -1;
	}
}
