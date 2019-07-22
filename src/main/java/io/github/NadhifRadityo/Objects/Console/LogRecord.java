package io.github.NadhifRadityo.Objects.Console;

import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogRecord implements Serializable {
	protected final LogSourceInfo logSourceInfo;
	protected final Logger logger;
	protected final ArrayList<Object> args;
	protected LogLevel logLevel;
	protected boolean markCanceled = false;

	public LogRecord(LogSourceInfo logSourceInfo, Logger logger, Object[] args, LogLevel logLevel) {
		this.logSourceInfo = logSourceInfo;
		this.logger = logger;
		this.args = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		this.logLevel = logLevel;

		this.args.addAll(Arrays.asList(args));
	}
	public LogRecord(Thread sourceThread, Class sourceClass, Method sourceMethod, Logger logger, Object[] args, LogLevel level) {
		this(new LogSourceInfo(sourceThread, sourceClass, sourceMethod), logger, args, level);
	}

	public LogSourceInfo getLogSourceInfo() { return logSourceInfo; }
	public Logger getLogger() { return logger; }
	public List<Object> getArgs() { return args; }
	public LogLevel getLogLevel() { return logLevel; }
	public boolean isMarkCanceled() { return markCanceled; }

	public void setLogLevel(LogLevel logLevel) { this.logLevel = logLevel; }
	public void setMarkCanceled(boolean markCanceled) { this.markCanceled = markCanceled; }

	public String asString(String separator) {
		StringBuilder stringBuilder = Pool.tryBorrow(Pool.getPool(StringBuilder.class)); try {
			for(int i = 0; i < args.size(); i++) { if(i != 0) stringBuilder.append(separator);
				if(args.get(i) != null) stringBuilder.append(args.get(i));
			} return stringBuilder.toString();
		} finally { Pool.returnObject(StringBuilder.class, stringBuilder); }
	} public String asString() { return asString(" "); }

	public void close() { Pool.returnObject(ArrayList.class, args); }
	@Override protected LogRecord clone() { return new LogRecord(logSourceInfo.clone(), logger, args.toArray(), logLevel); }

	public static class LogSourceInfo {
		public final Thread sourceThread;
		public final Class sourceClass;
		public final Method[] sourceMethod;

		protected LogSourceInfo(Thread sourceThread, Class sourceClass, Method... sourceMethod) {
			this.sourceThread = sourceThread;
			this.sourceClass = sourceClass;
			this.sourceMethod = sourceMethod;
		}

		public Thread getSourceThread() { return sourceThread; }
		public Class getSourceClass() { return sourceClass; }
		public Method[] getSourceMethod() { return sourceMethod; }

		@Override protected LogSourceInfo clone() { return new LogSourceInfo(sourceThread, sourceClass, sourceMethod); }
		protected static LogSourceInfo newInstance(String... exclude) {
			ArrayList<String> excluded = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
			excluded.add(LogSourceInfo.class.getCanonicalName());
			excluded.addAll(Arrays.asList(exclude));
			Thread currentThread = Thread.currentThread();
			StackTraceElement[] traceElements = currentThread.getStackTrace();
			for(StackTraceElement traceElement : traceElements) {
				if(excluded.contains(traceElement.getClassName())) continue; try {
				Class sourceClass = Class.forName(traceElement.getClassName());
				Method[] sourceMethods = sourceClass.getDeclaredMethods();
				ArrayList<Method> possibleMethods = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
					for(Method sourceMethod : sourceMethods) if(sourceMethod.getName().equals(traceElement.getMethodName())) possibleMethods.add(sourceMethod);
					return new LogSourceInfo(currentThread, sourceClass, possibleMethods.toArray(new Method[0]));
				} finally { Pool.returnObject(ArrayList.class, possibleMethods); } } catch(Exception ignored) { }
			} return null; } finally { Pool.returnObject(ArrayList.class, excluded); }
		}
	}
}
