package io.github.NadhifRadityo.Objects.Utilizations;

import java.awt.Color;

public class ColorUtils {
	private ColorUtils() {
		
	}
	
	/*
	 * if you used HSB value will range between 0 - 1
	 * otherwise it will range between 0 - 255 and 130 is consider light
	 */
	public static float getBrightness(Color color, boolean withHSB) {
		return withHSB ? Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null)[2] :
			  (float) Math.sqrt(
				      color.getRed() * color.getRed() * .241 +
				      color.getGreen() * color.getGreen() * .691 +
				      color.getBlue() * color.getBlue() * .068);
	}
	public static float getBrightness(Color color) {
		return getBrightness(color, false);
	}
	
	public static boolean isLight(Color color, boolean withHSB) {
		return getBrightness(color, withHSB) >= (withHSB ? 0.5 : 130);
	}
	public static boolean isLight(Color color) {
		return isLight(color, false);
	}
	
	public static boolean isDark(Color color, boolean withHSB) {
		return !isLight(color, withHSB);
	}
	public static boolean isDark(Color color) {
		return isDark(color, false);
	}
}
