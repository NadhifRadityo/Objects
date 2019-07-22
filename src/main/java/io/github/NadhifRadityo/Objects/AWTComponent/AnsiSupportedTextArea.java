package io.github.NadhifRadityo.Objects.AWTComponent;

import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.AnsiLogHandler;
import io.github.NadhifRadityo.Objects.Pool.Pool;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnsiSupportedTextArea extends JTextPane {
	protected static final Pattern ansiRegex = Pattern.compile("[\\u001B\\u009B][\\[\\]()#;?]*(?:(?:(?:[a-zA-Z\\d]*(?:;[-a-zA-Z\\d\\/#&.:=?%@~_]*)*)?\\u0007)|(?:(?:\\d{1,4}(?:;\\d{0,4})*)?[\\dA-PR-TZcf-ntqry=><~]))");
	protected final Map<String, Object> defaultValues = new HashMap<>();

	public AnsiSupportedTextArea() {
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_FOREGROUND_1), new Color(47, 54, 64));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_FOREGROUND_2), new Color(194, 54, 22));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_FOREGROUND_3), new Color(68, 189, 50));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_FOREGROUND_4), new Color(251, 197, 49));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_FOREGROUND_5), new Color(0, 151, 230));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_FOREGROUND_6), new Color(140, 122, 230));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_FOREGROUND_7), new Color(127, 143, 166));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_FOREGROUND_8), new Color(245, 246, 250));


		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_BACKGROUND_1), new Color(47, 54, 64));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_BACKGROUND_2), new Color(194, 54, 22));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_BACKGROUND_3), new Color(68, 189, 50));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_BACKGROUND_4), new Color(251, 197, 49));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_BACKGROUND_5), new Color(0, 151, 230));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_BACKGROUND_6), new Color(140, 122, 230));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_BACKGROUND_7), new Color(127, 143, 166));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.SGRParam.SET_BACKGROUND_8), new Color(245, 246, 250));

	}

	// TODO: try finally return borrowed object
	public void append(String string) {
		int lastEnd = 0;
		ArrayList<Object> sequences = Pool.tryBorrow(Pool.getPool(ArrayList.class));

		Matcher matcher = ansiRegex.matcher(string);
		while(matcher.find()) {
			MatchResult matchResult = matcher.toMatchResult();
			String matches = matchResult.group(0).replaceFirst("[\\u001B\\u009B][\\[\\]()#;?]*", "");
			String[] params = matches.split("[;:]");
			char commandCode = params[params.length - 1].charAt(params[params.length - 1].length() - 1);
			params[params.length - 1] = params[params.length - 1].substring(0, params[params.length - 1].length() - 1);
			AnsiLogHandler.AnsiCommand.AnsiCommandCode command = AnsiLogHandler.AnsiCommand.AnsiCommandCode.fromCode(commandCode);

			sequences.add(string.substring(lastEnd, matchResult.start())); if(command == null) continue;
			lastEnd = matchResult.end(); sequences.add(command); sequences.add(params);
		} sequences.add(string.substring(lastEnd));

		// TODO
		AttributeSet attributeSet = SimpleAttributeSet.EMPTY;
		for(int i = 0; i < sequences.size(); i++) {
			Object sequence = sequences.get(i);
			if(sequence instanceof String) replaceSelection((String) sequence);
			if(sequence instanceof AnsiLogHandler.AnsiCommand.AnsiCommandCode) {
				String[] params = i + 1 < sequences.size() && (sequences.get(i + 1) instanceof String[]) ?
						(String[]) sequences.get(i + 1) : null; if(params == null) continue; i++;
				attributeSet = parseAnsiCommand((AnsiLogHandler.AnsiCommand.AnsiCommandCode) sequence, params, attributeSet);
				setCharacterAttributes(attributeSet, false);
			} setCaretPosition(getDocument().getLength());
		}
		Pool.returnObject(ArrayList.class, sequences);
	}

	protected AttributeSet parseAnsiCommand(AnsiLogHandler.AnsiCommand.AnsiCommandCode command, String[] sParams, AttributeSet attributeSet) {
		StyleContext styleContext = StyleContext.getDefaultStyleContext();
		int[] params = new int[sParams.length];
		for(int i = 0; i < params.length; i++) params[i] = Integer.parseInt(sParams[i]);

		if(command == AnsiLogHandler.AnsiCommand.AnsiCommandCode.SELECT_GRAPHIC_RENDITION) {
			AnsiLogHandler.SGRParam param = AnsiLogHandler.SGRParam.fromCode(params[0]);
			if(inRange(param.getCode(), 30, 40) || inRange(param.getCode(), 90, 100) && param.getCode() % 10 != 8)
				attributeSet = styleContext.addAttribute(attributeSet, inRange(param.getCode(), 30, 39) || inRange(param.getCode(), 90, 99) ? StyleConstants.Foreground : StyleConstants.Background, defaultValues.get(getEnumIdValue(param)));
			if(param == AnsiLogHandler.SGRParam.SET_FOREGROUND_CUSTOM || param == AnsiLogHandler.SGRParam.SET_BACKGROUND_CUSTOM) {
				Color color = params[1] == 5 ? toColor(params[2]) : new Color(params[2], params[3], params[4]);
				attributeSet = styleContext.addAttribute(attributeSet, param == AnsiLogHandler.SGRParam.SET_FOREGROUND_CUSTOM ? StyleConstants.Foreground : StyleConstants.Background, color);
			}
			if(param == AnsiLogHandler.SGRParam.DEFAULT_FOREGROUND) attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Foreground, getForeground());
			if(param == AnsiLogHandler.SGRParam.DEFAULT_BACKGROUND) attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Background, getBackground());
			if(param == AnsiLogHandler.SGRParam.RESET) attributeSet = styleContext.getEmptySet();
			if(param == AnsiLogHandler.SGRParam.ITALIC) attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Italic, true);
			if(param == AnsiLogHandler.SGRParam.ITALIC_OFF) attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Italic, false);
			if(param == AnsiLogHandler.SGRParam.CONCEAL) throw new UnsupportedOperationException("Not yet implemented.");
			if(param == AnsiLogHandler.SGRParam.CONCEAL_OFF) throw new UnsupportedOperationException("Not yet implemented.");
			if(param == AnsiLogHandler.SGRParam.BLINK_SLOW) throw new UnsupportedOperationException("Not yet implemented.");
			if(param == AnsiLogHandler.SGRParam.BLINK_FAST) throw new UnsupportedOperationException("Not yet implemented.");
			if(param == AnsiLogHandler.SGRParam.BLINK_OFF) throw new UnsupportedOperationException("Not yet implemented.");
			if(param == AnsiLogHandler.SGRParam.UNDERLINE) attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Underline, true);
			if(param == AnsiLogHandler.SGRParam.UNDERLINE_DOUBLE) attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Underline, true);
			if(param == AnsiLogHandler.SGRParam.UNDERLINE_OFF) attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Underline, false);
			if(param == AnsiLogHandler.SGRParam.NEGATIVE && (attributeSet.getAttribute(getEnumIdValue(AnsiLogHandler.SGRParam.NEGATIVE)) == null ||
					!((boolean) attributeSet.getAttribute(getEnumIdValue(AnsiLogHandler.SGRParam.NEGATIVE))))) {
				Object fgColor = attributeSet.getAttribute(StyleConstants.Foreground);
				Object bgColor = attributeSet.getAttribute(StyleConstants.Background);
				attributeSet = styleContext.addAttribute(attributeSet, getEnumIdValue(AnsiLogHandler.SGRParam.NEGATIVE), true);
				attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Foreground, bgColor);
				attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Background, fgColor);
			}
			if(param == AnsiLogHandler.SGRParam.NEGATIVE_OFF && (attributeSet.getAttribute(getEnumIdValue(AnsiLogHandler.SGRParam.NEGATIVE)) != null &&
					((boolean) attributeSet.getAttribute(getEnumIdValue(AnsiLogHandler.SGRParam.NEGATIVE))))) {
				Object fgColor = attributeSet.getAttribute(StyleConstants.Foreground);
				Object bgColor = attributeSet.getAttribute(StyleConstants.Background);
				attributeSet = styleContext.addAttribute(attributeSet, getEnumIdValue(AnsiLogHandler.SGRParam.NEGATIVE), false);
				attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Foreground, bgColor);
				attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Background, fgColor);
			}
			if(param == AnsiLogHandler.SGRParam.INTENSITY_BOLD) attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Bold, true);
			if(param == AnsiLogHandler.SGRParam.INTENSITY_FAINT) attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Bold, false);
			if(param == AnsiLogHandler.SGRParam.INTENSITY_BOLD_OFF) attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Bold, false);
			if(param == AnsiLogHandler.SGRParam.STRIKETHROUGH) attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.StrikeThrough, true);
			if(param == AnsiLogHandler.SGRParam.STRIKETHROUGH_OFF) attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.StrikeThrough, false);
		} return attributeSet;
	}

	private static boolean inRange(int i, int start, int end) { return i >= start && i <= end; }
	private static String getEnumIdValue(Enum obj) { return obj.getClass().getCanonicalName() + "?" + obj.name(); }
	private static Color toColor(int i) { return new Color(((i >> 4) % 4) * 64, ((i >> 2) % 4) * 64, (i % 4) * 64, (i >> 6) * 64); }
}
