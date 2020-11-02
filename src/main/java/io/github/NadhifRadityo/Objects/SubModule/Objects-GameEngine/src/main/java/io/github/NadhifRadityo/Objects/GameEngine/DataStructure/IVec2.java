package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class IVec2 extends IVecN {
	public IVec2(boolean copy, int... x) { super(copy, x); if(copy && x.length != 2) throw new IllegalArgumentException(); }
	public IVec2(int... x) { this(true, x); }
	public IVec2(int x, int y) { super(x, y); }
	public IVec2(int a) { super(2, a); }
	public IVec2() { super(2); }

	@Override protected int getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(int x, int y) { x(x); y(y); }

	public int x() { return d[0]; }
	public int y() { return d[1]; }
	public void x(int x) { d[0] = x; }
	public void y(int y) { d[1] = y; }

	public int r() { return d[0]; }
	public int g() { return d[1]; }
	public void r(int r) { d[0] = r; }
	public void g(int g) { d[1] = g; }

	public int s() { return d[0]; }
	public int t() { return d[1]; }
	public void s(int s) { d[0] = s; }
	public void t(int t) { d[1] = t; }
}
