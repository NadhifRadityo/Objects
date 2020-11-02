package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.abs;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.add;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.all;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.cross;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.dot;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.equal;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.length;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.mix;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.mul;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.nonZeroSign;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sub;

public class CubicSegment extends EdgeSegment<CubicSegment> {
	public CubicSegment(Vec2 p0, Vec2 p1, Vec2 p2, Vec2 p3, EdgeColor color) { super(new Vec2[] { p0, p1, p2, p3 }, color); }
	public CubicSegment() { this(null, null, null, null, null); }

	@Override public CubicSegment clone() { return new CubicSegment((Vec2) points[0].clone(), (Vec2) points[1].clone(), (Vec2) points[2].clone(), (Vec2) points[3].clone(), color); }
	@Override public Vec2 point(double param) { Vec2 p12 = mix(points[1], points[2], param); return mix(mix(mix(points[0], points[1], param), p12, param), mix(p12, mix(points[2], points[3], param), param), param); }
	@Override public Vec2 direction(double param) {
		Vec2 tangent = mix(mix(sub(points[1], points[0]), sub(points[2], points[1]), param),
				mix(sub(points[2], points[1]), sub(points[3], points[2]), param), param);
		if(tangent.x() == 0 && tangent.y() == 0) {
			if(param == 0) return sub(points[2], points[0]);
			if(param == 1) return sub(points[3], points[1]);
		} return tangent;
	}
	@Override public SignedDistance signedDistance(Vec2 origin) {
		Vec2 qa = sub(points[0], origin);
		Vec2 ab = sub(points[1], points[0]);
		Vec2 br = sub(sub(points[2], points[1]), ab);
		Vec2 as = sub(sub(sub(points[3], points[2]), sub(points[2], points[1])), br);

		Vec2 epDir = direction(0);
		double minDistance = nonZeroSign(cross(epDir, qa)) * length(qa);
		double param = -dot(qa, epDir) / dot(epDir, epDir);
		{
			epDir = direction(1);
			double distance = nonZeroSign(cross(epDir, sub(points[3], origin))) * length(sub(points[3], origin));
			if(abs(distance) < abs(minDistance)) {
				minDistance = distance;
				param = dot(sub(epDir, sub(points[3], origin)), epDir) / dot(epDir, epDir);
			}
		}
		for(int i = 0; i <= 4; ++i) {
			double t = (double) i / 4;
			for(int step = 0; ; step++) {
				Vec2 qe = sub(add(add(add(points[0], mul(ab, 3 * t)), mul(br, 3 * t * t)), mul(as, t * t * t)), origin);
				double distance = nonZeroSign(cross(direction(t), qe)) * length(qe);
				if(abs(distance) < abs(minDistance)) {
					minDistance = distance; param = t;
				} if(step == 4) break;
				Vec2 d1 = add(add(mul(as, 3 * t * t), mul(br, 6 * t)), mul(ab, 3));
				Vec2 d2 = add(mul(as, 6 * t), mul(br, 6));
				t -= dot(qe, d1) / (dot(d1, d1) + dot(qe, d2));
				if(t < 0 || t > 1) break;
			}
		}

		if(param >= 0 && param <= 1) return new SignedDistance(minDistance, 0, param);
		if(param < .5) return new SignedDistance(minDistance, abs(dot(enhanceNormalize(direction(0)), enhanceNormalize(qa))), param);
		else return new SignedDistance(minDistance, abs(dot(enhanceNormalize(direction(1)), enhanceNormalize(sub(points[3], origin)))), param);
	}
	@Override public int scanLineIntersections(double[] x, int[] dy, double y) {
		int total = 0;
		int nextDY = y > points[0].y() ? 1 : -1;
		x[total] = points[0].x();
		if(points[0].y() == y) {
			if(points[0].y() < points[1].y() || (points[0].y() == points[1].y() && (points[0].y() < points[2].y() || (points[0].y() == points[2].y() && points[0].y() < points[3].y()))))
				dy[total++] = 1;
			else nextDY = 1;
		}
		{
			Vec2 ab = sub(points[1], points[0]);
			Vec2 br = sub(sub(points[2], points[1]), ab);
			Vec2 as = sub(sub(sub(points[3], points[2]), sub(points[2], points[1])), br);
			double[] t = new double[3];
			int solutions = solveCubic(t, as.y(), 3 * br.y(), 3 * ab.y(), points[0].y() - y);
			double tmp;
			if(solutions >= 2) {
				if(t[0] > t[1]) { tmp = t[0]; t[0] = t[1]; t[1] = tmp; }
				if(solutions >= 3 && t[1] > t[2]) {
					tmp = t[1]; t[1] = t[2]; t[2] = tmp;
					if(t[0] > t[1]) { tmp = t[0]; t[0] = t[1]; t[1] = tmp; }
				}
			}
			for(int i = 0; i < solutions && total < 3; ++i) {
				if(t[i] >= 0 && t[i] <= 1) {
					x[total] = points[0].x() + 3 * t[i] * ab.x() + 3 * t[i] * t[i] * br.x() + t[i] * t[i] * t[i] * as.x();
					if(nextDY * (ab.y() + 2 * t[i] * br.y() + t[i] * t[i] * as.y()) < 0) continue;
					dy[total++] = nextDY; nextDY = -nextDY;
				}
			}
		}
		if(points[3].y() == y) {
			if(nextDY > 0 && total > 0) { total--; nextDY = -1; }
			if((points[3].y() < points[2].y() || (points[3].y() == points[2].y() && (points[3].y() < points[1].y() || (points[3].y() == points[1].y() && points[3].y() < points[0].y())))) && total < 3) {
				x[total] = points[3].x(); if(nextDY < 0) { dy[total++] = -1; nextDY = 1; }
			}
		}
		if(nextDY != (y >= points[3].y() ? 1 : -1)) {
			if(total > 0) total--;
			else {
				if(abs(points[3].y() - y) < abs(points[0].y() - y))
					x[total] = points[3].x();
				dy[total++] = nextDY;
			}
		} return total;
	}
	@Override public void bound(Vec4 lbrt) {
		pointBounds(points[0], lbrt);
		pointBounds(points[3], lbrt);
		Vec2 a0 = sub(points[1], points[0]);
		Vec2 a1 = mul(sub(sub(points[2], points[1]), a0), 2);
		Vec2 a2 = sub(add(sub(points[3], mul(points[2], 3)), mul(points[1], 3)), points[0]);
		double[] params = new double[2]; int solutions;
		solutions = solveQuadratic(params, a2.x(), a1.x(), a0.x());
		for(int i = 0; i < solutions; i++) if(params[i] > 0 && params[i] < 1) pointBounds(point(params[i]), lbrt);
		solutions = solveQuadratic(params, a2.y(), a1.y(), a0.y());
		for(int i = 0; i < solutions; i++) if(params[i] > 0 && params[i] < 1) pointBounds(point(params[i]), lbrt);
	}
	@Override public void moveStartPoint(Vec2 to) { points[1] = add(sub(to, points[0]), points[1]); points[0] = to; }
	@Override public void moveEndPoint(Vec2 to) { points[2] = add(sub(to, points[3]), points[2]); points[3] = to; }
	@Override public void splitInThirds(CubicSegment part1, CubicSegment part2, CubicSegment part3) {
		part1.points[0] = points[0];
		part1.points[1] = all(equal(points[0], points[1])) ? points[0] : mix(points[0], points[1], 1 / 3.);
		part1.points[2] = mix(mix(points[0], points[1], 1 / 3.), mix(points[1], points[2], 1 / 3.), 1 / 3.);
		part1.points[3] = point(1 / 3.);
		part1.color = color;
		part2.points[0] = point(1 / 3.);
		part2.points[1] = mix(mix(mix(points[0], points[1], 1 / 3.), mix(points[1], points[2], 1 / 3.), 1 / 3.), mix(mix(points[1], points[2], 1 / 3.), mix(points[2], points[3], 1 / 3.), 1 / 3.), 2 / 3.);
		part2.points[2] = mix(mix(mix(points[0], points[1], 2 / 3.), mix(points[1], points[2], 2 / 3.), 2 / 3.), mix(mix(points[1], points[2], 2 / 3.), mix(points[2], points[3], 2 / 3.), 2 / 3.), 1 / 3.);
		part2.points[3] = point(2 / 3.);
		part2.color = color;
		part3.points[0] = point(2 / 3.);
		part3.points[1] = mix(mix(points[1], points[2], 2 / 3.), mix(points[2], points[3], 2 / 3.), 2 / 3.);
		part3.points[2] = all(equal(points[2], points[3])) ? points[3] : mix(points[2], points[3], 2 / 3.);
		part3.points[3] = points[3];
		part3.color = color;
	}
	@Override public CubicSegment create() { return new CubicSegment(); }
}
