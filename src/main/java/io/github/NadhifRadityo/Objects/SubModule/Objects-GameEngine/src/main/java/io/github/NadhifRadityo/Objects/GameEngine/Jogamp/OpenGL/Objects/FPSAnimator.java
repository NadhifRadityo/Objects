package io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.Objects;

/*
 * Copyright (c) 2003 Sun Microsystems, Inc. All Rights Reserved.
 * Copyright (c) 2010 JogAmp Community. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed or intended for use
 * in the design, construction, operation or maintenance of any nuclear
 * facility.
 *
 * Sun gratefully acknowledges that this software was originally authored
 * and developed by Kenneth Bradley Russell and Christopher John Kline.
 */

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLException;

import com.jogamp.common.ExceptionUtils;
import com.jogamp.opengl.util.AnimatorBase;
import io.github.NadhifRadityo.Objects.Thread.Handler;
import io.github.NadhifRadityo.Objects.Thread.RunnablePost;

/**
 * An Animator subclass which attempts to achieve a target
 * frames-per-second rate to avoid using all CPU time. The target FPS
 * is only an estimate and is not guaranteed.
 * <p>
 * The Animator execution thread does not run as a daemon thread,
 * so it is able to keep an application from terminating.<br>
 * Call {@link #stop() } to terminate the animation and it's execution thread.
 * </p>
 */
public class FPSAnimator extends AnimatorBase {
	private Handler handler = null;
	private MainTask task = null;
	private int fps;
	private final boolean scheduleAtFixedRate;
	private boolean isAnimating;          // MainTask feedback
	private volatile boolean pauseIssued; // MainTask trigger
	private volatile boolean stopIssued;  // MainTask trigger

	@Override
	protected String getBaseName(final String prefix) {
		return "FPS" + prefix + "Animator" ;
	}

	/** Creates an FPSAnimator with a given target frames-per-second
	 value. Equivalent to <code>FPSAnimator(null, null, fps)</code>. */
	public FPSAnimator(final int fps) {
		this(null, null, fps);
	}

	/** Creates an FPSAnimator with a given target frames-per-second
	 value and a drawing thread. Equivalent to <code>FPSAnimator(hanlder, null, fps)</code>. */
	public FPSAnimator(final Handler handler, final int fps) {
		this(handler, null, fps);
	}

	/** Creates an FPSAnimator with a given target frames-per-second
	 value and a flag indicating whether to use fixed-rate
	 scheduling. Equivalent to <code>FPSAnimator(null, null, fps,
	 scheduleAtFixedRate)</code>. */
	public FPSAnimator(final int fps, final boolean scheduleAtFixedRate) {
		this(null, null, fps, scheduleAtFixedRate);
	}

	/** Creates an FPSAnimator with a given target frames-per-second
	 value, a drawing thread, and a flag indicating whether to use fixed-rate
	 scheduling. Equivalent to <code>FPSAnimator(handler, null, fps,
	 scheduleAtFixedRate)</code>. */
	public FPSAnimator(final Handler handler, final int fps, final boolean scheduleAtFixedRate) {
		this(handler, null, fps, scheduleAtFixedRate);
	}

	/** Creates an FPSAnimator with a given target frames-per-second
	 value and an initial drawable to animate. Equivalent to
	 <code>FPSAnimator(null, drawable, fps, false)</code>. */
	public FPSAnimator(final GLAutoDrawable drawable, final int fps) {
		this(null, drawable, fps, false);
	}

	/** Creates an FPSAnimator with a given target frames-per-second
	 value, a drawing thread, and an initial drawable to animate. Equivalent to
	 <code>FPSAnimator(handler, drawable, fps, false)</code>. */
	public FPSAnimator(final Handler handler, final GLAutoDrawable drawable, final int fps) {
		this(handler, drawable, fps, false);
	}

	/** Creates an FPSAnimator with a given target frames-per-second
	 value, an initial drawable to animate, and a flag indicating
	 whether to use fixed-rate scheduling. Equivalent to
	 <code>FPSAnimator(null, drawable, fps, scheduleAtFixedRate)</code>. */
	public FPSAnimator(final GLAutoDrawable drawable, final int fps, final boolean scheduleAtFixedRate) {
		this(null, drawable, fps, scheduleAtFixedRate);
	}

	/** Creates an FPSAnimator with a given target frames-per-second
	 value, a drawing thread, an initial drawable to animate, and a flag indicating
	 whether to use fixed-rate scheduling. */
	public FPSAnimator(final Handler handler, final GLAutoDrawable drawable, final int fps, final boolean scheduleAtFixedRate) {
		super();
		this.handler = handler;
		this.fps = fps;
		if (drawable != null) {
			add(drawable);
		}
		this.scheduleAtFixedRate = scheduleAtFixedRate;
	}

