package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class IMat4 extends IMatNxN {
	public IMat4(boolean copy, int... x) { super(copy, x); if(copy && x.length != 16) throw new IllegalArgumentException(); }
	public IMat4(int... x) { this(true, x); }
	public IMat4(int d11, int d12, int d13, int d14,
				 int d21, int d22, int d23, int d24,
				 int d31, int d32, int d33, int d34,
				 int d41, int d42, int d43, int d44) { super(new int[] { d11, d12, d13, d14, d21, d22, d23, d24, d31, d32, d33, d34, d41, d42, d43, d44 }); }
	public IMat4(int a) { super(4, a); }
	public IMat4() { super(4); }

	public int d11() { return d[0]; }
	public int d12() { return d[1]; }
	public int d13() { return d[2]; }
	public int d14() { return d[3]; }
	public int d21() { return d[4]; }
	public int d22() { return d[5]; }
	public int d23() { return d[6]; }
	public int d24() { return d[7]; }
	public int d31() { return d[8]; }
	public int d32() { return d[9]; }
	public int d33() { return d[10]; }
	public int d34() { return d[11]; }
	public int d41() { return d[12]; }
	public int d42() { return d[13]; }
	public int d43() { return d[14]; }
	public int d44() { return d[15]; }
	public void d11(int d11) { d[0] = d11; }
	public void d12(int d12) { d[1] = d12; }
	public void d13(int d13) { d[2] = d13; }
	public void d14(int d14) { d[3] = d14; }
	public void d21(int d21) { d[4] = d21; }
	public void d22(int d22) { d[5] = d22; }
	public void d23(int d23) { d[6] = d23; }
	public void d24(int d24) { d[7] = d24; }
	public void d31(int d31) { d[8] = d31; }
	public void d32(int d32) { d[9] = d32; }
	public void d33(int d33) { d[10] = d33; }
	public void d34(int d34) { d[11] = d34; }
	public void d41(int d41) { d[12] = d41; }
	public void d42(int d42) { d[13] = d42; }
	public void d43(int d43) { d[14] = d43; }
	public void d44(int d44) { d[15] = d44; }
}
