package io.github.NadhifRadityo.Objects.$Utilizations.Algorithms.Sorting;

import io.github.NadhifRadityo.Objects.$Interface.Functional.Comparator;

public abstract class SelectionSort implements Sorting {
	public static final SelectionSort DEFAULT = new SelectionSort() { };

	@Override public <T> void sort(T[] array, Comparator<? super T> comparator, int start, int end) {
		T temp; int smallest;
		for(int i = start; i < end; i++) {
			smallest = i;
			for(int j = i + 1; j < end; j++)
				if(comparator.compare(array[j], array[smallest]) < 0)
					smallest = j;
			if(smallest == i) continue;
			temp = array[i];
			array[i] = array[smallest];
			array[smallest] = temp;
		}
	}
	@Override public void sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		int temp; int smallest;
		for(int i = start; i < end; i++) {
			smallest = i;
			for(int j = i + 1; j < end; j++)
				if(comparator._compare(array[j], array[smallest]) < 0)
					smallest = j;
			if(smallest == i) continue;
			temp = array[i];
			array[i] = array[smallest];
			array[smallest] = temp;
		}
	}
	@Override public void sort(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		long temp; int smallest;
		for(int i = start; i < end; i++) {
			smallest = i;
			for(int j = i + 1; j < end; j++)
				if(comparator._compare(array[j], array[smallest]) < 0)
					smallest = j;
			if(smallest == i) continue;
			temp = array[i];
			array[i] = array[smallest];
			array[smallest] = temp;
		}
	}
	@Override public void sort(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		short temp; int smallest;
		for(int i = start; i < end; i++) {
			smallest = i;
			for(int j = i + 1; j < end; j++)
				if(comparator._compare(array[j], array[smallest]) < 0)
					smallest = j;
			if(smallest == i) continue;
			temp = array[i];
			array[i] = array[smallest];
			array[smallest] = temp;
		}
	}
	@Override public void sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		float temp; int smallest;
		for(int i = start; i < end; i++) {
			smallest = i;
			for(int j = i + 1; j < end; j++)
				if(comparator._compare(array[j], array[smallest]) < 0)
					smallest = j;
			if(smallest == i) continue;
			temp = array[i];
			array[i] = array[smallest];
			array[smallest] = temp;
		}
	}
	@Override public void sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		double temp; int smallest;
		for(int i = start; i < end; i++) {
			smallest = i;
			for(int j = i + 1; j < end; j++)
				if(comparator._compare(array[j], array[smallest]) < 0)
					smallest = j;
			if(smallest == i) continue;
			temp = array[i];
			array[i] = array[smallest];
			array[smallest] = temp;
		}
	}
	@Override public void sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		char temp; int smallest;
		for(int i = start; i < end; i++) {
			smallest = i;
			for(int j = i + 1; j < end; j++)
				if(comparator._compare(array[j], array[smallest]) < 0)
					smallest = j;
			if(smallest == i) continue;
			temp = array[i];
			array[i] = array[smallest];
			array[smallest] = temp;
		}
	}
}
