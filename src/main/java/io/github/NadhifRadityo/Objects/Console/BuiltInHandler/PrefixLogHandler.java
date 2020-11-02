package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;

import java.util.List;

public class PrefixLogHandler extends SnippetHandler {
	public static final int DEFAULT_PRIORITY = 104;
	public static final Object ENABLED = new Object();
	protected static final SnippetListener listener = (snippet, _handler, record) -> {
		List<Object> args = record.getArgs();
		Object STATE_ENABLED = AttributesHandler.checkOnce(ENABLED, AttributesHandler.ON, args);
		if(STATE_ENABLED != AttributesHandler.ON) return 0;

		PrefixLogHandler handler = (PrefixLogHandler) _handler;
		snippet.add(handler.prefixCallback.get(record, handler)); return 0;
	};

	protected ReferencedCallback.StringReferencedCallback prefixCallback;
	public PrefixLogHandler(ReferencedCallback.StringReferencedCallback prefixCallback) { super(listener); this.prefixCallback = prefixCallback; }
	public PrefixLogHandler(String prefix) { this((obj) -> prefix); }

	public ReferencedCallback.StringReferencedCallback getPrefixCallback() { return prefixCallback; }
	public void setPrefixCallback(ReferencedCallback.StringReferencedCallback prefixCallback) { this.prefixCallback = prefixCallback; }
}
