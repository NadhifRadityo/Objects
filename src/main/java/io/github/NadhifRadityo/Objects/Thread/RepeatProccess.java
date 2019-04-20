package io.github.NadhifRadityo.Objects.Thread;

public abstract class RepeatProccess extends HandlerThread {
	protected volatile long delay;
	protected volatile boolean run = true;
	
	public RepeatProccess(String name, long delay) {
		super(name);
		setDelay(delay);
		super.getThreadHandler().postThrowable(() -> {
			while (run) { this.work(); Thread.sleep(delay); }
		});
	} public RepeatProccess(long delay) { this("", delay); }

    //Setter
    public synchronized void setDelay(long delay){
        if(delay < 1) throw new IllegalArgumentException("Delay must be above 1!");
        this.delay = delay;
    }
    public synchronized void setRun(boolean run) {
        this.run = run;
        if(run && !isAlive()) start();
    }

    //Getter
    public synchronized long getDelay() {
        return delay;
    }
    public synchronized boolean isRunning() {
        return run;
    }
    
    public abstract void work() throws Exception;
}
