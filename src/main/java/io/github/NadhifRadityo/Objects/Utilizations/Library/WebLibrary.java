package io.github.NadhifRadityo.Objects.Utilizations.Library;

import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;
import io.github.NadhifRadityo.Objects.Utilizations.JarUtils.JarEntryFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class WebLibrary {
	private WebLibrary() { }
	
	private static boolean inited = false;
	public static File[] initNatives(File currentJar, File extractDir) throws IOException, SecurityException, IllegalAccessException,
			IllegalArgumentException, NoSuchFieldException {
		if(currentJar == null || extractDir == null || inited) return null;
		File[] extracteds = JarUtils.extractFileFromJar(currentJar, extractDir, file -> {
			if(!(file instanceof JarEntryFile)) return false;
			return ((JarEntryFile) file).getJarEntry().getName().startsWith("jcef_build");
		}); if(extracteds.length == 0) return null;
		JarUtils.addLibraryPath(new File(extractDir, "jcef_build/native/Release/").getAbsolutePath());
		for(File extracted : extracteds) extracted.deleteOnExit(); inited = true;
		return extracteds;
	}
	public static File[] initNatives(File extractDir) throws IOException, URISyntaxException, SecurityException,
			IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
		return initNatives(JarUtils.getCurrentJar(), extractDir);
	}
	public static File[] initNatives() throws IOException, URISyntaxException, SecurityException,
			IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
		return initNatives(LibraryUtils.defExtractDirFile);
	}
	
	public static boolean isInited() { return inited; }
}
