package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Oval extends Rectangle {
	protected boolean center;
	
	public Oval(int x, int y, int width, int height, boolean center) {
		super(x, y, width, height);
		this.center = center;
		if(center) {
			this.x -= width / 2;
			this.y -= height / 2;
		}
	}
	public Oval(Point p, int width, int height, boolean center) { this(p.getX(), p.getY(), width, height, center); }
	public Oval(int x, int y, int width, int height) { this(x, y, width, height, false); }
	public Oval(Point p, int width, int height) { this(p.getX(), p.getY(), width, height); }
	
	public int getX(boolean center) { return center ? x + width / 2 : (this.center ? x - width / 2 : x); }
	public int getY(boolean center) { return center ? y + height / 2 : (this.center ? y - height / 2 : y); }
	@Override public int getX() { return getX(false); }
	@Override public int getY() { return getY(false); }
	
	public boolean isCenter() { return center; }
	public Point getCenterPoint() { return new Point(getX(true), getY(true)); }
	
	public Point[] getPoints(int numPoints) {
		final Point[] points = new Point[numPoints];
		int x = getX(true); int y = getY(true);
		for (int i = 0; i < points.length; ++i) {
			double angle = Math.toRadians(((double) i / points.length) * 360d);
			points[i] = new Point((int) (Math.cos(angle) * (width / 2)) + x, (int) (Math.sin(angle) * (height / 2)) + y);
		} return points;
	}
	
	@Override public void draw(Graphics g) { g.drawOval(x, y, width, height); }
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Oval))
			return false;
		if (!super.equals(other))
			return false;
		Oval castOther = (Oval) other;
		return Objects.equals(center, castOther.center);
	}
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), center);
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("center", center).toString();
	}

	public static Point[] checkIntersects(Oval c1, Oval c2, int numPoints, double minDistance) {
		List<Point> points = new ArrayList<>();
		Point[] p1s = c1.getPoints(numPoints);
		Point[] p2s = c2.getPoints(numPoints);
		for(Point p1 : p1s) { for(Point p2 : p2s)
			if(p1.equals(p2) && !points.contains(p1)) points.add(p2);
		}
		
		for(Point p1 : points) {
			L1: for(Point p2 : points) { if(!p1.equals(p2) && new Line(p1, p2).getDistance() <= minDistance) {
				points.remove(p1);
				break L1;
			} }
		} return points.toArray(new Point[points.size()]);
	}
}