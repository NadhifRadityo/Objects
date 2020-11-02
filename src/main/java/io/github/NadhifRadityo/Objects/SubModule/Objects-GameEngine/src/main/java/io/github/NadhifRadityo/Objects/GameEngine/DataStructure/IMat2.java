package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class IMat2 extends IMatNxN {
	public IMat2(boolean copy, int... x) { super(copy, x); if(copy && x.length != 4) throw new IllegalArgumentException(); }
	public IMat2(int... x) { this(true, x); }
	public IMat2(int d11, int d12, int d21, int d22) { super(new int[] { d11, d12, d21, d22 }); }
	public IMat2(int a) { super(2, a); }
	public IMat2() { super(2); }

	public int d11() { return d[0]; }
	public int d12() { return d[1]; }
	public int d21() { return d[2]; }
	public int d22() { return d[3]; }
	public void d11(int d11) { d[0] = d11; }
	public void d12(int d12) { d[1] = d12; }
	public void d21(int d21) { d[2] = d21; }
	public void d22(int d22) { d[3] = d22; }
}
