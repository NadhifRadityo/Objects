package io.github.NadhifRadityo.Objects.ObjectUtils;

import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;

public class WaitAtomic<T> extends Proxy<T> {
    protected final ExceptionHandler exceptionHandler;
    protected final Object changeLock = new Object();
    protected volatile boolean isChanging = false;

    public WaitAtomic(T object, ExceptionHandler exceptionHandler) { super(object); this.exceptionHandler = exceptionHandler; }
    public WaitAtomic(T object) { this(object, null); }
    public WaitAtomic() { this(null); }

    public T get() {
    	synchronized(changeLock) { try {
            while(isChanging) changeLock.wait();
            return super.get();
        } catch (Exception e) { throwException(e); } }
        throw new IllegalStateException("Object#wait interrupted");
    }
    public void set(T object) { try { synchronized (changeLock) {
		isChanging = true;
		super.set(object);
		isChanging = false;
		Thread.sleep(1000);
		changeLock.notifyAll();
	} } catch(InterruptedException e) { throw new Error(e); } }

    protected void throwException(Exception e) {
        if (exceptionHandler != null) exceptionHandler.onExceptionOccurred(e);
    }
}
