package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class IMat3 extends IMatNxN {
	public IMat3(boolean copy, int... x) { super(copy, x); if(copy && x.length != 9) throw new IllegalArgumentException(); }
	public IMat3(int... x) { this(true, x); }
	public IMat3(int d11, int d12, int d13,
				 int d21, int d22, int d23,
				 int d31, int d32, int d33) { super(new int[] { d11, d12, d13, d21, d22, d23, d31, d32, d33 }); }
	public IMat3(int a) { super(3, a); }
	public IMat3() { super(3); }

	public int d11() { return d[0]; }
	public int d12() { return d[1]; }
	public int d13() { return d[2]; }
	public int d21() { return d[3]; }
	public int d22() { return d[4]; }
	public int d23() { return d[5]; }
	public int d31() { return d[6]; }
	public int d32() { return d[7]; }
	public int d33() { return d[8]; }
	public void d11(int d11) { d[0] = d11; }
	public void d12(int d12) { d[1] = d12; }
	public void d13(int d13) { d[2] = d13; }
	public void d21(int d21) { d[3] = d21; }
	public void d22(int d22) { d[4] = d22; }
	public void d23(int d23) { d[5] = d23; }
	public void d31(int d31) { d[6] = d31; }
	public void d32(int d32) { d[7] = d32; }
	public void d33(int d33) { d[8] = d33; }
}
