package io.github.NadhifRadityo.Objects.Thread;

import io.github.NadhifRadityo.Objects.List.QueueList;

public class PostQueue extends QueueList<RunnablePost> {
	public PostQueue(boolean quitAllowed) { super(quitAllowed); }
	
	public long getTimeRunnableRun(RunnablePost runnable) { return map.get(runnable); }
}
