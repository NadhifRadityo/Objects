package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sqrt;

public class SMatNxN extends SMatMxN {
	public SMatNxN(boolean copy, short... x) { super(copy, (int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public SMatNxN(short... x) { this(true, x); }
	public SMatNxN(long... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public SMatNxN(int... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public SMatNxN(double... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public SMatNxN(float... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public SMatNxN(int n, short... x) { super(n, n, x); }
	public SMatNxN(int n, short x) { super(n, n, x); }
	public SMatNxN(int n) { super(n, n); }
}
