package io.github.NadhifRadityo.Objects.Utilizations;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class ClassUtils extends org.apache.commons.lang3.ClassUtils {
	public static Set<Class<?>> getClasses(String pack, ClassLoader... classLoader) {
		List<ClassLoader> classLoaders = new ArrayList<>(Arrays.asList(classLoader));
		if(classLoaders.isEmpty()) classLoaders.add(ClassUtils.class.getClassLoader());
		
		Reflections reflections = new Reflections(new ConfigurationBuilder()
			    .setScanners(new SubTypesScanner(false), new ResourcesScanner())
			    .setUrls(ClasspathHelper.forClassLoader(classLoaders.toArray(new ClassLoader[0])))
			    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pack))));
		return reflections.getSubTypesOf(Object.class);
	}
	public static Set<Class<?>> getClasses(Package pack, ClassLoader... classLoader) {
		return getClasses(pack.getName(), classLoader);
	}
	
	public static Constructor<?> getConstructor(Class<?> klass, Object[] args) throws SecurityException {
		try { return klass.getConstructor(ClassUtils.toClass(args)); } catch(NoSuchMethodException e) {} return null;
	}
}
