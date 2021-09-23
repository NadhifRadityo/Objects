package io.github.NadhifRadityo.Library.Providers;

import io.github.NadhifRadityo.Library.ReferencedCallback;
import io.github.NadhifRadityo.Library.ThrowsReferencedCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.github.NadhifRadityo.Library.LibraryModule.__static_dir;
import static io.github.NadhifRadityo.Library.Utils.HASH_EXE_CERTUTIL;
import static io.github.NadhifRadityo.Library.Utils.HASH_EXE_MDNSUM;
import static io.github.NadhifRadityo.Library.Utils.HASH_EXE_OPENSSL;
import static io.github.NadhifRadityo.Library.Utils.HASH_EXE_SHANSUM;
import static io.github.NadhifRadityo.Library.Utils.HASH_JAVA_NATIVE;
import static io.github.NadhifRadityo.Library.Utils.copyAllProperties;
import static io.github.NadhifRadityo.Library.Utils.error;
import static io.github.NadhifRadityo.Library.Utils.file;
import static io.github.NadhifRadityo.Library.Utils.info;
import static io.github.NadhifRadityo.Library.Utils.warn;

public class __shared__ {
	public static int VALIDATE_RESULT_ERROR = 1;
	public static int VALIDATE_RESULT_PASSED = 0;
	public static int VALIDATE_RESULT_NOT_PASSED = -1;
	public static int VALIDATE_REQUEST_RUN = 0;
	public static int VALIDATE_REQUEST_FETCH = 1;
	public static int DOWNLOAD_MAX_RETRY = 3;

	public static ThrowsReferencedCallback<File> defaultItemFileCallback(String path) {
		File staticDir = __static_dir();
		return (args) -> { String item = (String) args[1]; return file(staticDir, path, item); };
	}

	public static Map<String, File> download(Object dependency, ReferencedCallback<String[]> getItems, ThrowsReferencedCallback<File> itemFileCallback,
											 ThrowsReferencedCallback<Object> fileValidationCallback, ThrowsReferencedCallback<Void> download) throws Exception {
		int retry = 0;
		Map<String, File> results = new HashMap<>();
		String[] items = getItems.get(dependency);
		for(int i = 0; i < items.length; i++) {
			String item = items[i];
			File itemFile = itemFileCallback.get(dependency, item);

			ThrowsReferencedCallback<Object[]> validate = (args) -> {
				int[] validationResults = (int[]) fileValidationCallback.get(dependency, item, itemFile, itemFileCallback, VALIDATE_REQUEST_RUN, download);
				boolean anyPassed = false;
				boolean anyNotPassed = false;
				boolean allError = true;
				for(int validationResult : validationResults) {
					if(!anyPassed && validationResult == VALIDATE_RESULT_PASSED) anyPassed = true;
					if(!anyNotPassed && validationResult == VALIDATE_RESULT_NOT_PASSED) anyNotPassed = true;
					if(allError && validationResult != VALIDATE_RESULT_ERROR) allError = false;
					if(anyPassed && !allError && anyNotPassed) break;
				}
				return new Object[] { validationResults, anyPassed, anyNotPassed, allError };
			};

			boolean wasDownloaded = itemFile.exists();
			if(wasDownloaded) {
				info("File already downloaded, validating... (%s)", itemFile.getAbsolutePath());
				Object[] validationResult = validate.get();
				boolean anyPassed = (boolean) validationResult[1];
				boolean anyNotPassed = (boolean) validationResult[2];
				boolean allError = (boolean) validationResult[3];
				if(anyPassed) { retry = 0; results.put(item, itemFile); continue; }
				if(anyNotPassed) error("Validation failed! Will retry...");
				if(allError) warn("Validation not available! Try downloading again...");
			}

			fileValidationCallback.get(dependency, item, itemFile, itemFileCallback, VALIDATE_REQUEST_FETCH, download);
			if(wasDownloaded) {
				info("File already downloaded, validating... (%s)", itemFile.getAbsolutePath());
				Object[] validationResult = validate.get();
				boolean anyPassed = (boolean) validationResult[1];
				boolean allError = (boolean) validationResult[3];
				if(anyPassed) { retry = 0; results.put(item, itemFile); continue; }
				if(allError) { warn("Validation not available! Assuming it's good."); retry = 0; results.put(item, itemFile); continue; }
				error("Validation failed! Will retry...");
			}
			download.get(dependency, item, itemFile);

			{
				Object[] validationResult = validate.get();
				boolean anyPassed = (boolean) validationResult[1];
				boolean anyNotPassed = (boolean) validationResult[2];
				boolean allError = (boolean) validationResult[3];
				if(anyPassed) { retry = 0; results.put(item, itemFile); continue; }
				if(allError) { warn("Validation not available! Assuming it's good."); retry = 0; results.put(item, itemFile); continue; }
				if(anyNotPassed) {
					if(retry < DOWNLOAD_MAX_RETRY - 1) { error("Validation failed! Will retry..."); retry++; i -= 1;
					} else { error("Error! cannot download file, check your internet connection."); results.put(item, null); }
				}
			}
		}
		return results;
	}

	public static int[] validate(Map<String, List<ThrowsReferencedCallback<Boolean>>> validations, ThrowsReferencedCallback<File> getFile) throws Exception {
		Iterator<Map.Entry<String, List<ThrowsReferencedCallback<Boolean>>>> iterator = validations.entrySet().iterator();
		int[] results = new int[validations.size()];
		int i = -1;
		L0: while(iterator.hasNext()) {
			Map.Entry<String, List<ThrowsReferencedCallback<Boolean>>> validation = iterator.next(); i++;
			String extension = validation.getKey();
			List<ThrowsReferencedCallback<Boolean>> providers = validation.getValue();
			File targetFile = getFile.get("");
			File checksumFile = getFile.get(extension);
			if(!targetFile.exists() || !checksumFile.exists())
				{ results[i] = VALIDATE_RESULT_ERROR; continue; }
			for(ThrowsReferencedCallback<Boolean> provider : providers) { try {
				Boolean passed = provider.get(targetFile, checksumFile);
				results[i] = passed ? VALIDATE_RESULT_PASSED : VALIDATE_RESULT_NOT_PASSED;
				continue L0;
			} catch(Throwable e) { error("Error while validating checksum,\n%s", e); } }
			results[i] = VALIDATE_RESULT_ERROR;
		}
		return results;
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

	public static Map<String, List<ThrowsReferencedCallback<Boolean>>> hashesAvailable(Properties _properties, String options) {
		Map<String, List<ThrowsReferencedCallback<Boolean>>> result = new HashMap<>();
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

			List<ThrowsReferencedCallback<Boolean>> providers = result.computeIfAbsent(extension, k -> new ArrayList<>());
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
