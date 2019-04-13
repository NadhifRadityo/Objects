package io.github.NadhifRadityo.Objects.Object;

public interface DeadableObject {
	void setDead();
	boolean isDead();
	default void assertDead() {
		if(isDead()) throw new IllegalStateException("This object is dead!");
	}
}
