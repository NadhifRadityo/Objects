package io.github.NadhifRadityo.Objects.$Utilizations.Easing;

public abstract class Linear implements Easing {

	public static class EASE_NONE extends Linear {
		@Override public double ease(double t, double b , double c, double d) {
			return c * t / d + b;
		}
	} public static final Linear EASE_NONE = new EASE_NONE();

	public static final Linear EASE_IN = EASE_NONE;
	public static final Linear EASE_OUT = EASE_NONE;
	public static final Linear EASE_IN_OUT = EASE_NONE;
}
