package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class SMat2 extends SMatNxN {
	public SMat2(boolean copy, short... x) { super(copy, x); if(copy && x.length != 4) throw new IllegalArgumentException(); }
	public SMat2(short... x) { this(true, x); }
	public SMat2(short d11, short d12, short d21, short d22) { super(d11, d12, d21, d22); }
	public SMat2(short a) { super(2, a); }
	public SMat2() { super(2); }

	public short d11() { return d[0]; }
	public short d12() { return d[1]; }
	public short d21() { return d[2]; }
	public short d22() { return d[3]; }
	public void d11(short d11) { d[0] = d11; }
	public void d12(short d12) { d[1] = d12; }
	public void d21(short d21) { d[2] = d21; }
	public void d22(short d22) { d[3] = d22; }
}
