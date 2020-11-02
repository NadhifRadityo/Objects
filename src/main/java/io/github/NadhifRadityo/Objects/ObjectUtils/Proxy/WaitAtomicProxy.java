package io.github.NadhifRadityo.Objects.ObjectUtils.Proxy;

import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

public class WaitAtomicProxy<T> extends Proxy<T> {
	protected final Object changeLock = new Object();
	protected volatile boolean isChanging = false;

	public WaitAtomicProxy(T object) { super(object); }
	public WaitAtomicProxy() { this(null); }

	public T get() { synchronized(changeLock) {
		while(isChanging) ExceptionUtils.doSilentThrowsRunnable(false, changeLock::wait);
		return super.get();
	} }
	public void set(T object) { synchronized(changeLock) {
		isChanging = true;
		super.set(object);
		isChanging = false;
		changeLock.notifyAll();
	} }
}
