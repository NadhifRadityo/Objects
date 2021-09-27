package io.github.NadhifRadityo.Library.Utils;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.debug;
import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.error;
import static io.github.NadhifRadityo.Library.Utils.ProgressUtils.progress;
import static io.github.NadhifRadityo.Library.Utils.ProgressUtils.progress_id;
import static io.github.NadhifRadityo.Library.Utils.StreamUtils.getString;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;

public class ProcessUtils {

	@groovy.transform.ThreadInterrupt
	public static String getCommandOutput(File basedir, String... arguments) throws Exception {
		try(ProgressUtils.ProgressWrapper prog0 = progress(progress_id(basedir, arguments))) {
			prog0.inherit();
			prog0.setCategory(ProcessUtils.class);
			prog0.setDescription("Reading file");
			prog0.pstart();

			prog0.pdo(String.format("Executing command `%s`", String.join(" ", arguments)));
			debug("Executing command: %s", String.join(" ", arguments));
			ProcessBuilder processBuilder = new ProcessBuilder(arguments);
			if(basedir != null) processBuilder.directory(basedir);
			Process process = processBuilder.start();
			int returnCode = process.waitFor();
			String error = getString(process.getErrorStream(), StandardCharsets.UTF_8);
			if(!error.isEmpty()) error(error); if(returnCode != 0) return null;
			String result = getString(process.getInputStream(), StandardCharsets.UTF_8);
			debug("%s", result);
			return result;
		}
	}
	public static String getCommandOutput(String... arguments) throws Exception {
		return getCommandOutput(null, arguments);
	}

	public static File searchPath(String executable) throws Exception {
		if(IS_OS_WINDOWS) {
			String path = getCommandOutput("where", executable);
			if(path == null || path.isEmpty()) return null;
			return new File(path.trim().split("\r\n")[0]);
		}
		String path = getCommandOutput("which", executable);
		if(path == null || path.isEmpty()) return null;
		return new File(path.trim());
	}
}
