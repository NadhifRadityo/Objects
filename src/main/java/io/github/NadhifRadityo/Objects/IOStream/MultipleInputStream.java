package io.github.NadhifRadityo.Objects.IOStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MultipleInputStream extends PipedOutputStream {
	protected final List<PipedInputStream> sinks = new ArrayList<>();
	protected final InputStream inputStream;
	protected final byte[] buffer;
	protected volatile boolean reading = false;
	protected volatile boolean closed = false;
	
	public MultipleInputStream(InputStream inputStream, int bufferSize) {
		this.inputStream = inputStream;
		this.buffer = new byte[bufferSize];
	}
	public MultipleInputStream() {
		this(null, 1024);
	}
	
	public PipedInputStream createInputStream() throws IOException {
		return new PipedInputStream(this, buffer.length);
	}
	public synchronized void connect(PipedInputStream snk) throws IOException {
		setField(PipedOutputStream.class, this, "sink", null);
		super.connect(snk);
		setField(PipedOutputStream.class, this, "sink", null);
		sinks.add(snk);
	}
	public void readInputStream() throws IOException {
		reading = true;
		while(reading) {
			int count = inputStream.read(buffer, 0, buffer.length);
			if(count == -1) break;
			write(buffer, 0, count);
		} stopReadInputStream();
	}
	public void stopReadInputStream() {
		reading = false;
	}
	
	public void write(int b)  throws IOException {
		for(PipedInputStream sink : sinks) {
			setField(PipedOutputStream.class, this, "sink", sink);
			super.write(b);
		} setField(PipedOutputStream.class, this, "sink", null);
	}
	public void write(byte b[], int off, int len) throws IOException {
		for(PipedInputStream sink : sinks) {
			setField(PipedOutputStream.class, this, "sink", sink);
			super.write(b, off, len);
		} setField(PipedOutputStream.class, this, "sink", null);
    }

	public synchronized void flush() throws IOException {
		for(PipedInputStream sink : sinks) {
			setField(PipedOutputStream.class, this, "sink", sink);
			super.flush();
		} setField(PipedOutputStream.class, this, "sink", null);
	}
	public void close() throws IOException {
		for(PipedInputStream sink : sinks) {
			setField(PipedOutputStream.class, this, "sink", sink);
			super.close();
		} setField(PipedOutputStream.class, this, "sink", null);
		stopReadInputStream();
		closed = true;
	}
	public boolean isClosed() {
		return closed;
	}
	
//	@SuppressWarnings("deprecation") //TODO: Java < 9 Support?
	private static void setField(Class<?> klass, Object obj, String fieldName, Object val) throws IOException { try {
		Field field = klass.getDeclaredField(fieldName);
		boolean isAccessible = field.isAccessible();
		field.setAccessible(true);
		field.set(obj, val);
		field.setAccessible(isAccessible);
	} catch (Exception e) { throw new IOException(e); } }
//	@SuppressWarnings("deprecation") //TODO: Java < 9 Support?
	private static Object getField(Class<?> klass, Object obj, String fieldName) throws IOException { try {
		Field field = klass.getDeclaredField(fieldName);
		boolean isAccessible = field.isAccessible();
		field.setAccessible(true);
		Object ret = field.get(obj);
		field.setAccessible(isAccessible);
		return ret;
	} catch (Exception e) { throw new IOException(e); } }
	
	public static boolean isClosed(PipedInputStream inputStream) {
		try {
			return ((Boolean) getField(PipedInputStream.class, inputStream, "closedByWriter")).booleanValue() || 
				   ((Boolean) getField(PipedInputStream.class, inputStream, "closedByReader")).booleanValue();
		} catch (IOException e) { throw new IllegalStateException(e); }
	}
}
