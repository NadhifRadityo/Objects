package io.github.NadhifRadityo.Objects.Console.BuiltInListener;

import io.github.NadhifRadityo.Objects.Console.LogListener;
import io.github.NadhifRadityo.Objects.Console.LogRecord;

import java.io.OutputStream;
import java.nio.charset.Charset;

public class OutputStreamListener  implements LogListener {
	public static final int defaultMaxLength = 100000;
	public static final int defaultCroppedMaxLength = 50000;

	protected final OutputStream outputStream;
	protected int maxLength;
	protected int croppedMaxLength;

	public OutputStreamListener(OutputStream outputStream, int maxLength, int croppedMaxLength) {
		this.outputStream = outputStream;
		this.maxLength = maxLength;
		this.croppedMaxLength = croppedMaxLength;
	} public OutputStreamListener(OutputStream outputStream) { this(outputStream, defaultMaxLength, defaultCroppedMaxLength); }

	@Override public void onLog(LogRecord record) {
		String formatted = getFormattedRecord(record); if(formatted == null) return;
		try { outputStream.write(formatted.getBytes(Charset.defaultCharset()));
		} catch(Exception e) { throw new Error(e); }
	}

	public int getMaxLength() { return maxLength; }
	public int getCroppedMaxLength() { return croppedMaxLength; }
	public void setMaxLength(int maxLength) { this.maxLength = maxLength; }
	public void setCroppedMaxLength(int croppedMaxLength) { this.croppedMaxLength = croppedMaxLength; }

	protected String getFormattedRecord(LogRecord record) {
		String asString = record.asString();
		if(asString.length() >= defaultMaxLength) asString = null;
		else if(asString.length() >= defaultCroppedMaxLength)
			asString = asString.substring(0, 50000) + "(" + (asString.length() - 50000) + " more...)";
		return asString;
	}
}
