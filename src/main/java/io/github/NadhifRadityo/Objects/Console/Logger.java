package io.github.NadhifRadityo.Objects.Console;

import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.PrefixLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.SuffixLogHandler;
import io.github.NadhifRadityo.Objects.List.PriorityList;

import java.util.Map;

public class Logger {
	protected final PriorityList<LogHandler> handlers = new PriorityList<>();
	protected final PriorityList<LogListener> listeners = new PriorityList<>();

	public Logger(String prefix, String suffix) {
		if(prefix != null) addHandler(new PrefixLogHandler(prefix), PrefixLogHandler.DEFAULT_PRIORITY);
		if(suffix != null) addHandler(new SuffixLogHandler(suffix), SuffixLogHandler.DEFAULT_PRIORITY);
	} public Logger(String prefix) { this(prefix, null); } public Logger() { this(null); }

	public void addHandler(LogHandler handler, int priority) { handlers.add(handler, priority); }
	public void addHandler(LogHandler handler) { addHandler(handler, 0); }
	public void removeHandler(LogHandler handler) { handlers.remove(handler); }
	public Map<LogHandler, Integer> getHandlers() { return handlers.getMap(); }

	public void addListener(LogListener listener, int priority) { listeners.add(listener, priority); }
	public void addListener(LogListener listener) { addListener(listener, 0); }
	public void removeListener(LogListener listener) { listeners.remove(listener); }
	public Map<LogListener, Integer> getListeners() { return listeners.getMap(); }

	public void log(Object... args) { doLog(LogLevel.LOG, args); }
	public void info(Object... args) { doLog(LogLevel.INFO, args); }
	public void debug(Object... args) { doLog(LogLevel.DEBUG, args); }
	public void warn(Object... args) { doLog(LogLevel.WARN, args); }
	public void error(Object... args) { doLog(LogLevel.ERROR, args); }

	public void doLog(LogLevel level, Object... args) {
		if(listeners.size() == 0) return;
		LogRecord.LogSourceInfo logSourceInfo = LogRecord.LogSourceInfo.newInstance(Logger.class.getCanonicalName());
		LogRecord logRecord = new LogRecord(logSourceInfo, this, args, level);
		doLog(logRecord);
	}
	protected void doLog(LogRecord logRecord) { try {
		if(listeners.size() == 0) return;
		for(LogHandler handler : handlers)
			handler.manipulate(logRecord);
		if(logRecord.isMarkCanceled()) return;
		for(LogListener listener : listeners)
			listener.doLog(logRecord);
	} finally { logRecord.close(); } }
}
