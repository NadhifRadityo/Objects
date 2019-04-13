package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;
import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;

public class ExceptionUtils extends org.apache.commons.lang3.exception.ExceptionUtils {
    public static void doSilentException(ExceptionHandler exceptionHandler, ThrowsRunnable... throwsRunnables){
        for(ThrowsRunnable throwsRunnable : throwsRunnables)
            RunnableUtils.convertToRunnable(exceptionHandler, throwsRunnable).run();
    }

    public static void doSilentException(boolean printException, ThrowsRunnable... throwsRunnables){
        for(ThrowsRunnable throwsRunnable : throwsRunnables)
            RunnableUtils.convertToRunnable(printException, throwsRunnable).run();
    }

    public static void doSilentException(ExceptionHandler exceptionHandler, Runnable... runnables){
        doSilentException(exceptionHandler, RunnableUtils.convertToThrowsRunnable(runnables));
    }
    public static void doSilentException(boolean printException, Runnable... runnables){
        doSilentException(printException, RunnableUtils.convertToThrowsRunnable(runnables));
    }
}
