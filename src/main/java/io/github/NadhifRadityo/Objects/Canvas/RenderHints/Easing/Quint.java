package io.github.NadhifRadityo.Objects.Canvas.RenderHints.Easing;

public abstract class Quint implements Easing {

	public static final Quint EASE_IN = new Quint() {
		@Override public float ease(float t, float b, float c, float d) {
			return c*(t/=d)*t*t*t*t + b;
		}
	};
	public static final Quint EASE_OUT = new Quint() {
		@Override public float ease(float t, float b, float c, float d) {
			return c*((t=t/d-1)*t*t*t*t + 1) + b;
		}
	};
	public static final Quint EASE_IN_OUT = new Quint() {
		@Override public float ease(float t, float b, float c, float d) {
			if ((t/=d/2) < 1) return c/2*t*t*t*t*t + b;
			return c/2*((t-=2)*t*t*t*t + 2) + b;
		}
	};
}
