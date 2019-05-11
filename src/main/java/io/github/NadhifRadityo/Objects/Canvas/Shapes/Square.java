package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Square extends Rectangle {
	public Square(int x, int y, int s) { super(x, y, s, s); }
	public Square(Point p, int s) { this(p.getX(), p.getY(), s); }
	
	public int getS() { return (width + height) / 2; }
	public void setS(int s) { this.width = s; this.height = s; }
	@Override public void setWidth(int width) { throw new UnsupportedOperationException("Use setS(int) instead!"); }
	@Override public void setHeight(int height) { throw new UnsupportedOperationException("Use setS(int) instead!"); }
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Square))
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
