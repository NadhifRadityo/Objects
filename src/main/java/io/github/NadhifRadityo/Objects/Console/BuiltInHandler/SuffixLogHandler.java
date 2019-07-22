package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Console.LogHandler;
import io.github.NadhifRadityo.Objects.Console.LogRecord;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;

public class SuffixLogHandler implements LogHandler {
	public static final int DEFAULT_PRIORITY = 101;

	protected ReferencedCallback.StringReferencedCallback suffixCallback;
	public SuffixLogHandler(ReferencedCallback.StringReferencedCallback suffixCallback) { this.suffixCallback = suffixCallback; }
	public SuffixLogHandler(String suffix) { this((obj) -> suffix); }

	@Override public void manipulateLog(LogRecord record) {
		if(suffixCallback == null) return;
		record.getArgs().add(suffixCallback.get(record, this));
	}

	public ReferencedCallback.StringReferencedCallback getSuffixCallback() { return suffixCallback; }
	public void setSuffixCallback(ReferencedCallback.StringReferencedCallback suffixCallback) { this.suffixCallback = suffixCallback; }
}
