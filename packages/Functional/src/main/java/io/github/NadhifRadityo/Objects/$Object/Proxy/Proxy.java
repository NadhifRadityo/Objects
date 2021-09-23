package io.github.NadhifRadityo.Objects.$Object.Proxy;

public class Proxy<T> {
	protected T object;

	public Proxy(T object) { this.object = object; }

	public T get() { return object; }
	public void set(T object) { this.object = object; }

	public static <T> Proxy<T> as(T object) {
		return new Proxy<>(object);
	}
}
