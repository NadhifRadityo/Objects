package io.github.NadhifRadityo.Objects.Console.BuiltInListener;

import io.github.NadhifRadityo.Objects.Console.LogListener;
import io.github.NadhifRadityo.Objects.Console.LogRecord;
import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.List.PriorityList;

import java.util.Map;

public class ParentLoggerListener implements LogListener {
	protected final PriorityList<Logger> loggers;

	public ParentLoggerListener(Logger... loggers) {
		this.loggers = new PriorityList<>();
		for(Logger logger : loggers) this.loggers.add(logger);
	}

	public void addLogger(Logger logger, int priority) { loggers.add(logger, priority); }
	public void addLogger(Logger logger) { addLogger(logger, 0); }
	public void removeLogger(Logger logger) { loggers.remove(logger); }
	public Map.Entry<Logger, Integer>[] getLoggers() { return loggers.getMap(); }

	@Override public void onLog(LogRecord record) {
		for(Logger logger : loggers.get())
			logger.doLog(record.getLogLevel(), record.getArgs().toArray());
	}
}
