package io.github.NadhifRadityo.Objects.Utilizations.ScriptEngine;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import io.github.NadhifRadityo.Objects.Utilizations.URLUtils;

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
