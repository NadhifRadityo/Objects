package io.github.NadhifRadityo.Objects.Library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import sun.misc.Unsafe;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static io.github.NadhifRadityo.Objects.Library.Library.debug;
import static io.github.NadhifRadityo.Objects.Library.Library.error;
import static io.github.NadhifRadityo.Objects.Library.Library.info;
import static io.github.NadhifRadityo.Objects.Library.Library.warn;

@SuppressWarnings({"DuplicatedCode", "unused"})
public class Utils {
	public static final List<String> vmArguments = Collections.unmodifiableList(ManagementFactory.getRuntimeMXBean().getInputArguments());
	protected static final ThreadLocal<WeakReference<byte[]>> tempByteArray = new ThreadLocal<>();
	protected static final ThreadLocal<WeakReference<ByteArrayOutputStream>> tempOutputBuffer = new ThreadLocal<>();
	protected static final ThreadLocal<WeakReference<ByteArrayInputStream>> tempInputBuffer = new ThreadLocal<>();
	public static final Pattern SPECIAL_REGEX_CHARS = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");
	public static final int COPY_CACHE_SIZE = 65536;

	public static Class<?> classForName(String classname) {
		try { return Class.forName(classname); } catch(Exception e) { return null; }
	}

	private static final Unsafe unsafe;
	private static final long AFIELD_ByteArrayInputStream_buf;
	private static final long AFIELD_ByteArrayInputStream_pos;
	private static final long AFIELD_ByteArrayInputStream_mark;
	private static final long AFIELD_ByteArrayInputStream_count;
	static { try {
		Field FIELD_Unsafe_theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
		FIELD_Unsafe_theUnsafe.setAccessible(true);
		unsafe = (Unsafe) FIELD_Unsafe_theUnsafe.get(null);

		Field FIELD_ByteArrayInputStream_buf = ByteArrayInputStream.class.getDeclaredField("buf");
		Field FIELD_ByteArrayInputStream_pos = ByteArrayInputStream.class.getDeclaredField("pos");
		Field FIELD_ByteArrayInputStream_mark = ByteArrayInputStream.class.getDeclaredField("mark");
		Field FIELD_ByteArrayInputStream_count = ByteArrayInputStream.class.getDeclaredField("count");
		AFIELD_ByteArrayInputStream_buf = unsafe.objectFieldOffset(FIELD_ByteArrayInputStream_buf);
		AFIELD_ByteArrayInputStream_pos = unsafe.objectFieldOffset(FIELD_ByteArrayInputStream_pos);
		AFIELD_ByteArrayInputStream_mark = unsafe.objectFieldOffset(FIELD_ByteArrayInputStream_mark);
		AFIELD_ByteArrayInputStream_count = unsafe.objectFieldOffset(FIELD_ByteArrayInputStream_count);
	} catch(Exception e) { throw new Error(e); } }
	protected static byte[] getTempByteArray(int length) {
		WeakReference<byte[]> reference = tempByteArray.get(); byte[] result = reference == null ? null : reference.get();
		if(result == null || result.length < length) { result = new byte[length]; tempByteArray.set(new WeakReference<>(result)); } return result;
	}
	protected static ByteArrayOutputStream getTempOutputBuffer() {
		ByteArrayOutputStream result = tempOutputBuffer.get() == null || tempOutputBuffer.get().get() == null ? null : tempOutputBuffer.get().get();
		if(result == null) { result = new ByteArrayOutputStream(); tempOutputBuffer.set(new WeakReference<>(result)); } return result;
	}
	protected static ByteArrayInputStream getTempInputBuffer(byte[] bytes, int off, int len) {
		ByteArrayInputStream result = tempInputBuffer.get() == null || tempInputBuffer.get().get() == null ? null : tempInputBuffer.get().get();
		if(result == null) { result = new ByteArrayInputStream(bytes, off, len); tempInputBuffer.set(new WeakReference<>(result)); }
		unsafe.putObject(result, AFIELD_ByteArrayInputStream_buf, bytes);
		unsafe.putInt(result, AFIELD_ByteArrayInputStream_pos, off);
		unsafe.putInt(result, AFIELD_ByteArrayInputStream_mark, off);
		unsafe.putInt(result, AFIELD_ByteArrayInputStream_count, Math.min(off + len, bytes.length));
		return result;
	}

