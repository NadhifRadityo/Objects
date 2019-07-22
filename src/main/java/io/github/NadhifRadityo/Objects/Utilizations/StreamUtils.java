package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class StreamUtils {
	private StreamUtils() {
		
	}
	
	public static String toString(InputStream inputStream) throws IOException {
		StringBuilder stringBuilder = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			String inputLine; while((inputLine = bufferedReader.readLine()) != null)
				stringBuilder.append(inputLine); bufferedReader.close();
			return stringBuilder.toString();
		} finally { Pool.returnObject(StringBuilder.class, stringBuilder); }
	}
}
