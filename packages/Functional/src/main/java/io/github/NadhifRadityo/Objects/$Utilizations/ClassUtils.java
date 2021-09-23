package io.github.NadhifRadityo.Objects.$Utilizations;

import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import org.reflections8.Reflections;
import org.reflections8.scanners.ResourcesScanner;
import org.reflections8.scanners.SubTypesScanner;
import org.reflections8.util.ClasspathHelper;
import org.reflections8.util.ConfigurationBuilder;
import org.reflections8.util.FilterBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ClassUtils extends org.apache.commons.lang3.ClassUtils {
	public static <T> Class<? extends T>[] getClasses(String pack, Class<T> type, ClassLoader... classLoaders) {
		if(classLoaders == null || classLoaders.length == 0) classLoaders = new ClassLoader[] { ClassUtils.class.getClassLoader() };
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setScanners(new SubTypesScanner(false), new ResourcesScanner())
				.setUrls(ClasspathHelper.forClassLoader(classLoaders))
				.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pack))));
		return reflections.getSubTypesOf(type).toArray(new Class[0]);
	} public static <T> Class<? extends T>[] getClasses(Package pack, Class<T> type, ClassLoader... classLoader) { return getClasses(pack.getName(), type, classLoader); }

	public static <T> Constructor<? extends T> getConstructor(Class<T> classType, Class<?>... args) throws SecurityException {
		try { return classType.getConstructor(args); } catch(NoSuchMethodException ignored) { } return null;
	} public static <T> Constructor<? extends T> getConstructor(Class<T> classType, Object... args) throws SecurityException {
		return getConstructor(classType, toClass(args));
	} public static <T> Constructor<? extends T>[] getConstructors(Class<T> classType, List<Class<?>> args) {
		Constructor[] constructors = classType.getConstructors();
		ArrayList<Constructor> result = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
			LoopConstructor: for(Constructor constructor : constructors) {
				Class[] params = constructor.getParameterTypes();
				for(int i = 0; i < args.size(); i++) {
					Class arg = args.get(i); if(arg == null) continue;
					if(!arg.isAssignableFrom(params[i])) continue LoopConstructor;
				} result.add(constructor);
			} return result.toArray(new Constructor[0]);
		} finally { Pool.returnObject(ArrayList.class, result); }
	}

	public static Class[] enhancedWrappersToPrimitives(Class... classes) {
		Class[] changed = wrappersToPrimitives(classes);
		for(int i = 0; i < classes.length; i++)
			if(changed[i] == null) changed[i] = classes[i];
		return changed;
	}
	public static Class[] enhancedPrimitivesToWrappers(Class... classes) {
		Class[] changed = primitivesToWrappers(classes);
		for(int i = 0; i < classes.length; i++)
			if(changed[i] == null) changed[i] = classes[i];
		return changed;
	}

	/*
	 * Source: (1) https://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection
	 * 		   (2) http://java-performance.info/updating-final-and-static-final-fields/
	 * Pretty Evil :-)
	 */
	@Deprecated
	public static void setFinal(Object object, Field field, Object newValue) throws NoSuchFieldException, IllegalAccessException {
		boolean useUnsafe = SystemUtils.JAVA_DETECTION_VERSION > 12;
		Throwable exception = null;
		try { if(!useUnsafe) {
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.setAccessible(true);
			field.set(object, newValue);
		} } catch(Throwable e) { exception = e; }
		if(!useUnsafe && exception == null) return;
		try {
			UnsafeUtils.Unsafe unsafe = UnsafeUtils.R_getUnsafe();
			Object fieldObject = object != null ? object : unsafe.staticFieldBase(field);
			long fieldOffset = object != null ? unsafe.objectFieldOffset(field) : unsafe.staticFieldOffset(field);
			unsafe.putObject(fieldObject, fieldOffset, newValue);
		} catch(Throwable e1) {
			if(exception != null) e1.addSuppressed(exception);
			throw e1;
		}
	}
}
