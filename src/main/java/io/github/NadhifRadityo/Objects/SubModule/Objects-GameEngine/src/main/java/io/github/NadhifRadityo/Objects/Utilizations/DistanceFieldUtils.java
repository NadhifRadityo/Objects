package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.GenType;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec1;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Combiner.ContourCombiner;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Combiner.OverlappingContourCombiner;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Combiner.SimpleContourCombiner;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Contour;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.ContourShape;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.ScanLine;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeSegment;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.EdgeSelector;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.MultiAndTrueDistanceSelector;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.MultiDistanceSelector;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.PseudoDistanceSelector;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.TrueDistanceSelector;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;

import java.util.ArrayList;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.abs;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.max;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.min;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.vec2;
import static io.github.NadhifRadityo.Objects.Utilizations.ImageUtils.clampArray;
import static io.github.NadhifRadityo.Objects.Utilizations.ImageUtils.flipImage;
import static io.github.NadhifRadityo.Objects.Utilizations.ImageUtils.scaleBands;

@SuppressWarnings({"DuplicatedCode", "unused"})
public class DistanceFieldUtils {
	public static void generateSDF(float[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport, ScanLine.FillRule fillRule) {
		final ReferencedCallback.PVoidReferencedCallback output = (args) -> {
			int i = (int) args[0]; Vec1 distance = (Vec1) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			data[off] = args.length < 3 ? (float) (distance.x() / range + .5) : (float) distance.x();
		};
		final ReferencedCallback.PVoidReferencedCallback input = (args) -> {
			int i = (int) args[0]; Vec1 distance = (Vec1) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			distance.x(data[off]);
		};
		ContourCombiner<TrueDistanceSelector, Vec1> combiner = overlapSupport ? new OverlappingContourCombiner<>(shape, new TrueDistanceSelector()) : new SimpleContourCombiner<>(shape, new TrueDistanceSelector());
		generateDistanceField(output, w, h, shape, scale, translate, combiner); if(fillRule != null) distanceSignCorrection(output, input, w, h, shape, scale, translate, fillRule, combiner);
		clampArray(data, 0, 1, sx, sy, rw, rh, w, h, stride); flipImage(data, sx, sy, rw, rh, w, h, stride);
	}
	public static void generateSDF(float[] output, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		generateSDF(output, sx, sy, rw, rh, w, h, stride, shape, range, scale, translate, overlapSupport, ScanLine.FillRule.FILL_NONZERO);
	}
	public static void generatePseudoSDF(float[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport, ScanLine.FillRule fillRule) {
		final ReferencedCallback.PVoidReferencedCallback output = (args) -> {
			int i = (int) args[0]; Vec1 distance = (Vec1) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			data[off] = args.length < 3 ? (float) (distance.x() / range + .5) : (float) distance.x();
		};
		final ReferencedCallback.PVoidReferencedCallback input = (args) -> {
			int i = (int) args[0]; Vec1 distance = (Vec1) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			distance.x(data[off]);
		};
		ContourCombiner<PseudoDistanceSelector, Vec1> combiner = overlapSupport ? new OverlappingContourCombiner<>(shape, new PseudoDistanceSelector()) : new SimpleContourCombiner<>(shape, new PseudoDistanceSelector());
		generateDistanceField(output, w, h, shape, scale, translate, combiner); if(fillRule != null) distanceSignCorrection(output, input, w, h, shape, scale, translate, fillRule, combiner);
		clampArray(data, 0, 1, sx, sy, rw, rh, w, h, stride); flipImage(data, sx, sy, rw, rh, w, h, stride);
	}
	public static void generatePseudoSDF(float[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		generatePseudoSDF(data, sx, sy, rw, rh, w, h, stride, shape, range, scale, translate, overlapSupport, ScanLine.FillRule.FILL_NONZERO);
	}
	public static void generateMSDF(float[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, double edgeThreshold, boolean overlapSupport, ScanLine.FillRule fillRule) {
		final ReferencedCallback.PVoidReferencedCallback output = (args) -> {
			int i = (int) args[0]; Vec3 distance = (Vec3) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			data[off    ] = args.length < 3 ? (float) (distance.x() / range + .5) : (float) distance.x();
			data[off + 1] = args.length < 3 ? (float) (distance.y() / range + .5) : (float) distance.y();
			data[off + 2] = args.length < 3 ? (float) (distance.z() / range + .5) : (float) distance.z();
		};
		final ReferencedCallback.PVoidReferencedCallback input = (args) -> {
			int i = (int) args[0]; Vec3 distance = (Vec3) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			distance.x(data[off    ]);
			distance.y(data[off + 1]);
			distance.z(data[off + 2]);
		};
		ContourCombiner<MultiDistanceSelector, Vec3> combiner = overlapSupport ? new OverlappingContourCombiner<>(shape, new MultiDistanceSelector()) : new SimpleContourCombiner<>(shape, new MultiDistanceSelector());
		generateDistanceField(output, w, h, shape, scale, translate, combiner); if(fillRule != null) multiDistanceSignCorrection(output, input, w, h, shape, scale, translate, fillRule, combiner);
		if(edgeThreshold > 0) msdfErrorCorrection(output, input, w, h, vec2(edgeThreshold / (scale.x() * range), edgeThreshold / (scale.y() * range)), combiner);
		clampArray(data, 0, 1, sx, sy, rw, rh, w, h, stride); flipImage(data, sx, sy, rw, rh, w, h, stride);
	}
	public static void generateMSDF(float[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport, ScanLine.FillRule fillRule) {
		generateMSDF(data, sx, sy, rw, rh, w, h, stride, shape, range, scale, translate, 1.001, overlapSupport, fillRule);
	}
	public static void generateMSDF(float[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		generateMSDF(data, sx, sy, rw, rh, w, h, stride, shape, range, scale, translate, overlapSupport, ScanLine.FillRule.FILL_NONZERO);
	}
	public static void generateMTSDF(float[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, double edgeThreshold, boolean overlapSupport, ScanLine.FillRule fillRule) {
		final ReferencedCallback.PVoidReferencedCallback output = (args) -> {
			int i = (int) args[0]; Vec4 distance = (Vec4) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			data[off    ] = args.length < 3 ? (float) (distance.x() / range + .5) : (float) distance.x();
			data[off + 1] = args.length < 3 ? (float) (distance.y() / range + .5) : (float) distance.y();
			data[off + 2] = args.length < 3 ? (float) (distance.z() / range + .5) : (float) distance.z();
			data[off + 3] = args.length < 3 ? (float) (distance.w() / range + .5) : (float) distance.w();
		};
		final ReferencedCallback.PVoidReferencedCallback input = (args) -> {
			int i = (int) args[0]; Vec4 distance = (Vec4) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			distance.x(data[off    ]);
			distance.y(data[off + 1]);
			distance.z(data[off + 2]);
			distance.w(data[off + 3]);
		};
		ContourCombiner<MultiAndTrueDistanceSelector, Vec4> combiner = overlapSupport ? new OverlappingContourCombiner<>(shape, new MultiAndTrueDistanceSelector()) : new SimpleContourCombiner<>(shape, new MultiAndTrueDistanceSelector());
		generateDistanceField(output, w, h, shape, scale, translate, combiner); if(fillRule != null) multiDistanceSignCorrection(output, input, w, h, shape, scale, translate, fillRule, combiner);
		if(edgeThreshold > 0) msdfErrorCorrection(output, input, w, h, vec2(edgeThreshold / (scale.x() * range), edgeThreshold / (scale.y() * range)), combiner);
		clampArray(data, 0, 1, sx, sy, rw, rh, w, h, stride); flipImage(data, sx, sy, rw, rh, w, h, stride);
	}
	public static void generateMTSDF(float[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport, ScanLine.FillRule fillRule) {
		generateMTSDF(data, sx, sy, rw, rh, w, h, stride, shape, range, scale, translate, 1.001, overlapSupport, fillRule);
	}
	public static void generateMTSDF(float[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		generateMTSDF(data, sx, sy, rw, rh, w, h, stride, shape, range, scale, translate, overlapSupport, ScanLine.FillRule.FILL_NONZERO);
	}

	public static void generateSDF(short[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport, ScanLine.FillRule fillRule) {
		final ReferencedCallback.PVoidReferencedCallback output = (args) -> {
			int i = (int) args[0]; Vec1 distance = (Vec1) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			data[off] = (short) ((args.length < 3 ? (float) (distance.x() / range + .5) : (float) distance.x()) * 256);
		};
		final ReferencedCallback.PVoidReferencedCallback input = (args) -> {
			int i = (int) args[0]; Vec1 distance = (Vec1) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			distance.x((double) data[off] / 256);
		};
		ContourCombiner<TrueDistanceSelector, Vec1> combiner = overlapSupport ? new OverlappingContourCombiner<>(shape, new TrueDistanceSelector()) : new SimpleContourCombiner<>(shape, new TrueDistanceSelector());
		generateDistanceField(output, w, h, shape, scale, translate, combiner); if(fillRule != null) distanceSignCorrection(output, input, w, h, shape, scale, translate, fillRule, combiner);
		clampArray(data, (short) 0, (short) 255, sx, sy, rw, rh, w, h, stride); scaleBands(data, (double) Short.MAX_VALUE / 255, sx, sy, rw, rh, w, h, stride); flipImage(data, sx, sy, rw, rh, w, h, stride);
	}
	public static void generateSDF(short[] output, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		generateSDF(output, sx, sy, rw, rh, w, h, stride, shape, range, scale, translate, overlapSupport, ScanLine.FillRule.FILL_NONZERO);
	}
	public static void generatePseudoSDF(short[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport, ScanLine.FillRule fillRule) {
		final ReferencedCallback.PVoidReferencedCallback output = (args) -> {
			int i = (int) args[0]; Vec1 distance = (Vec1) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			data[off] = (short) ((args.length < 3 ? (float) (distance.x() / range + .5) : (float) distance.x()) * 256);
		};
		final ReferencedCallback.PVoidReferencedCallback input = (args) -> {
			int i = (int) args[0]; Vec1 distance = (Vec1) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			distance.x((double) data[off] / 256);
		};
		ContourCombiner<PseudoDistanceSelector, Vec1> combiner = overlapSupport ? new OverlappingContourCombiner<>(shape, new PseudoDistanceSelector()) : new SimpleContourCombiner<>(shape, new PseudoDistanceSelector());
		generateDistanceField(output, w, h, shape, scale, translate, combiner); if(fillRule != null) distanceSignCorrection(output, input, w, h, shape, scale, translate, fillRule, combiner);
		clampArray(data, (short) 0, (short) 255, sx, sy, rw, rh, w, h, stride); scaleBands(data, (double) Short.MAX_VALUE / 255, sx, sy, rw, rh, w, h, stride); flipImage(data, sx, sy, rw, rh, w, h, stride);
	}
	public static void generatePseudoSDF(short[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		generatePseudoSDF(data, sx, sy, rw, rh, w, h, stride, shape, range, scale, translate, overlapSupport, ScanLine.FillRule.FILL_NONZERO);
	}
	public static void generateMSDF(short[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, double edgeThreshold, boolean overlapSupport, ScanLine.FillRule fillRule) {
		final ReferencedCallback.PVoidReferencedCallback output = (args) -> {
			int i = (int) args[0]; Vec3 distance = (Vec3) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			data[off    ] = (short) ((args.length < 3 ? (float) (distance.x() / range + .5) : (float) distance.x()) * 256);
			data[off + 1] = (short) ((args.length < 3 ? (float) (distance.y() / range + .5) : (float) distance.y()) * 256);
			data[off + 2] = (short) ((args.length < 3 ? (float) (distance.z() / range + .5) : (float) distance.z()) * 256);
		};
		final ReferencedCallback.PVoidReferencedCallback input = (args) -> {
			int i = (int) args[0]; Vec3 distance = (Vec3) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			distance.x((double) data[off    ] / 256);
			distance.y((double) data[off + 1] / 256);
			distance.z((double) data[off + 2] / 256);
		};
		ContourCombiner<MultiDistanceSelector, Vec3> combiner = overlapSupport ? new OverlappingContourCombiner<>(shape, new MultiDistanceSelector()) : new SimpleContourCombiner<>(shape, new MultiDistanceSelector());
		generateDistanceField(output, w, h, shape, scale, translate, combiner); if(fillRule != null) multiDistanceSignCorrection(output, input, w, h, shape, scale, translate, fillRule, combiner);
		if(edgeThreshold > 0) msdfErrorCorrection(output, input, w, h, vec2(edgeThreshold / (scale.x() * range), edgeThreshold / (scale.y() * range)), combiner);
		clampArray(data, (short) 0, (short) 255, sx, sy, rw, rh, w, h, stride); scaleBands(data, (double) Short.MAX_VALUE / 255, sx, sy, rw, rh, w, h, stride); flipImage(data, sx, sy, rw, rh, w, h, stride);
	}
	public static void generateMSDF(short[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport, ScanLine.FillRule fillRule) {
		generateMSDF(data, sx, sy, rw, rh, w, h, stride, shape, range, scale, translate, 1.001, overlapSupport, fillRule);
	}
	public static void generateMSDF(short[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		generateMSDF(data, sx, sy, rw, rh, w, h, stride, shape, range, scale, translate, overlapSupport, ScanLine.FillRule.FILL_NONZERO);
	}
	public static void generateMTSDF(short[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, double edgeThreshold, boolean overlapSupport, ScanLine.FillRule fillRule) {
		final ReferencedCallback.PVoidReferencedCallback output = (args) -> {
			int i = (int) args[0]; Vec4 distance = (Vec4) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			data[off    ] = (short) ((args.length < 3 ? (float) (distance.x() / range + .5) : (float) distance.x()) * 256);
			data[off + 1] = (short) ((args.length < 3 ? (float) (distance.y() / range + .5) : (float) distance.y()) * 256);
			data[off + 2] = (short) ((args.length < 3 ? (float) (distance.z() / range + .5) : (float) distance.z()) * 256);
			data[off + 3] = (short) ((args.length < 3 ? (float) (distance.w() / range + .5) : (float) distance.w()) * 256);
		};
		final ReferencedCallback.PVoidReferencedCallback input = (args) -> {
			int i = (int) args[0]; Vec4 distance = (Vec4) args[1];
			int ix = i % w; int iy = i / w;
			int off = stride * ((sy * rw + sx) + (iy * rw + ix));
			distance.x((double) data[off    ] / 256);
			distance.y((double) data[off + 1] / 256);
			distance.z((double) data[off + 2] / 256);
			distance.w((double) data[off + 3] / 256);
		};
		ContourCombiner<MultiAndTrueDistanceSelector, Vec4> combiner = overlapSupport ? new OverlappingContourCombiner<>(shape, new MultiAndTrueDistanceSelector()) : new SimpleContourCombiner<>(shape, new MultiAndTrueDistanceSelector());
		generateDistanceField(output, w, h, shape, scale, translate, combiner); if(fillRule != null) multiDistanceSignCorrection(output, input, w, h, shape, scale, translate, fillRule, combiner);
		if(edgeThreshold > 0) msdfErrorCorrection(output, input, w, h, vec2(edgeThreshold / (scale.x() * range), edgeThreshold / (scale.y() * range)), combiner);
		clampArray(data, (short) 0, (short) 255, sx, sy, rw, rh, w, h, stride); scaleBands(data, (double) Short.MAX_VALUE / 255, sx, sy, rw, rh, w, h, stride); flipImage(data, sx, sy, rw, rh, w, h, stride);
	}
	public static void generateMTSDF(short[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport, ScanLine.FillRule fillRule) {
		generateMTSDF(data, sx, sy, rw, rh, w, h, stride, shape, range, scale, translate, 1.001, overlapSupport, fillRule);
	}
	public static void generateMTSDF(short[] data, int sx, int sy, int rw, int rh, int w, int h, int stride, ContourShape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		generateMTSDF(data, sx, sy, rw, rh, w, h, stride, shape, range, scale, translate, overlapSupport, ScanLine.FillRule.FILL_NONZERO);
	}

	/*
	 * args[0] -> index
	 * args[1] -> DATA (*GenType)
	 */
	public static void generateSDF(ReferencedCallback.VoidReferencedCallback output, int w, int h, ContourShape shape, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		if(overlapSupport) generateDistanceField(output, w, h, shape, scale, translate, new OverlappingContourCombiner<>(shape, new TrueDistanceSelector()));
		else generateDistanceField(output, w, h, shape, scale, translate, new SimpleContourCombiner<>(shape, new TrueDistanceSelector()));
	}
	public static void generatePseudoSDF(ReferencedCallback.VoidReferencedCallback output, int w, int h, ContourShape shape, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		if(overlapSupport) generateDistanceField(output, w, h, shape, scale, translate, new OverlappingContourCombiner<>(shape, new PseudoDistanceSelector()));
		else generateDistanceField(output, w, h, shape, scale, translate, new SimpleContourCombiner<>(shape, new PseudoDistanceSelector()));
	}
	public static void generateMSDF(ReferencedCallback.VoidReferencedCallback output, int w, int h, ContourShape shape, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		if(overlapSupport) generateDistanceField(output, w, h, shape, scale, translate, new OverlappingContourCombiner<>(shape, new MultiDistanceSelector()));
		else generateDistanceField(output, w, h, shape, scale, translate, new SimpleContourCombiner<>(shape, new MultiDistanceSelector()));
	}
	public static void generateMTSDF(ReferencedCallback.VoidReferencedCallback output, int w, int h, ContourShape shape, Vec2 scale, Vec2 translate, boolean overlapSupport) {
		if(overlapSupport) generateDistanceField(output, w, h, shape, scale, translate, new OverlappingContourCombiner<>(shape, new MultiAndTrueDistanceSelector()));
		else generateDistanceField(output, w, h, shape, scale, translate, new SimpleContourCombiner<>(shape, new MultiAndTrueDistanceSelector()));
	}

	public static void autoFrame(Vec4 bounds, double sizeX, double sizeY, Vec2 scale, Vec2 translate, Vec1 range) {
		sizeX -= 2; sizeY -= 2;
		if(bounds.x() >= bounds.z() || bounds.y() >= bounds.w()) {
			bounds.x(0); bounds.y(0);
			bounds.z(1); bounds.w(1);
		}
		if(sizeX <= 0 || sizeY <= 0)
			throw new IllegalArgumentException("Cannot fit the specified pixel range.");
		Vec2 dimensions = new Vec2(bounds.z() - bounds.x(), bounds.w() - bounds.y());
		if(dimensions.x() * sizeY < dimensions.y() * sizeX) {
			translate.set((sizeX / sizeY * dimensions.y() - dimensions.x()) / 2 - bounds.x(), -bounds.y());
			scale.set(sizeY / dimensions.y(), sizeY / dimensions.y());
		} else {
			translate.set(-bounds.x(), (sizeY / sizeX * dimensions.x() - dimensions.y()) / 2 - bounds.y());
			scale.set(sizeX / dimensions.x(), sizeX / dimensions.x());
		} translate.set(translate.x() + 1 / scale.x(), translate.y() + 1 / scale.y());
		range.set(2 / min(scale.x(), scale.y()));
	}
	public static void autoFrame(Vec4 bounds, Vec2 size, Vec2 scale, Vec2 translate, Vec1 range) {
		autoFrame(bounds, size.x(), size.y(), scale, translate, range);
	}

	public static <COMBINER extends ContourCombiner<SELECTOR, DATA>, SELECTOR extends EdgeSelector<SELECTOR, DATA>, DATA extends GenType> void generateDistanceField(ReferencedCallback.VoidReferencedCallback output, int w, int h, ContourShape contourShape, Vec2 scale, Vec2 translate, COMBINER combiner) {
		if(!contourShape.validate()) throw new Error("The geometry of the loaded shape is invalid.");
		contourShape.normalize(); int edgeCount = contourShape.edgeCount();
		SELECTOR selector = combiner.create();
		ArrayList<SELECTOR> shapeEdgeCache = new ArrayList<>(edgeCount);
		for(int i = 0; i < edgeCount; i++) shapeEdgeCache.add(selector.create());
		boolean rightToLeft = false; Vec2 p = new Vec2();
//		Pool.startLocalPool(); PoolCleaner.start();
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
				DATA data = combiner.distance();
				output.get(row * w + x, data);
			} rightToLeft = !rightToLeft;
		} //PoolCleaner.end(); Pool.endLocalPool();
	}

	protected static final Object postProcessIdentifier = new Object();
	public static <COMBINER extends ContourCombiner<SELECTOR, DATA>, SELECTOR extends EdgeSelector<SELECTOR, DATA>, DATA extends GenType> void msdfErrorCorrection(ReferencedCallback.VoidReferencedCallback output, ReferencedCallback.VoidReferencedCallback input, int w, int h, Vec2 threshold, COMBINER combiner) {
		DATA data = combiner.createData();
		DATA data2 = combiner.createData();
		int channels = (data.getSize() + data2.getSize()) / 2;
		if(channels != 3 && channels != 4) return;
		ArrayList<Long> clashes = new ArrayList<>();
		for(int y = 0; y < h; y++)
			for(int x = 0; x < w; x++) {
				if (
						(x > 0 && detectClash(output, input, y * w + x, y * w + x - 1, data, data2, channels, threshold.x()))       ||
						(x < w - 1 && detectClash(output, input, y * w + x, y * w + x + 1, data, data2, channels, threshold.x()))   ||
						(y > 0 && detectClash(output, input, y * w + x, (y - 1) * w + x, data, data2, channels, threshold.y()))     ||
						(y < h - 1 && detectClash(output, input, y * w + x, (y + 1) * w + x, data, data2, channels, threshold.y()))
				) clashes.add((((long) x) << 32) | (y & 0xffffffffL));
			}
		for(Long clash : clashes) {
			int i = (int) (clash.intValue() * w + (clash >> 32)); input.get(i, data);
			float dx = ((Number) data.getVectorAt(0)).floatValue();
			float dy = ((Number) data.getVectorAt(1)).floatValue();
			float dz = ((Number) data.getVectorAt(2)).floatValue();
			float med = median(dx, dy, dz);
			data2.setVectorAt(0, (double) med);
			data2.setVectorAt(1, (double) med);
			data2.setVectorAt(2, (double) med);
			if(channels >= 4)
				data2.setVectorAt(3, (double) ((Number) data.getVectorAt(3)).floatValue());
			output.get(i, data2, postProcessIdentifier);
		}
		clashes.clear();
		for (int y = 0; y < h; y++)
			for (int x = 0; x < w; x++) {
				if (
						(x > 0 && y > 0 && detectClash(output, input, y * w + x, (y - 1) * w + x - 1, data, data2, channels, threshold.x() + threshold.y()))         ||
						(x < w - 1 && y > 0 && detectClash(output, input, y * w + x, (y - 1) * w + x + 1, data, data2, channels, threshold.x() + threshold.y()))     ||
						(x > 0 && y < h - 1 && detectClash(output, input, y * w + x, (y + 1) * w + x - 1, data, data2, channels, threshold.x() + threshold.y()))     ||
						(x < w - 1 && y < h - 1 && detectClash(output, input, y * w + x, (y + 1) * w + x + 1, data, data2, channels, threshold.x() + threshold.y()))
				) clashes.add((((long) x) << 32) | (y & 0xffffffffL));
			}
		for(Long clash : clashes) {
			int i = (int) (clash.intValue() * w + (clash >> 32)); input.get(i, data);
			float dx = ((Number) data.getVectorAt(0)).floatValue();
			float dy = ((Number) data.getVectorAt(1)).floatValue();
			float dz = ((Number) data.getVectorAt(2)).floatValue();
			float med = median(dx, dy, dz);
			data2.setVectorAt(0, (double) med);
			data2.setVectorAt(1, (double) med);
			data2.setVectorAt(2, (double) med);
			if(channels >= 4)
				data2.setVectorAt(3, (double) ((Number) data.getVectorAt(3)).floatValue());
			output.get(i, data2, postProcessIdentifier);
		}
	}
	protected static <COMBINER extends ContourCombiner<SELECTOR, DATA>, SELECTOR extends EdgeSelector<SELECTOR, DATA>, DATA extends GenType> boolean detectClash(ReferencedCallback.VoidReferencedCallback output, ReferencedCallback.VoidReferencedCallback input, int ai, int bi, DATA data, DATA data2, int channels, double threshold) {
		if(channels != 3 && channels != 4) return false;
		input.get(ai, data); input.get(bi, data2);
		float a0 = ((Number) data.getVectorAt(0)).floatValue(), a1 = ((Number) data.getVectorAt(1)).floatValue(), a2 = ((Number) data.getVectorAt(2)).floatValue();
		float b0 = ((Number) data2.getVectorAt(0)).floatValue(), b1 = ((Number) data2.getVectorAt(1)).floatValue(), b2 = ((Number) data2.getVectorAt(2)).floatValue();
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

	public static <COMBINER extends ContourCombiner<SELECTOR, DATA>, SELECTOR extends EdgeSelector<SELECTOR, DATA>, DATA extends GenType> void distanceSignCorrection(ReferencedCallback.VoidReferencedCallback output, ReferencedCallback.VoidReferencedCallback input, int w, int h, ContourShape shape, Vec2 scale, Vec2 translate, ScanLine.FillRule fillRule, COMBINER combiner) {
		DATA data = combiner.createData();
		DATA data2 = combiner.createData();
		int channels = (data.getSize() + data2.getSize()) / 2;
		if(channels != 1) return;
		Vec2 p = new Vec2();
		ScanLine scanline = new ScanLine();
		for(int y = 0; y < h; y++) {
			int row = shape.isInverseYAxis() ? h - y - 1 : y;
			p.y((y + .5) / scale.y() - translate.y());
			shape.scanline(scanline, p.y());
			for(int x = 0; x < w; x++) {
				p.x((x + .5) / scale.x() - translate.x());
				boolean fill = scanline.filled(p.x(), fillRule);
				int i = row * w + x; input.get(i, data);
				float dx = ((Number) data.getVectorAt(0)).floatValue();
				if((dx > .5) != fill) {
					data2.setVectorAt(0, (double) 1 - dx);
					output.get(i, data2, postProcessIdentifier);
				}
			}
		}
	}
	public static <COMBINER extends ContourCombiner<SELECTOR, DATA>, SELECTOR extends EdgeSelector<SELECTOR, DATA>, DATA extends GenType> void multiDistanceSignCorrection(ReferencedCallback.VoidReferencedCallback output, ReferencedCallback.VoidReferencedCallback input, int w, int h, ContourShape shape, Vec2 scale, Vec2 translate, ScanLine.FillRule fillRule, COMBINER combiner) {
		DATA data = combiner.createData();
		DATA data2 = combiner.createData();
		int channels = (data.getSize() + data2.getSize()) / 2;
		if(channels != 3 && channels != 4) return;
		if(w * h == 0) return;
		Vec2 p = new Vec2();
		ScanLine scanline = new ScanLine();
		boolean ambiguous = false;
		short[] matchMap = new short[w * h];
		int matchMapIndex = 0;
		for(int y = 0; y < h; y++) {
			int row = shape.isInverseYAxis() ? h - y - 1 : y;
			p.y((y + .5) / scale.y() - translate.y());
			shape.scanline(scanline, p.y());
			for(int x = 0; x < w; x++) {
				p.x((x + .5) / scale.x() - translate.x());
				boolean fill = scanline.filled(p.x(), fillRule);
				int i = row * w + x; input.get(i, data);
				float dx = ((Number) data.getVectorAt(0)).floatValue();
				float dy = ((Number) data.getVectorAt(1)).floatValue();
				float dz = ((Number) data.getVectorAt(2)).floatValue();
				float sd = median(dx, dy, dz);
				if(sd == .5f) ambiguous = true;
				else if((sd > .5f) != fill) {
					data2.setVectorAt(0, (double) 1 - dx);
					data2.setVectorAt(1, (double) 1 - dy);
					data2.setVectorAt(2, (double) 1 - dz);
					if(channels >= 4) {
						float dw = ((Number) data.getVectorAt(3)).floatValue();
						data2.setVectorAt(3, (double) ((dw > .5f) != fill ? 1 - dw : dw));
					} output.get(i, data2, postProcessIdentifier);
					matchMap[matchMapIndex++] = -1;
				} else matchMap[matchMapIndex++] = 1;
			}
		}

		if(!ambiguous) return;
		matchMapIndex = 0;
		for(int y = 0; y < h; y++) {
			int row = shape.isInverseYAxis() ? h - y - 1 : y;
			for(int x = 0; x < w; x++) {
				if(matchMap[matchMapIndex] != 0) { matchMapIndex++; continue; }
				int neighborMatch = 0;
				if(x > 0) neighborMatch += matchMap[matchMapIndex - 1];
				if(x < w - 1) neighborMatch += matchMap[matchMapIndex + 1];
				if(y > 0) neighborMatch += matchMap[matchMapIndex - w];
				if(y < h - 1) neighborMatch += matchMap[matchMapIndex + w];
				if(neighborMatch < 0) {
					int i = row * w + x; input.get(i, data);
					float dx = ((Number) data.getVectorAt(0)).floatValue();
					float dy = ((Number) data.getVectorAt(1)).floatValue();
					float dz = ((Number) data.getVectorAt(2)).floatValue();
					data2.setVectorAt(0, (double) 1 - dx);
					data2.setVectorAt(1, (double) 1 - dy);
					data2.setVectorAt(2, (double) 1 - dz);
					output.get(i, data2, postProcessIdentifier);
				} matchMapIndex++;
			}
		}
	}
}
