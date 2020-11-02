package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils;

import java.util.Arrays;

public class SMatMxN extends GenType {
	public final int m;
	public final int n;
	public final short[] d;

	public SMatMxN(boolean copy, int m, int n, short... x) { super(m * n); this.m = m; this.n = n; d = copy ? new short[size] : x; if(copy) System.arraycopy(x, 0, d, 0, Math.min(x.length, size)); }
	public SMatMxN(int m, int n, short... x) { this(true, m, n, x); }
	public SMatMxN(int m, int n, long... x) { super(m * n); this.m = m; this.n = n; d = new short[size]; for(int i = 0; i < size; i++) d[i] = (short) x[i]; }
	public SMatMxN(int m, int n, int... x) { super(m * n); this.m = m; this.n = n; d = new short[size]; for(int i = 0; i < size; i++) d[i] = (short) x[i]; }
	public SMatMxN(int m, int n, double... x) { super(m * n); this.m = m; this.n = n; d = new short[size]; for(int i = 0; i < size; i++) d[i] = (short) x[i]; }
	public SMatMxN(int m, int n, float... x) { super(m * n); this.m = m; this.n = n; d = new short[size]; for(int i = 0; i < size; i++) d[i] = (short) x[i]; }
	public SMatMxN(int m, int n, short x) { super(m * n); this.m = m; this.n = n; d = new short[size]; Arrays.fill(d, x); }
	public SMatMxN(int m, int n) { super(m * n); this.m = m; this.n = n; d = new short[size]; }

	@Override public Object getVectorAt(int i) { return d[i]; }
	@Override public void setVectorAt(int i, Object object) { d[i] = object == null ? 0 : (short) object; }
	public int getIndex(int y, int x) { return getIndex0(y - 1, x - 1); }
	public int getIndex0(int y, int x) { ArrayUtils.assertCopyIndex(y, m, x, n, 1); return y * n + x; }

	public SVec2 svec2r(int row) { if(n != 2) throw new IllegalArgumentException(); return GenTypeUtils.svec2(d[getIndex0(row, 0)], d[getIndex0(row, 1)]); }
	public SVec3 svec3r(int row) { if(n != 3) throw new IllegalArgumentException(); return GenTypeUtils.svec3(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)]); }
	public SVec4 svec4r(int row) { if(n != 4) throw new IllegalArgumentException(); return GenTypeUtils.svec4(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)], d[getIndex0(row, 3)]); }
	public SVecN svecnr(int row) { SVecN result = GenTypeUtils.svecn(n); System.arraycopy(d, row * n, result.d, 0, n); return result; }
	public SVec2 svec2c(int col) { if(m != 2) throw new IllegalArgumentException(); return GenTypeUtils.svec2(d[getIndex0(0, col)], d[getIndex0(1, col)]); }
	public SVec3 svec3c(int col) { if(m != 3) throw new IllegalArgumentException(); return GenTypeUtils.svec3(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)]); }
	public SVec4 svec4c(int col) { if(m != 4) throw new IllegalArgumentException(); return GenTypeUtils.svec4(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)], d[getIndex0(3, col)]); }
	public SVecN svecnc(int col) { SVecN result = GenTypeUtils.svecn(m); for(int i = 0; i < n; i++) result.d[i] = d[col + n * i]; return result; }
	public SVecN svecn() { return GenTypeUtils.svecn(d); }

	public SVec2 svec2rx(int row, SVec2 x) { if(n != 2) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)]); return x; }
	public SVec3 svec3rx(int row, SVec3 x) { if(n != 3) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)]); return x; }
	public SVec4 svec4rx(int row, SVec4 x) { if(n != 4) throw new IllegalArgumentException(); x.set(d[getIndex0(row, 0)], d[getIndex0(row, 1)], d[getIndex0(row, 2)], d[getIndex0(row, 3)]); return x; }
	public SVecN svecnrx(int row, SVecN x) { System.arraycopy(d, row * n, x.d, 0, n); return x; }
	public SVec2 svec2cx(int col, SVec2 x) { if(m != 2) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)]); return x; }
	public SVec3 svec3cx(int col, SVec3 x) { if(m != 3) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)]); return x; }
	public SVec4 svec4cx(int col, SVec4 x) { if(m != 4) throw new IllegalArgumentException(); x.set(d[getIndex0(0, col)], d[getIndex0(1, col)], d[getIndex0(2, col)], d[getIndex0(3, col)]); return x; }
	public SVecN svecncx(int col, SVecN x) { for(int i = 0; i < m; i++) x.d[i] = d[col + n * i]; return x; }
	public SVecN svecnx(SVecN x) { if(x.size != d.length) throw new IllegalArgumentException(); System.arraycopy(d, 0, x.d, 0, d.length); return x; }

	@Override public int hashCode() {
		long bits = 7L;
		for(short x : d) bits = 31L * bits + x;
		return (int) (bits ^ (bits >> 32));
	}
	@Override public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof SMatMxN)
			return Arrays.equals(d, ((SMatMxN) obj).d);
		return false;
	}
}
