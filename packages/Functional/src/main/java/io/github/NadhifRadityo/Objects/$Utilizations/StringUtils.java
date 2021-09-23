package io.github.NadhifRadityo.Objects.$Utilizations;

import io.github.NadhifRadityo.Objects.$Interface.Functional.Comparator;
import org.apache.commons.lang3.StringEscapeUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	public static final byte LATIN1 = 0;
	public static final byte UTF16  = 1;

	public static final int HI_BYTE_SHIFT;
	public static final int LO_BYTE_SHIFT;

	public static final Class FIELD_TYPECOM_String_value;
	public static final int FIELD_TYPECOM_String_value_BASE_OFFSET;
	protected static final long AFIELD_String_value;
	protected static final long AFIELD_String_hash;
	protected static final long AFIELD_String_coder;
	protected static final long AFIELD_String_hashIsZero;
	public static final boolean FIELD_VAL_String_COMPACT_STRINGS;

	static { try {
		if(SystemUtils.IS_OS_BIG_ENDIAN) { HI_BYTE_SHIFT = 8; LO_BYTE_SHIFT = 0; }
		else { HI_BYTE_SHIFT = 0; LO_BYTE_SHIFT = 8; }
		Field FIELD_TYPECOM_String = String.class.getDeclaredField("value");
		FIELD_TYPECOM_String_value = FIELD_TYPECOM_String.getType().getComponentType();
		FIELD_TYPECOM_String_value_BASE_OFFSET = UnsafeUtils.baseOffset(FIELD_TYPECOM_String.getType());

		if(SystemUtils.JAVA_DETECTION_VERSION > 8) {
			FStringUtilsImplementation instance = (FStringUtilsImplementation) FutureJavaUtils.fastCall("getInstance");
			AFIELD_String_value = instance.getStringValueOffset();
			AFIELD_String_hash = instance.getStringHashOffset();
			AFIELD_String_coder = instance.getStringCoderOffset();
			AFIELD_String_hashIsZero = instance.getStringHashIsZeroOffset();
			FIELD_VAL_String_COMPACT_STRINGS = instance.getStringCompactStrings();
		} else {
			UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
			Field FIELD_String_value = String.class.getDeclaredField("value");
			Field FIELD_String_hash = String.class.getDeclaredField("hash");
			AFIELD_String_value = unsafe.objectFieldOffset(FIELD_String_value);
			AFIELD_String_hash = unsafe.objectFieldOffset(FIELD_String_hash);
			AFIELD_String_coder = -1;
			AFIELD_String_hashIsZero = -1;
			FIELD_VAL_String_COMPACT_STRINGS = false;
		}
	} catch(Exception e) { throw new Error(e); } }

	public static Object getValue(String string) { return UnsafeUtils.R_getUnsafe().getObject(string, AFIELD_String_value); }
	public static int getHash(String string) { return UnsafeUtils.R_getUnsafe().getInt(string, AFIELD_String_hash); }
	public static byte getCoder(String string) { return AFIELD_String_coder == -1 ? -1 : UnsafeUtils.R_getUnsafe().getByte(string, AFIELD_String_coder); }
	public static byte getCoder(String[] strings) { if(AFIELD_String_coder == -1) return -1; if(!FIELD_VAL_String_COMPACT_STRINGS) return UTF16; for(String string : strings) { if(getCoder(string) == LATIN1) continue; return UTF16; } return LATIN1; }
	public static void setValue(String string, Object value, byte coder) {
		if((value instanceof byte[] && SystemUtils.JAVA_DETECTION_VERSION <= 8) || (value instanceof char[] && SystemUtils.JAVA_DETECTION_VERSION > 8))
			throw new IllegalArgumentException("Incompatible object");
		UnsafeUtils.R_getUnsafe().putObject(string, AFIELD_String_value, value);
		if(SystemUtils.JAVA_DETECTION_VERSION > 8)
			UnsafeUtils.R_getUnsafe().putByte(string, AFIELD_String_coder, coder);
	}
	public static void setValue(String string, Object value) {
		setValue(string, value, LATIN1);
	}
	public static void invalidateHash(String string) {
		UnsafeUtils.R_getUnsafe().putInt(string, AFIELD_String_hash, 0);
		if(SystemUtils.JAVA_DETECTION_VERSION > 8)
			UnsafeUtils.R_getUnsafe().putBoolean(string, AFIELD_String_hashIsZero, false);
	}

	public static int componentValueLength(int len, byte coder) { // Javassist candidate
		if(SystemUtils.JAVA_DETECTION_VERSION <= 8) return len;
		if(FIELD_VAL_String_COMPACT_STRINGS && coder == LATIN1) return len;
		return len << 1;
	}
	public static void putCharAt(Object value, int i, char c, byte coder) { // Javassist candidate
		if(SystemUtils.JAVA_DETECTION_VERSION <= 8) { ((char[]) value)[i] = c; return; }
		byte[] _value = (byte[]) value;
		if(FIELD_VAL_String_COMPACT_STRINGS && coder == LATIN1) {
			if(c >>> 8 == 0) _value[i] = (byte) c;
			throw new IllegalArgumentException("Cannot encode character to current String");
		} else { i <<= 1; _value[i++] = (byte) (c >> HI_BYTE_SHIFT); _value[i] = (byte) (c >> LO_BYTE_SHIFT); }
	}
	public static String newString(Object value, byte coder) {
		UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe(); String result;
		try { result = (String) unsafe.allocateInstance(String.class);
		} catch(InstantiationException e) { throw new Error(e); }
		setValue(result, value, coder); return result;
	}
	public static String newString(Object value) {
		return newString(value, LATIN1);
	}

	public static String escapeString(String string) { return StringEscapeUtils.escapeJava(string); }
	public static String unescapeString(String string) { return StringEscapeUtils.unescapeJava(string); }

	public static void toLowerCaseX(String string) {
		byte coder = getCoder(string);
		Object result = getValue(string);
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i); if(!Character.isLetter(c)) continue;
			putCharAt(result, i, Character.toLowerCase(c), coder);
		} invalidateHash(string);
	}
	public static void toUpperCaseX(String string) {
		byte coder = getCoder(string);
		Object result = getValue(string);
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i); if(!Character.isLetter(c)) continue;
			putCharAt(result, i, Character.toUpperCase(c), coder);
		} invalidateHash(string);
	}
	public static void capitaliseEachWordsX(String string) {
		byte coder = getCoder(string);
		Object result = getValue(string);
		boolean beforeSpace = true;
		for(int i = 0; i < string.length(); i++) { char c = string.charAt(i);
			if(Character.isWhitespace(c)) { beforeSpace = true; continue; }
			if(beforeSpace) { putCharAt(result, i, Character.toUpperCase(c), coder); beforeSpace = false; }
		} invalidateHash(string);
	}

	public static String toLowerCase(String string) {
		byte coder = getCoder(string);
		Object result = ArrayUtils.clone(getValue(string));
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i); if(!Character.isLetter(c)) continue;
			putCharAt(result, i, Character.toLowerCase(c), coder);
		} return newString(result, coder);
	}
	public static String toUpperCase(String string) {
		byte coder = getCoder(string);
		Object result = ArrayUtils.clone(getValue(string));
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i); if(!Character.isLetter(c)) continue;
			putCharAt(result, i, Character.toUpperCase(c), coder);
		} return newString(result, coder);
	}
	public static String capitaliseEachWords(String string) {
		byte coder = getCoder(string);
		Object result = ArrayUtils.clone(getValue(string));
		boolean beforeSpace = true;
		for(int i = 0; i < string.length(); i++) { char c = string.charAt(i);
			if(Character.isWhitespace(c)) { beforeSpace = true; continue; }
			if(beforeSpace) { putCharAt(result, i, Character.toUpperCase(c), coder); beforeSpace = false; }
		} return newString(result, coder);
	}

	public static boolean isPalindrome(String string) {
		int lengthHalf = string.length() / 2;
		for(int i = 0; i < lengthHalf; i++)
			if(string.charAt(i) != string.charAt(string.length() - i - 1)) return false;
		return true;
	}
	public static boolean isPangram(String string) {
		int p = 0b00000011111111111111111111111111;
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i); if((c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) continue;
			p &= ~(1 << (Character.isUpperCase(c) ? c - 'A' : c - 'a'));
		} return p == 0;
	}
	public static boolean isAlphabetical(String string) {
		char last = 0;
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(!Character.isLetter(c)) continue;
			c = Character.toUpperCase(c);
			if(c < last) return false; last = c;
		} return true;
	}
	public static boolean isAllCharactersSame(String string) {
		if(string.length() == 0) return true;
		char c = string.charAt(0);
		for(int i = 1; i < string.length(); i++)
			if(string.charAt(i) != c) return false;
		return true;
	}
	public static boolean isASCII(String string) {
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(c >>> 8 != 0) return false;
		} return true;
	}
	public static boolean isAlphaNumeric(String string) {
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(!Character.isLetter(c) && !Character.isDigit(c))
				return false;
		} return true;
	}
	public static boolean isAllLetter(String string) {
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(!Character.isLetter(c))
				return false;
		} return true;
	}
	public static boolean isAllDigit(String string) {
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(!Character.isDigit(c))
				return false;
		} return true;
	}
	public static boolean isContainsControlChar(String string) {
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(Character.getType(c) == Character.CONTROL)
				return true;
		} return false;
	}
	public static boolean isLower(String string) {
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(!Character.isLetter(c)) continue;
			if(!Character.isLowerCase(c)) return false;
		} return true;
	}
	public static boolean isUpper(String string) {
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(!Character.isLetter(c)) continue;
			if(!Character.isUpperCase(c)) return false;
		} return true;
	}
	public static boolean isWhitespace(String string) {
		for(int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(!Character.isWhitespace(c)) return false;
		} return true;
	}

	public static String mergeCross(String[] strings, int off, int len) {
		ArrayUtils.assertIndex(off, strings.length, len);
		byte coder = getCoder(strings);
		int resultLen = Integer.MAX_VALUE;
		for(int i = off; i < off + len; i++) { int length = strings[i].length(); if(length < resultLen) resultLen = length; }
		resultLen *= len;
		Object result = Array.newInstance(FIELD_TYPECOM_String_value, componentValueLength(resultLen, coder));
		for(int i = 0; i < resultLen; i++)
			putCharAt(result, i, strings[off + (i % len)].charAt(i / len), coder);
		return newString(result, coder);
	}
	public static String mergeCross(String... strings) {
		return mergeCross(strings, 0, strings.length);
	}

	public static String merge(String by, String[] strings, int off, int len) {
		ArrayUtils.assertIndex(off, strings.length, len);
		byte byCoder = getCoder(by);
		byte coder = byCoder == -1 ? -1 : byCoder == LATIN1 ? getCoder(strings) : UTF16;
		int resultLen = 0;
		for(int i = off; i < off + len; i++) resultLen += strings[i].length();
		resultLen += by.length() * (len - 1); int resultOff = 0;
		Object result = Array.newInstance(FIELD_TYPECOM_String_value, componentValueLength(resultLen, coder));
		Object byValue = getValue(by); int byLength = byCoder == coder ? Array.getLength(byValue) : by.length();
		for(int i = off; i < off + len; i++) {
			String string = strings[i];
			Object value = getValue(string);
			byte valueCoder = getCoder(string);
			if(valueCoder == coder) {
				int length = Array.getLength(value);
				System.arraycopy(value, 0, result, resultOff, length);
				resultOff += length;
			} else {
				for(int j = 0; j < string.length(); j++)
					putCharAt(result, resultOff >> 1 + j, string.charAt(j), coder);
				resultOff += string.length() << 1;
			}
			if(i == strings.length - 1) continue;
			if(byCoder == coder) {
				System.arraycopy(byValue, 0, result, resultOff, byLength);
				resultOff += byLength;
			} else {
				for(int j = 0; j < byLength; j++)
					putCharAt(result, resultOff >> 1 + j, by.charAt(j), coder);
				resultOff += byLength << 1;
			}
		} return newString(result, coder);
	}
	public static String merge(String by, String... strings) { return merge(by, strings, 0, strings.length); }

	public static String repeat(String string, int count) {
		byte coder = getCoder(string);
		Object value = getValue(string); int length = Array.getLength(value);
		Object result = Array.newInstance(FIELD_TYPECOM_String_value, componentValueLength(length * count, coder));
		for(int i = 0; i < count; i++) System.arraycopy(value, 0, result, i * length, length);
		return newString(result, coder);
	}
	public static String reverse(String string) {
		byte coder = getCoder(string);
		Object result = Array.newInstance(FIELD_TYPECOM_String_value, componentValueLength(string.length(), coder));
		for(int i = 0; i < string.length(); i++) putCharAt(result, i, string.charAt(string.length() - i - 1), coder);
		return newString(result, coder);
	}
	public static String rotate(String string, int x) {
		// +x = to the left x times
		// -x = to the right x times
		byte coder = getCoder(string);
		Object result = Array.newInstance(FIELD_TYPECOM_String_value, componentValueLength(string.length(), coder));
		Object value = getValue(string);
		if(x >= 0) {
			x %= string.length();
			int xI = componentValueLength(x, coder);
			int lI = componentValueLength(string.length(), coder);
			System.arraycopy(value, xI, result, 0, lI - xI);
			System.arraycopy(value, 0, result, lI - xI, xI);
		} else {
			x = -x % string.length();
			int xI = componentValueLength(x, coder);
			int lI = componentValueLength(string.length(), coder);
			System.arraycopy(value, 0, result, xI, lI - xI);
			System.arraycopy(value, lI - xI, result, 0, xI);
		} return newString(result, coder);
	}

	// String Encoder
	public static int encodeASCII(CharSequence text, boolean nullTerminated, long target) {
		if(text == null || target == UnsafeUtils.NULLPTR) return -1;
		int len = text.length(); UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		for(int p = 0; p < len; p++) unsafe.putByte(target + p, (byte) text.charAt(p));
		if(nullTerminated) unsafe.putByte(target + len++, (byte) 0);
		return len;
	}
	public static int encodeASCII(CharSequence text, boolean nullTerminated, byte[] target) {
		return encodeASCII(text, nullTerminated, UnsafeUtils.__toAddress(target) + UnsafeUtils.Unsafe.ARRAY_BYTE_BASE_OFFSET);
	}
	public static int lengthEncodeASCII(CharSequence value, boolean nullTerminated) {
		return value.length() + (nullTerminated ? 1 : 0);
	}

	public static int encodeUTF8(CharSequence text, boolean nullTerminated, long target) {
		if(text == null || target == UnsafeUtils.NULLPTR) return -1;
		int i = 0, len = text.length(), p = 0; char c;
		UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
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
		return encodeUTF8(text, nullTerminated, UnsafeUtils.__toAddress(target) + UnsafeUtils.Unsafe.ARRAY_BYTE_BASE_OFFSET);
	}
	public static int lengthEncodeUTF8(CharSequence value, boolean nullTerminated) {
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
		int len = text.length(); UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		for(int i = 0; i < len; i++) unsafe.putShort(target + Integer.toUnsignedLong(i) * 2, (short) text.charAt(i));
		if(nullTerminated) unsafe.putShort(target + Integer.toUnsignedLong(len++) * 2, (short) 0);
		return 2 * len;
	}
	public static int encodeUTF16(CharSequence text, boolean nullTerminated, byte[] target) {
		return encodeUTF16(text, nullTerminated, UnsafeUtils.__toAddress(target) + UnsafeUtils.Unsafe.ARRAY_BYTE_BASE_OFFSET);
	}
	public static int lengthEncodeUTF16(CharSequence value, boolean nullTerminated) {
		return (value.length() + (nullTerminated ? 1 : 0)) << 1;
	}

	// String Decoder
	public static int lengthDecodeASCII(long target, int maxLength) {
		if(target == UnsafeUtils.NULLPTR) return 0;
		UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		int alignment = SystemUtils.IS_JAVA_64BIT ? 8 : 4;
		int i = 0;
		if(maxLength >= alignment) {
			int misalignment = (int) (target % alignment);
			if(misalignment != 0) for(int len = alignment - misalignment; i < len; i++)
				if(unsafe.getByte(target + i) == 0) return i;
			if(alignment == 8) while(i <= maxLength - 8) {
				if(ByteUtils.hasZeroByte(unsafe.getLong(target + i))) break; i += 8;
			} else while(i <= maxLength - 4) {
				if(ByteUtils.hasZeroByte(unsafe.getInt(target + i))) break; i += 4;
			}
		}
		for(; i < maxLength; i++)
			if(unsafe.getByte(target + i) == 0) break;
		return i;
	}
	public static String decodeASCII(long target, int length) {
		if(target == UnsafeUtils.NULLPTR || length < 0) return null; if(length == 0) return "";
		if(SystemUtils.JAVA_DETECTION_VERSION > 8 && FIELD_VAL_String_COMPACT_STRINGS) {
			Object result = Array.newInstance(FIELD_TYPECOM_String_value, componentValueLength(length, LATIN1));
			UnsafeUtils.__copyMemory(null, target, result, FIELD_TYPECOM_String_value_BASE_OFFSET, length);
			return newString(result, LATIN1);
		}
		byte[] result = ArrayUtils.getTempByteArray(length);
		UnsafeUtils.__copyMemory(null, target, result, UnsafeUtils.Unsafe.ARRAY_BYTE_BASE_OFFSET, length);
		return new String(result, 0, 0, length);
	}
	public static String decodeASCII(long target) {
		return decodeASCII(target, lengthDecodeASCII(target, Integer.MAX_VALUE));
	}
	public static int lengthDecodeUTF8(long target, int length) {
		if(target == UnsafeUtils.NULLPTR) return 0;
		UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		int alignment = SystemUtils.IS_JAVA_64BIT ? 8 : 4;
		int i = 0;
		if(length >= alignment) {
			int misalignment = (int) (target % alignment);
			if(misalignment != 0) for(int len = alignment - misalignment; i < len; i++)
				if(unsafe.getByte(target + i) == 0) return i;
			if(alignment == 8) while(i <= length - 8) {
				if(ByteUtils.hasZeroByte(unsafe.getLong(target + i))) break; i += 8;
			} else while(i <= length - 4) {
				if(ByteUtils.hasZeroByte(unsafe.getInt(target + i))) break; i += 4;
			}
		}
		for(; i < length; i++)
			if(unsafe.getByte(target + i) == 0) break;
		return i;
	}
	public static String decodeUTF8(long target, int length) {
		if(target == UnsafeUtils.NULLPTR || length < 0) return null; if(length == 0) return "";
		UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		char[] result = ArrayUtils.getEmptyCharacterArray(length);
		int i = 0; int position = 0;
		while(position < length) {
			int b0 = unsafe.getByte(null, target + position++) & 0xFF;
			if(b0 < 0x80) { result[i++] = (char) b0; continue; }
			int b1 = unsafe.getByte(null, target + position++) & 0x3F;
			if((b0 & 0xE0) == 0xC0) { result[i++] = (char) (((b0 & 0x1F) << 6) | b1); continue; }
			int b2 = unsafe.getByte(null, target + position++) & 0x3F;
			if((b0 & 0xF0) == 0xE0) { result[i++] = (char) (((b0 & 0x0F) << 12) | (b1 << 6) | b2); continue; }
			int b3 = unsafe.getByte(null, target + position++) & 0x3F;
			int cp = ((b0 & 0x07) << 18) | (b1 << 12) | (b2 << 6) | b3;
			result[i++] = (char) ((cp >>> 10) + 0xD7C0);
			result[i++] = (char) ((cp & 0x3FF) + 0xDC00);
		} return new String(result, 0, i);
	}
	public static String decodeUTF8(long target) {
		return decodeUTF8(target, lengthDecodeUTF8(target, Integer.MAX_VALUE));
	}
	public static int lengthDecodeUTF16(long target, int length) {
		if(target == UnsafeUtils.NULLPTR) return 0;
		UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		int alignment = SystemUtils.IS_JAVA_64BIT ? 8 : 4;
		int i = 0;
		if(length >= alignment) {
			int misalignment = (int) (target % alignment);
			if(misalignment != 0) for(int len = alignment - misalignment; i < len; i += 2)
				if(unsafe.getShort(target + i) == 0) return i;
			if(alignment == 8) while(i <= length - 8) {
				if(ByteUtils.hasZeroShort(unsafe.getLong(target + i))) break; i += 8;
			} else while(i <= length - 4) {
				if(ByteUtils.hasZeroShort(unsafe.getInt(target + i))) break; i += 4;
			}
		}
		for(; i < length; i += 2)
			if(unsafe.getShort(target + i) == 0) break;
		return i;
	}
	public static String decodeUTF16(long target, int length) {
		if(target == UnsafeUtils.NULLPTR || length < 0) return null; if(length == 0) return "";
		char[] result = ArrayUtils.getEmptyCharacterArray(length);
		UnsafeUtils.__copyMemory(null, target, result, UnsafeUtils.Unsafe.ARRAY_CHAR_BASE_OFFSET, length);
		if(SystemUtils.IS_OS_LITTLE_ENDIAN) ByteUtils.reverseBytes(result, result, 0, length);
		return new String(result, 0, length);
	}
	public static String decodeUTF16(long target) {
		return decodeUTF16(target, lengthDecodeUTF16(target, Integer.MAX_VALUE));
	}

	public static abstract class StringComparable implements Comparator.StringComparator {
		public static final StringComparable SENSITIVE = new StringComparable() {
			@Override protected char charAt(String string, int i) {
				return i >= string.length() ? 0 : string.charAt(i);
			}
		};
		public static final StringComparable INSENSITIVE = new StringComparable() {
			@Override protected char charAt(String string, int i) {
				return i >= string.length() ? 0 : Character.toUpperCase(string.charAt(i));
			}
		};

		protected StringComparable() { }

		// Optimized version of: http://www.davekoelle.com/files/AlphanumComparator.java
		// Clump between digit and non digit characters
		protected int getChunk(String string, int i, int length) {
			char c = charAt(string, i++);
			boolean isDigit = Character.isDigit(c);
			while(i < length) {
				c = charAt(string, i);
				if(isDigit ^ Character.isDigit(c))
					break; i++;
			} return isDigit ? -i : i;
		}
		@Override public int compare(String o1, String o2) {
			if(o1 == null || o2 == null) return 0;
			int i1 = 0; int i2 = 0;
			int o1Length = o1.length();
			int o2Length = o2.length();

			while(i1 < o1Length && i2 < o2Length) {
				int _ic1 = getChunk(o1, i1, o1Length);
				int _ic2 = getChunk(o2, i2, o2Length);
				boolean ic1IsDigit = _ic1 < 0;
				boolean ic2IsDigit = _ic2 < 0;
				int ic1 = Math.abs(_ic1);
				int ic2 = Math.abs(_ic2);
				int ic1Length = ic1 - i1;
				int ic2Length = ic2 - i2;

				int result = 0;
				if(ic1IsDigit && ic2IsDigit) {
					result = ic1Length - ic2Length;
					if(result == 0) {
						int lim = Math.min(ic1Length, ic2Length);
						for(int i = 0; i < lim; i++) {
							result = charAt(o1, i1 + i) - charAt(o2, i2 + i);
							if(result != 0) break; }
					}
				} else {
					int lim = Math.min(ic1Length, ic2Length);
					for(int i = 0; i < lim; i++) {
						result = charAt(o1, i1 + i) - charAt(o2, i2 + i);
						if(result != 0) break; }
					if(result == 0) result = ic1Length - ic2Length;
				}
				if(result != 0) return result;
				i1 = ic1; i2 = ic2;
			} return o1Length - o2Length;
		}
		protected abstract char charAt(String string, int i);
	}

	protected interface FStringUtilsImplementation extends FutureJavaUtils.FutureJavaImplementation {
		long getStringValueOffset() throws Exception;
		long getStringHashOffset() throws Exception;
		long getStringCoderOffset() throws Exception;
		long getStringHashIsZeroOffset() throws Exception;
		boolean getStringCompactStrings() throws Exception;
	}
}
