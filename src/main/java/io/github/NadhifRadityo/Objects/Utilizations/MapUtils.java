package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;

@SuppressWarnings("unchecked")
public class MapUtils {
	protected static final Class CLASS_CheckedMap;
	protected static final Class CLASS_CheckedSortedMap;
	protected static final Class CLASS_SynchronizedMap;
	protected static final Class CLASS_SynchronizedSortedMap;
	protected static final Class CLASS_UnmodifiableMap;
	protected static final Class CLASS_UnmodifiableSortedMap;
	protected static final Class CLASS_HashMap$HashIterator;
	protected static final Class CLASS_LinkedHashMap$LinkedHashIterator;
	protected static final long AFIELD_CheckedMap_m;
	protected static final long AFIELD_CheckedSortedMap_sm;
	protected static final long AFIELD_SynchronizedMap_m;
	protected static final long AFIELD_SynchronizedSortedMap_sm;
	protected static final long AFIELD_UnmodifiableMap_m;
	protected static final long AFIELD_UnmodifiableSortedMap_sm;
	protected static final long AFIELD_HashMap_table;
	protected static final long AFIELD_HashMap_modCount;
	protected static final long AFIELD_HashMap$HashIterator_this$0;
	protected static final long AFIELD_HashMap$HashIterator_next;
	protected static final long AFIELD_HashMap$HashIterator_current;
	protected static final long AFIELD_HashMap$HashIterator_expectedModCount;
	protected static final long AFIELD_HashMap$HashIterator_index;
	protected static final long AFIELD_LinkedHashMap_head;
	protected static final long AFIELD_LinkedHashMap_tail;
	protected static final long AFIELD_LinkedHashMap_modCount;
	protected static final long AFIELD_LinkedHashMap$LinkedHashIterator_this$0;
	protected static final long AFIELD_LinkedHashMap$LinkedHashIterator_next;
	protected static final long AFIELD_LinkedHashMap$LinkedHashIterator_current;
	protected static final long AFIELD_LinkedHashMap$LinkedHashIterator_expectedModCount;

