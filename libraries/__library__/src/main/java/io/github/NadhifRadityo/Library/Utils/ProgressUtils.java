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
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pend(Object identifier) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_pend")).call(identifier);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pend() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_pend")).call();
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pend(Object identifier, String status, boolean failed) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_pend")).call(identifier, status, failed);
	}
	public static void pend(Object identifier) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("pend")).call(identifier);
	}
	public static void pend() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("pend")).call();
	}
	public static void pend(Object identifier, String status, boolean failed) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("pend")).call(identifier, status, failed);
	}
	public static ProgressLogger __INTERNAL_Gradle$Strategies$ProgressUtils_progressInstance(Object identifier) {
		return ((Closure<ProgressLogger>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_progressInstance")).call(identifier);
	}
	public static ProgressLogger __INTERNAL_Gradle$Strategies$ProgressUtils_progressInstance() {
		return ((Closure<ProgressLogger>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_progressInstance")).call();
	}
	public static ProgressLogger progressInstance(Object identifier) {
		return ((Closure<ProgressLogger>) ((GroovyObject) getContext().getThat()).getProperty("progressInstance")).call(identifier);
	}
	public static ProgressLogger progressInstance() {
		return ((Closure<ProgressLogger>) ((GroovyObject) getContext().getThat()).getProperty("progressInstance")).call();
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_category() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_category");
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ProgressUtils> get__INTERNAL_Gradle$Strategies$ProgressUtils_cache() {
		return (Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.Strategies.ProgressUtils>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_cache");
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pstart(Object identifier) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_pstart")).call(identifier);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pstart() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_pstart")).call();
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pstart(Object identifier, String status) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_pstart")).call(identifier, status);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pstart(Object identifier, String description, String status) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_pstart")).call(identifier, description, status);
	}
	public static void pstart(Object identifier) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("pstart")).call(identifier);
	}
	public static void pstart() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("pstart")).call();
	}
	public static void pstart(Object identifier, String status) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("pstart")).call(identifier, status);
	}
	public static void pstart(Object identifier, String description, String status) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("pstart")).call(identifier, description, status);
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_previous() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_previous");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationId() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationId");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_state() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_state");
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper __INTERNAL_Gradle$Strategies$ProgressUtils_progress(Object identifier) {
		return ((Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_progress")).call(identifier);
	}
	public static Gradle.Strategies.ProgressUtils.ProgressWrapper progress(Object identifier) {
		return ((Closure<Gradle.Strategies.ProgressUtils.ProgressWrapper>) ((GroovyObject) getContext().getThat()).getProperty("progress")).call(identifier);
	}
	public static int __INTERNAL_Gradle$Strategies$ProgressUtils_hashCode() {
		return ((Closure<Integer>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_hashCode")).call();
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate(Object identifier, Class<?> progressCategory) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate")).call(identifier, progressCategory);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate(Object identifier, Class<?> progressCategory, ProgressLogger progressLogger) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate")).call(identifier, progressCategory, progressLogger);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate(Object identifier, Class<?> progressCategory, BuildOperationDescriptor buildOperationDescriptor) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate")).call(identifier, progressCategory, buildOperationDescriptor);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate(Object identifier, String progressCategory) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_progressCreate")).call(identifier, progressCategory);
	}
	public static void progressCreate(Object identifier, Class<?> progressCategory) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("progressCreate")).call(identifier, progressCategory);
	}
	public static void progressCreate(Object identifier, Class<?> progressCategory, ProgressLogger progressLogger) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("progressCreate")).call(identifier, progressCategory, progressLogger);
	}
	public static void progressCreate(Object identifier, Class<?> progressCategory, BuildOperationDescriptor buildOperationDescriptor) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("progressCreate")).call(identifier, progressCategory, buildOperationDescriptor);
	}
	public static void progressCreate(Object identifier, String progressCategory) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("progressCreate")).call(identifier, progressCategory);
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_listener() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_listener");
	}
	public static ProgressLoggerFactory get__INTERNAL_Gradle$Strategies$ProgressUtils_factory() {
		return (ProgressLoggerFactory) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_factory");
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_destruct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_destruct")).call();
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils___on_start(Object identifier, Function1<? super ProgressLogger, Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils___on_start")).call(identifier, callback);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils___on_do(Object identifier, Function1<? super ProgressLogger, Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils___on_do")).call(identifier, callback);
	}
	public static ThreadLocal<LinkedList<Object>> get__INTERNAL_Gradle$Strategies$ProgressUtils_stack() {
		return (ThreadLocal<LinkedList<Object>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_stack");
	}
	public static String __INTERNAL_Gradle$Strategies$ProgressUtils_progress_id(Object... objects) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_progress_id")).call(objects);
	}
	public static String progress_id(Object... objects) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("progress_id")).call(objects);
	}
	public static boolean __INTERNAL_Gradle$Strategies$ProgressUtils_equals(Object other) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_equals")).call(other);
	}
	public static String __INTERNAL_Gradle$Strategies$ProgressUtils_toString() {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_toString")).call();
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils___on_end(Object identifier, Function1<? super ProgressLogger, Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils___on_end")).call(identifier, callback);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pdo(Object identifier, String status) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_pdo")).call(identifier, status);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_pdo(Object identifier, String status, boolean failing) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_pdo")).call(identifier, status, failing);
	}
	public static void pdo(Object identifier, String status) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("pdo")).call(identifier, status);
	}
	public static void pdo(Object identifier, String status, boolean failing) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("pdo")).call(identifier, status, failing);
	}
	public static Map<Integer, ProgressLogger> get__INTERNAL_Gradle$Strategies$ProgressUtils_instances() {
		return (Map<Integer, ProgressLogger>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_instances");
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_progressDestroy(Object identifier) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_progressDestroy")).call(identifier);
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_progressDestroy() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_progressDestroy")).call();
	}
	public static void progressDestroy(Object identifier) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("progressDestroy")).call(identifier);
	}
	public static void progressDestroy() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("progressDestroy")).call();
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_loggingHeader() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_loggingHeader");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_clock() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_clock");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parent() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parent");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_totalProgress() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_totalProgress");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationStart() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationStart");
	}
	public static void __INTERNAL_Gradle$Strategies$ProgressUtils_construct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_construct")).call();
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parentBuildOperationId() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parentBuildOperationId");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_description() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_description");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationCategory() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationCategory");
	}
	public static long get__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_progressOperationId() {
		return (Long) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$Strategies$ProgressUtils_AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_progressOperationId");
	}
}
