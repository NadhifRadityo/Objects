package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.GenType;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec1;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Combiner.ContourCombiner;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Combiner.OverlappingContourCombiner;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Combiner.SimpleContourCombiner;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Contour;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.ContourShape;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.ScanLine;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeColor;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeSegment;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.*;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.FontUtils;
import io.github.NadhifRadityo.Objects.Utilizations.DistanceFieldUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ImageUtils;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferShort;
import java.io.IOException;
import java.util.ArrayList;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.*;

public class DistanceFieldGenerator2 {
	@SuppressWarnings({"ControlFlowStatementWithoutBraces", "ConstantConditions"})
	public static void main(String... args) throws IOException, FontFormatException {
//		BufferedImage aa = ImageIO.read(new File("E:\\Projects\\Visual Studio\\msdfgen\\x64\\Release\\output.png"));
//		BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);

		BufferedImage image = ImageUtils.newShortBufferedImage(64, 64);
//		{
//			int w = 64;
//			int h = 64;
//			int[] bands = new int[] { 0, 1, 2, 3 };
//			SampleModel sampleModel = new ComponentSampleModel(DataBuffer.TYPE_SHORT, w, h, bands.length, w * bands.length, bands);
//			WritableRaster raster = Raster.createWritableRaster(sampleModel, new DataBufferShort(new short[w * h * bands.length], w * h * bands.length), null);
//			ColorModel colorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_SHORT);
//			image = new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);
//
////			int w = 64;
////			int h = 64;
////			int[] bands = new int[] { 0, 1, 2, 3 };
////			SampleModel sampleModel = new PixelInterleavedSampleModel(DataBuffer.TYPE_SHORT, w, h, bands.length, w * bands.length, bands);
////			DataBuffer buffer = new DataBufferShort(w * h * bands.length);
////			WritableRaster raster = Raster.createWritableRaster(sampleModel, buffer, null);
////			ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);
////			ColorModel colorModel = new ComponentColorModel(colorSpace, true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_SHORT);
////			image = new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);
//
//			short[] pixels = ((DataBufferShort) image.getRaster().getDataBuffer()).getData();
//			short[] filler = new short[bands.length * 20];
//			for(int i = 0; i < filler.length / bands.length; i++)
//				filler[i * bands.length + 3] = 255;
//			ArrayUtils.fill(pixels, filler);
//		}

		Font font = new Font("Fira Code", Font.PLAIN, 12);
//		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Windows\\Fonts\\PLAYBILL.TTF")).deriveFont(Font.PLAIN, 32);
		GlyphVector vector = FontUtils.getVector(font, (Graphics2D) image.getGraphics(), "A");
		Shape shape = vector.getGlyphOutline(0);

//		GeneralPath shape = new GeneralPath();
//		GeneralPath path;
//
//		path = new GeneralPath();
//		path.moveTo(1471, 0);
//		path.lineTo(1149, 0);
//		path.lineTo(1021, 333);
//		path.lineTo(435, 333);
//		path.lineTo(314, 0);
//		path.lineTo(0, 0);
//		path.lineTo(571, 1466);
//		path.lineTo(884, 1466);
//		path.lineTo(1471, 0);
//		shape.append(path, false);
//
//		path = new GeneralPath();
//		path.moveTo(926, 580);
//		path.lineTo(724, 1124);
//		path.lineTo(526, 580);
//		path.lineTo(926, 580);
//		shape.append(path, false);

		ContourShape contourShape = ContourShape.convertFromShape(shape);
		System.out.println(getPathString(shape));

//		ContourShape contourShape = new ContourShape(true);
//		Contour contour = new Contour();
//		contourShape.getContours().add(contour);
//		contour.addEdge(new LinearSegment(vec2(1471, 0), vec2(1149, 0), EdgeColor.WHITE));
//		contour.addEdge(new LinearSegment(vec2(1149, 0), vec2(1021, 333), EdgeColor.WHITE));
//		contour.addEdge(new LinearSegment(vec2(1021, 333), vec2(435, 333), EdgeColor.WHITE));
//		contour.addEdge(new LinearSegment(vec2(435, 333), vec2(314, 0), EdgeColor.WHITE));
//		contour.addEdge(new LinearSegment(vec2(314, 0), vec2(0, 0), EdgeColor.WHITE));
//		contour.addEdge(new LinearSegment(vec2(0, 0), vec2(571, 1466), EdgeColor.WHITE));
//		contour.addEdge(new LinearSegment(vec2(571, 1466), vec2(884, 1466), EdgeColor.WHITE));
//		contour.addEdge(new LinearSegment(vec2(884, 1466), vec2(1471, 0), EdgeColor.WHITE));
//
//		contour = new Contour();
//		contourShape.getContours().add(contour);
//		contour.addEdge(new LinearSegment(vec2(926, 580), vec2(724, 1124), EdgeColor.WHITE));
//		contour.addEdge(new LinearSegment(vec2(724, 1124), vec2(526, 580), EdgeColor.WHITE));
//		contour.addEdge(new LinearSegment(vec2(526, 580), vec2(926, 580), EdgeColor.WHITE));

		EdgeColor.edgeColoringSimple(contourShape, 3.0, 0);
//		generateDistanceFieldTrueDistance(image, contourShape, 210.14285714285714, vec2(0.0095173351461590762), vec2(105.07142857142857, 107.57142857142857));

		Vec2 translate = new Vec2();
		Vec2 scale = new Vec2(1);
		Vec1 range = new Vec1(1);
		DistanceFieldUtils.autoFrame(contourShape.getBounds(0, 0, 0), image.getWidth(), image.getHeight(), scale, translate, range);

//		Vec4 bounds = contourShape.getBounds(0, 0, 0);
//		Vec2 frame = new Vec2(image.getWidth(), image.getHeight());
//		frame.d[0] -= 2; frame.d[1] -= 2;
//		if(bounds.x() >= bounds.z() || bounds.y() >= bounds.w()) {
//			bounds.x(0); bounds.y(0);
//			bounds.z(1); bounds.w(1);
//		}
//		if(frame.x() <= 0 || frame.y() <= 0)
//			throw new IllegalArgumentException("Cannot fit the specified pixel range.");
//		Vec2 dimensions = new Vec2(bounds.z() - bounds.x(), bounds.w() - bounds.y());
//		if(dimensions.x() * frame.y() < dimensions.y() * frame.x()) {
//			translate.set((frame.x() / frame.y() * dimensions.y() - dimensions.x()) / 2 - bounds.x(), -bounds.y());
//			scale.set(frame.y() / dimensions.y(), frame.y() / dimensions.y());
//		} else {
//			translate.set(-bounds.x(), (frame.y() / frame.x() * dimensions.x() - dimensions.y()) / 2 - bounds.y());
//			scale.set(frame.x() / dimensions.x(), frame.x() / dimensions.x());
//		} translate.set(translate.x() + 1 / scale.x(), translate.y() + 1 / scale.y());
//		range.x(2 / min(scale.x(), scale.y()));

		int channels = 3;
//		float[] data = new float[image.getWidth() * image.getHeight() * 4];
		short[] pixels = ((DataBufferShort) image.getRaster().getDataBuffer()).getData();
//		int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
//		float[] pixels = ((DataBufferFloat) image.getRaster().getDataBuffer()).getData();
		long start = System.currentTimeMillis();
//		generateMSDF(data, image.getWidth(), image.getHeight(), contourShape, range.x(), scale, translate, true);
//		ReferencedCallback.PVoidReferencedCallback distanceFieldCallback = (_args) -> {
//			int i = (int) _args[0]; GenType distance = (GenType) _args[1]; int r, g, b, a = 255;
//			r = g = b = (int) clamp(((double) distance.getVectorAt(0) / range.x() + .5) * 256, 0, 255);
//			if(channels >= 2) g = b = (int) clamp(((double) distance.getVectorAt(1) / range.x() + .5) * 256, 0, 255);
//			if(channels >= 3) b = (int) clamp(((double) distance.getVectorAt(2) / range.x() + .5) * 256, 0, 255);
//			if(channels >= 4) a = (int) clamp(((double) distance.getVectorAt(3) / range.x() + .5) * 256, 0, 255);
//			pixels[i] = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
//		};
//		if(channels == 1) DistanceFieldUtils.generateSDF(distanceFieldCallback, image.getWidth(), image.getHeight(), contourShape, scale, translate, true);
//		if(channels == 3) DistanceFieldUtils.generateMSDF(distanceFieldCallback, image.getWidth(), image.getHeight(), contourShape, scale, translate, true);
//		if(channels == 4) DistanceFieldUtils.generateMTSDF(distanceFieldCallback, image.getWidth(), image.getHeight(), contourShape, scale, translate, true);
		if(channels == 1) DistanceFieldUtils.generateSDF(pixels, 0, 0, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight(), 4, contourShape, range.x(), scale, translate, true, ScanLine.FillRule.FILL_NONZERO);
		if(channels == 3) DistanceFieldUtils.generateMSDF(pixels, 0, 0, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight(), 4, contourShape, range.x(), scale, translate, true, ScanLine.FillRule.FILL_NONZERO);
		if(channels == 4) DistanceFieldUtils.generateMTSDF(pixels, 0, 0, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight(), 4, contourShape, range.x(), scale, translate, true, ScanLine.FillRule.FILL_NONZERO);
//		ImageUtils.scaleBands(pixels, (double) Short.MAX_VALUE / 255, 0, 0, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight(), 4);
		System.out.println("Done! Took: " + (System.currentTimeMillis() - start) + "ms");

//		Graphics2D graphics = (Graphics2D) image.getGraphics();
//		graphics.setColor(Color.CYAN);
//		graphics.fillRect(20, 20, 30, 30);
//		ImageUtils.cutImage(pixels, 20, 20, image.getWidth(), image.getHeight(), pixels, 2, 1, image.getWidth(), image.getHeight(), 29, 29, 4, ImageUtils.getClearShortArray(4));

		start = System.nanoTime();
		Rectangle bounds = vector.getGlyphPixelBounds(0, ((Graphics2D) image.getGraphics()).getFontRenderContext(), 0, 0);
		double widthRatio = image.getWidth() / bounds.getWidth();
		double heightRatio = image.getHeight() / bounds.getHeight();
		double ratio = Math.min(widthRatio, heightRatio);
		bounds.width = (int) (bounds.width * ratio); bounds.height = (int) (bounds.height * ratio);
		int stride = image.getSampleModel().getNumBands();
		ImageUtils.cutImage(pixels, image.getWidth() / 2 - bounds.width / 2, image.getHeight() / 2 - bounds.height / 2, image.getWidth(), image.getHeight(), pixels, 0, 0, image.getWidth(), image.getHeight(), bounds.width, bounds.height, stride, ArrayUtils.getEmptyShortArray(-1));
		ImageUtils.clearPixels(pixels, bounds.width, 0, image.getWidth(), image.getHeight(), image.getWidth() - bounds.width, image.getHeight(), stride);
		ImageUtils.clearPixels(pixels, 0, bounds.height, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight() - bounds.height, stride);
		System.out.println("Done Cutting! Took: " + (float) (System.nanoTime() - start) / 1000000 + "ms");

//		copyToBufferedImage(data, pixels, image.getWidth(), image.getHeight(), 0, 0, 3);

//		int n = 100;
//		long average = 0;
//		for(int i = 0; i < n; i++) {
//			long _start = System.nanoTime();
//			DistanceFieldUtils.clampArray(pixels);
//			long diff = System.nanoTime() - _start;
//			average += diff;
//			System.out.println((float) diff / 1000000 + "ms");
//		}
//		System.out.println("avg: " + (float) average / n / 1000000 + "ms");

//		Rectangle shapeBounds = shape.getBounds();
//		Graphics2D graphics = (Graphics2D) image.getGraphics();
//		graphics.setColor(new Color(0, 0, 0, 128));
//		graphics.fillRect(image.getWidth() / 2 - shapeBounds.width / 2, image.getHeight() / 2 - shapeBounds.height / 2, shapeBounds.width, shapeBounds.height);
//		int width = (int) (((double) shapeBounds.width / shapeBounds.height * image.getHeight()));
//		graphics.fillRect(image.getWidth() / 2 - width / 2, 0, width, image.getHeight());

//		BufferedImage image2 = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
//		pixels = ((DataBufferInt) image2.getRaster().getDataBuffer()).getData();
//		copyToBufferedImage(data, pixels, image.getWidth(), image.getHeight(), 100 * image2.getHeight() + 100, image2.getWidth() - image.getWidth(), 3);
	}

