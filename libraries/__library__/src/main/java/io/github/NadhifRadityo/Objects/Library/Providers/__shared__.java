package io.github.NadhifRadityo.Objects.Library.Providers;

import io.github.NadhifRadityo.Objects.Library.Constants.Action;
import io.github.NadhifRadityo.Objects.Library.Constants.JSON_moduleRoot;
import io.github.NadhifRadityo.Objects.Library.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Library.ThrowsReferencedCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.github.NadhifRadityo.Objects.Library.Library.error;
import static io.github.NadhifRadityo.Objects.Library.Library.info;
import static io.github.NadhifRadityo.Objects.Library.Library.log;
import static io.github.NadhifRadityo.Objects.Library.Library.warn;
import static io.github.NadhifRadityo.Objects.Library.Utils.HASH_EXE_CERTUTIL;
import static io.github.NadhifRadityo.Objects.Library.Utils.HASH_EXE_MDNSUM;
import static io.github.NadhifRadityo.Objects.Library.Utils.HASH_EXE_OPENSSL;
import static io.github.NadhifRadityo.Objects.Library.Utils.HASH_EXE_SHANSUM;
import static io.github.NadhifRadityo.Objects.Library.Utils.HASH_JAVA_NATIVE;
import static io.github.NadhifRadityo.Objects.Library.Utils.bytesToHexString;
import static io.github.NadhifRadityo.Objects.Library.Utils.copyAllProperties;
import static io.github.NadhifRadityo.Objects.Library.Utils.getFileString;

public class __shared__ {
	public static int DOWNLOAD_CHECKSUM_ERROR = 1;
	public static int DOWNLOAD_CHECKSUM_PASSED = 0;
	public static int DOWNLOAD_CHECKSUM_NOT_PASSED = -1;
	public static int DOWNLOAD_MAX_RETRY = 3;
	
	public static boolean[] downloadDefault(JSON_moduleRoot.$dependency dependency, File currentDir, ThrowsReferencedCallback<Void> _download, ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> _hashes) throws Exception {
		boolean[] result = new boolean[dependency.items.length];
		int retry = 0;
		for(int i = 0; i < dependency.items.length; i++) {
			JSON_moduleRoot.$dependency.$item item = dependency.items[i];
			String dir = item.directory;
			String name = item.name;
			long act = item.actions;

			File directory = dir != null ? new File(currentDir, dir) : currentDir;
			if(!directory.exists() && !directory.mkdirs())
				throw new IllegalArgumentException();
			if(name.isEmpty() || !Action.DOWNLOAD.contains(act))
				continue;
			Map<String, List<ThrowsReferencedCallback<byte[]>>> hashes = _hashes.get(item);

			ReferencedCallback<File> getFile = (args) -> {
				String extension = (String) args[0];
				String fileName = name + extension;
				return new File(directory, fileName);
			};
			ThrowsReferencedCallback<Void> download = (args) -> _download.get(getFile, item, args[0]);
			ReferencedCallback<Integer> checksum = (args) -> {
				String extension = (String) args[0];
				List<ThrowsReferencedCallback<byte[]>> providers = (List<ThrowsReferencedCallback<byte[]>>) args[1];
				File checksumFile = getFile.get("." + extension); String checksumVerified; if(!checksumFile.exists()) return DOWNLOAD_CHECKSUM_ERROR;
				try { checksumVerified = getFileString(checksumFile); } catch(Throwable e) { error("Error while getting downloaded checksum,\n%s", e); return DOWNLOAD_CHECKSUM_ERROR; }
				for(ThrowsReferencedCallback<byte[]> provider : providers) { try {
					byte[] checksumByte = provider.get(getFile.get(""));
					String checksumGenerated = bytesToHexString(checksumByte);
					return checksumVerified.equals(checksumGenerated) ? DOWNLOAD_CHECKSUM_PASSED : DOWNLOAD_CHECKSUM_NOT_PASSED;
				} catch(Throwable e) { error("Error while validating checksum,\n%s", e); } }
				return DOWNLOAD_CHECKSUM_ERROR;
			};
			ReferencedCallback<Object[]> checksumResults = (args) -> {
				int[] hashResults = new int[hashes.size()];
				int j = 0; String hashLog = "";
				for(Map.Entry<String, List<ThrowsReferencedCallback<byte[]>>> hash : hashes.entrySet()) {
					int hashResult = checksum.get(hash.getKey(), hash.getValue()); hashResults[j++] = hashResult;
					hashLog += (j != 1 ? ", " : "") + hash.getKey() + " passed: " + (hashResult == DOWNLOAD_CHECKSUM_PASSED);
				}
				log(hashLog);

				boolean anyPassed = false;
				boolean anyNotPassed = false;
				boolean allError = true;
				for(int hashResult : hashResults) {
					if(!anyPassed && hashResult == DOWNLOAD_CHECKSUM_PASSED) anyPassed = true;
					if(!anyNotPassed && hashResult == DOWNLOAD_CHECKSUM_NOT_PASSED) anyNotPassed = true;
					if(allError && hashResult != DOWNLOAD_CHECKSUM_ERROR) allError = false;
					if(anyPassed && !allError && anyNotPassed) break;
				}
				return new Object[] { hashResults, anyPassed, anyNotPassed, allError };
			};

			File file = getFile.get("");
			boolean wasDownloaded = file.exists();
			if(wasDownloaded) {
				info("File already downloaded, checking checksums... (%s)", file.getAbsolutePath());
				Object[] checksumResult = checksumResults.get();
				boolean anyPassed = (boolean) checksumResult[1];
				boolean anyNotPassed = (boolean) checksumResult[2];
				boolean allError = (boolean) checksumResult[3];
				if(anyPassed) { retry = 0; result[i] = true; continue; }
				if(anyNotPassed) error("Checksum failed! Will retry...");
				if(allError) warn("Checksums not available! Try downloading again...");
			}

			for(String hash : hashes.keySet())
				download.get("." + hash);
			if(wasDownloaded) {
				info("File already downloaded, checking checksums... (%s)", file.getAbsolutePath());
				Object[] checksumResult = checksumResults.get();
				boolean anyPassed = (boolean) checksumResult[1];
				boolean allError = (boolean) checksumResult[3];
				if(anyPassed) { retry = 0; result[i] = true; continue; }
				if(allError) { warn("Checksums not available! Assuming it's good."); retry = 0; result[i] = true; continue; }
				error("Checksum failed! Will retry...");
			}
			download.get("");

			Object[] checksumResult = checksumResults.get();
			boolean anyPassed = (boolean) checksumResult[1];
			boolean anyNotPassed = (boolean) checksumResult[2];
			boolean allError = (boolean) checksumResult[3];
			if(anyPassed) { retry = 0; result[i] = true; continue; }
			if(allError) { warn("Checksums not available! Assuming it's good."); retry = 0; result[i] = true; continue; }
			if(anyNotPassed) {
				if(retry < DOWNLOAD_MAX_RETRY - 1) { error("Checksum failed! Will retry..."); retry++; i -= 1;
				} else { error("Error! cannot download file, check your internet connection."); result[i] = false; }
			}
		}
		return result;
	}

