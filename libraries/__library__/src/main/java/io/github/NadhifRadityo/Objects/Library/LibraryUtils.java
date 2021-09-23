package io.github.NadhifRadityo.Objects.Library;

import io.github.NadhifRadityo.Objects.Library.Constants.Action;
import io.github.NadhifRadityo.Objects.Library.Constants.JSON_configurationsRoot;
import io.github.NadhifRadityo.Objects.Library.Constants.Provider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import static io.github.NadhifRadityo.Objects.Library.Library.error;
import static io.github.NadhifRadityo.Objects.Library.Utils.extendProperties;
import static io.github.NadhifRadityo.Objects.Library.Utils.p_setObject;
import static io.github.NadhifRadityo.Objects.Library.Utils.pn_getBoolean;
import static io.github.NadhifRadityo.Objects.Library.Utils.pn_getObject;

@SuppressWarnings("unused")
public class LibraryUtils {

	public static long SONATYPE_MAVEN = Provider.SONATYPE.id | Provider.MAVEN.id << 16;
	public static long MAVEN_SONATYPE = Provider.MAVEN.id | Provider.SONATYPE.id << 16;

	public static JSON_configurationsRoot.$module.$dependency[] search(Properties properties, long providers, Object... args) throws Exception {
		Object[] newArgs = new Object[args.length + 1];
		newArgs[0] = properties;
		System.arraycopy(args, 0, newArgs, 1, args.length);

		for(int i = 0; i < Long.BYTES / Short.BYTES; i++) {
			short providerId = (short) ((providers >> i * 8 * 2) & 0xFFFF);
			Provider provider = Provider.fromId(providerId);
			if(provider == null) { error("Invalid provider: %s", providerId); continue; }
			ThrowsReferencedCallback<JSON_configurationsRoot.$module.$dependency[]> search = provider.SEARCH;
			if(search == null) continue;
			try {
				JSON_configurationsRoot.$module.$dependency[] result = search.get(newArgs);
				if(result != null) return result;
			} catch(Exception e) { error("Error while doing provider: %s,\n%s", providerId, e); }
		} return null;
	}

