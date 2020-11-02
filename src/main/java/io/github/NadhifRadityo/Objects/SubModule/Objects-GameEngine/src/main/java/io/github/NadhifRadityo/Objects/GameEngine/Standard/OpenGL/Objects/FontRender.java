package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVec4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.TempVec;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec1;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Combiner.ContourCombiner;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Combiner.OverlappingContourCombiner;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.ContourShape;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.ScanLine;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeColor;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.MultiAndTrueDistanceSelector;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.MultiDistanceSelector;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.PseudoDistanceSelector;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.TrueDistanceSelector;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Program;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Texture;
import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Thread.Handler;
import io.github.NadhifRadityo.Objects.Thread.HandlersManager;
import io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting.Sorting;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ClassUtils;
import io.github.NadhifRadityo.Objects.Utilizations.Direction.Direction2D;
import io.github.NadhifRadityo.Objects.Utilizations.DistanceFieldUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.FontUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ImageUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ListUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferShort;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.vec2;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.vec3;

@SuppressWarnings({"FieldCanBeLocal", "unused", "UnusedReturnValue", "deprecation", "jol", "DuplicatedCode"})
public class FontRender extends OpenGLObjectHolder {
	protected static final int MAX_GLYPH_SIZE = 16;
	protected static BufferedImage scratchImage = ImageUtils.newShortBufferedImage(MAX_GLYPH_SIZE, MAX_GLYPH_SIZE);
	protected static Graphics2D scratchGraphics = (Graphics2D) scratchImage.getGraphics();
	static {
		scratchGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		scratchGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}

	protected final Size size;
	protected final SVec4 padding;
	protected final Size maxGlyphSize;
	protected final Texture<? extends Number> texture;
	protected final PriorityList<GlyphPage.Effect> effects;
	protected final HashMap<Integer, GlyphChar> characters;

	protected Font font;
	protected FontMetrics fontMetrics;
	protected String glyphs;
	protected GlyphPage glyphPage;
	protected Handler drawHandler;
	protected HandlersManager handlersManager;

	public FontRender(GLContext<? extends Number> gl, Size size, SVec4 padding, Size maxGlyphSize) {
		super(gl);
		this.size = size;
		this.padding = padding;
		this.maxGlyphSize = maxGlyphSize;
		this.texture = getGL().constructTexture(getGL().GL_TEXTURE_2D());
		this.effects = Pool.tryBorrow(Pool.getPool(PriorityList.class));
		this.characters = Pool.tryBorrow(Pool.getPool(HashMap.class));
	}

	public Size getSize() { return size; }
	public SVec4 getPadding() { return padding; }
	public Size getMaxGlyphSize() { return maxGlyphSize; }
	public Texture<? extends Number> getTexture() { assertNotDead(); assertCreated(); return texture; }
	public GlyphPage.Effect[] getEffects() { assertNotDead(); return effects.get().toArray(new GlyphPage.Effect[0]); }
	public Font getFont() { return font; }
	public String getGlyphs() { return glyphs; }
	public Handler getDrawHandler() { return drawHandler; }
	public Handler getHandlersManager() { return handlersManager; }

	public void setFont(Font font) { this.font = font; this.fontMetrics = scratchGraphics.getFontMetrics(font); }
	public void setGlyphs(String glyphs) { this.glyphs = glyphs; }
	public void setHandler(Handler drawHandler, HandlersManager handlersManager) {
		if(handlersManager != null && drawHandler == null) newException("Draw handler must be provided");
		this.drawHandler = drawHandler; this.handlersManager = handlersManager;
	}

	public void addEffect(GlyphPage.Effect effect, int priority) { assertNotDead(); effects.add(effect, priority); }
	public void addEffect(GlyphPage.Effect effect) { assertNotDead(); addEffect(effect, 0); }
	public void removeEffect(GlyphPage.Effect effect) { assertNotDead(); effects.remove(effect); }
	public GlyphText createNewText(Program<? extends Number> program, TempVec temp) {
		assertNotDead(); assertCreated(); assertContextSame(program);
		return new GlyphText(program, temp);
	}

	@SuppressWarnings({"SynchronizeOnNonFinalField"})
	public void updateFont() {
		assertNotDead(); assertCreated();
		ReferencedCallback<GlyphPage> createGlyphPage = (args) -> {
			GlyphPage newGlyphPage = new GlyphPage(font, size, true, true);
			Map<Integer, GlyphChar> characters = new HashMap<>();
			if(!newGlyphPage.addGlyph(glyphs, effects, characters, padding, maxGlyphSize)) return null;
			this.characters.clear(); this.characters.putAll(characters);
			if(glyphPage != null && !glyphPage.isDead()) { glyphPage.stopAdding(); glyphPage.stopRendering();
				synchronized(glyphPage) { while(glyphPage.isAdding()) ExceptionUtils.doSilentThrowsRunnable(false, glyphPage::wait); }
			} return newGlyphPage;
		};
		GlyphPage newGlyphPage = createGlyphPage.get();
		if(newGlyphPage == null) { System.out.println("Glyph page too small."); return; } glyphPage = newGlyphPage;
		if(handlersManager == null) {
			boolean textureBind = texture.isBind();
			if(!textureBind) texture.bind(); texture.clear();
			BufferedImage scratchImage = ImageUtils.newShortBufferedImage(maxGlyphSize.getWidth(), maxGlyphSize.getHeight());
			newGlyphPage.render(texture, scratchImage, effects, getGL().GL_BGRA());
			if(!textureBind) texture.unbind();
			newGlyphPage.getOnFinished().forEach(Runnable::run);
			newGlyphPage.setDead(); newGlyphPage.getLost(); return;
		}
		handlersManager.post(new Runnable() {
			boolean clearing = false;
			final Runnable self = this;
			@Override public synchronized void run() {
				clearing = true; drawHandler.post(() -> { synchronized(self) {
					boolean textureBind = texture.isBind();
					if(!textureBind) texture.bind(); texture.clear();
					if(!textureBind) texture.unbind(); clearing = false;
					self.notifyAll();
				} });
				while(clearing) ExceptionUtils.doSilentThrowsRunnable(false, this::wait);
				newGlyphPage.render(texture, effects, drawHandler, handlersManager, maxGlyphSize, handlersManager.getMaxSize(), getGL().GL_BGRA());
				handlersManager.post(new Runnable() { @Override public void run() {
					if(newGlyphPage.isDead()) return;
					for(GlyphLineList glyphLine : newGlyphPage.getGlyphLines())
						if(glyphLine.isRendering()) { handlersManager.postDelayed(this, 100); return; }
					newGlyphPage.getOnFinished().forEach(handlersManager::post); newGlyphPage.setDead(); newGlyphPage.getLost(); System.gc();
				} });
			}
		});
	}
	public synchronized void stopRender(Runnable onFinished) {
		assertNotDead(); assertCreated();
		if(glyphPage == null || glyphPage.isDead()) return;
		glyphPage.stopAdding(); glyphPage.stopRendering();
		if(onFinished != null) glyphPage.getOnFinished().add(onFinished);
	}
	public synchronized void addOnFinished(Runnable onFinished) {
		assertNotDead(); assertCreated();
		if(glyphPage == null || glyphPage.isDead()) return;
		if(onFinished != null) glyphPage.getOnFinished().add(onFinished);
	}

