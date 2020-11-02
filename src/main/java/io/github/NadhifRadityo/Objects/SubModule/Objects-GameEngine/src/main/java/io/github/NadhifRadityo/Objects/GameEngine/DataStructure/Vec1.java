package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class Vec1 extends VecN {
	public Vec1(boolean copy, double... x) { super(copy, x); if(copy && x.length != 1) throw new IllegalArgumentException(); }
	public Vec1(double... x) { this(true, x); }
	public Vec1(double x) { super(x); }
	public Vec1() { super(1); }

	@Override protected double getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(double x) { x(x); }

	public double x() { return d[0]; }
	public void x(double x) { d[0] = x; }

	public double r() { return d[0]; }
	public void r(double r) { d[0] = r; }

	public double s() { return d[0]; }
	public void s(double s) { d[0] = s; }
}
