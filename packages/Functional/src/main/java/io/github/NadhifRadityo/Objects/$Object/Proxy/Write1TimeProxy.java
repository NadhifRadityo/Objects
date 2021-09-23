package io.github.NadhifRadityo.Objects.$Object.Proxy;

public class Write1TimeProxy<T> extends WriteNTimeProxy<T> {

	public Write1TimeProxy(T object) { super(object, 1); }
	public Write1TimeProxy() { this(null); }

}
