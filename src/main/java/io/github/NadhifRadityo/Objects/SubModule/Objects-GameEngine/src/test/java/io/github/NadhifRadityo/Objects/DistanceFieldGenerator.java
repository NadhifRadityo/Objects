package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Proxy;
import io.github.NadhifRadityo.Objects.Utilizations.FontUtils;

import java.awt.*;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.*;
import static java.lang.Math.PI;

@SuppressWarnings({"SameParameterValue", "unused"})
public class DistanceFieldGenerator {
	public static void main(String... args) {
		BufferedImage image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
		Font font = new Font("Fira Code", Font.PLAIN, 12);
		Shape shape = FontUtils.getVector(font, (Graphics2D) image.getGraphics(), "A").getGlyphOutline(0);
		generateDistanceFieldTrueDistance(image, shape, 4.0, vec2(1), vec2(4));
	}

	void generateSDF(BufferedImage output, Shape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {

	}
	void generateSDF(BufferedImage output, Shape shape, double range, Vec2 scale, Vec2 translate) { generateSDF(output, shape, range, scale, translate, true); }

	void generatePseudoSDF(BufferedImage output, Shape shape, double range, Vec2 scale, Vec2 translate, boolean overlapSupport) {

	}
	void generatePseudoSDF(BufferedImage output, Shape shape, double range, Vec2 scale, Vec2 translate) { generatePseudoSDF(output, shape, range, scale, translate, true); }

	void generateMSDF(BufferedImage output, Shape shape, double range, Vec2 scale, Vec2 translate, double edgeThreshold, boolean overlapSupport) {

	}
	void generateMSDF(BufferedImage output, Shape shape, double range, Vec2 scale, Vec2 translate, double edgeThreshold) { generateMSDF(output, shape, range, scale, translate, edgeThreshold, true); }
	void generateMSDF(BufferedImage output, Shape shape, double range, Vec2 scale, Vec2 translate) { generateMSDF(output, shape, range, scale, translate, 1.001); }

	void generateMTSDF(BufferedImage output, Shape shape, double range, Vec2 scale, Vec2 translate, double edgeThreshold, boolean overlapSupport) {

	}
	void generateMTSDF(BufferedImage output, Shape shape, double range, Vec2 scale, Vec2 translate, double edgeThreshold) { generateMTSDF(output, shape, range, scale, translate, edgeThreshold, true); }
	void generateMTSDF(BufferedImage output, Shape shape, double range, Vec2 scale, Vec2 translate) { generateMTSDF(output, shape, range, scale, translate, 1.001); }

	static void generateDistanceFieldTrueDistance(BufferedImage output, Shape shape, double range, Vec2 scale, Vec2 translate) {
		ReferencedCallback.PVoidReferencedCallback convert = (args) -> {
			int[] pixels = (int[]) args[0];
			double distance = (double) args[1];
			double _range = (double) args[2];
			int i = (int) args[3];
			pixels[0] = pixels[1] = pixels[2] = (int) ((distance / _range + .5) * 255);
		};

		int[] pixels = ((DataBufferInt) output.getRaster().getDataBuffer()).getData();
		int edgeCount = countShape(shape);
		ContourShape contourShape = ContourShape.convertFromShape(shape);
		ContourCombiner contourCombiner = new OverlappingContourCombiner(contourShape);
		ArrayList<EdgeSelector> shapeEdgeCache = new ArrayList<>(edgeCount);
		boolean rightToLeft = false; Vec2 p = new Vec2();
		for(int y = 0; y < output.getHeight(); ++y) {
			int row = contourShape.inverseYAxis ? output.getHeight() - y : y;
			p.y((y + .5) / scale.y() - translate.y());
			for(int col = 0; col < output.getWidth(); ++col) {
				int x = rightToLeft ? output.getWidth() - col : col;
				p.x((x + .5) / scale.x() - translate.x());
				contourCombiner.reset(p); int j = 0;

				for(int i = 0; i < contourShape.contours.size(); i++) {
					Contour contour = contourShape.contours.get(i);
					if(contour.edges.isEmpty()) continue;
					EdgeSelector edgeSelector = contourCombiner.edgeSelector(i);
					EdgeSegment prevEdge = contour.edges.size() >= 2 ? contour.edges.get(contour.edges.size() - 2).get() : contour.edges.get(0).get();
					EdgeSegment curEdge = contour.edges.get(contour.edges.size() - 1).get();
					for(EdgeHolder edge : contour.edges) {
						EdgeSegment nextEdge = edge.get();
						edgeSelector.addEdge(shapeEdgeCache.get(j++), prevEdge, curEdge, nextEdge);
						prevEdge = curEdge;
						curEdge = nextEdge;
					}
				}

				double distance = contourCombiner.distance();
				convert.get(pixels, distance, range, row * output.getWidth() + x);
			} rightToLeft = !rightToLeft;
		}
	}

	public static class ContourShape {
		protected ArrayList<Contour> contours = new ArrayList<>();
		protected boolean inverseYAxis;

		public static ContourShape convertFromShape(Shape shape) {
			PathIterator pathIterator = shape.getPathIterator(null);
			double[] coordinates = new double[6];
			double x = 0, y = 0;

			ContourShape contourShape = new ContourShape();
			Contour contour = null;
			while(!pathIterator.isDone()) {
				int code = pathIterator.currentSegment(coordinates);
				double x1 = coordinates[0];
 				double y1 = coordinates[1];
				double x2 = coordinates[2];
				double y2 = coordinates[3];
				double x3 = coordinates[4];
				double y3 = coordinates[5];
				switch(code) {
					case PathIterator.SEG_MOVETO: {
						if(contour == null || contour.edges.isEmpty()) {
							contour = new Contour();
							contourShape.addContour(contour);
						} x = x1; y = y1; break;
					}
					case PathIterator.SEG_LINETO: {
						contour.addEdge(new EdgeHolder(new EdgeSegment.LinearSegment(vec2(x, y), vec2(x1, y1), EdgeSegment.EdgeColor.WHITE)));
						x = x1; y = y1; break;
					}
					case PathIterator.SEG_QUADTO: {
						contour.addEdge(new EdgeHolder(new EdgeSegment.QuadraticSegment(vec2(x, y), vec2(x1, y1), vec2(x2, y2), EdgeSegment.EdgeColor.WHITE)));
						x = x2; y = y2; break;
					}
					case PathIterator.SEG_CUBICTO: {
						contour.addEdge(new EdgeHolder(new EdgeSegment.CubicSegment(vec2(x, y), vec2(x1, y1), vec2(x2, y2), vec2(x3, y3), EdgeSegment.EdgeColor.WHITE)));
						x = x3; y = y3; break;
					}
				} pathIterator.next();
			} return contourShape;
		}

		public void addContour(Contour contour) { contours.add(contour); }
		public void normalize() {
			ReferencedCallback<EdgeSegment[]> splitInThirds = (args) -> {
				EdgeSegment edgeSegment = (EdgeSegment) args[0];
				EdgeSegment part1 = edgeSegment instanceof EdgeSegment.LinearSegment ? new EdgeSegment.LinearSegment() : edgeSegment instanceof EdgeSegment.QuadraticSegment ? new EdgeSegment.QuadraticSegment() : new EdgeSegment.CubicSegment();
				EdgeSegment part2 = edgeSegment instanceof EdgeSegment.LinearSegment ? new EdgeSegment.LinearSegment() : edgeSegment instanceof EdgeSegment.QuadraticSegment ? new EdgeSegment.QuadraticSegment() : new EdgeSegment.CubicSegment();
				EdgeSegment part3 = edgeSegment instanceof EdgeSegment.LinearSegment ? new EdgeSegment.LinearSegment() : edgeSegment instanceof EdgeSegment.QuadraticSegment ? new EdgeSegment.QuadraticSegment() : new EdgeSegment.CubicSegment();
				edgeSegment.splitInThirds(part1, part2, part3);
				return new EdgeSegment[] { part1, part2, part3 };
			};
			for(Contour contour : contours)
				if(contour.edges.size() == 1) {
					EdgeSegment[] edgeSegments = splitInThirds.get(contour.edges.get(0).get());
					contour.edges.clear();
					contour.edges.add(new EdgeHolder(edgeSegments[0]));
					contour.edges.add(new EdgeHolder(edgeSegments[1]));
					contour.edges.add(new EdgeHolder(edgeSegments[2]));
				}
		}
		public boolean validate() {
			for(Contour contour : contours) {
				if(contour.edges.isEmpty()) continue;
				Vec2 corner = contour.edges.get(contour.edges.size() - 1).get().point(Proxy.as(1d));
				for(EdgeHolder edge : contour.edges) {
					if(edge.get() == null) return false;
					if(!all(equal(edge.get().point(Proxy.as(0d)), corner))) return false;
					corner = edge.get().point(Proxy.as(1d));
				}
			} return true;
		}
		public void bound(Vec4 lbrt) { for(Contour contour : contours) contour.bound(lbrt); }
		public void boundMiters(Vec4 lbrt, double border, double miterLimit, int polarity) { for(Contour contour : contours) contour.boundMiters(lbrt, border, miterLimit, polarity); }
		public Vec4 getBounds(double border, double miterLimit, int polarity) {
			final double LARGE_VALUE = 1e240;
			Vec4 bounds = new Vec4(+LARGE_VALUE, +LARGE_VALUE, -LARGE_VALUE, -LARGE_VALUE);
			bound(bounds);
			if(border > 0) {
				bounds.x(bounds.x() - border);
				bounds.y(bounds.y() - border);
				bounds.z(bounds.z() + border);
				bounds.w(bounds.w() + border);
				if(miterLimit > 0)
					boundMiters(bounds, border, miterLimit, polarity);
			} return bounds;
		}
		public void scanline(ScanLine line, double y) {
			ArrayList<ScanLine.Intersection> intersections = new ArrayList<>();
			double[] x = new double[3];
			int[] dy = new int[3];
			for(Contour contour : contours) {
				for(EdgeHolder edge : contour.edges) {
					int n = edge.get().scanLineIntersections(x, dy, y);
					for(int i = 0; i < n; i++) {
						ScanLine.Intersection intersection = new ScanLine.Intersection(x[i], dy[i]);
						intersections.add(intersection);
					}
				}
			} line.setIntersections(intersections);
		}
		public int edgeCount() {
			int total = 0;
			for(Contour contour : contours) total += contour.edges.size();
			return total;
		}
	}

	public static class ScanLine {
		protected ArrayList<Intersection> intersections = new ArrayList<>();
		protected int lastIndex;

		protected static boolean interpretFillRule(int intersections, FillRule fillRule) {
			switch(fillRule) {
				case FILL_NONZERO: return intersections != 0;
				case FILL_ODD: return (intersections & 1) != 0;
				case FILL_POSITIVE: return intersections > 0;
				case FILL_NEGATIVE: return intersections < 0;
			} return false;
		}
		public static double overlap(ScanLine a, ScanLine b, double xFrom, double xTo, FillRule fillRule) {
			double total = 0; boolean aInside = false, bInside = false; int ai = 0, bi = 0;
			double ax = !a.intersections.isEmpty() ? a.intersections.get(ai).getX() : xTo;
			double bx = !b.intersections.isEmpty() ? b.intersections.get(bi).getX() : xTo;
			while (ax < xFrom || bx < xFrom) {
				double xNext = min(ax, bx);
				if (ax == xNext && ai < (int) a.intersections.size()) {
					aInside = interpretFillRule(a.intersections.get(ai).direction, fillRule);
					ax = ++ai < (int) a.intersections.size() ? a.intersections.get(ai).getX() : xTo;
				}
				if (bx == xNext && bi < (int) b.intersections.size()) {
					bInside = interpretFillRule(b.intersections.get(bi).direction, fillRule);
					bx = ++bi < (int) b.intersections.size() ? b.intersections.get(bi).getX() : xTo;
				}
			}
			double x = xFrom;
			while (ax < xTo || bx < xTo) {
				double xNext = min(ax, bx);
				if (aInside == bInside)
					total += xNext-x;
				if (ax == xNext && ai < (int) a.intersections.size()) {
					aInside = interpretFillRule(a.intersections.get(ai).direction, fillRule);
					ax = ++ai < (int) a.intersections.size() ? a.intersections.get(ai).getX() : xTo;
				}
				if (bx == xNext && bi < (int) b.intersections.size()) {
					bInside = interpretFillRule(b.intersections.get(bi).direction, fillRule);
					bx = ++bi < (int) b.intersections.size() ? b.intersections.get(bi).getX() : xTo;
				}
				x = xNext;
			}
			if (aInside == bInside)
				total += xTo-x;
			return total;
		}

		public ScanLine() {

		}
		public void setIntersections(ArrayList<Intersection> intersections) {
			this.intersections = intersections;
			preprocess();
		}
		public int countIntersections(double x) {
			return moveTo(x) + 1;
		}
		public int sumIntersections(double x) {
			int index = moveTo(x);
			if(index >= 0)
				return intersections.get(index).getDirection();
			return 0;
		}
		public boolean filled(double x, FillRule fillRule) {
			return interpretFillRule(sumIntersections(x), fillRule);
		}

		public void preprocess() {
			lastIndex = 0;
			if(this.intersections.isEmpty()) return;
			this.intersections.sort((o1, o2) -> (int) sign(o1.getX() - o2.getX()));
			int totalDirection = 0;
			for(Intersection intersection : intersections) {
				totalDirection += intersection.getDirection();
				intersection.setDirection(totalDirection);
			}
		}
		public int moveTo(double x) {
			if (intersections.isEmpty()) return -1; int index = lastIndex;
			if(x < intersections.get(index).getX()) {
				do { if(index == 0) { lastIndex = 0; return -1; } --index;
				} while (x < intersections.get(index).getX());
			} else while (index < (int) intersections.size()-1 && x >= intersections.get(index + 1).getX()) ++index;
			lastIndex = index; return index;
		}

		enum FillRule { FILL_NONZERO, FILL_ODD, FILL_POSITIVE, FILL_NEGATIVE }
		public static class Intersection {
			protected double x;
			protected int direction;

			public Intersection(double x, int direction) {
				this.x = x;
				this.direction = direction;
			}

			public double getX() { return x; }
			public int getDirection() { return direction; }

			public void setX(double x) { this.x = x; }
			public void setDirection(int direction) { this.direction = direction; }
		}
	}
	
	public static class Contour {
		protected ArrayList<EdgeHolder> edges = new ArrayList<>();

		public void addEdge(EdgeHolder edgeHolder) { edges.add(edgeHolder); }
		public void bound(Vec4 lbrt) { for(EdgeHolder edge : edges) edge.get().bound(lbrt); }
		public void boundMiters(Vec4 lbrt, double border, double miterLimit, int polarity) {
			if(edges.isEmpty()) return;
			Vec2 prevDir = normalize(edges.get(edges.size() - 1).get().direction(Proxy.as(1d)));
			for(EdgeHolder edge : edges) {
				Vec2 dir = mul(normalize(edge.get().direction(Proxy.as(0d))), -1);
				if(polarity * cross(prevDir, dir) >= 0) {
					double miterLength = miterLimit;
					double q = .5 * (1 - dot(prevDir, dir));
					if(q > 0) miterLength = min(1 / sqrt(q), miterLimit);
					Vec2 miter = add(edge.get().point(Proxy.as(0d)), mulX(normalize(add(prevDir, dir)), border * miterLength));
					if(miter.x() < lbrt.x()) lbrt.x(miter.x());
					if(miter.y() < lbrt.y()) lbrt.y(miter.y());
					if(miter.x() > lbrt.z()) lbrt.z(miter.x());
					if(miter.y() > lbrt.w()) lbrt.w(miter.y());
				} prevDir = normalize(edge.get().direction(Proxy.as(1d)));
			}
		}
		public int winding() {
			if(edges.isEmpty()) return 0;
			double total = 0;
			if (edges.size() == 1) {
				Vec2 a = edges.get(0).get().point(Proxy.as(0d));
				Vec2 b = edges.get(0).get().point(Proxy.as(1 / 3.));
				Vec2 c = edges.get(0).get().point(Proxy.as(2 / 3.));
				total += shoelace(a, b);
				total += shoelace(b, c);
				total += shoelace(c, a);
			} else if (edges.size() == 2) {
				Vec2 a = edges.get(0).get().point(Proxy.as(0d));
				Vec2 b = edges.get(0).get().point(Proxy.as(.5));
				Vec2 c = edges.get(1).get().point(Proxy.as(0d));
				Vec2 d = edges.get(1).get().point(Proxy.as(.5));
				total += shoelace(a, b);
				total += shoelace(b, c);
				total += shoelace(c, d);
				total += shoelace(d, a);
			} else {
				Vec2 prev = edges.get(edges.size() - 1).get().point(Proxy.as(0d));
				for(EdgeHolder edge : edges) {
					Vec2 cur = edge.get().point(Proxy.as(0d));
					total += shoelace(prev, cur);
					prev = cur;
				}
			} return (int) sign(total);
		}
		static double shoelace(Vec2 a, Vec2 b) {
			return (b.x() - a.x()) * (a.y() + b.y());
		}
	}
	public static class EdgeHolder {
		protected EdgeSegment edgeSegment;

		public EdgeHolder(Vec2 p0, Vec2 p1, Vec2 p2, Vec2 p3, DistanceFieldGenerator.EdgeSegment.EdgeColor edgeColor) { this(new EdgeSegment.CubicSegment(p0, p1, p2, p3, edgeColor)); }
		public EdgeHolder(Vec2 p0, Vec2 p1, Vec2 p2, DistanceFieldGenerator.EdgeSegment.EdgeColor edgeColor) { this(new EdgeSegment.QuadraticSegment(p0, p1, p2, edgeColor)); }
		public EdgeHolder(Vec2 p0, Vec2 p1, DistanceFieldGenerator.EdgeSegment.EdgeColor edgeColor) { this(new EdgeSegment.LinearSegment(p0, p1, edgeColor)); }
		public EdgeHolder(EdgeHolder orig) { this(orig.edgeSegment.clone()); }
		public EdgeHolder(EdgeSegment segment) { this.edgeSegment = segment; }
		public EdgeHolder() { }

		public DistanceFieldGenerator.EdgeSegment get() { return edgeSegment; }
		public void set(EdgeSegment edgeSegment) { this.edgeSegment = edgeSegment.clone(); }
	}

	public static interface ContourCombiner {
		void reset(Vec2 p);
		EdgeSelector edgeSelector(int i);
		double distance();
		Vec3 _distance();
		Vec4 __distance();
	}
	public static class SimpleContourCombiner implements ContourCombiner {
		protected EdgeSelector shapeEdgeSelector;

		public SimpleContourCombiner(ContourShape shape) {

		}

		public void reset(Vec2 p) { shapeEdgeSelector.reset(p); }
		public EdgeSelector edgeSelector(int i) { return shapeEdgeSelector; }
		public double distance() { return shapeEdgeSelector.distance(); }
		public Vec3 _distance() { return shapeEdgeSelector._distance(); }
		public Vec4 __distance() { return shapeEdgeSelector.__distance(); }
	}
	public static class OverlappingContourCombiner implements ContourCombiner {
		protected Vec2 p;
		protected ArrayList<Integer> windings = new ArrayList<>();
		protected ArrayList<EdgeSelector> edgeSelectors = new ArrayList<>();

		protected static double resolveDistance(Vec3 distance) {
			return max(min(distance.r(), distance.g()), min(max(distance.r(), distance.g()), distance.b()));
		}
		protected static double resolveDistance(Vec4 distance) {
			return max(min(distance.r(), distance.g()), min(max(distance.r(), distance.g()), distance.b()));
		}
		protected static double resolveDistance(double distance) {
			return distance;
		}

		public OverlappingContourCombiner(ContourShape shape) {
			for(Contour contour : shape.contours)
				windings.add(contour.winding());
		}

		public void reset(Vec2 p) {
			this.p = p;
			for(EdgeSelector edgeSelector : edgeSelectors)
				edgeSelector.reset(p);
		}
		public EdgeSelector edgeSelector(int i) {
			return edgeSelectors.get(i);
		}
		public double distance() {
			int contourCount = edgeSelectors.size();
			TrueDistanceSelector shapeEdgeSelector = new TrueDistanceSelector();
			TrueDistanceSelector innerEdgeSelector = new TrueDistanceSelector();
			TrueDistanceSelector outerEdgeSelector = new TrueDistanceSelector();
			shapeEdgeSelector.reset(p);
			innerEdgeSelector.reset(p);
			outerEdgeSelector.reset(p);
			for(int i = 0; i < contourCount; ++i) {
				double edgeDistance = edgeSelectors.get(i).distance();
				shapeEdgeSelector.merge((TrueDistanceSelector) edgeSelectors.get(i));
				if (windings.get(i) > 0 && resolveDistance(edgeDistance) >= 0)
					innerEdgeSelector.merge((TrueDistanceSelector) edgeSelectors.get(i));
				if (windings.get(i) < 0 && resolveDistance(edgeDistance) <= 0)
					outerEdgeSelector.merge((TrueDistanceSelector) edgeSelectors.get(i));
			}

			double shapeDistance = shapeEdgeSelector.distance();
			double innerDistance = innerEdgeSelector.distance();
			double outerDistance = outerEdgeSelector.distance();
			double innerScalarDistance = resolveDistance(innerDistance);
			double outerScalarDistance = resolveDistance(outerDistance);
			double distance = EdgeSegment.SignedDistance.INFINITE.distance;

			int winding = 0;
			if(innerScalarDistance >= 0 && abs(innerScalarDistance) <= abs(outerScalarDistance)) {
				distance = innerDistance;
				winding = 1;
				for(int i = 0; i < contourCount; ++i)
					if(windings.get(i) > 0) {
						double contourDistance = edgeSelectors.get(i).distance();
						if(abs(resolveDistance(contourDistance)) < abs(outerScalarDistance) && resolveDistance(contourDistance) > resolveDistance(distance))
							distance = contourDistance;
					}
			} else if(outerScalarDistance <= 0 && abs(outerScalarDistance) < abs(innerScalarDistance)) {
				distance = outerDistance;
				winding = -1;
				for(int i = 0; i < contourCount; ++i)
					if(windings.get(i) < 0) {
						double contourDistance = edgeSelectors.get(i).distance();
						if(abs(resolveDistance(contourDistance)) < abs(innerScalarDistance) && resolveDistance(contourDistance) < resolveDistance(distance))
							distance = contourDistance;
					}
			} else return shapeDistance;

			for(int i = 0; i < contourCount; ++i)
				if(windings.get(i) != winding) {
					double contourDistance = edgeSelectors.get(i).distance();
					if(resolveDistance(contourDistance)*resolveDistance(distance) >= 0 && abs(resolveDistance(contourDistance)) < abs(resolveDistance(distance)))
						distance = contourDistance;
				}
			if(resolveDistance(distance) == resolveDistance(shapeDistance))
				distance = shapeDistance;
			return distance;
		}
		public Vec3 _distance() {
			int contourCount = (int) edgeSelectors.size();
			MultiDistanceSelector shapeEdgeSelector = new MultiAndTrueDistanceSelector();
			MultiDistanceSelector innerEdgeSelector = new MultiAndTrueDistanceSelector();
			MultiDistanceSelector outerEdgeSelector = new MultiAndTrueDistanceSelector();
			shapeEdgeSelector.reset(p);
			innerEdgeSelector.reset(p);
			outerEdgeSelector.reset(p);
			for(int i = 0; i < contourCount; ++i) {
				Vec3 edgeDistance = edgeSelectors.get(i)._distance();
				shapeEdgeSelector.merge((MultiDistanceSelector) edgeSelectors.get(i));
				if(windings.get(i) > 0 && resolveDistance(edgeDistance) >= 0)
					innerEdgeSelector.merge((MultiDistanceSelector) edgeSelectors.get(i));
				if(windings.get(i) < 0 && resolveDistance(edgeDistance) <= 0)
					outerEdgeSelector.merge((MultiDistanceSelector) edgeSelectors.get(i));
			}

			Vec3 shapeDistance = shapeEdgeSelector._distance();
			Vec3 innerDistance = innerEdgeSelector._distance();
			Vec3 outerDistance = outerEdgeSelector._distance();
			double innerScalarDistance = resolveDistance(innerDistance);
			double outerScalarDistance = resolveDistance(outerDistance);
			Vec3 distance = vec3(EdgeSegment.SignedDistance.INFINITE.distance);

			int winding = 0;
			if(innerScalarDistance >= 0 && abs(innerScalarDistance) <= abs(outerScalarDistance)) {
				distance = innerDistance;
				winding = 1;
				for(int i = 0; i < contourCount; ++i)
					if(windings.get(i) > 0) {
						Vec3 contourDistance = edgeSelectors.get(i)._distance();
						if(abs(resolveDistance(contourDistance)) < abs(outerScalarDistance) && resolveDistance(contourDistance) > resolveDistance(distance))
							distance = contourDistance;
					}
			} else if(outerScalarDistance <= 0 && abs(outerScalarDistance) < abs(innerScalarDistance)) {
				distance = outerDistance;
				winding = -1;
				for(int i = 0; i < contourCount; ++i)
					if(windings.get(i) < 0) {
						Vec3 contourDistance = edgeSelectors.get(i)._distance();
						if(abs(resolveDistance(contourDistance)) < abs(innerScalarDistance) && resolveDistance(contourDistance) < resolveDistance(distance))
							distance = contourDistance;
					}
			} else
				return shapeDistance;

			for(int i = 0; i < contourCount; ++i)
				if(windings.get(i) != winding) {
					Vec3 contourDistance = edgeSelectors.get(i)._distance();
					if(resolveDistance(contourDistance)*resolveDistance(distance) >= 0 && abs(resolveDistance(contourDistance)) < abs(resolveDistance(distance)))
						distance = contourDistance;
				}
			if(resolveDistance(distance) == resolveDistance(shapeDistance))
				distance = shapeDistance;
			return distance;
		}
		public Vec4 __distance() {
			int contourCount = (int) edgeSelectors.size();
			MultiAndTrueDistanceSelector shapeEdgeSelector = new MultiAndTrueDistanceSelector();
			MultiAndTrueDistanceSelector innerEdgeSelector = new MultiAndTrueDistanceSelector();
			MultiAndTrueDistanceSelector outerEdgeSelector = new MultiAndTrueDistanceSelector();
			shapeEdgeSelector.reset(p);
			innerEdgeSelector.reset(p);
			outerEdgeSelector.reset(p);
			for(int i = 0; i < contourCount; ++i) {
				Vec4 edgeDistance = edgeSelectors.get(i).__distance();
				shapeEdgeSelector.merge((MultiAndTrueDistanceSelector) edgeSelectors.get(i));
				if(windings.get(i) > 0 && resolveDistance(edgeDistance) >= 0)
					innerEdgeSelector.merge((MultiAndTrueDistanceSelector) edgeSelectors.get(i));
				if(windings.get(i) < 0 && resolveDistance(edgeDistance) <= 0)
					outerEdgeSelector.merge((MultiAndTrueDistanceSelector) edgeSelectors.get(i));
			}

			Vec4 shapeDistance = shapeEdgeSelector.__distance();
			Vec4 innerDistance = innerEdgeSelector.__distance();
			Vec4 outerDistance = outerEdgeSelector.__distance();
			double innerScalarDistance = resolveDistance(innerDistance);
			double outerScalarDistance = resolveDistance(outerDistance);
			Vec4 distance = vec4(EdgeSegment.SignedDistance.INFINITE.distance);

			int winding = 0;
			if(innerScalarDistance >= 0 && abs(innerScalarDistance) <= abs(outerScalarDistance)) {
				distance = innerDistance;
				winding = 1;
				for(int i = 0; i < contourCount; ++i)
					if(windings.get(i) > 0) {
						Vec4 contourDistance = edgeSelectors.get(i).__distance();
						if(abs(resolveDistance(contourDistance)) < abs(outerScalarDistance) && resolveDistance(contourDistance) > resolveDistance(distance))
							distance = contourDistance;
					}
			} else if(outerScalarDistance <= 0 && abs(outerScalarDistance) < abs(innerScalarDistance)) {
				distance = outerDistance;
				winding = -1;
				for(int i = 0; i < contourCount; ++i)
					if(windings.get(i) < 0) {
						Vec4 contourDistance = edgeSelectors.get(i).__distance();
						if(abs(resolveDistance(contourDistance)) < abs(innerScalarDistance) && resolveDistance(contourDistance) < resolveDistance(distance))
							distance = contourDistance;
					}
			} else
				return shapeDistance;

			for(int i = 0; i < contourCount; ++i)
				if(windings.get(i) != winding) {
					Vec4 contourDistance = edgeSelectors.get(i).__distance();
					if(resolveDistance(contourDistance)*resolveDistance(distance) >= 0 && abs(resolveDistance(contourDistance)) < abs(resolveDistance(distance)))
						distance = contourDistance;
				}
			if(resolveDistance(distance) == resolveDistance(shapeDistance))
				distance = shapeDistance;
			return distance;
		};
	}

	static int countShape(Shape shape) {
		int result = 0; PathIterator path = shape.getPathIterator(null);
		while(!path.isDone()) { result++; path.next(); }
		return result;
	}

	public interface EdgeSelector {
		void reset(Vec2 p);
		void addEdge(EdgeSelector cache, EdgeSegment prevEdge, EdgeSegment edge, EdgeSegment nextEdge);
		void merge(EdgeSelector other);
		double distance();
		Vec3 _distance();
		Vec4 __distance();
	}
	protected static abstract class TrueDistanceEdgeCache {
		protected Vec2 point;
		protected double absDistance;

		public TrueDistanceEdgeCache(double absDistance) {
			this.point = new Vec2();
			this.absDistance = absDistance;
		}

		public Vec2 getPoint() { return point; }
		public double getAbsDistance() { return absDistance; }

		public void setPoint(Vec2 point) { this.point = point; }
		public void setAbsDistance(double absDistance) { this.absDistance = absDistance; }
	}
	public static class TrueDistanceSelector extends TrueDistanceEdgeCache implements EdgeSelector {
		protected Vec2 p;
		protected EdgeSegment.SignedDistance minDistance;

		public TrueDistanceSelector() {
			super(0);
		}

		public void reset(Vec2 p) {
			double delta = 1.001 * length(sub(p, this.p));
			minDistance.setDistance(nonZeroSign(minDistance.getDistance()) * delta + minDistance.getDistance());
			this.p = p;
		}
		public void addEdge(EdgeSelector _cache, EdgeSegment prevEdge, EdgeSegment edge, EdgeSegment nextEdge) {
			TrueDistanceSelector cache = (TrueDistanceSelector) _cache;
			double delta = 1.001 * length(sub(p, cache.getPoint()));
			if(cache.getAbsDistance() - delta > abs(minDistance.getDistance())) return;
			EdgeSegment.SignedDistance distance = edge.signedDistance(p, Proxy.as(0d));
			if(EdgeSegment.SignedDistance.lessThan(distance, minDistance)) minDistance = distance;
			cache.setPoint(p); cache.setAbsDistance(abs(distance.distance));
		}
		public void merge(EdgeSelector _other) {
			TrueDistanceSelector other = (TrueDistanceSelector) _other;
			if(EdgeSegment.SignedDistance.lessThan(other.minDistance, minDistance))
				minDistance = other.minDistance;
		}
		public double distance() { return minDistance.getDistance(); }
		@Override public Vec3 _distance() { return null; }
		@Override public Vec4 __distance() { return null; }
	}


	protected static abstract class PseudoDistanceEdgeCache {
		protected Vec2 point;
		protected double absDistance;
		protected double edgeDomainDistance;
		protected double pseudoDistance;

		public PseudoDistanceEdgeCache(double absDistance) {
			this.point = new Vec2();
			this.absDistance = absDistance;
		}

		public Vec2 getPoint() { return point; }
		public double getAbsDistance() { return absDistance; }
		public double getEdgeDomainDistance() { return edgeDomainDistance; }
		public double getPseudoDistance() { return pseudoDistance; }

		public void setPoint(Vec2 point) { this.point = point; }
		public void setAbsDistance(double absDistance) { this.absDistance = absDistance; }
		public void setEdgeDomainDistance(double edgeDomainDistance) { this.edgeDomainDistance = edgeDomainDistance; }
		public void setPseudoDistance(double pseudoDistance) { this.pseudoDistance = pseudoDistance; }
	}
	public static class PseudoDistanceSelector extends PseudoDistanceEdgeCache implements EdgeSelector {
		protected EdgeSegment.SignedDistance minTrueDistance;
		protected EdgeSegment.SignedDistance minNegativePseudoDistance;
		protected EdgeSegment.SignedDistance minPositivePseudoDistance;
		protected EdgeSegment nearEdge;
		protected Proxy<Double> nearEdgeParam;
		protected Vec2 p;

		public PseudoDistanceSelector() {
			super(0);
		}

		public void reset(double delta) {
			minTrueDistance.setDistance(nonZeroSign(minTrueDistance.getDistance()) * delta + minTrueDistance.getDistance());
			minNegativePseudoDistance.setDistance(-abs(minTrueDistance.getDistance()));
			minPositivePseudoDistance.setDistance(abs(minTrueDistance.getDistance()));
			nearEdge = null; nearEdgeParam = Proxy.as(0d);
		}
		public boolean isEdgeRelevant(PseudoDistanceEdgeCache cache, EdgeSegment edge, Vec2 p) {
			double delta = 1.001 * length(sub(p, cache.point));
			return (
					cache.getAbsDistance() - delta <= abs(minTrueDistance.getDistance()) ||
							(cache.getEdgeDomainDistance() > 0 ?
									cache.getEdgeDomainDistance() - delta <= 0 :
									(cache.getPseudoDistance() < 0 ?
											cache.getPseudoDistance() + delta >= minNegativePseudoDistance.getDistance() :
											cache.getPseudoDistance() - delta <= minPositivePseudoDistance.getDistance()
									)
							)
			);
		}
		public void addEdgeTrueDistance(EdgeSegment edge, EdgeSegment.SignedDistance distance, Proxy<Double> param) {
			if(EdgeSegment.SignedDistance.greaterEqualThan(distance, minTrueDistance)) return;
			minTrueDistance = distance; nearEdge = edge; nearEdgeParam = param;
		}
		public void addEdgePseudoDistance(EdgeSegment.SignedDistance distance) {
			EdgeSegment.SignedDistance minPseudoDistance = distance.getDistance() < 0 ? minNegativePseudoDistance : minPositivePseudoDistance;
			if(EdgeSegment.SignedDistance.lessThan(distance, minPseudoDistance)) {
				if(distance.getDistance() < 0) minNegativePseudoDistance = distance;
				else minPositivePseudoDistance = distance;
			}
		}
		public void merge(EdgeSelector _other) {
			PseudoDistanceSelector other = (PseudoDistanceSelector) _other;
			if(EdgeSegment.SignedDistance.lessThan(other.minTrueDistance, minTrueDistance)) {
				minTrueDistance = other.minTrueDistance;
				nearEdge = other.nearEdge;
				nearEdgeParam = other.nearEdgeParam;
			}
			if(EdgeSegment.SignedDistance.lessThan(other.minNegativePseudoDistance, minNegativePseudoDistance))
				minNegativePseudoDistance = other.minNegativePseudoDistance;
			if(EdgeSegment.SignedDistance.lessThan(other.minPositivePseudoDistance, minPositivePseudoDistance))
				minPositivePseudoDistance = other.minPositivePseudoDistance;
		}
		public double computeDistance(Vec2 p) {
			double minDistance = minTrueDistance.getDistance() < 0 ? minNegativePseudoDistance.getDistance() : minPositivePseudoDistance.getDistance();
			if(nearEdge != null) {
				EdgeSegment.SignedDistance distance = minTrueDistance;
				nearEdge.distanceToPseudoDistance(distance, p, nearEdgeParam);
				if(abs(distance.getDistance()) < abs(minDistance))
					minDistance = distance.getDistance();
			} return minDistance;
		}
		public EdgeSegment.SignedDistance trueDistance() {
			return minTrueDistance;
		}

		public void reset(Vec2 p) {
			double delta = 1.001 * length(sub(p, this.p));
			reset(delta); this.p = p;
		}
		public void addEdge(EdgeSelector _cache, EdgeSegment prevEdge, EdgeSegment edge, EdgeSegment nextEdge) {
			PseudoDistanceSelector cache = (PseudoDistanceSelector) _cache;
			if(!isEdgeRelevant(cache, edge, p)) return;
			Proxy<Double> param = Proxy.as(0d);
			EdgeSegment.SignedDistance distance = edge.signedDistance(p, param);
			double edd = edgeDomainDistance(prevEdge, edge, nextEdge, p, param);
			addEdgeTrueDistance(edge, distance, param);
			cache.setPoint(p);
			cache.setAbsDistance(abs(distance.distance));
			cache.setEdgeDomainDistance(edd);
			if(edd > 0) return;
			edge.distanceToPseudoDistance(distance, p, param);
			addEdgePseudoDistance(distance);
			cache.setPseudoDistance(distance.distance);
		}
		public double distance() { return computeDistance(p); }
		@Override public Vec3 _distance() { return null; }
		@Override public Vec4 __distance() { return null; }

		public static double edgeDomainDistance(EdgeSegment prevEdge, EdgeSegment edge, EdgeSegment nextEdge, Vec2 p, Proxy<Double> param) {
			if(param.get() < 0) {
				Vec2 prevEdgeDir = mul(normalize(prevEdge.direction(Proxy.as(1d))), -1);
				Vec2 edgeDir = normalize(edge.direction(Proxy.as(0d)));
				Vec2 pointDir = sub(p, edge.point(Proxy.as(0d)));
				return dot(pointDir, normalize(sub(prevEdgeDir, edgeDir)));
			}
			if(param.get() > 1) {
				Vec2 edgeDir = mul(normalize(edge.direction(Proxy.as(1d))), -1);
				Vec2 nextEdgeDir = normalize(nextEdge.direction(Proxy.as(0d)));
				Vec2 pointDir = sub(p, edge.point(Proxy.as(1d)));
				return dot(pointDir, normalize(sub(nextEdgeDir, edgeDir)));
			} return 0;
		}
	}

	@SuppressWarnings("jol")
	public static class MultiDistanceSelector extends PseudoDistanceSelector implements EdgeSelector {
		protected Vec2 p;
		protected PseudoDistanceSelector r, g, b;

		public MultiDistanceSelector() {
			super();
		}

		public void reset(Vec2 p) {
			double delta = 1.001 * length(sub(p, this.p));
			r.reset(delta);
			g.reset(delta);
			b.reset(delta);
			this.p = p;
		}
		public void addEdge(EdgeSelector _cache, EdgeSegment prevEdge, EdgeSegment edge, EdgeSegment nextEdge) {
			MultiDistanceSelector cache = (MultiDistanceSelector) _cache;
			if(
					((edge.color.c & EdgeSegment.EdgeColor.RED.c) != 0 && r.isEdgeRelevant(cache, edge, p)) ||
					((edge.color.c & EdgeSegment.EdgeColor.GREEN.c) != 0 && g.isEdgeRelevant(cache, edge, p)) ||
					((edge.color.c & EdgeSegment.EdgeColor.BLUE.c) != 0 && b.isEdgeRelevant(cache, edge, p))
			) {
				Proxy<Double> param = Proxy.as(0d);
				EdgeSegment.SignedDistance distance = edge.signedDistance(p, param);
				double edd = edgeDomainDistance(prevEdge, edge, nextEdge, p, param);
				if((edge.color.c & EdgeSegment.EdgeColor.RED.c) != 0)
					r.addEdgeTrueDistance(edge, distance, param);
				if((edge.color.c & EdgeSegment.EdgeColor.GREEN.c) != 0)
					g.addEdgeTrueDistance(edge, distance, param);
				if((edge.color.c & EdgeSegment.EdgeColor.BLUE.c) != 0)
					b.addEdgeTrueDistance(edge, distance, param);
				cache.point = p;
				cache.absDistance = abs(distance.distance);
				cache.edgeDomainDistance = edd;
				if(edd <= 0) {
					edge.distanceToPseudoDistance(distance, p, param);
					if((edge.color.c & EdgeSegment.EdgeColor.RED.c) != 0)
						r.addEdgePseudoDistance(distance);
					if((edge.color.c & EdgeSegment.EdgeColor.GREEN.c) != 0)
						g.addEdgePseudoDistance(distance);
					if((edge.color.c & EdgeSegment.EdgeColor.BLUE.c) != 0)
						b.addEdgePseudoDistance(distance);
					cache.pseudoDistance = distance.distance;
				}
			}
		}
		public void merge(EdgeSelector _other) {
			MultiDistanceSelector other = (MultiDistanceSelector) _other;
			r.merge(other.r);
			g.merge(other.g);
			b.merge(other.b);
		}
		public Vec3 _distance() {
			Vec3 multiDistance = new Vec3();
			multiDistance.r(r.computeDistance(p));
			multiDistance.g(g.computeDistance(p));
			multiDistance.b(b.computeDistance(p));
			return multiDistance;
		}
		public EdgeSegment.SignedDistance trueDistance() {
			EdgeSegment.SignedDistance distance = r.trueDistance();
			if(EdgeSegment.SignedDistance.lessThan(g.trueDistance(), distance))
				distance = g.trueDistance();
			if(EdgeSegment.SignedDistance.lessThan(b.trueDistance(), distance))
				distance = b.trueDistance();
			return distance;
		}
	}

	@SuppressWarnings("jol")
	public static class MultiAndTrueDistanceSelector extends MultiDistanceSelector implements EdgeSelector {
		public MultiAndTrueDistanceSelector() {
			super();
		}

		public Vec4 __distance() {
			Vec3 multiDistance = _distance();
			return vec4(multiDistance, trueDistance().distance);
		}
	}

	public static abstract class EdgeSegment {
		protected EdgeColor color;

		public EdgeSegment(EdgeColor color) {
			this.color = color;
		}

		public EdgeColor getColor() { return color; }
		public abstract EdgeSegment clone();
		public abstract Vec2 point(Proxy<Double> param);
		public abstract Vec2 direction(Proxy<Double> param);
		public abstract SignedDistance signedDistance(Vec2 origin, Proxy<Double> param);
		public void distanceToPseudoDistance(SignedDistance distance, Vec2 origin, Proxy<Double> param) {
			if(param.get() >= 0 && param.get() <= 1) return;
			param = param.get() < 0 ? Proxy.as(0d) : param.get() > 1 ? Proxy.as(1d) : param;
			Vec2 dir = normalizeX(direction(param));
			Vec2 aq = sub(origin, point(param));
			double ts = dot(aq, dir); if(ts <= 0) return;
			double pseudoDistance = cross(aq, dir);
			if(abs(pseudoDistance) <= abs(distance.distance)) {
				distance.setDistance(pseudoDistance);
				distance.setDot(0);
			}
		}
		public abstract int scanLineIntersections(double[] x, int[] dy, double y);
		public abstract void bound(Vec4 lbrt);

		public abstract void moveStartPoint(Vec2 to);
		public abstract void moveEndPoint(Vec2 to);
		public abstract void splitInThirds(EdgeSegment part1, EdgeSegment part2, EdgeSegment part3);

		public static class LinearSegment extends EdgeSegment {
			Vec2[] p;

			public LinearSegment(Vec2 p0, Vec2 p1, EdgeColor color) {
				super(color);
				this.p = new Vec2[2];
				p[0] = p0;
				p[1] = p1;
			}
			public LinearSegment() { this(null, null, null); }

			@Override public LinearSegment clone() { return new LinearSegment(p[0], p[1], color); }
			@Override public Vec2 point(Proxy<Double> param) { return mix(p[0], p[1], param.get()); }
			@Override public Vec2 direction(Proxy<Double> param) { return sub(p[1], p[0]); }
			@Override public SignedDistance signedDistance(Vec2 origin, Proxy<Double> param) {
				Vec2 aq = sub(origin, p[0]);
				Vec2 ab = sub(p[1], p[0]);
				param.set(dot(aq, ab) / dot(ab, ab));
				Vec2 eq = sub(p[param.get() > .5 ? 1 : 0], origin);
				double endpointDistance = length(eq);
				if(param.get() > 0 && param.get() < 1) {
					double orthoDistance = dot(normalize(ab), aq);
					if(abs(orthoDistance) < endpointDistance)
						return new SignedDistance(orthoDistance, 0);
				} return new SignedDistance(nonZeroSign(cross(aq, ab)) * endpointDistance, abs(dot(normalize(ab), normalize(eq))));
			}
			@Override public int scanLineIntersections(double[] x, int[] dy, double y) {
				if((y < p[0].y() || y >= p[1].y()) && (y < p[1].y() && y >= p[0].y())) return 0;
				double param = (y - p[0].y()) / (p[1].y() - p[0].y());
				x[0] = mix(p[0].x(), p[1].x(), param);
				dy[0] = (int) sign(p[1].y() - p[0].y());
				return 1;
			}
			@Override public void bound(Vec4 lbrt) { pointBounds(p[0], lbrt); pointBounds(p[1], lbrt); }
			@Override public void moveStartPoint(Vec2 to) { p[0] = to; }
			@Override public void moveEndPoint(Vec2 to) { p[1] = to; }
			@Override public void splitInThirds(EdgeSegment part1, EdgeSegment part2, EdgeSegment part3) {
				((LinearSegment) part1).p[0] = p[0];
				((LinearSegment) part1).p[1] = point(Proxy.as(1 / 3.));
				((LinearSegment) part1).color = color;
				((LinearSegment) part2).p[0] = point(Proxy.as(1 / 3.));
				((LinearSegment) part2).p[1] = point(Proxy.as(2 / 3.));
				((LinearSegment) part2).color = color;
				((LinearSegment) part3).p[0] = point(Proxy.as(2 / 3.));
				((LinearSegment) part3).p[1] = p[1];
				((LinearSegment) part3).color = color;
			}
		}

		public static class QuadraticSegment extends EdgeSegment {
			Vec2[] p;

			public QuadraticSegment(Vec2 p0, Vec2 p1, Vec2 p2, EdgeColor color) {
				super(color);
				this.p = new Vec2[3];
				p[0] = p0;
				p[1] = p1;
				p[2] = p2;
			}
			public QuadraticSegment() { this(null, null, null, null); }

			@Override public QuadraticSegment clone() { return new QuadraticSegment(p[0], p[1], p[2], color); }
			@Override public Vec2 point(Proxy<Double> param) { return mix(mix(p[0], p[1], param.get()), mix(p[1], p[2], param.get()), param.get()); }
			@Override public Vec2 direction(Proxy<Double> param) {
				Vec2 tangent = mix(sub(p[1], p[0]), sub(p[2], p[1]), param.get());
				if(tangent.x() != 0 || tangent.y() != 0) return sub(p[2], p[0]);
				return tangent;
			}
			@Override public SignedDistance signedDistance(Vec2 origin, Proxy<Double> param) {
				Vec2 qa = sub(p[0], origin);
				Vec2 ab = sub(p[1], p[0]);
				Vec2 br = sub(p[2], sub(p[1], ab));
				double a = dot(br, br);
				double b = 3 * dot(ab, br);
				double c = 2 * dot(ab, ab) + dot(qa, br);
				double d = dot(qa, ab);
				double[] t = new double[3];
				int solutions = solveCubic(t, a, b, c, d);

				Vec2 epDir = direction(Proxy.as(0d));
				double minDistance = nonZeroSign(cross(epDir, qa)) * length(qa);
				param.set(-dot(qa, epDir) / dot(epDir, epDir));
				{
					epDir = direction(Proxy.as(1d));
					double distance = nonZeroSign(cross(epDir, sub(p[2], origin))) * length(sub(p[2], origin));
					if(abs(distance) < abs(minDistance)) {
						minDistance = distance;
						param.set(dot(sub(origin, p[1]), epDir) / dot(epDir, epDir));
					}
				}
				for(int i = 0; i < solutions; ++i) {
					if(t[i] <= 0 || t[i] >= 1) continue;
					Vec2 qe = sub(add(add(p[0], mul(ab, 2 * t[i])), mul(br, t[i] * t[i])), origin);
					double distance = nonZeroSign(cross(sub(p[2], p[0]), qe)) * length(qe);
					if(abs(distance) > abs(minDistance)) continue;
					minDistance = distance; param.set(t[i]);
				}

				if (param.get() >= 0 && param.get() <= 1) return new SignedDistance(minDistance, 0);
				if (param.get() < .5) return new SignedDistance(minDistance, abs(dot(normalize(direction(Proxy.as(0d))), normalize(qa))));
				else return new SignedDistance(minDistance, abs(dot(normalize(direction(Proxy.as(1d))), normalize(sub(p[2], origin)))));
			}
			@Override public int scanLineIntersections(double[] x, int[] dy, double y) {
				int total = 0;
				int nextDY = y > p[0].y() ? 1 : -1;
				x[total] = p[0].x();
				if (p[0].y() == y) {
					if (p[0].y() < p[1].y() || (p[0].y() == p[1].y() && p[0].y() < p[2].y()))
						dy[total++] = 1;
					else nextDY = 1;
				}
				{
					Vec2 ab = sub(p[1], p[0]);
					Vec2 br = sub(sub(p[2], p[1]), ab);
					double[] t = new double[2];
					int solutions = solveQuadratic(t, br.y(), 2 * ab.y(), p[0].y() - y);
					double tmp;
					if(solutions >= 2 && t[0] > t[1]) {
						tmp = t[0]; t[0] = t[1]; t[1] = tmp; }
					for(int i = 0; i < solutions && total < 2; i++) {
						if(t[i] < 0 || t[i] > 1) continue;
						x[total] = p[0].x() + 2 * t[i] * ab.x() + t[i] * t[i] * br.x();
						if(nextDY * (ab.y() + t[i] * br.y()) < 0) continue;
						dy[total++] = nextDY; nextDY = -nextDY;
					}
				}
				if(p[2].y() == y) {
					if(nextDY > 0 && total > 0) { --total; nextDY = -1; }
					if((p[2].y() < p[1].y() || (p[2].y() == p[1].y() && p[2].y() < p[0].y())) && total < 2) {
						x[total] = p[2].x(); if(nextDY < 0) { dy[total++] = -1; nextDY = 1; }
					}
				}
				if(nextDY != (y >= p[2].y() ? 1 : -1)) {
					if(total > 0) total--;
					else {
						if(abs(p[2].y() - y) < abs(p[0].y() - y))
							x[total] = p[2].x();
						dy[total++] = nextDY;
					}
				} return total;
			}
			@Override public void bound(Vec4 lbrt) {
				pointBounds(p[0], lbrt);
				pointBounds(p[2], lbrt);
				Vec2 bot = sub(sub(p[1], p[0]), sub(p[2], p[1]));
				if(bot.x() != 0) {
					Proxy<Double> param = Proxy.as((p[1].x() - p[0].x()) / bot.x());
					if(param.get() > 0 && param.get() < 1) pointBounds(point(param), lbrt);
				}
				if(bot.y() != 0) {
					Proxy<Double> param = Proxy.as((p[1].y() - p[0].y()) / bot.y());
					if(param.get() > 0 && param.get() < 1) pointBounds(point(param), lbrt);
				}
			}
			@Override public void moveStartPoint(Vec2 to) {
				Vec2 origSDir = sub(p[0], p[1]);
				Vec2 origP1 = p[1];
				p[1] = add(mul(sub(p[2], p[1]), cross(sub(p[0], p[1]), sub(to, p[0])) / cross(sub(p[0], p[1]), sub(p[2], p[1]))), p[1]);
				p[0] = to; if(dot(origSDir, sub(p[0], p[1])) < 0) p[1] = origP1;
			}
			@Override public void moveEndPoint(Vec2 to) {
				Vec2 origEDir = sub(p[2], p[1]);
				Vec2 origP1 = p[1];
				p[1] = add(mul(sub(p[0], p[1]), cross(sub(p[2], p[1]), sub(to, p[2])) / cross(sub(p[2], p[1]), sub(p[0], p[1]))), p[1]);
				p[2] = to; if(dot(origEDir, sub(p[2], p[1])) < 0) p[1] = origP1;
			}
			@Override public void splitInThirds(EdgeSegment part1, EdgeSegment part2, EdgeSegment part3) {
				((QuadraticSegment) part1).p[0] = p[0];
				((QuadraticSegment) part1).p[1] = mix(p[0], p[1], 1 / 3.);
				((QuadraticSegment) part1).p[2] = point(Proxy.as(1 / 3.));
				((QuadraticSegment) part1).color = color;
				((QuadraticSegment) part2).p[0] = point(Proxy.as(1 / 3.));
				((QuadraticSegment) part2).p[1] = mix(mix(p[0], p[1], 5 / 9.), mix(p[1], p[2], 4 / 9.), .5);
				((QuadraticSegment) part2).p[2] = point(Proxy.as(2 / 3.));
				((QuadraticSegment) part2).color = color;
				((QuadraticSegment) part3).p[0] = point(Proxy.as(2 / 3.));
				((QuadraticSegment) part3).p[1] = mix(p[1], p[2], 2/3.);
				((QuadraticSegment) part3).p[2] = p[2];
				((QuadraticSegment) part3).color = color;
			}
		}

		public static class CubicSegment extends EdgeSegment {
			Vec2[] p;

			public CubicSegment(Vec2 p0, Vec2 p1, Vec2 p2, Vec2 p3, EdgeColor color) {
				super(color);
				this.p = new Vec2[4];
				p[0] = p0;
				p[1] = p1;
				p[2] = p2;
				p[3] = p3;
			}
			public CubicSegment() { this(null, null, null, null, null); }

			@Override public CubicSegment clone() { return new CubicSegment(p[0], p[1], p[2], p[3], color); }
			@Override public Vec2 point(Proxy<Double> param) { Vec2 p12 = mix(p[1], p[2], param.get()); return mix(mix(mix(p[0], p[1], param.get()), p12, param.get()), mix(p12, mix(p[2], p[3], param.get()), param.get()), param.get()); }
			@Override public Vec2 direction(Proxy<Double> param) {
				Vec2 tangent = mix(mix(sub(p[1], p[0]), sub(p[2], p[1]), param.get()), mix(sub(p[2], p[1]), sub(p[3], p[2]), param.get()), param.get());
				if(tangent.x() != 0 || tangent.y() != 0) {
					if(param.get() == 0) return sub(p[2], p[0]);
					if(param.get() == 1) return sub(p[3], p[1]);
				} return tangent;
			}
			@Override public SignedDistance signedDistance(Vec2 origin, Proxy<Double> param) {
				Vec2 qa = sub(p[0], origin);
				Vec2 ab = sub(p[1], p[0]);
				Vec2 br = sub(sub(p[2], p[1]), ab);
				Vec2 as = sub(sub(sub(p[3], p[2]), sub(p[2], p[1])), br);

				Vec2 epDir = direction(Proxy.as(0d));
				double minDistance = nonZeroSign(cross(epDir, qa)) * length(qa);
				param.set(-dot(qa, epDir) / dot(epDir, epDir));
				{
					epDir = direction(Proxy.as(1d));
					double distance = nonZeroSign(cross(epDir, sub(p[3], origin))) * length(sub(p[3], origin));
					if(abs(distance) < abs(minDistance)) {
						minDistance = distance;
						param.set(dot(sub(epDir, sub(p[3], origin)), epDir) / dot(epDir, epDir));
					}
				}
				for(int i = 0; i <= 4; ++i) {
					double t = (double) i / 4;
					for(int step = 0; ; step++) {
						Vec2 qe = sub(add(add(add(p[0], mul(ab, 3 * t)), mul(br, 3 * t * t)), mul(as, t * t * t)), origin);
						double distance = nonZeroSign(cross(direction(Proxy.as(t)), qe)) * length(qe);
						if(abs(distance) < abs(minDistance)) {
							minDistance = distance; param.set(t);
						} if (step == 4) break;
						Vec2 d1 = add(add(mul(as, 3 * t * t), mul(br, 6 * t)), mul(ab, 3));
						Vec2 d2 = add(mul(as, 6 * t), mul(br, 6));
						t -= dot(qe, d1) / (dot(d1, d1) + dot(qe, d2));
						if(t < 0 || t > 1) break;
					}
				}

				if (param.get() >= 0 && param.get() <= 1) return new SignedDistance(minDistance, 0);
				if (param.get() < .5) return new SignedDistance(minDistance, abs(dot(normalize(direction(Proxy.as(0d))), normalize(qa))));
				else return new SignedDistance(minDistance, abs(dot(normalize(direction(Proxy.as(1d))), normalize(sub(p[3], origin)))));
			}
			@Override public int scanLineIntersections(double[] x, int[] dy, double y) {
				int total = 0;
				int nextDY = y > p[0].y() ? 1 : -1;
				x[total] = p[0].x();
				if(p[0].y() == y) {
					if(p[0].y() < p[1].y() || (p[0].y() == p[1].y() && (p[0].y() < p[2].y() || (p[0].y() == p[2].y() && p[0].y() < p[3].y()))))
						dy[total++] = 1;
					else nextDY = 1;
				}
				{
					Vec2 ab = sub(p[1], p[0]);
					Vec2 br = sub(sub(p[2], p[1]), ab);
					Vec2 as = sub(sub(sub(p[3], p[2]), sub(p[2], p[1])), br);
					double[] t = new double[3];
					int solutions = solveCubic(t, as.y(), 3 * br.y(), 3 * ab.y(), p[0].y() - y);
					double tmp;
					if(solutions >= 2) {
						if(t[0] > t[1]) { tmp = t[0]; t[0] = t[1]; t[1] = tmp; }
						if (solutions >= 3 && t[1] > t[2]) {
							tmp = t[1]; t[1] = t[2]; t[2] = tmp;
							if(t[0] > t[1]) { tmp = t[0]; t[0] = t[1]; t[1] = tmp; }
						}
					}
					for(int i = 0; i < solutions && total < 3; ++i) {
						if(t[i] >= 0 && t[i] <= 1) {
							x[total] = p[0].x() + 3 * t[i] * ab.x() + 3 * t[i] * t[i] * br.x() + t[i] * t[i] * t[i] * as.x();
							if(nextDY * (ab.y() + 2 * t[i] * br.y() + t[i] * t[i] * as.y()) < 0) continue;
							dy[total++] = nextDY; nextDY = -nextDY;
						}
					}
				}
				if(p[3].y() == y) {
					if(nextDY > 0 && total > 0) { total--; nextDY = -1; }
					if((p[3].y() < p[2].y() || (p[3].y() == p[2].y() && (p[3].y() < p[1].y() || (p[3].y() == p[1].y() && p[3].y() < p[0].y())))) && total < 3) {
						x[total] = p[3].x(); if (nextDY < 0) { dy[total++] = -1; nextDY = 1; }
					}
				}
				if(nextDY != (y >= p[3].y() ? 1 : -1)) {
					if(total > 0) total--;
					else {
						if(abs(p[3].y() - y) < abs(p[0].y() - y))
							x[total] = p[3].x();
						dy[total++] = nextDY;
					}
				} return total;
			}
			@Override public void bound(Vec4 lbrt) {
				pointBounds(p[0], lbrt);
				pointBounds(p[3], lbrt);
				Vec2 a0 = sub(p[1], p[0]);
				Vec2 a1 = mul(sub(sub(p[2], p[1]), a0), 2);
				Vec2 a2 = sub(add(sub(p[3], mul(p[2], 3)), mul(p[1], 3)), p[0]);
				double[] params = new double[2]; int solutions;
				solutions = solveQuadratic(params, a2.x(), a1.x(), a0.x());
				for(int i = 0; i < solutions; i++) if(params[i] > 0 && params[i] < 1) pointBounds(point(Proxy.as(params[i])), lbrt);
				solutions = solveQuadratic(params, a2.y(), a1.y(), a0.y());
				for(int i = 0; i < solutions; i++) if(params[i] > 0 && params[i] < 1) pointBounds(point(Proxy.as(params[i])), lbrt);
			}
			@Override public void moveStartPoint(Vec2 to) { p[1] = add(sub(to, p[0]), p[1]); p[0] = to; }
			@Override public void moveEndPoint(Vec2 to) { p[2] = add(sub(to, p[3]), p[2]); p[3] = to; }
			@Override public void splitInThirds(EdgeSegment part1, EdgeSegment part2, EdgeSegment part3) {
				((CubicSegment) part1).p[0] = p[0];
				((CubicSegment) part1).p[1] = all(equal(p[0], p[1])) ? p[0] : mix(p[0], p[1], 1 / 3.);
				((CubicSegment) part1).p[2] = mix(mix(p[0], p[1], 1 / 3.), mix(p[1], p[2], 1 / 3.), 1 / 3.);
				((CubicSegment) part1).p[3] = point(Proxy.as(1 / 3.));
				((CubicSegment) part1).color = color;
				((CubicSegment) part2).p[0] = point(Proxy.as(1 / 3.));
				((CubicSegment) part2).p[1] = mix(mix(mix(p[0], p[1], 1 / 3.), mix(p[1], p[2], 1 / 3.), 1 / 3.), mix(mix(p[1], p[2], 1 / 3.), mix(p[2], p[3], 1 / 3.), 1 / 3.), 2 / 3.);
				((CubicSegment) part2).p[2] = mix(mix(mix(p[0], p[1], 2 / 3.), mix(p[1], p[2], 2 / 3.), 2 / 3.), mix(mix(p[1], p[2], 2 / 3.), mix(p[2], p[3], 2 / 3.), 2 / 3.), 1 / 3.);
				((CubicSegment) part2).p[3] = point(Proxy.as(2 / 3.));
				((CubicSegment) part2).color = color;
				((CubicSegment) part3).p[0] = point(Proxy.as(2 / 3.));
				((CubicSegment) part3).p[1] = mix(mix(p[1], p[2], 2 / 3.), mix(p[2], p[3], 2 / 3.), 2 / 3.);
				((CubicSegment) part3).p[2] = all(equal(p[2], p[3])) ? p[3] : mix(p[2], p[3], 2 / 3.);
				((CubicSegment) part2).p[3] = p[3];
				((CubicSegment) part3).color = color;
			}
		}

		public enum EdgeColor {
			BLACK(0), RED(1), GREEN(2), YELLOW(3), BLUE(4), MAGENTA(5), CYAN(6), WHITE(7);

			public final int c;
			EdgeColor(int c) {
				this.c = c;
			}
		}
		public static class SignedDistance {
			public static final SignedDistance INFINITE = new SignedDistance(-1e240, 1);

			protected double distance;
			protected double dot;

			public SignedDistance(double distance, double dot) {
				this.distance = distance;
				this.dot = dot;
			}

			public double getDistance() { return distance; }
			public double getDot() { return dot; }

			public void setDistance(double distance) { this.distance = distance; }
			public void setDot(double dot) { this.dot = dot; }

			public static boolean lessThan(SignedDistance a, SignedDistance b) { return abs(a.distance) < abs(b.distance) || (abs(a.distance) == abs(b.distance) && a.dot < b.dot); }
			public static boolean greaterThan(SignedDistance a, SignedDistance b) { return abs(a.distance) > abs(b.distance) || (abs(a.distance) == abs(b.distance) && a.dot > b.dot); }
			public static boolean lessEqualThan(SignedDistance a, SignedDistance b) { return abs(a.distance) < abs(b.distance) || (abs(a.distance) == abs(b.distance) && a.dot <= b.dot); }
			public static boolean greaterEqualThan(SignedDistance a, SignedDistance b) { return abs(a.distance) > abs(b.distance) || (abs(a.distance) == abs(b.distance) && a.dot >= b.dot); }
		}

		// UTILS
		static int solveQuadratic(double[] x, double a, double b, double c) {
			if(a == 0 || abs(b) + abs(c) > 1e12 * abs(a)) {
				if(b == 0 || abs(c) > 1e12 * abs(b))
					return c == 0 ? -1 : 0;
				x[0] = -c / b; return 1;
			}
			double dscr = b * b - 4 * a * c;
			if(dscr > 0) {
				dscr = sqrt(dscr);
				x[0] = (-b + dscr) / (2 * a);
				x[1] = (-b - dscr) / (2 * a);
				return 2;
			} else if(dscr == 0) {
				x[0] = -b / (2 * a);
				return 1;
			} else return 0;
		}
		static int solveCubicNormed(double[] x, double a, double b, double c) {
			double a2 = a * a;
			double q  = (a2 - 3 * b) / 9;
			double r  = (a * (2 * a2 - 9 * b) + 27 * c) / 54;
			double r2 = r * r;
			double q3 = q * q * q;
			double A, B;
			if(r2 < q3) {
				double t = r / sqrt(q3);
				if(t < -1) t = -1;
				if(t > 1) t = 1;
				t = acos(t);
				a /= 3; q = -2 * sqrt(q);
				x[0] = q * cos(t/3)-a;
				x[1] = q * cos((t + 2 * PI) / 3) - a;
				x[2] = q * cos((t - 2 * PI) / 3) - a;
				return 3;
			} else {
				A = -pow(abs(r) + sqrt(r2 - q3), 1 / 3.);
				if(r < 0) A = -A;
				B = A == 0 ? 0 : q / A;
				a /= 3;
				x[0] = (A + B) - a;
				x[1] = -0.5 * (A + B) - a;
				x[2] = 0.5 * sqrt(3.) * (A - B);
				if (abs(x[2]) < 1e-14) return 2;
				return 1;
			}
		}
		static int solveCubic(double[] x, double a, double b, double c, double d) {
			if (a != 0) { double bn = b / a, cn = c / a, dn = d / a;
				if(abs(bn) < 1e12 && abs(cn) < 1e12 && abs(dn) < 1e12)
					return solveCubicNormed(x, bn, cn, dn);
			} return solveQuadratic(x, b, c, d);
		}

		static void pointBounds(Vec2 p, Vec4 lbrt) {
			if (p.x() < lbrt.x()) lbrt.x(p.x());
			if (p.y() < lbrt.y()) lbrt.y(p.y());
			if (p.x() > lbrt.z()) lbrt.z(p.x());
			if (p.y() > lbrt.w()) lbrt.w(p.y());
		}
	}

//	class EdgeSegment {
//
//		public:
//		EdgeColor color;
//
//		EdgeSegment(EdgeColor edgeColor = WHITE) : color(edgeColor) { }
//		virtual ~EdgeSegment() { }
//		/// Creates a copy of the edge segment.
//		virtual EdgeSegment * clone() = 0;
//		/// Returns the point on the edge specified by the parameter (between 0 and 1).
//		virtual Vec2 point(double param) = 0;
//		/// Returns the direction the edge has at the point specified by the parameter.
//		virtual Vec2 direction(double param) = 0;
//		/// Returns the minimum signed distance between origin and the edge.
//		virtual SignedDistance signedDistance(Vec2 origin, double &param) = 0;
//		/// Converts a previously retrieved signed distance from origin to pseudo-distance.
//		virtual void distanceToPseudoDistance(SignedDistance &distance, Vec2 origin, double param) const;
//		/// Outputs a list of (at most three) intersections (their X coordinates) with an infinite horizontal scanline at y and returns how many there are.
//		virtual int scanlineIntersections(double x[3], int dy[3], double y) = 0;
//		/// Adjusts the bounding box to fit the edge segment.
//		virtual void bound(double &l, double &b, double &r, double &t) = 0;
//
//		/// Moves the start point of the edge segment.
//		virtual void moveStartPoint(Vec2 to) = 0;
//		/// Moves the end point of the edge segment.
//		virtual void moveEndPoint(Vec2 to) = 0;
//		/// Splits the edge segments into thirds which together represent the original edge.
//		virtual void splitInThirds(EdgeSegment *&part1, EdgeSegment *&part2, EdgeSegment *&part3) = 0;
//
//	};
//
//	/// A line segment.
//	class LinearSegment : public EdgeSegment {
//
//		public:
//		Vec2 p[2];
//
//		LinearSegment(Vec2 p0, Vec2 p1, EdgeColor edgeColor = WHITE);
//		LinearSegment * clone() const;
//		Vec2 point(double param) const;
//		Vec2 direction(double param) const;
//		SignedDistance signedDistance(Vec2 origin, double &param) const;
//		int scanlineIntersections(double x[3], int dy[3], double y) const;
//		void bound(double &l, double &b, double &r, double &t) const;
//
//		void moveStartPoint(Vec2 to);
//		void moveEndPoint(Vec2 to);
//		void splitInThirds(EdgeSegment *&part1, EdgeSegment *&part2, EdgeSegment *&part3) const;
//
//	};
//
//	/// A quadratic Bezier curve.
//	class QuadraticSegment : public EdgeSegment {
//
//		public:
//		Vec2 p[3];
//
//		QuadraticSegment(Vec2 p0, Vec2 p1, Vec2 p2, EdgeColor edgeColor = WHITE);
//		QuadraticSegment * clone() const;
//		Vec2 point(double param) const;
//		Vec2 direction(double param) const;
//		SignedDistance signedDistance(Vec2 origin, double &param) const;
//		int scanlineIntersections(double x[3], int dy[3], double y) const;
//		void bound(double &l, double &b, double &r, double &t) const;
//
//		void moveStartPoint(Vec2 to);
//		void moveEndPoint(Vec2 to);
//		void splitInThirds(EdgeSegment *&part1, EdgeSegment *&part2, EdgeSegment *&part3) const;
//
//	};
//
//	/// A cubic Bezier curve.
//	class CubicSegment : public EdgeSegment {
//
//		public:
//		Vec2 p[4];
//
//		CubicSegment(Vec2 p0, Vec2 p1, Vec2 p2, Vec2 p3, EdgeColor edgeColor = WHITE);
//		CubicSegment * clone() const;
//		Vec2 point(double param) const;
//		Vec2 direction(double param) const;
//		SignedDistance signedDistance(Vec2 origin, double &param) const;
//		int scanlineIntersections(double x[3], int dy[3], double y) const;
//		void bound(double &l, double &b, double &r, double &t) const;
//
//		void moveStartPoint(Vec2 to);
//		void moveEndPoint(Vec2 to);
//		void splitInThirds(EdgeSegment *&part1, EdgeSegment *&part2, EdgeSegment *&part3) const;
//
//	};
//
//}
}
