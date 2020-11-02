package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class SMat3 extends SMatNxN {
	public SMat3(boolean copy, short... x) { super(copy, x); if(copy && x.length != 9) throw new IllegalArgumentException(); }
	public SMat3(short... x) { this(true, x); }
	public SMat3(short d11, short d12, short d13,
				 short d21, short d22, short d23,
				 short d31, short d32, short d33) { super(d11, d12, d13, d21, d22, d23, d31, d32, d33); }
	public SMat3(short a) { super(3, a); }
	public SMat3() { super(3); }

	public short d11() { return d[0]; }
	public short d12() { return d[1]; }
	public short d13() { return d[2]; }
	public short d21() { return d[3]; }
	public short d22() { return d[4]; }
	public short d23() { return d[5]; }
	public short d31() { return d[6]; }
	public short d32() { return d[7]; }
	public short d33() { return d[8]; }
	public void d11(short d11) { d[0] = d11; }
	public void d12(short d12) { d[1] = d12; }
	public void d13(short d13) { d[2] = d13; }
	public void d21(short d21) { d[3] = d21; }
	public void d22(short d22) { d[4] = d22; }
	public void d23(short d23) { d[5] = d23; }
	public void d31(short d31) { d[6] = d31; }
	public void d32(short d32) { d[7] = d32; }
	public void d33(short d33) { d[8] = d33; }
}
