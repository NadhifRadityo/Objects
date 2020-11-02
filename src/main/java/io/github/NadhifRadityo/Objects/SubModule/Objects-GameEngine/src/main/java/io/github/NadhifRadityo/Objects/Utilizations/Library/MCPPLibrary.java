package io.github.NadhifRadityo.Objects.Utilizations.Library;

import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.FileUtils;
import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;
import io.github.NadhifRadityo.Objects.Utilizations.StringUtils;
import io.github.NadhifRadityo.Objects.Utilizations.SystemUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MCPPLibrary extends LibraryUtils {
	private MCPPLibrary() { }

	private static File MCPP_BINARY;
	private static final Boolean WINE_AVAILABLE;
	public static File getMCPPBinary() { if(MCPP_BINARY == null || !MCPP_BINARY.exists()) throw new IllegalStateException("MCPP is not available"); return MCPP_BINARY; }
	public static String getMCPPCommand(String args, String inFile, String outFile) {
		if(SystemUtils.IS_OS_UNIX && WINE_AVAILABLE) return "wine " + getMCPPBinary().getAbsolutePath() + " " + args + " " + inFile + " " + outFile;
		return "\"" + getMCPPBinary().getAbsolutePath() + "\" " + args + " \"" + inFile + "\" \"" + outFile + "\"";
	}

	static {
		WINE_AVAILABLE = SystemUtils.IS_OS_UNIX ? ExceptionUtils.doSilentThrowsReferencedCallback((_args) -> false, (_args) -> FileUtils.getFileString(new File("/var/log/dpkg.log"),
				Charset.defaultCharset()).toLowerCase().contains("installed wine")) : false;

		String id = "mcpp/";
		if(WINE_AVAILABLE || SystemUtils.IS_OS_WINDOWS) id += "windows/bin/mcpp.exe";
		else if(SystemUtils.IS_OS_UNIX) id += "linux/mcpp_2.7.2_i386";
		else if(SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX) id += "macos/Archive.pax";
		else throw new LinkageError("Unknown platform: " + SystemUtils.OS_NAME);
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
		MCPP_BINARY = new File(extractDir, identifier); inited = true;
	}

	public static boolean isInited() { return inited; }
}
