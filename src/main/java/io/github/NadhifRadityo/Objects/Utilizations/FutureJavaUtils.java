package io.github.NadhifRadityo.Objects.Utilizations;

import java.lang.reflect.Method;

public class FutureJavaUtils {
	protected static Object callImpl(String methodN, Object... args) {
		return ExceptionUtils.doSilentThrowsReferencedCallback((e) -> { if(!(e instanceof FutureJavaCallException)) throw new Error("FutureJava module not found", e); }, (_args) -> {
			StackTraceElement caller = new Exception().getStackTrace()[7];
			String className = caller.getClassName();
			String methodName = methodN != null ? methodN : caller.getMethodName();
			className = className.substring(0, className.lastIndexOf(".")) + ".F" + className.substring(className.lastIndexOf(".") + 1);
			methodName = "F" + methodName;

			Class<?> sourceClass = Class.forName(caller.getClassName());
			Class<?> targetClass = Class.forName(className);
			if(!sourceClass.isAssignableFrom(targetClass))
				throw new IllegalStateException("Class is not extending original class");
			Method method = ClassUtils.getPublicMethod(targetClass, methodName, ClassUtils.toClass(args));
			try { return method.invoke(null, args); } catch(Throwable e) { throw new FutureJavaCallException(e); }
		});
	}
	public static Object call(String methodN, Object... args) { return callImpl(methodN, args); }
	public static Object call(Object... args) { return callImpl(null, args); }

	protected static FutureJavaImplementation fastCallImpl(String methodN, Object... args) {
		return ExceptionUtils.doSilentThrowsReferencedCallback((e) -> { if(!(e instanceof FutureJavaCallException)) throw new Error("FutureJava module not found", e); }, (_args) -> {
			StackTraceElement caller = new Exception().getStackTrace()[7];
			String className = caller.getClassName();
			String methodName = methodN != null ? methodN : caller.getMethodName();
			className = className.substring(0, className.lastIndexOf(".")) + ".F" + className.substring(className.lastIndexOf(".") + 1);
			methodName = "FImpl" + methodName;

			Class<?> sourceClass = Class.forName(caller.getClassName());
			Class<?> targetClass = Class.forName(className);
			if(!sourceClass.isAssignableFrom(targetClass))
				throw new IllegalStateException("Class is not extending original class");
			Method method = ClassUtils.getPublicMethod(targetClass, methodName, ClassUtils.toClass(args));
			if(!FutureJavaImplementation.class.isAssignableFrom(method.getReturnType()))
				throw new IllegalStateException("Return type not extending FutureJavaImplementation");
			if(!method.getReturnType().getSimpleName().startsWith("F"))
				throw new IllegalStateException("Return type name is not acceptable");
			try { return (FutureJavaImplementation) method.invoke(null, args); } catch(Throwable e) { throw new FutureJavaCallException(e); }
		});
	}
	public static FutureJavaImplementation fastCall(String methodN, Object... args) { return fastCallImpl(methodN, args); }
	public static FutureJavaImplementation fastCall(Object... args) { return fastCallImpl(null, args); }

	public interface FutureJavaImplementation { }
	protected static class FutureJavaCallException extends Error {
		public FutureJavaCallException(String msg, Throwable e) { super(msg, e); }
		public FutureJavaCallException(Throwable e) { super(e); }
		public FutureJavaCallException(String msg) { super(msg); }
	}
}
