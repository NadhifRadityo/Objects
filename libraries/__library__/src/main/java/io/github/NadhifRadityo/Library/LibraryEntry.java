package io.github.NadhifRadityo.Library;

import org.gradle.api.internal.project.DefaultProject;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static io.github.NadhifRadityo.Library.Utils.setFinal;

public class LibraryEntry {
	private static DefaultProject __PROJECT__;
	private static List<LibraryModule> __MODULES__;

	public static DefaultProject getProject() { return __PROJECT__; }
	public static List<LibraryModule> getModules() { return Collections.unmodifiableList(__MODULES__); }

	public static void log(String text, Object... format) { Utils.log(text, format); }
	public static void info(String text, Object... format) { Utils.info(text, format); }
	public static void debug(String text, Object... format) { Utils.debug(text, format); }
	public static void warn(String text, Object... format) { Utils.warn(text, format); }
	public static void error(String text, Object... format) { Utils.error(text, format); }

	private static void entry(DefaultProject project, Map<String, File> rawModules) throws Exception {
		__PROJECT__ = project;
		__MODULES__ = new ArrayList<>();

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
			__MODULES__.add(module);
		}

		for(LibraryModule module : __MODULES__)
			module.run();
	}

	private static final Field FIELD_LibraryModule_name;
	private static final Field FIELD_LibraryModule_file;
	static { try {
		FIELD_LibraryModule_name = LibraryModule.class.getDeclaredField("name");
		FIELD_LibraryModule_file = LibraryModule.class.getDeclaredField("file");
	} catch(Exception e) { throw new Error(e); } }
	private static LibraryModule initModule(String moduleName, File modulePath, Class<? extends LibraryModule> moduleClass) throws Exception {
		LibraryModule module = moduleClass.newInstance();
		setFinal(module, FIELD_LibraryModule_name, moduleName);
		setFinal(module, FIELD_LibraryModule_file, modulePath);
		return module;
	}
}
