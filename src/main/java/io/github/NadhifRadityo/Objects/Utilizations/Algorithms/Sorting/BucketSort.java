package io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting;

import io.github.NadhifRadityo.Objects.Object.Comparator;

import java.lang.reflect.Array;

public abstract class BucketSort implements Sorting {
	public static final BucketSort DEFAULT = new BucketSort() { };

	@SuppressWarnings({"unchecked"})
	@Override public <T> void sort(T[] array, Comparator<? super T> comparator, int start, int end) {
		Class<? extends T> componentType = (Class<? extends T>) array.getClass().getComponentType();
		boolean checkType = !Number.class.isAssignableFrom(array.getClass().getComponentType());
		int n = end - start;
		T[][] buckets = (T[][]) Array.newInstance(componentType, n, n);
		int[] lastBucketIndex = new int[n];
		T pivot = componentType.cast(Double.class.isAssignableFrom(componentType) ? -1d : -1);
		for(int i = start; i < end; i++) {
			if(checkType && !Number.class.isAssignableFrom(array[i].getClass()))
				throw new IllegalArgumentException("Only works with number");
			double value = ((Number) array[i]).doubleValue();
			if(value < 0 || value > 1)
				throw new IllegalArgumentException("Cannot sort with value less than 0 and/or value more than 1");
			int bucketIndex = (int) (n * value);
			if(value < -1 && comparator.compare(array[i], pivot) > 0)
				bucketIndex = n - bucketIndex - 1;
			if(value > -1 && comparator.compare(array[i], pivot) < 0)
				bucketIndex = n - bucketIndex - 1;
			buckets[bucketIndex][lastBucketIndex[bucketIndex]++] = array[i];
		} for(int i = 0; i < buckets.length; i++)
			IntroSort.DEFAULT.sort(buckets[i], comparator, 0, lastBucketIndex[i]);
		int index = 0;
		for(int i = 0; i < buckets.length; i++) {
			System.arraycopy(buckets[i], 0, array, start + index, lastBucketIndex[i]);
			index += lastBucketIndex[i];
		}
	}
	@Override public void sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		int n = end - start;
		int[][] buckets = new int[n][n];
		int[] lastBucketIndex = new int[n];
		final int pivot = -1;
		for(int i = start; i < end; i++) {
			int value = array[i];
			if(value < 0 || value > 1)
				throw new IllegalArgumentException("Cannot sort with value less than 0 and/or value more than 1");
			int bucketIndex = n * value;
			if(comparator._compare(array[i], pivot) < 0)
				bucketIndex = n - bucketIndex - 1;
			buckets[bucketIndex][lastBucketIndex[bucketIndex]++] = array[i];
		} for(int i = 0; i < buckets.length; i++)
			IntroSort.DEFAULT.sort(buckets[i], comparator, 0, lastBucketIndex[i]);
		int index = 0;
		for(int i = 0; i < buckets.length; i++) {
			System.arraycopy(buckets[i], 0, array, start + index, lastBucketIndex[i]);
			index += lastBucketIndex[i];
		}
	}
	@Override public void sort(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		int n = end - start;
		long[][] buckets = new long[n][n];
		int[] lastBucketIndex = new int[n];
		final long pivot = -1;
		for(int i = start; i < end; i++) {
			long value = array[i];
			if(value < 0 || value > 1)
				throw new IllegalArgumentException("Cannot sort with value less than 0 and/or value more than 1");
			int bucketIndex = (int) (n * value);
			if(comparator._compare(array[i], pivot) < 0)
				bucketIndex = n - bucketIndex - 1;
			buckets[bucketIndex][lastBucketIndex[bucketIndex]++] = array[i];
		} for(int i = 0; i < buckets.length; i++)
			IntroSort.DEFAULT.sort(buckets[i], comparator, 0, lastBucketIndex[i]);
		int index = 0;
		for(int i = 0; i < buckets.length; i++) {
			System.arraycopy(buckets[i], 0, array, start + index, lastBucketIndex[i]);
			index += lastBucketIndex[i];
		}
	}
	@Override public void sort(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		int n = end - start;
		short[][] buckets = new short[n][n];
		int[] lastBucketIndex = new int[n];
		final short pivot = -1;
		for(int i = start; i < end; i++) {
			short value = array[i];
			if(value < 0 || value > 1)
				throw new IllegalArgumentException("Cannot sort with value less than 0 and/or value more than 1");
			int bucketIndex = n * value;
			if(comparator._compare(array[i], pivot) < 0)
				bucketIndex = n - bucketIndex - 1;
			buckets[bucketIndex][lastBucketIndex[bucketIndex]++] = array[i];
		} for(int i = 0; i < buckets.length; i++)
			IntroSort.DEFAULT.sort(buckets[i], comparator, 0, lastBucketIndex[i]);
		int index = 0;
		for(int i = 0; i < buckets.length; i++) {
			System.arraycopy(buckets[i], 0, array, start + index, lastBucketIndex[i]);
			index += lastBucketIndex[i];
		}
	}
	@Override public void sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		int n = end - start;
		float[][] buckets = new float[n][n];
		int[] lastBucketIndex = new int[n];
		final float pivot = -1;
		for(int i = start; i < end; i++) {
			float value = array[i];
			if(value < 0 || value > 1)
				throw new IllegalArgumentException("Cannot sort with value less than 0 and/or value more than 1");
			int bucketIndex = (int) (n * value);
			if(comparator._compare(array[i], pivot) < 0)
				bucketIndex = n - bucketIndex - 1;
			buckets[bucketIndex][lastBucketIndex[bucketIndex]++] = array[i];
		} for(int i = 0; i < buckets.length; i++)
			IntroSort.DEFAULT.sort(buckets[i], comparator, 0, lastBucketIndex[i]);
		int index = 0;
		for(int i = 0; i < buckets.length; i++) {
			System.arraycopy(buckets[i], 0, array, start + index, lastBucketIndex[i]);
			index += lastBucketIndex[i];
		}
	}
	@Override public void sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		int n = end - start;
		double[][] buckets = new double[n][n];
		int[] lastBucketIndex = new int[n];
		final double pivot = -1;
		for(int i = start; i < end; i++) {
			double value = array[i];
			if(value < 0 || value > 1)
				throw new IllegalArgumentException("Cannot sort with value less than 0 and/or value more than 1");
			int bucketIndex = (int) (n * value);
			if(comparator._compare(array[i], pivot) < 0)
				bucketIndex = n - bucketIndex - 1;
			buckets[bucketIndex][lastBucketIndex[bucketIndex]++] = array[i];
		} for(int i = 0; i < buckets.length; i++)
			IntroSort.DEFAULT.sort(buckets[i], comparator, 0, lastBucketIndex[i]);
		int index = 0;
		for(int i = 0; i < buckets.length; i++) {
			System.arraycopy(buckets[i], 0, array, start + index, lastBucketIndex[i]);
			index += lastBucketIndex[i];
		}
	}
	@Override public void sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		int n = end - start;
		char[][] buckets = new char[n][n];
		int[] lastBucketIndex = new int[n];
		final char pivot = 0;
		for(int i = start; i < end; i++) {
			char value = array[i];
			if(value > 1)
				throw new IllegalArgumentException("Cannot sort with value less than 0 and/or value more than 1");
			int bucketIndex = n * value;
			if(comparator._compare(array[i], pivot) < 0)
				bucketIndex = n - bucketIndex - 1;
			buckets[bucketIndex][lastBucketIndex[bucketIndex]++] = array[i];
		} for(int i = 0; i < buckets.length; i++)
			IntroSort.DEFAULT.sort(buckets[i], comparator, 0, lastBucketIndex[i]);
		int index = 0;
		for(int i = 0; i < buckets.length; i++) {
			System.arraycopy(buckets[i], 0, array, start + index, lastBucketIndex[i]);
			index += lastBucketIndex[i];
		}
	}
}
