package io.github.NadhifRadityo.Objects.Canvas;

import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class CustomGraphicModifier extends Sprite {
	protected CanvasPanel toApply;
	
	public CustomGraphicModifier(boolean applyToGraphic) {
		super(0, 0);
		this.toApply = applyToGraphic ? null : new CanvasPanel();
	}
	
	public boolean isApplyToGraphic() { return toApply == null; }
	public void setApplyToGraphic(boolean flag) {
		if(isApplyToGraphic() == flag) return;
		this.toApply = flag ? null : new CanvasPanel();
	}
	
	public void addSprite(Sprite sprite, int priority) { toApply.addSprite(sprite, priority); }
	public void addSprite(Sprite sprite) { toApply.addSprite(sprite); }
	public void removeSprite(Sprite sprite) { toApply.removeSprite(sprite); }
	
	public abstract void draw(Graphics2D g);
	public abstract void reset(Graphics2D g);
	
	@Override
	public void draw(Graphics g) {
		if(!(g instanceof Graphics2D)) {
			toApply.paintComponent(g);
			return;
		} draw((Graphics2D) g);
		
		if(!isApplyToGraphic()) {
			toApply.paintComponent(g);
			reset((Graphics2D) g);
		}
	}
	
	@Override
	public boolean equals(Object other) {
		return this == other;
	}
}
