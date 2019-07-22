package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Console.LogHandler;
import io.github.NadhifRadityo.Objects.Console.LogRecord;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;

public class PrefixLogHandler implements LogHandler {
	public static final int DEFAULT_PRIORITY = 103;

	protected ReferencedCallback.StringReferencedCallback prefixCallback;
	public PrefixLogHandler(ReferencedCallback.StringReferencedCallback prefixCallback) { this.prefixCallback = prefixCallback; }
	public PrefixLogHandler(String prefix) { this((obj) -> prefix); }

	@Override public void manipulateLog(LogRecord record) {
		if(prefixCallback == null) return;
		record.getArgs().add(0, prefixCallback.get(record, this));
	}

	public ReferencedCallback.StringReferencedCallback getPrefixCallback() { return prefixCallback; }
	public void setPrefixCallback(ReferencedCallback.StringReferencedCallback prefixCallback) { this.prefixCallback = prefixCallback; }
}