	public static boolean[] deleteDefault(JSON_moduleRoot.$dependency dependency, File currentDir, ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> _hashes) throws Exception {
		boolean[] result = new boolean[dependency.items.length];
		for(int i = 0; i < dependency.items.length; i++) {
			JSON_moduleRoot.$dependency.$item item = dependency.items[i];
			String dir = item.directory;
			String name = item.name;
			long act = item.actions;

			File directory = dir != null ? new File(currentDir, dir) : currentDir;
			if(!directory.exists() && !directory.mkdirs())
				throw new IllegalArgumentException();
			if(name.isEmpty() || !Action.DELETE.contains(act))
				continue;
			Map<String, List<ThrowsReferencedCallback<byte[]>>> hashes = _hashes.get(item);

			ReferencedCallback<File> getFile = (args) -> {
				String extension = (String) args[0];
				String fileName = name + extension;
				return new File(directory, fileName);
			};

			File currentFile = getFile.get("");
			info("Deleting file... (%s)", currentFile.getAbsolutePath());
			boolean success = true;
			if(!currentFile.delete()) {
				warn("Couldn't delete file! (%s)", currentFile.getAbsolutePath());
				success = false;
			}
			for(String hash : hashes.keySet()) {
				File hashFile = getFile.get("." + hash);
				if(hashFile.exists() && !hashFile.delete()) {
					warn("Couldn't delete hash file! (%s)", hashFile.getAbsolutePath());
					success = false;
				}
			}
			result[i] = success;
		}
		return result;
	}

