package io.github.NadhifRadityo.Library.Utils;

import io.github.NadhifRadityo.Library.ThrowsReferencedCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import static io.github.NadhifRadityo.Library.Utils.CommonUtils.bytesToHexString;
import static io.github.NadhifRadityo.Library.Utils.CommonUtils.hexStringToBytes;
import static io.github.NadhifRadityo.Library.Utils.FileUtils.getFileString;
import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.debug;
import static io.github.NadhifRadityo.Library.Utils.ProcessUtils.getCommandOutput;
import static io.github.NadhifRadityo.Library.Utils.ProgressUtils.progress;
import static io.github.NadhifRadityo.Library.Utils.ProgressUtils.progress_id;
import static io.github.NadhifRadityo.Library.Utils.UnsafeUtils.getTempByteArray;

public class HashUtils {

	public static byte[] checksumJavaNative(File file, String digest) throws Exception {
		try(ProgressUtils.ProgressWrapper prog0 = progress(progress_id(file, digest))) {
			prog0.inherit();
			prog0.setCategory(HashUtils.class);
			prog0.setDescription("Creating java checksum");
			prog0.pstart();

			prog0.pdo(String.format("Checksum Java (%s) %s", digest, file.getPath()));
			debug("Creating java checksum %s: %s", digest, file.getPath());
			try(InputStream fileInputStream = new FileInputStream(file)) {
				MessageDigest messageDigest = MessageDigest.getInstance(digest);
				byte[] buffer = getTempByteArray(8192); int read;
				while((read = fileInputStream.read(buffer)) != -1)
					messageDigest.update(buffer, 0, read);
				return messageDigest.digest();
			}
		}
	}
	public static byte[] checksumJavaNative(byte[] bytes, String digest) throws Exception {
		try(ProgressUtils.ProgressWrapper prog0 = progress(progress_id(bytes, digest))) {
			prog0.inherit();
			prog0.setCategory(HashUtils.class);
			prog0.setDescription("Creating java checksum");
			prog0.pstart();

			prog0.pdo(String.format("Checksum Java (%s) %s", digest, "[Array]"));
			debug("Creating java checksum %s: %s", digest, "[Array]");
			MessageDigest messageDigest = MessageDigest.getInstance(digest);
			messageDigest.update(bytes, 0, bytes.length);
			return messageDigest.digest();
		}
	}
	public static byte[] checksumExeCertutil(File exe, File file, String digest) throws Exception {
		try(ProgressUtils.ProgressWrapper prog0 = progress(progress_id(exe, file, digest))) {
			prog0.inherit();
			prog0.setCategory(HashUtils.class);
			prog0.setDescription("Creating certutil checksum");
			prog0.pstart();

			prog0.pdo(String.format("Checksum certutil (%s) %s", digest, file.getPath()));
			debug("Creating exe certutil (%s) checksum %s: %s", exe.getPath(), digest, file.getPath());
			return hexStringToBytes(getCommandOutput(exe.getCanonicalPath(), "-hashfile", file.getCanonicalPath(), digest).split("\n")[1].trim());
		}
	}
	public static byte[] checksumExeOpenssl(File exe, File file, String digest) throws Exception {
		try(ProgressUtils.ProgressWrapper prog0 = progress(progress_id(exe, file, digest))) {
			prog0.inherit();
			prog0.setCategory(HashUtils.class);
			prog0.setDescription("Creating openssl checksum");
			prog0.pstart();

			prog0.pdo(String.format("Checksum openssl (%s) %s", digest, file.getPath()));
			debug("Creating exe openssl (%s) checksum %s: %s", exe.getPath(), digest, file.getPath());
			String prefix = digest.toUpperCase() + "(" + file.getCanonicalPath() + ")= ";
			return hexStringToBytes(getCommandOutput(exe.getCanonicalPath(), digest, file.getCanonicalPath()).substring(prefix.length()).trim());
		}
	}
	public static byte[] checksumExeMdNsum(File exe, File file, int N) throws Exception {
		try(ProgressUtils.ProgressWrapper prog0 = progress(progress_id(exe, file, N))) {
			prog0.inherit();
			prog0.setCategory(HashUtils.class);
			prog0.setDescription("Creating mdnsum checksum");
			prog0.pstart();

			prog0.pdo(String.format("Checksum md%dsum %s", N, file.getPath()));
			debug("Creating exe md%ssum (%s) checksum: %s", N, exe.getPath(), file.getPath());
			return hexStringToBytes(getCommandOutput(exe.getCanonicalPath(), file.getCanonicalPath()).substring(1, 33).trim());
		}
	}
	public static byte[] checksumExeShaNsum(File exe, File file, int N) throws Exception {
		try(ProgressUtils.ProgressWrapper prog0 = progress(progress_id(exe, file, N))) {
			prog0.inherit();
			prog0.setCategory(HashUtils.class);
			prog0.setDescription("Creating shansum checksum");
			prog0.pstart();

			prog0.pdo(String.format("Checksum sha%dsum %s", N, file.getPath()));
			debug("Creating exe sha%ssum (%s) checksum: %s", N, exe.getPath(), file.getPath());
			return hexStringToBytes(getCommandOutput(exe.getCanonicalPath(), file.getCanonicalPath()).substring(1, 41).trim());
		}
	}

	protected static String getHashExpected(Object object) throws Exception {
		String expected;
		if(object instanceof String) expected = (String) object;
		else if(object instanceof byte[]) expected = bytesToHexString((byte[]) object);
		else if(object instanceof File) expected = getFileString((File) object);
		else throw new IllegalStateException();
		return expected;
	}
	public static ThrowsReferencedCallback<Boolean> HASH_JAVA_NATIVE(String digest) {
		return (args) -> {
			String generated;
			if(args[0] instanceof File) generated = bytesToHexString(checksumJavaNative((File) args[0], digest));
			else if(args[0] instanceof byte[]) generated = bytesToHexString(checksumJavaNative((byte[]) args[0], digest));
			else throw new IllegalStateException();
			String expected = getHashExpected(args[1]);
			return generated.equals(expected);
		};
	}
	public static ThrowsReferencedCallback<Boolean> HASH_EXE_CERTUTIL(File exe, String digest) {
		return (args) -> {
			String generated = bytesToHexString(checksumExeCertutil(exe, (File) args[0], digest));
			String expected = getHashExpected(args[1]);
			return generated.equals(expected);
		};
	}
	public static ThrowsReferencedCallback<Boolean> HASH_EXE_OPENSSL(File exe, String digest) {
		return (args) -> {
			String generated = bytesToHexString(checksumExeOpenssl(exe, (File) args[0], digest));
			String expected = getHashExpected(args[1]);
			return generated.equals(expected);
		};
	}
	public static ThrowsReferencedCallback<Boolean> HASH_EXE_MDNSUM(File exe, int N) {
		return (args) -> {
			String generated = bytesToHexString(checksumExeMdNsum(exe, (File) args[0], N));
			String expected = getHashExpected(args[1]);
			return generated.equals(expected);
		};
	}
	public static ThrowsReferencedCallback<Boolean> HASH_EXE_SHANSUM(File exe, int N) {
		return (args) -> {
			String generated = bytesToHexString(checksumExeShaNsum(exe, (File) args[0], N));
			String expected = getHashExpected(args[1]);
			return generated.equals(expected);
		};
	}
}
