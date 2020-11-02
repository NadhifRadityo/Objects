package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils;

import java.util.Arrays;

public class BVecN extends GenType {
	public final boolean[] d;

	public BVecN(boolean copy, boolean... x) { super(x.length); d = copy ? new boolean[size] : x; if(copy) System.arraycopy(x, 0, d, 0, size); }
	public BVecN(boolean... x) { this(true, x); }
	public BVecN(int n, boolean x) { super(n); d = new boolean[n]; Arrays.fill(d, x); }
	public BVecN(int n) { super(n); d = new boolean[n]; }

	@Override public Object getVectorAt(int i) { return d[i]; }
	@Override public void setVectorAt(int i, Object object) { d[i] = (boolean) object; }

	public BVec2 bvec2() { if(d.length != 2) throw new IllegalArgumentException(); return GenTypeUtils.bvec2(d[0], d[1]); }
	public BVec3 bvec3() { if(d.length != 3) throw new IllegalArgumentException(); return GenTypeUtils.bvec3(d[0], d[1], d[2]); }
	public BVec4 bvec4() { if(d.length != 4) throw new IllegalArgumentException(); return GenTypeUtils.bvec4(d[0], d[1], d[2], d[3]); }

	public BVecN swizzle(String type) {
		type = type.toLowerCase();
		boolean[] b = new boolean[type.length()];
		for(int i = 0; i < type.length(); i++) b[i] = getAlias(type.charAt(i));
		if(b.length == 2) return GenTypeUtils.bvec2(b[0], b[1]);
		if(b.length == 3) return GenTypeUtils.bvec3(b[0], b[1], b[2]);
		if(b.length == 4) return GenTypeUtils.bvec4(b[0], b[1], b[2], b[3]);
		return GenTypeUtils._bvecn(b);
	}

	protected boolean getAlias(char charAt) { switch(charAt) {
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

	public boolean and() { for(boolean x : d) if(!x) return false; return true; }
	public boolean or() { for(boolean x : d) if(x) return true; return false; }
	public boolean nand() { return !and(); }
	public boolean nor() { return !or(); }
	public boolean xor() { boolean result = d[0]; for(int i = 1; i < d.length; i++) result ^= d[i]; return result; }
	public boolean xnor() { return !xor(); }

	@Override public int hashCode() {
		long bits = 7L;
		for(boolean x : d) bits = 31L * bits + ((byte) (x ? 1 : 0 ));
		return (int) (bits ^ (bits >> 32));
	}
	@Override public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof BVecN)
			return Arrays.equals(d, ((BVecN) obj).d);
		return false;
	}
}