	@SuppressWarnings("SynchronizeOnNonFinalField")
	public void setEffect(BuiltInEffect effect) { assertNotDead();
		if(effect == null) effect = BuiltInEffect.DEFAULT_ANTIALIAS; if(glyphPage != null)
			synchronized(glyphPage) { while(glyphPage.isAdding()) ExceptionUtils.doSilentThrowsRunnable(false, glyphPage::wait); }
		effects.clear(); switch(effect) {
			case DEFAULT_ANTIALIAS: {
				addEffect((image, graphics, raster, shape, x, y, w, h, codePoint) -> {
					graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				}, 1);
			}
			case DEFAULT: {
				addEffect((image, graphics, raster, shape, x, y, w, h, glyphChar) -> {
					Rectangle bounds = glyphChar.getDrawBounds();
					SVec4 padding = glyphChar.getPadding();
					x += -bounds.x + padding.x();
					y += -bounds.y + padding.y();
					graphics.setColor(Color.WHITE);
					graphics.fill(AffineTransform.getTranslateInstance(x, y).createTransformedShape(shape));
				}); break;
			}
			case DISTANCE_FIELD_SINGLE_TRUE: {
				addEffect(new GlyphPage.Effect() {
					@Override public void resize(Rectangle bounds, Shape shape, int w, int h, GlyphChar glyphChar) {
						Rectangle drawBounds = glyphChar.getDrawBounds();
						double widthRatio = w / drawBounds.getWidth();
						double heightRatio = h / drawBounds.getHeight();
						double ratio = Math.min(widthRatio, heightRatio);
						bounds.width = (int) (drawBounds.width * ratio); bounds.height = (int) (drawBounds.height * ratio);
					}
					@Override public void draw(BufferedImage image, Graphics2D graphics, short[] raster, Shape shape, int sx, int sy, int w, int h, GlyphChar glyphChar) {
						int rw = image.getWidth();
						int rh = image.getHeight();
						int stride = image.getSampleModel().getNumBands();
						int start = sy * rw + sx;

						Rectangle bounds = glyphChar.getBounds();
						ContourShape contourShape = ContourShape.convertFromShape(shape);
						Vec2 scale = new Vec2(1); Vec2 translate = new Vec2(); Vec1 range = new Vec1(1);
						DistanceFieldUtils.autoFrame(contourShape.getBounds(0, 0, 0), w, h, scale, translate, range);
						EdgeColor.edgeColoringSimple(contourShape, 3.0, 0);

						final ReferencedCallback.PVoidReferencedCallback output = (args) -> {
							int i = (int) args[0]; Vec1 distance = (Vec1) args[1];
							int ix = i % w; int iy = i / w;
							int off = stride * (start + (iy * rw + ix));
							raster[off    ] = (short) ((args.length < 3 ? (float) (distance.x() / range.x() + .5) : (float) distance.x()) * 256);
						};
						final ReferencedCallback.PVoidReferencedCallback input = (args) -> {
							int i = (int) args[0]; Vec1 distance = (Vec1) args[1];
							int ix = i % w; int iy = i / w;
							int off = stride * (start + (iy * rw + ix));
							distance.x((double) raster[off    ] / 256);
						};
						ContourCombiner<TrueDistanceSelector, Vec1> combiner = new OverlappingContourCombiner<>(contourShape, new TrueDistanceSelector());
						DistanceFieldUtils.generateDistanceField(output, w, h, contourShape, scale, translate, combiner);
						DistanceFieldUtils.distanceSignCorrection(output, input, w, h, contourShape, scale, translate, ScanLine.FillRule.FILL_NONZERO, combiner);
						ImageUtils.clampArray(raster, (short) 0, (short) 255, sx, sy, rw, rh, w, h, stride);
						ImageUtils.scaleBands(raster, (double) Short.MAX_VALUE / 255, sx, sy, rw, rh, w, h, stride);
						ImageUtils.flipImage(raster, sx, sy, rw, rh, w, h, stride);
						ImageUtils.cutImage(raster, sx + w / 2 - bounds.width / 2, sy + h / 2 - bounds.height / 2, rw, rh, raster, sx, sy, rw, rh, bounds.width, bounds.height, stride, ArrayUtils.getEmptyShortArray(-1));
						ImageUtils.clearPixels(raster, sx + bounds.width, sy, rw, rh, w - bounds.width, h, stride);
						ImageUtils.clearPixels(raster, sx, sy + bounds.height, rw, rh, w, h - bounds.height, stride);
					}
				}); break;
			}
			case DISTANCE_FIELD_SINGLE_PSEUDO: {
				addEffect(new GlyphPage.Effect() {
					@Override public void resize(Rectangle bounds, Shape shape, int w, int h, GlyphChar glyphChar) {
						Rectangle drawBounds = glyphChar.getDrawBounds();
						double widthRatio = w / drawBounds.getWidth();
						double heightRatio = h / drawBounds.getHeight();
						double ratio = Math.min(widthRatio, heightRatio);
						bounds.width = (int) (drawBounds.width * ratio); bounds.height = (int) (drawBounds.height * ratio);
					}
					@Override public void draw(BufferedImage image, Graphics2D graphics, short[] raster, Shape shape, int sx, int sy, int w, int h, GlyphChar glyphChar) {
						int rw = image.getWidth();
						int rh = image.getHeight();
						int stride = image.getSampleModel().getNumBands();
						int start = sy * rw + sx;

						Rectangle bounds = glyphChar.getBounds();
						ContourShape contourShape = ContourShape.convertFromShape(shape);
						Vec2 scale = new Vec2(1); Vec2 translate = new Vec2(); Vec1 range = new Vec1(1);
						DistanceFieldUtils.autoFrame(contourShape.getBounds(0, 0, 0), w, h, scale, translate, range);
						EdgeColor.edgeColoringSimple(contourShape, 3.0, 0);

						final ReferencedCallback.PVoidReferencedCallback output = (args) -> {
							int i = (int) args[0]; Vec1 distance = (Vec1) args[1];
							int ix = i % w; int iy = i / w;
							int off = stride * (start + (iy * rw + ix));
							raster[off    ] = (short) ((args.length < 3 ? (float) (distance.x() / range.x() + .5) : (float) distance.x()) * 256);
						};
						final ReferencedCallback.PVoidReferencedCallback input = (args) -> {
							int i = (int) args[0]; Vec1 distance = (Vec1) args[1];
							int ix = i % w; int iy = i / w;
							int off = stride * (start + (iy * rw + ix));
							distance.x((double) raster[off    ] / 256);
						};
						ContourCombiner<PseudoDistanceSelector, Vec1> combiner = new OverlappingContourCombiner<>(contourShape, new PseudoDistanceSelector());
						DistanceFieldUtils.generateDistanceField(output, w, h, contourShape, scale, translate, combiner);
						DistanceFieldUtils.distanceSignCorrection(output, input, w, h, contourShape, scale, translate, ScanLine.FillRule.FILL_NONZERO, combiner);
						ImageUtils.clampArray(raster, (short) 0, (short) 255, sx, sy, rw, rh, w, h, stride);
						ImageUtils.scaleBands(raster, (double) Short.MAX_VALUE / 255, sx, sy, rw, rh, w, h, stride);
						ImageUtils.flipImage(raster, sx, sy, rw, rh, w, h, stride);
						ImageUtils.cutImage(raster, sx + w / 2 - bounds.width / 2, sy + h / 2 - bounds.height / 2, rw, rh, raster, sx, sy, rw, rh, bounds.width, bounds.height, stride, ArrayUtils.getEmptyShortArray(-1));
						ImageUtils.clearPixels(raster, sx + bounds.width, sy, rw, rh, w - bounds.width, h, stride);
						ImageUtils.clearPixels(raster, sx, sy + bounds.height, rw, rh, w, h - bounds.height, stride);
					}
				}); break;
			}
			case DISTANCE_FIELD_MULTI_PSEUDO: {
				addEffect(new GlyphPage.Effect() {
					@Override public void resize(Rectangle bounds, Shape shape, int w, int h, GlyphChar glyphChar) {
						Rectangle drawBounds = glyphChar.getDrawBounds();
						double widthRatio = w / drawBounds.getWidth();
						double heightRatio = h / drawBounds.getHeight();
						double ratio = Math.min(widthRatio, heightRatio);
						bounds.width = (int) (drawBounds.width * ratio); bounds.height = (int) (drawBounds.height * ratio);
					}
					@Override public void draw(BufferedImage image, Graphics2D graphics, short[] raster, Shape shape, int sx, int sy, int w, int h, GlyphChar glyphChar) {
						int rw = image.getWidth();
						int rh = image.getHeight();
						int stride = image.getSampleModel().getNumBands();
						int start = sy * rw + sx;

						Rectangle bounds = glyphChar.getBounds();
						ContourShape contourShape = ContourShape.convertFromShape(shape);
						Vec2 scale = new Vec2(1); Vec2 translate = new Vec2(); Vec1 range = new Vec1(1);
						DistanceFieldUtils.autoFrame(contourShape.getBounds(0, 0, 0), w, h, scale, translate, range);
						EdgeColor.edgeColoringSimple(contourShape, 3.0, 0);

						final ReferencedCallback.PVoidReferencedCallback output = (args) -> {
							int i = (int) args[0]; Vec3 distance = (Vec3) args[1];
							int ix = i % w; int iy = i / w;
							int off = stride * (start + (iy * rw + ix));
							raster[off    ] = (short) ((args.length < 3 ? (float) (distance.x() / range.x() + .5) : (float) distance.x()) * 256);
							raster[off + 1] = (short) ((args.length < 3 ? (float) (distance.y() / range.x() + .5) : (float) distance.y()) * 256);
							raster[off + 2] = (short) ((args.length < 3 ? (float) (distance.z() / range.x() + .5) : (float) distance.z()) * 256);
						};
						final ReferencedCallback.PVoidReferencedCallback input = (args) -> {
							int i = (int) args[0]; Vec3 distance = (Vec3) args[1];
							int ix = i % w; int iy = i / w;
							int off = stride * (start + (iy * rw + ix));
							distance.x((double) raster[off    ] / 256);
							distance.y((double) raster[off + 1] / 256);
							distance.z((double) raster[off + 2] / 256);
						};
						ContourCombiner<MultiDistanceSelector, Vec3> combiner = new OverlappingContourCombiner<>(contourShape, new MultiDistanceSelector());
						DistanceFieldUtils.generateDistanceField(output, w, h, contourShape, scale, translate, combiner);
						DistanceFieldUtils.multiDistanceSignCorrection(output, input, w, h, contourShape, scale, translate, ScanLine.FillRule.FILL_NONZERO, combiner);
						DistanceFieldUtils.msdfErrorCorrection(output, input, w, h, vec2(1.001 / (scale.x() * range.x()), 1.001 / (scale.y() * range.x())), combiner);
						ImageUtils.clampArray(raster, (short) 0, (short) 255, sx, sy, rw, rh, w, h, stride);
						ImageUtils.scaleBands(raster, (double) Short.MAX_VALUE / 255, sx, sy, rw, rh, w, h, stride);
						ImageUtils.flipImage(raster, sx, sy, rw, rh, w, h, stride);
						ImageUtils.cutImage(raster, sx + w / 2 - bounds.width / 2, sy + h / 2 - bounds.height / 2, rw, rh, raster, sx, sy, rw, rh, bounds.width, bounds.height, stride, ArrayUtils.getEmptyShortArray(-1));
						ImageUtils.clearPixels(raster, sx + bounds.width, sy, rw, rh, w - bounds.width, h, stride);
						ImageUtils.clearPixels(raster, sx, sy + bounds.height, rw, rh, w, h - bounds.height, stride);
					}
				}); break;
			}
			case DISTANCE_FIELD_MULTI_TRUE_PSEUDO: {
				addEffect(new GlyphPage.Effect() {
					@Override public void resize(Rectangle bounds, Shape shape, int w, int h, GlyphChar glyphChar) {
						Rectangle drawBounds = glyphChar.getDrawBounds();
						double widthRatio = w / drawBounds.getWidth();
						double heightRatio = h / drawBounds.getHeight();
						double ratio = Math.min(widthRatio, heightRatio);
						bounds.width = (int) (drawBounds.width * ratio); bounds.height = (int) (drawBounds.height * ratio);
					}
					@Override public void draw(BufferedImage image, Graphics2D graphics, short[] raster, Shape shape, int sx, int sy, int w, int h, GlyphChar glyphChar) {
						int rw = image.getWidth();
						int rh = image.getHeight();
						int stride = image.getSampleModel().getNumBands();
						int start = sy * rw + sx;

						Rectangle bounds = glyphChar.getBounds();
						ContourShape contourShape = ContourShape.convertFromShape(shape);
						Vec2 scale = new Vec2(1); Vec2 translate = new Vec2(); Vec1 range = new Vec1(1);
						DistanceFieldUtils.autoFrame(contourShape.getBounds(0, 0, 0), w, h, scale, translate, range);
						EdgeColor.edgeColoringSimple(contourShape, 3.0, 0);

						final ReferencedCallback.PVoidReferencedCallback output = (args) -> {
							int i = (int) args[0]; Vec4 distance = (Vec4) args[1];
							int ix = i % w; int iy = i / w;
							int off = stride * (start + (iy * rw + ix));
							raster[off    ] = (short) ((args.length < 3 ? (float) (distance.x() / range.x() + .5) : (float) distance.x()) * 256);
							raster[off + 1] = (short) ((args.length < 3 ? (float) (distance.y() / range.x() + .5) : (float) distance.y()) * 256);
							raster[off + 2] = (short) ((args.length < 3 ? (float) (distance.z() / range.x() + .5) : (float) distance.z()) * 256);
							raster[off + 3] = (short) ((args.length < 3 ? (float) (distance.w() / range.x() + .5) : (float) distance.w()) * 256);
						};
						final ReferencedCallback.PVoidReferencedCallback input = (args) -> {
							int i = (int) args[0]; Vec4 distance = (Vec4) args[1];
							int ix = i % w; int iy = i / w;
							int off = stride * (start + (iy * rw + ix));
							distance.x((double) raster[off    ] / 256);
							distance.y((double) raster[off + 1] / 256);
							distance.z((double) raster[off + 2] / 256);
							distance.w((double) raster[off + 3] / 256);
						};
						ContourCombiner<MultiAndTrueDistanceSelector, Vec4> combiner = new OverlappingContourCombiner<>(contourShape, new MultiAndTrueDistanceSelector());
						DistanceFieldUtils.generateDistanceField(output, w, h, contourShape, scale, translate, combiner);
						DistanceFieldUtils.multiDistanceSignCorrection(output, input, w, h, contourShape, scale, translate, ScanLine.FillRule.FILL_NONZERO, combiner);
						DistanceFieldUtils.msdfErrorCorrection(output, input, w, h, vec2(1.001 / (scale.x() * range.x()), 1.001 / (scale.y() * range.x())), combiner);
						ImageUtils.clampArray(raster, (short) 0, (short) 255, sx, sy, rw, rh, w, h, stride);
						ImageUtils.scaleBands(raster, (double) Short.MAX_VALUE / 255, sx, sy, rw, rh, w, h, stride);
						ImageUtils.flipImage(raster, sx, sy, rw, rh, w, h, stride);
						ImageUtils.cutImage(raster, sx + w / 2 - bounds.width / 2, sy + h / 2 - bounds.height / 2, rw, rh, raster, sx, sy, rw, rh, bounds.width, bounds.height, stride, ArrayUtils.getEmptyShortArray(-1));
						ImageUtils.clearPixels(raster, sx + bounds.width, sy, rw, rh, w - bounds.width, h, stride);
						ImageUtils.clearPixels(raster, sx, sy + bounds.height, rw, rh, w, h - bounds.height, stride);
					}
				}); break;
			}
		}
	}

