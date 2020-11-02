package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class LMat4 extends LMatNxN {
	public LMat4(boolean copy, long... x) { super(copy, x); if(copy && x.length != 16) throw new IllegalArgumentException(); }
	public LMat4(long... x) { this(true, x); }
	public LMat4(long d11, long d12, long d13, long d14,
				 long d21, long d22, long d23, long d24,
				 long d31, long d32, long d33, long d34,
				 long d41, long d42, long d43, long d44) { super(d11, d12, d13, d14, d21, d22, d23, d24, d31, d32, d33, d34, d41, d42, d43, d44); }
	public LMat4(long a) { super(16, a); }
	public LMat4() { super(16); }

	public long d11() { return d[0]; }
	public long d12() { return d[1]; }
	public long d13() { return d[2]; }
	public long d14() { return d[3]; }
	public long d21() { return d[4]; }
	public long d22() { return d[5]; }
	public long d23() { return d[6]; }
	public long d24() { return d[7]; }
	public long d31() { return d[8]; }
	public long d32() { return d[9]; }
	public long d33() { return d[10]; }
	public long d34() { return d[11]; }
	public long d41() { return d[12]; }
	public long d42() { return d[13]; }
	public long d43() { return d[14]; }
	public long d44() { return d[15]; }
	public void d11(long d11) { d[0] = d11; }
	public void d12(long d12) { d[1] = d12; }
	public void d13(long d13) { d[2] = d13; }
	public void d14(long d14) { d[3] = d14; }
	public void d21(long d21) { d[4] = d21; }
	public void d22(long d22) { d[5] = d22; }
	public void d23(long d23) { d[6] = d23; }
	public void d24(long d24) { d[7] = d24; }
	public void d31(long d31) { d[8] = d31; }
	public void d32(long d32) { d[9] = d32; }
	public void d33(long d33) { d[10] = d33; }
	public void d34(long d34) { d[11] = d34; }
	public void d41(long d41) { d[12] = d41; }
	public void d42(long d42) { d[13] = d42; }
	public void d43(long d43) { d[14] = d43; }
	public void d44(long d44) { d[15] = d44; }
}