	public static void copy(InputStream inputStream, OutputStream outputStream, Consumer<Long> progress) throws IOException {
		byte[] buffer = getTempByteArray(COPY_CACHE_SIZE);
		if(progress != null) progress.accept(0L);
		long length = 0; int read;
		while((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
			outputStream.write(buffer, 0, read); length += read;
			if(progress != null) progress.accept(length);
		}
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

	public static byte[] getFileBytes(File file) throws IOException { debug("Getting contents from file: %s", file.getAbsolutePath()); try(FileInputStream fis = new FileInputStream(file)) { return getBytes(fis); } }
	public static byte[] getFileBytes(String path) throws IOException { return getFileBytes(new File(path)); }
	public static String getFileString(File file, Charset charset) throws IOException { debug("Getting contents from file: %s", file.getAbsolutePath()); try(FileInputStream fis = new FileInputStream(file)) { return getString(fis, charset); } }
	public static String getFileString(String path, Charset charset) throws IOException { return getFileString(new File(path), charset); }
	public static String getFileString(File file) throws IOException { return getFileString(file, StandardCharsets.UTF_8); }
	public static String getFileString(String path) throws IOException { return getFileString(path, StandardCharsets.UTF_8); }

	public static void writeFileBytes(File file, byte[] bytes, int off, int len) throws IOException { debug("Writing contents to file: %s", file.getAbsolutePath()); try(FileOutputStream fos = new FileOutputStream(file)) { writeBytes(bytes, off, len, fos); } }
	public static void writeFileBytes(String path, byte[] bytes, int off, int len) throws IOException { writeFileBytes(new File(path), bytes, off, len); }
	public static void writeFileString(File file, String string, Charset charset) throws IOException { debug("Writing contents to file: %s", file.getAbsolutePath()); try(FileOutputStream fos = new FileOutputStream(file)) { writeString(string, fos, charset); } }
	public static void writeFileString(String path, String string, Charset charset) throws IOException { writeFileString(new File(path), string, charset); }
	public static void writeFileString(File file, String string) throws IOException { writeFileString(file, string, StandardCharsets.UTF_8); }
	public static void writeFileString(String path, String string) throws IOException { writeFileString(path, string, StandardCharsets.UTF_8); }

	public static String getFileName(String fileName) { String name = ""; try { if(fileName != null && fileName.contains(".")) name = fileName.substring(0, fileName.lastIndexOf(".")); } catch (Exception ignored) { } return name; }
	public static String getFileName(File file) { return getFileName(file != null ? file.getName() : null); }
	public static String getFileExtension(String fileName) { String extension = ""; try { if(fileName != null && fileName.contains(".")) extension = fileName.substring(fileName.lastIndexOf(".") + 1); } catch (Exception ignored) { } return extension; }
	public static String getFileExtension(File file) { return getFileExtension(file != null ? file.getName() : null); }

	public static File getCurrentClassFile(Class<?> clazz) throws Exception {
		File result = new File(URLDecoder.decode(clazz.getProtectionDomain().getCodeSource().getLocation().getPath(), StandardCharsets.UTF_8.name()));
		if(result.isDirectory()) result = new File(result, clazz.getSimpleName() + ".class"); return result;
	}
	public static File getCurrentClassFile() throws Exception {
		Class<?> currentClass = classForName(Thread.currentThread().getStackTrace()[2].getClassName());
		return currentClass == null ? null : getCurrentClassFile(currentClass);
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
	static StringBuilder downloadProgressCache;
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

		System.out.println("[INFO] " + downloadProgressCache);
		downloadProgressCache.setLength(0);
	}
	static CharacterIterator readableByteCount = new StringCharacterIterator("kMGTPE");
	public static String humanReadableByteCount(long bytes) {
		if(-1000 < bytes && bytes < 1000) return bytes + " B"; readableByteCount.setIndex(0);
		while(bytes <= -999_950 || bytes >= 999_950) { bytes /= 1000; readableByteCount.next(); }
		return String.format("%.1f %cB", bytes / 1000.0, readableByteCount.current());
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
	public static byte[] checksumJavaNative(File file, String digest) throws Exception {
		debug("Creating java checksum %s: %s", digest, file.getAbsolutePath());
		try(InputStream fileInputStream = new FileInputStream(file)) {
			MessageDigest messageDigest = MessageDigest.getInstance(digest);
			byte[] buffer = getTempByteArray(8192); int read;
			while((read = fileInputStream.read(buffer)) != -1)
				messageDigest.update(buffer, 0, read);
			return messageDigest.digest();
		}
	}
	public static byte[] checksumJavaNative(byte[] bytes, String digest) throws Exception {
		debug("Creating java checksum %s: %s", digest, bytes);
		MessageDigest messageDigest = MessageDigest.getInstance(digest);
		messageDigest.update(bytes, 0, bytes.length);
		return messageDigest.digest();
	}
	public static byte[] checksumExeCertutil(File exe, File file, String digest) throws Exception {
		debug("Creating exe certutil (%s) checksum %s: %s", exe.getAbsolutePath(), digest, file.getAbsolutePath());
		return hexStringToBytes(getCommandOutput(exe.getAbsolutePath(), "-hashfile", file.getAbsolutePath(), digest).split("\n")[1].trim());
	}
	public static byte[] checksumExeOpenssl(File exe, File file, String digest) throws Exception {
		debug("Creating exe openssl (%s) checksum %s: %s", exe.getAbsolutePath(), digest, file.getAbsolutePath());
		String prefix = digest.toUpperCase() + "(" + file.getAbsolutePath() + ")= ";
		return hexStringToBytes(getCommandOutput(exe.getAbsolutePath(), digest, file.getAbsolutePath()).substring(prefix.length()).trim());
	}
	public static byte[] checksumExeMd5sum(File exe, File file) throws Exception {
		debug("Creating exe md5sum (%s) checksum: %s", exe.getAbsolutePath(), file.getAbsolutePath());
		return hexStringToBytes(getCommandOutput(exe.getAbsolutePath(), file.getAbsolutePath()).substring(1, 33).trim());
	}
	public static byte[] checksumExeSha1sum(File exe, File file) throws Exception {
		debug("Creating exe sha1sum (%s) checksum: %s", exe.getAbsolutePath(), file.getAbsolutePath());
		return hexStringToBytes(getCommandOutput(exe.getAbsolutePath(), file.getAbsolutePath()).substring(1, 41).trim());
	}
	public static ThrowsReferencedCallback<byte[]> HASH_JAVA_NATIVE(String digest) {
		return (args) -> {
			if(args[0] instanceof File) return checksumJavaNative((File) args[0], digest);
			else if(args[0] instanceof byte[]) return checksumJavaNative((byte[]) args[0], digest);
			throw new IllegalStateException();
		};
	}
	public static ThrowsReferencedCallback<byte[]> HASH_EXE_CERTUTIL(File exe, String digest) {
		return (args) -> checksumExeCertutil(exe, (File) args[0], digest);
	}
	public static ThrowsReferencedCallback<byte[]> HASH_EXE_OPENSSL(File exe, String digest) {
		return (args) -> checksumExeOpenssl(exe, (File) args[0], digest);
	}
	public static ThrowsReferencedCallback<byte[]> HASH_EXE_MD5SUM(File exe) {
		return (args) -> checksumExeMd5sum(exe, (File) args[0]);
	}
	public static ThrowsReferencedCallback<byte[]> HASH_EXE_SHA1SUM(File exe) {
		return (args) -> checksumExeSha1sum(exe, (File) args[0]);
	}

	public static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");
	public static String getCommandOutput(File basedir, String... arguments) throws Exception {
		debug("Executing command: %s", String.join(" ", arguments));
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
		if(basedir != null) processBuilder.directory(basedir);
		Process process = processBuilder.start();
		int returnCode = process.waitFor();
		String error = getString(process.getErrorStream(), StandardCharsets.UTF_8);
		if(!error.isEmpty()) error(error); if(returnCode != 0) return null;
		return getString(process.getInputStream(), StandardCharsets.UTF_8);
	}
	public static String getCommandOutput(String... arguments) throws Exception {
		return getCommandOutput(null, arguments);
	}
	public static File searchPath(String executable) throws Exception {
		if(IS_WINDOWS) {
			String path = getCommandOutput("where", executable);
			if(path == null || path.isEmpty()) return null;
			return new File(path.trim().split("\r\n")[0]);
		}
		String path = getCommandOutput("which", executable);
		if(path == null || path.isEmpty()) return null;
		return new File(path.trim());
	}

	public static Document newXMLDocument(Object object) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		if(object instanceof InputStream) object = getString((InputStream) object, StandardCharsets.UTF_8);
		else if(object instanceof File) object = getFileString((File) object, StandardCharsets.UTF_8);
		else if(object != null) throw new IllegalArgumentException();
		return object != null ? documentBuilder.parse(new InputSource(new StringReader((String) object))) : documentBuilder.newDocument();
	}
	public static String XMLToString(Object object) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		DOMSource source;
		if(object instanceof Document) {
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2));
			source = new DOMSource(((Document) object).getDocumentElement());
		} else if(object instanceof Element) {
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("omit-xml-declaration", "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2));
			source = new DOMSource((Element) object);
		} else throw new IllegalArgumentException();

		StringWriter stringWriter = new StringWriter();
		StreamResult streamResult = new StreamResult(stringWriter);
		transformer.transform(source, streamResult);
		return stringWriter.toString();
	}
	public static String createXMLFile(Object object, File target) throws Exception {
		String stringOut = XMLToString(object);
		if(!target.exists() && !target.createNewFile())
			throw new IllegalStateException("Cannot create file!");
		writeFileString(target, stringOut, StandardCharsets.UTF_8);
		info("Configurations written to: %s", target.getAbsolutePath());
		return stringOut;
	}

