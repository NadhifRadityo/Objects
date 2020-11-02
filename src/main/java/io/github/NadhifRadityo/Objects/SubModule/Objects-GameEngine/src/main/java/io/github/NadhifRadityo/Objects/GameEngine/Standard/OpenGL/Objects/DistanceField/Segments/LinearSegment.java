package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.abs;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.cross;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.dot;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.length;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.mix;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.nonZeroSign;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sign;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sub;

public class LinearSegment extends EdgeSegment<LinearSegment> {
	public LinearSegment(Vec2 p0, Vec2 p1, EdgeColor color) { super(new Vec2[] { p0, p1 }, color); }
	public LinearSegment() { this(null, null, null); }

	@Override public LinearSegment clone() { return new LinearSegment((Vec2) points[0].clone(), (Vec2) points[1].clone(), color); }
	@Override public Vec2 point(double param) { return mix(points[0], points[1], param); }
	@Override public Vec2 direction(double param) { return sub(points[1], points[0]); }
	@Override public SignedDistance signedDistance(Vec2 origin) {
		Vec2 aq = sub(origin, points[0]);
		Vec2 ab = sub(points[1], points[0]);
		double param = dot(aq, ab) / dot(ab, ab);
		Vec2 eq = sub(points[param > .5 ? 1 : 0], origin);
		double endpointDistance = length(eq);
		if(param > 0 && param < 1) {
			Vec2 orthonormal = new Vec2();
			double length = length(ab);
			if(length == 0) orthonormal.set(0, -1);
			else orthonormal.set(ab.y() / length, -ab.x() / length);
			double orthoDistance = dot(orthonormal, aq);
			if(abs(orthoDistance) < endpointDistance)
				return new SignedDistance(orthoDistance, 0, param);
		} return new SignedDistance(nonZeroSign(cross(aq, ab)) * endpointDistance, abs(dot(enhanceNormalize(ab), enhanceNormalize(eq))), param);
	}
	@Override public int scanLineIntersections(double[] x, int[] dy, double y) {
		if((y < points[0].y() || y >= points[1].y()) && (y < points[1].y() || y >= points[0].y())) return 0;
		double param = (y - points[0].y()) / (points[1].y() - points[0].y());
		x[0] = mix(points[0].x(), points[1].x(), param);
		dy[0] = (int) sign(points[1].y() - points[0].y());
		return 1;
	}
	@Override public void bound(Vec4 lbrt) { pointBounds(points[0], lbrt); pointBounds(points[1], lbrt); }
	@Override public void moveStartPoint(Vec2 to) { points[0] = to; }
	@Override public void moveEndPoint(Vec2 to) { points[1] = to; }
	@Override public void splitInThirds(LinearSegment part1, LinearSegment part2, LinearSegment part3) {
		part1.points[0] = points[0];
		part1.points[1] = point(1 / 3.);
		part1.color = color;
		part2.points[0] = point(1 / 3.);
		part2.points[1] = point(2 / 3.);
		part2.color = color;
		part3.points[0] = point(2 / 3.);
		part3.points[1] = points[1];
		part3.color = color;
	}
	@Override public LinearSegment create() { return new LinearSegment(); }
}
