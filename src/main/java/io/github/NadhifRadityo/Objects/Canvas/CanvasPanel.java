package io.github.NadhifRadityo.Objects.Canvas;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

public class CanvasPanel extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2291182424768508432L;
	protected final PriorityList<Sprite> sprites = new PriorityList<>(false);
	protected final ArrayList<Manager> managers = new ArrayList<>();

	public Map<Sprite, Integer> getSprites() { return sprites.getMap(); }
	public void addSprite(Sprite sprite, int priority) { sprites.add(sprite, priority); }
	public void addSprite(Sprite sprite) { addSprite(sprite, 0); }
	public void removeSprite(Sprite sprite) { sprites.remove(sprite); }
	
	public List<Manager> getManagers() { return Collections.unmodifiableList(managers); }
	public void addManager(Manager manager) {
		managers.add(manager);
		try { manager.initCanvas(this); } catch(Exception e) {
			ExceptionUtils.doSilentException(false, (ThrowsRunnable) () -> removeManager(manager)); throw e;
		}
	}
	public void removeManager(Manager manager) { if(!managers.contains(manager)) return; 
		managers.remove(manager); manager.uninitCanvas();
	}
	
	public List<Sprite> getAllSprites() {
		List<Sprite> sprites = new ArrayList<>(getSprites().keySet());
		for(Manager manager : managers) {
			if(manager.isApplyToGraphic()) continue;
			sprites.addAll(manager.getSprites().keySet());
		} return sprites;
	}
	
	@Override public void paintComponent(Graphics g) {
		for(Sprite sprite : sprites.get()) {
			sprite.draw(g); if(!(sprite instanceof OverrideGraphic)) continue;
			Graphics graphic = ((OverrideGraphic) sprite).getGraphics();
			if(graphic != null) g = graphic;
		}
	}
	public interface OverrideGraphic { Graphics getGraphics(); };
}
