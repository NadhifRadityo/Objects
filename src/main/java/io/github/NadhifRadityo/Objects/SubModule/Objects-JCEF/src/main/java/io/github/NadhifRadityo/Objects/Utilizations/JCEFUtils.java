package io.github.NadhifRadityo.Objects.Utilizations;

import org.cef.browser.CefBrowser;

public class JCEFUtils {
	private JCEFUtils() { }
	
	public static void log(String msg, CefBrowser browser) { runJs("console.log('" + StringUtils.escapeString(msg) + "');", browser); }
	public static void alert(String msg, CefBrowser browser) { runJs("alert('" + StringUtils.escapeString(msg) + "');", browser); }
	public static void runJs(String code, CefBrowser browser) { browser.executeJavaScript(code, "JavaSource", 0); }
}
