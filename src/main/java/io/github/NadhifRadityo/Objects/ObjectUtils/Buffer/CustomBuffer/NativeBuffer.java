package io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer;

import java.nio.Buffer;

public interface NativeBuffer<SELF extends NativeBuffer> {

	Buffer getBuffer();
	boolean isDirect();
	int elementSize();
	int position();
	int limit();
	int capacity();

	boolean hasArray();
	int arrayOffset();
	Object array() throws UnsupportedOperationException;

	boolean hasRemaining();
	int remaining();
	SELF position(int newPos);
	SELF rewind();
	SELF slice();
}
