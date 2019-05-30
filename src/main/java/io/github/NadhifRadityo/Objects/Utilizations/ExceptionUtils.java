package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;
import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;

public class ExceptionUtils extends org.apache.commons.lang3.exception.ExceptionUtils {
    public static void doSilentException(ExceptionHandler exceptionHandler, ThrowsRunnable... throwsRunnables){
    	Runnable[] runnables = RunnableUtils.convertToRunnable(exceptionHandler, throwsRunnables);
        for(Runnable runnable : runnables) runnable.run();
    }
    public static void doSilentException(boolean printException, ThrowsRunnable... throwsRunnables){
    	Runnable[] runnables = RunnableUtils.convertToRunnable(printException, throwsRunnables);
        for(Runnable runnable : runnables) runnable.run();
    }

    public static void doSilentException(ExceptionHandler exceptionHandler, Runnable... runnables){
        doSilentException(exceptionHandler, RunnableUtils.convertToThrowsRunnable(runnables));
    }
    public static void doSilentException(boolean printException, Runnable... runnables){
        doSilentException(printException, RunnableUtils.convertToThrowsRunnable(runnables));
    }
    
    public static final ExceptionHandler exceptionPrintHandler = new ExceptionHandler() {
		@Override public void onExceptionOccurred(Throwable e) { e.printStackTrace(); }
	};
}
