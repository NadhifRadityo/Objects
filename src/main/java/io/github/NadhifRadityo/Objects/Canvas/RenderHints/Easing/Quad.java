package io.github.NadhifRadityo.Objects.Canvas.RenderHints.Easing;

public abstract class Quad implements Easing {
	
	public static final Quad EASE_IN = new Quad() {
		@Override public float ease(float t, float b, float c, float d) {
			return c*(t/=d)*t + b;
		}
	};
	public static final Quad EASE_OUT = new Quad() {
		@Override public float ease(float t, float b, float c, float d) {
			return -c *(t/=d)*(t-2) + b;
		}
	};
	public static final Quad EASE_IN_OUT = new Quad() {
		@Override public float ease(float t, float b, float c, float d) {
			if ((t/=d/2) < 1) return c/2*t*t + b;
			return -c/2 * ((--t)*(t-2) - 1) + b;
		}
	};
}