	@Override protected void arrange() {
		this.glyphs = EXTENDED_CHARS;
		setEffect(BuiltInEffect.DEFAULT_ANTIALIAS);

		texture.create();
		texture.bind();
		try(BufferUtils.MemoryStack stack = BufferUtils.stack(size.getWidth() * size.getHeight() * 4)) {
			texture.load(stack.allocateByteBuffer(size.getWidth() * size.getHeight() * 4), 0, getGL().GL_RGBA8(), size.getWidth(), size.getHeight(), 0, getGL().GL_RGBA(), getGL().GL_UNSIGNED_BYTE());
		}
		texture.setTextureFilter(getGL().GL_LINEAR());
		texture.unbind();
	}
	@Override protected void disarrange() {
		texture.destroy();
		Pool.returnObject(PriorityList.class, effects);
		Pool.returnObject(HashMap.class, characters);
	}

	public enum BuiltInEffect {
		DEFAULT,
		DEFAULT_ANTIALIAS,
		DISTANCE_FIELD_SINGLE_TRUE,
		DISTANCE_FIELD_SINGLE_PSEUDO,
		DISTANCE_FIELD_MULTI_PSEUDO,
		DISTANCE_FIELD_MULTI_TRUE_PSEUDO
	}
	public static class GlyphPage implements DeadableObject {
		protected final Font font;
		protected final Size size;
		protected final ArrayList<GlyphLineList> glyphLines;
		protected final ArrayList<Runnable> onFinished;
		protected final boolean horizontal;
		protected final boolean directionIncreasing;
		protected final boolean startBeginning;
		protected boolean adding;
		protected boolean stopAdding;
		protected boolean isDead;
		private final boolean meantHorizontal;

