package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Ellipse extends Rectangle {

	public Ellipse(int x, int y, int width, int height, boolean center) {
		super(x, y, width, height);
		if(center) {
			this.x -= width / 2;
			this.y -= height / 2;
		}
	}
	public Ellipse(Point p, int width, int height, boolean center) { this(p.getX(), p.getY(), width, height, center); }
	public Ellipse(int x, int y, int width, int height) { this(x, y, width, height, false); }
	public Ellipse(Point p, int width, int height) { this(p.getX(), p.getY(), width, height); }

	public int getX(boolean center) { return center ? x + width / 2 : x; }
	public int getY(boolean center) { return center ? y + height / 2 : y; }
	@Override public int getX() { return getX(false); }
	@Override public int getY() { return getY(false); }
	public Point getCenterPoint() { return new Point(getX(true), getY(true)); }

	public void setX(int x, boolean center) { this.x = center ? x - (width / 2) : x; }
	public void setY(int y, boolean center) { this.y = center ? y - (height / 2) : y; }
	@Override public void setX(int x) { setX(x, false); }
	@Override public void setY(int y) { setY(y, false); }
	public void setPosition(int x, int y, boolean center) { setX(x, center); setY(y, center); }
	@Override public void setPosition(int x, int y) { setPosition(x, y, false); }

	public Point[] getPoints(int numPoints) {
		final Point[] points = new Point[numPoints];
		int x = getX(true); int y = getY(true);
		for (int i = 0; i < points.length; ++i) {
			double angle = Math.toRadians(((double) i / points.length) * 360d);
			points[i] = new Point((int) (Math.cos(angle) * (width / 2)) + x, (int) (Math.sin(angle) * (height / 2)) + y);
		} return points;
	}
	
	@Override public void draw(Graphics g) { g.drawOval(x, y, width, height); }
	@Override public Area getArea() { return new Area(new Ellipse2D.Double(getX(), getY(), width, height)); }

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Ellipse))
			return false;
		return new EqualsBuilder().appendSuper(super.equals(other)).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).toHashCode();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).toString();
	}
	
	public static Point[] checkIntersects(Ellipse c1, Ellipse c2, int numPoints, double minDistance) {
		ArrayList<Point> points = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
			Point[] p1s = c1.getPoints(numPoints);
			Point[] p2s = c2.getPoints(numPoints);
			for(Point p1 : p1s) for(Point p2 : p2s)
				if(p1.equals(p2) && !points.contains(p1)) points.add(p2);
			for(Point p1 : points) for(Point p2 : points) if(!p1.equals(p2) && new Line(p1, p2).getDistance() <= minDistance) {
				points.remove(p1); break; }
			return points.toArray(new Point[0]);
		} finally { Pool.returnObject(ArrayList.class, points); }
	}
}