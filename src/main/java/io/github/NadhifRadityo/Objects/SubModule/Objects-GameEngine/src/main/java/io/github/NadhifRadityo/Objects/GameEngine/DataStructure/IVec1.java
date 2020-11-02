package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class IVec1 extends IVecN {
	public IVec1(boolean copy, int... x) { super(copy, x); if(copy && x.length != 1) throw new IllegalArgumentException(); }
	public IVec1(int... x) { this(true, x); }
	public IVec1(int x) { super(1, x); }
	public IVec1() { super(1); }

	@Override protected int getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(int x) { x(x); }

	public int x() { return d[0]; }
	public void x(int x) { d[0] = x; }

	public int r() { return d[0]; }
	public void r(int r) { d[0] = r; }

	public int s() { return d[0]; }
	public void s(int s) { d[0] = s; }
}
