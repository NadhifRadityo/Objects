package io.github.NadhifRadityo.Library.Utils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static io.github.NadhifRadityo.Library.Utils.ExceptionUtils.exception;
import static io.github.NadhifRadityo.Library.Utils.RuntimeUtils.JAVA_DETECTION_VERSION;
import static io.github.NadhifRadityo.Library.Utils.UnsafeUtils.unsafe;

public class ClassUtils {

	public static <T> Class<? extends T> classForName(String classname) {
		try { return (Class<? extends T>) Class.forName(classname); } catch(Exception e) { exception(e); return null; }
	}
	public static <T> Class<? extends T> classForName0(String classname) throws ClassNotFoundException {
		return (Class<? extends T>) Class.forName(classname);
	}
	public static void setFinal(Object object, Field field, Object newValue) {
		boolean useUnsafe = JAVA_DETECTION_VERSION > 12;
		Throwable exception = null;
		try { if(!useUnsafe) {
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.setAccessible(true);
			field.set(object, newValue);
		} } catch(Throwable e) { exception = exception(e); }
		if(!useUnsafe && exception == null) return;
		try {
			Object fieldObject = object != null ? object : unsafe.staticFieldBase(field);
			long fieldOffset = object != null ? unsafe.objectFieldOffset(field) : unsafe.staticFieldOffset(field);
			unsafe.putObject(fieldObject, fieldOffset, newValue);
		} catch(Throwable e1) {
			if(exception != null) e1.addSuppressed(exception);
			throw new Error(exception(e1));
		}
	}

	public static File getCurrentClassFile(Class<?> clazz) throws Exception {
		File result = new File(URLDecoder.decode(clazz.getProtectionDomain().getCodeSource().getLocation().getPath(), StandardCharsets.UTF_8.name()));
		if(result.isDirectory()) result = new File(result, clazz.getSimpleName() + ".class"); return result;
	}
	public static File getCurrentClassFile() throws Exception {
		Class<?> currentClass = classForName(Thread.currentThread().getStackTrace()[2].getClassName());
		return currentClass == null ? null : getCurrentClassFile(currentClass);
	}
}
