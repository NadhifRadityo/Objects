package io.github.NadhifRadityo.Objects.AWTComponent;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public abstract class PanelDrawer extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1656300005487084668L;
	
	protected BufferedImage bufferedImage;
	protected Graphics2D bufferedGraphics;
	private ComponentListener componentListener;
	
	protected PanelDrawer(ComponentListener listener) {
		this.componentListener = listener;
		
		Dimension defaultSize = getDefaultSize();
		setSize(defaultSize);
		setPreferredSize(defaultSize);
		bufferedImage = new BufferedImage(defaultSize.width, defaultSize.height, BufferedImage.TYPE_INT_RGB);
		bufferedGraphics = bufferedImage.createGraphics();
		
		addComponentListener(new ComponentListener() {
			@Override public void componentHidden(ComponentEvent e) { componentListener.componentHidden(e); }
			@Override public void componentMoved(ComponentEvent e) { componentListener.componentMoved(e); }
			@Override public void componentShown(ComponentEvent e) { componentListener.componentShown(e); }
			@Override
			public void componentResized(ComponentEvent e) {
				bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
				bufferedGraphics = bufferedImage.createGraphics();
				repaint();
				componentListener.componentResized(e);
			}
		});
	}
	
	protected PanelDrawer() {
		this(new ComponentListener() {
			@Override public void componentHidden(ComponentEvent e) { }
			@Override public void componentMoved(ComponentEvent e) { }
			@Override public void componentShown(ComponentEvent e) { }
			@Override public void componentResized(ComponentEvent e) { }
		});
	}

	protected abstract Dimension getDefaultSize();
	
	public void reset() {
		bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		bufferedGraphics = bufferedImage.createGraphics();
		repaint();
	}
	
	protected void setComponentListener(ComponentListener listener) {
		this.componentListener = listener;
	}
	protected ComponentListener getComponentListener() {
		return componentListener;
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		g.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this);
	}
}
