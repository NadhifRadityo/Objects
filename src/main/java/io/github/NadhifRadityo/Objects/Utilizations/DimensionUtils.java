package io.github.NadhifRadityo.Objects.Utilizations;

import java.awt.*;

public final class DimensionUtils {
	private DimensionUtils() {
		
	}
	
	private static final Dimension MAX_DIMENSION = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	private static final Dimension NEUTRAL_DIMENSION = new Dimension(0, 0);

	public static Dimension getMaxDimension() { return (Dimension) MAX_DIMENSION.clone(); }
	public static Dimension getFullWidthDimension() { Dimension dim = getMaxDimension(); dim.height = 0; return dim; }
	public static Dimension getFullHeightDimension() { Dimension dim = getMaxDimension(); dim.width = 0; return dim; }
	public static Dimension getNeutralDimension() { return (Dimension) NEUTRAL_DIMENSION.clone(); }
}
