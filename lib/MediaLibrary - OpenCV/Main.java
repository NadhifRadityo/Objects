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
	public static final String NAME = "MediaLibrary - OpenCV";
	public static final String NATIVE_NAME = "MediaLibrary - OpenCV - Natives";
	public static final String LIBRARY_NAME = NAME.replaceAll("[^A-Za-z0-9]", "_");
	public static final String LIBRARY_NATIVE_NAME = NATIVE_NAME.replaceAll("[^A-Za-z0-9]", "_");
	public static final String JAR_NAME = "natives.jar";
	public static final String NATIVE_DIR = "opencv";
	public static final String GROUP = "org.bytedeco";
	public static final String ARTIFACT = "opencv";

	public static void download(Document dom, Element[] dependencies) throws Exception {
		File currentDir = Utils.getCurrentClassFile(Main.class).getParentFile();
		for(Element dependency : dependencies)
			LibUtils.downloadMaven(dependency, currentDir);
	}

	public static void build(Document dom, Element[] dependencies) throws Exception {
		File currentDir = Utils.getCurrentClassFile(Main.class).getParentFile();
		File opencvDir = new File(currentDir, NATIVE_DIR);
		if(!opencvDir.exists() && !opencvDir.mkdirs())
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
		Element version = LibUtils.searchMaven(GROUP, ARTIFACT)[0];
		Element dependencies = LibUtils.createDefaultMaven(dom, version, LibUtils.defaultMavenCallback(NATIVE_DIR));
		return new Element[] { dependencies };
	}
}
