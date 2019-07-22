package io.github.NadhifRadityo.Objects.AWTComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

@Deprecated
public class TextAreaPlaceholder implements FocusListener {
	private boolean placeHolderShow = false;
	private char passwordChar = "•".charAt(0);
	private String placeholder = "placeholder";
	
	private Color placeholderColor = new Color(150, 150, 150);
	private Color defaultColor;
	
	public TextAreaPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	
    @Override public void focusGained(FocusEvent e) {
    	showPlaceholder(e.getComponent());
    }
    @Override public void focusLost(FocusEvent e) {
    	hidePlaceholder(e.getComponent());
    }
    
    private void showPlaceholder(Component rawComp) {
    	JTextField comp;
    	if(rawComp instanceof JTextField)
        	comp = (JTextField) rawComp;
    	else
    		return;
    	
    	if(comp.getText().length() > 0) {
	    	if(comp instanceof JPasswordField)
	        	((JPasswordField) comp).setEchoChar(passwordChar);
	    	
	    	if(placeHolderShow)
	    		comp.setText("");
	    	placeHolderShow = false;
	    	
	    	comp.setForeground(defaultColor);
    	}
    }
    private void hidePlaceholder(Component rawComp) {
    	JTextField comp;
    	if(rawComp instanceof JTextField)
        	comp = (JTextField) rawComp;
    	else
    		return;
    	
        if (comp.getText().length() == 0) {
        	if(comp instanceof JPasswordField)
            	((JPasswordField) comp).setEchoChar((char) 0);
        	
        	placeHolderShow = true;
        	defaultColor = defaultColor != null ? defaultColor : comp.getForeground();
        	
        	comp.setText(placeholder);
        	comp.setForeground(placeholderColor);
        }
    }
    
    public TextAreaPlaceholder setPasswordEcho(char passwordChar) {
    	this.passwordChar = passwordChar;
    	return this;
    }
    public TextAreaPlaceholder setPlaceholder(String placeholder) {
    	this.placeholder = placeholder;
    	return this;
    }
    public TextAreaPlaceholder setPlaceholderColor(Color placeholderColor) {
    	this.placeholderColor = placeholderColor;
    	return this;
    }
    public TextAreaPlaceholder setDefaultColor(Color defaultColor) {
    	this.defaultColor = defaultColor;
    	return this;
    }
    
    public char getPasswordEcho() {
    	return passwordChar;
    }
    public String getPlaceholder() {
    	return placeholder;
    }
    public Color getPlaceholderColor() {
    	return placeholderColor;
    }
    public Color getDefaultColor() {
    	return defaultColor;
    }
    
    public void install(JTextField comp) {
    	comp.addFocusListener(this);
    }
    public void uninstall(JTextField comp) {
    	comp.removeFocusListener(this);
    }
    
    public static void install(TextAreaPlaceholder placeholder, JTextField comp) {
    	comp.addFocusListener(placeholder);
    }
    public static void uninstall(TextAreaPlaceholder placeholder, JTextField comp) {
    	comp.removeFocusListener(placeholder);
    }
}
