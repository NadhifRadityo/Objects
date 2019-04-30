package io.github.NadhifRadityo.Objects.Canvas.Shapes;

public class Square extends Rectangle {
	public Square(int x, int y, int s) { super(x, y, s, s); }
	public Square(Point p, int s) { this(p.getX(), p.getY(), s); }
}
