package io.github.NadhifRadityo.Objects.$Object.Thread;

import io.github.NadhifRadityo.Objects.$Interface.Functional.ExceptionHandler;

public final class Looper {
	private static final ThreadLocal<Looper> threadLocal = new ThreadLocal<>();
	private static Looper mainLooper;

	public static Looper myLooper() { return threadLocal.get(); }
	public static Looper getMainLooper() { synchronized(Looper.class) { return mainLooper; } }
	public static void prepare() { prepare(true); }
	private static void prepare(boolean quitAllowed) {
		if(threadLocal.get() != null) throw new RuntimeException("Only one Looper may be created per thread");
		threadLocal.set(new Looper(quitAllowed));
	}
	public static void prepareMainLooper() {
		prepare(false);
		synchronized(Looper.class) {
			if(mainLooper != null) throw new IllegalStateException("The main Looper has already been prepared.");
			mainLooper = myLooper();
		}
	}

	public static void loop() {
		Looper me = myLooper(); PostQueue queue = me != null ? me.queue : null;
		if(me == null || queue == null) throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
		while(true) { try { RunnablePost runnable = queue.get(); if(runnable == null) return; me.runPost(runnable); } catch(Throwable e) { me.catchException(e); } }
	}

	protected final Thread thread;
	protected final PostQueue queue;

	protected volatile ExceptionHandler exceptionHandler;
	protected volatile ThreadProgressHandler progressHandler;
	protected volatile RunnablePost jobRunning;

	private Looper(boolean quitAllowed) {
		this.thread = Thread.currentThread();
		this.queue = new PostQueue(quitAllowed);
	}

	private void runPost(RunnablePost runnable) throws Exception {
		jobRunning = runnable;
		logProgress(-1, -1, "Job Running...", runnable);
		runnable.work();
		logProgress(1, 1, "Job Done!", runnable);
		jobRunning = null;
	}

	protected void logProgress(int current, int total, String desc, RunnablePost jobRunning) { if(progressHandler != null) progressHandler.logProgress(current, total, desc, jobRunning, this); }
	protected void catchException(Throwable e) { if(progressHandler != null) progressHandler.catchException(e, jobRunning, this); jobRunning = null; updateProgress(); if(exceptionHandler == null) throw new RuntimeException(e); exceptionHandler.onExceptionOccurred(e); }
	protected void updateProgress() { if(progressHandler != null) progressHandler.update(); }
	public void logProgress(int current, int total, String desc) { logProgress(current, total, desc, jobRunning); }

	public Thread getThread() { return thread; }
	public PostQueue myQueue() { return queue; }
	public ExceptionHandler getExceptionHandler() { return exceptionHandler; }
	public ThreadProgressHandler getProgressHandler() { return progressHandler; }
	public RunnablePost getJobRunning() { return jobRunning; }

	public void setExceptionHandler(ExceptionHandler exceptionHandler) { this.exceptionHandler = exceptionHandler; }
	public void setProgressHandler(ThreadProgressHandler progressHandler) { this.progressHandler = progressHandler; }
	public boolean isCurrentThread() { return Thread.currentThread() == thread; }
	public boolean isIdling() { return queue.isWaiting(); }

	public void quit() { queue.quit(); }
	public void quitSafely() {
		while(queue.size() != 0) {
			RunnablePost runnable = queue.get(); if(runnable == null) break;
			try { runPost(runnable); } catch(Throwable e) { catchException(e); }
		} queue.quit();
	}

	@Override
	public String toString() {
		return "Looper{" +
				"name=" + thread.getName() +
				", tid=" + thread.getId() +
				"} (" + Integer.toHexString(System.identityHashCode(this)) + ")";
	}
}
