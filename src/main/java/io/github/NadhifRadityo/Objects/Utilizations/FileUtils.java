package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.ProviderNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class FileUtils extends org.apache.commons.io.FileUtils {
	public static File[] getFileTrees(File parentDir, FileFilter fileFilter) {
		if(!parentDir.exists() || parentDir.isFile()) return null;
		ArrayList<File> results = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		try { for(File file : parentDir.listFiles()) {
			if(file.isDirectory()) results.addAll(Arrays.asList(getFileTrees(file, fileFilter)));
			else if(fileFilter.accept(file)) results.add(file);
		} return results.toArray(new File[0]); } finally { Pool.returnObject(ArrayList.class, results); }
	} public static File[] getFileTrees(File parentDir) { return getFileTrees(parentDir, pathname -> true); }
	
	public static String getFileExtension(String fileName) {
		String extension = "";
		try { if (fileName != null && fileName.contains("."))
			extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		} catch (Exception ignored) { } return extension;
	} public static String getFileExtension(File file) { return getFileExtension(file != null ? file.getName() : null); }
	
	public static String getFileName(String fileName) {
		String name = "";
		try { if (fileName != null && fileName.contains("."))
			name = fileName.substring(0, fileName.lastIndexOf("."));
		} catch (Exception ignored) { } return name;
	} public static String getFileName(File file) { return getFileName(file != null ? file.getName() : null); }
	
	public static boolean isSupportPosixCompliant() {
		try { return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
		} catch (FileSystemNotFoundException | ProviderNotFoundException | SecurityException e) { return false; }
	}
	
	public static File createTempDir(String prefix, String suffix, File directory) throws IOException {
		File tempDir = File.createTempFile(prefix, suffix, directory);
		tempDir.delete(); tempDir.mkdirs(); tempDir.deleteOnExit(); return tempDir;
	} public static File createTempDir(String prefix, String suffix) throws IOException { return createTempDir(prefix, suffix, null); }

	public static byte[] getFileBytes(File file) throws IOException {
		byte[] buf = new byte[8192]; int length;
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((length = bis.read(buf)) != -1)
			baos.write(buf, 0, length);
		fis.close(); bis.close(); baos.close();
		return baos.toByteArray();
	} public static byte[] getFileBytes(String path) throws IOException { return getFileBytes(new File(path)); }
}
