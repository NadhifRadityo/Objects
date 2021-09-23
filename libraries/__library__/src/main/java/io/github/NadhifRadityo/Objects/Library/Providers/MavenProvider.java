package io.github.NadhifRadityo.Objects.Library.Providers;

import io.github.NadhifRadityo.Objects.Library.CURL;
import io.github.NadhifRadityo.Objects.Library.Constants.JSON_configurationsRoot;
import io.github.NadhifRadityo.Objects.Library.Constants.Phase;
import io.github.NadhifRadityo.Objects.Library.Constants.Provider;
import io.github.NadhifRadityo.Objects.Library.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Library.ThrowsReferencedCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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
import static io.github.NadhifRadityo.Objects.Library.Utils.p_getLong;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_getObject;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_setLong;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_setObject;
import static io.github.NadhifRadityo.Objects.Library.Utils.pn_getLong;
import static io.github.NadhifRadityo.Objects.Library.Utils.pn_getObject;
import static io.github.NadhifRadityo.Objects.Library.Utils.runJavascript;
import static io.github.NadhifRadityo.Objects.Library.Utils.toJson;
import static io.github.NadhifRadityo.Objects.Library.Utils.urlToUri;

public class MavenProvider {
	public static ThrowsReferencedCallback<Void> PHASE_PRE = (args) -> {
		Phase phase = (Phase) args[0];
		File directory = (File) args[1];
		JSON_configurationsRoot configurations = (JSON_configurationsRoot) args[2];
		Map<String, Class<?>> libraryClasses = (Map<String, Class<?>>) args[3];
		phase_pre(phase, directory, configurations, libraryClasses);
		return null;
	};
	public static ThrowsReferencedCallback<Void> PHASE_POST = (args) -> {
		Phase phase = (Phase) args[0];
		File directory = (File) args[1];
		JSON_configurationsRoot configurations = (JSON_configurationsRoot) args[2];
		Map<String, Class<?>> libraryClasses = (Map<String, Class<?>>) args[3];
		phase_post(phase, directory, configurations, libraryClasses);
		return null;
	};
	
	public static String GLOBAL_PROPERTIES_MAVEN_SEARCH = "mavenSearch";
	public static String GLOBAL_PROPERTIES_MAVEN_DOWNLOAD = "mavenDownload";
	public static String GLOBAL_PROPERTIES_MAVEN_HASHES = "mavenHashes";

	public static void phase_pre(Phase phase, File directory, JSON_configurationsRoot configurations, Map<String, Class<?>> libraryClasses) {
		switch(phase) {
			case CLEAN: { break; }
			case CONFIG: {
				configurations.properties.putIfAbsent(GLOBAL_PROPERTIES_MAVEN_SEARCH, "(g, a) => `https://search.maven.org/solrsearch/select?q=g:\"${g}\"+AND+a:\"${a}\"&core=gav&wt=json`");
				configurations.properties.putIfAbsent(GLOBAL_PROPERTIES_MAVEN_DOWNLOAD, "(g, a, v, f) => `https://search.maven.org/remotecontent?filepath=${g.replace(/\\./g, '/')}/${a}/${v}/${f}`");
				configurations.properties.putIfAbsent(GLOBAL_PROPERTIES_MAVEN_HASHES, String.join(",", DEFAULT_HASH_OPTIONS_ALL(configurations.properties)));
				break;
			}
			case FETCH: { break; }
			case BUILD: { break; }
		}
	}
	public static void phase_post(Phase phase, File directory, JSON_configurationsRoot configurations, Map<String, Class<?>> libraryClasses) {
		switch(phase) {
			case CLEAN: { break; }
			case CONFIG: { break; }
			case FETCH: { break; }
			case BUILD: { break; }
		}
	}

