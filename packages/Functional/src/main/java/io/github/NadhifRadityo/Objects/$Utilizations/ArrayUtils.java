package io.github.NadhifRadityo.Objects.$Utilizations;

import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;

@SuppressWarnings("SuspiciousSystemArraycopy")
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

	static {
		new Exception().printStackTrace();
	}

	private static final ThreadLocal<WeakReference<Object[]>> tempObjectArray = new ThreadLocal<>();
	private static final ThreadLocal<WeakReference<int[]>> tempIntegerArray = new ThreadLocal<>();
	private static final ThreadLocal<WeakReference<long[]>> tempLongArray = new ThreadLocal<>();
	private static final ThreadLocal<WeakReference<short[]>> tempShortArray = new ThreadLocal<>();
	private static final ThreadLocal<WeakReference<float[]>> tempFloatArray = new ThreadLocal<>();
	private static final ThreadLocal<WeakReference<double[]>> tempDoubleArray = new ThreadLocal<>();
	private static final ThreadLocal<WeakReference<char[]>> tempCharacterArray = new ThreadLocal<>();
	private static final ThreadLocal<WeakReference<byte[]>> tempByteArray = new ThreadLocal<>();
	private static final ThreadLocal<WeakReference<boolean[]>> tempBooleanArray = new ThreadLocal<>();
	private static WeakReference<Object[]> emptyObjectArray;
	private static WeakReference<int[]> emptyIntegerArray;
	private static WeakReference<long[]> emptyLongArray;
	private static WeakReference<short[]> emptyShortArray;
	private static WeakReference<float[]> emptyFloatArray;
	private static WeakReference<double[]> emptyDoubleArray;
	private static WeakReference<char[]> emptyCharacterArray;
	private static WeakReference<byte[]> emptyByteArray;
	private static WeakReference<boolean[]> emptyBooleanArray;

	private static int randSize() { return NumberUtils.rand(107, 107 * 2); }
	public static Object[] getTempObjectArray(int length) { if(length < 0) length = randSize(); WeakReference<Object[]> reference = tempObjectArray.get(); Object[] result = reference == null ? null : reference.get(); if(result == null || result.length < length) { result = new Object[length]; tempObjectArray.set(new WeakReference<>(result)); } return result; }
	public static int[] getTempIntegerArray(int length) { if(length < 0) length = randSize(); WeakReference<int[]> reference = tempIntegerArray.get(); int[] result = reference == null ? null : reference.get(); if(result == null || result.length < length) { result = new int[length]; tempIntegerArray.set(new WeakReference<>(result)); } return result; }
	public static long[] getTempLongArray(int length) { if(length < 0) length = randSize(); WeakReference<long[]> reference = tempLongArray.get(); long[] result = reference == null ? null : reference.get(); if(result == null || result.length < length) { result = new long[length]; tempLongArray.set(new WeakReference<>(result)); } return result; }
	public static short[] getTempShortArray(int length) { if(length < 0) length = randSize(); WeakReference<short[]> reference = tempShortArray.get(); short[] result = reference == null ? null : reference.get(); if(result == null || result.length < length) { result = new short[length]; tempShortArray.set(new WeakReference<>(result)); } return result; }
	public static float[] getTempFloatArray(int length) { if(length < 0) length = randSize(); WeakReference<float[]> reference = tempFloatArray.get(); float[] result = reference == null ? null : reference.get(); if(result == null || result.length < length) { result = new float[length]; tempFloatArray.set(new WeakReference<>(result)); } return result; }
	public static double[] getTempDoubleArray(int length) { if(length < 0) length = randSize(); WeakReference<double[]> reference = tempDoubleArray.get(); double[] result = reference == null ? null : reference.get(); if(result == null || result.length < length) { result = new double[length]; tempDoubleArray.set(new WeakReference<>(result)); } return result; }
	public static char[] getTempCharacterArray(int length) { if(length < 0) length = randSize(); WeakReference<char[]> reference = tempCharacterArray.get(); char[] result = reference == null ? null : reference.get(); if(result == null || result.length < length) { result = new char[length]; tempCharacterArray.set(new WeakReference<>(result)); } return result; }
	public static byte[] getTempByteArray(int length) { if(length < 0) length = randSize(); WeakReference<byte[]> reference = tempByteArray.get(); byte[] result = reference == null ? null : reference.get(); if(result == null || result.length < length) { result = new byte[length]; tempByteArray.set(new WeakReference<>(result)); } return result; }
	public static boolean[] getTempBooleanArray(int length) { if(length < 0) length = randSize(); WeakReference<boolean[]> reference = tempBooleanArray.get(); boolean[] result = reference == null ? null : reference.get(); if(result == null || result.length < length) { result = new boolean[length]; tempBooleanArray.set(new WeakReference<>(result)); } return result; }
	public static Object getTempArray(int length, Class<?> clazz) {
		if(length < 0) length = randSize();
		if(clazz == int.class) return getTempIntegerArray(length);
		if(clazz == long.class) return getTempLongArray(length);
		if(clazz == short.class) return getTempShortArray(length);
		if(clazz == float.class) return getTempFloatArray(length);
		if(clazz == double.class) return getTempDoubleArray(length);
		if(clazz == char.class) return getTempCharacterArray(length);
		if(clazz == byte.class) return getTempByteArray(length);
		if(clazz == boolean.class) return getTempBooleanArray(length);
		return getTempObjectArray(length);
	}
	public static Object[] getEmptyObjectArray(int length) { Object[] result = getTempObjectArray(length); if(length < 0) length = result.length; empty(result, 0, length); return result; }
	public static int[] getEmptyIntegerArray(int length) { int[] result = getTempIntegerArray(length); if(length < 0) length = result.length; empty(result, 0, length); return result; }
	public static long[] getEmptyLongArray(int length) { long[] result = getTempLongArray(length); if(length < 0) length = result.length; empty(result, 0, length); return result; }
	public static short[] getEmptyShortArray(int length) { short[] result = getTempShortArray(length); if(length < 0) length = result.length; empty(result, 0, length); return result; }
	public static float[] getEmptyFloatArray(int length) { float[] result = getTempFloatArray(length); if(length < 0) length = result.length; empty(result, 0, length); return result; }
	public static double[] getEmptyDoubleArray(int length) { double[] result = getTempDoubleArray(length); if(length < 0) length = result.length; empty(result, 0, length); return result; }
	public static char[] getEmptyCharacterArray(int length) { char[] result = getTempCharacterArray(length); if(length < 0) length = result.length; empty(result, 0, length); return result; }
	public static byte[] getEmptyByteArray(int length) { byte[] result = getTempByteArray(length); if(length < 0) length = result.length; empty(result, 0, length); return result; }
	public static boolean[] getEmptyBooleanArray(int length) { boolean[] result = getTempBooleanArray(length); if(length < 0) length = result.length; empty(result, 0, length); return result; }
	public static Object getEmptyArray(int length, Class<?> clazz) {
		if(clazz == int.class) return getEmptyIntegerArray(length);
		if(clazz == long.class) return getEmptyLongArray(length);
		if(clazz == short.class) return getEmptyShortArray(length);
		if(clazz == float.class) return getEmptyFloatArray(length);
		if(clazz == double.class) return getEmptyDoubleArray(length);
		if(clazz == char.class) return getEmptyCharacterArray(length);
		if(clazz == byte.class) return getEmptyByteArray(length);
		if(clazz == boolean.class) return getEmptyBooleanArray(length);
		return getEmptyObjectArray(length);
	}

	protected static Object[] getEmptyObjectArray() { Object[] result = emptyObjectArray == null ? null : emptyObjectArray.get(); if(result == null) { result = new Object[randSize()]; emptyObjectArray = new WeakReference<>(result); } return result; }
	protected static int[] getEmptyIntegerArray() { int[] result = emptyIntegerArray == null ? null : emptyIntegerArray.get(); if(result == null) { result = new int[randSize()]; emptyIntegerArray = new WeakReference<>(result); } return result; }
	protected static long[] getEmptyLongArray() { long[] result = emptyLongArray == null ? null : emptyLongArray.get(); if(result == null) { result = new long[randSize()]; emptyLongArray = new WeakReference<>(result); } return result; }
	protected static short[] getEmptyShortArray() { short[] result = emptyShortArray == null ? null : emptyShortArray.get(); if(result == null) { result = new short[randSize()]; emptyShortArray = new WeakReference<>(result); } return result; }
	protected static float[] getEmptyFloatArray() { float[] result = emptyFloatArray == null ? null : emptyFloatArray.get(); if(result == null) { result = new float[randSize()]; emptyFloatArray = new WeakReference<>(result); } return result; }
	protected static double[] getEmptyDoubleArray() { double[] result = emptyDoubleArray == null ? null : emptyDoubleArray.get(); if(result == null) { result = new double[randSize()]; emptyDoubleArray = new WeakReference<>(result); } return result; }
	protected static char[] getEmptyCharacterArray() { char[] result = emptyCharacterArray == null ? null : emptyCharacterArray.get(); if(result == null) { result = new char[randSize()]; emptyCharacterArray = new WeakReference<>(result); } return result; }
	protected static byte[] getEmptyByteArray() { byte[] result = emptyByteArray == null ? null : emptyByteArray.get(); if(result == null) { result = new byte[randSize()]; emptyByteArray = new WeakReference<>(result); } return result; }
	protected static boolean[] getEmptyBooleanArray() { boolean[] result = emptyBooleanArray == null ? null : emptyBooleanArray.get(); if(result == null) { result = new boolean[randSize()]; emptyBooleanArray = new WeakReference<>(result); } return result; }
	protected static Object getEmptyArray(Class<?> clazz) {
		if(clazz == int.class) return getEmptyIntegerArray();
		if(clazz == long.class) return getEmptyLongArray();
		if(clazz == short.class) return getEmptyShortArray();
		if(clazz == float.class) return getEmptyFloatArray();
		if(clazz == double.class) return getEmptyDoubleArray();
		if(clazz == char.class) return getEmptyCharacterArray();
		if(clazz == byte.class) return getEmptyByteArray();
		if(clazz == boolean.class) return getEmptyBooleanArray();
		return getEmptyObjectArray();
	}

	public static boolean checkIndex(long off, long totalLen, long len) { return !(off < 0) && !(off + len > totalLen); }
	public static void assertIndex(long off, long totalLen, long len) { if(!checkIndex(off, totalLen, len)) throw new IndexOutOfBoundsException(); }
	public static boolean checkCopyIndex(long srcOff, long srcLen, long destOff, long destLen, long len) { return checkIndex(srcOff, srcLen, len) && checkIndex(destOff, destLen, len); }
	public static void assertCopyIndex(long srcOff, long srcLen, long destOff, long destLen, long len) { if(!checkCopyIndex(srcOff, srcLen, destOff, destLen, len)) throw new IndexOutOfBoundsException(); }

	public static boolean checkArray(Object array) { return array.getClass().isArray(); }
	public static boolean checkArray(Object array, Object array1) { return array.getClass().isArray() && array1.getClass().isArray() && array.getClass().getComponentType().isAssignableFrom(array1.getClass().getComponentType()); }
	public static boolean checkArray(Object array, Object array1, Object array2) { return array.getClass().isArray() && array1.getClass().isArray() && array2.getClass().isArray() && array.getClass().getComponentType().isAssignableFrom(array1.getClass().getComponentType()) && array.getClass().getComponentType().isAssignableFrom(array2.getClass().getComponentType()); }
	public static void assertArray(Object array) { if(!checkArray(array)) throw new IllegalArgumentException(); }
	public static void assertArray(Object array, Object array1) { if(!checkArray(array, array1)) throw new IllegalArgumentException(); }
	public static void assertArray(Object array, Object array1, Object array2) { if(!checkArray(array, array1, array2)) throw new IllegalArgumentException(); }

	public static void fillArray(Object array, int offArr, int lenArr, Object filler, int offFill, int lenFill) {
		assertArray(array, filler);
		assertIndex(offArr, Array.getLength(array), lenArr);
		assertIndex(offFill, Array.getLength(filler), lenFill);
		int loops = lenArr / lenFill + (lenArr % lenFill > 0 ? 1 : 0);
		for(int i = 0; i < loops; i++) {
			System.arraycopy(filler, offFill, array, offArr, Math.min(lenArr, lenFill));
			offArr += lenFill; lenArr -= lenFill;
		}
	}
	public static void fillArray(Object array, int offArr, int lenArr, Object filler, int offFill) { assertArray(array, filler); fillArray(array, offArr, lenArr, filler, offFill, Array.getLength(filler) - offFill); }
	public static void fillArray(Object array, int offArr, int lenArr, Object filler) { fillArray(array, offArr, lenArr, filler, 0); }
	public static void fillArray(Object array, int offArr, Object filler, int offFill, int lenFill) { assertArray(array, filler); fillArray(array, offArr, Array.getLength(array) - offArr, filler, offFill, lenFill); }
	public static void fillArray(Object array, int offArr, Object filler, int offFill) { assertArray(array, filler); fillArray(array, offArr, filler, offFill, Array.getLength(filler) - offFill); }
	public static void fillArray(Object array, int offArr, Object filler) { fillArray(array, offArr, filler, 0); }
	public static void fillArray(Object array, Object filler) { fillArray(array, 0, filler); }

	public static void fill(Object array, int offArr, int lenArr, Object filler, int offFill, int lenFill) { fillArray(array, offArr, lenArr, filler, offFill, lenFill); }
	public static void fill(Object[] array, int offArr, int lenArr, Object[] filler, int offFill, int lenFill) { fillArray(array, offArr, lenArr, filler, offFill, lenFill); }
	public static void fill(byte[] array, int offArr, int lenArr, byte[] filler, int offFill, int lenFill) { fillArray(array, offArr, lenArr, filler, offFill, lenFill); }
	public static void fill(boolean[] array, int offArr, int lenArr, boolean[] filler, int offFill, int lenFill) { fillArray(array, offArr, lenArr, filler, offFill, lenFill); }
	public static void fill(int[] array, int offArr, int lenArr, int[] filler, int offFill, int lenFill) { fillArray(array, offArr, lenArr, filler, offFill, lenFill); }
	public static void fill(float[] array, int offArr, int lenArr, float[] filler, int offFill, int lenFill) { fillArray(array, offArr, lenArr, filler, offFill, lenFill); }
	public static void fill(double[] array, int offArr, int lenArr, double[] filler, int offFill, int lenFill) { fillArray(array, offArr, lenArr, filler, offFill, lenFill); }
	public static void fill(long[] array, int offArr, int lenArr, long[] filler, int offFill, int lenFill) { fillArray(array, offArr, lenArr, filler, offFill, lenFill); }
	public static void fill(short[] array, int offArr, int lenArr, short[] filler, int offFill, int lenFill) { fillArray(array, offArr, lenArr, filler, offFill, lenFill); }
	public static void fill(char[] array, int offArr, int lenArr, char[] filler, int offFill, int lenFill) { fillArray(array, offArr, lenArr, filler, offFill, lenFill); }
	public static void fill(Object array, int offArr, int lenArr, Object filler, int offFill) { assertArray(array, filler); fill(array, offArr, lenArr, filler, offFill, Array.getLength(filler) - offFill); }
	public static void fill(Object[] array, int offArr, int lenArr, Object[] filler, int offFill) { fill(array, offArr, lenArr, filler, offFill, filler.length - offFill); }
	public static void fill(byte[] array, int offArr, int lenArr, byte[] filler, int offFill) { fill(array, offArr, lenArr, filler, offFill, filler.length - offFill); }
	public static void fill(boolean[] array, int offArr, int lenArr, boolean[] filler, int offFill) { fill(array, offArr, lenArr, filler, offFill, filler.length - offFill); }
	public static void fill(int[] array, int offArr, int lenArr, int[] filler, int offFill) { fill(array, offArr, lenArr, filler, offFill, filler.length - offFill); }
	public static void fill(float[] array, int offArr, int lenArr, float[] filler, int offFill) { fill(array, offArr, lenArr, filler, offFill, filler.length - offFill); }
	public static void fill(double[] array, int offArr, int lenArr, double[] filler, int offFill) { fill(array, offArr, lenArr, filler, offFill, filler.length - offFill); }
	public static void fill(long[] array, int offArr, int lenArr, long[] filler, int offFill) { fill(array, offArr, lenArr, filler, offFill, filler.length - offFill); }
	public static void fill(short[] array, int offArr, int lenArr, short[] filler, int offFill) { fill(array, offArr, lenArr, filler, offFill, filler.length - offFill); }
	public static void fill(char[] array, int offArr, int lenArr, char[] filler, int offFill) { fill(array, offArr, lenArr, filler, offFill, filler.length - offFill); }
	public static void fill(Object array, int offArr, int lenArr, Object filler) { fill(array, offArr, lenArr, filler, 0); }
	public static void fill(Object[] array, int offArr, int lenArr, Object[] filler) { fill(array, offArr, lenArr, filler, 0); }
	public static void fill(byte[] array, int offArr, int lenArr, byte[] filler) { fill(array, offArr, lenArr, filler, 0); }
	public static void fill(boolean[] array, int offArr, int lenArr, boolean[] filler) { fill(array, offArr, lenArr, filler, 0); }
	public static void fill(int[] array, int offArr, int lenArr, int[] filler) { fill(array, offArr, lenArr, filler, 0); }
	public static void fill(float[] array, int offArr, int lenArr, float[] filler) { fill(array, offArr, lenArr, filler, 0); }
	public static void fill(double[] array, int offArr, int lenArr, double[] filler) { fill(array, offArr, lenArr, filler, 0); }
	public static void fill(long[] array, int offArr, int lenArr, long[] filler) { fill(array, offArr, lenArr, filler, 0); }
	public static void fill(short[] array, int offArr, int lenArr, short[] filler) { fill(array, offArr, lenArr, filler, 0); }
	public static void fill(char[] array, int offArr, int lenArr, char[] filler) { fill(array, offArr, lenArr, filler, 0); }
	public static void fill(Object array, int offArr, Object filler, int offFill, int lenFill) { assertArray(array, filler); fillArray(array, offArr, Array.getLength(array) - offArr, filler, offFill, lenFill); }
	public static void fill(Object[] array, int offArr, Object[] filler, int offFill, int lenFill) { fillArray(array, offArr, array.length - offArr, filler, offFill, lenFill); }
	public static void fill(byte[] array, int offArr, byte[] filler, int offFill, int lenFill) { fillArray(array, offArr, array.length - offArr, filler, offFill, lenFill); }
	public static void fill(boolean[] array, int offArr, boolean[] filler, int offFill, int lenFill) { fillArray(array, offArr, array.length - offArr, filler, offFill, lenFill); }
	public static void fill(int[] array, int offArr, int[] filler, int offFill, int lenFill) { fillArray(array, offArr, array.length - offArr, filler, offFill, lenFill); }
	public static void fill(float[] array, int offArr, float[] filler, int offFill, int lenFill) { fillArray(array, offArr, array.length - offArr, filler, offFill, lenFill); }
	public static void fill(double[] array, int offArr, double[] filler, int offFill, int lenFill) { fillArray(array, offArr, array.length - offArr, filler, offFill, lenFill); }
	public static void fill(long[] array, int offArr, long[] filler, int offFill, int lenFill) { fillArray(array, offArr, array.length - offArr, filler, offFill, lenFill); }
	public static void fill(short[] array, int offArr, short[] filler, int offFill, int lenFill) { fillArray(array, offArr, array.length - offArr, filler, offFill, lenFill); }
	public static void fill(char[] array, int offArr, char[] filler, int offFill, int lenFill) { fillArray(array, offArr, array.length - offArr, filler, offFill, lenFill); }
	public static void fill(Object array, int offArr, Object filler, int offFill) { assertArray(array, filler); fill(array, offArr, filler, offFill, Array.getLength(filler) - offFill); }
	public static void fill(Object[] array, int offArr, Object[] filler, int offFill) { fill(array, offArr, filler, offFill, filler.length - offFill); }
	public static void fill(byte[] array, int offArr, byte[] filler, int offFill) { fill(array, offArr, filler, offFill, filler.length - offFill); }
	public static void fill(boolean[] array, int offArr, boolean[] filler, int offFill) { fill(array, offArr, filler, offFill, filler.length - offFill); }
	public static void fill(int[] array, int offArr, int[] filler, int offFill) { fill(array, offArr, filler, offFill, filler.length - offFill); }
	public static void fill(float[] array, int offArr, float[] filler, int offFill) { fill(array, offArr, filler, offFill, filler.length - offFill); }
	public static void fill(double[] array, int offArr, double[] filler, int offFill) { fill(array, offArr, filler, offFill, filler.length - offFill); }
	public static void fill(long[] array, int offArr, long[] filler, int offFill) { fill(array, offArr, filler, offFill, filler.length - offFill); }
	public static void fill(short[] array, int offArr, short[] filler, int offFill) { fill(array, offArr, filler, offFill, filler.length - offFill); }
	public static void fill(char[] array, int offArr, char[] filler, int offFill) { fill(array, offArr, filler, offFill, filler.length - offFill); }
	public static void fill(Object array, int offArr, Object filler) { assertArray(array); fill(array, offArr, filler, 0); }
	public static void fill(Object[] array, int offArr, Object[] filler) { fill(array, offArr, filler, 0); }
	public static void fill(byte[] array, int offArr, byte[] filler) { fill(array, offArr, filler, 0); }
	public static void fill(boolean[] array, int offArr, boolean[] filler) { fill(array, offArr, filler, 0); }
	public static void fill(int[] array, int offArr, int[] filler) { fill(array, offArr, filler, 0); }
	public static void fill(float[] array, int offArr, float[] filler) { fill(array, offArr, filler, 0); }
	public static void fill(double[] array, int offArr, double[] filler) { fill(array, offArr, filler, 0); }
	public static void fill(long[] array, int offArr, long[] filler) { fill(array, offArr, filler, 0); }
	public static void fill(short[] array, int offArr, short[] filler) { fill(array, offArr, filler, 0); }
	public static void fill(char[] array, int offArr, char[] filler) { fill(array, offArr, filler, 0); }
	public static void fill(Object array, Object filler) { fill(array, 0, filler); }
	public static void fill(Object[] array, Object[] filler) { fill(array, 0, filler); }
	public static void fill(byte[] array, byte[] filler) { fill(array, 0, filler); }
	public static void fill(boolean[] array, boolean[] filler) { fill(array, 0, filler); }
	public static void fill(int[] array, int[] filler) { fill(array, 0, filler); }
	public static void fill(float[] array, float[] filler) { fill(array, 0, filler); }
	public static void fill(double[] array, double[] filler) { fill(array, 0, filler); }
	public static void fill(long[] array, long[] filler) { fill(array, 0, filler); }
	public static void fill(short[] array, short[] filler) { fill(array, 0, filler); }
	public static void fill(char[] array, char[] filler) { fill(array, 0, filler); }

	public static void empty(Object array, int off, int len) { fill(array, off, len, getEmptyArray(array.getClass().getComponentType())); }
	public static void empty(Object[] array, int off, int len) { fill(array, off, len, getEmptyObjectArray()); }
	public static void empty(byte[] array, int off, int len) { fill(array, off, len, getEmptyByteArray()); }
	public static void empty(boolean[] array, int off, int len) { fill(array, off, len, getEmptyBooleanArray()); }
	public static void empty(int[] array, int off, int len) { fill(array, off, len, getEmptyIntegerArray()); }
	public static void empty(float[] array, int off, int len) { fill(array, off, len, getEmptyFloatArray()); }
	public static void empty(double[] array, int off, int len) { fill(array, off, len, getEmptyDoubleArray()); }
	public static void empty(long[] array, int off, int len) { fill(array, off, len, getEmptyLongArray()); }
	public static void empty(short[] array, int off, int len) { fill(array, off, len, getEmptyShortArray()); }
	public static void empty(char[] array, int off, int len) { fill(array, off, len, getEmptyCharacterArray()); }
	public static void empty(Object array, int off) { assertArray(array); empty(array, off, Array.getLength(array)); }
	public static void empty(Object[] array, int off) { empty(array, off, array.length); }
	public static void empty(byte[] array, int off) { empty(array, off, array.length); }
	public static void empty(boolean[] array, int off) { empty(array, off, array.length); }
	public static void empty(int[] array, int off) { empty(array, off, array.length); }
	public static void empty(float[] array, int off) { empty(array, off, array.length); }
	public static void empty(double[] array, int off) { empty(array, off, array.length); }
	public static void empty(long[] array, int off) { empty(array, off, array.length); }
	public static void empty(short[] array, int off) { empty(array, off, array.length); }
	public static void empty(char[] array, int off) { empty(array, off, array.length); }
	public static void empty(Object array) { empty(array, 0); }
	public static void empty(Object[] array) { empty(array, 0); }
	public static void empty(byte[] array) { empty(array, 0); }
	public static void empty(boolean[] array) { empty(array, 0); }
	public static void empty(int[] array) { empty(array, 0); }
	public static void empty(float[] array) { empty(array, 0); }
	public static void empty(double[] array) { empty(array, 0); }
	public static void empty(long[] array) { empty(array, 0); }
	public static void empty(short[] array) { empty(array, 0); }
	public static void empty(char[] array) { empty(array, 0); }

	public static Object clone(Object array) {
		assertArray(array); int length = Array.getLength(array);
		Object result = Array.newInstance(array.getClass().getComponentType(), length);
		System.arraycopy(array, 0, result, 0, length); return result;
	}

	public static void copy(Object src, int offSrc, Object dst, int offDst, int len) {
		assertArray(src, dst);
		assertIndex(offSrc, Array.getLength(src), len); assertIndex(offDst, Array.getLength(dst), 1);
		System.arraycopy(src, offSrc, dst, offDst, len);
	}
	public static void cut(Object src, int offSrc, Object dst, int offDst, int len) {
		assertArray(src, dst);
		assertIndex(offSrc, Array.getLength(src), len); assertIndex(offDst, Array.getLength(dst), 1);
		Object temp = getTempArray(len, src.getClass().getComponentType());
		System.arraycopy(src, offSrc, temp, 0, len);
		empty(src, offSrc, len);
		System.arraycopy(temp, 0, dst, offDst, len);
	}

	public static String deepToString(Object objects) {
		if(objects == null) return "null";
		if(!objects.getClass().isArray()) throw new IllegalArgumentException();
		StringBuilder builder = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
		try { deepToString(objects, builder, 0); return builder.toString();
		} finally { Pool.returnObject(StringBuilder.class, builder); }
	}
	private static void addIndent(StringBuilder builder, int indent) { for(int i = 0; i < indent; i++) builder.append("\t"); }
	protected static void deepToString(Object objects, StringBuilder builder, int indent) {
		if(objects == null) { builder.append("null"); return; }
		if(!objects.getClass().isArray()) throw new IllegalArgumentException();
		if(Array.getLength(objects) == 0) { builder.append("[]"); return; }
		int length = Array.getLength(objects);

		addIndent(builder, indent); builder.append("[\n");
		for(int i = 0; i < length; i++) { Object object = Array.get(objects, i); try {
			if(object == null) { addIndent(builder, indent + 1); builder.append("null"); continue; }
			if(object.getClass().isArray()) { deepToString(object, builder, indent + 1); continue; }
			addIndent(builder, indent + 1); builder.append(object.toString());
		} finally { builder.append(i < length - 1 ? ", \n" : "\n"); } }
		addIndent(builder, indent); builder.append("]");
	}
}
