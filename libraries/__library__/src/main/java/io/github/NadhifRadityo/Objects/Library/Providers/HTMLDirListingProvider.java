package io.github.NadhifRadityo.Objects.Library.Providers;

import io.github.NadhifRadityo.Objects.Library.Constants.JSON_configurationsRoot;
import io.github.NadhifRadityo.Objects.Library.Constants.Phase;
import io.github.NadhifRadityo.Objects.Library.Constants.Provider;
import io.github.NadhifRadityo.Objects.Library.Constants.Stage;
import io.github.NadhifRadityo.Objects.Library.LibraryPack;
import io.github.NadhifRadityo.Objects.Library.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Library.ThrowsReferencedCallback;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import static io.github.NadhifRadityo.Objects.Library.Library.debug;
import static io.github.NadhifRadityo.Objects.Library.Providers.__shared__.deleteDefault;
import static io.github.NadhifRadityo.Objects.Library.Providers.__shared__.downloadDefault;
import static io.github.NadhifRadityo.Objects.Library.Utils.downloadFile;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_getLong;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_setBoolean;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_setLong;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_setObject;
import static io.github.NadhifRadityo.Objects.Library.Utils.pn_getBoolean;
import static io.github.NadhifRadityo.Objects.Library.Utils.pn_getLong;
import static io.github.NadhifRadityo.Objects.Library.Utils.pn_getObject;

public class HTMLDirListingProvider {
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

