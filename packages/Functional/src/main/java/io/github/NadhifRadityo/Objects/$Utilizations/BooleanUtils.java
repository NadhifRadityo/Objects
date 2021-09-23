package io.github.NadhifRadityo.Objects.$Utilizations;

public class BooleanUtils extends org.apache.commons.lang3.BooleanUtils {

	public static boolean not(boolean x) { return !x; }
	public static boolean[] not(boolean[] x, boolean[] target) { assert x.length == target.length : "Length is not equal!"; int fit = (target.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { target[i] = not(x[i]); target[i + 1] = not(x[i + 1]); target[i + 2] = not(x[i + 2]); target[i + 3] = not(x[i + 3]); } for(; i < target.length; i++) target[i] = not(x[i]); return target; }
	public static boolean[] not(boolean[] x) { return not(x, new boolean[x.length]); }
	public static boolean all(boolean[] x) { boolean result = true; for(boolean b : x) result &= b; return result; }
	public static boolean any(boolean[] x) { boolean result = false; for(boolean b : x) result |= b; return result; }
}
