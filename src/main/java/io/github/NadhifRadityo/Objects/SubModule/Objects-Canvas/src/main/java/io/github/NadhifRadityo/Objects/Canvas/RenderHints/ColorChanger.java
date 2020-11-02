package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;

import java.awt.*;

public class ColorChanger extends GraphicModifierManager.CustomGraphicModifier {
	protected Color color;

	public ColorChanger(Color color, Sprite... sprites) { super(sprites); this.color = color; }

	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; }

	protected Color oldColor;
	@Override public void draw(Graphics g) {
		if(oldColor != null) return;
		oldColor = g.getColor();
		g.setColor(color);
	}
	@Override public void reset(Graphics g) {
		if(oldColor == null) return;
		g.setColor(oldColor);
		oldColor = null;
	}
}
