package io.github.NadhifRadityo.Objects.$Utilizations;

import java.awt.*;

public class ColorUtils {
	private ColorUtils() {
		
	}

	public static Color getColor(int color, boolean hasAlpha) { return new Color(color, hasAlpha); }
	public static Color getColor(int color) { return new Color(color, false); }

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
	} public static float getBrightness(Color color) { return getBrightness(color, false); }
	
	public static boolean isLight(Color color, boolean withHSB) { return getBrightness(color, withHSB) >= (withHSB ? 0.5 : 130); }
	public static boolean isLight(Color color) { return isLight(color, false); }
	
	public static boolean isDark(Color color, boolean withHSB) { return !isLight(color, withHSB); }
	public static boolean isDark(Color color) { return isDark(color, false); }
	
	public static Color shadeColor(Color color, float percent) {
		percent = Math.max(-1, Math.min(percent, 1)) + 1;
		int r = (int) Math.max(0, Math.min(255, color.getRed()   * percent));
		int g = (int) Math.max(0, Math.min(255, color.getGreen() * percent));
		int b = (int) Math.max(0, Math.min(255, color.getBlue()  * percent));
		return new Color(r, g, b, color.getAlpha());
	}
	public static Color darkenColor(Color color, float percent) { return shadeColor(color, percent * -1); }
	public static Color lightenColor(Color color, float percent) { return shadeColor(color, percent); }
	
	public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        if(fractions == null) throw new IllegalArgumentException("Fractions can't be null");
        if(colors == null) throw new IllegalArgumentException("Colours can't be null");
        if(fractions.length != colors.length) throw new IllegalArgumentException("Fractions and colours must have equal number of elements");

		int startPoint = 0;
		while(startPoint < fractions.length && fractions[startPoint] <= progress) startPoint++;
		if(startPoint >= fractions.length) startPoint = fractions.length - 1;
		float range0 = fractions[startPoint - 1]; float range1 = fractions[startPoint];
        return blendColor(colors[startPoint - 1], colors[startPoint], 1f - ((progress - range0) / (range1 - range0)));
    }
	public static Color blendColor(Color color1, Color color2, float ratio) {
		float nextRatio = 1.0f - ratio;
		float r = Math.max(0, Math.min(255, color1.getRed()   * ratio + color2.getRed()   * nextRatio));
		float g = Math.max(0, Math.min(255, color1.getGreen() * ratio + color2.getGreen() * nextRatio));
		float b = Math.max(0, Math.min(255, color1.getBlue()  * ratio + color2.getBlue()  * nextRatio));
		return new Color(r, g, b);
	}
}
