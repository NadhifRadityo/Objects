package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Console.LogHandler;
import io.github.NadhifRadityo.Objects.Console.LogRecord;

import java.util.List;

public class FormatHandler implements LogHandler {
	public static final int DEFAULT_PRIORITY = 100;
	public static final Object ENABLED = new Object();
	public static final Object SUBSTRING_ENABLED = new Object();
	public static final Object BACK_R_PARSING_ENABLED = new Object();
	public static final Object NEW_LINE_ENABLED = new Object();
	public static final Object SUBSTRING_START = new Object();
	public static final Object SUBSTRING_END = new Object();

	@Override public void manipulateLog(LogRecord record) {
		List<Object> args = record.getArgs();
		Object STATE_ENABLED = AttributesHandler.ON;
		Object STATE_SUBSTRING_ENABLED = AttributesHandler.ON;
		Object STATE_BACK_R_PARSING_ENABLED = AttributesHandler.ON;
		Object STATE_NEW_LINE_ENABLED = AttributesHandler.ON;

		// Substring
		// S -> Start, E -> End
		// [1, S, 2, 3, E, 4] -> [2, 3]
		// [1, E, 2, 3, S, 4] -> [1, 4]
		// [1, S, 2, 3, E, 4, S, 5, 6, E, 7] -> [2, 3, 5, 6]
		// [1, S, 2, 3, E, 4, S, 5, 6, 7] -> [2, 3, 5, 6, 7]
		// [1, E, 2, 3, S, 4, E, 5, 6, S, 7] -> [1, 4, 7]
		// [1, E, 2, 3, S, 4, E, 5, 6, 7] -> [1, 4]
		int lastEnd = 0; boolean hasEnd = false; boolean lastStart = false;
		for(int i = 0; i < args.size(); i++) { Object arg = args.get(i);
			if((STATE_ENABLED = AttributesHandler.checkNotConsume(arg, ENABLED, STATE_ENABLED)) != AttributesHandler.ON) continue;
			if((STATE_SUBSTRING_ENABLED = AttributesHandler.check(arg, SUBSTRING_ENABLED, STATE_SUBSTRING_ENABLED, args, i)) != AttributesHandler.ON) continue;
			if(arg == SUBSTRING_START) { lastStart = true; for(int j = i; j >= lastEnd; j--) args.set(j, AttributesHandler.DUMMY); }
			if(arg == SUBSTRING_END) { lastStart = false; hasEnd = true; lastEnd = i; }
		}
		if(hasEnd && !lastStart)
			for(int i = lastEnd; i < args.size(); i++)
				args.set(i, AttributesHandler.DUMMY);
		AttributesHandler.consume(args);

		STATE_ENABLED = AttributesHandler.ON;
		// Back R Parsing
		// ["AAA", "BBB", "CCC"] -> ["AAA", "BBB", "CCC"]
		// ["AAA", "B\rBB", "CCC"] -> ["\r", "AAA", "BBB", "CCC"]
		// ["AAA", "B\rBB", "CC\rC"] -> ["\r", "AAA", "BBB", "CCC"]
		boolean hasBackR = false;
		for(int i = 0; i < args.size(); i++) { Object arg = args.get(i);
			if((STATE_ENABLED = AttributesHandler.checkNotConsume(arg, ENABLED, STATE_ENABLED)) != AttributesHandler.ON) continue;
			if((STATE_BACK_R_PARSING_ENABLED = AttributesHandler.check(arg, BACK_R_PARSING_ENABLED, STATE_BACK_R_PARSING_ENABLED, args, i)) != AttributesHandler.ON) continue;
			String stringArg = arg.toString(); if(!stringArg.contains("\r")) continue;
			args.set(i, stringArg.replaceAll("\r", "")); hasBackR = true;
		}
		if(hasBackR) args.add(0, "\r");
		AttributesHandler.consume(args);

		STATE_ENABLED = AttributesHandler.ON;
		// Add New Line
		for(int i = 0; i < args.size(); i++) { Object arg = args.get(i);
			if((STATE_ENABLED = AttributesHandler.check(arg, ENABLED, STATE_ENABLED, args, i)) != AttributesHandler.ON) continue;
			STATE_NEW_LINE_ENABLED = AttributesHandler.check(arg, NEW_LINE_ENABLED, STATE_NEW_LINE_ENABLED, args, i);
		}
		if(STATE_NEW_LINE_ENABLED == AttributesHandler.ON)
			args.add(System.lineSeparator());
		AttributesHandler.consume(args);
	}
}
