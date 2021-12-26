package io.github.NadhifRadityo.Library;

import groovy.lang.Closure;
import groovy.lang.MissingPropertyException;
import org.gradle.api.Action;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UnknownProjectException;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.LoggingManager;
import org.gradle.api.tasks.TaskContainer;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.NadhifRadityo.Library.LibraryEntry.__CONTEXT__;
import static io.github.NadhifRadityo.Library.LibraryEntry.__MODULES__;
import static io.github.NadhifRadityo.Library.Utils.ClassUtils.classForName;
import static io.github.NadhifRadityo.Library.Utils.FileUtils.file;

public class LibraryImplement {
	protected static final Map<String, Class<?>> __class_cache = new HashMap<>();
	public static Class<? extends LibraryModule> __last_caller_class() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		for(StackTraceElement stackTraceElement : stackTraceElements) {
			String className = stackTraceElement.getClassName();
			Class<?> callerClass = __class_cache.get(className);
			if(callerClass == null) {
				if(__class_cache.containsKey(className)) continue;
				callerClass = classForName(className);
				__class_cache.put(className, callerClass);
				if(callerClass == null) continue;
			}
			if(!LibraryModule.class.isAssignableFrom(callerClass) ||
					LibraryModule.class.equals(callerClass))
				continue;
			return (Class<? extends LibraryModule>) callerClass;
		}
		return null;
	}

	public static <T> T __module(Class<? extends T> moduleClass) { return (T) __MODULES__.get(moduleClass); }
	public static File __module_path(Class<? extends LibraryModule> moduleClass) { return __module(moduleClass).getPath(); }
	public static File __build_dir(Class<? extends LibraryModule> moduleClass, String... path) { return file(__module(moduleClass).getBuildDir(), path); }
	public static File __static_dir(Class<? extends LibraryModule> moduleClass) { return __build_dir(moduleClass, "__static__"); }
	public static File __module_path() { return __module_path(__last_caller_class()); }
	public static File __build_dir(String... path) { return __build_dir(__last_caller_class(), path); }
	public static File __static_dir() { return __static_dir(__last_caller_class()); }

	public Project getRootProject() { return __CONTEXT__.getProject().getRootProject(); }
	public File getRootDir() { return __CONTEXT__.getProject().getRootDir(); }
	public File getBuildDir() { return __CONTEXT__.getProject().getBuildDir(); }
	public void setBuildDir(File file) { __CONTEXT__.getProject().setBuildDir(file); }
	public void setBuildDir(Object o) { __CONTEXT__.getProject().setBuildDir(o); }
	public File getBuildFile() { return __CONTEXT__.getProject().getBuildFile(); }
	@Nullable public Project getParent() { return __CONTEXT__.getProject().getParent(); }
	public String getName() { return __CONTEXT__.getProject().getName(); }
//	public String getDisplayName() { return __CONTEXT__.getProject().getDisplayName(); }
	@Nullable public String getDescription() { return __CONTEXT__.getProject().getDescription(); }
	public void setDescription(@Nullable String s) { __CONTEXT__.getProject().setDescription(s); }
//	public Object getGroup() { return __CONTEXT__.getProject().getGroup(); }
//	public void setGroup(Object o) { __CONTEXT__.getProject().setGroup(o); }
//	public Object getVersion() { return __CONTEXT__.getProject().getVersion(); }
//	public void setVersion(Object o) { __CONTEXT__.getProject().setVersion(o); }
//	public Object getStatus() { return __CONTEXT__.getProject().getStatus(); }
//	public void setStatus(Object o) { __CONTEXT__.getProject().setStatus(o); }
	public Map<String, Project> getChildProjects() { return __CONTEXT__.getProject().getChildProjects(); }
	public void setProperty(String s, @Nullable Object o) throws MissingPropertyException { __CONTEXT__.getProject().setProperty(s, o); }
	public Project getProject() { return __CONTEXT__.getProject().getProject(); }
	public Set<Project> getAllprojects() { return __CONTEXT__.getProject().getAllprojects(); }
	public Set<Project> getSubprojects() { return __CONTEXT__.getProject().getSubprojects(); }
	public Task task(String s) throws InvalidUserDataException { return __CONTEXT__.getProject().task(s); }
	public Task task(Map<String, ?> map, String s) throws InvalidUserDataException { return __CONTEXT__.getProject().task(map, s); }
	public Task task(Map<String, ?> map, String s, Closure closure) { return __CONTEXT__.getProject().task(map, s, closure); }
	public Task task(String s, Closure closure) { return __CONTEXT__.getProject().task(s, closure); }
	public Task task(String s, Action<? super Task> action) { return __CONTEXT__.getProject().task(s, action); }
