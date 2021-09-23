package io.github.NadhifRadityo.Objects.$Object.Thread;

import io.github.NadhifRadityo.Objects.$Interface.Functional.ReferencedCallback.StringReferencedCallback;
import io.github.NadhifRadityo.Objects.$Utilizations.ExceptionUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class HandlersManager extends Handler {
	protected final Map<Handler, Long> handlers;
	protected final long maxIdleTime;
	protected final AtomicInteger nextId;
	protected int maxSize;
	protected StringReferencedCallback threadName;
	protected volatile boolean isFetching;

	public HandlersManager(long maxIdleTime, int maxSize) {
		this.handlers = new HashMap<>();
		this.maxIdleTime = maxIdleTime;
		this.nextId = new AtomicInteger();
		this.maxSize = maxSize;
		this.threadName = (args) -> "Job Thread #" + ((HandlersManager) args[0]).getNextId();
	}

	public Handler[] getHandlers() { return handlers.keySet().toArray(new Handler[0]); }
	public long getMaxIdleTime() { return maxIdleTime; }
	public int getNextId() { return nextId.incrementAndGet(); }
	public int getMaxSize() { return maxSize; }

	public void setMaxSize(int maxSize) { this.maxSize = maxSize; }
	public void setThreadName(StringReferencedCallback threadName) { this.threadName = threadName; }

	@Override public long removePost(RunnablePost runnable) {
		while(isFetching)
			ExceptionUtils.doSilentThrowsRunnable(false, this::wait);
		try { isFetching = true;
			for(Handler handler : handlers.keySet())
				if(handler.hasPost(runnable)) return handler.removePost(runnable);
			return 0;
		} finally {
			isFetching = false;
			notifyAll();
		}
	}
	@Override public synchronized boolean hasPost(RunnablePost runnable) {
		while(isFetching)
			ExceptionUtils.doSilentThrowsRunnable(false, this::wait);
		try { isFetching = true;
			for(Handler handler : handlers.keySet())
				if(handler.hasPost(runnable)) return true;
			return false;
		} finally {
			isFetching = false;
			notifyAll();
		}
	}
	@Override public boolean hasPostReturnAt(long returnAt) {
		while(isFetching)
			ExceptionUtils.doSilentThrowsRunnable(false, this::wait);
		try { isFetching = true;
			for(Handler handler : handlers.keySet())
				if(handler.hasPostReturnAt(returnAt)) return true;
			return false;
		} finally {
			isFetching = false;
			notifyAll();
		}
	}

	public synchronized void quit() {
		while(isFetching)
			ExceptionUtils.doSilentThrowsRunnable(false, this::wait);
		try { isFetching = true;
			for(Handler handler : handlers.keySet())
				handler.getLooper().quit();
			handlers.clear();
		} finally {
			isFetching = false;
			notifyAll();
		}
	}
	public synchronized void quitSafely() {
		while(isFetching)
			ExceptionUtils.doSilentThrowsRunnable(false, this::wait);
		try { isFetching = true;
			for(Handler handler : handlers.keySet())
				handler.getLooper().quitSafely();
			handlers.clear();
		} finally {
			isFetching = false;
			notifyAll();
		}
	}
	public synchronized void revive() {
		while(isFetching)
			ExceptionUtils.doSilentThrowsRunnable(false, this::wait);
		try { isFetching = true;
			Iterator<Handler> iterator = handlers.keySet().iterator();
			while(iterator.hasNext()) {
				Handler handler = iterator.next();
				if(handler.getLooper().getThread().isAlive())
					continue;
				iterator.remove();
			}
		} finally {
			isFetching = false;
			notifyAll();
		}
	}
	public synchronized void willWait(boolean run) throws Exception {
		while(isFetching)
			ExceptionUtils.doSilentThrowsRunnable(false, this::wait);
		try { isFetching = true;
			Handler currentHandler = null;
			for(Handler handler : handlers.keySet()) {
				if(!handler.getLooper().isCurrentThread()) continue;
				currentHandler = handler; break;
			} if(currentHandler == null) throw new IllegalMonitorStateException("Current thread is not listed");
			for(Map.Entry<RunnablePost, Long> entry : currentHandler.queue.getMap()) {
				Map.Entry<Handler, Long> _entry = fetchHandler(currentHandler);
				Handler handler = _entry != null ? _entry.getKey() : null;
				if(handler != null) handler.sendMessageAtTime(entry.getKey(), entry.getValue());
				else if(!run) throw new IllegalStateException("Cannot fetch Handler");
				entry.getKey().work();
			}
		} finally {
			isFetching = false;
			notifyAll();
		}
	}

	@Override protected RunnablePost sendMessageAtTime(RunnablePost runnable, long uptimeMillis) {
		Map.Entry<Handler, Long> entry = fetchHandler(null);
		if(entry == null) throw new IllegalStateException("Cannot fetch Handler");
		entry.setValue(System.currentTimeMillis());
		return entry.getKey().sendMessageAtTime(new RunnablePost() {
			@Override public void work() throws Exception { try { runnable.work(); } finally { entry.setValue(System.currentTimeMillis()); } }
			@Override public void stop() throws Exception { runnable.stop(); }
		}, uptimeMillis);
	}

	protected synchronized Map.Entry<Handler, Long> fetchHandler(Handler except) {
		while(isFetching)
			ExceptionUtils.doSilentThrowsRunnable(false, this::wait);
		try { isFetching = true; int iterate = 0;
			long now = System.currentTimeMillis();
			while(iterate++ < 100) { // Killer post may have been removed
				boolean mayChanged = false;
				for(Map.Entry<Handler, Long> entry : handlers.entrySet()) {
					Looper looper = entry.getKey().getLooper();
					if((except != null && entry.getKey() == except) || !looper.isIdling() || (maxIdleTime >= 0 &&
							entry.getValue() >= 0 && (mayChanged |= now - entry.getValue() > maxIdleTime))) continue;
					return entry;
				}
				if(initThread() == null) {
					Map.Entry<Handler, Long> choosed = null;
					for(Map.Entry<Handler, Long> entry : handlers.entrySet()) {
						if((except != null && entry.getKey() == except) || (maxIdleTime >= 0 &&
								entry.getValue() >= 0 && (mayChanged |= now - entry.getValue() > maxIdleTime))) continue;
						if(choosed == null || choosed.getKey().queue.size() < choosed.getKey().queue.size()) choosed = entry;
					}
					if(choosed == null) if(mayChanged) continue;
					else return null; return choosed;
				}
			} return null;
		} finally {
			isFetching = false;
			notifyAll();
		}
	}
	protected HandlerThread initThread() {
		if(handlers.size() >= maxSize) return null;
		HandlerThread handlerThread = new HandlerThread(threadName.get(this));
		handlerThread.start(); handlers.put(handlerThread.getThreadHandler(), -1L);
		Map.Entry<Handler, Long> _entry = null;
		for(Map.Entry<Handler, Long> entry : handlers.entrySet())
			if(entry.getKey() == handlerThread.getThreadHandler()) {
				_entry = entry; break; }
		if(_entry == null) throw new IllegalStateException();
		Map.Entry<Handler, Long> entry = _entry;
		if(maxIdleTime >= 0 && entry.getValue() < 0) entry.getKey().postDelayed(new Runnable() { @Override public void run() {
			if(System.currentTimeMillis() - entry.getValue() > maxIdleTime) { killThread(entry.getKey()); return; }
			entry.getKey().postDelayed(this, maxIdleTime);
		} }, maxIdleTime);
		return handlerThread;
	}
	protected void killThread(Handler handler) {
		handlers.remove(handler);
		handler.getLooper().quit();
	}
}
