package io.github.NadhifRadityo.Library.Utils;

import io.github.NadhifRadityo.Library.ThrowsReferencedCallback;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static io.github.NadhifRadityo.Library.Utils.ClassUtils.classForName;
import static io.github.NadhifRadityo.Library.Utils.ExceptionUtils.exception;
import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.debug;
import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.warn;
import static io.github.NadhifRadityo.Library.Utils.ProgressUtils.progress;
import static io.github.NadhifRadityo.Library.Utils.ProgressUtils.progress_id;
import static io.github.NadhifRadityo.Library.Utils.RuntimeUtils.JAVA_DETECTION_VERSION;
import static io.github.NadhifRadityo.Library.Utils.RuntimeUtils.vmArguments;

public class JavascriptUtils {
	public static final ThrowsReferencedCallback<Object> javascriptGraalVM;
	public static final ThrowsReferencedCallback<Object> javascriptNashorn;

	static { try {
		Class<?> CLASS_GRAALVM_Context = classForName("org.graalvm.polyglot.Context");
		javascriptGraalVM = CLASS_GRAALVM_Context == null ? null : new ThrowsReferencedCallback<Object>() {
			final Class<?> CLASS_GRAALVM_Value = classForName("org.graalvm.polyglot.Value");
			final Method METHOD_GRAALVM_Context_create = CLASS_GRAALVM_Context.getMethod("create", String[].class);
			final Method METHOD_GRAALVM_Context_eval = CLASS_GRAALVM_Context.getMethod("eval", String.class, CharSequence.class);
			final Method METHOD_GRAALVM_Value_execute = CLASS_GRAALVM_Value.getMethod("execute", Object[].class);
			final Map<Method, Method> convertToJavaType = new HashMap<>();
			{
				Method[] methods = CLASS_GRAALVM_Value.getMethods();
				for(Method method : methods) {
					String name = method.getName();
					if(!name.startsWith("is")) continue;
					if(method.getParameterCount() != 0) continue;
					Class<?> returnType = method.getReturnType();
					if(!returnType.equals(boolean.class) && !returnType.equals(Boolean.class))
						continue;
					Method converter = null;
					try { converter = CLASS_GRAALVM_Value.getMethod("as" + name.replaceFirst("is", ""));
					} catch(Exception e) { exception(e); } if(converter == null) continue;
					convertToJavaType.put(method, converter);
				}
			}
			@Override public Object get(Object... ___) throws Exception {
				String source = (String) ___[0]; Object[] args = (Object[]) ___[1];
				Object context = METHOD_GRAALVM_Context_create.invoke(null, (Object) new String[0]);
				try(AutoCloseable __ = (AutoCloseable) context) {
					Object function = METHOD_GRAALVM_Context_eval.invoke(context, "js", source);
					Object value = METHOD_GRAALVM_Value_execute.invoke(function, (Object) args);
					for(Map.Entry<Method, Method> convert : convertToJavaType.entrySet()) {
						boolean typeCorrect = false;
						try { typeCorrect = (boolean) convert.getKey().invoke(value);
						} catch(Exception e) { exception(e); }
						if(!typeCorrect) continue;
						return convert.getValue().invoke(value);
					}
					warn("GraalVM object not found! value=%s", value);
					return value;
				}
			}
		};

		Class<?> CLASS_NASHORN_ScriptEngineManager = classForName("javax.script.ScriptEngineManager");
		javascriptNashorn = CLASS_NASHORN_ScriptEngineManager == null ? null : new ThrowsReferencedCallback<Object>() {
			final Class<?> CLASS_NASHORN_ScriptEngine = classForName("javax.script.ScriptEngine");
			final Class<?> CLASS_NASHORN_JSObject = classForName("jdk.nashorn.api.scripting.JSObject");
			final Constructor<?> CONSTRUCTOR_NASHORN_ScriptEngineManager = CLASS_NASHORN_ScriptEngineManager.getConstructor();
			final Method METHOD_NASHORN_ScriptEngineManager_getEngineByName = CLASS_NASHORN_ScriptEngineManager.getMethod("getEngineByName", String.class);
			final Method METHOD_NASHORN_ScriptEngine_eval = CLASS_NASHORN_ScriptEngine.getMethod("eval", String.class);
			final Method METHOD_NASHORN_JSObject_call = CLASS_NASHORN_JSObject.getMethod("call", Object.class, Object[].class);
			@Override public Object get(Object... ___) throws Exception {
				String source = (String) ___[0]; Object[] args = (Object[]) ___[1];
				Object engine = CONSTRUCTOR_NASHORN_ScriptEngineManager.newInstance();
				engine = METHOD_NASHORN_ScriptEngineManager_getEngineByName.invoke(engine, "nashorn");
				Object function = METHOD_NASHORN_ScriptEngine_eval.invoke(engine, source);
				return METHOD_NASHORN_JSObject_call.invoke(function, null, args);
			}
		};
		if(CLASS_NASHORN_ScriptEngineManager != null) {
			boolean isEs6 = false;
			for(String arg : vmArguments) {
				if(!arg.equals("-Dnashorn.args=--language=es6")) continue;
				isEs6 = true; break;
			}
			if(!isEs6)
				warn("Error may occur, Please add \"-Dnashorn.args=--language=es6\" to your JVM arguments");
			else if(JAVA_DETECTION_VERSION <= 8)
				warn("Error may occur, javascript functionality may be limited. It is recommended to use java >= 9");
		}
	} catch(Exception e) { throw new Error(e); } }

	public static Object runJavascript(String source, Object... args) throws Exception {
		try(ProgressUtils.ProgressWrapper prog0 = progress(progress_id(source, args))) {
			prog0.inherit();
			prog0.setCategory(JavascriptUtils.class);
			prog0.setDescription("Running javascript");
			prog0.pstart();

			if(javascriptGraalVM != null) {
				prog0.pdo(String.format("Running javascript (%s)", "GraalVM"));
				debug("Running javascript (GraalVM)");
				return javascriptGraalVM.get(source, args);
			}
			if(javascriptNashorn != null) {
				prog0.pdo(String.format("Running javascript (%s)", "Nashorn"));
				debug("Running javascript (Nashorn)");
				return javascriptNashorn.get(source, args);
			}
			throw new UnsupportedOperationException("Supported javascript runtime is not available!");
		}
	}
	public static <T> ThrowsReferencedCallback<T> runJavascriptAsCallback(String source) {
		return (args) -> (T) runJavascript(source, args);
	}
}
