package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class Mat3 extends MatNxN {
	public Mat3(boolean copy, double... x) { super(copy, x); if(copy && x.length != 9) throw new IllegalArgumentException(); }
	public Mat3(double... x) { this(true, x); }
	public Mat3(double d11, double d12, double d13,
				double d21, double d22, double d23,
				double d31, double d32, double d33) { super(d11, d12, d13, d21, d22, d23, d31, d32, d33); }
	public Mat3(double a) { super(3, a); }
	public Mat3() { super(3); }

	public double d11() { return d[0]; }
	public double d12() { return d[1]; }
	public double d13() { return d[2]; }
	public double d21() { return d[3]; }
	public double d22() { return d[4]; }
	public double d23() { return d[5]; }
	public double d31() { return d[6]; }
	public double d32() { return d[7]; }
	public double d33() { return d[8]; }
	public void d11(double d11) { d[0] = d11; }
	public void d12(double d12) { d[1] = d12; }
	public void d13(double d13) { d[2] = d13; }
	public void d21(double d21) { d[3] = d21; }
	public void d22(double d22) { d[4] = d22; }
	public void d23(double d23) { d[5] = d23; }
	public void d31(double d31) { d[6] = d31; }
	public void d32(double d32) { d[7] = d32; }
	public void d33(double d33) { d[8] = d33; }
}
