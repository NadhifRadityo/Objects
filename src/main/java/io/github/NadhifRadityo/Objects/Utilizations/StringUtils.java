package io.github.NadhifRadityo.Objects.Utilizations;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	private StringUtils() {
		
	}
	
	public static String capitaliseEachWords(String s) {
		String[] words = s.split(" ");
		String result = "";
		for(int i = 0; i < words.length; i++)
			result += capitalize(words[i]) + ((i + 1 < words.length) ? " " : "");
		return result;
	}
	
	public static String escapeString(String string) { return StringEscapeUtils.escapeJava(string); }
	public static String unescapeString(String string) { return StringEscapeUtils.unescapeJava(string); }
	
	public static boolean isPalindrome(String string) {
		string = string.toLowerCase();
		return string.equals(new StringBuilder(string).reverse().toString());
	}
}
