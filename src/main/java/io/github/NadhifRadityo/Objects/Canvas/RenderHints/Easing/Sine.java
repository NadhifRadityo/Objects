package io.github.NadhifRadityo.Objects.Canvas.RenderHints.Easing;

public abstract class Sine implements Easing {
	
	public static final Sine EASE_IN = new Sine() {
		@Override public float ease(float t, float b, float c, float d) {
			return -c * (float)Math.cos(t/d * (Math.PI/2)) + c + b;
		}
	};
	public static final Sine EASE_OUT = new Sine() {
		@Override public float ease(float t, float b, float c, float d) {
			return c * (float)Math.sin(t/d * (Math.PI/2)) + b;
		}
	};
	public static final Sine EASE_IN_OUT = new Sine() {
		@Override public float ease(float t, float b, float c, float d) {
			return -c/2 * ((float)Math.cos(Math.PI*t/d) - 1) + b;
		}
	};
}
