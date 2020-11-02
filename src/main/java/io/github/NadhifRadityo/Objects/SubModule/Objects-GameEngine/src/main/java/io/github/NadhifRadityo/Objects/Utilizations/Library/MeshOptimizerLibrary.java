package io.github.NadhifRadityo.Objects.Utilizations.Library;

import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;
import io.github.NadhifRadityo.Objects.Utilizations.SystemUtils;

import java.io.File;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MeshOptimizerLibrary extends LibraryUtils {
	private MeshOptimizerLibrary() { }

	private static File MESHOPTIMIZER_BINARY;
	public static File getMeshOptimizerBinary() { if(MESHOPTIMIZER_BINARY == null || !MESHOPTIMIZER_BINARY.exists()) throw new IllegalStateException("MeshOptimizer is not available"); return MESHOPTIMIZER_BINARY; }
	public static String getMESHOPTIMIZERCommand(String args) {
		return "\"" + getMeshOptimizerBinary().getAbsolutePath() + "\" " + args;
	}

	static {
		String id = "meshoptimizer/";
		if(SystemUtils.IS_OS_WINDOWS) id += "windows/gltfpack.exe";
		else if(SystemUtils.IS_OS_UNIX) id += "linux/gltfpack";
		else if(SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX) id += "macos/gltfpack";
		else throw new LinkageError("Unknown platform: " + SystemUtils.OS_NAME);
		identifier = id;
	}

	private static final String identifier;
	private static boolean inited = false;
	public static boolean extractLibrary(JarEntry je, JarFile jar, File file) {
		return !inited && je.getName().startsWith(identifier);
	}
	private static void extractSuccess(File currentJar, File extractDir, List<File> extracteds) throws NoSuchFieldException, IllegalAccessException {
		if(inited) return; if(extracteds.size() == 0 || !SystemUtils.IS_OS_WINDOWS) return;
		JarUtils.addLibraryPath(new File(extractDir, identifier).getParentFile().getAbsolutePath());
		MESHOPTIMIZER_BINARY = new File(extractDir, identifier); inited = true;
	}

	public static boolean isInited() { return inited; }
}
