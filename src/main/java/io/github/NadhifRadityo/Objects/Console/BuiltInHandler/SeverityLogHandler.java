package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Console.LogLevel;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;

import java.util.List;

public class SeverityLogHandler extends SnippetHandler {
	public static final int DEFAULT_PRIORITY = 103;
	public static final Object ENABLED = new Object();
	protected static final SnippetListener listener = (snippet, _handler, record) -> {
		List<Object> args = record.getArgs();
		Object STATE_ENABLED = AttributesHandler.checkOnce(ENABLED, AttributesHandler.ON, args);
		if(STATE_ENABLED != AttributesHandler.ON) return 0;

		SeverityLogHandler handler = (SeverityLogHandler) _handler;
		boolean ansiSupported = AnsiLogHandler.isAnsiSupported(record.getLogger());
		if(ansiSupported) snippet.add(handler.getLevelSeverityColor(null)); snippet.add("[");
		if(ansiSupported) snippet.add(handler.getLevelSeverityColor(record.getLogLevel())); snippet.add(record.getLogLevel().name());
		if(ansiSupported) snippet.add(handler.getLevelSeverityColor(null)); snippet.add("] ");
		if(ansiSupported) snippet.add(AnsiLogHandler.AnsiColor.DEFAULT.asForeground()); return 0;
	};

	protected ReferencedCallback<Object> colorCallback;
	public SeverityLogHandler(ReferencedCallback<Object> colorCallback) { super(listener); this.colorCallback = colorCallback; }
	public SeverityLogHandler() { this(null); }

	public ReferencedCallback<Object> getColorCallback() { return colorCallback; }
	public void setColorCallback(ReferencedCallback<Object> colorCallback) { this.colorCallback = colorCallback; }

	public Object getLevelSeverityColor(LogLevel logLevel) {
		if(colorCallback != null) return colorCallback.get(logLevel, this);
		if(logLevel == null) return AnsiLogHandler.AnsiColor.YELLOW;
		switch(logLevel) {
			case LOG: return AnsiLogHandler.AnsiColor.WHITE;
			case INFO: return AnsiLogHandler.AnsiColor.GREEN;
			case DEBUG: return AnsiLogHandler.AnsiColor.PURPLE;
			case WARN: return AnsiLogHandler.AnsiColor.YELLOW;
			case ERROR: return AnsiLogHandler.AnsiColor.RED;
		} return AnsiLogHandler.AnsiColor.WHITE;
	}
}
