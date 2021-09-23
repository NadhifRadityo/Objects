package io.github.NadhifRadityo.Objects.Library;

import org.gradle.api.internal.project.DefaultProject;

public class GradleUtils {
	public static DefaultProject __PROJECT__;

	public static boolean isGradleAvailable() {
		return __PROJECT__ != null;
	}
}