	static { try {
		CLASS_CheckedMap = Class.forName("java.util.Collections$CheckedMap");
		CLASS_CheckedSortedMap = Class.forName("java.util.Collections$CheckedSortedMap");
		CLASS_SynchronizedMap = Class.forName("java.util.Collections$SynchronizedMap");
		CLASS_SynchronizedSortedMap = Class.forName("java.util.Collections$SynchronizedSortedMap");
		CLASS_UnmodifiableMap = Class.forName("java.util.Collections$UnmodifiableMap");
		CLASS_UnmodifiableSortedMap = Class.forName("java.util.Collections$UnmodifiableSortedMap");
		CLASS_HashMap$HashIterator = Class.forName("java.util.HashMap$HashIterator");
		CLASS_LinkedHashMap$LinkedHashIterator = Class.forName("java.util.LinkedHashMap$LinkedHashIterator");

		if(SystemUtils.JAVA_DETECTION_VERSION > 8) {
			FMapUtilsImplementation instance = (FMapUtilsImplementation) FutureJavaUtils.fastCall("getInstance");
			AFIELD_CheckedMap_m = instance.getCheckedMapMOffset();
			AFIELD_CheckedSortedMap_sm = instance.getCheckedSortedMapSMOffset();
			AFIELD_SynchronizedMap_m = instance.getSynchronizedMapMOffset();
			AFIELD_SynchronizedSortedMap_sm = instance.getSynchronizedSortedMapSMOffset();
			AFIELD_UnmodifiableMap_m = instance.getUnmodifiableMapMOffset();
			AFIELD_UnmodifiableSortedMap_sm = instance.getUnmodifiableSortedMapSMOffset();
			AFIELD_HashMap_table = instance.getHashMapTableOffset();
			AFIELD_HashMap_modCount = instance.getHashMapModCountOffset();
			AFIELD_HashMap$HashIterator_this$0 = instance.getHashMap$HashIteratorThis$0Offset();
			AFIELD_HashMap$HashIterator_next = instance.getHashMap$HashIteratorNextOffset();
			AFIELD_HashMap$HashIterator_current = instance.getHashMap$HashIteratorCurrentOffset();
			AFIELD_HashMap$HashIterator_expectedModCount = instance.getHashMap$HashIteratorExpectedModCountOffset();
			AFIELD_HashMap$HashIterator_index = instance.getHashMap$HashIteratorIndexOffset();
			AFIELD_LinkedHashMap_head = instance.getLinkedHashMapHeadOffset();
			AFIELD_LinkedHashMap_tail = instance.getLinkedHashMapTailOffset();
			AFIELD_LinkedHashMap_modCount = instance.getLinkedHashMapModCountOffset();
			AFIELD_LinkedHashMap$LinkedHashIterator_this$0 = instance.getLinkedHashMap$LinkedHashIteratorThis$0Offset();
			AFIELD_LinkedHashMap$LinkedHashIterator_next = instance.getLinkedHashMap$LinkedHashIteratorNextOffset();
			AFIELD_LinkedHashMap$LinkedHashIterator_current = instance.getLinkedHashMap$LinkedHashIteratorCurrentOffset();
			AFIELD_LinkedHashMap$LinkedHashIterator_expectedModCount = instance.getLinkedHashMap$LinkedHashIteratorExpectedModCountOffset();
		} else {
			Unsafe unsafe = UnsafeUtils.R_getUnsafe();
			Field FIELD_CheckedMap_m = CLASS_CheckedMap.getDeclaredField("m");
			Field FIELD_CheckedSortedMap_m = CLASS_CheckedSortedMap.getDeclaredField("sm");
			Field FIELD_SynchronizedMap_m = CLASS_SynchronizedMap.getDeclaredField("m");
			Field FIELD_SynchronizedSortedMap_m = CLASS_SynchronizedSortedMap.getDeclaredField("sm");
			Field FIELD_UnmodifiableMap_m = CLASS_UnmodifiableMap.getDeclaredField("m");
			Field FIELD_UnmodifiableSortedMap_m = CLASS_UnmodifiableSortedMap.getDeclaredField("sm");
			Field FIELD_HashMap_table = HashMap.class.getDeclaredField("table");
			Field FIELD_HashMap_modCount = HashMap.class.getDeclaredField("modCount");
			Field FIELD_HashMap$HashIterator_this$0 = CLASS_HashMap$HashIterator.getDeclaredField("this$0");
			Field FIELD_HashMap$HashIterator_next = CLASS_HashMap$HashIterator.getDeclaredField("next");
			Field FIELD_HashMap$HashIterator_current = CLASS_HashMap$HashIterator.getDeclaredField("current");
			Field FIELD_HashMap$HashIterator_expectedModCount = CLASS_HashMap$HashIterator.getDeclaredField("expectedModCount");
			Field FIELD_HashMap$HashIterator_index = CLASS_HashMap$HashIterator.getDeclaredField("index");
			Field FIELD_LinkedHashMap_head = LinkedHashMap.class.getDeclaredField("head");
			Field FIELD_LinkedHashMap_tail = LinkedHashMap.class.getDeclaredField("tail");
			Field FIELD_LinkedHashMap_modCount = LinkedHashMap.class.getSuperclass().getDeclaredField("modCount");
			Field FIELD_LinkedHashMap$LinkedHashIterator_this$0 = CLASS_LinkedHashMap$LinkedHashIterator.getDeclaredField("this$0");
			Field FIELD_LinkedHashMap$LinkedHashIterator_next = CLASS_LinkedHashMap$LinkedHashIterator.getDeclaredField("next");
			Field FIELD_LinkedHashMap$LinkedHashIterator_current = CLASS_LinkedHashMap$LinkedHashIterator.getDeclaredField("current");
			Field FIELD_LinkedHashMap$LinkedHashIterator_expectedModCount = CLASS_LinkedHashMap$LinkedHashIterator.getDeclaredField("expectedModCount");
			AFIELD_CheckedMap_m = unsafe.objectFieldOffset(FIELD_CheckedMap_m);
			AFIELD_CheckedSortedMap_sm = unsafe.objectFieldOffset(FIELD_CheckedSortedMap_m);
			AFIELD_SynchronizedMap_m = unsafe.objectFieldOffset(FIELD_SynchronizedMap_m);
			AFIELD_SynchronizedSortedMap_sm = unsafe.objectFieldOffset(FIELD_SynchronizedSortedMap_m);
			AFIELD_UnmodifiableMap_m = unsafe.objectFieldOffset(FIELD_UnmodifiableMap_m);
			AFIELD_UnmodifiableSortedMap_sm = unsafe.objectFieldOffset(FIELD_UnmodifiableSortedMap_m);
			AFIELD_HashMap_table = unsafe.objectFieldOffset(FIELD_HashMap_table);
			AFIELD_HashMap_modCount = unsafe.objectFieldOffset(FIELD_HashMap_modCount);
			AFIELD_HashMap$HashIterator_this$0 = unsafe.objectFieldOffset(FIELD_HashMap$HashIterator_this$0);
			AFIELD_HashMap$HashIterator_next = unsafe.objectFieldOffset(FIELD_HashMap$HashIterator_next);
			AFIELD_HashMap$HashIterator_current = unsafe.objectFieldOffset(FIELD_HashMap$HashIterator_current);
			AFIELD_HashMap$HashIterator_expectedModCount = unsafe.objectFieldOffset(FIELD_HashMap$HashIterator_expectedModCount);
			AFIELD_HashMap$HashIterator_index = unsafe.objectFieldOffset(FIELD_HashMap$HashIterator_index);
			AFIELD_LinkedHashMap_head = unsafe.objectFieldOffset(FIELD_LinkedHashMap_head);
			AFIELD_LinkedHashMap_tail = unsafe.objectFieldOffset(FIELD_LinkedHashMap_tail);
			AFIELD_LinkedHashMap_modCount = unsafe.objectFieldOffset(FIELD_LinkedHashMap_modCount);
			AFIELD_LinkedHashMap$LinkedHashIterator_this$0 = unsafe.objectFieldOffset(FIELD_LinkedHashMap$LinkedHashIterator_this$0);
			AFIELD_LinkedHashMap$LinkedHashIterator_next = unsafe.objectFieldOffset(FIELD_LinkedHashMap$LinkedHashIterator_next);
			AFIELD_LinkedHashMap$LinkedHashIterator_current = unsafe.objectFieldOffset(FIELD_LinkedHashMap$LinkedHashIterator_current);
			AFIELD_LinkedHashMap$LinkedHashIterator_expectedModCount = unsafe.objectFieldOffset(FIELD_LinkedHashMap$LinkedHashIterator_expectedModCount);
		}
	} catch(Exception e) { throw new Error(e); } }

