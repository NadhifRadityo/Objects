package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Point extends Circle {
	public Point(int x, int y) {
		super(x, y, 2, true);
	}
	
	@Override public Point getCenterPoint() { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public Point[] getPoints(int numPoints) { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public boolean isCenter() { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public int getD() { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public int getR() { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public int getWidth() { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public int getHeight() { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public int getX(boolean center) { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public int getY(boolean center) { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public int getX() { return super.getX(true); }
	@Override public int getY() { return super.getY(true); }
	
	@Override public void setCenter(boolean center) { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public void setD(int d) { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public void setR(int r) { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public void setWidth(int width) { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public void setHeight(int height) { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public void setX(int x, boolean center) { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public void setY(int y, boolean center) { throw new UnsupportedOperationException("Not implemented!"); }
	@Override public void setX(int x) { this.x = x; }
	@Override public void setY(int y) { this.y = y; }
	
	@Override public void draw(Graphics g) {
		Color defColor = g.getColor();
		g.setColor(Color.RED);
		super.draw(g);
		g.setColor(defColor);
	}
	@Override public Area getArea() { return new Area(new Rectangle2D.Double(getX(), getY(), 1, 1)); }

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Point))
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
}
