package io.github.NadhifRadityo.Objects.$Object.Thread;

public interface ThreadProgressHandler {
	void logProgress(int current, int total, String desc, RunnablePost job, Looper looper);
	default void catchException(Throwable e, RunnablePost job, Looper looper) { }
	default void update() { }
}
