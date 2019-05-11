package io.github.NadhifRadityo.Objects.Canvas;

import java.util.List;
import java.util.Map;

public abstract class Manager {
	private CanvasPanel init = null;
	protected ManagerCanvas toApply;
	
	public Manager(boolean applyToGraphic) {
		setApplyToGraphic(applyToGraphic);
	}
	
	public boolean isInited() { return init != null; }
	public boolean isApplyToGraphic() { return toApply == null; }
	public void setApplyToGraphic(boolean flag) {
		if(isApplyToGraphic() == flag) return;
		this.toApply = flag ? null : new ManagerCanvas(this, init);
	}
	
	public Map<Sprite, Integer> getSprites() { return toApply.getSprites(); }
	public void addSprite(Sprite sprite, int priority) { toApply.addSprite(sprite, priority); }
	public void addSprite(Sprite sprite) { addSprite(sprite, 0); }
	public void removeSprite(Sprite sprite) { toApply.removeSprite(sprite); }
	
	public List<Manager> getManagers() { return toApply.getManagers(); }
	public void addManager(Manager manager) { toApply.addManager(manager); }
	public void removeManager(Manager manager) { toApply.removeManager(manager); }
	
	protected final void initCanvas(CanvasPanel canvas) {
		if(init != null) throw new IllegalArgumentException("Already inited!");
		if(canvas == null) return;
		init(canvas); this.init = canvas;
		if(!isApplyToGraphic()) toApply.canvasParent = canvas;
	} protected abstract void init(CanvasPanel canvas);
	protected final CanvasPanel uninitCanvas() {
		if(init == null) return null;
		uninit(init); CanvasPanel canvas = init;
		this.init = null;
		if(!isApplyToGraphic()) toApply.canvasParent = null;
		return canvas;
	} protected abstract void uninit(CanvasPanel canvas);
	protected void reinit() { initCanvas(uninitCanvas()); }
	
	protected class ManagerCanvas extends CanvasPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7303158112910216128L;
		protected final Manager manager;
		protected CanvasPanel canvasParent;
		
		public ManagerCanvas(Manager manager, CanvasPanel canvasParent) {
			this.manager = manager;
			this.canvasParent = canvasParent;
		}
		
		public Manager getManager() { return manager; }
		public CanvasPanel getCanvasParent() { return canvasParent; }
	}
}
