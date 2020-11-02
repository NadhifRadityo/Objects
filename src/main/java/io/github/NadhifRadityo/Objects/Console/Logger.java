package io.github.NadhifRadityo.Objects.Console;

import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.PrefixLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.SuffixLogHandler;
import io.github.NadhifRadityo.Objects.List.PriorityList;

import java.util.Map;

public class Logger {
	protected static final String[] sourceInfoExcludeDefault = new String[] { Logger.class.getCanonicalName(), LogRecord.class.getCanonicalName(), LogRecord.LogSourceInfo.class.getCanonicalName() };
	protected final PriorityList<LogHandler> handlers = new PriorityList<>();
	protected final PriorityList<LogListener> listeners = new PriorityList<>();
	protected boolean enableSourceInfo;

	public Logger(String prefix, String suffix, boolean enableSourceInfo) {
		if(prefix != null) addHandler(new PrefixLogHandler(prefix), PrefixLogHandler.DEFAULT_PRIORITY);
		if(suffix != null) addHandler(new SuffixLogHandler(suffix), SuffixLogHandler.DEFAULT_PRIORITY);
		this.enableSourceInfo = enableSourceInfo;
	} public Logger(String prefix, boolean enableSourceInfo) { this(prefix, null, enableSourceInfo); }
	public Logger(boolean enableSourceInfo) { this(null, enableSourceInfo); }
	public Logger() { this(false); }

	public synchronized void addHandler(LogHandler handler, int priority) { handlers.add(handler, priority); }
	public synchronized void addHandler(LogHandler handler) { addHandler(handler, 0); }
	public synchronized void removeHandler(LogHandler handler) { handlers.remove(handler); }
	public synchronized Map.Entry<LogHandler, Integer>[] getHandlers() { return handlers.getMap(); }

	public synchronized void addListener(LogListener listener, int priority) { listeners.add(listener, priority); }
	public synchronized void addListener(LogListener listener) { addListener(listener, 0); }
	public synchronized void removeListener(LogListener listener) { listeners.remove(listener); }
	public synchronized Map.Entry<LogListener, Integer>[] getListeners() { return listeners.getMap(); }

	public void log(Object... args) { doLog(LogLevel.LOG, args); }
	public void info(Object... args) { doLog(LogLevel.INFO, args); }
	public void debug(Object... args) { doLog(LogLevel.DEBUG, args); }
	public void warn(Object... args) { doLog(LogLevel.WARN, args); }
	public void error(Object... args) { doLog(LogLevel.ERROR, args); }
	public void _log(String string, Object... args) { log(String.format(string, args)); }
	public void _info(String string, Object... args) { info(String.format(string, args)); }
	public void _debug(String string, Object... args) { debug(String.format(string, args)); }
	public void _warn(String string, Object... args) { warn(String.format(string, args)); }
	public void _error(String string, Object... args) { error(String.format(string, args)); }

	public void doLog(LogLevel level, Object... args) {
		if(listeners.size() == 0) return;
		LogRecord.LogSourceInfo logSourceInfo = enableSourceInfo ? LogRecord.LogSourceInfo.newInstance(sourceInfoExcludeDefault) : null;
		LogRecord logRecord = LogRecord.newInstance(this, logSourceInfo, args, level);
		doLog(logRecord);
	}
	public synchronized void doLog(LogRecord logRecord) { try {
		if(listeners.size() == 0) return;
		for(LogHandler handler : handlers) {
			handler.manipulate(logRecord);
			if(logRecord.isMarkCanceled()) return;
		}
		for(LogListener listener : listeners) {
			listener.doLog(logRecord);
			if(logRecord.isMarkCanceled()) return;
		}
	} finally { logRecord.setDead(); } }
}
