package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.CubicSegment;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeColor;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeSegment;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.LinearSegment;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.QuadraticSegment;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.any;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.length;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.notEqual;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sub;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.vec2;

public class ContourShape {
	protected ArrayList<Contour> contours;
	protected boolean inverseYAxis;

	public ContourShape(boolean inverseYAxis) {
		this.contours = new ArrayList<>();
		this.inverseYAxis = inverseYAxis;
	}

	public ArrayList<Contour> getContours() { return contours; }
	public boolean isInverseYAxis() { return inverseYAxis; }

	public static ContourShape convertFromShape(Shape shape) {
		Rectangle bounds = shape.getBounds(); double boundsLength = length(new double[] { bounds.width, bounds.height });
		PathIterator pathIterator = shape.getPathIterator(new AffineTransform());
		double[] coordinates = new double[6]; double x = 0, y = 0; Vec2 start = vec2(0);

		ContourShape contourShape = new ContourShape(true);
		Contour contour = null;
		while(!pathIterator.isDone()) {
			int code = pathIterator.currentSegment(coordinates);
			double x1 = coordinates[0]; double y1 = coordinates[1];
			double x2 = coordinates[2]; double y2 = coordinates[3];
			double x3 = coordinates[4]; double y3 = coordinates[5];
			switch(code) {
				case PathIterator.SEG_MOVETO: {
					contour = new Contour();
					contourShape.addContour(contour);
					x = x1; y = y1; start.set(x, y); break;
				}
				case PathIterator.SEG_LINETO: {
					assert contour != null;
					contour.addEdge(new LinearSegment(vec2(x, y), vec2(x1, y1), EdgeColor.WHITE));
					x = x1; y = y1; break;
				}
				case PathIterator.SEG_QUADTO: {
					assert contour != null;
					contour.addEdge(new QuadraticSegment(vec2(x, y), vec2(x1, y1), vec2(x2, y2), EdgeColor.WHITE));
					x = x2; y = y2; break;
				}
				case PathIterator.SEG_CUBICTO: {
					assert contour != null;
					contour.addEdge(new CubicSegment(vec2(x, y), vec2(x1, y1), vec2(x2, y2), vec2(x3, y3), EdgeColor.WHITE));
					x = x3; y = y3; break;
				}
				case PathIterator.SEG_CLOSE: {
					if(contour == null) break;
					if(!contour.getEdges().isEmpty() && (x != start.x() || y != start.y())) {
						int size = contour.getEdges().size();
						if(length(sub(contour.getEdges().get(size - 1).point(1), contour.getEdges().get(0).point(0))) < 1/16384. * boundsLength)
							contour.getEdges().get(size - 1).moveEndPoint(contour.getEdges().get(0).point(0));
						else contour.addEdge(new LinearSegment(vec2(x, y), vec2(start.d), EdgeColor.WHITE));
					} contour = null; break;
				}
			} pathIterator.next();
		} return contourShape;
	}

	public void addContour(Contour contour) { contours.add(contour); }
	public <SEGMENT extends EdgeSegment<SEGMENT>> void normalize() {
		ReferencedCallback<EdgeSegment<?>[]> splitInThirds = (args) -> {
			@SuppressWarnings("unchecked") SEGMENT edgeSegment = (SEGMENT) args[0];
			SEGMENT part1 = edgeSegment.create();
			SEGMENT part2 = edgeSegment.create();
			SEGMENT part3 = edgeSegment.create();
			edgeSegment.splitInThirds(part1, part2, part3);
			return new EdgeSegment[] { part1, part2, part3 };
		};
		for(Contour contour : contours)
			if(contour.edges.size() == 1) {
				EdgeSegment<?>[] edgeSegments = splitInThirds.get(contour.edges.get(0));
				contour.edges.clear();
				contour.edges.add(edgeSegments[0]);
				contour.edges.add(edgeSegments[1]);
				contour.edges.add(edgeSegments[2]);
			}
	}
	public boolean validate() {
		for(Contour contour : contours) {
			if(contour.edges.isEmpty()) continue;
			Vec2 corner = contour.edges.get(contour.edges.size() - 1).point(1);
			for(EdgeSegment<?> edge : contour.edges) {
				if(edge == null) return false;
				if(any(notEqual(edge.point(0), corner))) return false;
				corner = edge.point(1);
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
			for(EdgeSegment<?> edge : contour.edges) {
				int n = edge.scanLineIntersections(x, dy, y);
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
