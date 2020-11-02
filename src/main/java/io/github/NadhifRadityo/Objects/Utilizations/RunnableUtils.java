package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;
import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Object.ThrowsReferencedCallback;

public class RunnableUtils {
	@Deprecated public static void runOnDifferentThread(Runnable... runnables) { for(Runnable runnable : runnables) new Thread(runnable).start(); }
	@Deprecated public static void runOnDifferentThread(boolean printException, ThrowsRunnable... throwsRunnables) { runOnDifferentThread(convertToRunnable(printException, throwsRunnables)); }

	public static Runnable[] convertToRunnable(ExceptionHandler exceptionHandler, ThrowsRunnable... throwsRunnables) {
		Runnable[] result = new Runnable[throwsRunnables.length];
		for(int i = 0; i < result.length; i++) { int _i = i; result[_i] = () -> { try { throwsRunnables[_i].run(); } catch(Throwable e) { if(exceptionHandler != null) exceptionHandler.onExceptionOccurred(e); } }; }
		return result;
	}
	public static Runnable[] convertToRunnable(boolean printException, ThrowsRunnable... throwsRunnables) {
		return convertToRunnable(ExceptionUtils.exceptionPrintHandler(printException), throwsRunnables);
	}
	public static Runnable convertToRunnable(ExceptionHandler exceptionHandler, ThrowsRunnable throwsRunnable) {
		return convertToRunnable(exceptionHandler, new ThrowsRunnable[] { throwsRunnable })[0];
	}
	public static Runnable convertToRunnable(boolean printException, ThrowsRunnable throwsRunnable) {
		return convertToRunnable(printException, new ThrowsRunnable[] { throwsRunnable })[0];
	}

	public static ThrowsRunnable[] convertToThrowsRunnable(Runnable... runnables) {
		ThrowsRunnable[] result = new ThrowsRunnable[runnables.length];
		for(int i = 0; i < result.length; i++) result[i] = runnables[i]::run;
		return result;
	}
	public static ThrowsRunnable convertToThrowsRunnable(Runnable runnable) {
		return convertToThrowsRunnable(new Runnable[] { runnable })[0];
	}

	public static <T> ReferencedCallback<T>[] convertToReferencedCallback(ExceptionHandler exceptionHandler, ThrowsReferencedCallback<T>... throwsReferencedCallbacks) {
		ReferencedCallback<T>[] result = new ReferencedCallback[throwsReferencedCallbacks.length];
		for(int i = 0; i < result.length; i++) { int _i = i; result[_i] = (args) -> { try { return throwsReferencedCallbacks[_i].get(args); } catch(Throwable e) { if(exceptionHandler != null) exceptionHandler.onExceptionOccurred(e); return null; } }; }
		return result;
	}
	public static <T> ReferencedCallback<T> convertToReferencedCallback(ExceptionHandler exceptionHandler, ThrowsReferencedCallback<T> throwsReferencedCallback) {
		return convertToReferencedCallback(exceptionHandler, (ThrowsReferencedCallback<T>[]) new ThrowsReferencedCallback[] { throwsReferencedCallback })[0];
	}
	public static <T> ReferencedCallback<T>[] convertToReferencedCallback(ReferencedCallback<T> exceptionHandler, ThrowsReferencedCallback<T>... throwsReferencedCallbacks) {
		ReferencedCallback<T>[] result = new ReferencedCallback[throwsReferencedCallbacks.length];
		for(int i = 0; i < result.length; i++) { int _i = i; result[_i] = (args) -> { try { return throwsReferencedCallbacks[_i].get(args); } catch(Throwable e) { return exceptionHandler != null ? exceptionHandler.get(e) : null; } }; }
		return result;
	}
	public static <T> ReferencedCallback<T> convertToReferencedCallback(ReferencedCallback<T> exceptionHandler, ThrowsReferencedCallback<T> throwsReferencedCallbacks) {
		return convertToReferencedCallback(exceptionHandler, (ThrowsReferencedCallback<T>[]) new ThrowsReferencedCallback[] { throwsReferencedCallbacks })[0];
	}
	public static <T> ReferencedCallback<T>[] convertToReferencedCallback(boolean printException, ThrowsReferencedCallback<T>... throwsReferencedCallbacks) {
		return convertToReferencedCallback(ExceptionUtils.exceptionPrintHandler(printException), throwsReferencedCallbacks);
	}
	public static <T> ReferencedCallback<T> convertToReferencedCallback(boolean printException, ThrowsReferencedCallback<T> throwsReferencedCallback) {
		return convertToReferencedCallback(printException, (ThrowsReferencedCallback<T>[]) new ThrowsReferencedCallback[] { throwsReferencedCallback })[0];
	}

	public static <T> ThrowsReferencedCallback<T>[] convertToThrowsReferencedCallback(ReferencedCallback<T>... referencedCallbacks) {
		ThrowsReferencedCallback<T>[] result = new ThrowsReferencedCallback[referencedCallbacks.length];
		for(int i = 0; i < result.length; i++) result[i] = referencedCallbacks[i]::get;
		return result;
	}
	public static <T> ThrowsReferencedCallback<T> convertToThrowsReferencedCallback(ReferencedCallback<T> referencedCallback) {
		return convertToThrowsReferencedCallback((ReferencedCallback<T>[]) new ReferencedCallback[] { referencedCallback })[0];
	}
}
