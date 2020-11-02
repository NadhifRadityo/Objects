package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class IVec3 extends IVecN {
	public IVec3(boolean copy, int... x) { super(copy, x); if(copy && x.length != 3) throw new IllegalArgumentException(); }
	public IVec3(int... x) { this(true, x); }
	public IVec3(int x, int y, int z) { super(x, y, z); }
	public IVec3(int a) { super(3, a); }
	public IVec3() { super(3); }

	public IVec3(IVec2 xy, int z) { super(xy.x(), xy.y(), z); }
	public IVec3(int x, IVec2 yz) { super(x, yz.x(), yz.y()); }

	@Override protected int getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			case 'z': case 'b': case 'p': return d[2];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(int x, int y, int z) { x(x); y(y); z(z); }

	public int x() { return d[0]; }
	public int y() { return d[1]; }
	public int z() { return d[2]; }
	public void x(int x) { d[0] = x; }
	public void y(int y) { d[1] = y; }
	public void z(int z) { d[2] = z; }

	public int r() { return d[0]; }
	public int g() { return d[1]; }
	public int b() { return d[2]; }
	public void r(int r) { d[0] = r; }
	public void g(int g) { d[1] = g; }
	public void b(int b) { d[2] = b; }

	public int s() { return d[0]; }
	public int t() { return d[1]; }
	public int p() { return d[2]; }
	public void s(int s) { d[0] = s; }
	public void t(int t) { d[1] = t; }
	public void p(int p) { d[2] = p; }
}
