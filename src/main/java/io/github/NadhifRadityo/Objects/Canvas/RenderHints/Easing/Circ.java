package io.github.NadhifRadityo.Objects.Canvas.RenderHints.Easing;

public abstract class Circ implements Easing {

	public static final Circ EASE_IN = new Circ() {
		@Override public float ease(float t, float b , float c, float d) {
			return -c * ((float)Math.sqrt(1 - (t/=d)*t) - 1) + b;
		}
	};
	public static final Circ EASE_OUT = new Circ() {
		@Override public float ease(float t, float b , float c, float d) {
			return c * (float)Math.sqrt(1 - (t=t/d-1)*t) + b;
		}
	};
	public static final Circ EASE_IN_OUT = new Circ() {
		@Override public float ease(float t, float b , float c, float d) {
			if ((t/=d/2) < 1) return -c/2 * ((float)Math.sqrt(1 - t*t) - 1) + b;
			return c/2 * ((float)Math.sqrt(1 - (t-=2)*t) + 1) + b;
		}
	};
}
