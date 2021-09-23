package io.github.NadhifRadityo.Library;

import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Task;

import java.io.File;
import java.util.Map;

import static io.github.NadhifRadityo.Library.Utils.file;

public abstract class LibraryModule extends LibraryImplement {
	public final String identifier;
	public final String name;
	public final File file;

	protected LibraryModule(String identifier) {
		this.identifier = identifier;
		// Avoid JIT optimization
		this.name = getClass().getSimpleName();
		this.file = file(this.name);
	}
	protected LibraryModule() {
		this(default_identifier());
	}

	public abstract void run();

	public String unique(String s) { return this.identifier + "." + s; }
	public File sourceDir() { return __source_dir(getClass()); }
	public File targetDir(String path) { return __target_dir(getClass(), path); }
	public File targetDir() { return __target_dir(getClass()); }
	public File staticDir() { return __static_dir(getClass()); }

	// Modified
	private Task returnTask(Task task) { task.setGroup(identifier); return task; }
	public Task task(String s) throws InvalidUserDataException { return returnTask(super.task(s)); }
	public Task task(Map<String, ?> map, String s) throws InvalidUserDataException { return returnTask(super.task(map, s)); }
	public Task task(Map<String, ?> map, String s, Closure closure) { return returnTask(super.task(map, s, closure)); }
	public Task task(String s, Closure closure) { return returnTask(super.task(s, closure)); }
	public Task task(String s, Action<? super Task> action) { return returnTask(super.task(s, action)); }

	private static String default_identifier() {
		Class<? extends LibraryModule> moduleClass = __last_caller_class();
		return "library." + moduleClass.getPackage().getName();
	}
}
