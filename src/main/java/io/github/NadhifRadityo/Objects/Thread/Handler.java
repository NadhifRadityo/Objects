package io.github.NadhifRadityo.Objects.Thread;

import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.Utilizations.RunnableUtils;

public class Handler {
	private static Handler MAIN_THREAD_HANDLER = null;
    private final Looper looper;
    private final PostQueue queue;
    
    public Handler(Looper looper) {
        this.looper = looper;
        this.queue = looper.myQueue();
    }
    
    public static Handler getMain() {
        if (MAIN_THREAD_HANDLER == null) MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());
        return MAIN_THREAD_HANDLER;
    }
    
    public final RunnablePost post(Runnable r, String title, String subject) {
    	return sendMessageDelayed(createRunnablePost(r, title, subject), 0L);
    }
    public final RunnablePost postDelayed(Runnable r, long delayMillis, String title, String subject) {
        return sendMessageDelayed(createRunnablePost(r, title, subject), delayMillis);
    }
    public final RunnablePost postAtTime(Runnable r, long uptimeMillis, String title, String subject) {
        return sendMessageAtTime(createRunnablePost(r, title, subject), uptimeMillis);
    }
    public final RunnablePost postAtFrontOfQueue(Runnable r, String title, String subject) {
        return sendMessageAtTime(createRunnablePost(r, title, subject), 0L);
    }
    
    public final RunnablePost postThrowable(ThrowsRunnable r, String title, String subject) {
    	return sendMessageDelayed(createThrowsRunnablePost(r, title, subject), 0L);
    }
    public final RunnablePost postThrowableDelayed(ThrowsRunnable r, long delayMillis, String title, String subject) {
        return sendMessageDelayed(createThrowsRunnablePost(r, title, subject), delayMillis);
    }
    public final RunnablePost postThrowableAtTime(ThrowsRunnable r, long uptimeMillis, String title, String subject) {
        return sendMessageAtTime(createThrowsRunnablePost(r, title, subject), uptimeMillis);
    }
    public final RunnablePost postThrowableAtFrontOfQueue(ThrowsRunnable r, String title, String subject) {
        return sendMessageAtTime(createThrowsRunnablePost(r, title, subject), 0L);
    }
    
    public final RunnablePost post(Runnable r) {
    	return post(r, null, null);
    }
    public final RunnablePost postDelayed(Runnable r, long delayMillis) {
        return postDelayed(r, delayMillis, null, null);
    }
    public final RunnablePost postAtTime(Runnable r, long uptimeMillis) {
        return postAtTime(r, uptimeMillis, null, null);
    }
    public final RunnablePost postAtFrontOfQueue(Runnable r) {
        return postAtFrontOfQueue(r, null, null);
    }
    
    public final RunnablePost postThrowable(ThrowsRunnable r) {
    	return postThrowable(r, null, null);
    }
    public final RunnablePost postThrowableDelayed(ThrowsRunnable r, long delayMillis) {
        return postThrowableDelayed(r, delayMillis, null, null);
    }
    public final RunnablePost postThrowableAtTime(ThrowsRunnable r, long uptimeMillis) {
        return postThrowableAtTime(r, uptimeMillis, null, null);
    }
    public final RunnablePost postThrowableAtFrontOfQueue(ThrowsRunnable r) {
        return postThrowableAtFrontOfQueue(r, null, null);
    }
    
    public final long removePost(RunnablePost runnable) {
    	return queue.remove(runnable);
    }
    public final boolean hasPost(RunnablePost runnable) {
        return queue.contains(runnable);
    }
    public final boolean hasPostReturnAt(long returnAt) {
        return queue.containsReturnAt(returnAt);
    }
    public final Looper getLooper() {
        return looper;
    }
    
    private final RunnablePost sendMessageDelayed(RunnablePost runnable, long delayMillis) {
        if (delayMillis < 0) delayMillis = 0;
        return sendMessageAtTime(runnable, System.currentTimeMillis() + delayMillis);
    }
    private final RunnablePost sendMessageAtTime(RunnablePost runnable, long uptimeMillis) {
        if (queue == null) throw new IllegalStateException(this + " sendMessageAtTime() called with no queue");
        queue.add(runnable, uptimeMillis);
        return runnable; 
    }
    
    private RunnablePost createRunnablePost(Runnable runnable, String title, String subject) {
    	return createThrowsRunnablePost(RunnableUtils.convertToThrowsRunnable(runnable), title, subject);
    }
    private RunnablePost createThrowsRunnablePost(ThrowsRunnable runnable, String title, String subject) {
    	return new RunnablePost(title, subject) {
			@Override public void work() throws Exception { runnable.run(); }
			@Override public void stop() throws Exception { looper.getThread().interrupt(); }
		};
    }
    
    @Override
    public String toString() {
        return "Handler (" + getClass().getName() + ") {" + Integer.toHexString(System.identityHashCode(this)) + "}";
    }
}
