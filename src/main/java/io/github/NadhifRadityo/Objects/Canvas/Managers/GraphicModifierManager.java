package io.github.NadhifRadityo.Objects.Canvas.Managers;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import io.github.NadhifRadityo.Objects.Canvas.CanvasPanel;
import io.github.NadhifRadityo.Objects.Canvas.CanvasPanel.SpriteChecker;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;
import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.Utilizations.ListUtils;

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
	
	@Override public void setApplyToGraphic(boolean flag) {
		if(isApplyToGraphic() == flag) return;
		CanvasPanel canvas = uninitCanvas();
		super.setApplyToGraphic(flag);
		initCanvas(canvas);
	}
	
	protected class GraphicModifierSprite extends ImplementSprite implements OverrideGraphic, SpriteChecker {
		Graphics graphics;
		@Override public void draw(Graphics g) {
			Graphics realGraphics = g;
			List<CustomGraphicModifier> modifiers = GraphicModifierManager.this.modifiers.get();
			for(CustomGraphicModifier modifier : modifiers) {
				if(modifier.sprite.size() > 0) continue;
				modifier.draw(g); if(!(modifier instanceof OverrideGraphic)) continue;
				Graphics graphic = ((OverrideGraphic) modifier).getGraphics();
				if(graphic != null) g = graphic;
			}
			
			if(!isApplyToGraphic()) {
				toApply.paint(g);
				for(CustomGraphicModifier modifier : modifiers) {
					if(modifier.sprite.size() > 0) continue;
					modifier.reset(g);
				} graphics = realGraphics;
			} else graphics = g;
		}
		@Override public Graphics getGraphics() { Graphics g = graphics; graphics = null; return g; }
		
		@Override public Graphics beforeDraw(Sprite sprite, Graphics graphic) { return graphic; }
		@Override public Graphics afterDraw(Sprite sprite, Graphics graphic) {
			return equals(sprite) ? getGraphics() : graphic;
		}
	}
	
	protected class GraphicModifierChecker implements SpriteChecker {
		Graphics oldGraphic;
		@Override public Graphics beforeDraw(Sprite sprite, Graphics g) { this.oldGraphic = g;
			List<CustomGraphicModifier> modifiers = GraphicModifierManager.this.modifiers.get();
			Graphics graphic;
			for(CustomGraphicModifier modifier : modifiers) {
				if((modifier instanceof SpriteChecker) && (modifier.sprite.size() == 0 || 
						ListUtils.containsEquals(modifier.sprite, sprite))) {
					graphic = ((SpriteChecker) modifier).beforeDraw(sprite, g);
					if(graphic != null) g = graphic;
				} if(modifier.sprite.size() == 0 || !ListUtils.containsEquals(modifier.sprite, sprite)) continue;
				modifier.draw(g); if(modifier instanceof OverrideGraphic) {
					graphic = ((OverrideGraphic) modifier).getGraphics();
					if(graphic != null) g = graphic;
				}
			} return g;
		}
		@Override public Graphics afterDraw(Sprite sprite, Graphics g) {
			List<CustomGraphicModifier> modifiers = GraphicModifierManager.this.modifiers.get(false);
			Graphics graphic;
			for(CustomGraphicModifier modifier : modifiers) {
				if(modifier.sprite.size() > 0 && ListUtils.containsEquals(modifier.sprite, sprite))
					modifier.reset(g);
				if(!(modifier instanceof SpriteChecker) || (modifier.sprite.size() > 0 && 
						!ListUtils.containsEquals(modifier.sprite, sprite))) continue;
				graphic = ((SpriteChecker) modifier).afterDraw(sprite, g);
				if(graphic != null) g = graphic;
			} g = oldGraphic; oldGraphic = null; return g;
		}
	}
	protected GraphicModifierChecker checker = new GraphicModifierChecker();
	
	@Override protected void init(CanvasPanel canvas) {
		super.init(canvas); canvas.addChecker((SpriteChecker) sprite, priority);
		if(!isApplyToGraphic()) canvas = toApply;
		canvas.addChecker(checker, priority);
	}
	@Override protected void uninit(CanvasPanel canvas) {
		super.uninit(canvas); canvas.removeChecker((SpriteChecker) sprite);
		if(!isApplyToGraphic()) canvas = toApply;
		canvas.removeChecker(checker);
	}
	
	public static abstract class CustomGraphicModifier {
		protected final List<Sprite> sprite;
		
		public CustomGraphicModifier(Sprite... sprite) {
			this.sprite = Arrays.asList(sprite);
		}
		
		public abstract void draw(Graphics g);
		public abstract void reset(Graphics g);
	}
	public interface OverrideGraphic { Graphics getGraphics(); };
}
