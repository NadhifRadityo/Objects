package io.github.NadhifRadityo.Objects.Thread;

public class HandlerThread extends Thread {
    private int mTid = -1;
    private Looper mLooper;
    private Handler mHandler;
    
    public HandlerThread(String name) {
        super(name);
    }
    
    @Override
    public void run() {
        mTid = getThreadId();
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        } onLooperPrepared();
        Looper.loop();
        mTid = -1;
    }
    
    public int getThreadId() { return mTid; }
    public Looper getLooper() {
        if (!isAlive()) return null;
        // If the thread has been started, wait until the looper has been created.
        synchronized (this) {
            while (isAlive() && mLooper == null) {
                try { wait();
                } catch (InterruptedException e) { }
            }
        } return mLooper;
    }
    public Handler getThreadHandler() {
        if (mHandler == null) mHandler = new Handler(getLooper());
        return mHandler;
    }
    
    protected void onLooperPrepared() {
    }
    
    public boolean quit() {
        Looper looper = getLooper();
        if (looper != null) {
            looper.quit();
            return true;
        } return false;
    }
    public boolean quitSafely() {
        Looper looper = getLooper();
        if (looper != null) {
            looper.quitSafely();
            return true;
        } return false;
    }
}
