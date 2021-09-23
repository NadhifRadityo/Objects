package io.github.NadhifRadityo.Objects.Library.Constants;

public enum Action {
	DOWNLOAD(1L), DELETE(1L << 2), PACK(1L << 3);

	public final long id;
	Action(long id) {
		this.id = id;
	}

	public boolean contains(long ids) {
		return (ids & this.id) != 0;
	}

	public static Action fromId(long id) {
		for(Action action : values())
			if(action.id == id) return action;
		return null;
	}
}
