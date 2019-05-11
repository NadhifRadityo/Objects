package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Lens extends Rectangle {
	protected Area calculatedArea = null;
	protected int calculatedHeight = -1;
	
	public Lens(int x, int y, int width, int height) {
		super(x, y, width, height);
	} public Lens(Point p, int width, int height) { this(p.getX(), p.getY(), width, height); }
	
	protected void recalculate() { this.calculatedArea = null; this.calculatedHeight = -1; }
	@Override public void setX(int x) { if(x == this.x) return; this.x = x; recalculate(); }
	@Override public void setY(int y) { if(y == this.y) return; this.y = y; recalculate(); }
	@Override public void setWidth(int width) { this.width = width; recalculate(); }
	@Override public void setHeight(int height) { this.height = height; recalculate(); }
	
	@Override
	public int getHeight() {
		if(calculatedHeight == -1) {
			Circle c1 = new Circle(0, 0, height);
			Circle c2 = new Circle(height - width, 0, height);
			Point[] intersect = Oval.checkIntersects(c1, c2, 1000, width / 2);
			calculatedHeight = (int) new Line(intersect[0], intersect[1]).getDistance();
		} return calculatedHeight;
	}
	
	@Override public void draw(Graphics g) {
		if(!(g instanceof Graphics2D)) return;
		((Graphics2D) g).draw(getArea());
	}
	@Override public Area getArea() {
		if(calculatedArea == null) {
			Area area = new Area(new Ellipse2D.Double(0, 0, height, height));
			area.intersect(new Area(new Ellipse2D.Double(height - width, 0, height, height)));
			
			AffineTransform transform = new AffineTransform();
			transform.translate(-height + width + x, -(height - getHeight()) / 2 + y);
			area.transform(transform); calculatedArea = area;
		} return calculatedArea;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Lens))
			return false;
		return new EqualsBuilder().appendSuper(super.equals(other)).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(calculatedArea).append(calculatedHeight)
				.toHashCode();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("calculatedArea", calculatedArea)
				.append("calculatedHeight", calculatedHeight).toString();
	}
}
