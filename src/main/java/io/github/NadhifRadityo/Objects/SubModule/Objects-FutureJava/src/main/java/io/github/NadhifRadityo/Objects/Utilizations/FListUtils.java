package io.github.NadhifRadityo.Objects.Utilizations;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.ArrayList;

public class FListUtils extends ListUtils {

	static {
		Java9Utils.disableJava9SillyWarning();
	}

	public static FListUtilsImplementation FImplgetInstance() {
		return new FListUtilsImplementation() {
			@Override public long getCheckedListListOffset() throws Exception {
				Field FIELD_CheckedList_list = Class.forName("java.util.Collections$CheckedList").getDeclaredField("list");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_CheckedList_list);
			}
			@Override public long getSynchronizedListListOffset() throws Exception {
				Field FIELD_SynchronizedList_list = Class.forName("java.util.Collections$SynchronizedList").getDeclaredField("list");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_SynchronizedList_list);
			}
			@Override public long getUnmodifiableListListOffset() throws Exception {
				Field FIELD_UnmodifiableList_list = Class.forName("java.util.Collections$UnmodifiableList").getDeclaredField("list");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_UnmodifiableList_list);
			}
			@Override public long getArrayListElementDataOffset() throws Exception {
				Field FIELD_ArrayList_elementData = ArrayList.class.getDeclaredField("elementData");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_ArrayList_elementData);
			}
			@Override public long getArrayListSizeOffset() throws Exception {
				Field FIELD_ArrayList_size = ArrayList.class.getDeclaredField("size");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_ArrayList_size);
			}
			@Override public long getAbstractListModCountOffset() throws Exception {
				Field FIELD_AbstractList_modCount = AbstractList.class.getDeclaredField("modCount");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_AbstractList_modCount);
			}
			@Override public long getArrayList$ItrThis$0Offset() throws Exception {
				Field FIELD_ArrayList$Itr_this$0 = Class.forName("java.util.ArrayList$Itr").getDeclaredField("this$0");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_ArrayList$Itr_this$0);
			}
			@Override public long getArrayList$ItrCursorOffset() throws Exception {
				Field FIELD_ArrayList$Itr_cursor = Class.forName("java.util.ArrayList$Itr").getDeclaredField("cursor");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_ArrayList$Itr_cursor);
			}
			@Override public long getArrayList$ItrLastRetOffset() throws Exception {
				Field FIELD_ArrayList$Itr_lastRet = Class.forName("java.util.ArrayList$Itr").getDeclaredField("lastRet");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_ArrayList$Itr_lastRet);
			}
			@Override public long getArrayList$ItrExpectedModCountOffset() throws Exception {
				Field FIELD_ArrayList$Itr_expectedModCount = Class.forName("java.util.ArrayList$Itr").getDeclaredField("expectedModCount");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_ArrayList$Itr_expectedModCount);
			}
			@Override public Object[] getArrayListEmptyElementData() throws Exception {
				Unsafe unsafe = UnsafeUtils.R_getUnsafe();
				Field FIELD_ArrayList_EMPTY_ELEMENTDATA = ArrayList.class.getDeclaredField("EMPTY_ELEMENTDATA");
				return (Object[]) unsafe.getObject(unsafe.staticFieldBase(FIELD_ArrayList_EMPTY_ELEMENTDATA), unsafe.staticFieldOffset(FIELD_ArrayList_EMPTY_ELEMENTDATA));
			}
			@Override public Object[] getArrayListDefaultCapacityEmptyElementData() throws Exception {
				Unsafe unsafe = UnsafeUtils.R_getUnsafe();
				Field FIELD_ArrayList_DEFAULTCAPACITY_EMPTY_ELEMENTDATA = ArrayList.class.getDeclaredField("DEFAULTCAPACITY_EMPTY_ELEMENTDATA");
				return (Object[]) unsafe.getObject(unsafe.staticFieldBase(FIELD_ArrayList_DEFAULTCAPACITY_EMPTY_ELEMENTDATA), unsafe.staticFieldOffset(FIELD_ArrayList_DEFAULTCAPACITY_EMPTY_ELEMENTDATA));
			}
		};
	}
}