	public static ReferencedCallback<Boolean> mavenSonatypeDependencyParser(String nativeDir) {
		return args -> {
			JSON_configurationsRoot.$module.$dependency.$item item = (JSON_configurationsRoot.$module.$dependency.$item) args[0];
			JSON_configurationsRoot.$module.$dependency dependency = (JSON_configurationsRoot.$module.$dependency) args[1];
			String name = item.name;
			if(!Provider.MAVEN.equals(dependency.source) && !Provider.SONATYPE.equals(dependency.source))
				return true;
			String group = pn_getObject(dependency.properties, "group");
			String artifact = pn_getObject(dependency.properties, "artifact");
			String version = pn_getObject(dependency.properties, "version");

			String mainJar = artifact + "-" + version + ".jar";
			List<Action> actions = new ArrayList<>();
			actions.add(Action.DOWNLOAD);
			actions.add(Action.DELETE);
			String type = null;
			if(name.equals(mainJar)) type = "classes";
			if(name.endsWith("-javadoc.jar")) type = "javadoc";
			if(name.endsWith("-sources.jar")) type = "sources";
			if(name.endsWith(".pom")) type = "pom";
			if(nativeDir != null && type == null)
				item.directory = nativeDir;
			else actions.add(Action.PACK);
			item.actions = actions.stream().map(a -> a.id).reduce((a, b) -> a | b).get();
			if(type != null)
				p_setObject(item.properties, "type", type);
			return true;
		};
	}
	public static ReferencedCallback<Boolean> htmlDirDependencyParser(ReferencedCallback<String> typeProvider, String toDepend, String defaultDir, String nativeDir) {
		return args -> {
			JSON_configurationsRoot.$module.$dependency.$item item = (JSON_configurationsRoot.$module.$dependency.$item) args[0];
			JSON_configurationsRoot.$module.$dependency dependency = (JSON_configurationsRoot.$module.$dependency) args[1];
			String name = item.name;
			if(!Provider.HTML_DIR_LISTING.equals(dependency.source))
				return true;
			String path = pn_getObject(item.properties, "path");
			boolean directory = pn_getBoolean(item.properties, "directory");
			if(!path.startsWith(toDepend) || directory) return false;

			String type = typeProvider.get(name, path);
			List<Action> actions = new ArrayList<>();
			actions.add(Action.DOWNLOAD);
			actions.add(Action.DELETE);
			if(defaultDir != null)
				item.directory = defaultDir;
			if(nativeDir != null && type == null)
				item.directory = nativeDir;
			else actions.add(Action.PACK);
			item.actions = actions.stream().map(a -> a.id).reduce((a, b) -> a | b).get();
			if(type != null)
				p_setObject(item.properties, "type", type);
			return true;
		};
	}
	public static void parseDependency(JSON_configurationsRoot.$module.$dependency dependency, ReferencedCallback<Boolean> callback) {
		dependency.items = Stream.of(dependency.items).filter(item -> callback.get(item, dependency))
				.toArray(JSON_configurationsRoot.$module.$dependency.$item[]::new);
	}
	public static void putDependencies(JSON_configurationsRoot.$module module, JSON_configurationsRoot.$module.$dependency... dependencies) {
		module.dependencies = dependencies;
		if(module.dependencies == null) return;
		for(JSON_configurationsRoot.$module.$dependency dependency : module.dependencies) {
			dependency.properties = extendProperties(dependency.properties, module.properties, false);
			if(dependency.items == null) continue;
			for(JSON_configurationsRoot.$module.$dependency.$item item : dependency.items) {
				item.properties = extendProperties(item.properties, dependency.properties, false);
			}
		}
	}

	public static boolean[] download(JSON_configurationsRoot.$module.$dependency dependency, File currentDir, Object... args) throws Exception {
		Object[] newArgs = new Object[args.length + 2];
		newArgs[0] = dependency;
		newArgs[1] = currentDir;
		System.arraycopy(args, 0, newArgs, 2, args.length);
		ThrowsReferencedCallback<boolean[]> provider = dependency.source.DOWNLOAD;
		return provider != null ? provider.get(newArgs) : null;
	}

	public static boolean[] delete(JSON_configurationsRoot.$module.$dependency dependency, File currentDir, Object... args) throws Exception {
		Object[] newArgs = new Object[args.length + 2];
		newArgs[0] = dependency;
		newArgs[1] = currentDir;
		System.arraycopy(args, 0, newArgs, 2, args.length);
		ThrowsReferencedCallback<boolean[]> provider = dependency.source.DELETE;
		return provider != null ? provider.get(newArgs) : null;
	}

