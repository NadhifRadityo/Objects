package io.github.NadhifRadityo.Objects.Canvas.RenderHints.Easing;

import io.github.NadhifRadityo.Objects.Utilizations.Canvas.FastTrig;

public abstract class Elastic implements Easing {
	protected float amplitude;
	protected float period;
	
	public Elastic(float amplitude, float period) {
		this.amplitude = amplitude;
		this.period = period;
	} public Elastic() { this(-1f, 0f); }
	
	public float getAmplitude() { return amplitude; }
	public float getPeriod() { return period; }
	public void setAmplitude(float amplitude) { this.amplitude = amplitude; }
	public void setPeriod(float period) { this.period = period; }
	
	public static class EASE_IN extends Elastic {
		public EASE_IN(float amplitude, float period) { super(amplitude, period); }
		public EASE_IN() { super(); }
		
		@Override public float ease(float t, float b , float c, float d) {
			float a = getAmplitude();
			float p = getPeriod();
			if (t==0) return b;  if ((t/=d)==1) return b+c;  if (p==0) p=d*.3f;
			float s = 0;
			if (a < Math.abs(c)) { a=c; s=p/4; } 
			else s = p/(float)(2*Math.PI) * (float)Math.asin(c/a);
			return -(a*(float)Math.pow(2,10*(t-=1)) * (float)Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
		}
	} public static final Elastic def_EASE_IN = new EASE_IN();
	
	public static class EASE_OUT extends Elastic {
		public EASE_OUT(float amplitude, float period) { super(amplitude, period); }
		public EASE_OUT() { super(); }
		
		@Override public float ease(float t, float b , float c, float d) {
			float a = getAmplitude();
			float p = getPeriod();
			if (t==0) return b;  if ((t/=d)==1) return b+c;  if (p==0) p=d*.3f;
			float s = 0;
			if (a < Math.abs(c)) { a=c; s=p/4; }
			else s = p/(float)(2*Math.PI) * (float)Math.asin(c/a);
			return a*(float)Math.pow(2,-10*t) * (float)FastTrig.sin( (t*d-s)*(2*Math.PI)/p ) + c + b;
		}
	} public static final Elastic def_EASE_OUT = new EASE_OUT();
	
	public static class EASE_IN_OUT extends Elastic {
		public EASE_IN_OUT(float amplitude, float period) { super(amplitude, period); }
		public EASE_IN_OUT() { super(); }
		
		@Override public float ease(float t, float b , float c, float d) {
			float a = getAmplitude();
			float p = getPeriod();
			if (t==0) return b;  if ((t/=d/2)==2) return b+c;  if (p==0) p=d*(.3f*1.5f);
			float s = 0;
			if (a < Math.abs(c)) { a=c; s=p/4f; }
			else s = p/(float)(2*Math.PI) * (float)Math.asin(c/a);
			if (t < 1) return -.5f*(a*(float)Math.pow(2,10*(t-=1)) * (float)FastTrig.sin( (t*d-s)*(2*Math.PI)/p )) + b;
			return a*(float)Math.pow(2,-10*(t-=1)) * (float)FastTrig.sin( (t*d-s)*(2*Math.PI)/p )*.5f + c + b;
		}
	} public static final Elastic def_EASE_IN_OUT = new EASE_IN_OUT();
}
