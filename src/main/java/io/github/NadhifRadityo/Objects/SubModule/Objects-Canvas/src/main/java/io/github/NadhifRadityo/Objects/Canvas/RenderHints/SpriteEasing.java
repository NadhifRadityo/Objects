package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import io.github.NadhifRadityo.Objects.Canvas.CanvasPanel;
import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager;
import io.github.NadhifRadityo.Objects.Canvas.Managers.ImplementSpriteManager;
import io.github.NadhifRadityo.Objects.Utilizations.Easing.Easing;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteEasing extends GraphicModifierManager.CustomGraphicModifier implements GraphicModifierManager.OverrideGraphic, CanvasPanel.SpriteChecker {
	protected final Map<Sprite, RunningEase> runningEase;
	protected final EaseRenderer easeRenderer;
	protected Easing easing;
	protected long time;
	
	public SpriteEasing(long time, Easing easing, Sprite... sprites) {
		super(sprites);
		this.runningEase = new HashMap<>();
		this.easeRenderer = new EaseRenderer(null);
		this.easing = easing;
		this.time = time;
	}

	public Easing getEasing() { return easing; }
	public long getTime() { return time; }
	public void setEasing(Easing easing) { this.easing = easing; }
	public void setTime(long time) { this.time = time; }
	
	private Sprite sprite;
	private Graphics2D g;
	@Override public void draw(Graphics g) { if(!(g instanceof Graphics2D)) return; this.g = (Graphics2D) g; }
	@Override public void reset(Graphics g) { this.g = null; }
	@Override public Graphics beforeDraw(Sprite sprite, Graphics graphic) { this.sprite = sprite; return null; }
	@Override public Graphics afterDraw(Sprite sprite, Graphics graphic) { this.sprite = null; return null; }

	@Override public Graphics getGraphics() {
		if(sprite == null || g == null) return null;
		return new Graphics2D() { /* TODO */
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
			@Override public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) { g.fillRoundRect(x, y, width, height, arcWidth, arcHeight); }
			@Override public void fillRect(int x, int y, int width, int height) { g.fillRect(x, y, width, height); }
			@Override public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) { g.fillPolygon(xPoints, yPoints, nPoints); }
			@Override public void fillOval(int x, int y, int width, int height) { g.fillOval(x, y, width, height); }
			@Override public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) { g.fillArc(x, y, width, height, startAngle, arcAngle); }
			@Override public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) { g.drawRoundRect(x, y, width, height, arcWidth, arcHeight); }
			@Override public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) { g.drawPolyline(xPoints, yPoints, nPoints); }
			@Override public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) { g.drawPolygon(xPoints, yPoints, nPoints); }
			@Override public void drawOval(int x, int y, int width, int height) { g.drawOval(x, y, width, height); }
			@Override public void drawLine(int x1, int y1, int x2, int y2) { g.drawLine(x1, y1, x2, y2); }
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
			@Override public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) { g.drawArc(x, y, width, height, startAngle, arcAngle); }
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
			@Override public void fill(Shape s) { g.fill(s); }
			@Override public void drawString(AttributedCharacterIterator iterator, float x, float y) { g.drawString(iterator, x, y); }
			@Override public void drawString(AttributedCharacterIterator iterator, int x, int y) { g.drawString(iterator, x, y); }
			@Override public void drawString(String str, float x, float y) { g.drawString(str, x, y); }
			@Override public void drawString(String str, int x, int y) { g.drawString(str, x, y); }
			@Override public void drawRenderedImage(RenderedImage img, AffineTransform xform) { g.drawRenderedImage(img, xform); }
			@Override public void drawRenderableImage(RenderableImage img, AffineTransform xform) { g.drawRenderableImage(img, xform); }
			@Override public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) { g.drawImage(img, op, x, y); }
			@Override public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) { return g.drawImage(img, xform, obs); }
			@Override public void drawGlyphVector(GlyphVector gv, float x, float y) { g.drawGlyphVector(gv, x, y); }
			@Override public void draw(Shape s) { g.draw(s); }
			@Override public void clip(Shape s) { g.clip(s); }
			@Override public void addRenderingHints(Map<?, ?> hints) { }
			@Override public Graphics create(int x, int y, int width, int height) { return g.create(x, y, width, height); }
			@Override public void draw3DRect(int x, int y, int width, int height, boolean raised) { g.draw3DRect(x, y, width, height, raised); }
			@Override public void drawBytes(byte[] data, int offset, int length, int x, int y) { g.drawBytes(data, offset, length, x, y); }
			@Override public void drawChars(char[] data, int offset, int length, int x, int y) { g.drawChars(data, offset, length, x, y); }
			@Override public void drawRect(int x, int y, int width, int height) { g.drawRect(x, y, width, height); }
			@Override public void drawPolygon(Polygon p) { g.drawPolygon(p); }
			@Override public void fill3DRect(int x, int y, int width, int height, boolean raised) { g.fill3DRect(x, y, width, height, raised); }
			@Override public void fillPolygon(Polygon p) { g.fillPolygon(p); }
