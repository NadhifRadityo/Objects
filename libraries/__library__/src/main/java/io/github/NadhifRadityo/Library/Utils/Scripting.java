package io.github.NadhifRadityo.Library.Utils;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import org.gradle.api.initialization.IncludedBuild;
import static io.github.NadhifRadityo.Library.LibraryEntry.getContext;

public class Scripting {
	protected static GroovyObject __UTILS_IMPORTED__;
	protected static void __UTILS_CONSTRUCT__() {
		if(__UTILS_IMPORTED__ == null)
			__UTILS_IMPORTED__ = ((Closure<GroovyObject>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call("/std$Scripting", "std$Scripting");
	}
	protected static void __UTILS_DESTRUCT__() {
		if(__UTILS_IMPORTED__ != null)
			__UTILS_IMPORTED__ = null;
	}
	protected static <T> T __UTILS_GET_PROPERTY__(String property) {
		return (T) __UTILS_IMPORTED__.getProperty(property);
	}
	protected static <T> void __UTILS_SET_PROPERTY__(String property, T value) {
		__UTILS_IMPORTED__.setProperty(property, value);
	}
	public static List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>> __INTERNAL_Gradle$DynamicScripting$Scripting_includeFlags(String... flags) {
		return Scripting.<Closure<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_includeFlags").call(flags);
	}
	public static List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>> includeFlags(String... flags) {
		return Scripting.<Closure<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>>>__UTILS_GET_PROPERTY__("includeFlags").call(flags);
	}
	public static ArrayList<IncludedBuild> get__INTERNAL_Gradle$DynamicScripting$Scripting_builds() {
		return Scripting.<ArrayList<IncludedBuild>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_builds");
	}
	public static List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>> __INTERNAL_Gradle$DynamicScripting$Scripting_exportSetter() {
		return Scripting.<Closure<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_exportSetter").call();
	}
	public static List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>> exportSetter() {
		return Scripting.<Closure<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>>>__UTILS_GET_PROPERTY__("exportSetter").call();
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting___addInjectScript(Gradle.Context context, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting___addInjectScript").call(context, cache);
	}
	public static int __INTERNAL_Gradle$DynamicScripting$Scripting_hashCode() {
		return Scripting.<Closure<Integer>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_hashCode").call();
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_removeInjectScript(Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_removeInjectScript").call(cache);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting___applyImport(Gradle.DynamicScripting.ScriptImport scriptImport, Gradle.DynamicScripting.Script script) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting___applyImport").call(scriptImport, script);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_addImportAction(Object from, Function1<? super Gradle.DynamicScripting.ScriptImport, Unit> action) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_addImportAction").call(from, action);
	}
	public static void addImportAction(Object from, Function1<? super Gradle.DynamicScripting.ScriptImport, Unit> action) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("addImportAction").call(from, action);
	}
	public static HashMap<String, Gradle.DynamicScripting.Script> get__INTERNAL_Gradle$DynamicScripting$Scripting_scripts() {
		return Scripting.<HashMap<String, Gradle.DynamicScripting.Script>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scripts");
	}
	public static String __INTERNAL_Gradle$DynamicScripting$Scripting_getScriptId(File file) {
		return Scripting.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_getScriptId").call(file);
	}
	public static String getScriptId(File file) {
		return Scripting.<Closure<String>>__UTILS_GET_PROPERTY__("getScriptId").call(file);
	}
	public static HashMap<String, Function1<Gradle.DynamicScripting.ScriptImport, Unit>> get__INTERNAL_Gradle$DynamicScripting$Scripting_actions() {
		return Scripting.<HashMap<String, Function1<Gradle.DynamicScripting.ScriptImport, Unit>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_actions");
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport(Object what, Gradle.Strategies.KeywordsUtils.Being<String> being, Gradle.Strategies.KeywordsUtils.With<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>> with) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport").call(what, being, with);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport(Object what, Gradle.Strategies.KeywordsUtils.Being<String> being) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport").call(what, being);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport(Object what, String being, List<? extends Function4<? super Gradle.DynamicScripting.ScriptExport, ? super Map<String, Function1<Object[], Object>>, ? super Map<String, Function1<Object[], Object>>, ? super Map<String, Function1<Object[], Object>>, Unit>> with) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport").call(what, being, with);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport(Object what, String being) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport").call(what, being);
	}
	public static void scriptExport(Object what, Gradle.Strategies.KeywordsUtils.Being<String> being, Gradle.Strategies.KeywordsUtils.With<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>> with) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("scriptExport").call(what, being, with);
	}
	public static void scriptExport(Object what, Gradle.Strategies.KeywordsUtils.Being<String> being) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("scriptExport").call(what, being);
	}
	public static void scriptExport(Object what, String being, List<? extends Function4<? super Gradle.DynamicScripting.ScriptExport, ? super Map<String, Function1<Object[], Object>>, ? super Map<String, Function1<Object[], Object>>, ? super Map<String, Function1<Object[], Object>>, Unit>> with) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("scriptExport").call(what, being, with);
	}
	public static void scriptExport(Object what, String being) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("scriptExport").call(what, being);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_construct() {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_construct").call();
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting___checkBuild(IncludedBuild build) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting___checkBuild").call(build);
	}
	public static Gradle.DynamicScripting.Script __INTERNAL_Gradle$DynamicScripting$Scripting___getLastImportScript() {
		return Scripting.<Closure<Gradle.DynamicScripting.Script>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting___getLastImportScript").call();
	}
	public static Gradle.DynamicScripting.Script __getLastImportScript() {
		return Scripting.<Closure<Gradle.DynamicScripting.Script>>__UTILS_GET_PROPERTY__("__getLastImportScript").call();
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptApply() {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptApply").call();
	}
	public static void scriptApply() {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("scriptApply").call();
	}
	public static boolean __INTERNAL_Gradle$DynamicScripting$Scripting_containsFlag(String flag, Gradle.DynamicScripting.ScriptImport scriptImport) {
		return Scripting.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_containsFlag").call(flag, scriptImport);
	}
	public static boolean __INTERNAL_Gradle$DynamicScripting$Scripting_containsFlag(String flag) {
		return Scripting.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_containsFlag").call(flag);
	}
	public static boolean containsFlag(String flag, Gradle.DynamicScripting.ScriptImport scriptImport) {
		return Scripting.<Closure<Boolean>>__UTILS_GET_PROPERTY__("containsFlag").call(flag, scriptImport);
	}
	public static boolean containsFlag(String flag) {
		return Scripting.<Closure<Boolean>>__UTILS_GET_PROPERTY__("containsFlag").call(flag);
	}
	public static List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>> __INTERNAL_Gradle$DynamicScripting$Scripting_exportGetter() {
		return Scripting.<Closure<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_exportGetter").call();
	}
	public static List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>> exportGetter() {
		return Scripting.<Closure<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>>>__UTILS_GET_PROPERTY__("exportGetter").call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.DynamicScripting.Scripting> get__INTERNAL_Gradle$DynamicScripting$Scripting_cache() {
		return Scripting.<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.DynamicScripting.Scripting>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_cache");
	}
	public static boolean __INTERNAL_Gradle$DynamicScripting$Scripting_equals(Object other) {
		return Scripting.<Closure<Boolean>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_equals").call(other);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_destruct() {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_destruct").call();
	}
	public static String __INTERNAL_Gradle$DynamicScripting$Scripting_toString() {
		return Scripting.<Closure<String>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_toString").call();
	}
	public static Gradle.DynamicScripting.ScriptImport __INTERNAL_Gradle$DynamicScripting$Scripting___getLastImport() {
		return Scripting.<Closure<Gradle.DynamicScripting.ScriptImport>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting___getLastImport").call();
	}
	public static Gradle.DynamicScripting.ScriptImport __getLastImport() {
		return Scripting.<Closure<Gradle.DynamicScripting.ScriptImport>>__UTILS_GET_PROPERTY__("__getLastImport").call();
	}
	public static List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>> __INTERNAL_Gradle$DynamicScripting$Scripting_exportMethod() {
		return Scripting.<Closure<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_exportMethod").call();
	}
	public static List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>> exportMethod() {
		return Scripting.<Closure<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>>>__UTILS_GET_PROPERTY__("exportMethod").call();
	}
	public static Closure<?> __INTERNAL_Gradle$DynamicScripting$Scripting_withCurrentImport() {
		return Scripting.<Closure<Closure<?>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_withCurrentImport").call();
	}
	public static Closure<?> withCurrentImport() {
		return Scripting.<Closure<Closure<?>>>__UTILS_GET_PROPERTY__("withCurrentImport").call();
	}
	public static ThreadLocal<LinkedList<Gradle.DynamicScripting.ScriptImport>> get__INTERNAL_Gradle$DynamicScripting$Scripting_stack() {
		return Scripting.<ThreadLocal<LinkedList<Gradle.DynamicScripting.ScriptImport>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_stack");
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting___removeInjectScript(Gradle.Context context, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting___removeInjectScript").call(context, cache);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(from, being, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(from, being);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(from);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(from, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Object from, String being, List<? extends Function1<? super Gradle.DynamicScripting.ScriptImport, Unit>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(from, being, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Object from, String being) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(from, being);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Object from) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(from);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Object from, List<? extends Function1<? super Gradle.DynamicScripting.ScriptImport, Unit>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(from, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(what, from, being, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(what, from, being);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(what, from);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(what, from, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Object from, String being, List<? extends Function1<? super Gradle.DynamicScripting.ScriptImport, Unit>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(what, from, being, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Object from, String being) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(what, from, being);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Object from) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport").call(what, from);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(from, being, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(from, being);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(from);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(from, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Object from, String being, List<? extends Function1<? super Gradle.DynamicScripting.ScriptImport, Unit>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(from, being, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Object from, String being) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(from, being);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Object from) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(from);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Object from, List<? extends Function1<? super Gradle.DynamicScripting.ScriptImport, Unit>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(from, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(what, from, being, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(what, from, being);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(what, from);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(what, from, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Object from, String being, List<? extends Function1<? super Gradle.DynamicScripting.ScriptImport, Unit>> with) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(what, from, being, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Object from, String being) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(what, from, being);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Object from) {
		return Scripting.<Closure<Gradle.DynamicScripting.Imported>>__UTILS_GET_PROPERTY__("scriptImport").call(what, from);
	}
	public static Pair<IncludedBuild, File> __INTERNAL_Gradle$DynamicScripting$Scripting___getScriptFile(Object scriptObj) {
		return Scripting.<Closure<Pair<IncludedBuild, File>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting___getScriptFile").call(scriptObj);
	}
	public static Gradle.DynamicScripting.Script __INTERNAL_Gradle$DynamicScripting$Scripting_getScript(Gradle.DynamicScripting.ScriptImport info) {
		return Scripting.<Closure<Gradle.DynamicScripting.Script>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_getScript").call(info);
	}
	public static Gradle.DynamicScripting.Script __INTERNAL_Gradle$DynamicScripting$Scripting_getScript(File file) {
		return Scripting.<Closure<Gradle.DynamicScripting.Script>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_getScript").call(file);
	}
	public static Gradle.DynamicScripting.Script __INTERNAL_Gradle$DynamicScripting$Scripting_getScript(String id) {
		return Scripting.<Closure<Gradle.DynamicScripting.Script>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_getScript").call(id);
	}
	public static Gradle.DynamicScripting.Script getScript(Gradle.DynamicScripting.ScriptImport info) {
		return Scripting.<Closure<Gradle.DynamicScripting.Script>>__UTILS_GET_PROPERTY__("getScript").call(info);
	}
	public static Gradle.DynamicScripting.Script getScript(File file) {
		return Scripting.<Closure<Gradle.DynamicScripting.Script>>__UTILS_GET_PROPERTY__("getScript").call(file);
	}
	public static Gradle.DynamicScripting.Script getScript(String id) {
		return Scripting.<Closure<Gradle.DynamicScripting.Script>>__UTILS_GET_PROPERTY__("getScript").call(id);
	}
	public static ArrayList<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?>> get__INTERNAL_Gradle$DynamicScripting$Scripting_injectScripts() {
		return Scripting.<ArrayList<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?>>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_injectScripts");
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptConstruct(Function0<Unit> callback) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptConstruct").call(callback);
	}
	public static void scriptConstruct(Function0<Unit> callback) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("scriptConstruct").call(callback);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptDestruct(Function0<Unit> callback) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptDestruct").call(callback);
	}
	public static void scriptDestruct(Function0<Unit> callback) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("scriptDestruct").call(callback);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_removeImportAction(Object from) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_removeImportAction").call(from);
	}
	public static void removeImportAction(Object from) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("removeImportAction").call(from);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_addInjectScript(Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		Scripting.<Closure<Void>>__UTILS_GET_PROPERTY__("__INTERNAL_Gradle$DynamicScripting$Scripting_addInjectScript").call(cache);
	}
}
