package io.github.NadhifRadityo.Objects.$Interface.Service;

import io.github.NadhifRadityo.Objects.$Interface.Functional.ReferencedCallback;
import io.github.NadhifRadityo.Objects.$Utilizations.MapUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.UnsafeUtils;

import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public interface VetoableChangeService {
	void addVetoableChangeListener(String propertyName, VetoableChangeListener listener);
	void addVetoableChangeListener(VetoableChangeListener listener);
	void removeVetoableChangeListener(String propertyName, VetoableChangeListener listener);
	void removeVetoableChangeListener(VetoableChangeListener listener);
	Map.Entry<VetoableChangeListener, String>[] getVetoableChangeListener();

	static void eachVetoableChangeListener(VetoableChangeSupport changeSupport, ReferencedCallback.VoidReferencedCallback consumer) {
		HashMap<String, Object> map = __.getMap(changeSupport); if(map == null) return;
		Object table = MapUtils.getTable(map); int length = Array.getLength(table);
		Object[] args = __.getTempArgs3(); args[0] = changeSupport;
		for(int i = 0; i < length; i++) {
			Map.Entry<String, VetoableChangeListener[]> entry = (Map.Entry<String, VetoableChangeListener[]>) Array.get(table, i);
			if(entry == null) continue; args[1] = entry.getKey(); VetoableChangeListener[] listeners = entry.getValue();
			for(int j = listeners.length - 1; j >= 0; j--) { args[2] = listeners[j]; consumer.get(args); }
		}
	}
	static Map.Entry<VetoableChangeListener, String>[] getVetoableChangeListener(VetoableChangeSupport changeSupport) {
		HashMap<String, Object> map = __.getMap(changeSupport); if(map == null) return new Map.Entry[0];
		Object table = MapUtils.getTable(map); int length = Array.getLength(table);
		int totalLength = 0; for(int i = 0; i < length; i++) {
			Map.Entry<String, VetoableChangeListener[]> entry = (Map.Entry<String, VetoableChangeListener[]>) Array.get(table, i);
			if(entry == null) continue; totalLength += entry.getValue().length; }
		Map.Entry<VetoableChangeListener, String>[] result = new Map.Entry[totalLength]; int j = 0;
		for(int i = 0; i < length; i++) {
			Map.Entry<String, VetoableChangeListener[]> entry = (Map.Entry<String, VetoableChangeListener[]>) Array.get(table, i);
			if(entry == null) continue; VetoableChangeListener[] listeners = entry.getValue();
			for(VetoableChangeListener listener : listeners)
				result[j++] = MapUtils.dumpMapEntry(listener, entry.getKey());
		} return result;
	}
	static void removeAllVetoableChangeListener(VetoableChangeSupport changeSupport) {
		eachVetoableChangeListener(changeSupport, __.removeEach);
	}

	class __ {
		private static final UnsafeUtils.Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);
		protected static final Class CLASS_ChangeListenerMap_map;
		protected static final long AFIELD_ChangeListenerMap_map;
		protected static final long AFIELD_VetoableChangeSupport_map;

		protected static final ThreadLocal<WeakReference<Object[]>> tempArgs3 = new ThreadLocal<>();
		protected static final ReferencedCallback.PVoidReferencedCallback removeEach = (args) -> {
			VetoableChangeSupport changeSupport = (VetoableChangeSupport) args[0];
			String propertyName = (String) args[1];
			VetoableChangeListener listener = (VetoableChangeListener) args[2];
			changeSupport.removeVetoableChangeListener(propertyName, listener);
		};

		static { try {
			CLASS_ChangeListenerMap_map = Class.forName("java.beans.ChangeListenerMap");
			Field FIELD_ChangeListenerMap_map = CLASS_ChangeListenerMap_map.getDeclaredField("map");
			Field FIELD_VetoableChangeSupport_map = VetoableChangeSupport.class.getDeclaredField("map");
			AFIELD_ChangeListenerMap_map = unsafe.objectFieldOffset(FIELD_ChangeListenerMap_map);
			AFIELD_VetoableChangeSupport_map = unsafe.objectFieldOffset(FIELD_VetoableChangeSupport_map);
		} catch(Exception e) { throw new Error(e); } }

		protected static Object[] getTempArgs3() {
			Object[] result = tempArgs3.get() == null || tempArgs3.get().get() == null ? null : tempArgs3.get().get();
			if(result == null) { result = new Object[3]; tempArgs3.set(new WeakReference<>(result)); } return result;
		}

		protected static HashMap<String, Object> getMap(VetoableChangeSupport changeSupport) {
			Object changeListenerMap = unsafe.getObject(changeSupport, AFIELD_VetoableChangeSupport_map);
			return (HashMap<String, Object>) unsafe.getObject(changeListenerMap, AFIELD_ChangeListenerMap_map);
		}
	}
}
