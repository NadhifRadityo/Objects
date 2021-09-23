package io.github.NadhifRadityo.Objects.$Utilizations;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class UnsafeUtils {

	public static final long NULLPTR = 0L;
	protected static final boolean PRIVILEGED_ACCESS = Boolean.parseBoolean(System.getProperty("unsafeutils.privilegedaccess", "true"));
	protected static final long UNSAFE_COPY_THRESHOLD = 1024L * 1024L;
	protected static final long COPY_STRIDE = 8;
	protected static final Unsafe unsafe;

	static { try {
		if(SystemUtils.JAVA_DETECTION_VERSION > 8) {
			FUnsafeUtilsImplementation instance = (FUnsafeUtilsImplementation) FutureJavaUtils.fastCall("getInstance");
			unsafe = instance.getUnsafeImplementation();
		} else {
			Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			sun.misc.Unsafe _unsafe = (sun.misc.Unsafe) field.get(null);
			unsafe = new UnsafeImpl(_unsafe);
		}
	} catch(Exception e) { throw new Error(e); } }

	static Error noAccessException() { throw new IllegalAccessError("No access exception"); }
	static Unsafe R_getUnsafe() { return unsafe; }
	public static Unsafe getUnsafe() { return !PRIVILEGED_ACCESS || PrivilegedUtils.isRunningOnPrivileged() ? R_getUnsafe() : null; }
	public static Unsafe E_getUnsafe() { Unsafe unsafe = getUnsafe(); if(unsafe == null) throw noAccessException(); return unsafe; }

	public static int baseOffset(Class clazz) {
		if(clazz == int[].class) return Unsafe.ARRAY_INT_BASE_OFFSET;
		if(clazz == long[].class) return Unsafe.ARRAY_LONG_BASE_OFFSET;
		if(clazz == short[].class) return Unsafe.ARRAY_SHORT_BASE_OFFSET;
		if(clazz == float[].class) return Unsafe.ARRAY_FLOAT_BASE_OFFSET;
		if(clazz == double[].class) return Unsafe.ARRAY_DOUBLE_BASE_OFFSET;
		if(clazz == char[].class) return Unsafe.ARRAY_CHAR_BASE_OFFSET;
		if(clazz == byte[].class) return Unsafe.ARRAY_BYTE_BASE_OFFSET;
		if(clazz == boolean[].class) return Unsafe.ARRAY_BOOLEAN_BASE_OFFSET;
		if(clazz.isArray()) return Unsafe.ARRAY_OBJECT_BASE_OFFSET;
		throw new IllegalArgumentException("Not an array class");
	}
	public static int indexScale(Class clazz) {
		if(clazz == int[].class) return Unsafe.ARRAY_INT_INDEX_SCALE;
		if(clazz == long[].class) return Unsafe.ARRAY_LONG_INDEX_SCALE;
		if(clazz == short[].class) return Unsafe.ARRAY_SHORT_INDEX_SCALE;
		if(clazz == float[].class) return Unsafe.ARRAY_FLOAT_INDEX_SCALE;
		if(clazz == double[].class) return Unsafe.ARRAY_DOUBLE_INDEX_SCALE;
		if(clazz == char[].class) return Unsafe.ARRAY_CHAR_INDEX_SCALE;
		if(clazz == byte[].class) return Unsafe.ARRAY_BYTE_INDEX_SCALE;
		if(clazz == boolean[].class) return Unsafe.ARRAY_BOOLEAN_INDEX_SCALE;
		if(clazz.isArray()) return Unsafe.ARRAY_OBJECT_INDEX_SCALE;
		throw new IllegalArgumentException("Not an array class");
	}

	public static int getInt(Object object, long offset) { return E_getUnsafe().getInt(object, offset); }
	public static Object getObject(Object object, long offset) { return E_getUnsafe().getObject(object, offset); }
	public static boolean getBoolean(Object object, long offset) { return E_getUnsafe().getBoolean(object, offset); }
	public static byte getByte(Object object, long offset) { return E_getUnsafe().getByte(object, offset); }
	public static short getShort(Object object, long offset) { return E_getUnsafe().getShort(object, offset); }
	public static char getChar(Object object, long offset) { return E_getUnsafe().getChar(object, offset); }
	public static long getLong(Object object, long offset) { return E_getUnsafe().getLong(object, offset); }
	public static float getFloat(Object object, long offset) { return E_getUnsafe().getFloat(object, offset); }
	public static double getDouble(Object object, long offset) { return E_getUnsafe().getDouble(object, offset); }

	public static void setInt(Object object, long offset, int value) { E_getUnsafe().putInt(object, offset, value); }
	public static void setObject(Object object, long offset, Object value) { E_getUnsafe().putObject(object, offset, value); }
	public static void setBoolean(Object object, long offset, boolean value) { E_getUnsafe().putBoolean(object, offset, value); }
	public static void setByte(Object object, long offset, byte value) { E_getUnsafe().putByte(object, offset, value); }
	public static void setShort(Object object, long offset, short value) { E_getUnsafe().putShort(object, offset, value); }
	public static void setChar(Object object, long offset, char value) { E_getUnsafe().putChar(object, offset, value); }
	public static void setLong(Object object, long offset, long value) { E_getUnsafe().putLong(object, offset, value); }
	public static void setFloat(Object object, long offset, float value) { E_getUnsafe().putFloat(object, offset, value); }
	public static void setDouble(Object object, long offset, double value) { E_getUnsafe().putDouble(object, offset, value); }

	public static int _getInt(Object object, long offset, int fallback) { return ExceptionUtils.doSilentReferencedCallback((args) -> fallback, (args) -> getInt(object, offset)); }
	public static Object _getObject(Object object, long offset, Object fallback) { return ExceptionUtils.doSilentReferencedCallback((args) -> fallback, (args) -> getObject(object, offset)); }
	public static boolean _getBoolean(Object object, long offset, boolean fallback) { return ExceptionUtils.doSilentReferencedCallback((args) -> fallback, (args) -> getBoolean(object, offset)); }
	public static byte _getByte(Object object, long offset, byte fallback) { return ExceptionUtils.doSilentReferencedCallback((args) -> fallback, (args) -> getByte(object, offset)); }
	public static short _getShort(Object object, long offset, short fallback) { return ExceptionUtils.doSilentReferencedCallback((args) -> fallback, (args) -> getShort(object, offset)); }
	public static char _getChar(Object object, long offset, char fallback) { return ExceptionUtils.doSilentReferencedCallback((args) -> fallback, (args) -> getChar(object, offset)); }
	public static long _getLong(Object object, long offset, long fallback) { return ExceptionUtils.doSilentReferencedCallback((args) -> fallback, (args) -> getLong(object, offset)); }
	public static float _getFloat(Object object, long offset, float fallback) { return ExceptionUtils.doSilentReferencedCallback((args) -> fallback, (args) -> getFloat(object, offset)); }
	public static double _getDouble(Object object, long offset, double fallback) { return ExceptionUtils.doSilentReferencedCallback((args) -> fallback, (args) -> getDouble(object, offset)); }

	public static boolean _setInt(Object object, long offset, int value) { return ExceptionUtils.doSilentReferencedCallback((args) -> false, (args) -> { setInt(object, offset, value); return true; }); }
	public static boolean _setObject(Object object, long offset, Object value) { return ExceptionUtils.doSilentReferencedCallback((args) -> false, (args) -> { setObject(object, offset, value); return true; }); }
	public static boolean _setBoolean(Object object, long offset, boolean value) { return ExceptionUtils.doSilentReferencedCallback((args) -> false, (args) -> { setBoolean(object, offset, value); return true; }); }
	public static boolean _setByte(Object object, long offset, byte value) { return ExceptionUtils.doSilentReferencedCallback((args) -> false, (args) -> { setByte(object, offset, value); return true; }); }
	public static boolean _setShort(Object object, long offset, short value) { return ExceptionUtils.doSilentReferencedCallback((args) -> false, (args) -> { setShort(object, offset, value); return true; }); }
	public static boolean _setChar(Object object, long offset, char value) { return ExceptionUtils.doSilentReferencedCallback((args) -> false, (args) -> { setChar(object, offset, value); return true; }); }
	public static boolean _setLong(Object object, long offset, long value) { return ExceptionUtils.doSilentReferencedCallback((args) -> false, (args) -> { setLong(object, offset, value); return true; }); }
	public static boolean _setFloat(Object object, long offset, float value) { return ExceptionUtils.doSilentReferencedCallback((args) -> false, (args) -> { setFloat(object, offset, value); return true; }); }
	public static boolean _setDouble(Object object, long offset, double value) { return ExceptionUtils.doSilentReferencedCallback((args) -> false, (args) -> { setDouble(object, offset, value); return true; }); }

	static int __getInt(Object object, long offset) { return R_getUnsafe().getInt(object, offset); }
	static Object __getObject(Object object, long offset) { return R_getUnsafe().getObject(object, offset); }
	static boolean __getBoolean(Object object, long offset) { return R_getUnsafe().getBoolean(object, offset); }
	static byte __getByte(Object object, long offset) { return R_getUnsafe().getByte(object, offset); }
	static short __getShort(Object object, long offset) { return R_getUnsafe().getShort(object, offset); }
	static char __getChar(Object object, long offset) { return R_getUnsafe().getChar(object, offset); }
	static long __getLong(Object object, long offset) { return R_getUnsafe().getLong(object, offset); }
	static float __getFloat(Object object, long offset) { return R_getUnsafe().getFloat(object, offset); }
	static double __getDouble(Object object, long offset) { return R_getUnsafe().getDouble(object, offset); }

	static void __setInt(Object object, long offset, int value) { R_getUnsafe().putInt(object, offset, value); }
	static void __setObject(Object object, long offset, Object value) { R_getUnsafe().putObject(object, offset, value); }
	static void __setBoolean(Object object, long offset, boolean value) { R_getUnsafe().putBoolean(object, offset, value); }
	static void __setByte(Object object, long offset, byte value) { R_getUnsafe().putByte(object, offset, value); }
	static void __setShort(Object object, long offset, short value) { R_getUnsafe().putShort(object, offset, value); }
	static void __setChar(Object object, long offset, char value) { R_getUnsafe().putChar(object, offset, value); }
	static void __setLong(Object object, long offset, long value) { R_getUnsafe().putLong(object, offset, value); }
	static void __setFloat(Object object, long offset, float value) { R_getUnsafe().putFloat(object, offset, value); }
	static void __setDouble(Object object, long offset, double value) { R_getUnsafe().putDouble(object, offset, value); }

	public static boolean is32Bit() { return E_getUnsafe().addressSize() == 4; }
	public static boolean is64Bit() { return E_getUnsafe().addressSize() == 8; }
	public static <T> T allocateInstance(Class<? extends T> clazz) throws InstantiationException {
		return (T) E_getUnsafe().allocateInstance(clazz);
	}

	public static void copyMemory(long srcAddress, long dstAddress, long len) {
		if(SystemUtils.JAVA_DETECTION_VERSION <= 8) { copyMemoryWithSafePointPolling(srcAddress, dstAddress, len); return; }
		E_getUnsafe().copyMemory(srcAddress, dstAddress, len);
	}
	public static void copyMemoryWithSafePointPolling(long srcAddr, long dstAddr, long len) {
		Unsafe unsafe = E_getUnsafe(); while(len > 0) {
			long size = Math.min(len, UNSAFE_COPY_THRESHOLD);
			unsafe.copyMemory(srcAddr, dstAddr, size);
			len -= size; srcAddr += size; dstAddr += size;
		}
	}

	public static long toAddress(Object obj) {
		Unsafe unsafe = E_getUnsafe();
		Object[] array = ArrayUtils.getTempObjectArray(1); array[0] = obj;
		return normalize(unsafe.getInt(array, Unsafe.ARRAY_OBJECT_BASE_OFFSET));
	}
	public static Object fromAddress(long address) {
		if(address == NULLPTR) return null;
		Unsafe unsafe = E_getUnsafe();
		Object[] array = ArrayUtils.getTempObjectArray(1); array[0] = null;
		unsafe.putLong(array, Unsafe.ARRAY_OBJECT_BASE_OFFSET, address);
		return array[0];
	}

	public static void copyMemory(Object src, long srcOff, Object dest, long destOff, long len) {
		ArrayUtils.assertCopyIndex(srcOff, src != null ? sizeOf(src) : Long.MAX_VALUE, destOff, dest != null ? sizeOf(dest) : Long.MAX_VALUE, len);
		if(SystemUtils.JAVA_DETECTION_VERSION <= 8) { copyMemoryWithSafePointPolling(src, srcOff, dest, destOff, len); return; }
		E_getUnsafe().copyMemory(src, srcOff, dest, destOff, len);
	}
	public static void copyMemoryWithSafePointPolling(Object src, long srcOff, Object dest, long destOff, long len) {
		Unsafe unsafe = E_getUnsafe(); while(len > 0) {
			long size = Math.min(len, UNSAFE_COPY_THRESHOLD);
			unsafe.copyMemory(src, srcOff, dest, destOff, len);
			len -= size; srcOff += size; destOff += size;
		}
	}
	public static void copyMemory(Object src, Object dest, long len) { copyMemory(src, 0, dest, 0, len); }
	public static void copyMemory(Object src, Object dest) { copyMemory(src, 0, dest, 0, sizeOf(src)); }
	public static Object copyMemory(Object src) throws InstantiationException { Object dest = allocateInstance(src.getClass()); copyMemory(src, 0, dest, 0, sizeOf(src)); return dest; }
	public static void copyMemory(long srcAddress, Object dest, long destOffset, long len) { copyMemory(null, srcAddress, dest, destOffset, len); }
	public static void copyMemory(long srcAddress, Object dest, long destOffset) { copyMemory(srcAddress, dest, destOffset, sizeOf(dest) - destOffset); }
	public static void copyMemory(long srcAddress, Object dest) { copyMemory(srcAddress, dest, 0); }
	public static void copyMemory(Object src, long srcOffset, long destAddress, long len) { copyMemory(src, srcOffset, null, destAddress, len); }
	public static void copyMemory(Object src, long srcOffset, long destAddress) { copyMemory(src, srcOffset, destAddress, sizeOf(src) - srcOffset); }
	public static void copyMemory(Object src, long destAddress) { copyMemory(src, 0, destAddress); }

	public static void copyMemoryFieldByField(long srcAddress, long destAddress, Class clazz) throws InstantiationException {
		Unsafe unsafe = E_getUnsafe(); while(clazz != Object.class) { for(Field field : clazz.getDeclaredFields()) {
			if((field.getModifiers() & Modifier.STATIC) != 0) continue; Class type = field.getType();
			long offset = unsafe.objectFieldOffset(field);
			if(!type.isPrimitive()) {
				Object object = allocateInstance(type);
				long objectAddress = toAddress(object);
				copyMemoryFieldByField(unsafe.getLong(srcAddress + offset), objectAddress, type);
				unsafe.putLong(destAddress + offset, objectAddress); continue; }
			if(type == int.class) unsafe.putInt(destAddress + offset, unsafe.getInt(srcAddress + offset, offset));
			else if(type == long.class) unsafe.putLong(destAddress + offset, unsafe.getLong(srcAddress + offset, offset));
			else if(type == short.class) unsafe.putShort(destAddress + offset, unsafe.getShort(srcAddress + offset, offset));
			else if(type == float.class) unsafe.putFloat(destAddress + offset, unsafe.getFloat(srcAddress + offset, offset));
			else if(type == double.class) unsafe.putDouble(destAddress + offset, unsafe.getDouble(srcAddress + offset, offset));
			else if(type == char.class) unsafe.putChar(destAddress + offset, unsafe.getChar(srcAddress + offset, offset));
			else if(type == byte.class) unsafe.putByte(destAddress + offset, unsafe.getByte(srcAddress + offset, offset));
			else throw new IllegalStateException("Illegal state type: " + type);
		} clazz = clazz.getSuperclass(); }
	}
	public static Object copyMemoryFieldByField(Object src) throws InstantiationException { Object dest = allocateInstance(src.getClass()); copyMemoryFieldByField(toAddress(src), toAddress(dest), src.getClass()); return dest; }
	public static void copyMemoryFieldByField(long srcAddress, Object dest) throws InstantiationException { copyMemoryFieldByField(srcAddress, toAddress(dest), dest.getClass()); }
	public static void copyMemoryFieldByField(Object src, long destAddress) throws InstantiationException { copyMemoryFieldByField(toAddress(src), destAddress, src.getClass()); }

	public static byte[] toByteArray(long address, byte[] result) { copyMemory(address, result, Unsafe.ARRAY_BYTE_BASE_OFFSET, result.length); return result; }
	public static byte[] toByteArray(long address, long len) { return toByteArray(address, new byte[(int) len]); }
	public static byte[] toByteArray(Object object, byte[] result) { return toByteArray(toAddress(object), result); }
	public static byte[] toByteArray(Object object) { return toByteArray(object, new byte[(int) sizeOf(object)]); }
	public static long fromByteArray(byte[] array, long len) { long address = E_getUnsafe().allocateMemory(len); copyMemory(array, Unsafe.ARRAY_BYTE_BASE_OFFSET, address, len); return address; }
	public static long fromByteArray(byte[] array) { return fromByteArray(array, array.length); }
	public static Object objectFromByteArray(byte[] array, long len) { return fromAddress(fromByteArray(array, len)); }
	public static Object objectFromByteArray(byte[] array) { return objectFromByteArray(array, array.length); }

	public static long jvm7_32_sizeOf(Object object) { Unsafe unsafe = E_getUnsafe(); return unsafe.getAddress(normalize(unsafe.getInt(object, 4L)) + 12L); }
	public static long headerSize(Object obj) { return headerSize(obj.getClass()); }
	public static long headerSize(Class clazz) { long len = 12; if(clazz.isArray()) len += 4; return len; }
	public static long firstFieldOffset(Object obj) { return firstFieldOffset(obj.getClass()); }
	public static long firstFieldOffset(Class clazz) {
		long minSize = roundUpTo8(headerSize(clazz));
		Unsafe unsafe = E_getUnsafe();
		while(clazz != Object.class) { for(Field f : clazz.getDeclaredFields()) {
			if((f.getModifiers() & Modifier.STATIC) != 0) continue;
			long offset = unsafe.objectFieldOffset(f);
			if(offset < minSize) minSize = offset;
		} clazz = clazz.getSuperclass(); } return minSize;
	}
	public static long sizeOf(Object obj) {
		Class clazz = obj.getClass(); long len = sizeOf(clazz);
		if(!clazz.isArray()) return len; int length = Array.getLength(obj);
		if(obj instanceof long[] || obj instanceof double[]) len += 8 * length;
		else if(obj instanceof int[] || obj instanceof float[]) len += 4 * length;
		else if(obj instanceof char[] || obj instanceof short[]) len += 2 * length;
		else if(obj instanceof boolean[] || obj instanceof byte[]) len += length;
		else len += 8 * length; return len;
	}
	public static long sizeOf(Class clazz) {
		long maxSize = headerSize(clazz);
		Unsafe unsafe = E_getUnsafe();
		while(clazz != Object.class) { for(Field f : clazz.getDeclaredFields()) {
			if((f.getModifiers() & Modifier.STATIC) != 0) continue;
			long offset = unsafe.objectFieldOffset(f);
			if(offset > maxSize) maxSize = offset + 1;
		} clazz = clazz.getSuperclass(); }
		return roundUpTo8(maxSize);
	}
	public static long sizeOfFields(Object obj) { return sizeOfFields(obj.getClass()); }
	public static long sizeOfFields(Class clazz) { return sizeOf(clazz) - firstFieldOffset(clazz); }

	static boolean __is32Bit() { return R_getUnsafe().addressSize() == 4; }
	static boolean __is64Bit() { return R_getUnsafe().addressSize() == 8; }
	static <T> T __allocateInstance(Class<? extends T> clazz) throws InstantiationException {
		return (T) R_getUnsafe().allocateInstance(clazz);
	}

	static void __copyMemory(long srcAddress, long dstAddress, long len) {
		if(SystemUtils.JAVA_DETECTION_VERSION <= 8) { __copyMemoryWithSafePointPolling(srcAddress, dstAddress, len); return; }
		R_getUnsafe().copyMemory(srcAddress, dstAddress, len);
	}
	static void __copyMemoryWithSafePointPolling(long srcAddr, long dstAddr, long len) {
		Unsafe unsafe = R_getUnsafe(); while(len > 0) {
			long size = Math.min(len, UNSAFE_COPY_THRESHOLD);
			unsafe.copyMemory(srcAddr, dstAddr, size);
			len -= size; srcAddr += size; dstAddr += size;
		}
	}

	static long __toAddress(Object obj) {
		Unsafe unsafe = R_getUnsafe();
		Object[] array = ArrayUtils.getTempObjectArray(1); array[0] = obj;
		return normalize(unsafe.getInt(array, Unsafe.ARRAY_OBJECT_BASE_OFFSET));
	}
	static Object __fromAddress(long address) {
		if(address == NULLPTR) return null;
		Unsafe unsafe = R_getUnsafe();
		Object[] array = ArrayUtils.getTempObjectArray(1); array[0] = null;
		unsafe.putLong(array, Unsafe.ARRAY_OBJECT_BASE_OFFSET, address);
		return array[0];
	}

	static void __copyMemory(Object src, long srcOff, Object dest, long destOff, long len) {
		ArrayUtils.assertCopyIndex(srcOff, src != null ? __sizeOf(src) : Long.MAX_VALUE, destOff, dest != null ? __sizeOf(dest) : Long.MAX_VALUE, len);
		if(SystemUtils.JAVA_DETECTION_VERSION <= 8) { __copyMemoryWithSafePointPolling(src, srcOff, dest, destOff, len); return; }
		R_getUnsafe().copyMemory(src, srcOff, dest, destOff, len);
	}
	static void __copyMemoryWithSafePointPolling(Object src, long srcOff, Object dest, long destOff, long len) {
		Unsafe unsafe = R_getUnsafe(); while(len > 0) {
			long size = Math.min(len, UNSAFE_COPY_THRESHOLD);
			unsafe.copyMemory(src, srcOff, dest, destOff, len);
			len -= size; srcOff += size; destOff += size;
		}
	}
	static void __copyMemory(Object src, Object dest, long len) { __copyMemory(src, 0, dest, 0, len); }
	static void __copyMemory(Object src, Object dest) { __copyMemory(src, 0, dest, 0, __sizeOf(src)); }
	static Object __copyMemory(Object src) throws InstantiationException { Object dest = __allocateInstance(src.getClass()); __copyMemory(src, 0, dest, 0, __sizeOf(src)); return dest; }
	static void __copyMemory(long srcAddress, Object dest, long destOffset, long len) { __copyMemory(null, srcAddress, dest, destOffset, len); }
	static void __copyMemory(long srcAddress, Object dest, long destOffset) { __copyMemory(srcAddress, dest, destOffset, __sizeOf(dest) - destOffset); }
	static void __copyMemory(long srcAddress, Object dest) { __copyMemory(srcAddress, dest, 0); }
	static void __copyMemory(Object src, long srcOffset, long destAddress, long len) { __copyMemory(src, srcOffset, null, destAddress, len); }
	static void __copyMemory(Object src, long srcOffset, long destAddress) { __copyMemory(src, srcOffset, destAddress, __sizeOf(src) - srcOffset); }
	static void __copyMemory(Object src, long destAddress) { __copyMemory(src, 0, destAddress); }

	static void __copyMemoryFieldByField(long srcAddress, long destAddress, Class clazz) throws InstantiationException {
		Unsafe unsafe = R_getUnsafe(); while(clazz != Object.class) { for(Field field : clazz.getDeclaredFields()) {
			if((field.getModifiers() & Modifier.STATIC) != 0) continue; Class type = field.getType();
			long offset = unsafe.objectFieldOffset(field);
			if(!type.isPrimitive()) {
				Object object = __allocateInstance(type);
				long objectAddress = __toAddress(object);
				__copyMemoryFieldByField(unsafe.getLong(srcAddress + offset), objectAddress, type);
				unsafe.putLong(destAddress + offset, objectAddress); continue; }
			if(type == int.class) unsafe.putInt(destAddress + offset, unsafe.getInt(srcAddress + offset, offset));
			else if(type == long.class) unsafe.putLong(destAddress + offset, unsafe.getLong(srcAddress + offset, offset));
			else if(type == short.class) unsafe.putShort(destAddress + offset, unsafe.getShort(srcAddress + offset, offset));
			else if(type == float.class) unsafe.putFloat(destAddress + offset, unsafe.getFloat(srcAddress + offset, offset));
			else if(type == double.class) unsafe.putDouble(destAddress + offset, unsafe.getDouble(srcAddress + offset, offset));
			else if(type == char.class) unsafe.putChar(destAddress + offset, unsafe.getChar(srcAddress + offset, offset));
			else if(type == byte.class) unsafe.putByte(destAddress + offset, unsafe.getByte(srcAddress + offset, offset));
			else throw new IllegalStateException("Illegal state type: " + type);
		} clazz = clazz.getSuperclass(); }
	}
	static Object __copyMemoryFieldByField(Object src) throws InstantiationException { Object dest = __allocateInstance(src.getClass()); __copyMemoryFieldByField(__toAddress(src), __toAddress(dest), src.getClass()); return dest; }
	static void __copyMemoryFieldByField(long srcAddress, Object dest) throws InstantiationException { __copyMemoryFieldByField(srcAddress, __toAddress(dest), dest.getClass()); }
	static void __copyMemoryFieldByField(Object src, long destAddress) throws InstantiationException { __copyMemoryFieldByField(__toAddress(src), destAddress, src.getClass()); }

	static byte[] __toByteArray(long address, byte[] result) { __copyMemory(address, result, result.length); return result; }
	static byte[] __toByteArray(long address, long len) { return __toByteArray(address, new byte[(int) len]); }
	static byte[] __toByteArray(Object object, byte[] result) { return __toByteArray(__toAddress(object), result); }
	static byte[] __toByteArray(Object object) { return __toByteArray(object, new byte[(int) __sizeOf(object)]); }
	static long __fromByteArray(byte[] array, long len) { long address = R_getUnsafe().allocateMemory(len); __copyMemory(array, Unsafe.ARRAY_BYTE_BASE_OFFSET, address, len); return address; }
	static long __fromByteArray(byte[] array) { return __fromByteArray(array, array.length); }
	static Object __objectFromByteArray(byte[] array, long len) { return __fromAddress(__fromByteArray(array, len)); }
	static Object __objectFromByteArray(byte[] array) { return __objectFromByteArray(array, array.length); }

	static long __jvm7_32___sizeOf(Object object) { Unsafe unsafe = R_getUnsafe(); return unsafe.getAddress(normalize(unsafe.getInt(object, 4L)) + 12L); }
	static long __headerSize(Object obj) { return __headerSize(obj.getClass()); }
	static long __headerSize(Class clazz) { long len = 12; if(clazz.isArray()) len += 4; return len; }
	static long __firstFieldOffset(Object obj) { return __firstFieldOffset(obj.getClass()); }
	static long __firstFieldOffset(Class clazz) {
		long minSize = roundUpTo8(__headerSize(clazz));
		Unsafe unsafe = R_getUnsafe();
		while(clazz != Object.class) { for(Field f : clazz.getDeclaredFields()) {
			if((f.getModifiers() & Modifier.STATIC) != 0) continue;
			long offset = unsafe.objectFieldOffset(f);
			if(offset < minSize) minSize = offset;
		} clazz = clazz.getSuperclass(); } return minSize;
	}
	static long __sizeOf(Object obj) {
		Class clazz = obj.getClass(); long len = __sizeOf(clazz);
		if(!clazz.isArray()) return len; int length = Array.getLength(obj);
		if(obj instanceof long[] || obj instanceof double[]) len += 8 * length;
		else if(obj instanceof int[] || obj instanceof float[]) len += 4 * length;
		else if(obj instanceof char[] || obj instanceof short[]) len += 2 * length;
		else if(obj instanceof boolean[] || obj instanceof byte[]) len += length;
		else len += 8 * length; return len;
	}
	static long __sizeOf(Class clazz) {
		long maxSize = __headerSize(clazz);
		Unsafe unsafe = R_getUnsafe();
		while(clazz != Object.class) { for(Field f : clazz.getDeclaredFields()) {
			if((f.getModifiers() & Modifier.STATIC) != 0) continue;
			long offset = unsafe.objectFieldOffset(f);
			if(offset > maxSize) maxSize = offset + 1;
		} clazz = clazz.getSuperclass(); }
		return roundUpTo8(maxSize);
	}
	static long __sizeOfFields(Object obj) { return __sizeOfFields(obj.getClass()); }
	static long __sizeOfFields(Class clazz) { return __sizeOf(clazz) - __firstFieldOffset(clazz); }

	private static long roundUpTo8(long value) { return ((value + 7) / 8) * 8; }
	private static long normalize(int value) { if(value >= 0) return value; return (~0L >>> 32) & value; }

	public interface Unsafe {
		int getInt(Object o, long offset);
		void putInt(Object o, long offset, int x);
		Object getObject(Object o, long offset);
		void putObject(Object o, long offset, Object x);
		boolean getBoolean(Object o, long offset);
		void putBoolean(Object o, long offset, boolean x);
		byte getByte(Object o, long offset);
		void putByte(Object o, long offset, byte x);
		short getShort(Object o, long offset);
		void putShort(Object o, long offset, short x);
		char getChar(Object o, long offset);
		void putChar(Object o, long offset, char x);
		long getLong(Object o, long offset);
		void putLong(Object o, long offset, long x);
		float getFloat(Object o, long offset);
		void putFloat(Object o, long offset, float x);
		double getDouble(Object o, long offset);
		void putDouble(Object o, long offset, double x);
		byte getByte(long address);
		void putByte(long address, byte x);
		short getShort(long address);
		void putShort(long address, short x);
		char getChar(long address);
		void putChar(long address, char x);
		int getInt(long address);
		void putInt(long address, int x);
		long getLong(long address);
		void putLong(long address, long x);
		float getFloat(long address);
		void putFloat(long address, float x);
		double getDouble(long address);
		void putDouble(long address, double x);
		long getAddress(long address);
		void putAddress(long address, long x);
		long allocateMemory(long bytes);
		long reallocateMemory(long address, long bytes);
		void setMemory(Object o, long offset, long bytes, byte value);
		void setMemory(long address, long bytes, byte value);
		void copyMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long bytes);
		void copyMemory(long srcAddress, long destAddress, long bytes);
		void freeMemory(long address);
		long objectFieldOffset(Field f);
		long staticFieldOffset(Field f);
		Object staticFieldBase(Field f);
		boolean shouldBeInitialized(Class<?> c);
		void ensureClassInitialized(Class<?> c);
		int arrayBaseOffset(Class<?> arrayClass);
		int arrayIndexScale(Class<?> arrayClass);
		int addressSize();
		int pageSize();
		Class<?> defineAnonymousClass(Class<?> hostClass, byte[] data, Object[] cpPatches);
		Object allocateInstance(Class<?> cls) throws InstantiationException;
		void throwException(Throwable ee);
		boolean compareAndSwapObject(Object o, long offset, Object expected, Object x);
		boolean compareAndSwapInt(Object o, long offset, int expected, int x);
		boolean compareAndSwapLong(Object o, long offset, long expected, long x);
		Object getObjectVolatile(Object o, long offset);
		void putObjectVolatile(Object o, long offset, Object x);
		int getIntVolatile(Object o, long offset);
		void putIntVolatile(Object o, long offset, int x);
		boolean getBooleanVolatile(Object o, long offset);
		void putBooleanVolatile(Object o, long offset, boolean x);
		byte getByteVolatile(Object o, long offset);
		void putByteVolatile(Object o, long offset, byte x);
		short getShortVolatile(Object o, long offset);
		void putShortVolatile(Object o, long offset, short x);
		char getCharVolatile(Object o, long offset);
		void putCharVolatile(Object o, long offset, char x);
		long getLongVolatile(Object o, long offset);
		void putLongVolatile(Object o, long offset, long x);
		float getFloatVolatile(Object o, long offset);
		void putFloatVolatile(Object o, long offset, float x);
		double getDoubleVolatile(Object o, long offset);
		void putDoubleVolatile(Object o, long offset, double x);
		void putOrderedObject(Object o, long offset, Object x);
		void putOrderedInt(Object o, long offset, int x);
		void putOrderedLong(Object o, long offset, long x);
		void unpark(Object thread);
		void park(boolean isAbsolute, long time);
		int getLoadAverage(double[] loadavg, int nelems);
		int getAndAddInt(Object o, long offset, int delta);
		long getAndAddLong(Object o, long offset, long delta);
		int getAndSetInt(Object o, long offset, int newValue);
		long getAndSetLong(Object o, long offset, long newValue);
		Object getAndSetObject(Object o, long offset, Object newValue);
		void loadFence();
		void storeFence();
		void fullFence();
		void invokeCleaner(java.nio.ByteBuffer directBuffer);

		static int INVALID_FIELD_OFFSET = sun.misc.Unsafe.INVALID_FIELD_OFFSET;
		static int ARRAY_BOOLEAN_BASE_OFFSET = sun.misc.Unsafe.ARRAY_BOOLEAN_BASE_OFFSET;
		static int ARRAY_BYTE_BASE_OFFSET = sun.misc.Unsafe.ARRAY_BYTE_BASE_OFFSET;
		static int ARRAY_SHORT_BASE_OFFSET = sun.misc.Unsafe.ARRAY_SHORT_BASE_OFFSET;
		static int ARRAY_CHAR_BASE_OFFSET = sun.misc.Unsafe.ARRAY_CHAR_BASE_OFFSET;
		static int ARRAY_INT_BASE_OFFSET = sun.misc.Unsafe.ARRAY_INT_BASE_OFFSET;
		static int ARRAY_LONG_BASE_OFFSET = sun.misc.Unsafe.ARRAY_LONG_BASE_OFFSET;
		static int ARRAY_FLOAT_BASE_OFFSET = sun.misc.Unsafe.ARRAY_FLOAT_BASE_OFFSET;
		static int ARRAY_DOUBLE_BASE_OFFSET = sun.misc.Unsafe.ARRAY_DOUBLE_BASE_OFFSET;
		static int ARRAY_OBJECT_BASE_OFFSET = sun.misc.Unsafe.ARRAY_OBJECT_BASE_OFFSET;
		static int ARRAY_BOOLEAN_INDEX_SCALE = sun.misc.Unsafe.ARRAY_BOOLEAN_INDEX_SCALE;
		static int ARRAY_BYTE_INDEX_SCALE = sun.misc.Unsafe.ARRAY_BYTE_INDEX_SCALE;
		static int ARRAY_SHORT_INDEX_SCALE = sun.misc.Unsafe.ARRAY_SHORT_INDEX_SCALE;
		static int ARRAY_CHAR_INDEX_SCALE = sun.misc.Unsafe.ARRAY_CHAR_INDEX_SCALE;
		static int ARRAY_INT_INDEX_SCALE = sun.misc.Unsafe.ARRAY_INT_INDEX_SCALE;
		static int ARRAY_LONG_INDEX_SCALE = sun.misc.Unsafe.ARRAY_LONG_INDEX_SCALE;
		static int ARRAY_FLOAT_INDEX_SCALE = sun.misc.Unsafe.ARRAY_FLOAT_INDEX_SCALE;
		static int ARRAY_DOUBLE_INDEX_SCALE = sun.misc.Unsafe.ARRAY_DOUBLE_INDEX_SCALE;
		static int ARRAY_OBJECT_INDEX_SCALE = sun.misc.Unsafe.ARRAY_OBJECT_INDEX_SCALE;
		static int ADDRESS_SIZE = sun.misc.Unsafe.ADDRESS_SIZE;
	}
	public static class UnsafeImpl implements Unsafe {
		protected final sun.misc.Unsafe unsafe;

		public UnsafeImpl(sun.misc.Unsafe unsafe) { this.unsafe = unsafe; }
		public UnsafeImpl() { this(null); }

		public int getInt(Object o, long offset) { return unsafe.getInt(o, offset); }
		public void putInt(Object o, long offset, int x) { unsafe.putInt(o, offset, x); }
		public Object getObject(Object o, long offset) { return unsafe.getObject(o, offset); }
		public void putObject(Object o, long offset, Object x) { unsafe.putObject(o, offset, x); }
		public boolean getBoolean(Object o, long offset) { return unsafe.getBoolean(o, offset); }
		public void putBoolean(Object o, long offset, boolean x) { unsafe.putBoolean(o, offset, x); }
		public byte getByte(Object o, long offset) { return unsafe.getByte(o, offset); }
		public void putByte(Object o, long offset, byte x) { unsafe.putByte(o, offset, x); }
		public short getShort(Object o, long offset) { return unsafe.getShort(o, offset); }
		public void putShort(Object o, long offset, short x) { unsafe.putShort(o, offset, x); }
		public char getChar(Object o, long offset) { return unsafe.getChar(o, offset); }
		public void putChar(Object o, long offset, char x) { unsafe.putChar(o, offset, x); }
		public long getLong(Object o, long offset) { return unsafe.getLong(o, offset); }
		public void putLong(Object o, long offset, long x) { unsafe.putLong(o, offset, x); }
		public float getFloat(Object o, long offset) { return unsafe.getFloat(o, offset); }
		public void putFloat(Object o, long offset, float x) { unsafe.putFloat(o, offset, x); }
		public double getDouble(Object o, long offset) { return unsafe.getDouble(o, offset); }
		public void putDouble(Object o, long offset, double x) { unsafe.putDouble(o, offset, x); }
		public byte getByte(long address) { return unsafe.getByte(address); }
		public void putByte(long address, byte x) { unsafe.putByte(address, x); }
		public short getShort(long address) { return unsafe.getShort(address); }
		public void putShort(long address, short x) { unsafe.putShort(address, x); }
		public char getChar(long address) { return unsafe.getChar(address); }
		public void putChar(long address, char x) { unsafe.putChar(address, x); }
		public int getInt(long address) { return unsafe.getInt(address); }
		public void putInt(long address, int x) { unsafe.putInt(address, x); }
		public long getLong(long address) { return unsafe.getLong(address); }
		public void putLong(long address, long x) { unsafe.putLong(address, x); }
		public float getFloat(long address) { return unsafe.getFloat(address); }
		public void putFloat(long address, float x) { unsafe.putFloat(address, x); }
		public double getDouble(long address) { return unsafe.getDouble(address); }
		public void putDouble(long address, double x) { unsafe.putDouble(address, x); }
		public long getAddress(long address) { return unsafe.getAddress(address); }
		public void putAddress(long address, long x) { unsafe.putAddress(address, x); }
		public long allocateMemory(long bytes) { return unsafe.allocateMemory(bytes); }
		public long reallocateMemory(long address, long bytes) { return unsafe.reallocateMemory(address, bytes); }
		public void setMemory(Object o, long offset, long bytes, byte value) { unsafe.setMemory(o, offset, bytes, value); }
		public void setMemory(long address, long bytes, byte value) { unsafe.setMemory(address, bytes, value); }
		public void copyMemory(Object srcBase, long srcOffset, Object destBase, long destOffset, long bytes) { unsafe.copyMemory(srcBase, srcOffset, destBase, destOffset, bytes); }
		public void copyMemory(long srcAddress, long destAddress, long bytes) { unsafe.copyMemory(srcAddress, destAddress, bytes); }
		public void freeMemory(long address) { unsafe.freeMemory(address); }
		public long objectFieldOffset(Field f) { return unsafe.objectFieldOffset(f); }
		public long staticFieldOffset(Field f) { return unsafe.staticFieldOffset(f); }
		public Object staticFieldBase(Field f) { return unsafe.staticFieldBase(f); }
		public boolean shouldBeInitialized(Class<?> c) { return unsafe.shouldBeInitialized(c); }
		public void ensureClassInitialized(Class<?> c) { unsafe.ensureClassInitialized(c); }
		public int arrayBaseOffset(Class<?> arrayClass) { return unsafe.arrayBaseOffset(arrayClass); }
		public int arrayIndexScale(Class<?> arrayClass) { return unsafe.arrayIndexScale(arrayClass); }
		public int addressSize() { return unsafe.addressSize(); }
		public int pageSize() { return unsafe.pageSize(); }
		public Class<?> defineAnonymousClass(Class<?> hostClass, byte[] data, Object[] cpPatches) { return unsafe.defineAnonymousClass(hostClass, data, cpPatches); }
		public Object allocateInstance(Class<?> cls) throws InstantiationException { return unsafe.allocateInstance(cls); }
		public void throwException(Throwable ee) { unsafe.throwException(ee); }
		public boolean compareAndSwapObject(Object o, long offset, Object expected, Object x) { return unsafe.compareAndSwapObject(o, offset, expected, x); }
		public boolean compareAndSwapInt(Object o, long offset, int expected, int x) { return unsafe.compareAndSwapInt(o, offset, expected, x); }
		public boolean compareAndSwapLong(Object o, long offset, long expected, long x) { return unsafe.compareAndSwapLong(o, offset, expected, x); }
		public Object getObjectVolatile(Object o, long offset) { return unsafe.getObjectVolatile(o, offset); }
		public void putObjectVolatile(Object o, long offset, Object x) { unsafe.putObjectVolatile(o, offset, x); }
		public int getIntVolatile(Object o, long offset) { return unsafe.getIntVolatile(o, offset); }
		public void putIntVolatile(Object o, long offset, int x) { unsafe.putIntVolatile(o, offset, x); }
		public boolean getBooleanVolatile(Object o, long offset) { return unsafe.getBooleanVolatile(o, offset); }
		public void putBooleanVolatile(Object o, long offset, boolean x) { unsafe.putBooleanVolatile(o, offset, x); }
		public byte getByteVolatile(Object o, long offset) { return unsafe.getByteVolatile(o, offset); }
		public void putByteVolatile(Object o, long offset, byte x) { unsafe.putByteVolatile(o, offset, x); }
		public short getShortVolatile(Object o, long offset) { return unsafe.getShortVolatile(o, offset); }
		public void putShortVolatile(Object o, long offset, short x) { unsafe.putShortVolatile(o, offset, x); }
		public char getCharVolatile(Object o, long offset) { return unsafe.getCharVolatile(o, offset); }
		public void putCharVolatile(Object o, long offset, char x) { unsafe.putCharVolatile(o, offset, x); }
		public long getLongVolatile(Object o, long offset) { return unsafe.getLongVolatile(o, offset); }
		public void putLongVolatile(Object o, long offset, long x) { unsafe.putLongVolatile(o, offset, x); }
		public float getFloatVolatile(Object o, long offset) { return unsafe.getFloatVolatile(o, offset); }
		public void putFloatVolatile(Object o, long offset, float x) { unsafe.putFloatVolatile(o, offset, x); }
		public double getDoubleVolatile(Object o, long offset) { return unsafe.getDoubleVolatile(o, offset); }
		public void putDoubleVolatile(Object o, long offset, double x) { unsafe.putDoubleVolatile(o, offset, x); }
		public void putOrderedObject(Object o, long offset, Object x) { unsafe.putOrderedObject(o, offset, x); }
		public void putOrderedInt(Object o, long offset, int x) { unsafe.putOrderedInt(o, offset, x); }
		public void putOrderedLong(Object o, long offset, long x) { unsafe.putOrderedLong(o, offset, x); }
		public void unpark(Object thread) { unsafe.unpark(thread); }
		public void park(boolean isAbsolute, long time) { unsafe.park(isAbsolute, time); }
		public int getLoadAverage(double[] loadavg, int nelems) { return unsafe.getLoadAverage(loadavg, nelems); }
		public int getAndAddInt(Object o, long offset, int delta) { return unsafe.getAndAddInt(o, offset, delta); }
		public long getAndAddLong(Object o, long offset, long delta) { return unsafe.getAndAddLong(o, offset, delta); }
		public int getAndSetInt(Object o, long offset, int newValue) { return unsafe.getAndSetInt(o, offset, newValue); }
		public long getAndSetLong(Object o, long offset, long newValue) { return unsafe.getAndSetLong(o, offset, newValue); }
		public Object getAndSetObject(Object o, long offset, Object newValue) { return unsafe.getAndSetObject(o, offset, newValue); }
		public void loadFence() { unsafe.loadFence(); }
		public void storeFence() { unsafe.storeFence(); }
		public void fullFence() { unsafe.fullFence(); }
		public void invokeCleaner(java.nio.ByteBuffer directBuffer) { throw new UnsupportedOperationException("Not yet implemented for java 8"); }
	}

	protected interface FUnsafeUtilsImplementation extends FutureJavaUtils.FutureJavaImplementation {
		Unsafe getUnsafeImplementation() throws Exception;
	}
}
