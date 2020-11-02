package io.github.NadhifRadityo.Objects.Utilizations.ColorPalettes;

import java.awt.*;

public interface ColorPalette {
	String getName();
	Color getColor();
	Color getColor(int alpha);
	Color getColor(float alpha);
}
