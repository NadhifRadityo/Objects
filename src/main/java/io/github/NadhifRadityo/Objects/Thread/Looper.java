package io.github.NadhifRadityo.Objects.Thread;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

public final class Looper {
	private static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
	private static Looper mainLooper;
	
	protected final Thread thread;
	protected final PostQueue queue;
	protected final Logger logger;
	
	protected volatile ExceptionHandler exceptionHandler;
	protected volatile ThreadProgressHandler progressHandler;
	protected volatile RunnablePost jobRunning;
	
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
			me.logger.error(">>> " + ExceptionUtils.getStackTrace(e));
			if(me.progressHandler != null) me.progressHandler.catchException(e, me.jobRunning, me);
			me.jobRunning = null; me.updateProgress();
			if(me.exceptionHandler == null) throw new RuntimeException(e);
			me.exceptionHandler.onExceptionOccurred(e);
		} }
	}
	
	private Looper(boolean quitAllowed) {
		thread = Thread.currentThread();
		queue = new PostQueue(quitAllowed);
		logger = new Logger("[Thread " + thread.getName() + "] ");
	}
	
	private void runPost(RunnablePost runnable) throws Exception {
		logger.log(">>> Dispatching to " + runnable.getTitle() + (runnable.getSubject() != null && !runnable.getSubject().isEmpty() ?
				": " + runnable.getSubject() : ""));
		long startTime = System.currentTimeMillis();
		
		jobRunning = runnable;
		logProgress(-1, -1, "Job Running...", runnable);
		runnable.work();
		logProgress(1, 1, "Job Done!", runnable);
		jobRunning = null;

		logger.log("<<< Finished to " + runnable.getTitle() + (runnable.getSubject() != null && !runnable.getSubject().isEmpty() ?
				": " + runnable.getSubject() + " | " : " ") + "Took: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
	protected void logProgress(int current, int total, String desc, RunnablePost jobRunning) {
		if(progressHandler != null) progressHandler.logProgress(current, total, desc, jobRunning, this);
	} public void logProgress(int current, int total, String desc) { logProgress(current, total, desc, jobRunning); } 
	protected void updateProgress() { if(progressHandler != null) progressHandler.update(); }

	public Thread getThread() { return thread; }
	public PostQueue myQueue() { return queue; }
	public Logger getLogger() { return logger; }
	public ExceptionHandler getExceptionHandler() { return exceptionHandler; }
	public ThreadProgressHandler getProgressHandler() { return progressHandler; }
	public RunnablePost getJobRunning() { return jobRunning; }
	
	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	public void setProgressHandler(ThreadProgressHandler progressHandler) {
		this.progressHandler = progressHandler;
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
			try { runPost(runnable); } catch(Exception e) {
				logger.error(">>> " + ExceptionUtils.getStackTrace(e));
				if(progressHandler != null) progressHandler.catchException(e, jobRunning, this);
				jobRunning = null; updateProgress();
				if(exceptionHandler == null) throw new RuntimeException(e);
				exceptionHandler.onExceptionOccurred(e);
			}
		} queue.quit();
		logger.debug("<<< Thread Quited Safely | Took: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
	public String toString() {
		return "Looper (" + thread.getName() + ", tid " + thread.getId() + ") {" + Integer.toHexString(System.identityHashCode(this)) + "}";
	}
}