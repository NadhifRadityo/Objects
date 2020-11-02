package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class LMat2 extends LMatNxN {
	public LMat2(boolean copy, long... x) { super(copy, x); if(copy && x.length != 4) throw new IllegalArgumentException(); }
	public LMat2(long... x) { this(true, x); }
	public LMat2(long d11, long d12, long d21, long d22) { super(d11, d12, d21, d22); }
	public LMat2(long a) { super(4, a); }
	public LMat2() { super(4); }

	public long d11() { return d[0]; }
	public long d12() { return d[1]; }
	public long d21() { return d[2]; }
	public long d22() { return d[3]; }
	public void d11(long d11) { d[0] = d11; }
	public void d12(long d12) { d[1] = d12; }
	public void d21(long d21) { d[2] = d21; }
	public void d22(long d22) { d[3] = d22; }
}
