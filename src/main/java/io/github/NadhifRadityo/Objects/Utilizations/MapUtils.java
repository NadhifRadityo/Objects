package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class MapUtils {
	private MapUtils() { }
	
	public static <K, V extends Comparable<? super V>> void sort(Map<K, V> map, Comparator<? super Entry<K, V>> comparator) {
//		List<Object> list = new ArrayList<>(); list.addAll(map.entrySet());
//		list.sort((o1, o2) -> comparator.compare((Entry<K, V>) o1, (Entry<K, V>) o2)); map.clear();
//		for(Object entry : list) map.put(((Entry<K, V>) entry).getKey(), ((Entry<K, V>) entry).getValue());
		ArrayList<Entry<K, V>> list = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		list.addAll(map.entrySet()); list.sort(comparator); map.clear();
		for(Entry<K, V> entry : list) map.put(entry.getKey(), entry.getValue());
		Pool.returnObject(ArrayList.class, list);
	}
}
