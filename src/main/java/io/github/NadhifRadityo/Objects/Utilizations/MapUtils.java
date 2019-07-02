package io.github.NadhifRadityo.Objects.Utilizations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapUtils {
	private MapUtils() { }
	
	// TODO: Avoid using new ArrayList. Reuse from Pool instead.
	public static <K, V extends Comparable<? super V>> void sort(Map<K, V> map, Comparator<? super Entry<K, V>> comparator) {
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet()); list.sort(comparator); map.clear();
		for(Entry<K, V> entry : list) map.put(entry.getKey(), entry.getValue());
	}
}
