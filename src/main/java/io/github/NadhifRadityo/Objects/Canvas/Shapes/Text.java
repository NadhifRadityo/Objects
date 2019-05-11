package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Text extends Rectangle {
	protected String text;
	
	public Text(int x, int y, String text) {
		super(x, y, 0, 0);
		this.text = text;
	} public Text(Point p, String text) { this(p.getX(), p.getY(), text); }
	
	public String getText() { return text; }
	public void setText(String text) { this.text = text; int[] dimension = getTextDimension(text, lastFont); this.width = dimension[0]; this.height = dimension[1]; }
	@Override public void setWidth(int width) { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public void setHeight(int height) { throw new UnsupportedOperationException("Not implemented!"); }
	
	private Font lastFont;
	@Override public void draw(Graphics g) {
		if(width <= 0 || height <= 0 || !g.getFont().equals(lastFont)) {
			int[] dimension = getTextDimension(text, g.getFont());
			this.width = dimension[0]; this.height = dimension[1]; this.lastFont = g.getFont();
		} g.drawString(text, x, y + ((height / 2) + (height / 3)));
	}
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Text))
			return false;
		return new EqualsBuilder().appendSuper(super.equals(other)).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).toHashCode();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).toString();
	}
	
	public static int[] getTextDimension(String text, Font font) {
		if(font == null) return new int[] { -1, -1 };
		if(text == null) text = "";
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
		int[] results = new int[2];
		results[0] = (int)(font.getStringBounds(text, frc).getWidth());
		results[1] = (int)(font.getStringBounds(text, frc).getHeight());
		return results;
	}
}
