package io.github.NadhifRadityo.Objects.Library;

import io.github.NadhifRadityo.Objects.Library.Constants.JSON_configurationsRoot;
import io.github.NadhifRadityo.Objects.Library.Constants.Phase;
import io.github.NadhifRadityo.Objects.Library.Constants.Provider;
import io.github.NadhifRadityo.Objects.Library.Constants.Stage;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static io.github.NadhifRadityo.Objects.Library.Utils.copyNonDefaultProperties;
import static io.github.NadhifRadityo.Objects.Library.Utils.createJSONFile;
import static io.github.NadhifRadityo.Objects.Library.Utils.extendProperties;
import static io.github.NadhifRadityo.Objects.Library.Utils.getFileString;
import static io.github.NadhifRadityo.Objects.Library.Utils.searchPath;
import static io.github.NadhifRadityo.Objects.Library.Utils.throwableToString;
import static io.github.NadhifRadityo.Objects.Library.Utils.toJson;
import static io.github.NadhifRadityo.Objects.Library.Utils.unescapeJavaString;

public class Library {
	public static void main(String... args) throws Exception {
		Options options = new Options();

		options.addOption(Option.builder("p").longOpt("phase").argName("phase name").desc("Run with phase <phase name>.").hasArg().valueSeparator(' ').required().build());
		options.addOption(Option.builder("s").longOpt("stage").argName("stage name").desc("Run with stage <stage name>.").hasArg().valueSeparator(' ').required().build());
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
		Stage stage = Stage.valueOf(commandLine.getOptionValue("stage"));
		File directory = new File(commandLine.getOptionValue("directory"));
		Properties additional = new Properties();
		String[] targets = null;
		if(commandLine.hasOption("additional"))
			additional.load(new StringReader(String.join("\n", Arrays.stream(
					unescapeJavaString(commandLine.getOptionValue("additional"))
							.split("\n")).map(Utils::escape).toArray(String[]::new))));
		if(commandLine.hasOption("targets"))
			targets = commandLine.getOptionValue("targets").split(";");

		__PHASE__ = phase;
		__STAGE__ = stage;
		__DIRECTORY__ = directory;
		__ADDITIONAL__ = additional;
		__TARGETS__ = targets;
		__BUILD_DIRECTORY__ = new File(__DIRECTORY__, "__build__");

		File configFile = new File(__BUILD_DIRECTORY__, stage.name().toLowerCase() + ".json");
		JSON_configurationsRoot configurations = null;
		if(configFile.exists()) {
			configurations = toJson(getFileString(configFile), JSON_configurationsRoot.class);
			if(configurations != null)
				configurations.properties.putAll(additional);
		} else if(!configFile.createNewFile())
			throw new Error("Cannot create configuration file");
		if(configurations == null) {
			configurations = new JSON_configurationsRoot();
			configurations.properties = additional;
		}

		File[] listFiles = directory.listFiles();
		if(targets == null && listFiles != null)
			targets = Arrays.stream(listFiles).filter(File::isDirectory).filter(f -> !f.getName().startsWith("__") && !f.getName().endsWith("__"))
					.map(File::getName).toArray(String[]::new);
		if(targets == null)
			throw new IllegalArgumentException("Target not specified");

		List<LibraryPack> libraryPacks = new ArrayList<>();
		for(String target : targets) {
			File manifestFile = new File(directory, target + File.separator + "lib.mf");
			log("Checking \"%s\" library manager... %s", target, manifestFile.exists() ? "yes" : "no");
			if(!manifestFile.exists()) continue;

			Manifest manifest;
			try(FileInputStream manifestFileStream = new FileInputStream(manifestFile)) {
				manifest = new Manifest(manifestFileStream); }
			Attributes mainAttributes = manifest.getMainAttributes();
			Class<?> mainClass = null; try { mainClass = Class.forName(mainAttributes.getValue("Main-Class")); } catch(Exception ignored) { }
			Class<?> testClass = null; try { testClass = Class.forName(mainAttributes.getValue("Test-Class")); } catch(Exception ignored) { }
			LibraryPack libraryPack = new LibraryPack(target, manifest, mainClass, testClass);
			libraryPacks.add(libraryPack);
		}

		if(phase == Phase.CONFIG)
			defaultPropertiesConfig(configurations);
		preProvider(phase, stage, directory, configurations, libraryPacks);
		Map<String, JSON_configurationsRoot.$module> modules = readModules(phase, stage, directory, configurations, libraryPacks);
		runModules(phase, stage, directory, configurations, libraryPacks, modules);
		writeModules(phase, stage, directory, configurations, libraryPacks, modules);
		postProvider(phase, stage, directory, configurations, libraryPacks);
		if(phase == Phase.CONFIG) {
			String stringOut = createJSONFile(configurations, configFile);
			info(stringOut.replaceAll("%", "%%"));
		}
	}

