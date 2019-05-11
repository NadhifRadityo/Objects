package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import java.awt.Font;
import java.awt.Graphics2D;

import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager.CustomGraphicModifier;

public class FontChanger extends CustomGraphicModifier {
	protected Font font;
	
	public FontChanger(Font font) { this.font = font; }

	public Font getFont() { return font; }
	public void setFont(Font font) { this.font = font; }

	private Font old;
	@Override public void draw(Graphics2D g) { old = g.getFont(); g.setFont(font); }
	@Override public void reset(Graphics2D g) { g.setFont(old); }
}
