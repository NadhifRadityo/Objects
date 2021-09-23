package io.github.NadhifRadityo.Objects.$Utilizations.Easing;

public abstract class Quint implements Easing {

	public static class EASE_IN extends Quint {
		@Override public double ease(double t, double b, double c, double d) {
			return c * (t /= d) * t * t * t * t + b;
		}
	} public static final Quint EASE_IN = new EASE_IN();

	public static class EASE_OUT extends Quint {
		@Override public double ease(double t, double b, double c, double d) {
			return c * ((t /= d - 1) * t * t * t * t + 1) + b;
		}
	} public static final Quint EASE_OUT = new EASE_OUT();

	public static class EASE_IN_OUT extends Quint {
		@Override public double ease(double t, double b, double c, double d) {
			if((t /= d / 2) < 1) return c / 2 * t * t * t * t * t + b;
			return c / 2 * ((t -= 2) * t * t * t * t + 2) + b;
		}
	} public static final Quint EASE_IN_OUT = new EASE_IN_OUT();
}
