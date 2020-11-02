package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class Vec4 extends VecN {
	public Vec4(boolean copy, double... x) { super(copy, x); if(copy && x.length != 4) throw new IllegalArgumentException(); }
	public Vec4(double... x) { this(true, x); }
	public Vec4(double x, double y, double z, double w) { super(x, y, z, w); }
	public Vec4(double a) { super(4, a); }
	public Vec4() { super(4); }

	public Vec4(Vec3 xyz, double w) { super(xyz.x(), xyz.y(), xyz.z(), w); }
	public Vec4(double x, Vec3 yzw) { super(x, yzw.x(), yzw.y(), yzw.z()); }
	public Vec4(Vec2 xy, Vec2 zw) { super(xy.x(), xy.y(), zw.x(), zw.y()); }
	public Vec4(Vec2 xy, double z, double w) { super(xy.x(), xy.y(), z, w); }
	public Vec4(double x, Vec2 yz, double w) { super(x, yz.x(), yz.y(), w); }
	public Vec4(double x, double y, Vec2 zw) { super(x, y, zw.x(), zw.y()); }

	@Override protected double getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			case 'z': case 'b': case 'p': return d[2];
			case 'w': case 'a': case 'q': return d[3];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(double x, double y, double z, double w) { x(x); y(y); z(z); w(w); }

	public double x() { return d[0]; }
	public double y() { return d[1]; }
	public double z() { return d[2]; }
	public double w() { return d[3]; }
	public void x(double x) { d[0] = x; }
	public void y(double y) { d[1] = y; }
	public void z(double z) { d[2] = z; }
	public void w(double w) { d[3] = w; }

	public double r() { return d[0]; }
	public double g() { return d[1]; }
	public double b() { return d[2]; }
	public double a() { return d[3]; }
	public void r(double r) { d[0] = r; }
	public void g(double g) { d[1] = g; }
	public void b(double b) { d[2] = b; }
	public void a(double a) { d[3] = a; }

	public double s() { return d[0]; }
	public double t() { return d[1]; }
	public double p() { return d[2]; }
	public double q() { return d[3]; }
	public void s(double s) { d[0] = s; }
	public void t(double t) { d[1] = t; }
	public void p(double p) { d[2] = p; }
	public void q(double q) { d[3] = q; }
}
