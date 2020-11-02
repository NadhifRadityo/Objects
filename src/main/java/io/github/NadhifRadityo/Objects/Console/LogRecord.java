package io.github.NadhifRadityo.Objects.Console;

import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolConfig;
import io.github.NadhifRadityo.Objects.Pool.PoolFactory;
import io.github.NadhifRadityo.Objects.Pool.PooledObject;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ListUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.Utilizations.StringUtils;
import io.github.NadhifRadityo.Objects.Utilizations.UnsafeUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class LogRecord implements DeadableObject {
	protected final Logger logger;
	protected final LogSourceInfo logSourceInfo;
	protected final ArrayList<Object> args;

	protected LogLevel logLevel;
	protected boolean markCanceled;
	protected boolean isDead;

	protected LogRecord(Logger logger, LogSourceInfo logSourceInfo, Object[] args, LogLevel logLevel) {
		this.logger = logger;
		this.logSourceInfo = logSourceInfo;
		this.args = Pool.tryBorrow(Pool.getPool(ArrayList.class));

		this.logLevel = logLevel;
		if(args != null)
			this.args.addAll(Arrays.asList(args));
	}

	public Logger getLogger() { return logger; }
	public LogSourceInfo getLogSourceInfo() { return logSourceInfo; }
	public ArrayList<Object> getArgs() { return args; }
	public LogLevel getLogLevel() { return logLevel; }
	public boolean isMarkCanceled() { return markCanceled; }
	@Override public boolean isDead() { return isDead; }

	public void setLogLevel(LogLevel logLevel) { this.logLevel = logLevel; }
	public void setMarkCanceled(boolean markCanceled) { this.markCanceled = markCanceled; }
	@Override public void setDead() { isDead = true;
		if(logSourceInfo != null && LogSourceInfo.LogSourceInfoPool.pool.isUsing(logSourceInfo))
			Pool.returnObject(LogSourceInfo.LogSourceInfoPool.pool, logSourceInfo);
		Pool.returnObject(ArrayList.class, args);
		if(LogRecordPool.pool.isUsing(this))
			Pool.returnObject(LogRecordPool.pool, this);
	}

	public String asString(String separator, int start, int end) { return StringUtils.join(ListUtils.getElementData(args), separator, start, end); }
	public String asString(String separator, int start) { return asString(separator, start, args.size()); }
	public String asString(String separator) { return asString(separator, 0); }
	public String asString(int start, int end) { return asString("", start, end); }
	public String asString(int start) { return asString(start, args.size()); }
	public String asString() { return asString(""); }

	public static LogRecord newInstance(Logger logger, LogSourceInfo logSourceInfo, Object[] _args, LogLevel logLevel) {
		LogRecord result = Pool.tryBorrow(LogRecordPool.pool);
		ArrayList<Object> args = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		if(_args != null) ListUtils.fastSet(args, _args, 0, 0, _args.length);
		LogRecordPool.assign(result, logger, logSourceInfo, args);
		result.setLogLevel(logLevel);
		return result;
	}

	@SuppressWarnings("jol")
	protected static class LogRecordPool extends Pool<LogRecord> {
		public static final LogRecordPool pool = new LogRecordPool(new LogRecordPoolFactory(), Pool.getDefaultPoolConfig());
		private static final Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);

		protected LogRecordPool(PoolFactory<LogRecord> factory) { super(factory); }
		protected LogRecordPool(PoolFactory<LogRecord> factory, PoolConfig<LogRecord> config) { super(factory, config); }

		protected static final long AFIELD_LogRecord_logger;
		protected static final long AFIELD_LogRecord_logSourceInfo;
		protected static final long AFIELD_LogRecord_args;
		static { try {
			AFIELD_LogRecord_logger = unsafe.objectFieldOffset(LogRecord.class.getDeclaredField("logger"));
			AFIELD_LogRecord_logSourceInfo = unsafe.objectFieldOffset(LogRecord.class.getDeclaredField("logSourceInfo"));
			AFIELD_LogRecord_args = unsafe.objectFieldOffset(LogRecord.class.getDeclaredField("args"));
		} catch(Throwable e) { throw new Error(e); } }

		public static LogRecord newInstance() {
			try { return (LogRecord) unsafe.allocateInstance(LogRecord.class);
			} catch (InstantiationException e) { throw new UnsupportedOperationException(e); }
		}
		public static LogRecord assign(LogRecord logRecord, Logger logger, LogSourceInfo logSourceInfo, ArrayList<Object> args) {
			unsafe.putObject(logRecord, AFIELD_LogRecord_logger, logger);
			unsafe.putObject(logRecord, AFIELD_LogRecord_logSourceInfo, logSourceInfo);
			unsafe.putObject(logRecord, AFIELD_LogRecord_args, args);
			return logRecord;
		}
		public static boolean validate(LogRecord logRecord) {
			return logRecord.isDead() &&
					unsafe.getObject(logRecord, AFIELD_LogRecord_logger) == null &&
					unsafe.getObject(logRecord, AFIELD_LogRecord_logSourceInfo) == null &&
					unsafe.getObject(logRecord, AFIELD_LogRecord_args) == null;
		}

		protected static class LogRecordPoolFactory extends PoolFactory<LogRecord> {
			@Override public LogRecord create() { return newInstance(); }
			@Override public PooledObject<LogRecord> wrap(LogRecord obj) { return new PooledObject<>(obj); }
			@Override public boolean validateObject(PooledObject<LogRecord> p) { return validate(p.getObject()); }
			@Override public void passivateObject(PooledObject<LogRecord> p) { assign(p.getObject(), null, null, null); }
		}
	}

	public static class LogSourceInfo {
		public final Thread sourceThread;
		public final StackTraceElement[] sourceTraces;
		public final Class sourceClass;
		public final Method[] sourceMethod;

		protected LogSourceInfo(Thread sourceThread, StackTraceElement[] sourceTraces, Class sourceClass, Method[] sourceMethod) {
			this.sourceThread = sourceThread;
			this.sourceTraces = sourceTraces;
			this.sourceClass = sourceClass;
			this.sourceMethod = sourceMethod;
		}

		public Thread getSourceThread() { return sourceThread; }
		public StackTraceElement[] getSourceTraces() { return sourceTraces; }
		public Class getSourceClass() { calculateClass(); return sourceClass; }
		public Method[] getSourceMethod() { calculateMethod(); return sourceMethod; }

		protected void calculateMethod() {
			if(sourceMethod != null) return; calculateClass();
			ArrayList<Method> possibleMethods = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
				StackTraceElement traceElement = sourceTraces[0];
				Method[] sourceMethods = sourceClass.getDeclaredMethods();
				for(Method sourceMethod : sourceMethods) if(sourceMethod.getName().equals(traceElement.getMethodName())) possibleMethods.add(sourceMethod);
				LogSourceInfoPool.unsafe.putObject(this, LogSourceInfoPool.AFIELD_LogSourceInfo_sourceMethod, possibleMethods.toArray(new Method[0]));
			} catch(Exception e) { throw new UnsupportedOperationException(e); } finally { Pool.returnObject(ArrayList.class, possibleMethods); }
		}
		protected void calculateClass() {
			if(sourceClass != null) return; try {
				StackTraceElement traceElement = sourceTraces[0];
				Class sourceClass = Class.forName(traceElement.getClassName());
				LogSourceInfoPool.unsafe.putObject(this, LogSourceInfoPool.AFIELD_LogSourceInfo_sourceClass, sourceClass);
			} catch(Exception e) { throw new UnsupportedOperationException(e); }
		}

		public static LogSourceInfo newInstance(String... exclude) {
			LogSourceInfo result = Pool.tryBorrow(LogSourceInfoPool.pool);
			Thread currentThread = Thread.currentThread();
			StackTraceElement[] traceElements = currentThread.getStackTrace();
			{
				ArrayList<String> excluded = Pool.tryBorrow(Pool.getPool(ArrayList.class));
				if(exclude != null) ListUtils.fastSet(excluded, exclude, 0, 0, exclude.length);
				int index = 0;
				try { for(int i = 0; i < traceElements.length; i++) {
					if(excluded.contains(traceElements[i].getClassName())) continue; index = i; break;
					} ArrayUtils.cut(traceElements, index, traceElements, 0, traceElements.length - index);
				} finally { Pool.returnObject(ArrayList.class, excluded); }
			}
			LogSourceInfoPool.assign(result, currentThread, traceElements, null, null);
			return result;
		}

		@SuppressWarnings("jol")
		protected static class LogSourceInfoPool extends Pool<LogSourceInfo> {
			public static final LogSourceInfoPool pool = new LogSourceInfoPool(new LogSourceInfoPoolFactory(), Pool.getDefaultPoolConfig());
			private static final Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);

			protected LogSourceInfoPool(PoolFactory<LogSourceInfo> factory) { super(factory); }
			protected LogSourceInfoPool(PoolFactory<LogSourceInfo> factory, PoolConfig<LogSourceInfo> config) { super(factory, config); }

			protected static final long AFIELD_LogSourceInfo_sourceThread;
			protected static final long AFIELD_LogSourceInfo_sourceTraces;
			protected static final long AFIELD_LogSourceInfo_sourceClass;
			protected static final long AFIELD_LogSourceInfo_sourceMethod;
			static { try {
				AFIELD_LogSourceInfo_sourceThread = unsafe.objectFieldOffset(LogSourceInfo.class.getDeclaredField("sourceThread"));
				AFIELD_LogSourceInfo_sourceTraces = unsafe.objectFieldOffset(LogSourceInfo.class.getDeclaredField("sourceTraces"));
				AFIELD_LogSourceInfo_sourceClass = unsafe.objectFieldOffset(LogSourceInfo.class.getDeclaredField("sourceClass"));
				AFIELD_LogSourceInfo_sourceMethod = unsafe.objectFieldOffset(LogSourceInfo.class.getDeclaredField("sourceMethod"));
			} catch(Throwable e) { throw new Error(e); } }

			public static LogSourceInfo newInstance() {
				try { return (LogSourceInfo) unsafe.allocateInstance(LogSourceInfo.class);
				} catch (InstantiationException e) { throw new UnsupportedOperationException(e); }
			}
			public static LogSourceInfo assign(LogSourceInfo logRecord, Thread sourceThread, StackTraceElement[] sourceTraces, Class sourceClass, Method[] sourceMethod) {
				unsafe.putObject(logRecord, AFIELD_LogSourceInfo_sourceThread, sourceThread);
				unsafe.putObject(logRecord, AFIELD_LogSourceInfo_sourceTraces, sourceTraces);
				unsafe.putObject(logRecord, AFIELD_LogSourceInfo_sourceClass, sourceClass);
				unsafe.putObject(logRecord, AFIELD_LogSourceInfo_sourceMethod, sourceMethod);
				return logRecord;
			}
			public static boolean validate(LogSourceInfo logRecord) {
				return unsafe.getObject(logRecord, AFIELD_LogSourceInfo_sourceThread) == null &&
						unsafe.getObject(logRecord, AFIELD_LogSourceInfo_sourceTraces) == null &&
						unsafe.getObject(logRecord, AFIELD_LogSourceInfo_sourceClass) == null &&
						unsafe.getObject(logRecord, AFIELD_LogSourceInfo_sourceMethod) == null;
			}

			protected static class LogSourceInfoPoolFactory extends PoolFactory<LogSourceInfo> {
				@Override public LogSourceInfo create() { return newInstance(); }
				@Override public PooledObject<LogSourceInfo> wrap(LogSourceInfo obj) { return new PooledObject<>(obj); }
				@Override public boolean validateObject(PooledObject<LogSourceInfo> p) { return validate(p.getObject()); }
				@Override public void passivateObject(PooledObject<LogSourceInfo> p) { assign(p.getObject(), null, null, null, null); }
			}
		}
	}
}
