package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sqrt;

public class MatNxN extends MatMxN {
	public MatNxN(boolean copy, double... x) { super(copy, (int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public MatNxN(double... x) { this(true, x); }
	public MatNxN(float... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public MatNxN(long... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public MatNxN(int... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public MatNxN(short... x) { super((int) sqrt(x.length), (int) sqrt(x.length), x); if(sqrt(x.length) % 1 != 0) throw new IllegalArgumentException(); }
	public MatNxN(int n, double... x) { super(n, n, x); }
	public MatNxN(int n, double x) { super(n, n, x); }
	public MatNxN(int n) { super(n, n); }
}
