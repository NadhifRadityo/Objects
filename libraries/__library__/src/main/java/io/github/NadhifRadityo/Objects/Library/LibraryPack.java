package io.github.NadhifRadityo.Objects.Library;

import java.util.jar.Manifest;

public class LibraryPack {
	public final String name;
	public final Manifest manifest;
	public final Class<? extends Library> mainClass;
	public final Class<? extends Library> testClass;

	public LibraryPack(String name, Manifest manifest, Class<?> mainClass, Class<?> testClass) {
		this.name = name;
		this.manifest = manifest;
		this.mainClass = (Class<? extends Library>) mainClass;
		this.testClass = (Class<? extends Library>) testClass;
	}
}
