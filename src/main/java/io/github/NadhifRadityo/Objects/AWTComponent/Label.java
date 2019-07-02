package io.github.NadhifRadityo.Objects.AWTComponent;

import javax.swing.JLabel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

public class Label extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2042564162824898121L;
	
	protected String pureText = "";
	protected String textPrefix = "";
	protected String textSuffix = "";
	
	public Label(String text) { super(text); }
	public Label() { super(); }
	
	public void setTextPrefix(String textPrefix) { this.textPrefix = textPrefix; }
	public void setTextSuffix(String textSuffix) { this.textSuffix = textSuffix; }
	@Override public void setText(String text) {
		this.pureText = text;
		String prefix = textPrefix != null ? textPrefix : "";
		String suffix = textSuffix != null ? textSuffix : "";
		super.setText(prefix + text + suffix);
	}
	
	public String getTextPrefix() { return textPrefix; }
	public String getTextSuffix() { return textSuffix; }
	public String getPureText(boolean fromCache) {
		try { if(fromCache) return pureText;
			String text = getText();
			if(!textPrefix.equals("")) text = text.replaceFirst(textPrefix, "");
			if(!textSuffix.equals("")) text = text.substring(0, text.length() - textSuffix.length());
			return text;
		} catch(Exception e) { return ""; }
	} public String getPureText() { return getPureText(true); }
	
	public void updateUI() { setText(pureText); super.updateUI(); }
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("pureText", pureText)
				.append("textPrefix", textPrefix).append("textSuffix", textSuffix).toString();
	}
	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Label))
			return false;
		Label castOther = (Label) other;
		return new EqualsBuilder().append(pureText, castOther.pureText).append(textPrefix, castOther.textPrefix)
				.append(textSuffix, castOther.textSuffix).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(pureText).append(textPrefix).append(textSuffix).toHashCode();
	}
}
