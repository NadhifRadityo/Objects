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

import static io.github.NadhifRadityo.Library.LibraryEntry.__MODULES__;
import static io.github.NadhifRadityo.Library.LibraryEntry.__PROJECT__;
import static io.github.NadhifRadityo.Library.Utils.classForName;
import static io.github.NadhifRadityo.Library.Utils.file;

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

	public Project getRootProject() { return __PROJECT__.getRootProject(); }
	public File getRootDir() { return __PROJECT__.getRootDir(); }
	public File getBuildDir() { return __PROJECT__.getBuildDir(); }
	public void setBuildDir(File file) { __PROJECT__.setBuildDir(file); }
	public void setBuildDir(Object o) { __PROJECT__.setBuildDir(o); }
	public File getBuildFile() { return __PROJECT__.getBuildFile(); }
	@Nullable public Project getParent() { return __PROJECT__.getParent(); }
	public String getName() { return __PROJECT__.getName(); }
//	public String getDisplayName() { return __PROJECT__.getDisplayName(); }
	@Nullable public String getDescription() { return __PROJECT__.getDescription(); }
	public void setDescription(@Nullable String s) { __PROJECT__.setDescription(s); }
//	public Object getGroup() { return __PROJECT__.getGroup(); }
//	public void setGroup(Object o) { __PROJECT__.setGroup(o); }
//	public Object getVersion() { return __PROJECT__.getVersion(); }
//	public void setVersion(Object o) { __PROJECT__.setVersion(o); }
//	public Object getStatus() { return __PROJECT__.getStatus(); }
//	public void setStatus(Object o) { __PROJECT__.setStatus(o); }
	public Map<String, Project> getChildProjects() { return __PROJECT__.getChildProjects(); }
	public void setProperty(String s, @Nullable Object o) throws MissingPropertyException { __PROJECT__.setProperty(s, o); }
	public Project getProject() { return __PROJECT__.getProject(); }
	public Set<Project> getAllprojects() { return __PROJECT__.getAllprojects(); }
	public Set<Project> getSubprojects() { return __PROJECT__.getSubprojects(); }
	public Task task(String s) throws InvalidUserDataException { return __PROJECT__.task(s); }
	public Task task(Map<String, ?> map, String s) throws InvalidUserDataException { return __PROJECT__.task(map, s); }
	public Task task(Map<String, ?> map, String s, Closure closure) { return __PROJECT__.task(map, s, closure); }
	public Task task(String s, Closure closure) { return __PROJECT__.task(s, closure); }
	public Task task(String s, Action<? super Task> action) { return __PROJECT__.task(s, action); }
//	public String getPath() { return __PROJECT__.getPath(); }
	public List<String> getDefaultTasks() { return __PROJECT__.getDefaultTasks(); }
	public void setDefaultTasks(List<String> list) { __PROJECT__.setDefaultTasks(list); }
	public void defaultTasks(String... strings) { __PROJECT__.defaultTasks(strings); }
	public Project evaluationDependsOn(String s) throws UnknownProjectException { return __PROJECT__.evaluationDependsOn(s); }
	public void evaluationDependsOnChildren() { __PROJECT__.evaluationDependsOnChildren(); }
	@Nullable public Project findProject(String s) { return __PROJECT__.findProject(s); }
	public Project project(String s) throws UnknownProjectException { return __PROJECT__.project(s); }
	public Project project(String s, Closure closure) { return __PROJECT__.project(s, closure); }
	public Project project(String s, Action<? super Project> action) { return __PROJECT__.project(s, action); }
	public Map<Project, Set<Task>> getAllTasks(boolean b) { return __PROJECT__.getAllTasks(b); }
	public Set<Task> getTasksByName(String s, boolean b) { return __PROJECT__.getTasksByName(s, b); }
	public File getProjectDir() { return __PROJECT__.getProjectDir(); }
