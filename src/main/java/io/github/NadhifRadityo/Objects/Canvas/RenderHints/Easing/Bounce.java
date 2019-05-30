package io.github.NadhifRadityo.Objects.Canvas.RenderHints.Easing;

public abstract class Bounce implements Easing {

	public static final Bounce EASE_IN = new Bounce() {
		@Override public float ease(float t, float b , float c, float d) {
			return c - EASE_OUT.ease(d-t, 0, c, d) + b;
		}
	};
	public static final Bounce EASE_OUT = new Bounce() {
		@Override public float ease(float t, float b , float c, float d) {
			if ((t/=d) < (1/2.75f)) return c*(7.5625f*t*t) + b;
			else if (t < (2/2.75f)) return c*(7.5625f*(t-=(1.5f/2.75f))*t + .75f) + b;
			else if (t < (2.5/2.75)) return c*(7.5625f*(t-=(2.25f/2.75f))*t + .9375f) + b;
			else return c*(7.5625f*(t-=(2.625f/2.75f))*t + .984375f) + b;
		}
	};
	public static final Bounce EASE_IN_OUT = new Bounce() {
		@Override public float ease(float t, float b , float c, float d) {
			if (t < d/2) return EASE_IN.ease(t*2, 0, c, d) * .5f + b;
			else return EASE_OUT.ease(t*2-d, 0, c, d) * .5f + c*.5f + b;
		}
	};
}
