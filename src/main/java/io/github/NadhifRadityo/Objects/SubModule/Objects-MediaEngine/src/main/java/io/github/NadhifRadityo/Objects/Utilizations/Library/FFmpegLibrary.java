package io.github.NadhifRadityo.Objects.Utilizations.Library;

import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;
import io.github.NadhifRadityo.Objects.Utilizations.SystemUtils;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FFmpegLibrary extends LibraryUtils {
	private FFmpegLibrary() { }

	private static File FFMPEG_BINARY;
	private static File FFPROBE_BINARY;
	private static FFmpeg FFMPEG_INSTANCE;
	private static FFprobe FFPROBE_INSTANCE;
	public static File getFFmpegBinary() { if(FFMPEG_BINARY == null || !FFMPEG_BINARY.exists()) throw new IllegalStateException("FFMPEG is not available"); return FFMPEG_BINARY; }
	public static File getFFprobeBinary() { if(FFPROBE_BINARY == null || !FFPROBE_BINARY.exists()) throw new IllegalStateException("FFPROBE is not available"); return FFPROBE_BINARY; }
	public static FFmpeg getFFmpegInstance() throws IOException { if(FFMPEG_INSTANCE == null) FFMPEG_INSTANCE = new FFmpeg(getFFmpegBinary().getAbsolutePath()); return FFMPEG_INSTANCE; }
	public static FFprobe getFFprobeInstance() throws IOException { if(FFPROBE_INSTANCE == null) FFPROBE_INSTANCE = new FFprobe(getFFprobeBinary().getAbsolutePath()); return FFPROBE_INSTANCE; }

	static {
		String id = "ffmpeg/";
		if(SystemUtils.IS_OS_WINDOWS) id += "windows/";
		else if(SystemUtils.IS_OS_UNIX) id += "linux/";
		else if(SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX) id += "macosx/";
		// else if(SystemUtils.IS_ANDROID) id += "android/"
		else throw new LinkageError("Unknown platform: " + SystemUtils.OS_NAME);

		if(SystemUtils.IS_OS_WINDOWS) {
			id += SystemUtils.IS_OS_32BIT ? "windows-x86/" : "windows-x86_64/";
		}
		if(SystemUtils.IS_OS_UNIX) id += SystemUtils.OS_ARCH + "/";
		if(SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX) {
			id += "macosx-x86_64/";
		}
		// else if(SystemUtils.IS_ANDROID) id += "..."
		identifier = id;
	}

	private static final String identifier;
	private static boolean inited = false;
	public static boolean extractLibrary(JarEntry je, JarFile jar, File file) {
		return !inited && je.getName().startsWith(identifier);
	}
	private static void extractSuccess(File currentJar, File extractDir, List<File> extracteds) throws NoSuchFieldException, IllegalAccessException {
		if(inited) return; if(extracteds.size() == 0) return;
		JarUtils.addLibraryPath(new File(extractDir, identifier).getAbsolutePath());
		extractDir = new File(extractDir, identifier);
		String fileType = SystemUtils.IS_OS_WINDOWS ? ".exe" : "";
		FFMPEG_BINARY = new File(extractDir, "ffmpeg" + fileType);
		FFPROBE_BINARY = new File(extractDir, "ffprobe" + fileType);
		inited = true;
	}

	public static boolean isInited() { return inited; }
}
