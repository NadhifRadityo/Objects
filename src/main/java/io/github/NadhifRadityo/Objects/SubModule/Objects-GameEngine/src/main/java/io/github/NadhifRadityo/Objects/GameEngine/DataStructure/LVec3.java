package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class LVec3 extends LVecN {
	public LVec3(boolean copy, long... x) { super(copy, x); if(copy && x.length != 3) throw new IllegalArgumentException(); }
	public LVec3(long... x) { this(true, x); }
	public LVec3(long x, long y, long z) { super(x, y, z); }
	public LVec3(long a) { super(3, a); }
	public LVec3() { super(3); }

	public LVec3(LVec2 xy, long z) { super(xy.x(), xy.y(), z); }
	public LVec3(long x, LVec2 yz) { super(x, yz.x(), yz.y()); }

	@Override protected long getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			case 'z': case 'b': case 'p': return d[2];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(long x, long y, long z) { x(x); y(y); z(z); }

	public long x() { return d[0]; }
	public long y() { return d[1]; }
	public long z() { return d[2]; }
	public void x(long x) { d[0] = x; }
	public void y(long y) { d[1] = y; }
	public void z(long z) { d[2] = z; }

	public long r() { return d[0]; }
	public long g() { return d[1]; }
	public long b() { return d[2]; }
	public void r(long r) { d[0] = r; }
	public void g(long g) { d[1] = g; }
	public void b(long b) { d[2] = b; }

	public long s() { return d[0]; }
	public long t() { return d[1]; }
	public long p() { return d[2]; }
	public void s(long s) { d[0] = s; }
	public void t(long t) { d[1] = t; }
	public void p(long p) { d[2] = p; }
}