	public static ThrowsReferencedCallback<JSON_configurationsRoot.$module.$dependency[]> SEARCH = (args) -> {
		Properties properties = (Properties) args[0];
		String group = (String) args[1];
		String artifact = (String) args[2];
		boolean snapshots = (boolean) args[3];
		if(snapshots) warn("Maven does not support snapshots build.");
		return search(properties, group, artifact);
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
	
	public static JSON_configurationsRoot.$module.$dependency[] search(Properties properties, String group, String artifact) throws Exception {
		CURL curl = new CURL();
		curl.setUrl(formattedUrl((String) runJavascript(p_getObject(properties, GLOBAL_PROPERTIES_MAVEN_SEARCH), group, artifact)));
		curl.setRequestMethod(CURL.RequestMethod.GET);
		curl.setCustomHandler((args) -> { info("Searching repository \"%s\"... (%s)", group + "." + artifact, urlToUri((URL) args[1]).toASCIIString()); return null; });
		curl.setOnConnect((args) -> toJson(((URLConnection) args[0]).getInputStream(), JSON_mavenSearch.class));
		JSON_mavenSearch mavenSearch = (JSON_mavenSearch) curl.build(StandardCharsets.UTF_8);

		List<JSON_configurationsRoot.$module.$dependency> results = new ArrayList<>();
		JSON_mavenSearch.$response.$doc[] docs = mavenSearch.response.docs;
		for(JSON_mavenSearch.$response.$doc doc : docs) {
			String id = doc.id;
			String g = doc.g;
			String a = doc.a;
			String v = doc.v;
			String[] ec = doc.ec;
			long timestamp = doc.timestamp;
			debug("g=\"%s\" a=\"%s\" id=\"%s\" v=\"%s\" ec=\"%s\" timestamp=\"%s\"", g, a, id, v, String.join(";", Arrays.asList(ec)), timestamp);
			if(!group.equals(g) || !artifact.equals(a)) continue;

			JSON_configurationsRoot.$module.$dependency result = new JSON_configurationsRoot.$module.$dependency();
			result.id = id;
			result.source = Provider.MAVEN;
			result.properties = new Properties(properties);
			p_setObject(result.properties, DEPENDENCY_PROPERTIES_GROUP, g);
			p_setObject(result.properties, DEPENDENCY_PROPERTIES_ARTIFACT, a);
			p_setObject(result.properties, DEPENDENCY_PROPERTIES_VERSION, v);
			p_setLong(result.properties, DEPENDENCY_PROPERTIES_TIMESTAMP, timestamp);
			result.items = new JSON_configurationsRoot.$module.$dependency.$item[ec.length];
			for(int i = 0; i < result.items.length; i++) {
				JSON_configurationsRoot.$module.$dependency.$item item = new JSON_configurationsRoot.$module.$dependency.$item();
				item.name = a + "-" + v + ec[i];
				item.properties = new Properties(result.properties);
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

		ThrowsReferencedCallback<Void> download = (args) -> {
			ReferencedCallback<File> getFile = (ReferencedCallback<File>) args[0];
			JSON_configurationsRoot.$module.$dependency.$item item = (JSON_configurationsRoot.$module.$dependency.$item) args[1];
			String extension = (String) args[2];
			File target = getFile.get(extension);
			if(!target.exists() && !target.createNewFile())
				throw new IllegalArgumentException();
			try(FileOutputStream fos = new FileOutputStream(target)) {
				downloadFile(new URL((String) runJavascript(p_getObject(dependency.properties, GLOBAL_PROPERTIES_MAVEN_DOWNLOAD), group, artifact, version, target.getName())), fos);
			} return null;
		};
		ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> hashes = (args) -> {
			JSON_configurationsRoot.$module.$dependency.$item item = (JSON_configurationsRoot.$module.$dependency.$item) args[0];
			return hashesAvailable(item.properties, p_getObject(item.properties, GLOBAL_PROPERTIES_MAVEN_HASHES));
		};
		return downloadDefault(dependency, currentDir, download, hashes);
	}
	public static boolean[] delete(JSON_configurationsRoot.$module.$dependency dependency, File currentDir) throws Exception {
		ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> hashes = (args) -> {
			JSON_configurationsRoot.$module.$dependency.$item item = (JSON_configurationsRoot.$module.$dependency.$item) args[0];
			return hashesAvailable(item.properties, p_getObject(item.properties, GLOBAL_PROPERTIES_MAVEN_HASHES));
		};
		return deleteDefault(dependency, currentDir, hashes);
	}

	@SuppressWarnings("unused")
	public static class JSON_mavenSearch {
		public $responseHeader responseHeader;
		public $response response;

		public static class $responseHeader {
			public int status;
			public int QTime;
			public $params params;

			public static class $params {
				public String q;
				public String core;
				public String indent;
				public String fl;
				public String start;
				public String sort;
				public String rows;
				public String wt;
				public String version;
			}
		}
		public static class $response {
			public int numFound;
			public int start;
			public $doc[] docs;

			public static class $doc {
				public String id;
				public String g;
				public String a;
				public String v;
				public String p;
				public long timestamp;
				public String[] ec;
				public String[] tags;
			}
		}
	}
}