	public static <K, V> void sort(Map<K, V> map, Comparator<? super Map.Entry<K, V>> comparator) {
		ArrayList<Map.Entry<K, V>> list = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		list.addAll(map.entrySet()); list.sort(comparator); map.clear();
		for(Map.Entry<K, V> entry : list) map.put(entry.getKey(), entry.getValue());
		Pool.returnObject(ArrayList.class, list);
	}

	public static <K, V> Map<K, V> getSourceMap(Map<K, V> map) {
		if(CLASS_CheckedMap.isInstance(map)) return (Map<K, V>) UnsafeUtils.R_getUnsafe().getObject(map, AFIELD_CheckedMap_m);
		if(CLASS_SynchronizedMap.isInstance(map)) return (Map<K, V>) UnsafeUtils.R_getUnsafe().getObject(map, AFIELD_SynchronizedMap_m);
		if(CLASS_UnmodifiableMap.isInstance(map)) return (Map<K, V>) UnsafeUtils.R_getUnsafe().getObject(map, AFIELD_UnmodifiableMap_m);
		return map;
	}
	public static <K, V> SortedMap<K, V> getSourceSortedMap(SortedMap<K, V> map) {
		if(CLASS_CheckedSortedMap.isInstance(map)) return (SortedMap<K, V>) UnsafeUtils.R_getUnsafe().getObject(map, AFIELD_CheckedSortedMap_sm);
		if(CLASS_SynchronizedSortedMap.isInstance(map)) return (SortedMap<K, V>) UnsafeUtils.R_getUnsafe().getObject(map, AFIELD_SynchronizedSortedMap_sm);
		if(CLASS_UnmodifiableSortedMap.isInstance(map)) return (SortedMap<K, V>) UnsafeUtils.R_getUnsafe().getObject(map, AFIELD_UnmodifiableSortedMap_sm);
		return map;
	}
	public static <K, V> Object getTable(HashMap<K, V> map) { return UnsafeUtils.R_getUnsafe().getObject(map, AFIELD_HashMap_table); }
	@SuppressWarnings("SuspiciousSystemArraycopy")
	public static <K, V> void fastGet(HashMap<K, V> from, Map.Entry<K, V>[] to, int srcOffset, int dstOffset, int len) {
		ArrayUtils.assertCopyIndex(srcOffset, from.size(), dstOffset, to.length, len);
		System.arraycopy(getTable(from), srcOffset, to, dstOffset, len);
	}
	public static <K, V> Iterator<Map.Entry<K, V>> reusableIterator(Map<K, V> map) {
		if(map instanceof OptimizedHashMap)
			return ((OptimizedHashMap<K, V>) map).reusableIterator();
		if(map instanceof OptimizedLinkedHashMap)
			return ((OptimizedLinkedHashMap<K, V>) map).reusableIterator();
		throw new IllegalArgumentException("Object is not OptimizedHashMap");
	}
	public static void resetHashMapIterator(Object hashIterator) {
		Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		if(CLASS_HashMap$HashIterator.isInstance(hashIterator)) {
			HashMap<?, ?> hashMap = (HashMap<?, ?>) unsafe.getObject(hashIterator, AFIELD_HashMap$HashIterator_this$0);
			unsafe.putInt(hashIterator, AFIELD_HashMap$HashIterator_expectedModCount, unsafe.getInt(hashMap, AFIELD_HashMap_modCount));
			unsafe.putObject(hashIterator, AFIELD_HashMap$HashIterator_current, null);
			Object next = null; Object table = getTable(hashMap); int index = 0;
			if(table != null && hashMap.size() > 0) while(index < Array.getLength(table) && (next = Array.get(table, index++)) == null);
			unsafe.putInt(hashIterator, AFIELD_HashMap$HashIterator_index, index);
			unsafe.putObject(hashIterator, AFIELD_HashMap$HashIterator_next, next);
		} else if(CLASS_LinkedHashMap$LinkedHashIterator.isInstance(hashIterator)) {
			LinkedHashMap<?, ?> hashMap = (LinkedHashMap<?, ?>) unsafe.getObject(hashIterator, AFIELD_LinkedHashMap$LinkedHashIterator_this$0);
			unsafe.putObject(hashIterator, AFIELD_LinkedHashMap$LinkedHashIterator_next, getHead(hashMap));
			unsafe.putInt(hashIterator, AFIELD_LinkedHashMap$LinkedHashIterator_expectedModCount, unsafe.getInt(hashMap, AFIELD_LinkedHashMap_modCount));
			unsafe.putObject(hashIterator, AFIELD_LinkedHashMap$LinkedHashIterator_current, null);
		} else throw new IllegalArgumentException("Not an HashIterator class");
	}
	public static <K, V> Map.Entry<K, V> getHead(LinkedHashMap<K, V> map) { return (Map.Entry<K, V>) UnsafeUtils.R_getUnsafe().getObject(map, AFIELD_LinkedHashMap_head); }
	public static <K, V> Map.Entry<K, V> getTail(LinkedHashMap<K, V> map) { return (Map.Entry<K, V>) UnsafeUtils.R_getUnsafe().getObject(map, AFIELD_LinkedHashMap_tail); }

