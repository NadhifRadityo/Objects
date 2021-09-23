package io.github.NadhifRadityo.Objects.Library.Providers;

public class HTMLDirListingProvider {
//	public static ThrowsReferencedCallback<JSON_configurationsRoot.$module.$dependency[]> SEARCH = (args) -> {
//		Properties properties = (Properties) args[0];
//		String url = (String) args[1];
//		String tableSelector = (String) args[2];
//		List<String> selectors = (List<String>) args[3];
//		return search(properties, url, tableSelector, selectors);
//	};
//	public static ThrowsReferencedCallback<boolean[]> DOWNLOAD = (args) -> {
//		Properties properties = (Properties) args[0];
//		JSON_configurationsRoot.$module.$dependency dependency = (JSON_configurationsRoot.$module.$dependency) args[1];
//		File currentDir = (File) args[2];
//		return download(properties, dependency, currentDir);
//	};
//	public static ThrowsReferencedCallback<boolean[]> DELETE = (args) -> {
//		Properties properties = (Properties) args[0];
//		JSON_configurationsRoot.$module.$dependency dependency = (JSON_configurationsRoot.$module.$dependency) args[1];
//		File currentDir = (File) args[2];
//		return delete(properties, dependency, currentDir);
//	};
//
//	public static JSON_configurationsRoot.$module.$dependency[] search(Properties properties, String url, String tableSelector, List<String> selectors) throws Exception {
//
//	}
//	public static boolean[] download(Properties properties, JSON_configurationsRoot.$module.$dependency dependency, File currentDir) throws Exception {
//		ThrowsReferencedCallback<Void> download = (args) -> {
//			ReferencedCallback<File> getFile = (ReferencedCallback<File>) args[0];
//			JSON_configurationsRoot.$module.$dependency.$item item = (JSON_configurationsRoot.$module.$dependency.$item) args[1];
//			String group = (String) args[2];
//			String artifact = (String) args[3];
//			String version = (String) args[4];
//			boolean snapshot = (boolean) args[5];
//			String extension = (String) args[6];
//			File target = getFile.get(extension);
//			if(!target.exists() && !target.createNewFile())
//				throw new IllegalArgumentException();
//			try(FileOutputStream fos = new FileOutputStream(target)) {
//				Utils.download(new URL((String) Utils.runJavascript(properties.getProperty("mavenDownload"), group, artifact, version, target.getName())), fos);
//			} return null;
//		};
//
//		Map<String, String> hashes = new HashMap<>();
//		hashes.put("MD5", ".md5");
//		hashes.put("SHA-1", ".sha1");
//		return downloadDefault(properties, dependency, currentDir, download, hashes);
//	}
//	public static boolean[] delete(Properties properties, JSON_configurationsRoot.$module.$dependency dependency, File currentDir) throws Exception {
//		List<String> hashes = new ArrayList<>();
//		hashes.add(".md5");
//		hashes.add(".sha1");
//		return deleteDefault(properties, dependency, currentDir, hashes);
//	}
}
