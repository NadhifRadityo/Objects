package io.github.NadhifRadityo.Objects.Utilizations.Direction;

public enum Direction2D {
	UP, BOTTOM, RIGHT, LEFT;
	
	public Compass toCompass() {
		return toCompass(this);
	}
	
	public static Compass toCompass(Direction2D direction) {
		return Compass.fromDirection2D(direction);
	}
	public static Direction2D fromCompass(Compass compass) {
		return Compass.toDirection2D(compass);
	}
}
