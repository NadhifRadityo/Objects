package io.github.NadhifRadityo.Objects.ObjectUtils;

import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;

public class WaitAtomic<T> {
    protected final ExceptionHandler exceptionHandler;
    protected final Object changeLock = new Object();
    protected volatile boolean isChanging = false;
    protected volatile T value;

    public WaitAtomic(T initialValue, ExceptionHandler exceptionHandler) {
        this.value = initialValue;
        this.exceptionHandler = exceptionHandler;
    }
    public WaitAtomic(T initialValue) { this(initialValue, null); }
    public WaitAtomic() { this(null); }

    public T get() {
    	synchronized(changeLock) { try {
            while(isChanging) changeLock.wait();
            return value;
        } catch (Exception e) { throwException(e); } }
        throw new IllegalStateException("Object#wait interrupted");
    }
    public void set(T value) throws InterruptedException {
    	synchronized (changeLock) {
	    	isChanging = true;
	    	this.value = value;
	    	isChanging = false;
	    	Thread.sleep(1000);
        	changeLock.notifyAll();
		}
    }

    protected void throwException(Exception e) {
        if (exceptionHandler != null) exceptionHandler.onExceptionOccurred(e);
    }
}
