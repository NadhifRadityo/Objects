package io.github.NadhifRadityo.Objects.Library;

import io.github.NadhifRadityo.Objects.Library.Constants.JSON_moduleRoot;

import java.util.jar.Manifest;

public class ModulePack {
	public final String name;
	public final Manifest manifest;
	public final Class<? extends Library> mainClass;
	public final Class<? extends Library> testClass;
	public final JSON_moduleRoot moduleRoot;

	public ModulePack(String name, Manifest manifest, Class<?> mainClass, Class<?> testClass, JSON_moduleRoot moduleRoot) {
		this.name = name;
		this.manifest = manifest;
		this.mainClass = (Class<? extends Library>) mainClass;
		this.testClass = (Class<? extends Library>) testClass;
		this.moduleRoot = moduleRoot;
	}
}
