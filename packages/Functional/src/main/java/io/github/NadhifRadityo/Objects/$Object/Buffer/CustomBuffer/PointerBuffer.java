package io.github.NadhifRadityo.Objects.$Object.Buffer.CustomBuffer;

import io.github.NadhifRadityo.Objects.$Utilizations.ArrayUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static io.github.NadhifRadityo.Objects.$Utilizations.SystemUtils.IS_OS_32BIT;

public class PointerBuffer extends AbstractBuffer<PointerBuffer> {
	public static final int ELEMENT_SIZE_EXPONENT = IS_OS_32BIT ? 2 : 3;
	public static final int ELEMENT_SIZE = IS_OS_32BIT ? Integer.BYTES : Long.BYTES;
	public static final int ALIGNMENT = IS_OS_32BIT ? Integer.BYTES : Long.BYTES;

	public static PointerBuffer wrap(ByteBuffer buffer) { return IS_OS_32BIT ? new PointerBuffer(buffer.asIntBuffer()) : new PointerBuffer(buffer.asLongBuffer()); }
	public static PointerBuffer allocate(int size) { return wrap(ByteBuffer.allocate(size * ELEMENT_SIZE)); }
	public static PointerBuffer allocateDirect(int size) { return wrap(ByteBuffer.allocateDirect(size * ELEMENT_SIZE)); }

	protected PointerBuffer(Buffer buffer) {
		super(buffer);
	}

	@Override protected final PointerBuffer self() { return this; }
	@Override public PointerBuffer slice() { return !IS_OS_32BIT ? new PointerBuffer(((LongBuffer) buffer).slice()) : new PointerBuffer(((IntBuffer) buffer).slice()); }
	@Override public PointerBuffer duplicate() { return !IS_OS_32BIT ? new PointerBuffer(((LongBuffer) buffer).duplicate()) : new PointerBuffer(((IntBuffer) buffer).duplicate()); }
	@Override public PointerBuffer asReadOnlyBuffer() { return !IS_OS_32BIT ? new PointerBuffer(((LongBuffer) buffer).asReadOnlyBuffer()) : new PointerBuffer(((IntBuffer) buffer).asReadOnlyBuffer()); }
	@Override public final int elementSize() { return PointerBuffer.ELEMENT_SIZE; }

	protected long get0(int index) {
		if(!IS_OS_32BIT) return ((LongBuffer) buffer).get(index);
		else return ((IntBuffer) buffer).get(index) & 0x00000000FFFFFFFFL;
	}
	public long get(int index) { return get0(checkIndex(index)); }
	public long get() { return get0(nextGetIndex()); }
	public PointerBuffer get(long[] dst, int offset, int length) {
		ArrayUtils.assertCopyIndex(0, remaining(), offset, dst.length, length);
		int end = offset + length; for(int i = offset; i < end; i++) dst[i] = get(); return self();
	}

	public PointerBuffer get(PointerBuffer dst) {
		ArrayUtils.assertIndex(0, dst.remaining(), remaining());
		while(hasRemaining()) dst.put(get()); return self();
	}

	protected PointerBuffer put0(int index, long value) {
		if(!IS_OS_32BIT) ((LongBuffer) buffer).put(index, value);
		else ((IntBuffer) buffer).put(index, (int) value);
		return self();
	}
	public PointerBuffer put(int index, long value) { return put0(checkIndex(index), value); }
	public PointerBuffer put(long value) { return put0(nextPutIndex(), value); }
	public PointerBuffer put(long[] src, int offset, int length) {
		ArrayUtils.assertCopyIndex(offset, src.length, 0, remaining(), length);
		int end = offset + length; for(int i = offset; i < end; i++) put(src[i]); return self();
	}

	public PointerBuffer put(PointerBuffer src) {
		ArrayUtils.assertIndex(0, remaining(), src.remaining());
		while(src.hasRemaining()) put(src.get()); return self();
	}
}
