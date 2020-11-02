package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class FVec3 extends FVecN {
	public FVec3(boolean copy, float... x) { super(copy, x); if(copy && x.length != 3) throw new IllegalArgumentException(); }
	public FVec3(float... x) { this(true, x); }
	public FVec3(float x, float y, float z) { super(x, y, z); }
	public FVec3(float a) { super(3, a); }
	public FVec3() { super(3); }

	public FVec3(FVec2 xy, float z) { super(xy.x(), xy.y(), z); }
	public FVec3(float x, FVec2 yz) { super(x, yz.x(), yz.y()); }

	@Override protected float getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			case 'z': case 'b': case 'p': return d[2];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(float x, float y, float z) { x(x); y(y); z(z); }

	public float x() { return d[0]; }
	public float y() { return d[1]; }
	public float z() { return d[2]; }
	public void x(float x) { d[0] = x; }
	public void y(float y) { d[1] = y; }
	public void z(float z) { d[2] = z; }

	public float r() { return d[0]; }
	public float g() { return d[1]; }
	public float b() { return d[2]; }
	public void r(float r) { d[0] = r; }
	public void g(float g) { d[1] = g; }
	public void b(float b) { d[2] = b; }

	public float s() { return d[0]; }
	public float t() { return d[1]; }
	public float p() { return d[2]; }
	public void s(float s) { d[0] = s; }
	public void t(float t) { d[1] = t; }
	public void p(float p) { d[2] = p; }
}
