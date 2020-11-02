package io.github.NadhifRadityo.Objects.Utilizations.Easing;

public abstract class Sine implements Easing {

	public static class EASE_IN extends Sine {
		@Override public double ease(double t, double b, double c, double d) {
			return -c * Math.cos(t / d * (Math.PI / 2)) + c + b;
		}
	} public static final Sine EASE_IN = new EASE_IN();

	public static class EASE_OUT extends Sine {
		@Override public double ease(double t, double b, double c, double d) {
			return c * Math.sin(t / d * (Math.PI / 2)) + b;
		}
	} public static final Sine EASE_OUT = new EASE_OUT();

	public static class EASE_IN_OUT extends Sine {
		@Override public double ease(double t, double b, double c, double d) {
			return -c / 2 * (Math.cos(Math.PI * t / d) - 1) + b;
		}
	} public static final Sine EASE_IN_OUT = new EASE_IN_OUT();
}
