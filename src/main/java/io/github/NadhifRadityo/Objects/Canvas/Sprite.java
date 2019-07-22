package io.github.NadhifRadityo.Objects.Canvas;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Objects;

public abstract class Sprite {
	protected int x, y;

	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setPosition(int x, int y) { setX(x); setY(y); }

	public abstract void draw(Graphics g);
	public abstract Area getArea();

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Sprite))
			return false;
		Sprite castOther = (Sprite) other;
		return Objects.equals(x, castOther.x) && Objects.equals(y, castOther.y);
	}
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("x", x).append("y", y).toString();
	}
}
