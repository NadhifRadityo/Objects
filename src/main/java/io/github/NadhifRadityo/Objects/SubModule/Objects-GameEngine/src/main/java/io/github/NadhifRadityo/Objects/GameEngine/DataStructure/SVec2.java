package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class SVec2 extends SVecN {
	public SVec2(boolean copy, short... x) { super(copy, x); if(copy && x.length != 2) throw new IllegalArgumentException(); }
	public SVec2(short... x) { this(true, x); }
	public SVec2(short x, short y) { super(new short[] { x, y }); }
	public SVec2(short a) { super(2, a); }
	public SVec2() { super(2); }

	@Override protected short getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			case 'y': case 'g': case 't': return d[1];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(short x, short y) { x(x); y(y); }

	public short x() { return d[0]; }
	public short y() { return d[1]; }
	public void x(short x) { d[0] = x; }
	public void y(short y) { d[1] = y; }

	public short r() { return d[0]; }
	public short g() { return d[1]; }
	public void r(short r) { d[0] = r; }
	public void g(short g) { d[1] = g; }

	public short s() { return d[0]; }
	public short t() { return d[1]; }
	public void s(short s) { d[0] = s; }
	public void t(short t) { d[1] = t; }
}
