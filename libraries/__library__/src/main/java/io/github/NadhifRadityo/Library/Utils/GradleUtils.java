package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.util.Collection;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.initialization.IncludedBuild;
import org.gradle.kotlin.dsl.support.DefaultKotlinScript;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class GradleUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$GradleUtils", "std$GradleUtils");
	}
	protected static void __UTILS_DESTRUCT__() {
		if(__UTILS_IMPORTED__ != null)
			__UTILS_IMPORTED__ = null;
	}
	protected static <T> T __UTILS_GET_PROPERTY__(String property) {
		return (T) __UTILS_IMPORTED__.getProperty(property);
	}
	protected static <T> void __UTILS_SET_PROPERTY__(String property, T value) {
		__UTILS_IMPORTED__.setProperty(property, value);
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_isDaemonProbablyUnstable(Object project) {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_isDaemonProbablyUnstable").call(project);
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_isDaemonProbablyUnstable() {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_isDaemonProbablyUnstable").call();
	}
	public static boolean isDaemonProbablyUnstable(Object project) {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("isDaemonProbablyUnstable").call(project);
	}
	public static boolean isDaemonProbablyUnstable() {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("isDaemonProbablyUnstable").call();
	}
	public static void __INTERNAL_Gradle$Strategies$GradleUtils_runAfterAnotherTask(Collection<?> tasks, Object project) {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_runAfterAnotherTask").call(tasks, project);
	}
	public static void __INTERNAL_Gradle$Strategies$GradleUtils_runAfterAnotherTask(Collection<?> tasks) {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_runAfterAnotherTask").call(tasks);
	}
	public static void runAfterAnotherTask(Collection<?> tasks, Object project) {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("runAfterAnotherTask").call(tasks, project);
	}
	public static void runAfterAnotherTask(Collection<?> tasks) {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("runAfterAnotherTask").call(tasks);
	}
	public static Project __INTERNAL_Gradle$Strategies$GradleUtils_asProject(Object project) {
		return GradleUtils.<Closure<Project>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_asProject").call(project);
	}
	public static Project __INTERNAL_Gradle$Strategies$GradleUtils_asProject() {
		return GradleUtils.<Closure<Project>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_asProject").call();
	}
	public static Project asProject(Object project) {
		return GradleUtils.<Closure<Project>>__UTILS_GET_PROPERTY__("asProject").call(project);
	}
	public static Project asProject() {
		return GradleUtils.<Closure<Project>>__UTILS_GET_PROPERTY__("asProject").call();
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_equals(Object other) {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_equals").call(other);
	}
	public static <T> void __INTERNAL_Gradle$Strategies$GradleUtils_setGlobalExt(String key, T obj, Object project) {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_setGlobalExt").call(key, obj, project);
	}
	public static <T> void __INTERNAL_Gradle$Strategies$GradleUtils_setGlobalExt(String key, T obj) {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_setGlobalExt").call(key, obj);
	}
	public static <T> void setGlobalExt(String key, T obj, Object project) {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("setGlobalExt").call(key, obj, project);
	}
	public static <T> void setGlobalExt(String key, T obj) {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("setGlobalExt").call(key, obj);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$GradleUtils_getGlobalExt(String key, Object project) {
		return GradleUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_getGlobalExt").call(key, project);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$GradleUtils_getGlobalExt(String key) {
		return GradleUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_getGlobalExt").call(key);
	}
	public static <T> T getGlobalExt(String key, Object project) {
		return GradleUtils.<Closure<T>>__UTILS_GET_PROPERTY__("getGlobalExt").call(key, project);
	}
	public static <T> T getGlobalExt(String key) {
		return GradleUtils.<Closure<T>>__UTILS_GET_PROPERTY__("getGlobalExt").call(key);
	}
	public static void __INTERNAL_Gradle$Strategies$GradleUtils_forwardTask(Object project, Function1<? super Task, Boolean> filter, Function1<? super Task, Unit> exec) {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_forwardTask").call(project, filter, exec);
	}
	public static void __INTERNAL_Gradle$Strategies$GradleUtils_forwardTask(Object project, Function1<? super Task, Boolean> filter) {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_forwardTask").call(project, filter);
	}
	public static void forwardTask(Object project, Function1<? super Task, Boolean> filter, Function1<? super Task, Unit> exec) {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("forwardTask").call(project, filter, exec);
	}
	public static void forwardTask(Object project, Function1<? super Task, Boolean> filter) {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("forwardTask").call(project, filter);
	}
	public static void __INTERNAL_Gradle$Strategies$GradleUtils_construct() {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_construct").call();
	}
	public static String __INTERNAL_Gradle$Strategies$GradleUtils_toString() {
		return GradleUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_toString").call();
	}
	public static Project __INTERNAL_Gradle$Strategies$GradleUtils_asBuildProject(IncludedBuild build) {
		return GradleUtils.<Closure<Project>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_asBuildProject").call(build);
	}
	public static Project asBuildProject(IncludedBuild build) {
		return GradleUtils.<Closure<Project>>__UTILS_GET_PROPERTY__("asBuildProject").call(build);
	}
	public static org.gradle.api.invocation.Gradle __INTERNAL_Gradle$Strategies$GradleUtils_asGradle(Object project) {
		return GradleUtils.<Closure<org.gradle.api.invocation.Gradle>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_asGradle").call(project);
	}
	public static org.gradle.api.invocation.Gradle __INTERNAL_Gradle$Strategies$GradleUtils_asGradle() {
		return GradleUtils.<Closure<org.gradle.api.invocation.Gradle>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_asGradle").call();
	}
	public static org.gradle.api.invocation.Gradle asGradle(Object project) {
		return GradleUtils.<Closure<org.gradle.api.invocation.Gradle>>__UTILS_GET_PROPERTY__("asGradle").call(project);
	}
	public static org.gradle.api.invocation.Gradle asGradle() {
		return GradleUtils.<Closure<org.gradle.api.invocation.Gradle>>__UTILS_GET_PROPERTY__("asGradle").call();
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_hasGlobalExt(String key, Object project) {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_hasGlobalExt").call(key, project);
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_hasGlobalExt(String key) {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_hasGlobalExt").call(key);
	}
	public static boolean hasGlobalExt(String key, Object project) {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("hasGlobalExt").call(key, project);
	}
	public static boolean hasGlobalExt(String key) {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("hasGlobalExt").call(key);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.GradleUtils> get__INTERNAL_Gradle$Strategies$GradleUtils_cache() {
		return GradleUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.GradleUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_cache");
	}
	public static <T> T __INTERNAL_Gradle$Strategies$GradleUtils_asService(Class<T> clazz, Object project) {
		return GradleUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_asService").call(clazz, project);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$GradleUtils_asService(Class<T> clazz) {
		return GradleUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_asService").call(clazz);
	}
	public static <T> T asService(Class<T> clazz, Object project) {
		return GradleUtils.<Closure<T>>__UTILS_GET_PROPERTY__("asService").call(clazz, project);
	}
	public static <T> T asService(Class<T> clazz) {
		return GradleUtils.<Closure<T>>__UTILS_GET_PROPERTY__("asService").call(clazz);
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_isSingleUseDaemon(Object project) {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_isSingleUseDaemon").call(project);
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_isSingleUseDaemon() {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_isSingleUseDaemon").call();
	}
	public static boolean isSingleUseDaemon(Object project) {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("isSingleUseDaemon").call(project);
	}
	public static boolean isSingleUseDaemon() {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("isSingleUseDaemon").call();
	}
	public static int __INTERNAL_Gradle$Strategies$GradleUtils_hashCode() {
		return GradleUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_hashCode").call();
	}
	public static Dependency __INTERNAL_Gradle$Strategies$GradleUtils_gradleKotlinDsl(Object project) {
		return GradleUtils.<Closure<Dependency>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_gradleKotlinDsl").call(project);
	}
	public static Dependency __INTERNAL_Gradle$Strategies$GradleUtils_gradleKotlinDsl() {
		return GradleUtils.<Closure<Dependency>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_gradleKotlinDsl").call();
	}
	public static Dependency gradleKotlinDsl(Object project) {
		return GradleUtils.<Closure<Dependency>>__UTILS_GET_PROPERTY__("gradleKotlinDsl").call(project);
	}
	public static Dependency gradleKotlinDsl() {
		return GradleUtils.<Closure<Dependency>>__UTILS_GET_PROPERTY__("gradleKotlinDsl").call();
	}
	public static Task __INTERNAL_Gradle$Strategies$GradleUtils_asTask(Object task, Object project) {
		return GradleUtils.<Closure<Task>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_asTask").call(task, project);
	}
	public static Task __INTERNAL_Gradle$Strategies$GradleUtils_asTask(Object task) {
		return GradleUtils.<Closure<Task>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_asTask").call(task);
	}
	public static Task asTask(Object task, Object project) {
		return GradleUtils.<Closure<Task>>__UTILS_GET_PROPERTY__("asTask").call(task, project);
	}
	public static Task asTask(Object task) {
		return GradleUtils.<Closure<Task>>__UTILS_GET_PROPERTY__("asTask").call(task);
	}
	public static void __INTERNAL_Gradle$Strategies$GradleUtils_destruct() {
		GradleUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_destruct").call();
	}
	public static <T> T __INTERNAL_Gradle$Strategies$GradleUtils_kotlinScriptHostTarget(DefaultKotlinScript script) {
		return GradleUtils.<Closure<T>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_kotlinScriptHostTarget").call(script);
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_isRunningOnDebug() {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$GradleUtils_isRunningOnDebug").call();
	}
	public static boolean isRunningOnDebug() {
		return GradleUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("isRunningOnDebug").call();
	}
}
