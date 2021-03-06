package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import io.github.NadhifRadityo.Objects.Canvas.Sprite;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Line2D;

public class Line extends Sprite {
	protected int x2, y2;
	
	public Line(int x, int y, int x2, int y2) {
		super(x, y);
		this.x2 = x2;
		this.y2 = y2;
	} public Line(Point p1, Point p2) { this(p1.getX(), p1.getY(), p2.getX(), p2.getY()); }

	public int getX2() { return x2; }
	public int getY2() { return y2; }
	public void setX2(int x2) { this.x2 = x2; }
	public void setY2(int y2) { this.y2 = y2; }
	
	public double getDistance() { return Math.hypot(Math.abs(y2 - y), Math.abs(x2 - x)); }
	public Line extend(int length) { return extend(this, length); }
	
	@Override public void draw(Graphics g) { g.drawLine(x, y, x2, y2); }
	@Override public Area getArea() { return new Area(new Line2D.Double(x, y, x2, y2)); }

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Line))
			return false;
		Line castOther = (Line) other;
		return new EqualsBuilder().appendSuper(super.equals(other)).append(x2, castOther.x2).append(y2, castOther.y2)
				.isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(x2).append(y2).toHashCode();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("x2", x2).append("y2", y2).toString();
	}
	
	public static Line extend(Line line, int length) {
		return extend(new Point(line.getX(), line.getY()), new Point(line.getX2(), line.getY2()), length);
	}
	public static Line extend(Point start, Point end, int length) {
		int deltaX = end.getX() - start.getX();
		int deltaY = end.getY() - start.getY();
		double atan = Math.atan2(deltaY, deltaX);
		int x = (int) (start.getX() + length * Math.cos(atan));
		int y = (int) (start.getY() + length * Math.sin(atan));
		return new Line(start.getX(), start.getY(), x, y);
//		int x = startPoint.getX(), y = startPoint.getY();
//		double alpha = Math.atan2(y - lockPoint.getX(), x - lockPoint.getY());
//		x = (int) (lockPoint.getX() + length * Math.cos(alpha));
//		y = (int) (lockPoint.getY() + length * Math.sin(alpha));
//		return new Line(lockPoint.getX(), lockPoint.getY(), x, y);
	}
}
