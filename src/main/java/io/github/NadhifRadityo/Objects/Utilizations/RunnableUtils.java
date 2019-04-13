package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;
import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;

public class RunnableUtils {
    public static void runOnDifferentThread(Runnable... runnables){
        for(Runnable runnable : runnables)
            new Thread(runnable).start();
    }
    public static void runOnDifferentThread(boolean printException, ThrowsRunnable... throwsRunnables){
        runOnDifferentThread(convertToRunnable(printException, throwsRunnables));
    }

    public static Runnable convertToRunnable(ExceptionHandler exceptionHandler, ThrowsRunnable runnable){
        return () -> { try { runnable.run(); } catch (Exception e) { exceptionHandler.onExceptionOccurred(e); } };
    }
    public static Runnable convertToRunnable(boolean printException, ThrowsRunnable runnable){
        return convertToRunnable(e -> { if(printException) e.printStackTrace(); }, runnable);
    }
    public static Runnable[] convertToRunnable(boolean printException, ThrowsRunnable... throwsRunnables){
        Runnable[] runnables = new Runnable[throwsRunnables.length];
        for(int i = 0; i < throwsRunnables.length; i++)
            runnables[i] = convertToRunnable(printException, throwsRunnables[i]);
        return runnables;
    }

    public static ThrowsRunnable convertToThrowsRunnable(Runnable runnable){
        return runnable::run;
    }
    public static ThrowsRunnable[] convertToThrowsRunnable(Runnable... runnables){
        ThrowsRunnable[] throwsRunnables = new ThrowsRunnable[runnables.length];
        for(int i = 0; i < throwsRunnables.length; i++)
            throwsRunnables[i] = convertToThrowsRunnable(runnables[i]);
        return throwsRunnables;
    }
}