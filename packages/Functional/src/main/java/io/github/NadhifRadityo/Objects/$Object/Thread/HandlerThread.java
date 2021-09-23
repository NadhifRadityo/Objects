package io.github.NadhifRadityo.Objects.$Object.Thread;

import io.github.NadhifRadityo.Objects.$Utilizations.ExceptionUtils;

@SuppressWarnings("jol")
public class HandlerThread extends Thread {
	protected int tid = -1;
	protected Looper looper;
	protected Handler handler;

	public HandlerThread(String name) { super(name); }

	@Override public void run() {
		tid = getThreadId(); Looper.prepare();
		synchronized(this) { looper = Looper.myLooper(); notifyAll(); }
		onLooperPrepared(); Looper.loop(); tid = -1;
		synchronized(this) { looper = null; notifyAll(); }
	}

	public int getThreadId() { return tid; }
	public Looper getLooper() { synchronized(this) {
		if(looper != null) return looper;
		while(isAlive() && looper == null)
			ExceptionUtils.doSilentThrowsRunnable(false, HandlerThread.this::wait);
		return looper;
	} }
	public Handler getThreadHandler() {
		if(handler == null) handler = new Handler(getLooper());
		return handler;
	}

	protected void onLooperPrepared() { }

	public boolean quit() {
		Looper looper = getLooper();
		if(looper == null) return false;
		looper.quit(); return true;
	}
	public boolean quitSafely() {
		Looper looper = getLooper();
		if(looper == null) return false;
		looper.quitSafely(); return true;
	}

	public static void runOnThreadHandler(Runnable runnable, HandlerThread handler, String title, String desc) {
		if(!handler.getLooper().isCurrentThread())
			handler.getThreadHandler().post(() -> runOnThreadHandler(runnable, handler, title, desc), title, desc);
		else runnable.run();
	} public static void runOnThreadHandler(Runnable runnable, HandlerThread handler) { runOnThreadHandler(runnable, handler, null, null); }
}
