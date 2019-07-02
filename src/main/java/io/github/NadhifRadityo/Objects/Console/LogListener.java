package io.github.NadhifRadityo.Objects.Console;

public interface LogListener {
	public void log(String log);
	default void info(String log) { log(log); }
	default void debug(String log) { log(log); }
	default void warn(String log) { log(log); }
	default void error(String log) { log(log); }
}
