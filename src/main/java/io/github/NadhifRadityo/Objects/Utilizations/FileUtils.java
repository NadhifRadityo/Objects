package io.github.NadhifRadityo.Objects.Utilizations;

import java.io.File;

public class FileUtils {
	private FileUtils() {
		
	}
	
	public static String getFileExtension(String fileName) {
		String extension = "";
		try {
			if (fileName != null && fileName.contains("."))
				extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		} catch (Exception ignored) { }
		return extension;
	}
	public static String getFileExtension(File file) {
		return getFileExtension(file != null && file.exists() ? file.getName() : null);
	}
	
	public static String getFileName(String fileName) {
		String name = "";
		try {
			if (fileName != null && fileName.contains("."))
				name = fileName.substring(0, fileName.lastIndexOf("."));
		} catch (Exception ignored) { }
		return name;
	}
	public static String getFileName(File file) {
		return getFileName(file != null && file.exists() ? file.getName() : null);
	}
}
