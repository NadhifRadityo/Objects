package io.github.NadhifRadityo.Objects.$Utilizations;

public class BitwiseUtils {

	public static int and(int[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); int result = objects.length <= start ? 0 : objects[start]; for(int i = start + 1; i < end; i++) result &= objects[i]; return result; }
	public static long and(long[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); long result = objects.length <= start ? 0 : objects[start]; for(int i = start + 1; i < end; i++) result &= objects[i]; return result; }
	public static short and(short[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); short result = objects.length <= start ? 0 : objects[start]; for(int i = start + 1; i < end; i++) result &= objects[i]; return result; }
	public static char and(char[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); char result = objects.length <= start ? 0 : objects[start]; for(int i = start + 1; i < end; i++) result &= objects[i]; return result; }
	public static boolean and(boolean[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); boolean result = objects.length <= start ? true : objects[start]; for(int i = start + 1; i < end; i++) result &= objects[i]; return result; }
	public static int and(int... objects) { return and(objects, 0, objects.length); }
	public static long and(long... objects) { return and(objects, 0, objects.length); }
	public static short and(short... objects) { return and(objects, 0, objects.length); }
	public static char and(char... objects) { return and(objects, 0, objects.length); }
	public static boolean and(boolean... objects) { return and(objects, 0, objects.length); }

	public static int or(int[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); int result = objects.length <= start ? 0 : objects[start]; for(int i = start + 1; i < end; i++) result |= objects[i]; return result; }
	public static long or(long[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); long result = objects.length <= start ? 0 : objects[start]; for(int i = start + 1; i < end; i++) result |= objects[i]; return result; }
	public static short or(short[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); short result = objects.length <= start ? 0 : objects[start]; for(int i = start + 1; i < end; i++) result |= objects[i]; return result; }
	public static char or(char[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); char result = objects.length <= start ? 0 : objects[start]; for(int i = start + 1; i < end; i++) result |= objects[i]; return result; }
	public static boolean or(boolean[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); boolean result = objects.length <= start ? false : objects[start]; for(int i = start + 1; i < end; i++) result |= objects[i]; return result; }
	public static int or(int... objects) { return or(objects, 0, objects.length); }
	public static long or(long... objects) { return or(objects, 0, objects.length); }
	public static short or(short... objects) { return or(objects, 0, objects.length); }
	public static char or(char... objects) { return or(objects, 0, objects.length); }
	public static boolean or(boolean... objects) { return or(objects, 0, objects.length); }

	public static int xor(int[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); int result = objects.length <= start ? 0 : objects[start]; for(int i = start + 1; i < end; i++) result ^= objects[i]; return result; }
	public static long xor(long[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); long result = objects.length <= start ? 0 : objects[start]; for(int i = start + 1; i < end; i++) result ^= objects[i]; return result; }
	public static short xor(short[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); short result = objects.length <= start ? 0 : objects[start]; for(int i = start + 1; i < end; i++) result ^= objects[i]; return result; }
	public static char xor(char[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); char result = objects.length <= start ? 0 : objects[start]; for(int i = start + 1; i < end; i++) result ^= objects[i]; return result; }
	public static boolean xor(boolean[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); boolean result = objects.length <= start ? false : objects[start]; for(int i = start + 1; i < end; i++) result ^= objects[i]; return result; }
	public static int xor(int... objects) { return xor(objects, 0, objects.length); }
	public static long xor(long... objects) { return xor(objects, 0, objects.length); }
	public static short xor(short... objects) { return xor(objects, 0, objects.length); }
	public static char xor(char... objects) { return xor(objects, 0, objects.length); }
	public static boolean xor(boolean... objects) { return xor(objects, 0, objects.length); }

