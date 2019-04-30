package io.github.NadhifRadityo.Objects.Canvas.Shapes;

public class Circle extends Oval {
	public Circle(int x, int y, int d, boolean center) { super(x, y, d, d, center); }
	public Circle(Point p, int d, boolean center) { this(p.getX(), p.getY(), d, center); }
	public Circle(int x, int y, int d) { this(x, y, d, false); }
	public Circle(Point p, int d) { this(p.getX(), p.getY(), d); }
	
	public int getD() { return (width + height) / 2; }
	public int getR() { return getD() / 2; }
}
