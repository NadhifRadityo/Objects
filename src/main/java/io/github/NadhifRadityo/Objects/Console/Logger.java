package io.github.NadhifRadityo.Objects.Console;

import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback.StringReferencedCallback;

public class Logger {
	protected static final StringReferencedCallback defaultPrefix = (obj) -> "";
	
	protected final PriorityList<LogListener> listeners = new PriorityList<>();
	protected StringReferencedCallback prefix;
	protected String logPrefix = "";
	protected String infoPrefix = "";
	protected String debugPrefix = "";
	protected String warnPrefix = "";
	protected String errorPrefix = "";
	
	public Logger(StringReferencedCallback prefix) { this.prefix = prefix != null ? prefix : defaultPrefix; }
	public Logger(String prefix) { this((obj) -> prefix); }
	public Logger() { this((StringReferencedCallback) null); }
	
	public void addListener(LogListener listener, int priority) { listeners.add(listener, priority); }
	public void addListener(LogListener listener) { addListener(listener, 0); }
	public void removeListener(LogListener listener) { listeners.remove(listener); }
	
	public StringReferencedCallback getPrefix() { return prefix != defaultPrefix ? prefix : null; }
	public String getLogPrefix() { return logPrefix; }
	public String getInfoPrefix() { return infoPrefix; }
	public String getDebugPrefix() { return debugPrefix; }
	public String getWarnPrefix() { return warnPrefix; }
	public String getErrorPrefix() { return errorPrefix; }
	
	public void setPrefix(StringReferencedCallback prefix) { this.prefix = prefix != null ? prefix : defaultPrefix; }
	public void setLogPrefix(String logPrefix) { this.logPrefix = logPrefix; }
	public void setInfoPrefix(String infoPrefix) { this.infoPrefix = infoPrefix; }
	public void setDebugPrefix(String debugPrefix) { this.debugPrefix = debugPrefix; }
	public void setWarnPrefix(String warnPrefix) { this.warnPrefix = warnPrefix; }
	public void setErrorPrefix(String errorPrefix) { this.errorPrefix = errorPrefix; }
	
	public void log(String log) { for(LogListener listener : listeners.get()) listener.log(prefix.get(this) + logPrefix + log); }
	public void info(String log) { for(LogListener listener : listeners.get()) listener.info(prefix.get(this) + infoPrefix + log); }
	public void debug(String log) { for(LogListener listener : listeners.get()) listener.debug(prefix.get(this) + debugPrefix + log); }
	public void warn(String log) { for(LogListener listener : listeners.get()) listener.warn(prefix.get(this) + warnPrefix + log); }
	public void error(String log) { for(LogListener listener : listeners.get()) listener.error(prefix.get(this) + errorPrefix + log); }
}
