package io.github.NadhifRadityo.Objects.ObjectUtils.Proxy;

public abstract class WriteNTimeProxy<T> extends Proxy<T> {
	protected final int maxWrite;
	protected int writeCount = 0;

	protected WriteNTimeProxy(T object, int maxWrite) { super(object); this.maxWrite = maxWrite; }
	protected WriteNTimeProxy(int maxWrite) { this(null, maxWrite); }

	@Override public void set(T object) {
		if(writeCount < maxWrite) { super.set(object); writeCount++; return; }
		throw new IllegalArgumentException(maxWrite + " time write only!");
	}
}
