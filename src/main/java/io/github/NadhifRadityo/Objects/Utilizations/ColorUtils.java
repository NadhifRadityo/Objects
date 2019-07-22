package io.github.NadhifRadityo.Objects.Utilizations;

import java.awt.*;

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
	} public static float getBrightness(Color color) { return getBrightness(color, false); }
	
	public static boolean isLight(Color color, boolean withHSB) { return getBrightness(color, withHSB) >= (withHSB ? 0.5 : 130); }
	public static boolean isLight(Color color) { return isLight(color, false); }
	
	public static boolean isDark(Color color, boolean withHSB) { return !isLight(color, withHSB); }
	public static boolean isDark(Color color) { return isDark(color, false); }
	
	public static Color shadeColor(Color color, int percent) {
		int r = Math.max(0, Math.min(255, color.getRed()   * (100 + percent) / 100));
		int g = Math.max(0, Math.min(255, color.getGreen() * (100 + percent) / 100));
		int b = Math.max(0, Math.min(255, color.getBlue()  * (100 + percent) / 100));
		return new Color(r, g, b, color.getAlpha());
	}
	public static Color darkenColor(Color color, int percent) { return shadeColor(color, percent * -1); }
	public static Color lightenColor(Color color, int percent) { return shadeColor(color, percent); }
	
	public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        if(fractions == null) throw new IllegalArgumentException("Fractions can't be null");
        if(colors == null) throw new IllegalArgumentException("Colours can't be null");
        if(fractions.length != colors.length) throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        
        int[] indicies = getFractionIndicies(fractions, progress);
        float[] range = new float[] { fractions[indicies[0]], fractions[indicies[1]] };
        Color[] colorRange = new Color[] { colors[indicies[0]], colors[indicies[1]] };
        return blendColor(colorRange[0], colorRange[1], 1f - ((progress - range[0]) / (range[1] - range[0])));
    }
    protected static int[] getFractionIndicies(float[] fractions, float progress) {
        int[] range = new int[2];
        int startPoint = 0;
        while (startPoint < fractions.length && fractions[startPoint] <= progress)
            startPoint++;
        if (startPoint >= fractions.length)
            startPoint = fractions.length - 1;
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }

    public static Color blendColor(Color color1, Color color2, double ratio) {
        float r = (float) ratio;
        float ir = (float) 1.0 - r;
        float rgb1[] = new float[3];
        float rgb2[] = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        
        float red = Math.max(0, Math.min(255, rgb1[0] * r + rgb2[0] * ir));
        float green = Math.max(0, Math.min(255, rgb1[1] * r + rgb2[1] * ir));
        float blue = Math.max(0, Math.min(255, rgb1[2] * r + rgb2[2] * ir));
        return new Color(red, green, blue);
    }
}
