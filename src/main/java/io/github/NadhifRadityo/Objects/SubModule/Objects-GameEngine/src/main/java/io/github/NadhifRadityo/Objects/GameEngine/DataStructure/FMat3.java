package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class FMat3 extends FMatNxN {
	public FMat3(boolean copy, float... x) { super(copy, x); if(copy && x.length != 9) throw new IllegalArgumentException(); }
	public FMat3(float... x) { this(true, x); }
	public FMat3(float d11, float d12, float d13,
				 float d21, float d22, float d23,
				 float d31, float d32, float d33) { super(d11, d12, d13, d21, d22, d23, d31, d32, d33); }
	public FMat3(float a) { super(3, a); }
	public FMat3() { super(3); }

	public float d11() { return d[0]; }
	public float d12() { return d[1]; }
	public float d13() { return d[2]; }
	public float d21() { return d[3]; }
	public float d22() { return d[4]; }
	public float d23() { return d[5]; }
	public float d31() { return d[6]; }
	public float d32() { return d[7]; }
	public float d33() { return d[8]; }
	public void d11(float d11) { d[0] = d11; }
	public void d12(float d12) { d[1] = d12; }
	public void d13(float d13) { d[2] = d13; }
	public void d21(float d21) { d[3] = d21; }
	public void d22(float d22) { d[4] = d22; }
	public void d23(float d23) { d[5] = d23; }
	public void d31(float d31) { d[6] = d31; }
	public void d32(float d32) { d[7] = d32; }
	public void d33(float d33) { d[8] = d33; }
}
