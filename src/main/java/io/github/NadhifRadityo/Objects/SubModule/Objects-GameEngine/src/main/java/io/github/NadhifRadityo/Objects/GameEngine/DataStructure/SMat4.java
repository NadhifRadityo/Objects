package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class SMat4 extends SMatNxN {
	public SMat4(boolean copy, short... x) { super(copy, x); if(copy && x.length != 16) throw new IllegalArgumentException(); }
	public SMat4(short... x) { this(true, x); }
	public SMat4(short d11, short d12, short d13, short d14,
				 short d21, short d22, short d23, short d24,
				 short d31, short d32, short d33, short d34,
				 short d41, short d42, short d43, short d44) { super(d11, d12, d13, d14, d21, d22, d23, d24, d31, d32, d33, d34, d41, d42, d43, d44); }
	public SMat4(short a) { super(4, a); }
	public SMat4() { super(4); }

	public short d11() { return d[0]; }
	public short d12() { return d[1]; }
	public short d13() { return d[2]; }
	public short d14() { return d[3]; }
	public short d21() { return d[4]; }
	public short d22() { return d[5]; }
	public short d23() { return d[6]; }
	public short d24() { return d[7]; }
	public short d31() { return d[8]; }
	public short d32() { return d[9]; }
	public short d33() { return d[10]; }
	public short d34() { return d[11]; }
	public short d41() { return d[12]; }
	public short d42() { return d[13]; }
	public short d43() { return d[14]; }
	public short d44() { return d[15]; }
	public void d11(short d11) { d[0] = d11; }
	public void d12(short d12) { d[1] = d12; }
	public void d13(short d13) { d[2] = d13; }
	public void d14(short d14) { d[3] = d14; }
	public void d21(short d21) { d[4] = d21; }
	public void d22(short d22) { d[5] = d22; }
	public void d23(short d23) { d[6] = d23; }
	public void d24(short d24) { d[7] = d24; }
	public void d31(short d31) { d[8] = d31; }
	public void d32(short d32) { d[9] = d32; }
	public void d33(short d33) { d[10] = d33; }
	public void d34(short d34) { d[11] = d34; }
	public void d41(short d41) { d[12] = d41; }
	public void d42(short d42) { d[13] = d42; }
	public void d43(short d43) { d[14] = d43; }
	public void d44(short d44) { d[15] = d44; }
}
