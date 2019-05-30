package io.github.NadhifRadityo.Objects.Utilizations;

import java.util.Collection;

public class ListUtils {
	public static <T> boolean containsEquals(Collection<T> list, T e) {
		for(T o : list) if(o == e) return true;
		return false;
	}
}
