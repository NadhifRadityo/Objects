package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils;

import java.util.Arrays;

public class FVecN extends GenType {
	public final float[] d;

	public FVecN(boolean copy, float... x) { super(x.length); d = copy ? new float[size] : x; if(copy) System.arraycopy(x, 0, d, 0, size); }
	public FVecN(float... x) { this(true, x); }
	public FVecN(double... x) { super(x.length); d = new float[size]; for(int i = 0; i < size; i++) d[i] = (float) x[i]; }
	public FVecN(long... x) { super(x.length); d = new float[size]; for(int i = 0; i < size; i++) d[i] = (float) x[i]; }
	public FVecN(int... x) { super(x.length); d = new float[size]; for(int i = 0; i < size; i++) d[i] = (float) x[i]; }
	public FVecN(short... x) { super(x.length); d = new float[size]; for(int i = 0; i < size; i++) d[i] = (float) x[i]; }
	public FVecN(int n, float x) { super(n); d = new float[n]; Arrays.fill(d, x); }
	public FVecN(int n) { super(n); d = new float[n]; }

	@Override public Object getVectorAt(int i) { return d[i]; }
	@Override public void setVectorAt(int i, Object object) { d[i] = object == null ? 0 : (float) object; }

	public FVec2 fvec2() { if(d.length != 2) throw new IllegalArgumentException(); return GenTypeUtils.fvec2(d[0], d[1]); }
	public FVec3 fvec3() { if(d.length != 3) throw new IllegalArgumentException(); return GenTypeUtils.fvec3(d[0], d[1], d[2]); }
	public FVec4 fvec4() { if(d.length != 4) throw new IllegalArgumentException(); return GenTypeUtils.fvec4(d[0], d[1], d[2], d[3]); }

	public FVecN swizzle(String type) {
		type = type.toLowerCase();
		float[] b = new float[type.length()];
		for(int i = 0; i < type.length(); i++) b[i] = getAlias(type.charAt(i));
		if(b.length == 2) return GenTypeUtils.fvec2(b[0], b[1]);
		if(b.length == 3) return GenTypeUtils.fvec3(b[0], b[1], b[2]);
		if(b.length == 4) return GenTypeUtils.fvec4(b[0], b[1], b[2], b[3]);
		return GenTypeUtils._fvecn(b);
	}

	protected float getAlias(char charAt) { switch(charAt) {
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
		for(float x : d) bits = 31L * bits + Float.floatToIntBits(x);
		return (int) (bits ^ (bits >> 32));
	}
	@Override public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof FVecN)
			return Arrays.equals(d, ((FVecN) obj).d);
		return false;
	}
}
