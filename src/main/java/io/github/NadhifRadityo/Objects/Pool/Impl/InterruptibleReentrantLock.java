package io.github.NadhifRadityo.Objects.Pool.Impl;

import java.util.Collection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptibleReentrantLock extends ReentrantLock {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a new InterruptibleReentrantLock with the given fairness policy.
	 *
	 * @param fairness true means threads should acquire contended locks as if
	 * waiting in a FIFO queue
	 */
	public InterruptibleReentrantLock(final boolean fairness) {
		super(fairness);
	}

	/**
	 * Interrupt the threads that are waiting on a specific condition
	 *
	 * @param condition the condition on which the threads are waiting.
	 */
	public void interruptWaiters(final Condition condition) {
		final Collection<Thread> threads = getWaitingThreads(condition);
		for (final Thread thread : threads) {
			thread.interrupt();
		}
	}
}
