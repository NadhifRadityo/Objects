package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;

import java.awt.*;

public class StrokeChanger extends GraphicModifierManager.CustomGraphicModifier {
	protected Stroke stroke;

	public StrokeChanger(Stroke stroke, Sprite... sprites) { super(sprites); this.stroke = stroke; }

	public Stroke getStroke() { return stroke; }
	public void setStroke(Stroke stroke) { this.stroke = stroke; }

	protected Stroke oldStroke;
	@Override public void draw(Graphics g) {
		if(!(g instanceof Graphics2D) || oldStroke != null) return;
		oldStroke = ((Graphics2D) g).getStroke();
		((Graphics2D) g).setStroke(stroke);
	}
	@Override public void reset(Graphics g) {
		if(!(g instanceof Graphics2D) || oldStroke == null) return;
		((Graphics2D) g).setStroke(oldStroke);
		oldStroke = null;
	}
}
