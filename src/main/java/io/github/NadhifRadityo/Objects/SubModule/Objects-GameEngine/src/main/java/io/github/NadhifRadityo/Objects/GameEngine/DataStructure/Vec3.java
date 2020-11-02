package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class Vec3 extends VecN {
	public Vec3(boolean copy, double... x) { super(copy, x); if(copy && x.length != 3) throw new IllegalArgumentException(); }
	public Vec3(double... x) { this(true, x); }
	public Vec3(double x, double y, double z) { super(x, y, z); }
	public Vec3(double a) { super(3, a); }
	public Vec3() { super(3); }

	public Vec3(Vec2 xy, double z) { super(xy.x(), xy.y(), z); }
	public Vec3(double x, Vec2 yz) { super(x, yz.x(), yz.y()); }

	@Override protected double getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			case 'z': case 'b': case 'p': return d[2];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(double x, double y, double z) { x(x); y(y); z(z); }

	public double x() { return d[0]; }
	public double y() { return d[1]; }
	public double z() { return d[2]; }
	public void x(double x) { d[0] = x; }
	public void y(double y) { d[1] = y; }
	public void z(double z) { d[2] = z; }

	public double r() { return d[0]; }
	public double g() { return d[1]; }
	public double b() { return d[2]; }
	public void r(double r) { d[0] = r; }
	public void g(double g) { d[1] = g; }
	public void b(double b) { d[2] = b; }

	public double s() { return d[0]; }
	public double t() { return d[1]; }
	public double p() { return d[2]; }
	public void s(double s) { d[0] = s; }
	public void t(double t) { d[1] = t; }
	public void p(double p) { d[2] = p; }
}
