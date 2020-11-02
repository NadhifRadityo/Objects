package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class SVec4 extends SVecN {
	public SVec4(boolean copy, short... x) { super(copy, x); if(copy && x.length != 4) throw new IllegalArgumentException(); }
	public SVec4(short... x) { this(true, x); }
	public SVec4(short x, short y, short z, short w) { super(x, y, z, w); }
	public SVec4(short a) { super(4, a); }
	public SVec4() { super(4); }

	public SVec4(SVec3 xyz, short w) { super(xyz.x(), xyz.y(), xyz.z(), w); }
	public SVec4(short x, SVec3 yzw) { super(x, yzw.x(), yzw.y(), yzw.z()); }
	public SVec4(SVec2 xy, SVec2 zw) { super(xy.x(), xy.y(), zw.x(), zw.y()); }
	public SVec4(SVec2 xy, short z, short w) { super(xy.x(), xy.y(), z, w); }
	public SVec4(short x, SVec2 yz, short w) { super(x, yz.x(), yz.y(), w); }
	public SVec4(short x, short y, SVec2 zw) { super(x, y, zw.x(), zw.y()); }

	@Override protected short getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			case 'z': case 'b': case 'p': return d[2];
			case 'w': case 'a': case 'q': return d[3];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(short x, short y, short z, short w) { x(x); y(y); z(z); w(w); }

	public short x() { return d[0]; }
	public short y() { return d[1]; }
	public short z() { return d[2]; }
	public short w() { return d[3]; }
	public void x(short x) { d[0] = x; }
	public void y(short y) { d[1] = y; }
	public void z(short z) { d[2] = z; }
	public void w(short w) { d[3] = w; }

	public short r() { return d[0]; }
	public short g() { return d[1]; }
	public short b() { return d[2]; }
	public short a() { return d[3]; }
	public void r(short r) { d[0] = r; }
	public void g(short g) { d[1] = g; }
	public void b(short b) { d[2] = b; }
	public void a(short a) { d[3] = a; }

	public short s() { return d[0]; }
	public short t() { return d[1]; }
	public short p() { return d[2]; }
	public short q() { return d[3]; }
	public void s(short s) { d[0] = s; }
	public void t(short t) { d[1] = t; }
	public void p(short p) { d[2] = p; }
	public void q(short q) { d[3] = q; }
}