//			@SuppressWarnings("deprecation") // For Java >= 9
			@Override public void finalize() { g.finalize(); }
			@Override public Rectangle getClipBounds(Rectangle r) { return g.getClipBounds(); }
			@SuppressWarnings("deprecation")
			@Override public Rectangle getClipRect() { return g.getClipRect(); }
			@Override public FontMetrics getFontMetrics() { return g.getFontMetrics(); }
			@Override public boolean hitClip(int x, int y, int width, int height) { return g.hitClip(x, y, width, height); }
		};
	}
	
	protected void createEaseInstance(Sprite sprite, float beginningVal, float targetVal, RenderStepper callback) {
		runningEase.put(sprite, new RunningEase(System.currentTimeMillis(), sprite, 
				beginningVal, targetVal, callback, RunningEase.PROGRESS_ANIMATION));
	}
	protected void createWaitEaseInstance(Sprite sprite, RenderStepper callback) {
		runningEase.put(sprite, new RunningEase(System.currentTimeMillis(), sprite, 
				0, 1, callback, RunningEase.WAIT_UNTIL_FINSIHED));
	}
	
	protected class RunningEase {
		protected final long start;
		protected final Sprite sprite;
		protected final float beginningVal;
		protected final float targetVal;
		protected final RenderStepper wish;
		protected final int type;
		protected boolean finished = false;
		
		public static final int PROGRESS_ANIMATION = 0;
		public static final int WAIT_UNTIL_FINSIHED = 1;
		
		public RunningEase(long start, Sprite sprite, float beginningVal, float targetVal, RenderStepper wish, int type) {
			this.start = start;
			this.sprite = sprite;
			this.beginningVal = beginningVal;
			this.targetVal = targetVal;
			this.wish = wish;
			this.type = type;
		}
		
		public long getStart() { return start; }
		public Sprite getSprite() { return sprite; }
		public float getBeginningVal() { return beginningVal; }
		public float getTargetVal() { return targetVal; }
		public int getType() { return type; }
		public boolean isFinished() { return finished; }
		public boolean isExpired(long millis) { return finished || millis > start + time; }
		
		public void update(long millis) {
			float interpolate = targetVal;
			if(!isExpired(millis))
				interpolate = (float) easing.ease(millis, beginningVal, start + time, targetVal);
			if(interpolate >= targetVal) finished = true;
			if(type == PROGRESS_ANIMATION) wish.render(interpolate);
			if(type == WAIT_UNTIL_FINSIHED && interpolate >= targetVal) wish.render(targetVal);
		}
	}
	public interface RenderStepper { void render(float interpolate); }
	
	protected class EaseRenderer extends Sprite {
		protected ImplementSpriteManager manager;
		
		public EaseRenderer(ImplementSpriteManager manager) {
			super(0, 0);
			this.manager = manager;
		}
		
		public ImplementSpriteManager getManager() { return manager; }
		@Override public void draw(Graphics g) {
			List<RunningEase> removeRunningEase = new ArrayList<>();
			for(RunningEase running : runningEase.values()) {
				if(running.isFinished()) { removeRunningEase.add(running); continue; }
				running.update(System.currentTimeMillis());
			}
		} @Override public Area getArea() { return null; }
	}
}
