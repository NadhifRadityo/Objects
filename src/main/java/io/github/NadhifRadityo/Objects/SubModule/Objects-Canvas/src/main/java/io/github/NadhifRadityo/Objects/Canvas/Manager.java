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
	
	public Map.Entry<Sprite, Integer>[] getSprites() { assertNotApplyToGraphic(); return toApply.getSprites(); }
	public void addSprite(Sprite sprite, int priority) { assertNotApplyToGraphic(); toApply.addSprite(sprite, priority); }
	public void addSprite(Sprite sprite) { addSprite(sprite, 0); }
	public void removeSprite(Sprite sprite) { assertNotApplyToGraphic(); toApply.removeSprite(sprite); }
	
	public List<Manager> getManagers() { assertNotApplyToGraphic(); return toApply.getManagers(); }
	public void addManager(Manager manager) { assertNotApplyToGraphic(); toApply.addManager(manager); }
	public void removeManager(Manager manager) { assertNotApplyToGraphic(); toApply.removeManager(manager); }

	public Map.Entry<CanvasPanel.SpriteChecker, Integer>[] getCheckers() { assertNotApplyToGraphic(); return toApply.getCheckers(); }
	public void addChecker(CanvasPanel.SpriteChecker checker, int priority) { assertNotApplyToGraphic(); toApply.addChecker(checker, priority); }
	public void addChecker(CanvasPanel.SpriteChecker checker) { addChecker(checker,  0); }
	public void removeChecker(CanvasPanel.SpriteChecker checker) { assertNotApplyToGraphic(); toApply.removeChecker(checker); }

	public Sprite[] getAllSprites() { assertNotApplyToGraphic(); return toApply.getAllSprites(); }
	public void assertApplyToGraphic() { if(!isApplyToGraphic()) throw new IllegalArgumentException("Not applied to graphic!"); }
	public void assertNotApplyToGraphic() { if(isApplyToGraphic()) throw new IllegalArgumentException("Applied to graphic!"); }
	
	protected final void initCanvas(CanvasPanel canvas) {
		if(parent != null) throw new IllegalArgumentException("Already inited!");
		if(canvas == null) return; this.parent = canvas; init(canvas);
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
		
		public ManagerCanvas(Manager manager) { this.manager = manager; }
		public Manager getManager() { return manager; }
	}
}
