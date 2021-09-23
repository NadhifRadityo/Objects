package io.github.NadhifRadityo.Objects.$Object.Buffer.CustomBuffer;

import io.github.NadhifRadityo.Objects.$Utilizations.BufferUtils;

import java.nio.Buffer;

public abstract class AbstractBuffer<SELF extends AbstractBuffer<SELF>> extends NativeBuffer<SELF> {
	protected final Buffer buffer;

	public AbstractBuffer(Buffer buffer) {
		this.buffer = buffer;

		int elementSize = elementSize();
		int mark = BufferUtils.__getMark(buffer);
		int position = buffer.position();
		int limit = buffer.limit();
		if(mark != -1) {
			buffer.position((mark / elementSize) * elementSize);
			buffer.mark(); }
		buffer.position((position / elementSize) * elementSize);
		buffer.limit((limit / elementSize) * elementSize);
	}

	@Override public Buffer getBuffer() { return buffer; }

	@Override public boolean hasArray() { return buffer.hasArray(); }
	@Override public Object array() throws UnsupportedOperationException { return buffer.array(); }
	@Override public int arrayOffset() { return buffer.arrayOffset(); }
}
