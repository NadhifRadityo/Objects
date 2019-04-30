package io.github.NadhifRadityo.Objects.Utilizations;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {
	private SecurityUtils() {
		
	}

	public static String MD5Digest(byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(bytes);
			StringBuffer result = new StringBuffer();
			for(int i = 0; i < digest.length; i++)
				result.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
			return result.toString();
		} catch (NoSuchAlgorithmException ignore) { }
		return null;
	}

	public static String getMD5(String string) { return MD5Digest(string.getBytes()); }
	public static String getMD5(File file) {
		byte[] buf = new byte[8192];
		int length;
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((length = bis.read(buf)) != -1)
				baos.write(buf, 0, length);
			fis.close(); bis.close(); baos.close();
			return MD5Digest(baos.toByteArray());
		} catch (Exception e) { }
		return null;
	}
}
