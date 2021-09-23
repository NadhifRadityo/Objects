package io.github.NadhifRadityo.Objects.$Utilizations;

import io.github.NadhifRadityo.Objects.$Interface.Functional.ExceptionHandler;
import io.github.NadhifRadityo.Objects.$Interface.Functional.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.$Interface.Functional.ReferencedCallback;
import io.github.NadhifRadityo.Objects.$Interface.Functional.ThrowsReferencedCallback;

import java.util.Arrays;
import java.util.List;

public class ExceptionUtils extends org.apache.commons.lang3.exception.ExceptionUtils {
	@Deprecated public static void doSilentException(ExceptionHandler exceptionHandler, ThrowsRunnable... throwsRunnables) { doSilentThrowsRunnable(exceptionHandler, throwsRunnables); }
	@Deprecated public static void doSilentException(boolean printException, ThrowsRunnable... throwsRunnables) { doSilentThrowsRunnable(printException, throwsRunnables); }
	@Deprecated public static void doSilentException(ExceptionHandler exceptionHandler, Runnable... runnables) { doSilentRunnable(exceptionHandler, runnables); }
	@Deprecated public static void doSilentException(boolean printException, Runnable... runnables) { doSilentRunnable(printException, runnables); }

	public static void doSilentThrowsRunnable(ExceptionHandler exceptionHandler, ThrowsRunnable... throwsRunnables) {
		Runnable[] runnables = RunnableUtils.convertToRunnable(exceptionHandler, throwsRunnables);
		for(Runnable runnable : runnables) runnable.run();
	}
	public static void doSilentThrowsRunnable(boolean printException, ThrowsRunnable... throwsRunnables) {
		Runnable[] runnables = RunnableUtils.convertToRunnable(printException, throwsRunnables);
		for(Runnable runnable : runnables) runnable.run();
	}
	public static void doSilentRunnable(ExceptionHandler exceptionHandler, Runnable... runnables) { doSilentThrowsRunnable(exceptionHandler, RunnableUtils.convertToThrowsRunnable(runnables)); }
	public static void doSilentRunnable(boolean printException, Runnable... runnables) { doSilentThrowsRunnable(printException, RunnableUtils.convertToThrowsRunnable(runnables)); }

