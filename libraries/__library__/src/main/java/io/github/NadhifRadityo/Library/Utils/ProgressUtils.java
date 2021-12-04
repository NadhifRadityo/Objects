package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import org.gradle.internal.logging.progress.ProgressLogger;
import org.gradle.internal.operations.BuildOperationDescriptor;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.stream.Stream;

import static io.github.NadhifRadityo.Library.LibraryEntry.getProject;
import static io.github.NadhifRadityo.Library.Utils.UnsafeUtils.unsafe;

public class ProgressUtils {
	public static void progress_create(Object... args) {
		Closure<Void> progress_create = (Closure<Void>) getProject().findProperty("__INTERNAL_Progress_progressCreate");
		assert progress_create != null;
		progress_create.call(args);
	}
	public static void progressCreate(Object identifier, String loggerCategory) {
		progress_create(identifier, loggerCategory != null ? loggerCategory : "");
	}
	public static void progressCreate(Object identifier, Class<?> loggerCategory) {
		progress_create(identifier, loggerCategory != null ? loggerCategory : ProgressUtils.class);
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
		Closure<Void> progress_destroy = (Closure<Void>) getProject().findProperty("__INTERNAL_Progress_progressDestroy");
		assert progress_destroy != null;
		progress_destroy.call(args);
	}
	public static void progressDestroy(Object identifier) {
		progress_destroy(identifier);
	}
	public static void progressDestroy() {
		progress_destroy((Object) null);
	}

	public static ProgressLogger progress_instance(Object... args) {
		Closure<ProgressLogger> progress_instance = (Closure<ProgressLogger>) getProject().findProperty("__INTERNAL_Progress_progressInstance");
		assert progress_instance != null;
		return progress_instance.call(args);
	}
	public static ProgressLogger progressInstance(Object identifier) {
		return progress_instance(identifier);
	}
	public static ProgressLogger progressInstance() {
		return progress_instance((Object) null);
	}

	public static void progress_start(Object... args) {
		Closure<Void> progress_start = (Closure<Void>) getProject().findProperty("__INTERNAL_Progress_pstart");
		assert progress_start != null;
		progress_start.call(args);
	}
	public static void progressStart(Object identifier, String description, String status) {
		progress_start(identifier, description, status);
	}
	public static void progressStart(Object identifier, String status) {
		progress_start(identifier, status);
	}
	public static void progressStart(Object identifier) {
		progress_start(identifier);
	}
	public static void progressStart(String description, String status) {
		progress_start(null, description, status);
	}
	public static void progressStart(String status) {
		progress_start(null, status);
	}
	public static void progressStart() {
		progress_start((Object) null);
	}

	public static void progress_do(Object... args) {
		Closure<Void> progress_do = (Closure<Void>) getProject().findProperty("__INTERNAL_Progress_pdo");
		assert progress_do != null;
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
		Closure<Void> progress_end = (Closure<Void>) getProject().findProperty("__INTERNAL_Progress_pend");
		assert progress_end != null;
		progress_end.call(args);
	}
	public static void progressEnd(Object identifier) {
		progress_end(identifier);
	}
	public static void progressEnd(Object identifier, String status, boolean failed) {
		progress_end(identifier, status, failed);
	}
	public static void progressEnd() {
		progress_end((Object) null);
	}
	public static void progressEnd(String status, boolean failed) {
		progress_end(null, status, failed);
	}

