package io.github.NadhifRadityo.Objects.IOStream;

import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import static io.github.NadhifRadityo.Objects.Utilizations.StreamUtils.EOF;

public class TunneledInputStream extends InputStream {
	protected final TunneledOutputStream linked;

	public TunneledInputStream(TunneledOutputStream linked) {
		this.linked = linked;
	}

	@Override public int read() throws IOException { synchronized(linked) { linked.begin(); try { if(linked.retrieve()) throw new EOFException(); return linked.buffer[linked.currentPos++]; } finally { linked.end(); } } }
	@Override public int read(byte[] b, int off, int len) { synchronized(linked) { linked.begin(); try {
		ArrayUtils.assertIndex(off, b.length, len);
		if(linked.retrieve()) return -1; int size = Math.min(len, linked.buffer.length - linked.currentPos);
		System.arraycopy(linked.buffer, linked.currentPos, b, off, size); linked.currentPos += size; return size;
	} finally { linked.end(); } } } @Override public int read(byte[] b) { return read(b, 0, b.length); }

	@Override public void close() throws IOException { synchronized(linked) { linked.begin(); linked.queue.add(EOF); super.close(); } }
	@Override public int available() { synchronized(linked) { linked.begin(); try { linked.retrieve(); return linked.buffer.length - linked.currentPos; } finally { linked.end(); } } }
}
