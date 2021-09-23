package io.github.NadhifRadityo.Library;

import groovy.lang.Closure;
import groovy.lang.MissingPropertyException;
import org.gradle.api.Action;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Task;
import org.gradle.api.internal.file.BaseDirFileResolver;
import org.gradle.api.internal.file.FileResolver;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static io.github.NadhifRadityo.Library.LibraryEntry.__ROOT_DIRECTORY__;
import static io.github.NadhifRadityo.Library.LibraryEntry.__TARGET_DIRECTORY__;
import static io.github.NadhifRadityo.Library.Utils.a_getString;
import static io.github.NadhifRadityo.Library.Utils.a_setObject;
import static io.github.NadhifRadityo.Library.Utils.file;
import static io.github.NadhifRadityo.Library.Utils.getCurrentClassFile;
import static io.github.NadhifRadityo.Library.Utils.mkdir;
import static java.util.Objects.requireNonNull;

public abstract class LibraryModule extends LibraryImplement {
	private String identifier;
	private File path;

	private Manifest manifest;
	private String unique;
	private String name;
	private String description;

	protected LibraryModule(String unique) { this.unique = unique; }
	protected LibraryModule() { this(null); }
	private void init(String identifier, File path, Manifest manifest) throws Exception {
		this.identifier = identifier;
		this.path = path;

		Attributes attributes = manifest.getMainAttributes();
		this.manifest = manifest;
		this.unique = "library." + a_getString(attributes, "Module-Unique", identifier);
		this.name = a_getString(attributes, "Module-Name", path.getName());
		this.description = a_getString(attributes, "Module-Description");
		initProperties();
	}

	public String getIdentifier() { return identifier; }
	public File getPath() { return path; }
	public Manifest getManifest() { return manifest; }
	public String getUnique() { return unique; }

	public abstract void run();

	public String unique(String s) { return this.unique + "." + s; }

	// Modified
	private FileResolver fileResolver;
	private File buildDir;
	private File buildFile;
	private File staticDir;
	private void initProperties() throws Exception {
		this.fileResolver = new BaseDirFileResolver(this.path);
		this.buildDir = file(__TARGET_DIRECTORY__, this.name);
		this.buildFile = getCurrentClassFile(getClass());
		this.staticDir = file(this.buildDir, "__static__");
		mkdir(buildDir);
		mkdir(staticDir);
	}

	public File getRootDir() { return __ROOT_DIRECTORY__; }
	public File getProjectDir() { return this.path; }
	public File getBuildDir() { return this.buildDir; }
	public void setBuildDir(File file) { this.buildDir = file; }
	public void setBuildDir(Object o) { this.buildDir = this.fileResolver.resolve(o); }
	public File getBuildFile() { return this.buildFile; }
	public File getStaticDir() { return staticDir; }
	public void setStaticDir(File file) { this.staticDir = file; }
	public void setStaticDir(Object o) { this.staticDir = this.fileResolver.resolve(o); }

	public String getName() { return this.name; }
	@Nullable public String getDescription() { return this.description; }
	public void setDescription(@Nullable String s) { this.description = s; }

	public Attributes getPropertyAttributes(String key) { return key != null ? this.manifest.getAttributes(key) : this.manifest.getMainAttributes(); }
	private String attributeName(String s) { int splitIndex = s.indexOf('.'); return splitIndex != -1 ? s.substring(0, splitIndex) : null; }
	private String attributeProperties(String s) { int splitIndex = s.indexOf('.'); return splitIndex != -1 ? s.substring(splitIndex + 1) : s; }
	public void setProperty(String s, @Nullable Object o) throws MissingPropertyException { a_setObject(getPropertyAttributes(attributeName(s)), attributeProperties(s), o); }
	@Nullable public String property(String s) throws MissingPropertyException { return requireNonNull(a_getString(getPropertyAttributes(attributeName(s)), attributeProperties(s))); }
	@Nullable public String findProperty(String s) { return a_getString(getPropertyAttributes(attributeName(s)), attributeProperties(s)); }

	private Task returnTask(Task task) { task.setGroup(unique); return task; }
	public Task task(String s) throws InvalidUserDataException { return returnTask(super.task(s)); }
	public Task task(Map<String, ?> map, String s) throws InvalidUserDataException { return returnTask(super.task(map, s)); }
	public Task task(Map<String, ?> map, String s, Closure closure) { return returnTask(super.task(map, s, closure)); }
	public Task task(String s, Closure closure) { return returnTask(super.task(s, closure)); }
	public Task task(String s, Action<? super Task> action) { return returnTask(super.task(s, action)); }
}
