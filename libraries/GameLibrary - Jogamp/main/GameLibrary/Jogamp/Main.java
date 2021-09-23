package GameLibrary.Jogamp;

import io.github.NadhifRadityo.Objects.Library.Constants.JSON_configurationsRoot;
import io.github.NadhifRadityo.Objects.Library.Constants.Provider;
import io.github.NadhifRadityo.Objects.Library.Constants.Stage;
import io.github.NadhifRadityo.Objects.Library.Library;
import io.github.NadhifRadityo.Objects.Library.Providers.HTMLDirListingProvider;
import io.github.NadhifRadityo.Objects.Library.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Library.ThrowsReferencedCallback;
import io.github.NadhifRadityo.Objects.Library.Utils;
import org.jsoup.Jsoup;

import java.io.File;
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
import java.util.stream.Stream;

import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.delete;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.download;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.generateDefaultAndNativeLibrary;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.htmlDirDependencyParser;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.parseDependency;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.putDependencies;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.search;
import static io.github.NadhifRadityo.Objects.Library.Utils.getCommandOutput;

public class Main extends Library implements STATIC {
	public static void CLEAN(Stage stage, JSON_configurationsRoot.$module module) throws Exception {
		File buildDirectory = __static_dir();
		for(JSON_configurationsRoot.$module.$dependency dependency : module.dependencies)
			delete(dependency, buildDirectory, (ReferencedCallback<Map>) (args) -> new HashMap());
		if(!buildDirectory.delete())
			warn("Couldn't delete folder");
	}

	public static void CONFIG(Stage stage, JSON_configurationsRoot.$module module) throws Exception {
		List<JSON_configurationsRoot.$module.$dependency> dependencies = new ArrayList<>();
		String[] blacklistFiles = new String[] { "www/", "javadoc/", "javadoc_dev/" };
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ThrowsReferencedCallback<org.jsoup.nodes.Document> getSource = (args) -> {
			String url = (String) args[0];
			String path = (String) args[1];
			return Jsoup.connect(url).get();
		};
		ThrowsReferencedCallback<HTMLDirListingProvider.HTMLFileDetails[]> fileProvider = (args) -> {
			org.jsoup.nodes.Document document = (org.jsoup.nodes.Document) args[0];
			String path = "/" + String.join("/", Stream.of(((String) args[1]).split("/")).skip(2).toArray(String[]::new));
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
			JSON_configurationsRoot.$module.$dependency dependency = search(module.properties, Provider.HTML_DIR_LISTING.id, VERSION_LIST, getSource, versionProvider, fileProvider)[0];
			parseDependency(dependency, htmlDirDependencyParser((args) -> {
				String name = (String) args[0];
				if(name.endsWith(".pom")) return "pom";
				if(name.contains("natives")) return null;
				return name.endsWith(".jar") ? "classes" : null;
			}, "/jar", null, NATIVE_DIR));
			dependencies.add(dependency);
		}
		putDependencies(module, dependencies.toArray(new JSON_configurationsRoot.$module.$dependency[0]));
	}

	public static void FETCH(Stage stage, JSON_configurationsRoot.$module module) throws Exception {
		File staticDirectory = __static_dir();
		for(JSON_configurationsRoot.$module.$dependency dependency : module.dependencies)
			download(dependency, staticDirectory, (ReferencedCallback<Map>) (args) -> new HashMap());
	}

	public static void BUILD(Stage stage, JSON_configurationsRoot.$module module) throws Exception {
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
}
