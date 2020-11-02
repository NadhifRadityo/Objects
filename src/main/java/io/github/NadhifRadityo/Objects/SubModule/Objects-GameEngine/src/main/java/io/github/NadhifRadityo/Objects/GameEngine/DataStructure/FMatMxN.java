package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils;

import java.util.Arrays;

public class FMatMxN extends GenType {
	public final int m;
	public final int n;
	public final float[] d;

	public FMatMxN(boolean copy, int m, int n, float... x) { super(m * n); this.m = m; this.n = n; d = copy ? new float[size] : x; if(copy) System.arraycopy(x, 0, d, 0, Math.min(x.length, size)); }
	public FMatMxN(int m, int n, float... x) { this(true, m, n, x); }
	public FMatMxN(int m, int n, double... x) { super(m * n); this.m = m; this.n = n; d = new float[size]; for(int i = 0; i < size; i++) d[i] = (float) x[i]; }
	public FMatMxN(int m, int n, long... x) { super(m * n); this.m = m; this.n = n; d = new float[size]; for(int i = 0; i < size; i++) d[i] = (float) x[i]; }
	public FMatMxN(int m, int n, int... x) { super(m * n); this.m = m; this.n = n; d = new float[size]; for(int i = 0; i < size; i++) d[i] = (float) x[i]; }
	public FMatMxN(int m, int n, short... x) { super(m * n); this.m = m; this.n = n; d = new float[size]; for(int i = 0; i < size; i++) d[i] = (float) x[i]; }
	public FMatMxN(int m, int n, float x) { super(m * n); this.m = m; this.n = n; d = new float[size]; Arrays.fill(d, x); }
	public FMatMxN(int m, int n) { super(m * n); this.m = m; this.n = n; d = new float[size]; }

	@Override public Object getVectorAt(int i) { return d[i]; }
	@Override public void setVectorAt(int i, Object object) { d[i] = object == null ? 0 : (float) object; }
	public int getIndex(int y, int x) { return getIndex0(y - 1, x - 1); }
	public int getIndex0(int y, int x) { ArrayUtils.assertCopyIndex(y, m, x, n, 1); return y * n + x; }

	public FVec2 fvec2r(int row) { if(n != 2) throw new IllegalArgumentException(); return GenTypeUtils.fvec2(d[getIndex0(row, 0)], d[getIndex0(row, 1)]); }
	public FVec3 fvec3r(int row) { if(n != 3) throw new IllegalArgumentException(); return GenTypeUtils.fvec3(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)]); }
	public FVec4 fvec4r(int row) { if(n != 4) throw new IllegalArgumentException(); return GenTypeUtils.fvec4(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)], d[getIndex0(row, 3)]); }
	public FVecN fvecnr(int row) { FVecN result = GenTypeUtils.fvecn(n); System.arraycopy(d, row * n, result.d, 0, n); return result; }
	public FVec2 fvec2c(int col) { if(m != 2) throw new IllegalArgumentException(); return GenTypeUtils.fvec2(d[getIndex0(0, col)], d[getIndex0(1, col)]); }
	public FVec3 fvec3c(int col) { if(m != 3) throw new IllegalArgumentException(); return GenTypeUtils.fvec3(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)]); }
	public FVec4 fvec4c(int col) { if(m != 4) throw new IllegalArgumentException(); return GenTypeUtils.fvec4(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)], d[getIndex0(3, col)]); }
	public FVecN fvecnc(int col) { FVecN result = GenTypeUtils.fvecn(m); for(int i = 0; i < n; i++) result.d[i] = d[col + n * i]; return result; }
	public FVecN fvecn() { return GenTypeUtils.fvecn(d); }

	public FVec2 fvec2rx(int row, FVec2 x) { if(n != 2) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)]); return x; }
	public FVec3 fvec3rx(int row, FVec3 x) { if(n != 3) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)]); return x; }
	public FVec4 fvec4rx(int row, FVec4 x) { if(n != 4) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)], d[getIndex0(row, 3)]); return x; }
	public FVecN fvecnrx(int row, FVecN x) { System.arraycopy(d, row * n, x.d, 0, n); return x; }
	public FVec2 fvec2cx(int col, FVec2 x) { if(m != 2) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)]); return x; }
	public FVec3 fvec3cx(int col, FVec3 x) { if(m != 3) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)]); return x; }
	public FVec4 fvec4cx(int col, FVec4 x) { if(m != 4) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)], d[getIndex0(3, col)]); return x; }
	public FVecN fvecncx(int col, FVecN x) { for(int i = 0; i < m; i++) x.d[i] = d[col + n * i]; return x; }
	public FVecN fvecnx(FVecN x) { if(x.size != d.length) throw new IllegalArgumentException(); System.arraycopy(d, 0, x.d, 0, d.length); return x; }

	@Override public int hashCode() {
		long bits = 7L;
		for(float x : d) bits = 31L * bits + Float.floatToIntBits(x);
		return (int) (bits ^ (bits >> 32));
	}
	@Override public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof FMatMxN)
			return Arrays.equals(d, ((FMatMxN) obj).d);
		return false;
	}
}
