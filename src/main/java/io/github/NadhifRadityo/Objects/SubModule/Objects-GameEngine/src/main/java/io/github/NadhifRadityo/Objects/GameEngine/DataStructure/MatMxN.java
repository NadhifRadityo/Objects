package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils;

import java.util.Arrays;

public class MatMxN extends GenType {
	public final int m;
	public final int n;
	public final double[] d;

	public MatMxN(boolean copy, int m, int n, double... x) { super(m * n); this.m = m; this.n = n; d = copy ? new double[size] : x; if(copy) System.arraycopy(x, 0, d, 0, Math.min(x.length, size)); }
	public MatMxN(int m, int n, double... x) { this(true, m, n, x); }
	public MatMxN(int m, int n, float... x) { super(m * n); this.m = m; this.n = n; d = new double[size]; for(int i = 0; i < size; i++) d[i] = (double) x[i]; }
	public MatMxN(int m, int n, long... x) { super(m * n); this.m = m; this.n = n; d = new double[size]; for(int i = 0; i < size; i++) d[i] = (double) x[i]; }
	public MatMxN(int m, int n, int... x) { super(m * n); this.m = m; this.n = n; d = new double[size]; for(int i = 0; i < size; i++) d[i] = (double) x[i]; }
	public MatMxN(int m, int n, short... x) { super(m * n); this.m = m; this.n = n; d = new double[size]; for(int i = 0; i < size; i++) d[i] = (double) x[i]; }
	public MatMxN(int m, int n, double x) { super(m * n); this.m = m; this.n = n; d = new double[size]; Arrays.fill(d, x); }
	public MatMxN(int m, int n) { super(m * n); this.m = m; this.n = n; d = new double[size]; }

	@Override public Object getVectorAt(int i) { return d[i]; }
	@Override public void setVectorAt(int i, Object object) { d[i] = object == null ? 0 : (double) object; }
	public int getIndex(int y, int x) { return getIndex0(y - 1, x - 1); }
	public int getIndex0(int y, int x) { ArrayUtils.assertCopyIndex(y, m, x, n, 1); return y * n + x; }

	public Vec2 vec2r(int row) { if(n != 2) throw new IllegalArgumentException(); return GenTypeUtils.vec2(d[getIndex0(row, 0)], d[getIndex0(row, 1)]); }
	public Vec3 vec3r(int row) { if(n != 3) throw new IllegalArgumentException(); return GenTypeUtils.vec3(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)]); }
	public Vec4 vec4r(int row) { if(n != 4) throw new IllegalArgumentException(); return GenTypeUtils.vec4(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)], d[getIndex0(row, 3)]); }
	public VecN vecnr(int row) { VecN result = GenTypeUtils.vecn(n); System.arraycopy(d, row * n, result.d, 0, n); return result; }
	public Vec2 vec2c(int col) { if(m != 2) throw new IllegalArgumentException(); return GenTypeUtils.vec2(d[getIndex0(0, col)], d[getIndex0(1, col)]); }
	public Vec3 vec3c(int col) { if(m != 3) throw new IllegalArgumentException(); return GenTypeUtils.vec3(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)]); }
	public Vec4 vec4c(int col) { if(m != 4) throw new IllegalArgumentException(); return GenTypeUtils.vec4(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)], d[getIndex0(3, col)]); }
	public VecN vecnc(int col) { VecN result = GenTypeUtils.vecn(m); for(int i = 0; i < n; i++) result.d[i] = d[col + n * i]; return result; }
	public VecN vecn() { return GenTypeUtils.vecn(d); }

	public Vec2 vec2rx(int row, Vec2 x) { if(n != 2) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)]); return x; }
	public Vec3 vec3rx(int row, Vec3 x) { if(n != 3) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)]); return x; }
	public Vec4 vec4rx(int row, Vec4 x) { if(n != 4) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)], d[getIndex0(row, 3)]); return x; }
	public VecN vecnrx(int row, VecN x) { System.arraycopy(d, row * n, x.d, 0, n); return x; }
	public Vec2 vec2cx(int col, Vec2 x) { if(m != 2) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)]); return x; }
	public Vec3 vec3cx(int col, Vec3 x) { if(m != 3) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)]); return x; }
	public Vec4 vec4cx(int col, Vec4 x) { if(m != 4) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)], d[getIndex0(3, col)]); return x; }
	public VecN vecncx(int col, VecN x) { for(int i = 0; i < m; i++) x.d[i] = d[col + n * i]; return x; }
	public VecN vecnx(VecN x) { if(x.size != d.length) throw new IllegalArgumentException(); System.arraycopy(d, 0, x.d, 0, d.length); return x; }

	@Override public int hashCode() {
		long bits = 7L;
		for(double x : d) bits = 31L * bits + Double.doubleToLongBits(x);
		return (int) (bits ^ (bits >> 32));
	}
	@Override public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof MatMxN)
			return Arrays.equals(d, ((MatMxN) obj).d);
		return false;
	}
}
