package io.github.NadhifRadityo.Objects.Utilizations;

public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {
	public static int map(int x, int inMin, int inMax, int outMin, int outMax) {
		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}
	public static float map(float x, float inMin, float inMax, float outMin, float outMax) {
		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}
	public static double map(double x, double inMin, double inMax, double outMin, double outMax) {
		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}
	public static long map(long x, long inMin, long inMax, long outMin, long outMax) {
		return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
	}
}
