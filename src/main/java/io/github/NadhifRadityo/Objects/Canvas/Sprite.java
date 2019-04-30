package io.github.NadhifRadityo.Objects.Canvas;

import java.awt.Graphics;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class Sprite {
	protected int x, y;

	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public abstract void draw(Graphics g);

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
