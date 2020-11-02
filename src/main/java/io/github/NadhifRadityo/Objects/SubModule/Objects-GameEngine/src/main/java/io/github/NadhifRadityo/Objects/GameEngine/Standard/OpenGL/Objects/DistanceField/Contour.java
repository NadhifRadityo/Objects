package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeSegment;

import java.util.ArrayList;

import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeSegment.enhanceNormalize;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.add;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.cross;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.dot;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.min;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.mul;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.mulX;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sign;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sqrt;

public class Contour {
	protected ArrayList<EdgeSegment<?>> edges = new ArrayList<>();

	public ArrayList<EdgeSegment<?>> getEdges() { return edges; }
	public void addEdge(EdgeSegment<?> edge) { edges.add(edge); }
	public void bound(Vec4 lbrt) { for(EdgeSegment<?> edge : edges) edge.bound(lbrt); }
	public void boundMiters(Vec4 lbrt, double border, double miterLimit, int polarity) {
		if(edges.isEmpty()) return;
		Vec2 prevDir = enhanceNormalize(edges.get(edges.size() - 1).direction(1), true);
		for(EdgeSegment<?> edge : edges) {
			Vec2 dir = mul(enhanceNormalize(edge.direction(0), true), -1);
			if(polarity * cross(prevDir, dir) >= 0) {
				double miterLength = miterLimit;
				double q = .5 * (1 - dot(prevDir, dir));
				if(q > 0) miterLength = min(1 / sqrt(q), miterLimit);
				Vec2 miter = add(edge.point(0), mulX(enhanceNormalize(add(prevDir, dir), true), border * miterLength));
				if(miter.x() < lbrt.x()) lbrt.x(miter.x());
				if(miter.y() < lbrt.y()) lbrt.y(miter.y());
				if(miter.x() > lbrt.z()) lbrt.z(miter.x());
				if(miter.y() > lbrt.w()) lbrt.w(miter.y());
			} prevDir = enhanceNormalize(edge.direction(1), true);
		}
	}
	public int winding() {
		if(edges.isEmpty()) return 0;
		double total = 0;
		if (edges.size() == 1) {
			Vec2 a = edges.get(0).point(0);
			Vec2 b = edges.get(0).point(1 / 3.);
			Vec2 c = edges.get(0).point(2 / 3.);
			total += shoelace(a, b);
			total += shoelace(b, c);
			total += shoelace(c, a);
		} else if (edges.size() == 2) {
			Vec2 a = edges.get(0).point(0);
			Vec2 b = edges.get(0).point(.5);
			Vec2 c = edges.get(1).point(0);
			Vec2 d = edges.get(1).point(.5);
			total += shoelace(a, b);
			total += shoelace(b, c);
			total += shoelace(c, d);
			total += shoelace(d, a);
		} else {
			Vec2 prev = edges.get(edges.size() - 1).point(0);
			for(EdgeSegment<?> edge : edges) {
				Vec2 cur = edge.point(0);
				total += shoelace(prev, cur);
				prev = cur;
			}
		} return (int) sign(total);
	}
	static double shoelace(Vec2 a, Vec2 b) { return (b.x() - a.x()) * (a.y() + b.y()); }
}
