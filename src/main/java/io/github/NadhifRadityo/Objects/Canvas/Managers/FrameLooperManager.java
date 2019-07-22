package io.github.NadhifRadityo.Objects.Canvas.Managers;

import io.github.NadhifRadityo.Objects.Canvas.CanvasPanel;
import io.github.NadhifRadityo.Objects.Canvas.Manager;
import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.Thread.Handler;
import io.github.NadhifRadityo.Objects.Thread.RunnablePost;

import javax.swing.*;

public class FrameLooperManager extends ImplementSpriteManager {
	protected final PriorityList<FrameUpdater> updaters;
	protected boolean enabled;
	protected Handler handler;

	protected final ThrowsRunnable runnable;
	protected RunnablePost post;
	protected CanvasPanel canvas;

	private volatile boolean painting = false;
	private final Object paintingLock = new Object();
	
	protected volatile int fps = 30;
	protected volatile RunBehindCallback runBehind;

	public FrameLooperManager(boolean applyToGraphic, boolean enabled, Handler handler, int priority) {
		super(applyToGraphic, priority);
		this.updaters = new PriorityList<>();
		this.enabled = enabled;
		this.handler = handler;
		
		this.runnable = () -> {
			long nextTick = System.currentTimeMillis();
			while(enabled && canvas != null) {
				updaters.get().forEach(FrameUpdater::update);
				painting = true; canvas.repaint();
				SwingUtilities.invokeLater(() -> { painting = false; synchronized(paintingLock) { paintingLock.notifyAll(); } });
				synchronized(paintingLock) { while(painting) try { paintingLock.wait(); } catch(Exception ignored) { } }
				
				long currentTick = System.currentTimeMillis();
				long sleep = (nextTick += 1000 / fps) - currentTick;
				if(sleep < 0) { nextTick = currentTick;
					if(runBehind != null) runBehind.late(sleep * -1);
				} else Thread.sleep(sleep);
			}
		};
	} public FrameLooperManager(boolean applyToGraphic, boolean enabled, Handler handler) { this(applyToGraphic, enabled, handler, 0); }
	
	public boolean isEnabled() { return enabled; }
	public Handler getHandler() { return handler; }
	public int getFps() { return fps; }
	public RunBehindCallback getRunBehindCallback() { return runBehind; }
	
	public void setEnabled(boolean enabled) {
		if(this.enabled == enabled) return;
		this.enabled = enabled;
		if(enabled) start();
		else stop();
	}
	public void setHandler(Handler handler) { this.handler = handler; }
	public void setFps(int fps) { this.fps = Math.max(1, fps); }
	public void setRunBehindCallback(RunBehindCallback runBehind) { this.runBehind = runBehind; }
	
	public void addUpdater(FrameUpdater updater, int priority) { updaters.add(updater, priority); }
	public void addUpdater(FrameUpdater updater) { addUpdater(updater, 0); }
	public void removeUpdater(FrameUpdater updater) { updaters.remove(updater); }
	
	protected void start() {
		if(canvas == null || post != null) return;
		post = handler.postThrowable(runnable);
	}
	protected void stop() {
		if(post == null) return;
		handler.removePost(post);
		this.post = null;
	}
	private void checkManagers(CanvasPanel canvas) {
		for(Manager manager : canvas.getManagers()) {
			if(!manager.isApplyToGraphic()) checkManagers(manager.getToApply());
			if(!(manager instanceof FrameLooperManager) || manager.equals(this)) continue;
			uninitCanvas(); throw new IllegalArgumentException("There's already frame looper!");
		}
	}
	
	@Override protected void init(CanvasPanel canvas) {
		super.init(canvas); checkManagers(canvas);
		while(canvas instanceof ManagerCanvas) {
			CanvasPanel parent = ((ManagerCanvas) canvas).getManager().getParent();
			if(parent == null) break; canvas = parent; checkManagers(canvas);
		} this.canvas = canvas; if(enabled) start();
	}
	@Override protected void uninit(CanvasPanel canvas) { super.uninit(canvas); stop(); this.canvas = null; }
	
	public interface FrameUpdater { void update(); }
	public interface RunBehindCallback { void late(long delay); }
}
