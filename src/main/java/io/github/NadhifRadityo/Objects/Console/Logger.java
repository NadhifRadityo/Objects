package io.github.NadhifRadityo.Objects.Console;

import java.util.List;

import io.github.NadhifRadityo.Objects.List.PriorityList;

public class Logger {
	protected final PriorityList<LogListener> list = new PriorityList<>();
	protected String prefix = "";
	protected String infoPrefix = "";
	protected String debugPrefix = "";
	protected String warnPrefix = "";
	protected String errorPrefix = "";
	
	public Logger(String prefix) {
		this.prefix = prefix; 
	} public Logger() { this(""); } 
	
	public void addLogListener(LogListener listener, int priority) {
		list.add(listener, priority);
	} public void addLogListener(LogListener listener) { addLogListener(listener, 0); }
	public void removeLogListener(LogListener listener) {
		list.remove(listener);
	}
	
	public void setPrefix(String prefix) { this.prefix = prefix; }
	public void setInfoPrefix(String infoPrefix) { this.infoPrefix = infoPrefix; }
	public void setDebugPrefix(String debugPrefix) { this.debugPrefix = debugPrefix; }
	public void setWarnPrefix(String warnPrefix) { this.warnPrefix = warnPrefix; }
	public void setErrorPrefix(String errorPrefix) { this.errorPrefix = errorPrefix; }
	
	public String getPrefix() { return prefix; }
	public String getInfoPrefix() { return infoPrefix; }
	public String getDebugPrefix() { return debugPrefix; }
	public String getWarnPrefix() { return warnPrefix; }
	public String getErrorPrefix() { return errorPrefix; }
	
	public void log(String log) {
		List<LogListener> listener = list.get();
		for(int i = 0; i < listener.size(); i++)
			listener.get(i).log(prefix + log);
	}
	public void info(String log) {
		List<LogListener> listener = list.get();
		for(int i = 0; i < listener.size(); i++)
			listener.get(i).info(prefix + log);
	}
	public void debug(String log) {
		List<LogListener> listener = list.get();
		for(int i = 0; i < listener.size(); i++)
			listener.get(i).debug(prefix + log);
	}
	public void warn(String log) {
		List<LogListener> listener = list.get();
		for(int i = 0; i < listener.size(); i++)
			listener.get(i).warn(prefix + log);
	}
	public void error(String log) {
		List<LogListener> listener = list.get();
		for(int i = 0; i < listener.size(); i++)
			listener.get(i).error(prefix + log);
	}
}
