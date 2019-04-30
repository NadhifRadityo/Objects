package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import java.awt.Graphics;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

import io.github.NadhifRadityo.Objects.Canvas.Sprite;

public class Rectangle extends Sprite {
	protected int width, height;
	
	public Rectangle(int x, int y, int width, int height) {
		super(x, y);
		this.width = width;
		this.height = height;
	} public Rectangle(Point p, int width, int height) { this(p.getX(), p.getY(), width, height); }

	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	@Override public void draw(Graphics g) { g.drawRect(x, y, width, height); }

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Rectangle))
			return false;
		if (!super.equals(other))
			return false;
		Rectangle castOther = (Rectangle) other;
		return Objects.equals(width, castOther.width) && Objects.equals(height, castOther.height);
	}
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), width, height);
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("width", width).append("height", height)
				.toString();
	}
}
