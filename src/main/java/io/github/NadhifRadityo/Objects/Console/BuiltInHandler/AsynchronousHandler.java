package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Console.LogHandler;
import io.github.NadhifRadityo.Objects.Console.LogRecord;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Thread.Handler;
import io.github.NadhifRadityo.Objects.Thread.HandlerThread;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AsynchronousHandler implements LogHandler {
	public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;
	public static final Object ENABLED = new Object();

	protected static final HandlerThread handlerThread;
	protected static final Handler handler;
	protected static final ThreadLocal<WeakReference<ArrayList<AsynchronousLogRecord>>> tempRecord = new ThreadLocal<>();

	static {
		handlerThread = new HandlerThread("Print Thread");
		handlerThread.setDaemon(true);
		handlerThread.start();
		handler = handlerThread.getThreadHandler();
	}

	protected static AsynchronousLogRecord getTempRecord() {
		WeakReference<ArrayList<AsynchronousLogRecord>> reference = tempRecord.get();
		ArrayList<AsynchronousLogRecord> list = reference != null && reference.get() != null ? reference.get() : null;
		if(list == null) { list = new ArrayList<>(); tempRecord.set(new WeakReference<>(list)); }
		AsynchronousLogRecord record = null; for(AsynchronousLogRecord r : list) if(r.getLogger() == null) { record = r; break; }
		if(record == null) { record = new AsynchronousLogRecord(); list.add(record); } return record;
	}

	@Override public void manipulateLog(LogRecord record) {
		if(handlerThread.getLooper().isCurrentThread()) return;
		List<Object> args = record.getArgs();
		Object STATE_ENABLED = AttributesHandler.checkOnce(ENABLED, AttributesHandler.ON, args);
		if(STATE_ENABLED != AttributesHandler.ON) return;

		record.setMarkCanceled(true);
		AsynchronousLogRecord asynchronousLogRecord = getTempRecord();
		asynchronousLogRecord.set(record);
		handler.post(asynchronousLogRecord);
	}

	protected static class AsynchronousLogRecord extends LogRecord implements Runnable {
		public AsynchronousLogRecord() {
			super(null, null, null, null);
			setDead(); reset();
		}

		public void set(LogRecord logRecord) {
			ArrayList<Object> args = Pool.tryBorrow(Pool.getPool(ArrayList.class)); args.addAll(logRecord.getArgs());
			LogRecordPool.assign(this, logRecord.getLogger(), logRecord.getLogSourceInfo(), args);
			logLevel = logRecord.getLogLevel();
		}
		public void reset() {
			LogRecordPool.assign(this, null, null, null);
			logLevel = null;
		}

		@Override public void run() {
			logger.doLog(this);
			reset();
		}
	}
}
