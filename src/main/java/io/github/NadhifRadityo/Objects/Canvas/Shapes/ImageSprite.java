package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ImageSprite extends Rectangle {
	protected BufferedImage image;
	
	public ImageSprite(int x, int y, BufferedImage image) { super(x, y, image.getWidth(), image.getHeight()); this.image = image; }
	public ImageSprite(Point p, BufferedImage image) { this(p.getX(), p.getY(), image); }

	public BufferedImage getImage() { return image; }
	public void setImage(BufferedImage image) { this.image = image; this.width = image.getWidth(); this.height = image.getHeight(); }
	
	@Override public void draw(Graphics g) { g.drawImage(image, 0, 0, null); }
	
	@Override
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(other instanceof ImageSprite))
			return false;
		ImageSprite castOther = (ImageSprite) other;
		return new EqualsBuilder().appendSuper(super.equals(other)).append(image, castOther.image).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).append(image).toHashCode();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString()).append("image", image).toString();
	}
}
