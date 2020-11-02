package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils;

import java.util.Arrays;

public class LMatMxN extends GenType {
	public final int m;
	public final int n;
	public final long[] d;

	public LMatMxN(boolean copy, int m, int n, long... x) { super(m * n); this.m = m; this.n = n; d = copy ? new long[size] : x; if(copy) System.arraycopy(x, 0, d, 0, Math.min(x.length, size)); }
	public LMatMxN(int m, int n, long... x) { this(true, m, n, x); }
	public LMatMxN(int m, int n, int... x) { super(m * n); this.m = m; this.n = n; d = new long[size]; for(int i = 0; i < size; i++) d[i] = (long) x[i]; }
	public LMatMxN(int m, int n, short... x) { super(m * n); this.m = m; this.n = n; d = new long[size]; for(int i = 0; i < size; i++) d[i] = (long) x[i]; }
	public LMatMxN(int m, int n, double... x) { super(m * n); this.m = m; this.n = n; d = new long[size]; for(int i = 0; i < size; i++) d[i] = (long) x[i]; }
	public LMatMxN(int m, int n, float... x) { super(m * n); this.m = m; this.n = n; d = new long[size]; for(int i = 0; i < size; i++) d[i] = (long) x[i]; }
	public LMatMxN(int m, int n, long x) { super(m * n); this.m = m; this.n = n; d = new long[size]; Arrays.fill(d, x); }
	public LMatMxN(int m, int n) { super(m * n); this.m = m; this.n = n; d = new long[size]; }

	@Override public Object getVectorAt(int i) { return d[i]; }
	@Override public void setVectorAt(int i, Object object) { d[i] = object == null ? 0 : (long) object; }
	public int getIndex(int y, int x) { return getIndex0(y - 1, x - 1); }
	public int getIndex0(int y, int x) { ArrayUtils.assertCopyIndex(y, m, x, n, 1); return y * n + x; }

	public LVec2 lvec2r(int row) { if(n != 2) throw new IllegalArgumentException(); return GenTypeUtils.lvec2(d[getIndex0(row, 0)], d[getIndex0(row, 1)]); }
	public LVec3 lvec3r(int row) { if(n != 3) throw new IllegalArgumentException(); return GenTypeUtils.lvec3(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)]); }
	public LVec4 lvec4r(int row) { if(n != 4) throw new IllegalArgumentException(); return GenTypeUtils.lvec4(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)], d[getIndex0(row, 3)]); }
	public LVecN lvecnr(int row) { LVecN result = GenTypeUtils.lvecn(n); System.arraycopy(d, row * n, result.d, 0, n); return result; }
	public LVec2 lvec2c(int col) { if(m != 2) throw new IllegalArgumentException(); return GenTypeUtils.lvec2(d[getIndex0(0, col)], d[getIndex0(1, col)]); }
	public LVec3 lvec3c(int col) { if(m != 3) throw new IllegalArgumentException(); return GenTypeUtils.lvec3(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)]); }
	public LVec4 lvec4c(int col) { if(m != 4) throw new IllegalArgumentException(); return GenTypeUtils.lvec4(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)], d[getIndex0(3, col)]); }
	public LVecN lvecnc(int col) { LVecN result = GenTypeUtils.lvecn(m); for(int i = 0; i < n; i++) result.d[i] = d[col + n * i]; return result; }
	public LVecN lvecn() { return GenTypeUtils.lvecn(d); }

	public LVec2 lvec2rx(int row, LVec2 x) { if(n != 2) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)]); return x; }
	public LVec3 lvec3rx(int row, LVec3 x) { if(n != 3) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)]); return x; }
	public LVec4 lvec4rx(int row, LVec4 x) { if(n != 4) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)], d[getIndex0(row, 3)]); return x; }
	public LVecN lvecnrx(int row, LVecN x) { System.arraycopy(d, row * n, x.d, 0, n); return x; }
	public LVec2 lvec2cx(int col, LVec2 x) { if(m != 2) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)]); return x; }
	public LVec3 lvec3cx(int col, LVec3 x) { if(m != 3) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)]); return x; }
	public LVec4 lvec4cx(int col, LVec4 x) { if(m != 4) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)], d[getIndex0(3, col)]); return x; }
	public LVecN lvecncx(int col, LVecN x) { for(int i = 0; i < m; i++) x.d[i] = d[col + n * i]; return x; }
	public LVecN lvecnx(LVecN x) { if(x.size != d.length) throw new IllegalArgumentException(); System.arraycopy(d, 0, x.d, 0, d.length); return x; }

	@Override public int hashCode() {
		long bits = 7L;
		for(long x : d) bits = 31L * bits + x;
		return (int) (bits ^ (bits >> 32));
	}
	@Override public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof LMatMxN)
			return Arrays.equals(d, ((LMatMxN) obj).d);
		return false;
	}
}
