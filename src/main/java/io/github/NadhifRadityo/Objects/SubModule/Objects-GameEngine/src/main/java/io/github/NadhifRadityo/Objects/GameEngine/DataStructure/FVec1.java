package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class FVec1 extends FVecN {
	public FVec1(boolean copy, float... x) { super(copy, x); if(copy && x.length != 1) throw new IllegalArgumentException(); }
	public FVec1(float... x) { this(true, x); }
	public FVec1(float x) { super(x); }
	public FVec1() { super(1); }

	@Override protected float getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(float x) { x(x); }

	public float x() { return d[0]; }
	public void x(float x) { d[0] = x; }

	public float r() { return d[0]; }
	public void r(float r) { d[0] = r; }

	public float s() { return d[0]; }
	public void s(float s) { d[0] = s; }
}
