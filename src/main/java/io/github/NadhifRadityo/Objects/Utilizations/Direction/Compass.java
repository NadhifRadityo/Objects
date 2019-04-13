package io.github.NadhifRadityo.Objects.Utilizations.Direction;

public enum Compass {
	// Is'nt that sick?!
					 NORTH, 
		NORTH_WEST,			 NORTH_EAST, 
								
		WEST,						EAST,
							
		SOUTH_WEST,			 SOUTH_EAST, 
		
					 SOUTH;
	
	public Direction2D toDirection2D() {
		return toDirection2D(this);
	}
	public Compass toPrimaryDirection() {
		return toPrimaryDirection(this);
	}
	
	public static Direction2D toDirection2D(Compass compass) {
		switch(compass) {
			case NORTH:
				return Direction2D.UP;
			case EAST:
				return Direction2D.RIGHT;
			case SOUTH:
				return Direction2D.BOTTOM;
			case WEST:
				return Direction2D.LEFT;
			default:
				return null;
		}
	}
	
	public static Compass fromDirection2D(Direction2D direction) {
		switch(direction) {
			case UP:
				return Compass.NORTH;
			case RIGHT:
				return Compass.EAST;
			case BOTTOM:
				return Compass.SOUTH;
			case LEFT:
				return Compass.WEST;
		}
		return null;
	}
	public static Compass toPrimaryDirection(Compass compass) {
		switch(compass) {
			case NORTH:
			case EAST:
			case SOUTH:
			case WEST:
				return compass;
			default:
				return null;
		}
	}
}
