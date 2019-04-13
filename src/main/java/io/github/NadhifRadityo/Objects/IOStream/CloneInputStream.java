package io.github.NadhifRadityo.Objects.IOStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

@Deprecated
/*
 * Use MultipleInputStream instead!
 */
public class CloneInputStream extends PipedOutputStream {
	private Scanner scanner;
	private Thread thread;
	private volatile boolean isClosed = false;
	
	public CloneInputStream(InputStream inputStream, PipedInputStream pipedInputStream) throws IOException {
		connect(pipedInputStream);
		byte[] read = new byte[inputStream.available()];
		inputStream.read(read);
		write(read);
		
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				byte nextByte;
				while(!isClosed && (nextByte = scanner.nextByte()) != -1)
					try { write(nextByte); } catch (IOException e) { e.printStackTrace(); }
			}
		}); thread.start();
	}
	
//	@SuppressWarnings("deprecation")
	public void close() throws IOException {
		super.close();
		thread.stop();
	}
}
