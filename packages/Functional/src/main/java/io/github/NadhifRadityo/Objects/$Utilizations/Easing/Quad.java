package io.github.NadhifRadityo.Objects.$Utilizations.Easing;

public abstract class Quad implements Easing {

	public static class EASE_IN extends Quad {
		@Override public double ease(double t, double b, double c, double d) {
			return c * (t /= d) * t + b;
		}
	} public static final Quad EASE_IN = new EASE_IN();

	public static class EASE_OUT extends Quad {
		@Override public double ease(double t, double b, double c, double d) {
			return -c * (t /= d) * (t - 2) + b;
		}
	} public static final Quad EASE_OUT = new EASE_OUT();

	public static class EASE_IN_OUT extends Quad {
		@Override public double ease(double t, double b, double c, double d) {
			if((t /= d / 2) < 1) return c / 2 * t * t + b;
			return -c / 2 * ((--t) * (t - 2) - 1) + b;
		}
	} public static final Quad EASE_IN_OUT = new EASE_IN_OUT();
}
