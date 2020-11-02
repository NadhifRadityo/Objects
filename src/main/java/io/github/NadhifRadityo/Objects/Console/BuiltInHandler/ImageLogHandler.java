package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Console.LogHandler;
import io.github.NadhifRadityo.Objects.Console.LogRecord;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ConsoleUtils;
import io.github.NadhifRadityo.Objects.Utilizations.MapUtils;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ImageLogHandler implements LogHandler {
	public static final int DEFAULT_PRIORITY = 106;
	public static final Object ENABLED = new Object();
	public static final Object FIT_IMAGE_ENABLED = new Object();
	public static final Object MARK_NEXT_AS_RASTER = new Object();

	protected boolean colored;
	protected boolean useShadesAsAlpha;
	protected char[] shades;
	protected double[] shadeThresholds;

	public ImageLogHandler(boolean colored, boolean useShadesAsAlpha, char[] shades, double[] shadeThresholds) {
		if(shades.length != shadeThresholds.length)
			throw new IllegalArgumentException("Length must be equal");
		this.colored = colored;
		this.useShadesAsAlpha = useShadesAsAlpha;
		this.shades = shades;
		this.shadeThresholds = shadeThresholds;
	}
	public ImageLogHandler(boolean colored, boolean useShadesAsAlpha) {
		this(colored, useShadesAsAlpha, new char[] { ' ', '░', '▒', '▓', '█' }, new double[] { 0, (double) 1 / 5, (double) 2 / 5, (double) 3 / 5, (double) 4 / 5 });
	}
	public ImageLogHandler() {
		this(true, true);
	}

	public boolean isColored() { return colored; }
	public boolean isUseShadesAsAlpha() { return useShadesAsAlpha; }
	public char[] getShades() { return shades; }
	public double[] getShadeThresholds() { return shadeThresholds; }

	public void setColored(boolean colored) { this.colored = colored; }
	public void setUseShadesAsAlpha(boolean useShadesAsAlpha) { this.useShadesAsAlpha = useShadesAsAlpha; }
	public void setShades(char[] shades, double[] shadeThresholds) {
		if(shades.length != shadeThresholds.length)
			throw new IllegalArgumentException("Length must be equal");
		this.shades = shades;
		this.shadeThresholds = shadeThresholds;
	}

	@Override public void manipulateLog(LogRecord record) {
		List<Object> args = record.getArgs();
		Object STATE_ENABLED = AttributesHandler.ON;
		Object STATE_FIT_IMAGE_ENABLED = AttributesHandler.OFF;

		boolean ansiSupported = AnsiLogHandler.isAnsiSupported(record.getLogger());
		LinkedHashMap<Integer, ArrayList<Object>> inserts = Pool.tryBorrow(Pool.getPool(LinkedHashMap.class));
		LinkedHashMap<Integer, AnsiLogHandler.AnsiCommand> compiled = Pool.tryBorrow(Pool.getPool(LinkedHashMap.class));
		StringBuilder stringBuilder = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
		int nextRaster = 0; int[] consoleSize = null;

		int width = 0; int height = 0;
		BufferedImage image = null;
		for(int i = 0; i < args.size(); i++) { Object arg = args.get(i);
			if((STATE_ENABLED = AttributesHandler.check(arg, ENABLED, STATE_ENABLED, args, i)) != AttributesHandler.ON) continue;
			STATE_FIT_IMAGE_ENABLED = AttributesHandler.check(arg, FIT_IMAGE_ENABLED, STATE_FIT_IMAGE_ENABLED, args, i);
			if(arg == MARK_NEXT_AS_RASTER) { nextRaster = 1; args.set(i, AttributesHandler.DUMMY); continue; }
			if(nextRaster == 1) { try { String[] info = arg.toString().replaceAll("\\s", "").split(","); width = Integer.parseInt(info[0]); height = Integer.parseInt(info[1]); } catch(NumberFormatException e) { width = 0; height = 0; } nextRaster--; args.set(i, AttributesHandler.DUMMY); continue; }
			if(arg instanceof BufferedImage) { image = (BufferedImage) arg; width = width != 0 ? width : image.getWidth(); height = height != 0 ? height : image.getHeight(); } if(width <= 0 || height <= 0 || image == null) { width = 0; height = 0; image = null; continue; }
			if(STATE_FIT_IMAGE_ENABLED == AttributesHandler.ON && consoleSize == null) { consoleSize = new int[] { 80, 25 }; try { if(ConsoleUtils.GET_CONSOLE_SIZE_POSSIBLE != 0) ConsoleUtils.getConsoleSize(consoleSize); } catch(Exception ignored) { } }

			int targetWidth = width; int targetHeight = height; int lastColor = 0;
			if(STATE_FIT_IMAGE_ENABLED == AttributesHandler.ON) { targetWidth = consoleSize[0]; targetHeight = consoleSize[1]; }
			ArrayList<Object> imageText = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			for(int y = 0; y < targetHeight; y++) {
				int my = NumberUtils.map(y, 0, targetHeight, 0, image.getHeight());
				for(int x = 0; x < targetWidth; x++) {
					int mx = NumberUtils.map(x, 0, targetWidth, 0, image.getWidth());
					int color = image.getRGB(mx, my);

					if(colored && ansiSupported && (color != lastColor || x == 0)) {
						if(compiled.size() >= 100) MapUtils.reusableIterator(compiled).remove();
						AnsiLogHandler.AnsiCommand compiledColor = compiled.get(color);
						if(compiledColor == null) {
							compiledColor = AnsiLogHandler.AnsiColor.getForeground24BitCustomColor(new Color(color));
							compiled.put(color, compiledColor);
						}
						imageText.add(compiledColor);
						imageText.add(stringBuilder.toString()); stringBuilder.setLength(0);
					}
					double alpha = useShadesAsAlpha ? (double) (color >> 24 & 0xFF) / 255 : 1;
					int j = 0;
					while(j < shadeThresholds.length && shadeThresholds[j] <= alpha) j++;
					if(j >= shadeThresholds.length) j = shadeThresholds.length - 1;
					stringBuilder.append(shades[j]); lastColor = color;
				}
				if(y != targetHeight - 1) stringBuilder.append(System.lineSeparator());
				lastColor = 0;
			}
			width = 0; height = 0; image = null;
			inserts.put(i, imageText); args.set(i, AttributesHandler.DUMMY);
		}

		try {
			if(inserts.isEmpty()) return;
			MapUtils.sort(inserts, (o1, o2) -> o2.getKey() - o1.getKey());
			for(Iterator<Map.Entry<Integer, ArrayList<Object>>> it = MapUtils.reusableIterator(inserts); it.hasNext(); ) {
				Map.Entry<Integer, ArrayList<Object>> insert = it.next();
				args.remove((int) insert.getKey());
				args.addAll(insert.getKey(), insert.getValue());
				Pool.returnObject(ArrayList.class, insert.getValue());
			}
		} finally {
			AttributesHandler.consume(args);
			Pool.returnObject(LinkedHashMap.class, inserts);
			Pool.returnObject(LinkedHashMap.class, compiled);
			Pool.returnObject(StringBuilder.class, stringBuilder);
		}
	}
}
