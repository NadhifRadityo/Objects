package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

public class Circle extends Ellipse {
	public Circle(int x, int y, int d, boolean center) { super(x, y, d, d, center); }
	public Circle(Point p, int d, boolean center) { this(p.getX(), p.getY(), d, center); }
	public Circle(int x, int y, int d) { this(x, y, d, false); }
	public Circle(Point p, int d) { this(p.getX(), p.getY(), d); }
	
	public int getD() { return (width + height) / 2; }
	public int getR() { return getD() / 2; }
	public void setD(int d) { this.width = d; this.height = d; }
	public void setR(int r) { setD(r * 2); }
	@Override public void setWidth(int width) { throw new UnsupportedOperationException("Use setD(int) / setR(int) instead!"); }
	@Override public void setHeight(int height) { throw new UnsupportedOperationException("Use setD(int) / setR(int) instead!"); }
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Circle))
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
