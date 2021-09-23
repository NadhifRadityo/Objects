package io.github.NadhifRadityo.Library.Providers;

import io.github.NadhifRadityo.Library.CURL;
import io.github.NadhifRadityo.Library.JSON_mainRoot;
import io.github.NadhifRadityo.Library.ThrowsReferencedCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.github.NadhifRadityo.Library.LibraryEntry.getMainConfig;
import static io.github.NadhifRadityo.Library.Providers.__shared__.DEFAULT_HASH_OPTIONS_MD5_SHA1;
import static io.github.NadhifRadityo.Library.Providers.__shared__.VALIDATE_REQUEST_FETCH;
import static io.github.NadhifRadityo.Library.Providers.__shared__.VALIDATE_REQUEST_RUN;
import static io.github.NadhifRadityo.Library.Providers.__shared__.hashesAvailable;
import static io.github.NadhifRadityo.Library.Providers.__shared__.validate;
import static io.github.NadhifRadityo.Library.Utils.CommonUtils.downloadFile;
import static io.github.NadhifRadityo.Library.Utils.CommonUtils.formattedUrl;
import static io.github.NadhifRadityo.Library.Utils.CommonUtils.urlToUri;
import static io.github.NadhifRadityo.Library.Utils.FileUtils.mkfile;
import static io.github.NadhifRadityo.Library.Utils.JSONUtils.toJson;
import static io.github.NadhifRadityo.Library.Utils.JavascriptUtils.runJavascriptAsCallback;
import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.debug;
import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.info;
import static io.github.NadhifRadityo.Library.Utils.StringUtils.mostSafeString;

public class MavenProvider {
	public static final String MAIN_PROPERTIES_MAVEN_HASHES = "mavenHashes";
	protected static final MavenProvider DEFAULT;
	protected static final Map<String, List<ThrowsReferencedCallback<Boolean>>> DEFAULT_VALIDATIONS;
	protected static final ThrowsReferencedCallback<String> DEFAULT_SEARCH_URL_CALLBACK;
	protected static final ThrowsReferencedCallback<String> DEFAULT_DOWNLOAD_URL_CALLBACK;
	protected static final ThrowsReferencedCallback<Object> FILE_VALIDATION_CALLBACK;

	static {
		JSON_mainRoot mainConfig = getMainConfig();
		mainConfig.properties.putIfAbsent(MAIN_PROPERTIES_MAVEN_HASHES, String.join(",", DEFAULT_HASH_OPTIONS_MD5_SHA1(mainConfig.properties)));
		DEFAULT_VALIDATIONS = hashesAvailable(mainConfig.properties, mainConfig.properties.getProperty(MAIN_PROPERTIES_MAVEN_HASHES));
		DEFAULT_SEARCH_URL_CALLBACK = runJavascriptAsCallback("(g, a, v) => `https://search.maven.org/solrsearch/select?q=${[[\"g\", g], [\"a\", a], [\"v\", v]].map(([k, v]) => v != null ? `${k}:\"${v}\"` : null).filter(v => v != null).join(\"+AND+\")}&core=gav&wt=json`");
		DEFAULT_DOWNLOAD_URL_CALLBACK = runJavascriptAsCallback("(g, a, v, f) => `https://search.maven.org/remotecontent?filepath=${g.replace(/\\./g, '/')}/${a}/${v}/${f}`");
		FILE_VALIDATION_CALLBACK = (args) -> {
			MavenDependency dependency = (MavenDependency) args[0];
			String item = (String) args[1];
			File itemFile = (File) args[2];
			ThrowsReferencedCallback<File> itemFileCallback = (ThrowsReferencedCallback<File>) args[3];
			int type = (int) args[4];
			ThrowsReferencedCallback<Void> download = (ThrowsReferencedCallback<Void>) args[5];
			if(type == VALIDATE_REQUEST_RUN)
				return validate(DEFAULT_VALIDATIONS, (_args) -> {
					String extension = (String) _args[0];
					if(extension.isEmpty()) return itemFile;
					else return itemFileCallback.get(dependency, item + "." + extension);
				});
			if(type == VALIDATE_REQUEST_FETCH) {
				for(String extension : DEFAULT_VALIDATIONS.keySet()) {
					String itemName = item + "." + extension;
					download.get(dependency, itemName, itemFileCallback.get(dependency, itemName));
				}
				return null;
			}
			return null;
		};
		DEFAULT = new Builder()
				.searchUrlCallback(DEFAULT_SEARCH_URL_CALLBACK)
				.downloadUrlCallback(DEFAULT_DOWNLOAD_URL_CALLBACK)
				.fileValidationCallback(FILE_VALIDATION_CALLBACK)
				.build();
	}

	public static MavenProvider getDefaultMavenProvider() {
		return DEFAULT;
	}

	protected final ThrowsReferencedCallback<String> searchUrlCallback;
	protected final ThrowsReferencedCallback<String> downloadUrlCallback;
	protected final ThrowsReferencedCallback<Object> fileValidationCallback;

	protected MavenProvider(ThrowsReferencedCallback<String> searchUrlCallback, ThrowsReferencedCallback<String> downloadUrlCallback, ThrowsReferencedCallback<Object> fileValidationCallback) {
		this.searchUrlCallback = searchUrlCallback;
		this.downloadUrlCallback = downloadUrlCallback;
		this.fileValidationCallback = fileValidationCallback;
	}