//	public String getPath() { return __CONTEXT__.getProject().getPath(); }
	public List<String> getDefaultTasks() { return __CONTEXT__.getProject().getDefaultTasks(); }
	public void setDefaultTasks(List<String> list) { __CONTEXT__.getProject().setDefaultTasks(list); }
	public void defaultTasks(String... strings) { __CONTEXT__.getProject().defaultTasks(strings); }
	public Project evaluationDependsOn(String s) throws UnknownProjectException { return __CONTEXT__.getProject().evaluationDependsOn(s); }
	public void evaluationDependsOnChildren() { __CONTEXT__.getProject().evaluationDependsOnChildren(); }
	@Nullable public Project findProject(String s) { return __CONTEXT__.getProject().findProject(s); }
	public Project project(String s) throws UnknownProjectException { return __CONTEXT__.getProject().project(s); }
	public Project project(String s, Closure closure) { return __CONTEXT__.getProject().project(s, closure); }
	public Project project(String s, Action<? super Project> action) { return __CONTEXT__.getProject().project(s, action); }
	public Map<Project, Set<Task>> getAllTasks(boolean b) { return __CONTEXT__.getProject().getAllTasks(b); }
	public Set<Task> getTasksByName(String s, boolean b) { return __CONTEXT__.getProject().getTasksByName(s, b); }
	public File getProjectDir() { return __CONTEXT__.getProject().getProjectDir(); }
