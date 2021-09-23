package io.github.NadhifRadityo.Objects.$Object.Stream;

import io.github.NadhifRadityo.Objects.$Object.List.QueueList;
import io.github.NadhifRadityo.Objects.$Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.ExceptionUtils;

import java.io.IOException;
import java.io.OutputStream;

import static io.github.NadhifRadityo.Objects.$Utilizations.StreamUtils.EOF;

public class TunneledOutputStream extends OutputStream {
	protected final QueueList<byte[]> queue = new QueueList<>(true);
	protected byte[] buffer; protected int currentPos;
	protected volatile boolean accessing = false;

	protected boolean retrieve() {
		if(buffer == EOF) return true;
		if(buffer != null && buffer.length - currentPos > 0) return false;
		buffer = queue.get(); currentPos = 0; return buffer == EOF;
	}
	protected void begin() { while(accessing) ExceptionUtils.doSilentThrowsRunnable(false, this::wait); accessing = true; }
	protected void end() { accessing = false; notify(); }

	@Override public void write(int b) { queue.add(new byte[] { ((Integer) b).byteValue() }); }
	@Override public void write(byte[] b, int off, int len) {
		ArrayUtils.assertIndex(off, b.length, len); byte[] croppedBuffer = new byte[len];
		System.arraycopy(b, off, croppedBuffer, 0, len); queue.add(croppedBuffer);
	} @Override public void write(byte[] b) { write(b, 0, b.length); }

	@Override public void close() throws IOException { queue.add(EOF); super.close(); }
}