	public static <K, V> HashMap<K, V> optimizedHashMap() { return new OptimizedHashMap<>(); }
	protected static class OptimizedHashMap<K, V> extends HashMap<K, V> {
		protected Iterator<Entry<K, V>> reusableIterator;

		public OptimizedHashMap() { }

		public Iterator<Entry<K, V>> reusableIterator() {
			if(reusableIterator == null)
				return reusableIterator = super.entrySet().iterator();
			resetHashMapIterator(reusableIterator);
			return reusableIterator;
		}
	}

	public static <K, V> LinkedHashMap<K, V> optimizedLinkedHashMap() { return new OptimizedLinkedHashMap<>(); }
	protected static class OptimizedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
		protected Iterator<Map.Entry<K, V>> reusableIterator;

		public OptimizedLinkedHashMap() { }

		public Iterator<Map.Entry<K, V>> reusableIterator() {
			if(reusableIterator == null)
				return reusableIterator = super.entrySet().iterator();
			resetHashMapIterator(reusableIterator);
			return reusableIterator;
		}
	}

	public static <K, V> Map.Entry<K, V> dumpMapEntry(K key, V value) { return new DumpMapEntry<>(key, value); }
	protected static class DumpMapEntry<K, V> implements Map.Entry<K, V> {
		protected final K key;
		protected V value;

		public DumpMapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override public K getKey() { return key; }
		@Override public V getValue() { return value; }
		@Override public V setValue(V newValue) { V oldValue = value; value = newValue; return oldValue; }
		public int hashCode() { return Objects.hashCode(key) ^ Objects.hashCode(value); }
		public boolean equals(Object o) {
			if(o == this) return true;
			if(o instanceof Map.Entry) {
				Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
				return Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue());
			} return false;
		}
	}

	protected interface FMapUtilsImplementation extends FutureJavaUtils.FutureJavaImplementation {
		long getCheckedMapMOffset() throws Exception;
		long getCheckedSortedMapSMOffset() throws Exception;
		long getSynchronizedMapMOffset() throws Exception;
		long getSynchronizedSortedMapSMOffset() throws Exception;
		long getUnmodifiableMapMOffset() throws Exception;
		long getUnmodifiableSortedMapSMOffset() throws Exception;
		long getHashMapTableOffset() throws Exception;
		long getHashMapModCountOffset() throws Exception;
		long getHashMap$HashIteratorThis$0Offset() throws Exception;
		long getHashMap$HashIteratorNextOffset() throws Exception;
		long getHashMap$HashIteratorCurrentOffset() throws Exception;
		long getHashMap$HashIteratorExpectedModCountOffset() throws Exception;
		long getHashMap$HashIteratorIndexOffset() throws Exception;
		long getLinkedHashMapHeadOffset() throws Exception;
		long getLinkedHashMapTailOffset() throws Exception;
		long getLinkedHashMapModCountOffset() throws Exception;
		long getLinkedHashMap$LinkedHashIteratorThis$0Offset() throws Exception;
		long getLinkedHashMap$LinkedHashIteratorNextOffset() throws Exception;
		long getLinkedHashMap$LinkedHashIteratorCurrentOffset() throws Exception;
		long getLinkedHashMap$LinkedHashIteratorExpectedModCountOffset() throws Exception;
	}
}
