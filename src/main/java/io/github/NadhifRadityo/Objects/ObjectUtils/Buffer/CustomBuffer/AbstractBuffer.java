package io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer;

import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;

import java.nio.Buffer;
import java.util.Objects;

public abstract class AbstractBuffer<SELF extends AbstractBuffer> implements NativeBuffer<SELF> {
	protected final Buffer buffer;
	protected final int elementSize;
	protected final int capacity;
	protected int position;

	protected AbstractBuffer(Buffer buffer, int elementSize, int capacity) {
		this.buffer = buffer;
		this.elementSize = elementSize;
		this.capacity = capacity;
		this.position = 0;
	}

	@Override public Buffer getBuffer() { return buffer; }
	@Override public boolean isDirect() { return buffer.isDirect(); }
	@Override public int elementSize() { return elementSize; }
	@Override public int position() { return position; }
	@Override public int limit() { return capacity; }
	@Override public int capacity() { return capacity; }

	@Override public boolean hasArray() { return buffer.hasArray(); }
	@Override public int arrayOffset() { return hasArray() ? buffer.arrayOffset() : 0; }
	@Override public Object array() throws UnsupportedOperationException { return buffer.array(); }

	@Override public boolean hasRemaining() { return position < capacity; }
	@Override public int remaining() { return capacity - position; }
	@Override public SELF position(int newPos) { ArrayUtils.assertIndex(newPos, capacity, 0); position = newPos; return (SELF) this; }
	@Override public SELF rewind() { position = 0; return (SELF) this; }

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AbstractBuffer<?> that = (AbstractBuffer<?>) o;
		return elementSize == that.elementSize &&
				capacity == that.capacity &&
				position == that.position &&
				buffer.equals(that.buffer);
	}
	@Override public String toString() {
		return "AbstractBuffer[direct " + isDirect() + ", hasArray " + hasArray() + ", capacity " + capacity + ", position " + position +
			", elementSize " + elementSize + ", buffer[capacity " + buffer.capacity() + ", lim " + buffer.limit() + ", pos " + buffer.position() + "]]";
	}
	@Override public int hashCode() {
		return Objects.hash(buffer, elementSize, capacity, position);
	}
}