	public static <T> T toJson(Reader reader, Class<T> clazz) { return new Gson().fromJson(reader, clazz); }
	public static <T> T toJson(InputStream stream, Class<T> clazz) { return new Gson().fromJson(new InputStreamReader(stream), clazz); }
	public static <T> T toJson(String string, Class<T> clazz) { return new Gson().fromJson(string, clazz); }
	public static <T> String JSONToString(T object) throws IOException {
		try(StringWriter stringWriter = new StringWriter(); JsonWriter jsonWriter = new JsonWriter(stringWriter)) {
			jsonWriter.setIndent("\t");
			new GsonBuilder().disableHtmlEscaping().create()
					.toJson(object, object.getClass(), jsonWriter);
			return stringWriter.toString();
		}
	}
	public static <T> String createJSONFile(T object, File target) throws Exception {
		String stringOut = JSONToString(object);
		if(!target.exists() && !target.createNewFile())
			throw new IllegalStateException("Cannot create file!");
		writeFileString(target, stringOut, StandardCharsets.UTF_8);
		info("Configurations written to: %s", target.getAbsolutePath());
		return stringOut;
	}

	private static final long AFIELD_Properties_defaults;
	static { try {
		Field FIELD_Properties_defaults = Properties.class.getDeclaredField("defaults");
		AFIELD_Properties_defaults = unsafe.objectFieldOffset(FIELD_Properties_defaults);
	} catch(Exception e) { throw new Error(e); } }
	public static Properties __get_defaults_properties(Properties properties) {
		return (Properties) unsafe.getObject(properties, AFIELD_Properties_defaults);
	}
	public static Properties extendProperties(Properties original, Properties extend, boolean nullable) {
		if(nullable && sizeNonDefaultProperties(original) == 0 && sizeAllProperties(extend) == 0)
			return null;
		Properties properties = new Properties(extend);
		if(original != null)
			properties.putAll(original);
		return properties;
	}
	public static Properties copyAllProperties(Properties properties, boolean nullable) {
		if(nullable && sizeAllProperties(properties) == 0)
			return null;
		Properties result = new Properties();
		enumerateAllProperties(properties, result);
		return result;
	}
	public static Properties copyNonDefaultProperties(Properties properties, boolean nullable) {
		if(nullable && sizeNonDefaultProperties(properties) == 0)
			return null;
		Properties result = new Properties();
		enumerateNonDefaultProperties(properties, result);
		return result;
	}
	public static void enumerateAllProperties(Properties properties, Hashtable<Object, Object> hashtable) {
		if(properties == null) return;
		Properties defaults = __get_defaults_properties(properties);
		if(defaults != null) enumerateAllProperties(defaults, hashtable);
		enumerateNonDefaultProperties(properties, hashtable);
	}
	public static void enumerateNonDefaultProperties(Properties properties, Hashtable<Object, Object> hashtable) {
		if(properties == null) return;
		Enumeration<Object> enumeration = properties.keys();
		while(enumeration.hasMoreElements()) {
			Object key = enumeration.nextElement();
			Object value = properties.get(key);
			hashtable.put(key, value);
		}
	}
	public static int sizeAllProperties(Properties properties) {
		if(properties == null) return 0;
		Properties defaults = __get_defaults_properties(properties);
		return (defaults != null ? sizeAllProperties(defaults) : 0) + sizeNonDefaultProperties(properties);
	}
	public static int sizeNonDefaultProperties(Properties properties) {
		return properties != null ? properties.size() : 0;
	}
	public static <T> T pn_getObject(Properties properties, String key, Class<T> type, T defaultValue) {
		Object object = properties.get(key);
		return type.isInstance(object) ? (T) object : defaultValue;
	}
	public static <T> T pn_getObject(Properties properties, String key, T defaultValue) { return (T) pn_getObject(properties, key, Object.class, defaultValue); }
	public static byte pn_getByte(Properties properties, String key, byte defaultValue) { return pn_getObject(properties, key, Byte.class, defaultValue); }
	public static boolean pn_getBoolean(Properties properties, String key, boolean defaultValue) { return pn_getObject(properties, key, Boolean.class, defaultValue); }
	public static char pn_getChar(Properties properties, String key, char defaultValue) { return pn_getObject(properties, key, Character.class, defaultValue); }
	public static short pn_getShort(Properties properties, String key, short defaultValue) { return pn_getObject(properties, key, Short.class, defaultValue); }
	public static int pn_getInt(Properties properties, String key, int defaultValue) { return pn_getObject(properties, key, Integer.class, defaultValue); }
	public static long pn_getLong(Properties properties, String key, long defaultValue) { return pn_getObject(properties, key, Long.class, defaultValue); }
	public static float pn_getFloat(Properties properties, String key, float defaultValue) { return pn_getObject(properties, key, Float.class, defaultValue); }
	public static double pn_getDouble(Properties properties, String key, double defaultValue) { return pn_getObject(properties, key, Double.class, defaultValue); }
	public static <T> T pn_getObject(Properties properties, String key, Class<T> type) { return pn_getObject(properties, key, type, null); }
	public static <T> T pn_getObject(Properties properties, String key) { return (T) pn_getObject(properties, key, Object.class, null); }
	public static byte pn_getByte(Properties properties, String key) { return pn_getByte(properties, key, (byte) 0); }
	public static boolean pn_getBoolean(Properties properties, String key) { return pn_getBoolean(properties, key, false); }
	public static char pn_getChar(Properties properties, String key) { return pn_getChar(properties, key, (char) 0); }
	public static short pn_getShort(Properties properties, String key) { return pn_getShort(properties, key, (short) 0); }
	public static int pn_getInt(Properties properties, String key) { return pn_getInt(properties, key, 0); }
	public static long pn_getLong(Properties properties, String key) { return pn_getLong(properties, key, 0); }
	public static float pn_getFloat(Properties properties, String key) { return pn_getFloat(properties, key, 0); }
	public static double pn_getDouble(Properties properties, String key) { return pn_getDouble(properties, key, 0); }
	public static <T> T p_getObject(Properties properties, String key, Class<T> type, T defaultValue) {
		Object object = properties.get(key);
		T castedObject = type.isInstance(object) ? (T) object : null;
		if(castedObject != null) return castedObject;
		Properties defaults = __get_defaults_properties(properties);
		if(defaults == null) return defaultValue;
		return p_getObject(defaults, key, type, defaultValue);
	}
	public static <T> T p_getObject(Properties properties, String key, T defaultValue) { return (T) p_getObject(properties, key, Object.class, defaultValue); }
	public static byte p_getByte(Properties properties, String key, byte defaultValue) { return p_getObject(properties, key, Byte.class, defaultValue); }
	public static boolean p_getBoolean(Properties properties, String key, boolean defaultValue) { return p_getObject(properties, key, Boolean.class, defaultValue); }
	public static char p_getChar(Properties properties, String key, char defaultValue) { return p_getObject(properties, key, Character.class, defaultValue); }
	public static short p_getShort(Properties properties, String key, short defaultValue) { return p_getObject(properties, key, Short.class, defaultValue); }
	public static int p_getInt(Properties properties, String key, int defaultValue) { return p_getObject(properties, key, Integer.class, defaultValue); }
	public static long p_getLong(Properties properties, String key, long defaultValue) { return p_getObject(properties, key, Long.class, defaultValue); }
	public static float p_getFloat(Properties properties, String key, float defaultValue) { return p_getObject(properties, key, Float.class, defaultValue); }
	public static double p_getDouble(Properties properties, String key, double defaultValue) { return p_getObject(properties, key, Double.class, defaultValue); }
	public static <T> T p_getObject(Properties properties, String key, Class<T> type) { return p_getObject(properties, key, type, null); }
	public static <T> T p_getObject(Properties properties, String key) { return (T) p_getObject(properties, key, Object.class, null); }
	public static byte p_getByte(Properties properties, String key) { return p_getByte(properties, key, (byte) 0); }
	public static boolean p_getBoolean(Properties properties, String key) { return p_getBoolean(properties, key, false); }
	public static char p_getChar(Properties properties, String key) { return p_getChar(properties, key, (char) 0); }
	public static short p_getShort(Properties properties, String key) { return p_getShort(properties, key, (short) 0); }
	public static int p_getInt(Properties properties, String key) { return p_getInt(properties, key, 0); }
	public static long p_getLong(Properties properties, String key) { return p_getLong(properties, key, 0); }
	public static float p_getFloat(Properties properties, String key) { return p_getFloat(properties, key, 0); }
	public static double p_getDouble(Properties properties, String key) { return p_getDouble(properties, key, 0); }
	public static <T> void p_setObject(Properties properties, String key, Class<T> type, T value) { if(key == null || value == null) return; properties.put(key, value); }
	public static <T> void p_setObject(Properties properties, String key, T value) { p_setObject(properties, key, Object.class, value); }
	public static void p_setByte(Properties properties, String key, byte value) { p_setObject(properties, key, Byte.class, value); }
	public static void p_setBoolean(Properties properties, String key, boolean value) { p_setObject(properties, key, Boolean.class, value); }
	public static void p_setChar(Properties properties, String key, char value) { p_setObject(properties, key, Character.class, value); }
	public static void p_setShort(Properties properties, String key, short value) { p_setObject(properties, key, Short.class, value); }
	public static void p_setInt(Properties properties, String key, int value) { p_setObject(properties, key, Integer.class, value); }
	public static void p_setLong(Properties properties, String key, long value) { p_setObject(properties, key, Long.class, value); }
	public static void p_setFloat(Properties properties, String key, float value) { p_setObject(properties, key, Float.class, value); }
	public static void p_setDouble(Properties properties, String key, double value) { p_setObject(properties, key, Double.class, value); }

