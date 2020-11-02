package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;

public class FJarUtils extends JarUtils {

	static {
		Java9Utils.disableJava9SillyWarning();
	}

	protected static boolean systemClassLoaderReplaced = false;
	protected static void replaceSystemClassLoader() {
		if(systemClassLoaderReplaced) return; systemClassLoaderReplaced = true;
		Field FIELD_scl = Arrays.stream(ClassLoader.class.getDeclaredFields()).filter(f -> f.getType() == ClassLoader.class && !f.getName().equals("parent")).findFirst().orElse(null);
		if(FIELD_scl == null) return; FIELD_scl.setAccessible(true);
		ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> FIELD_scl.set(null, new URLClassLoader(new URL[0])));
		for(Thread thread : Thread.getAllStackTraces().keySet())
			ExceptionUtils.doSilentThrowsRunnable(false, () -> thread.setContextClassLoader(new URLClassLoader(new URL[0], ClassLoader.getSystemClassLoader())));
	}

	public static void FaddLibraryPath(String... pathsToAdd) throws NoSuchFieldException, SecurityException,
															 IllegalArgumentException, IllegalAccessException {
		replaceSystemClassLoader();
		MethodHandles.Lookup METHODHANDLES_ClassLoader = MethodHandles.privateLookupIn(ClassLoader.class, MethodHandles.lookup());
		VarHandle sys_paths = METHODHANDLES_ClassLoader.findStaticVarHandle(ClassLoader.class, "sys_paths", String[].class);
		String[] paths = (String[]) sys_paths.get();
		ArrayList<String> deltas = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		deltas.addAll(Arrays.asList(pathsToAdd));
		deltas.removeAll(Arrays.asList(paths));

		try {
			String[] newPaths = Arrays.copyOf(paths, paths.length + deltas.size());
			int i = paths.length; for(String delta : deltas) newPaths[i++] = delta;
			sys_paths.set((Object) newPaths);

			StringBuilder libPathProps = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
			try { for(String pathAdd : pathsToAdd) libPathProps.append(File.pathSeparator).append(pathAdd);
				System.setProperty("java.library.path", System.getProperty("java.library.path") + libPathProps.toString());
			} finally { Pool.returnObject(StringBuilder.class, libPathProps); }
		} finally { Pool.returnObject(ArrayList.class, deltas); }
	}
	protected static boolean alreadyDynamicClassPathWarn;
	public static void FaddJarToClassPath(File... jarFiles) throws SecurityException, IllegalArgumentException, MalformedURLException {
		replaceSystemClassLoader();
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		if(!(alreadyDynamicClassPathWarn)) {
			alreadyDynamicClassPathWarn = true;
			System.err.println("WARN! This might not working properly.");
			System.err.println("Since adding classpath at runtime only works on java 8 and older.");
		}
		JarUtils.DynamicURLClassLoader customLoader = classLoader instanceof URLClassLoader ?
				new JarUtils.DynamicURLClassLoader((URLClassLoader) classLoader) : new JarUtils.DynamicURLClassLoader();
		for(File jarFile : jarFiles) customLoader.addURL(jarFile.toURI().toURL());
	}
}
