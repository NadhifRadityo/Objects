package io.github.NadhifRadityo.Objects.Utilizations.Library;

import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;
import io.github.NadhifRadityo.Objects.Utilizations.SystemUtils;

import java.io.File;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ImageMagickLibrary extends LibraryUtils {
	private ImageMagickLibrary() { }

	private static File IMAGEMAGICK_BINARY;
	public static File getImageMagickBinary() { if(IMAGEMAGICK_BINARY == null || !IMAGEMAGICK_BINARY.exists()) throw new IllegalStateException("ImageMagick is not available"); return IMAGEMAGICK_BINARY; }

	static {
		String id = "imagemagick/";
		if(SystemUtils.IS_OS_WINDOWS) id += "windows/";
		else if(SystemUtils.IS_OS_UNIX) id += "linux/";
		else if(SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX) id += "macos/imagemagick";
		else throw new LinkageError("Unknown platform: " + SystemUtils.OS_NAME);

		if(SystemUtils.IS_OS_UNIX) id += SystemUtils.OS_ARCH + "/imagemagick";
		if(SystemUtils.IS_OS_WINDOWS) id += (SystemUtils.IS_OS_32BIT ? "x86" : "x64") + "imagemagick.exe";
		identifier = id;
	}

	private static final String identifier;
	private static boolean inited = false;
	public static boolean extractLibrary(JarEntry je, JarFile jar, File file) {
		return !inited && je.getName().startsWith(identifier);
	}
	private static void extractSuccess(File currentJar, File extractDir, List<File> extracteds) throws NoSuchFieldException, IllegalAccessException {
		if(inited) return; if(extracteds.size() == 0) return;
		JarUtils.addLibraryPath(new File(extractDir, identifier).getParentFile().getAbsolutePath());
		IMAGEMAGICK_BINARY = new File(extractDir, identifier); inited = true;
	}

	public static boolean isInited() { return inited; }
}
