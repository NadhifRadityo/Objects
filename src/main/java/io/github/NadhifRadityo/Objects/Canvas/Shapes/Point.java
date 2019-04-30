package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Circle {
	public Point(int x, int y) {
		super(x, y, 2, true);
	}
	
	@Override public int getX() { return super.getX(true); }
	@Override public int getY() { return super.getY(true); }
	
	@Override
	public void draw(Graphics g) {
		Color defColor = g.getColor();
		g.setColor(Color.RED);
		super.draw(g);
		g.setColor(defColor);
	}
}
