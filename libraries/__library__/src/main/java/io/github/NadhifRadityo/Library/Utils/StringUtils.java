package io.github.NadhifRadityo.Library.Utils;

public class StringUtils {

	// https://stackoverflow.com/questions/3537706/how-to-unescape-a-java-string-literal-in-java
	public static String unescapeJavaString(String string) {
		StringBuilder stringBuilder = new StringBuilder(string.length());
		for(int i = 0; i < string.length(); i++) {
			char currentChar = string.charAt(i);
			if(currentChar != '\\') { stringBuilder.append(currentChar); continue; }
			char nextChar = (i == string.length() - 1) ? '\\' : string.charAt(i + 1);
			if(nextChar >= '0' && nextChar <= '7') {
				String code = "" + nextChar; i++;
				if((i < string.length() - 1) && string.charAt(i + 1) >= '0' && string.charAt(i + 1) <= '7') {
					code += string.charAt(i + 1); i++;
					if((i < string.length() - 1) && string.charAt(i + 1) >= '0' && string.charAt(i + 1) <= '7') {
						code += string.charAt(i + 1); i++; } }
				stringBuilder.append((char) Integer.parseInt(code, 8));
				continue;
			}
			switch (nextChar) {
				case '\\': currentChar = '\\'; break;
				case 'b': currentChar = '\b'; break;
				case 'f': currentChar = '\f'; break;
				case 'n': currentChar = '\n'; break;
				case 'r': currentChar = '\r'; break;
				case 't': currentChar = '\t'; break;
				case '\"': currentChar = '\"'; break;
				case '\'': currentChar = '\''; break;
				case 'u':
					if (i >= string.length() - 5) { currentChar = 'u'; break; }
					int code = Integer.parseInt("" + string.charAt(i + 2) + string.charAt(i + 3) + string.charAt(i + 4) + string.charAt(i + 5), 16);
					stringBuilder.append(Character.toChars(code)); i += 5; continue;
			} stringBuilder.append(currentChar); i++;
		} return stringBuilder.toString();
	}
	// https://stackoverflow.com/questions/2406121/how-do-i-escape-a-string-in-java
	public static String escape(String string) {
		return string.replace("\\", "\\\\")
				.replace("\t", "\\t")
				.replace("\b", "\\b")
				.replace("\n", "\\n")
				.replace("\r", "\\r")
				.replace("\f", "\\f")
				.replace("\'", "\\'")
				.replace("\"", "\\\"");
	}
	public static String mostSafeString(String string) {
		return string.toLowerCase().replaceAll("[^A-Za-z0-9]", "_");
	}
}
