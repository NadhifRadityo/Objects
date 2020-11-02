package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils;

import java.util.Arrays;

public class IMatMxN extends GenType {
	public final int m;
	public final int n;
	public final int[] d;

	public IMatMxN(boolean copy, int m, int n, int... x) { super(m * n); this.m = m; this.n = n; d = copy ? new int[size] : x; if(copy) System.arraycopy(x, 0, d, 0, Math.min(x.length, size)); }
	public IMatMxN(int m, int n, int... x) { this(true, m, n, x); }
	public IMatMxN(int m, int n, long... x) { super(m * n); this.m = m; this.n = n; d = new int[size]; for(int i = 0; i < size; i++) d[i] = (int) x[i]; }
	public IMatMxN(int m, int n, short... x) { super(m * n); this.m = m; this.n = n; d = new int[size]; for(int i = 0; i < size; i++) d[i] = (int) x[i]; }
	public IMatMxN(int m, int n, float... x) { super(m * n); this.m = m; this.n = n; d = new int[size]; for(int i = 0; i < size; i++) d[i] = (int) x[i]; }
	public IMatMxN(int m, int n, double... x) { super(m * n); this.m = m; this.n = n; d = new int[size]; for(int i = 0; i < size; i++) d[i] = (int) x[i]; }
	public IMatMxN(int m, int n, int x) { super(m * n); this.m = m; this.n = n; d = new int[size]; Arrays.fill(d, x); }
	public IMatMxN(int m, int n) { super(m * n); this.m = m; this.n = n; d = new int[size]; }

	@Override public Object getVectorAt(int i) { return d[i]; }
	@Override public void setVectorAt(int i, Object object) { d[i] = object == null ? 0 : (int) object; }
	public int getIndex(int y, int x) { return getIndex0(y - 1, x - 1); }
	public int getIndex0(int y, int x) { ArrayUtils.assertCopyIndex(y, m, x, n, 1); return y * n + x; }

	public IVec2 ivec2r(int row) { if(n != 2) throw new IllegalArgumentException(); return GenTypeUtils.ivec2(d[getIndex0(row, 0)], d[getIndex0(row, 1)]); }
	public IVec3 ivec3r(int row) { if(n != 3) throw new IllegalArgumentException(); return GenTypeUtils.ivec3(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)]); }
	public IVec4 ivec4r(int row) { if(n != 4) throw new IllegalArgumentException(); return GenTypeUtils.ivec4(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)], d[getIndex0(row, 3)]); }
	public IVecN ivecnr(int row) { IVecN result = GenTypeUtils.ivecn(n); System.arraycopy(d, row * n, result.d, 0, n); return result; }
	public IVec2 ivec2c(int col) { if(m != 2) throw new IllegalArgumentException(); return GenTypeUtils.ivec2(d[getIndex0(0, col)], d[getIndex0(1, col)]); }
	public IVec3 ivec3c(int col) { if(m != 3) throw new IllegalArgumentException(); return GenTypeUtils.ivec3(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)]); }
	public IVec4 ivec4c(int col) { if(m != 4) throw new IllegalArgumentException(); return GenTypeUtils.ivec4(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)], d[getIndex0(3, col)]); }
	public IVecN ivecnc(int col) { IVecN result = GenTypeUtils.ivecn(m); for(int i = 0; i < n; i++) result.d[i] = d[col + n * i]; return result; }
	public IVecN ivecn() { return GenTypeUtils.ivecn(d); }

	public IVec2 ivec2rx(int row, IVec2 x) { if(n != 2) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)]); return x; }
	public IVec3 ivec3rx(int row, IVec3 x) { if(n != 3) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)]); return x; }
	public IVec4 ivec4rx(int row, IVec4 x) { if(n != 4) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)], d[getIndex0(row, 3)]); return x; }
	public IVecN ivecnrx(int row, IVecN x) { System.arraycopy(d, row * n, x.d, 0, n); return x; }
	public IVec2 ivec2cx(int col, IVec2 x) { if(m != 2) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)]); return x; }
	public IVec3 ivec3cx(int col, IVec3 x) { if(m != 3) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)]); return x; }
	public IVec4 ivec4cx(int col, IVec4 x) { if(m != 4) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)], d[getIndex0(3, col)]); return x; }
	public IVecN ivecncx(int col, IVecN x) { for(int i = 0; i < m; i++) x.d[i] = d[col + n * i]; return x; }
	public IVecN ivecnx(IVecN x) { if(x.size != d.length) throw new IllegalArgumentException(); System.arraycopy(d, 0, x.d, 0, d.length); return x; }

	@Override public int hashCode() {
		long bits = 7L;
		for(int x : d) bits = 31L * bits + x;
		return (int) (bits ^ (bits >> 32));
	}
	@Override public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof IMatMxN)
			return Arrays.equals(d, ((IMatMxN) obj).d);
		return false;
	}
}
