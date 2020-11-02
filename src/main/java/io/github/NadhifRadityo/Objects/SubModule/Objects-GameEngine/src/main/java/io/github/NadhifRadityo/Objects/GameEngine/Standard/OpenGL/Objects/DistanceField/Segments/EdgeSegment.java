package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.abs;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.acos;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.cos;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.cross;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.dot;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.length;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.pow;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sqrt;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sub;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.vec2;
import static java.lang.Math.PI;

public abstract class EdgeSegment<SELF extends EdgeSegment<SELF>> {
	protected final Vec2[] points;
	protected EdgeColor color;

	public EdgeSegment(Vec2[] points, EdgeColor color) {
		this.points = points;
		this.color = color;
	}

	public Vec2[] getPoints() { return points; }
	public EdgeColor getColor() { return color; }
	public void setColor(EdgeColor color) { this.color = color; }

	public abstract SELF clone();
	public abstract Vec2 point(double param);
	public abstract Vec2 direction(double param);
	public abstract SignedDistance signedDistance(Vec2 origin);
	public void distanceToPseudoDistance(SignedDistance distance, Vec2 origin, double param) {
		if(param >= 0 && param <= 1) return;
		param = param < 0 ? 0 : param > 1 ? 1 : param;
		Vec2 dir = enhanceNormalize(direction(param));
		Vec2 aq = sub(origin, point(param));
		double ts = dot(aq, dir);
		if(param == 1 ? ts <= 0 : param != 0 || ts >= 0) return;
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
	public abstract void splitInThirds(SELF part1, SELF part2, SELF part3);
	public abstract SELF create();

	// UTILS
	protected static int solveQuadratic(double[] x, double a, double b, double c) {
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
	protected static int solveCubicNormed(double[] x, double a, double b, double c) {
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
			x[0] = q * cos(t / 3) - a;
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
			if(abs(x[2]) < 1e-14) return 2;
			return 1;
		}
	}
	protected static int solveCubic(double[] x, double a, double b, double c, double d) {
		if(a != 0) { double bn = b / a, cn = c / a, dn = d / a;
			if(abs(bn) < 1e12 && abs(cn) < 1e12 && abs(dn) < 1e12)
				return solveCubicNormed(x, bn, cn, dn);
		} return solveQuadratic(x, b, c, d);
	}
	protected static void pointBounds(Vec2 p, Vec4 lbrt) {
		if(p.x() < lbrt.x()) lbrt.x(p.x());
		if(p.y() < lbrt.y()) lbrt.y(p.y());
		if(p.x() > lbrt.z()) lbrt.z(p.x());
		if(p.y() > lbrt.w()) lbrt.w(p.y());
	}
	public static Vec2 enhanceNormalize(Vec2 x, boolean allowZero) { double a = length(x); return a == 0 ? vec2(0, allowZero ? 0 : 1) : vec2(x.x() / a, x.y() / a); }
	public static Vec2 enhanceNormalizeX(Vec2 x, boolean allowZero) { double a = length(x); x.set(a == 0 ? 0 : x.x() / a, a == 0 ? (allowZero ? 0 : 1) : x.y() / a); return x; }
	public static Vec2 enhanceNormalize(Vec2 x) { return enhanceNormalize(x, false); }
	public static Vec2 enhanceNormalizeX(Vec2 x) { return enhanceNormalizeX(x, false); }
}
