package io.github.NadhifRadityo.Objects.Utilizations.Library;

import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.FileUtils;
import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;
import io.github.NadhifRadityo.Objects.Utilizations.JarUtils.JarEntryFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibraryUtils {
	private LibraryUtils() { }
	
	private static final String defExtractDirName = "libs_obj";
	public static final File defExtractDirFile;
	
	static { 
		File tempDir = null; try { tempDir = FileUtils.createTempDir(defExtractDirName, Long.toString(System.nanoTime()));
		} catch(Exception e) { e.printStackTrace(); System.exit(0); } defExtractDirFile = tempDir;
		
		for(File file : defExtractDirFile.getParentFile().listFiles()) {
			ExceptionUtils.doSilentException(false, (ThrowsRunnable) () -> {
				if(file.isDirectory() && file.getName().startsWith(defExtractDirName)) FileUtils.deleteDirectory(file);
			});
		} Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override public void run() { if(!defExtractDirFile.exists()) return;
				ExceptionUtils.doSilentException(false, (ThrowsRunnable) () -> FileUtils.deleteDirectory(defExtractDirFile));
			}
		});
	}
	
	public static File[] initLibraries(File currentJar, File extractDir) throws IOException {
		if(currentJar == null || extractDir == null) return null;
		List<File> extracteds = new ArrayList<>();
		JarUtils.extractFileFromJar(currentJar, extractDir, file -> {
			if(!(file instanceof JarEntryFile)) return false; try {
				if(((JarEntryFile) file).getJarEntry().getName().startsWith("gamelib_natives") &&
						!GameLibrary.isInited()) extracteds.addAll(Arrays.asList(GameLibrary.initNatives(currentJar, extractDir)));
				if(((JarEntryFile) file).getJarEntry().getName().startsWith("jcef_build") &&
						!WebLibrary.isInited()) extracteds.addAll(Arrays.asList(WebLibrary.initNatives(currentJar, extractDir)));
			} catch(Exception e) { throw new Error(e); } return false;
		}); return extracteds.toArray(new File[0]);
	} 
	public static File[] initLibraries(File extractDir) throws IOException, URISyntaxException { return initLibraries(JarUtils.getCurrentJar(), extractDir); }
	public static File[] initLibraries() throws IOException, URISyntaxException { return initLibraries(defExtractDirFile); }
}
