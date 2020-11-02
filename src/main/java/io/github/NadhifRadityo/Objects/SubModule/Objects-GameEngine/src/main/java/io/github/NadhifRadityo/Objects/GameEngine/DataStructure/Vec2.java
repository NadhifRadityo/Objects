package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class Vec2 extends VecN {
	public Vec2(boolean copy, double... x) { super(copy, x); if(copy && x.length != 2) throw new IllegalArgumentException(); }
	public Vec2(double... x) { this(true, x); }
	public Vec2(double x, double y) { super(x, y); }
	public Vec2(double a) { super(2, a); }
	public Vec2() { super(2); }

	@Override protected double getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(double x, double y) { x(x); y(y); }

	public double x() { return d[0]; }
	public double y() { return d[1]; }
	public void x(double x) { d[0] = x; }
	public void y(double y) { d[1] = y; }

	public double r() { return d[0]; }
	public double g() { return d[1]; }
	public void r(double r) { d[0] = r; }
	public void g(double g) { d[1] = g; }

	public double s() { return d[0]; }
	public double t() { return d[1]; }
	public void s(double s) { d[0] = s; }
	public void t(double t) { d[1] = t; }
}
