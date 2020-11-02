package io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer;

import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static io.github.NadhifRadityo.Objects.Utilizations.SystemUtils.IS_OS_32BIT;

public class PointerBuffer extends AbstractBuffer<PointerBuffer> {
	public static final int ELEMENT_SIZE = IS_OS_32BIT ? Integer.BYTES : Long.BYTES;

	public static PointerBuffer wrap(ByteBuffer buffer) { return IS_OS_32BIT ? new PointerBuffer(buffer.asIntBuffer()) : new PointerBuffer(buffer.asLongBuffer()); }
	public static PointerBuffer allocateDirect(int size) { return wrap(BufferUtils.createByteBuffer(size * ELEMENT_SIZE)); }

	protected PointerBuffer(Buffer buffer) {
		super(buffer, ELEMENT_SIZE, buffer.capacity());
	}

	public long get(int index) {
		ArrayUtils.assertIndex(index, capacity, 1);
		return !IS_OS_32BIT ? ((LongBuffer) buffer).get(index) : ((IntBuffer) buffer).get(index) & 0x00000000FFFFFFFFL;
	}
	public long get() { return get(position++); }
	public PointerBuffer get(long[] dst, int offset, int length) {
		ArrayUtils.assertCopyIndex(0, remaining(), offset, dst.length, length);
		int end = offset + length;
		for(int i = offset; i < end; i++) dst[i] = get(); return this;
	}

	public PointerBuffer put(int index, long value) {
		ArrayUtils.assertIndex(index, capacity, 1);
		if(IS_OS_32BIT) ((IntBuffer) buffer).put(index, (int) value);
		else ((LongBuffer) buffer).put(index, value); return this;
	}
	public PointerBuffer put(long value) { return put(position++, value); }
	public PointerBuffer put(long[] src, int offset, int length) {
		ArrayUtils.assertCopyIndex(offset, src.length, 0, remaining(), length);
		int end = offset + length;
		for(int i = offset; i < end; i++) put(src[i]); return this;
	}

	public PointerBuffer put(PointerBuffer src) {
		ArrayUtils.assertIndex(0, remaining(), src.remaining());
		while(src.hasRemaining()) put(src.get()); return this;
	}

	@Override public PointerBuffer slice() { return new PointerBuffer(BufferUtils.slice(buffer)); }
}
