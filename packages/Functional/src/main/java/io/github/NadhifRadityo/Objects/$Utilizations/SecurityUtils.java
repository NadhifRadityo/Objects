package io.github.NadhifRadityo.Objects.$Utilizations;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class SecurityUtils {

	protected static final ThreadLocal<WeakReference<Map<String, MessageDigest>>> algorithms = new ThreadLocal<>();
	protected static final Set<String> availableAlgorithms;
	protected static final Map<String, String> algorithmAliases;

	static {
		availableAlgorithms = Collections.unmodifiableSet(Arrays.stream(Security.getProviders())
				.map(p -> p.getServices().stream()
						.filter(s -> s.getType().equalsIgnoreCase("MessageDigest"))
						.map(Provider.Service::getAlgorithm).collect(Collectors.toList()))
				.flatMap(Collection::stream).collect(Collectors.toSet()));
		HashMap<String, String> _algorithmAliases = new HashMap<>();
		for(Provider provider : Security.getProviders())
			_algorithmAliases.putAll(provider.keySet().stream().filter(k -> k.toString().startsWith("Alg.Alias.MessageDigest."))
					.collect(Collectors.toMap(o -> o.toString().substring(24), o -> provider.get(o.toString()).toString())));
		algorithmAliases = Collections.unmodifiableMap(_algorithmAliases);
	}

	protected static MessageDigest getAlgorithm(String algorithm) {
		Map<String, MessageDigest> map = algorithms.get() != null ? algorithms.get().get() : null;
		if(map == null) { map = new HashMap<>(); algorithms.set(new WeakReference<>(map)); }
		MessageDigest result = map.get(algorithm); if(result == null) {
			try { result = MessageDigest.getInstance(algorithm);
			} catch(NoSuchAlgorithmException e) { throw new Error(e); }
			map.put(algorithm, result); }
		result.reset(); return result;
	}

	public static Set<String> getAvailableAlgorithms() { return availableAlgorithms; }
	public static boolean isAlgorithmAvailable(String algorithm) { return availableAlgorithms.contains(algorithm); }
	public static Map<String, String> getAlgorithmAliases() { return algorithmAliases; }

	public static byte[] doDigest(String algorithm, byte[] bytes) { return getAlgorithm(algorithm).digest(bytes); }
	public static String doDigestString(String algorithm, byte[] bytes) { return ByteUtils.toHex(doDigest(algorithm, bytes)); }

	public static byte[] MD2Digest(byte[] bytes) { return doDigest("MD2", bytes); }
	public static byte[] getMD2(String string) { return MD2Digest(string.getBytes()); }
	public static byte[] getMD2(File file) throws IOException { return MD2Digest(FileUtils.getFileBytes(file)); }
	public static String MD2DigestString(byte[] bytes) { return doDigestString("MD2", bytes); }
	public static String getMD2String(String string) { return MD2DigestString(string.getBytes()); }
	public static String getMD2String(File file) throws IOException { return MD2DigestString(FileUtils.getFileBytes(file)); }

	public static byte[] MD5Digest(byte[] bytes) { return doDigest("MD5", bytes); }
	public static byte[] getMD5(String string) { return MD5Digest(string.getBytes()); }
	public static byte[] getMD5(File file) throws IOException { return MD5Digest(FileUtils.getFileBytes(file)); }
	public static String MD5DigestString(byte[] bytes) { return doDigestString("MD5", bytes); }
	public static String getMD5String(String string) { return MD5DigestString(string.getBytes()); }
	public static String getMD5String(File file) throws IOException { return MD5DigestString(FileUtils.getFileBytes(file)); }

	public static byte[] SHA1Digest(byte[] bytes) { return doDigest("SHA-1", bytes); }
	public static byte[] getSHA1(String string) { return SHA1Digest(string.getBytes()); }
	public static byte[] getSHA1(File file) throws IOException { return SHA1Digest(FileUtils.getFileBytes(file)); }
	public static String SHA1DigestString(byte[] bytes) { return doDigestString("SHA-1", bytes); }
	public static String getSHA1String(String string) { return SHA1DigestString(string.getBytes()); }
	public static String getSHA1String(File file) throws IOException { return SHA1DigestString(FileUtils.getFileBytes(file)); }

	public static byte[] SHA256Digest(byte[] bytes) { return doDigest("SHA-256", bytes); }
	public static byte[] getSHA256(String string) { return SHA256Digest(string.getBytes()); }
	public static byte[] getSHA256(File file) throws IOException { return SHA256Digest(FileUtils.getFileBytes(file)); }
	public static String SHA256DigestString(byte[] bytes) { return doDigestString("SHA-256", bytes); }
	public static String getSHA256String(String string) { return SHA256DigestString(string.getBytes()); }
	public static String getSHA256String(File file) throws IOException { return SHA256DigestString(FileUtils.getFileBytes(file)); }

	public static byte[] SHA512Digest(byte[] bytes) { return doDigest("SHA-512", bytes); }
	public static byte[] getSHA512(String string) { return SHA512Digest(string.getBytes()); }
	public static byte[] getSHA512(File file) throws IOException { return SHA512Digest(FileUtils.getFileBytes(file)); }
	public static String SHA512DigestString(byte[] bytes) { return doDigestString("SHA-512", bytes); }
	public static String getSHA512String(String string) { return SHA512DigestString(string.getBytes()); }
	public static String getSHA512String(File file) throws IOException { return SHA512DigestString(FileUtils.getFileBytes(file)); }

	public static UUID getUUID(byte[] from) {
		assert from.length == 16 : "data must be 16 bytes in length";
		from[6] &= 0x0f; from[6] |= 0x40;
		from[8] &= 0x3f; from[8] |= 0x80;
		long msb = 0; long lsb = 0;
		for (int i = 0; i < 8; i++) msb = (msb << 8) | (from[i] & 0xff);
		for (int i = 8; i < 16; i++) lsb = (lsb << 8) | (from[i] & 0xff);
		return new UUID(msb, lsb);
	} public static UUID getRandomUUID(Random random) { byte[] from = new byte[16]; random.nextBytes(from); return getUUID(from); }
	public static UUID getRandomUUID() { return getRandomUUID(PublicRandom.getRandom()); }
}
