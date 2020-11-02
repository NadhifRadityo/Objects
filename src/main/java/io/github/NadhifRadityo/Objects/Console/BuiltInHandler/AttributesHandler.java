package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import java.util.Iterator;
import java.util.List;

public class AttributesHandler {
	public static final Object ON = new Object();
	public static final Object OFF = new Object();
	public static final Object TOGGLE = new Object();
	public static final Object SEEK = new Object();
	public static final Object DUMMY = new Object();

	private static Object checkImpl(Object obj, Object property, Object state) {
		if(obj != property && state != SEEK) return state; if(state != SEEK) return SEEK;
		return obj == ON ? ON : obj == TOGGLE ? state == ON ? OFF : ON : OFF;
	}
	public static Object check(Object obj, Object property, Object state, Iterator<Object> iterator) {
		Object result = checkImpl(obj, property, state); if(result != state) iterator.remove(); return result;
	}
	public static Object check(Object obj, Object property, Object state, List<Object> list, int index) {
		Object result = checkImpl(obj, property, state); if(result != state) list.set(index, DUMMY); return result;
	}
	public static Object checkOnce(Object property, Object state, List<Object> list) {
		for(int i = 0; i < list.size(); i++)
			state = check(list.get(i), property, state, list, i);
		consume(list); return state;
	}

	public static Object checkNotConsume(Object obj, Object property, Object state) {
		return checkImpl(obj, property, state);
	}
	public static Object checkOnceNotConsume(Object property, Object state, List<Object> list) {
		for(Object o : list) state = checkNotConsume(o, property, state);
		return state;
	}

	public static void consume(List<Object> list) {
		for(int i = list.size() - 1; i >= 0; i--) {
			if(list.get(i) == DUMMY)
				list.remove(i);
		}
	}
}
