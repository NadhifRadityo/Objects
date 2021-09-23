package io.github.NadhifRadityo.Library;

import java.io.File;

public abstract class LibraryModule extends LibraryEntry {
	public final String name;
	public final File file;

	protected LibraryModule() {
		this.name = getClass().getSimpleName();
		this.file = new File(this.name);
	}

	public abstract void run();
}
