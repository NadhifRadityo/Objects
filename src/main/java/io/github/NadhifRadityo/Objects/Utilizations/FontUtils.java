package io.github.NadhifRadityo.Objects.Utilizations;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class FontUtils {
	private FontUtils() {
		
	}
	
	public static Rectangle2D getStringBounds(String text, Font font) {
		AffineTransform affineTitle = new AffineTransform();
		FontRenderContext titleRender = new FontRenderContext(affineTitle, true, false);
		return font.getStringBounds(text, titleRender);
	}
	public static double getStringWidth(String text, Font font) { return getStringBounds(text, font).getWidth(); }
	public static double getStringHeight(String text, Font font) { return getStringBounds(text, font).getHeight(); }

	public static Font createFont(InputStream stream, int type) throws IOException, FontFormatException { return Font.createFont(type, stream); }
	public static Font createFont(InputStream stream) throws IOException, FontFormatException { return createFont(stream, Font.TRUETYPE_FONT); }
	public static Font createFont(byte[] bytes, int type) throws IOException, FontFormatException { return createFont(new ByteArrayInputStream(bytes), type); }
	public static Font createFont(byte[] bytes) throws IOException, FontFormatException { return createFont(bytes, Font.TRUETYPE_FONT); }
	public static Font createFont(File file, int type) throws IOException, FontFormatException { return Font.createFont(type, file); }
	public static Font createFont(File file) throws IOException, FontFormatException { return createFont(file, FileUtils.getFileExtension(file).equals("ttf") ? Font.TRUETYPE_FONT : Font.TYPE1_FONT); }

	@SuppressWarnings({"unchecked", "RawUseOfParameterized"})
	public static Font deriveFont(Font font, int size, float weight, float posture, boolean kerning) {
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.SIZE, size);
		attributes.put(TextAttribute.WEIGHT, weight);
		attributes.put(TextAttribute.POSTURE, posture);
		attributes.put(TextAttribute.KERNING, kerning ? 1 : 0);
		return font.deriveFont(attributes);
	} public static Font deriveFont(Font font, int size, float weight, float posture) { return deriveFont(font, size, weight, posture, false); }

	public static GlyphVector getVector(Font font, FontRenderContext renderContext, char[] chars, int start, int end, boolean ltr) { return font.layoutGlyphVector(renderContext, chars, start, end, ltr ? Font.LAYOUT_LEFT_TO_RIGHT : Font.LAYOUT_RIGHT_TO_LEFT); }
	public static GlyphVector getVector(Font font, FontRenderContext renderContext, char[] chars, boolean ltr) { return getVector(font, renderContext, chars, 0, chars.length, ltr); }
	public static GlyphVector getVector(Font font, FontRenderContext renderContext, char[] chars) { return getVector(font, renderContext, chars, true); }
	public static GlyphVector getVector(Font font, FontRenderContext renderContext, String string, int start, int end, boolean ltr) { return getVector(font, renderContext, string.toCharArray(), start, end, ltr); }
	public static GlyphVector getVector(Font font, FontRenderContext renderContext, String string, boolean ltr) { return getVector(font, renderContext, string, 0, string.length(), ltr); }
	public static GlyphVector getVector(Font font, FontRenderContext renderContext, String string) { return getVector(font, renderContext, string, true); }
	public static GlyphVector getVector(Font font, Graphics2D graphics, char[] chars, int start, int end, boolean ltr) { return getVector(font, graphics.getFontRenderContext(), chars, start, end, ltr); }
	public static GlyphVector getVector(Font font, Graphics2D graphics, char[] chars, boolean ltr) { return getVector(font, graphics, chars, 0, chars.length, ltr); }
	public static GlyphVector getVector(Font font, Graphics2D graphics, char[] chars) { return getVector(font, graphics, chars, true); }
	public static GlyphVector getVector(Font font, Graphics2D graphics, String string, int start, int end, boolean ltr) { return getVector(font, graphics, string.toCharArray(), start, end, ltr); }
	public static GlyphVector getVector(Font font, Graphics2D graphics, String string, boolean ltr) { return getVector(font, graphics, string, 0, string.length(), ltr); }
	public static GlyphVector getVector(Font font, Graphics2D graphics, String string) { return getVector(font, graphics, string, true); }
}
