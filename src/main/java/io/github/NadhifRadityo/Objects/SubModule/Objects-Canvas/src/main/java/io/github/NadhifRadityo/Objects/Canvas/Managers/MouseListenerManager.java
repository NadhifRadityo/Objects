package io.github.NadhifRadityo.Objects.Canvas.Managers;

import io.github.NadhifRadityo.Objects.Canvas.CanvasPanel;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;
import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MouseListenerManager extends ImplementSpriteManager {
	protected final PriorityList<CustomMouseListener> listeners;

	public MouseListenerManager(boolean applyToGraphic, int priority) {
		super(applyToGraphic, priority);
		this.listeners = new PriorityList<>();
	} public MouseListenerManager(boolean applyToGraphic) { this(applyToGraphic, 0); }

	public void addListener(CustomMouseListener listener, int priority) { listeners.add(listener, priority); }
	public void addListener(CustomMouseListener listener) { addListener(listener, 0); }
	public void removeListener(CustomMouseListener listener) { listeners.remove(listener); }
	public Map.Entry<CustomMouseListener, Integer>[] getListeners() { return listeners.getMap(); }

	private ArrayList<CustomMouseListener> eachListener(MouseEvent event, Consumer<CustomMouseListener> consumer) {
		Set<Sprite> inside = !isApplyToGraphic() ? Arrays.stream(toApply.getSprites()).map(Map.Entry::getKey).collect(Collectors.toSet()) : null;
		ArrayList<CustomMouseListener> result = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
			for(CustomMouseListener listener : listeners.get()) {
				if(listener.sprite == null) { result.add(listener); continue; }
				if(inside != null && !inside.contains(listener.sprite)) continue;
				Area area = listener.sprite.getArea();
				if(area == null || !area.contains(event.getPoint())) continue;
				result.add(listener);
			} if(consumer == null) return result; result.forEach(consumer); return null;
		} finally { if(consumer != null) Pool.returnObject(ArrayList.class, result); }
	}

	private CanvasPanel registeredListener;
	protected MouseAdapter mouseListener = new MouseAdapter() {
		public void mousePressed(MouseEvent e) { eachListener(e, i -> i.mousePressed(e)); }
		public void mouseReleased(MouseEvent e) { eachListener(e, i -> i.mouseReleased(e)); }
		public void mouseClicked(MouseEvent e) { eachListener(e, i -> i.mouseClicked(e)); }
		public void mouseDragged(MouseEvent e) { eachListener(e, i -> i.mouseDragged(e)); }
		public void mouseWheelMoved(MouseWheelEvent e) { eachListener(e, i -> i.mouseWheelMoved(e)); }

		MouseEvent oldEvent = null;
		public void mouseMoved(MouseEvent e) {
			ArrayList<CustomMouseListener> entered = eachListener(e, null);
			try { if(oldEvent != null) {
				ArrayList<CustomMouseListener> exited = eachListener(oldEvent, null); try {
					if(entered.size() > 0) entered.forEach(i -> { if(i.entered || exited.contains(i)) return; i.entered = true; i.mouseEntered(e); });
					if(exited.size() > 0) exited.forEach(i -> { if(!i.entered || entered.contains(i)) return; i.entered = false; i.mouseExited(e); });
				} finally { Pool.returnObject(ArrayList.class, exited); }
				} oldEvent = e; entered.forEach(i -> i.mouseMoved(e));
			} finally { Pool.returnObject(ArrayList.class, entered); }
		}
	};
	
	@Override protected void init(CanvasPanel canvas) {
		super.init(canvas);
		while(canvas instanceof ManagerCanvas) {
			CanvasPanel parent = ((ManagerCanvas) canvas).getManager().getParent();
			if(parent == null) break; canvas = parent;
		} this.registeredListener = canvas;
		registeredListener.addMouseListener(mouseListener);
		registeredListener.addMouseMotionListener(mouseListener);
	}
	@Override protected void uninit(CanvasPanel canvas) {
		super.uninit(canvas);
		registeredListener.removeMouseListener(mouseListener);
		registeredListener.removeMouseMotionListener(mouseListener);
	}
	
	public static abstract class CustomMouseListener extends MouseAdapter {
		protected final Sprite sprite;
		private boolean entered = false;
		
		public CustomMouseListener(Sprite sprite) {
			this.sprite = sprite;
		}
	}
}