//	public File file(Object o) { return __PROJECT__.file(o); }
//	public File file(Object o, PathValidation pathValidation) throws InvalidUserDataException { return __PROJECT__.file(o, pathValidation); }
//	public URI uri(Object o) { return __PROJECT__.uri(o); }
//	public String relativePath(Object o) { return __PROJECT__.relativePath(o); }
//	public ConfigurableFileCollection files(Object... objects) { return __PROJECT__.files(objects); }
//	public ConfigurableFileCollection files(Object o, Closure closure) { return __PROJECT__.files(o, closure); }
//	public ConfigurableFileCollection files(Object o, Action<? super ConfigurableFileCollection> action) { return __PROJECT__.files(o, action); }
//	public ConfigurableFileTree fileTree(Object o) { return __PROJECT__.fileTree(o); }
//	public ConfigurableFileTree fileTree(Object o, Closure closure) { return __PROJECT__.fileTree(o, closure); }
//	public ConfigurableFileTree fileTree(Object o, Action<? super ConfigurableFileTree> action) { return __PROJECT__.fileTree(o, action); }
//	public ConfigurableFileTree fileTree(Map<String, ?> map) { return __PROJECT__.fileTree(map); }
//	public FileTree zipTree(Object o) { return __PROJECT__.zipTree(o); }
//	public FileTree tarTree(Object o) { return __PROJECT__.tarTree(o); }
//	public <T> Provider<T> provider(Callable<T> callable) { return __PROJECT__.provider(callable); }
//	public ProviderFactory getProviders() { return __PROJECT__.getProviders(); }
//	public ObjectFactory getObjects() { return __PROJECT__.getObjects(); }
//	public ProjectLayout getLayout() { return __PROJECT__.getLayout(); }
//	public File mkdir(Object o) { return __PROJECT__.mkdir(o); }
//	public boolean delete(Object... objects) { return __PROJECT__.delete(objects); }
//	public WorkResult delete(Action<? super DeleteSpec> action) { return __PROJECT__.delete(action); }
//	public ExecResult javaexec(Closure closure) { return __PROJECT__.javaexec(closure); }
//	public ExecResult javaexec(Action<? super JavaExecSpec> action) { return __PROJECT__.javaexec(action); }
//	public ExecResult exec(Closure closure) { return __PROJECT__.exec(closure); }
//	public ExecResult exec(Action<? super ExecSpec> action) { return __PROJECT__.exec(action); }
//	public String absoluteProjectPath(String s) { return __PROJECT__.absoluteProjectPath(s); }
//	public String relativeProjectPath(String s) { return __PROJECT__.relativeProjectPath(s); }
//	public AntBuilder getAnt() { return __PROJECT__.getAnt(); }
//	public AntBuilder createAntBuilder() { return __PROJECT__.createAntBuilder(); }
//	public AntBuilder ant(Closure closure) { return __PROJECT__.ant(closure); }
//	public AntBuilder ant(Action<? super AntBuilder> action) { return __PROJECT__.ant(action); }
	public ConfigurationContainer getConfigurations() { return __PROJECT__.getConfigurations(); }
	public void configurations(Closure closure) { __PROJECT__.configurations(closure); }
