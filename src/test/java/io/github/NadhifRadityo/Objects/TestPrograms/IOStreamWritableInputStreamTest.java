package io.github.NadhifRadityo.Objects.TestPrograms;

import io.github.NadhifRadityo.Objects.Console.LogListener;
import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.IOStream.WritableInputStream;
import io.github.NadhifRadityo.Objects.ObjectUtils.RandomString;
import io.github.NadhifRadityo.Objects.TestProgram;
import io.github.NadhifRadityo.Objects.Tester;
import io.github.NadhifRadityo.Objects.Thread.HandlerThread;
import io.github.NadhifRadityo.Objects.Thread.RunnablePost;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@TestProgram(group = "Utilizations")
public class IOStreamWritableInputStreamTest extends Tester {
	public IOStreamWritableInputStreamTest() {
		super("IOStreamWritableInputStreamTest");
	}

	@Override protected boolean doRun(Logger logger, ByteBuffer dump) throws Throwable {
		WritableInputStream writableInputStream = new WritableInputStream();
		WritableInputStream systemInputStream = new WritableInputStream();
		dump.position(Integer.BYTES);
		LogListener logListener = record -> {
			if(record == null) { dump.putInt(0, dump.position()); return; }
			byte[] bytes = record.asString().getBytes(StandardCharsets.UTF_8); dump.put(bytes);
		}; logger.addListener(logListener, -1);

		StringBuilder receive = new StringBuilder();
		StringBuilder send = new StringBuilder();

		HandlerThread handlerThreadReader = new HandlerThread(getName() + " #Reader");
		handlerThreadReader.start();
		handlerThreadReader.getLooper().setProgressHandler((current, total, desc, job, looper) -> {
			String title = job instanceof RunnablePost.IdentifiedRunnablePost ? ((RunnablePost.IdentifiedRunnablePost) job).getTitle() : null;
			String subject = job instanceof RunnablePost.IdentifiedRunnablePost ? ((RunnablePost.IdentifiedRunnablePost) job).getSubject() : null;
			logger.log("Title: " + title + " Subject: " + subject + " Desc: " + desc + " (" + (current / total) * 100 + ")");
		});
		handlerThreadReader.getThreadHandler().postThrowable(() -> {
			int count; byte[] buffer = new byte[1024];
			while((count = writableInputStream.read(buffer, 0, buffer.length)) != -1) {
				logger.log(buffer.length + " ? " + count);
				logger.log(new String(buffer, 0, count));
				receive.append(new String(buffer, 0, count));
			} logger.log("Exited"); handlerThreadReader.quit();
		});

		HandlerThread handlerThreadWriter = new HandlerThread(getName() + " #Writer");
		handlerThreadWriter.start();
		handlerThreadReader.getLooper().setProgressHandler((current, total, desc, job, looper) -> {
			String title = job instanceof RunnablePost.IdentifiedRunnablePost ? ((RunnablePost.IdentifiedRunnablePost) job).getTitle() : null;
			String subject = job instanceof RunnablePost.IdentifiedRunnablePost ? ((RunnablePost.IdentifiedRunnablePost) job).getSubject() : null;
			logger.log("Title: " + title + " Subject: " + subject + " Desc: " + desc + " (" + (current / total) * 100 + ")");
		});
		handlerThreadWriter.getThreadHandler().postThrowable(() -> {
			int count; byte[] buffer = new byte[1024 * 1024];
			while((count = systemInputStream.read(buffer, 0, buffer.length)) != -1) {
				String line = new String(buffer, 0, count - 2);
				if(line.equalsIgnoreCase("exit")) { writableInputStream.close(); handlerThreadWriter.quit(); break; }
				writableInputStream.write(line.getBytes()); send.append(line);
			} writableInputStream.close(); handlerThreadWriter.quit();
		});

		RandomString randomString = new RandomString(1024 * 2);
		for(int i = 0; i < 27; i++) systemInputStream.write(randomString.nextString().getBytes(StandardCharsets.UTF_8));
		systemInputStream.write("exit".getBytes(StandardCharsets.UTF_8)); systemInputStream.close();

		handlerThreadReader.join();
		handlerThreadWriter.join();
		logger.removeListener(logListener);
		return receive.toString().equals(send.toString());
	}
}
