package io.github.NadhifRadityo.Objects.AWTComponent;

import javax.swing.JLabel;

public class Label extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2042564162824898121L;
	
	private String textPrefix = "";
	private String textSuffix = "";
	
	public Label(String text) { super(text); }
	public Label() { super(); }
	
	public void setTextPrefix(String textPrefix) {
		this.textPrefix = textPrefix;
	}
	public void setTextSuffix(String textSuffix) {
		this.textSuffix = textSuffix;
	}
	
	public String getTextPrefix() {
		return textPrefix;
	}
	public String getTextSuffix() {
		return textSuffix;
	}
	public String getPureText() {
		try {
			String text = getText();
			if(!textPrefix.equals("")) text = text.replaceFirst(textPrefix, "");
			if(!textSuffix.equals("")) text = text.substring(0, text.length() - textSuffix.length());
			return text;
		}catch(Exception e) { return ""; }
	}
	
	@Override
	public void setText(String text) {
		String prefix = textPrefix != null ? textPrefix : "";
		String suffix = textSuffix != null ? textSuffix : "";
		super.setText(prefix + text + suffix);
	}
}
