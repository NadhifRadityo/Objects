package io.github.NadhifRadityo.Objects.Canvas.RenderHints.Easing;

public abstract class Cubic implements Easing {
	
	public static final Cubic EASE_IN = new Cubic() {
		@Override public float ease(float t, float b, float c, float d) {
			return c*(t/=d)*t*t + b;
		}
	};
	public static final Cubic EASE_OUT = new Cubic() {
		@Override public float ease(float t, float b, float c, float d) {
			return c*((t=t/d-1)*t*t + 1) + b;
		}
	};
	public static final Cubic EASE_IN_OUT = new Cubic() {
		@Override public float ease(float t, float b, float c, float d) {
			if ((t/=d/2) < 1) return c/2*t*t*t + b;
			return c/2*((t-=2)*t*t + 2) + b;
		}
	};
}
