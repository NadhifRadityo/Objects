package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;

import java.util.List;

public class SuffixLogHandler extends SnippetHandler {
	public static final int DEFAULT_PRIORITY = 104;
	public static final Object ENABLED = new Object();
	protected static final SnippetListener listener = (snippet, _handler, record) -> {
		List<Object> args = record.getArgs();
		Object STATE_ENABLED = AttributesHandler.checkOnce(ENABLED, AttributesHandler.ON, args);
		if(STATE_ENABLED != AttributesHandler.ON) return args.size();

		SuffixLogHandler handler = (SuffixLogHandler) _handler;
		snippet.add(handler.suffixCallback.get(record, handler)); return args.size();
	};

	protected ReferencedCallback.StringReferencedCallback suffixCallback;
	public SuffixLogHandler(ReferencedCallback.StringReferencedCallback suffixCallback) { super(listener); this.suffixCallback = suffixCallback; }
	public SuffixLogHandler(String suffix) { this((obj) -> suffix); }

	public ReferencedCallback.StringReferencedCallback getSuffixCallback() { return suffixCallback; }
	public void setSuffixCallback(ReferencedCallback.StringReferencedCallback suffixCallback) { this.suffixCallback = suffixCallback; }
}
