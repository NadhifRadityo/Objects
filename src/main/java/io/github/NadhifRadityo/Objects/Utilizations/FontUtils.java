package io.github.NadhifRadityo.Objects.Utilizations;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public final class FontUtils {
	private FontUtils() {
		
	}
	
	public static Rectangle2D getStringBounds(String text, Font font) {
		AffineTransform afflineTitle = new AffineTransform();
		FontRenderContext titleRender = new FontRenderContext(afflineTitle, true, false);
		return font.getStringBounds(text, titleRender);
	}
	public static double getStringWidth(String text, Font font) {
		return getStringBounds(text, font).getWidth();
	}
	public static double getStringHeight(String text, Font font) {
		return getStringBounds(text, font).getHeight();
	}
}
