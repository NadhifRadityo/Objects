package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Comparator;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	private StringUtils() {
		
	}
	
	public static final Comparator<String> sortStringWithNumber = new Comparator<String>() {
		@Override public int compare(String o1, String o2) { return charToNumber(o1).compareTo(charToNumber(o2)); }
		String[] corresponding = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K" };
		String charToNumber(String what) {
			StringBuilder result = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
			try { for(int i = 0; i < what.length(); i++) { char c = what.charAt(i);
					result.append(Character.isDigit(c) || c == '.' ? corresponding[c == '.' ? 10 : Integer.parseInt(c + "")] : c);
				} return result.toString();
			} finally { Pool.returnObject(StringBuilder.class, result); }
		}
	};
	
	public static String capitaliseEachWords(String s) {
		String[] words = s.split(" ");
		StringBuilder result = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
		try { for(int i = 0; i < words.length; i++)
			result.append(capitalize(words[i]) + ((i + 1 < words.length) ? " " : ""));
		return result.toString(); } finally { Pool.returnObject(StringBuilder.class, result); }
	}
	
	public static String escapeString(String string) { return StringEscapeUtils.escapeJava(string); }
	public static String unescapeString(String string) { return StringEscapeUtils.unescapeJava(string); }
	
	public static boolean isPalindrome(String string) {
		StringBuilder reversed = Pool.tryBorrow(Pool.getPool(StringBuilder.class)); reversed.append(string).reverse();
		try { return string.equals(reversed.toString()); } finally { Pool.returnObject(StringBuilder.class, reversed); }
	} public static boolean isPalindromeLowercase(String string) { return isPalindrome(string.toLowerCase()); }
	
	public static String mergeCross(String... strings) {
		StringBuilder result = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
		try { int maxLen = 0; for(String string : strings) maxLen += string.length();
			for(int i = 0; i < maxLen; i++) { for(String string : strings)
				if(i < string.length()) result.append(string.charAt(i));
			} return result.toString();
		} finally { Pool.returnObject(StringBuilder.class, result); }
	}
}
