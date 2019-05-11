package io.github.NadhifRadityo.Objects.Canvas.Managers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.github.NadhifRadityo.Objects.Canvas.CanvasPanel;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;
import io.github.NadhifRadityo.Objects.List.PriorityList;

public class MouseListenerManager extends ImplementSpriteManager {
	protected final PriorityList<CustomMouseListener> listeners;

	public MouseListenerManager(boolean applyToGraphic, int priority) {
		super(applyToGraphic, priority);
		this.listeners = new PriorityList<>();
	} public MouseListenerManager(boolean applyToGraphic) { this(applyToGraphic, 0); }

	public void addListener(CustomMouseListener listener, int priority) { listeners.add(listener, priority); }
	public void addListener(CustomMouseListener listener) { addListener(listener, 0); }
	public void removeListener(CustomMouseListener listener) { listeners.remove(listener); }
	
	private List<CustomMouseListener> getListeners(MouseEvent e) {
		Set<Sprite> inside = !isApplyToGraphic() ? toApply.getSprites().keySet() : null;
		List<CustomMouseListener> result = new ArrayList<>();
		for(CustomMouseListener listener : listeners.get()) {
			if(listener.sprite == null) { result.add(listener); continue; }
			if(inside != null && !inside.contains(listener.sprite)) continue;
			Area area = listener.sprite.getArea();
			if(area == null || !area.contains(e.getPoint())) continue;
			result.add(listener);
		} return result;
	}

	private CanvasPanel registeredListener;
	protected MouseAdapter mouseListener = new MouseAdapter() {
		public void mousePressed(MouseEvent e) { getListeners(e).forEach(i -> i.mousePressed(e)); }
		public void mouseReleased(MouseEvent e) { getListeners(e).forEach(i -> i.mouseReleased(e)); }
		public void mouseClicked(MouseEvent e) { getListeners(e).forEach(i -> i.mouseClicked(e)); }
		public void mouseDragged(MouseEvent e) { getListeners(e).forEach(i -> i.mouseDragged(e)); }
		
		MouseEvent oldEvent = null;
		public void mouseMoved(MouseEvent e) {
			List<CustomMouseListener> entered = getListeners(e);
			if(oldEvent != null) {
				List<CustomMouseListener> exited = getListeners(oldEvent);
				if(entered.size() > 0) entered.forEach(i -> { if(i.entered || exited.contains(i)) return; i.entered = true; i.mouseEntered(e); });
				if(exited.size() > 0) exited.forEach(i -> { if(!i.entered || entered.contains(i)) return; i.entered = false; i.mouseExited(e); });
			} oldEvent = e;
			entered.forEach(i -> i.mouseMoved(e));
		}
	};
	
	@Override protected void init(CanvasPanel canvas) {
		super.init(canvas);
		while(canvas instanceof ManagerCanvas) {
			CanvasPanel parent = ((ManagerCanvas) canvas).getCanvasParent();
			if(parent == null) break;
			canvas = parent;
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
