package io.github.NadhifRadityo.Objects.Utilizations;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Java9Utils {

	protected static boolean alreadyDisableJava9SillyWarning;
	public static void disableJava9SillyWarning() {
		// JVM9 complains about using reflection to access packages from a module that aren't exported. This makes no sense; the whole point of reflection
		// is to get past such issues. The only comment from the jigsaw team lead on this was some unspecified mumbling about security which makes no sense,
		// as the SecurityManager is invoked to check such things. Therefore this warning is a bug, so we shall patch java to fix it.
//		if(alreadyDisableJava9SillyWarning) return;
		ExceptionUtils.doSilentThrowsRunnable(true, () -> {
			Unsafe unsafe = UnsafeUtils.R_getUnsafe();
			Class<?> clazz = Class.forName("jdk.internal.module.IllegalAccessLogger");
			Field logger = clazz.getDeclaredField("logger");
			unsafe.putObjectVolatile(clazz, unsafe.staticFieldOffset(logger), null);
		}); alreadyDisableJava9SillyWarning = true;
	}
}
