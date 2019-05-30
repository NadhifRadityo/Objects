package io.github.NadhifRadityo.Objects.Utilizations.Library;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;
import io.github.NadhifRadityo.Objects.Utilizations.JarUtils.JarEntryFile;

public class WebLibrary {
	private WebLibrary() { }
	
	private static boolean inited = false;
	public static File[] initNatives(File currentJar, File extractDir) throws URISyntaxException, IOException, NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		if(currentJar == null || extractDir == null || inited) return null;
		File[] extracteds = JarUtils.extractFileFromJar(currentJar, extractDir, new FileFilter() {
			@Override public boolean accept(File file) {
				if(!(file instanceof JarEntryFile)) return false;
				return ((JarEntryFile) file).getJarEntry().getName().startsWith("jcef_build");
			}
		}); if(extracteds.length == 0) return null;
		JarUtils.addLibraryPath(new File(extractDir, "jcef_build/native/Release/").getAbsolutePath());
		for(File extracted : extracteds) extracted.deleteOnExit(); inited = true;
		return extracteds;
	}
	public static File[] initNatives(File extractDir) throws URISyntaxException, IOException, NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		return initNatives(JarUtils.getCurrentJar(), extractDir);
	}
	public static File[] initNatives() throws URISyntaxException, IOException, NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		return initNatives(LibraryUtils.defExtractDirFile);
	}
	
	public static boolean isInited() { return inited; }
}
