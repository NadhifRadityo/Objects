package io.github.NadhifRadityo.Objects.Library.Providers;

import io.github.NadhifRadityo.Objects.Library.CURL;
import io.github.NadhifRadityo.Objects.Library.Constants.JSON_configurationsRoot;
import io.github.NadhifRadityo.Objects.Library.Constants.Phase;
import io.github.NadhifRadityo.Objects.Library.Constants.Provider;
import io.github.NadhifRadityo.Objects.Library.Constants.Stage;
import io.github.NadhifRadityo.Objects.Library.LibraryPack;
import io.github.NadhifRadityo.Objects.Library.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Library.ThrowsReferencedCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.github.NadhifRadityo.Objects.Library.Library.debug;
import static io.github.NadhifRadityo.Objects.Library.Library.info;
import static io.github.NadhifRadityo.Objects.Library.Library.warn;
import static io.github.NadhifRadityo.Objects.Library.Providers.__shared__.DEFAULT_HASH_OPTIONS_ALL;
import static io.github.NadhifRadityo.Objects.Library.Providers.__shared__.deleteDefault;
import static io.github.NadhifRadityo.Objects.Library.Providers.__shared__.downloadDefault;
import static io.github.NadhifRadityo.Objects.Library.Providers.__shared__.hashesAvailable;
import static io.github.NadhifRadityo.Objects.Library.Utils.downloadFile;
import static io.github.NadhifRadityo.Objects.Library.Utils.formattedUrl;
import static io.github.NadhifRadityo.Objects.Library.Utils.getFileExtension;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_getLong;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_getObject;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_setBoolean;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_setLong;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_setObject;
import static io.github.NadhifRadityo.Objects.Library.Utils.pn_getBoolean;
import static io.github.NadhifRadityo.Objects.Library.Utils.pn_getLong;
import static io.github.NadhifRadityo.Objects.Library.Utils.pn_getObject;
import static io.github.NadhifRadityo.Objects.Library.Utils.runJavascript;
import static io.github.NadhifRadityo.Objects.Library.Utils.toJson;
import static io.github.NadhifRadityo.Objects.Library.Utils.urlToUri;
import static io.github.NadhifRadityo.Objects.Library.Utils.writeFileString;

public class SonatypeProvider {
	public static ThrowsReferencedCallback<Void> PHASE_PRE = (args) -> {
		Phase phase = (Phase) args[0];
		Stage stage = (Stage) args[1];
		File directory = (File) args[2];
		JSON_configurationsRoot configurations = (JSON_configurationsRoot) args[3];
		List<LibraryPack> libraryPacks = (List<LibraryPack>) args[4];
		phase_pre(phase, stage, directory, configurations, libraryPacks);
		return null;
	};
	public static ThrowsReferencedCallback<Void> PHASE_POST = (args) -> {
		Phase phase = (Phase) args[0];
		Stage stage = (Stage) args[1];
		File directory = (File) args[2];
		JSON_configurationsRoot configurations = (JSON_configurationsRoot) args[3];
		List<LibraryPack> libraryPacks = (List<LibraryPack>) args[4];
		phase_post(phase, stage, directory, configurations, libraryPacks);
		return null;
	};

	public static String GLOBAL_PROPERTIES_SONATYPE_SEARCH = "sonatypeSearch";
	public static String GLOBAL_PROPERTIES_SONATYPE_VERSION_INFO = "sonatypeVersionInfo";
	public static String GLOBAL_PROPERTIES_SONATYPE_FILE_INFO = "sonatypeFileInfo";
	public static String GLOBAL_PROPERTIES_SONATYPE_DOWNLOAD = "sonatypeDownload";
	public static String GLOBAL_PROPERTIES_SONATYPE_HASHES = "sonatypeHashes";

