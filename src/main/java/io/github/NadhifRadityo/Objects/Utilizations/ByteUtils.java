package io.github.NadhifRadityo.Objects.Utilizations;

import sun.misc.Unsafe;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteUtils {
	protected static final int tempBufferSize = (int) Math.max(Long.BYTES, Math.pow(2, 10));
	protected static final ThreadLocal<WeakReference<ByteBuffer>> tempBuffer = new ThreadLocal<>();
	protected static final ThreadLocal<WeakReference<ByteBuffer>> nativeTempBuffer = new ThreadLocal<>();

	protected static ByteBuffer getTempBuffer() {
		ByteBuffer result = tempBuffer.get() == null || tempBuffer.get().get() == null ? null : tempBuffer.get().get();
		if(result == null) { result = ByteBuffer.allocate(tempBufferSize); tempBuffer.set(new WeakReference<>(result)); } return result;
	}
	protected static ByteBuffer getNativeTempBuffer() {
		ByteBuffer result = nativeTempBuffer.get() == null || nativeTempBuffer.get().get() == null ? null : nativeTempBuffer.get().get();
		if(result == null) { result = ByteBuffer.allocateDirect(tempBufferSize).order(ByteOrder.nativeOrder()); nativeTempBuffer.set(new WeakReference<>(result)); } return result;
	}

	protected static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	public static String toHex(byte[] bytes) {
		int len = StringUtils.componentValueLength(bytes.length * 2);
		Object result = Array.newInstance(StringUtils.FIELD_TYPECOM_String_value, len);
		for(int i = 0; i < bytes.length; i++) { byte v = bytes[i];
			StringUtils.putCharAt(result, i * 2, HEX_ARRAY[(v & 0xF0) >> 4]);
			StringUtils.putCharAt(result, i * 2 + 1, HEX_ARRAY[v & 0x0F]);
		} return StringUtils.newString(result);
	}

	// floating point to short (represented by bytes)
	public static short toHalf(float object) {
		int bits = Float.floatToIntBits(object);
		int sign = bits >>> 16 & 0x8000;
		int val = (bits & 0x7fffffff) + 0x1000;
		if(val < 0x47800000) {
			if(val >= 0x38800000) return (short) (sign | val - 0x38000000 >>> 13);
			if(val < 0x33000000) return (short) sign;
			val = (bits & 0x7fffffff) >>> 23;
			return (short) (sign | ((bits & 0x7fffff | 0x800000) + (0x800000 >>> val - 102) >>> 126 - val));
		}
		if((bits & 0x7fffffff) < 0x47800000) return (short) (sign | 0x7bff);
		if(val < 0x7f800000) return (short) (sign | 0x7c00);
		return (short) (sign | 0x7c00 | (bits & 0x007fffff) >>> 13);
	}
	public static short toHalf(double object) { return toHalf((float) object); }

	// short (represented by bytes) to floating point
	public static float toFloat(short object) {
		int mant = object & 0x03ff;
		int exp = object & 0x7c00;
		if(exp == 0x7c00) exp = 0x3fc00;
		else if(exp != 0) {
			exp += 0x1c000;
			if(mant == 0 && exp > 0x1c400)
				return Float.intBitsToFloat((object & 0x8000) << 16 | exp << 13 | 0x3ff);
		} else if(mant != 0) {
			exp = 0x1c400;
			do { mant <<= 1; exp -= 0x400;
			} while((mant & 0x400) == 0);
			mant &= 0x3ff;
		}
		return Float.intBitsToFloat((object & 0x8000) << 16 | (exp | mant) << 13);
	}
	public static double toDouble(short object) { return toFloat(object); }

	public static short[] toHalf(float[] object, short[] result, int offObj, int offRes, int len) {
		ArrayUtils.assertIndex(offObj, object.length, len);
		ArrayUtils.assertIndex(offRes, result.length, len);
		int fit = (len / 8) * 8;
		int i = 0; for(; i < fit; i += 8) {
			result[offRes + i    ] = toHalf(object[offObj + i    ]);
			result[offRes + i + 1] = toHalf(object[offObj + i + 1]);
			result[offRes + i + 2] = toHalf(object[offObj + i + 2]);
			result[offRes + i + 3] = toHalf(object[offObj + i + 3]);
			result[offRes + i + 4] = toHalf(object[offObj + i + 4]);
			result[offRes + i + 5] = toHalf(object[offObj + i + 5]);
			result[offRes + i + 6] = toHalf(object[offObj + i + 6]);
			result[offRes + i + 7] = toHalf(object[offObj + i + 7]);
		}
		for(; i < len; i++) result[offRes + i] = toHalf(object[offObj + i]);
		return result;
	}
	public static short[] toHalf(float[] object, short[] result, int offObj, int offRes) { return toHalf(object, result, offObj, offRes, object.length - offObj); }
	public static short[] toHalf(float[] object, short[] result, int offObj) { return toHalf(object, result, offObj, 0); }
	public static short[] toHalf(float[] object, short[] result) { return toHalf(object, result, 0); }
	public static short[] toHalf(float[] object) { return toHalf(object, new short[object.length]); }

	public static short[] toHalf(double[] object, short[] result, int offObj, int offRes, int len) {
		ArrayUtils.assertIndex(offObj, object.length, len);
		ArrayUtils.assertIndex(offRes, result.length, len);
		int fit = (len / 8) * 8;
		int i = 0; for(; i < fit; i += 8) {
			result[offRes + i    ] = toHalf(object[offObj + i    ]);
			result[offRes + i + 1] = toHalf(object[offObj + i + 1]);
			result[offRes + i + 2] = toHalf(object[offObj + i + 2]);
			result[offRes + i + 3] = toHalf(object[offObj + i + 3]);
			result[offRes + i + 4] = toHalf(object[offObj + i + 4]);
			result[offRes + i + 5] = toHalf(object[offObj + i + 5]);
			result[offRes + i + 6] = toHalf(object[offObj + i + 6]);
			result[offRes + i + 7] = toHalf(object[offObj + i + 7]);
		}
		for(; i < len; i++) result[offRes + i] = toHalf(object[offObj + i]);
		return result;
	}
	public static short[] toHalf(double[] object, short[] result, int offObj, int offRes) { return toHalf(object, result, offObj, offRes, object.length - offObj); }
	public static short[] toHalf(double[] object, short[] result, int offObj) { return toHalf(object, result, offObj, 0); }
	public static short[] toHalf(double[] object, short[] result) { return toHalf(object, result, 0); }
	public static short[] toHalf(double[] object) { return toHalf(object, new short[object.length]); }

	public static float[] toFloat(short[] object, float[] result, int offObj, int offRes, int len) {
		ArrayUtils.assertIndex(offObj, object.length, len);
		ArrayUtils.assertIndex(offRes, result.length, len);
		int fit = (len / 8) * 8;
		int i = 0; for(; i < fit; i += 8) {
			result[offRes + i    ] = toFloat(object[offObj + i    ]);
			result[offRes + i + 1] = toFloat(object[offObj + i + 1]);
			result[offRes + i + 2] = toFloat(object[offObj + i + 2]);
			result[offRes + i + 3] = toFloat(object[offObj + i + 3]);
			result[offRes + i + 4] = toFloat(object[offObj + i + 4]);
			result[offRes + i + 5] = toFloat(object[offObj + i + 5]);
			result[offRes + i + 6] = toFloat(object[offObj + i + 6]);
			result[offRes + i + 7] = toFloat(object[offObj + i + 7]);
		}
		for(; i < len; i++) result[offRes + i] = toFloat(object[offObj + i]);
		return result;
	}
	public static float[] toFloat(short[] object, float[] result, int offObj, int offRes) { return toFloat(object, result, offObj, offRes, object.length - offObj); }
	public static float[] toFloat(short[] object, float[] result, int offObj) { return toFloat(object, result, offObj, 0); }
	public static float[] toFloat(short[] object, float[] result) { return toFloat(object, result, 0); }
	public static float[] toFloat(short[] object) { return toFloat(object, new float[object.length]); }

	public static double[] toDouble(short[] object, double[] result, int offObj, int offRes, int len) {
		ArrayUtils.assertIndex(offObj, object.length, len);
		ArrayUtils.assertIndex(offRes, result.length, len);
		int fit = (len / 8) * 8;
		int i = 0; for(; i < fit; i += 8) {
			result[offRes + i    ] = toDouble(object[offObj + i    ]);
			result[offRes + i + 1] = toDouble(object[offObj + i + 1]);
			result[offRes + i + 2] = toDouble(object[offObj + i + 2]);
			result[offRes + i + 3] = toDouble(object[offObj + i + 3]);
			result[offRes + i + 4] = toDouble(object[offObj + i + 4]);
			result[offRes + i + 5] = toDouble(object[offObj + i + 5]);
			result[offRes + i + 6] = toDouble(object[offObj + i + 6]);
			result[offRes + i + 7] = toDouble(object[offObj + i + 7]);
		}
		for(; i < len; i++) result[offRes + i] = toDouble(object[offObj + i]);
		return result;
	}
	public static double[] toDouble(short[] object, double[] result, int offObj, int offRes) { return toDouble(object, result, offObj, offRes, object.length - offObj); }
	public static double[] toDouble(short[] object, double[] result, int offObj) { return toDouble(object, result, offObj, 0); }
	public static double[] toDouble(short[] object, double[] result) { return toDouble(object, result, 0); }
	public static double[] toDouble(short[] object) { return toDouble(object, new double[object.length]); }

	public static String toZeroTerminatedString(byte[] bytes, int off, int len) {
		if(bytes == null) return null;
		ArrayUtils.assertIndex(off, bytes.length, len); int i = 0;
		while(i < len && bytes[off + i++] != 0); i--;
		return new String(bytes, off, off + i);
	}
	public static String toZeroTerminatedString(byte[] bytes, int off) { return toZeroTerminatedString(bytes, off, bytes.length - off); }
	public static String toZeroTerminatedString(byte[] bytes) { return toZeroTerminatedString(bytes, 0); }

	public static String toZeroTerminatedString(ByteBuffer bytes, int off, int len) {
		if(bytes == null) return null;
		ArrayUtils.assertIndex(off, bytes.capacity(), len); int i = 0;
		while(i < len && bytes.get(off + i++) != 0); i--;
		byte[] tempByte = ArrayUtils.getTempByteArray(i);
		UnsafeUtils.__copyMemory(null, BufferUtils.__getAddress(bytes) + off, tempByte, Unsafe.ARRAY_BYTE_BASE_OFFSET, i);
		return new String(tempByte, 0, i);
	}
	public static String toZeroTerminatedString(ByteBuffer bytes, int off) { return toZeroTerminatedString(bytes, off, bytes.capacity() - off); }
	public static String toZeroTerminatedString(ByteBuffer bytes) { return toZeroTerminatedString(bytes, 0); }

	public static String toZeroTerminatedString(long address, int len) {
		if(address == UnsafeUtils.NULLPTR) return null;
		Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		int i = 0; while(i < len && unsafe.getByte(address + i++) != 0); i--;
		byte[] tempByte = ArrayUtils.getTempByteArray(i);
		UnsafeUtils.__copyMemory(null, address, tempByte, Unsafe.ARRAY_BYTE_BASE_OFFSET, i);
		return new String(tempByte, 0, i);
	}
	public static String toZeroTerminatedString(long address) { return toZeroTerminatedString(address, Integer.MAX_VALUE); }

	public static byte[] toByte(int object, byte[] result, int off) { ArrayUtils.assertIndex(off, result.length, Integer.BYTES); ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.putInt(0, object); System.arraycopy(tempBuffer.array(), 0, result, off, Integer.BYTES); return result; }
	public static byte[] toByte(long object, byte[] result, int off) { ArrayUtils.assertIndex(off, result.length, Long.BYTES); ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.putLong(0, object); System.arraycopy(tempBuffer.array(), 0, result, off, Long.BYTES); return result; }
	public static byte[] toByte(short object, byte[] result, int off) { ArrayUtils.assertIndex(off, result.length, Short.BYTES); ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.putShort(0, object); System.arraycopy(tempBuffer.array(), 0, result, off, Short.BYTES); return result; }
	public static byte[] toByte(float object, byte[] result, int off) { ArrayUtils.assertIndex(off, result.length, Float.BYTES); ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.putFloat(0, object); System.arraycopy(tempBuffer.array(), 0, result, off, Float.BYTES); return result; }
	public static byte[] toByte(double object, byte[] result, int off) { ArrayUtils.assertIndex(off, result.length, Double.BYTES); ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.putDouble(0, object); System.arraycopy(tempBuffer.array(), 0, result, off, Double.BYTES); return result; }
	public static byte[] toByte(char object, byte[] result, int off) { ArrayUtils.assertIndex(off, result.length, Character.BYTES); ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.putChar(0, object); System.arraycopy(tempBuffer.array(), 0, result, off, Character.BYTES); return result; }
	public static byte[] toByte(int object, byte[] result) { return toByte(object, result, 0); }
	public static byte[] toByte(long object, byte[] result) { return toByte(object, result, 0); }
	public static byte[] toByte(short object, byte[] result) { return toByte(object, result, 0); }
	public static byte[] toByte(float object, byte[] result) { return toByte(object, result, 0); }
	public static byte[] toByte(double object, byte[] result) { return toByte(object, result, 0); }
	public static byte[] toByte(char object, byte[] result) { return toByte(object, result, 0); }
	public static byte[] toByte(int object) { return toByte(object, new byte[Integer.BYTES]); }
	public static byte[] toByte(long object) { return toByte(object, new byte[Long.BYTES]); }
	public static byte[] toByte(short object) { return toByte(object, new byte[Short.BYTES]); }
	public static byte[] toByte(float object) { return toByte(object, new byte[Float.BYTES]); }
	public static byte[] toByte(double object) { return toByte(object, new byte[Double.BYTES]); }
	public static byte[] toByte(char object) { return toByte(object, new byte[Character.BYTES]); }
	public static byte[] toByte(int[] objects, byte[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length); ArrayUtils.assertIndex(dstOff, result.length, length * Integer.BYTES); for(int i = 0; i < length; i++) toByte(objects[srcOff + i], result, dstOff + (i * Integer.BYTES)); return result; }
	public static byte[] toByte(long[] objects, byte[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length); ArrayUtils.assertIndex(dstOff, result.length, length * Long.BYTES); for(int i = 0; i < length; i++) toByte(objects[srcOff + i], result, dstOff + (i * Long.BYTES)); return result; }
	public static byte[] toByte(short[] objects, byte[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length); ArrayUtils.assertIndex(dstOff, result.length, length * Short.BYTES); for(int i = 0; i < length; i++) toByte(objects[srcOff + i], result, dstOff + (i * Short.BYTES)); return result; }
	public static byte[] toByte(float[] objects, byte[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length); ArrayUtils.assertIndex(dstOff, result.length, length * Float.BYTES); for(int i = 0; i < length; i++) toByte(objects[srcOff + i], result, dstOff + (i * Float.BYTES)); return result; }
	public static byte[] toByte(double[] objects, byte[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length); ArrayUtils.assertIndex(dstOff, result.length, length * Double.BYTES); for(int i = 0; i < length; i++) toByte(objects[srcOff + i], result, dstOff + (i * Double.BYTES)); return result; }
	public static byte[] toByte(char[] objects, byte[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length); ArrayUtils.assertIndex(dstOff, result.length, length * Character.BYTES); for(int i = 0; i < length; i++) toByte(objects[srcOff + i], result, dstOff + (i * Character.BYTES)); return result; }
	public static byte[] toByte(int[] objects, byte[] result, int srcOff, int dstOff) { return toByte(objects, result, srcOff, dstOff, objects.length); }
	public static byte[] toByte(long[] objects, byte[] result, int srcOff, int dstOff) { return toByte(objects, result, srcOff, dstOff, objects.length); }
	public static byte[] toByte(short[] objects, byte[] result, int srcOff, int dstOff) { return toByte(objects, result, srcOff, dstOff, objects.length); }
	public static byte[] toByte(float[] objects, byte[] result, int srcOff, int dstOff) { return toByte(objects, result, srcOff, dstOff, objects.length); }
	public static byte[] toByte(double[] objects, byte[] result, int srcOff, int dstOff) { return toByte(objects, result, srcOff, dstOff, objects.length); }
	public static byte[] toByte(char[] objects, byte[] result, int srcOff, int dstOff) { return toByte(objects, result, srcOff, dstOff, objects.length); }
	public static byte[] toByte(int[] objects, byte[] result, int srcOff) { return toByte(objects, result, srcOff, 0); }
	public static byte[] toByte(long[] objects, byte[] result, int srcOff) { return toByte(objects, result, srcOff, 0); }
	public static byte[] toByte(short[] objects, byte[] result, int srcOff) { return toByte(objects, result, srcOff, 0); }
	public static byte[] toByte(float[] objects, byte[] result, int srcOff) { return toByte(objects, result, srcOff, 0); }
	public static byte[] toByte(double[] objects, byte[] result, int srcOff) { return toByte(objects, result, srcOff, 0); }
	public static byte[] toByte(char[] objects, byte[] result, int srcOff) { return toByte(objects, result, srcOff, 0); }
	public static byte[] toByte(int[] objects, byte[] result) { return toByte(objects, result, 0); }
	public static byte[] toByte(long[] objects, byte[] result) { return toByte(objects, result, 0); }
	public static byte[] toByte(short[] objects, byte[] result) { return toByte(objects, result, 0); }
	public static byte[] toByte(float[] objects, byte[] result) { return toByte(objects, result, 0); }
	public static byte[] toByte(double[] objects, byte[] result) { return toByte(objects, result, 0); }
	public static byte[] toByte(char[] objects, byte[] result) { return toByte(objects, result, 0); }
	public static byte[] toByte(int[] objects) { return toByte(objects, new byte[objects.length * Integer.BYTES]); }
	public static byte[] toByte(long[] objects) { return toByte(objects, new byte[objects.length * Long.BYTES]); }
	public static byte[] toByte(short[] objects) { return toByte(objects, new byte[objects.length * Short.BYTES]); }
	public static byte[] toByte(float[] objects) { return toByte(objects, new byte[objects.length * Float.BYTES]); }
	public static byte[] toByte(double[] objects) { return toByte(objects, new byte[objects.length * Double.BYTES]); }
	public static byte[] toByte(char[] objects) { return toByte(objects, new byte[objects.length * Character.BYTES]); }

	public static int toInt(byte d1, byte d2, byte d3, byte d4) { ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.position(0); tempBuffer.put(d1); tempBuffer.put(d2); tempBuffer.put(d3); tempBuffer.put(d4); return tempBuffer.getInt(0); }
	public static long toLong(byte d1, byte d2, byte d3, byte d4, byte d5, byte d6, byte d7, byte d8) { ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.position(0); tempBuffer.put(d1); tempBuffer.put(d2); tempBuffer.put(d3); tempBuffer.put(d4); tempBuffer.put(d5); tempBuffer.put(d6); tempBuffer.put(d7); tempBuffer.put(d8); return tempBuffer.getLong(0); }
	public static short toShort(byte d1, byte d2) { ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.position(0); tempBuffer.put(d1); tempBuffer.put(d2); return tempBuffer.getShort(0); }
	public static float toFloat(byte d1, byte d2, byte d3, byte d4) { ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.position(0); tempBuffer.put(d1); tempBuffer.put(d2); tempBuffer.put(d3); tempBuffer.put(d4); return tempBuffer.getFloat(0); }
	public static double toDouble(byte d1, byte d2, byte d3, byte d4, byte d5, byte d6, byte d7, byte d8) { ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.position(0); tempBuffer.put(d1); tempBuffer.put(d2); tempBuffer.put(d3); tempBuffer.put(d4); tempBuffer.put(d5); tempBuffer.put(d6); tempBuffer.put(d7); tempBuffer.put(d8); return tempBuffer.getDouble(0); }
	public static char toChar(byte d1, byte d2) { ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.position(0); tempBuffer.put(d1); tempBuffer.put(d2); return tempBuffer.getChar(0); }
	public static int toInt(byte[] objects, int off) { ArrayUtils.assertIndex(off, objects.length, Integer.BYTES); ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.position(0); tempBuffer.put(objects, off, Integer.BYTES); return tempBuffer.getInt(0); }
	public static long toLong(byte[] objects, int off) { ArrayUtils.assertIndex(off, objects.length, Long.BYTES); ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.position(0); tempBuffer.put(objects, off, Long.BYTES); return tempBuffer.getLong(0); }
	public static short toShort(byte[] objects, int off) { ArrayUtils.assertIndex(off, objects.length, Short.BYTES); ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.position(0); tempBuffer.put(objects, off, Short.BYTES); return tempBuffer.getShort(0); }
	public static float toFloat(byte[] objects, int off) { ArrayUtils.assertIndex(off, objects.length, Float.BYTES); ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.position(0); tempBuffer.put(objects, off, Float.BYTES); return tempBuffer.getFloat(0); }
	public static double toDouble(byte[] objects, int off) { ArrayUtils.assertIndex(off, objects.length, Double.BYTES); ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.position(0); tempBuffer.put(objects, off, Double.BYTES); return tempBuffer.getDouble(0); }
	public static char toChar(byte[] objects, int off) { ArrayUtils.assertIndex(off, objects.length, Character.BYTES); ByteBuffer tempBuffer = getTempBuffer(); tempBuffer.position(0); tempBuffer.put(objects, off, Character.BYTES); return tempBuffer.getChar(0); }
	public static int toInt(byte... objects) { return toInt(objects, 0); }
	public static long toLong(byte... objects) { return toLong(objects, 0); }
	public static short toShort(byte... objects) { return toShort(objects, 0); }
	public static float toFloat(byte... objects) { return toFloat(objects, 0); }
	public static double toDouble(byte... objects) { return toDouble(objects, 0); }
	public static char toChar(byte... objects) { return toChar(objects, 0); }
	public static int[] toInts(byte[] objects, int[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length * Integer.BYTES); ArrayUtils.assertIndex(dstOff, result.length, length); for(int i = 0; i < length; i++) result[srcOff + i] = toInt(objects, dstOff + (i * Integer.BYTES)); return result; }
	public static long[] toLongs(byte[] objects, long[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length * Long.BYTES); ArrayUtils.assertIndex(dstOff, result.length, length); for(int i = 0; i < length; i++) result[srcOff + i] = toLong(objects, dstOff + (i * Long.BYTES)); return result; }
	public static short[] toShorts(byte[] objects, short[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length * Short.BYTES); ArrayUtils.assertIndex(dstOff, result.length, length); for(int i = 0; i < length; i++) result[srcOff + i] = toShort(objects, dstOff + (i * Short.BYTES)); return result; }
	public static float[] toFloats(byte[] objects, float[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length * Float.BYTES); ArrayUtils.assertIndex(dstOff, result.length, length); for(int i = 0; i < length; i++) result[srcOff + i] = toFloat(objects, dstOff + (i * Float.BYTES)); return result; }
	public static double[] toDoubles(byte[] objects, double[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length * Double.BYTES); ArrayUtils.assertIndex(dstOff, result.length, length); for(int i = 0; i < length; i++) result[srcOff + i] = toDouble(objects, dstOff + (i * Double.BYTES)); return result; }
	public static char[] toChars(byte[] objects, char[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length * Character.BYTES); ArrayUtils.assertIndex(dstOff, result.length, length); for(int i = 0; i < length; i++) result[srcOff + i] = toChar(objects, dstOff + (i * Character.BYTES)); return result; }
	public static int[] toInts(byte[] objects, int[] result, int srcOff, int dstOff) { return toInts(objects, result, srcOff, dstOff, objects.length / Integer.BYTES); }
	public static long[] toLongs(byte[] objects, long[] result, int srcOff, int dstOff) { return toLongs(objects, result, srcOff, dstOff, objects.length / Long.BYTES); }
	public static short[] toShorts(byte[] objects, short[] result, int srcOff, int dstOff) { return toShorts(objects, result, srcOff, dstOff, objects.length / Short.BYTES); }
	public static float[] toFloats(byte[] objects, float[] result, int srcOff, int dstOff) { return toFloats(objects, result, srcOff, dstOff, objects.length / Float.BYTES); }
	public static double[] toDoubles(byte[] objects, double[] result, int srcOff, int dstOff) { return toDoubles(objects, result, srcOff, dstOff, objects.length / Double.BYTES); }
	public static char[] toChars(byte[] objects, char[] result, int srcOff, int dstOff) { return toChars(objects, result, srcOff, dstOff, objects.length / Character.BYTES); }
	public static int[] toInts(byte[] objects, int[] result, int srcOff) { return toInts(objects, result, srcOff, 0); }
	public static long[] toLongs(byte[] objects, long[] result, int srcOff) { return toLongs(objects, result, srcOff, 0); }
	public static short[] toShorts(byte[] objects, short[] result, int srcOff) { return toShorts(objects, result, srcOff, 0); }
	public static float[] toFloats(byte[] objects, float[] result, int srcOff) { return toFloats(objects, result, srcOff, 0); }
	public static double[] toDoubles(byte[] objects, double[] result, int srcOff) { return toDoubles(objects, result, srcOff, 0); }
	public static char[] toChars(byte[] objects, char[] result, int srcOff) { return toChars(objects, result, srcOff, 0); }
	public static int[] toInts(byte[] objects, int[] result) { return toInts(objects, result, 0); }
	public static long[] toLongs(byte[] objects, long[] result) { return toLongs(objects, result, 0); }
	public static short[] toShorts(byte[] objects, short[] result) { return toShorts(objects, result, 0); }
	public static float[] toFloats(byte[] objects, float[] result) { return toFloats(objects, result, 0); }
	public static double[] toDoubles(byte[] objects, double[] result) { return toDoubles(objects, result, 0); }
	public static char[] toChars(byte[] objects, char[] result) { return toChars(objects, result, 0); }
	public static int[] toInts(byte... objects) { return toInts(objects, new int[objects.length / Integer.BYTES]); }
	public static long[] toLongs(byte... objects) { return toLongs(objects, new long[objects.length / Long.BYTES]); }
	public static short[] toShorts(byte... objects) { return toShorts(objects, new short[objects.length / Short.BYTES]); }
	public static float[] toFloats(byte... objects) { return toFloats(objects, new float[objects.length / Float.BYTES]); }
	public static double[] toDoubles(byte... objects) { return toDoubles(objects, new double[objects.length / Double.BYTES]); }
	public static char[] toChars(byte... objects) { return toChars(objects, new char[objects.length / Character.BYTES]); }

	public static byte[] toByteNative(int object, byte[] result, int off) { ArrayUtils.assertIndex(off, result.length, Integer.BYTES); ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.putInt(0, object); System.arraycopy(tempBuffer.array(), 0, result, off, Integer.BYTES); return result; }
	public static byte[] toByteNative(long object, byte[] result, int off) { ArrayUtils.assertIndex(off, result.length, Long.BYTES); ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.putLong(0, object); System.arraycopy(tempBuffer.array(), 0, result, off, Long.BYTES); return result; }
	public static byte[] toByteNative(short object, byte[] result, int off) { ArrayUtils.assertIndex(off, result.length, Short.BYTES); ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.putShort(0, object); System.arraycopy(tempBuffer.array(), 0, result, off, Short.BYTES); return result; }
	public static byte[] toByteNative(float object, byte[] result, int off) { ArrayUtils.assertIndex(off, result.length, Float.BYTES); ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.putFloat(0, object); System.arraycopy(tempBuffer.array(), 0, result, off, Float.BYTES); return result; }
	public static byte[] toByteNative(double object, byte[] result, int off) { ArrayUtils.assertIndex(off, result.length, Double.BYTES); ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.putDouble(0, object); System.arraycopy(tempBuffer.array(), 0, result, off, Double.BYTES); return result; }
	public static byte[] toByteNative(char object, byte[] result, int off) { ArrayUtils.assertIndex(off, result.length, Character.BYTES); ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.putChar(0, object); System.arraycopy(tempBuffer.array(), 0, result, off, Character.BYTES); return result; }
	public static byte[] toByteNative(int object, byte[] result) { return toByteNative(object, result, 0); }
	public static byte[] toByteNative(long object, byte[] result) { return toByteNative(object, result, 0); }
	public static byte[] toByteNative(short object, byte[] result) { return toByteNative(object, result, 0); }
	public static byte[] toByteNative(float object, byte[] result) { return toByteNative(object, result, 0); }
	public static byte[] toByteNative(double object, byte[] result) { return toByteNative(object, result, 0); }
	public static byte[] toByteNative(char object, byte[] result) { return toByteNative(object, result, 0); }
	public static byte[] toByteNative(int object) { return toByteNative(object, new byte[Integer.BYTES]); }
	public static byte[] toByteNative(long object) { return toByteNative(object, new byte[Long.BYTES]); }
	public static byte[] toByteNative(short object) { return toByteNative(object, new byte[Short.BYTES]); }
	public static byte[] toByteNative(float object) { return toByteNative(object, new byte[Float.BYTES]); }
	public static byte[] toByteNative(double object) { return toByteNative(object, new byte[Double.BYTES]); }
	public static byte[] toByteNative(char object) { return toByteNative(object, new byte[Character.BYTES]); }
	public static byte[] toByteNative(int[] objects, byte[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length); ArrayUtils.assertIndex(dstOff, result.length, length * Integer.BYTES); for(int i = 0; i < length; i++) toByteNative(objects[srcOff + i], result, dstOff + (i * Integer.BYTES)); return result; }
	public static byte[] toByteNative(long[] objects, byte[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length); ArrayUtils.assertIndex(dstOff, result.length, length * Long.BYTES); for(int i = 0; i < length; i++) toByteNative(objects[srcOff + i], result, dstOff + (i * Long.BYTES)); return result; }
	public static byte[] toByteNative(short[] objects, byte[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length); ArrayUtils.assertIndex(dstOff, result.length, length * Short.BYTES); for(int i = 0; i < length; i++) toByteNative(objects[srcOff + i], result, dstOff + (i * Short.BYTES)); return result; }
	public static byte[] toByteNative(float[] objects, byte[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length); ArrayUtils.assertIndex(dstOff, result.length, length * Float.BYTES); for(int i = 0; i < length; i++) toByteNative(objects[srcOff + i], result, dstOff + (i * Float.BYTES)); return result; }
	public static byte[] toByteNative(double[] objects, byte[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length); ArrayUtils.assertIndex(dstOff, result.length, length * Double.BYTES); for(int i = 0; i < length; i++) toByteNative(objects[srcOff + i], result, dstOff + (i * Double.BYTES)); return result; }
	public static byte[] toByteNative(char[] objects, byte[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length); ArrayUtils.assertIndex(dstOff, result.length, length * Character.BYTES); for(int i = 0; i < length; i++) toByteNative(objects[srcOff + i], result, dstOff + (i * Character.BYTES)); return result; }
	public static byte[] toByteNative(int[] objects, byte[] result, int srcOff, int dstOff) { return toByteNative(objects, result, srcOff, dstOff, objects.length); }
	public static byte[] toByteNative(long[] objects, byte[] result, int srcOff, int dstOff) { return toByteNative(objects, result, srcOff, dstOff, objects.length); }
	public static byte[] toByteNative(short[] objects, byte[] result, int srcOff, int dstOff) { return toByteNative(objects, result, srcOff, dstOff, objects.length); }
	public static byte[] toByteNative(float[] objects, byte[] result, int srcOff, int dstOff) { return toByteNative(objects, result, srcOff, dstOff, objects.length); }
	public static byte[] toByteNative(double[] objects, byte[] result, int srcOff, int dstOff) { return toByteNative(objects, result, srcOff, dstOff, objects.length); }
	public static byte[] toByteNative(char[] objects, byte[] result, int srcOff, int dstOff) { return toByteNative(objects, result, srcOff, dstOff, objects.length); }
	public static byte[] toByteNative(int[] objects, byte[] result, int srcOff) { return toByteNative(objects, result, srcOff, 0); }
	public static byte[] toByteNative(long[] objects, byte[] result, int srcOff) { return toByteNative(objects, result, srcOff, 0); }
	public static byte[] toByteNative(short[] objects, byte[] result, int srcOff) { return toByteNative(objects, result, srcOff, 0); }
	public static byte[] toByteNative(float[] objects, byte[] result, int srcOff) { return toByteNative(objects, result, srcOff, 0); }
	public static byte[] toByteNative(double[] objects, byte[] result, int srcOff) { return toByteNative(objects, result, srcOff, 0); }
	public static byte[] toByteNative(char[] objects, byte[] result, int srcOff) { return toByteNative(objects, result, srcOff, 0); }
	public static byte[] toByteNative(int[] objects, byte[] result) { return toByteNative(objects, result, 0); }
	public static byte[] toByteNative(long[] objects, byte[] result) { return toByteNative(objects, result, 0); }
	public static byte[] toByteNative(short[] objects, byte[] result) { return toByteNative(objects, result, 0); }
	public static byte[] toByteNative(float[] objects, byte[] result) { return toByteNative(objects, result, 0); }
	public static byte[] toByteNative(double[] objects, byte[] result) { return toByteNative(objects, result, 0); }
	public static byte[] toByteNative(char[] objects, byte[] result) { return toByteNative(objects, result, 0); }
	public static byte[] toByteNative(int[] objects) { return toByteNative(objects, new byte[objects.length * Integer.BYTES]); }
	public static byte[] toByteNative(long[] objects) { return toByteNative(objects, new byte[objects.length * Long.BYTES]); }
	public static byte[] toByteNative(short[] objects) { return toByteNative(objects, new byte[objects.length * Short.BYTES]); }
	public static byte[] toByteNative(float[] objects) { return toByteNative(objects, new byte[objects.length * Float.BYTES]); }
	public static byte[] toByteNative(double[] objects) { return toByteNative(objects, new byte[objects.length * Double.BYTES]); }
	public static byte[] toByteNative(char[] objects) { return toByteNative(objects, new byte[objects.length * Character.BYTES]); }

	public static int toIntNative(byte d1, byte d2, byte d3, byte d4) { ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.position(0); tempBuffer.put(d1); tempBuffer.put(d2); tempBuffer.put(d3); tempBuffer.put(d4); return tempBuffer.getInt(0); }
	public static long toLongNative(byte d1, byte d2, byte d3, byte d4, byte d5, byte d6, byte d7, byte d8) { ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.position(0); tempBuffer.put(d1); tempBuffer.put(d2); tempBuffer.put(d3); tempBuffer.put(d4); tempBuffer.put(d5); tempBuffer.put(d6); tempBuffer.put(d7); tempBuffer.put(d8); return tempBuffer.getLong(0); }
	public static short toShortNative(byte d1, byte d2) { ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.position(0); tempBuffer.put(d1); tempBuffer.put(d2); return tempBuffer.getShort(0); }
	public static float toFloatNative(byte d1, byte d2, byte d3, byte d4) { ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.position(0); tempBuffer.put(d1); tempBuffer.put(d2); tempBuffer.put(d3); tempBuffer.put(d4); return tempBuffer.getFloat(0); }
	public static double toDoubleNative(byte d1, byte d2, byte d3, byte d4, byte d5, byte d6, byte d7, byte d8) { ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.position(0); tempBuffer.put(d1); tempBuffer.put(d2); tempBuffer.put(d3); tempBuffer.put(d4); tempBuffer.put(d5); tempBuffer.put(d6); tempBuffer.put(d7); tempBuffer.put(d8); return tempBuffer.getDouble(0); }
	public static char toCharNative(byte d1, byte d2) { ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.position(0); tempBuffer.put(d1); tempBuffer.put(d2); return tempBuffer.getChar(0); }
	public static int toIntNative(byte[] objects, int off) { ArrayUtils.assertIndex(off, objects.length, Integer.BYTES); ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.position(0); tempBuffer.put(objects, off, Integer.BYTES); return tempBuffer.getInt(0); }
	public static long toLongNative(byte[] objects, int off) { ArrayUtils.assertIndex(off, objects.length, Long.BYTES); ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.position(0); tempBuffer.put(objects, off, Long.BYTES); return tempBuffer.getLong(0); }
	public static short toShortNative(byte[] objects, int off) { ArrayUtils.assertIndex(off, objects.length, Short.BYTES); ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.position(0); tempBuffer.put(objects, off, Short.BYTES); return tempBuffer.getShort(0); }
	public static float toFloatNative(byte[] objects, int off) { ArrayUtils.assertIndex(off, objects.length, Float.BYTES); ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.position(0); tempBuffer.put(objects, off, Float.BYTES); return tempBuffer.getFloat(0); }
	public static double toDoubleNative(byte[] objects, int off) { ArrayUtils.assertIndex(off, objects.length, Double.BYTES); ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.position(0); tempBuffer.put(objects, off, Double.BYTES); return tempBuffer.getDouble(0); }
	public static char toCharNative(byte[] objects, int off) { ArrayUtils.assertIndex(off, objects.length, Character.BYTES); ByteBuffer tempBuffer = getNativeTempBuffer(); tempBuffer.position(0); tempBuffer.put(objects, off, Character.BYTES); return tempBuffer.getChar(0); }
	public static int toIntNative(byte... objects) { return toIntNative(objects, 0); }
	public static long toLongNative(byte... objects) { return toLongNative(objects, 0); }
	public static short toShortNative(byte... objects) { return toShortNative(objects, 0); }
	public static float toFloatNative(byte... objects) { return toFloatNative(objects, 0); }
	public static double toDoubleNative(byte... objects) { return toDoubleNative(objects, 0); }
	public static char toCharNative(byte... objects) { return toCharNative(objects, 0); }
	public static int[] toIntsNative(byte[] objects, int[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length * Integer.BYTES); ArrayUtils.assertIndex(dstOff, result.length, length); for(int i = 0; i < length; i++) result[srcOff + i] = toIntNative(objects, dstOff + (i * Integer.BYTES)); return result; }
	public static long[] toLongsNative(byte[] objects, long[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length * Long.BYTES); ArrayUtils.assertIndex(dstOff, result.length, length); for(int i = 0; i < length; i++) result[srcOff + i] = toLongNative(objects, dstOff + (i * Long.BYTES)); return result; }
	public static short[] toShortsNative(byte[] objects, short[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length * Short.BYTES); ArrayUtils.assertIndex(dstOff, result.length, length); for(int i = 0; i < length; i++) result[srcOff + i] = toShortNative(objects, dstOff + (i * Short.BYTES)); return result; }
	public static float[] toFloatsNative(byte[] objects, float[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length * Float.BYTES); ArrayUtils.assertIndex(dstOff, result.length, length); for(int i = 0; i < length; i++) result[srcOff + i] = toFloatNative(objects, dstOff + (i * Float.BYTES)); return result; }
	public static double[] toDoublesNative(byte[] objects, double[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length * Double.BYTES); ArrayUtils.assertIndex(dstOff, result.length, length); for(int i = 0; i < length; i++) result[srcOff + i] = toDoubleNative(objects, dstOff + (i * Double.BYTES)); return result; }
	public static char[] toCharsNative(byte[] objects, char[] result, int srcOff, int dstOff, int length) { ArrayUtils.assertIndex(srcOff, objects.length, length * Character.BYTES); ArrayUtils.assertIndex(dstOff, result.length, length); for(int i = 0; i < length; i++) result[srcOff + i] = toCharNative(objects, dstOff + (i * Character.BYTES)); return result; }
	public static int[] toIntsNative(byte[] objects, int[] result, int srcOff, int dstOff) { return toIntsNative(objects, result, srcOff, dstOff, objects.length / Integer.BYTES); }
	public static long[] toLongsNative(byte[] objects, long[] result, int srcOff, int dstOff) { return toLongsNative(objects, result, srcOff, dstOff, objects.length / Long.BYTES); }
	public static short[] toShortsNative(byte[] objects, short[] result, int srcOff, int dstOff) { return toShortsNative(objects, result, srcOff, dstOff, objects.length / Short.BYTES); }
	public static float[] toFloatsNative(byte[] objects, float[] result, int srcOff, int dstOff) { return toFloatsNative(objects, result, srcOff, dstOff, objects.length / Float.BYTES); }
	public static double[] toDoublesNative(byte[] objects, double[] result, int srcOff, int dstOff) { return toDoublesNative(objects, result, srcOff, dstOff, objects.length / Double.BYTES); }
	public static char[] toCharsNative(byte[] objects, char[] result, int srcOff, int dstOff) { return toCharsNative(objects, result, srcOff, dstOff, objects.length / Character.BYTES); }
	public static int[] toIntsNative(byte[] objects, int[] result, int srcOff) { return toIntsNative(objects, result, srcOff, 0); }
	public static long[] toLongsNative(byte[] objects, long[] result, int srcOff) { return toLongsNative(objects, result, srcOff, 0); }
	public static short[] toShortsNative(byte[] objects, short[] result, int srcOff) { return toShortsNative(objects, result, srcOff, 0); }
	public static float[] toFloatsNative(byte[] objects, float[] result, int srcOff) { return toFloatsNative(objects, result, srcOff, 0); }
	public static double[] toDoublesNative(byte[] objects, double[] result, int srcOff) { return toDoublesNative(objects, result, srcOff, 0); }
	public static char[] toCharsNative(byte[] objects, char[] result, int srcOff) { return toCharsNative(objects, result, srcOff, 0); }
	public static int[] toIntsNative(byte[] objects, int[] result) { return toIntsNative(objects, result, 0); }
	public static long[] toLongsNative(byte[] objects, long[] result) { return toLongsNative(objects, result, 0); }
	public static short[] toShortsNative(byte[] objects, short[] result) { return toShortsNative(objects, result, 0); }
	public static float[] toFloatsNative(byte[] objects, float[] result) { return toFloatsNative(objects, result, 0); }
	public static double[] toDoublesNative(byte[] objects, double[] result) { return toDoublesNative(objects, result, 0); }
	public static char[] toCharsNative(byte[] objects, char[] result) { return toCharsNative(objects, result, 0); }
	public static int[] toIntsNative(byte... objects) { return toIntsNative(objects, new int[objects.length / Integer.BYTES]); }
	public static long[] toLongsNative(byte... objects) { return toLongsNative(objects, new long[objects.length / Long.BYTES]); }
	public static short[] toShortsNative(byte... objects) { return toShortsNative(objects, new short[objects.length / Short.BYTES]); }
	public static float[] toFloatsNative(byte... objects) { return toFloatsNative(objects, new float[objects.length / Float.BYTES]); }
	public static double[] toDoublesNative(byte... objects) { return toDoublesNative(objects, new double[objects.length / Double.BYTES]); }
	public static char[] toCharsNative(byte... objects) { return toCharsNative(objects, new char[objects.length / Character.BYTES]); }
}
