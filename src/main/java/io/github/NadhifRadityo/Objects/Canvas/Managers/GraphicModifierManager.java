package io.github.NadhifRadityo.Objects.Canvas.Managers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import io.github.NadhifRadityo.Objects.Canvas.CanvasPanel.OverrideGraphic;
import io.github.NadhifRadityo.Objects.List.PriorityList;

public class GraphicModifierManager extends ImplementSpriteManager {
	protected final PriorityList<CustomGraphicModifier> modifiers;
	
	public GraphicModifierManager(boolean applyToGraphic, int priority) {
		super(applyToGraphic, priority);
		this.modifiers = new PriorityList<>();
		this.sprite = new GraphicModifierSprite();
	} public GraphicModifierManager(boolean applyToGraphic) { this(applyToGraphic, 0); }
	
	public void addModifier(CustomGraphicModifier modifier, int priority) { modifiers.add(modifier, priority); }
	public void addModifier(CustomGraphicModifier modifier) { addModifier(modifier, 0); }
	public void removeModifier(CustomGraphicModifier modifier) { modifiers.remove(modifier); }
	
	protected class GraphicModifierSprite extends ImplementSprite implements OverrideGraphic {
		Graphics graphics;
		@Override public void draw(Graphics g) {
			if(!(g instanceof Graphics2D)) {
				if(!isApplyToGraphic()) toApply.paintComponent(g); return;
			} Graphics realGraphics = g;
			List<CustomGraphicModifier> modifiers = GraphicModifierManager.this.modifiers.get();
			for(CustomGraphicModifier modifier : modifiers) {
				modifier.draw((Graphics2D) g); if(!(modifier instanceof OverrideGraphic)) continue;
				Graphics graphic = ((OverrideGraphic) modifier).getGraphics();
				if(graphic != null) g = graphic;
			}
			
			if(!isApplyToGraphic()) {
				toApply.paintComponent(g); Graphics ga = g;
				modifiers.forEach(e -> e.reset((Graphics2D) ga));
				graphics = realGraphics;
			} else graphics = g;
		}
		@Override public Graphics getGraphics() { Graphics g = graphics; graphics = null; return g; }
	}
	
	public static abstract class CustomGraphicModifier {
		public abstract void draw(Graphics2D g);
		public abstract void reset(Graphics2D g);
	}
}