	public static URI urlToUri(URL url) throws URISyntaxException { return new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef()); }
	public static String formattedUrl(String url) throws MalformedURLException, URISyntaxException { return urlToUri(new URL(url)).toASCIIString(); }

	public static final ThrowsReferencedCallback<Object> javascriptGraalVM;
	public static final ThrowsReferencedCallback<Object> javascriptNashorn;
	static { try {
		Class<?> CLASS_GRAALVM_Context = classForName("org.graalvm.polyglot.Context");
		javascriptGraalVM = CLASS_GRAALVM_Context == null ? null : new ThrowsReferencedCallback<Object>() {
			final Class<?> CLASS_GRAALVM_Value = classForName("org.graalvm.polyglot.Value");
			final Method METHOD_GRAALVM_Context_create = CLASS_GRAALVM_Context.getMethod("create", String[].class);
			final Method METHOD_GRAALVM_Context_eval = CLASS_GRAALVM_Context.getMethod("eval", String.class, CharSequence.class);
			final Method METHOD_GRAALVM_Value_execute = CLASS_GRAALVM_Value.getMethod("execute", Object[].class);
			final Map<Method, Method> convertToJavaType = new HashMap<>();
			{
				Method[] methods = CLASS_GRAALVM_Value.getMethods();
				for(Method method : methods) {
					String name = method.getName();
					if(!name.startsWith("is")) continue;
					if(method.getParameterCount() != 0) continue;
					Class<?> returnType = method.getReturnType();
					if(!returnType.equals(boolean.class) && !returnType.equals(Boolean.class))
						continue;
					Method converter = null;
					try { converter = CLASS_GRAALVM_Value.getMethod("as" + name.replaceFirst("is", ""));
					} catch(Exception ignored) { } if(converter == null) continue;
					convertToJavaType.put(method, converter);
				}
			}
			@Override public Object get(Object... ___) throws Exception {
				String source = (String) ___[0]; Object[] args = (Object[]) ___[1];
				Object context = METHOD_GRAALVM_Context_create.invoke(null, (Object) new String[0]);
				try(AutoCloseable __ = (AutoCloseable) context) {
					Object function = METHOD_GRAALVM_Context_eval.invoke(context, "js", source);
					Object value = METHOD_GRAALVM_Value_execute.invoke(function, (Object) args);
					for(Map.Entry<Method, Method> convert : convertToJavaType.entrySet()) {
						boolean typeCorrect = false;
						try { typeCorrect = (boolean) convert.getKey().invoke(value); } catch(Exception ignored) { }
						if(!typeCorrect) continue;
						return convert.getValue().invoke(value);
					}
					warn("GraalVM object not found! value=%s", value);
					return value;
				}
			}
		};

		Class<?> CLASS_NASHORN_ScriptEngineManager = classForName("javax.script.ScriptEngineManager");
		javascriptNashorn = CLASS_NASHORN_ScriptEngineManager == null ? null : new ThrowsReferencedCallback<Object>() {
			final Class<?> CLASS_NASHORN_ScriptEngine = classForName("javax.script.ScriptEngine");
			final Class<?> CLASS_NASHORN_JSObject = classForName("jdk.nashorn.api.scripting.JSObject");
			final Constructor<?> CONSTRUCTOR_NASHORN_ScriptEngineManager = CLASS_NASHORN_ScriptEngineManager.getConstructor();
			final Method METHOD_NASHORN_ScriptEngineManager_getEngineByName = CLASS_NASHORN_ScriptEngineManager.getMethod("getEngineByName", String.class);
			final Method METHOD_NASHORN_ScriptEngine_eval = CLASS_NASHORN_ScriptEngine.getMethod("eval", String.class);
			final Method METHOD_NASHORN_JSObject_call = CLASS_NASHORN_JSObject.getMethod("call", Object.class, Object[].class);
			@Override public Object get(Object... ___) throws Exception {
				String source = (String) ___[0]; Object[] args = (Object[]) ___[1];
				Object engine = CONSTRUCTOR_NASHORN_ScriptEngineManager.newInstance();
				engine = METHOD_NASHORN_ScriptEngineManager_getEngineByName.invoke(engine, "nashorn");
				Object function = METHOD_NASHORN_ScriptEngine_eval.invoke(engine, source);
				return METHOD_NASHORN_JSObject_call.invoke(function, null, args);
			}
		};
		if(CLASS_NASHORN_ScriptEngineManager != null) {
			boolean isEs6 = false;
			for(String arg : vmArguments) {
				if(!arg.equals("-Dnashorn.args=--language=es6")) continue;
				isEs6 = true; break;
			}
			if(!isEs6)
				warn("Error may occur, Please add \"-Dnashorn.args=--language=es6\" to your JVM arguments");
		}
	} catch(Exception e) { throw new Error(e); } }
	public static Object runJavascript(String source, Object... args) throws Exception {
		if(javascriptGraalVM != null) {
			debug("Running javascript (GraalVM)");
			return javascriptGraalVM.get(source, args);
		}
		if(javascriptNashorn != null) {
			debug("Running javascript (Nashorn)");
			return javascriptNashorn.get(source, args);
		}
		throw new UnsupportedOperationException("Supported javascript runtime is not available!");
	}

	public static String throwableToString(Throwable throwable) {
		try(StringWriter stringWriter = new StringWriter(); PrintWriter printWriter = new PrintWriter(stringWriter)) {
			throwable.printStackTrace(printWriter);
			return stringWriter.toString();
		} catch(IOException e) {
			throw new Error(e);
		}
	}

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
}