	public static <T> T[] doSilentThrowsReferencedCallback(ExceptionHandler exceptionHandler, Object[] args, ThrowsReferencedCallback<T>... throwsReferencedCallbacks) {
		Object[] result = new Object[throwsReferencedCallbacks.length];
		ReferencedCallback<T>[] referencedCallbacks = RunnableUtils.convertToReferencedCallback(exceptionHandler, throwsReferencedCallbacks);
		for(int i = 0; i < result.length; i++) result[i] = referencedCallbacks[i].get(args);
		return (T[]) result;
	}
	public static <T> T[] doSilentThrowsReferencedCallback(ExceptionHandler exceptionHandler, ThrowsReferencedCallback<T>... throwsReferencedCallbacks) {
		return doSilentThrowsReferencedCallback(exceptionHandler, null, throwsReferencedCallbacks);
	}
	public static <T> T doSilentThrowsReferencedCallback(ExceptionHandler exceptionHandler, Object[] args, ThrowsReferencedCallback<T> throwsReferencedCallback) {
		return doSilentThrowsReferencedCallback(exceptionHandler, args, (ThrowsReferencedCallback<T>[]) new ThrowsReferencedCallback[] { throwsReferencedCallback })[0];
	}
	public static <T> T doSilentThrowsReferencedCallback(ExceptionHandler exceptionHandler, ThrowsReferencedCallback<T> throwsReferencedCallback) {
		return doSilentThrowsReferencedCallback(exceptionHandler, null, throwsReferencedCallback);
	}
	public static <T> T[] doSilentThrowsReferencedCallback(ReferencedCallback<T> exceptionHandler, Object[] args, ThrowsReferencedCallback<T>... throwsReferencedCallbacks) {
		Object[] result = new Object[throwsReferencedCallbacks.length];
		ReferencedCallback<T>[] referencedCallbacks = RunnableUtils.convertToReferencedCallback(exceptionHandler, throwsReferencedCallbacks);
		for(int i = 0; i < result.length; i++) result[i] = referencedCallbacks[i].get(args);
		return (T[]) result;
	}
	public static <T> T[] doSilentThrowsReferencedCallback(ReferencedCallback<T> exceptionHandler, ThrowsReferencedCallback<T>... throwsReferencedCallbacks) {
		return doSilentThrowsReferencedCallback(exceptionHandler, null, throwsReferencedCallbacks);
	}
	public static <T> T doSilentThrowsReferencedCallback(ReferencedCallback<T> exceptionHandler, Object[] args, ThrowsReferencedCallback<T> throwsReferencedCallback) {
		return doSilentThrowsReferencedCallback(exceptionHandler, args, (ThrowsReferencedCallback<T>[]) new ThrowsReferencedCallback[] { throwsReferencedCallback })[0];
	}
	public static <T> T doSilentThrowsReferencedCallback(ReferencedCallback<T> exceptionHandler, ThrowsReferencedCallback<T> throwsReferencedCallback) {
		return doSilentThrowsReferencedCallback(exceptionHandler, null, throwsReferencedCallback);
	}
	public static <T> T[] doSilentThrowsReferencedCallback(boolean printException, Object[] args, ThrowsReferencedCallback<T>... throwsReferencedCallback) {
		Object[] result = new Object[throwsReferencedCallback.length];
		ReferencedCallback<T>[] referencedCallbacks = RunnableUtils.convertToReferencedCallback(printException, throwsReferencedCallback);
		for(int i = 0; i < result.length; i++) result[i] = referencedCallbacks[i].get(args);
		return (T[]) result;
	}
	public static <T> T[] doSilentThrowsReferencedCallback(boolean printException, ThrowsReferencedCallback<T>... throwsReferencedCallback) {
		return doSilentThrowsReferencedCallback(printException, null, throwsReferencedCallback);
	}
	public static <T> T doSilentThrowsReferencedCallback(boolean printException, Object[] args, ThrowsReferencedCallback<T> throwsReferencedCallbacks) {
		return doSilentThrowsReferencedCallback(printException, args, (ThrowsReferencedCallback<T>[]) new ThrowsReferencedCallback[] { throwsReferencedCallbacks })[0];
	}
	public static <T> T doSilentThrowsReferencedCallback(boolean printException, ThrowsReferencedCallback<T> throwsReferencedCallbacks) {
		return doSilentThrowsReferencedCallback(printException, null, throwsReferencedCallbacks);
	}
	public static <T> T[] doSilentReferencedCallback(ExceptionHandler exceptionHandler, Object[] args, ReferencedCallback<T>... referencedCallbacks) {
		return doSilentThrowsReferencedCallback(exceptionHandler, args, RunnableUtils.convertToThrowsReferencedCallback(referencedCallbacks));
	}
	public static <T> T[] doSilentReferencedCallback(ExceptionHandler exceptionHandler, ReferencedCallback<T>... referencedCallbacks) {
		return doSilentReferencedCallback(exceptionHandler, null, referencedCallbacks);
	}
	public static <T> T doSilentReferencedCallback(ExceptionHandler exceptionHandler, Object[] args, ReferencedCallback<T> referencedCallback) {
		return doSilentReferencedCallback(exceptionHandler, args, (ReferencedCallback<T>[]) new ReferencedCallback[] { referencedCallback })[0];
	}
	public static <T> T doSilentReferencedCallback(ExceptionHandler exceptionHandler, ReferencedCallback<T> referencedCallback) {
		return doSilentReferencedCallback(exceptionHandler, null, referencedCallback);
	}
	public static <T> T[] doSilentReferencedCallback(ReferencedCallback<T> exceptionHandler, Object[] args, ReferencedCallback<T>... referencedCallbacks) {
		return doSilentThrowsReferencedCallback(exceptionHandler, args, RunnableUtils.convertToThrowsReferencedCallback(referencedCallbacks));
	}
	public static <T> T[] doSilentReferencedCallback(ReferencedCallback<T> exceptionHandler, ReferencedCallback<T>... referencedCallbacks) {
		return doSilentReferencedCallback(exceptionHandler, null, referencedCallbacks);
	}
	public static <T> T doSilentReferencedCallback(ReferencedCallback<T> exceptionHandler, Object[] args, ReferencedCallback<T> referencedCallback) {
		return doSilentReferencedCallback(exceptionHandler, args, (ReferencedCallback<T>[]) new ReferencedCallback[] { referencedCallback })[0];
	}
	public static <T> T doSilentReferencedCallback(ReferencedCallback<T> exceptionHandler, ReferencedCallback<T> referencedCallback) {
		return doSilentReferencedCallback(exceptionHandler, null, referencedCallback);
	}
	public static <T> T[] doSilentReferencedCallback(boolean printException, Object[] args, ReferencedCallback<T>... referencedCallbacks) {
		return doSilentThrowsReferencedCallback(printException, args, RunnableUtils.convertToThrowsReferencedCallback(referencedCallbacks));
	}
	public static <T> T[] doSilentReferencedCallback(boolean printException, ReferencedCallback<T>... referencedCallbacks) {
		return doSilentReferencedCallback(printException, null, referencedCallbacks);
	}
	public static <T> T doSilentReferencedCallback(boolean printException, Object[] args, ReferencedCallback<T> referencedCallback) {
		return doSilentReferencedCallback(printException, args, (ReferencedCallback<T>[]) new ReferencedCallback[] { referencedCallback })[0];
	}
	public static <T> T doSilentReferencedCallback(boolean printException, ReferencedCallback<T> referencedCallback) {
		return doSilentReferencedCallback(printException, null, referencedCallback);
	}

