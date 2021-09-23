package io.github.NadhifRadityo.Objects.$Utilizations.Easing;

public abstract class Circ implements Easing {

	public static class EASE_IN extends Circ {
		@Override public double ease(double t, double b , double c, double d) {
			return -c * (Math.sqrt(1 - (t /= d) * t) - 1) + b;
		}
	} public static final Circ EASE_IN = new EASE_IN();

	public static class EASE_OUT extends Circ {
		@Override public double ease(double t, double b , double c, double d) {
			return c * Math.sqrt(1 - (t /= d - 1) * t) + b;
		}
	} public static final Circ EASE_OUT = new EASE_OUT();

	public static class EASE_IN_OUT extends Circ {
		@Override public double ease(double t, double b , double c, double d) {
			if((t /= d / 2) < 1) return -c / 2 * (Math.sqrt(1 - t * t) - 1) + b;
			return c / 2 * (Math.sqrt(1 - (t -= 2) * t) + 1) + b;
		}
	} public static final Circ EASE_IN_OUT = new EASE_IN_OUT();
}
