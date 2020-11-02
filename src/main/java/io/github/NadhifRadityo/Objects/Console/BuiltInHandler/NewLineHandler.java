package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Console.LogHandler;
import io.github.NadhifRadityo.Objects.Console.LogLevel;
import io.github.NadhifRadityo.Objects.Console.LogListener;
import io.github.NadhifRadityo.Objects.Console.LogRecord;
import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewLineHandler implements LogHandler {
	public static final int DEFAULT_PRIORITY = 105;
	public static final Object ENABLED = new Object();
	public static final Object BROADCAST_ENABLED = new Object();
	public static final Object BROADCAST_START = new Object();
	public static final Object BROADCAST_END = new Object();
	protected static final Pattern newLinePattern = Pattern.compile(".+", Pattern.MULTILINE);

	protected Map<Logger, NewLineListener> listeners;
	public NewLineHandler() { this.listeners = new HashMap<>(); }

	@SuppressWarnings("unchecked")
	@Override public void manipulateLog(LogRecord record) {
		List<Object> args = record.getArgs();
		Object STATE_ENABLED = AttributesHandler.ON;
		Object STATE_BROADCAST_ENABLED = AttributesHandler.ON;

		NewLineListener listener = listeners.get(record.getLogger());
		if(listener == null) {
			listener = new NewLineListener(this); listeners.put(record.getLogger(), listener);
			record.getLogger().addListener(listener, NewLineListener.DEFAULT_PRIORITY);
		} if(listener.isDoingLoop()) return;

		ArrayList<Object> broadcast = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		int lastStart = 0; boolean hasStart = false; boolean lastEnd = false;
		for(int i = 0; i < args.size(); i++) { Object arg = args.get(i);
			if((STATE_ENABLED = AttributesHandler.checkNotConsume(arg, ENABLED, STATE_ENABLED)) != AttributesHandler.ON) continue;
			if((STATE_BROADCAST_ENABLED = AttributesHandler.check(arg, BROADCAST_ENABLED, STATE_BROADCAST_ENABLED, args, i)) != AttributesHandler.ON) continue;
			if(arg == BROADCAST_START) { lastEnd = false; hasStart = true; lastStart = i; }
			if(arg == BROADCAST_END) { lastEnd = true; for(int j = i; j >= lastStart; j--) { if(j != i && j != lastStart) broadcast.add(0, args.get(j)); args.set(j, AttributesHandler.DUMMY); } }
		}
		if(hasStart && !lastEnd)
			for(int i = lastStart; i < args.size(); i++) {
				if(i != lastStart) broadcast.add(args.get(i));
				args.set(i, AttributesHandler.DUMMY);
			}
		AttributesHandler.consume(args);

		ArrayList<ArrayList<Object>> scheduled = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		ArrayList<Object> currentLine = null;
		try { ListIterator<Object> iterator = args.listIterator(); while(iterator.hasNext()) { Object arg = iterator.next();
			if((STATE_ENABLED = AttributesHandler.check(arg, ENABLED, STATE_ENABLED, iterator)) != AttributesHandler.ON) continue;
			String stringArg = arg.toString(); if(!stringArg.contains("\n")) { if(currentLine == null) continue; currentLine.add(arg); iterator.remove(); continue; }
			Matcher matcher = newLinePattern.matcher(stringArg); int newLines = 0;
			if(matcher.find()) { if(currentLine == null) iterator.set(matcher.group(0)); else { iterator.remove(); currentLine.add(matcher.group(0)); } }
			while(matcher.find()) { newLines++; currentLine = Pool.tryBorrow(Pool.getPool(ArrayList.class)); currentLine.addAll(broadcast); scheduled.add(currentLine); currentLine.add(matcher.group(0)); }
			for(int i = 0; i < stringArg.length(); i++) if(stringArg.charAt(i) == '\n') newLines--;
			for(newLines = -newLines; newLines > 0; newLines--) { currentLine = Pool.tryBorrow(Pool.getPool(ArrayList.class)); currentLine.addAll(broadcast); scheduled.add(currentLine); }
		} if(scheduled.size() > 0) { listener.setLastLevel(record.getLogLevel()); listener.setLastArgs(scheduled.toArray(new ArrayList[0])); }
		} finally { AttributesHandler.consume(args); Pool.returnObject(ArrayList.class, broadcast); Pool.returnObject(ArrayList.class, scheduled); }
	}

	protected static class NewLineListener implements LogListener {
		public static final int DEFAULT_PRIORITY = -105;

		protected final NewLineHandler handler;
		protected LogLevel lastLevel;
		protected ArrayList<Object>[] lastArgs;
		protected boolean doingLoop;
		protected NewLineListener(NewLineHandler handler) { this.handler = handler; }

		public NewLineHandler getHandler() { return handler; }
		public LogLevel getLastLevel() { return lastLevel; }
		public ArrayList<Object>[] getLastArgs() { return lastArgs; }
		public boolean isDoingLoop() { return doingLoop; }

		public void setLastLevel(LogLevel lastLevel) { this.lastLevel = lastLevel; }
		public void setLastArgs(ArrayList<Object>[] lastArgs) { this.lastArgs = lastArgs; }

		@Override public void onLog(LogRecord record) {
			if(doingLoop || lastLevel == null || lastArgs == null) return;
			doingLoop = true;
			for(ArrayList<Object> arg : lastArgs) {
				record.getLogger().doLog(lastLevel, arg.toArray());
				Pool.returnObject(ArrayList.class, arg);
			}
			lastLevel = null; lastArgs = null;
			doingLoop = false;
		}
	}
}
