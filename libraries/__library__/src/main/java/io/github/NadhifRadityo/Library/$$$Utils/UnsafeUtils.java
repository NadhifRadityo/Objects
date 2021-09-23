package io.github.NadhifRadityo.Library.$$$Utils;

import sun.misc.Unsafe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public interface UnsafeUtils {
	static byte[] getTempByteArray(int length) {
		WeakReference<byte[]> reference = $.tempByteArray.get(); byte[] result = reference == null ? null : reference.get();
		if(result == null || result.length < length) { result = new byte[length]; $.tempByteArray.set(new WeakReference<>(result)); } return result;
	}
	static ByteArrayOutputStream getTempOutputBuffer() {
		ByteArrayOutputStream result = $.tempOutputBuffer.get() == null || $.tempOutputBuffer.get().get() == null ? null : $.tempOutputBuffer.get().get();
		if(result == null) { result = new ByteArrayOutputStream(); $.tempOutputBuffer.set(new WeakReference<>(result)); } return result;
	}
	static ByteArrayInputStream getTempInputBuffer(byte[] bytes, int off, int len) {
		ByteArrayInputStream result = $.tempInputBuffer.get() == null || $.tempInputBuffer.get().get() == null ? null : $.tempInputBuffer.get().get();
		if(result == null) { result = new ByteArrayInputStream(bytes, off, len); $.tempInputBuffer.set(new WeakReference<>(result)); }
		$.unsafe.putObject(result, $.AFIELD_ByteArrayInputStream_buf, bytes);
		$.unsafe.putInt(result, $.AFIELD_ByteArrayInputStream_pos, off);
		$.unsafe.putInt(result, $.AFIELD_ByteArrayInputStream_mark, off);
		$.unsafe.putInt(result, $.AFIELD_ByteArrayInputStream_count, Math.min(off + len, bytes.length));
		return result;
	}

	class $ {
		public static final Unsafe unsafe;
		protected static final long AFIELD_ByteArrayInputStream_buf;
		protected static final long AFIELD_ByteArrayInputStream_pos;
		protected static final long AFIELD_ByteArrayInputStream_mark;
		protected static final long AFIELD_ByteArrayInputStream_count;
		protected static final ThreadLocal<WeakReference<byte[]>> tempByteArray = new ThreadLocal<>();
		protected static final ThreadLocal<WeakReference<ByteArrayOutputStream>> tempOutputBuffer = new ThreadLocal<>();
		protected static final ThreadLocal<WeakReference<ByteArrayInputStream>> tempInputBuffer = new ThreadLocal<>();

		static { try {
			Field FIELD_Unsafe_theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			FIELD_Unsafe_theUnsafe.setAccessible(true);
			unsafe = (Unsafe) FIELD_Unsafe_theUnsafe.get(null);

			Field FIELD_ByteArrayInputStream_buf = ByteArrayInputStream.class.getDeclaredField("buf");
			Field FIELD_ByteArrayInputStream_pos = ByteArrayInputStream.class.getDeclaredField("pos");
			Field FIELD_ByteArrayInputStream_mark = ByteArrayInputStream.class.getDeclaredField("mark");
			Field FIELD_ByteArrayInputStream_count = ByteArrayInputStream.class.getDeclaredField("count");
			AFIELD_ByteArrayInputStream_buf = unsafe.objectFieldOffset(FIELD_ByteArrayInputStream_buf);
			AFIELD_ByteArrayInputStream_pos = unsafe.objectFieldOffset(FIELD_ByteArrayInputStream_pos);
			AFIELD_ByteArrayInputStream_mark = unsafe.objectFieldOffset(FIELD_ByteArrayInputStream_mark);
			AFIELD_ByteArrayInputStream_count = unsafe.objectFieldOffset(FIELD_ByteArrayInputStream_count);
		} catch(Exception e) { throw new Error(e); } }
	}
}
