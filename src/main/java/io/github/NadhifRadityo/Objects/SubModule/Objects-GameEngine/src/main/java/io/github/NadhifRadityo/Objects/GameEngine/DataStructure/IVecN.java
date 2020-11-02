package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils;

import java.util.Arrays;

public class IVecN extends GenType {
	public final int[] d;

	public IVecN(boolean copy, int... x) { super(x.length); d = copy ? new int[size] : x; if(copy) System.arraycopy(x, 0, d, 0, size); }
	public IVecN(int... x) { this(true, x); }
	public IVecN(long... x) { super(x.length); d = new int[size]; for(int i = 0; i < size; i++) d[i] = (int) x[i]; }
	public IVecN(short... x) { super(x.length); d = new int[size]; for(int i = 0; i < size; i++) d[i] = (int) x[i]; }
	public IVecN(double... x) { super(x.length); d = new int[size]; for(int i = 0; i < size; i++) d[i] = (int) x[i]; }
	public IVecN(float... x) { super(x.length); d = new int[size]; for(int i = 0; i < size; i++) d[i] = (int) x[i]; }
	public IVecN(int n, int x) { super(n); d = new int[n]; Arrays.fill(d, x); }
	public IVecN(int n) { super(n); d = new int[n]; }

	@Override public Object getVectorAt(int i) { return d[i]; }
	@Override public void setVectorAt(int i, Object object) { d[i] = object == null ? 0 : (int) object; }

	public IVec2 ivec2() { if(d.length != 2) throw new IllegalArgumentException(); return GenTypeUtils.ivec2(d[0], d[1]); }
	public IVec3 ivec3() { if(d.length != 3) throw new IllegalArgumentException(); return GenTypeUtils.ivec3(d[0], d[1], d[2]); }
	public IVec4 ivec4() { if(d.length != 4) throw new IllegalArgumentException(); return GenTypeUtils.ivec4(d[0], d[1], d[2], d[3]); }

	public IVecN swizzle(String type) {
		type = type.toLowerCase();
		int[] b = new int[type.length()];
		for(int i = 0; i < type.length(); i++) b[i] = getAlias(type.charAt(i));
		if(b.length == 2) return GenTypeUtils.ivec2(b[0], b[1]);
		if(b.length == 3) return GenTypeUtils.ivec3(b[0], b[1], b[2]);
		if(b.length == 4) return GenTypeUtils.ivec4(b[0], b[1], b[2], b[3]);
		return GenTypeUtils._ivecn(b);
	}

	protected int getAlias(char charAt) { switch(charAt) {
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
		for(int x : d) bits = 31L * bits + x;
		return (int) (bits ^ (bits >> 32));
	}
	@Override public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof IVecN)
			return Arrays.equals(d, ((IVecN) obj).d);
		return false;
	}
}
