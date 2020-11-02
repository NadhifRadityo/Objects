package io.github.NadhifRadityo.Objects.Utilizations.Library;

import io.github.NadhifRadityo.Objects.Utilizations.FileUtils;
import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;
import io.github.NadhifRadityo.Objects.Utilizations.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LWJGLLibrary extends LibraryUtils {
	private LWJGLLibrary() { }

	static {
		File[] tempFiles = LibraryUtils.defExtractDirFile.getParentFile().listFiles();
		if(tempFiles == null) tempFiles = new File[0];
		String identifier = "lwjgl" + System.getProperty("user.name");
		for(File file : tempFiles) {
			if(!file.getName().equals(identifier)) continue; try { FileUtils.deleteDirectory(file);
			} catch(Exception ignored) { System.err.println("Couldn't delete last LWJGL files."); }
			break; }
	}

	static {
		String internalId = "";
		String id = "";
		if(SystemUtils.IS_OS_WINDOWS) { internalId += "windows"; id += "windows"; }
		else if(SystemUtils.IS_OS_UNIX) { internalId += "linux"; id += "linux"; }
		else if(SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX) { internalId += "macos"; id += "macos"; }
		else throw new LinkageError("Unknown platform: " + SystemUtils.OS_NAME);

		internalId += "/";
		String osArch = SystemUtils.OS_ARCH;
		boolean is64Bit = osArch.contains("64") || osArch.startsWith("armv8");
		internalId += osArch.startsWith("arm") || osArch.startsWith("aarch64")
				? (is64Bit ? "arm64" : "arm32") : (is64Bit ? "x64" : "x86");
		id += osArch.startsWith("arm") || osArch.startsWith("aarch64")
				? (is64Bit ? "-arm64" : "-arm32") : (is64Bit ? "" : "-x86");

		internalIdentifier = internalId;
		identifier = id + ".jar";
	}

	private static final String internalIdentifier;
	private static final String identifier;
	private static boolean inited = false;
	public static boolean extractLibrary(JarEntry je, JarFile jar, File file) {
		return !inited && je.getName().startsWith("gamelib_natives") && je.getName().endsWith(identifier);
	}
	private static void extractSuccess(File currentJar, File extractDir, List<File> extracteds) throws IOException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		if(inited) return; if(extracteds.size() == 0) return;
		extractDir = new File(extractDir, "gamelib_natives"); for(File extracted : extracteds)
			JarUtils.extractFileFromJar(extracted, extractDir, (je, jar) -> je.getName().startsWith(internalIdentifier));
		extractDir = new File(extractDir, internalIdentifier + File.separator + "org" + File.separator + "lwjgl");
		JarUtils.addLibraryPath(extractDir.getAbsolutePath()); File[] listFiles = extractDir.listFiles();
		if(listFiles != null) for(File extracted : listFiles)
			if(extracted.isDirectory()) JarUtils.addLibraryPath(extracted.getAbsolutePath());
		JarUtils.addJarToClassPath(extracteds.toArray(new File[0])); inited = true;
	}

	public static boolean isInited() { return inited; }
}
