package io.github.NadhifRadityo.Objects.Canvas.Managers;

import io.github.NadhifRadityo.Objects.List.PriorityList;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.Map;

public class ComponentBridgeManager extends ImplementSpriteManager {
	protected final PriorityList<Component> components;
	protected boolean invalidate = false;

	public ComponentBridgeManager(int priority) {
		super(true, priority);
		this.components = new PriorityList<>();
		this.sprite = new ComponentBridgeSprite();
	} public ComponentBridgeManager() { this(0); }

	public Map.Entry<Component, Integer>[] getComponents() { return components.getMap(); }
	public void addComponent(Component component, int priority) { components.add(component, priority); }
	public void addComponent(Component component) { addComponent(component, 0); }
	public void removeComponent(Component component) { components.remove(component); }
	public void invalidate() { this.invalidate = true; }

	protected class ComponentBridgeSprite extends ImplementSprite {
		Area lastArea;
		@Override public void draw(Graphics g) { super.draw(g); components.forEach(c -> c.print(g)); if(invalidate || lastArea == null) calculateArea(); }
		@Override public Area getArea() { if(invalidate || lastArea == null) calculateArea(); return lastArea; }
		protected void calculateArea() {
			lastArea = new Area(); for(Component component : components.get(false))
				lastArea.add(new Area(new Rectangle2D.Double(component.getX(), component.getY(), component.getWidth(), component.getHeight())));
			invalidate = false;
		}
	}
}
