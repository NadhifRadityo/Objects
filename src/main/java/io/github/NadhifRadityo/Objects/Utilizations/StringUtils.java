package io.github.NadhifRadityo.Objects.Utilizations;

import java.util.Comparator;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	private StringUtils() {
		
	}
	
	public static final Comparator<String> sortStringWithNumber = new Comparator<String>() {
		@Override public int compare(String o1, String o2) { return charFromNumber(o1).compareTo(charFromNumber(o2)); }
		String[] corresponding = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K" };
		String charFromNumber(String what) {
			StringBuilder result = new StringBuilder();
			for(int i = 0; i < what.length(); i++) {
				char c = what.charAt(i);
				result.append(Character.isDigit(c) || c == '.' ? corresponding[c == '.' ? 10 : Integer.parseInt(c + "")] : c);
			} return result.toString();
		}
	};
	
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
	
	public static String mergeCross(String... strings) {
		StringBuilder result = new StringBuilder(); int maxLen = 0;
		for(String string : strings) maxLen += string.length();
		for(int i = 0; i < maxLen; i++) { for(String string : strings)
			if(i < string.length()) result.append(string.charAt(i));
		} return result.toString();
	}
}
