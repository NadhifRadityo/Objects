package GameLibrary.LWJGL;

import io.github.NadhifRadityo.Objects.Library.Constants.JSON_configurationsRoot;
import io.github.NadhifRadityo.Objects.Library.Constants.Stage;
import io.github.NadhifRadityo.Objects.Library.Library;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.MAVEN_SONATYPE;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.createLibrary;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.defaultDependencyParser;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.delete;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.download;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.parseDependency;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.putDependencies;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.search;
import static io.github.NadhifRadityo.Objects.Library.Utils.createXMLFile;
import static io.github.NadhifRadityo.Objects.Library.Utils.getCommandOutput;
import static io.github.NadhifRadityo.Objects.Library.Utils.newXMLDocument;

public class Main extends Library implements STATIC {
	public static void CLEAN(Stage stage, JSON_configurationsRoot.$module module) throws Exception {
		File buildDirectory = __static_dir();
		for(JSON_configurationsRoot.$module.$dependency dependency : module.dependencies)
			delete(dependency, buildDirectory);
		if(!buildDirectory.delete())
			warn("Couldn't delete folder");
	}

	public static void CONFIG(Stage stage, JSON_configurationsRoot.$module module) throws Exception {
		List<JSON_configurationsRoot.$module.$dependency> dependencies = new ArrayList<>();
		for(String ARTIFACT : ARTIFACTS) {
			JSON_configurationsRoot.$module.$dependency dependency = search(module.properties, MAVEN_SONATYPE, GROUP, ARTIFACT, false)[0];
			parseDependency(dependency, defaultDependencyParser(NATIVE_DIR));
			dependencies.add(dependency);
		}
		putDependencies(module, dependencies.toArray(new JSON_configurationsRoot.$module.$dependency[0]));
	}

	public static void FETCH(Stage stage, JSON_configurationsRoot.$module module) throws Exception {
		File staticDirectory = __static_dir();
		for(JSON_configurationsRoot.$module.$dependency dependency : module.dependencies)
			download(dependency, staticDirectory);
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

		Document out = newXMLDocument(null);
		Element library = createLibrary(module, staticDirectory, out);
		createXMLFile(library, new File(buildDirectory, LIBRARY_NAME + ".xml"));
		Element nativeLibrary = createLibrary(NATIVE_NAME, out, new File[] { new File(buildDirectory, JAR_NAME) }, null, null);
		createXMLFile(nativeLibrary, new File(buildDirectory, LIBRARY_NATIVE_NAME + ".xml"));
	}
}
