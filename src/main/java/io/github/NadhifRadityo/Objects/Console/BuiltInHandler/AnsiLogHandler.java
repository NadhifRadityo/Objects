package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Console.LogHandler;
import io.github.NadhifRadityo.Objects.Console.LogRecord;
import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnsiLogHandler implements LogHandler {
	public static final int DEFAULT_PRIORITY = 100;
	public static final int defaultMaxLength = 10000;
	public static final String[] defaultAliasChars = new String[] {
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

	protected String[] aliasChars;
	protected int maxLength;

	public AnsiLogHandler(String[] aliasChars, int maxLength) {
		this.aliasChars = aliasChars;
		this.maxLength = maxLength;
	}
	public AnsiLogHandler(String[] aliasChars) { this(aliasChars, defaultMaxLength); }
	public AnsiLogHandler(int maxLength) { this(defaultAliasChars, maxLength); }
	public AnsiLogHandler() { this(defaultMaxLength); }

	@Override public void manipulateLog(LogRecord record) {
		List<Object> args = record.getArgs();
		for(int i = 0; i < args.size(); i++) {
			Object obj = args.get(i); if(!(obj instanceof String)) continue;
			if(((String) obj).length() >= maxLength) continue;
			String arg = (String) obj; int j = 0;

			/*
			 * Foreground
			 */
			// j >= 0 && j <= 8
			for(AnsiColor ansiColor : AnsiColor.values()) {
				if(j >= aliasChars.length) break;
				arg = arg.replaceAll(aliasChars[j], ansiColor.asForeground().asCommand().toString());
				j++;
			}
			// j >= 9 && j <= 17
			for(AnsiColor ansiColor : AnsiColor.values()) {
				if(j >= aliasChars.length) break; if(ansiColor == AnsiColor.DEFAULT) continue;
				arg = arg.replaceAll(aliasChars[j], ansiColor.asForegroundBrighter().asCommand().toString());
				j++;
			}
			if(j < aliasChars.length) arg = doComplicatedParsing(aliasChars[j++], arg, (objects) -> AnsiColor.getForeground8BitCustomColor(Integer.parseInt((String) objects[0])).toString());
			if(j < aliasChars.length) arg = doComplicatedParsing(aliasChars[j++], arg, (objects) -> AnsiColor.getForeground24BitCustomColor(new Color(Integer.parseInt((String) objects[0]), Integer.parseInt((String) objects[1]), Integer.parseInt((String) objects[2]))).toString());

			/*
			 * Background
			 */
			// j >= 20 && j <= 28
			for(AnsiColor ansiColor : AnsiColor.values()) {
				if(j >= aliasChars.length) break;
				arg = arg.replaceAll(aliasChars[j], ansiColor.asBackground().asCommand().toString());
				j++;
			}
			// j >= 29 && j <= 37
			for(AnsiColor ansiColor : AnsiColor.values()) {
				if(j >= aliasChars.length) break; if(ansiColor == AnsiColor.DEFAULT) continue;
				arg = arg.replaceAll(aliasChars[j], ansiColor.asBackgroundBrighter().asCommand().toString());
				j++;
			}
			if(j < aliasChars.length) arg = doComplicatedParsing(aliasChars[j++], arg, (objects) -> AnsiColor.getBackground8BitCustomColor(Integer.parseInt((String) objects[0])).toString());
			if(j < aliasChars.length) arg = doComplicatedParsing(aliasChars[j++], arg, (objects) -> AnsiColor.getBackground24BitCustomColor(new Color(Integer.parseInt((String) objects[0]), Integer.parseInt((String) objects[1]), Integer.parseInt((String) objects[2]))).toString());

			/*
			 * Attributes
			 */
			// j >= 40 && j <= 58
			for(AnsiAttribute ansiAttribute : AnsiAttribute.values()) {
				if(j >= aliasChars.length) break;
				arg = arg.replaceAll(aliasChars[j], ansiAttribute.asSGRParam().asCommand().toString());
				j++;
			}

			args.set(i, arg);
		}
	}

	protected Pattern argsPattern = Pattern.compile("(.+)\\[((args)(:)(\\d))]");
	protected Pattern paraPattern = Pattern.compile("([^,]+)");
	protected String funcPattern = "\\[([^\\[\\]]+)]";
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

	public static boolean isAnsiSupport(Logger logger) {
		for(LogHandler handler : logger.getHandlers().keySet())
			if(AnsiLogHandler.class.isAssignableFrom(handler.getClass())) return true;
		return false;
	}

	public static class AnsiCommand {
		public static final char FIRST_ESC_CHAR = 27;
		public static final char SECOND_ESC_CHAR = '[';

		protected final ArrayList<Integer> sequences;
		protected String compiledSequences;
		protected AnsiCommandCode command;

		public AnsiCommand(AnsiCommandCode command) {
			this.sequences = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			this.command = command;
		} public AnsiCommand() { this(null); }

		public List<Integer> getSequences() { return Collections.unmodifiableList(sequences); }
		public AnsiCommandCode getCommand() { return command; }
		public void setCommand(AnsiCommandCode command) { this.command = command; }

		public void add(int param) {
			if(sequences.size() == 0 && command == null) {
				AnsiCommandCode command = AnsiCommandCode.fromCode((char) param);
				if(command != null) setCommand(command);
				throw new IllegalArgumentException("Invalid Command!");
			} sequences.add(param);
		} public void remove(int index) { sequences.remove(index); }

		@Override public String toString() {
			if(compiledSequences != null) return compiledSequences;
			if(command == null) throw new IllegalArgumentException("Undefined Command!");
			StringBuilder builder = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
			builder.append(FIRST_ESC_CHAR); builder.append(SECOND_ESC_CHAR);
			for(int i = 0; i < sequences.size(); i++) {
				if(i != 0) builder.append(';');
				builder.append(sequences.get(i));
			} builder.append(command.getCode());
			compiledSequences = builder.toString();
			Pool.returnObject(StringBuilder.class, builder);
			Pool.returnObject(ArrayList.class, sequences);
			return compiledSequences;
		}

		public enum AnsiCommandCode {
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
			SAVE_CURSOR_POSITION('s'),
			RESTORE_CURSOR_POSITION('u');

			private final char code;
			AnsiCommandCode(char code) { this.code = code; }
			public char getCode() { return code; }
			public AnsiCommand asCommand() { return asCommand(this); }

			public static AnsiCommandCode fromCode(char code) {
				for(AnsiCommandCode ansiCommandCode : AnsiCommandCode.values())
					if(ansiCommandCode.getCode() == code) return ansiCommandCode;
				return null;
			}
			public static AnsiCommand asCommand(AnsiCommandCode ansiCommandCode) {
				return new AnsiCommand(ansiCommandCode);
			}
		}
	}

	public enum SGRParam {
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
		SGRParam(int code) { this.code = code; }
		public int getCode() { return code; }
		public AnsiCommand asCommand() { return asCommand(this); }

		public static SGRParam fromCode(int code) {
			for(SGRParam sgrParam : SGRParam.values())
				if(sgrParam.getCode() == code) return sgrParam;
			return null;
		}
		public static AnsiCommand asCommand(SGRParam sgrParam) {
			AnsiCommand ansiCommand = AnsiCommand.AnsiCommandCode.SELECT_GRAPHIC_RENDITION.asCommand();
			ansiCommand.add(sgrParam.getCode()); return ansiCommand;
		}
	}

	public enum AnsiColor {
		DEFAULT(9), BLACK(0) , RED(1)  ,
		GREEN(2)  , YELLOW(3), BLUE(4) ,
		PURPLE(5) , CYAN(6)  , WHITE(7);

		private final int code;
		AnsiColor(int code) { this.code = code; }

		public int getCode() { return code; }
		public SGRParam asForeground() { return SGRParam.fromCode(code + 30); }
		public SGRParam asForegroundBrighter() { return SGRParam.fromCode(code + 90); }
		public SGRParam asBackground() { return SGRParam.fromCode(code + 40); }
		public SGRParam asBackgroundBrighter() { return SGRParam.fromCode(code + 100); }

		public static AnsiCommand getForeground8BitCustomColor(int n) {
			AnsiCommand ansiCommand = SGRParam.SET_FOREGROUND_CUSTOM.asCommand();
			ansiCommand.add(5); ansiCommand.add(n); return ansiCommand;
		}
		public static AnsiCommand getBackground8BitCustomColor(int n) {
			AnsiCommand ansiCommand = SGRParam.SET_BACKGROUND_CUSTOM.asCommand();
			ansiCommand.add(5); ansiCommand.add(n); return ansiCommand;
		}
		public static AnsiCommand getForeground24BitCustomColor(Color color) {
			AnsiCommand ansiCommand = SGRParam.SET_FOREGROUND_CUSTOM.asCommand();
			ansiCommand.add(2);
			ansiCommand.add(color.getRed());
			ansiCommand.add(color.getGreen());
			ansiCommand.add(color.getBlue());
			return ansiCommand;
		}
		public static AnsiCommand getBackground24BitCustomColor(Color color) {
			AnsiCommand ansiCommand = SGRParam.SET_BACKGROUND_CUSTOM.asCommand();
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
		public SGRParam asSGRParam() { return SGRParam.fromCode(code); }
	}
}
