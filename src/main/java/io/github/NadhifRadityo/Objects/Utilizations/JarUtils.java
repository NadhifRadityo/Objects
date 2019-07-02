package io.github.NadhifRadityo.Objects.Utilizations;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.jogamp.common.util.JarUtil;

public class JarUtils extends JarUtil {

	public static File[] extractFileFromJar(File jarFile, File destDir, FileFilter filter) throws IOException {
		JarFile jar = new JarFile(jarFile);
		List<File> extracteds = new ArrayList<>();
		for(Enumeration<JarEntry> enumEntries = jar.entries(); enumEntries.hasMoreElements();) {
			JarEntry jarEntry = enumEntries.nextElement();
			File file = new JarEntryFile(destDir, jarEntry.getName(), jarEntry);
			if(!filter.accept(file)) continue; extracteds.add(file);
			if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
			if(jarEntry.isDirectory()) { file.mkdirs(); continue; }
			try(InputStream is = jar.getInputStream(jarEntry)) { Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING); }
		} jar.close(); return extracteds.toArray(new File[0]);
	} public static File[] extractFileFromJar(File jarFile, File destDir) throws IOException {
		return extractFileFromJar(jarFile, destDir, f -> { return true; });
	}
	
	public static void addLibraryPath(String... pathsToAdd) throws NoSuchFieldException, SecurityException, 
															IllegalArgumentException, IllegalAccessException {
		Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
		usrPathsField.setAccessible(true);
		String[] paths = (String[]) usrPathsField.get(null);
		List<String> deltas = new ArrayList<>(Arrays.asList(pathsToAdd));
		deltas.removeAll(Arrays.asList(paths));
		
		String[] newPaths = Arrays.copyOf(paths, paths.length + deltas.size());
		int i = paths.length; for(String delta : deltas) newPaths[i++] = delta;
		usrPathsField.set(null, newPaths);
		
		String libPathProps = "";
		for(String pathAdd : pathsToAdd) libPathProps += File.pathSeparator + pathAdd;
		System.setProperty("java.library.path", System.getProperty("java.library.path") + libPathProps);
	}
	
	public static void addJarToClassPath(File... jarFiles) throws NoSuchMethodException, SecurityException, 
														   IllegalAccessException, IllegalArgumentException, 
														   InvocationTargetException, MalformedURLException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		Method addUrlMethod = classLoader.getClass().getSuperclass().getDeclaredMethod("addURL", new Class[] { URL.class });
		addUrlMethod.setAccessible(true);
		for(File jarFile : jarFiles) addUrlMethod.invoke(classLoader, jarFile.toURI().toURL());
	}
	
	public static File getCurrentJar() throws URISyntaxException {
		File currentJar = new File(JarUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		if(!FileUtils.getFileExtension(currentJar).equalsIgnoreCase("jar")) return null; else return currentJar;
	}
	
	public static class JarEntryFile extends File {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2396556437986810388L;
		protected final JarEntry jarEntry;
		
		protected JarEntryFile(URI uri, JarEntry jarEntry) { super(uri); this.jarEntry = jarEntry; }
		protected JarEntryFile(String pathname, JarEntry jarEntry) { super(pathname); this.jarEntry = jarEntry; }
		protected JarEntryFile(File parent, String child, JarEntry jarEntry) { super(parent, child); this.jarEntry = jarEntry; }
		protected JarEntryFile(String parent, String child, JarEntry jarEntry) { super(parent, child); this.jarEntry = jarEntry; }
		
		public JarEntry getJarEntry() { return jarEntry; }
	}
}
