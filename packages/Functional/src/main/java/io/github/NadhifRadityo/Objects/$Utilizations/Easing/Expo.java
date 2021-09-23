package io.github.NadhifRadityo.Objects.$Utilizations.Easing;

public abstract class Expo implements Easing {

	public static class EASE_IN extends Expo {
		@Override public double ease(double t, double b , double c, double d) {
			return t == 0 ? b : c * Math.pow(2, 10 * (t / d - 1)) + b;
		}
	} public static final Expo EASE_IN = new EASE_IN();

	public static class EASE_OUT extends Expo {
		@Override public double ease(double t, double b , double c, double d) {
			return t == d ? b + c : c * (-Math.pow(2, -10 * t / d) + 1) + b;
		}
	} public static final Expo EASE_OUT = new EASE_OUT();

	public static class EASE_IN_OUT extends Expo {
		@Override public double ease(double t, double b , double c, double d) {
			if(t == 0) return b;
			if(t == d) return b + c;
			if((t /= d / 2) < 1) return c / 2 * Math.pow(2, 10 * (t - 1)) + b;
			return c / 2 * (-Math.pow(2, -10 * --t) + 2) + b;
		}
	} public static final Expo EASE_IN_OUT = new EASE_IN_OUT();
}