		public GlyphPage(Font font, Size size, boolean horizontal, boolean directionIncreasing, boolean startBeginning) {
			this.font = font;
			this.size = size;
			this.glyphLines = new ArrayList<>();
			this.onFinished = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			this.horizontal = horizontal;
			this.directionIncreasing = directionIncreasing;
			this.startBeginning = startBeginning;
			this.meantHorizontal = isMetricsHorizontal(FontUtils.getVector(font, scratchGraphics, "A").getGlyphMetrics(0));
		}
		public GlyphPage(Font font, Size size, boolean directionIncreasing, boolean startBeginning) {
			this(font, size, isMetricsHorizontal(FontUtils.getVector(font, scratchGraphics, "A").getGlyphMetrics(0)), directionIncreasing, startBeginning);
		}

		public Font getFont() { return font; }
		public Size getSize() { return size; }
		public GlyphLineList[] getGlyphLines() { return glyphLines.toArray(new GlyphLineList[0]); }
		public ArrayList<Runnable> getOnFinished() { return onFinished; }
		public boolean isHorizontal() { return horizontal; }
		public boolean isDirectionIncreasing() { return directionIncreasing; }
		public boolean isStartBeginning() { return startBeginning; }
		public boolean isAdding() { return adding; }
		public Direction2D getTextDirection() {
			if(horizontal) { if(directionIncreasing) return Direction2D.RIGHT; else return Direction2D.LEFT; }
			if(directionIncreasing) return Direction2D.BOTTOM; else return Direction2D.UP; // Who write character upwards?
		}

		public void stopRendering() { for(GlyphLineList glyphLine : glyphLines) glyphLine.stopRendering(); }
		public void stopAdding() { this.stopAdding = true; }
		@Override public void setDead() {
			isDead = true; for(GlyphLineList glyphLine : glyphLines) glyphLine.setDead();
			Pool.returnObject(ArrayList.class, onFinished);
		}
		@Override public boolean isDead() { return isDead; }

		protected boolean addGlyph(GlyphChar glyphChar) {
			// Make sure feeds this functions with biggest characters first to get optimized result.
			Rectangle charBounds = glyphChar.getBounds();
			for(GlyphLineList glyphLine : glyphLines) if(glyphLine.addGlyph(glyphChar)) return true;
			GlyphLineList lastLine = glyphLines.size() > 0 ? glyphLines.get(glyphLines.size() - 1) : null;
			int pos = startBeginning ? 0 : horizontal ? size.getHeight() : size.getWidth();
			if(lastLine != null && horizontal) pos = lastLine.getBounds().y + lastLine.getBounds().height * (startBeginning ? 1 : -1);
			if(lastLine != null && !horizontal) pos = lastLine.getBounds().x + lastLine.getBounds().width * (startBeginning ? 1 : -1);
			if(pos < 0 || (horizontal ? pos > size.getHeight() : pos > size.getWidth())) return false; /*throw new Error("Page too small");*/
			GlyphLineList newLine = new GlyphLineList(font, horizontal ? size.getWidth() : size.getHeight(), pos, horizontal, directionIncreasing, startBeginning, meantHorizontal);
			glyphLines.add(newLine); return newLine.addGlyph(glyphChar);
		}
		public synchronized boolean addGlyph(String string, PriorityList<Effect> effects, Map<Integer, GlyphChar> characterMap, SVec4 padding, Size maxGlyphSize) { try {
			synchronized(this) { while(isAdding()) ExceptionUtils.doSilentThrowsRunnable(false, this::wait); }
			adding = true; ArrayList<GlyphChar> glyphChars = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
			Effect[] effectsArray = effects.get().toArray(new Effect[0]);
			for(int i = 0; i < string.length(); i++) { if(stopAdding) return false;
				GlyphChar glyphChar = new GlyphChar(string.codePointAt(i), font, horizontal, meantHorizontal, padding);
				glyphChars.add(glyphChar); for(Effect effect : effectsArray) { if(stopAdding) return false;
					effect.resize(glyphChar.getBounds(), glyphChar.getShape(), maxGlyphSize.getWidth(), maxGlyphSize.getHeight(), glyphChar); }
				characterMap.put(glyphChar.getCodePoint(), glyphChar);
			} Sorting.HEAP_SORT.sort(glyphChars, (o1, o2) -> horizontal ? o2.getBounds().height - o1.getBounds().height : o2.getBounds().width - o1.getBounds().width);
			for(GlyphChar glyphChar : glyphChars) { if(!addGlyph(glyphChar) || stopAdding) return false; glyphChar.calculateTexBounds(size); } return true;
		} finally { Pool.returnObject(ArrayList.class, glyphChars); } } finally { adding = false; stopAdding = false; notifyAll(); } }
		public boolean addGlyph(int[] codePoints, PriorityList<Effect> effects, Map<Integer, GlyphChar> characterMap, SVec4 padding, Size maxGlyphSize) {
			StringBuilder builder = new StringBuilder();
			for(int codePoint : codePoints)
				builder.append(Character.toChars(codePoint));
			return addGlyph(builder.toString(), effects, characterMap, padding, maxGlyphSize);
		}

		public void render(BufferedImage image, BufferedImage scratchImage, PriorityList<Effect> effects) {
			for(GlyphLineList glyphLine : glyphLines) glyphLine.render(image, scratchImage, effects);
		}
		public void render(Texture<? extends Number> texture, BufferedImage scratchImage, PriorityList<Effect> effects, int format) {
			boolean textureBind = texture.isBind(); if(!textureBind) texture.bind();
			ByteBuffer byteBuffer = BufferUtils.createByteBuffer(scratchImage.getWidth() * scratchImage.getHeight() * scratchImage.getSampleModel().getNumBands());
			try { for(GlyphLineList glyphLine : glyphLines) glyphLine.render(texture, scratchImage, effects, byteBuffer, format);
			} finally { BufferUtils.deallocate(byteBuffer); if(!textureBind) texture.unbind(); }
		}
		public void render(Texture<? extends Number> texture, PriorityList<Effect> effects, Handler drawHandler, HandlersManager handlersManager, Size maxGlyphSize, int targetThread, int format) {
			for(GlyphLineList glyphLine : glyphLines) glyphLine.render(texture, effects, drawHandler, handlersManager, maxGlyphSize, targetThread, format);
		}

		public void getLost() { ExceptionUtils.doSilentThrowsRunnable(false, () -> {
			for(GlyphLineList glyphLine : glyphLines) glyphLine.getLost();
			ClassUtils.setFinal(this, GlyphPage.class.getDeclaredField("font"), null);
			ClassUtils.setFinal(this, GlyphPage.class.getDeclaredField("size"), null);
			ClassUtils.setFinal(this, GlyphPage.class.getDeclaredField("onFinished"), null);
		}); }

		private static boolean isMetricsHorizontal(GlyphMetrics metrics) { try {
			Field FIELD_horizontal = GlyphMetrics.class.getDeclaredField("horizontal");
			return (boolean) FIELD_horizontal.get(metrics);
		} catch(Exception ignored) { return true; } }

