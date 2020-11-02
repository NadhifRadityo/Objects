package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.abs;

public class SignedDistance {
	public static final SignedDistance INFINITE = new SignedDistance();

	protected double distance;
	protected double dot;
	protected double param;

	public SignedDistance(double distance, double dot, double param) {
		this.distance = distance;
		this.dot = dot;
		this.param = param;
	}
	public SignedDistance() { this(-1e240, 1, 0); }

	public double getDistance() { return distance; }
	public double getDot() { return dot; }
	public double getParam() { return param; }

	public void setDistance(double distance) { this.distance = distance; }
	public void setDot(double dot) { this.dot = dot; }

	@Override public SignedDistance clone() { return new SignedDistance(distance, dot, 0); }
	@Override public String toString() { return "SignedDistance[" + "distance=" + distance + ", dot=" + dot + ']'; }

	public static boolean lessThan(SignedDistance a, SignedDistance b) { return abs(a.distance) < abs(b.distance) || (abs(a.distance) == abs(b.distance) && a.dot < b.dot); }
	public static boolean greaterThan(SignedDistance a, SignedDistance b) { return abs(a.distance) > abs(b.distance) || (abs(a.distance) == abs(b.distance) && a.dot > b.dot); }
	public static boolean lessEqualThan(SignedDistance a, SignedDistance b) { return abs(a.distance) < abs(b.distance) || (abs(a.distance) == abs(b.distance) && a.dot <= b.dot); }
	public static boolean greaterEqualThan(SignedDistance a, SignedDistance b) { return abs(a.distance) > abs(b.distance) || (abs(a.distance) == abs(b.distance) && a.dot >= b.dot); }
}
