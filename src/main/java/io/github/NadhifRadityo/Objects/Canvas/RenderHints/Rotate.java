package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import io.github.NadhifRadityo.Objects.Canvas.Sprite;
import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager.CustomGraphicModifier;

public class Rotate extends CustomGraphicModifier {
	protected double degree;
	
	public Rotate(double degree, Sprite... sprites) { super(sprites); this.degree = degree; }
	
	public double getDegree() { return degree; }
	public void setDegree(double degree) { this.degree = degree; }

	private AffineTransform old;
	@Override public void draw(Graphics g) {
		if(!(g instanceof Graphics2D)) return;
		old = ((Graphics2D) g).getTransform();
		((Graphics2D) g).rotate(Math.toRadians(degree));
	}
	@Override public void reset(Graphics g) {
		if(!(g instanceof Graphics2D) || old == null) return;
		((Graphics2D) g).setTransform(old); old = null;
	}
}
