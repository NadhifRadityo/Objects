package io.github.NadhifRadityo.Objects.Utilizations;

import java.awt.*;

public enum WindowPosition{
	CENTER, LEFT, TOP, RIGHT, BOTTOM,
	LEFT_CENTER,
	RIGHT_CENTER,
	TOP_LEFT, TOP_CENTER, TOP_RIGHT,
	BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT;
	
	private static final Rectangle usableBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	
	public Point getPoint(Dimension size) {
		Point point = new Point();
		switch(this) {
			case CENTER:
				point.setLocation(usableBounds.width / 2 - size.width / 2, usableBounds.height / 2 - size.height / 2);
				break;
			case LEFT:
				point.setLocation(0, 0);
				break;
			case TOP:
				point.setLocation(0, 0);
				break;
			case RIGHT:
				point.setLocation(usableBounds.width - size.getWidth(), 0);
				break;
			case BOTTOM:
				point.setLocation(0, usableBounds.height - size.getHeight());
				break;
			case LEFT_CENTER:
				point.setLocation(0, usableBounds.height / 2 - size.height / 2);
				break;
			case RIGHT_CENTER:
				point.setLocation(usableBounds.width - size.getWidth(), usableBounds.height / 2 - size.height / 2);
				break;
			case TOP_LEFT:
				point.setLocation(0, 0);
				break;
			case TOP_CENTER:
				point.setLocation(usableBounds.width / 2 - size.width / 2, 0);
				break;
			case TOP_RIGHT:
				point.setLocation(usableBounds.width - size.width, 0);
				break;
			case BOTTOM_LEFT:
				point.setLocation(0, usableBounds.height - size.getHeight());
				break;
			case BOTTOM_CENTER:
				point.setLocation(usableBounds.width / 2 - size.width / 2, usableBounds.height - size.getHeight());
				break;
			case BOTTOM_RIGHT:
				point.setLocation(usableBounds.width - size.getWidth(), usableBounds.height - size.getHeight());
				break;
		}
		point.setLocation(usableBounds.x + point.x, usableBounds.y + point.y);
		return point;
	}
	
	public static Rectangle getUsablebounds() {
		return (Rectangle) usableBounds.clone();
	}
}
