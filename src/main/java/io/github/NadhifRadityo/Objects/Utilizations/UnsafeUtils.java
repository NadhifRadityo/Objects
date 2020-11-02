package io.github.NadhifRadityo.Objects.Utilizations;

import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class UnsafeUtils {

	public static final long NULLPTR = 0L;
	protected static final boolean HOLD_UNSAFE = Boolean.parseBoolean(System.getProperty("unsafeutils.holdunsafe", "true"));
	protected static final boolean PRIVILEGED_ACCESS = Boolean.parseBoolean(System.getProperty("unsafeutils.privilegedaccess", "true"));
	protected static final long UNSAFE_COPY_THRESHOLD = 1024L * 1024L;
	protected static final long COPY_STRIDE = 8;
	protected static Unsafe unsafe;

	static Error noAccessException() { throw new IllegalAccessError("No access exception"); }
	static Unsafe R_getUnsafe() { if(HOLD_UNSAFE && unsafe != null) return unsafe; try {
		Field field = Unsafe.class.getDeclaredField("theUnsafe");
		field.setAccessible(true); Unsafe _unsafe = (Unsafe) field.get(null);
		if(HOLD_UNSAFE) unsafe = _unsafe; return _unsafe;
	} catch(Exception e) { throw new Error(e); } }
	public static Unsafe getUnsafe() { return !PRIVILEGED_ACCESS || PrivilegedUtils.isRunningOnPrivileged() ? R_getUnsafe() : null; }
	public static Unsafe E_getUnsafe() { Unsafe unsafe = getUnsafe(); if(unsafe == null) throw noAccessException(); return unsafe; }

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
}