	public static final ExceptionHandler exceptionPrintHandler = Throwable::printStackTrace;
	public static final ExceptionHandler silentException = e -> { throw new Error(e); };
	public static final ExceptionHandler noException = e -> { };
	public static ExceptionHandler exceptionPrintHandler(boolean printException) {
		return printException ? exceptionPrintHandler : noException;
	}
	public static ExceptionHandler exceptionPrintHandler(ReferencedCallback.BooleanReferencedCallback printException) {
		return printException != null ? e -> { if(printException.get(e)) exceptionPrintHandler.onExceptionOccurred(e); } : noException;
	}
	public static ExceptionHandler exceptionPrintHandler(boolean invert, Class<? extends Throwable>... filter) {
		List<Class<? extends Throwable>> throwables = Arrays.asList(filter);
		return exceptionPrintHandler((args) -> invert ^ throwables.contains(args[0].getClass()));
	}
	public static ExceptionHandler exceptionPrintHandler(Class<? extends Throwable>... filter) {
		return exceptionPrintHandler(false, filter);
	}
	public static final ExceptionHandler silentException(Runnable prethrow) {
		return prethrow != null ? e -> { prethrow.run(); silentException.onExceptionOccurred(e); } : noException;
	}
	public static final ExceptionHandler silentException(ReferencedCallback.BooleanReferencedCallback doThrow) {
		return doThrow != null ? e -> { if(doThrow.get(e)) silentException.onExceptionOccurred(e); } : noException;
	}
	public static ExceptionHandler silentException(boolean invert, Class<? extends Throwable>... filter) {
		List<Class<? extends Throwable>> throwables = Arrays.asList(filter);
		return silentException((args) -> invert ^ throwables.contains(args[0].getClass()));
	}
	public static ExceptionHandler silentException(Class<? extends Throwable>... filter) {
		return silentException(false, filter);
	}
}
