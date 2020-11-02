package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class IVec4 extends IVecN {
	public IVec4(boolean copy, int... x) { super(copy, x); if(copy && x.length != 4) throw new IllegalArgumentException(); }
	public IVec4(int... x) { this(true, x); }
	public IVec4(int x, int y, int z, int w) { super(x, y, z, w); }
	public IVec4(int a) { super(4, a); }
	public IVec4() { super(4); }

	public IVec4(IVec3 xyz, int w) { super(xyz.x(), xyz.y(), xyz.z(), w); }
	public IVec4(int x, IVec3 yzw) { super(x, yzw.x(), yzw.y(), yzw.z()); }
	public IVec4(IVec2 xy, IVec2 zw) { super(xy.x(), xy.y(), zw.x(), zw.y()); }
	public IVec4(IVec2 xy, int z, int w) { super(xy.x(), xy.y(), z, w); }
	public IVec4(int x, IVec2 yz, int w) { super(x, yz.x(), yz.y(), w); }
	public IVec4(int x, int y, IVec2 zw) { super(x, y, zw.x(), zw.y()); }

	@Override protected int getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			case 'z': case 'b': case 'p': return d[2];
			case 'w': case 'a': case 'q': return d[3];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(int x, int y, int z, int w) { x(x); y(y); z(z); w(w); }

	public int x() { return d[0]; }
	public int y() { return d[1]; }
	public int z() { return d[2]; }
	public int w() { return d[3]; }
	public void x(int x) { d[0] = x; }
	public void y(int y) { d[1] = y; }
	public void z(int z) { d[2] = z; }
	public void w(int w) { d[3] = w; }

	public int r() { return d[0]; }
	public int g() { return d[1]; }
	public int b() { return d[2]; }
	public int a() { return d[3]; }
	public void r(int r) { d[0] = r; }
	public void g(int g) { d[1] = g; }
	public void b(int b) { d[2] = b; }
	public void a(int a) { d[3] = a; }

	public int s() { return d[0]; }
	public int t() { return d[1]; }
	public int p() { return d[2]; }
	public int q() { return d[3]; }
	public void s(int s) { d[0] = s; }
	public void t(int t) { d[1] = t; }
	public void p(int p) { d[2] = p; }
	public void q(int q) { d[3] = q; }
}
