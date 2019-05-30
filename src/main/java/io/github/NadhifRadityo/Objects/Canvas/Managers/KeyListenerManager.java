package io.github.NadhifRadityo.Objects.Canvas.Managers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import io.github.NadhifRadityo.Objects.Canvas.CanvasPanel;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;
import io.github.NadhifRadityo.Objects.List.PriorityList;

public class KeyListenerManager extends ImplementSpriteManager {
	protected final PriorityList<CustomKeyListener> listeners;

	public KeyListenerManager(boolean applyToGraphic, int priority) {
		super(applyToGraphic, priority);
		this.listeners = new PriorityList<>();
	} public KeyListenerManager(boolean applyToGraphic) { this(applyToGraphic, 0); }
	
	public void addListener(CustomKeyListener listener, int priority) { listeners.add(listener, priority); }
	public void addListener(CustomKeyListener listener) { addListener(listener, 0); }
	public void removeListener(CustomKeyListener listener) { listeners.remove(listener); }
	
	private List<CustomKeyListener> getListeners(KeyEvent e, List<Integer> pressed) {
		Set<Sprite> inside = !isApplyToGraphic() ? toApply.getSprites().keySet() : null;
		List<CustomKeyListener> result = new ArrayList<>();
		for(CustomKeyListener listener : listeners.get()) {
			if(listener.keycodes.size() > 0 && !listener.keycodes.containsAll(pressed)) continue;
			if(listener.sprite == null) { result.add(listener); continue; }
			if(inside != null && !inside.contains(listener.sprite)) continue;
			result.add(listener);
		} return result;
	}
	
	private CanvasPanel registeredListener;
	protected KeyAdapter keyListener = new KeyAdapter() {
		List<Integer> pressed = new ArrayList<>();
		@Override public void keyPressed(KeyEvent e) { pressed.add(e.getKeyCode()); getListeners(e, pressed).forEach(i -> i.keyPressed(e)); }
		@Override public void keyReleased(KeyEvent e) { pressed.remove((Integer) e.getKeyCode()); getListeners(e, pressed).forEach(i -> i.keyReleased(e)); }
		@Override public void keyTyped(KeyEvent e) { getListeners(e, Arrays.asList(e.getKeyCode())).forEach(i -> i.keyTyped(e)); }
	};
	
	@Override protected void init(CanvasPanel canvas) {
		super.init(canvas);
		while(canvas instanceof ManagerCanvas) {
			CanvasPanel parent = ((ManagerCanvas) canvas).getManager().getParent();
			if(parent == null) break; canvas = parent;
		} this.registeredListener = canvas;
		registeredListener.addKeyListener(keyListener);
	}
	@Override protected void uninit(CanvasPanel canvas) {
		super.uninit(canvas);
		registeredListener.removeKeyListener(keyListener);
	}
	
	public static abstract class CustomKeyListener extends KeyAdapter {
		protected final Sprite sprite;
		protected final List<Integer> keycodes;
		
		public CustomKeyListener(Sprite sprite, Integer... keycodes) {
			this.sprite = sprite;
			this.keycodes = Arrays.asList(keycodes);
		}
	}
}