//	public File file(Object o) { return __CONTEXT__.getProject().file(o); }
//	public File file(Object o, PathValidation pathValidation) throws InvalidUserDataException { return __CONTEXT__.getProject().file(o, pathValidation); }
//	public URI uri(Object o) { return __CONTEXT__.getProject().uri(o); }
//	public String relativePath(Object o) { return __CONTEXT__.getProject().relativePath(o); }
//	public ConfigurableFileCollection files(Object... objects) { return __CONTEXT__.getProject().files(objects); }
//	public ConfigurableFileCollection files(Object o, Closure closure) { return __CONTEXT__.getProject().files(o, closure); }
//	public ConfigurableFileCollection files(Object o, Action<? super ConfigurableFileCollection> action) { return __CONTEXT__.getProject().files(o, action); }
//	public ConfigurableFileTree fileTree(Object o) { return __CONTEXT__.getProject().fileTree(o); }
//	public ConfigurableFileTree fileTree(Object o, Closure closure) { return __CONTEXT__.getProject().fileTree(o, closure); }
//	public ConfigurableFileTree fileTree(Object o, Action<? super ConfigurableFileTree> action) { return __CONTEXT__.getProject().fileTree(o, action); }
//	public ConfigurableFileTree fileTree(Map<String, ?> map) { return __CONTEXT__.getProject().fileTree(map); }
//	public FileTree zipTree(Object o) { return __CONTEXT__.getProject().zipTree(o); }
//	public FileTree tarTree(Object o) { return __CONTEXT__.getProject().tarTree(o); }
//	public <T> Provider<T> provider(Callable<T> callable) { return __CONTEXT__.getProject().provider(callable); }
//	public ProviderFactory getProviders() { return __CONTEXT__.getProject().getProviders(); }
//	public ObjectFactory getObjects() { return __CONTEXT__.getProject().getObjects(); }
//	public ProjectLayout getLayout() { return __CONTEXT__.getProject().getLayout(); }
//	public File mkdir(Object o) { return __CONTEXT__.getProject().mkdir(o); }
//	public boolean delete(Object... objects) { return __CONTEXT__.getProject().delete(objects); }
//	public WorkResult delete(Action<? super DeleteSpec> action) { return __CONTEXT__.getProject().delete(action); }
//	public ExecResult javaexec(Closure closure) { return __CONTEXT__.getProject().javaexec(closure); }
//	public ExecResult javaexec(Action<? super JavaExecSpec> action) { return __CONTEXT__.getProject().javaexec(action); }
//	public ExecResult exec(Closure closure) { return __CONTEXT__.getProject().exec(closure); }
//	public ExecResult exec(Action<? super ExecSpec> action) { return __CONTEXT__.getProject().exec(action); }
//	public String absoluteProjectPath(String s) { return __CONTEXT__.getProject().absoluteProjectPath(s); }
//	public String relativeProjectPath(String s) { return __CONTEXT__.getProject().relativeProjectPath(s); }
//	public AntBuilder getAnt() { return __CONTEXT__.getProject().getAnt(); }
//	public AntBuilder createAntBuilder() { return __CONTEXT__.getProject().createAntBuilder(); }
//	public AntBuilder ant(Closure closure) { return __CONTEXT__.getProject().ant(closure); }
//	public AntBuilder ant(Action<? super AntBuilder> action) { return __CONTEXT__.getProject().ant(action); }
	public ConfigurationContainer getConfigurations() { return __CONTEXT__.getProject().getConfigurations(); }
	public void configurations(Closure closure) { __CONTEXT__.getProject().configurations(closure); }
//	public ArtifactHandler getArtifacts() { return __CONTEXT__.getProject().getArtifacts(); }
//	public void artifacts(Closure closure) { __CONTEXT__.getProject().artifacts(closure); }
//	public void artifacts(Action<? super ArtifactHandler> action) { __CONTEXT__.getProject().artifacts(action); }
//	public Convention getConvention() { return __CONTEXT__.getProject().getConvention(); }
//	public int depthCompare(Project project) { return __CONTEXT__.getProject().depthCompare(project); }
//	public int getDepth() { return __CONTEXT__.getProject().getDepth(); }
	public TaskContainer getTasks() { return __CONTEXT__.getProject().getTasks(); }
	public void subprojects(Action<? super Project> action) { __CONTEXT__.getProject().subprojects(action); }
	public void subprojects(Closure closure) { __CONTEXT__.getProject().subprojects(closure); }
	public void allprojects(Action<? super Project> action) { __CONTEXT__.getProject().allprojects(action); }
	public void allprojects(Closure closure) { __CONTEXT__.getProject().allprojects(closure); }
	public void beforeEvaluate(Action<? super Project> action) { __CONTEXT__.getProject().beforeEvaluate(action); }
	public void afterEvaluate(Action<? super Project> action) { __CONTEXT__.getProject().afterEvaluate(action); }
	public void beforeEvaluate(Closure closure) { __CONTEXT__.getProject().beforeEvaluate(closure); }
	public void afterEvaluate(Closure closure) { __CONTEXT__.getProject().afterEvaluate(closure); }
	public boolean hasProperty(String s) { return __CONTEXT__.getProject().hasProperty(s); }
	public Map<String, ?> getProperties() { return __CONTEXT__.getProject().getProperties(); }
	@Nullable public Object property(String s) throws MissingPropertyException { return __CONTEXT__.getProject().property(s); }
	@Nullable public Object findProperty(String s) { return __CONTEXT__.getProject().findProperty(s); }
	public Logger getLogger() { return __CONTEXT__.getProject().getLogger(); }
	public Gradle getGradle() { return __CONTEXT__.getProject().getGradle(); }
	public LoggingManager getLogging() { return __CONTEXT__.getProject().getLogging(); }
	public Object configure(Object o, Closure closure) { return __CONTEXT__.getProject().configure(o, closure); }
	public Iterable<?> configure(Iterable<?> iterable, Closure closure) { return __CONTEXT__.getProject().configure(iterable, closure); }
	public <T> Iterable<T> configure(Iterable<T> iterable, Action<? super T> action) { return __CONTEXT__.getProject().configure(iterable, action); }
