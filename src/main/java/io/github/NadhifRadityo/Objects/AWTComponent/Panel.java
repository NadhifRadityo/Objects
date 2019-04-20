package io.github.NadhifRadityo.Objects.AWTComponent;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Panel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5731345035242914662L;

	private Color backgroundColor = super.getBackground();
	private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;

    private Color foregroundColor = super.getForeground();
	private Color hoverForegroundColor;
    private Color pressedForegroundColor;
    
    private final MouseListener mouseListener = new MouseListener() {
		private boolean hover;
		private boolean pressed;
		
		@Override
		public void mouseReleased(MouseEvent e) {
			pressed = false;
			if(hover) {
				Panel.super.setBackground(hoverBackgroundColor);
				Panel.super.setForeground(hoverForegroundColor);
			} else {
				Panel.super.setBackground(backgroundColor);
				Panel.super.setForeground(foregroundColor);
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			pressed = true;
        	if(pressedBackgroundColor != null)
        		Panel.super.setBackground(pressedBackgroundColor);
        	if(pressedForegroundColor != null)
        		Panel.super.setForeground(pressedForegroundColor);
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			hover = false;
			if(!pressed) {
				Panel.super.setBackground(backgroundColor);
				Panel.super.setForeground(foregroundColor);
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			hover = true;
			if(!pressed) {
	        	if(hoverBackgroundColor != null)
	        		Panel.super.setBackground(hoverBackgroundColor);
	        	if(hoverForegroundColor != null)
	        		Panel.super.setForeground(hoverForegroundColor);
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) { }
	};
    
    public Panel() {
    	addMouseListener(mouseListener);
    }
    
    public void setBackground(Color bg) {
		backgroundColor = bg;
    	super.setBackground(bg);
    }

    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }
    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }
    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
    
    
    public void setForeground(Color fg) {
		foregroundColor = fg;
    	super.setForeground(fg);
    }

    public Color getHoverForeground() {
        return hoverForegroundColor;
    }
    public void setHoverForegroundColor(Color hoverForegroundColor) {
        this.hoverForegroundColor = hoverForegroundColor;
    }

    public Color getPressedForegroundColor() {
        return pressedForegroundColor;
    }
    public void setPressedForegroundColor(Color pressedForegroundColor) {
        this.pressedForegroundColor = pressedForegroundColor;
    }
    
    public MouseListener getBackgroundMouseListener() {
		return mouseListener;
	}
}
