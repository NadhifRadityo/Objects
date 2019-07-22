package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

public class SecurityUtils {
	private SecurityUtils() {
		
	}

	public static String doDigest(String algorithm, byte[] bytes) { try {
		MessageDigest md = MessageDigest.getInstance(algorithm); byte[] digest = md.digest(bytes);
		StringBuilder result = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
		try { for(byte b : digest) result.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3)); // Is substring redundant?
			return result.toString(); } finally { Pool.returnObject(StringBuilder.class, result); }
	} catch (NoSuchAlgorithmException e) { throw new Error(e); } }

	public static String MD5Digest(byte[] bytes) { return doDigest("MD5", bytes); }
	public static String getMD5(String string) { return MD5Digest(string.getBytes()); }
	public static String getMD5(File file) throws IOException { return MD5Digest(FileUtils.getFileBytes(file)); }
	
	public static String SHA256Digest(byte[] bytes) { return doDigest("SHA-256", bytes); }
	public static String getSHA256(String string) { return SHA256Digest(string.getBytes()); }
	public static String getSHA256(File file) throws IOException { return SHA256Digest(FileUtils.getFileBytes(file)); }

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
