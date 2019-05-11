package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import io.github.NadhifRadityo.Objects.Canvas.CanvasPanel.OverrideGraphic;
import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager.CustomGraphicModifier;

public class FillSprite extends CustomGraphicModifier implements OverrideGraphic {
	protected boolean enabled;
	
	public FillSprite(boolean enabled) { this.enabled = enabled; }

	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; }

	protected Graphics2D g;
	@Override public void draw(Graphics2D g) { this.g = g; }
	@Override public void reset(Graphics2D g) { this.g = null; }
	@Override public Graphics getGraphics() {
		if(g == null) return null;
		Graphics2D g = this.g;
		return new Graphics2D() {
			@Override public void setXORMode(Color c1) { g.setXORMode(c1); }
			@Override public void setPaintMode() { g.setPaintMode(); }
			@Override public void setFont(Font font) { g.setFont(font); }
			@Override public void setColor(Color c) { g.setColor(c); }
			@Override public void setClip(int x, int y, int width, int height) { g.setClip(x, y, width, height); }
			@Override public void setClip(Shape clip) { g.setClip(clip); }
			@Override public FontMetrics getFontMetrics(Font f) { return g.getFontMetrics(f); }
			@Override public Font getFont() { return g.getFont(); }
			@Override public Color getColor() { return g.getColor(); }
			@Override public Rectangle getClipBounds() { return g.getClipBounds(); }
			@Override public Shape getClip() { return g.getClip(); }
			/* MODIFIED */ @Override public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) { if(!enabled) g.drawRoundRect(x, y, width, height, arcWidth, arcHeight); else g.fillRoundRect(x, y, width, height, arcWidth, arcHeight); }
			/* MODIFIED */ @Override public void fillRect(int x, int y, int width, int height) { if(!enabled) g.drawRect(x, y, width, height); else g.fillRect(x, y, width, height); }
			/* MODIFIED */ @Override public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) { if(!enabled) g.drawPolygon(xPoints, yPoints, nPoints); else g.fillPolygon(xPoints, yPoints, nPoints); }
			/* MODIFIED */ @Override public void fillOval(int x, int y, int width, int height) { if(!enabled) g.drawOval(x, y, width, height); else g.fillOval(x, y, width, height); }
			/* MODIFIED */ @Override public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) { if(!enabled) g.drawArc(x, y, width, height, startAngle, arcAngle); else g.fillArc(x, y, width, height, startAngle, arcAngle); }
			/* MODIFIED */ @Override public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) { if(enabled) g.fillRoundRect(x, y, width, height, arcWidth, arcHeight); else g.drawRoundRect(x, y, width, height, arcWidth, arcHeight); }
			/* MODIFIED */ @Override public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) { if(enabled) g.fillPolygon(xPoints, yPoints, nPoints); else g.drawPolyline(xPoints, yPoints, nPoints); }
			/* MODIFIED */ @Override public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) { if(enabled) g.fillPolygon(xPoints, yPoints, nPoints); else g.drawPolygon(xPoints, yPoints, nPoints); }
			/* MODIFIED */ @Override public void drawOval(int x, int y, int width, int height) { if(enabled) g.fillOval(x, y, width, height); else g.drawOval(x, y, width, height); }
			/* --MODIFIED-- */ @Override public void drawLine(int x1, int y1, int x2, int y2) { g.drawLine(x1, y1, x2, y2); }
			@Override public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
					Color bgcolor, ImageObserver observer) { return g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer); }
			@Override public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
					ImageObserver observer) { return g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer); }
			@Override public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
				return g.drawImage(img, x, y, width, height, bgcolor, observer); }
			@Override public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
				return g.drawImage(img, x, y, width, height, observer); }
			@Override public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
				return g.drawImage(img, x, y, bgcolor, observer); }
			@Override public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
				return g.drawImage(img, x, y, observer); }
			/* MODIFIED */ @Override public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) { if(enabled) g.fillArc(x, y, width, height, startAngle, arcAngle); else g.drawArc(x, y, width, height, startAngle, arcAngle); }
			@Override public void dispose() { g.dispose(); }
			@Override public Graphics create() { return g.create(); }
			@Override public void copyArea(int x, int y, int width, int height, int dx, int dy) { g.copyArea(x, y, width, height, dx, dy); }
			@Override public void clipRect(int x, int y, int width, int height) { g.clipRect(x, y, width, height); }
			@Override public void clearRect(int x, int y, int width, int height) { g.clearRect(x, y, width, height); }
			@Override public void translate(double tx, double ty) { g.translate(tx, ty); }
			@Override public void translate(int x, int y) { g.translate(x, y); }
			@Override public void transform(AffineTransform Tx) { g.transform(Tx); }
			@Override public void shear(double shx, double shy) { g.shear(shx, shy); }
			@Override public void setTransform(AffineTransform Tx) { g.setTransform(Tx); }
			@Override public void setStroke(Stroke s) { g.setStroke(s); }
			@Override public void setRenderingHints(Map<?, ?> hints) { g.setRenderingHints(hints); }
			@Override public void setRenderingHint(Key hintKey, Object hintValue) { g.setRenderingHint(hintKey, hintValue); }
			@Override public void setPaint(Paint paint) { g.setPaint(paint); }
			@Override public void setComposite(Composite comp) { g.setComposite(comp); }
			@Override public void setBackground(Color color) { g.setBackground(color); }
			@Override public void scale(double sx, double sy) { g.scale(sx, sy); }
			@Override public void rotate(double theta, double x, double y) { g.rotate(theta, x, y); }
			@Override public void rotate(double theta) { g.rotate(theta); }
			@Override public boolean hit(Rectangle rect, Shape s, boolean onStroke) { return g.hit(rect, s, onStroke); }
			@Override public AffineTransform getTransform() { return g.getTransform(); }
			@Override public Stroke getStroke() { return g.getStroke(); }
			@Override public RenderingHints getRenderingHints() { return g.getRenderingHints(); }
			@Override public Object getRenderingHint(Key hintKey) { return g.getRenderingHint(hintKey); }
			@Override public Paint getPaint() { return g.getPaint(); }
			@Override public FontRenderContext getFontRenderContext() { return g.getFontRenderContext(); }
			@Override public GraphicsConfiguration getDeviceConfiguration() { return g.getDeviceConfiguration(); }
			@Override public Composite getComposite() { return g.getComposite(); }
			@Override public Color getBackground() { return g.getBackground(); }
			/* MODIFIED */ @Override public void fill(Shape s) { if(!enabled) g.draw(s); else g.fill(s); }
			@Override public void drawString(AttributedCharacterIterator iterator, float x, float y) { g.drawString(iterator, x, y); }
			@Override public void drawString(AttributedCharacterIterator iterator, int x, int y) { g.drawString(iterator, x, y); }
			@Override public void drawString(String str, float x, float y) { g.drawString(str, x, y); }
			@Override public void drawString(String str, int x, int y) { g.drawString(str, x, y); }
			@Override public void drawRenderedImage(RenderedImage img, AffineTransform xform) { g.drawRenderedImage(img, xform); }
			@Override public void drawRenderableImage(RenderableImage img, AffineTransform xform) { g.drawRenderableImage(img, xform); }
			@Override public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) { g.drawImage(img, op, x, y); }
			@Override public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) { return g.drawImage(img, xform, obs); }
			@Override public void drawGlyphVector(GlyphVector g, float x, float y) { FillSprite.this.g.drawGlyphVector(g, x, y); }
			/* MODIFIED */ @Override public void draw(Shape s) { if(enabled) g.fill(s); else g.draw(s); }
			@Override public void clip(Shape s) { g.clip(s); }
			@Override public void addRenderingHints(Map<?, ?> hints) { }
			@Override public Graphics create(int x, int y, int width, int height) { return g.create(x, y, width, height); }
			/* MODIFIED */ @Override public void draw3DRect(int x, int y, int width, int height, boolean raised) { if(enabled) g.fill3DRect(x, y, width, height, raised); else g.draw3DRect(x, y, width, height, raised); }
			@Override public void drawBytes(byte[] data, int offset, int length, int x, int y) { g.drawBytes(data, offset, length, x, y); }
			@Override public void drawChars(char[] data, int offset, int length, int x, int y) { g.drawChars(data, offset, length, x, y); }
			/* MODIFIED */ @Override public void drawRect(int x, int y, int width, int height) { if(enabled) g.fillRect(x, y, width, height); else g.drawRect(x, y, width, height); }
			/* MODIFIED */ @Override public void drawPolygon(Polygon p) { if(enabled) g.fillPolygon(p); else g.drawPolygon(p); }
			/* MODIFIED */ @Override public void fill3DRect(int x, int y, int width, int height, boolean raised) { if(!enabled) g.draw3DRect(x, y, width, height, raised); else g.fill3DRect(x, y, width, height, raised); }
			/* MODIFIED */ @Override public void fillPolygon(Polygon p) { if(!enabled) g.drawPolygon(p); else g.fillPolygon(p); }
			@Override public void finalize() { g.finalize(); }
			@Override public Rectangle getClipBounds(Rectangle r) { return g.getClipBounds(); }
			@SuppressWarnings("deprecation")
			@Override public Rectangle getClipRect() { return g.getClipRect(); }
			@Override public FontMetrics getFontMetrics() { return g.getFontMetrics(); }
			@Override public boolean hitClip(int x, int y, int width, int height) { return g.hitClip(x, y, width, height); }
		};
	}
}
