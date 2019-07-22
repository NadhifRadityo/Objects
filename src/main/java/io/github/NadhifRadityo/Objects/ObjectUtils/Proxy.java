package io.github.NadhifRadityo.Objects.ObjectUtils;

public class Proxy<T> {
	protected T object;

	public Proxy(T object) { this.object = object; }

	public T get() { return object; }
	public void set(T object) { this.object = object; }
}
