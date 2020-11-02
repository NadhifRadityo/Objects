package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class SVec3 extends SVecN {
	public SVec3(boolean copy, short... x) { super(copy, x); if(copy && x.length != 3) throw new IllegalArgumentException(); }
	public SVec3(short... x) { this(true, x); }
	public SVec3(short x, short y, short z) { super(x, y, z); }
	public SVec3(short a) { super(3, a); }
	public SVec3() { super(3); }

	public SVec3(SVec2 xy, short z) { super(xy.x(), xy.y(), z); }
	public SVec3(short x, SVec2 yz) { super(x, yz.x(), yz.y()); }

	@Override protected short getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			case 'z': case 'b': case 'p': return d[2];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(short x, short y, short z) { x(x); y(y); z(z); }

	public short x() { return d[0]; }
	public short y() { return d[1]; }
	public short z() { return d[2]; }
	public void x(short x) { d[0] = x; }
	public void y(short y) { d[1] = y; }
	public void z(short z) { d[2] = z; }

	public short r() { return d[0]; }
	public short g() { return d[1]; }
	public short b() { return d[2]; }
	public void r(short r) { d[0] = r; }
	public void g(short g) { d[1] = g; }
	public void b(short b) { d[2] = b; }

	public short s() { return d[0]; }
	public short t() { return d[1]; }
	public short p() { return d[2]; }
	public void s(short s) { d[0] = s; }
	public void t(short t) { d[1] = t; }
	public void p(short p) { d[2] = p; }
}
