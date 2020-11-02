package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField;

import java.util.ArrayList;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.min;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sign;

public class ScanLine {
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
		while(ax < xFrom || bx < xFrom) {
			double xNext = min(ax, bx);
			if(ax == xNext && ai < a.intersections.size()) {
				aInside = interpretFillRule(a.intersections.get(ai).direction, fillRule);
				ax = ++ai < a.intersections.size() ? a.intersections.get(ai).getX() : xTo;
			}
			if(bx == xNext && bi < b.intersections.size()) {
				bInside = interpretFillRule(b.intersections.get(bi).direction, fillRule);
				bx = ++bi < b.intersections.size() ? b.intersections.get(bi).getX() : xTo;
			}
		}
		double x = xFrom;
		while(ax < xTo || bx < xTo) {
			double xNext = min(ax, bx);
			if(aInside == bInside)
				total += xNext-x;
			if(ax == xNext && ai < a.intersections.size()) {
				aInside = interpretFillRule(a.intersections.get(ai).direction, fillRule);
				ax = ++ai < a.intersections.size() ? a.intersections.get(ai).getX() : xTo;
			}
			if(bx == xNext && bi < b.intersections.size()) {
				bInside = interpretFillRule(b.intersections.get(bi).direction, fillRule);
				bx = ++bi < b.intersections.size() ? b.intersections.get(bi).getX() : xTo;
			}
			x = xNext;
		}
		if(aInside == bInside)
			total += xTo-x;
		return total;
	}

	public ScanLine() { }

	public void setIntersections(ArrayList<Intersection> intersections) { this.intersections = intersections; preprocess(); }
	public int countIntersections(double x) { return moveTo(x) + 1; }
	public int sumIntersections(double x) { int index = moveTo(x); if(index >= 0) return intersections.get(index).getDirection(); return 0; }
	public boolean filled(double x, FillRule fillRule) { return interpretFillRule(sumIntersections(x), fillRule); }

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
		if(intersections.isEmpty()) return -1; int index = lastIndex;
		if(x < intersections.get(index).getX()) {
			do { if(index == 0) { lastIndex = 0; return -1; } --index;
			} while(x < intersections.get(index).getX());
		} else while(index < intersections.size() -1 && x >= intersections.get(index + 1).getX()) ++index;
		lastIndex = index; return index;
	}

	public enum FillRule { FILL_NONZERO, FILL_ODD, FILL_POSITIVE, FILL_NEGATIVE }
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
