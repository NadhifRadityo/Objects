package io.github.NadhifRadityo.Objects.Utilizations.Library;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import io.github.NadhifRadityo.Objects.Utilizations.FileUtils;
import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;
import io.github.NadhifRadityo.Objects.Utilizations.JarUtils.JarEntryFile;

public class GameLibrary {
	private GameLibrary() { }
	
	private static boolean inited = false;
	public static File[] initNatives(File currentJar, File extractDir) throws URISyntaxException, IOException, NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(currentJar == null || extractDir == null || inited) return null;
		File[] extracteds = JarUtils.extractFileFromJar(currentJar, extractDir, new FileFilter() {
			@Override public boolean accept(File file) {
				if(!(file instanceof JarEntryFile)) return false;
				return ((JarEntryFile) file).getJarEntry().getName().startsWith("gamelib_natives") && 
						FileUtils.getFileExtension(((JarEntryFile) file).getJarEntry().getName()).equalsIgnoreCase("jar");
			}
		}); if(extracteds.length == 0) return null;
		JarUtils.addJarToClassPath(extracteds); inited = true;
		for(File extracted : extracteds) extracted.deleteOnExit();
		return extracteds;
	}
	public static File[] initNatives(File extractDir) throws URISyntaxException, IOException, NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return initNatives(JarUtils.getCurrentJar(), extractDir);
	}
	public static File[] initNatives() throws URISyntaxException, IOException, NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return initNatives(LibraryUtils.defExtractDirFile);
	}
	
	public static boolean isInited() { return inited; }
}