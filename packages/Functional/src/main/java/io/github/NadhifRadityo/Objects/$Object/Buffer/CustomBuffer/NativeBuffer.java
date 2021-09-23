package io.github.NadhifRadityo.Objects.$Object.Buffer.CustomBuffer;

import io.github.NadhifRadityo.Objects.$Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.BufferUtils;

import java.nio.Buffer;

public abstract class NativeBuffer<SELF extends NativeBuffer<SELF>> {

	protected abstract SELF self();
	public abstract SELF slice();
	public abstract SELF duplicate();
	public abstract SELF asReadOnlyBuffer();
	public abstract Buffer getBuffer();
	public abstract int elementSize();

	public int capacity() { return BufferUtils.getBytesCapacity(getBuffer()) / elementSize(); }
	public int position() { return BufferUtils.getBytesPosition(getBuffer()) / elementSize(); }
	public SELF position(int position) { BufferUtils.setBytesPosition(getBuffer(), position * elementSize()); return self(); }
	public int limit() { return BufferUtils.getBytesLimit(getBuffer()) / elementSize(); }
	public SELF limit(int limit) { BufferUtils.setBytesLimit(getBuffer(), limit * elementSize()); return self(); }
	public SELF mark() { getBuffer().mark(); return self(); }
	public SELF reset() { getBuffer().reset(); return self(); }
	public SELF clear() { getBuffer().clear(); return self(); }
	public SELF flip() { getBuffer().flip(); return self(); }
	public SELF rewind() { getBuffer().rewind(); return self(); }
	public int remaining() { return getBuffer().remaining() / elementSize(); }
	public boolean hasRemaining() { return getBuffer().hasRemaining(); }
	public boolean isReadOnly() { return getBuffer().isReadOnly(); }
	public abstract boolean hasArray();
	public abstract Object array() throws UnsupportedOperationException;
	public abstract int arrayOffset();
	public boolean isDirect() { return getBuffer().isDirect(); }

	protected int nextGetIndex(int n) { int position = position(); int limit = limit(); ArrayUtils.assertIndex(position, limit, n); position(position + n); return position; }
	protected int nextGetIndex() { return nextGetIndex(1); }
	protected int nextPutIndex(int n) { int position = position(); int limit = limit(); ArrayUtils.assertIndex(position, limit, n); position(position + n); return position; }
	protected int nextPutIndex() { return nextPutIndex(1); }
	protected int checkIndex(int i, int nb) { int limit = limit(); ArrayUtils.assertIndex(i, limit, nb); return i; }
	protected int checkIndex(int i) { return checkIndex(i, 1); }
}
