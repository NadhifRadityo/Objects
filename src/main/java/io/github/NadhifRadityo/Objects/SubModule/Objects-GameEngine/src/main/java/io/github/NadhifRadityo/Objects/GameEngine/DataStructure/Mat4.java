package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class Mat4 extends MatNxN {
	public Mat4(boolean copy, double... x) { super(copy, x); if(copy && x.length != 16) throw new IllegalArgumentException(); }
	public Mat4(double... x) { this(true, x); }
	public Mat4(double d11, double d12, double d13, double d14,
				double d21, double d22, double d23, double d24,
				double d31, double d32, double d33, double d34,
				double d41, double d42, double d43, double d44) { super(d11, d12, d13, d14, d21, d22, d23, d24, d31, d32, d33, d34, d41, d42, d43, d44); }
	public Mat4(double a) { super(4, a); }
	public Mat4() { super(4); }

	public double d11() { return d[0]; }
	public double d12() { return d[1]; }
	public double d13() { return d[2]; }
	public double d14() { return d[3]; }
	public double d21() { return d[4]; }
	public double d22() { return d[5]; }
	public double d23() { return d[6]; }
	public double d24() { return d[7]; }
	public double d31() { return d[8]; }
	public double d32() { return d[9]; }
	public double d33() { return d[10]; }
	public double d34() { return d[11]; }
	public double d41() { return d[12]; }
	public double d42() { return d[13]; }
	public double d43() { return d[14]; }
	public double d44() { return d[15]; }
	public void d11(double d11) { d[0] = d11; }
	public void d12(double d12) { d[1] = d12; }
	public void d13(double d13) { d[2] = d13; }
	public void d14(double d14) { d[3] = d14; }
	public void d21(double d21) { d[4] = d21; }
	public void d22(double d22) { d[5] = d22; }
	public void d23(double d23) { d[6] = d23; }
	public void d24(double d24) { d[7] = d24; }
	public void d31(double d31) { d[8] = d31; }
	public void d32(double d32) { d[9] = d32; }
	public void d33(double d33) { d[10] = d33; }
	public void d34(double d34) { d[11] = d34; }
	public void d41(double d41) { d[12] = d41; }
	public void d42(double d42) { d[13] = d42; }
	public void d43(double d43) { d[14] = d43; }
	public void d44(double d44) { d[15] = d44; }
}
