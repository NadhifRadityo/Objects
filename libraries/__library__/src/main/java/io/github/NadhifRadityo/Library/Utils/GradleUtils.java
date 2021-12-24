package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.util.Collection;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.initialization.IncludedBuild;
import org.gradle.kotlin.dsl.support.DefaultKotlinScript;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class GradleUtils {
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_isDaemonProbablyUnstable(Object project) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_isDaemonProbablyUnstable")).call(project);
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_isDaemonProbablyUnstable() {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_isDaemonProbablyUnstable")).call();
	}
	public static boolean isDaemonProbablyUnstable(Object project) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("isDaemonProbablyUnstable")).call(project);
	}
	public static boolean isDaemonProbablyUnstable() {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("isDaemonProbablyUnstable")).call();
	}
	public static void __INTERNAL_Gradle$Strategies$GradleUtils_runAfterAnotherTask(Collection<?> tasks, Object project) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_runAfterAnotherTask")).call(tasks, project);
	}
	public static void __INTERNAL_Gradle$Strategies$GradleUtils_runAfterAnotherTask(Collection<?> tasks) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_runAfterAnotherTask")).call(tasks);
	}
	public static void runAfterAnotherTask(Collection<?> tasks, Object project) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("runAfterAnotherTask")).call(tasks, project);
	}
	public static void runAfterAnotherTask(Collection<?> tasks) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("runAfterAnotherTask")).call(tasks);
	}
	public static Project __INTERNAL_Gradle$Strategies$GradleUtils_asProject(Object project) {
		return ((Closure<Project>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_asProject")).call(project);
	}
	public static Project __INTERNAL_Gradle$Strategies$GradleUtils_asProject() {
		return ((Closure<Project>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_asProject")).call();
	}
	public static Project asProject(Object project) {
		return ((Closure<Project>) ((GroovyObject) getContext().getThat()).getProperty("asProject")).call(project);
	}
	public static Project asProject() {
		return ((Closure<Project>) ((GroovyObject) getContext().getThat()).getProperty("asProject")).call();
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_equals(Object other) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_equals")).call(other);
	}
	public static <T> void __INTERNAL_Gradle$Strategies$GradleUtils_setGlobalExt(String key, T obj, Object project) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_setGlobalExt")).call(key, obj, project);
	}
	public static <T> void __INTERNAL_Gradle$Strategies$GradleUtils_setGlobalExt(String key, T obj) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_setGlobalExt")).call(key, obj);
	}
	public static <T> void setGlobalExt(String key, T obj, Object project) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("setGlobalExt")).call(key, obj, project);
	}
	public static <T> void setGlobalExt(String key, T obj) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("setGlobalExt")).call(key, obj);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$GradleUtils_getGlobalExt(String key, Object project) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_getGlobalExt")).call(key, project);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$GradleUtils_getGlobalExt(String key) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_getGlobalExt")).call(key);
	}
	public static <T> T getGlobalExt(String key, Object project) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("getGlobalExt")).call(key, project);
	}
	public static <T> T getGlobalExt(String key) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("getGlobalExt")).call(key);
	}
	public static void __INTERNAL_Gradle$Strategies$GradleUtils_forwardTask(Object project, Function1<? super Task, Boolean> filter, Function1<? super Task, Unit> exec) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_forwardTask")).call(project, filter, exec);
	}
	public static void __INTERNAL_Gradle$Strategies$GradleUtils_forwardTask(Object project, Function1<? super Task, Boolean> filter) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_forwardTask")).call(project, filter);
	}
	public static void forwardTask(Object project, Function1<? super Task, Boolean> filter, Function1<? super Task, Unit> exec) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("forwardTask")).call(project, filter, exec);
	}
	public static void forwardTask(Object project, Function1<? super Task, Boolean> filter) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("forwardTask")).call(project, filter);
	}
	public static void __INTERNAL_Gradle$Strategies$GradleUtils_construct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_construct")).call();
	}
	public static String __INTERNAL_Gradle$Strategies$GradleUtils_toString() {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_toString")).call();
	}
	public static Project __INTERNAL_Gradle$Strategies$GradleUtils_asBuildProject(IncludedBuild build) {
		return ((Closure<Project>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_asBuildProject")).call(build);
	}
	public static Project asBuildProject(IncludedBuild build) {
		return ((Closure<Project>) ((GroovyObject) getContext().getThat()).getProperty("asBuildProject")).call(build);
	}
	public static org.gradle.api.invocation.Gradle __INTERNAL_Gradle$Strategies$GradleUtils_asGradle(Object project) {
		return ((Closure<org.gradle.api.invocation.Gradle>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_asGradle")).call(project);
	}
	public static org.gradle.api.invocation.Gradle __INTERNAL_Gradle$Strategies$GradleUtils_asGradle() {
		return ((Closure<org.gradle.api.invocation.Gradle>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_asGradle")).call();
	}
	public static org.gradle.api.invocation.Gradle asGradle(Object project) {
		return ((Closure<org.gradle.api.invocation.Gradle>) ((GroovyObject) getContext().getThat()).getProperty("asGradle")).call(project);
	}
	public static org.gradle.api.invocation.Gradle asGradle() {
		return ((Closure<org.gradle.api.invocation.Gradle>) ((GroovyObject) getContext().getThat()).getProperty("asGradle")).call();
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_hasGlobalExt(String key, Object project) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_hasGlobalExt")).call(key, project);
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_hasGlobalExt(String key) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_hasGlobalExt")).call(key);
	}
	public static boolean hasGlobalExt(String key, Object project) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("hasGlobalExt")).call(key, project);
	}
	public static boolean hasGlobalExt(String key) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("hasGlobalExt")).call(key);
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.GradleUtils> get__INTERNAL_Gradle$Strategies$GradleUtils_cache() {
		return (Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.GradleUtils>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_cache");
	}
	public static <T> T __INTERNAL_Gradle$Strategies$GradleUtils_asService(Class<T> clazz, Object project) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_asService")).call(clazz, project);
	}
	public static <T> T __INTERNAL_Gradle$Strategies$GradleUtils_asService(Class<T> clazz) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_asService")).call(clazz);
	}
	public static <T> T asService(Class<T> clazz, Object project) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("asService")).call(clazz, project);
	}
	public static <T> T asService(Class<T> clazz) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("asService")).call(clazz);
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_isSingleUseDaemon(Object project) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_isSingleUseDaemon")).call(project);
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_isSingleUseDaemon() {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_isSingleUseDaemon")).call();
	}
	public static boolean isSingleUseDaemon(Object project) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("isSingleUseDaemon")).call(project);
	}
	public static boolean isSingleUseDaemon() {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("isSingleUseDaemon")).call();
	}
	public static int __INTERNAL_Gradle$Strategies$GradleUtils_hashCode() {
		return ((Closure<Integer>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_hashCode")).call();
	}
	public static Task __INTERNAL_Gradle$Strategies$GradleUtils_asTask(Object task, Object project) {
		return ((Closure<Task>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_asTask")).call(task, project);
	}
	public static Task __INTERNAL_Gradle$Strategies$GradleUtils_asTask(Object task) {
		return ((Closure<Task>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_asTask")).call(task);
	}
	public static Task asTask(Object task, Object project) {
		return ((Closure<Task>) ((GroovyObject) getContext().getThat()).getProperty("asTask")).call(task, project);
	}
	public static Task asTask(Object task) {
		return ((Closure<Task>) ((GroovyObject) getContext().getThat()).getProperty("asTask")).call(task);
	}
	public static void __INTERNAL_Gradle$Strategies$GradleUtils_destruct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_destruct")).call();
	}
	public static <T> T __INTERNAL_Gradle$Strategies$GradleUtils_kotlinScriptHostTarget(DefaultKotlinScript script) {
		return ((Closure<T>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_kotlinScriptHostTarget")).call(script);
	}
	public static boolean __INTERNAL_Gradle$Strategies$GradleUtils_isRunningOnDebug() {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$GradleUtils_isRunningOnDebug")).call();
	}
	public static boolean isRunningOnDebug() {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("isRunningOnDebug")).call();
	}
}
