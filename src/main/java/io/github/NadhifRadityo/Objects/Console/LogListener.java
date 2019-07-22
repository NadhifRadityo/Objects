package io.github.NadhifRadityo.Objects.Console;

public interface LogListener {
	default void doLog(LogRecord record) {
		switch(record.getLogLevel()) {
			case LOG: onLog(record); break;
			case INFO: onInfo(record); break;
			case DEBUG: onDebug(record); break;
			case WARN: onWarn(record); break;
			case ERROR: onError(record); break;
		}
	}

	void onLog(LogRecord record);
	default void onInfo(LogRecord record) { onLog(record); }
	default void onDebug(LogRecord record) { onLog(record); }
	default void onWarn(LogRecord record) { onLog(record); }
	default void onError(LogRecord record) { onLog(record); }
}
