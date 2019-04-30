package io.github.NadhifRadityo.Objects.Canvas;

import java.awt.Graphics;
import java.util.Map;

import javax.swing.JComponent;

import io.github.NadhifRadityo.Objects.List.PriorityList;

public class CanvasPanel extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2291182424768508432L;
	protected final PriorityList<Sprite> sprites = new PriorityList<>(false);

	public Map<Sprite, Integer> getSprites() { return sprites.getMap(); }
	public void addSprite(Sprite sprite, int priority) { sprites.add(sprite, priority); }
	public void addSprite(Sprite sprite) { addSprite(sprite, 0); }
	public void removeSprite(Sprite sprite) { sprites.remove(sprite); }
	
	@Override public void paintComponent(Graphics g) {
		sprites.get().forEach(e -> e.draw(g));
	}
}
