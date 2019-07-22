package io.github.NadhifRadityo.Objects.Console;

public interface LogHandler {
	default void manipulate(LogRecord record) {
		switch(record.getLogLevel()) {
			case LOG: manipulateLog(record); break;
			case INFO: manipulateInfo(record); break;
			case DEBUG: manipulateDebug(record); break;
			case WARN: manipulateWarn(record); break;
			case ERROR: manipulateError(record); break;
		}
	}

	void manipulateLog(LogRecord record);
	default void manipulateInfo(LogRecord record) { manipulateLog(record); }
	default void manipulateDebug(LogRecord record) { manipulateLog(record); }
	default void manipulateWarn(LogRecord record) { manipulateLog(record); }
	default void manipulateError(LogRecord record) { manipulateLog(record); }
}
