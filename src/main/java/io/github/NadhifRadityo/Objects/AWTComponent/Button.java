package io.github.NadhifRadityo.Objects.AWTComponent;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ButtonModel;
import javax.swing.JButton;

public class Button extends JButton{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6218119154712423816L;

    private Color disabledBackgroundColor;
	private Color disabledHoverBackgroundColor;
    private Color disabledPressedBackgroundColor;
    
    private Color disabledForegroundColor;
	private Color disabledHoverForegroundColor;
    private Color disabledPressedForegroundColor;
	
    private Color backgroundColor = super.getBackground();
	private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;

    private Color foregroundColor = super.getForeground();
	private Color hoverForegroundColor;
    private Color pressedForegroundColor;

    public Button() {
        this(null);
    }

    public Button(String text) {
        super(text);
        super.setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
    	Color background = null;
    	Color foreground = null;
    	ButtonModel model = getModel();
    	
    	if(!isEnabled()) {
    		if(model.isPressed()) {
    			background = disabledPressedBackgroundColor;
    			foreground = disabledPressedForegroundColor;
    		} else if(model.isRollover()) {
    			background = disabledHoverBackgroundColor;
    			foreground = disabledHoverForegroundColor;
    		} else {
    			background = disabledBackgroundColor;
    			foreground = disabledForegroundColor;
    		}
    		
    		if(background == null)
    			background = disabledBackgroundColor;
    		if(foreground == null)
    			foreground = disabledForegroundColor;
    		
    	} else {
    		if(model.isPressed()) {
    			background = pressedBackgroundColor;
    			foreground = pressedForegroundColor;
    		} else if(model.isRollover()) {
    			background = hoverBackgroundColor;
    			foreground = hoverForegroundColor;
    		} else {
    			background = backgroundColor;
    			foreground = foregroundColor;
    		}
    		
    		if(background == null)
    			background = backgroundColor;
    		if(foreground == null)
    			foreground = foregroundColor;
    	}
    	
    	if(background != null) {
        	g.setColor(background);
    		super.setBackground(background);
    	}
    	if(foreground != null)
    		super.setForeground(foreground);
    	
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

//    @Override
//    public void setContentAreaFilled(boolean b) {
//    }
    
    /*
     * Disabled
     */
    //Background
    public void setDisabledBackground(Color bg) {
		disabledBackgroundColor = bg;
    }
    public Color getDisabledBackground() {
    	return disabledBackgroundColor;
    }
    
    //Disabled Hover Background
    public void setDisabledHoverBackgroundColor(Color disabledHoverBackgroundColor) {
        this.disabledHoverBackgroundColor = disabledHoverBackgroundColor;
    }
    public Color getDisabledHoverBackgroundColor() {
        return disabledHoverBackgroundColor;
    }

    //Pressed Background
    public void setDisabledPressedBackgroundColor(Color disabledPressedBackgroundColor) {
        this.disabledPressedBackgroundColor = disabledPressedBackgroundColor;
    }
    public Color getDisabledPressedBackgroundColor() {
        return disabledPressedBackgroundColor;
    }
    
    //Foreground
    public void setDisabledForeground(Color fg) {
		this.disabledForegroundColor = fg;
    }
    public Color getDisabledForeground() {
    	return disabledForegroundColor;
    }

    //Hover Foreground
    public void setDisabledHoverForegroundColor(Color disabledHoverForegroundColor) {
        this.disabledHoverForegroundColor = disabledHoverForegroundColor;
    }
    public Color getDisabledHoverForeground() {
        return disabledHoverForegroundColor;
    }

    //Pressed Foreground
    public void setDisabledPressedForegroundColor(Color disabledPressedForegroundColor) {
        this.disabledPressedForegroundColor = disabledPressedForegroundColor;
    }
    public Color getDisabledPressedForegroundColor() {
        return disabledPressedForegroundColor;
    }
    
    /*
     * Enabled
     */
    //Background
    public void setBackground(Color bg) {
		backgroundColor = bg;
    	super.setBackground(bg);
    }
    
    //Hover Background
    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }
    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    //Pressed Background
    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }
    
    //Foreground
    public void setForeground(Color fg) {
		foregroundColor = fg;
    	super.setForeground(fg);
    }

    //Hover Foreground
    public void setHoverForegroundColor(Color hoverForegroundColor) {
        this.hoverForegroundColor = hoverForegroundColor;
    }
    public Color getHoverForeground() {
        return hoverForegroundColor;
    }

    //Pressed Foreground
    public void setPressedForegroundColor(Color pressedForegroundColor) {
        this.pressedForegroundColor = pressedForegroundColor;
    }
    public Color getPressedForegroundColor() {
        return pressedForegroundColor;
    }
}