	public static final String DEFAULT_HASH_OPTION_JAVA_NATIVE_MD2 = "md2:JAVA_NATIVE:MD2";
	public static final String DEFAULT_HASH_OPTION_JAVA_NATIVE_MD5 = "md5:JAVA_NATIVE:MD5";
	public static final String DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA1 = "sha1:JAVA_NATIVE:SHA-1";
	public static final String DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA224 = "sha224:JAVA_NATIVE:SHA-224";
	public static final String DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA256 = "sha256:JAVA_NATIVE:SHA-256";
	public static final String DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA384 = "sha384:JAVA_NATIVE:SHA-384";
	public static final String DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA512 = "sha512:JAVA_NATIVE:SHA-512";
	public static final String DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD2 = "md2:EXE_CERTUTIL:MD2";
	public static final String DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD4 = "md4:EXE_CERTUTIL:MD4";
	public static final String DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD5 = "md5:EXE_CERTUTIL:MD5";
	public static final String DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA1 = "sha1:EXE_CERTUTIL:SHA1";
	public static final String DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA256 = "sha256:EXE_CERTUTIL:SHA256";
	public static final String DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA384 = "sha384:EXE_CERTUTIL:SHA384";
	public static final String DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA512 = "sha512:EXE_CERTUTIL:SHA512";
	public static final String DEFAULT_HASH_OPTION_EXE_OPENSSL_MD4 = "md4:EXE_OPENSSL:MD4";
	public static final String DEFAULT_HASH_OPTION_EXE_OPENSSL_MD5 = "md5:EXE_OPENSSL:MD5";
	public static final String DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA1 = "sha1:EXE_OPENSSL:SHA1";
	public static final String DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA224 = "sha224:EXE_OPENSSL:SHA224";
	public static final String DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA256 = "sha256:EXE_OPENSSL:SHA256";
	public static final String DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA384 = "sha384:EXE_OPENSSL:SHA384";
	public static final String DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA512 = "sha512:EXE_OPENSSL:SHA512";
	public static final String DEFAULT_HASH_OPTION_EXE_MD5SUM = "md5:EXE_MD5SUM";
	public static final String DEFAULT_HASH_OPTION_EXE_SHA1SUM = "sha1:EXE_SHA1SUM";
	public static final String DEFAULT_HASH_OPTION_EXE_SHA224SUM = "sha224:EXE_SHA224SUM";
	public static final String DEFAULT_HASH_OPTION_EXE_SHA256SUM = "sha256:EXE_SHA256SUM";
	public static final String DEFAULT_HASH_OPTION_EXE_SHA384SUM = "sha384:EXE_SHA384SUM";
	public static final String DEFAULT_HASH_OPTION_EXE_SHA512SUM = "sha512:EXE_SHA512SUM";

