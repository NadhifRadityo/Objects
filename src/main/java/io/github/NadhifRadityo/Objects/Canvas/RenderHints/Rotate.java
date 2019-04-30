package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import io.github.NadhifRadityo.Objects.Canvas.CustomGraphicModifier;

public class Rotate extends CustomGraphicModifier {
	protected double degree;
	
	public Rotate(double degree, boolean applyToGraphic) {
		super(applyToGraphic);
		this.degree = degree;
	} public Rotate(double degree) { this(degree, true); }
	
	public double getDegree() { return degree; }
	public void setDegree(double degree) { this.degree = degree; }

	private AffineTransform old;
	@Override
	public void draw(Graphics2D g) {
		old = g.getTransform();
		((Graphics2D) g).rotate(Math.toRadians(degree));
	}
	@Override
	public void reset(Graphics2D g) { g.setTransform(old); }
}
