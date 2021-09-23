package io.github.NadhifRadityo.Objects.Library;

import io.github.NadhifRadityo.Objects.Library.Constants.JSON_configurationsRoot;
import io.github.NadhifRadityo.Objects.Library.Constants.Phase;
import io.github.NadhifRadityo.Objects.Library.Constants.Provider;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.StringReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import static io.github.NadhifRadityo.Objects.Library.Utils.copyNonDefaultProperties;
import static io.github.NadhifRadityo.Objects.Library.Utils.createJSONFile;
import static io.github.NadhifRadityo.Objects.Library.Utils.extendProperties;
import static io.github.NadhifRadityo.Objects.Library.Utils.getFileName;
import static io.github.NadhifRadityo.Objects.Library.Utils.getFileString;
import static io.github.NadhifRadityo.Objects.Library.Utils.searchPath;
import static io.github.NadhifRadityo.Objects.Library.Utils.throwableToString;
import static io.github.NadhifRadityo.Objects.Library.Utils.toJson;
import static io.github.NadhifRadityo.Objects.Library.Utils.unescapeJavaString;

public class Library {
	public static void main(String... args) throws Exception {
		Options options = new Options();

		options.addOption(Option.builder("p").longOpt("phase").argName("phase name").desc("Run with phase <phase name>.").hasArg().valueSeparator(' ').required().build());
		options.addOption(Option.builder("d").longOpt("directory").argName("current dir").desc("Check libraries in <current dir>.").hasArg().valueSeparator(' ').required().build());
		options.addOption(Option.builder("a").longOpt("additional").argName("value").desc("Set some additional properties.").hasArg().valueSeparator(' ').optionalArg(true).build());
		options.addOption(Option.builder("t").longOpt("targets").argName("targets lib").desc("Run with specified library.").hasArg().valueSeparator(' ').optionalArg(true).build());

		CommandLineParser commandLineParser = new DefaultParser();
		HelpFormatter helpFormatter = new HelpFormatter();
		CommandLine commandLine = null;

		try {
			if(args.length == 0) throw new Error("Empty arguments!");
			commandLine = commandLineParser.parse(options, args);
		} catch(ParseException|Error e) {
			System.out.println(e.getMessage());
			return;
		}

		Phase phase = Phase.valueOf(commandLine.getOptionValue("phase"));
		File directory = new File(commandLine.getOptionValue("directory"));
		Properties additional = new Properties();
		String[] targets = null;
		if(commandLine.hasOption("additional"))
			additional.load(new StringReader(String.join("\n", Arrays.stream(
					unescapeJavaString(commandLine.getOptionValue("additional"))
							.split("\n")).map(Utils::escape).toArray(String[]::new))));
		if(commandLine.hasOption("targets"))
			targets = commandLine.getOptionValue("targets").split(";");

		File configFile = new File(directory, "configurations.json");
		JSON_configurationsRoot configurations;
		if(configFile.exists()) {
			configurations = toJson(getFileString(configFile), JSON_configurationsRoot.class);
			configurations.properties.putAll(additional);
		} else {
			if(!configFile.createNewFile())
				throw new Error("Cannot create configuration file");
			configurations = new JSON_configurationsRoot();
			configurations.properties = additional;
		}

		File[] listFiles = directory.listFiles();
		if(targets == null && listFiles != null)
			targets = Arrays.stream(listFiles).filter(File::isDirectory).map(File::getName).toArray(String[]::new);
		if(targets == null)
			throw new IllegalArgumentException("Target not specified");

		Map<String, Class<?>> libraryClasses = new HashMap<>();
		for(String target : targets) {
			File source = new File(directory, target + File.separator + "Main.java");
			log("Checking \"%s\" library manager... %s", target, source.exists() ? "yes" : "no");
			if(!source.exists()) continue;

			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

			String classpaths = System.getProperty("java.class.path");
			classpaths += File.pathSeparator + directory.getAbsolutePath();
			List<String> optionList = new ArrayList<>();
			optionList.add("-classpath");
			optionList.add(classpaths);

			Iterable<? extends JavaFileObject> compilationUnit = fileManager.getJavaFileObjectsFromFiles(Collections.singletonList(source));
			JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, optionList, null, compilationUnit);
			if(!task.call()) {
				for(Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics())
					error("Error on line %d in %s: %s%n", diagnostic.getLineNumber(), diagnostic.getSource().toUri(), diagnostic.getMessage(Locale.ENGLISH));
				continue; }
			URLClassLoader classLoader = new URLClassLoader(new URL[] { source.getParentFile().toURI().toURL() });
			Class<?> clazz = classLoader.loadClass(getFileName(source));
			libraryClasses.put(target, clazz);
		}