	public static List<String> DEFAULT_HASH_OPTIONS_ALL_MD2(Properties properties) {
		List<String> result = new ArrayList<>();
		properties = copyAllProperties(properties, false);
		result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_MD2);
		if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD2);
		return result;
	}
	public static List<String> DEFAULT_HASH_OPTIONS_ALL_MD4(Properties properties) {
		List<String> result = new ArrayList<>();
		properties = copyAllProperties(properties, false);
		if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD4);
		if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_MD4);
		return result;
	}
	public static List<String> DEFAULT_HASH_OPTIONS_ALL_MD5(Properties properties) {
		List<String> result = new ArrayList<>();
		properties = copyAllProperties(properties, false);
		result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_MD5);
		if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD5);
		if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_MD5);
		if(properties.containsKey("md5sumPath")) result.add(DEFAULT_HASH_OPTION_EXE_MD5SUM);
		return result;
	}
	public static List<String> DEFAULT_HASH_OPTIONS_ALL_SHA1(Properties properties) {
		List<String> result = new ArrayList<>();
		properties = copyAllProperties(properties, false);
		result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA1);
		if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA1);
		if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA1);
		if(properties.containsKey("sha1sumPath")) result.add(DEFAULT_HASH_OPTION_EXE_SHA1SUM);
		return result;
	}
	public static List<String> DEFAULT_HASH_OPTIONS_ALL_SHA224(Properties properties) {
		List<String> result = new ArrayList<>();
		properties = copyAllProperties(properties, false);
		result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA224);
		if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA224);
		if(properties.containsKey("sha1sumPath")) result.add(DEFAULT_HASH_OPTION_EXE_SHA224SUM);
		return result;
	}
	public static List<String> DEFAULT_HASH_OPTIONS_ALL_SHA256(Properties properties) {
		List<String> result = new ArrayList<>();
		properties = copyAllProperties(properties, false);
		result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA256);
		if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA256);
		if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA256);
		if(properties.containsKey("sha1sumPath")) result.add(DEFAULT_HASH_OPTION_EXE_SHA256SUM);
		return result;
	}
	public static List<String> DEFAULT_HASH_OPTIONS_ALL_SHA384(Properties properties) {
		List<String> result = new ArrayList<>();
		properties = copyAllProperties(properties, false);
		result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA384);
		if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA384);
		if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA384);
		if(properties.containsKey("sha1sumPath")) result.add(DEFAULT_HASH_OPTION_EXE_SHA384SUM);
		return result;
	}
	public static List<String> DEFAULT_HASH_OPTIONS_ALL_SHA512(Properties properties) {
		List<String> result = new ArrayList<>();
		properties = copyAllProperties(properties, false);
		result.add(DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA512);
		if(properties.containsKey("certutilPath")) result.add(DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA512);
		if(properties.containsKey("opensslPath")) result.add(DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA512);
		if(properties.containsKey("sha1sumPath")) result.add(DEFAULT_HASH_OPTION_EXE_SHA512SUM);
		return result;
	}
	public static List<String> DEFAULT_HASH_OPTIONS_MDN(Properties properties) {
		List<String> result = new ArrayList<>();
		result.addAll(DEFAULT_HASH_OPTIONS_ALL_MD2(properties));
		result.addAll(DEFAULT_HASH_OPTIONS_ALL_MD4(properties));
		result.addAll(DEFAULT_HASH_OPTIONS_ALL_MD5(properties));
		return result;
	}
	public static List<String> DEFAULT_HASH_OPTIONS_SHAN(Properties properties) {
		List<String> result = new ArrayList<>();
		result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA1(properties));
		result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA224(properties));
		result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA256(properties));
		result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA384(properties));
		result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA512(properties));
		return result;
	}
	public static List<String> DEFAULT_HASH_OPTIONS_ALL(Properties properties) {
		List<String> result = new ArrayList<>();
		result.addAll(DEFAULT_HASH_OPTIONS_MDN(properties));
		result.addAll(DEFAULT_HASH_OPTIONS_SHAN(properties));
		return result;
	}
	public static List<String> DEFAULT_HASH_OPTIONS_MD5_SHA1(Properties properties) {
		List<String> result = new ArrayList<>();
		result.addAll(DEFAULT_HASH_OPTIONS_ALL_MD5(properties));
		result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA1(properties));
		return result;
	}

	public static Map<String, List<ThrowsReferencedCallback<byte[]>>> hashesAvailable(Properties _properties, String options) {
		Map<String, List<ThrowsReferencedCallback<byte[]>>> result = new HashMap<>();
		Properties properties = copyAllProperties(_properties, false);
		ReferencedCallback<File> getFileIfPropertiesExist = (args) -> { String key = (String) args[0]; return properties.containsKey(key) ? new File(properties.getProperty(key)) : null; };
		File certutil = getFileIfPropertiesExist.get("certutilPath");
		File openssl = getFileIfPropertiesExist.get("opensslPath");
		File md5sum = getFileIfPropertiesExist.get("md5sumPath");
		File sha1sum = getFileIfPropertiesExist.get("sha1sumPath");
		File sha224sum = getFileIfPropertiesExist.get("sha224sumPath");
		File sha256sum = getFileIfPropertiesExist.get("sha256sumPath");
		File sha384sum = getFileIfPropertiesExist.get("sha384sumPath");
		File sha512sum = getFileIfPropertiesExist.get("sha512sumPath");

		for(String option : options.split(",")) {
			String[] settings = option.split(":");
			String extension = settings[0];
			String provider = settings[1];
			String[] args = new String[settings.length - 2];
			System.arraycopy(settings, 2, args, 0, args.length);

			List<ThrowsReferencedCallback<byte[]>> providers = result.computeIfAbsent(extension, k -> new ArrayList<>());
			switch(provider.toUpperCase()) {
				case "JAVA_NATIVE": providers.add(HASH_JAVA_NATIVE(args[0])); break;
				case "EXE_CERTUTIL": providers.add(HASH_EXE_CERTUTIL(certutil, args[0])); break;
				case "EXE_OPENSSL": providers.add(HASH_EXE_OPENSSL(openssl, args[0])); break;
				case "EXE_MD5SUM": providers.add(HASH_EXE_MDNSUM(md5sum, 5)); break;
				case "EXE_SHA1SUM": providers.add(HASH_EXE_SHANSUM(sha1sum, 1)); break;
				case "EXE_SHA224SUM": providers.add(HASH_EXE_SHANSUM(sha224sum, 224)); break;
				case "EXE_SHA256SUM": providers.add(HASH_EXE_SHANSUM(sha256sum, 256)); break;
				case "EXE_SHA384SUM": providers.add(HASH_EXE_SHANSUM(sha384sum, 384)); break;
				case "EXE_SHA512SUM": providers.add(HASH_EXE_SHANSUM(sha512sum, 512)); break;
			}
		}
		return result;
	}
}
