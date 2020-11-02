package io.github.NadhifRadityo.Objects.Object;

import io.github.NadhifRadityo.Objects.Utilizations.MapUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.Utilizations.UnsafeUtils;
import sun.misc.Unsafe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface PropertyChangeService {
	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
	void addPropertyChangeListener(PropertyChangeListener listener);
	void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
	void removePropertyChangeListener(PropertyChangeListener listener);
	Map.Entry<PropertyChangeListener, String>[] getPropertyChangeListener();

	static Map.Entry<PropertyChangeListener, String>[] getPropertyChangeListener(PropertyChangeSupport changeSupport, Map.Entry<PropertyChangeListener, String>[] result) {
		HashMap<String, Object> map = __.getMap(changeSupport);
		Object table = MapUtils.getTable(map); int length = Array.getLength(table);
		if(result == null) result = new Map.Entry[length]; int j = 0;
		for(int i = 0; i < length; i++) {
			Map.Entry<String, PropertyChangeListener> entry = (Map.Entry<String, PropertyChangeListener>) Array.get(table, i);
			if(entry == null) continue; PropertyChangeListener listener = entry.getValue();
			if(j >= result.length) result = Arrays.copyOf(result, result.length + 10);
			result[j++] = MapUtils.dumpMapEntry(listener, listener instanceof PropertyChangeListenerProxy ? ((PropertyChangeListenerProxy) listener).getPropertyName() : null);
		} if(j < result.length) result = Arrays.copyOf(result, j); return result;
	}
	static Map.Entry<PropertyChangeListener, String>[] getPropertyChangeListener(PropertyChangeSupport changeSupport) {
		return getPropertyChangeListener(changeSupport, null);
	}
	static void removeAllPropertyChangeListener(PropertyChangeSupport changeSupport) {
		Map.Entry<PropertyChangeListener, String>[] entries = getPropertyChangeListener(changeSupport);
		for(Map.Entry<PropertyChangeListener, String> entry : entries) changeSupport.removePropertyChangeListener(entry.getKey());
	}

	class __ {
		private static final Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);
		protected static final Class CLASS_ChangeListenerMap_map;
		protected static final long AFIELD_ChangeListenerMap_map;
		protected static final long AFIELD_PropertyChangeSupport_map;

		static { try {
			CLASS_ChangeListenerMap_map = Class.forName("java.beans.ChangeListenerMap");
			Field FIELD_ChangeListenerMap_map = CLASS_ChangeListenerMap_map.getDeclaredField("map");
			Field FIELD_PropertyChangeSupport_map = PropertyChangeSupport.class.getDeclaredField("map");
			AFIELD_ChangeListenerMap_map = unsafe.objectFieldOffset(FIELD_ChangeListenerMap_map);
			AFIELD_PropertyChangeSupport_map = unsafe.objectFieldOffset(FIELD_PropertyChangeSupport_map);
		} catch(Exception e) { throw new Error(e); } }

		protected static HashMap<String, Object> getMap(PropertyChangeSupport changeSupport) {
			Object changeListenerMap = unsafe.getObject(changeSupport, AFIELD_PropertyChangeSupport_map);
			return (HashMap<String, Object>) unsafe.getObject(changeListenerMap, AFIELD_ChangeListenerMap_map);
		}
	}
}
