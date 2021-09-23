package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import org.gradle.internal.logging.progress.ProgressLogger;
import org.gradle.internal.operations.BuildOperationDescriptor;

import static io.github.NadhifRadityo.Library.LibraryEntry.getProject;

public class ProgressUtils {
	public static void progress_create(Object... args) {
		Closure<Void> progress_create = (Closure<Void>) getProject().findProperty("ext_common$progress_init");
		progress_create.call(args);
	}
	public static void progressCreate(Object identifier, String loggerCategory) {
		progress_create(identifier, loggerCategory);
	}
	public static void progressCreate(Object identifier, Class<?> loggerCategory) {
		progress_create(identifier, loggerCategory);
	}
	public static void progressCreate(Object identifier, Class<?> loggerCategory, BuildOperationDescriptor buildOperationDescriptor) {
		progress_create(identifier, loggerCategory, buildOperationDescriptor);
	}
	public static void progressCreate(Object identifier, Class<?> loggerCategory, ProgressLogger parent) {
		progress_create(identifier, loggerCategory, parent);
	}
	public static void progressCreate(String loggerCategory) {
		progress_create(null, loggerCategory);
	}
	public static void progressCreate(Class<?> loggerCategory) {
		progress_create(null, loggerCategory);
	}
	public static void progressCreate(Class<?> loggerCategory, BuildOperationDescriptor buildOperationDescriptor) {
		progress_create(null, loggerCategory, buildOperationDescriptor);
	}
	public static void progressCreate(Class<?> loggerCategory, ProgressLogger parent) {
		progress_create(null, loggerCategory, parent);
	}

	public static void progress_destroy(Object... args) {
		Closure<Void> progress_destroy = (Closure<Void>) getProject().findProperty("ext_common$progress_destroy");
		progress_destroy.call(args);
	}
	public static void progressDestroy() {
		progress_destroy();
	}

	public static ProgressLogger progress_instance(Object... args) {
		Closure<ProgressLogger> progress_instance = (Closure<ProgressLogger>) getProject().findProperty("ext_common$progress_instance");
		return progress_instance.call(args);
	}
	public static ProgressLogger progressInstance(Object identifier) {
		return progress_instance(identifier);
	}
	public static ProgressLogger progressInstance() {
		return progress_instance((Object) null);
	}

	public static void progress_start(Object... args) {
		Closure<Void> progress_start = (Closure<Void>) getProject().findProperty("ext_common$progress_start");
		progress_start.call(args);
	}
	public static void progressStart(Object identifier, String description, String status) {
		progress_start(identifier, description, status);
	}
	public static void progressStart(Object identifier, String status) {
		progress_start(identifier, status);
	}
	public static void progressStart(Object identifier) {
		progress_start(identifier, new Object[0]);
	}
	public static void progressStart(String description, String status) {
		progress_start(null, description, status);
	}
	public static void progressStart(String status) {
		progress_start(null, status);
	}
	public static void progressStart() {
		progress_start(null, new Object[0]);
	}

	public static void progress_do(Object... args) {
		Closure<Void> progress_do = (Closure<Void>) getProject().findProperty("ext_common$progress_do");
		progress_do.call(args);
	}
	public static void progressDo(Object identifier, String status) {
		progress_do(identifier, status);
	}
	public static void progressDo(Object identifier, String status, boolean failing) {
		progress_do(identifier, status, failing);
	}
	public static void progressDo(String status) {
		progress_do(null, status);
	}
	public static void progressDo(String status, boolean failing) {
		progress_do(null, status, failing);
	}

	public static void progress_end(Object... args) {
		Closure<Void> progress_end = (Closure<Void>) getProject().findProperty("ext_common$progress_end");
		progress_end.call(args);
	}
	public static void progressEnd(Object identifier) {
		progress_end(identifier, new Object[0]);
	}
	public static void progressEnd(Object identifier, String status, boolean failed) {
		progress_end(identifier, status, failed);
	}
	public static void progressEnd() {
		progress_end(null, new Object[0]);
	}
	public static void progressEnd(String status, boolean failed) {
		progress_end(null, status, failed);
	}
}
