package io.github.NadhifRadityo.Objects.Utilizations;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class FMapUtils extends MapUtils {

	static {
		Java9Utils.disableJava9SillyWarning();
	}

	public static FMapUtilsImplementation FImplgetInstance() {
		return new FMapUtilsImplementation() {
			@Override public long getCheckedMapMOffset() throws Exception {
				Field FIELD_CheckedMap_m = Class.forName("java.util.Collections$CheckedMap").getDeclaredField("m");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_CheckedMap_m);
			}
			@Override public long getCheckedSortedMapSMOffset() throws Exception {
				Field FIELD_CheckedSortedMap_m = Class.forName("java.util.Collections$CheckedSortedMap").getDeclaredField("sm");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_CheckedSortedMap_m);
			}
			@Override public long getSynchronizedMapMOffset() throws Exception {
				Field FIELD_SynchronizedMap_m = Class.forName("java.util.Collections$SynchronizedMap").getDeclaredField("m");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_SynchronizedMap_m);
			}
			@Override public long getSynchronizedSortedMapSMOffset() throws Exception {
				Field FIELD_SynchronizedSortedMap_m = Class.forName("java.util.Collections$SynchronizedSortedMap").getDeclaredField("sm");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_SynchronizedSortedMap_m);
			}
			@Override public long getUnmodifiableMapMOffset() throws Exception {
				Field FIELD_UnmodifiableMap_m = Class.forName("java.util.Collections$UnmodifiableMap").getDeclaredField("m");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_UnmodifiableMap_m);
			}
			@Override public long getUnmodifiableSortedMapSMOffset() throws Exception {
				Field FIELD_UnmodifiableSortedMap_m = Class.forName("java.util.Collections$UnmodifiableSortedMap").getDeclaredField("sm");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_UnmodifiableSortedMap_m);
			}
			@Override public long getHashMapTableOffset() throws Exception {
				Field FIELD_HashMap_table = HashMap.class.getDeclaredField("table");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_HashMap_table);
			}
			@Override public long getHashMapModCountOffset() throws Exception {
				Field FIELD_HashMap_modCount = HashMap.class.getDeclaredField("modCount");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_HashMap_modCount);
			}
			@Override public long getHashMap$HashIteratorThis$0Offset() throws Exception {
				Field FIELD_HashMap$HashIterator_this$0 = Class.forName("java.util.HashMap$HashIterator").getDeclaredField("this$0");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_HashMap$HashIterator_this$0);
			}
			@Override public long getHashMap$HashIteratorNextOffset() throws Exception {
				Field FIELD_HashMap$HashIterator_next = Class.forName("java.util.HashMap$HashIterator").getDeclaredField("next");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_HashMap$HashIterator_next);
			}
			@Override public long getHashMap$HashIteratorCurrentOffset() throws Exception {
				Field FIELD_HashMap$HashIterator_current = Class.forName("java.util.HashMap$HashIterator").getDeclaredField("current");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_HashMap$HashIterator_current);
			}
			@Override public long getHashMap$HashIteratorExpectedModCountOffset() throws Exception {
				Field FIELD_HashMap$HashIterator_expectedModCount = Class.forName("java.util.HashMap$HashIterator").getDeclaredField("expectedModCount");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_HashMap$HashIterator_expectedModCount);
			}
			@Override public long getHashMap$HashIteratorIndexOffset() throws Exception {
				Field FIELD_HashMap$HashIterator_index = Class.forName("java.util.HashMap$HashIterator").getDeclaredField("index");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_HashMap$HashIterator_index);
			}
			@Override public long getLinkedHashMapHeadOffset() throws Exception {
				Field FIELD_LinkedHashMap_head = LinkedHashMap.class.getDeclaredField("head");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_LinkedHashMap_head);
			}
			@Override public long getLinkedHashMapTailOffset() throws Exception {
				Field FIELD_LinkedHashMap_tail = LinkedHashMap.class.getDeclaredField("tail");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_LinkedHashMap_tail);
			}
			@Override public long getLinkedHashMapModCountOffset() throws Exception {
				Field FIELD_LinkedHashMap_modCount = LinkedHashMap.class.getSuperclass().getDeclaredField("modCount");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_LinkedHashMap_modCount);
			}
			@Override public long getLinkedHashMap$LinkedHashIteratorThis$0Offset() throws Exception {
				Field FIELD_LinkedHashMap$LinkedHashIterator_this$0 = Class.forName("java.util.LinkedHashMap$LinkedHashIterator").getDeclaredField("this$0");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_LinkedHashMap$LinkedHashIterator_this$0);
			}
			@Override public long getLinkedHashMap$LinkedHashIteratorNextOffset() throws Exception {
				Field FIELD_LinkedHashMap$LinkedHashIterator_next = Class.forName("java.util.LinkedHashMap$LinkedHashIterator").getDeclaredField("next");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_LinkedHashMap$LinkedHashIterator_next);
			}
			@Override public long getLinkedHashMap$LinkedHashIteratorCurrentOffset() throws Exception {
				Field FIELD_LinkedHashMap$LinkedHashIterator_current = Class.forName("java.util.LinkedHashMap$LinkedHashIterator").getDeclaredField("current");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_LinkedHashMap$LinkedHashIterator_current);
			}
			@Override public long getLinkedHashMap$LinkedHashIteratorExpectedModCountOffset() throws Exception {
				Field FIELD_LinkedHashMap$LinkedHashIterator_expectedModCount = Class.forName("java.util.LinkedHashMap$LinkedHashIterator").getDeclaredField("expectedModCount");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_LinkedHashMap$LinkedHashIterator_expectedModCount);
			}
		};
	}
}