	public static void phase_pre(Phase phase, Stage stage, File directory, JSON_configurationsRoot configurations, List<LibraryPack> libraryPacks) {
		switch(phase) {
			case CLEAN: { break; }
			case CONFIG: {
				configurations.properties.putIfAbsent(GLOBAL_PROPERTIES_SONATYPE_SEARCH, "(g, a, s) => `https://oss.sonatype.org/service/local/repositories/${s?'snapshots':'releases'}/index_content/?_dc=${Date.now()}&groupIdHint=${g}&artifactIdHint=${a}`");
				configurations.properties.putIfAbsent(GLOBAL_PROPERTIES_SONATYPE_VERSION_INFO, "(g, a, v, s, c, e) => `https://oss.sonatype.org/service/local/artifact/maven/resolve?_dc=${Date.now()}&r=${s?'snapshots':'releases'}&g=${g}&a=${a}&v=${v}&isLocal=true&e=${e}${c && `&c=${c}` || ''}`");
				configurations.properties.putIfAbsent(GLOBAL_PROPERTIES_SONATYPE_FILE_INFO, "(g, a, v, s, f) => `https://oss.sonatype.org/service/local/repositories/${s?'snapshots':'releases'}/content/${g.replace(/\\./g, '/')}/${a}/${v}/${f}?_dc=${Date.now()}&describe=info&isLocal=true`");
				configurations.properties.putIfAbsent(GLOBAL_PROPERTIES_SONATYPE_DOWNLOAD, "(g, a, v, s, c, e) => `https://oss.sonatype.org/service/local/artifact/maven/redirect?r=${s?'snapshots':'releases'}&g=${g}&a=${a}&v=${v}&e=${e}${c && `&c=${c}` || ''}`");
				configurations.properties.putIfAbsent(GLOBAL_PROPERTIES_SONATYPE_HASHES, String.join(",", DEFAULT_HASH_OPTIONS_ALL(configurations.properties)));
				break;
			}
			case FETCH: { break; }
			case BUILD: { break; }
			case VALIDATE: { break; }
		}
	}
	public static void phase_post(Phase phase, Stage stage, File directory, JSON_configurationsRoot configurations, List<LibraryPack> libraryPacks) {
		switch(phase) {
			case CLEAN: { break; }
			case CONFIG: { break; }
			case FETCH: { break; }
			case BUILD: { break; }
			case VALIDATE: { break; }
		}
	}

	public static ThrowsReferencedCallback<JSON_configurationsRoot.$module.$dependency[]> SEARCH = (args) -> {
		Properties properties = (Properties) args[0];
		String group = (String) args[1];
		String artifact = (String) args[2];
		boolean snapshots = (boolean) args[3];
		return search(properties, group, artifact, snapshots);
	};
	public static ThrowsReferencedCallback<boolean[]> DOWNLOAD = (args) -> {
		JSON_configurationsRoot.$module.$dependency dependency = (JSON_configurationsRoot.$module.$dependency) args[0];
		File currentDir = (File) args[1];
		return download(dependency, currentDir);
	};
	public static ThrowsReferencedCallback<boolean[]> DELETE = (args) -> {
		JSON_configurationsRoot.$module.$dependency dependency = (JSON_configurationsRoot.$module.$dependency) args[0];
		File currentDir = (File) args[1];
		return delete(dependency, currentDir);
	};

	public static String DEPENDENCY_PROPERTIES_GROUP = "group";
	public static String DEPENDENCY_PROPERTIES_ARTIFACT = "artifact";
	public static String DEPENDENCY_PROPERTIES_VERSION = "version";
	public static String DEPENDENCY_PROPERTIES_TIMESTAMP = "timestamp";
	public static String DEPENDENCY_PROPERTIES_SNAPSHOT = "snapshot";
	public static String ITEM_PROPERTIES_CLASSIFIER = "classifier";

