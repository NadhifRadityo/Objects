package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class BVec4 extends BVecN {
	public BVec4(boolean copy, boolean... x) { super(copy, x); if(copy && x.length != 4) throw new IllegalArgumentException(); }
	public BVec4(boolean... x) { this(true, x); }
	public BVec4(boolean x, boolean y, boolean z, boolean w) { super(new boolean[] { x, y, z, w }); }
	public BVec4(boolean a) { super(4, a); }
	public BVec4() { super(4); }

	public BVec4(BVec3 xyz, boolean w) { super(new boolean[] { xyz.x(), xyz.y(), xyz.z(), w }); }
	public BVec4(boolean x, BVec3 yzw) { super(new boolean[] { x, yzw.x(), yzw.y(), yzw.z() }); }
	public BVec4(BVec2 xy, BVec2 zw) { super(new boolean[] { xy.x(), xy.y(), zw.x(), zw.y() }); }
	public BVec4(BVec2 xy, boolean z, boolean w) { super(new boolean[] { xy.x(), xy.y(), z, w }); }
	public BVec4(boolean x, BVec2 yz, boolean w) { super(new boolean[] { x, yz.x(), yz.y(), w }); }
	public BVec4(boolean x, boolean y, BVec2 zw) { super(new boolean[] { x, y, zw.x(), zw.y() }); }

	@Override protected boolean getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			case 'z': case 'b': case 'p': return d[2];
			case 'w': case 'a': case 'q': return d[3];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(boolean x, boolean y, boolean z, boolean w) { x(x); y(y); z(z); w(w); }

	public boolean x() { return d[0]; }
	public boolean y() { return d[1]; }
	public boolean z() { return d[2]; }
	public boolean w() { return d[3]; }
	public void x(boolean x) { d[0] = x; }
	public void y(boolean y) { d[1] = y; }
	public void z(boolean z) { d[2] = z; }
	public void w(boolean w) { d[3] = w; }

	public boolean r() { return d[0]; }
	public boolean g() { return d[1]; }
	public boolean b() { return d[2]; }
	public boolean a() { return d[3]; }
	public void r(boolean r) { d[0] = r; }
	public void g(boolean g) { d[1] = g; }
	public void b(boolean b) { d[2] = b; }
	public void a(boolean a) { d[3] = a; }

	public boolean s() { return d[0]; }
	public boolean t() { return d[1]; }
	public boolean p() { return d[2]; }
	public boolean q() { return d[3]; }
	public void s(boolean s) { d[0] = s; }
	public void t(boolean t) { d[1] = t; }
	public void p(boolean p) { d[2] = p; }
	public void q(boolean q) { d[3] = q; }
}
