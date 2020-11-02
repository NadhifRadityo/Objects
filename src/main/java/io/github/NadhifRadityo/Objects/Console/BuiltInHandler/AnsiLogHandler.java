package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Console.LogHandler;
import io.github.NadhifRadityo.Objects.Console.LogRecord;
import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ByteUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ConsoleUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnsiLogHandler implements LogHandler {
	public static final int DEFAULT_PRIORITY = 101;
	public static final Object ENABLED = new Object();

	public static final int defaultMaxLength = 10000;
	public static final String[] defaultAliasChars = new String[] {
			/*
			 * Color: Foreground & Background
			 * default  , black, red, green, yellow, blue, purple, cyan, white
			 * *lighter*, black, red, green, yellow, blue, purple, cyan, white
			 * 8bit(x) 24bit(x, y, z)
			 *
			 * Attributes
			 * reset, italic, italic off, conceal, conceal off, blink slow, blink fast, blink off, underline,
			 * underline double, underline off, negative, negative off, bold, faint, bold off, strikethrough, strikethrough off
			 */

			// Color Graphic Rendition
			"§fr"  ,   "§f0", "§fc", "§fa", "§fe", "§f9", "§f5", "§fb", "§ff",
			/*"§fh",*/ "§fl", "§fv", "§fq", "§fx", "§fy", "§f4", "§fp", "§f1",
			"§fk[args:1]", "§fm[args:3]",
			"§br"  ,   "§b0", "§bc", "§ba", "§be", "§b9", "§b5", "§bb", "§bf",
			/*"§bh",*/ "§bl", "§bv", "§bq", "§bx", "§by", "§b4", "§bp", "§b1",
			"§bk[args:1]", "§bm[args:3]",

			// Attributes
			"§tr", "§ti", "§to", "§tc", "§tk", "§t1", "§t2", "§t0", "§tu",
			"§t8", "§tl", "§t7", "§t3", "§tp", "§tv", "§tq", "§th", "§tg"
	};

	public static boolean isAnsiSupported(Logger logger) {
		if(!ConsoleUtils.ANSI_SUPPORTED) return false;
		for(Map.Entry<LogHandler, Integer> entry : logger.getHandlers())
			if(entry.getKey() instanceof AnsiLogHandler) return true;
		return false;
	}

	protected final Map<Object, AnsiCommand> parsedCommands;
	protected String[] aliasChars;
	protected int maxLength;

	public AnsiLogHandler(String[] aliasChars, int maxLength) {
		this.parsedCommands = new HashMap<>();
		this.aliasChars = aliasChars;
		this.maxLength = maxLength;
	}
	public AnsiLogHandler(String[] aliasChars) { this(aliasChars, defaultMaxLength); }
	public AnsiLogHandler(int maxLength) { this(defaultAliasChars, maxLength); }
	public AnsiLogHandler() { this(defaultMaxLength); }

	@Override public void manipulateLog(LogRecord record) {
		if(!ConsoleUtils.ANSI_SUPPORTED) return;
		List<Object> args = record.getArgs();
		Object STATE_ENABLED = AttributesHandler.ON;

		AnsiCommand command;
		command = parsedCommands.get(AnsiColor.DEFAULT); if(command == null) { command = AnsiColor.DEFAULT.asForeground().asCommand(); parsedCommands.put(AnsiColor.DEFAULT.name() + "F", command); }
		args.add(command.toString(this));
		command = parsedCommands.get(AnsiColor.DEFAULT); if(command == null) { command = AnsiColor.DEFAULT.asBackground().asCommand(); parsedCommands.put(AnsiColor.DEFAULT.name() + "B", command); }
		args.add(command.toString(this));
		command = parsedCommands.get(AnsiAttribute.RESET); if(command == null) { command = AnsiAttribute.RESET.asSGRParam().asCommand(); parsedCommands.put(AnsiAttribute.RESET, command); }
		args.add(command.toString(this));
		for(int i = 0; i < args.size(); i++) { Object arg = args.get(i);
			if((STATE_ENABLED = AttributesHandler.check(arg, ENABLED, STATE_ENABLED, args, i)) != AttributesHandler.ON) continue;
			if(arg instanceof AnsiCommand.CSICommand) arg = ((AnsiCommand.CSICommand) arg).asCommand();
			if(arg instanceof AnsiColor) arg = ((AnsiColor) arg).asForeground();
			if(arg instanceof AnsiAttribute) arg = ((AnsiAttribute) arg).asSGRParam();
			if(arg instanceof CSISGRParameter) arg = ((CSISGRParameter) arg).asCommand();
			if(arg instanceof AnsiCommand) { args.set(i, ((AnsiCommand) arg).toString(this)); continue; }
			if(!(arg instanceof String)) continue;
			if(((String) arg).length() >= maxLength) continue;
			String stringArg = (String) arg; int j = 0;

			/*
			 * Foreground
			 */
			// j >= 0 && j <= 8
			for(AnsiColor ansiColor : AnsiColor.values()) {
				if(j >= aliasChars.length) break; if(!stringArg.contains(aliasChars[j])) { j++; continue; }
				command = parsedCommands.get(ansiColor.name() + "F");
				if(command == null) { command = ansiColor.asForeground().asCommand(); parsedCommands.put(ansiColor.name() + "F", command); }
				stringArg = stringArg.replaceAll(aliasChars[j], command.toString(this)); j++;
			}
			// j >= 9 && j <= 17
			for(AnsiColor ansiColor : AnsiColor.values()) {
				if(j >= aliasChars.length) break; if(ansiColor == AnsiColor.DEFAULT) continue; if(!stringArg.contains(aliasChars[j])) { j++; continue; }
				command = parsedCommands.get(ansiColor.name() + "FB");
				if(command == null) { command = ansiColor.asForegroundBrighter().asCommand(); parsedCommands.put(ansiColor.name() + "FB", command); }
				stringArg = stringArg.replaceAll(aliasChars[j], command.toString(this)); j++;
			}
			if(j < aliasChars.length) { if(stringArg.contains(aliasChars[j])) stringArg = doComplicatedParsing(aliasChars[j], stringArg, (objects) -> AnsiColor.getForeground8BitCustomColor(Integer.parseInt((String) objects[0])).toString()); j++; }
			if(j < aliasChars.length) { if(stringArg.contains(aliasChars[j])) stringArg = doComplicatedParsing(aliasChars[j], stringArg, (objects) -> AnsiColor.getForeground24BitCustomColor(new Color(Integer.parseInt((String) objects[0]), Integer.parseInt((String) objects[1]), Integer.parseInt((String) objects[2]))).toString()); j++; }

			/*
			 * Background
			 */
			// j >= 20 && j <= 28
			for(AnsiColor ansiColor : AnsiColor.values()) {
				if(j >= aliasChars.length) break; if(!stringArg.contains(aliasChars[j])) { j++; continue; }
				command = parsedCommands.get(ansiColor.name() + "B");
				if(command == null) { command = ansiColor.asBackground().asCommand(); parsedCommands.put(ansiColor.name() + "B", command); }
				stringArg = stringArg.replaceAll(aliasChars[j], command.toString(this)); j++;
			}
			// j >= 29 && j <= 37
			for(AnsiColor ansiColor : AnsiColor.values()) {
				if(j >= aliasChars.length) break; if(ansiColor == AnsiColor.DEFAULT) continue; if(!stringArg.contains(aliasChars[j])) { j++; continue; }
				command = parsedCommands.get(ansiColor.name() + "BB");
				if(command == null) { command = ansiColor.asBackgroundBrighter().asCommand(); parsedCommands.put(ansiColor.name() + "BB", command); }
				stringArg = stringArg.replaceAll(aliasChars[j], command.toString(this)); j++;
			}
			if(j < aliasChars.length) { if(stringArg.contains(aliasChars[j])) stringArg = doComplicatedParsing(aliasChars[j], stringArg, (objects) -> AnsiColor.getBackground8BitCustomColor(Integer.parseInt((String) objects[0])).toString()); j++; }
			if(j < aliasChars.length) { if(stringArg.contains(aliasChars[j])) stringArg = doComplicatedParsing(aliasChars[j], stringArg, (objects) -> AnsiColor.getBackground24BitCustomColor(new Color(Integer.parseInt((String) objects[0]), Integer.parseInt((String) objects[1]), Integer.parseInt((String) objects[2]))).toString()); j++; }

			/*
			 * Attributes
			 */
			// j >= 40 && j <= 58
			for(AnsiAttribute ansiAttribute : AnsiAttribute.values()) {
				if(j >= aliasChars.length) break; if(!stringArg.contains(aliasChars[j])) { j++; continue; }
				command = parsedCommands.get(ansiAttribute);
				if(command == null) { command = ansiAttribute.asSGRParam().asCommand(); parsedCommands.put(ansiAttribute, command); }
				stringArg = stringArg.replaceAll(aliasChars[j], command.toString(this)); j++;
			}

			args.set(i, stringArg);
		} AttributesHandler.consume(args);
	}

	protected static final Pattern argsPattern = Pattern.compile("(.+)\\[((args)(:)(\\d))]");
	protected static final Pattern paraPattern = Pattern.compile("([^,]+)");
	protected static final String funcPattern = "\\[([^\\[\\]]+)]";
	private String doComplicatedParsing(String commandStructure, String target, ReferencedCallback.StringReferencedCallback callback) {
		Matcher argsMatcher = argsPattern.matcher(commandStructure);
		if(!argsMatcher.find()) return target;
		String aliasName = argsMatcher.group(1);
		int argReq = Integer.parseInt(argsMatcher.group(5));
		if(!target.contains(aliasName)) return target;

		Matcher funcMatcher = Pattern.compile(aliasName + funcPattern).matcher(target);
		FuncMatcher: while(funcMatcher.find()) {
			String funcCode = funcMatcher.group(0);
			funcCode = funcCode.replaceAll("\\[", "\\\\[");
			funcCode = funcCode.replaceAll("]", "\\\\]");
			Matcher paraMatcher = paraPattern.matcher(funcMatcher.group(1));
			String[] params = new String[argReq];
			for(int i = 0; i < params.length; i++) {
				if(!paraMatcher.find()) continue FuncMatcher;
				params[i] = paraMatcher.group(1);
			} target = target.replaceAll(funcCode, callback.get((Object[]) params));
			funcMatcher = Pattern.compile(aliasName + funcPattern).matcher(target); // target was changed
		} return target;
	}

	public String[] getAliasChars() { return aliasChars == defaultAliasChars ? null : aliasChars; }
	public int getMaxLength() { return maxLength; }
	public void setAliasChars(String[] aliasChars) { this.aliasChars = aliasChars != null ? aliasChars : defaultAliasChars; }
	public void setMaxLength(int maxLength) { this.maxLength = maxLength; }
	public String parseAnsiCommand(AnsiCommand command) {
		List<byte[]> sequences = command.sequences;
		StringBuilder builder = Pool.tryBorrow(Pool.getPool(StringBuilder.class)); try {
		builder.append(AnsiCommand.ESC_CHAR).append(command.command.getEscape());
		for(int i = 0; i < sequences.size(); i++) {
			if(i != 0) builder.append(';');
			builder.append(ByteUtils.toChars(sequences.get(i)));
		} builder.append(command.endChar);
		return builder.toString(); } finally { Pool.returnObject(StringBuilder.class, builder); }
	}

	public static class AnsiCommand {
		public static final char ESC_CHAR = 27;

		protected final ArrayList<byte[]> sequences;
		protected AnsiCommandCode command;
		protected char endChar;
		protected String compiledSequences;

		public AnsiCommand(AnsiCommandCode command) {
			this.sequences = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			this.command = command;
		} public AnsiCommand() { this(null); }

		public void assertNotCompiled() { if(compiledSequences != null) throw new IllegalStateException("Already compiled!"); }
		public void assertCompiled() { if(compiledSequences == null) throw new IllegalStateException("Not compiled!"); }
		public List<byte[]> getSequences() { assertNotCompiled(); return Collections.unmodifiableList(sequences); }
		public AnsiCommandCode getCommand() { return command; }
		public char getEndChar() { return endChar; }
		public AnsiCommand setCommand(AnsiCommandCode command) { assertNotCompiled(); this.command = command; return this; }
		public AnsiCommand setEndChar(char endChar) { assertNotCompiled(); this.endChar = endChar; return this; }

		public AnsiCommand add(byte... params) { assertNotCompiled();
			if(params.length == 1 && sequences.size() == 0 && command == null) {
				AnsiCommandCode command = AnsiCommandCode.fromCode((char) params[0]);
				if(command != null) { setCommand(command); return this; }
				throw new IllegalArgumentException("Invalid Command!");
			} sequences.add(params); return this;
		}
		public AnsiCommand add(int... params) { for(int param : params) add(ByteUtils.toByte(String.valueOf(param).toCharArray())); return this; }
		public AnsiCommand remove(int index) { assertNotCompiled(); sequences.remove(index); return this; }
		public String toString(AnsiLogHandler handler) {
			if(handler == null) return toString();
			if(compiledSequences != null) return compiledSequences;
			if(command == null) throw new IllegalArgumentException("Undefined Command!");
			try { compiledSequences = handler.parseAnsiCommand(this); return compiledSequences;
			} finally { Pool.returnObject(ArrayList.class, sequences); }
		}

		public enum AnsiCommandCode {
			SINGLE_SHIFT_TWO('N'),
			SINGLE_SHIFT_THREE('O'),
			DEVICE_CONTROL_STRING('P'),
			CONTROL_SEQUENCE_INTRODUCER('['),
			STRING_TERMINATOR('\\'),
			OPERATING_SYSTEM_COMMAND(']'),
			START_OF_STRING('X'),
			PRIVACY_MESSAGE('^'),
			APPLICATION_PROGRAM_COMMAND('_'),
			RESET_TO_INITIAL_STATE('c');

			private final char escape;
			AnsiCommandCode(char escape) { this.escape = escape; }
			public char getEscape() { return escape; }
			public AnsiCommand asCommand() { return asCommand(this); }

			public static AnsiCommandCode fromCode(char code) {
				for(AnsiCommandCode ansiCommandCode : AnsiCommandCode.values())
					if(ansiCommandCode.getEscape() == code) return ansiCommandCode;
				return null;
			}
			public static AnsiCommand asCommand(AnsiCommandCode ansiCommandCode) {
				return new AnsiCommand(ansiCommandCode);
			}
		}

		public enum CSICommand {
			CURSOR_UP('A'),
			CURSOR_DOWN('B'),
			CURSOR_FORWARD('C'),
			CURSOR_BACKWARD('D'),
			CURSOR_NEXT_LINE('E'),
			CURSOR_PREVIOUS_LINE('F'),
			CURSOR_HORIZONTAL_ABSOLUTE('G'),
			CURSOR_POSITION('H'),
			ERASE_DISPLAY('J'),
			ERASE_LINE('K'),
			SCROLL_UP('S'),
			SCROLL_DOWN('T'),
			HORIZONTAL_VERTICAL_POSITION('f'),
			SELECT_GRAPHIC_RENDITION('m'),
			AUX_ON('i', ByteUtils.toByte(String.valueOf(5).toCharArray())),
			AUX_OFF('i', ByteUtils.toByte(String.valueOf(4).toCharArray())),
			DEVICE_STATUS_REPORT('n', ByteUtils.toByte(String.valueOf(6).toCharArray())),
			SAVE_CURSOR_POSITION('s'),
			RESTORE_CURSOR_POSITION('u');

			private final char code;
			private final byte[][] builtSequences;
			CSICommand(char code, byte[]... builtSequences) { this.code = code; this.builtSequences = builtSequences; }
			public char getCode() { return code; }
			public byte[][] getBuiltSequences() { return builtSequences.clone(); }
			public AnsiCommand asCommand() { return asCommand(this); }

			public static CSICommand fromCode(char code) {
				for(CSICommand csiCommand : CSICommand.values())
					if(csiCommand.getCode() == code) return csiCommand;
				return null;
			}
			public static AnsiCommand asCommand(CSICommand csiCommand) {
				AnsiCommand result = AnsiCommandCode.CONTROL_SEQUENCE_INTRODUCER.asCommand().setEndChar(csiCommand.code);
				for(byte[] sequence : csiCommand.builtSequences) result.add(sequence); return result;
			}
		}
	}

	public enum CSISGRParameter {
		RESET(0),
		ITALIC(3), ITALIC_OFF(23),
		CONCEAL(8), CONCEAL_OFF(28),
		BLINK_SLOW(5), BLINK_FAST(6), BLINK_OFF(25),
		UNDERLINE(4), UNDERLINE_DOUBLE(21), UNDERLINE_OFF(24),
		NEGATIVE(7), NEGATIVE_OFF(27),
		INTENSITY_BOLD(1), INTENSITY_FAINT(2), INTENSITY_BOLD_OFF(22),
		STRIKETHROUGH(9), STRIKETHROUGH_OFF(29),

		PRIMARY_FONT(10),
		ALTERNATE_FONT_1(11), ALTERNATE_FONT_2(12), ALTERNATE_FONT_3(13),
		ALTERNATE_FONT_4(14), ALTERNATE_FONT_5(15), ALTERNATE_FONT_6(16),
		ALTERNATE_FONT_7(17), ALTERNATE_FONT_8(18), ALTERNATE_FONT_9(19),
		// FRAKTUR(20),

		SET_FOREGROUND_1(30), SET_FOREGROUND_2(31), SET_FOREGROUND_3(32), SET_FOREGROUND_4(33),
		SET_FOREGROUND_5(34), SET_FOREGROUND_6(35), SET_FOREGROUND_7(36), SET_FOREGROUND_8(37),
		SET_FOREGROUND_CUSTOM(38), DEFAULT_FOREGROUND(39),

		SET_BACKGROUND_1(40), SET_BACKGROUND_2(41), SET_BACKGROUND_3(42), SET_BACKGROUND_4(43),
		SET_BACKGROUND_5(44), SET_BACKGROUND_6(45), SET_BACKGROUND_7(46), SET_BACKGROUND_8(47),
		SET_BACKGROUND_CUSTOM(48), DEFAULT_BACKGROUND(49),

		SET_FOREGROUND_BRIGHT_1(90), SET_FOREGROUND_BRIGHT_2(91), SET_FOREGROUND_BRIGHT_3(92), SET_FOREGROUND_BRIGHT_4(93),
		SET_FOREGROUND_BRIGHT_5(94), SET_FOREGROUND_BRIGHT_6(95), SET_FOREGROUND_BRIGHT_7(96), SET_FOREGROUND_BRIGHT_8(97),
		SET_BACKGROUND_BRIGHT_1(100), SET_BACKGROUND_BRIGHT_2(101), SET_BACKGROUND_BRIGHT_3(102), SET_BACKGROUND_BRIGHT_4(103),
		SET_BACKGROUND_BRIGHT_5(104), SET_BACKGROUND_BRIGHT_6(105), SET_BACKGROUND_BRIGHT_7(106), SET_BACKGROUND_BRIGHT_8(107);

		private final int code;
		CSISGRParameter(int code) { this.code = code; }
		public int getCode() { return code; }
		public AnsiCommand asCommand() { return asCommand(this); }

		public static CSISGRParameter fromCode(int code) {
			for(CSISGRParameter csisgrParameter : CSISGRParameter.values())
				if(csisgrParameter.getCode() == code) return csisgrParameter;
			return null;
		}
		public static AnsiCommand asCommand(CSISGRParameter csisgrParameter) {
			AnsiCommand ansiCommand = AnsiCommand.CSICommand.SELECT_GRAPHIC_RENDITION.asCommand();
			ansiCommand.add(csisgrParameter.getCode()); return ansiCommand;
		}
	}

	public enum AnsiColor {
		DEFAULT(9), BLACK(0) , RED(1)  ,
		GREEN(2)  , YELLOW(3), BLUE(4) ,
		PURPLE(5) , CYAN(6)  , WHITE(7);

		private final int code;
		AnsiColor(int code) { this.code = code; }
		public int getCode() { return code; }
		public CSISGRParameter asForeground() { return CSISGRParameter.fromCode(code + 30); }
		public CSISGRParameter asForegroundBrighter() { return CSISGRParameter.fromCode(code + 90); }
		public CSISGRParameter asBackground() { return CSISGRParameter.fromCode(code + 40); }
		public CSISGRParameter asBackgroundBrighter() { return CSISGRParameter.fromCode(code + 100); }

		public static AnsiCommand getForeground8BitCustomColor(int n) {
			AnsiCommand ansiCommand = CSISGRParameter.SET_FOREGROUND_CUSTOM.asCommand();
			ansiCommand.add(5); ansiCommand.add(n); return ansiCommand;
		}
		public static AnsiCommand getBackground8BitCustomColor(int n) {
			AnsiCommand ansiCommand = CSISGRParameter.SET_BACKGROUND_CUSTOM.asCommand();
			ansiCommand.add(5); ansiCommand.add(n); return ansiCommand;
		}
		public static AnsiCommand getForeground24BitCustomColor(Color color) {
			AnsiCommand ansiCommand = CSISGRParameter.SET_FOREGROUND_CUSTOM.asCommand();
			ansiCommand.add(2);
			ansiCommand.add(color.getRed());
			ansiCommand.add(color.getGreen());
			ansiCommand.add(color.getBlue());
			return ansiCommand;
		}
		public static AnsiCommand getBackground24BitCustomColor(Color color) {
			AnsiCommand ansiCommand = CSISGRParameter.SET_BACKGROUND_CUSTOM.asCommand();
			ansiCommand.add(2);
			ansiCommand.add(color.getRed());
			ansiCommand.add(color.getGreen());
			ansiCommand.add(color.getBlue());
			return ansiCommand;
		}
	}
	public enum AnsiAttribute {
		RESET(0),
		ITALIC(3),
		ITALIC_OFF(23),
		CONCEAL(8),
		CONCEAL_OFF(28),
		BLINK_SLOW(5),
		BLINK_FAST(6),
		BLINK_OFF(25),
		UNDERLINE(4),
		UNDERLINE_DOUBLE(21),
		UNDERLINE_OFF(24),
		NEGATIVE(7),
		NEGATIVE_OFF(27),
		INTENSITY_BOLD(1),
		INTENSITY_FAINT(2),
		INTENSITY_BOLD_OFF(22),
		STRIKETHROUGH(9),
		STRIKETHROUGH_OFF(29);

		private final int code;
		AnsiAttribute(int code) { this.code = code; }
		public int getCode() { return code; }
		public CSISGRParameter asSGRParam() { return CSISGRParameter.fromCode(code); }
	}
}
