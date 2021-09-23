package io.github.NadhifRadityo.Objects.$Utilizations.Easing;

public abstract class Elastic implements Easing {
	protected double amplitude;
	protected double period;

	public Elastic(double amplitude, double period) {
		this.amplitude = amplitude;
		this.period = period;
	} public Elastic() { this(-1f, 0f); }

	public double getAmplitude() { return amplitude; }
	public double getPeriod() { return period; }
	public void setAmplitude(double amplitude) { this.amplitude = amplitude; }
	public void setPeriod(double period) { this.period = period; }

	public static class EASE_IN extends Elastic {
		public EASE_IN(double amplitude, double period) { super(amplitude, period); }
		public EASE_IN() { super(); }

		@Override public double ease(double t, double b , double c, double d) {
			double a = getAmplitude();
			double p = getPeriod();
			if(t==0) return b; if ((t /= d) == 1) return b + c; if(p == 0) p = d * .3f;
			double s = 0;
			if(a < Math.abs(c)) { a = c; s = p / 4; }
			else s = p / (2 * Math.PI) * Math.asin(c / a);
			return -(a * Math.pow(2, 10 * --t) * Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
		}
	} public static final Elastic EASE_IN = new EASE_IN();

	public static class EASE_OUT extends Elastic {
		public EASE_OUT(double amplitude, double period) { super(amplitude, period); }
		public EASE_OUT() { super(); }

		@Override public double ease(double t, double b , double c, double d) {
			double a = getAmplitude();
			double p = getPeriod();
			if(t == 0) return b; if((t /= d) == 1) return b + c; if(p == 0) p = d * .3f;
			double s = 0;
			if(a < Math.abs(c)) { a = c; s = p / 4; }
			else s = p / (2 * Math.PI) * Math.asin(c / a);
			return a * Math.pow(2, -10 * t) * Math.sin((t * d - s) * (2 * Math.PI) / p) + c + b;
		}
	} public static final Elastic EASE_OUT = new EASE_OUT();

	public static class EASE_IN_OUT extends Elastic {
		public EASE_IN_OUT(double amplitude, double period) { super(amplitude, period); }
		public EASE_IN_OUT() { super(); }

		@Override public double ease(double t, double b , double c, double d) {
			double a = getAmplitude();
			double p = getPeriod();
			if(t == 0) return b; if((t /= d / 2) == 2) return b + c; if(p == 0) p = d * (.3f * 1.5f);
			double s = 0;
			if(a < Math.abs(c)) { a = c; s = p / 4; }
			else s = p / (2 * Math.PI) * Math.asin(c / a);
			if(t < 1) return -.5f * (a * Math.pow(2, 10 * --t) * Math.sin( (t * d - s) * (2 * Math.PI) / p)) + b;
			return a * Math.pow(2, -10 * --t) * Math.sin((t * d - s) * (2 * Math.PI) / p) * .5f + c + b;
		}
	} public static final Elastic EASE_IN_OUT = new EASE_IN_OUT();
}
