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
	
	public static Color shadeColor(Color color, int percent) {
		int r = Math.max(0, Math.min(255, color.getRed()   * (100 + percent) / 100));
		int g = Math.max(0, Math.min(255, color.getGreen() * (100 + percent) / 100));
		int b = Math.max(0, Math.min(255, color.getBlue()  * (100 + percent) / 100));
		return new Color(r, g, b, color.getAlpha());
	}
	public static Color darkenColor(Color color, int percent) {
		return shadeColor(color, percent * -1);
	}
	public static Color lightenColor(Color color, int percent) {
		return shadeColor(color, percent);
	}
}
