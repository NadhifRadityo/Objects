package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager.CustomGraphicModifier;

public class Translate extends CustomGraphicModifier {
	protected double tx, ty;
	
	public Translate(double tx, double ty) {
		this.tx = tx;
		this.ty = ty;
	}
	
	public double getTx() { return tx; }
	public double getTy() { return ty; }
	public void setTx(double tx) { this.tx = tx; }
	public void setTy(double ty) { this.ty = ty; }
	
	private AffineTransform old;
	@Override public void draw(Graphics2D g) {
		old = g.getTransform();
		((Graphics2D) g).translate(tx, ty);
	}
	@Override public void reset(Graphics2D g) { g.setTransform(old); }
}
