package io.github.NadhifRadityo.Objects.$Interface.Service;

import io.github.NadhifRadityo.Objects.$Interface.Functional.ReferencedCallback;
import io.github.NadhifRadityo.Objects.$Utilizations.MapUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.UnsafeUtils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public interface PropertyChangeService {
	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
	void addPropertyChangeListener(PropertyChangeListener listener);
	void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
	void removePropertyChangeListener(PropertyChangeListener listener);
	Map.Entry<PropertyChangeListener, String>[] getPropertyChangeListener();

	static void eachPropertyChangeListener(PropertyChangeSupport changeSupport, ReferencedCallback.VoidReferencedCallback consumer) {
		HashMap<String, Object> map = __.getMap(changeSupport); if(map == null) return;
		Object table = MapUtils.getTable(map); int length = Array.getLength(table);
		Object[] args = __.getTempArgs3(); args[0] = changeSupport;
		for(int i = 0; i < length; i++) {
			Map.Entry<String, PropertyChangeListener[]> entry = (Map.Entry<String, PropertyChangeListener[]>) Array.get(table, i);
			if(entry == null) continue; args[1] = entry.getKey(); PropertyChangeListener[] listeners = entry.getValue();
			for(int j = listeners.length - 1; j >= 0; j--) { args[2] = listeners[j]; consumer.get(args); }
		}
	}
	static Map.Entry<PropertyChangeListener, String>[] getPropertyChangeListener(PropertyChangeSupport changeSupport) {
		HashMap<String, Object> map = __.getMap(changeSupport); if(map == null) return new Map.Entry[0];
		Object table = MapUtils.getTable(map); int length = Array.getLength(table);
		int totalLength = 0; for(int i = 0; i < length; i++) {
			Map.Entry<String, PropertyChangeListener[]> entry = (Map.Entry<String, PropertyChangeListener[]>) Array.get(table, i);
			if(entry == null) continue; totalLength += entry.getValue().length; }
		Map.Entry<PropertyChangeListener, String>[] result = new Map.Entry[totalLength]; int j = 0;
		for(int i = 0; i < length; i++) {
			Map.Entry<String, PropertyChangeListener[]> entry = (Map.Entry<String, PropertyChangeListener[]>) Array.get(table, i);
			if(entry == null) continue; PropertyChangeListener[] listeners = entry.getValue();
			for(PropertyChangeListener listener : listeners)
				result[j++] = MapUtils.dumpMapEntry(listener, entry.getKey());
		} return result;
	}
	static void removeAllPropertyChangeListener(PropertyChangeSupport changeSupport) {
		eachPropertyChangeListener(changeSupport, __.removeEach);
	}

	class __ {
		private static final UnsafeUtils.Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);
		protected static final Class CLASS_ChangeListenerMap_map;
		protected static final long AFIELD_ChangeListenerMap_map;
		protected static final long AFIELD_PropertyChangeSupport_map;

		protected static final ThreadLocal<WeakReference<Object[]>> tempArgs3 = new ThreadLocal<>();
		protected static final ReferencedCallback.PVoidReferencedCallback removeEach = (args) -> {
			PropertyChangeSupport changeSupport = (PropertyChangeSupport) args[0];
			String propertyName = (String) args[1];
			PropertyChangeListener listener = (PropertyChangeListener) args[2];
			changeSupport.removePropertyChangeListener(propertyName, listener);
		};

		static { try {
			CLASS_ChangeListenerMap_map = Class.forName("java.beans.ChangeListenerMap");
			Field FIELD_ChangeListenerMap_map = CLASS_ChangeListenerMap_map.getDeclaredField("map");
			Field FIELD_PropertyChangeSupport_map = PropertyChangeSupport.class.getDeclaredField("map");
			AFIELD_ChangeListenerMap_map = unsafe.objectFieldOffset(FIELD_ChangeListenerMap_map);
			AFIELD_PropertyChangeSupport_map = unsafe.objectFieldOffset(FIELD_PropertyChangeSupport_map);
		} catch(Exception e) { throw new Error(e); } }

		protected static Object[] getTempArgs3() {
			Object[] result = tempArgs3.get() == null || tempArgs3.get().get() == null ? null : tempArgs3.get().get();
			if(result == null) { result = new Object[3]; tempArgs3.set(new WeakReference<>(result)); } return result;
		}

		protected static HashMap<String, Object> getMap(PropertyChangeSupport changeSupport) {
			Object changeListenerMap = unsafe.getObject(changeSupport, AFIELD_PropertyChangeSupport_map);
			return (HashMap<String, Object>) unsafe.getObject(changeListenerMap, AFIELD_ChangeListenerMap_map);
		}
	}
}
