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
		super(name);
		setDelay(delay);

		this.runnable = () -> {
            while (run) {
                if(getLooper().getExceptionHandler() != null)
                    ExceptionUtils.doSilentException(getLooper().getExceptionHandler(), (ThrowsRunnable) this::work);
                else this.work();
                Thread.sleep(delay);
            }
        };
	}
	public RepeatProccess(long delay) { this("", delay); }

    @Override protected void onLooperPrepared() {
        getThreadHandler().postThrowable(runnable);
        if(exceptionHandler != null) {
            getLooper().setExceptionHandler(exceptionHandler);
            exceptionHandler = null;
        }
    }

    //Setter
    public synchronized void setDelay(long delay){
        if(delay < 1) throw new IllegalArgumentException("Delay must be above 1!");
        this.delay = delay;
    }
    public synchronized void setRun(boolean run) {
        this.run = run;
        if(run && !isAlive()) { start(); onLooperPrepared(); }
    }
    public synchronized void setExceptionHandler(ExceptionHandler exceptionHandler) {
	    if(mLooper == null) this.exceptionHandler = exceptionHandler;
        else mLooper.setExceptionHandler(exceptionHandler);
    }

    //Getter
    public synchronized long getDelay() {
        return delay;
    }
    public synchronized boolean isRunning() {
        return run;
    }
    public synchronized ExceptionHandler getExceptionHandler() {
        return mLooper == null ? exceptionHandler : mLooper.getExceptionHandler();
    }
    
    public abstract void work() throws Exception;
}
