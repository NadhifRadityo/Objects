package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager.CustomGraphicModifier;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Shear extends CustomGraphicModifier {
	protected double shx, shy;
	
	public Shear(double shx, double shy, Sprite... sprites) {
		super(sprites); this.shx = shx; this.shy = shy;
	}
	
	public double getShx() { return shx; }
	public double getShy() { return shy; }
	public void setShx(double shx) { this.shx = shx; }
	public void setShy(double shy) { this.shy = shy; }
	
	private AffineTransform old;
	@Override public void draw(Graphics g) {
		if(!(g instanceof Graphics2D)) return;
		old = ((Graphics2D) g).getTransform();
		((Graphics2D) g).shear(shx, shy);
	}
	@Override public void reset(Graphics g) {
		if(!(g instanceof Graphics2D) || old == null) return;
		((Graphics2D) g).setTransform(old); old = null;
	}
}
