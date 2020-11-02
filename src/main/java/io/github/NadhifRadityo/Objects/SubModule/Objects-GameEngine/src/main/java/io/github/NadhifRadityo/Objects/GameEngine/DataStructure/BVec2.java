package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class BVec2 extends BVecN {
	public BVec2(boolean copy, boolean... x) { super(copy, x); if(copy && x.length != 2) throw new IllegalArgumentException(); }
	public BVec2(boolean... x) { this(true, x); }
	public BVec2(boolean x, boolean y) { super(new boolean[] { x, y }); }
	public BVec2(boolean a) { super(2, a); }
	public BVec2() { super(2); }

	@Override protected boolean getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(boolean x, boolean y) { x(x); y(y); }

	public boolean x() { return d[0]; }
	public boolean y() { return d[1]; }
	public void x(boolean x) { d[0] = x; }
	public void y(boolean y) { d[1] = y; }

	public boolean r() { return d[0]; }
	public boolean g() { return d[1]; }
	public void r(boolean r) { d[0] = r; }
	public void g(boolean g) { d[1] = g; }

	public boolean s() { return d[0]; }
	public boolean t() { return d[1]; }
	public void s(boolean s) { d[0] = s; }
	public void t(boolean t) { d[1] = t; }
}
