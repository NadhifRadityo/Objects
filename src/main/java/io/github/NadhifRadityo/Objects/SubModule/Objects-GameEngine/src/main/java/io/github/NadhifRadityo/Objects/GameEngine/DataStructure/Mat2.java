package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class Mat2 extends MatNxN {
	public Mat2(boolean copy, double... x) { super(copy, x); if(copy && x.length != 4) throw new IllegalArgumentException(); }
	public Mat2(double... x) { this(true, x); }
	public Mat2(double d11, double d12, double d21, double d22) { super(d11, d12, d21, d22); }
	public Mat2(double a) { super(2, a); }
	public Mat2() { super(2); }

	public double d11() { return d[0]; }
	public double d12() { return d[1]; }
	public double d21() { return d[2]; }
	public double d22() { return d[3]; }
	public void d11(double d11) { d[0] = d11; }
	public void d12(double d12) { d[1] = d12; }
	public void d21(double d21) { d[2] = d21; }
	public void d22(double d22) { d[3] = d22; }
}
