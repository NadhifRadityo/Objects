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
	
	public static int[] getFractionValue(double dbl) {
		String s = String.valueOf(dbl);
		int digitsDec = s.length() - 1 - s.indexOf('.'); int denom = 1;
		for(int i = 0; i < digitsDec; i++) { dbl *= 10; denom *= 10; }
		int num = (int) Math.round(dbl);
		return new int[] { num, denom };
	}
	public static int[] getSimplestFractionValue(double dbl) {
		int[] fractions = getFractionValue(dbl);
		int gcd = getGreatestCommonDivisor(fractions[0], fractions[1]);
		fractions[0] /= gcd; fractions[1] /= gcd;
		return fractions;
	}
	public static int getGreatestCommonDivisor(int num1, int num2) { // Faktor persekutuan terbesar
		int gcd = 1; for(int i = 1; i <= num1 && i <= num2; i++)
			if(num1 % i == 0 && num2 % i == 0) gcd = i;
		return gcd;
	}
}
