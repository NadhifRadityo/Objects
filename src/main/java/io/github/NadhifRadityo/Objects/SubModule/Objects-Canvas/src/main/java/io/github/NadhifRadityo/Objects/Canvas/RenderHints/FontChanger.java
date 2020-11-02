package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;

import java.awt.*;

public class FontChanger extends GraphicModifierManager.CustomGraphicModifier {
	protected Font font;
	
	public FontChanger(Font font, Sprite... sprites) { super(sprites); this.font = font; }

	public Font getFont() { return font; }
	public void setFont(Font font) { this.font = font; }

	private Font old;
	@Override public void draw(Graphics g) { old = g.getFont(); g.setFont(font); }
	@Override public void reset(Graphics g) { g.setFont(old); }
}