	protected static final ThreadLocal<LinkedList<Object>> stack = new ThreadLocal<>();
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_progressOperationId;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_category;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_listener;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_clock;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationStart;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationId;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_parentBuildOperationId;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationCategory;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_previous;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_parent;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_description;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_loggingHeader;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_state;
	protected static final long AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_totalProgress;
	static { try {
		Class<?> CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl = Class.forName("org.gradle.internal.logging.progress.DefaultProgressLoggerFactory$ProgressLoggerImpl");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_progressOperationId = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("progressOperationId");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_category = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("category");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_listener = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("listener");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_clock = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("clock");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationStart = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("buildOperationStart");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationId = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("buildOperationId");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_parentBuildOperationId = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("parentBuildOperationId");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationCategory = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("buildOperationCategory");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_previous = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("previous");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_parent = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("parent");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_description = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("description");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_loggingHeader = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("loggingHeader");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_state = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("state");
		Field FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_totalProgress = CLASS_DefaultProgressLoggerFactory$ProgressLoggerImpl.getDeclaredField("totalProgress");
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_progressOperationId = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_progressOperationId);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_category = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_category);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_listener = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_listener);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_clock = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_clock);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationStart = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationStart);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationId = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationId);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_parentBuildOperationId = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_parentBuildOperationId);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationCategory = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationCategory);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_previous = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_previous);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_parent = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_parent);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_description = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_description);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_loggingHeader = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_loggingHeader);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_state = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_state);
		AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_totalProgress = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_totalProgress);
	} catch(Exception e) { throw new Error(e); } }

	public static ProgressWrapper progress(Object identifier) {
		progressCreate(identifier, (String) null);
		return new ProgressWrapper(identifier);
	}
	public static String progress_id(Object... objects) {
		return String.join(";", Stream.of(objects).map(t -> Integer.toString(System.identityHashCode(t), 16)).toArray(String[]::new));
	}

	public static class ProgressWrapper implements AutoCloseable {
		protected final Object identifier;

		protected ProgressWrapper(Object identifier) {
			this.identifier = identifier;
		}

		public Object getIdentifier() {
			return identifier;
		}
		public ProgressLogger getInstance() {
			return progressInstance(identifier);
		}
		public String getState() {
			return ((Enum<?>) unsafe.getObject(getInstance(), AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_state)).name().toUpperCase();
		}

		public void setCategory(String category) {
			assertNotStarted();
			unsafe.putObject(getInstance(), AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_category, category);
		}
		public void setCategory(Class<?> category) {
			setCategory(category.toString());
		}
		public void setDescription(String description) {
			assertNotStarted();
			unsafe.putObject(getInstance(), AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_description, description);
		}
		public void setLoggingHeader(String loggingHeader) {
			assertNotStarted();
			unsafe.putObject(getInstance(), AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_loggingHeader, loggingHeader);
		}
		public void setTotalProgress(int totalProgress) {
			assertNotStarted();
			unsafe.putInt(getInstance(), AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_totalProgress, totalProgress);
		}

		protected void assertNotStarted() {
			ProgressLogger instance = getInstance();
			String state = getState();
			if(state.equals("STARTED")) {
				throw new IllegalStateException(String.format("This operation (%s) has already been started.", instance));
			} else if(state.equals("COMPLETED")) {
				throw new IllegalStateException(String.format("This operation (%s) has already completed.", instance));
			}
		}
		protected void assertRunning() {
			ProgressLogger instance = getInstance();
			String state = getState();
			if(state.equals("IDLE")) {
				throw new IllegalStateException(String.format("This operation (%s) has not been started.", instance));
			} else if(state.equals("COMPLETED")) {
				throw new IllegalStateException(String.format("This operation (%s) has already been completed.", instance));
			}
		}
		protected void assertCanConfigure() {
			ProgressLogger instance = getInstance();
			String state = getState();
			if(state.equals("IDLE")) {
				throw new IllegalStateException(String.format("Cannot configure this operation (%s) once it has started.", instance));
			}
		}

		protected boolean inherited = false;
		public void inherit() {
			assertNotStarted();
			if(this.inherited) return;
			LinkedList<Object> list = stack.get();
			if(list == null) {
				list = new LinkedList<>();
				stack.set(list);
			}
			Object last = list.size() > 0 ? list.getLast() : null;
			ProgressLogger lastInstance = progressInstance(last);
			if(lastInstance != null) {
				Object parentId = unsafe.getObject(lastInstance, AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_progressOperationId);
				unsafe.putObject(getInstance(), AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_parent, lastInstance);
				unsafe.putObject(getInstance(), AFIELD_DefaultProgressLoggerFactory$ProgressLoggerImpl_buildOperationId, parentId);
			}
			list.addLast(identifier);
			this.inherited = true;
		}

		public void pstart(String description, String status) {
			progressStart(identifier, description, status);
		}
		public void pstart(String status) {
			progressStart(identifier, status);
		}
		public void pstart() {
			progressStart(identifier);
		}

		public void pdo(String status) {
			progressDo(identifier, status);
		}
		public void pdo(String status, boolean failing) {
			progressDo(identifier, status, failing);
		}

		protected boolean endCalled = false;
		public void pend() {
			this.endCalled = true;
			progressEnd(identifier);
		}
		public void pend(String status, boolean failed) {
			this.endCalled = true;
			progressEnd(identifier, status, failed);
		}

		@Override
		public void close() {
			if(this.inherited) {
				LinkedList<Object> list = stack.get();
				Object last = list.removeLast();
				if(identifier != last)
					throw new IllegalStateException("Must not happen");
			}
			if(!getState().equals("STARTED"))
				return;
			if(!this.endCalled)
				progressEnd(identifier);
		}
	}
}
