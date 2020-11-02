package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class SVec1 extends SVecN {
	public SVec1(boolean copy, short... x) { super(copy, x); if(copy && x.length != 1) throw new IllegalArgumentException(); }
	public SVec1(short... x) { this(true, x); }
	public SVec1(short x) { super(x); }
	public SVec1() { super(1); }

	@Override protected short getAlias(char charAt) {
		switch(charAt) {
			case 'x': case 'r': case 's': return d[0];
			default: throw new IllegalArgumentException();
		}
	}

	public void set(short x, short y) { x(x); }

	public short x() { return d[0]; }
	public void x(short x) { d[0] = x; }

	public short r() { return d[0]; }
	public void r(short r) { d[0] = r; }

	public short s() { return d[0]; }
	public void s(short s) { d[0] = s; }
}
