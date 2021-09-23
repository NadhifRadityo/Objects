package io.github.NadhifRadityo.Library.Utils;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.debug;
import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.info;
import static io.github.NadhifRadityo.Library.Utils.StreamUtils.copy;

public class CommonUtils {
	private static StringBuilder downloadProgressCache;
	protected static CharacterIterator readableByteCount = new StringCharacterIterator("kMGTPE");

	public static URI urlToUri(URL url) throws URISyntaxException { return new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef()); }
	public static String formattedUrl(String url) throws MalformedURLException, URISyntaxException { return urlToUri(new URL(url)).toASCIIString(); }

	public static String bytesToHexString(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for(byte aByte : bytes)
			builder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
		return builder.toString();
	}
	public static byte[] hexStringToBytes(String string) {
		int length = string.length();
		byte[] bytes = new byte[length / 2];
		for(int i = 0; i < length; i += 2)
			bytes[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4)
					+ Character.digit(string.charAt(i + 1), 16));
		return bytes;
	}

	public static void downloadFile(URL url, OutputStream outputStream) throws Exception {
		String source = Paths.get(new URI(url.toString()).getPath()).getFileName().toString();
		debug("Starting to download: %s", source);
		URLConnection connection = url.openConnection();
		long completeFileSize = connection.getContentLength();
		try(BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream())) {
			info("Downloading %s... (%s)", source, url.toString());
			copy(inputStream, outputStream, newStreamProgress(completeFileSize));
		}
	}
	public static Consumer<Long> newStreamProgress(long totalSize) {
		return new Consumer<Long>() {
			long startTime = 0;
			long lastTime = 0;
			long lastLength = 0;
			long lastPrint = 0;
			long speed = 0;
			long[] speeds = null;
			int speedsIndex = 0;
			public void accept(Long length) {
				if(speeds == null) {
					startTime = System.currentTimeMillis();
					lastTime = System.currentTimeMillis();
					speeds = new long[30];
					Arrays.fill(speeds, -1L);
				}
				float alpha = 0.9f;

				long now = System.currentTimeMillis();
				long deltaTime = Math.max(1, now - lastTime);
				long deltaLength = length - lastLength;
				speeds[speedsIndex++] = deltaLength * 1000 / deltaTime;
				if(speedsIndex >= speeds.length) speedsIndex = 0;
				lastTime = now; lastLength = length;
				if(now - lastPrint < 1000) return;

				long result = 0;
				long[] values = speeds;
				int counter = 0;
				for(long value : values) {
					if(value == -1) continue; counter++;
					result += value;
				} result /= counter;

				speed = (long) (speed * alpha) + (long) (result * (1 - alpha));
				printDownloadProgress(startTime, totalSize, length, speed);
				lastPrint = now;
			}
		};
	}
	public static void printDownloadProgress(long startTime, long total, long current, long speed) {
		if(downloadProgressCache == null)
			downloadProgressCache = new StringBuilder(200);
		if(total < 0) return;

		long eta = speed == 0 ? Long.MAX_VALUE : (total - current) * 1000 / speed;
		String etaHms = speed == 0 ? "N/A" :
				String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
						TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
						TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

		int percent = total == 0 ? 0 : (int) (current * 100 / total);
		downloadProgressCache
//				.append('\r')
				.append(String.join("", Collections.nCopies(2 - (percent == 0 ? 0 : (int) Math.log10(percent)), " ")))
				.append(String.format(" %d%% [", percent))
				.append(String.join("", Collections.nCopies(percent, "=")))
				.append('>')
				.append(String.join("", Collections.nCopies(100 - percent, " ")))
				.append(']')
				.append(String.join("", Collections.nCopies((int) Math.log10(total) - (current == 0 ? 0 : (int) Math.log10(current)), " ")))
				.append(String.format(" %d/%d, ETA: %s, Speed: %s/s", current, total, etaHms, humanReadableByteCount(speed)));

		info(downloadProgressCache.toString());
		downloadProgressCache.setLength(0);
	}
	public static String humanReadableByteCount(long bytes) {
		if(-1000 < bytes && bytes < 1000) return bytes + " B"; readableByteCount.setIndex(0);
		while(bytes <= -999_950 || bytes >= 999_950) { bytes /= 1000; readableByteCount.next(); }
		return String.format("%.1f %cB", bytes / 1000.0, readableByteCount.current());
	}
}
