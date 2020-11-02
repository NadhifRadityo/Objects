package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class FVec4 extends FVecN {
	public FVec4(boolean copy, float... x) { super(copy, x); if(copy && x.length != 4) throw new IllegalArgumentException(); }
	public FVec4(float... x) { this(true, x); }
	public FVec4(float x, float y, float z, float w) { super(x, y, z, w); }
	public FVec4(float a) { super(4, a); }
	public FVec4() { super(4); }

	public FVec4(FVec3 xyz, float w) { super(xyz.x(), xyz.y(), xyz.z(), w); }
	public FVec4(float x, FVec3 yzw) { super(x, yzw.x(), yzw.y(), yzw.z()); }
	public FVec4(FVec2 xy, FVec2 zw) { super(xy.x(), xy.y(), zw.x(), zw.y()); }
	public FVec4(FVec2 xy, float z, float w) { super(xy.x(), xy.y(), z, w); }
	public FVec4(float x, FVec2 yz, float w) { super(x, yz.x(), yz.y(), w); }
	public FVec4(float x, float y, FVec2 zw) { super(x, y, zw.x(), zw.y()); }

	@Override protected float getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			case 'z': case 'b': case 'p': return d[2];
			case 'w': case 'a': case 'q': return d[3];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(float x, float y, float z, float w) { x(x); y(y); z(z); w(w); }

	public float x() { return d[0]; }
	public float y() { return d[1]; }
	public float z() { return d[2]; }
	public float w() { return d[3]; }
	public void x(float x) { d[0] = x; }
	public void y(float y) { d[1] = y; }
	public void z(float z) { d[2] = z; }
	public void w(float w) { d[3] = w; }

	public float r() { return d[0]; }
	public float g() { return d[1]; }
	public float b() { return d[2]; }
	public float a() { return d[3]; }
	public void r(float r) { d[0] = r; }
	public void g(float g) { d[1] = g; }
	public void b(float b) { d[2] = b; }
	public void a(float a) { d[3] = a; }

	public float s() { return d[0]; }
	public float t() { return d[1]; }
	public float p() { return d[2]; }
	public float q() { return d[3]; }
	public void s(float s) { d[0] = s; }
	public void t(float t) { d[1] = t; }
	public void p(float p) { d[2] = p; }
	public void q(float q) { d[3] = q; }
}