		public interface Effect {
			default void resize(Rectangle bounds, Shape shape, int w, int h, GlyphChar glyphChar) { }
			void draw(BufferedImage image, Graphics2D graphics, short[] raster, Shape shape, int x, int y, int w, int h, GlyphChar glyphChar);
		}
	}

	public static class GlyphLineList implements DeadableObject {
		/*
		 * bounds.x represents x offset.
		 * bounds.y represents y offset.
		 * bounds.width represents line width.
		 * bounds.height represents line height.
		 */

		protected final Font font;
		protected final int maxLine;
		protected final Rectangle bounds;
		protected final ArrayList<GlyphChar> glyphChars;
		protected final boolean horizontal;
		protected final boolean directionIncreasing;
		protected final boolean startBeginning;
		private final boolean meantHorizontal;
		protected boolean stopRendering;
		protected boolean rendering;
		protected boolean isDead;

		protected GlyphLineList(Font font, int maxLine, int pos, boolean horizontal, boolean directionIncreasing, boolean startBeginning, boolean meantHorizontal) {
			this.font = font;
			this.maxLine = maxLine;
			this.bounds = new Rectangle();
			this.glyphChars = new ArrayList<>();
			this.horizontal = horizontal;
			this.directionIncreasing = directionIncreasing;
			this.startBeginning = startBeginning;
			this.meantHorizontal = meantHorizontal;

			if(horizontal) { bounds.y = pos; if(!directionIncreasing) bounds.x = maxLine; }
			if(!horizontal) { bounds.x = pos; if(!directionIncreasing) bounds.y = maxLine; }
		}

		public Font getFont() { return font; }
		public int getMaxLine() { return maxLine; }
		public Rectangle getBounds() { return bounds; }
		public ArrayList<GlyphChar> getGlyphChars() { return glyphChars; }
		public boolean isHorizontal() { return horizontal; }
		public boolean isDirectionIncreasing() { return directionIncreasing; }
		public boolean isStartBeginning() { return startBeginning; }
		public boolean isRendering() { return rendering; }

		public void stopRendering() { stopRendering = true; }
		@Override public void setDead() { isDead = true; }
		@Override public boolean isDead() { return isDead; }

		public boolean containsCodePoint(int codePoint) {
			for(GlyphChar glyphChar : glyphChars) if(glyphChar.getCodePoint() == codePoint) return true;
			return false;
		}

		public boolean addGlyph(GlyphChar glyphChar) {
			Rectangle charBounds = glyphChar.getBounds();
			SVec4 charPadding = glyphChar.getPadding();
			if(horizontal) {
				if(directionIncreasing) { if(bounds.x + charBounds.width + charPadding.z() > maxLine - charPadding.x()) return false; }
				else if(bounds.x - charBounds.width - charPadding.x() < charPadding.z()) return false;
				if(startBeginning) { if(bounds.y + charBounds.height + charPadding.w() > maxLine - charPadding.y()) return false; }
				else if(bounds.y - charBounds.height - charPadding.y() < charPadding.w()) return false;
			} else {
				if(directionIncreasing) { if(bounds.y + charBounds.height + charPadding.w() > maxLine - charPadding.y()) return false; }
				else if(bounds.y - charBounds.height - charPadding.y() < charPadding.w()) return false;
				if(startBeginning) { if(bounds.x + charBounds.width + charPadding.z() > maxLine - charPadding.x()) return false; }
				else if(bounds.x - charBounds.width - charPadding.x() < charPadding.z()) return false;
			}
			glyphChars.add(glyphChar);
			if(horizontal) {
				charBounds.x = bounds.x - (!directionIncreasing ? charBounds.width + charPadding.z() + 1 : -charPadding.x());
				charBounds.y = bounds.y + (!startBeginning ? -charPadding.w() - charBounds.height : charPadding.y());
				bounds.x += (charBounds.width + (directionIncreasing ? charPadding.z() + charPadding.x() + 1 : charPadding.x())) * (directionIncreasing ? 1 : -1);
				bounds.height = Math.max(bounds.height, charBounds.height + charPadding.y() + charPadding.w());
				bounds.width = directionIncreasing ? bounds.x : maxLine - bounds.x;
			}
			if(!horizontal) {
				charBounds.y = bounds.y - (!directionIncreasing ? charBounds.height + charPadding.w() + 1 : -charPadding.y());
				charBounds.x = bounds.x + (!startBeginning ? -charPadding.z() - charBounds.width : charPadding.x());
				bounds.y += (charBounds.height + (directionIncreasing ? charPadding.w() : charPadding.y() + charPadding.w() + 1)) * (directionIncreasing ? 1 : -1);
				bounds.width = Math.max(bounds.width, charBounds.width + charPadding.x() + charPadding.z());
				bounds.height = directionIncreasing ? bounds.y : maxLine - bounds.y;
			} return true;
		}

		public synchronized void render(BufferedImage image, BufferedImage scratchImage, PriorityList<GlyphPage.Effect> effects) { try { rendering = true;
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			for(GlyphChar glyphChar : glyphChars) { if(stopRendering) break;
				glyphChar.render(scratchImage, 0, 0, scratchImage.getWidth(), scratchImage.getHeight(), effects);
				graphics.drawImage(scratchImage, glyphChar.getBounds().x, glyphChar.getBounds().y, null);
			}
		} finally { rendering = false; stopRendering = false; notifyAll(); } }
		public synchronized void render(Texture<? extends Number> texture, BufferedImage scratchImage, PriorityList<GlyphPage.Effect> effects, ByteBuffer byteBuffer, int format) { try { rendering = true;
			boolean textureBind = texture.isBind(); if(!textureBind) texture.bind();
			short[] raster = ((DataBufferShort) scratchImage.getRaster().getDataBuffer()).getData();
			int stride = scratchImage.getSampleModel().getNumBands();
			for(GlyphChar glyphChar : glyphChars) { if(stopRendering) break; Rectangle glyphBounds = glyphChar.getBounds();
				glyphChar.render(scratchImage, 0, 0, scratchImage.getWidth(), scratchImage.getHeight(), effects);
				for(int y = 0; y < glyphBounds.height; y++) for(int x = 0; x < glyphBounds.width; x++) {
					int offset = stride * (y * scratchImage.getWidth() + x);
					byteBuffer.put((byte) ((double) raster[offset    ] / Short.MAX_VALUE * 255));
					byteBuffer.put((byte) ((double) raster[offset + 1] / Short.MAX_VALUE * 255));
					byteBuffer.put((byte) ((double) raster[offset + 2] / Short.MAX_VALUE * 255));
					byteBuffer.put((byte) ((double) raster[offset + 3] / Short.MAX_VALUE * 255));
				} byteBuffer.clear();
				texture.reload(byteBuffer, 0, glyphBounds.x, glyphBounds.y, glyphBounds.width, glyphBounds.height, format);
			} if(!textureBind) texture.unbind();
		} finally { rendering = false; stopRendering = false; notifyAll(); } }
		public void render(Texture<? extends Number> texture, PriorityList<GlyphPage.Effect> effects, Handler drawHandler, HandlersManager handlersManager, Size maxGlyphSize, int targetThread, int format) {
			int maxGlyphSizeWidth = maxGlyphSize.getWidth(); int maxGlyphSizeHeight = maxGlyphSize.getHeight(); // Avoids thread racing
			int threadToRun = Math.min(targetThread, glyphChars.size()); int closestSquare = Integer.MAX_VALUE; for(int i = 1; i <= threadToRun; ++i) { if(threadToRun % i != 0) continue;
				if(Math.abs((threadToRun / i) - i) < Math.abs((threadToRun / closestSquare) - closestSquare)) closestSquare = i; }
			BufferedImage image = ImageUtils.newShortBufferedImage(closestSquare * maxGlyphSizeWidth, threadToRun / closestSquare * maxGlyphSizeHeight);
			short[] raster = ((DataBufferShort) image.getRaster().getDataBuffer()).getData();
			int stride = image.getSampleModel().getNumBands();
			ByteBuffer byteBuffer = BufferUtils.createByteBuffer(maxGlyphSizeWidth * maxGlyphSizeHeight * stride * threadToRun);
			AtomicInteger joinedThreads = new AtomicInteger();
			ReferencedCallback.PVoidReferencedCallback join = (args) -> { synchronized(GlyphLineList.this) {
				int currentJoined = joinedThreads.incrementAndGet(); if(currentJoined < threadToRun) return;
				ImageUtils.free(image); BufferUtils.deallocate(byteBuffer);
				rendering = false; stopRendering = false; GlyphLineList.this.notifyAll();
			} }; rendering = true; GlyphChar[] glyphChars = this.glyphChars.toArray(new GlyphChar[0]);
			for(int i = 0; i < threadToRun; i++) { int _i = i;
				handlersManager.post(new Runnable() {
					int current = _i; boolean reloading = false; final Runnable self = this;
					final ByteBuffer _byteBuffer = BufferUtils.slice(byteBuffer, maxGlyphSizeWidth * maxGlyphSizeHeight * stride * _i, maxGlyphSizeWidth * maxGlyphSizeHeight * stride);
					final int x = (maxGlyphSizeWidth * _i) % image.getWidth();
					final int y = ((maxGlyphSizeWidth * _i) / image.getWidth()) * maxGlyphSizeHeight;
					@Override public synchronized void run() { try { if(stopRendering || current >= glyphChars.length) return;
						GlyphChar glyphChar = glyphChars[current]; Rectangle glyphBounds = glyphChar.getBounds();
						glyphChar.render(image, x, y, maxGlyphSizeWidth, maxGlyphSizeHeight, effects);
						while(reloading) ExceptionUtils.doSilentThrowsRunnable(false, this::wait); if(stopRendering) return;
						int start = this.y * image.getWidth() + this.x;
						for(int y = 0; y < glyphBounds.height; y++) for(int x = 0; x < glyphBounds.width; x++) {
							int offset = stride * (start + y * image.getWidth() + x);
							_byteBuffer.put((byte) ((double) raster[offset    ] / Short.MAX_VALUE * 255));
							_byteBuffer.put((byte) ((double) raster[offset + 1] / Short.MAX_VALUE * 255));
							_byteBuffer.put((byte) ((double) raster[offset + 2] / Short.MAX_VALUE * 255));
							_byteBuffer.put((byte) ((double) raster[offset + 3] / Short.MAX_VALUE * 255));
						} _byteBuffer.clear();
						reloading = true; drawHandler.post(() -> { boolean textureBind = texture.isBind(); if(!textureBind) texture.bind();
							texture.reload(_byteBuffer, 0, glyphBounds.x, glyphBounds.y, glyphBounds.width, glyphBounds.height, format);
							if(!textureBind) texture.unbind(); synchronized(self) { reloading = false; self.notifyAll(); }
						}); current += threadToRun;
					} finally { if(stopRendering || current >= glyphChars.length) join.get(); else handlersManager.post(this); } }
				});
			}
		}

		public void getLost() { ExceptionUtils.doSilentThrowsRunnable(false, () -> {
			for(GlyphChar glyphChar : glyphChars) glyphChar.getLost();
			ClassUtils.setFinal(this, GlyphLineList.class.getDeclaredField("font"), null);
			ClassUtils.setFinal(this, GlyphLineList.class.getDeclaredField("bounds"), null);
		}); }
	}

	public static class GlyphChar {
		protected final int codePoint;
		protected final boolean horizontal;
		private final boolean meantHorizontal;

		protected final Rectangle bounds;				// Bounds in image (int), only used when rendering
		protected final Rectangle drawBounds;			// Real unmodified bounds
		protected final Rectangle2D textureBounds;		// Bounds in texture (double 0-1), only used when drawing
		protected final Rectangle2D vertexBounds;		// Bounds in vertex (double 0-1), only used when drawing
		protected final SVec4 padding;
		protected final Shape shape;
		protected double advance;						// Only used when drawing (double 0-1)
		protected double lineSize;

		protected GlyphChar(int codePoint, Font font, boolean horizontal, boolean meantHorizontal, SVec4 padding) {
			FontMetrics fontMetrics = scratchGraphics.getFontMetrics(font);
			GlyphVector glyphVector = FontUtils.getVector(font, scratchGraphics, Character.toChars(codePoint));
			GlyphMetrics glyphMetrics = glyphVector.getGlyphMetrics(0);
			this.codePoint = codePoint;
			this.horizontal = horizontal;
			this.meantHorizontal = meantHorizontal;

			this.bounds = getGlyphBounds(codePoint, glyphVector);
			this.drawBounds = new Rectangle(bounds);
			this.textureBounds = new Rectangle2D.Double();
			this.vertexBounds = new Rectangle2D.Double();
			this.padding = padding;
			this.shape = glyphVector.getGlyphOutline(0);
			this.advance = glyphMetrics.getAdvance();
			this.lineSize = fontMetrics.getHeight();

			double xOff = drawBounds.x + (!horizontal ? !meantHorizontal ? lineSize : drawBounds.height : 0);
			double yOff = drawBounds.y + (horizontal ? meantHorizontal ? lineSize : drawBounds.width : 0);
			vertexBounds.setRect(xOff / font.getSize(), yOff / font.getSize(),
					(double) drawBounds.width / font.getSize(), (double) drawBounds.height / font.getSize());
			advance /= font.getSize(); lineSize /= font.getSize();
		}

		public int getCodePoint() { return codePoint; }
		public boolean isHorizontal() { return horizontal; }
		public Rectangle getBounds() { return bounds; }
		public Rectangle getDrawBounds() { return drawBounds; }
		public Rectangle2D getTextureBounds() { return textureBounds; }
		public Rectangle2D getVertexBounds() { return vertexBounds; }
		public SVec4 getPadding() { return padding; }
		public Shape getShape() { return shape; }
		public double getAdvance() { return advance; }
		public double getLineSize() { return lineSize; }

		public void render(BufferedImage image, int x, int y, int w, int h, PriorityList<GlyphPage.Effect> effects) {
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			short[] raster = ((DataBufferShort) image.getRaster().getDataBuffer()).getData();
			ImageUtils.clearPixels(raster, x, y, image.getWidth(), image.getHeight(), w, h, image.getSampleModel().getNumBands());
			GlyphPage.Effect[] effectsArray = effects.get().toArray(new GlyphPage.Effect[0]);
			for(GlyphPage.Effect effect : effectsArray) effect.draw(image, graphics, raster, shape, x, y, w, h, this);
		}
		public void calculateTexBounds(Size size) {
			textureBounds.setRect((double) bounds.x / size.getWidth(), (double) bounds.y / size.getHeight(),
					(double) bounds.width / size.getWidth(), (double) bounds.height / size.getHeight());
		}

		public void getLost() { ExceptionUtils.doSilentThrowsRunnable(false, () -> {
			ClassUtils.setFinal(this, GlyphChar.class.getDeclaredField("padding"), null);
			ClassUtils.setFinal(this, GlyphChar.class.getDeclaredField("shape"), null);
		}); }

		private static Rectangle getGlyphBounds(int codePoint, GlyphVector vector) {
			if(Character.isWhitespace(codePoint)) return vector.getGlyphLogicalBounds(0).getBounds();
			return vector.getGlyphPixelBounds(0, scratchGraphics.getFontRenderContext(), 0, 0);
		}
	}

	public class GlyphText extends Entity {
		protected String text;

		protected GlyphText(Program<? extends Number> program, TempVec temp) {
			super(new GlyphTextModel().loadToGL(program.getGL()), program, FontRender.this.getTexture(), temp);
		}

		public String getText() { return text; }
		public void setText(String text) { this.text = text; }

		public void updateText(double fontSize, double charSpacing, double lineSpacing, double maxLine, boolean horizontal) {
			assertNotDead(); assertCreated();
			((GlyphTextModel) model.getModel()).updateText(text, fontSize, charSpacing, lineSpacing, maxLine, horizontal);
			model.reload();
		}
		public void updateText() {
			assertNotDead(); assertCreated();
			((GlyphTextModel) model.getModel()).updateText(text);
			model.reload();
		}

		@Override protected void disarrange() {
			super.disarrange(); if(!model.isDead()) model.destroy();
		}
	}
	protected class GlyphTextModel extends Model {
		protected final ModelObject mainModelObject;
		protected final ModelMesh mainModelMesh;
		protected final List<ModelFace> mainFaceList;
		protected int faceCount;

		protected final ArrayList<Long> words;
		protected double lastFontSize;
		protected double lastCharSpacing;
		protected double lastLineSpacing;
		protected double lastMaxLine;
		protected boolean lastHorizontal;

		protected GlyphTextModel() {
			this.mainModelObject = new ModelObject("Default", ModelObject.TYPE_OBJECT);
			this.mainModelMesh = new ModelMesh("Default");
			this.mainFaceList = mainModelMesh.getFaces();
			this.words = ListUtils.optimizedArrayList();/*new ArrayList<>();*/

			getObjects().add(mainModelObject);
			mainModelObject.getMeshes().add(mainModelMesh);
		}

		@SuppressWarnings("ListRemoveInLoop")
		public void updateText(String text, double fontSize, double charSpacing, double lineSpacing, double maxLine, boolean horizontal) {
			faceCount = 0; int startWord = 0; int j = 0;
			for(int i = 0; i < text.length(); i++) {
				int codePoint = text.codePointAt(i);
				if(!Character.isWhitespace(codePoint)) continue;
				words.add(j++, (((long) startWord) << 32) | ((i + 1) & 0xffffffffL));
				startWord = i + 1;
			} words.add(j++, (((long) startWord) << 32) | (text.length() & 0xffffffffL));
			for(int i = words.size() - 1; i >= j; i--)
				words.remove(i);

			int index = 0;
			double cursorX = 0;
			double cursorY = 0;
			for(long word : words) {
				int start = (int) (word >> 32);
				int end = (int) word;
				double cursorBefore = horizontal ? cursorX : cursorY;
				for(int i = start; i < end; i++) {
					int codePoint = text.codePointAt(i);
					GlyphChar glyphChar = characters.get(codePoint); // TODO LEAK integer is being boxed here
					if(glyphChar == null) continue; // TODO any visual hint?
					index = putVertexes(index, glyphChar, cursorX, cursorY, fontSize, charSpacing);
					if(horizontal) cursorX += (glyphPage.meantHorizontal ? glyphChar.getAdvance() : glyphChar.getVertexBounds().getWidth()) * fontSize + charSpacing;
					else cursorY += (!glyphPage.meantHorizontal ? glyphChar.getAdvance() : glyphChar.getVertexBounds().getHeight()) * fontSize + charSpacing;
					if(horizontal) { if(cursorX > maxLine && cursorX - cursorBefore <= maxLine) {
						cursorX = 0; cursorY += glyphChar.getLineSize() * fontSize + lineSpacing;
						i = start - 1; index = 4 * start; faceCount = 2 * start;
					} } else { if(cursorY > maxLine && cursorY - cursorBefore <= maxLine) {
						cursorY = 0; cursorX += glyphChar.getLineSize() * fontSize + lineSpacing;
						i = start - 1; index = 4 * start; faceCount = 2 * start;
					} }
				}
			}

			for(int i = getVertexes().size() - 1; i >= index; i--)
				getVertexes().remove(i);
			for(int i = mainFaceList.size() - 1; i >= faceCount; i--)
				mainFaceList.remove(i);

			index = 0;
			for(int i = 0; i < text.length(); i++) {
				int codePoint = text.codePointAt(i);
				GlyphChar glyphChar = characters.get(codePoint); // TODO LEAK integer is being boxed here
				if(glyphChar == null) continue; // TODO any visual hint?
				index = putTexCoordinates(index, glyphChar);
			}
			for(int i = getTextureCoordinates().size() - 1; i >= index; i--)
				getTextureCoordinates().remove(i);

			lastFontSize = fontSize;
			lastCharSpacing = charSpacing;
			lastLineSpacing = lineSpacing;
			lastMaxLine = maxLine;
			lastHorizontal = horizontal;
		}
		public void updateText(String text) {
			updateText(text, lastFontSize, lastCharSpacing, lastLineSpacing, lastMaxLine, lastHorizontal);
		}

		protected int putVertexes(int index, GlyphChar glyphChar, double cursorX, double cursorY, double fontSize, double charSpacing) {
			List<Vec3> data = getVertexes();
			Rectangle2D scaledSize = glyphChar.getVertexBounds();
			cursorX += scaledSize.getX() * fontSize;
			cursorY += scaledSize.getY() * fontSize;
			double maxX = cursorX + (scaledSize.getWidth() * fontSize);
			double maxY = cursorY + (scaledSize.getHeight() * fontSize);
			Vec3 d0 = index     < data.size() ? data.get(index    ) : vec3(0);
			Vec3 d1 = index + 1 < data.size() ? data.get(index + 1) : vec3(0);
			Vec3 d2 = index + 2 < data.size() ? data.get(index + 2) : vec3(0);
			Vec3 d3 = index + 3 < data.size() ? data.get(index + 3) : vec3(0);
			if(index     >= data.size()) data.add(d0);
			if(index + 1 >= data.size()) data.add(d1);
			if(index + 2 >= data.size()) data.add(d2);
			if(index + 3 >= data.size()) data.add(d3);
			d0.set(cursorX, -cursorY, 0);
			d1.set(cursorX, -maxY, 0);
			d2.set(maxX, -cursorY, 0);
			d3.set(maxX, -maxY, 0);

			ModelFace face = faceCount < mainFaceList.size() ? mainFaceList.get(faceCount) : null;
			if(face == null) { face = new ModelFace(3); mainFaceList.add(face); }
			ModelFaceIndex[] indices = face.getIndices();
			if(indices[0] == null) indices[0] = new ModelFaceIndex(ModelFaceIndex.UNDEFINED, ModelFaceIndex.UNDEFINED, ModelFaceIndex.UNDEFINED);
			if(indices[1] == null) indices[1] = new ModelFaceIndex(ModelFaceIndex.UNDEFINED, ModelFaceIndex.UNDEFINED, ModelFaceIndex.UNDEFINED);
			if(indices[2] == null) indices[2] = new ModelFaceIndex(ModelFaceIndex.UNDEFINED, ModelFaceIndex.UNDEFINED, ModelFaceIndex.UNDEFINED);
			indices[0].setVertexIndex(index    ); indices[0].setTextureCoordinateIndex(index    );
			indices[1].setVertexIndex(index + 1); indices[1].setTextureCoordinateIndex(index + 1);
			indices[2].setVertexIndex(index + 2); indices[2].setTextureCoordinateIndex(index + 2);
			faceCount++;
			face = faceCount < mainFaceList.size() ? mainFaceList.get(faceCount) : null;
			if(face == null) { face = new ModelFace(3); mainFaceList.add(face); }
			indices = face.getIndices();
			if(indices[0] == null) indices[0] = new ModelFaceIndex(ModelFaceIndex.UNDEFINED, ModelFaceIndex.UNDEFINED, ModelFaceIndex.UNDEFINED);
			if(indices[1] == null) indices[1] = new ModelFaceIndex(ModelFaceIndex.UNDEFINED, ModelFaceIndex.UNDEFINED, ModelFaceIndex.UNDEFINED);
			if(indices[2] == null) indices[2] = new ModelFaceIndex(ModelFaceIndex.UNDEFINED, ModelFaceIndex.UNDEFINED, ModelFaceIndex.UNDEFINED);
			indices[0].setVertexIndex(index + 1); indices[0].setTextureCoordinateIndex(index + 1);
			indices[1].setVertexIndex(index + 3); indices[1].setTextureCoordinateIndex(index + 3);
			indices[2].setVertexIndex(index + 2); indices[2].setTextureCoordinateIndex(index + 2);
			faceCount++; return index + 4;
		}
		protected int putTexCoordinates(int index, GlyphChar glyphChar) {
			List<Vec2> data = getTextureCoordinates();
			Rectangle2D textureBounds = glyphChar.getTextureBounds();
			Vec2 d0 = index     < data.size() ? data.get(index    ) : vec2(0);
			Vec2 d1 = index + 1 < data.size() ? data.get(index + 1) : vec2(0);
			Vec2 d2 = index + 2 < data.size() ? data.get(index + 2) : vec2(0);
			Vec2 d3 = index + 3 < data.size() ? data.get(index + 3) : vec2(0);
			if(index     >= data.size()) data.add(d0);
			if(index + 1 >= data.size()) data.add(d1);
			if(index + 2 >= data.size()) data.add(d2);
			if(index + 3 >= data.size()) data.add(d3);
			d0.set(textureBounds.getX(), textureBounds.getY());
			d1.set(textureBounds.getX(), textureBounds.getMaxY());
			d2.set(textureBounds.getMaxX(), textureBounds.getY());
			d3.set(textureBounds.getMaxX(), textureBounds.getMaxY());
			return index + 4;
		}
	}

	public static final String EXTENDED_CHARS;
	public static final String NEHE_CHARS;
	static {
		StringBuilder builder = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
		try {
			int[] codePoints = new int[] { 0, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56,
					57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86,
					87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113,
					114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170,
					171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194,
					195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218,
					219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242,
					243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266,
					267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290,
					291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314,
					315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338,
					339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362,
					363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 884, 885, 890,
					891, 892, 893, 894, 900, 901, 902, 903, 904, 905, 906, 908, 910, 911, 912, 913, 914, 915, 916, 917, 918, 919, 920, 921,
					922, 923, 924, 925, 926, 927, 928, 929, 931, 932, 933, 934, 935, 936, 937, 938, 939, 940, 941, 942, 943, 944, 945, 946,
					947, 948, 949, 950, 951, 952, 953, 954, 955, 956, 957, 958, 959, 960, 961, 962, 963, 964, 965, 966, 967, 968, 969, 970,
					971, 972, 973, 974, 976, 977, 978, 979, 980, 981, 982, 983, 984, 985, 986, 987, 988, 989, 990, 991, 992, 993, 994, 995,
					996, 997, 998, 999, 1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012, 1013, 1014, 1015, 1016,
					1017, 1018, 1019, 1020, 1021, 1022, 1023, 1024, 1025, 1026, 1027, 1028, 1029, 1030, 1031, 1032, 1033, 1034, 1035, 1036,
					1037, 1038, 1039, 1040, 1041, 1042, 1043, 1044, 1045, 1046, 1047, 1048, 1049, 1050, 1051, 1052, 1053, 1054, 1055, 1056,
					1057, 1058, 1059, 1060, 1061, 1062, 1063, 1064, 1065, 1066, 1067, 1068, 1069, 1070, 1071, 1072, 1073, 1074, 1075, 1076,
					1077, 1078, 1079, 1080, 1081, 1082, 1083, 1084, 1085, 1086, 1087, 1088, 1089, 1090, 1091, 1092, 1093, 1094, 1095, 1096,
					1097, 1098, 1099, 1100, 1101, 1102, 1103, 1104, 1105, 1106, 1107, 1108, 1109, 1110, 1111, 1112, 1113, 1114, 1115, 1116,
					1117, 1118, 1119, 1120, 1121, 1122, 1123, 1124, 1125, 1126, 1127, 1128, 1129, 1130, 1131, 1132, 1133, 1134, 1135, 1136,
					1137, 1138, 1139, 1140, 1141, 1142, 1143, 1144, 1145, 1146, 1147, 1148, 1149, 1150, 1151, 1152, 1153, 1154, 1155, 1156,
					1157, 1158, 1159, 1160, 1161, 1162, 1163, 1164, 1165, 1166, 1167, 1168, 1169, 1170, 1171, 1172, 1173, 1174, 1175, 1176,
					1177, 1178, 1179, 1180, 1181, 1182, 1183, 1184, 1185, 1186, 1187, 1188, 1189, 1190, 1191, 1192, 1193, 1194, 1195, 1196,
					1197, 1198, 1199, 1200, 1201, 1202, 1203, 1204, 1205, 1206, 1207, 1208, 1209, 1210, 1211, 1212, 1213, 1214, 1215, 1216,
					1217, 1218, 1219, 1220, 1221, 1222, 1223, 1224, 1225, 1226, 1227, 1228, 1229, 1230, 1231, 1232, 1233, 1234, 1235, 1236,
					1237, 1238, 1239, 1240, 1241, 1242, 1243, 1244, 1245, 1246, 1247, 1248, 1249, 1250, 1251, 1252, 1253, 1254, 1255, 1256,
					1257, 1258, 1259, 1260, 1261, 1262, 1263, 1264, 1265, 1266, 1267, 1268, 1269, 1270, 1271, 1272, 1273, 1274, 1275, 1276,
					1277, 1278, 1279, 1280, 1281, 1282, 1283, 1284, 1285, 1286, 1287, 1288, 1289, 1290, 1291, 1292, 1293, 1294, 1295, 1296,
					1297, 1298, 1299, 1300, 1301, 1302, 1303, 1304, 1305, 1306, 1307, 1308, 1309, 1310, 1311, 1312, 1313, 1314, 1315, 1316,
					1317, 1318, 1319, 8192, 8193, 8194, 8195, 8196, 8197, 8198, 8199, 8200, 8201, 8202, 8203, 8204, 8205, 8206, 8207, 8210,
					8211, 8212, 8213, 8214, 8215, 8216, 8217, 8218, 8219, 8220, 8221, 8222, 8223, 8224, 8225, 8226, 8230, 8234, 8235, 8236,
					8237, 8238, 8239, 8240, 8242, 8243, 8244, 8249, 8250, 8252, 8254, 8260, 8286, 8298, 8299, 8300, 8301, 8302, 8303, 8352,
					8353, 8354, 8355, 8356, 8357, 8358, 8359, 8360, 8361, 8363, 8364, 8365, 8366, 8367, 8368, 8369, 8370, 8371, 8372, 8373,
					8377, 8378, 11360, 11361, 11362, 11363, 11364, 11365, 11366, 11367, 11368, 11369, 11370, 11371, 11372, 11373, 11377,
					11378, 11379, 11380, 11381, 11382, 11383 };
			for(int codePoint : codePoints) builder.append(Character.toChars(codePoint)); EXTENDED_CHARS = builder.toString();
		} finally { Pool.returnObject(StringBuilder.class, builder); }
		NEHE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890 \"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*\u0000\u007F";
	}

