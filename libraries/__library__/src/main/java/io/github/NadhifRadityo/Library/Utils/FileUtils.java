package io.github.NadhifRadityo.Library.Utils;

import io.github.NadhifRadityo.Library.Utils.ProgressUtils.ProgressWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.debug;
import static io.github.NadhifRadityo.Library.Utils.ProgressUtils.progress;
import static io.github.NadhifRadityo.Library.Utils.ProgressUtils.progress_id;
import static io.github.NadhifRadityo.Library.Utils.StreamUtils.getBytes;
import static io.github.NadhifRadityo.Library.Utils.StreamUtils.getString;
import static io.github.NadhifRadityo.Library.Utils.StreamUtils.writeBytes;
import static io.github.NadhifRadityo.Library.Utils.StreamUtils.writeString;

public class FileUtils {

	public static byte[] getFileBytes(File file) throws IOException {
		try(ProgressWrapper prog0 = progress(progress_id(file))) {
			prog0.inherit();
			prog0.setCategory(FileUtils.class);
			prog0.setDescription("Reading file");
			prog0.pstart();

			prog0.pdo(String.format("Reading %s", file.getPath()));
			debug("Getting contents from file: %s", file.getPath());
			try(FileInputStream fis = new FileInputStream(file)) { return getBytes(fis); }
		}
	}
	public static byte[] getFileBytes(String path) throws IOException { return getFileBytes(new File(path)); }
	public static String getFileString(File file, Charset charset) throws IOException {
		try(ProgressWrapper prog0 = progress(progress_id(file, charset))) {
			prog0.inherit();
			prog0.setCategory(FileUtils.class);
			prog0.setDescription("Reading file");
			prog0.pstart();

			prog0.pdo(String.format("Reading %s", file.getPath()));
			debug("Getting contents from file: %s", file.getPath());
			try(FileInputStream fis = new FileInputStream(file)) { return getString(fis, charset); }
		}
	}
	public static String getFileString(String path, Charset charset) throws IOException { return getFileString(new File(path), charset); }
	public static String getFileString(File file) throws IOException { return getFileString(file, StandardCharsets.UTF_8); }
	public static String getFileString(String path) throws IOException { return getFileString(path, StandardCharsets.UTF_8); }

	public static void writeFileBytes(File file, byte[] bytes, int off, int len) throws IOException {
		try(ProgressWrapper prog0 = progress(progress_id(file, bytes, off, len))) {
			prog0.inherit();
			prog0.setCategory(FileUtils.class);
			prog0.setDescription("Writing file");
			prog0.pstart();

			prog0.pdo(String.format("Writing %s", file.getPath()));
			debug("Writing contents to file: %s", file.getPath());
			try(FileOutputStream fos = new FileOutputStream(file)) { writeBytes(bytes, off, len, fos); }
		}
	}
	public static void writeFileBytes(String path, byte[] bytes, int off, int len) throws IOException { writeFileBytes(new File(path), bytes, off, len); }
	public static void writeFileString(File file, String string, Charset charset) throws IOException {
		try(ProgressWrapper prog0 = progress(progress_id(file, string, charset))) {
			prog0.inherit();
			prog0.setCategory(FileUtils.class);
			prog0.setDescription("Writing file");
			prog0.pstart();

			prog0.pdo(String.format("Writing %s", file.getPath()));
			debug("Writing contents to file: %s", file.getPath());
			try(FileOutputStream fos = new FileOutputStream(file)) { writeString(string, fos, charset); }
		}
	}
	public static void writeFileString(String path, String string, Charset charset) throws IOException { writeFileString(new File(path), string, charset); }
	public static void writeFileString(File file, String string) throws IOException { writeFileString(file, string, StandardCharsets.UTF_8); }
	public static void writeFileString(String path, String string) throws IOException { writeFileString(path, string, StandardCharsets.UTF_8); }

