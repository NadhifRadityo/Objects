package io.github.NadhifRadityo.Library.Utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.channels.ClosedByInterruptException;

import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.error;

public class ExceptionUtils {

	public static String throwableToString(Throwable throwable) {
		try(StringWriter stringWriter = new StringWriter(); PrintWriter printWriter = new PrintWriter(stringWriter)) {
			throwable.printStackTrace(printWriter);
			return stringWriter.toString();
		} catch(IOException e) {
			throw new Error(e);
		}
	}
	public static <E extends Throwable> E exception(E e) {
		if(e instanceof InterruptedException)
			throw new MustNotCatchThisError(e);
		if(e instanceof ClosedByInterruptException)
			throw new MustNotCatchThisError(e);
		if(e instanceof InterruptedIOException)
			throw new MustNotCatchThisError(e);
		return e;
	}
	private static class MustNotCatchThisError extends Error {
		public MustNotCatchThisError(Throwable e) {
			super(e);
			error("%s", e);
		}
	}
}
