package io.github.NadhifRadityo.Objects.$Utilizations;

import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@SuppressWarnings("unchecked")
public class ListUtils {
	protected static final Class CLASS_CheckedList;
	protected static final Class CLASS_SynchronizedList;
	protected static final Class CLASS_UnmodifiableList;
	protected static final Class CLASS_ArrayList$Itr;
	protected static final long AFIELD_CheckedList_list;
	protected static final long AFIELD_SynchronizedList_list;
	protected static final long AFIELD_UnmodifiableList_list;
	protected static final long AFIELD_ArrayList_elementData;
	protected static final long AFIELD_ArrayList_size;
	protected static final long AFIELD_AbstractList_modCount;
	protected static final long AFIELD_ArrayList$Itr_this$0;
	protected static final long AFIELD_ArrayList$Itr_cursor;
	protected static final long AFIELD_ArrayList$Itr_lastRet;
	protected static final long AFIELD_ArrayList$Itr_expectedModCount;
	protected static final Object[] FIELD_VAL_ArrayList_EMPTY_ELEMENTDATA;
	protected static final Object[] FIELD_VAL_ArrayList_DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
	public static final Set<Collector.Characteristics> CH_CONCURRENT_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT, Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH));
	public static final Set<Collector.Characteristics> CH_CONCURRENT_NOID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT, Collector.Characteristics.UNORDERED));
	public static final Set<Collector.Characteristics> CH_UNORDERED_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH));
	public static final Set<Collector.Characteristics> CH_UNORDERED_NOID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED));
	public static final Set<Collector.Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
	public static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();

	static { try {
		CLASS_CheckedList = Class.forName("java.util.Collections$CheckedList");
		CLASS_SynchronizedList = Class.forName("java.util.Collections$SynchronizedList");
		CLASS_UnmodifiableList = Class.forName("java.util.Collections$UnmodifiableList");
		CLASS_ArrayList$Itr = Class.forName("java.util.ArrayList$Itr");

		if(SystemUtils.JAVA_DETECTION_VERSION > 8) {
			FListUtilsImplementation instance = (FListUtilsImplementation) FutureJavaUtils.fastCall("getInstance");
			AFIELD_CheckedList_list = instance.getCheckedListListOffset();
			AFIELD_SynchronizedList_list = instance.getSynchronizedListListOffset();
			AFIELD_UnmodifiableList_list = instance.getUnmodifiableListListOffset();
			AFIELD_ArrayList_elementData = instance.getArrayListElementDataOffset();
			AFIELD_ArrayList_size = instance.getArrayListSizeOffset();
			AFIELD_AbstractList_modCount = instance.getAbstractListModCountOffset();
			AFIELD_ArrayList$Itr_this$0 = instance.getArrayList$ItrThis$0Offset();
			AFIELD_ArrayList$Itr_cursor = instance.getArrayList$ItrCursorOffset();
			AFIELD_ArrayList$Itr_lastRet = instance.getArrayList$ItrLastRetOffset();
			AFIELD_ArrayList$Itr_expectedModCount = instance.getArrayList$ItrExpectedModCountOffset();
			FIELD_VAL_ArrayList_EMPTY_ELEMENTDATA = instance.getArrayListEmptyElementData();
			FIELD_VAL_ArrayList_DEFAULTCAPACITY_EMPTY_ELEMENTDATA = instance.getArrayListDefaultCapacityEmptyElementData();
		} else {
			UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
			Field FIELD_CheckedList_list = CLASS_CheckedList.getDeclaredField("list");
			Field FIELD_SynchronizedList_list = CLASS_SynchronizedList.getDeclaredField("list");
			Field FIELD_UnmodifiableList_list = CLASS_UnmodifiableList.getDeclaredField("list");
			Field FIELD_ArrayList_elementData = ArrayList.class.getDeclaredField("elementData");
			Field FIELD_ArrayList_size = ArrayList.class.getDeclaredField("size");
			Field FIELD_AbstractList_modCount = AbstractList.class.getDeclaredField("modCount");
			Field FIELD_ArrayList$Itr_this$0 = CLASS_ArrayList$Itr.getDeclaredField("this$0");
			Field FIELD_ArrayList$Itr_cursor = CLASS_ArrayList$Itr.getDeclaredField("cursor");
			Field FIELD_ArrayList$Itr_lastRet = CLASS_ArrayList$Itr.getDeclaredField("lastRet");
			Field FIELD_ArrayList$Itr_expectedModCount = CLASS_ArrayList$Itr.getDeclaredField("expectedModCount");
			Field FIELD_ArrayList_EMPTY_ELEMENTDATA = ArrayList.class.getDeclaredField("EMPTY_ELEMENTDATA");
			Field FIELD_ArrayList_DEFAULTCAPACITY_EMPTY_ELEMENTDATA = ArrayList.class.getDeclaredField("DEFAULTCAPACITY_EMPTY_ELEMENTDATA");
			AFIELD_CheckedList_list = unsafe.objectFieldOffset(FIELD_CheckedList_list);
			AFIELD_SynchronizedList_list = unsafe.objectFieldOffset(FIELD_SynchronizedList_list);
			AFIELD_UnmodifiableList_list = unsafe.objectFieldOffset(FIELD_UnmodifiableList_list);
			AFIELD_ArrayList_elementData = unsafe.objectFieldOffset(FIELD_ArrayList_elementData);
			AFIELD_ArrayList_size = unsafe.objectFieldOffset(FIELD_ArrayList_size);
			AFIELD_AbstractList_modCount = unsafe.objectFieldOffset(FIELD_AbstractList_modCount);
			AFIELD_ArrayList$Itr_this$0 = unsafe.objectFieldOffset(FIELD_ArrayList$Itr_this$0);
			AFIELD_ArrayList$Itr_cursor = unsafe.objectFieldOffset(FIELD_ArrayList$Itr_cursor);
			AFIELD_ArrayList$Itr_lastRet = unsafe.objectFieldOffset(FIELD_ArrayList$Itr_lastRet);
			AFIELD_ArrayList$Itr_expectedModCount = unsafe.objectFieldOffset(FIELD_ArrayList$Itr_expectedModCount);
			FIELD_VAL_ArrayList_EMPTY_ELEMENTDATA = (Object[]) unsafe.getObject(unsafe.staticFieldBase(FIELD_ArrayList_EMPTY_ELEMENTDATA), unsafe.staticFieldOffset(FIELD_ArrayList_EMPTY_ELEMENTDATA));
			FIELD_VAL_ArrayList_DEFAULTCAPACITY_EMPTY_ELEMENTDATA = (Object[]) unsafe.getObject(unsafe.staticFieldBase(FIELD_ArrayList_DEFAULTCAPACITY_EMPTY_ELEMENTDATA), unsafe.staticFieldOffset(FIELD_ArrayList_DEFAULTCAPACITY_EMPTY_ELEMENTDATA));
		}
	} catch(Exception e) { throw new Error(e); } }

	public static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
		protected final Supplier<A> supplier;
		protected final BiConsumer<A, T> accumulator;
		protected final BinaryOperator<A> combiner;
		protected final Function<A, R> finisher;
		protected final Set<Characteristics> characteristics;

		public CollectorImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner, Function<A, R> finisher, Set<Characteristics> characteristics) {
			this.supplier = supplier;
			this.accumulator = accumulator;
			this.combiner = combiner;
			this.finisher = finisher;
			this.characteristics = characteristics;
		}
		public CollectorImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner, Set<Characteristics> characteristics) {
			this(supplier, accumulator, combiner, i -> (R) i, characteristics);
		}

		@Override public Supplier<A> supplier() { return supplier; }
		@Override public BiConsumer<A, T> accumulator() { return accumulator; }
		@Override public BinaryOperator<A> combiner() { return combiner; }
		@Override public Function<A, R> finisher() { return finisher; }
		@Override public Set<Characteristics> characteristics() { return characteristics; }
	}

	public static <T> boolean containsEquals(Iterable<T> list, T e) {
		for(T o : list) if(o == e) return true; return false;
	}

	public static <T> List<T> getSourceList(List<T> list) {
		if(CLASS_CheckedList.isInstance(list)) return (List<T>) UnsafeUtils.R_getUnsafe().getObject(list, AFIELD_CheckedList_list);
		if(CLASS_SynchronizedList.isInstance(list)) return (List<T>) UnsafeUtils.R_getUnsafe().getObject(list, AFIELD_SynchronizedList_list);
		if(CLASS_UnmodifiableList.isInstance(list)) return (List<T>) UnsafeUtils.R_getUnsafe().getObject(list, AFIELD_UnmodifiableList_list);
		return list;
	}
	public static Object[] getElementData(ArrayList<?> list) { return (Object[]) UnsafeUtils.R_getUnsafe().getObject(list, AFIELD_ArrayList_elementData); }
	public static <T> void fastSet(ArrayList<T> to, T[] from, int srcOffset, int dstOffset, int len) {
		ArrayUtils.assertIndex(srcOffset, from.length, len);
		ArrayUtils.assertIndex(dstOffset, dstOffset, 0);
		to.ensureCapacity(dstOffset + len);
		Object[] elementData = getElementData(to);
		if(elementData == FIELD_VAL_ArrayList_EMPTY_ELEMENTDATA || elementData == FIELD_VAL_ArrayList_DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
			to.add(null); elementData = getElementData(to);
		} System.arraycopy(from, srcOffset, elementData, dstOffset, len);
		try { UnsafeUtils.R_getUnsafe().putInt(to, AFIELD_ArrayList_size, Math.max(to.size(), dstOffset + len)); } catch(Exception e) { throw new Error(e); }
	}
	public static <T> void fastGet(ArrayList<T> from, T[] to, int srcOffset, int dstOffset, int len) {
		ArrayUtils.assertCopyIndex(srcOffset, from.size(), dstOffset, to.length, len);
		Object[] elementData = getElementData(from);
		System.arraycopy(elementData, srcOffset, to, dstOffset, len);
	}
	public static <T> Iterator<T> reusableIterator(List<T> list) {
		if(!(list instanceof OptimizedArrayList))
			throw new IllegalArgumentException("Object is not OptimizedArrayList");
		return ((OptimizedArrayList<T>) list).reusableIterator();
	}
	public static void resetArrayListIterator(Object itr) {
		if(!CLASS_ArrayList$Itr.isInstance(itr))
			throw new IllegalArgumentException("Not an HashIterator class");
		UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
		ArrayList<?> arrayList = (ArrayList<?>) unsafe.getObject(itr, AFIELD_ArrayList$Itr_this$0);
		unsafe.putInt(itr, AFIELD_ArrayList$Itr_cursor, 0);
		unsafe.putInt(itr, AFIELD_ArrayList$Itr_lastRet, -1);
		unsafe.putInt(itr, AFIELD_ArrayList$Itr_expectedModCount, unsafe.getInt(arrayList, AFIELD_AbstractList_modCount));
	}

	public static <T> ArrayList<T> optimizedArrayList() { return new OptimizedArrayList<>(); }
	protected static class OptimizedArrayList<T> extends ArrayList<T> {
		protected Iterator<T> reusableIterator;

		public OptimizedArrayList() { }

		public Iterator<T> reusableIterator() {
			if(reusableIterator == null)
				return reusableIterator = super.iterator();
			resetArrayListIterator(reusableIterator);
			return reusableIterator;
		}
	}

	protected interface FListUtilsImplementation extends FutureJavaUtils.FutureJavaImplementation {
		long getCheckedListListOffset() throws Exception;
		long getSynchronizedListListOffset() throws Exception;
		long getUnmodifiableListListOffset() throws Exception;
		long getArrayListElementDataOffset() throws Exception;
		long getArrayListSizeOffset() throws Exception;
		long getAbstractListModCountOffset() throws Exception;
		long getArrayList$ItrThis$0Offset() throws Exception;
		long getArrayList$ItrCursorOffset() throws Exception;
		long getArrayList$ItrLastRetOffset() throws Exception;
		long getArrayList$ItrExpectedModCountOffset() throws Exception;
		Object[] getArrayListEmptyElementData() throws Exception;
		Object[] getArrayListDefaultCapacityEmptyElementData() throws Exception;
	}
}
