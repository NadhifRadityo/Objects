package io.github.NadhifRadityo.Objects.Utilizations.Library;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LibraryUtils {
	public LibraryUtils() { }

	private static final Map<File, Map<Class<?>, List<File>>> initedLib = new HashMap<>();
	private static final String defExtractDirName = "libs_obj";
	public static final File defExtractDirFile;

	static {
		File tempDir = FileUtils.getTempDir();
		if(tempDir == null) throw new IllegalStateException("Null temporary dir");
		File extractDir = null;
		for(File file : Objects.requireNonNull(tempDir.listFiles())) {
			if(!file.isDirectory() || !file.getName().startsWith(defExtractDirName)) continue;
			extractDir = file; break;
		} if(extractDir == null)
			extractDir = new File(FileUtils.getTempDir(), "libs_obj" + System.nanoTime());
		if(!extractDir.exists() && !extractDir.mkdirs()) throw new IllegalStateException("Cannot create folder");
		defExtractDirFile = extractDir; readConfigs();
	}

	protected static void readConfigs() { ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> {
		File configFile = new File(defExtractDirFile, "configs.json"); if(!configFile.exists()) return;
		JSONObject configs = new JSONObject(FileUtils.getFileString(configFile, StandardCharsets.UTF_8));
		for(String jar : configs.keySet()) {
			File currentJar = new File(jar);
			Map<Class<?>, List<File>> currentMap = new HashMap<>();
			JSONObject classes = configs.getJSONObject(jar);
			for(String clazz : classes.keySet()) {
				Class<?> currentClass;
				try { currentClass = Class.forName(clazz);
				} catch(ClassNotFoundException e) { continue; }
				List<File> currentList = new ArrayList<>();
				JSONArray extracteds = classes.getJSONArray(clazz);
				for(int i = 0; i < extracteds.length(); i++)
					currentList.add(new File(extracteds.getString(i)));
				currentMap.put(currentClass, currentList);
			} initedLib.put(currentJar, currentMap);
		}
		for(File currentJar : initedLib.keySet()) {
			Map<Class<?>, List<File>> currentMap = initedLib.get(currentJar);
			for(Class<?> clazz : currentMap.keySet()) {
				List<File> extracteds = currentMap.get(clazz);
				Method onDone = clazz.getDeclaredMethod("extractSuccess", File.class, File.class, List.class);
				if(!Modifier.isStatic(onDone.getModifiers()) || !Modifier.isPrivate(onDone.getModifiers())) continue;
				onDone.setAccessible(true); onDone.invoke(null, currentJar, defExtractDirFile, extracteds);
			}
		}
	}); }
	protected static void writeConfigs() { ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> {
		File configFile = new File(defExtractDirFile, "configs.json");
		if(!configFile.exists() && !configFile.createNewFile()) return;
		JSONObject configs = new JSONObject();
		for(File currentJar : initedLib.keySet()) {
			Map<Class<?>, List<File>> currentMap = initedLib.get(currentJar);
			JSONObject classes = new JSONObject();
			for(Class<?> clazz : currentMap.keySet()) {
				List<File> currentList = currentMap.get(clazz);
				JSONArray extracteds = new JSONArray();
				for(File extracted : currentList)
					extracteds.put(extracted.getAbsolutePath());
				classes.put(clazz.getCanonicalName(), extracteds);
			} configs.put(currentJar.getAbsolutePath(), classes);
		} FileUtils.write(configFile, configs.toString(2), StandardCharsets.UTF_8);
	}); }

	public static File[] initLibraries(File _currentJar, File _extractDir, Class<?>... _classes) throws IOException, URISyntaxException {
		if(_currentJar == null) _currentJar = JarUtils.getCurrentJar();
		if(_extractDir == null) _extractDir = defExtractDirFile;
		if(_classes == null) _classes = ClassUtils.getClasses(LibraryUtils.class.getPackage(), LibraryUtils.class);
		File currentJar = _currentJar; File extractDir = _extractDir; Class<?>[] classes = _classes;
		initedLib.computeIfAbsent(currentJar, k -> new HashMap<>());
		Map<Class<?>, List<File>> currentMap = initedLib.get(currentJar);
		ArrayList<Method> methods = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
			for(Class<?> clazz : classes) { ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException(true, NoSuchMethodException.class), () -> { try {
				Method isInitedMethod = clazz.getDeclaredMethod("isInited");
				if(!Modifier.isStatic(isInitedMethod.getModifiers())) return;
				isInitedMethod.setAccessible(true); Object result = isInitedMethod.invoke(null);
				if(result instanceof Boolean && ((boolean) result)) return; } catch (Exception ignored) { }
				currentMap.computeIfAbsent(clazz, k -> new ArrayList<>());
				Method method = clazz.getDeclaredMethod("extractLibrary", JarEntry.class, JarFile.class, File.class);
				if(!Modifier.isStatic(method.getModifiers())) return;
				method.setAccessible(true); methods.add(method);
			}); }

			return JarUtils.extractFileFromJar(currentJar, extractDir, (je, jar) -> {
				boolean result = false; for(Method method : methods) try {
					List<File> currentList = currentMap.get(method.getDeclaringClass());
					File targetExtract = new File(extractDir, je.getName());
					Object _return = method.invoke(null, je, jar, targetExtract);
					if(!(_return instanceof Boolean)) continue;
					boolean _returnBoolean = (boolean) _return;
					if(_returnBoolean) currentList.add(targetExtract);
					if(!result && _returnBoolean) result = true;
				} catch(Exception e) { e.printStackTrace(); currentMap.remove(method.getDeclaringClass()); }
				return result;
			});
		} finally {
			for(Method method : methods) try {
				Class<?> clazz = method.getDeclaringClass(); List<File> currentList = currentMap.get(clazz);
				Method onDone = clazz.getDeclaredMethod("extractSuccess", File.class, File.class, List.class);
				if(!Modifier.isStatic(onDone.getModifiers()) || !Modifier.isPrivate(onDone.getModifiers())) continue;
				onDone.setAccessible(true); onDone.invoke(null, currentJar, extractDir, currentList); try {
				Method isInitedMethod = clazz.getDeclaredMethod("isInited");
				if(!Modifier.isStatic(isInitedMethod.getModifiers())) continue;
				isInitedMethod.setAccessible(true); Object result = isInitedMethod.invoke(null);
				if(result instanceof Boolean && !((boolean) result)) currentMap.remove(clazz); } catch (Exception ignored) { }
			} catch(Exception e) { e.printStackTrace(); } Pool.returnObject(ArrayList.class, methods);
			if(extractDir == defExtractDirFile) writeConfigs();
		}
	}
	public static File[] initLibraries(File currentJar, Class<?>... classes) throws IOException, URISyntaxException { return initLibraries(currentJar, null, classes); }
	public static File[] initLibraries(Class<?>... classes) throws IOException, URISyntaxException { return initLibraries(null, classes); }
	public static File[] initLibraries(File currentJar, File extractDir) throws IOException, URISyntaxException { return initLibraries(currentJar, extractDir, (Class<?>[]) null); }
	public static File[] initLibraries(File currentJar) throws IOException, URISyntaxException { return initLibraries(currentJar, defExtractDirFile); }
	public static File[] initLibraries() throws IOException, URISyntaxException { return initLibraries(JarUtils.getCurrentJar()); }
}
