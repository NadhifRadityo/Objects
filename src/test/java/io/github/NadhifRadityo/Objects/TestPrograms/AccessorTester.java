package io.github.NadhifRadityo.Objects.TestPrograms;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Tester;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.Utilizations.UnsafeUtils;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public class AccessorTester extends Tester {
	public AccessorTester() {
		super("AccessorTester");
	}

	@Override
	protected boolean doRun(Logger logger, ByteBuffer dump) throws Throwable {
		int repeat = 10;
		int loops = 100000;
		for(int r = 0; r < repeat; r++) {
			logger.debug("------- Starting New Session -------");
			A a = new A((byte) NumberUtils.randChar(), NumberUtils.randChar(), NumberUtils.randInt(), NumberUtils.randLong());
			logger._debug("Loops: %s", loops);
			logger._debug("A = _byte: %s, _char: %s, _int: %s, _long: %s", a._byte, a._char, a._int, a._long);
			long t1 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((byte) s_r_A$_byte.get(a));//
			long t2 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((char) s_r_A$_char.get(a));
			long t3 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((int) s_r_A$_int.get(a));
			long t4 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((long) s_r_A$_long.get(a));
			long t5 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((byte) s_u_A$_byte.invoke(a));//
			long t6 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((char) s_u_A$_char.invoke(a));
			long t7 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((int) s_u_A$_int.invoke(a));
			long t8 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((long) s_u_A$_long.invoke(a));
			long t9 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((byte) s_u_A$_byte.invokeExact(a));//
			long t10 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((char) s_u_A$_char.invokeExact(a));
			long t11 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((int) s_u_A$_int.invokeExact(a));
			long t12 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((long) s_u_A$_long.invokeExact(a));
			long t13 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((byte) s_mh_A$_byte.invoke(a));//
			long t14 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((char) s_mh_A$_char.invoke(a));
			long t15 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((int) s_mh_A$_int.invoke(a));
			long t16 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((long) s_mh_A$_long.invoke(a));
			long t17 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((byte) s_mh_A$_byte.invokeExact(a));//
			long t18 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((char) s_mh_A$_char.invokeExact(a));
			long t19 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((int) s_mh_A$_int.invokeExact(a));
			long t20 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((long) s_mh_A$_long.invokeExact(a));
			long t21 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((byte) unsafe.getByte(a, l_mh_A$_byte));//
			long t22 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((char) unsafe.getChar(a, l_mh_A$_char));//
			long t23 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((int) unsafe.getInt(a, l_mh_A$_int));//
			long t24 = System.nanoTime();
			for(int i = 0; i < loops; i++) __((long) unsafe.getLong(a, l_mh_A$_long));//
			long t25 = System.nanoTime();
			logger._info("Byte = Reflect: %sms (%sns/op), Unreflect: %sms (%sns/op), Unreflect exact: %sms (%sns/op), Handles: %sms (%sns/op), Handles exact: %sms (%sns/op), Unsafe: %sms (%sns/op)",
					(float) (t2 - t1) / 1000000, (float) (t2 - t1) / loops,
					(float) (t6 - t5) / 1000000, (float) (t6 - t5) / loops,
					(float) (t10 - t9) / 1000000, (float) (t10 - t9) / loops,
					(float) (t14 - t13) / 1000000, (float) (t14 - t13) / loops,
					(float) (t18 - t17) / 1000000, (float) (t18 - t17) / loops,
					(float) (t22 - t21) / 1000000, (float) (t22 - t21) / loops);
			logger._info("Char = Reflect: %sms (%sns/op), Unreflect: %sms (%sns/op), Unreflect exact: %sms (%sns/op), Handles: %sms (%sns/op), Handles exact: %sms (%sns/op), Unsafe: %sms (%sns/op)",
					(float) (t3 - t2) / 1000000, (float) (t3 - t2) / loops,
					(float) (t7 - t6) / 1000000, (float) (t7 - t6) / loops,
					(float) (t11 - t10) / 1000000, (float) (t11 - t10) / loops,
					(float) (t15 - t14) / 1000000, (float) (t15 - t14) / loops,
					(float) (t19 - t18) / 1000000, (float) (t19 - t18) / loops,
					(float) (t23 - t22) / 1000000, (float) (t23 - t22) / loops);
			logger._info("Int = Reflect: %sms (%sns/op), Unreflect: %sms (%sns/op), Unreflect exact: %sms (%sns/op), Handles: %sms (%sns/op), Handles exact: %sms (%sns/op), Unsafe: %sms (%sns/op)",
					(float) (t4 - t3) / 1000000, (float) (t4 - t3) / loops,
					(float) (t8 - t7) / 1000000, (float) (t8 - t7) / loops,
					(float) (t12 - t11) / 1000000, (float) (t12 - t11) / loops,
					(float) (t16 - t15) / 1000000, (float) (t16 - t15) / loops,
					(float) (t20 - t19) / 1000000, (float) (t20 - t19) / loops,
					(float) (t24 - t23) / 1000000, (float) (t24 - t23) / loops);
			logger._info("Long = Reflect: %sms (%sns/op), Unreflect: %sms (%sns/op), Unreflect exact: %sms (%sns/op), Handles: %sms (%sns/op), Handles exact: %sms (%sns/op), Unsafe: %sms (%sns/op)",
					(float) (t5 - t4) / 1000000, (float) (t5 - t4) / loops,
					(float) (t9 - t8) / 1000000, (float) (t9 - t8) / loops,
					(float) (t13 - t12) / 1000000, (float) (t13 - t12) / loops,
					(float) (t17 - t16) / 1000000, (float) (t17 - t16) / loops,
					(float) (t21 - t20) / 1000000, (float) (t21 - t20) / loops,
					(float) (t25 - t24) / 1000000, (float) (t25 - t24) / loops);
			logger.debug("------------------------------------\n");
		}
		return true;
	}

	public long __;
	void __(byte __) { this.__ = __; }
	void __(char __) { this.__ = __; }
	void __(int __) { this.__ = __; }
	void __(long __) { this.__ = __; }

	@SuppressWarnings("jol")
	static class A {
		A(byte b, char c, int i, long l) {
			this._byte = b;
			this._char = c;
			this._int = i;
			this._long = l;
		}

		public byte _byte;//1
		public char _char;//2
		public int _int;//4
		public long _long;//8
	}

	private static final Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);
	private static Field s_r_A$_byte;
	private static Field s_r_A$_char;
	private static Field s_r_A$_int;
	private static Field s_r_A$_long;
	private static MethodHandle s_u_A$_byte;
	private static MethodHandle s_u_A$_char;
	private static MethodHandle s_u_A$_int;
	private static MethodHandle s_u_A$_long;
	private static MethodHandle s_mh_A$_byte;
	private static MethodHandle s_mh_A$_char;
	private static MethodHandle s_mh_A$_int;
	private static MethodHandle s_mh_A$_long;
	private static final Field sf_r_A$_byte;
	private static final Field sf_r_A$_char;
	private static final Field sf_r_A$_int;
	private static final Field sf_r_A$_long;
	private static final MethodHandle sf_u_A$_byte;
	private static final MethodHandle sf_u_A$_char;
	private static final MethodHandle sf_u_A$_int;
	private static final MethodHandle sf_u_A$_long;
	private static final MethodHandle sf_mh_A$_byte;
	private static final MethodHandle sf_mh_A$_char;
	private static final MethodHandle sf_mh_A$_int;
	private static final MethodHandle sf_mh_A$_long;
	private static final long l_mh_A$_byte;
	private static final long l_mh_A$_char;
	private static final long l_mh_A$_int;
	private static final long l_mh_A$_long;

	static {
		try {
			Lookup lookup = MethodHandles.lookup();
			s_r_A$_byte = A.class.getDeclaredField("_byte");
			s_r_A$_char = A.class.getDeclaredField("_char");
			s_r_A$_int = A.class.getDeclaredField("_int");
			s_r_A$_long = A.class.getDeclaredField("_long");
//			s_r_A$_byte.setAccessible(true);
//			s_r_A$_char.setAccessible(true);
//			s_r_A$_int.setAccessible(true);
//			s_r_A$_long.setAccessible(true);
			s_u_A$_byte = lookup.unreflectGetter(s_r_A$_byte);
			s_u_A$_char = lookup.unreflectGetter(s_r_A$_char);
			s_u_A$_int = lookup.unreflectGetter(s_r_A$_int);
			s_u_A$_long = lookup.unreflectGetter(s_r_A$_long);
			s_mh_A$_byte = lookup.findGetter(A.class, "_byte", byte.class);
			s_mh_A$_char = lookup.findGetter(A.class, "_char", char.class);
			s_mh_A$_int = lookup.findGetter(A.class, "_int", int.class);
			s_mh_A$_long = lookup.findGetter(A.class, "_long", long.class);
			sf_r_A$_byte = s_r_A$_byte;
			sf_r_A$_char = s_r_A$_char;
			sf_r_A$_int = s_r_A$_int;
			sf_r_A$_long = s_r_A$_long;
			sf_u_A$_byte = s_u_A$_byte;
			sf_u_A$_char = s_u_A$_char;
			sf_u_A$_int = s_u_A$_int;
			sf_u_A$_long = s_u_A$_long;
			sf_mh_A$_byte = s_mh_A$_byte;
			sf_mh_A$_char = s_mh_A$_char;
			sf_mh_A$_int = s_mh_A$_int;
			sf_mh_A$_long = s_mh_A$_long;
			l_mh_A$_byte = unsafe.objectFieldOffset(s_r_A$_byte);
			l_mh_A$_char = unsafe.objectFieldOffset(s_r_A$_char);
			l_mh_A$_int = unsafe.objectFieldOffset(s_r_A$_int);
			l_mh_A$_long = unsafe.objectFieldOffset(s_r_A$_long);
		} catch(Exception e) {
			throw new Error(e);
		}
	}
}