//	public ArtifactHandler getArtifacts() { return __PROJECT__.getArtifacts(); }
//	public void artifacts(Closure closure) { __PROJECT__.artifacts(closure); }
//	public void artifacts(Action<? super ArtifactHandler> action) { __PROJECT__.artifacts(action); }
//	public Convention getConvention() { return __PROJECT__.getConvention(); }
//	public int depthCompare(Project project) { return __PROJECT__.depthCompare(project); }
//	public int getDepth() { return __PROJECT__.getDepth(); }
	public TaskContainer getTasks() { return __PROJECT__.getTasks(); }
	public void subprojects(Action<? super Project> action) { __PROJECT__.subprojects(action); }
	public void subprojects(Closure closure) { __PROJECT__.subprojects(closure); }
	public void allprojects(Action<? super Project> action) { __PROJECT__.allprojects(action); }
	public void allprojects(Closure closure) { __PROJECT__.allprojects(closure); }
	public void beforeEvaluate(Action<? super Project> action) { __PROJECT__.beforeEvaluate(action); }
	public void afterEvaluate(Action<? super Project> action) { __PROJECT__.afterEvaluate(action); }
	public void beforeEvaluate(Closure closure) { __PROJECT__.beforeEvaluate(closure); }
	public void afterEvaluate(Closure closure) { __PROJECT__.afterEvaluate(closure); }
	public boolean hasProperty(String s) { return __PROJECT__.hasProperty(s); }
	public Map<String, ?> getProperties() { return __PROJECT__.getProperties(); }
	@Nullable public Object property(String s) throws MissingPropertyException { return __PROJECT__.property(s); }
	@Nullable public Object findProperty(String s) { return __PROJECT__.findProperty(s); }
	public Logger getLogger() { return __PROJECT__.getLogger(); }
	public Gradle getGradle() { return __PROJECT__.getGradle(); }
	public LoggingManager getLogging() { return __PROJECT__.getLogging(); }
	public Object configure(Object o, Closure closure) { return __PROJECT__.configure(o, closure); }
	public Iterable<?> configure(Iterable<?> iterable, Closure closure) { return __PROJECT__.configure(iterable, closure); }
	public <T> Iterable<T> configure(Iterable<T> iterable, Action<? super T> action) { return __PROJECT__.configure(iterable, action); }
//	public RepositoryHandler getRepositories() { return __PROJECT__.getRepositories(); }
//	public void repositories(Closure closure) { __PROJECT__.repositories(closure); }
//	public DependencyHandler getDependencies() { return __PROJECT__.getDependencies(); }
//	public void dependencies(Closure closure) { __PROJECT__.dependencies(closure); }
//	public ScriptHandler getBuildscript() { return __PROJECT__.getBuildscript(); }
//	public void buildscript(Closure closure) { __PROJECT__.buildscript(closure); }
//	public WorkResult copy(Closure closure) { return __PROJECT__.copy(closure); }
//	public WorkResult copy(Action<? super CopySpec> action) { return __PROJECT__.copy(action); }
//	public CopySpec copySpec(Closure closure) { return __PROJECT__.copySpec(closure); }
//	public CopySpec copySpec(Action<? super CopySpec> action) { return __PROJECT__.copySpec(action); }
//	public CopySpec copySpec() { return __PROJECT__.copySpec(); }
//	public WorkResult sync(Action<? super CopySpec> action) { return __PROJECT__.sync(action); }
//	public ProjectState getState() { return __PROJECT__.getState(); }
//	public <T> NamedDomainObjectContainer<T> container(Class<T> aClass) { return __PROJECT__.container(aClass); }
//	public <T> NamedDomainObjectContainer<T> container(Class<T> aClass, NamedDomainObjectFactory<T> namedDomainObjectFactory) { return __PROJECT__.container(aClass, namedDomainObjectFactory); }
//	public <T> NamedDomainObjectContainer<T> container(Class<T> aClass, Closure closure) { return __PROJECT__.container(aClass, closure); }
//	public ExtensionContainer getExtensions() { return __PROJECT__.getExtensions(); }
//	public ResourceHandler getResources() { return __PROJECT__.getResources(); }
//	public SoftwareComponentContainer getComponents() { return __PROJECT__.getComponents(); }
//	public InputNormalizationHandler getNormalization() { return __PROJECT__.getNormalization(); }
//	public void normalization(Action<? super InputNormalizationHandler> action) { __PROJECT__.normalization(action); }
//	public void dependencyLocking(Action<? super DependencyLockingHandler> action) { __PROJECT__.dependencyLocking(action); }
//	public DependencyLockingHandler getDependencyLocking() { return __PROJECT__.getDependencyLocking(); }
//	public int compareTo(@NotNull Project o) { return __PROJECT__.depthCompare(o); }
//	public PluginContainer getPlugins() { return __PROJECT__.getPlugins(); }
//	public void apply(Closure closure) { __PROJECT__.apply(closure); }
//	public void apply(Action<? super ObjectConfigurationAction> action) { __PROJECT__.apply(action); }
//	public void apply(Map<String, ?> map) { __PROJECT__.apply(map); }
//	public PluginManager getPluginManager() { return __PROJECT__.getPluginManager(); }
}