	public static void phase_pre(Phase phase, Stage stage, File directory, JSON_configurationsRoot configurations, List<LibraryPack> libraryPacks) {
		switch(phase) {
			case CLEAN: { break; }
			case CONFIG: { break; }
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
		String source = (String) args[1];
		ThrowsReferencedCallback<Document> getSource = (ThrowsReferencedCallback<Document>) args[2];
		ThrowsReferencedCallback<HTMLFileDetails[]> versionProvider = (ThrowsReferencedCallback<HTMLFileDetails[]>) args[3];
		ThrowsReferencedCallback<HTMLFileDetails[]> fileProvider = (ThrowsReferencedCallback<HTMLFileDetails[]>) args[4];
		return search(properties, source, getSource, versionProvider, fileProvider);
	};
	public static ThrowsReferencedCallback<boolean[]> DOWNLOAD = (args) -> {
		JSON_configurationsRoot.$module.$dependency dependency = (JSON_configurationsRoot.$module.$dependency) args[0];
		File currentDir = (File) args[1];
		ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> hashes = (ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>>) args[2];
		return download(dependency, currentDir, hashes);
	};
	public static ThrowsReferencedCallback<boolean[]> DELETE = (args) -> {
		JSON_configurationsRoot.$module.$dependency dependency = (JSON_configurationsRoot.$module.$dependency) args[0];
		File currentDir = (File) args[1];
		ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> hashes = (ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>>) args[2];
		return delete(dependency, currentDir, hashes);
	};

	public static final String DEPENDENCY_PROPERTIES_LINK = "link";
	public static final String DEPENDENCY_PROPERTIES_DATE = "date";
	public static final String DEPENDENCY_PROPERTIES_DESCRIPTION = "description";
	public static final String ITEM_PROPERTIES_PATH = "path";
	public static final String ITEM_PROPERTIES_LINK = "link";
	public static final String ITEM_PROPERTIES_DIRECTORY = "directory";
	public static final String ITEM_PROPERTIES_DATE = "date";
	public static final String ITEM_PROPERTIES_SIZE = "size";
	public static final String ITEM_PROPERTIES_DESCRIPTION = "description";

	public static JSON_configurationsRoot.$module.$dependency[] search(Properties properties, String url, ThrowsReferencedCallback<Document> getSource, ThrowsReferencedCallback<HTMLFileDetails[]> versionProvider, ThrowsReferencedCallback<HTMLFileDetails[]> fileProvider) throws Exception {
		Document document = getSource.get(url, "/");
		HTMLFileDetails[] versions = Stream.of(versionProvider.get(document, "/")).filter(v -> v.directory).toArray(HTMLFileDetails[]::new);
		ThrowsReferencedCallback<Void> getChilds = new ThrowsReferencedCallback<Void>() {
			@Override public Void get(Object... args) throws Exception {
				String childUrl = (String) args[0];
				String childPath = (String) args[1];
				debug("Visiting %s -> %s", childPath, childUrl);
				List<HTMLFileDetails> children = (List<HTMLFileDetails>) args[2];
				Document childDocument = getSource.get(childUrl, childPath);
				for(HTMLFileDetails child : fileProvider.get(childDocument, childPath)) {
					debug("File n=%s dir=%s t=%s s=%s d=%s", child.name, child.directory, child.date, child.size, child.description);
					children.add(child);
					if(!child.directory) continue;
					get(child.link, childPath + "/" + child.name, children);
				}
				return null;
			}
		};

		for(HTMLFileDetails version : versions)
			debug("v=%s t=%s d=%s", version.name, version.date, version.description);
		List<JSON_configurationsRoot.$module.$dependency> results = new ArrayList<>();
		for(HTMLFileDetails version : versions) {
			List<HTMLFileDetails> children = new ArrayList<>();
			getChilds.get(version.link, "/" + version.name, children);

			JSON_configurationsRoot.$module.$dependency result = new JSON_configurationsRoot.$module.$dependency();
			result.id = version.name;
			result.source = Provider.HTML_DIR_LISTING;
			result.properties = new Properties(properties);
			p_setObject(result.properties, DEPENDENCY_PROPERTIES_LINK, version.link);
			p_setLong(result.properties, DEPENDENCY_PROPERTIES_DATE, version.date);
			p_setObject(result.properties, DEPENDENCY_PROPERTIES_DESCRIPTION, version.description);
			result.items = new JSON_configurationsRoot.$module.$dependency.$item[children.size()];
			for(int i = 0; i < result.items.length; i++) {
				HTMLFileDetails child = children.get(i);
				JSON_configurationsRoot.$module.$dependency.$item item = new JSON_configurationsRoot.$module.$dependency.$item();
				item.name = child.name;
				item.properties = new Properties(result.properties);
				p_setObject(item.properties, ITEM_PROPERTIES_PATH, child.path);
				p_setObject(item.properties, ITEM_PROPERTIES_LINK, child.link);
				p_setBoolean(item.properties, ITEM_PROPERTIES_DIRECTORY, child.directory);
				p_setLong(item.properties, ITEM_PROPERTIES_DATE, child.date);
				p_setLong(item.properties, ITEM_PROPERTIES_SIZE, child.size);
				p_setObject(item.properties, ITEM_PROPERTIES_DESCRIPTION, child.description);
				result.items[i] = item;
			}
			results.add(result);
		}
		results.sort((v1, v2) -> Long.compare(p_getLong(v2.properties, DEPENDENCY_PROPERTIES_DATE), p_getLong(v1.properties, DEPENDENCY_PROPERTIES_DATE)));
		return results.toArray(new JSON_configurationsRoot.$module.$dependency[0]);
	}
	public static boolean[] download(JSON_configurationsRoot.$module.$dependency dependency, File currentDir, ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> hashes) throws Exception {
		String link = pn_getObject(dependency.properties, DEPENDENCY_PROPERTIES_LINK);
		long date = pn_getLong(dependency.properties, DEPENDENCY_PROPERTIES_DATE);
		String description = pn_getObject(dependency.properties, DEPENDENCY_PROPERTIES_DESCRIPTION);

		ThrowsReferencedCallback<Void> download = (args) -> {
			ReferencedCallback<File> getFile = (ReferencedCallback<File>) args[0];
			JSON_configurationsRoot.$module.$dependency.$item item = (JSON_configurationsRoot.$module.$dependency.$item) args[1];
			String extension = (String) args[2];
			File target = getFile.get(extension);

			String childLink = pn_getObject(item.properties, ITEM_PROPERTIES_LINK);
			boolean childDirectory = pn_getBoolean(item.properties, ITEM_PROPERTIES_DIRECTORY);
			if(childDirectory) {
				if(!target.exists() && !target.mkdirs())
					throw new IllegalArgumentException();
				return null;
			}
			if(!target.exists() && !target.createNewFile())
				throw new IllegalArgumentException();
			try(FileOutputStream fos = new FileOutputStream(target)) {
				downloadFile(new URL(childLink), fos);
			} return null;
		};
		return downloadDefault(dependency, currentDir, download, hashes);
	}
	public static boolean[] delete(JSON_configurationsRoot.$module.$dependency dependency, File currentDir, ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> hashes) throws Exception {
		return deleteDefault(dependency, currentDir, hashes);
	}

	public static class HTMLFileDetails {
		public final String name;
		public final String path;
		public final String link;
		public final boolean directory;
		public final long date;
		public final long size;
		public final String description;

		public HTMLFileDetails(String name, String path, String link, boolean directory, long date, long size, String description) {
			this.name = name;
			this.path = path;
			this.link = link;
			this.directory = directory;
			this.date = date;
			this.size = size;
			this.description = description;
		}
	}
}
