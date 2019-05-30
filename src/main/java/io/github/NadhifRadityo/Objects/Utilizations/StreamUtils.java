package io.github.NadhifRadityo.Objects.Utilizations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class StreamUtils {
	private StreamUtils() {
		
	}
	
	public static String toString(InputStream inputStream) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
			String inputLine; StringBuilder stringBuilder = new StringBuilder();
			while ((inputLine = bufferedReader.readLine()) != null)
				stringBuilder.append(inputLine);
			bufferedReader.close();
			return stringBuilder.toString();
		}
	}
}
