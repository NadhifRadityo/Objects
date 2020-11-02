package io.github.NadhifRadityo.Objects.Utilizations.Easing;

public abstract class Back implements Easing {
	protected double overshoot;

	public Back(double overshoot) { this.overshoot = overshoot; }
	public Back() { this(1.70158f); }

	public double getOvershoot() { return overshoot; }
	public void setOvershoot(double overshoot) { this.overshoot = overshoot; }

	public static class EASE_IN extends Back {
		public EASE_IN(double overshoot) { super(overshoot); }
		public EASE_IN() { super(); }

		@Override public double ease(double t, double b, double c, double d) {
			double s = getOvershoot();
			return c * (t /= d) * t * ((s + 1) * t - s) + b;
        }
	} public static final Back EASE_IN = new EASE_IN();

	public static class EASE_OUT extends Back {
		public EASE_OUT(double overshoot) { super(overshoot); }
		public EASE_OUT() { super(); }

		@Override public double ease(double t, double b, double c, double d) {
			double s = getOvershoot();
			return c * ((t /= d - 1) * t * ((s + 1) * t + s) + 1) + b;
        }
	} public static final Back EASE_OUT = new EASE_OUT();

	public static class EASE_IN_OUT extends Back {
		public EASE_IN_OUT(double overshoot) { super(overshoot); }
		public EASE_IN_OUT() { super(); }

		@Override public double ease(double t, double b, double c, double d) {
			double s = getOvershoot();
			if((t /= d / 2) < 1) return c / 2 * (t * t * (((s *= 1.525) + 1) * t - s)) + b;
			return c / 2 * ((t -= 2) * t * (((s *= 1.525) + 1) * t + s) + 2) + b;
        }
	} public static final Back EASE_IN_OUT = new EASE_IN_OUT();
}
