package io.github.NadhifRadityo.Objects.Canvas.RenderHints.Easing;

public abstract class Linear implements Easing {
	
	public static final Linear EASE_NONE = new Linear() {
		@Override public float ease(float t, float b , float c, float d) {
			return c*t/d + b;
		}
	};
	public static final Linear EASE_IN = EASE_NONE;
	public static final Linear EASE_OUT = EASE_NONE;
	public static final Linear EASE_IN_OUT = EASE_NONE;
}
