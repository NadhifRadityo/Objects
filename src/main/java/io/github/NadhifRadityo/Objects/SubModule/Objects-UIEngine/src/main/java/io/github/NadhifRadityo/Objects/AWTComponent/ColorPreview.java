package io.github.NadhifRadityo.Objects.AWTComponent;

import javax.swing.*;
import java.awt.*;

public class ColorPreview extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7980833247299375681L;
	public static final String EVENT_COLOR_CHANGED = "ColorChanged";
	private Color color;
	
	public ColorPreview(Color color, int width, int height) {
		this.color = color;
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createLineBorder(new Color(21, 21, 21), 2));
	} public ColorPreview(Color color) { this(color, 15, 15); }
	
	public Color getColor() { return color; }
	public void setColor(Color color) {
		Color oldColor = this.color;
		this.color = color;
		repaint();
		firePropertyChange(EVENT_COLOR_CHANGED, oldColor, color);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		getBorder().paintBorder(this, g, 0, 0, getWidth(), getHeight());
	}
}
