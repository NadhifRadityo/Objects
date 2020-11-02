package io.github.NadhifRadityo.Objects.Utilizations.Library;

import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;
import jogamp.common.os.PlatformPropsImpl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JogampLibrary extends LibraryUtils {
	private JogampLibrary() { }

	private static final String identifier = PlatformPropsImpl.os_and_arch + ".jar";
	private static boolean inited = false;
	public static boolean extractLibrary(JarEntry je, JarFile jar, File file) {
		return !inited && je.getName().startsWith("gamelib_natives") && je.getName().endsWith(identifier);
	}
	private static void extractSuccess(File currentJar, File extractDir, List<File> extracteds) throws IOException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		if(inited) return; if(extracteds.size() == 0) return;
		extractDir = new File(extractDir, "gamelib_natives"); for(File extracted : extracteds)
			JarUtils.extractFileFromJar(extracted, extractDir, (je, jar) -> je.getName().startsWith("natives"));
		extractDir = new File(extractDir, "natives" + File.separator + PlatformPropsImpl.os_and_arch);
		JarUtils.addLibraryPath(extractDir.getAbsolutePath()); File[] listFiles = extractDir.listFiles();
		if(listFiles != null) for(File extracted : listFiles)
			if(extracted.isDirectory()) JarUtils.addLibraryPath(extracted.getAbsolutePath());
		JarUtils.addJarToClassPath(extracteds.toArray(new File[0])); inited = true;
	}

	public static boolean isInited() { return inited; }
}
