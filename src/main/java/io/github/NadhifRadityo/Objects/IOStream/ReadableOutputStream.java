package io.github.NadhifRadityo.Objects.IOStream;

import io.github.NadhifRadityo.Objects.List.QueueList;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;

import static io.github.NadhifRadityo.Objects.Utilizations.StreamUtils.EOF;

public class ReadableOutputStream extends OutputStream {
	protected final QueueList<byte[]> queue = new QueueList<>(true);
	protected byte[] buffer; protected int currentPos;
	protected volatile boolean accessing = false;

	protected boolean retrieve() {
		if(buffer == EOF) return true;
		if(buffer != null && buffer.length - currentPos > 0) return false;
		buffer = queue.get(); currentPos = 0; return buffer == EOF;
	}
	protected synchronized void begin() { while(accessing) ExceptionUtils.doSilentThrowsRunnable(false, this::wait); accessing = true; }
	protected synchronized void end() { accessing = false; notify(); }

	public int read() throws IOException { if(retrieve()) throw new EOFException(); synchronized(this) { begin(); try { return buffer[currentPos++]; } finally { end(); } } }
	public int read(byte[] b, int off, int len) {
		ArrayUtils.assertIndex(off, b.length, len);
		if(retrieve()) return -1; synchronized(this) { begin(); try { int size = Math.min(len, buffer.length - currentPos);
		System.arraycopy(buffer, currentPos, b, off, size); currentPos += size; return size;
	} finally { end(); } } } public int read(byte[] b) { return read(b, 0, b.length); }

	@Override public synchronized void write(int b) { begin(); try { queue.add(new byte[] { ((Integer) b).byteValue() }); } finally { end(); } }
	@Override public synchronized void write(byte[] b, int off, int len) { begin(); try {
		ArrayUtils.assertIndex(off, b.length, len); byte[] croppedBuffer = new byte[len];
		System.arraycopy(b, off, croppedBuffer, 0, len); queue.add(croppedBuffer);
	} finally { end(); } } public synchronized void write(byte[] b) { write(b, 0, b.length); }

	@Override public synchronized void close() throws IOException { begin(); try { queue.add(EOF); super.close(); } finally { end(); } }
	public int available() { retrieve(); synchronized(this) { begin(); try { return buffer.length - currentPos; } finally { end(); } } }
}