	/**
	 * @param fps
	 * @throws GLException if the animator has already been started
	 */
	public final void setFPS(final int fps) throws GLException {
		if ( isStarted() ) {
			throw new GLException("Animator already started.");
		}
		this.fps = fps;
	}
	public final int getFPS() { return fps; }

	class MainTask implements Runnable {
		private boolean justStarted;
		private boolean alreadyStopped;
		private boolean alreadyPaused;
		private RunnablePost post;

		public MainTask() {
		}

		public void start(final Handler handler) {
			fpsCounter.resetFPSCounter();
			pauseIssued = false;
			stopIssued = false;
			isAnimating = false;

			justStarted = true;
			alreadyStopped = false;
			alreadyPaused = false;

			long period = fps > 0 ? (long) (1000.0f / fps) : 0; // 0 -> 1: IllegalArgumentException: Non-positive period
			post = scheduleAtFixedRate ? handler.postFixedRate(this, period) : handler.post(this, period);
		}

		public boolean isActive() { return !alreadyStopped && !alreadyPaused; }

		@Override
		public final String toString() {
			return "Task[thread "+animThread+", stopped "+alreadyStopped+", paused "+alreadyPaused+" pauseIssued "+pauseIssued+", stopIssued "+stopIssued+" -- started "+isStarted()+", animating "+isAnimatingImpl()+", paused "+isPaused()+", drawable "+drawables.size()+", drawablesEmpty "+drawablesEmpty+"]";
		}

		@Override
		public void run() {
			UncaughtAnimatorException caughtException = null;

			if( justStarted ) {
				justStarted = false;
				synchronized (FPSAnimator.this) {
					animThread = Thread.currentThread();
					if(DEBUG) {
						System.err.println("FPSAnimator start/resume:" + Thread.currentThread() + ": " + toString());
					}
					isAnimating = true;
					if( drawablesEmpty ) {
						pauseIssued = true; // isAnimating:=false @ pause below
					} else {
						pauseIssued = false;
						setDrawablesExclCtxState(exclusiveContext); // may re-enable exclusive context
					}
					FPSAnimator.this.notifyAll(); // Wakes up 'waitForStartedCondition' sync -and resume from pause or drawablesEmpty
					if(DEBUG) {
						System.err.println("FPSAnimator P1:" + Thread.currentThread() + ": " + toString());
					}
				}
			}
			if( !pauseIssued && !stopIssued ) { // RUN
				try {
					display();
				} catch (final UncaughtAnimatorException dre) {
					caughtException = dre;
					stopIssued = true;
				}
			} else if( pauseIssued && !stopIssued ) { // PAUSE
				if(DEBUG) {
					System.err.println("FPSAnimator pausing: "+alreadyPaused+", "+ Thread.currentThread() + ": " + toString());
				}
				handler.removePost(post);

				if( !alreadyPaused ) { // PAUSE
					alreadyPaused = true;
					if( exclusiveContext && !drawablesEmpty ) {
						setDrawablesExclCtxState(false);
						try {
							display(); // propagate exclusive context -> off!
						} catch (final UncaughtAnimatorException dre) {
							caughtException = dre;
							stopIssued = true;
						}
					}
					if( null == caughtException ) {
						synchronized (FPSAnimator.this) {
							if(DEBUG) {
								System.err.println("FPSAnimator pause " + Thread.currentThread() + ": " + toString());
							}
							isAnimating = false;
							FPSAnimator.this.notifyAll();
						}
					}
				}
			}
			if( stopIssued ) { // STOP incl. immediate exception handling of 'displayCaught'
				if(DEBUG) {
					System.err.println("FPSAnimator stopping: "+alreadyStopped+", "+ Thread.currentThread() + ": " + toString());
				}
				handler.removePost(post);

				if( !alreadyStopped ) {
					alreadyStopped = true;
					if( exclusiveContext && !drawablesEmpty ) {
						setDrawablesExclCtxState(false);
						try {
							display(); // propagate exclusive context -> off!
						} catch (final UncaughtAnimatorException dre) {
							if( null == caughtException ) {
								caughtException = dre;
							} else {
								System.err.println("FPSAnimator.setExclusiveContextThread: caught: "+dre.getMessage());
								dre.printStackTrace();
							}
						}
					}
					boolean flushGLRunnables = false;
					boolean throwCaughtException = false;
					synchronized (FPSAnimator.this) {
						if(DEBUG) {
							System.err.println("FPSAnimator stop " + Thread.currentThread() + ": " + toString());
							if( null != caughtException ) {
								System.err.println("Animator caught: "+caughtException.getMessage());
								caughtException.printStackTrace();
							}
						}
						isAnimating = false;
						if( null != caughtException ) {
							flushGLRunnables = true;
							throwCaughtException = !handleUncaughtException(caughtException);
						}
						animThread = null;
						FPSAnimator.this.notifyAll();
					}
					if( flushGLRunnables ) {
						flushGLRunnables();
					}
					if( throwCaughtException ) {
						throw caughtException;
					}
				}
			}
		}
	}
	private final boolean isAnimatingImpl() {
		return animThread != null && isAnimating ;
	}
	@Override
	public final synchronized boolean isAnimating() {
		return animThread != null && isAnimating ;
	}

