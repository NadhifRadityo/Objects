package io.github.NadhifRadityo.Objects.AWTComponent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class ImageView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3193073683858548973L;
	
	private Image image;
	private int x, y = 0;
	private float scale = 1;
	
	public ImageView(Image img) {
		super();
		setImage(img);
		
		//Zooming
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent event) {
				double delta = 0.05f * event.getPreciseWheelRotation();
				scale += delta;
				
				repaint();
			}
		});
		
		//Dragging
		MouseAdapter listener = new MouseAdapter() {
			int lastX, lastY;
			int locX, locY;
	        @Override
	        public void mousePressed(MouseEvent e) {
	            locX = x;
	            locY = y;
	            lastX = e.getX();
	            lastY = e.getY();
	        }
	        @Override
	        public void mouseDragged(MouseEvent e) {
	            x = locX - lastX + e.getX();
	            y = locY - lastY + e.getY();
	            repaint();
	        }
		};
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}
	public ImageView() {
		this(null);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(image != null) {
			Graphics2D g2d = (Graphics2D) g.create();
			AffineTransform at = new AffineTransform();
			at.translate(x, y);
			at.scale(scale, scale);
			g2d.drawImage(image, at, this);
			g2d.dispose();
		}
	}
	
	public void setImage(Image img) {
		this.image = img;
		repaint();
	}
}
