package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class Ellipse extends Rectangle {
	protected boolean center;
	
	public Ellipse(int x, int y, int width, int height, boolean center) {
		super(x, y, width, height);
		this.center = center;
		if(center) {
			this.x -= width / 2;
			this.y -= height / 2;
		}
	}
	public Ellipse(Point p, int width, int height, boolean center) { this(p.getX(), p.getY(), width, height, center); }
	public Ellipse(int x, int y, int width, int height) { this(x, y, width, height, false); }
	public Ellipse(Point p, int width, int height) { this(p.getX(), p.getY(), width, height); }

	public boolean isCenter() { return center; }
	public int getX(boolean center) { return center ? x + width / 2 : (this.center ? x - width / 2 : x); }
	public int getY(boolean center) { return center ? y + height / 2 : (this.center ? y - height / 2 : y); }
	@Override public int getX() { return getX(false); }
	@Override public int getY() { return getY(false); }
	public Point getCenterPoint() { return new Point(getX(true), getY(true)); }
	
	public void setCenter(boolean center) {
		if(this.center == center) return;
		this.center = center;
		if(center) { setX(x, true); setY(y, true); }
		else { setX(x + (width / 2), false); setY(y + (height / 2)); }
	}
	public void setX(int x, boolean center) { this.x = center ? x - (width / 2) : x; }
	public void setY(int y, boolean center) { this.y = center ? y - (height / 2) : y; }
	@Override public void setX(int x) { setX(x, false); }
	@Override public void setY(int y) { setY(y, false); }
	
	public Point[] getPoints(int numPoints) {
		final Point[] points = new Point[numPoints];
		int x = getX(true); int y = getY(true);
		for (int i = 0; i < points.length; ++i) {
			double angle = Math.toRadians(((double) i / points.length) * 360d);
			points[i] = new Point((int) (Math.cos(angle) * (width / 2)) + x, (int) (Math.sin(angle) * (height / 2)) + y);
		} return points;
	}
	
	@Override public void draw(Graphics g) { g.drawOval(x, y, width, height); }
	@Override public Area getArea() { return new Area(new Ellipse2D.Double(getX(true), getY(true), width, height)); }

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Ellipse))
			return false;
		Ellipse castOther = (Ellipse) other;
		return new EqualsBuilder().appendSuper(super.equals(other)).append(center, castOther.center).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(center).toHashCode();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("center", center).toString();
	}
	
	public static Point[] checkIntersects(Ellipse c1, Ellipse c2, int numPoints, double minDistance) {
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