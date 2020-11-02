package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVec4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;
import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ClassUtils;
import io.github.NadhifRadityo.Objects.Utilizations.Direction.Direction2D;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.FontUtils;

import java.awt.*;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class BitmapFontTest {
	public static final int MAX_GLYPH_SIZE = 512;
	private static BufferedImage scratchImage = new BufferedImage(MAX_GLYPH_SIZE, MAX_GLYPH_SIZE, BufferedImage.TYPE_INT_ARGB);
	private static Graphics2D scratchGraphics = (Graphics2D) scratchImage.getGraphics();
	static {
		scratchGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		scratchGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}

	private static BufferedImage result = new BufferedImage(4096, 4096, BufferedImage.TYPE_INT_ARGB);

	public static void main(String[] args) {
		Font font = new Font("Fira Code", Font.PLAIN, 64);
		long start = System.currentTimeMillis();
		GlyphPage page = new GlyphPage(font, new Size(result.getWidth(), result.getHeight()), new SVec4((short) 5), true, true);
//		page.addGlyph("ABCDEFGHIJabcdefghij");
		page.addGlyph(new int[] {0, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56,
				57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86,
				87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113,
				114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170,
				171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194,
				195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218,
				219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242,
				243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266,
				267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290,
				291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314,
				315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338,
				339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362,
				363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 884, 885, 890,
				891, 892, 893, 894, 900, 901, 902, 903, 904, 905, 906, 908, 910, 911, 912, 913, 914, 915, 916, 917, 918, 919, 920, 921,
				922, 923, 924, 925, 926, 927, 928, 929, 931, 932, 933, 934, 935, 936, 937, 938, 939, 940, 941, 942, 943, 944, 945, 946,
				947, 948, 949, 950, 951, 952, 953, 954, 955, 956, 957, 958, 959, 960, 961, 962, 963, 964, 965, 966, 967, 968, 969, 970,
				971, 972, 973, 974, 976, 977, 978, 979, 980, 981, 982, 983, 984, 985, 986, 987, 988, 989, 990, 991, 992, 993, 994, 995,
				996, 997, 998, 999, 1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012, 1013, 1014, 1015, 1016,
				1017, 1018, 1019, 1020, 1021, 1022, 1023, 1024, 1025, 1026, 1027, 1028, 1029, 1030, 1031, 1032, 1033, 1034, 1035, 1036,
				1037, 1038, 1039, 1040, 1041, 1042, 1043, 1044, 1045, 1046, 1047, 1048, 1049, 1050, 1051, 1052, 1053, 1054, 1055, 1056,
				1057, 1058, 1059, 1060, 1061, 1062, 1063, 1064, 1065, 1066, 1067, 1068, 1069, 1070, 1071, 1072, 1073, 1074, 1075, 1076,
				1077, 1078, 1079, 1080, 1081, 1082, 1083, 1084, 1085, 1086, 1087, 1088, 1089, 1090, 1091, 1092, 1093, 1094, 1095, 1096,
				1097, 1098, 1099, 1100, 1101, 1102, 1103, 1104, 1105, 1106, 1107, 1108, 1109, 1110, 1111, 1112, 1113, 1114, 1115, 1116,
				1117, 1118, 1119, 1120, 1121, 1122, 1123, 1124, 1125, 1126, 1127, 1128, 1129, 1130, 1131, 1132, 1133, 1134, 1135, 1136,
				1137, 1138, 1139, 1140, 1141, 1142, 1143, 1144, 1145, 1146, 1147, 1148, 1149, 1150, 1151, 1152, 1153, 1154, 1155, 1156,
				1157, 1158, 1159, 1160, 1161, 1162, 1163, 1164, 1165, 1166, 1167, 1168, 1169, 1170, 1171, 1172, 1173, 1174, 1175, 1176,
				1177, 1178, 1179, 1180, 1181, 1182, 1183, 1184, 1185, 1186, 1187, 1188, 1189, 1190, 1191, 1192, 1193, 1194, 1195, 1196,
				1197, 1198, 1199, 1200, 1201, 1202, 1203, 1204, 1205, 1206, 1207, 1208, 1209, 1210, 1211, 1212, 1213, 1214, 1215, 1216,
				1217, 1218, 1219, 1220, 1221, 1222, 1223, 1224, 1225, 1226, 1227, 1228, 1229, 1230, 1231, 1232, 1233, 1234, 1235, 1236,
				1237, 1238, 1239, 1240, 1241, 1242, 1243, 1244, 1245, 1246, 1247, 1248, 1249, 1250, 1251, 1252, 1253, 1254, 1255, 1256,
				1257, 1258, 1259, 1260, 1261, 1262, 1263, 1264, 1265, 1266, 1267, 1268, 1269, 1270, 1271, 1272, 1273, 1274, 1275, 1276,
				1277, 1278, 1279, 1280, 1281, 1282, 1283, 1284, 1285, 1286, 1287, 1288, 1289, 1290, 1291, 1292, 1293, 1294, 1295, 1296,
				1297, 1298, 1299, 1300, 1301, 1302, 1303, 1304, 1305, 1306, 1307, 1308, 1309, 1310, 1311, 1312, 1313, 1314, 1315, 1316,
				1317, 1318, 1319, 8192, 8193, 8194, 8195, 8196, 8197, 8198, 8199, 8200, 8201, 8202, 8203, 8204, 8205, 8206, 8207, 8210,
				8211, 8212, 8213, 8214, 8215, 8216, 8217, 8218, 8219, 8220, 8221, 8222, 8223, 8224, 8225, 8226, 8230, 8234, 8235, 8236,
				8237, 8238, 8239, 8240, 8242, 8243, 8244, 8249, 8250, 8252, 8254, 8260, 8286, 8298, 8299, 8300, 8301, 8302, 8303, 8352,
				8353, 8354, 8355, 8356, 8357, 8358, 8359, 8360, 8361, 8363, 8364, 8365, 8366, 8367, 8368, 8369, 8370, 8371, 8372, 8373,
				8377, 8378, 11360, 11361, 11362, 11363, 11364, 11365, 11366, 11367, 11368, 11369, 11370, 11371, 11372, 11373, 11377,
				11378, 11379, 11380, 11381, 11382, 11383});
		page.render(result);
//		GlyphPage page = new GlyphPage(font, "ABCDEFGHIJabcdefghij".toCharArray(), new Size(2048, 2048), new SVec4(new short[] { 1, 1, 1, 1 }));
		System.out.println("DONE " + (System.currentTimeMillis() - start) + "ms");
		page.getLost();
	}
//
//	public static class GlyphPage {
//		protected final Font font;
//		protected final char[] characters;
//		protected final GlyphVector vector;
//		protected final PriorityList<Effect> effects;
//
//		protected final ArrayList<Row> rows;
//		protected final Size size;
//		protected final SVec4 padding;
//
//		//		protected final FrameBufferObject<? extends Number> frameBuffer;
////		protected final Texture<? extends Number> texture;
//
//		public GlyphPage(/*GLContext<? extends Number> gl, */Font font, char[] characters, Size size, SVec4 padding) {
////			super(gl);
//
//			this.font = font;
//			this.characters = characters;
//			this.vector = FontUtils.getVector(font, scratchGraphics, characters);
//			this.effects = Pool.tryBorrow(Pool.getPool(PriorityList.class));
//
//			effects.add((image, graphics, shape, character) -> {
//				graphics.setColor(Color.BLACK);
//				graphics.fill(shape);
//			});
//
//			this.rows = Pool.tryBorrow(Pool.getPool(ArrayList.class));
//			this.size = size;
//			this.padding = padding;
//			arrange();
//
////			this.frameBuffer = getGL().constructFrameBufferObject(gl.GL_FRAMEBUFFER());
////			this.texture = getGL().constructTexture(gl.GL_TEXTURE_2D());
//		}
//
//		/*@Override*/ protected void arrange() {
////			frameBuffer.create(); texture.create();
////			frameBuffer.bind(); frameBuffer.allocateSlot();
////			forceAttach(frameBuffer, texture);
//
////			texture.bind();
////			texture.setTextureFilter(getGL().GL_NEAREST());
////			texture.asSize(size);
//
////			getGL().glViewport(0, 0, size.getWidth(), size.getHeight());
//
//			ByteBuffer targetByte = BufferUtils.createByteBuffer(scratchImage.getWidth() * scratchImage.getHeight() * 4);
//			IntBuffer targetInt = targetByte.asIntBuffer();
//			try {
//				WritableRaster raster = scratchImage.getRaster();
//				int[] temp = new int[scratchImage.getWidth()];
//				for(int i = 0; i < vector.getNumGlyphs(); i++) {
//					Rectangle bounds = getGlyphBounds(i);
//					Row bestRow = null;
//					for(Row row : rows) {
//						if(row.x + bounds.width + padding.z() > size.getWidth()) continue;
//						if(row.y + bounds.height + padding.w() > size.getHeight()) continue;
//						if(bounds.height > row.height) continue;
//						if(bestRow == null || row.height < bestRow.height) bestRow = row;
//					}
//					if(bestRow == null) {
//						Row lastRow = rows.size() > 0 ? rows.get(rows.size() - 1) : null;
//						bestRow = new Row(lastRow != null ? lastRow.y : 0, padding.x(), padding.y());
//						rows.add(bestRow);
//					}
//
//					bestRow.height = Math.max(bestRow.height, bounds.height);
//					GlyphMetrics metrics = vector.getGlyphMetrics(i);
//					scratchGraphics.setBackground(new Color(255, 255, 255, 0));
//					scratchGraphics.clearRect(0,0, scratchImage.getWidth(), scratchImage.getHeight());
//					drawGlyph(i, bounds, targetByte, targetInt, bestRow.x, bestRow.y, raster, temp);
//					bestRow.glyphs.put((int) characters[i], metrics);
//					bestRow.x += bounds.width;
//				}
//			} finally { BufferUtils.deallocate(targetByte); }
//		}
////		@Override protected void disarrange() {
//////			if(!frameBuffer.isDead()) frameBuffer.destroy();
////			if(!texture.isDead()) texture.destroy();
////			Pool.returnObject(PriorityList.class, effects);
////			Pool.returnObject(ArrayList.class, rows);
////		}
//
//		protected void drawGlyph(int index, Rectangle bounds, ByteBuffer targetByte, IntBuffer targetInt, int x, int y, WritableRaster raster, int[] tempArray) {
//			if(tempArray == null) tempArray = new int[scratchImage.getWidth()];
//			for(Effect effect : effects.get()) effect.draw(scratchImage, scratchGraphics, vector.getGlyphOutline(index, -bounds.x + padding.x(), -bounds.y + padding.y()), characters[index]);
//			for(int _y = 0; _y < bounds.height + padding.y(); _y++) {
//				raster.getDataElements(0, _y, bounds.width + padding.x(), 1, tempArray);
////				targetInt.put(tempArray, 0, bounds.width + padding.x());
//				result.getRaster().setDataElements(x, _y + y, bounds.width + padding.x(), 1, tempArray);
//			}
//			//texture.reload(targetByte, x, y, bounds.width, bounds.height, true);
//		}
//
//		private Rectangle getGlyphBounds(int index) {
//			if(Character.isWhitespace(characters[index])) return vector.getGlyphLogicalBounds(index).getBounds();
//			return vector.getGlyphPixelBounds(index, scratchGraphics.getFontRenderContext(), 0, 0);
//		}
//
//		//		@SuppressWarnings("unchecked")
////		private static <ID extends Number> void forceAttach(FrameBufferObject<? extends Number> frameBuffer, Texture<? extends Number> texture) {
////			((FrameBufferObject<ID>) frameBuffer).attachTexture((Texture<ID>) texture);
////		}
//		public interface Effect {
//			void draw(BufferedImage image, Graphics2D graphics, Shape shape, int character);
//		}
//		public static class Row {
//			protected final int y;
//			protected int x;
//			protected int height;
//			protected HashMap<Integer, GlyphMetrics> glyphs;
//
//			public Row(int y, int paddingX, int paddingY) {
//				this.y = y + paddingY;
//				this.x += paddingX;
//				this.height = Integer.MAX_VALUE;
//				this.glyphs = new HashMap<>(); // LEAK
//			}
//		}
//	}

	public static class GlyphPage {
		protected final Font font;
		protected final Size size;
		protected final SVec4 padding;
		protected final ArrayList<GlyphLineList> glyphLines;
		protected final boolean horizontal;
		protected final boolean advanceIncreasing;
		protected final boolean startBeginning;

		private final boolean meantHorizontal;
		protected final PriorityList<Effect> effects;

		public GlyphPage(Font font, Size size, SVec4 padding, boolean horizontal, boolean advanceIncreasing, boolean startBeginning) {
			this.font = font;
			this.size = size;
			this.padding = padding;
			this.glyphLines = new ArrayList<>();
			this.horizontal = horizontal;
			this.advanceIncreasing = advanceIncreasing;
			this.startBeginning = startBeginning;

			this.meantHorizontal = isMetricsHorizontal(FontUtils.getVector(font, scratchGraphics, "A").getGlyphMetrics(0));
			this.effects = Pool.tryBorrow(Pool.getPool(PriorityList.class));
			effects.add(((image, graphics, glyphChar) -> {
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			}), 1);
			effects.add((image, graphics, glyphChar) -> {
				Shape shape = glyphChar.getShape();
				Rectangle bounds = glyphChar.getBounds();
				graphics.setColor(Color.BLACK);
				graphics.fill(shape.getBounds2D());
				graphics.setColor(Color.WHITE);
				graphics.fill(shape);

//				int scale = 10;
//				double spread = 1.5;
//				ReferencedCallback.PVoidReferencedCallback drawGlyph = (args) -> {
//					Graphics2D _graphics = (Graphics2D) ((BufferedImage) args[0]).getGraphics();
//					_graphics.setTransform(AffineTransform.getScaleInstance(scale, scale));
//					// We don't really want anti-aliasing (we'll discard it anyway),
//					// but accurate positioning might improve the result slightly
//					_graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
//					_graphics.setColor(Color.WHITE); _graphics.fill(shape);
//				};
//
//				BufferedImage input = new BufferedImage(scale * bounds.width, scale * bounds.height, BufferedImage.TYPE_BYTE_BINARY);
//				drawGlyph.get(input);
//
//				DistanceFieldGenerator generator = new DistanceFieldGenerator();
//				generator.setColor(color);
//				generator.setDownscale(scale);
//				// We multiply spread by the scale, so that changing scale will only affect accuracy
//				// and not spread in the output image.
//				generator.setSpread(scale * spread);
//				BufferedImage distanceField = generator.generateDistanceField(input);
//
//				g.drawImage(distanceField, new AffineTransform(), null);
			});
		}
		public GlyphPage(Font font, Size size, SVec4 padding, boolean advanceIncreasing, boolean startBeginning) {
			this(font, size, padding, isMetricsHorizontal(FontUtils.getVector(font, scratchGraphics, "A").getGlyphMetrics(0)), advanceIncreasing, startBeginning);
		}

		public Font getFont() { return font; }
		public Size getSize() { return size; }
		public SVec4 getPadding() { return padding; }
		public ArrayList<GlyphLineList> getGlyphLines() { return glyphLines; }
		public boolean isHorizontal() { return horizontal; }
		public boolean isAdvanceIncreasing() { return advanceIncreasing; }
		public Direction2D getTextDirection() {
			if(horizontal) { if(advanceIncreasing) return Direction2D.RIGHT; else return Direction2D.LEFT; }
			if(advanceIncreasing) return Direction2D.BOTTOM; else return Direction2D.UP; // Who write character upwards?
		}

		public boolean addGlyph(GlyphChar glyphChar) {
			// Make sure feeds this functions with biggest characters first to get optimized result.
			Rectangle charBounds = glyphChar.getBounds();
			GlyphLineList selectedLine = null;
			for(GlyphLineList glyphLine : glyphLines) {
				Rectangle bounds = glyphLine.getBounds();
				if(horizontal) {
					if(advanceIncreasing) { if(bounds.x + charBounds.width > size.getWidth()) continue; }
					else if(bounds.x - charBounds.width < 0) continue;
					if(startBeginning) { if(bounds.y + charBounds.height > size.getHeight()) continue; }
					else if(bounds.y - charBounds.height < 0) continue;
					if(charBounds.height > bounds.height) continue;
					if(selectedLine == null || bounds.height < selectedLine.getBounds().height)
						selectedLine = glyphLine;
				} else {
					if(advanceIncreasing) { if(bounds.y + charBounds.height > size.getHeight()) continue; }
					else if(bounds.y - charBounds.height < 0) continue;
					if(startBeginning) { if(bounds.x + charBounds.width > size.getWidth()) continue; }
					else if(bounds.x - charBounds.width < 0) continue;
					if(charBounds.width > bounds.width) continue;
					if(selectedLine == null || bounds.width < selectedLine.getBounds().width)
						selectedLine = glyphLine;
				}
			}
			if(selectedLine == null) {
				GlyphLineList lastLine = glyphLines.size() > 0 ? glyphLines.get(glyphLines.size() - 1) : null;
				int pos = startBeginning ? 0 : horizontal ? size.getHeight() : size.getWidth();
				if(lastLine != null && horizontal) pos = lastLine.getBounds().y + lastLine.getBounds().height * (startBeginning ? 1 : -1);
				if(lastLine != null && !horizontal) pos = lastLine.getBounds().x + lastLine.getBounds().width * (startBeginning ? 1 : -1);
				if(pos < 0 || horizontal ? pos > size.getHeight() : pos > size.getWidth()) throw new Error("Page too small");
				selectedLine = new GlyphLineList(font, horizontal ? size.getWidth() : size.getHeight(), pos, padding, horizontal, advanceIncreasing, startBeginning, meantHorizontal);
				glyphLines.add(selectedLine);
			} return selectedLine.addGlyph(glyphChar);
		}
		public void addGlyph(String string) {
			ArrayList<GlyphChar> glyphChars = new ArrayList<>();
			for(int i = 0; i < string.length(); i++)
				glyphChars.add(new GlyphChar(string.codePointAt(i), font, horizontal, padding, meantHorizontal));
			glyphChars.sort((o1, o2) -> horizontal ? o2.getBounds().height - o1.getBounds().height : o2.getBounds().width - o1.getBounds().width);
			for(GlyphChar glyphChar : glyphChars) addGlyph(glyphChar);
		}
		public void addGlyph(int[] codePoints) {
			StringBuilder builder = new StringBuilder();
			for(int codePoint : codePoints)
				builder.append(Character.toChars(codePoint));
			addGlyph(builder.toString());
		}

		public void render(BufferedImage image) {
			for(GlyphLineList glyphLine : glyphLines) {
				glyphLine.render(image, effects);
			}
		}

		public void getLost() { ExceptionUtils.doSilentThrowsRunnable(false, () -> {
			ClassUtils.setFinal(this, GlyphPage.class.getDeclaredField("font"), null);
			ClassUtils.setFinal(this, GlyphPage.class.getDeclaredField("size"), null);
			ClassUtils.setFinal(this, GlyphPage.class.getDeclaredField("padding"), null);
			ClassUtils.setFinal(this, GlyphPage.class.getDeclaredField("effects"), null);
			for(GlyphLineList glyphLine : glyphLines) glyphLine.getLost();
		}); }

		private static boolean isMetricsHorizontal(GlyphMetrics metrics) { try {
			Field FIELD_horizontal = GlyphMetrics.class.getDeclaredField("horizontal");
			return (boolean) FIELD_horizontal.get(metrics);
		} catch(Exception ignored) { return true; } }

		public interface Effect {
			void draw(BufferedImage image, Graphics2D graphics, GlyphChar glyphChar);
		}
	}

	public static class GlyphLineList {
		/*
		 * bounds.x represents x offset.
		 * bounds.x represents y offset.
		 * bounds.width represents line width.
		 * bounds.height represents line height.
		 */

		protected final Font font;
		protected final int maxLine;
		protected final Rectangle bounds;
		protected final SVec4 padding;
		protected final ArrayList<GlyphChar> glyphChars;
		protected final boolean horizontal;
		protected final boolean advanceIncreasing;
		protected final boolean startBeginning;
		private final boolean meantHorizontal;

		protected GlyphLineList(Font font, int maxLine, int pos, SVec4 padding, boolean horizontal, boolean advanceIncreasing, boolean startBeginning, boolean meantHorizontal) {
			this.font = font;
			this.maxLine = maxLine;
			this.bounds = new Rectangle();
			this.padding = padding;
			this.glyphChars = new ArrayList<>();
			this.horizontal = horizontal;
			this.advanceIncreasing = advanceIncreasing;
			this.startBeginning = startBeginning;
			this.meantHorizontal = meantHorizontal;

			if(horizontal) { bounds.y = pos; if(!advanceIncreasing) bounds.x = maxLine; }
			if(!horizontal) { bounds.x = pos; if(!advanceIncreasing) bounds.y = maxLine; }
		}

		public Font getFont() { return font; }
		public int getMaxLine() { return maxLine; }
		public Rectangle getBounds() { return bounds; }
		public SVec4 getPadding() { return padding; }
		public ArrayList<GlyphChar> getGlyphChars() { return glyphChars; }
		public boolean isHorizontal() { return horizontal; }
		public boolean isAdvanceIncreasing() { return advanceIncreasing; }
		public boolean isStartBeginning() { return startBeginning; }

		public boolean containsCodePoint(int codePoint) {
			for(GlyphChar glyphChar : glyphChars) if(glyphChar.getCodePoint() == codePoint) return true;
			return false;
		}

		public boolean addGlyph(GlyphChar glyphChar) {
			if(containsCodePoint(glyphChar.getCodePoint())) return false; glyphChars.add(glyphChar);
			if(horizontal) {
				bounds.x += glyphChar.getAdvance() * (advanceIncreasing ? 1 : -1);
				bounds.height = Math.max(bounds.height, glyphChar.getBounds().height);
				bounds.width = advanceIncreasing ? bounds.x : maxLine - bounds.x;
			}
			if(!horizontal) {
				bounds.y += glyphChar.getAdvance() * (advanceIncreasing ? 1 : -1);
				bounds.width = Math.max(bounds.width, glyphChar.getBounds().width);
				bounds.height = advanceIncreasing ? bounds.y : maxLine - bounds.y;
			}
			return true;
		}

		public void render(BufferedImage image, PriorityList<GlyphPage.Effect> effects) {
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			GlyphChar lastChar = glyphChars.size() > 0 ? glyphChars.get(glyphChars.size() - 1) : null;
			int advance = advanceIncreasing ? 0 : maxLine - (lastChar == null ? 0 : horizontal ? lastChar.getBounds().height : lastChar.getBounds().width);
			for(GlyphChar glyphChar : glyphChars) {
				glyphChar.render(scratchImage, effects);
				graphics.drawImage(scratchImage, horizontal ? advance : bounds.x - (!startBeginning ? bounds.width : 0), !horizontal ? advance : bounds.y - (!startBeginning ? bounds.height : 0), null);
				advance += glyphChar.getAdvance() * (advanceIncreasing ? 1 : -1);
			}
		}

		public void getLost() { ExceptionUtils.doSilentThrowsRunnable(false, () -> {
			ClassUtils.setFinal(this, GlyphLineList.class.getDeclaredField("font"), null);
			ClassUtils.setFinal(this, GlyphLineList.class.getDeclaredField("bounds"), null);
			ClassUtils.setFinal(this, GlyphLineList.class.getDeclaredField("padding"), null);
			for(GlyphChar glyphChar : glyphChars) glyphChar.getLost();
		}); }
	}

	public static class GlyphChar {
		protected final GlyphVector vector;
		protected final GlyphMetrics metrics;
		protected final boolean horizontal;
		private final boolean meantHorizontal;

		protected final int codePoint;
		protected final Rectangle bounds;
		protected final Shape shape;
		protected final float advance;

		protected GlyphChar(int codePoint, Font font, boolean horizontal, SVec4 padding, boolean meantHorizontal) {
			this.vector = FontUtils.getVector(font, scratchGraphics, Character.toChars(codePoint));
			this.metrics = vector.getGlyphMetrics(0);
			this.horizontal = horizontal;
			this.meantHorizontal = meantHorizontal;

			this.codePoint = codePoint;
			this.bounds = getGlyphBounds(codePoint, vector);
			this.shape = vector.getGlyphOutline(0, -bounds.x + padding.x(), -bounds.y + padding.y());
			bounds.x -= padding.x();
			bounds.y -= padding.y();
			bounds.width += padding.x() + padding.z();
			bounds.height += padding.y() + padding.w();
			this.advance = horizontal == meantHorizontal ? metrics.getAdvance() + (horizontal ? padding.x() + padding.z() : padding.y() + padding.w()) :
					meantHorizontal ? bounds.height : bounds.width;
		}

		public GlyphVector getVector() { return vector; }
		public GlyphMetrics getMetrics() { return metrics; }
		public boolean isHorizontal() { return horizontal; }

		public int getCodePoint() { return codePoint; }
		public Rectangle getBounds() { return bounds; }
		public Shape getShape() { return shape; }
		public float getAdvance() { return advance; }

		public void render(BufferedImage image, PriorityList<GlyphPage.Effect> effects) {
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setBackground(new Color(255, 255, 255, 0));
			graphics.clearRect(0,0, image.getWidth(), image.getHeight());
			for(GlyphPage.Effect effect : effects) effect.draw(image, graphics, this);
		}

		public void getLost() { ExceptionUtils.doSilentThrowsRunnable(false, () -> {
			ClassUtils.setFinal(this, GlyphChar.class.getDeclaredField("vector"), null);
			ClassUtils.setFinal(this, GlyphChar.class.getDeclaredField("metrics"), null);
			ClassUtils.setFinal(this, GlyphChar.class.getDeclaredField("bounds"), null);
			ClassUtils.setFinal(this, GlyphChar.class.getDeclaredField("shape"), null);
		}); }

		private static Rectangle getGlyphBounds(int codePoint, GlyphVector vector) {
			if(Character.isWhitespace(codePoint)) return vector.getGlyphLogicalBounds(0).getBounds();
			return vector.getGlyphPixelBounds(0, scratchGraphics.getFontRenderContext(), 0, 0);
		}
	}
}
