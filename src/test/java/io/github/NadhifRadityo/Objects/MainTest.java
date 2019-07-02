package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.IOStream.WriteableInputStream;
import io.github.NadhifRadityo.Objects.Thread.HandlerThread;

public class MainTest {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		WriteableInputStream writeableInputStream = new WriteableInputStream();
		HandlerThread handlerThread = new HandlerThread("Test");
		handlerThread.start();
		handlerThread.getThreadHandler().postThrowable(() -> {
			int count; byte[] buffer = new byte[1024];
			while((count = writeableInputStream.read(buffer, 0, buffer.length)) != -1) {
				System.out.println(buffer.length + " ? " + count);
				System.out.println(new String(buffer, 0, count));
			} System.out.println("Exited"); handlerThread.stop();
		});
		
		int count; byte[] buffer = new byte[1024 * 1024];
		while((count = System.in.read(buffer, 0, buffer.length)) != -1) {
			String line = new String(buffer, 0, count - 2);
			if(line.equalsIgnoreCase("exit")) { writeableInputStream.close(); break; }
			else writeableInputStream.write(line.getBytes());
		}
	}

}
