package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils;

import java.util.Arrays;

public class VecN extends GenType {
	public final double[] d;

	public VecN(boolean copy, double... x) { super(x.length); d = copy ? new double[size] : x; if(copy) System.arraycopy(x, 0, d, 0, size); }
	public VecN(double... x) { this(true, x); }
	public VecN(float... x) { super(x.length); d = new double[size]; for(int i = 0; i < size; i++) d[i] = (double) x[i]; }
	public VecN(long... x) { super(x.length); d = new double[size]; for(int i = 0; i < size; i++) d[i] = (double) x[i]; }
	public VecN(int... x) { super(x.length); d = new double[size]; for(int i = 0; i < size; i++) d[i] = (double) x[i]; }
	public VecN(short... x) { super(x.length); d = new double[size]; for(int i = 0; i < size; i++) d[i] = (double) x[i]; }
	public VecN(int n, double x) { super(n); d = new double[n]; Arrays.fill(d, x); }
	public VecN(int n) { super(n); d = new double[n]; }

	@Override public Object getVectorAt(int i) { return d[i]; }
	@Override public void setVectorAt(int i, Object object) { d[i] = object == null ? 0 : (double) object; }

	public Vec2 vec2() { if(d.length != 2) throw new IllegalArgumentException(); return GenTypeUtils.vec2(d[0], d[1]); }
	public Vec3 vec3() { if(d.length != 3) throw new IllegalArgumentException(); return GenTypeUtils.vec3(d[0], d[1], d[2]); }
	public Vec4 vec4() { if(d.length != 4) throw new IllegalArgumentException(); return GenTypeUtils.vec4(d[0], d[1], d[2], d[3]); }

	public VecN swizzle(String type) {
		type = type.toLowerCase();
		double[] b = new double[type.length()];
		for(int i = 0; i < type.length(); i++) b[i] = getAlias(type.charAt(i));
		if(b.length == 2) return GenTypeUtils.vec2(b[0], b[1]);
		if(b.length == 3) return GenTypeUtils.vec3(b[0], b[1], b[2]);
		if(b.length == 4) return GenTypeUtils.vec4(b[0], b[1], b[2], b[3]);
		return GenTypeUtils._vecn(b);
	}

	protected double getAlias(char charAt) { switch(charAt) {
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
		for(double x : d) bits = 31L * bits + Double.doubleToLongBits(x);
		return (int) (bits ^ (bits >> 32));
	}
	@Override public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof VecN)
			return Arrays.equals(d, ((VecN) obj).d);
		return false;
	}
}
