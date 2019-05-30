package io.github.NadhifRadityo.Objects.Canvas;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

public class CanvasPanel extends Component {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2291182424768508432L;
	protected final PriorityList<Sprite> sprites = new PriorityList<>();
	protected final ArrayList<Manager> managers = new ArrayList<>();
	protected final PriorityList<SpriteChecker> checkers = new PriorityList<>();

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
	
	public Map<SpriteChecker, Integer> getCheckers() { return checkers.getMap(); }
	public void addChecker(SpriteChecker checker, int priority) { checkers.add(checker, priority); }
	public void addChecker(SpriteChecker checker) { addChecker(checker,  0); }
	public void removeChecker(SpriteChecker checker) { checkers.remove(checker); }
	
	public List<Sprite> getAllSprites() {
		List<Sprite> sprites = new ArrayList<>(getSprites().keySet());
		for(Manager manager : managers) {
			if(manager.isApplyToGraphic()) continue;
			sprites.addAll(manager.getSprites().keySet());
		} return sprites;
	}
	
	@Override public void paint(Graphics g) {
		Graphics graphic;
		for(Sprite sprite : sprites.get(false)) {
			for(SpriteChecker checker : checkers.get()) {
				graphic = checker.beforeDraw(sprite, g);
				if(graphic != null) g = graphic;
			}
			sprite.draw(g);
			for(SpriteChecker checker : checkers.get(false)) {
				graphic = checker.afterDraw(sprite, g);
				if(graphic != null) g = graphic;
			}
		}
	}
	
	public static Area getAllArea(Collection<Sprite> sprites) {
		Area area = new Area();
		for(Sprite sprite : sprites) area.add(sprite.getArea());
		return area;
	}
	
	public interface SpriteChecker {
		Graphics beforeDraw(Sprite sprite, Graphics graphic);
		Graphics afterDraw(Sprite sprite, Graphics graphic);
	}
}
