package io.github.NadhifRadityo.Objects.Utilizations;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	public static String capitaliseEachWords(String s) {
		String[] words = s.split(" ");
		String result = "";
		for(int i = 0; i < words.length; i++)
			result += capitalize(words[i]) + ((i + 1 < words.length) ? " " : "");
		return result;
	}
}
