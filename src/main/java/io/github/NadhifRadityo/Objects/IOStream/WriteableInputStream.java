package io.github.NadhifRadityo.Objects.IOStream;

import io.github.NadhifRadityo.Objects.List.QueueList;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class WriteableInputStream extends InputStream {
	protected static final byte[] EOF = new byte[] {};
	protected final QueueList<byte[]> queue = new QueueList<>();
	protected byte[] buffer; protected int currentPos;
	
	protected boolean retrieve() {
		if(buffer == EOF) return true;
		if(buffer != null && buffer.length - currentPos > 0) return false;
		buffer = queue.get(); currentPos = 0; return buffer == EOF;
	}
	
	@Override public int read() throws IOException { if(retrieve()) throw new EOFException(); return buffer[currentPos++]; }
	@Override public int read(byte[] b, int off, int len) {
		if(off + len > b.length) throw new ArrayIndexOutOfBoundsException();
		if(retrieve()) return -1; int size = Math.min(len, buffer.length - currentPos);
		System.arraycopy(buffer, currentPos, b, off, size); currentPos += size; return size;
	} @Override public int read(byte[] b) { return read(b, 0, b.length); }
	
	public void write(int b) { queue.add(new byte[] { ((Integer) b).byteValue() }); }
	public void write(byte[] b, int off, int len) {
		if(off + len > b.length) throw new ArrayIndexOutOfBoundsException(); byte[] croppedBuffer = new byte[len];
		System.arraycopy(b, off, croppedBuffer, 0, len); queue.add(croppedBuffer);
	} public void write(byte[] b) { write(b, 0, b.length); }
	
	@Override public void close() throws IOException { queue.add(EOF); super.close(); }
	@Override public int available() { retrieve(); return buffer.length - currentPos; }
}
