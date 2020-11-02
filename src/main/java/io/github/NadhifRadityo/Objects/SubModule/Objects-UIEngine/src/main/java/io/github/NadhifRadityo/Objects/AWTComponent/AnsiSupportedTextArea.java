package io.github.NadhifRadityo.Objects.AWTComponent;

import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.AnsiLogHandler;
import io.github.NadhifRadityo.Objects.Pool.Pool;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnsiSupportedTextArea extends JTextPane {
	protected static final Pattern ansiRegex = Pattern.compile("[\\u001B\\u009B][\\[\\]()#;?]*(?:(?:(?:[a-zA-Z\\d]*(?:;[-a-zA-Z\\d\\/#&.:=?%@~_]*)*)?\\u0007)|(?:(?:\\d{1,4}(?:;\\d{0,4})*)?[\\dA-PR-TZcf-ntqry=><~]))");
	protected final Map<String, Object> defaultValues = new HashMap<>();

	public AnsiSupportedTextArea() {
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_FOREGROUND_1), new Color(47, 54, 64));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_FOREGROUND_2), new Color(194, 54, 22));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_FOREGROUND_3), new Color(68, 189, 50));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_FOREGROUND_4), new Color(251, 197, 49));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_FOREGROUND_5), new Color(0, 151, 230));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_FOREGROUND_6), new Color(140, 122, 230));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_FOREGROUND_7), new Color(127, 143, 166));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_FOREGROUND_8), new Color(245, 246, 250));


		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_BACKGROUND_1), new Color(47, 54, 64));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_BACKGROUND_2), new Color(194, 54, 22));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_BACKGROUND_3), new Color(68, 189, 50));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_BACKGROUND_4), new Color(251, 197, 49));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_BACKGROUND_5), new Color(0, 151, 230));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_BACKGROUND_6), new Color(140, 122, 230));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_BACKGROUND_7), new Color(127, 143, 166));
		defaultValues.put(getEnumIdValue(AnsiLogHandler.CSISGRParameter.SET_BACKGROUND_8), new Color(245, 246, 250));

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
			AnsiLogHandler.AnsiCommand.CSICommand command = AnsiLogHandler.AnsiCommand.CSICommand.fromCode(commandCode);

			sequences.add(string.substring(lastEnd, matchResult.start())); if(command == null) continue;
			lastEnd = matchResult.end(); sequences.add(command); sequences.add(params);
		} sequences.add(string.substring(lastEnd));

		// TODO
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		for(int i = 0; i < sequences.size(); i++) {
			Object sequence = sequences.get(i);
			if(sequence instanceof String) replaceSelection((String) sequence);
			if(sequence instanceof AnsiLogHandler.AnsiCommand.CSICommand) {
				String[] params = i + 1 < sequences.size() && (sequences.get(i + 1) instanceof String[]) ?
						(String[]) sequences.get(i + 1) : null; if(params == null) continue; i++;
				attributeSet = parseAnsiCommand((AnsiLogHandler.AnsiCommand.CSICommand) sequence, params, attributeSet);
				setCharacterAttributes(attributeSet, false);
			} setCaretPosition(getDocument().getLength());
		}
		Pool.returnObject(ArrayList.class, sequences);
	}

	protected SimpleAttributeSet parseAnsiCommand(AnsiLogHandler.AnsiCommand.CSICommand command, String[] sParams, SimpleAttributeSet attributeSet) {
		int[] params = new int[sParams.length];
		for(int i = 0; i < params.length; i++) params[i] = Integer.parseInt(sParams[i]);

		switch(command) {
			case SELECT_GRAPHIC_RENDITION: {
				AnsiLogHandler.CSISGRParameter param = AnsiLogHandler.CSISGRParameter.fromCode(params[0]);
				if(inRange(param.getCode(), 30, 40) || inRange(param.getCode(), 90, 100) && param.getCode() % 10 != 8)
					attributeSet.addAttribute(inRange(param.getCode(), 30, 39) || inRange(param.getCode(), 90, 99) ? StyleConstants.Foreground : StyleConstants.Background, defaultValues.get(getEnumIdValue(param)));
				if(param == AnsiLogHandler.CSISGRParameter.SET_FOREGROUND_CUSTOM || param == AnsiLogHandler.CSISGRParameter.SET_BACKGROUND_CUSTOM) {
					Color color = params[1] == 5 ? toColor(params[2]) : new Color(params[2], params[3], params[4]);
					attributeSet.addAttribute(param == AnsiLogHandler.CSISGRParameter.SET_FOREGROUND_CUSTOM ? StyleConstants.Foreground : StyleConstants.Background, color);
				}

				switch(param) {
					case DEFAULT_FOREGROUND: StyleConstants.setForeground(attributeSet, getForeground()); break;
					case DEFAULT_BACKGROUND: StyleConstants.setBackground(attributeSet, getBackground()); break;
					case RESET: attributeSet = new SimpleAttributeSet(); break;
					case ITALIC: StyleConstants.setItalic(attributeSet, true); break;
					case ITALIC_OFF: StyleConstants.setItalic(attributeSet, false); break;
					case CONCEAL:
					case CONCEAL_OFF:
					case BLINK_SLOW:
					case BLINK_FAST:
					case BLINK_OFF: throw new UnsupportedOperationException("Not yet implemented.");
					case UNDERLINE: StyleConstants.setUnderline(attributeSet, true);
					case UNDERLINE_DOUBLE: StyleConstants.setUnderline(attributeSet, true);
					case UNDERLINE_OFF: StyleConstants.setUnderline(attributeSet, false);
					case NEGATIVE: { if(attributeSet.getAttribute(getEnumIdValue(AnsiLogHandler.CSISGRParameter.NEGATIVE)) == null ||
							!((boolean) attributeSet.getAttribute(getEnumIdValue(AnsiLogHandler.CSISGRParameter.NEGATIVE)))) {
						Color fgColor = StyleConstants.getForeground(attributeSet);
						Color bgColor = StyleConstants.getBackground(attributeSet);
						attributeSet.addAttribute(getEnumIdValue(AnsiLogHandler.CSISGRParameter.NEGATIVE), true);
						StyleConstants.setForeground(attributeSet, bgColor);
						StyleConstants.setBackground(attributeSet, fgColor);
					} }
					case NEGATIVE_OFF: { if(attributeSet.getAttribute(getEnumIdValue(AnsiLogHandler.CSISGRParameter.NEGATIVE)) != null &&
							((boolean) attributeSet.getAttribute(getEnumIdValue(AnsiLogHandler.CSISGRParameter.NEGATIVE)))) {
						Color fgColor = StyleConstants.getForeground(attributeSet);
						Color bgColor = StyleConstants.getBackground(attributeSet);
						attributeSet.addAttribute(getEnumIdValue(AnsiLogHandler.CSISGRParameter.NEGATIVE), false);
						StyleConstants.setForeground(attributeSet, bgColor);
						StyleConstants.setBackground(attributeSet, fgColor);
					} }
					case INTENSITY_BOLD: StyleConstants.setBold(attributeSet, true); break;
					case INTENSITY_FAINT:
					case INTENSITY_BOLD_OFF: StyleConstants.setBold(attributeSet, false); break;
					case STRIKETHROUGH: StyleConstants.setStrikeThrough(attributeSet, true); break;
					case STRIKETHROUGH_OFF: StyleConstants.setStrikeThrough(attributeSet, false); break;
				}
			}
			default: { }
		} return attributeSet;
	}

	private static boolean inRange(int i, int start, int end) { return i >= start && i <= end; }
	private static String getEnumIdValue(Enum obj) { return obj.getClass().getCanonicalName() + "?" + obj.name(); }
	private static Color toColor(int i) { return new Color(((i >> 4) % 4) * 64, ((i >> 2) % 4) * 64, (i % 4) * 64, (i >> 6) * 64); }

	protected static class SimpleAttributeSet implements MutableAttributeSet, Serializable, Cloneable {
		public static final AttributeSet EMPTY = new EmptyAttributeSet();
		private transient LinkedHashMap<Object, Object> table = new LinkedHashMap<>(3);

		public SimpleAttributeSet() { }
		public SimpleAttributeSet(AttributeSet source) { addAttributes(source); }

		public boolean isEmpty() { return table.isEmpty(); }
		public int getAttributeCount() { return table.size(); }
		public boolean isDefined(Object attrName) { return table.containsKey(attrName); }
		public boolean isEqual(AttributeSet attr) { return ((getAttributeCount() == attr.getAttributeCount()) && containsAttributes(attr)); }
		public AttributeSet copyAttributes() { return (AttributeSet) clone(); }
		public Enumeration<?> getAttributeNames() { return Collections.enumeration(table.keySet()); }

		// TODO NullPointerException -> check if attribute is equal. But, some key are missing.
		public Object getAttribute(Object name) {
			Object value = table.get(name);
			if(value != null) return value;
			System.out.println(name);
			return getResolveParent() != null ? getResolveParent().getAttribute(name) : null;
		}
		public boolean containsAttribute(Object name, Object value) { return value.equals(getAttribute(name)); }
		public boolean containsAttributes(AttributeSet attributes) {
			boolean result = true;
			Enumeration<?> names = attributes.getAttributeNames();
			while (result && names.hasMoreElements()) {
				Object name = names.nextElement();
				Object object = attributes.getAttribute(name);
				if(object == null) return false;
				result = object.equals(getAttribute(name));
			} return result;
		}

		public void addAttribute(Object name, Object value) { table.put(name, value); }
		public void addAttributes(AttributeSet attributes) {
			Enumeration<?> names = attributes.getAttributeNames();
			while (names.hasMoreElements()) {
				Object name = names.nextElement();
				addAttribute(name, attributes.getAttribute(name));
			}
		}

		public void removeAttribute(Object name) { table.remove(name); }
		public void removeAttributes(Enumeration<?> names) { while (names.hasMoreElements()) removeAttribute(names.nextElement()); }
		public void removeAttributes(AttributeSet attributes) {
			if (attributes == this) table.clear();
			else {
				Enumeration<?> names = attributes.getAttributeNames();
				while (names.hasMoreElements()) {
					Object name = names.nextElement();
					Object value = attributes.getAttribute(name);
					if (value.equals(getAttribute(name)))
						removeAttribute(name);
				}
			}
		}

		public AttributeSet getResolveParent() { return (AttributeSet) table.get(StyleConstants.ResolveAttribute); }
		public void setResolveParent(AttributeSet parent) { addAttribute(StyleConstants.ResolveAttribute, parent); }

		@SuppressWarnings("unchecked")
		public Object clone() {
			SimpleAttributeSet attr; try {
				attr = (SimpleAttributeSet) super.clone();
				attr.table = (LinkedHashMap) table.clone();
			} catch (CloneNotSupportedException cnse) { attr = null; }
			return attr;
		}
		public int hashCode() { return table.hashCode(); }
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj instanceof AttributeSet) {
				AttributeSet attrs = (AttributeSet) obj;
				return isEqual(attrs);
			} return false;
		}
		public String toString() {
			String s = "";
			Enumeration<?> names = getAttributeNames();
			while (names.hasMoreElements()) {
				Object key = names.nextElement();
				Object value = getAttribute(key);
				s = s + key + (value instanceof AttributeSet ? "=**AttributeSet** " : "=" + value + " ");
			} return s;
		}

		private void writeObject(java.io.ObjectOutputStream s) throws IOException {
			s.defaultWriteObject();
			StyleContext.writeAttributeSet(s, this);
		}
		private void readObject(ObjectInputStream s)
				throws ClassNotFoundException, IOException {
			s.defaultReadObject();
			table = new LinkedHashMap<>(3);
			StyleContext.readAttributeSet(s, this);
		}

		static class EmptyAttributeSet implements AttributeSet, Serializable {
			public int getAttributeCount() { return 0; }
			public boolean isDefined(Object attrName) { return false; }
			public boolean isEqual(AttributeSet attr) { return (attr.getAttributeCount() == 0); }
			public AttributeSet copyAttributes() { return this; }
			public Object getAttribute(Object key) { return null; }
			public Enumeration<?> getAttributeNames() { return Collections.emptyEnumeration(); }
			public boolean containsAttribute(Object name, Object value) { return false; }
			public boolean containsAttributes(AttributeSet attributes) { return (attributes.getAttributeCount() == 0); }
			public AttributeSet getResolveParent() { return null; }
			public boolean equals(Object obj) {
				if (this == obj) return true;
				return ((obj instanceof AttributeSet) && (((AttributeSet)obj).getAttributeCount() == 0));
			}
			public int hashCode() { return 0; }
		}
	}
}
