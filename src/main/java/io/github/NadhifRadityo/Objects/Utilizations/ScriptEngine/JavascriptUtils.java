package io.github.NadhifRadityo.Objects.Utilizations.ScriptEngine;

import io.github.NadhifRadityo.Objects.Utilizations.URLUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;

public class JavascriptUtils {
	private JavascriptUtils() { }

	public static ScriptEngine newJavascriptEngine() {
		return new ScriptEngineManager().getEngineByName("JavaScript");
	}
	
	public static void addExternalScript(ScriptEngine engine, String... sources) throws IOException, ScriptException {
		for(String source : sources)
			engine.eval(URLUtils.getSourceCode(source));
	}
}
