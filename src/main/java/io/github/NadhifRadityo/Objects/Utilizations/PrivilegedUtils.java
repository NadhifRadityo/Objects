package io.github.NadhifRadityo.Objects.Utilizations;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class PrivilegedUtils {

	public static boolean isRunningOnPrivileged() {
		String doPrivilegedMethodName = "doPrivileged";
		String accessControllerClassName = AccessController.class.getCanonicalName();
		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		for(StackTraceElement trace : traces) { try { if(trace.getClassName().equals(accessControllerClassName) && trace.getMethodName().equals(doPrivilegedMethodName))
			return true; } catch(Exception ignored) { } } return false;
	}

	public static <T> T doPrivileged(PrivilegedAction<T> action) { return AccessController.doPrivileged(action); }
	public static void doPrivileged(Runnable action) { doPrivileged(() -> { action.run(); return null; }); }
}
