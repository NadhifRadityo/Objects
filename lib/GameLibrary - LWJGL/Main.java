import java.util.*;
import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

public class Main extends Library {
	public static final String NAME = "GameLibrary - LWJGL";
	public static final String NATIVE_NAME = "GameLibrary - LWJGL - Natives";
	public static final String LIBRARY_NAME = NAME.replaceAll("[^A-Za-z0-9]", "_");
	public static final String LIBRARY_NATIVE_NAME = NATIVE_NAME.replaceAll("[^A-Za-z0-9]", "_");
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

	public static void download(Document dom, Element[] dependencies) throws Exception {
		File currentDir = Utils.getCurrentClassFile(Main.class).getParentFile();
		for(Element dependency : dependencies)
			Library.LibUtils.downloadMaven(dependency, currentDir);
	}

	public static void build(Document dom, Element[] dependencies) throws Exception {
		File currentDir = Utils.getCurrentClassFile(Main.class).getParentFile();
		File gamelib_nativesDir = new File(currentDir, NATIVE_DIR);
		if(!gamelib_nativesDir.exists() && !gamelib_nativesDir.mkdirs())
			throw new IllegalArgumentException();
		String result = Utils.getCommandOutput(currentDir, properties.getProperty("jarPath"), "-cf", JAR_NAME, NATIVE_DIR + "/*");
		if(result == null) throw new IllegalArgumentException("Error just occured.");

		Element library = LibUtils.createLibrary(NAME, dom, dependencies, currentDir);
		Utils.createXMLFile(library, new File(currentDir, LIBRARY_NAME + ".xml"));
		Element nativeLibrary = LibUtils.createLibrary(NATIVE_NAME, dom, new File[] { new File(currentDir, JAR_NAME) }, null, null);
		Utils.createXMLFile(nativeLibrary, new File(currentDir, LIBRARY_NATIVE_NAME + ".xml"));
	}

	public static void clean(Document dom, Element[] dependencies) throws Exception {
		File currentDir = Utils.getCurrentClassFile(Main.class).getParentFile();
		File nativesJar = new File(currentDir, JAR_NAME);
		if(nativesJar.exists() && !nativesJar.delete())
			warn("Couldn't delete file! File=%s", nativesJar.getAbsolutePath());

		File libraryFile = new File(currentDir, LIBRARY_NAME + ".xml");
		if(libraryFile.exists() && !libraryFile.delete())
			warn("Couldn't delete file! File=%s", libraryFile.getAbsolutePath());
		File libraryNativeFile = new File(currentDir, LIBRARY_NATIVE_NAME + ".xml");
		if(libraryNativeFile.exists() && !libraryNativeFile.delete())
			warn("Couldn't delete file! File=%s", libraryNativeFile.getAbsolutePath());
	}

	public static Element[] configure(Document dom) throws Exception {
		List<Element> result = new ArrayList<>();
		for(String artifact : ARTIFACTS) {
			Element version = LibUtils.searchMaven(GROUP, artifact)[0];
			Element dependencies = LibUtils.createDefaultMaven(dom, version, LibUtils.defaultMavenCallback(NATIVE_DIR));
			result.add(dependencies);
		}
		Element version = LibUtils.searchMaven("org.lwjgl.lwjgl", "lwjgl_util")[0];
		Element dependencies = LibUtils.createDefaultMaven(dom, version, LibUtils.defaultMavenCallback("."));
		result.add(dependencies);
		return result.toArray(new Element[0]);
	}
}
