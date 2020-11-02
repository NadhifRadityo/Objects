package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sqrt;

public class LMatNxN extends LMatMxN {
	public LMatNxN(boolean copy, long... x) { super(copy, (int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public LMatNxN(long... x) { this(true, x); }
	public LMatNxN(int... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public LMatNxN(short... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public LMatNxN(double... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public LMatNxN(float... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public LMatNxN(int n, long... x) { super(n, n, x); }
	public LMatNxN(int n, long x) { super(n, n, x); }
	public LMatNxN(int n) { super(n, n); }
}