	@Override
	public final synchronized boolean isPaused() {
		return animThread != null && pauseIssued;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public Handler getHandler() {
		return handler;
	}

	@Override
	public final synchronized boolean start() {
		if(handler == null) throw new IllegalArgumentException("Handler is null!");
		if ( null != task || isStarted() ) {
			return false;
		}
		task = new MainTask();
		if(DEBUG) {
			System.err.println("FPSAnimator.start() START: "+task+", "+ Thread.currentThread() + ": " + toString());
		}
		task.start(handler);

		final boolean res = finishLifecycleAction( drawablesEmpty ? waitForStartedEmptyCondition : waitForStartedAddedCondition,
				POLLP_WAIT_FOR_FINISH_LIFECYCLE_ACTION);
		if(DEBUG) {
			System.err.println("FPSAnimator.start() END: "+task+", "+ Thread.currentThread() + ": " + toString());
		}
		if( drawablesEmpty ) {
			handler.removePost(task.post);
			task = null;
		}
		return res;
	}
	private final Condition waitForStartedAddedCondition = new Condition() {
		@Override
		public boolean eval() {
			return !isStarted() || !isAnimating ;
		} };
	private final Condition waitForStartedEmptyCondition = new Condition() {
		@Override
		public boolean eval() {
			return !isStarted() || isAnimating ;
		} };

	/** Stops this FPSAnimator. Due to the implementation of the
	 FPSAnimator it is not guaranteed that the FPSAnimator will be
	 completely stopped by the time this method returns. */
	@Override
	public final synchronized boolean stop() {
		if ( null == handler || !isStarted() ) {
			return false;
		}
		if(DEBUG) {
			System.err.println("FPSAnimator.stop() START: "+task+", "+ Thread.currentThread() + ": " + toString());
		}
		final boolean res;
		if( null == task ) {
			// start/resume case w/ drawablesEmpty
			res = true;
		} else {
			stopIssued = true;
			res = finishLifecycleAction(waitForStoppedCondition, POLLP_WAIT_FOR_FINISH_LIFECYCLE_ACTION);
		}

		if(DEBUG) {
			System.err.println("FPSAnimator.stop() END: "+task+", "+ Thread.currentThread() + ": " + toString());
		}
		if(null != task) {
			handler.removePost(task.post);
			task = null;
		}
		animThread = null;
		return res;
	}
	private final Condition waitForStoppedCondition = new Condition() {
		@Override
		public boolean eval() {
			return isStarted();
		} };

	@Override
	public final synchronized boolean pause() {
		if ( !isStarted() || pauseIssued ) {
			return false;
		}
		if(DEBUG) {
			System.err.println("FPSAnimator.pause() START: "+task+", "+ Thread.currentThread() + ": " + toString());
		}
		final boolean res;
		if( null == task ) {
			// start/resume case w/ drawablesEmpty
			res = true;
		} else {
			pauseIssued = true;
			res = finishLifecycleAction(waitForPausedCondition, POLLP_WAIT_FOR_FINISH_LIFECYCLE_ACTION);
		}

		if(DEBUG) {
			System.err.println("FPSAnimator.pause() END: "+task+", "+ Thread.currentThread() + ": " + toString());
		}
		if(null != task) {
			handler.removePost(task.post);
			task = null;
		}
		return res;
	}
	private final Condition waitForPausedCondition = new Condition() {
		@Override
		public boolean eval() {
			// end waiting if stopped as well
			return isStarted() && isAnimating;
		} };

	@Override
	public final synchronized boolean resume() {
		if ( !isStarted() || !pauseIssued ) {
			return false;
		}
		if(DEBUG) {
			System.err.println("FPSAnimator.resume() START: "+ Thread.currentThread() + ": " + toString());
		}
		final boolean res;
		if( drawablesEmpty ) {
			res = true;
		} else {
			if( null != task ) {
				if( DEBUG ) {
					System.err.println("FPSAnimator.resume() Ops: !pauseIssued, but task != null: "+toString());
					ExceptionUtils.dumpStack(System.err);
				}
				handler.removePost(task.post);
				task = null;
			}
			task = new MainTask();
			task.start(handler);
			res = finishLifecycleAction(waitForResumeCondition, POLLP_WAIT_FOR_FINISH_LIFECYCLE_ACTION);
		}
		if(DEBUG) {
			System.err.println("FPSAnimator.resume() END: "+task+", "+ Thread.currentThread() + ": " + toString());
		}
		return res;
	}
	private final Condition waitForResumeCondition = new Condition() {
		@Override
		public boolean eval() {
			// end waiting if stopped as well
			return !drawablesEmpty && !isAnimating && isStarted();
		} };
}
