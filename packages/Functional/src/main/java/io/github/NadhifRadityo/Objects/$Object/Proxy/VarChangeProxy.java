package io.github.NadhifRadityo.Objects.$Object.Proxy;

import io.github.NadhifRadityo.Objects.$Interface.Service.PropertyChangeService;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;

public class VarChangeProxy<T> extends Proxy<T> implements PropertyChangeService {
	public static final String OBJECT_CHANGED_EVENT = "varChanged";
	public static final String OBJECT_GET_EVENT = "varGet";
	protected final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	public VarChangeProxy(T object) { super(object); }
	public VarChangeProxy() { this(null); }

	@Override public T get() { changeSupport.firePropertyChange(OBJECT_GET_EVENT, object, object); return super.get(); }
	@Override public void set(T object) { T oldObject = this.object; super.set(object); changeSupport.firePropertyChange(OBJECT_CHANGED_EVENT, oldObject, object); }

	@Override public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) { changeSupport.addPropertyChangeListener(propertyName, listener); }
	@Override public void addPropertyChangeListener(PropertyChangeListener listener) { changeSupport.addPropertyChangeListener(listener); }
	@Override public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) { changeSupport.addPropertyChangeListener(propertyName, listener); }
	@Override public void removePropertyChangeListener(PropertyChangeListener listener) { changeSupport.addPropertyChangeListener(listener); }
	@Override public Map.Entry<PropertyChangeListener, String>[] getPropertyChangeListener() { return PropertyChangeService.getPropertyChangeListener(changeSupport); }
}
