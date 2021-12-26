package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.util.LinkedList;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.gradle.internal.logging.progress.ProgressLogger;
import org.gradle.internal.logging.progress.ProgressLoggerFactory;
import org.gradle.internal.operations.BuildOperationDescriptor;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class ProgressUtils {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$ProgressUtils", "std$ProgressUtils");
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
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pend(Object identifier) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_pend").call(identifier);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pend() {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_pend").call();
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pend(Object identifier, String status, boolean failed) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_pend").call(identifier, status, failed);
	}
	public static void pend(Object identifier) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("pend").call(identifier);
	}
	public static void pend() {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("pend").call();
	}
	public static void pend(Object identifier, String status, boolean failed) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("pend").call(identifier, status, failed);
	}
	public static ProgressLogger __INTERNAL_Gradle$Strategies$ProgressUtils_progressInstance(Object identifier) {
		return ProgressUtils.<Closure<ProgressLogger>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_progressInstance").call(identifier);
	}
	public static ProgressLogger __INTERNAL_Gradle$Strategies$ProgressUtils_progressInstance() {
		return ProgressUtils.<Closure<ProgressLogger>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_progressInstance").call();
	}
	public static ProgressLogger progressInstance(Object identifier) {
		return ProgressUtils.<Closure<ProgressLogger>>__UTILS_GET_PROPERTY__("progressInstance").call(identifier);
	}
	public static ProgressLogger progressInstance() {
		return ProgressUtils.<Closure<ProgressLogger>>__UTILS_GET_PROPERTY__("progressInstance").call();
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_category() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_category");
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ProgressUtils> get__INTERNAL_Gradle$Strategies$ProgressUtils_cache() {
		return ProgressUtils.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ProgressUtils>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_cache");
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pstart(Object identifier) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_pstart").call(identifier);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pstart() {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_pstart").call();
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pstart(Object identifier, String status) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_pstart").call(identifier, status);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pstart(Object identifier, String description, String status) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_pstart").call(identifier, description, status);
	}
	public static void pstart(Object identifier) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("pstart").call(identifier);
	}
	public static void pstart() {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("pstart").call();
	}
	public static void pstart(Object identifier, String status) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("pstart").call(identifier, status);
	}
	public static void pstart(Object identifier, String description, String status) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("pstart").call(identifier, description, status);
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_previous() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_previous");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationId() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationId");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_state() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_state");
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper __INTERNAL_Gradle$Strategies$ProgressUtils_progress(Object identifier) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_progress").call(identifier);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper progress(Object identifier) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("progress").call(identifier);
	}
	public static int __INTERNAL_Gradle$Strategies$ProgressUtils_hashCode() {
		return ProgressUtils.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_hashCode").call();
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate(Object identifier, Class<?> progressCategory) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate").call(identifier, progressCategory);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate(Object identifier, Class<?> progressCategory, ProgressLogger progressLogger) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate").call(identifier, progressCategory, progressLogger);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate(Object identifier, Class<?> progressCategory, BuildOperationDescriptor buildOperationDescriptor) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate").call(identifier, progressCategory, buildOperationDescriptor);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate(Object identifier, String progressCategory) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate").call(identifier, progressCategory);
	}
	public static void progressCreate(Object identifier, Class<?> progressCategory) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("progressCreate").call(identifier, progressCategory);
	}
	public static void progressCreate(Object identifier, Class<?> progressCategory, ProgressLogger progressLogger) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("progressCreate").call(identifier, progressCategory, progressLogger);
	}
	public static void progressCreate(Object identifier, Class<?> progressCategory, BuildOperationDescriptor buildOperationDescriptor) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("progressCreate").call(identifier, progressCategory, buildOperationDescriptor);
	}
	public static void progressCreate(Object identifier, String progressCategory) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("progressCreate").call(identifier, progressCategory);
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_listener() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_listener");
	}
	public static ProgressLoggerFactory get__INTERNAL_Gradle$Strategies$ProgressUtils_factory() {
		return ProgressUtils.<ProgressLoggerFactory>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_factory");
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_destruct() {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_destruct").call();
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils___on_start(Object identifier, Function1<? super ProgressLogger, Unit> callback) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils___on_start").call(identifier, callback);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils___on_do(Object identifier, Function1<? super ProgressLogger, Unit> callback) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils___on_do").call(identifier, callback);
	}
	public static ThreadLocal<LinkedList<Object>> get__INTERNAL_Gradle$Strategies$ProgressUtils_stack() {
		return ProgressUtils.<ThreadLocal<LinkedList<Object>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_stack");
	}
	public static String __INTERNAL_Gradle$Strategies$ProgressUtils_progress_id(Object... objects) {
		return ProgressUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_progress_id").call(objects);
	}
	public static String progress_id(Object... objects) {
		return ProgressUtils.<Closure<String>>__UTILS_GET_PROPERTY__("progress_id").call(objects);
	}
	public static boolean __INTERNAL_Gradle$Strategies$ProgressUtils_equals(Object other) {
		return ProgressUtils.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_equals").call(other);
	}
	public static String __INTERNAL_Gradle$Strategies$ProgressUtils_toString() {
		return ProgressUtils.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_toString").call();
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils___on_end(Object identifier, Function1<? super ProgressLogger, Unit> callback) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils___on_end").call(identifier, callback);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pdo(Object identifier, String status) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_pdo").call(identifier, status);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pdo(Object identifier, String status, boolean failing) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_pdo").call(identifier, status, failing);
	}
	public static void pdo(Object identifier, String status) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("pdo").call(identifier, status);
	}
	public static void pdo(Object identifier, String status, boolean failing) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("pdo").call(identifier, status, failing);
	}
	public static Map<Integer, ProgressLogger> get__INTERNAL_Gradle$Strategies$ProgressUtils_instances() {
		return ProgressUtils.<Map<Integer, ProgressLogger>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_instances");
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper __INTERNAL_Gradle$Strategies$ProgressUtils_prog(Object[] id, Class<?> category, String description, boolean inherit, int totalProgress) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_prog").call(id, category, description, inherit, totalProgress);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper __INTERNAL_Gradle$Strategies$ProgressUtils_prog(Object[] id, Class<?> category, String description, boolean inherit) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_prog").call(id, category, description, inherit);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper __INTERNAL_Gradle$Strategies$ProgressUtils_prog(Object[] id, Class<?> category, String description) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_prog").call(id, category, description);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper __INTERNAL_Gradle$Strategies$ProgressUtils_prog(Object[] id, Class<?> category) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_prog").call(id, category);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper __INTERNAL_Gradle$Strategies$ProgressUtils_prog(Object[] id, String category, String description, boolean inherit, int totalProgress) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_prog").call(id, category, description, inherit, totalProgress);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper __INTERNAL_Gradle$Strategies$ProgressUtils_prog(Object[] id, String category, String description, boolean inherit) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_prog").call(id, category, description, inherit);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper __INTERNAL_Gradle$Strategies$ProgressUtils_prog(Object[] id, String category, String description) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_prog").call(id, category, description);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper __INTERNAL_Gradle$Strategies$ProgressUtils_prog(Object[] id, String category) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_prog").call(id, category);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper __INTERNAL_Gradle$Strategies$ProgressUtils_prog(Object... id) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_prog").call(id);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper prog(Object[] id, Class<?> category, String description, boolean inherit, int totalProgress) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("prog").call(id, category, description, inherit, totalProgress);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper prog(Object[] id, Class<?> category, String description, boolean inherit) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("prog").call(id, category, description, inherit);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper prog(Object[] id, Class<?> category, String description) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("prog").call(id, category, description);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper prog(Object[] id, Class<?> category) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("prog").call(id, category);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper prog(Object[] id, String category, String description, boolean inherit, int totalProgress) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("prog").call(id, category, description, inherit, totalProgress);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper prog(Object[] id, String category, String description, boolean inherit) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("prog").call(id, category, description, inherit);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper prog(Object[] id, String category, String description) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("prog").call(id, category, description);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper prog(Object[] id, String category) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("prog").call(id, category);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper prog(Object... id) {
		return ProgressUtils.<Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>>__UTILS_GET_PROPERTY__("prog").call(id);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_progressDestroy(Object identifier) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_progressDestroy").call(identifier);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_progressDestroy() {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_progressDestroy").call();
	}
	public static void progressDestroy(Object identifier) {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("progressDestroy").call(identifier);
	}
	public static void progressDestroy() {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("progressDestroy").call();
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_loggingHeader() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_loggingHeader");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_clock() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_clock");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parent() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parent");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_totalProgress() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_totalProgress");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationStart() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationStart");
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_construct() {
		ProgressUtils.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_construct").call();
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parentBuildOperationId() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parentBuildOperationId");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_description() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_description");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationCategory() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationCategory");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_progressOperationId() {
		return ProgressUtils.<Long>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_progressOperationId");
	}
}