//	public RepositoryHandler getRepositories() { return __CONTEXT__.getProject().getRepositories(); }
//	public void repositories(Closure closure) { __CONTEXT__.getProject().repositories(closure); }
//	public DependencyHandler getDependencies() { return __CONTEXT__.getProject().getDependencies(); }
//	public void dependencies(Closure closure) { __CONTEXT__.getProject().dependencies(closure); }
//	public ScriptHandler getBuildscript() { return __CONTEXT__.getProject().getBuildscript(); }
//	public void buildscript(Closure closure) { __CONTEXT__.getProject().buildscript(closure); }
//	public WorkResult copy(Closure closure) { return __CONTEXT__.getProject().copy(closure); }
//	public WorkResult copy(Action<? super CopySpec> action) { return __CONTEXT__.getProject().copy(action); }
//	public CopySpec copySpec(Closure closure) { return __CONTEXT__.getProject().copySpec(closure); }
//	public CopySpec copySpec(Action<? super CopySpec> action) { return __CONTEXT__.getProject().copySpec(action); }
//	public CopySpec copySpec() { return __CONTEXT__.getProject().copySpec(); }
//	public WorkResult sync(Action<? super CopySpec> action) { return __CONTEXT__.getProject().sync(action); }
//	public ProjectState getState() { return __CONTEXT__.getProject().getState(); }
//	public <T> NamedDomainObjectContainer<T> container(Class<T> aClass) { return __CONTEXT__.getProject().container(aClass); }
//	public <T> NamedDomainObjectContainer<T> container(Class<T> aClass, NamedDomainObjectFactory<T> namedDomainObjectFactory) { return __CONTEXT__.getProject().container(aClass, namedDomainObjectFactory); }
//	public <T> NamedDomainObjectContainer<T> container(Class<T> aClass, Closure closure) { return __CONTEXT__.getProject().container(aClass, closure); }
//	public ExtensionContainer getExtensions() { return __CONTEXT__.getProject().getExtensions(); }
//	public ResourceHandler getResources() { return __CONTEXT__.getProject().getResources(); }
//	public SoftwareComponentContainer getComponents() { return __CONTEXT__.getProject().getComponents(); }
//	public InputNormalizationHandler getNormalization() { return __CONTEXT__.getProject().getNormalization(); }
//	public void normalization(Action<? super InputNormalizationHandler> action) { __CONTEXT__.getProject().normalization(action); }
//	public void dependencyLocking(Action<? super DependencyLockingHandler> action) { __CONTEXT__.getProject().dependencyLocking(action); }
//	public DependencyLockingHandler getDependencyLocking() { return __CONTEXT__.getProject().getDependencyLocking(); }
//	public int compareTo(@NotNull Project o) { return __CONTEXT__.getProject().depthCompare(o); }
//	public PluginContainer getPlugins() { return __CONTEXT__.getProject().getPlugins(); }
//	public void apply(Closure closure) { __CONTEXT__.getProject().apply(closure); }
//	public void apply(Action<? super ObjectConfigurationAction> action) { __CONTEXT__.getProject().apply(action); }
//	public void apply(Map<String, ?> map) { __CONTEXT__.getProject().apply(map); }
//	public PluginManager getPluginManager() { return __CONTEXT__.getProject().getPluginManager(); }
}
