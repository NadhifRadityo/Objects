package io.github.NadhifRadityo.Objects.$Utilizations.Easing;

public abstract class Cubic implements Easing {

	public static class EASE_IN extends Cubic {
		@Override public double ease(double t, double b, double c, double d) {
			return c * (t /= d) * t * t + b;
		}
	} public static final Cubic EASE_IN = new EASE_IN();

	public static class EASE_OUT extends Cubic {
		@Override public double ease(double t, double b, double c, double d) {
			return c * ((t /= d - 1) * t * t + 1) + b;
		}
	} public static final Cubic EASE_OUT = new EASE_OUT();

	public static class EASE_IN_OUT extends Cubic {
		@Override public double ease(double t, double b, double c, double d) {
			if((t /= d / 2) < 1) return c / 2 * t * t * t + b;
			return c / 2 * ((t -= 2) * t * t + 2) + b;
		}
	} public static final Cubic EASE_IN_OUT = new EASE_IN_OUT();
}
