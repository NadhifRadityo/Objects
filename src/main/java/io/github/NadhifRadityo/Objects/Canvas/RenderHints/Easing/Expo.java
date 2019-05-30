package io.github.NadhifRadityo.Objects.Canvas.RenderHints.Easing;

public abstract class Expo implements Easing {

	public static final Expo EASE_IN = new Expo() {
		@Override public float ease(float t, float b , float c, float d) {
			return (t==0) ? b : c * (float)Math.pow(2, 10 * (t/d - 1)) + b;
		}
	};
	public static final Expo EASE_OUT = new Expo() {
		@Override public float ease(float t, float b , float c, float d) {
			return (t==d) ? b+c : c * (-(float)Math.pow(2, -10 * t/d) + 1) + b;
		}
	};
	public static final Expo EASE_IN_OUT = new Expo() {
		@Override public float ease(float t, float b , float c, float d) {
			if (t==0) return b;
			if (t==d) return b+c;
			if ((t/=d/2) < 1) return c/2 * (float)Math.pow(2, 10 * (t - 1)) + b;
			return c/2 * (-(float)Math.pow(2, -10 * --t) + 2) + b;
		}
	};
}
