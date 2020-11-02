package io.github.NadhifRadityo.Objects.Canvas.Managers;

import com.jogamp.opengl.awt.GLJPanel;
import io.github.NadhifRadityo.Objects.List.PriorityList;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Field;
import java.util.Map;

/*
 * Don't use this too often!
 * This class is actually have an expensive calls.
 * I don't know for sure, why this only works with GLJPanel.
 * Other methods that I have tried, but still no luck.
 * Like GLCanvas, GLWindow -> NewtCanvasAWT.
 *
 * Please, optimize this!
 * For now, just use vanilla GLCanvas.
 * It performs better than this.
 *
 * FYI. This supports transparent background.
 */
public class GLJPanelBridgeManager extends ImplementSpriteManager {
	protected final Frame dumpPeer = new Frame();
	protected final PriorityList<GLJPanel> glPanels;
	protected boolean invalidate = false;

	public GLJPanelBridgeManager(int priority) {
		super(true, priority);
		this.glPanels = new PriorityList<>();
		this.sprite = new GLJPanelBridgeSprite();
		initDumpPeer();
	} public GLJPanelBridgeManager() { this(0); }

	public Map.Entry<GLJPanel, Integer>[] getGLJPanels() { return glPanels.getMap(); }
	public void addGLJPanel(GLJPanel panel, int priority) { glPanels.add(panel, priority); dumpPeer.add(panel, priority); }
	public void addGLJPanel(GLJPanel panel) { addGLJPanel(panel, 0); }
	public void removeGLJPanel(GLJPanel panel) { dumpPeer.remove(panel); glPanels.remove(panel); }
	public void invalidate() { this.invalidate = true; }

	protected void initDumpPeer() { try {
		dumpPeer.setUndecorated(true);
		dumpPeer.setBackground(new Color(0, 0, 0, 0));
		dumpPeer.addNotify();
		Field visibleField = Component.class.getDeclaredField("visible");
		visibleField.setAccessible(true);
		visibleField.set(dumpPeer, true);
	} catch(Exception e) { throw new Error(e); } }

	protected class GLJPanelBridgeSprite extends ImplementSprite {
		Area lastArea;
		@Override public void draw(Graphics g) { super.draw(g); dumpPeer.paint(g); if(invalidate || lastArea == null) calculateArea(); }
		@Override public Area getArea() { if(invalidate || lastArea == null) calculateArea(); return lastArea; }
		protected void calculateArea() {
			lastArea = new Area(); for(GLJPanel canvas : glPanels.get(false))
				lastArea.add(new Area(new Rectangle2D.Double(canvas.getX(), canvas.getY(), canvas.getWidth(), canvas.getHeight())));
			invalidate = false;
		}
	}
}
