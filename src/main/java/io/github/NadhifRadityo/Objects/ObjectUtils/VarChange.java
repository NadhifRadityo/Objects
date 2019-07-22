package io.github.NadhifRadityo.Objects.ObjectUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class VarChange<T> extends Proxy<T> {
	public static final String OBJECT_CHANGED_EVENT = "varChanged";
	public static final String OBJECT_GET_EVENT = "varGet";
	private PropertyChangeSupport eventSupport = new PropertyChangeSupport(this);
	
	public VarChange(T object) { super(object); }
	public VarChange() { this(null); }

	@Override public T get() { eventSupport.firePropertyChange(OBJECT_GET_EVENT, object, object); return super.get(); }
	@Override public void set(T object) { T oldObject = this.object; super.set(object); eventSupport.firePropertyChange(OBJECT_CHANGED_EVENT, oldObject, object); }

	public void addListener(PropertyChangeListener listener) { eventSupport.addPropertyChangeListener(listener); }
	public void removeListener(PropertyChangeListener listener) { eventSupport.removePropertyChangeListener(listener); }
}
