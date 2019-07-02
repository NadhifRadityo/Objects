package io.github.NadhifRadityo.Objects.Thread;

import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;
import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

public abstract class RepeatProccess extends HandlerThread {
	protected volatile long delay;
	protected volatile boolean run = true;
	protected final ThrowsRunnable runnable;
	protected ExceptionHandler exceptionHandler;
	
	public RepeatProccess(String name, long delay) {
		super(name); setDelay(delay);
		
		this.runnable = () -> {
			while (run) {
				if(getLooper().getExceptionHandler() != null)
					ExceptionUtils.doSilentException(getLooper().getExceptionHandler(), (ThrowsRunnable) this::work);
				else this.work();
				Thread.sleep(delay);
			}
		};
	}

    @Override protected void onLooperPrepared() {
        getThreadHandler().postThrowable(runnable);
        if(exceptionHandler != null) {
            getLooper().setExceptionHandler(exceptionHandler);
            exceptionHandler = null;
        }
    }
    
    public synchronized long getDelay() { return delay; }
    public synchronized boolean isRunning() { return run; }
    public synchronized ExceptionHandler getExceptionHandler() { return looper == null ? exceptionHandler : looper.getExceptionHandler(); }
    
    public synchronized void setDelay(long delay){ if(delay < 1) throw new IllegalArgumentException("Delay must be above 1!"); this.delay = delay; }
    public synchronized void setRun(boolean run) { this.run = run; if(run && !isAlive()) { start(); onLooperPrepared(); } }
    public synchronized void setExceptionHandler(ExceptionHandler exceptionHandler) { if(looper == null) this.exceptionHandler = exceptionHandler; else looper.setExceptionHandler(exceptionHandler); }
    
    public abstract void work() throws Exception;
}
