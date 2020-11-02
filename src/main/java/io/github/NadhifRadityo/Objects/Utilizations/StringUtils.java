package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import org.apache.commons.lang3.StringEscapeUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.Comparator;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	protected static final int HI_BYTE_SHIFT;
	protected static final int LO_BYTE_SHIFT;

	protected static final Class FIELD_TYPECOM_String_value;
	protected static final long AFIELD_String_value;
	protected static final long AFIELD_String_hash;
	protected static final long AFIELD_String_coder;
	protected static final boolean FIELD_VAL_String_COMPACT_STRINGS;

	static { try {
		boolean bigEndian = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
		if(bigEndian) { HI_BYTE_SHIFT = 8; LO_BYTE_SHIFT = 0; }
		else  { HI_BYTE_SHIFT = 0; LO_BYTE_SHIFT = 8; }
		FIELD_TYPECOM_String_value = String.class.getDeclaredField("value").getType().getComponentType();

		if(SystemUtils.JAVA_DETECTION_VERSION > 8) {
			FStringUtilsImplementation instance = (FStringUtilsImplementation) FutureJavaUtils.fastCall("getInstance");
			AFIELD_String_value = instance.getStringValueOffset();
			AFIELD_String_hash = instance.getStringHashOffset();
			AFIELD_String_coder = instance.getStringCoderOffset();
			FIELD_VAL_String_COMPACT_STRINGS = instance.getStringCompactStrings();
		} else {
			Unsafe unsafe = UnsafeUtils.R_getUnsafe();
			Field FIELD_String_value = String.class.getDeclaredField("value");
			Field FIELD_String_hash = String.class.getDeclaredField("hash");
			AFIELD_String_value = unsafe.objectFieldOffset(FIELD_String_value);
			AFIELD_String_hash = unsafe.objectFieldOffset(FIELD_String_hash);
			AFIELD_String_coder = -1;
			FIELD_VAL_String_COMPACT_STRINGS = false;
		}
	} catch(Exception e) { throw new Error(e); } }

	public static Object getValue(String string) { return UnsafeUtils.R_getUnsafe().getObject(string, AFIELD_String_value); }
	public static int getHash(String string) { return UnsafeUtils.R_getUnsafe().getInt(string, AFIELD_String_hash); }
	public static void setValue(String string, Object value) {
		if((value instanceof byte[] && SystemUtils.JAVA_DETECTION_VERSION <= 8) || (value instanceof char[] && SystemUtils.JAVA_DETECTION_VERSION > 8))
			throw new IllegalArgumentException("Incompatible object");
		UnsafeUtils.R_getUnsafe().putObject(string, AFIELD_String_value, value);
	}
	public static String newString(Object value) {
		Unsafe unsafe = UnsafeUtils.R_getUnsafe(); String result;
		try { result = (String) unsafe.allocateInstance(String.class);
		} catch(InstantiationException e) { throw new Error(e); }
		setValue(result, value); return result;
	}

	public static int componentValueLength(int len) {
		if(SystemUtils.JAVA_DETECTION_VERSION <= 8) return len;
		if(FIELD_VAL_String_COMPACT_STRINGS) return len;
		return len << 1;
	}
	public static void putCharAt(Object value, int i, char c) {
		if(SystemUtils.JAVA_DETECTION_VERSION <= 8) { ((char[]) value)[i] = c; return; }
		byte[] _value = (byte[]) value;
		if(FIELD_VAL_String_COMPACT_STRINGS) {
			if(c >>> 8 != 0) throw new IllegalArgumentException("Cannot encode character to current JVM");
			_value[i] = (byte) c;
		} else { i <<= 1; _value[i++] = (byte) (c >> HI_BYTE_SHIFT); _value[i] = (byte) (c >> LO_BYTE_SHIFT); }
	}

	public static String escapeString(String string) { return StringEscapeUtils.escapeJava(string); }
	public static String unescapeString(String string) { return StringEscapeUtils.unescapeJava(string); }

	public static String capitaliseEachWords(String string) {
		Object result = ArrayUtils.clone(getValue(string));
		boolean beforeSpace = true;
		for(int i = 0; i < string.length(); i++) { char c = string.charAt(i);
			if(c == '\r' || c == '\n' || c == '\t' || c == '\f' || c == ' ' || c == 0x000b) { beforeSpace = true; continue; }
			if(beforeSpace) { putCharAt(result, i, Character.toUpperCase(c)); beforeSpace = false; }
		} return newString(result);
	}

	public static boolean isPalindrome(String string) {
		int lengthHalf = string.length() / 2;
		for(int i = 0; i < lengthHalf; i++)
			if(string.charAt(i) != string.charAt(string.length() - i - 1)) return false;
		return true;
	}

	public static String mergeCross(String[] strings, int start, int end) {
		ArrayUtils.assertIndex(start, strings.length, 0);
		ArrayUtils.assertIndex(end, strings.length, 0);
		int stringsLength = end - start; int len = 0; int min = Integer.MAX_VALUE;
		for(int i = start; i < end; i++) { int length = strings[i].length(); len += length; if(min > length) min = length; }
		Object result = Array.newInstance(FIELD_TYPECOM_String_value, componentValueLength(len));
		for(int i = 0; i < len; i++) { if(i / stringsLength >= min) break;
			putCharAt(result, i, strings[start + (i % stringsLength)].charAt(i / stringsLength)); }
		return newString(result);
	}
	public static String mergeCross(String... strings) {
		return mergeCross(strings, 0, strings.length);
	}

	public static String merge(String by, String[] strings, int start, int end) {
		ArrayUtils.assertIndex(start, strings.length, 0);
		ArrayUtils.assertIndex(end, strings.length, 0);
		int stringsLength = end - start; int len = 0;
		for(int i = start; i < end; i++) len += strings[i].length();
		len += by.length() * (stringsLength - 1); int off = 0;
		Object result = Array.newInstance(FIELD_TYPECOM_String_value, componentValueLength(len));
		Object byValue = getValue(by); int byLength = Array.getLength(byValue);
		for(int i = start; i < end; i++) {
			Object value = getValue(strings[i]);
			int length = Array.getLength(value);
			System.arraycopy(value, 0, result, off, length);
			off += length; if(i == strings.length - 1) continue;
			System.arraycopy(byValue, 0, result, off, byLength);
			off += byLength;
		} return newString(result);
	}
	public static String merge(String by, String... strings) { return merge(by, strings, 0, strings.length); }

	public static String repeat(String string, int count) {
		Object value = getValue(string); int length = Array.getLength(value); int len = length * count;
		Object result = Array.newInstance(FIELD_TYPECOM_String_value, componentValueLength(len));
		for(int i = 0; i < count; i++) System.arraycopy(value, 0, result, i * length, length);
		return newString(result);
	}

	public static int encodeASCII(CharSequence text, boolean nullTerminated, long target) {
		if(text == null || target == UnsafeUtils.NULLPTR) return -1;
		int len = text.length(); Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		for(int p = 0; p < len; p++) unsafe.putByte(target + p, (byte) text.charAt(p));
		if(nullTerminated) unsafe.putByte(target + len++, (byte) 0);
		return len;
	}
	public static int encodeASCII(CharSequence text, boolean nullTerminated, byte[] target) {
		return encodeASCII(text, nullTerminated, UnsafeUtils.__toAddress(target) + Unsafe.ARRAY_BYTE_BASE_OFFSET);
	}
	public static int lengthASCII(CharSequence value, boolean nullTerminated) {
		return value.length() + (nullTerminated ? 1 : 0);
	}

	public static int encodeUTF8(CharSequence text, boolean nullTerminated, long target) {
		if(text == null || target == UnsafeUtils.NULLPTR) return -1;
		int i = 0, len = text.length(), p = 0; char c;
		Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		while(i < len && (c = text.charAt(i)) < 0x80) { unsafe.putByte(target + p++, (byte) c); i++; }
		while(i < len) { c = text.charAt(i++);
			if(c < 0x80) { unsafe.putByte(target + p++, (byte) c); continue; }
			int cp = c; if(c < 0x800) {
				unsafe.putByte(target + p++, (byte) (0xC0 | cp >> 6));
				unsafe.putByte(target + p++, (byte) (0x80 | cp & 0x3F));
				continue;
			} if(Character.isHighSurrogate(c)) {
				cp = Character.toCodePoint(c, text.charAt(i++));
				unsafe.putByte(target + p++, (byte) (0xF0 | cp >> 18));
				unsafe.putByte(target + p++, (byte) (0x80 | cp >> 12 & 0x3F));
			} else { unsafe.putByte(target + p++, (byte) (0xE0 | cp >> 12)); }
			unsafe.putByte(target + p++, (byte) (0x80 | cp >> 6 & 0x3F));
			unsafe.putByte(target + p++, (byte) (0x80 | cp & 0x3F));
		}
		if(nullTerminated)
			unsafe.putByte(target + p++, (byte) 0);
		return p;
	}
	public static int encodeUTF8(CharSequence text, boolean nullTerminated, byte[] target) {
		return encodeUTF8(text, nullTerminated, UnsafeUtils.__toAddress(target) + Unsafe.ARRAY_BYTE_BASE_OFFSET);
	}
	public static int lengthUTF8(CharSequence value, boolean nullTerminated) {
		int i, len = value.length(), bytes = len;
		for(i = 0; i < len; i++) if(0x80 <= value.charAt(i)) break;
		for(; i < len; i++) { char c = value.charAt(i);
			if(0x800 > c) { bytes += (0x7F - c) >>> 31; continue; }
			for(int j = i; j < len; j++) { char d = value.charAt(j);
				if(d < 0x800) bytes += (0x7F - d) >>> 31;
				else if(d < Character.MIN_SURROGATE || Character.MAX_SURROGATE < d) bytes += 2;
				else { bytes += 2; j++; }
			}
		} return bytes + (nullTerminated ? 1 : 0);
	}

	public static int encodeUTF16(CharSequence text, boolean nullTerminated, long target) {
		if(text == null || target == UnsafeUtils.NULLPTR) return -1;
		int len = text.length(); Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		for(int i = 0; i < len; i++) unsafe.putShort(target + Integer.toUnsignedLong(i) * 2, (short) text.charAt(i));
		if(nullTerminated) unsafe.putShort(target + Integer.toUnsignedLong(len++) * 2, (short) 0);
		return 2 * len;
	}
	public static int encodeUTF16(CharSequence text, boolean nullTerminated, byte[] target) {
		return encodeUTF16(text, nullTerminated, UnsafeUtils.__toAddress(target) + Unsafe.ARRAY_BYTE_BASE_OFFSET);
	}
	public static int lengthUTF16(CharSequence value, boolean nullTerminated) {
		return (value.length() + (nullTerminated ? 1 : 0)) << 1;
	}

	public static final Comparator<String> sortStringWithNumber = new Comparator<String>() {
		@Override public int compare(String o1, String o2) { return charToNumber(o1).compareTo(charToNumber(o2)); }
		String[] corresponding = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K" };
		String charToNumber(String what) {
			StringBuilder result = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
			try { for(int i = 0; i < what.length(); i++) { char c = what.charAt(i);
				result.append(Character.isDigit(c) || c == '.' ? corresponding[c == '.' ? 10 : Integer.parseInt(c + "")] : c);
			} return result.toString();
			} finally { Pool.returnObject(StringBuilder.class, result); }
		}
	};

	protected interface FStringUtilsImplementation extends FutureJavaUtils.FutureJavaImplementation {
		long getStringValueOffset() throws Exception;
		long getStringHashOffset() throws Exception;
		long getStringCoderOffset() throws Exception;
		boolean getStringCompactStrings() throws Exception;
	}
}
