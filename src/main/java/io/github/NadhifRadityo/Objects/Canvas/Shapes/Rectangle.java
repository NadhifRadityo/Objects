package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import io.github.NadhifRadityo.Objects.Canvas.Sprite;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Rectangle extends Sprite {
	protected int width, height;
	
	public Rectangle(int x, int y, int width, int height) {
		super(x, y);
		this.width = width;
		this.height = height;
	} public Rectangle(Point p, int width, int height) { this(p.getX(), p.getY(), width, height); }

	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }
	
	@Override public void draw(Graphics g) { g.drawRect(x, y, width, height); }
	@Override public Area getArea() { return new Area(new Rectangle2D.Double(x, y, width, height)); }

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Rectangle))
			return false;
		Rectangle castOther = (Rectangle) other;
		return new EqualsBuilder().appendSuper(super.equals(other)).append(width, castOther.width)
				.append(height, castOther.height).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(width).append(height).toHashCode();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("width", width).append("height", height)
				.toString();
	}
}
