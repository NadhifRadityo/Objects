package io.github.NadhifRadityo.Objects.Library;

import io.github.NadhifRadityo.Objects.Library.Constants.JSON_mainRoot;
import io.github.NadhifRadityo.Objects.Library.Constants.JSON_moduleRoot;
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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
		__BUILD_DIRECTORY__ = new File(__DIRECTORY__, "__target__");

		File configFile = new File(__BUILD_DIRECTORY__, stage.name().toLowerCase() + ".json");
		JSON_mainRoot configurations = null;
		if(configFile.exists()) {
			configurations = toJson(getFileString(configFile), JSON_mainRoot.class);
			if(configurations != null)
				configurations.properties.putAll(additional);
		} else if(!configFile.createNewFile())
			throw new Error("Cannot create configuration file");
		if(configurations == null) {
			configurations = new JSON_mainRoot();
			configurations.properties = additional;
		}
		__MAIN_ROOT__ = configurations;

		File[] listFiles = directory.listFiles();
		if(targets == null && listFiles != null)
			targets = Arrays.stream(listFiles).filter(File::isDirectory).filter(f -> !f.getName().startsWith("__") && !f.getName().endsWith("__"))
					.map(File::getName).toArray(String[]::new);
		if(targets == null)
			throw new IllegalArgumentException("Target not specified");

		List<ModulePack> modulePacks = new ArrayList<>();
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
			File moduleConfigFile = new File(__BUILD_DIRECTORY__, target + File.separator + stage.name().toLowerCase() + ".json");
			JSON_moduleRoot moduleRoot = null;
			if(moduleConfigFile.exists()) {
				moduleRoot = toJson(getFileString(moduleConfigFile), JSON_moduleRoot.class);
				if(moduleRoot != null)
					moduleRoot.properties = extendProperties(moduleRoot.properties, __MAIN_ROOT__.properties, false);
			} else if(!moduleConfigFile.createNewFile())
				throw new Error("Cannot create configuration file");
			if(moduleRoot == null) {
				moduleRoot = new JSON_moduleRoot();
				moduleRoot.name = target;
				moduleRoot.properties = new Properties(__MAIN_ROOT__.properties);
			}
			ModulePack modulePack = new ModulePack(target, manifest, mainClass, testClass, moduleRoot);
			modulePacks.add(modulePack);
		}
		__TARGETS__ = Collections.unmodifiableList(modulePacks);

		if(phase == Phase.CONFIG)
			defaultPropertiesConfig(configurations);
		preProvider();
		readModules();
		preModules();
		runModules();
		postModules();
		writeModules();
		postProvider();
		if(phase == Phase.CONFIG) {
			String stringOut = createJSONFile(configurations, configFile);
//			info(stringOut.replaceAll("%", "%%"));
			for(ModulePack modulePack : __TARGETS__) {
				File moduleConfigFile = new File(__BUILD_DIRECTORY__, modulePack.name + File.separator + stage.name().toLowerCase() + ".json");
				stringOut = createJSONFile(modulePack.moduleRoot, moduleConfigFile);
//				info(stringOut.replaceAll("%", "%%"));
			}
		}
	}

	protected static void preProvider() throws Exception {
		for(Provider provider : Provider.values())
			provider.PHASE_PRE.get(__TARGETS__);
	}
	protected static void postProvider() throws Exception {
		for(Provider provider : Provider.values())
			provider.PHASE_POST.get(__TARGETS__);
	}

	protected static void readModules() throws Exception {
		for(ModulePack modulePack : __TARGETS__) {
			JSON_moduleRoot moduleRoot = modulePack.moduleRoot;
			moduleRoot.properties = extendProperties(moduleRoot.properties, __MAIN_ROOT__.properties, false);
			if(moduleRoot.dependencies == null) continue;
			for(JSON_moduleRoot.$dependency dependency : moduleRoot.dependencies) {
				dependency.properties = extendProperties(dependency.properties, moduleRoot.properties, false);
				if(dependency.items == null) continue;
				for(JSON_moduleRoot.$dependency.$item item : dependency.items) {
					item.properties = extendProperties(item.properties, dependency.properties, false);
				}
			}
		}
	}
	protected static void preModules() throws Exception {
		for(ModulePack modulePack : __TARGETS__) {
			Class<?> CLASS_$ANY = __PHASE__ != Phase.VALIDATE ? modulePack.mainClass : modulePack.testClass;
			Method METHOD_$ANY_PRE__MODULES; try { METHOD_$ANY_PRE__MODULES = CLASS_$ANY.getMethod("PRE_MODULES",
					JSON_moduleRoot.class); } catch(NoSuchMethodException ignored) { continue; }
			__CURRENT__ = modulePack;
			__CURRENT_DIR__ = new File(__DIRECTORY__, modulePack.name);
			log("Executing \"%s\" library manager... pre modules", modulePack.name);
			JSON_moduleRoot module = modulePack.moduleRoot;
			METHOD_$ANY_PRE__MODULES.invoke(null, module);
		}
	}
	protected static void runModules() throws Exception {
		for(ModulePack modulePack : __TARGETS__) {
			Class<?> CLASS_$ANY = __PHASE__ != Phase.VALIDATE ? modulePack.mainClass : modulePack.testClass;
			Method METHOD_$ANY_$PHASE; try { METHOD_$ANY_$PHASE = CLASS_$ANY.getMethod(__PHASE__.name(),
					JSON_moduleRoot.class); } catch(NoSuchMethodException ignored) { continue; }
			__CURRENT__ = modulePack;
			__CURRENT_DIR__ = new File(__DIRECTORY__, modulePack.name);
			log("Executing \"%s\" library manager... %s (%s)", modulePack.name, __PHASE__.name().toLowerCase(), __STAGE__.name().toLowerCase());
			JSON_moduleRoot module = modulePack.moduleRoot;
			METHOD_$ANY_$PHASE.invoke(null, module);
		}
	}
	protected static void postModules() throws Exception {
		for(ModulePack modulePack : __TARGETS__) {
			Class<?> CLASS_$ANY = __PHASE__ != Phase.VALIDATE ? modulePack.mainClass : modulePack.testClass;
			Method METHOD_$ANY_POST__MODULES; try { METHOD_$ANY_POST__MODULES = CLASS_$ANY.getMethod("POST_MODULES",
					JSON_moduleRoot.class); } catch(NoSuchMethodException ignored) { continue; }
			__CURRENT__ = modulePack;
			__CURRENT_DIR__ = new File(__DIRECTORY__, modulePack.name);
			log("Executing \"%s\" library manager... post modules", modulePack.name);
			JSON_moduleRoot module = modulePack.moduleRoot;
			METHOD_$ANY_POST__MODULES.invoke(null, module);
		}
	}
	protected static void writeModules() throws Exception {
		for(ModulePack modulePack : __TARGETS__) {
			JSON_moduleRoot moduleRoot = modulePack.moduleRoot;
			moduleRoot.properties = copyNonDefaultProperties(moduleRoot.properties, true);
			if(moduleRoot.dependencies == null) continue;
			for(JSON_moduleRoot.$dependency dependency : moduleRoot.dependencies) {
				dependency.properties = copyNonDefaultProperties(dependency.properties, true);
				if(dependency.items == null) continue;
				for(JSON_moduleRoot.$dependency.$item item : dependency.items) {
					item.properties = copyNonDefaultProperties(item.properties, true);
				}
			}
		}
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

	private static Phase __PHASE__;
	private static Stage __STAGE__;
	private static File __DIRECTORY__;
	private static Properties __ADDITIONAL__;
	private static List<ModulePack> __TARGETS__;
	private static File __BUILD_DIRECTORY__;
	private static JSON_mainRoot __MAIN_ROOT__;

	private static ModulePack __CURRENT__;
	private static File __CURRENT_DIR__;

	public static Phase phase() { return __PHASE__; }
	public static Stage stage() { return __STAGE__; }
	public static File directory() { return __DIRECTORY__; }
	public static Properties additional() { return __ADDITIONAL__; }
	public static List<ModulePack> targets() { return __TARGETS__; }
	public static File buildDirectory() { return __BUILD_DIRECTORY__; }
	public static JSON_mainRoot mainRoot() { return __MAIN_ROOT__; }

	public static ModulePack current() { return __CURRENT__; }
	public static File currentDir() { return __CURRENT_DIR__; }

	public static File __target_dir(ModulePack modulePack, String subDirectory) {
		File targetDir = new File(__BUILD_DIRECTORY__, modulePack.name + File.separator + subDirectory);
		if(!targetDir.exists() && !targetDir.mkdirs())
			throw new IllegalArgumentException();
		return targetDir;
	}
	public static File __target_dir(ModulePack modulePack) {
		return __target_dir(modulePack, "");
	}
	public static File __build_dir(ModulePack modulePack) {
		return __target_dir(modulePack, __STAGE__.name().toLowerCase());
	}
	public static File __static_dir(ModulePack modulePack) {
		return __target_dir(modulePack, "__static__");
	}

	public static File __target_dir() {
		return __target_dir(__CURRENT__);
	}
	public static File __build_dir() {
		return __build_dir(__CURRENT__);
	}
	public static File __static_dir() {
		return __static_dir(__CURRENT__);
	}
}
