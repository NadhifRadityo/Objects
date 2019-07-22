package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager.CustomGraphicModifier;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Scale extends CustomGraphicModifier {
	protected double sx, sy;
	
	public Scale(double sx, double sy, Sprite... sprites) {
		super(sprites); this.sx = sx; this.sy = sy;
	}
	
	public double getSx() { return sx; }
	public double getSy() { return sy; }
	public void setSx(double sx) { this.sx = sx; }
	public void setSy(double sy) { this.sy = sy; }
	
	private AffineTransform old;
	@Override public void draw(Graphics g) {
		if(!(g instanceof Graphics2D)) return;
		old = ((Graphics2D) g).getTransform();
		((Graphics2D) g).scale(sx, sy);
	}
	@Override public void reset(Graphics g) {
		if(!(g instanceof Graphics2D) || old == null) return;
		((Graphics2D) g).setTransform(old); old = null;
	}
}
