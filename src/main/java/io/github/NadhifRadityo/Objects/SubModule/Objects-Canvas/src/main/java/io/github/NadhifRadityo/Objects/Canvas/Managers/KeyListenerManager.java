package io.github.NadhifRadityo.Objects.Canvas.Managers;

import io.github.NadhifRadityo.Objects.Canvas.CanvasPanel;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;
import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class KeyListenerManager extends ImplementSpriteManager {
	protected final PriorityList<CustomKeyListener> listeners;

	public KeyListenerManager(boolean applyToGraphic, int priority) {
		super(applyToGraphic, priority);
		this.listeners = new PriorityList<>();
	} public KeyListenerManager(boolean applyToGraphic) { this(applyToGraphic, 0); }
	
	public void addListener(CustomKeyListener listener, int priority) { listeners.add(listener, priority); }
	public void addListener(CustomKeyListener listener) { addListener(listener, 0); }
	public void removeListener(CustomKeyListener listener) { listeners.remove(listener); }
	public Map.Entry<CustomKeyListener, Integer>[] getListeners() { return listeners.getMap(); }

	private ArrayList<CustomKeyListener> eachListener(List<Integer> pressed, Consumer<CustomKeyListener> consumer) {
		Set<Sprite> inside = !isApplyToGraphic() ? Arrays.stream(toApply.getSprites()).map(Map.Entry::getKey).collect(Collectors.toSet()) : null;
		ArrayList<CustomKeyListener> result = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
			for(CustomKeyListener listener : listeners.get()) {
				if(listener.keycodes.size() > 0 && !listener.keycodes.containsAll(pressed)) continue;
				if(listener.sprite == null) { result.add(listener); continue; }
				if(inside != null && !inside.contains(listener.sprite)) continue;
				result.add(listener);
			} if(consumer == null) return result; result.forEach(consumer); return null;
		} finally { if(consumer != null) Pool.returnObject(ArrayList.class, result); }
	}
	
	private CanvasPanel registeredListener;
	protected KeyAdapter keyListener = new KeyAdapter() {
		List<Integer> pressed = new ArrayList<>();
		@Override public void keyPressed(KeyEvent e) { pressed.add(e.getKeyCode()); eachListener(pressed, i -> i.keyPressed(e)); }
		@Override public void keyReleased(KeyEvent e) { pressed.remove((Integer) e.getKeyCode()); eachListener(pressed, i -> i.keyReleased(e)); }
		@Override public void keyTyped(KeyEvent e) { eachListener(Collections.singletonList(e.getKeyCode()), i -> i.keyTyped(e)); }
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
