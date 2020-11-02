package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class LVec1 extends LVecN {
	public LVec1(boolean copy, long... x) { super(copy, x); if(copy && x.length != 1) throw new IllegalArgumentException(); }
	public LVec1(long... x) { this(true, x); }
	public LVec1(long x, long y) { super(x); }
	public LVec1() { super(1); }

	@Override protected long getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(long x) { x(x); }

	public long x() { return d[0]; }
	public void x(long x) { d[0] = x; }

	public long r() { return d[0]; }
	public void r(long r) { d[0] = r; }

	public long s() { return d[0]; }
	public void s(long s) { d[0] = s; }
}
