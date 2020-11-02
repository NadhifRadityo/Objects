package io.github.NadhifRadityo.Objects.Pool.Impl;

import org.apache.commons.pool2.TrackedUse;
import org.apache.commons.pool2.impl.CallStack;
import org.apache.commons.pool2.impl.CallStackUtils;
import org.apache.commons.pool2.impl.NoOpCallStack;

import java.io.PrintWriter;
import java.util.Deque;

@SuppressWarnings("jol")
public class BasePooledObject<T, SELF extends BasePooledObject<T, SELF>> {
	protected final T object;
	protected PooledObjectState state;
	protected final long createTime;
	protected volatile CallStack borrowedBy;
	protected volatile CallStack usedBy;
	protected volatile long lastBorrowTime;
	protected volatile long lastUseTime;
	protected volatile long lastReturnTime;
	protected volatile long borrowedCount;
	protected volatile boolean logAbandoned;

	public BasePooledObject(T object) {
		this.object = object;
		this.state = PooledObjectState.IDLE;
		this.createTime = System.currentTimeMillis();
		this.borrowedBy = NoOpCallStack.INSTANCE;
		this.usedBy = NoOpCallStack.INSTANCE;
		this.lastBorrowTime = createTime;
		this.lastUseTime = createTime;
		this.lastReturnTime = createTime;
	}

	public T getObject() { return object; }
	public long getCreateTime() { return createTime; }
	public long getActiveTimeMillis() {
		long returnTime = lastReturnTime;
		long borrowTime = lastBorrowTime;
		return returnTime > borrowTime ? returnTime - borrowTime : System.currentTimeMillis() - borrowTime;
	}
	public long getIdleTimeMillis() {
		long elapsed;
		return (elapsed = System.currentTimeMillis() - lastReturnTime) >= 0 ? elapsed : 0;
	}
	public long getLastBorrowTime() { return lastBorrowTime; }
	public long getLastReturnTime() { return lastReturnTime; }
	public long getBorrowedCount() { return borrowedCount; }
	public long getLastUsedTime() { return object instanceof TrackedUse ? Math.max(((TrackedUse) object).getLastUsed(), lastUseTime) : lastUseTime; }
	public int compareTo(org.apache.commons.pool2.PooledObject<T> other) {
		long lastActiveDiff = this.getLastReturnTime() - other.getLastReturnTime();
		if(lastActiveDiff == 0) return System.identityHashCode(this) - System.identityHashCode(other);
		return (int) Math.min(Math.max(lastActiveDiff, Integer.MIN_VALUE), Integer.MAX_VALUE);
	}

	public synchronized boolean startEvictionTest() {
		if(state == PooledObjectState.IDLE) { state = PooledObjectState.EVICTION; return true; } return false;
	}
	public synchronized boolean endEvictionTest(Deque<SELF> idleQueue) {
		if(state == PooledObjectState.EVICTION) { state = PooledObjectState.IDLE; return true; }
		if(state == PooledObjectState.EVICTION_RETURN_TO_HEAD) { state = PooledObjectState.IDLE; idleQueue.offerFirst((SELF) this); }
		return false;
	}

	public synchronized boolean allocate() {
		if(state == PooledObjectState.EVICTION) { state = PooledObjectState.EVICTION_RETURN_TO_HEAD; return false; }
		if(state != PooledObjectState.IDLE) return false;
		state = PooledObjectState.ALLOCATED;
		lastBorrowTime = System.currentTimeMillis();
		lastUseTime = lastBorrowTime;
		borrowedCount++;
		if(logAbandoned) borrowedBy.fillInStackTrace();
		return true;
	}
	public synchronized boolean deallocate() {
		if(state != PooledObjectState.ALLOCATED && state != PooledObjectState.RETURNING)
			return false;
		state = PooledObjectState.IDLE;
		lastReturnTime = System.currentTimeMillis();
		borrowedBy.clear();
		return true;
	}

	public synchronized void invalidate() { state = PooledObjectState.INVALID; }
	public void use() { lastUseTime = System.currentTimeMillis(); usedBy.fillInStackTrace(); }
	public void printStackTrace(PrintWriter writer) {
		boolean written = borrowedBy.printStackTrace(writer);
		written |= usedBy.printStackTrace(writer);
		if(written) writer.flush();
	}

	public synchronized PooledObjectState getState() { return state; }
	public synchronized void markAbandoned() { state = PooledObjectState.ABANDONED; }
	public synchronized void markReturning() { state = PooledObjectState.RETURNING; }
	public void setLogAbandoned(final boolean logAbandoned) { this.logAbandoned = logAbandoned; }
	public void setRequireFullStackTrace(final boolean requireFullStackTrace) {
		borrowedBy = CallStackUtils.newCallStack("'Pooled object created' " +
						"yyyy-MM-dd HH:mm:ss Z 'by the following code has not been returned to the pool:'",
				true, requireFullStackTrace);
		usedBy = CallStackUtils.newCallStack("The last code to use this object was:",
				false, requireFullStackTrace);
	}

	public enum PooledObjectState {
		IDLE,
		ALLOCATED,
		EVICTION,
		EVICTION_RETURN_TO_HEAD,
		VALIDATION,
		VALIDATION_PREALLOCATED,
		VALIDATION_RETURN_TO_HEAD,
		INVALID,
		ABANDONED,
		RETURNING
	}
}
