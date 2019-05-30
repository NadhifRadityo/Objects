package io.github.NadhifRadityo.Objects.Canvas.Managers;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Field;
import java.util.Map;

import com.jogamp.opengl.awt.GLJPanel;

import io.github.NadhifRadityo.Objects.List.PriorityList;

public class GLCanvasBridgeManager extends ImplementSpriteManager {
	protected final Frame dumpPeer = new Frame();
	protected final PriorityList<GLJPanel> glPanels;

	public GLCanvasBridgeManager(int priority) {
		super(true, priority);
		this.dumpPeer.addNotify();
		this.glPanels = new PriorityList<>();
		this.sprite = new GLCanvasBridgeSprite();
		initDumpPeer();
	} public GLCanvasBridgeManager() { this(0); }
	
	public Map<GLJPanel, Integer> getGLPanels() { return glPanels.getMap(); }
	public void addGLPanel(GLJPanel panel, int priority) { glPanels.add(panel, priority); dumpPeer.add(panel, priority); }
	public void addGLPanel(GLJPanel panel) { addGLPanel(panel, 0); }
	public void removeGLPanel(GLJPanel panel) { glPanels.remove(panel); dumpPeer.remove(panel); }
	
	protected void initDumpPeer() { try {
		Field visibleField = Component.class.getDeclaredField("visible");
		visibleField.setAccessible(true);
		visibleField.set(dumpPeer, true);
	} catch(Exception e) { throw new Error(e); } }
	
	protected class GLCanvasBridgeSprite extends ImplementSprite {
		protected Image lastImage;
		@Override public void draw(Graphics g) { super.draw(g);
	        Rectangle bounds = getArea().getBounds();
	        if(bounds.width == 0 || bounds.height == 0) return;
	        if(lastImage == null || (lastImage.getWidth(dumpPeer) != bounds.width || lastImage.getHeight(dumpPeer) != bounds.height))
	        	lastImage = parent.createImage(bounds.width, bounds.height);
	        dumpPeer.paint(lastImage.getGraphics());
	        g.drawImage(lastImage, 0, 0, dumpPeer);
	        lastImage.flush();
		}
		@Override public Area getArea() {
			Area area = new Area();
			for(GLJPanel canvas : glPanels.get(false))
				area.add(new Area(new Rectangle2D.Double(canvas.getX(), canvas.getY(), canvas.getWidth(), canvas.getHeight())));
			return area;
		}
	}
}
