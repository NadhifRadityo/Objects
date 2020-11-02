package io.github.NadhifRadityo.Objects.Canvas;

import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.util.List;
import java.util.*;

public class CanvasPanel extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2291182424768508432L;
	protected final PriorityList<Sprite> sprites = new PriorityList<>();
	protected final ArrayList<Manager> managers = new ArrayList<>();
	protected final PriorityList<SpriteChecker> checkers = new PriorityList<>();
//	protected BufferedImage offscreenBuffer;
//	protected Graphics offscreenGraphics;

	public Map.Entry<Sprite, Integer>[] getSprites() { return sprites.getMap(); }
	public void addSprite(Sprite sprite, int priority) { sprites.add(sprite, priority); }
	public void addSprite(Sprite sprite) { addSprite(sprite, 0); }
	public void removeSprite(Sprite sprite) { sprites.remove(sprite); }
	
	public List<Manager> getManagers() { return Collections.unmodifiableList(managers); }
	public void addManager(Manager manager) { managers.add(manager); try { manager.initCanvas(this); } catch(Exception e) { ExceptionUtils.doSilentThrowsRunnable(false, () -> removeManager(manager)); throw e; } }
	public void removeManager(Manager manager) { manager.uninitCanvas(); if(!managers.contains(manager)) return; managers.remove(manager); }
	
	public Map.Entry<SpriteChecker, Integer>[] getCheckers() { return checkers.getMap(); }
	public void addChecker(SpriteChecker checker, int priority) { checkers.add(checker, priority); }
	public void addChecker(SpriteChecker checker) { addChecker(checker,  0); }
	public void removeChecker(SpriteChecker checker) { checkers.remove(checker); }
	
	public Sprite[] getAllSprites() {
		ArrayList<Sprite> result = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		try { for(Map.Entry<Sprite, Integer> entry : getSprites()) result.add(entry.getKey());
			for(Manager manager : managers) {
				if(manager.isApplyToGraphic()) continue;
				result.addAll(Arrays.asList(manager.getAllSprites()));
			} return result.toArray(new Sprite[0]);
		} finally { Pool.returnObject(ArrayList.class, result); }
	}
	
	@Override public void paint(Graphics /*w*/g) {
//		if(offscreenBuffer == null || getWidth() != offscreenBuffer.getWidth() || getHeight() != offscreenBuffer.getHeight()) {
//			offscreenBuffer = new BufferedImage(Math.max(getWidth(), 1), Math.max(getHeight(), 1), BufferedImage.TYPE_INT_ARGB);
//			if(offscreenGraphics != null) offscreenGraphics.dispose();
//			offscreenGraphics = offscreenBuffer.createGraphics();
//		}
//
//		Graphics g = offscreenGraphics;
		// TODO: Don't use toArray() -> Avoid heap.
		Graphics graphic;
		for(Sprite sprite : sprites.get(false).toArray(new Sprite[0])) {
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
//
//		offscreenBuffer.flush();
//		wg.drawImage(offscreenBuffer, 0, 0, null);
	}
	
	public static Area getAllArea(Sprite[] sprites) {
		Area area = new Area();
		for(Sprite sprite : sprites) area.add(sprite.getArea());
		return area;
	}
	
	public interface SpriteChecker {
		Graphics beforeDraw(Sprite sprite, Graphics graphic);
		Graphics afterDraw(Sprite sprite, Graphics graphic);
	}
}
