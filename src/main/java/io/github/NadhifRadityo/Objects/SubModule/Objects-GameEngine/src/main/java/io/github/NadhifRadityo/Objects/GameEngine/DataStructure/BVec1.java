package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class BVec1 extends BVecN {
	public BVec1(boolean copy, boolean... x) { super(copy, x); if(copy && x.length != 1) throw new IllegalArgumentException(); }
	public BVec1(boolean... x) { this(true, x); }
	public BVec1(boolean x) { super(new boolean[] { x }); }
	public BVec1() { super(1); }

	@Override protected boolean getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(boolean x) { x(x); }

	public boolean x() { return d[0]; }
	public void x(boolean x) { d[0] = x; }

	public boolean r() { return d[0]; }
	public void r(boolean r) { d[0] = r; }

	public boolean s() { return d[0]; }
	public void s(boolean s) { d[0] = s; }
}
