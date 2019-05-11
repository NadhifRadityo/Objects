package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager.CustomGraphicModifier;

public class Scale extends CustomGraphicModifier {
	protected double sx, sy;
	
	public Scale(double sx, double sy) {
		this.sx = sx; this.sy = sy;
	}
	
	public double getSx() { return sx; }
	public double getSy() { return sy; }
	public void setSx(double sx) { this.sx = sx; }
	public void setSy(double sy) { this.sy = sy; }
	
	private AffineTransform old;
	@Override public void draw(Graphics2D g) {
		old = g.getTransform();
		((Graphics2D) g).scale(sx, sy);
	}
	@Override public void reset(Graphics2D g) { g.setTransform(old); }
}
