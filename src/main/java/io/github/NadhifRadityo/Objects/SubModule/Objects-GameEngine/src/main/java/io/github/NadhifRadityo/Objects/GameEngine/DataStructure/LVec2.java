package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class LVec2 extends LVecN {
	public LVec2(boolean copy, long... x) { super(copy, x); if(copy && x.length != 2) throw new IllegalArgumentException(); }
	public LVec2(long... x) { this(true, x); }
	public LVec2(long x, long y) { super(x, y); }
	public LVec2(long a) { super(2, a); }
	public LVec2() { super(2); }

	@Override protected long getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(long x, long y) { x(x); y(y); }

	public long x() { return d[0]; }
	public long y() { return d[1]; }
	public void x(long x) { d[0] = x; }
	public void y(long y) { d[1] = y; }

	public long r() { return d[0]; }
	public long g() { return d[1]; }
	public void r(long r) { d[0] = r; }
	public void g(long g) { d[1] = g; }

	public long s() { return d[0]; }
	public long t() { return d[1]; }
	public void s(long s) { d[0] = s; }
	public void t(long t) { d[1] = t; }
}
