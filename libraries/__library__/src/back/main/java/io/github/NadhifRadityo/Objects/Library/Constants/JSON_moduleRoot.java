package io.github.NadhifRadityo.Objects.Library.Constants;

import java.util.Properties;

public class JSON_moduleRoot {
	public String name;
	public Properties properties;
	public $dependency[] dependencies;

	public static class $dependency {
		public String id;
		public Provider source;
		public Properties properties;
		public $item[] items;

		public static class $item {
			public String name;
			public String directory;
			public long actions;
			public Properties properties;
		}
	}
}
