package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Utilizations.TimeUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeLogHandler extends SnippetHandler {
	public static final int DEFAULT_PRIORITY = 102;
	public static final Object ENABLED = new Object();

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
	protected static final Pattern injectMatcher = Pattern.compile("(<[a-zA-Z]+>)(.+?(?=<|$))");
	protected static final SnippetListener listener = (snippet, _handler, record) -> {
		List<Object> args = record.getArgs();
		Object STATE_ENABLED = AttributesHandler.checkOnce(ENABLED, AttributesHandler.ON, args);
		if(STATE_ENABLED != AttributesHandler.ON) return 0;

		TimeLogHandler handler = (TimeLogHandler) _handler;
		boolean ansiSupported = AnsiLogHandler.isAnsiSupported(record.getLogger());
		if(ansiSupported) snippet.add(handler.getTimeColor(ADDITIONAL_INJECT)); snippet.add("[");
		String formatted = handler.dateCallback != null ? handler.dateCallback.get(handler) : TimeUtils.getTime(defaultDatePattern);
		Matcher matcher = injectMatcher.matcher(formatted);
		while(matcher.find()) { if(ansiSupported) snippet.add(handler.getTimeColor(matcher.group(1))); snippet.add(matcher.group(2)); }
		if(ansiSupported) snippet.add(handler.getTimeColor(ADDITIONAL_INJECT)); snippet.add("] ");
		if(ansiSupported) snippet.add(AnsiLogHandler.AnsiColor.DEFAULT.asForeground()); return 0;
	};

	protected ReferencedCallback.StringReferencedCallback dateCallback;
	protected ReferencedCallback<Object> colorCallback;
	public TimeLogHandler(ReferencedCallback.StringReferencedCallback dateCallback, ReferencedCallback<Object> colorCallback) {
		super(listener);
		this.dateCallback = dateCallback;
		this.colorCallback = colorCallback;
	}
	public TimeLogHandler(ReferencedCallback.StringReferencedCallback dateCallback) { this(dateCallback, null); }
	public TimeLogHandler(ReferencedCallback<Object> colorCallback) { this(null, colorCallback); }
	public TimeLogHandler() { super(listener); }

	public ReferencedCallback.StringReferencedCallback getDateCallback() { return dateCallback; }
	public ReferencedCallback<Object> getColorCallback() { return colorCallback; }
	public void setDateCallback(ReferencedCallback.StringReferencedCallback dateCallback) { this.dateCallback = dateCallback; }
	public void setColorCallback(ReferencedCallback<Object> colorCallback) { this.colorCallback = colorCallback; }

	public Object getTimeColor(String kind) {
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
