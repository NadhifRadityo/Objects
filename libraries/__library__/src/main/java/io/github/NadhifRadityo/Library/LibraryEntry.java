package io.github.NadhifRadityo.Library;

import Gradle.Context;
import io.github.NadhifRadityo.Library.Utils.__common__;
import kotlin.jvm.functions.Function1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Manifest;

import static io.github.NadhifRadityo.Library.Utils.AttributesUtils.a_getString;
import static io.github.NadhifRadityo.Library.Utils.ClassUtils.classForName0;
import static io.github.NadhifRadityo.Library.Utils.ExceptionUtils.exception;
import static io.github.NadhifRadityo.Library.Utils.FileUtils.fileString;
import static io.github.NadhifRadityo.Library.Utils.FileUtils.mkdir;
import static io.github.NadhifRadityo.Library.Utils.FileUtils.mkfile;
import static io.github.NadhifRadityo.Library.Utils.JSONUtils.createJSONFile;
import static io.github.NadhifRadityo.Library.Utils.JSONUtils.toJson;
import static io.github.NadhifRadityo.Library.Utils.LoggerUtils._lwarn;
import static io.github.NadhifRadityo.Library.Utils.ProcessUtils.searchPath;

public class LibraryEntry {
	protected static final ClassLoader currentClassLoader = LibraryEntry.class.getClassLoader();
	protected static Context __CONTEXT__;
	protected static File __ROOT_DIRECTORY__;
	protected static File __TARGET_DIRECTORY__;
	protected static Map<Class<? extends LibraryModule>, LibraryModule> __MODULES__;
	protected static JSONROOT_main __MAIN_CONFIG__;

	public static Context getContext() { return __CONTEXT__; }
	public static File getRootDirectory() { return __ROOT_DIRECTORY__; }
	public static File getTargetDirectory() { return __TARGET_DIRECTORY__; }
	public static Map<Class<? extends LibraryModule>, LibraryModule> getModules() { return Collections.unmodifiableMap(__MODULES__); }
	public static JSONROOT_main getMainConfig() { return __MAIN_CONFIG__; }

	private static void entry(Context context, File rootDirectory, Map<String, File> rawModules) throws Exception {
		try {
			__CONTEXT__ = context;
			__common__.construct();
			__ROOT_DIRECTORY__ = rootDirectory;
			__TARGET_DIRECTORY__ = mkdir(__ROOT_DIRECTORY__, "__target__");
			__MODULES__ = new HashMap<>();

			File mainConfigFile = mkfile(__TARGET_DIRECTORY__, "configurations.json");
			JSONROOT_main configurations = toJson(fileString(mainConfigFile), JSONROOT_main.class);
			if(configurations == null) {
				configurations = new JSONROOT_main();
				configurations.properties = new Properties();
			}
			__MAIN_CONFIG__ = configurations;

			for(Map.Entry<String, File> rawModule : rawModules.entrySet()) {
				String moduleIdentifier = rawModule.getKey();
				File modulePath = rawModule.getValue();
				File manifestFile = new File(modulePath, "lib.mf");
				if(!manifestFile.exists()) continue;

				Manifest moduleManifest;
				Class<? extends LibraryModule> moduleEntry;
				try(FileInputStream manifestFileStream = new FileInputStream(manifestFile)) {
					moduleManifest = new Manifest(manifestFileStream);
					moduleEntry = classForName0(a_getString(moduleManifest.getMainAttributes(), "Module-Entry"), true, currentClassLoader);
				} catch(Exception e) {
					_lwarn("Error reading manifest file \"%s\"\n%s", manifestFile.getPath(), exception(e));
					continue;
				}

				LibraryModule module = initModule(moduleEntry, moduleIdentifier, modulePath, moduleManifest);
				__MODULES__.put(moduleEntry, module);
			}

			defaultPropertiesConfig(configurations);
			for(LibraryModule module : __MODULES__.values())
				module.run();
			createJSONFile(configurations, mainConfigFile);
		} finally {
			__MAIN_CONFIG__ = null;
			__MODULES__ = null;
			__TARGET_DIRECTORY__ = null;
			__ROOT_DIRECTORY__ = null;
			__common__.destruct();
			__CONTEXT__ = null;
		}
	}

	protected static final Method METHOD_LibraryModule_init;
	static { try {
		METHOD_LibraryModule_init = LibraryModule.class.getDeclaredMethod("init", String.class, File.class, Manifest.class);
		METHOD_LibraryModule_init.setAccessible(true);
	} catch(Exception e) { throw new Error(e); } }
	protected static LibraryModule initModule(Class<? extends LibraryModule> entry, String identifier, File path, Manifest manifest) throws Exception {
		LibraryModule module = entry.newInstance();
		METHOD_LibraryModule_init.invoke(module, identifier, path, manifest);
		return module;
	}

	protected static void defaultPropertiesConfig(JSONROOT_main configurations) throws Exception {
		Function1<Object[], Void> putExecutable = (args) -> {
			String executable = args.length >= 1 ? (String) args[0] : null;
			String[] alternatives = args.length >= 2 ? (String[]) args[1] : null;
			String key = executable + "Path";
			if(configurations.properties.containsKey(key)) return null;
			File path = searchPath(executable);
			if(path == null && alternatives != null)
				for(String alternative : alternatives) {
					path = searchPath(alternative);
					if(path != null) break;
				}
			if(path == null) return null;
			try { configurations.properties.setProperty(key, path.getCanonicalPath()); }
			catch (IOException e) { throw new Error(e); }
			return null;
		};
		putExecutable.invoke(new Object[] { "java", new String[] { "java8", "java9", "java11", "java13", "java17", "gvm19java8", "gvm20java8", "gvm20java11", "rlyjava15", "rlyjava16", "pnmjava14", "pnmjava16", "zuljava15" } });
		putExecutable.invoke(new Object[] { "javac", new String[] { "javac8", "javac9", "javac11", "javac13", "javac17", "gvm19javac8", "gvm20javac8", "gvm20javac11", "rlyjavac15", "rlyjavac16", "pnmjavac14", "pnmjavac16", "zuljavac15" } });
		putExecutable.invoke(new Object[] { "jar", new String[] { "jar8", "jar9", "jar11", "jar13", "jar17", "gvm19jar8", "gvm20jar8", "gvm20jar11", "rlyjar15", "rlyjar16", "pnmjar14", "pnmjar16", "zuljar15" } });
		putExecutable.invoke(new Object[] { "certutil" });
		putExecutable.invoke(new Object[] { "openssl" });
		putExecutable.invoke(new Object[] { "md5sum" });
		putExecutable.invoke(new Object[] { "sha1sum" });
		putExecutable.invoke(new Object[] { "sha224sum" });
		putExecutable.invoke(new Object[] { "sha256sum" });
		putExecutable.invoke(new Object[] { "sha384sum" });
		putExecutable.invoke(new Object[] { "sha512sum" });
	}
}
