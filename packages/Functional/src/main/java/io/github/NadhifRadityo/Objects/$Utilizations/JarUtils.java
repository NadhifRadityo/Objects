package io.github.NadhifRadityo.Objects.$Utilizations;

import com.jogamp.common.util.JarUtil;
import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static io.github.NadhifRadityo.Objects.$Utilizations.FileUtils.VisitResult;

public class JarUtils extends JarUtil {
	public static void walk(File jarFile, JarEntryFilter filter, JarEntryVisitor visitor) throws IOException {
		if(!jarFile.exists() || !jarFile.isFile() || !FileUtils.getFileExtension(jarFile).equals("jar")) return; JarFile jar = new JarFile(jarFile);
		for(Enumeration<JarEntry> enumEntries = jar.entries(); enumEntries.hasMoreElements();) { JarEntry jarEntry = enumEntries.nextElement();
		if(!filter.filter(jarEntry, jar)) continue; switch(visitor.visit(jarEntry, jar)) { case BACK_DIR: case END: return; case CONTINUE: default: break;
			case ENTER_DIR: { /*if(Files.isDirectory(currentPath)) walk(currentPath, filter, visitor);*/ break; } } }
	}

	public static File[] extractFileFromJar(File jarFile, File destDir, JarEntryFilter filter) throws IOException {
		ArrayList<File> extracteds = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
			walk(jarFile, filter, (je, jar) -> { ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> {
				File file = new File(destDir, je.getName()); extracteds.add(file);
				if(!file.getParentFile().exists() && !file.getParentFile().mkdirs()) throw new IOException("Cannot create folder!");
				if(je.isDirectory() && !file.exists() && !file.mkdirs()) throw new IOException("Cannot create folder!"); if(je.isDirectory()) return;
				try(InputStream is = jar.getInputStream(je)) { Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING); }
			}); return VisitResult.ENTER_DIR; });
			return extracteds.toArray(new File[0]);
		} finally { Pool.returnObject(ArrayList.class, extracteds); }
	} public static File[] extractFileFromJar(File jarFile, File destDir) throws IOException { return extractFileFromJar(jarFile, destDir, (je, jar) -> true); }

	public static void addLibraryPath(String... pathsToAdd) throws NoSuchFieldException, SecurityException, 
															IllegalArgumentException, IllegalAccessException {
		if(SystemUtils.JAVA_DETECTION_VERSION > 8) { FutureJavaUtils.call((Object) pathsToAdd); return; }
		Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
		usrPathsField.setAccessible(true);
		String[] paths = (String[]) usrPathsField.get(null);
		ArrayList<String> deltas = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		deltas.addAll(Arrays.asList(pathsToAdd));
		deltas.removeAll(Arrays.asList(paths));

		try {
			String[] newPaths = Arrays.copyOf(paths, paths.length + deltas.size());
			int i = paths.length; for(String delta : deltas) newPaths[i++] = delta;
			usrPathsField.set(null, newPaths);

			StringBuilder libPathProps = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
			try { for(String pathAdd : pathsToAdd) libPathProps.append(File.pathSeparator).append(pathAdd);
				System.setProperty("java.library.path", System.getProperty("java.library.path") + libPathProps.toString());
			} finally { Pool.returnObject(StringBuilder.class, libPathProps); }
		} finally { Pool.returnObject(ArrayList.class, deltas); }
	}

	public static void addJarToClassPath(boolean java9newer, File... jarFiles) throws NoSuchMethodException, SecurityException,
																			   IllegalAccessException, IllegalArgumentException,
																			   InvocationTargetException, MalformedURLException {
		if(SystemUtils.JAVA_DETECTION_VERSION > 8 || java9newer) { FutureJavaUtils.call((Object) jarFiles); return; }
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		Method addUrlMethod = classLoader.getClass().getSuperclass().getDeclaredMethod("addURL", URL.class);
		addUrlMethod.setAccessible(true);
		for(File jarFile : jarFiles) addUrlMethod.invoke(classLoader, jarFile.toURI().toURL());
	}
	public static void addJarToClassPath(File... jarFiles) throws NoSuchMethodException, SecurityException,
														   IllegalAccessException, IllegalArgumentException,
														   InvocationTargetException, MalformedURLException {
		addJarToClassPath(false, jarFiles);
	}

	public static File getCurrentJar(CodeSource codeSource) throws URISyntaxException {
		File currentJar = new File(codeSource.getLocation().toURI());
		if(!FileUtils.getFileExtension(currentJar).equalsIgnoreCase("jar")) return null; else return currentJar;
	} public static File getCurrentJar() throws URISyntaxException { return getCurrentJar(JarUtils.class.getProtectionDomain().getCodeSource()); }

	public interface JarEntryFilter { boolean filter(JarEntry jarEntry, JarFile jarFile); }
	public interface JarEntryVisitor { VisitResult visit(JarEntry jarEntry, JarFile jarFile); }

	@SuppressWarnings("jol")
	protected static class DynamicURLClassLoader extends URLClassLoader {
		public DynamicURLClassLoader(URLClassLoader classLoader) { super(classLoader.getURLs()); }
		public DynamicURLClassLoader(URL... urls) { super(urls); }
		@Override public void addURL(URL url) { super.addURL(url); }
	}
}