	protected static void preProvider(Phase phase, Stage stage, File directory, JSON_configurationsRoot configurations, List<LibraryPack> libraryPacks) throws Exception {
		for(Provider provider : Provider.values())
			provider.PHASE_PRE.get(phase, stage, directory, configurations, libraryPacks);
	}
	protected static void postProvider(Phase phase, Stage stage, File directory, JSON_configurationsRoot configurations, List<LibraryPack> libraryPacks) throws Exception {
		for(Provider provider : Provider.values())
			provider.PHASE_POST.get(phase, stage, directory, configurations, libraryPacks);
	}

	protected static Map<String, JSON_configurationsRoot.$module> readModules(Phase phase, Stage stage, File directory, JSON_configurationsRoot configurations, List<LibraryPack> libraryPacks) throws Exception {
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
		libraryPacks.stream().map(l -> l.name).filter(n -> !modules.containsKey(n)).forEach(l -> {
			JSON_configurationsRoot.$module module = new JSON_configurationsRoot.$module();
			module.name = l;
			module.properties = new Properties(configurations.properties);
			module.dependencies = new JSON_configurationsRoot.$module.$dependency[0];
			modules.put(l, module);
		});
		return modules;
	}
	protected static void runModules(Phase phase, Stage stage, File directory, JSON_configurationsRoot configurations, List<LibraryPack> libraryPacks, Map<String, JSON_configurationsRoot.$module> modules) throws Exception {
		for(LibraryPack libraryPack : libraryPacks) {
			__CURRENT__ = libraryPack;
			__CURRENT_DIR__ = new File(directory, libraryPack.name);
			log("Executing \"%s\" library manager... %s (%s)", libraryPack.name, phase.name().toLowerCase(), stage.name().toLowerCase());
			JSON_configurationsRoot.$module module = modules.get(libraryPack.name);
			if(phase != Phase.VALIDATE)
				libraryPack.mainClass.getMethod(phase.name(), Stage.class, JSON_configurationsRoot.$module.class).invoke(null, stage, module);
			else libraryPack.testClass.getMethod(phase.name(), Stage.class, JSON_configurationsRoot.$module.class).invoke(null, stage, module);
		}
	}
	protected static void writeModules(Phase phase, Stage stage, File directory, JSON_configurationsRoot configurations, List<LibraryPack> libraryPacks, Map<String, JSON_configurationsRoot.$module> modules) throws Exception {
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
		putExecutable.get("java", new String[] { "java8", "java9", "java11", "java13", "gvm19java8", "gvm20java8", "gvm20java11", "rlyjava15", "rlyjava16", "pnmjava14", "pnmjava16", "zuljava15" });
		putExecutable.get("javac", new String[] { "javac8", "javac9", "javac11", "javac13", "gvm19javac8", "gvm20javac8", "gvm20javac11", "rlyjavac15", "rlyjavac16", "pnmjavac14", "pnmjavac16", "zuljavac15" });
		putExecutable.get("jar", new String[] { "jar8", "jar9", "jar11", "jar13", "gvm19jar8", "gvm20jar8", "gvm20jar11", "rlyjar15", "rlyjar16", "pnmjar14", "pnmjar16", "zuljar15" });
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

	protected static Phase __PHASE__;
	protected static Stage __STAGE__;
	protected static File __DIRECTORY__;
	protected static Properties __ADDITIONAL__;
	protected static String[] __TARGETS__;
	protected static File __BUILD_DIRECTORY__;

	protected static LibraryPack __CURRENT__;
	protected static File __CURRENT_DIR__;

	public static File __build_dir() {
		File buildDirectory = new File(__BUILD_DIRECTORY__, __CURRENT__.name + File.separator + __STAGE__.name().toLowerCase());
		if(!buildDirectory.exists() && !buildDirectory.mkdirs())
			throw new IllegalArgumentException();
		return buildDirectory;
	}
	public static File __static_dir() {
		File buildDirectory = new File(__BUILD_DIRECTORY__, __CURRENT__.name + File.separator + "__static__");
		if(!buildDirectory.exists() && !buildDirectory.mkdirs())
			throw new IllegalArgumentException();
		return buildDirectory;
	}
}
