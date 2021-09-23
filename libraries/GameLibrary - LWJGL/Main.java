import java.util.*;
import java.io.*;
import org.w3c.dom.*;

import io.github.NadhifRadityo.Objects.Library.*;
import io.github.NadhifRadityo.Objects.Library.Constants.*;

import static io.github.NadhifRadityo.Objects.Library.LibraryUtils.*;
import static io.github.NadhifRadityo.Objects.Library.Utils.*;

public class Main extends Library {
	public static final String NAME = "GameLibrary - LWJGL";
	public static final String NATIVE_NAME = "GameLibrary - LWJGL - Natives";
	public static final String LIBRARY_NAME = NAME.replaceAll("[^A-Za-z0-9]", "_");
	public static final String LIBRARY_NATIVE_NAME = NATIVE_NAME.replaceAll("[^A-Za-z0-9]", "_");
	public static final String BUILD_DIR = "build";
	public static final String JAR_NAME = "natives.jar";
	public static final String NATIVE_DIR = "gamelib_natives";
	public static final String GROUP = "org.lwjgl";
	public static final String[] ARTIFACTS = new String[] {
			"lwjgl", "lwjgl-assimp", "lwjgl-bgfx", "lwjgl-bom", "lwjgl-cuda", "lwjgl-egl", "lwjgl-glfw", "lwjgl-jawt", "lwjgl-jemalloc",
			"lwjgl-libdivide", "lwjgl-llvm", "lwjgl-lmdb", "lwjgl-lz4", "lwjgl-meow", "lwjgl-nanovg", "lwjgl-nfd", "lwjgl-nuklear", "lwjgl-odbc",
			"lwjgl-openal", "lwjgl-opencl", "lwjgl-opengl", "lwjgl-opengles", "lwjgl-openvr", "lwjgl-opus", "lwjgl-ovr", "lwjgl-par", "lwjgl-platform",
			"lwjgl-remotery", "lwjgl-rpmalloc", "lwjgl-shaderc", "lwjgl-sse", "lwjgl-stb", "lwjgl-tinyexr", "lwjgl-tinyfd", "lwjgl-tootle", "lwjgl-vma",
			"lwjgl-vulkan", "lwjgl-xxhash", "lwjgl-yoga", "lwjgl-zstd"
	};

	public static File __build_dir(File moduleDirectory) {
		File buildDirectory = new File(moduleDirectory, BUILD_DIR);
		if(!buildDirectory.exists() && !buildDirectory.mkdirs())
			throw new IllegalArgumentException();
		return buildDirectory;
	}

	public static void CLEAN(File moduleDirectory, JSON_configurationsRoot.$module module) throws Exception {
		File buildDirectory = __build_dir(moduleDirectory);
		for(JSON_configurationsRoot.$module.$dependency dependency : module.dependencies)
			delete(dependency, buildDirectory);
		if(!buildDirectory.delete())
			throw new IllegalArgumentException();
	}

	public static void CONFIG(File moduleDirectory, JSON_configurationsRoot.$module module) throws Exception {
		File buildDirectory = __build_dir(moduleDirectory);
		List<JSON_configurationsRoot.$module.$dependency> dependencies = new ArrayList<>();
		for(String ARTIFACT : ARTIFACTS) {
			JSON_configurationsRoot.$module.$dependency dependency = search(module.properties, MAVEN_SONATYPE, GROUP, ARTIFACT, false)[0];
			parseDependency(dependency, defaultDependencyParser(NATIVE_DIR));
			dependencies.add(dependency);
		}
		putDependencies(module, dependencies.toArray(new JSON_configurationsRoot.$module.$dependency[0]));
	}

	public static void FETCH(File moduleDirectory, JSON_configurationsRoot.$module module) throws Exception {
		File buildDirectory = __build_dir(moduleDirectory);
		for(JSON_configurationsRoot.$module.$dependency dependency : module.dependencies)
			download(dependency, buildDirectory);
	}

	public static void BUILD(File moduleDirectory, JSON_configurationsRoot.$module module) throws Exception {
		File buildDirectory = __build_dir(moduleDirectory);

		File gamelib_nativesDir = new File(buildDirectory, NATIVE_DIR);
		if(!gamelib_nativesDir.exists() && !gamelib_nativesDir.mkdirs())
			throw new IllegalArgumentException();
		String result = getCommandOutput(buildDirectory, module.properties.getProperty("jarPath"), "-cf", JAR_NAME, NATIVE_DIR + "/*");
		if(result == null) throw new IllegalArgumentException("Error just occured.");

		Document out = newXMLDocument(null);
		Element library = createLibrary(module, buildDirectory, out);
		createXMLFile(library, new File(buildDirectory, LIBRARY_NAME + ".xml"));
		Element nativeLibrary = createLibrary(NATIVE_NAME, out, new File[] { new File(buildDirectory, JAR_NAME) }, null, null);
		createXMLFile(nativeLibrary, new File(buildDirectory, LIBRARY_NATIVE_NAME + ".xml"));
	}
}
