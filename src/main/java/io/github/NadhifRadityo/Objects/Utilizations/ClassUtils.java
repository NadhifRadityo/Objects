package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class ClassUtils extends org.apache.commons.lang3.ClassUtils {
	public static Set<Class<?>> getClasses(String pack, ClassLoader... classLoader) {
		ArrayList<ClassLoader> classLoaders = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		try { classLoaders.addAll(Arrays.asList(classLoader));
		if(classLoaders.isEmpty()) classLoaders.add(ClassUtils.class.getClassLoader());
		
		Reflections reflections = new Reflections(new ConfigurationBuilder()
			    .setScanners(new SubTypesScanner(false), new ResourcesScanner())
			    .setUrls(ClasspathHelper.forClassLoader(classLoaders.toArray(new ClassLoader[0])))
			    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pack))));
		return reflections.getSubTypesOf(Object.class); } finally { Pool.returnObject(ArrayList.class, classLoaders); }
	} public static Set<Class<?>> getClasses(Package pack, ClassLoader... classLoader) { return getClasses(pack.getName(), classLoader); }
	
	public static Constructor<?> getConstructor(Class<?> classType, Class<?>[] args) throws SecurityException {
		try { return classType.getConstructor(args); } catch(NoSuchMethodException ignored) { } return null;
	} public static Constructor<?> getConstructor(Class<?> classType, Object[] args) throws SecurityException {
		return getConstructor(classType, toClass(args));
	}
}
