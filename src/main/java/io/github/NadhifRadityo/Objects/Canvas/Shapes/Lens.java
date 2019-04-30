package io.github.NadhifRadityo.Objects.Canvas.Shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Lens extends Rectangle {
	
	public Lens(int x, int y, int width, int height) {
		super(x, y, width, height);
	} public Lens(Point p, int width, int height) { this(p.getX(), p.getY(), width, height); }
	
	@Override
	public int getHeight() {
		Circle c1 = new Circle(0, 0, height);
		Circle c2 = new Circle(height - width, 0, height);
		Point[] intersect = Oval.checkIntersects(c1, c2, 1000, width / 2);
		return (int) new Line(intersect[0], intersect[1]).getDistance();
	}
	
	@Override
	public void draw(Graphics g) {
		if(!(g instanceof Graphics2D)) return;
		Area area = new Area(new Ellipse2D.Double(0, 0, height, height));
		area.intersect(new Area(new Ellipse2D.Double(height - width, 0, height, height)));
		
		AffineTransform transform = new AffineTransform();
		transform.translate(-height + width + x, -(height - getHeight()) / 2 + y);
		area.transform(transform);
		((Graphics2D) g).draw(area);
	}
}
