package io.github.NadhifRadityo.Objects.ObjectUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class VarChange<T> {
	private T variable;
	private PropertyChangeSupport eventSupport = new PropertyChangeSupport(this);
	
	public VarChange(T initValue) { this.variable = initValue; }
	public VarChange() { this(null); }
	
	public void set(T variable) { eventSupport.firePropertyChange("varChanged", this.variable, variable); this.variable = variable; }
	public T get() { return variable; }
	
	public void addListener(PropertyChangeListener listener) { eventSupport.addPropertyChangeListener(listener); }
	public void removeListener(PropertyChangeListener listener) { eventSupport.removePropertyChangeListener(listener); }
}
