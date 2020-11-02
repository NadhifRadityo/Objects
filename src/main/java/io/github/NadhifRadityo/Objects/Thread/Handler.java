package io.github.NadhifRadityo.Objects.Thread;

import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.Utilizations.RunnableUtils;

public class Handler {
	private static Handler MAIN_THREAD_HANDLER = null;
	protected final Looper looper;
	protected final PostQueue queue;

	public Handler(Looper looper) {
		this.looper = looper;
		this.queue = looper.myQueue();
		if(looper.getThread() instanceof HandlerThread) {
			HandlerThread handlerThread = (HandlerThread) looper.getThread();
			if(handlerThread.handler != null && !equals(handlerThread.handler))
				System.out.println("Multiple handler detected!");
			else handlerThread.handler = this;
		}
	}
	protected Handler() {
		this.looper = null;
		this.queue = null;
	}

	public static Handler getMain() {
		if (MAIN_THREAD_HANDLER == null && Looper.getMainLooper() != null) {
			if(Looper.getMainLooper().getThread() instanceof HandlerThread) {
				HandlerThread handlerThread = (HandlerThread) Looper.getMainLooper().getThread();
				if(handlerThread.handler != null) MAIN_THREAD_HANDLER = handlerThread.handler;
			} if(MAIN_THREAD_HANDLER == null) MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());
		} return MAIN_THREAD_HANDLER;
	}

	public RunnablePost post(Runnable r, String title, String subject) { return sendMessageDelayed(createRunnablePost(r, title, subject), 0L); }
	public RunnablePost postDelayed(Runnable r, long delayMillis, String title, String subject) { return sendMessageDelayed(createRunnablePost(r, title, subject), delayMillis); }
	public RunnablePost postAtTime(Runnable r, long uptimeMillis, String title, String subject) { return sendMessageAtTime(createRunnablePost(r, title, subject), uptimeMillis); }
	public RunnablePost postAtFrontOfQueue(Runnable r, String title, String subject) { return sendMessageAtTime(createRunnablePost(r, title, subject), 0L); }

	public RunnablePost post(Runnable r, long period, String title, String subject) { return sendMessageDelayed(createRunnablePost(r, period, title, subject), 0L); }
	public RunnablePost postDelayed(Runnable r, long delayMillis, long period, String title, String subject) { return sendMessageDelayed(createRunnablePost(r, period, title, subject), delayMillis); }
	public RunnablePost postAtTime(Runnable r, long uptimeMillis, long period, String title, String subject) { return sendMessageAtTime(createRunnablePost(r, period, title, subject), uptimeMillis); }
	public RunnablePost postAtFrontOfQueue(Runnable r, long period, String title, String subject) { return sendMessageAtTime(createRunnablePost(r, period, title, subject), 0L); }

	public RunnablePost postFixedRate(Runnable r, long period, String title, String subject) { return sendMessageDelayed(createRunnablePost(r, true, period, title, subject), 0L); }
	public RunnablePost postFixedRateDelayed(Runnable r, long delayMillis, long period, String title, String subject) { return sendMessageDelayed(createRunnablePost(r, true, period, title, subject), delayMillis); }
	public RunnablePost postFixedRateAtTime(Runnable r, long uptimeMillis, long period, String title, String subject) { return sendMessageAtTime(createRunnablePost(r, true, period, title, subject), uptimeMillis); }
	public RunnablePost postFixedRateAtFrontOfQueue(Runnable r, long period, String title, String subject) { return sendMessageAtTime(createRunnablePost(r, true, period, title, subject), 0L); }

	public RunnablePost postThrowable(ThrowsRunnable r, String title, String subject) { return sendMessageDelayed(createThrowsRunnablePost(r, title, subject), 0L); }
	public RunnablePost postThrowableDelayed(ThrowsRunnable r, long delayMillis, String title, String subject) { return sendMessageDelayed(createThrowsRunnablePost(r, title, subject), delayMillis); }
	public RunnablePost postThrowableAtTime(ThrowsRunnable r, long uptimeMillis, String title, String subject) { return sendMessageAtTime(createThrowsRunnablePost(r, title, subject), uptimeMillis); }
	public RunnablePost postThrowableAtFrontOfQueue(ThrowsRunnable r, String title, String subject) { return sendMessageAtTime(createThrowsRunnablePost(r, title, subject), 0L); }

	public RunnablePost postThrowable(ThrowsRunnable r, long period, String title, String subject) { return sendMessageDelayed(createThrowsRunnablePost(r, period, title, subject), 0L); }
	public RunnablePost postThrowableDelayed(ThrowsRunnable r, long delayMillis, long period, String title, String subject) { return sendMessageDelayed(createThrowsRunnablePost(r, period, title, subject), delayMillis); }
	public RunnablePost postThrowableAtTime(ThrowsRunnable r, long uptimeMillis, long period, String title, String subject) { return sendMessageAtTime(createThrowsRunnablePost(r, period, title, subject), uptimeMillis); }
	public RunnablePost postThrowableAtFrontOfQueue(ThrowsRunnable r, long period, String title, String subject) { return sendMessageAtTime(createThrowsRunnablePost(r, period, title, subject), 0L); }

	public RunnablePost postThrowableFixedRate(ThrowsRunnable r, long period, String title, String subject) { return sendMessageDelayed(createThrowsRunnablePost(r, true, period, title, subject), 0L); }
	public RunnablePost postThrowableFixedRateDelayed(ThrowsRunnable r, long delayMillis, long period, String title, String subject) { return sendMessageDelayed(createThrowsRunnablePost(r, true, period, title, subject), delayMillis); }
	public RunnablePost postThrowableFixedRateAtTime(ThrowsRunnable r, long uptimeMillis, long period, String title, String subject) { return sendMessageAtTime(createThrowsRunnablePost(r, true, period, title, subject), uptimeMillis); }
	public RunnablePost postThrowableFixedRateAtFrontOfQueue(ThrowsRunnable r, long period, String title, String subject) { return sendMessageAtTime(createThrowsRunnablePost(r, true, period, title, subject), 0L); }

	public RunnablePost post(Runnable r) { return post(r, null, null); }
	public RunnablePost postDelayed(Runnable r, long delayMillis) { return postDelayed(r, delayMillis, null, null); }
	public RunnablePost postAtTime(Runnable r, long uptimeMillis) { return postAtTime(r, uptimeMillis, null, null); }
	public RunnablePost postAtFrontOfQueue(Runnable r) { return postAtFrontOfQueue(r, null, null); }

	public RunnablePost post(Runnable r, long period) { return post(r, period, null, null); }
	public RunnablePost postDelayed(Runnable r, long delayMillis, long period) { return postDelayed(r, period, delayMillis, null, null); }
	public RunnablePost postAtTime(Runnable r, long uptimeMillis, long period) { return postAtTime(r, period, uptimeMillis, null, null); }
	public RunnablePost postAtFrontOfQueue(Runnable r, long period) { return postAtFrontOfQueue(r, period, null, null); }

	public RunnablePost postFixedRate(Runnable r, long period) { return postFixedRate(r, period, null, null); }
	public RunnablePost postFixedRateDelayed(Runnable r, long delayMillis, long period) { return postFixedRateDelayed(r, period, delayMillis, null, null); }
	public RunnablePost postFixedRateAtTime(Runnable r, long uptimeMillis, long period) { return postFixedRateAtTime(r, period, uptimeMillis, null, null); }
	public RunnablePost postFixedRateAtFrontOfQueue(Runnable r, long period) { return postFixedRateAtFrontOfQueue(r, period, null, null); }

	public RunnablePost postThrowable(ThrowsRunnable r) { return postThrowable(r, null, null); }
	public RunnablePost postThrowableDelayed(ThrowsRunnable r, long delayMillis) { return postThrowableDelayed(r, delayMillis, null, null); }
	public RunnablePost postThrowableAtTime(ThrowsRunnable r, long uptimeMillis) { return postThrowableAtTime(r, uptimeMillis, null, null); }
	public RunnablePost postThrowableAtFrontOfQueue(ThrowsRunnable r) { return postThrowableAtFrontOfQueue(r, null, null); }

	public RunnablePost postThrowable(ThrowsRunnable r, long period) { return postThrowable(r, period, null, null); }
	public RunnablePost postThrowableDelayed(ThrowsRunnable r, long delayMillis, long period) { return postThrowableDelayed(r, period, delayMillis, null, null); }
	public RunnablePost postThrowableAtTime(ThrowsRunnable r, long uptimeMillis, long period) { return postThrowableAtTime(r, period, uptimeMillis, null, null); }
	public RunnablePost postThrowableAtFrontOfQueue(ThrowsRunnable r, long period) { return postThrowableAtFrontOfQueue(r, period, null, null); }

	public RunnablePost postThrowableFixedRate(ThrowsRunnable r, long period) { return postThrowableFixedRate(r, period, null, null); }
	public RunnablePost postThrowableFixedRateDelayed(ThrowsRunnable r, long delayMillis, long period) { return postThrowableFixedRateDelayed(r, period, delayMillis, null, null); }
	public RunnablePost postThrowableFixedRateAtTime(ThrowsRunnable r, long uptimeMillis, long period) { return postThrowableFixedRateAtTime(r, period, uptimeMillis, null, null); }
	public RunnablePost postThrowableFixedRateAtFrontOfQueue(ThrowsRunnable r, long period) { return postThrowableFixedRateAtFrontOfQueue(r, period, null, null); }

	public long removePost(RunnablePost runnable) { if(runnable == null) return 0L; runnable.setCancelled(); Long result = queue.remove(runnable); return result == null ? 0L : result; }
	public boolean hasPost(RunnablePost runnable) { return queue.contains(runnable); }
	public boolean hasPostReturnAt(long returnAt) { return queue.containsReturnAt(returnAt); }
	public final Looper getLooper() { return looper; }

	protected RunnablePost sendMessageDelayed(RunnablePost runnable, long delayMillis) {
		if (delayMillis < 0) delayMillis = 0;
		return sendMessageAtTime(runnable, System.currentTimeMillis() + delayMillis);
	}
	protected RunnablePost sendMessageAtTime(RunnablePost runnable, long uptimeMillis) {
		if(queue == null) throw new IllegalStateException(this + " sendMessageAtTime() called with no queue");
		queue.add(runnable, uptimeMillis);
		looper.updateProgress();
		return runnable;
	}

	protected RunnablePost createRunnablePost(Runnable runnable, String title, String subject) { return createRunnablePost(runnable, -1, title, subject); }
	protected RunnablePost createThrowsRunnablePost(ThrowsRunnable runnable, String title, String subject) { return createThrowsRunnablePost(runnable, -1, title, subject); }
	protected RunnablePost createRunnablePost(Runnable runnable, long period, String title, String subject) { return createRunnablePost(runnable, false, period, title, subject); }
	protected RunnablePost createThrowsRunnablePost(ThrowsRunnable runnable, long period, String title, String subject) { return createThrowsRunnablePost(runnable, false, period, title, subject); }
	protected RunnablePost createRunnablePost(Runnable runnable, boolean fixedRate, long period, String title, String subject) { return createThrowsRunnablePost(RunnableUtils.convertToThrowsRunnable(runnable), fixedRate, period, title, subject); }
	protected RunnablePost createThrowsRunnablePost(ThrowsRunnable runnable, boolean fixedRate, long period, String title, String subject) {
		if(title == null && subject == null)
			return new RunnablePost(fixedRate, period) {
				@Override public void work() throws Exception { Handler.this.work(this, runnable); }
				@Override public void stop() throws Exception { Handler.this.stop(this, runnable); }
			};
		return new RunnablePost.IdentifiedRunnablePost(fixedRate, period, title, subject) {
			@Override public void work() throws Exception { Handler.this.work(this, runnable); }
			@Override public void stop() throws Exception { Handler.this.stop(this, runnable); }
		};
	}

	protected void work(RunnablePost post, ThrowsRunnable runnable) throws Exception {
		long start = System.currentTimeMillis(); try { runnable.run(); } finally { boolean isCancelled = post.isCancelled(); boolean fixedRate = post.getFixedRate(); long period = post.getPeriod();
		if(period >= 0 && !isCancelled) { sendMessageDelayed(post, fixedRate ? Math.max(0, period - (System.currentTimeMillis() - start)) : period); } else post.done = true; synchronized(post) { post.notifyAll(); } }
	}
	protected void stop(RunnablePost post, ThrowsRunnable runnable) throws Exception {
		try { looper.getThread().interrupt(); } finally { post.cancelled = true; synchronized(post) { post.notifyAll(); } }
	}
}
