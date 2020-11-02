package io.github.NadhifRadityo.Objects.Utilizations.Library;

import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;

import java.io.File;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class WebLibrary extends LibraryUtils {
	private WebLibrary() { }

	private static boolean inited = false;
	public static boolean extractLibrary(JarEntry je, JarFile jar, File file) {
		return !inited && je.getName().startsWith("jcef_build");
	}
	private static void extractSuccess(File currentJar, File extractDir, List<File> extracteds) throws NoSuchFieldException, IllegalAccessException {
		if(inited) return; if(extracteds.size() == 0) return; JarUtils.addLibraryPath(new File(extractDir, "jcef_build/native/Release/").getAbsolutePath()); inited = true;
	}

	public static boolean isInited() { return inited; }
}
