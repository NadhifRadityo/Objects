package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class BVec3 extends BVecN {
	public BVec3(boolean copy, boolean... x) { super(copy, x); if(copy && x.length != 3) throw new IllegalArgumentException(); }
	public BVec3(boolean... x) { this(true, x); }
	public BVec3(boolean x, boolean y, boolean z) { super(new boolean[] { x, y, z }); }
	public BVec3(boolean a) { super(3, a); }
	public BVec3() { super(3); }

	public BVec3(BVec2 xy, boolean z) { super(new boolean[] { xy.x(), xy.y(), z }); }
	public BVec3(boolean x, BVec2 yz) { super(new boolean[] { x, yz.x(), yz.y() }); }

	@Override protected boolean getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			case 'z': case 'b': case 'p': return d[2];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(boolean x, boolean y, boolean z) { x(x); y(y); z(z); }

	public boolean x() { return d[0]; }
	public boolean y() { return d[1]; }
	public boolean z() { return d[2]; }
	public void x(boolean x) { d[0] = x; }
	public void y(boolean y) { d[1] = y; }
	public void z(boolean z) { d[2] = z; }

	public boolean r() { return d[0]; }
	public boolean g() { return d[1]; }
	public boolean b() { return d[2]; }
	public void r(boolean r) { d[0] = r; }
	public void g(boolean g) { d[1] = g; }
	public void b(boolean b) { d[2] = b; }

	public boolean s() { return d[0]; }
	public boolean t() { return d[1]; }
	public boolean p() { return d[2]; }
	public void s(boolean s) { d[0] = s; }
	public void t(boolean t) { d[1] = t; }
	public void p(boolean p) { d[2] = p; }
}
