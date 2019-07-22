package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager.CustomGraphicModifier;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Translate extends CustomGraphicModifier {
	protected double tx, ty;
	
	public Translate(double tx, double ty, Sprite... sprites) {
		super(sprites); this.tx = tx; this.ty = ty;
	}
	
	public double getTx() { return tx; }
	public double getTy() { return ty; }
	public void setTx(double tx) { this.tx = tx; }
	public void setTy(double ty) { this.ty = ty; }
	
	private AffineTransform old;
	@Override public void draw(Graphics g) {
		if(!(g instanceof Graphics2D)) return;
		old = ((Graphics2D) g).getTransform();
		((Graphics2D) g).translate(tx, ty);
	}
	@Override public void reset(Graphics g) {
		if(!(g instanceof Graphics2D) || old == null) return;
		((Graphics2D) g).setTransform(old); old = null;
	}
}