	public static void copyToBufferedImage(float[] data, int[] imageRaster, int w, int h, int start, int stride, int channels) {
		if(channels <= 0 || channels > 4) throw new IllegalArgumentException("Invalid channel!");
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				int i = y * w + x; int j = channels * i; int r, g, b, a = 255;
				r = g = b = (int) clamp(data[j] * 256, 0, 255);
				if(channels >= 2) g = b = (int) clamp(data[j + 1] * 256, 0, 255);
				if(channels >= 3) b = (int) clamp(data[j + 2] * 256, 0, 255);
				if(channels >= 4) a = (int) clamp(data[j + 3] * 256, 0, 255);
				imageRaster[start + stride * y + i] = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
			}
		}
	}

	static String getPathString(Shape shape) {
		double[] coords = new double[6];
		StringBuilder pathStringBuilder = new StringBuilder();
		for (PathIterator pathIterator = shape.getPathIterator(new AffineTransform()); !pathIterator.isDone(); pathIterator.next()) {
			int type = pathIterator.currentSegment(coords);
//			for(int i = 0; i < coords.length; i++) coords[i] *= 10;
			if (type == PathIterator.SEG_MOVETO) {
				pathStringBuilder.append("M");
				pathStringBuilder.append(coords[0] + "," + coords[1]);
				pathStringBuilder.append(" ");
			} else if (type == PathIterator.SEG_LINETO) {
				pathStringBuilder.append("L");
				pathStringBuilder.append(coords[0] + "," + coords[1]);
				pathStringBuilder.append(" ");
			} else if (type == PathIterator.SEG_QUADTO) {
				pathStringBuilder.append("Q");
				pathStringBuilder.append(coords[0] + "," + coords[1] + "," + coords[2] + "," + coords[3]);
				pathStringBuilder.append(" ");
			} else if (type == PathIterator.SEG_CUBICTO) {
				pathStringBuilder.append("C");
				pathStringBuilder.append(coords[0] + "," + coords[1] + "," + coords[2]+ "," + coords[3] + "," + coords[4] + "," + coords[5]);
				pathStringBuilder.append(" ");
			} else if (type == PathIterator.SEG_CLOSE) {
				pathStringBuilder.append("Z");
				pathStringBuilder.append(" ");
			}
		} return pathStringBuilder.toString();
	}

	public static void generateSDF(float[] output, int w, int h, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		if(overlapSupport) generateDistanceField(output, w, h, shape, range, scale, translate, new OverlappingContourCombiner<>(shape, new TrueDistanceSelector()));
    	else generateDistanceField(output, w, h, shape, range, scale, translate, new SimpleContourCombiner<>(shape, new TrueDistanceSelector()));
	}
	public static void generatePseudoSDF(float[] output, int w, int h, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		if(overlapSupport) generateDistanceField(output, w, h, shape, range, scale, translate, new OverlappingContourCombiner<>(shape, new PseudoDistanceSelector()));
    	else generateDistanceField(output, w, h, shape, range, scale, translate, new SimpleContourCombiner<>(shape, new PseudoDistanceSelector()));
	}
	public static void generateMSDF(float[] output, int w, int h, ContourShape shape, double range, Vec2 scale, Vec2 translate, double edgeThreshold, boolean overlapSupport) {
		if(overlapSupport) generateDistanceField(output, w, h, shape, range, scale, translate, new OverlappingContourCombiner<>(shape, new MultiDistanceSelector()));
    	else generateDistanceField(output, w, h, shape, range, scale, translate, new SimpleContourCombiner<>(shape, new MultiDistanceSelector()));
		if(edgeThreshold > 0) msdfErrorCorrection(output, w, h, vec2(edgeThreshold / (scale.x() * range), edgeThreshold / (scale.y() * range)));
	}
	public static void generateMSDF(float[] output, int w, int h, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		generateMSDF(output, w, h, shape, range, scale, translate, 0, overlapSupport);
	}
	public static void generateMTSDF(float[] output, int w, int h, ContourShape shape, double range, Vec2 scale, Vec2 translate, double edgeThreshold, boolean overlapSupport) {
		if(overlapSupport) generateDistanceField(output, w, h, shape, range, scale, translate, new OverlappingContourCombiner<>(shape, new MultiAndTrueDistanceSelector()));
    	else generateDistanceField(output, w, h, shape, range, scale, translate, new SimpleContourCombiner<>(shape, new MultiAndTrueDistanceSelector()));
		if(edgeThreshold > 0) msdfErrorCorrection(output, w, h, vec2(edgeThreshold / (scale.x() * range), edgeThreshold / (scale.y() * range)));
	}
	public static void generateMTSDF(float[] output, int w, int h, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		generateMTSDF(output, w, h, shape, range, scale, translate, 0, overlapSupport);
	}

	@SuppressWarnings("unchecked")
	protected static <COMBINER extends ContourCombiner<SELECTOR, DATA>, SELECTOR extends EdgeSelector<SELECTOR, DATA>, DATA extends GenType> void generateDistanceField(float[] output, int w, int h, ContourShape contourShape, double range, Vec2 scale, Vec2 translate, COMBINER combiner) {
		ReferencedCallback.PVoidReferencedCallback convert = (args) -> {
			DATA distance = (DATA) args[0];
			int x = (int) args[1]; int y = (int) args[2];
			int i = distance.getSize() * (y * w + x);
			for(int j = 0; j < distance.getSize(); j++)
				output[i + j] = (float) (((Number) distance.getVectorAt(j)).doubleValue() / range + .5);
		};

		if(!contourShape.validate()) throw new Error("The geometry of the loaded shape is invalid.");
		contourShape.normalize(); int edgeCount = contourShape.edgeCount();
		SELECTOR selector = combiner.create();
		ArrayList<SELECTOR> shapeEdgeCache = new ArrayList<>(edgeCount);
		for(int i = 0; i < edgeCount; i++) shapeEdgeCache.add(selector.create());
		boolean rightToLeft = false; Vec2 p = new Vec2();
		for(int y = 0; y < h; y++) {
			int row = contourShape.isInverseYAxis() ? h - y - 1 : y;
			p.y((y + .5) / scale.y() - translate.y());
			for(int col = 0; col < w; col++) {
				int x = rightToLeft ? w - col - 1 : col;
				p.x((x + .5) / scale.x() - translate.x());
				combiner.reset(p); int j = 0;
				ArrayList<Contour> contours = contourShape.getContours();
				for(int i = 0; i < contours.size(); i++) { Contour contour = contours.get(i);
					ArrayList<EdgeSegment<?>> edges = contour.getEdges(); if(edges.isEmpty()) continue;
					SELECTOR edgeSelector = combiner.edgeSelector(i);
					EdgeSegment<?> prevEdge = edges.size() >= 2 ? edges.get(edges.size() - 2) : edges.get(0);
					EdgeSegment<?> curEdge = edges.get(edges.size() - 1);
					for(EdgeSegment<?> edge : edges) {
						edgeSelector.addEdge(shapeEdgeCache.get(j++), prevEdge, curEdge, edge);
						prevEdge = curEdge; curEdge = edge;
					}
				}
				DATA distance = combiner.distance();
				convert.get(distance, x, row);
			} rightToLeft = !rightToLeft;
		}
	}

	protected static void msdfErrorCorrection(float[] output, int w, int h, Vec2 threshold) {
		class ClashesPair {
			int x; int y;
			public ClashesPair(int x, int y) {
				this.x = x;
				this.y = y;
			}
		}
		ArrayList<ClashesPair> clashes = new ArrayList<>();
		for(int y = 0; y < h; ++y)
			for(int x = 0; x < w; ++x) {
				if (
						(x > 0 && detectClash(output, output, y * w + x, y * w + x - 1, threshold.x())) ||
						(x < w - 1 && detectClash(output, output, y * w + x, y * w + x + 1, threshold.x())) ||
						(y > 0 && detectClash(output, output, y * w + x, (y - 1) * w + x, threshold.y())) ||
						(y < h - 1 && detectClash(output, output, y * w + x, (y + 1) * w + x, threshold.y()))
				) clashes.add(new ClashesPair(x, y));
			}
		for(ClashesPair clash : clashes) {
			int i = clash.y * w + clash.x;
			float med = median(output[i], output[i + 1], output[i + 2]);
			output[i] = output[i + 1] = output[i + 2] = med;
		}
		clashes.clear();
		for (int y = 0; y < h; ++y)
			for (int x = 0; x < w; ++x) {
				if (
						(x > 0 && y > 0 && detectClash(output, output, y * w + x, (y - 1) * w + x - 1, threshold.x() + threshold.y())) ||
						(x < w - 1 && y > 0 && detectClash(output, output, y * w + x, (y - 1) * w + x + 1, threshold.x() + threshold.y())) ||
						(x > 0 && y < h - 1 && detectClash(output, output, y * w + x, (y + 1) * w + x - 1, threshold.x() + threshold.y())) ||
						(x < w - 1 && y < h - 1 && detectClash(output, output, y * w + x, (y + 1) * w + x + 1, threshold.x() + threshold.y()))
				) clashes.add(new ClashesPair(x, y));
			}
		for(ClashesPair clash : clashes) {
			int i = clash.y * w + clash.x;
			float med = median(output[i], output[i + 1], output[i + 2]);
			output[i] = output[i + 1] = output[i + 2] = med;
		}
	}
	protected static boolean detectClash(float[] a, float[] b, int ai, int bi, double threshold) {
		float a0 = a[ai], a1 = a[ai + 1], a2 = a[ai + 2];
		float b0 = b[bi], b1 = b[bi + 1], b2 = b[bi + 2];
		float tmp;
		if(abs(b0 - a0) < abs(b1 - a1)) {
			tmp = a0; a0 = a1; a1 = tmp;
			tmp = b0; b0 = b1; b1 = tmp;
		}
		if(abs(b1 - a1) < abs(b2 - a2)) {
			tmp = a1; a1 = a2; a2 = tmp;
			tmp = b1; b1 = b2; b2 = tmp;
			if(abs(b0 - a0) < abs(b1 - a1)) {
				tmp = a0; a0 = a1; a1 = tmp;
				tmp = b0; b0 = b1; b1 = tmp;
			}
		} return (abs(b1 - a1) >= threshold) && !(b0 == b1 && b0 == b2) && abs(a2 - .5f) >= abs(b2 - .5f);
	}
	protected static float median(float a, float b, float c) {
		return (float) max(min(a, b), min(max(a, b), c));
	}
}
