package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Console.LogHandler;
import io.github.NadhifRadityo.Objects.Console.LogRecord;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.TimeUtils;

public class TimeLogHandler implements LogHandler {
	public static final int DEFAULT_PRIORITY = 101;

	public static final String ERA_INJECT = "<tlhEra>";
	public static final String YEAR_INJECT = "<tlhYear>";
	public static final String MONTH_INJECT = "<tlhMonth>";
	public static final String WEEK_INJECT = "<tlhWeek>";
	public static final String DAY_INJECT = "<tlhDay>";
	public static final String HOUR_INJECT = "<tlhHour>";
	public static final String MINUTE_INJECT = "<tlhMinute>";
	public static final String SECOND_INJECT = "<tlhSecond>";
	public static final String MILLIS_INJECT = "<tlhMillis>";
	public static final String AM_PM_INJECT = "<tlhAmPm>";
	public static final String TIMEZONE_INJECT = "<tlhTimezone>";
	public static final String ADDITIONAL_INJECT = "<tlhAdditional>";
	public static final String[] injectKinds = new String[] {
			ERA_INJECT   , YEAR_INJECT , MONTH_INJECT   , WEEK_INJECT      ,
			DAY_INJECT   , HOUR_INJECT , MINUTE_INJECT  , SECOND_INJECT    ,
			MILLIS_INJECT, AM_PM_INJECT, TIMEZONE_INJECT, ADDITIONAL_INJECT
	};
	
	private static String COLOR_INJECT(String string) { return "'" + string + "'"; }
	public static final String ERA_COLOR_INJECT = COLOR_INJECT(ERA_INJECT);
	public static final String YEAR_COLOR_INJECT = COLOR_INJECT(YEAR_INJECT);
	public static final String MONTH_COLOR_INJECT = COLOR_INJECT(MONTH_INJECT);
	public static final String WEEK_COLOR_INJECT = COLOR_INJECT(WEEK_INJECT);
	public static final String DAY_COLOR_INJECT = COLOR_INJECT(DAY_INJECT);
	public static final String HOUR_COLOR_INJECT = COLOR_INJECT(HOUR_INJECT);
	public static final String MINUTE_COLOR_INJECT = COLOR_INJECT(MINUTE_INJECT);
	public static final String SECOND_COLOR_INJECT = COLOR_INJECT(SECOND_INJECT);
	public static final String MILLIS_COLOR_INJECT = COLOR_INJECT(MILLIS_INJECT);
	public static final String AM_PM_COLOR_INJECT = COLOR_INJECT(AM_PM_INJECT);
	public static final String TIMEZONE_COLOR_INJECT = COLOR_INJECT(TIMEZONE_INJECT);
	public static final String ADDITIONAL_COLOR_INJECT = COLOR_INJECT(ADDITIONAL_INJECT);
	public static final String[] colorInjectKinds = new String[] {
			ERA_COLOR_INJECT   , YEAR_COLOR_INJECT , MONTH_COLOR_INJECT   , WEEK_COLOR_INJECT      ,
			DAY_COLOR_INJECT   , HOUR_COLOR_INJECT , MINUTE_COLOR_INJECT  , SECOND_COLOR_INJECT    ,
			MILLIS_COLOR_INJECT, AM_PM_COLOR_INJECT, TIMEZONE_COLOR_INJECT, ADDITIONAL_COLOR_INJECT
	};
	public static final String defaultDatePattern = HOUR_COLOR_INJECT + "HH" + ADDITIONAL_COLOR_INJECT + ":" + MINUTE_COLOR_INJECT + "mm" + ADDITIONAL_COLOR_INJECT + ":" + SECOND_COLOR_INJECT + "ss";

	protected ReferencedCallback.StringReferencedCallback dateCallback;
	protected ReferencedCallback<AnsiLogHandler.AnsiColor> colorCallback;

	public TimeLogHandler(ReferencedCallback.StringReferencedCallback dateCallback, ReferencedCallback<AnsiLogHandler.AnsiColor> colorCallback) {
		this.dateCallback = dateCallback;
		this.colorCallback = colorCallback;
	}
	public TimeLogHandler(ReferencedCallback.StringReferencedCallback dateCallback) { this(dateCallback, null); }
	public TimeLogHandler(ReferencedCallback<AnsiLogHandler.AnsiColor> colorCallback) { this(null, colorCallback); }
	public TimeLogHandler() { }

	@Override public void manipulateLog(LogRecord record) {
		boolean coloredSupport = AnsiLogHandler.isAnsiSupport(record.getLogger());
		StringBuilder builder = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
		if(coloredSupport) builder.append(AnsiLogHandler.AnsiColor.YELLOW.asForeground().asCommand().toString()); builder.append("[");
		String formatted = dateCallback != null ? dateCallback.get(this) : TimeUtils.getTime(defaultDatePattern);
		if(coloredSupport) for(String kind : injectKinds) formatted = formatted.replaceAll(kind, getTimeColor(kind).asForeground().asCommand().toString()); builder.append(formatted);
		if(coloredSupport) builder.append(AnsiLogHandler.AnsiColor.YELLOW.asForeground().asCommand().toString()); builder.append("]");
		if(coloredSupport) builder.append(AnsiLogHandler.AnsiAttribute.RESET.asSGRParam().asCommand().toString());
		record.getArgs().add(0, builder.toString());
		Pool.returnObject(StringBuilder.class, builder);
	}

	public ReferencedCallback.StringReferencedCallback getDateCallback() { return dateCallback; }
	public ReferencedCallback<AnsiLogHandler.AnsiColor> getColorCallback() { return colorCallback; }
	public void setDateCallback(ReferencedCallback.StringReferencedCallback dateCallback) { this.dateCallback = dateCallback; }
	public void setColorCallback(ReferencedCallback<AnsiLogHandler.AnsiColor> colorCallback) { this.colorCallback = colorCallback; }

	public AnsiLogHandler.AnsiColor getTimeColor(String kind) {
		if(colorCallback != null) return colorCallback.get(kind, this);
		switch(kind) {
			case ERA_INJECT:
			case AM_PM_INJECT:
				return AnsiLogHandler.AnsiColor.RED;
			case YEAR_INJECT:
			case ADDITIONAL_INJECT:
				return AnsiLogHandler.AnsiColor.YELLOW;
			case MONTH_INJECT:
			case MINUTE_INJECT:
				return AnsiLogHandler.AnsiColor.GREEN;
			case WEEK_INJECT:
			case TIMEZONE_INJECT:
				return AnsiLogHandler.AnsiColor.BLUE;
			case DAY_INJECT:
			case SECOND_INJECT:
				return AnsiLogHandler.AnsiColor.CYAN;
			case HOUR_INJECT:
				return AnsiLogHandler.AnsiColor.PURPLE;
			case MILLIS_INJECT:
				return AnsiLogHandler.AnsiColor.WHITE;
		} return AnsiLogHandler.AnsiColor.WHITE;
	}
}
