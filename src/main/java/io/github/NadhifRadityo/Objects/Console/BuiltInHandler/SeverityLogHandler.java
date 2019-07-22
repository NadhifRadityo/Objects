package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Console.LogHandler;
import io.github.NadhifRadityo.Objects.Console.LogLevel;
import io.github.NadhifRadityo.Objects.Console.LogRecord;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Pool.Pool;

public class SeverityLogHandler implements LogHandler {
	public static final int DEFAULT_PRIORITY = 102;

	protected ReferencedCallback<AnsiLogHandler.AnsiColor> colorCallback;
	public SeverityLogHandler(ReferencedCallback<AnsiLogHandler.AnsiColor> colorCallback) { this.colorCallback = colorCallback; }
	public SeverityLogHandler() { }

	@Override public void manipulateLog(LogRecord record) {
		boolean coloredSupport = AnsiLogHandler.isAnsiSupport(record.getLogger());
		StringBuilder builder = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
		if(coloredSupport) builder.append(AnsiLogHandler.AnsiColor.YELLOW.asForeground().asCommand().toString()); builder.append("[");
		if(coloredSupport) builder.append(getLevelSeverityColor(record.getLogLevel()).asForeground().asCommand().toString()); builder.append(record.getLogLevel().name());
		if(coloredSupport) builder.append(AnsiLogHandler.AnsiColor.YELLOW.asForeground().asCommand().toString()); builder.append("]");
		if(coloredSupport) builder.append(AnsiLogHandler.AnsiAttribute.RESET.asSGRParam().asCommand().toString());
		record.getArgs().add(0, builder.toString());
		Pool.returnObject(StringBuilder.class, builder);
	}

	public ReferencedCallback<AnsiLogHandler.AnsiColor> getColorCallback() { return colorCallback; }
	public void setColorCallback(ReferencedCallback<AnsiLogHandler.AnsiColor> colorCallback) { this.colorCallback = colorCallback; }

	public AnsiLogHandler.AnsiColor getLevelSeverityColor(LogLevel logLevel) {
		if(colorCallback != null) return colorCallback.get(logLevel, this);
		switch(logLevel) {
			case LOG: return AnsiLogHandler.AnsiColor.WHITE;
			case INFO: return AnsiLogHandler.AnsiColor.GREEN;
			case DEBUG: return AnsiLogHandler.AnsiColor.PURPLE;
			case WARN: return AnsiLogHandler.AnsiColor.YELLOW;
			case ERROR: return AnsiLogHandler.AnsiColor.RED;
		} return AnsiLogHandler.AnsiColor.WHITE;
	}
}
