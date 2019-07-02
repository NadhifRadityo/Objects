package io.github.NadhifRadityo.Objects.Object;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

public interface PropertyChangeService {
	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
	void addPropertyChangeListener(PropertyChangeListener listener);
	void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
	void removePropertyChangeListener(PropertyChangeListener listener);
	Map<PropertyChangeListener, String> getPropertyChangeListener();
	
	static Map<PropertyChangeListener, String> getPropertyChangeListener(PropertyChangeSupport changeSupport) {
		Map<PropertyChangeListener, String> result = new HashMap<>();
		for(PropertyChangeListener listener : changeSupport.getPropertyChangeListeners())
			result.put(listener, listener instanceof PropertyChangeListenerProxy ? ((PropertyChangeListenerProxy) listener).getPropertyName() : null);
		return result;
	}
}
