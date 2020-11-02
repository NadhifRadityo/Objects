package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils;

import java.util.Arrays;

public class LVecN extends GenType {
	public final long[] d;

	public LVecN(boolean copy, long... x) { super(x.length); d = copy ? new long[size] : x; if(copy) System.arraycopy(x, 0, d, 0, size); }
	public LVecN(long... x) { this(true, x); }
	public LVecN(int... x) { super(x.length); d = new long[size]; for(int i = 0; i < size; i++) d[i] = (long) x[i]; }
	public LVecN(short... x) { super(x.length); d = new long[size]; for(int i = 0; i < size; i++) d[i] = (long) x[i]; }
	public LVecN(double... x) { super(x.length); d = new long[size]; for(int i = 0; i < size; i++) d[i] = (long) x[i]; }
	public LVecN(float... x) { super(x.length); d = new long[size]; for(int i = 0; i < size; i++) d[i] = (long) x[i]; }
	public LVecN(int n, long x) { super(n); d = new long[n]; Arrays.fill(d, x); }
	public LVecN(int n) { super(n); d = new long[n]; }

	@Override public Object getVectorAt(int i) { return d[i]; }
	@Override public void setVectorAt(int i, Object object) { d[i] = object == null ? 0 : (long) object; }

	public LVec2 lvec2() { if(d.length != 2) throw new IllegalArgumentException(); return GenTypeUtils.lvec2(d[0], d[1]); }
	public LVec3 lvec3() { if(d.length != 3) throw new IllegalArgumentException(); return GenTypeUtils.lvec3(d[0], d[1], d[2]); }
	public LVec4 lvec4() { if(d.length != 4) throw new IllegalArgumentException(); return GenTypeUtils.lvec4(d[0], d[1], d[2], d[3]); }

	public LVecN swizzle(String type) {
		type = type.toLowerCase();
		long[] b = new long[type.length()];
		for(int i = 0; i < type.length(); i++) b[i] = getAlias(type.charAt(i));
		if(b.length == 2) return GenTypeUtils.lvec2(b[0], b[1]);
		if(b.length == 3) return GenTypeUtils.lvec3(b[0], b[1], b[2]);
		if(b.length == 4) return GenTypeUtils.lvec4(b[0], b[1], b[2], b[3]);
		return GenTypeUtils._lvecn(b);
	}

	protected long getAlias(char charAt) { switch(charAt) {
		case 'a': return d[0]; case 'b': return d[1]; case 'c': return d[2];
		case 'd': return d[3]; case 'e': return d[4]; case 'f': return d[5];
		case 'g': return d[6]; case 'h': return d[7]; case 'i': return d[8];
		case 'j': return d[9]; case 'k': return d[10]; case 'l': return d[11];
		case 'm': return d[12]; case 'n': return d[13]; case 'o': return d[14];
		case 'p': return d[15]; case 'q': return d[16]; case 'r': return d[17];
		case 's': return d[18]; case 't': return d[19]; case 'u': return d[20];
		case 'v': return d[21]; case 'w': return d[22]; case 'x': return d[23];
		case 'y': return d[24]; case 'z': return d[25]; default: throw new IllegalArgumentException();
	} }

	@Override public int hashCode() {
		long bits = 7L;
		for(long x : d) bits = 31L * bits + x;
		return (int) (bits ^ (bits >> 32));
	}
	@Override public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof LVecN)
			return Arrays.equals(d, ((LVecN) obj).d);
		return false;
	}
}
