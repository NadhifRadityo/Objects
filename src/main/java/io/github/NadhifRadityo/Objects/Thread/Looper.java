package io.github.NadhifRadityo.Objects.Thread;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;
import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

public final class Looper {
	private static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
	private static Looper mainLooper;
	
	private final Thread thread;
	private final PostQueue queue;
	
	private final Logger logger;
	private volatile ExceptionHandler exceptionHandler;
	private volatile RunnablePost jobRunning;
	
	public static void prepare() { prepare(true); }
	private static void prepare(boolean quitAllowed) {
		if (sThreadLocal.get() != null) throw new RuntimeException("Only one Looper may be created per thread");
		sThreadLocal.set(new Looper(quitAllowed));
	}
	public static void prepareMainLooper() {
		prepare(false);
		synchronized (Looper.class) {
			if (mainLooper != null) throw new IllegalStateException("The main Looper has already been prepared.");
			mainLooper = myLooper();
		}
	}

	public static Looper myLooper() { return sThreadLocal.get(); }
	public static Looper getMainLooper() { synchronized (Looper.class) { return mainLooper; } }
	
	public static void loop() {
		final Looper me = myLooper();
		if (me == null) throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
		final PostQueue queue = me.queue;
		for (;;) { try {
			RunnablePost runnable = queue.get();
			if (runnable == null) return;
			me.runPost(runnable);
		} catch(Exception e) {
			if(me.exceptionHandler == null) throw e;
			me.exceptionHandler.onExceptionOccurred(e);
		} }
	}
	
	private Looper(boolean quitAllowed) {
		thread = Thread.currentThread();
		queue = new PostQueue(quitAllowed);
		logger = new Logger("[Thread " + thread.getName() + "] ");
	}
	
	private void runPost(RunnablePost runnable) {
		logger.log(">>> Dispatching to " + runnable.getTitle() + (runnable.getSubject() != null && runnable.getSubject() != "" ? 
				": " + runnable.getSubject() : ""));
		long startTime = System.currentTimeMillis();
		
		jobRunning = runnable;
		ExceptionUtils.doSilentException((e) -> {
			logger.error(">>> " + ExceptionUtils.getStackTrace(e));
		}, (ThrowsRunnable) () -> runnable.work());
		jobRunning = null;
		
		logger.log("<<< Finished to " + runnable.getTitle() + (runnable.getSubject() != null && runnable.getSubject() != "" ?
				": " + runnable.getSubject() + " | " : " ") + "Took: " + (System.currentTimeMillis() - startTime) + "ms");
	}

	public Thread getThread() { return thread; }
	public PostQueue myQueue() { return queue; }
	public Logger getLogger() { return logger; }
	public RunnablePost getJobRunning() { return jobRunning; }
	
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	public boolean isCurrentThread() { return Thread.currentThread() == thread; }
	public boolean isIdling() { return queue.isWaiting(); }
	
	public void quit() {
		logger.debug(">>> Quiting Thread");
		long startTime = System.currentTimeMillis();
		queue.quit();
		logger.debug("<<< Thread Quited | Took: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	public void quitSafely() {
		logger.debug(">>> Quiting Safely Thread");
		long startTime = System.currentTimeMillis();
		while(queue.size() != 0) {
			RunnablePost runnable = queue.get();
			if (runnable == null) break;
			runPost(runnable);
		} queue.quit();
		logger.debug("<<< Thread Quited Safely | Took: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
	public String toString() {
		return "Looper (" + thread.getName() + ", tid " + thread.getId() + ") {" + Integer.toHexString(System.identityHashCode(this)) + "}";
	}
}