package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class LMat3 extends LMatNxN {
	public LMat3(boolean copy, long... x) { super(copy, x); if(copy && x.length != 9) throw new IllegalArgumentException(); }
	public LMat3(long... x) { this(true, x); }
	public LMat3(long d11, long d12, long d13,
				 long d21, long d22, long d23,
				 long d31, long d32, long d33) { super(d11, d12, d13, d21, d22, d23, d31, d32, d33); }
	public LMat3(long a) { super(9, a); }
	public LMat3() { super(9); }

	public long d11() { return d[0]; }
	public long d12() { return d[1]; }
	public long d13() { return d[2]; }
	public long d21() { return d[3]; }
	public long d22() { return d[4]; }
	public long d23() { return d[5]; }
	public long d31() { return d[6]; }
	public long d32() { return d[7]; }
	public long d33() { return d[8]; }
	public void d11(long d11) { d[0] = d11; }
	public void d12(long d12) { d[1] = d12; }
	public void d13(long d13) { d[2] = d13; }
	public void d21(long d21) { d[3] = d21; }
	public void d22(long d22) { d[4] = d22; }
	public void d23(long d23) { d[5] = d23; }
	public void d31(long d31) { d[6] = d31; }
	public void d32(long d32) { d[7] = d32; }
	public void d33(long d33) { d[8] = d33; }
}