	public static int[] compliment(int[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); int[] result = new int[end - start]; for(int i = 0; i < result.length; i++) result[i] = ~objects[start + i]; return result; }
	public static long[] compliment(long[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); long[] result = new long[end - start]; for(int i = 0; i < result.length; i++) result[i] = ~objects[start + i]; return result; }
	public static short[] compliment(short[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); short[] result = new short[end - start]; for(int i = 0; i < result.length; i++) result[i] = (short) ~objects[start + i]; return result; }
	public static char[] compliment(char[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); char[] result = new char[end - start]; for(int i = 0; i < result.length; i++) result[i] = (char) ~objects[start + i]; return result; }
	public static boolean[] compliment(boolean[] objects, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); boolean[] result = new boolean[end - start]; for(int i = 0; i < result.length; i++) result[i] = !objects[start + i]; return result; }
	public static int[] compliment(int... objects) { return compliment(objects, 0, objects.length); }
	public static long[] compliment(long... objects) { return compliment(objects, 0, objects.length); }
	public static short[] compliment(short... objects) { return compliment(objects, 0, objects.length); }
	public static char[] compliment(char... objects) { return compliment(objects, 0, objects.length); }
	public static boolean[] compliment(boolean... objects) { return compliment(objects, 0, objects.length); }

	public static int[] shiftLeft(int[] objects, int x, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); int[] result = new int[end - start]; for(int i = 0; i < result.length; i++) result[i] = objects[start + i] << x; return result; }
	public static long[] shiftLeft(long[] objects, int x, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); long[] result = new long[end - start]; for(int i = 0; i < result.length; i++) result[i] = objects[start + i] << x; return result; }
	public static short[] shiftLeft(short[] objects, int x, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); short[] result = new short[end - start]; for(int i = 0; i < result.length; i++) result[i] = (short) (objects[start + i] << x); return result; }
	public static char[] shiftLeft(char[] objects, int x, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); char[] result = new char[end - start]; for(int i = 0; i < result.length; i++) result[i] = (char) (objects[start + i] << x); return result; }
	public static int[] shiftLeft(int x, int... objects) { return shiftLeft(objects, x, 0, objects.length); }
	public static long[] shiftLeft(int x, long... objects) { return shiftLeft(objects, x, 0, objects.length); }
	public static short[] shiftLeft(int x, short... objects) { return shiftLeft(objects, x, 0, objects.length); }
	public static char[] shiftLeft(int x, char... objects) { return shiftLeft(objects, x, 0, objects.length); }

	public static int[] shiftRight(int[] objects, int x, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); int[] result = new int[end - start]; for(int i = 0; i < result.length; i++) result[i] = objects[start + i] >> x; return result; }
	public static long[] shiftRight(long[] objects, int x, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); long[] result = new long[end - start]; for(int i = 0; i < result.length; i++) result[i] = objects[start + i] >> x; return result; }
	public static short[] shiftRight(short[] objects, int x, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); short[] result = new short[end - start]; for(int i = 0; i < result.length; i++) result[i] = (short) (objects[start + i] >> x); return result; }
	public static char[] shiftRight(char[] objects, int x, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); char[] result = new char[end - start]; for(int i = 0; i < result.length; i++) result[i] = (char) (objects[start + i] >> x); return result; }
	public static int[] shiftRight(int x, int... objects) { return shiftRight(objects, x, 0, objects.length); }
	public static long[] shiftRight(int x, long... objects) { return shiftRight(objects, x, 0, objects.length); }
	public static short[] shiftRight(int x, short... objects) { return shiftRight(objects, x, 0, objects.length); }
	public static char[] shiftRight(int x, char... objects) { return shiftRight(objects, x, 0, objects.length); }

	public static int[] zeroFillShiftRight(int[] objects, int x, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); int[] result = new int[end - start]; for(int i = 0; i < result.length; i++) result[i] = objects[start + i] >>> x; return result; }
	public static long[] zeroFillShiftRight(long[] objects, int x, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); long[] result = new long[end - start]; for(int i = 0; i < result.length; i++) result[i] = objects[start + i] >>> x; return result; }
	public static short[] zeroFillShiftRight(short[] objects, int x, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); short[] result = new short[end - start]; for(int i = 0; i < result.length; i++) result[i] = (short) (objects[start + i] >>> x); return result; }
	public static char[] zeroFillShiftRight(char[] objects, int x, int start, int end) { ArrayUtils.assertCopyIndex(start, objects.length, end, objects.length, 0); char[] result = new char[end - start]; for(int i = 0; i < result.length; i++) result[i] = (char) (objects[start + i] >>> x); return result; }
	public static int[] zeroFillShiftRight(int x, int... objects) { return zeroFillShiftRight(objects, x, 0, objects.length); }
	public static long[] zeroFillShiftRight(int x, long... objects) { return zeroFillShiftRight(objects, x, 0, objects.length); }
	public static short[] zeroFillShiftRight(int x, short... objects) { return zeroFillShiftRight(objects, x, 0, objects.length); }
	public static char[] zeroFillShiftRight(int x, char... objects) { return zeroFillShiftRight(objects, x, 0, objects.length); }

	public static boolean is(int target, int x) { return (target & x) != 0; }
	public static boolean is(long target, long x) { return (target & x) != 0; }
	public static boolean is(short target, short x) { return (target & x) != 0; }
	public static boolean is(char target, char x) { return (target & x) != 0; }
	public static boolean is(int target, int x, int y) { return (target & x) != 0 && (target & y) != 0; }
	public static boolean is(long target, long x, long y) { return (target & x) != 0 && (target & y) != 0; }
	public static boolean is(short target, short x, short y) { return (target & x) != 0 && (target & y) != 0; }
	public static boolean is(char target, char x, char y) { return (target & x) != 0 && (target & y) != 0; }
	public static boolean is(int target, int x, int y, int z) { return (target & x) != 0 && (target & y) != 0 && (target & z) != 0; }
	public static boolean is(long target, long x, long y, long z) { return (target & x) != 0 && (target & y) != 0 && (target & z) != 0; }
	public static boolean is(short target, short x, short y, short z) { return (target & x) != 0 && (target & y) != 0 && (target & z) != 0; }
	public static boolean is(char target, char x, char y, char z) { return (target & x) != 0 && (target & y) != 0 && (target & z) != 0; }

	public static boolean not(int target, int x) { return (target & x) == 0; }
	public static boolean not(long target, long x) { return (target & x) == 0; }
	public static boolean not(short target, short x) { return (target & x) == 0; }
	public static boolean not(char target, char x) { return (target & x) == 0; }
	public static boolean not(int target, int x, int y) { return (target & x) == 0 && (target & y) == 0; }
	public static boolean not(long target, long x, long y) { return (target & x) == 0 && (target & y) == 0; }
	public static boolean not(short target, short x, short y) { return (target & x) == 0 && (target & y) == 0; }
	public static boolean not(char target, char x, char y) { return (target & x) == 0 && (target & y) == 0; }
	public static boolean not(int target, int x, int y, int z) { return (target & x) == 0 && (target & y) == 0 && (target & z) == 0; }
	public static boolean not(long target, long x, long y, long z) { return (target & x) == 0 && (target & y) == 0 && (target & z) == 0; }
	public static boolean not(short target, short x, short y, short z) { return (target & x) == 0 && (target & y) == 0 && (target & z) == 0; }
	public static boolean not(char target, char x, char y, char z) { return (target & x) == 0 && (target & y) == 0 && (target & z) == 0; }

	public static boolean[] is(int x, int... targets) { boolean[] result = new boolean[targets.length]; for(int i = 0; i < result.length; i++) result[i] = is(targets[i], x); return result; }
	public static boolean[] is(long x, long... targets) { boolean[] result = new boolean[targets.length]; for(int i = 0; i < result.length; i++) result[i] = is(targets[i], x); return result; }
	public static boolean[] is(short x, short... targets) { boolean[] result = new boolean[targets.length]; for(int i = 0; i < result.length; i++) result[i] = is(targets[i], x); return result; }
	public static boolean[] is(char x, char... targets) { boolean[] result = new boolean[targets.length]; for(int i = 0; i < result.length; i++) result[i] = is(targets[i], x); return result; }

	public static boolean[] not(int x, int... targets) { boolean[] result = new boolean[targets.length]; for(int i = 0; i < result.length; i++) result[i] = not(targets[i], x); return result; }
	public static boolean[] not(long x, long... targets) { boolean[] result = new boolean[targets.length]; for(int i = 0; i < result.length; i++) result[i] = not(targets[i], x); return result; }
	public static boolean[] not(short x, short... targets) { boolean[] result = new boolean[targets.length]; for(int i = 0; i < result.length; i++) result[i] = not(targets[i], x); return result; }
	public static boolean[] not(char x, char... targets) { boolean[] result = new boolean[targets.length]; for(int i = 0; i < result.length; i++) result[i] = not(targets[i], x); return result; }

	public static int[] extract(int target, int... props) { for(int i = 0; i < props.length; i++) props[i] = is(target, props[i]) ? 1 : 0; return props; }
	public static long[] extract(long target, long... props) { for(int i = 0; i < props.length; i++) props[i] = is(target, props[i]) ? 1 : 0; return props; }
	public static short[] extract(short target, short... props) { for(int i = 0; i < props.length; i++) props[i] = (short) (is(target, props[i]) ? 1 : 0); return props; }
	public static char[] extract(char target, char... props) { for(int i = 0; i < props.length; i++) props[i] = (char) (is(target, props[i]) ? 1 : 0); return props; }

	public static boolean any(int target, int... props) { for(int prop : props) if(is(target, prop)) return true; return false; }
	public static boolean any(long target, long... props) { for(long prop : props) if(is(target, prop)) return true; return false; }
	public static boolean any(short target, short... props) { for(short prop : props) if(is(target, prop)) return true; return false; }
	public static boolean any(char target, char... props) { for(char prop : props) if(is(target, prop)) return true; return false; }

	public static boolean all(int target, int... props) { for(int prop : props) if(not(target, prop)) return false; return true; }
	public static boolean all(long target, long... props) { for(long prop : props) if(not(target, prop)) return false; return true; }
	public static boolean all(short target, short... props) { for(short prop : props) if(not(target, prop)) return false; return true; }
	public static boolean all(char target, char... props) { for(char prop : props) if(not(target, prop)) return false; return true; }
}
