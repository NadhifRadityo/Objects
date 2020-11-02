package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class LVec4 extends LVecN {
	public LVec4(boolean copy, long... x) { super(copy, x); if(copy && x.length != 4) throw new IllegalArgumentException(); }
	public LVec4(long... x) { this(true, x); }
	public LVec4(long x, long y, long z, long w) { super(x, y, z, w); }
	public LVec4(long a) { super(4, a); }
	public LVec4() { super(4); }

	public LVec4(LVec3 xyz, long w) { super(xyz.x(), xyz.y(), xyz.z(), w); }
	public LVec4(long x, LVec3 yzw) { super(x, yzw.x(), yzw.y(), yzw.z()); }
	public LVec4(LVec2 xy, LVec2 zw) { super(xy.x(), xy.y(), zw.x(), zw.y()); }
	public LVec4(LVec2 xy, long z, long w) { super(xy.x(), xy.y(), z, w); }
	public LVec4(long x, LVec2 yz, long w) { super(x, yz.x(), yz.y(), w); }
	public LVec4(long x, long y, LVec2 zw) { super(x, y, zw.x(), zw.y()); }

	@Override protected long getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			case 'z': case 'b': case 'p': return d[2];
			case 'w': case 'a': case 'q': return d[3];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(long x, long y, long z, long w) { x(x); y(y); z(z); w(w); }

	public long x() { return d[0]; }
	public long y() { return d[1]; }
	public long z() { return d[2]; }
	public long w() { return d[3]; }
	public void x(long x) { d[0] = x; }
	public void y(long y) { d[1] = y; }
	public void z(long z) { d[2] = z; }
	public void w(long w) { d[3] = w; }

	public long r() { return d[0]; }
	public long g() { return d[1]; }
	public long b() { return d[2]; }
	public long a() { return d[3]; }
	public void r(long r) { d[0] = r; }
	public void g(long g) { d[1] = g; }
	public void b(long b) { d[2] = b; }
	public void a(long a) { d[3] = a; }

	public long s() { return d[0]; }
	public long t() { return d[1]; }
	public long p() { return d[2]; }
	public long q() { return d[3]; }
	public void s(long s) { d[0] = s; }
	public void t(long t) { d[1] = t; }
	public void p(long p) { d[2] = p; }
	public void q(long q) { d[3] = q; }
}
