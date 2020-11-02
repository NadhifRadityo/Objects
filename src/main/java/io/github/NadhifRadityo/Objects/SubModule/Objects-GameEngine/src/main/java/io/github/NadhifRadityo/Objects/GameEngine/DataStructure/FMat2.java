package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class FMat2 extends FMatNxN {
	public FMat2(boolean copy, float... x) { super(copy, x); if(copy && x.length != 4) throw new IllegalArgumentException(); }
	public FMat2(float... x) { this(true, x); }
	public FMat2(float d11, float d12, float d21, float d22) { super(d11, d12, d21, d22); }
	public FMat2(float a) { super(2, a); }
	public FMat2() { super(2); }

	public float d11() { return d[0]; }
	public float d12() { return d[1]; }
	public float d21() { return d[2]; }
	public float d22() { return d[3]; }
	public void d11(float d11) { d[0] = d11; }
	public void d12(float d12) { d[1] = d12; }
	public void d21(float d21) { d[2] = d21; }
	public void d22(float d22) { d[3] = d22; }
}
