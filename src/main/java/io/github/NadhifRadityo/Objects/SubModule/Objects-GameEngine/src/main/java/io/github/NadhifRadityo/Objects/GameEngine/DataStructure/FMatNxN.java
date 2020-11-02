package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sqrt;

public class FMatNxN extends FMatMxN {
	public FMatNxN(boolean copy, float... x) { super(copy, (int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public FMatNxN(float... x) { this(true, x); }
	public FMatNxN(double... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public FMatNxN(long... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public FMatNxN(int... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public FMatNxN(short... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public FMatNxN(int n, float... x) { super(n, n, x); }
	public FMatNxN(int n, float x) { super(n, n, x); }
	public FMatNxN(int n) { super(n, n); }
}