	public static String getFileName(String fileName) { String name = ""; try { if(fileName != null && fileName.contains(".")) name = fileName.substring(0, fileName.lastIndexOf(".")); } catch (Exception ignored) { } return name; }
	public static String getFileName(File file) { return getFileName(file != null ? file.getName() : null); }
	public static String getFileExtension(String fileName) { String extension = ""; try { if(fileName != null && fileName.contains(".")) extension = fileName.substring(fileName.lastIndexOf(".") + 1); } catch (Exception ignored) { } return extension; }
	public static String getFileExtension(File file) { return getFileExtension(file != null ? file.getName() : null); }

	public static File file(File parent, String... children) {
		return new File(parent, String.join("/", children));
	}
	public static File file(String... children) {
		return new File(String.join("/", children));
	}
	public static File mkfile(File parent, String... children) {
		File result = file(parent, children);
		if(result.exists()) return result;
		try(ProgressWrapper prog0 = progress(progress_id(parent, children))) {
			prog0.inherit();
			prog0.setCategory(FileUtils.class);
			prog0.setDescription("Making file");
			prog0.pstart();

			prog0.pdo(String.format("mkfile %s", result.getPath()));
			debug("Making file: %s", result.getPath());
			mkdir(result.getParentFile());
			try { if(result.createNewFile()) return result;
			} catch(IOException e) { throw new Error(e); }
			throw new IllegalStateException("Cannot make file");
		}
	}
	public static File mkfile(String... children) {
		File result = file(children);
		if(result.exists()) return result;
		try(ProgressWrapper prog0 = progress(progress_id((Object) children))) {
			prog0.inherit();
			prog0.setCategory(FileUtils.class);
			prog0.setDescription("Making file");
			prog0.pstart();

			prog0.pdo(String.format("mkfile %s", result.getPath()));
			debug("Making file: %s", result.getPath());
			mkdir(result.getParentFile());
			try { if(result.createNewFile()) return result;
			} catch(IOException e) { throw new Error(e); }
			throw new IllegalStateException("Cannot make file");
		}
	}
	public static File mkdir(File parent, String... children) {
		File result = file(parent, children);
		if(result.exists()) return result;
		try(ProgressWrapper prog0 = progress(progress_id(parent, children))) {
			prog0.inherit();
			prog0.setCategory(FileUtils.class);
			prog0.setDescription("Making directory");
			prog0.pstart();

			prog0.pdo(String.format("mkdir %s", result.getPath()));
			debug("Making directory: %s", result.getPath());
			if(result.mkdirs()) return result;
			throw new IllegalStateException("Cannot make directory");
		}
	}
	public static File mkdir(String... children) {
		File result = file(children);
		if(result.exists()) return result;
		try(ProgressWrapper prog0 = progress(progress_id((Object) children))) {
			prog0.inherit();
			prog0.setCategory(FileUtils.class);
			prog0.setDescription("Making directory");
			prog0.pstart();

			prog0.pdo(String.format("mkdir %s", result.getPath()));
			debug("Making directory: %s", result.getPath());
			if(result.mkdirs()) return result;
			throw new IllegalStateException("Cannot make directory");
		}
	}
	public static void delfile0(File file) {
		if(!file.exists()) return;
		try(ProgressWrapper prog0 = progress(progress_id(file))) {
			prog0.inherit();
			prog0.setCategory(FileUtils.class);
			prog0.setDescription("Deleting file");
			prog0.pstart();

			prog0.pdo(String.format("del %s", file.getPath()));
			debug("Deleting file: %s", file.getPath());
			if(file.delete()) return;
			throw new IllegalStateException("Cannot delete file");
		}
	}
	public static void delfile(File file) {
		if(file.isFile()) { delfile0(file); return; }
		File[] children = file.listFiles();
		if(children == null) { delfile0(file); return; }
		try(ProgressWrapper prog0 = progress(progress_id(file))) {
			prog0.inherit();
			prog0.setCategory(FileUtils.class);
			prog0.setDescription("Deleting directory");
			prog0.setTotalProgress(children.length + 1);
			prog0.pstart();

			for(File child : children) {
				prog0.pdo(child.getName());
				if(child.isDirectory())
					delfile(child);
				else delfile0(child);
			}
			prog0.pdo(file.getName());
			delfile0(file);
		}
	}
}
