package io.github.NadhifRadityo.Objects.Canvas;

import java.util.List;
import java.util.Map;

public abstract class Manager {
	protected CanvasPanel parent = null;
	protected ManagerCanvas toApply;
	
	public Manager(boolean applyToGraphic) {
		setApplyToGraphic(applyToGraphic);
	}
	
	public CanvasPanel getParent() { return parent; }
	public ManagerCanvas getToApply() { return toApply; }
	public boolean isInited() { return parent != null; }
	public boolean isApplyToGraphic() { return toApply == null; }
	public void setApplyToGraphic(boolean flag) {
		if(isApplyToGraphic() == flag) return;
		this.toApply = flag ? null : new ManagerCanvas(this);
	}
	
	public Map<Sprite, Integer> getSprites() { return toApply.getSprites(); }
	public void addSprite(Sprite sprite, int priority) { toApply.addSprite(sprite, priority); }
	public void addSprite(Sprite sprite) { addSprite(sprite, 0); }
	public void removeSprite(Sprite sprite) { toApply.removeSprite(sprite); }
	
	public List<Manager> getManagers() { return toApply.getManagers(); }
	public void addManager(Manager manager) { toApply.addManager(manager); }
	public void removeManager(Manager manager) { toApply.removeManager(manager); }
	
	protected final void initCanvas(CanvasPanel canvas) {
		if(parent != null) throw new IllegalArgumentException("Already inited!");
		if(canvas == null) return;
		init(canvas); this.parent = canvas;
	} protected abstract void init(CanvasPanel canvas);
	protected final CanvasPanel uninitCanvas() {
		if(parent == null) return null;
		uninit(parent); CanvasPanel canvas = parent;
		this.parent = null; return canvas;
	} protected abstract void uninit(CanvasPanel canvas);
	protected void reinit() { initCanvas(uninitCanvas()); }
	
	protected static class ManagerCanvas extends CanvasPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7303158112910216128L;
		protected final Manager manager;
		
		public ManagerCanvas(Manager manager) {
			this.manager = manager;
		}
		
		public Manager getManager() { return manager; }
	}
}