	public ThrowsReferencedCallback<String> getSearchUrlCallback() { return searchUrlCallback; }
	public ThrowsReferencedCallback<String> getDownloadUrlCallback() { return downloadUrlCallback; }
	public ThrowsReferencedCallback<Object> getFileValidationCallback() { return fileValidationCallback; }

	public MavenDependency[] search(String group, String artifact, String version) throws Exception {
		CURL curl = new CURL();
		curl.setUrl(formattedUrl(searchUrlCallback.get(group, artifact, version)));
		curl.setRequestMethod(CURL.RequestMethod.GET);
		curl.setCustomHandler((args) -> { info("Searching repository \"%s\"... (%s)", group + "." + artifact, urlToUri((URL) args[1]).toASCIIString()); return null; });
		curl.setOnConnect((args) -> toJson(((URLConnection) args[0]).getInputStream(), JSON_mavenSearch.class));
		JSON_mavenSearch mavenSearch = (JSON_mavenSearch) curl.build(StandardCharsets.UTF_8);

		List<MavenDependency> results = new ArrayList<>();
		JSON_mavenSearch.$response.$doc[] docs = mavenSearch.response.docs;
		for(JSON_mavenSearch.$response.$doc doc : docs) {
			String id = doc.id;
			String g = doc.g;
			String a = doc.a;
			String v = doc.v;
			String p = doc.p;
			long timestamp = doc.timestamp;
			String[] ec = doc.ec;
			String[] tags = doc.tags;
			debug("g=\"%s\" a=\"%s\" v=\"%s\" p=\"%s\" id=\"%s\" timestamp=\"%s\" ec=\"%s\" tags=\"%s\"", g, a, v, p, id, timestamp, String.join(";", ec), String.join(";", tags));
			if(group != null && !group.equals(g)) continue;
			if(artifact != null && !artifact.equals(a)) continue;
			if(version != null && !version.equals(v)) continue;

			String[] items = Stream.of(ec).map(i ->  a + "-" + v + i).toArray(String[]::new);
			MavenDependency result = new MavenDependency(id, g, a, v, p, timestamp, items, tags);
			results.add(result);
		}
		results.sort((d1, d2) -> Long.compare(d2.getTimestamp(), d1.getTimestamp()));
		return results.toArray(new MavenDependency[0]);
	}

	public Map<String, File> download(MavenDependency dependency, ThrowsReferencedCallback<File> itemFileCallback) throws Exception {
		ThrowsReferencedCallback<Void> download = (args) -> {
			MavenDependency dependency_ = (MavenDependency) args[0];
			String item = (String) args[1];
			File itemFile = (File) args[2];
			mkfile(itemFile);
			try(FileOutputStream fos = new FileOutputStream(itemFile)) {
				downloadFile(new URL(downloadUrlCallback.get(dependency_.group, dependency_.artifact, dependency_.version, item)), fos);
			} return null;
		};
		return __shared__.download(dependency, (args) -> ((MavenDependency) args[0]).getItems(), itemFileCallback, this.fileValidationCallback, download);
	}
	public Map<String, File> download(MavenDependency dependency) throws Exception {
		return download(dependency, __shared__.defaultItemFileCallback(mostSafeString(dependency.id)));
	}

	public static class MavenDependency {
		protected final String id;
		protected final String group;
		protected final String artifact;
		protected final String version;
		protected final String packaging;
		protected final long timestamp;
		protected final String[] items;
		protected final String[] tags;

		public MavenDependency(String id, String group, String artifact, String version, String packaging, long timestamp, String[] items, String[] tags) {
			this.id = id;
			this.group = group;
			this.artifact = artifact;
			this.version = version;
			this.packaging = packaging;
			this.timestamp = timestamp;
			this.items = items;
			this.tags = tags;
		}

		public String getId() { return this.id; }
		public String getGroup() { return this.group; }
		public String getArtifact() { return this.artifact; }
		public String getVersion() { return this.version; }
		public String getPackaging() { return this.packaging; }
		public long getTimestamp() { return this.timestamp; }
		public String[] getItems() { return this.items; }
		public String[] getTags() { return this.tags; }

		public String getGav() { return String.format("%s.%s:%s", this.group, this.artifact, this.version); }
		public String getGa() { return String.format("%s.%s", this.group, this.artifact); }
	}
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
	public static class Builder {
		protected ThrowsReferencedCallback<String> searchUrlCallback;
		protected ThrowsReferencedCallback<String> downloadUrlCallback;
		protected ThrowsReferencedCallback<Object> fileValidationCallback;

		public Builder() {

		}

		public Builder searchUrlCallback(ThrowsReferencedCallback<String> searchUrlCallback) { this.searchUrlCallback = searchUrlCallback; return this; }
		public Builder downloadUrlCallback(ThrowsReferencedCallback<String> downloadUrlCallback) { this.downloadUrlCallback = downloadUrlCallback; return this; }
		public Builder fileValidationCallback(ThrowsReferencedCallback<Object> fileValidationCallback) { this.fileValidationCallback = fileValidationCallback; return this; }

		public ThrowsReferencedCallback<String> getSearchUrlCallback() { return searchUrlCallback; }
		public ThrowsReferencedCallback<String> getDownloadUrlCallback() { return downloadUrlCallback; }
		public ThrowsReferencedCallback<Object> getFileValidationCallback() { return fileValidationCallback; }

		public MavenProvider build() {
			return new MavenProvider(this.searchUrlCallback, this.downloadUrlCallback, this.fileValidationCallback);
		}
	}
}
