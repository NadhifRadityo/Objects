package GameLibrary.Jogamp;

import io.github.NadhifRadityo.Objects.Library.Constants.JSON_moduleRoot;
import io.github.NadhifRadityo.Objects.Library.Constants.Provider;
import io.github.NadhifRadityo.Objects.Library.Library;
import io.github.NadhifRadityo.Objects.Library.Providers.HTMLDirListingProvider;
import io.github.NadhifRadityo.Objects.Library.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Library.ThrowsReferencedCallback;
import io.github.NadhifRadityo.Objects.Library.Utils;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.delete;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.download;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.generateDefaultAndNativeLibrary;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.htmlDirDependencyParser;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.parseDependency;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.search;
import static io.github.NadhifRadityo.Objects.Library.Providers.HTMLDirListingProvider.GLOBAL_PROPERTIES_HTML_DIR_LISTING_HASHES;
import static io.github.NadhifRadityo.Objects.Library.Providers.__shared__.hashesAvailable;
import static io.github.NadhifRadityo.Objects.Library.Utils.downloadFile;
import static io.github.NadhifRadityo.Objects.Library.Utils.getCommandOutput;
import static io.github.NadhifRadityo.Objects.Library.Utils.getFileString;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_getObject;
import static io.github.NadhifRadityo.Objects.Library.Utils.pn_getBoolean;
import static io.github.NadhifRadityo.Objects.Library.Utils.pn_getObject;
import static io.github.NadhifRadityo.Objects.Library.Utils.writeFileString;

public class Main extends Library implements STATIC {
	public static void CLEAN(JSON_moduleRoot module) throws Exception {
		File buildDirectory = __static_dir();
		for(JSON_moduleRoot.$dependency dependency : module.dependencies)
			delete(dependency, buildDirectory, hashes);
		if(!buildDirectory.delete())
			warn("Couldn't delete folder");
	}

	public static void CONFIG(JSON_moduleRoot module) throws Exception {
		List<JSON_moduleRoot.$dependency> dependencies = new ArrayList<>();
		String[] blacklistFiles = new String[] { "www/", "javadoc/", "javadoc_dev/" };
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ThrowsReferencedCallback<org.jsoup.nodes.Document> getSource = (args) -> {
			String url = (String) args[0];
			String path = (String) args[1];
			return Jsoup.connect(url).get();
		};
		ThrowsReferencedCallback<HTMLDirListingProvider.HTMLFileDetails[]> fileProvider = (args) -> {
			org.jsoup.nodes.Document document = (org.jsoup.nodes.Document) args[0];
			String path = String.join("/", Stream.of(((String) args[1]).split("/")).skip(1).toArray(String[]::new));
			org.jsoup.select.Elements elements = document.select("body > table > tbody > tr");
			return elements.stream().limit(elements.size() - 1).skip(3).map(n -> {
				org.jsoup.nodes.Element nameElement = n.select("td:nth-child(2) > a").first();
				org.jsoup.nodes.Element lastModifiedElement = n.select("td:nth-child(3)").first();
				org.jsoup.nodes.Element sizeElement = n.select("td:nth-child(4)").first();
				org.jsoup.nodes.Element descriptionElement = n.select("td:nth-child(5)").first();
				for(String blacklistFile : blacklistFiles)
					if(nameElement.text().equals(blacklistFile)) return null;
				String name = nameElement.text();
				String link = nameElement.absUrl("href");
				long date = 0; try { date = dateFormat.parse(lastModifiedElement.text()).getTime();
				} catch(ParseException e) { warn(Utils.throwableToString(e)); }
				String sizeText = sizeElement.text();
				long sizeMul = sizeText.endsWith("K") ? 1024 : sizeText.endsWith("M") ? 1024 * 1024 : 1;
				double sizeNum = sizeText.equals("-") ? 0 : Double.parseDouble(sizeMul != 1 ? sizeText.substring(0, sizeText.length() - 1) : sizeText);
				long size = sizeText.equals("-") ? 0 : (long) (sizeNum * sizeMul);
				String description = descriptionElement.text();
				return new HTMLDirListingProvider.HTMLFileDetails(name.endsWith("/") ? name.substring(0, name.length() - 1) : name, path, link, name.endsWith("/"), date, size, description);
			}).filter(Objects::nonNull).toArray(HTMLDirListingProvider.HTMLFileDetails[]::new);
		};
		ThrowsReferencedCallback<HTMLDirListingProvider.HTMLFileDetails[]> versionProvider = (args) ->
				Stream.of(fileProvider.get(args)).filter(n -> n.name.startsWith(AT_LEAST_VERSION)).toArray(HTMLDirListingProvider.HTMLFileDetails[]::new);
		{
			JSON_moduleRoot.$dependency dependency = search(module.properties, Provider.HTML_DIR_LISTING.id, VERSION_LIST, getSource, versionProvider, fileProvider)[0];
			parseDependency(dependency, htmlDirDependencyParser((args) -> {
				JSON_moduleRoot.$dependency.$item item = (JSON_moduleRoot.$dependency.$item) args[0];
				String name = item.name;
				String path = pn_getObject(item.properties, "path");
				boolean directory = pn_getBoolean(item.properties, "directory");
				return !directory && (path.startsWith("jar") || (path.equals("") && name.contains(CHECKSUM_FILE)));
			}, (args) -> {
				JSON_moduleRoot.$dependency.$item item = (JSON_moduleRoot.$dependency.$item) args[0];
				String name = item.name;
				String path = pn_getObject(item.properties, "path");
				if(name.endsWith(".pom")) return "pom";
				if(path.equals("")) return name.contains(CHECKSUM_FILE) ? "hash" : "classes";
				if(name.contains("natives")) return null;
				return name.endsWith(".jar") ? "classes" : null;
			}, NATIVE_DIR));
			// Important to make the signature file on first index
			dependency.items = Stream.of(dependency.items).sorted((i1, i2) -> i1.name.contains(CHECKSUM_FILE) ? -1 : i2.name.contains(CHECKSUM_FILE) ? 1 : 0)
					.toArray(JSON_moduleRoot.$dependency.$item[]::new);
			Stream.of(dependency.items).filter(i -> i.name.equals(CHECKSUM_FILE)).findFirst().ifPresent(i -> {
//				p_setObject(i.properties, GLOBAL_PROPERTIES_HTML_DIR_LISTING_HASHES);
			});
			dependencies.add(dependency);
		}
		module.dependencies = dependencies.toArray(new JSON_moduleRoot.$dependency[0]);
	}

