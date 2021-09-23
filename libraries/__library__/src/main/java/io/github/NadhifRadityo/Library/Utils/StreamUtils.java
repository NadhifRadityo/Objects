package io.github.NadhifRadityo.Library.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.function.Consumer;

import static io.github.NadhifRadityo.Library.Utils.CommonUtils.newStreamProgress;
import static io.github.NadhifRadityo.Library.Utils.UnsafeUtils.getTempByteArray;
import static io.github.NadhifRadityo.Library.Utils.UnsafeUtils.getTempInputBuffer;
import static io.github.NadhifRadityo.Library.Utils.UnsafeUtils.getTempOutputBuffer;

public class StreamUtils {
	public static final int COPY_CACHE_SIZE = 65536;

	@groovy.transform.ThreadInterrupt
	public static void copy(InputStream inputStream, OutputStream outputStream, Consumer<Long> progress) throws IOException {
		byte[] buffer = getTempByteArray(COPY_CACHE_SIZE);
		if(progress != null) progress.accept(-1L);
		long length = 0; int read;
		while(!Thread.currentThread().isInterrupted() &&
				(read = inputStream.read(buffer, 0, buffer.length)) != -1) {
			outputStream.write(buffer, 0, read); length += read;
			if(progress != null) progress.accept(length);
		}
		if(progress != null) progress.accept(-2L);
		if(Thread.currentThread().isInterrupted())
			throw new InterruptedIOException();
	}
	public static byte[] getBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream outputStream = getTempOutputBuffer();
		long totalSize = inputStream instanceof FileInputStream ? ((FileInputStream) inputStream).getChannel().size() : 0;
		Consumer<Long> progress = totalSize >= 1000 * 1000 * 10 ? newStreamProgress(totalSize) : null;
		try { copy(inputStream, outputStream, progress); outputStream.close(); return outputStream.toByteArray(); } finally { outputStream.reset(); }
	}
	public static void writeBytes(byte[] input, int off, int len, OutputStream outputStream) throws IOException {
		ByteArrayInputStream inputStream = getTempInputBuffer(input, off, len);
		Consumer<Long> progress = len >= 1000 * 1000 * 10 ? newStreamProgress(len) : null;
		try { copy(inputStream, outputStream, progress); inputStream.close(); } finally { inputStream.reset(); }
	}

	public static String getString(InputStream inputStream, Charset charset) throws IOException { return new String(getBytes(inputStream), charset); }
	public static void writeString(String string, OutputStream outputStream, Charset charset) throws IOException { byte[] bytes = string.getBytes(charset); writeBytes(bytes, 0, bytes.length, outputStream); }
}
