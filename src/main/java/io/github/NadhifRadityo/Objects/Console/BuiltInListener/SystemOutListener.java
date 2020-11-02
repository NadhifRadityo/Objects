package io.github.NadhifRadityo.Objects.Console.BuiltInListener;

import io.github.NadhifRadityo.Objects.Console.LogRecord;

public class SystemOutListener extends OutputStreamListener {
	protected final OutputStreamListener systemOutListener;
	protected final OutputStreamListener systemErrListener;

	public SystemOutListener(int maxLength, int croppedMaxLength) {
		super(null);
		this.systemOutListener = new OutputStreamListener(System.out, maxLength, croppedMaxLength);
		this.systemErrListener = new OutputStreamListener(System.err, maxLength, croppedMaxLength);
	} public SystemOutListener() { this(defaultMaxLength, defaultCroppedMaxLength); }

	@Override public void onLog(LogRecord record) { systemOutListener.onLog(record); }
	@Override public void onError(LogRecord record) { systemErrListener.onError(record); }

	public int getMaxLength() { return systemOutListener.getMaxLength(); }
	public int getCroppedMaxLength() { return systemOutListener.getCroppedMaxLength(); }
	public void setMaxLength(int maxLength) { systemOutListener.setMaxLength(maxLength); systemErrListener.setMaxLength(maxLength); }
	public void setCroppedMaxLength(int croppedMaxLength) { systemOutListener.setCroppedMaxLength(croppedMaxLength); systemErrListener.setCroppedMaxLength(croppedMaxLength); }
}
