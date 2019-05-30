package io.github.NadhifRadityo.Objects.Canvas.RenderHints.Easing;

public abstract class Back implements Easing {
	protected float overshoot;
	
	public Back(float overshoot) { this.overshoot = overshoot; }
	public Back() { this(1.70158f); }
	
	public float getOvershoot() { return overshoot; }
	public void setOvershoot(float overshoot) { this.overshoot = overshoot; }
	
	public static class EASE_IN extends Back {
		public EASE_IN(float overshoot) { super(overshoot); }
		public EASE_IN() { super(); }
		
		@Override public float ease(float t, float b, float c, float d) {
			float s = getOvershoot();
			return c*(t/=d)*t*((s+1)*t - s) + b;
        }
	} public static final Back def_EASE_IN = new EASE_IN();
	
	public static class EASE_OUT extends Back {
		public EASE_OUT(float overshoot) { super(overshoot); }
		public EASE_OUT() { super(); }
		
		@Override public float ease(float t, float b, float c, float d) {
			float s = getOvershoot();
			return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
        }
	} public static final Back def_EASE_OUT = new EASE_OUT();
	
	public static class EASE_IN_OUT extends Back {
		public EASE_IN_OUT(float overshoot) { super(overshoot); }
		public EASE_IN_OUT() { super(); }
		
		@Override public float ease(float t, float b, float c, float d) {
			float s = getOvershoot();
			if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525))+1)*t - s)) + b;
			return c/2*((t-=2)*t*(((s*=(1.525))+1)*t + s) + 2) + b;
        }
	} public static final Back def_EASE_IN_OUT = new EASE_IN_OUT();
}
