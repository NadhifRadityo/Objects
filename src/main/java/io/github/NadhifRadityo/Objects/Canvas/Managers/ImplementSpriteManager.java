package io.github.NadhifRadityo.Objects.Canvas.Managers;

import java.awt.Graphics;
import java.awt.geom.Area;
import java.util.Objects;
import java.util.Set;

import io.github.NadhifRadityo.Objects.Canvas.CanvasPanel;
import io.github.NadhifRadityo.Objects.Canvas.Manager;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;

public class ImplementSpriteManager extends Manager {
	protected int priority;

	public ImplementSpriteManager(boolean applyToGraphic, int priority) {
		super(applyToGraphic);
		this.priority = priority;
	} public ImplementSpriteManager(boolean applyToGraphic) { this(applyToGraphic, 0); }
	
	public int getPriority() { return priority; }
	public void setPriority(int priority) { this.priority = priority; reinit(); }
	
	protected class ImplementSprite extends Sprite {
		private int lastHash; private Area lastArea;
		public ImplementSprite() { super(0, 0); }
		@Override public void draw(Graphics g) { if(!isApplyToGraphic()) toApply.paint(g); }
		@Override public Area getArea() { if(isApplyToGraphic()) return null;
			Set<Sprite> sprites = toApply.getSprites().keySet();
			int currentHash = Objects.hash((Object[]) sprites.toArray(new Sprite[0]));
			if(currentHash != lastHash) { lastHash = currentHash; lastArea = CanvasPanel.getAllArea(sprites); }
			return lastArea;
		} @Override public boolean equals(Object other) { return this == other; }
	} protected ImplementSprite sprite = new ImplementSprite();
	
	@Override protected void init(CanvasPanel canvas) { canvas.addSprite(sprite, priority); }
	@Override protected void uninit(CanvasPanel canvas) { canvas.removeSprite(sprite); }
}
