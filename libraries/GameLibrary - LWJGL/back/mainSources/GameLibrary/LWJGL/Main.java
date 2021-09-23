package GameLibrary.LWJGL;

import io.github.NadhifRadityo.Objects.Library.Constants.JSON_moduleRoot;
import io.github.NadhifRadityo.Objects.Library.Library;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.MAVEN_SONATYPE;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.delete;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.download;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.generateDefaultAndNativeLibrary;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.mavenSonatypeDependencyParser;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.parseDependency;
import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.search;
import static io.github.NadhifRadityo.Objects.Library.Utils.getCommandOutput;

public class Main extends Library implements STATIC {
	public static void CLEAN(JSON_moduleRoot module) throws Exception {
		File buildDirectory = __static_dir();
		for(JSON_moduleRoot.$dependency dependency : module.dependencies)
			delete(dependency, buildDirectory);
		if(!buildDirectory.delete())
			warn("Couldn't delete folder");
	}

	public static void CONFIG(JSON_moduleRoot module) throws Exception {
		List<JSON_moduleRoot.$dependency> dependencies = new ArrayList<>();
		for(String ARTIFACT : ARTIFACTS) {
			JSON_moduleRoot.$dependency dependency = search(module.properties, MAVEN_SONATYPE, GROUP, ARTIFACT, false)[0];
			parseDependency(dependency, mavenSonatypeDependencyParser(NATIVE_DIR));
			dependencies.add(dependency);
		}
		module.dependencies = dependencies.toArray(new JSON_moduleRoot.$dependency[0]);
	}

	public static void FETCH(JSON_moduleRoot module) throws Exception {
		File staticDirectory = __static_dir();
		for(JSON_moduleRoot.$dependency dependency : module.dependencies)
			download(dependency, staticDirectory);
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
}