	public static JSON_configurationsRoot.$module.$dependency[] search(Properties properties, String group, String artifact, boolean snapshots) throws Exception {
		CURL curl = new CURL();
		curl.setUrl(formattedUrl((String) runJavascript(p_getObject(properties, GLOBAL_PROPERTIES_SONATYPE_SEARCH), group, artifact, snapshots)));
		curl.setRequestMethod(CURL.RequestMethod.GET);
		curl.addHeader("Accept", "application/json");
		curl.setCustomHandler((args) -> { info("Searching repository \"%s\"... (%s)", group + "." + artifact, urlToUri((URL) args[1]).toASCIIString()); return null; });
		curl.setOnConnect((args) -> toJson(((URLConnection) args[0]).getInputStream(), JSON_sonatypeSearch.class));
		JSON_sonatypeSearch sonatypeSearch = (JSON_sonatypeSearch) curl.build(StandardCharsets.UTF_8);

		String pathToVersion = "/" + group.replaceAll("\\.", "/") + "/" + artifact + "/";
		ReferencedCallback<JSON_sonatypeSearch.$children> getArtifact = new ReferencedCallback<JSON_sonatypeSearch.$children>() {
			@Override public JSON_sonatypeSearch.$children get(Object... args) {
				JSON_sonatypeSearch.$children current = (JSON_sonatypeSearch.$children) args[0];
				if(pathToVersion.equals(current.path)) return current;
				if(!pathToVersion.startsWith(current.path)) return null;
				for(JSON_sonatypeSearch.$children children : current.children) {
					args[0] = children; JSON_sonatypeSearch.$children result = get(args);
					if(result != null) return result;
				} return null;
			}
		};
		JSON_sonatypeSearch.$children versions = getArtifact.get(sonatypeSearch.data);
		if(versions == null) return null;

		ThrowsReferencedCallback<JSON_sonatypeSearch_resolveInfo.$data> getVersionInfo = args -> {
			JSON_sonatypeSearch.$children version = (JSON_sonatypeSearch.$children) args[0];
			CURL versionCurl = new CURL();
			versionCurl.setUrl((String) runJavascript(p_getObject(properties, GLOBAL_PROPERTIES_SONATYPE_VERSION_INFO), version.groupId, version.artifactId, version.version, snapshots, "", "jar"));
			versionCurl.setRequestMethod(CURL.RequestMethod.GET);
			versionCurl.addHeader("Accept", "application/json");
			versionCurl.setCustomHandler((_args) -> { info("Getting version info \"%s\"... (%s)", version.groupId + "." + version.artifactId + ":" + version.version, urlToUri((URL) _args[1]).toASCIIString()); return null; });
			versionCurl.setOnConnect((_args) -> toJson(((URLConnection) _args[0]).getInputStream(), JSON_sonatypeSearch_resolveInfo.class));
			return ((JSON_sonatypeSearch_resolveInfo) versionCurl.build(StandardCharsets.UTF_8)).data;
		};
		ThrowsReferencedCallback<JSON_sonatypeSearch_describeInfo.$data> getFileInfo = args -> {
			JSON_sonatypeSearch_resolveInfo.$data versionInfo = (JSON_sonatypeSearch_resolveInfo.$data) args[0];
			CURL infoCurl = new CURL();
			infoCurl.setUrl((String) runJavascript(p_getObject(properties, GLOBAL_PROPERTIES_SONATYPE_FILE_INFO), versionInfo.groupId, versionInfo.artifactId, versionInfo.baseVersion != null ? versionInfo.baseVersion : versionInfo.version, snapshots, versionInfo.repositoryPath.substring(versionInfo.repositoryPath.lastIndexOf('/') + 1)));
			infoCurl.setRequestMethod(CURL.RequestMethod.GET);
			infoCurl.addHeader("Accept", "application/json");
			infoCurl.setCustomHandler((_args) -> { info("Getting file info \"%s\"... (%s)", versionInfo.groupId + "." + versionInfo.artifactId + ":" + versionInfo.version, urlToUri((URL) _args[1]).toASCIIString()); return null; });
			infoCurl.setOnConnect((_args) -> toJson(((URLConnection) _args[0]).getInputStream(), JSON_sonatypeSearch_describeInfo.class));
			return ((JSON_sonatypeSearch_describeInfo) infoCurl.build(StandardCharsets.UTF_8)).data;
		};
		List<JSON_configurationsRoot.$module.$dependency> results = new ArrayList<>();
		for(JSON_sonatypeSearch.$children version : versions.children) {
			JSON_sonatypeSearch_resolveInfo.$data versionInfo = getVersionInfo.get(version);
			String id = versionInfo.groupId + ":" + versionInfo.artifactId + ":" + (versionInfo.baseVersion != null ? versionInfo.baseVersion : versionInfo.version);
			String g = versionInfo.groupId;
			String a = versionInfo.artifactId;
			String v = versionInfo.baseVersion != null ? versionInfo.baseVersion : versionInfo.version;
			String[] ec = Arrays.stream(version.children).map(c -> c.nodeName).toArray(String[]::new);
			long timestamp = 0; try { timestamp = getFileInfo.get(versionInfo).uploaded; } catch(FileNotFoundException e) { warn("Cannot get file info \"%s\"", id); }
			boolean snapshot = snapshots;
			debug("g=\"%s\" a=\"%s\" id=\"%s\" v=\"%s\" ec=\"%s\" timestamp=\"%s\" snapshot=\"%s\"", g, a, id, v, String.join(";", Arrays.asList(ec)), timestamp, snapshot);
			if(!group.equals(g) || !artifact.equals(a)) continue;

			JSON_configurationsRoot.$module.$dependency result = new JSON_configurationsRoot.$module.$dependency();
			result.id = id;
			result.source = Provider.SONATYPE;
			result.properties = new Properties(properties);
			p_setObject(result.properties, DEPENDENCY_PROPERTIES_GROUP, g);
			p_setObject(result.properties, DEPENDENCY_PROPERTIES_ARTIFACT, a);
			p_setObject(result.properties, DEPENDENCY_PROPERTIES_VERSION, v);
			p_setLong(result.properties, DEPENDENCY_PROPERTIES_TIMESTAMP, timestamp);
			p_setBoolean(result.properties, DEPENDENCY_PROPERTIES_SNAPSHOT, snapshot);
			result.items = new JSON_configurationsRoot.$module.$dependency.$item[version.children.length + 1];
			{
				JSON_configurationsRoot.$module.$dependency.$item pomItem = result.items[0] = new JSON_configurationsRoot.$module.$dependency.$item();
				pomItem.name = a + "-" + v + ".pom";
				pomItem.properties = new Properties(result.properties);
			}
			for(int i = 1; i < result.items.length; i++) {
				JSON_configurationsRoot.$module.$dependency.$item item = new JSON_configurationsRoot.$module.$dependency.$item();
				JSON_sonatypeSearch.$children children = version.children[i - 1];
				item.name = children.nodeName;
				item.properties = new Properties(result.properties);
				p_setObject(item.properties, ITEM_PROPERTIES_CLASSIFIER, children.classifier);
				result.items[i] = item;
			}
			results.add(result);
		}
		results.sort((v1, v2) -> Long.compare(p_getLong(v2.properties, DEPENDENCY_PROPERTIES_TIMESTAMP), p_getLong(v1.properties, DEPENDENCY_PROPERTIES_TIMESTAMP)));
		return results.toArray(new JSON_configurationsRoot.$module.$dependency[0]);
	}
	public static boolean[] download(JSON_configurationsRoot.$module.$dependency dependency, File currentDir) throws Exception {
		String group = pn_getObject(dependency.properties, DEPENDENCY_PROPERTIES_GROUP);
		String artifact = pn_getObject(dependency.properties, DEPENDENCY_PROPERTIES_ARTIFACT);
		String version = pn_getObject(dependency.properties, DEPENDENCY_PROPERTIES_VERSION);
		long timestamp = pn_getLong(dependency.properties, DEPENDENCY_PROPERTIES_TIMESTAMP);
		boolean snapshot = pn_getBoolean(dependency.properties, DEPENDENCY_PROPERTIES_SNAPSHOT);

		Map<String, String> cachedHashes = new HashMap<>();
		ThrowsReferencedCallback<Void> download = (args) -> {
			ReferencedCallback<File> getFile = (ReferencedCallback<File>) args[0];
			JSON_configurationsRoot.$module.$dependency.$item item = (JSON_configurationsRoot.$module.$dependency.$item) args[1];
			String extension = (String) args[2];
			File target = getFile.get(extension);
			if(extension.equals(".md5") || extension.equals(".sha1")) {
				if(!cachedHashes.containsKey(item.name)) {
					CURL versionCurl = new CURL();
					versionCurl.setUrl((String) runJavascript(p_getObject(dependency.properties, GLOBAL_PROPERTIES_SONATYPE_VERSION_INFO), group, artifact, version, snapshot,
							pn_getObject(item.properties, ITEM_PROPERTIES_CLASSIFIER), getFileExtension(item.name)));
					versionCurl.setRequestMethod(CURL.RequestMethod.GET);
					versionCurl.addHeader("Accept", "application/json");
					versionCurl.setCustomHandler((_args) -> { info("Getting version info \"%s\"... (%s)", group + "." + artifact + ":" + version, urlToUri((URL) _args[1]).toASCIIString()); return null; });
					versionCurl.setOnConnect((_args) -> toJson(((URLConnection) _args[0]).getInputStream(), JSON_sonatypeSearch_resolveInfo.class));
					JSON_sonatypeSearch_resolveInfo.$data versionInfo = ((JSON_sonatypeSearch_resolveInfo) versionCurl.build(StandardCharsets.UTF_8)).data;

					String fileName = versionInfo.repositoryPath.substring(versionInfo.repositoryPath.lastIndexOf('/') + 1);
					JSON_sonatypeSearch_describeInfo.$data info;
					try {
						CURL infoCurl = new CURL();
						infoCurl.setUrl((String) runJavascript(p_getObject(dependency.properties, GLOBAL_PROPERTIES_SONATYPE_FILE_INFO), versionInfo.groupId, versionInfo.artifactId,
								versionInfo.baseVersion != null ? versionInfo.baseVersion : versionInfo.version, snapshot, fileName));
						infoCurl.setRequestMethod(CURL.RequestMethod.GET);
						infoCurl.addHeader("Accept", "application/json");
						infoCurl.setCustomHandler((_args) -> { info("Getting file info \"%s\"... (%s)", versionInfo.groupId + "." + versionInfo.artifactId + ":" + versionInfo.version, urlToUri((URL) _args[1]).toASCIIString()); return null; });
						infoCurl.setOnConnect((_args) -> toJson(((URLConnection) _args[0]).getInputStream(), JSON_sonatypeSearch_describeInfo.class));
						info = ((JSON_sonatypeSearch_describeInfo) infoCurl.build(StandardCharsets.UTF_8)).data;
					} catch(FileNotFoundException e) { warn("Cannot get file hash: %s", fileName); return null; }
					cachedHashes.put(item.name, info.md5Hash + File.pathSeparator + info.sha1Hash);
				}

				if(!target.exists() && !target.createNewFile())
					throw new IllegalArgumentException();
				String[] cachedHash = cachedHashes.get(item.name).split(File.pathSeparator);
				writeFileString(target, extension.equals(".md5") ? cachedHash[0] : cachedHash[1], StandardCharsets.UTF_8);
				return null;
			}
			if(!target.exists() && !target.createNewFile())
				throw new IllegalArgumentException();
			try(FileOutputStream fos = new FileOutputStream(target)) {
				downloadFile(new URL((String) runJavascript(p_getObject(dependency.properties, GLOBAL_PROPERTIES_SONATYPE_DOWNLOAD), group, artifact, version, snapshot,
						pn_getObject(item.properties, ITEM_PROPERTIES_CLASSIFIER), getFileExtension(target))), fos);
			} return null;
		};
		ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> hashes = (args) -> {
			JSON_configurationsRoot.$module.$dependency.$item item = (JSON_configurationsRoot.$module.$dependency.$item) args[0];
			return hashesAvailable(item.properties, p_getObject(item.properties, GLOBAL_PROPERTIES_SONATYPE_HASHES));
		};
		return downloadDefault(dependency, currentDir, download, hashes);
	}
	public static boolean[] delete(JSON_configurationsRoot.$module.$dependency dependency, File currentDir) throws Exception {
		ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> hashes = (args) -> {
			JSON_configurationsRoot.$module.$dependency.$item item = (JSON_configurationsRoot.$module.$dependency.$item) args[0];
			return hashesAvailable(item.properties, p_getObject(item.properties, GLOBAL_PROPERTIES_SONATYPE_HASHES));
		};
		return deleteDefault(dependency, currentDir, hashes);
	}

