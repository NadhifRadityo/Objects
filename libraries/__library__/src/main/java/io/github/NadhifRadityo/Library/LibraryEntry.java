package io.github.NadhifRadityo.Library;

import org.gradle.api.internal.project.DefaultProject;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static io.github.NadhifRadityo.Library.Utils.createJSONFile;
import static io.github.NadhifRadityo.Library.Utils.file;
import static io.github.NadhifRadityo.Library.Utils.getFileString;
import static io.github.NadhifRadityo.Library.Utils.log;
import static io.github.NadhifRadityo.Library.Utils.mkdir;
import static io.github.NadhifRadityo.Library.Utils.mkfile;
import static io.github.NadhifRadityo.Library.Utils.searchPath;
import static io.github.NadhifRadityo.Library.Utils.setFinal;
import static io.github.NadhifRadityo.Library.Utils.toJson;

public class LibraryEntry {
	protected static DefaultProject __PROJECT__;
	protected static File __ROOT_DIRECTORY__;
	protected static File __TARGET_DIRECTORY__;
	protected static Map<Class<? extends LibraryModule>, LibraryModule> __MODULES__;
	protected static JSON_mainRoot __MAIN_CONFIG__;

	public static DefaultProject getProject() { return __PROJECT__; }
	public static File getRootDirectory() { return __ROOT_DIRECTORY__; }
	public static File getTargetDirectory() { return __TARGET_DIRECTORY__; }
	public static Map<Class<? extends LibraryModule>, LibraryModule> getModules() { return Collections.unmodifiableMap(__MODULES__); }
	public static JSON_mainRoot getMainConfig() { return __MAIN_CONFIG__; }

	private static void entry(DefaultProject project, File rootDirectory, Map<String, File> rawModules) throws Exception {
		__PROJECT__ = project;
		__ROOT_DIRECTORY__ = rootDirectory;
		__TARGET_DIRECTORY__ = mkdir(__ROOT_DIRECTORY__, "__target__");
		__MODULES__ = new HashMap<>();

		File mainConfigFile = file(__TARGET_DIRECTORY__, "configurations.json");
		mkfile(mainConfigFile);
		JSON_mainRoot configurations = toJson(getFileString(mainConfigFile), JSON_mainRoot.class);
		if(configurations == null) {
			configurations = new JSON_mainRoot();
			configurations.properties = new Properties();
		}
		__MAIN_CONFIG__ = configurations;

		for(Map.Entry<String, File> rawModule : rawModules.entrySet()) {
			String moduleName = rawModule.getKey();
			File modulePath = rawModule.getValue();
			File manifestFile = new File(modulePath, "lib.mf");
			log("Checking \"%s\" library manager... %s", moduleName, manifestFile.exists() ? "yes" : "no");
			if(!manifestFile.exists()) continue;

			Manifest manifest;
			try(FileInputStream manifestFileStream = new FileInputStream(manifestFile)) {
				manifest = new Manifest(manifestFileStream); }
			Attributes mainAttributes = manifest.getMainAttributes();
			Class<? extends LibraryModule> moduleClass = (Class<? extends LibraryModule>)
					Class.forName(mainAttributes.getValue("Main-Class"));
			LibraryModule module = initModule(moduleName, modulePath, moduleClass);
			__MODULES__.put(moduleClass, module);
		}

		defaultPropertiesConfig(configurations);
		for(LibraryModule module : __MODULES__.values())
			module.run();
		createJSONFile(configurations, mainConfigFile);
	}

	protected static final Field FIELD_LibraryModule_name;
	protected static final Field FIELD_LibraryModule_file;
	static { try {
		FIELD_LibraryModule_name = LibraryModule.class.getDeclaredField("name");
		FIELD_LibraryModule_file = LibraryModule.class.getDeclaredField("file");
	} catch(Exception e) { throw new Error(e); } }
	protected static LibraryModule initModule(String moduleName, File modulePath, Class<? extends LibraryModule> moduleClass) throws Exception {
		LibraryModule module = moduleClass.newInstance();
		setFinal(module, FIELD_LibraryModule_name, moduleName);
		setFinal(module, FIELD_LibraryModule_file, modulePath);
		return module;
	}

	protected static void defaultPropertiesConfig(JSON_mainRoot configurations) throws Exception {
		ThrowsReferencedCallback<Void> putExecutable = (args) -> {
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
			configurations.properties.setProperty(key, path.getAbsolutePath());
			return null;
		};
		putExecutable.get("java", new String[] { "java8", "java9", "java11", "java13", "java17", "gvm19java8", "gvm20java8", "gvm20java11", "rlyjava15", "rlyjava16", "pnmjava14", "pnmjava16", "zuljava15" });
		putExecutable.get("javac", new String[] { "javac8", "javac9", "javac11", "javac13", "javac17", "gvm19javac8", "gvm20javac8", "gvm20javac11", "rlyjavac15", "rlyjavac16", "pnmjavac14", "pnmjavac16", "zuljavac15" });
		putExecutable.get("jar", new String[] { "jar8", "jar9", "jar11", "jar13", "jar17", "gvm19jar8", "gvm20jar8", "gvm20jar11", "rlyjar15", "rlyjar16", "pnmjar14", "pnmjar16", "zuljar15" });
		putExecutable.get("certutil");
		putExecutable.get("openssl");
		putExecutable.get("md5sum");
		putExecutable.get("sha1sum");
		putExecutable.get("sha224sum");
		putExecutable.get("sha256sum");
		putExecutable.get("sha384sum");
		putExecutable.get("sha512sum");
	}
}
