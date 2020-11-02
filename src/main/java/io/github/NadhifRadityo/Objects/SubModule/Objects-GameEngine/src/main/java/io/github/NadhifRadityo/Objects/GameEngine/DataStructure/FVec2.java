package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class FVec2 extends FVecN {
	public FVec2(boolean copy, float... x) { super(copy, x); if(copy && x.length != 2) throw new IllegalArgumentException(); }
	public FVec2(float... x) { this(true, x); }
	public FVec2(float x, float y) { super(x, y); }
	public FVec2(float a) { super(2, a); }
	public FVec2() { super(2); }

	@Override protected float getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(float x, float y) { x(x); y(y); }

	public float x() { return d[0]; }
	public float y() { return d[1]; }
	public void x(float x) { d[0] = x; }
	public void y(float y) { d[1] = y; }

	public float r() { return d[0]; }
	public float g() { return d[1]; }
	public void r(float r) { d[0] = r; }
	public void g(float g) { d[1] = g; }

	public float s() { return d[0]; }
	public float t() { return d[1]; }
	public void s(float s) { d[0] = s; }
	public void t(float t) { d[1] = t; }
}
