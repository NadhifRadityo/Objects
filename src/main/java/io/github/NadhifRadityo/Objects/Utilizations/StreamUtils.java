package io.github.NadhifRadityo.Objects.Utilizations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StreamUtils {
	public static final byte[] EOF = new byte[0];
	protected static final ThreadLocal<WeakReference<ByteArrayOutputStream>> tempBuffer = new ThreadLocal<>();

	protected static ByteArrayOutputStream getTempBuffer() {
		ByteArrayOutputStream result = tempBuffer.get() == null || tempBuffer.get().get() == null ? null : tempBuffer.get().get();
		if(result == null) { result = new ByteArrayOutputStream(); tempBuffer.set(new WeakReference<>(result)); } return result;
	}

	public static byte[] getBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream result = getTempBuffer();
		byte[] buffer = ArrayUtils.getTempByteArray(8192); int length; try {
		while((length = inputStream.read(buffer)) != -1)
			result.write(buffer, 0, length);
		result.close(); return result.toByteArray();
	} finally { result.reset(); } }

	public static String toString(InputStream inputStream, Charset charset) throws IOException { return new String(getBytes(inputStream), charset); }
	@Deprecated public static String toString(InputStream inputStream) throws IOException { return toString(inputStream, StandardCharsets.UTF_8); }
}
