package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class FMat4 extends FMatNxN {
	public FMat4(boolean copy, float... x) { super(copy, x); if(copy && x.length != 16) throw new IllegalArgumentException(); }
	public FMat4(float... x) { this(true, x); }
	public FMat4(float d11, float d12, float d13, float d14,
				 float d21, float d22, float d23, float d24,
				 float d31, float d32, float d33, float d34,
				 float d41, float d42, float d43, float d44) { super(d11, d12, d13, d14, d21, d22, d23, d24, d31, d32, d33, d34, d41, d42, d43, d44); }
	public FMat4(float a) { super(4, a); }
	public FMat4() { super(4); }

	public float d11() { return d[0]; }
	public float d12() { return d[1]; }
	public float d13() { return d[2]; }
	public float d14() { return d[3]; }
	public float d21() { return d[4]; }
	public float d22() { return d[5]; }
	public float d23() { return d[6]; }
	public float d24() { return d[7]; }
	public float d31() { return d[8]; }
	public float d32() { return d[9]; }
	public float d33() { return d[10]; }
	public float d34() { return d[11]; }
	public float d41() { return d[12]; }
	public float d42() { return d[13]; }
	public float d43() { return d[14]; }
	public float d44() { return d[15]; }
	public void d11(float d11) { d[0] = d11; }
	public void d12(float d12) { d[1] = d12; }
	public void d13(float d13) { d[2] = d13; }
	public void d14(float d14) { d[3] = d14; }
	public void d21(float d21) { d[4] = d21; }
	public void d22(float d22) { d[5] = d22; }
	public void d23(float d23) { d[6] = d23; }
	public void d24(float d24) { d[7] = d24; }
	public void d31(float d31) { d[8] = d31; }
	public void d32(float d32) { d[9] = d32; }
	public void d33(float d33) { d[10] = d33; }
	public void d34(float d34) { d[11] = d34; }
	public void d41(float d41) { d[12] = d41; }
	public void d42(float d42) { d[13] = d42; }
	public void d43(float d43) { d[14] = d43; }
	public void d44(float d44) { d[15] = d44; }
}