//	public static void main(String[] args) {
//		Font font = new Font("Fira Code", Font.PLAIN, 16);
//		SVec4 padding = new SVec4(new short[] { 50, 25, 25, 50 });
//		Size maxGlyphSize = new Size(64, 64);
//		GlyphPage glyphPage = new GlyphPage(font, new Size(2048, 2048), true, true, true);
//		PriorityList<GlyphPage.Effect> effects = new PriorityList<>();
//		effects.add((image, graphics, raster, shape, x, y, w, h, codePoint) -> {
//			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//		}, 1);
//		effects.add(new GlyphPage.Effect() {
//			@Override public void resize(Rectangle bounds, Shape shape, int w, int h, SVec4 padding, GlyphChar glyphChar) {
//				Rectangle drawBounds = glyphChar.getDrawBounds();
//				boolean widthSmaller = drawBounds.width < drawBounds.height;
//				bounds.width = widthSmaller ? (int) ((double) drawBounds.width / drawBounds.height * h) : w;
//				bounds.height = !widthSmaller ? (int) ((double) drawBounds.height / drawBounds.width * w) : h;
//			}
//			@Override public void draw(BufferedImage image, Graphics2D graphics, int[] raster, Shape shape, int x, int y, int w, int h, GlyphChar glyphChar) {
//				ContourShape contourShape = ContourShape.convertFromShape(shape);
//				EdgeColor.edgeColoringSimple(contourShape, 3.0, 0);
//				Vec2 translate = new Vec2(); Vec2 scale = new Vec2(1); Vec1 range = new Vec1(1);
//				DistanceFieldUtils.autoFrame(contourShape.getBounds(0, 0, 0), w, h, translate, scale, range);
//
//				ReferencedCallback.PVoidReferencedCallback distanceFieldCallback = (args) -> {
//					int i = (int) args[0] / 3; Vec3 distance = (Vec3) args[1];
//					i = (y * w + x) + (i / w) * image.getWidth() + (i % w);
//					float dataX = (float) (distance.x() / range.x() + .5);
//					float dataY = (float) (distance.y() / range.x() + .5);
//					float dataZ = (float) (distance.z() / range.x() + .5);
//
//					int r, g, b, a = 255;
//					r = (int) clamp(dataX * 256, 0, 255);
//					g = (int) clamp(dataY * 256, 0, 255);
//					b = (int) clamp(dataZ * 256, 0, 255);
//					raster[i] = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
//				}; DistanceFieldUtils.generateMSDF(distanceFieldCallback, w, h, contourShape, range.x(), scale, translate, true);
//			}
//		});
////		effects.add((image, graphics, raster, shape, x, y, w, h, glyphChar) -> {
//////			Rectangle2D bounds = shape.getBounds2D();
//////			graphics.translate(bounds.getX(), bounds.getY());
//////			graphics.setColor(Color.GREEN);
//////			graphics.fillRect(0, 0, padding.x(), (int) bounds.getHeight());
//////			graphics.setColor(Color.BLUE);
//////			graphics.fillRect(0, 0, (int) bounds.getWidth(), padding.y());
//////			graphics.setColor(Color.RED);
//////			graphics.fillRect((int) bounds.getWidth() - padding.z(), 0, padding.z(), (int) bounds.getHeight());
//////			graphics.setColor(Color.YELLOW);
//////			graphics.fillRect(0, (int) bounds.getHeight() - padding.w(), (int) bounds.getWidth(), padding.y());
////			graphics.translate(x, y);
////			graphics.setColor(Color.BLACK);
////			graphics.fill(shape.getBounds2D());
////			graphics.setColor(Color.WHITE);
////			graphics.fill(shape);
////		});
//		glyphPage.addGlyph(NEHE_CHARS, effects, padding, maxGlyphSize);
//		BufferedImage result = new BufferedImage(2048, 2048, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D graphics = (Graphics2D) result.getGraphics();
//		graphics.setColor(Color.BLACK);
//		graphics.fillRect(0, 0, result.getWidth(), result.getHeight());
//		graphics.setColor(Color.GREEN);
//		graphics.fillRect(0, 0, padding.x(), result.getHeight());
//		graphics.setColor(Color.RED);
//		graphics.fillRect(0, 0, result.getWidth(), padding.y());
//		graphics.setColor(Color.BLUE);
//		graphics.fillRect(result.getWidth() - padding.z(), 0, padding.z(), result.getHeight());
//		graphics.setColor(Color.YELLOW);
//		graphics.fillRect(0, result.getHeight() - padding.w(), result.getWidth(), padding.w());
//		glyphPage.render(result, new BufferedImage(maxGlyphSize.getWidth(), maxGlyphSize.getHeight(), BufferedImage.TYPE_INT_ARGB), effects);
//	}
}
