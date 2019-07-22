package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import io.github.NadhifRadityo.Objects.Canvas.Sprite;

import java.awt.*;
import java.awt.geom.Area;

public class CustomShape extends Sprite {
	protected Polygon polygon;

	public CustomShape(int x, int y, Polygon polygon) {
		super(x, y);
		this.polygon = polygon;
	}
	public CustomShape(int x, int y, int[] xPoints, int[] yPoints, int n) { this(x, y, new Polygon(xPoints, yPoints, n)); }
	public CustomShape(int x, int y, int[]... points) { this(x, y, extractPoints(points)); }
	
	public Polygon getPolygon() { return polygon; }
	public void setPolygon(Polygon polygon) { this.polygon = polygon; }
	public void setPolygon(int[] xPoints, int[] yPoints, int n) { setPolygon(new Polygon(xPoints, yPoints, n)); }
	public void setPolygon(int[]... points) { setPolygon(extractPoints(points)); }
	
	@Override public void draw(Graphics g) { g.drawPolygon(polygon); }
	@Override public Area getArea() { return new Area(polygon); }
	
	protected static Polygon extractPoints(int[]... pointz) {
		int[] xPoints = new int[pointz.length];
		int[] yPoints = new int[pointz.length];
		for(int n = 0; n < pointz.length; n++) {
			if(pointz[n] == null) throw new NullPointerException();
			if(pointz[n].length != 2) throw new IllegalArgumentException("Invalid points!");
			xPoints[n] = pointz[n][0];
			yPoints[n] = pointz[n][1];
		} return new Polygon(xPoints, yPoints, pointz.length);
	}
}
