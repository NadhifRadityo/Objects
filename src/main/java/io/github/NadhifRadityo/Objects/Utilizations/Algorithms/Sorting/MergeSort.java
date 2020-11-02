package io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting;

import io.github.NadhifRadityo.Objects.Object.Comparator;

import java.lang.reflect.Array;

public abstract class MergeSort implements Sorting {
	public static final MergeSort DEFAULT = new MergeSort() { };

	@SuppressWarnings("unchecked")
	@Override public <T> void sort(T[] array, Comparator<? super T> comparator, int start, int end) {
		T[] sorted = _sort(array, comparator, start, end, (Class<? extends T>) array.getClass().getComponentType());
		System.arraycopy(sorted, 0, array, start, sorted.length);
	}
	@Override public void sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		int[] sorted = _sort(array, comparator, start, end);
		System.arraycopy(sorted, 0, array, start, sorted.length);
	}
	@Override public void sort(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		long[] sorted = _sort(array, comparator, start, end);
		System.arraycopy(sorted, 0, array, start, sorted.length);
	}
	@Override public void sort(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		short[] sorted = _sort(array, comparator, start, end);
		System.arraycopy(sorted, 0, array, start, sorted.length);
	}
	@Override public void sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		float[] sorted = _sort(array, comparator, start, end);
		System.arraycopy(sorted, 0, array, start, sorted.length);
	}
	@Override public void sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		double[] sorted = _sort(array, comparator, start, end);
		System.arraycopy(sorted, 0, array, start, sorted.length);
	}
	@Override public void sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		char[] sorted = _sort(array, comparator, start, end);
		System.arraycopy(sorted, 0, array, start, sorted.length);
	}

	@SuppressWarnings("unchecked")
	protected static <T> T[] _sort(T[] array, Comparator<? super T> comparator, int start, int end, Class<? extends T> componentType) {
		int n = (end - start);
		if(n <= 1) return array;
		// TODO LEAK
		T[] a = (T[]) Array.newInstance(componentType, n / 2);
		T[] b = (T[]) Array.newInstance(componentType, n - (n / 2));
		System.arraycopy(array, 0, a, 0, a.length);
		System.arraycopy(array, n / 2, b, 0, b.length);
		return merge(_sort(a, comparator, 0, a.length, componentType), _sort(b, comparator, 0, b.length, componentType), comparator, componentType);
	}
	protected static int[] _sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		int n = (end - start);
		if(n <= 1) return array;
		int[] a = new int[n / 2];
		int[] b = new int[n - (n / 2)];
		System.arraycopy(array, 0, a, 0, a.length);
		System.arraycopy(array, n / 2, b, 0, b.length);
		return merge(_sort(a, comparator, 0, a.length), _sort(b, comparator, 0, b.length), comparator);
	}
	protected static long[] _sort(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		int n = (end - start);
		if(n <= 1) return array;
		long[] a = new long[n / 2];
		long[] b = new long[n - (n / 2)];
		System.arraycopy(array, 0, a, 0, a.length);
		System.arraycopy(array, n / 2, b, 0, b.length);
		return merge(_sort(a, comparator, 0, a.length), _sort(b, comparator, 0, b.length), comparator);
	}
	protected static short[] _sort(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		int n = (end - start);
		if(n <= 1) return array;
		short[] a = new short[n / 2];
		short[] b = new short[n - (n / 2)];
		System.arraycopy(array, 0, a, 0, a.length);
		System.arraycopy(array, n / 2, b, 0, b.length);
		return merge(_sort(a, comparator, 0, a.length), _sort(b, comparator, 0, b.length), comparator);
	}
	protected static float[] _sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		int n = (end - start);
		if(n <= 1) return array;
		float[] a = new float[n / 2];
		float[] b = new float[n - (n / 2)];
		System.arraycopy(array, 0, a, 0, a.length);
		System.arraycopy(array, n / 2, b, 0, b.length);
		return merge(_sort(a, comparator, 0, a.length), _sort(b, comparator, 0, b.length), comparator);
	}
	protected static double[] _sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		int n = (end - start);
		if(n <= 1) return array;
		double[] a = new double[n / 2];
		double[] b = new double[n - (n / 2)];
		System.arraycopy(array, 0, a, 0, a.length);
		System.arraycopy(array, n / 2, b, 0, b.length);
		return merge(_sort(a, comparator, 0, a.length), _sort(b, comparator, 0, b.length), comparator);
	}
	protected static char[] _sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		int n = (end - start);
		if(n <= 1) return array;
		char[] a = new char[n / 2];
		char[] b = new char[n - (n / 2)];
		System.arraycopy(array, 0, a, 0, a.length);
		System.arraycopy(array, n / 2, b, 0, b.length);
		return merge(_sort(a, comparator, 0, a.length), _sort(b, comparator, 0, b.length), comparator);
	}

	@SuppressWarnings("unchecked")
	protected static <T> T[] merge(T[] a, T[] b, Comparator<? super T> comparator, Class<? extends T> componentType) {
		T[] c = (T[]) Array.newInstance(componentType, a.length + b.length);
		int i = 0, j = 0;
		for(int k = 0; k < c.length; k++) {
			if(i >= a.length) c[k] = b[j++];
			else if(j >= b.length) c[k] = a[i++];
			else if(comparator.compare(a[i], b[j]) <= 0) c[k] = a[i++];
			else c[k] = b[j++];
		} return c;
	}
	protected static int[] merge(int[] a, int[] b, Comparator.PIntegerComparator comparator) {
		int[] c = new int[a.length + b.length];
		int i = 0, j = 0;
		for(int k = 0; k < c.length; k++) {
			if(i >= a.length) c[k] = b[j++];
			else if(j >= b.length) c[k] = a[i++];
			else if(comparator._compare(a[i], b[j]) <= 0) c[k] = a[i++];
			else c[k] = b[j++];
		} return c;
	}
	protected static long[] merge(long[] a, long[] b, Comparator.PLongComparator comparator) {
		long[] c = new long[a.length + b.length];
		int i = 0, j = 0;
		for(int k = 0; k < c.length; k++) {
			if(i >= a.length) c[k] = b[j++];
			else if(j >= b.length) c[k] = a[i++];
			else if(comparator._compare(a[i], b[j]) <= 0) c[k] = a[i++];
			else c[k] = b[j++];
		} return c;
	}
	protected static short[] merge(short[] a, short[] b, Comparator.PShortComparator comparator) {
		short[] c = new short[a.length + b.length];
		int i = 0, j = 0;
		for(int k = 0; k < c.length; k++) {
			if(i >= a.length) c[k] = b[j++];
			else if(j >= b.length) c[k] = a[i++];
			else if(comparator._compare(a[i], b[j]) <= 0) c[k] = a[i++];
			else c[k] = b[j++];
		} return c;
	}
	protected static float[] merge(float[] a, float[] b, Comparator.PFloatComparator comparator) {
		float[] c = new float[a.length + b.length];
		int i = 0, j = 0;
		for(int k = 0; k < c.length; k++) {
			if(i >= a.length) c[k] = b[j++];
			else if(j >= b.length) c[k] = a[i++];
			else if(comparator._compare(a[i], b[j]) <= 0) c[k] = a[i++];
			else c[k] = b[j++];
		} return c;
	}
	protected static double[] merge(double[] a, double[] b, Comparator.PDoubleComparator comparator) {
		double[] c = new double[a.length + b.length];
		int i = 0, j = 0;
		for(int k = 0; k < c.length; k++) {
			if(i >= a.length) c[k] = b[j++];
			else if(j >= b.length) c[k] = a[i++];
			else if(comparator._compare(a[i], b[j]) <= 0) c[k] = a[i++];
			else c[k] = b[j++];
		} return c;
	}
	protected static char[] merge(char[] a, char[] b, Comparator.PCharacterComparator comparator) {
		char[] c = new char[a.length + b.length];
		int i = 0, j = 0;
		for(int k = 0; k < c.length; k++) {
			if(i >= a.length) c[k] = b[j++];
			else if(j >= b.length) c[k] = a[i++];
			else if(comparator._compare(a[i], b[j]) <= 0) c[k] = a[i++];
			else c[k] = b[j++];
		} return c;
	}
}
