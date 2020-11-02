package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

public class Size extends Vec2 {
	public Size(int width, int height) { super(width, height); }
	public Size(Vec2 v) { this((int) v.x(), (int) v.y()); }
	public Size(int a) { this(a, a); }
	public Size() { this(0); }

	public int getWidth() { return (int) d[0]; }
	public int getHeight() { return (int) d[1]; }
	public void setWidth(int width) { d[0] = width; }
	public void setHeight(int height) { d[1] = height; }

	public static Size from(INT size) { return new Size(size.getWidth(), size.getHeight()); }
	public static Size from(LONG size) { return new Size(size.getWidth(), size.getHeight()); }

	public static class INT extends IVec2 {
		public INT(int width, int height) { super(width, height); }
		public INT(Vec2 v) { this((int) v.x(), (int) v.y()); }
		public INT(int a) { this(a, a); }
		public INT() { this(0); }

		public int getWidth() { return (int) d[0]; }
		public int getHeight() { return (int) d[1]; }
		public void setWidth(int width) { d[0] = width; }
		public void setHeight(int height) { d[1] = height; }

		public static INT from(Size size) { return new INT(size.getWidth(), size.getHeight()); }
		public static INT from(LONG size) { return new INT(size.getWidth(), size.getHeight()); }
	}
	public static class LONG extends LVec2 {
		public LONG(int width, int height) { super(width, height); }
		public LONG(Vec2 v) { this((int) v.x(), (int) v.y()); }
		public LONG(int a) { this(a, a); }
		public LONG() { this(0); }

		public int getWidth() { return (int) d[0]; }
		public int getHeight() { return (int) d[1]; }
		public void setWidth(int width) { d[0] = width; }
		public void setHeight(int height) { d[1] = height; }

		public static LONG from(Size size) { return new LONG(size.getWidth(), size.getHeight()); }
		public static LONG from(INT size) { return new LONG(size.getWidth(), size.getHeight()); }
	}
}