	public static void FETCH(JSON_moduleRoot module) throws Exception {
		File staticDirectory = __static_dir();
		ThrowsReferencedCallback<Void> download = (args) -> {
			File target = (File) args[0];
			String childLink = (String) args[1];
			ReferencedCallback<File> getFile = (ReferencedCallback<File>) args[2];
			JSON_moduleRoot.$dependency.$item item = (JSON_moduleRoot.$dependency.$item) args[3];
			String extension = (String) args[4];
			String name = item.name;
			String path = pn_getObject(item.properties, "path");

			if(extension.equals(CHECKSUM_EXTENSION)) {
				String hash = fileHash.get(path + "/" + name);
				if(hash == null) return null;
				writeFileString(target, hash);
				return null;
			}
			try(FileOutputStream fos = new FileOutputStream(target)) {
				downloadFile(new URL(childLink), fos);
			} return null;
		};
		for(JSON_moduleRoot.$dependency dependency : module.dependencies)
			download(dependency, staticDirectory, download, hashes);
	}

	public static void BUILD(JSON_moduleRoot module) throws Exception {
		File staticDirectory = __static_dir();
		File buildDirectory = __build_dir();

		File gamelib_nativesDir = new File(staticDirectory, NATIVE_DIR);
		if(!gamelib_nativesDir.exists() && !gamelib_nativesDir.mkdirs())
			throw new IllegalArgumentException();
		String result = getCommandOutput(staticDirectory, module.properties.getProperty("jarPath"), "-cf", JAR_NAME, NATIVE_DIR + "/*");
		if(result == null) throw new IllegalArgumentException("Error just occurred.");
		Files.move(new File(staticDirectory, JAR_NAME).toPath(), new File(buildDirectory, JAR_NAME).toPath(), StandardCopyOption.REPLACE_EXISTING);

		generateDefaultAndNativeLibrary(module, staticDirectory, buildDirectory, LIBRARY_NAME, LIBRARY_NATIVE_NAME, NATIVE_NAME, JAR_NAME);
	}

	public static ReferencedCallback<String> fileHash;
	public static ReferencedCallback<Map<String, List<ThrowsReferencedCallback<byte[]>>>> hashes;
	public static void PRE_MODULES(JSON_moduleRoot module) throws Exception {
		fileHash = new ReferencedCallback<String>() {
			final File hashFile = new File(__static_dir(), CHECKSUM_FILE);
			Map<String, String> fileHashes = null;
			@Override public String get(Object... args) {
				String path = (String) args[0];
				if(!hashFile.exists()) return null;
				if(fileHashes == null) {
					String hashFileContents;
					try { hashFileContents = getFileString(hashFile); } catch(IOException e) { throw new Error(e); }
					Pattern pattern = Pattern.compile("^([a-fA-F0-9]{128})\\s*\\*(.*)$", Pattern.MULTILINE);
					Matcher matcher = pattern.matcher(hashFileContents);
					fileHashes = new HashMap<>();
					while(matcher.find())
						fileHashes.put(matcher.group(2), matcher.group(1));
				}
				return fileHashes.get(path);
			}
		};
		hashes = (args) -> {
			JSON_moduleRoot.$dependency.$item item = (JSON_moduleRoot.$dependency.$item) args[0];
			if(item.properties.containsKey(GLOBAL_PROPERTIES_HTML_DIR_LISTING_HASHES))
				return hashesAvailable(item.properties, p_getObject(item.properties, GLOBAL_PROPERTIES_HTML_DIR_LISTING_HASHES));
			String name = item.name;
			String path = pn_getObject(item.properties, "path");
			String hash = fileHash.get(path + "/" + name);
			if(hash == null) return new HashMap<>();
			return hashesAvailable(item.properties, CHECKSUM_TYPE.get(item.properties));
		};
	}
}
