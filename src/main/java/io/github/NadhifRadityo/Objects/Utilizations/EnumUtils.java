package io.github.NadhifRadityo.Objects.Utilizations;

public class EnumUtils extends org.apache.commons.lang3.EnumUtils {
	
	public static <E extends Enum<E>> String[] getEnumNames(Class<E> enumClass) {
		return getEnumMap(enumClass).keySet().toArray(new String[0]);
	}
}
