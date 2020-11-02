package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.abs;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.add;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.cross;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.dot;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.length;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.mix;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.mul;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.nonZeroSign;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sub;

public class QuadraticSegment extends EdgeSegment<QuadraticSegment> {
	public QuadraticSegment(Vec2 p0, Vec2 p1, Vec2 p2, EdgeColor color) { super(new Vec2[] { p0, p1, p2 }, color); }
	public QuadraticSegment() { this(null, null, null, null); }

	@Override public QuadraticSegment clone() { return new QuadraticSegment((Vec2) points[0].clone(), (Vec2) points[1].clone(), (Vec2) points[2].clone(), color); }
	@Override public Vec2 point(double param) { return mix(mix(points[0], points[1], param), mix(points[1], points[2], param), param); }
	@Override public Vec2 direction(double param) {
		Vec2 tangent = mix(sub(points[1], points[0]), sub(points[2], points[1]), param);
		if(tangent.x() == 0 && tangent.y() == 0) return sub(points[2], points[0]); return tangent;
	}
	@Override public SignedDistance signedDistance(Vec2 origin) {
		Vec2 qa = sub(points[0], origin);
		Vec2 ab = sub(points[1], points[0]);
		Vec2 br = sub(sub(points[2], points[1]), ab);
		double a = dot(br, br);
		double b = 3 * dot(ab, br);
		double c = 2 * dot(ab, ab) + dot(qa, br);
		double d = dot(qa, ab);
		double[] t = new double[3];
		int solutions = solveCubic(t, a, b, c, d);

		Vec2 epDir = direction(0);
		double minDistance = nonZeroSign(cross(epDir, qa)) * length(qa);
		double param = -dot(qa, epDir) / dot(epDir, epDir);
		{
			epDir = direction(1);
			double distance = nonZeroSign(cross(epDir, sub(points[2], origin))) * length(sub(points[2], origin));
			if(abs(distance) < abs(minDistance)) {
				minDistance = distance;
				param = dot(sub(origin, points[1]), epDir) / dot(epDir, epDir);
			}
		}
		for(int i = 0; i < solutions; ++i) {
			if(t[i] <= 0 || t[i] >= 1) continue;
			Vec2 qe = sub(add(add(points[0], mul(ab, 2 * t[i])), mul(br, t[i] * t[i])), origin);
			double distance = nonZeroSign(cross(sub(points[2], points[0]), qe)) * length(qe);
			if(abs(distance) > abs(minDistance)) continue;
			minDistance = distance; param = t[i];
		}

		if(param >= 0 && param <= 1) return new SignedDistance(minDistance, 0, param);
		if(param < .5) return new SignedDistance(minDistance, abs(dot(enhanceNormalize(direction(0)), enhanceNormalize(qa))), param);
		else return new SignedDistance(minDistance, abs(dot(enhanceNormalize(direction(1)), enhanceNormalize(sub(points[2], origin)))), param);
	}
	@Override public int scanLineIntersections(double[] x, int[] dy, double y) {
		int total = 0;
		int nextDY = y > points[0].y() ? 1 : -1;
		x[total] = points[0].x();
		if(points[0].y() == y) {
			if(points[0].y() < points[1].y() || (points[0].y() == points[1].y() && points[0].y() < points[2].y()))
				dy[total++] = 1;
			else nextDY = 1;
		}
		{
			Vec2 ab = sub(points[1], points[0]);
			Vec2 br = sub(sub(points[2], points[1]), ab);
			double[] t = new double[2];
			int solutions = solveQuadratic(t, br.y(), 2 * ab.y(), points[0].y() - y);
			double tmp;
			if(solutions >= 2 && t[0] > t[1]) {
				tmp = t[0]; t[0] = t[1]; t[1] = tmp; }
			for(int i = 0; i < solutions && total < 2; i++) {
				if(t[i] < 0 || t[i] > 1) continue;
				x[total] = points[0].x() + 2 * t[i] * ab.x() + t[i] * t[i] * br.x();
				if(nextDY * (ab.y() + t[i] * br.y()) < 0) continue;
				dy[total++] = nextDY; nextDY = -nextDY;
			}
		}
		if(points[2].y() == y) {
			if(nextDY > 0 && total > 0) { --total; nextDY = -1; }
			if((points[2].y() < points[1].y() || (points[2].y() == points[1].y() && points[2].y() < points[0].y())) && total < 2) {
				x[total] = points[2].x(); if(nextDY < 0) { dy[total++] = -1; nextDY = 1; }
			}
		}
		if(nextDY != (y >= points[2].y() ? 1 : -1)) {
			if(total > 0) total--;
			else {
				if(abs(points[2].y() - y) < abs(points[0].y() - y))
					x[total] = points[2].x();
				dy[total++] = nextDY;
			}
		} return total;
	}
	@Override public void bound(Vec4 lbrt) {
		pointBounds(points[0], lbrt);
		pointBounds(points[2], lbrt);
		Vec2 bot = sub(sub(points[1], points[0]), sub(points[2], points[1]));
		if(bot.x() != 0) {
			double param = (points[1].x() - points[0].x()) / bot.x();
			if(param > 0 && param < 1) pointBounds(point(param), lbrt);
		}
		if(bot.y() != 0) {
			double param = (points[1].y() - points[0].y()) / bot.y();
			if(param > 0 && param < 1) pointBounds(point(param), lbrt);
		}
	}
	@Override public void moveStartPoint(Vec2 to) {
		Vec2 origSDir = sub(points[0], points[1]);
		Vec2 origP1 = points[1];
		points[1] = add(mul(sub(points[2], points[1]), cross(sub(points[0], points[1]), sub(to, points[0])) / cross(sub(points[0], points[1]), sub(points[2], points[1]))), points[1]);
		points[0] = to; if(dot(origSDir, sub(points[0], points[1])) < 0) points[1] = origP1;
	}
	@Override public void moveEndPoint(Vec2 to) {
		Vec2 origEDir = sub(points[2], points[1]);
		Vec2 origP1 = points[1];
		points[1] = add(mul(sub(points[0], points[1]), cross(sub(points[2], points[1]), sub(to, points[2])) / cross(sub(points[2], points[1]), sub(points[0], points[1]))), points[1]);
		points[2] = to; if(dot(origEDir, sub(points[2], points[1])) < 0) points[1] = origP1;
	}
	@Override public void splitInThirds(QuadraticSegment part1, QuadraticSegment part2, QuadraticSegment part3) {
		part1.points[0] = points[0];
		part1.points[1] = mix(points[0], points[1], 1 / 3.);
		part1.points[2] = point(1 / 3.);
		part1.color = color;
		part2.points[0] = point(1 / 3.);
		part2.points[1] = mix(mix(points[0], points[1], 5 / 9.), mix(points[1], points[2], 4 / 9.), .5);
		part2.points[2] = point(2 / 3.);
		part2.color = color;
		part3.points[0] = point(2 / 3.);
		part3.points[1] = mix(points[1], points[2], 2/3.);
		part3.points[2] = points[2];
		part3.color = color;
	}
	@Override public QuadraticSegment create() { return new QuadraticSegment(); }
}
