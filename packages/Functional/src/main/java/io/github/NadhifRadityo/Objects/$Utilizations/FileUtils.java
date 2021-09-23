package io.github.NadhifRadityo.Objects.$Utilizations;

import io.github.NadhifRadityo.Objects.$Object.Pool.BuiltInPool.ArrayListPool;
import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

public class FileUtils extends org.apache.commons.io.FileUtils {
	public static void walk(File directory, FileFilter filter, FileVisitor visitor) {
		if(!directory.exists() || !directory.isDirectory()) return;
		File[] filteredFiles = directory.listFiles(filter::filter);
		if(filteredFiles == null) return; for(File currentFile : filteredFiles)
		switch(visitor.visit(currentFile)) { case BACK_DIR: case END: return; case CONTINUE: default: break;
			case ENTER_DIR: { if(currentFile.isDirectory()) walk(currentFile, filter, visitor); break; } }
	} public static void walk(File directory, FileVisitor visitor) { walk(directory, f -> true, visitor); }
	public static void walk(Path directory, PathFilter filter, PathVisitor visitor) throws IOException {
		if(!Files.exists(directory) || !Files.isDirectory(directory)) return; try(Stream<Path> paths = Files.walk(directory)) {
		ArrayList<Path> filteredPaths = paths.filter(filter::filter).collect(ArrayListPool.getCollectors());
		if(filteredPaths == null) return; filteredPaths.remove(0); for(Path currentPath : filteredPaths)
		switch(visitor.visit(currentPath)) { case BACK_DIR: case END: return; case CONTINUE: default: break;
			case ENTER_DIR: { /*if(Files.isDirectory(currentPath)) walk(currentPath, filter, visitor);*/ break; } } }
	} public static void walk(Path directory, PathVisitor visitor) throws IOException { walk(directory, p -> true, visitor); }

	public static File[] getFileTree(File directory, FileFilter fileFilter) {
		if(!directory.exists() || !directory.isDirectory()) return null;
		ArrayList<File> results = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		try { walk(directory, fileFilter, f -> { results.add(f); return VisitResult.ENTER_DIR; });
		return results.toArray(new File[0]); } finally { Pool.returnObject(ArrayList.class, results); }
	} public static File[] getFileTree(File directory) { return getFileTree(directory, f -> true); }
	public static Path[] getFileTree(Path directory, PathFilter fileFilter) throws IOException {
		if(!Files.exists(directory) || !Files.isDirectory(directory)) return null;
		ArrayList<Path> results = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		try { walk(directory, fileFilter, p -> { results.add(p); return VisitResult.ENTER_DIR; });
		return results.toArray(new Path[0]); } finally { Pool.returnObject(ArrayList.class, results); }
	} public static Path[] getFileTree(Path directory) throws IOException { return getFileTree(directory, p -> true); }

	public static String getFileExtension(String fileName) {
		String extension = ""; try { if (fileName != null && fileName.contains("."))
		extension = fileName.substring(fileName.lastIndexOf(".") + 1); } catch (Exception ignored) { } return extension;
	} public static String getFileExtension(File file) { return getFileExtension(file != null ? file.getName() : null); }

	public static String getFileName(String fileName) {
		String name = ""; try { if (fileName != null && fileName.contains("."))
		name = fileName.substring(0, fileName.lastIndexOf(".")); } catch (Exception ignored) { } return name;
	} public static String getFileName(File file) { return getFileName(file != null ? file.getName() : null); }

	public static boolean isSupportPosixCompliant() {
		try { return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
		} catch (FileSystemNotFoundException | ProviderNotFoundException | SecurityException e) { return false; }
	}

	public static File createTempDir(String prefix, String suffix, File directory) throws IOException {
		File tempDir = File.createTempFile(prefix, suffix, directory);
		if(!tempDir.delete() || !tempDir.mkdirs()) throw new IOException("Cannot modify file!");
		tempDir.deleteOnExit(); return tempDir;
	} public static File createTempDir(String prefix, String suffix) throws IOException { return createTempDir(prefix, suffix, null); }
	public static File getTempDir() { File tempDir = new File(System.getProperty("java.io.tmpdir")); return tempDir.exists() ? tempDir : null; }

	public static FileSystem getFileSystem(URI uri) throws IOException {
		try { return FileSystems.getFileSystem(uri);
		} catch (FileSystemNotFoundException ignored) {
			return FileSystems.newFileSystem(uri, Collections.<String, String>emptyMap());
		}
	}

	public static byte[] getFileBytes(File file) throws IOException { try(FileInputStream fis = new FileInputStream(file)) { return StreamUtils.getBytes(fis); } }
	public static byte[] getFileBytes(String path) throws IOException { return getFileBytes(new File(path)); }
	public static String getFileString(File file, Charset charset) throws IOException { try(FileInputStream fis = new FileInputStream(file)) { return StreamUtils.toString(fis, charset); } }
	public static String getFileString(String path, Charset charset) throws IOException { return getFileString(new File(path), charset); }
	@Deprecated public static String getFileString(File file) throws IOException { return getFileString(file, StandardCharsets.UTF_8); }
	@Deprecated public static String getFileString(String path) throws IOException { return getFileString(path, StandardCharsets.UTF_8); }

	public enum VisitResult { ENTER_DIR, BACK_DIR, END, CONTINUE }
	public interface FileFilter { boolean filter(File file); }
	public interface FileVisitor { VisitResult visit(File file); }
	public interface PathFilter { boolean filter(Path path); }
	public interface PathVisitor { VisitResult visit(Path path); }
}
