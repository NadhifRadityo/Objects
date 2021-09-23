package io.github.NadhifRadityo.Objects.$Object.Thread;

import io.github.NadhifRadityo.Objects.$Object.List.OptimizedQueueList;

public class PostQueue extends OptimizedQueueList<RunnablePost> {
	public PostQueue(boolean quitAllowed) { super(quitAllowed); }
	
	public long getTimeRunnableRun(RunnablePost runnable) { return map.get(runnable); }
}