		if(phase == Phase.CONFIG)
			defaultPropertiesConfig(configurations);
		preProvider(phase, directory, configurations, libraryClasses);
		Map<String, JSON_configurationsRoot.$module> modules = readModules(phase, directory, configurations, libraryClasses);
		runModules(phase, directory, configurations, libraryClasses, modules);
		writeModules(phase, directory, configurations, libraryClasses, modules);
		postProvider(phase, directory, configurations, libraryClasses);
		if(phase == Phase.CONFIG) {
			String stringOut = createJSONFile(configurations, configFile);
			info(stringOut.replaceAll("%", "%%"));
		}
	}

	protected static void preProvider(Phase phase, File directory, JSON_configurationsRoot configurations, Map<String, Class<?>> libraryClasses) throws Exception {
		for(Provider provider : Provider.values())
			provider.PHASE_PRE.get(phase, directory, configurations, libraryClasses);
	}
	protected static void postProvider(Phase phase, File directory, JSON_configurationsRoot configurations, Map<String, Class<?>> libraryClasses) throws Exception {
		for(Provider provider : Provider.values())
			provider.PHASE_POST.get(phase, directory, configurations, libraryClasses);
	}

	protected static Map<String, JSON_configurationsRoot.$module> readModules(Phase phase, File directory, JSON_configurationsRoot configurations, Map<String, Class<?>> libraryClasses) throws Exception {
		Map<String, JSON_configurationsRoot.$module> modules = new HashMap<>();
		if(configurations.modules != null) for(JSON_configurationsRoot.$module module : configurations.modules) {
			module.properties = extendProperties(module.properties, configurations.properties, false);
			if(module.dependencies == null) continue;
			for(JSON_configurationsRoot.$module.$dependency dependency : module.dependencies) {
				dependency.properties = extendProperties(dependency.properties, module.properties, false);
				if(dependency.items == null) continue;
				for(JSON_configurationsRoot.$module.$dependency.$item item : dependency.items) {
					item.properties = extendProperties(item.properties, dependency.properties, false);
				}
			}
			modules.put(module.name, module);
		}
		libraryClasses.keySet().stream().filter(n -> !modules.containsKey(n)).forEach(l -> {
			JSON_configurationsRoot.$module module = new JSON_configurationsRoot.$module();
			module.name = l;
			module.properties = new Properties(configurations.properties);
			modules.put(l, module);
		});
		return modules;
	}
	protected static void runModules(Phase phase, File directory, JSON_configurationsRoot configurations, Map<String, Class<?>> libraryClasses, Map<String, JSON_configurationsRoot.$module> modules) throws Exception {
		for(Map.Entry<String, Class<?>> entry : libraryClasses.entrySet()) {
			log("Executing \"%s\" library manager... %s", entry.getKey(), phase.name().toLowerCase());
			File moduleDirectory = new File(directory, entry.getKey());
			JSON_configurationsRoot.$module module = modules.get(entry.getKey());
			entry.getValue().getMethod(phase.name(), File.class, JSON_configurationsRoot.$module.class).invoke(null, moduleDirectory, module);
		}
	}
	protected static void writeModules(Phase phase, File directory, JSON_configurationsRoot configurations, Map<String, Class<?>> libraryClasses, Map<String, JSON_configurationsRoot.$module> modules) throws Exception {
		configurations.modules = modules.values().toArray(new JSON_configurationsRoot.$module[0]);
		configurations.properties = copyNonDefaultProperties(configurations.properties, true);
		for(JSON_configurationsRoot.$module module : modules.values()) {
			module.properties = copyNonDefaultProperties(module.properties, true);
			if(module.dependencies == null) continue;
			for(JSON_configurationsRoot.$module.$dependency dependency : module.dependencies) {
				dependency.properties = copyNonDefaultProperties(dependency.properties, true);
				if(dependency.items == null) continue;
				for(JSON_configurationsRoot.$module.$dependency.$item item : dependency.items) {
					item.properties = copyNonDefaultProperties(item.properties, true);
				}
			}
		}
	}

	protected static void defaultPropertiesConfig(JSON_configurationsRoot configurations) throws Exception {
		ThrowsReferencedCallback<Void> putExecutable = (args) -> {
			String executable = (String) args[0];
			String key = executable + "Path";
			if(configurations.properties.containsKey(key)) return null;
			File path = searchPath(executable); if(path == null) return null;
			configurations.properties.setProperty(key, path.getAbsolutePath());
			return null;
		};
		putExecutable.get("java");
		putExecutable.get("javac");
		putExecutable.get("jar");
		putExecutable.get("certutil");
		putExecutable.get("openssl");
		putExecutable.get("md5sum");
		putExecutable.get("sha1sum");
	}

	public static boolean disableDebugPrint = false;
	protected static void parseFormat(Object... format) {
		for(int i = 0; i < format.length; i++) {
			Object object = format[i];
			if(object instanceof Throwable)
				format[i] = throwableToString((Throwable) object);
		}
	}
	public static void log(String text, Object... format) { parseFormat(format); for(String line : String.format(text, format).split("\n")) System.out.println("[LOG] " + line); }
	public static void info(String text, Object... format) { parseFormat(format); for(String line : String.format(text, format).split("\n")) System.out.println("[INFO] " + line); }
	public static void debug(String text, Object... format) { if(disableDebugPrint) return; parseFormat(format); for(String line : String.format(text, format).split("\n")) System.out.println("[DEBUG] " + line); }
	public static void warn(String text, Object... format) { parseFormat(format); for(String line : String.format(text, format).split("\n")) System.out.println("[WARN] " + line); }
	public static void error(String text, Object... format) { parseFormat(format); for(String line : String.format(text, format).split("\n")) System.out.println("[ERROR] " + line); }
}
