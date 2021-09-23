package io.github.NadhifRadityo.Objects.$Utilizations;

import io.github.NadhifRadityo.Objects.$Interface.Functional.ReferencedCallback;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EclipseCollectionsUtils {
	protected static final long KEYTYPE_OBJECT = 0;
	protected static final long KEYTYPE_INT = 1;
	protected static final long KEYTYPE_LONG = 2;
	protected static final long KEYTYPE_SHORT = 3;
	protected static final long KEYTYPE_FLOAT = 4;
	protected static final long KEYTYPE_DOUBLE = 5;
	protected static final long KEYTYPE_CHAR = 6;
	protected static final long KEYTYPE_BYTE = 7;

	protected static final int U_AFIELD_Unmodifiable$_map = 0;
	protected static final Map<Class, long[]> $U_AFIELD_Unmodifiable$;
	protected static final int S_AFIELD_Synchronized$_lock = 0;
	protected static final int S_AFIELD_Synchronized$_map = 1;
	protected static final Map<Class, long[]> $S_AFIELD_Synchronized$;
	protected static final int HM_AFIELD_$HashMap_keys = 0;
	protected static final int HM_AFIELD_$HashMap_values = 1;
	protected static final int HM_AFIELD_$HashMap_occupiedWithData = 2;
	protected static final int HM_AFIELD_$HashMap_occupiedWithSentinels = 3;
	protected static final int HM_AFIELD_$HashMap_sentinelValues = 4;
	protected static final int HM_AFIELD_$HashMap_copyKeysOnWrite = 5;
	protected static final int HM_AFIELD_$HashMap_$KEYTYPE = 6;
	protected static final Map<Class, long[]> $HM_AFIELD_$HashMap;
	protected static final int HMI_AFIELD_$HashMap$Iterator_this$0 = 0;
	protected static final int HMI_AFIELD_$HashMap$Iterator_count = 1;
	protected static final int HMI_AFIELD_$HashMap$Iterator_position = 2;
	protected static final int HMI_AFIELD_$HashMap$Iterator_currentKey = 3;
	protected static final int HMI_AFIELD_$HashMap$Iterator_isCurrentKeySet = 4;
	protected static final int HMI_AFIELD_$HashMap$Iterator_handledZeroKey = 5;
	protected static final int HMI_AFIELD_$HashMap$Iterator_handledOneKey = 6;
	protected static final Map<Class, long[]> $HMI_AFIELD_$HashMap$Iterator;

	protected static final int HM_FIELD_VAL_$HashMap_NULL_KEY = 0;
	protected static final int HM_FIELD_VAL_$HashMap_REMOVED_KEY = 1;
	protected static final Map<Class, Object> $HM_FIELD_VAL_$HashMap;

	static {
		UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		$U_AFIELD_Unmodifiable$ = new HashMap<>();
		$S_AFIELD_Synchronized$ = new HashMap<>();
		$HM_AFIELD_$HashMap = new HashMap<>();
		$HMI_AFIELD_$HashMap$Iterator = new HashMap<>();
		Class[] classes = ClassUtils.getClasses("org.eclipse.collections.impl.map.mutable.primitive", Object.class);
		ReferencedCallback<Field> __ = (args) -> { Class clazz = (Class) args[0]; String name = (String) args[1]; try { return clazz.getDeclaredField(name); } catch(NoSuchFieldException ignored) { return null; } };

		for(Class clazz : classes) {
			if(clazz.getSimpleName().startsWith("Unmodifiable")) {
				ExceptionUtils.doSilentThrowsRunnable(false, () -> {
					Class CLASS_Unmodifiable$ = clazz;
					Field FIELD_Unmodifiable$_map = CLASS_Unmodifiable$.getDeclaredField("map");
					long AFIELD_Unmodifiable$_map = unsafe.objectFieldOffset(FIELD_Unmodifiable$_map);
					long[] _$U_AFIELD_Unmodifiable$ = new long[] {
							AFIELD_Unmodifiable$_map
					};
					$U_AFIELD_Unmodifiable$.put(CLASS_Unmodifiable$, _$U_AFIELD_Unmodifiable$);
				});
			}
			if(clazz.getSimpleName().startsWith("Synchronized")) {
				ExceptionUtils.doSilentThrowsRunnable(false, () -> {
					Class CLASS_Synchronized$ = clazz;
					Field FIELD_Synchronized$_lock = CLASS_Synchronized$.getDeclaredField("lock");
					Field FIELD_Synchronized$_map = CLASS_Synchronized$.getDeclaredField("map");
					long AFIELD_Synchronized$_lock = unsafe.objectFieldOffset(FIELD_Synchronized$_lock);
					long AFIELD_Synchronized$_map = unsafe.objectFieldOffset(FIELD_Synchronized$_map);
					long[] _$S_AFIELD_Synchronized$ = new long[] {
							AFIELD_Synchronized$_lock, AFIELD_Synchronized$_map
					};
					$S_AFIELD_Synchronized$.put(CLASS_Synchronized$, _$S_AFIELD_Synchronized$);
				});
			}
			if(clazz.getSimpleName().endsWith("HashMap")) {
				ExceptionUtils.doSilentThrowsRunnable(false, () -> {
					Class CLASS_$HashMap = clazz;
					Field FIELD_$HashMap_keys = __.get(CLASS_$HashMap, "keys");
					Field FIELD_$HashMap_values = __.get(CLASS_$HashMap, "values");
					Field FIELD_$HashMap_occupiedWithData = __.get(CLASS_$HashMap, "occupiedWithData");
					Field FIELD_$HashMap_occupiedWithSentinels = __.get(CLASS_$HashMap, "occupiedWithSentinels");
					Field FIELD_$HashMap_sentinelValues = __.get(CLASS_$HashMap, "sentinelValues");
					Field FIELD_$HashMap_copyKeysOnWrite = __.get(CLASS_$HashMap, "copyKeysOnWrite");
					long AFIELD_$HashMap_keys = FIELD_$HashMap_keys != null ? unsafe.objectFieldOffset(FIELD_$HashMap_keys) : -1L;
					long AFIELD_$HashMap_values = FIELD_$HashMap_values != null ? unsafe.objectFieldOffset(FIELD_$HashMap_values) : -1L;
					long AFIELD_$HashMap_occupiedWithData = FIELD_$HashMap_occupiedWithData != null ? unsafe.objectFieldOffset(FIELD_$HashMap_occupiedWithData) : -1L;
					long AFIELD_$HashMap_occupiedWithSentinels = FIELD_$HashMap_occupiedWithSentinels != null ? unsafe.objectFieldOffset(FIELD_$HashMap_occupiedWithSentinels) : -1L;
					long AFIELD_$HashMap_sentinelValues = FIELD_$HashMap_sentinelValues != null ? unsafe.objectFieldOffset(FIELD_$HashMap_sentinelValues) : -1L;
					long AFIELD_$HashMap_copyKeysOnWrite = FIELD_$HashMap_copyKeysOnWrite != null ? unsafe.objectFieldOffset(FIELD_$HashMap_copyKeysOnWrite) : -1L;
					long AFIELD_$HashMap_$KEYTYPE = -1; String simpleName = CLASS_$HashMap.getSimpleName();
					if(simpleName.startsWith("Object")) AFIELD_$HashMap_$KEYTYPE = KEYTYPE_OBJECT;
					if(simpleName.startsWith("Int")) AFIELD_$HashMap_$KEYTYPE = KEYTYPE_INT;
					if(simpleName.startsWith("Long")) AFIELD_$HashMap_$KEYTYPE = KEYTYPE_LONG;
					if(simpleName.startsWith("Short")) AFIELD_$HashMap_$KEYTYPE = KEYTYPE_SHORT;
					if(simpleName.startsWith("Float")) AFIELD_$HashMap_$KEYTYPE = KEYTYPE_FLOAT;
					if(simpleName.startsWith("Double")) AFIELD_$HashMap_$KEYTYPE = KEYTYPE_DOUBLE;
					if(simpleName.startsWith("Char")) AFIELD_$HashMap_$KEYTYPE = KEYTYPE_CHAR;
					if(simpleName.startsWith("Byte")) AFIELD_$HashMap_$KEYTYPE = KEYTYPE_BYTE;
					long[] _$HM_AFIELD_$HashMap = new long[] {
							AFIELD_$HashMap_keys, AFIELD_$HashMap_values, AFIELD_$HashMap_occupiedWithData,
							AFIELD_$HashMap_occupiedWithSentinels, AFIELD_$HashMap_sentinelValues, AFIELD_$HashMap_copyKeysOnWrite,
							AFIELD_$HashMap_$KEYTYPE
					};
					$HM_AFIELD_$HashMap.put(CLASS_$HashMap, _$HM_AFIELD_$HashMap);
				});
				ExceptionUtils.doSilentThrowsRunnable(false, () -> {
					for(Class CLASS_$HashMap$Iterator : clazz.getDeclaredClasses()) {
						if(!CLASS_$HashMap$Iterator.getSimpleName().endsWith("Iterator")) continue;
						Field FIELD_$HashMap$Iterator_this$0 = __.get(CLASS_$HashMap$Iterator, "this$0");
						Field FIELD_$HashMap$Iterator_count = __.get(CLASS_$HashMap$Iterator, "count");
						Field FIELD_$HashMap$Iterator_position = __.get(CLASS_$HashMap$Iterator, "position");
						Field FIELD_$HashMap$Iterator_currentKey = __.get(CLASS_$HashMap$Iterator, "currentKey");
						Field FIELD_$HashMap$Iterator_isCurrentKeySet = __.get(CLASS_$HashMap$Iterator, "isCurrentKeySet");
						Field FIELD_$HashMap$Iterator_handledZeroKey = __.get(CLASS_$HashMap$Iterator, "handledZeroKey");
						Field FIELD_$HashMap$Iterator_handledOneKey = __.get(CLASS_$HashMap$Iterator, "handledOneKey");
						long AFIELD_$HashMap$Iterator_this$0 = FIELD_$HashMap$Iterator_this$0 != null ? unsafe.objectFieldOffset(FIELD_$HashMap$Iterator_this$0) : -1L;
						long AFIELD_$HashMap$Iterator_count = FIELD_$HashMap$Iterator_count != null ? unsafe.objectFieldOffset(FIELD_$HashMap$Iterator_count) : -1L;
						long AFIELD_$HashMap$Iterator_position = FIELD_$HashMap$Iterator_position != null ? unsafe.objectFieldOffset(FIELD_$HashMap$Iterator_position) : -1L;
						long AFIELD_$HashMap$Iterator_currentKey = FIELD_$HashMap$Iterator_currentKey != null ? unsafe.objectFieldOffset(FIELD_$HashMap$Iterator_currentKey) : -1L;
						long AFIELD_$HashMap$Iterator_isCurrentKeySet = FIELD_$HashMap$Iterator_isCurrentKeySet != null ? unsafe.objectFieldOffset(FIELD_$HashMap$Iterator_isCurrentKeySet) : -1L;
						long AFIELD_$HashMap$Iterator_handledZeroKey = FIELD_$HashMap$Iterator_handledZeroKey != null ? unsafe.objectFieldOffset(FIELD_$HashMap$Iterator_handledZeroKey) : -1L;
						long AFIELD_$HashMap$Iterator_handledOneKey = FIELD_$HashMap$Iterator_handledOneKey != null ? unsafe.objectFieldOffset(FIELD_$HashMap$Iterator_handledOneKey) : -1L;
						long[] _$HMI_AFIELD_$HashMap$Iterator = new long[] {
								AFIELD_$HashMap$Iterator_this$0,
								AFIELD_$HashMap$Iterator_count, AFIELD_$HashMap$Iterator_position, AFIELD_$HashMap$Iterator_currentKey,
								AFIELD_$HashMap$Iterator_isCurrentKeySet, AFIELD_$HashMap$Iterator_handledZeroKey, AFIELD_$HashMap$Iterator_handledOneKey
						};
						$HMI_AFIELD_$HashMap$Iterator.put(CLASS_$HashMap$Iterator, _$HMI_AFIELD_$HashMap$Iterator);
					}
				});
			}
		}

		$HM_FIELD_VAL_$HashMap = new HashMap<>();
		for(Class clazz : classes) {
			if(clazz.getSimpleName().endsWith("HashMap")) {
				ExceptionUtils.doSilentThrowsRunnable(false, () -> {
					Class CLASS_$HashMap = clazz;
					Field FIELD_$HashMap_NULL_KEY = CLASS_$HashMap.getDeclaredField("NULL_KEY");
					Field FIELD_$HashMap_REMOVED_KEY = CLASS_$HashMap.getDeclaredField("REMOVED_KEY");
					FIELD_$HashMap_NULL_KEY.setAccessible(true);
					FIELD_$HashMap_REMOVED_KEY.setAccessible(true);
					Object FIELD_VAL_$HashMap_NULL_KEY = FIELD_$HashMap_NULL_KEY.get(null);
					Object FIELD_VAL_$HashMap_REMOVED_KEY = FIELD_$HashMap_REMOVED_KEY.get(null);
					Object[] _$HM_FIELD_VAL_$HashMap = new Object[] {
							FIELD_VAL_$HashMap_NULL_KEY, FIELD_VAL_$HashMap_REMOVED_KEY
					};
					$HM_FIELD_VAL_$HashMap.put(CLASS_$HashMap, _$HM_FIELD_VAL_$HashMap);
				});
			}
		}
	}

	public static long[] $U_AFIELD_Unmodifiable$(Class classMap) { return $U_AFIELD_Unmodifiable$.get(classMap).clone(); }
	public static long[] $S_AFIELD_Synchronized$(Class classMap) { return $S_AFIELD_Synchronized$.get(classMap).clone(); }
	public static long[] $HM_AFIELD_$HashMap(Class classMap) { return $HM_AFIELD_$HashMap.get(classMap).clone(); }
	public static long[] $HMI_AFIELD_$HashMap$Iterator(Class classIterator) { return $HMI_AFIELD_$HashMap$Iterator.get(classIterator).clone(); }
	public static Object $HM_FIELD_VAL_$HashMap(Class classMap) { return ArrayUtils.clone($HM_FIELD_VAL_$HashMap.get(classMap)); }

	public static Object getUnmodifiableSourceMap(Object map, long[] _$U_AFIELD_Unmodifiable$) {
		UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		if(_$U_AFIELD_Unmodifiable$ == null) _$U_AFIELD_Unmodifiable$ = $U_AFIELD_Unmodifiable$.get(map.getClass());
		if(_$U_AFIELD_Unmodifiable$ != null) return unsafe.getObject(map, _$U_AFIELD_Unmodifiable$[U_AFIELD_Unmodifiable$_map]);
		return map;
	} public static Object getUnmodifiableSourceMap(Object map) { return getUnmodifiableSourceMap(map, null); }
	public static Object getSynchronizedSourceMap(Object map, long[] _$S_AFIELD_Synchronized$) {
		UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		if(_$S_AFIELD_Synchronized$ == null) _$S_AFIELD_Synchronized$ = $S_AFIELD_Synchronized$.get(map.getClass());
		if(_$S_AFIELD_Synchronized$ != null) return unsafe.getObject(map, _$S_AFIELD_Synchronized$[S_AFIELD_Synchronized$_map]);
		return map;
	} public static Object getSynchronizedSourceMap(Object map) { return getSynchronizedSourceMap(map, null); }
	public static Object getKeys(Object map, long[] _$HM_AFIELD_$HashMap) {
		if(_$HM_AFIELD_$HashMap == null) _$HM_AFIELD_$HashMap = $HM_AFIELD_$HashMap.get(map.getClass());
		if(_$HM_AFIELD_$HashMap == null) throw new IllegalArgumentException("Object is not HashMap");
		long AFIELD_$HashMap_keys = _$HM_AFIELD_$HashMap[HM_AFIELD_$HashMap_keys];
		if(AFIELD_$HashMap_keys == -1) throw new IllegalArgumentException("Object is not supported");
		return UnsafeUtils.R_getUnsafe().getObject(map, AFIELD_$HashMap_keys);
	} public static Object getKeys(Object map) { return getKeys(map, null); }
	public static Object getValues(Object map, long[] _$HM_AFIELD_$HashMap) {
		if(_$HM_AFIELD_$HashMap == null) _$HM_AFIELD_$HashMap = $HM_AFIELD_$HashMap.get(map.getClass());
		if(_$HM_AFIELD_$HashMap == null) throw new IllegalArgumentException("Object is not HashMap");
		long AFIELD_$HashMap_values = _$HM_AFIELD_$HashMap[HM_AFIELD_$HashMap_values];
		if(AFIELD_$HashMap_values == -1) throw new IllegalArgumentException("Object is not supported");
		return UnsafeUtils.R_getUnsafe().getObject(map, AFIELD_$HashMap_values);
	} public static Object getValues(Object map) { return getValues(map, null); }
	public static void resetHashMapIterator(Object iterator, long[] _$HMI_AFIELD_$HashMap$Iterator, long[] _$HM_AFIELD_$HashMap) {
		if(_$HMI_AFIELD_$HashMap$Iterator == null) _$HMI_AFIELD_$HashMap$Iterator = $HMI_AFIELD_$HashMap$Iterator.get(iterator.getClass());
		if(_$HMI_AFIELD_$HashMap$Iterator == null) throw new IllegalArgumentException("Object is not Iterator");
		UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		long AFIELD_$HashMap$Iterator_this$0 = _$HMI_AFIELD_$HashMap$Iterator[HMI_AFIELD_$HashMap$Iterator_this$0];
		long AFIELD_$HashMap$Iterator_count = _$HMI_AFIELD_$HashMap$Iterator[HMI_AFIELD_$HashMap$Iterator_count];
		long AFIELD_$HashMap$Iterator_position = _$HMI_AFIELD_$HashMap$Iterator[HMI_AFIELD_$HashMap$Iterator_position];
		long AFIELD_$HashMap$Iterator_currentKey = _$HMI_AFIELD_$HashMap$Iterator[HMI_AFIELD_$HashMap$Iterator_currentKey];
		long AFIELD_$HashMap$Iterator_isCurrentKeySet = _$HMI_AFIELD_$HashMap$Iterator[HMI_AFIELD_$HashMap$Iterator_isCurrentKeySet];
		long AFIELD_$HashMap$Iterator_handledZeroKey = _$HMI_AFIELD_$HashMap$Iterator[HMI_AFIELD_$HashMap$Iterator_handledZeroKey];
		long AFIELD_$HashMap$Iterator_handledOneKey = _$HMI_AFIELD_$HashMap$Iterator[HMI_AFIELD_$HashMap$Iterator_handledOneKey];
		if(AFIELD_$HashMap$Iterator_count != -1) unsafe.putInt(iterator, AFIELD_$HashMap$Iterator_count, 0);
		if(AFIELD_$HashMap$Iterator_position != -1) unsafe.putInt(iterator, AFIELD_$HashMap$Iterator_position, 0);
		if(AFIELD_$HashMap$Iterator_isCurrentKeySet != -1) unsafe.putBoolean(iterator, AFIELD_$HashMap$Iterator_isCurrentKeySet, false);
		if(AFIELD_$HashMap$Iterator_handledZeroKey != -1) unsafe.putBoolean(iterator, AFIELD_$HashMap$Iterator_handledZeroKey, false);
		if(AFIELD_$HashMap$Iterator_handledOneKey != -1) unsafe.putBoolean(iterator, AFIELD_$HashMap$Iterator_handledOneKey, false);
		if(AFIELD_$HashMap$Iterator_currentKey != -1) {
			Object map = unsafe.getObject(iterator, AFIELD_$HashMap$Iterator_this$0);
			if(_$HM_AFIELD_$HashMap == null) _$HM_AFIELD_$HashMap = $HM_AFIELD_$HashMap.get(map.getClass());
			if(_$HM_AFIELD_$HashMap == null) return;
			long AFIELD_$HashMap_$KEYTYPE = _$HM_AFIELD_$HashMap[HM_AFIELD_$HashMap_$KEYTYPE];
			if(AFIELD_$HashMap_$KEYTYPE == -1) return;
			if(AFIELD_$HashMap_$KEYTYPE == KEYTYPE_OBJECT) unsafe.putObject(iterator, AFIELD_$HashMap$Iterator_currentKey, null);
			if(AFIELD_$HashMap_$KEYTYPE == KEYTYPE_INT) unsafe.putInt(iterator, AFIELD_$HashMap$Iterator_currentKey, 0);
			if(AFIELD_$HashMap_$KEYTYPE == KEYTYPE_LONG) unsafe.putLong(iterator, AFIELD_$HashMap$Iterator_currentKey, 0);
			if(AFIELD_$HashMap_$KEYTYPE == KEYTYPE_SHORT) unsafe.putShort(iterator, AFIELD_$HashMap$Iterator_currentKey, (short) 0);
			if(AFIELD_$HashMap_$KEYTYPE == KEYTYPE_FLOAT) unsafe.putFloat(iterator, AFIELD_$HashMap$Iterator_currentKey, 0);
			if(AFIELD_$HashMap_$KEYTYPE == KEYTYPE_DOUBLE) unsafe.putDouble(iterator, AFIELD_$HashMap$Iterator_currentKey, 0);
			if(AFIELD_$HashMap_$KEYTYPE == KEYTYPE_CHAR) unsafe.putChar(iterator, AFIELD_$HashMap$Iterator_currentKey, (char) 0);
			if(AFIELD_$HashMap_$KEYTYPE == KEYTYPE_BYTE) unsafe.putByte(iterator, AFIELD_$HashMap$Iterator_currentKey, (byte) 0);
		}
	}
	public static void resetHashMapIterator(Object iterator) { resetHashMapIterator(iterator, null, null); }

	public static Object getSentinelMap_NULL_KEY(Class classMap) {
		Object FIELD_VAL_$HashMap_$HM = $HM_FIELD_VAL_$HashMap.get(classMap);
		if(FIELD_VAL_$HashMap_$HM == null) throw new IllegalArgumentException("Object is not HashMap");
		return Array.get(FIELD_VAL_$HashMap_$HM, HM_FIELD_VAL_$HashMap_NULL_KEY);
	}
	public static Object getSentinelMap_REMOVED_KEY(Class classMap) {
		Object FIELD_VAL_$HashMap_$HM = $HM_FIELD_VAL_$HashMap.get(classMap);
		if(FIELD_VAL_$HashMap_$HM == null) throw new IllegalArgumentException("Object is not HashMap");
		return Array.get(FIELD_VAL_$HashMap_$HM, HM_FIELD_VAL_$HashMap_REMOVED_KEY);
	}
}
