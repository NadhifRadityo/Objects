package io.github.NadhifRadityo.Objects.Object;

import io.github.NadhifRadityo.Objects.Utilizations.MapUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.Utilizations.UnsafeUtils;
import sun.misc.Unsafe;

import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeListenerProxy;
import java.beans.VetoableChangeSupport;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface VetoableChangeService {
	void addVetoableChangeListener(String propertyName, VetoableChangeListener listener);
	void addVetoableChangeListener(VetoableChangeListener listener);
	void removeVetoableChangeListener(String propertyName, VetoableChangeListener listener);
	void removeVetoableChangeListener(VetoableChangeListener listener);
	Map.Entry<VetoableChangeListener, String>[] getVetoableChangeListener();

	static Map.Entry<VetoableChangeListener, String>[] getVetoableChangeListener(VetoableChangeSupport changeSupport, Map.Entry<VetoableChangeListener, String>[] result) {
		HashMap<String, Object> map = __.getMap(changeSupport);
		Object table = MapUtils.getTable(map); int length = Array.getLength(table);
		if(result == null) result = new Map.Entry[length]; int j = 0;
		for(int i = 0; i < length; i++) {
			Map.Entry<String, VetoableChangeListener> entry = (Map.Entry<String, VetoableChangeListener>) Array.get(table, i);
			if(entry == null) continue; VetoableChangeListener listener = entry.getValue();
			if(j >= result.length) result = Arrays.copyOf(result, result.length + 10);
			result[j++] = MapUtils.dumpMapEntry(listener, listener instanceof VetoableChangeListenerProxy ? ((VetoableChangeListenerProxy) listener).getPropertyName() : null);
		} if(j < result.length) result = Arrays.copyOf(result, j); return result;
	}
	static Map.Entry<VetoableChangeListener, String>[] getVetoableChangeListener(VetoableChangeSupport changeSupport) {
		return getVetoableChangeListener(changeSupport, null);
	}
	static void removeAllVetoableChangeListener(VetoableChangeSupport changeSupport) {
		Map.Entry<VetoableChangeListener, String>[] entries = getVetoableChangeListener(changeSupport);
		for(Map.Entry<VetoableChangeListener, String> entry : entries) changeSupport.removeVetoableChangeListener(entry.getKey());
	}

	class __ {
		private static final Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);
		protected static final Class CLASS_ChangeListenerMap_map;
		protected static final long AFIELD_ChangeListenerMap_map;
		protected static final long AFIELD_VetoableChangeSupport_map;

		static { try {
			CLASS_ChangeListenerMap_map = Class.forName("java.beans.ChangeListenerMap");
			Field FIELD_ChangeListenerMap_map = CLASS_ChangeListenerMap_map.getDeclaredField("map");
			Field FIELD_VetoableChangeSupport_map = VetoableChangeSupport.class.getDeclaredField("map");
			AFIELD_ChangeListenerMap_map = unsafe.objectFieldOffset(FIELD_ChangeListenerMap_map);
			AFIELD_VetoableChangeSupport_map = unsafe.objectFieldOffset(FIELD_VetoableChangeSupport_map);
		} catch(Exception e) { throw new Error(e); } }

		protected static HashMap<String, Object> getMap(VetoableChangeSupport changeSupport) {
			Object changeListenerMap = unsafe.getObject(changeSupport, AFIELD_VetoableChangeSupport_map);
			return (HashMap<String, Object>) unsafe.getObject(changeListenerMap, AFIELD_ChangeListenerMap_map);
		}
	}
}
