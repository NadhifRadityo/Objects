package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sqrt;

public class IMatNxN extends IMatMxN {
	public IMatNxN(boolean copy, int... x) { super(copy, (int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public IMatNxN(int... x) { this(true, x); }
	public IMatNxN(long... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public IMatNxN(short... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public IMatNxN(double... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public IMatNxN(float... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public IMatNxN(int n, int... x) { super(n, n, x); }
	public IMatNxN(int n, int x) { super(n, n, x); }
	public IMatNxN(int n) { super(n, n); }
}
