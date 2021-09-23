package io.github.NadhifRadityo.Objects.Library.Providers;

import io.github.NadhifRadityo.Objects.Library.Constants.Action;
import io.github.NadhifRadityo.Objects.Library.Constants.JSON_configurationsRoot;
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
import static io.github.NadhifRadityo.Objects.Library.Utils.HASH_EXE_MD5SUM;
import static io.github.NadhifRadityo.Objects.Library.Utils.HASH_EXE_OPENSSL;
import static io.github.NadhifRadityo.Objects.Library.Utils.HASH_EXE_SHA1SUM;
import static io.github.NadhifRadityo.Objects.Library.Utils.HASH_JAVA_NATIVE;
import static io.github.NadhifRadityo.Objects.Library.Utils.copyAllProperties;
import static io.github.NadhifRadityo.Objects.Library.Utils.getFileString;
import static io.github.NadhifRadityo.Objects.Library.Utils.bytesToHexString;

public class __shared__ {
	public static int DOWNLOAD_CHECKSUM_ERROR = 1;
	public static int DOWNLOAD_CHECKSUM_PASSED = 0;
	public static int DOWNLOAD_CHECKSUM_NOT_PASSED = -1;
	public static int DOWNLOAD_MAX_RETRY = 3;
	
	public static boolean[] downloadDefault(JSON_configurationsRoot.$module.$dependency dependency, File currentDir, ThrowsReferencedCallback<Void> _download, ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> _hashes) throws Exception {
		boolean[] result = new boolean[dependency.items.length];
		int retry = 0;
		for(int i = 0; i < dependency.items.length; i++) {
			JSON_configurationsRoot.$module.$dependency.$item item = dependency.items[i];
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

	public static boolean[] deleteDefault(JSON_configurationsRoot.$module.$dependency dependency, File currentDir, ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> _hashes) throws Exception {
		boolean[] result = new boolean[dependency.items.length];
		for(int i = 0; i < dependency.items.length; i++) {
			JSON_configurationsRoot.$module.$dependency.$item item = dependency.items[i];
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
				File hashFile = getFile.get(hash);
				if(hashFile.exists() && !hashFile.delete()) {
					warn("Couldn't delete hash file! (%s)", hashFile.getAbsolutePath());
					success = false;
				}
			}
			result[i] = success;
		}
		return result;
	}

	public static final String DEFAULT_HASH_OPTION_JAVA_NATIVE_MD5 = "md5:JAVA_NATIVE:MD5";
	public static final String DEFAULT_HASH_OPTION_JAVA_NATIVE_SHA1 = "sha1:JAVA_NATIVE:SHA1";
	public static final String DEFAULT_HASH_OPTION_EXE_CERTUTIL_MD5 = "md5:EXE_CERTUTIL:MD5";
	public static final String DEFAULT_HASH_OPTION_EXE_CERTUTIL_SHA1 = "sha1:EXE_CERTUTIL:SHA1";
	public static final String DEFAULT_HASH_OPTION_EXE_OPENSSL_MD5 = "md5:EXE_OPENSSL:MD5";
	public static final String DEFAULT_HASH_OPTION_EXE_OPENSSL_SHA1 = "sha1:EXE_OPENSSL:SHA1";
	public static final String DEFAULT_HASH_OPTION_EXE_MD5SUM = "md5:EXE_MD5SUM";
	public static final String DEFAULT_HASH_OPTION_EXE_SHA1SUM = "sha1:EXE_SHA1SUM";
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
	public static List<String> DEFAULT_HASH_OPTIONS_ALL(Properties properties) {
		List<String> result = new ArrayList<>();
		result.addAll(DEFAULT_HASH_OPTIONS_ALL_MD5(properties));
		result.addAll(DEFAULT_HASH_OPTIONS_ALL_SHA1(properties));
		return result;
	}
	public static Map<String, List<ThrowsReferencedCallback<byte[]>>> hashesAvailable(Properties properties, String options) {
		Map<String, List<ThrowsReferencedCallback<byte[]>>> result = new HashMap<>();
		properties = copyAllProperties(properties, false);
		File certutil = properties.containsKey("certutilPath") ? new File(properties.getProperty("certutilPath")) : null;
		File openssl = properties.containsKey("opensslPath") ? new File(properties.getProperty("opensslPath")) : null;
		File md5sum = properties.containsKey("md5sumPath") ? new File(properties.getProperty("md5sumPath")) : null;
		File sha1sum = properties.containsKey("sha1sumPath") ? new File(properties.getProperty("sha1sumPath")) : null;

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
				case "EXE_MD5SUM": providers.add(HASH_EXE_MD5SUM(md5sum)); break;
				case "EXE_SHA1SUM": providers.add(HASH_EXE_SHA1SUM(sha1sum)); break;
			}
		}
		return result;
	}
}
