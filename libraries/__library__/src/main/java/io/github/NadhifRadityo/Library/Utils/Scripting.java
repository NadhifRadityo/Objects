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
	public static List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>> __INTERNAL_Gradle$DynamicScripting$Scripting_includeFlags(String... flags) {
		return ((Closure<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_includeFlags")).call(flags);
	}
	public static List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>> includeFlags(String... flags) {
		return ((Closure<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>>) ((GroovyObject) getContext().getThat()).getProperty("includeFlags")).call(flags);
	}
	public static ArrayList<IncludedBuild> get__INTERNAL_Gradle$DynamicScripting$Scripting_builds() {
		return (ArrayList<IncludedBuild>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_builds");
	}
	public static List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>> __INTERNAL_Gradle$DynamicScripting$Scripting_exportSetter() {
		return ((Closure<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_exportSetter")).call();
	}
	public static List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>> exportSetter() {
		return ((Closure<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>>) ((GroovyObject) getContext().getThat()).getProperty("exportSetter")).call();
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting___addInjectScript(Gradle.Context context, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting___addInjectScript")).call(context, cache);
	}
	public static int __INTERNAL_Gradle$DynamicScripting$Scripting_hashCode() {
		return ((Closure<Integer>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_hashCode")).call();
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_removeInjectScript(Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_removeInjectScript")).call(cache);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting___applyImport(Gradle.DynamicScripting.ScriptImport scriptImport, Gradle.DynamicScripting.Script script) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting___applyImport")).call(scriptImport, script);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_addImportAction(Object from, Function1<? super Gradle.DynamicScripting.ScriptImport, Unit> action) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_addImportAction")).call(from, action);
	}
	public static void addImportAction(Object from, Function1<? super Gradle.DynamicScripting.ScriptImport, Unit> action) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("addImportAction")).call(from, action);
	}
	public static HashMap<String, Gradle.DynamicScripting.Script> get__INTERNAL_Gradle$DynamicScripting$Scripting_scripts() {
		return (HashMap<String, Gradle.DynamicScripting.Script>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scripts");
	}
	public static String __INTERNAL_Gradle$DynamicScripting$Scripting_getScriptId(File file) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_getScriptId")).call(file);
	}
	public static String getScriptId(File file) {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("getScriptId")).call(file);
	}
	public static HashMap<String, Function1<Gradle.DynamicScripting.ScriptImport, Unit>> get__INTERNAL_Gradle$DynamicScripting$Scripting_actions() {
		return (HashMap<String, Function1<Gradle.DynamicScripting.ScriptImport, Unit>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_actions");
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport(Object what, Gradle.Strategies.KeywordsUtils.Being<String> being, Gradle.Strategies.KeywordsUtils.With<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>> with) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport")).call(what, being, with);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport(Object what, Gradle.Strategies.KeywordsUtils.Being<String> being) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport")).call(what, being);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport(Object what, String being, List<? extends Function4<? super Gradle.DynamicScripting.ScriptExport, ? super Map<String, Function1<Object[], Object>>, ? super Map<String, Function1<Object[], Object>>, ? super Map<String, Function1<Object[], Object>>, Unit>> with) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport")).call(what, being, with);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport(Object what, String being) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptExport")).call(what, being);
	}
	public static void scriptExport(Object what, Gradle.Strategies.KeywordsUtils.Being<String> being, Gradle.Strategies.KeywordsUtils.With<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>> with) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("scriptExport")).call(what, being, with);
	}
	public static void scriptExport(Object what, Gradle.Strategies.KeywordsUtils.Being<String> being) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("scriptExport")).call(what, being);
	}
	public static void scriptExport(Object what, String being, List<? extends Function4<? super Gradle.DynamicScripting.ScriptExport, ? super Map<String, Function1<Object[], Object>>, ? super Map<String, Function1<Object[], Object>>, ? super Map<String, Function1<Object[], Object>>, Unit>> with) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("scriptExport")).call(what, being, with);
	}
	public static void scriptExport(Object what, String being) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("scriptExport")).call(what, being);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_construct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_construct")).call();
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting___checkBuild(IncludedBuild build) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting___checkBuild")).call(build);
	}
	public static Gradle.DynamicScripting.Script __INTERNAL_Gradle$DynamicScripting$Scripting___getLastImportScript() {
		return ((Closure<Gradle.DynamicScripting.Script>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting___getLastImportScript")).call();
	}
	public static Gradle.DynamicScripting.Script __getLastImportScript() {
		return ((Closure<Gradle.DynamicScripting.Script>) ((GroovyObject) getContext().getThat()).getProperty("__getLastImportScript")).call();
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptApply() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptApply")).call();
	}
	public static void scriptApply() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("scriptApply")).call();
	}
	public static boolean __INTERNAL_Gradle$DynamicScripting$Scripting_containsFlag(String flag, Gradle.DynamicScripting.ScriptImport scriptImport) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_containsFlag")).call(flag, scriptImport);
	}
	public static boolean __INTERNAL_Gradle$DynamicScripting$Scripting_containsFlag(String flag) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_containsFlag")).call(flag);
	}
	public static boolean containsFlag(String flag, Gradle.DynamicScripting.ScriptImport scriptImport) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("containsFlag")).call(flag, scriptImport);
	}
	public static boolean containsFlag(String flag) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("containsFlag")).call(flag);
	}
	public static List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>> __INTERNAL_Gradle$DynamicScripting$Scripting_exportGetter() {
		return ((Closure<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_exportGetter")).call();
	}
	public static List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>> exportGetter() {
		return ((Closure<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>>) ((GroovyObject) getContext().getThat()).getProperty("exportGetter")).call();
	}
	public static Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.DynamicScripting.Scripting> get__INTERNAL_Gradle$DynamicScripting$Scripting_cache() {
		return (Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<Gradle.DynamicScripting.Scripting>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_cache");
	}
	public static boolean __INTERNAL_Gradle$DynamicScripting$Scripting_equals(Object other) {
		return ((Closure<Boolean>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_equals")).call(other);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_destruct() {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_destruct")).call();
	}
	public static String __INTERNAL_Gradle$DynamicScripting$Scripting_toString() {
		return ((Closure<String>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_toString")).call();
	}
	public static Gradle.DynamicScripting.ScriptImport __INTERNAL_Gradle$DynamicScripting$Scripting___getLastImport() {
		return ((Closure<Gradle.DynamicScripting.ScriptImport>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting___getLastImport")).call();
	}
	public static Gradle.DynamicScripting.ScriptImport __getLastImport() {
		return ((Closure<Gradle.DynamicScripting.ScriptImport>) ((GroovyObject) getContext().getThat()).getProperty("__getLastImport")).call();
	}
	public static List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>> __INTERNAL_Gradle$DynamicScripting$Scripting_exportMethod() {
		return ((Closure<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_exportMethod")).call();
	}
	public static List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>> exportMethod() {
		return ((Closure<List<Function4<Gradle.DynamicScripting.ScriptExport, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Map<String, Function1<? super Object[], ?>>, Unit>>>) ((GroovyObject) getContext().getThat()).getProperty("exportMethod")).call();
	}
	public static Closure<?> __INTERNAL_Gradle$DynamicScripting$Scripting_withCurrentImport() {
		return ((Closure<Closure<?>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_withCurrentImport")).call();
	}
	public static Closure<?> withCurrentImport() {
		return ((Closure<Closure<?>>) ((GroovyObject) getContext().getThat()).getProperty("withCurrentImport")).call();
	}
	public static ThreadLocal<LinkedList<Gradle.DynamicScripting.ScriptImport>> get__INTERNAL_Gradle$DynamicScripting$Scripting_stack() {
		return (ThreadLocal<LinkedList<Gradle.DynamicScripting.ScriptImport>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_stack");
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting___removeInjectScript(Gradle.Context context, Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting___removeInjectScript")).call(context, cache);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(from, being, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(from, being);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(from);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(from, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Object from, String being, List<? extends Function1<? super Gradle.DynamicScripting.ScriptImport, Unit>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(from, being, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Object from, String being) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(from, being);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Object from) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(from);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(Object from, List<? extends Function1<? super Gradle.DynamicScripting.ScriptImport, Unit>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(from, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(what, from, being, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(what, from, being);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(what, from);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(what, from, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Object from, String being, List<? extends Function1<? super Gradle.DynamicScripting.ScriptImport, Unit>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(what, from, being, with);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Object from, String being) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(what, from, being);
	}
	public static Gradle.DynamicScripting.Imported __INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport(List<String> what, Object from) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptImport")).call(what, from);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(from, being, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(from, being);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(from);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(from, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Object from, String being, List<? extends Function1<? super Gradle.DynamicScripting.ScriptImport, Unit>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(from, being, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Object from, String being) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(from, being);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Object from) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(from);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(Object from, List<? extends Function1<? super Gradle.DynamicScripting.ScriptImport, Unit>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(from, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(what, from, being, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.Being<String> being) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(what, from, being);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(what, from);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Gradle.Strategies.KeywordsUtils.From<Object> from, Gradle.Strategies.KeywordsUtils.With<List<Function1<Gradle.DynamicScripting.ScriptImport, Unit>>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(what, from, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Object from, String being, List<? extends Function1<? super Gradle.DynamicScripting.ScriptImport, Unit>> with) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(what, from, being, with);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Object from, String being) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(what, from, being);
	}
	public static Gradle.DynamicScripting.Imported scriptImport(List<String> what, Object from) {
		return ((Closure<Gradle.DynamicScripting.Imported>) ((GroovyObject) getContext().getThat()).getProperty("scriptImport")).call(what, from);
	}
	public static Pair<IncludedBuild, File> __INTERNAL_Gradle$DynamicScripting$Scripting___getScriptFile(Object scriptObj) {
		return ((Closure<Pair<IncludedBuild, File>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting___getScriptFile")).call(scriptObj);
	}
	public static Gradle.DynamicScripting.Script __INTERNAL_Gradle$DynamicScripting$Scripting_getScript(Gradle.DynamicScripting.ScriptImport info) {
		return ((Closure<Gradle.DynamicScripting.Script>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_getScript")).call(info);
	}
	public static Gradle.DynamicScripting.Script __INTERNAL_Gradle$DynamicScripting$Scripting_getScript(File file) {
		return ((Closure<Gradle.DynamicScripting.Script>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_getScript")).call(file);
	}
	public static Gradle.DynamicScripting.Script __INTERNAL_Gradle$DynamicScripting$Scripting_getScript(String id) {
		return ((Closure<Gradle.DynamicScripting.Script>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_getScript")).call(id);
	}
	public static Gradle.DynamicScripting.Script getScript(Gradle.DynamicScripting.ScriptImport info) {
		return ((Closure<Gradle.DynamicScripting.Script>) ((GroovyObject) getContext().getThat()).getProperty("getScript")).call(info);
	}
	public static Gradle.DynamicScripting.Script getScript(File file) {
		return ((Closure<Gradle.DynamicScripting.Script>) ((GroovyObject) getContext().getThat()).getProperty("getScript")).call(file);
	}
	public static Gradle.DynamicScripting.Script getScript(String id) {
		return ((Closure<Gradle.DynamicScripting.Script>) ((GroovyObject) getContext().getThat()).getProperty("getScript")).call(id);
	}
	public static ArrayList<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?>> get__INTERNAL_Gradle$DynamicScripting$Scripting_injectScripts() {
		return (ArrayList<Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?>>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_injectScripts");
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptConstruct(Function0<Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptConstruct")).call(callback);
	}
	public static void scriptConstruct(Function0<Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("scriptConstruct")).call(callback);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_scriptDestruct(Function0<Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_scriptDestruct")).call(callback);
	}
	public static void scriptDestruct(Function0<Unit> callback) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("scriptDestruct")).call(callback);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_removeImportAction(Object from) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_removeImportAction")).call(from);
	}
	public static void removeImportAction(Object from) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("removeImportAction")).call(from);
	}
	public static void __INTERNAL_Gradle$DynamicScripting$Scripting_addInjectScript(Gradle.GroovyKotlinInteroperability.GroovyKotlinCache<?> cache) {
		((Closure<Void>) ((GroovyObject) getContext().getThat()).getProperty("__INTERNAL_Gradle$DynamicScripting$Scripting_addInjectScript")).call(cache);
	}
}