	public static Element createLibrary(JSON_configurationsRoot.$module module, File currentDir, Document out) throws Exception {
		List<JSON_configurationsRoot.$module.$dependency.$item> classes = new ArrayList<>();
		List<JSON_configurationsRoot.$module.$dependency.$item> javadoc = new ArrayList<>();
		List<JSON_configurationsRoot.$module.$dependency.$item> sources = new ArrayList<>();
		for(JSON_configurationsRoot.$module.$dependency dependency : module.dependencies) {
			for(int i = 0; i < dependency.items.length; i++) {
				JSON_configurationsRoot.$module.$dependency.$item item = dependency.items[i];
				String type = pn_getObject(item.properties, "type");
				if(type == null) continue;

				List<String> types = Arrays.asList(type.split(File.pathSeparator));
				if(types.contains("classes")) classes.add(item);
				if(types.contains("javadoc")) javadoc.add(item);
				if(types.contains("sources")) sources.add(item);
			}
		}
		return createLibrary(module.name, currentDir, out, classes.toArray(new JSON_configurationsRoot.$module.$dependency.$item[0]),
				javadoc.toArray(new JSON_configurationsRoot.$module.$dependency.$item[0]), sources.toArray(new JSON_configurationsRoot.$module.$dependency.$item[0]));
	}
	public static Element createLibrary(String moduleName, File currentDir, Document out, JSON_configurationsRoot.$module.$dependency.$item[] classes, JSON_configurationsRoot.$module.$dependency.$item[] javadoc, JSON_configurationsRoot.$module.$dependency.$item[] sources) throws Exception {
		List<File> classesFile = null;
		List<File> javadocFile = null;
		List<File> sourcesFile = null;
		if(classes != null && classes.length > 0) {
			classesFile = new ArrayList<>();
			for(JSON_configurationsRoot.$module.$dependency.$item item : classes) {
				String dir = item.directory;
				String name = item.name;
				long act = item.actions;

				File directory = dir != null ? new File(currentDir, dir) : currentDir;
				if(!directory.exists() && !directory.mkdirs())
					throw new IllegalArgumentException();
				if(name.isEmpty() || !Action.PACK.contains(act))
					continue;

				classesFile.add(new File(directory, name));
			}
		}
		if(javadoc != null && javadoc.length > 0) {
			javadocFile = new ArrayList<>();
			for(JSON_configurationsRoot.$module.$dependency.$item item : javadoc) {
				String dir = item.directory;
				String name = item.name;
				long act = item.actions;

				File directory = dir != null ? new File(currentDir, dir) : currentDir;
				if(!directory.exists() && !directory.mkdirs())
					throw new IllegalArgumentException();
				if(name.isEmpty() || !Action.PACK.contains(act))
					continue;

				javadocFile.add(new File(directory, name));
			}
		}
		if(sources != null && sources.length > 0) {
			sourcesFile = new ArrayList<>();
			for(JSON_configurationsRoot.$module.$dependency.$item item : sources) {
				String dir = item.directory;
				String name = item.name;
				long act = item.actions;

				File directory = dir != null ? new File(currentDir, dir) : currentDir;
				if(!directory.exists() && !directory.mkdirs())
					throw new IllegalArgumentException();
				if(name.isEmpty() || !Action.PACK.contains(act))
					continue;

				sourcesFile.add(new File(directory, name));
			}
		}
		return createLibrary(moduleName, out, classesFile != null ? classesFile.toArray(new File[0]) : null, javadocFile != null ? javadocFile.toArray(new File[0]) : null,
				sourcesFile != null ? sourcesFile.toArray(new File[0]) : null);
	}
	public static Element createLibrary(String moduleName, Document out, File[] classes, File[] javadoc, File[] sources) throws Exception {
		Element component = out.createElement("component");
		component.setAttribute("name", "libraryTable");
		Element library = out.createElement("library");
		library.setAttribute("name", moduleName);
		component.appendChild(library);
		if(classes != null && classes.length > 0) {
			Element eClasses = out.createElement("CLASSES");
			library.appendChild(eClasses);
			for(File classesFile : classes) {
				Element root = out.createElement("root");
				root.setAttribute("url", "jar://" + classesFile.getAbsolutePath());
				eClasses.appendChild(root);
			}
		}
		if(javadoc != null && javadoc.length > 0) {
			Element eJavadoc = out.createElement("JAVADOC");
			library.appendChild(eJavadoc);
			for(File javadocFile : javadoc) {
				Element root = out.createElement("root");
				root.setAttribute("url", "jar://" + javadocFile.getAbsolutePath());
				eJavadoc.appendChild(root);
			}
		}
		if(sources != null && sources.length > 0) {
			Element eSources = out.createElement("SOURCES");
			library.appendChild(eSources);
			for(File sourcesFile : sources) {
				Element root = out.createElement("root");
				root.setAttribute("url", "jar://" + sourcesFile.getAbsolutePath());
				eSources.appendChild(root);
			}
		}
		return component;
	}
}