	@SuppressWarnings("unused")
	public static class JSON_sonatypeSearch {
		public $children data;

		@SuppressWarnings("jol")
		public static class $children {
			public String type;
			public boolean leaf;
			public String nodeName;
			public String path;
			public String groupId;
			public String artifactId;
			public String version;
			public String repositoryId;
			public boolean locallyAvailable;
			public long artifactTimestamp;
			public String classifier;
			public String extension;
			public String packaging;
			public String artifactUri;
			public String pomUri;
			public $children[] children;
		}
	}
	@SuppressWarnings("unused")
	public static class JSON_sonatypeSearch_resolveInfo {
		public $data data;

		public static class $data {
			public boolean presentLocally;
			public String groupId;
			public String artifactId;
			public String version;
			public String baseVersion;
			public String extension;
			public boolean snapshot;
			public int snapshotBuildNumber;
			public long snapshotTimeStamp;
			public String repositoryPath;
		}
	}
	@SuppressWarnings({"unused", "jol"})
	public static class JSON_sonatypeSearch_describeInfo {
		public $data data;

		public static class $data {
			public boolean presentLocally;
			public String repositoryId;
			public String repositoryName;
			public String repositoryPath;
			public String mimeType;
			public String uploader;
			public long uploaded;
			public long lastChanged;
			public long size;
			public String sha1Hash;
			public String md5Hash;
			public $repository[] repositories;
			public boolean canDelete;

			public static class $repository {
				public String repositoryId;
				public String repositoryName;
				public String path;
				public String artifactUrl;
				public boolean canView;
			}
		}
	}
}
