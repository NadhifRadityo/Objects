package io.github.NadhifRadityo.Objects.AWTComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

	private final MouseListener backgroundMouseListener = new MouseListener() {
		private boolean hover;
		private boolean pressed;

		@Override
		public void mouseReleased(MouseEvent e) {
			pressed = false;
			if(hover) {
				changeBackground(hoverBackgroundColor);
				changeForeground(hoverForegroundColor);
			} else {
				changeBackground(backgroundColor);
				changeForeground(foregroundColor);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			pressed = true;
			if(pressedBackgroundColor != null)
				changeBackground(pressedBackgroundColor);
			if(pressedForegroundColor != null)
				changeForeground(pressedForegroundColor);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			hover = false;
			if(!pressed) {
				changeBackground(backgroundColor);
				changeForeground(foregroundColor);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			hover = true;
			if(!pressed) {
				if(hoverBackgroundColor != null)
					changeBackground(hoverBackgroundColor);
				if(hoverForegroundColor != null)
					changeForeground(hoverForegroundColor);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) { }
	};

	public Panel() {
		addMouseListener(backgroundMouseListener);
	}

	public MouseListener getBackgroundMouseListener() {
		return backgroundMouseListener;
	}

	//Background
	public final static String BACKGROUND_COLOR_CHANGED = "backgroundColorChanged";
	public Color getBackground() { return backgroundColor; }
	public void setBackground(Color bg) { backgroundColor = bg; changeBackground(bg); }

	public Color getHoverBackgroundColor() { return hoverBackgroundColor; }
	public void setHoverBackgroundColor(Color hoverBackgroundColor) { this.hoverBackgroundColor = hoverBackgroundColor; }

	public Color getPressedBackgroundColor() { return pressedBackgroundColor; }
	public void setPressedBackgroundColor(Color pressedBackgroundColor) { this.pressedBackgroundColor = pressedBackgroundColor; }

	//Foreground
	public final static String FOREGROUND_COLOR_CHANGED = "foregroundColorChanged";
	public Color getForeground() { return foregroundColor; }
	public void setForeground(Color fg) { foregroundColor = fg; changeForeground(fg); }

	public Color getHoverForeground() { return hoverForegroundColor; }
	public void setHoverForegroundColor(Color hoverForegroundColor) { this.hoverForegroundColor = hoverForegroundColor; }

	public Color getPressedForegroundColor() { return pressedForegroundColor; }
	public void setPressedForegroundColor(Color pressedForegroundColor) { this.pressedForegroundColor = pressedForegroundColor; }

	//Utils
	private void changeBackground(Color newColor) {
		Color oldColor = getBackground();
		super.setBackground(newColor);
		firePropertyChange(BACKGROUND_COLOR_CHANGED, oldColor, newColor);
	}
	private void changeForeground(Color newColor) {
		Color oldColor = getForeground();
		super.setForeground(newColor);
		firePropertyChange(FOREGROUND_COLOR_CHANGED, oldColor, newColor);
	}
}
