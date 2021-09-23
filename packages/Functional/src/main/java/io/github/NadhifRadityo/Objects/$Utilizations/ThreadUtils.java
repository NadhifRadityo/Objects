package io.github.NadhifRadityo.Objects.$Utilizations;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ThreadUtils {
	private static final Field FIELD_Thread_threadLocals;
	private static final Method METHOD_ThreadLocal_initialValue;
	private static final Method METHOD_ThreadLocal_createMap;
	private static final Method METHOD_ThreadLocal$ThreadLocalMap_set;
	private static final Method METHOD_ThreadLocal$ThreadLocalMap_getEntry;
	private static final Field FIELD_ThreadLocal$ThreadLocalMap$Entry_value;

	static { try {
		Class<?> CLASS_ThreadLocal$ThreadLocalMap = Class.forName("java.lang.ThreadLocal$ThreadLocalMap");
		Class<?> CLASS_ThreadLocal$ThreadLocalMap$Entry = Class.forName("java.lang.ThreadLocal$ThreadLocalMap$Entry");
		FIELD_Thread_threadLocals = Thread.class.getDeclaredField("threadLocals");
		METHOD_ThreadLocal_initialValue = ThreadLocal.class.getDeclaredMethod("initialValue");
		METHOD_ThreadLocal_createMap = ThreadLocal.class.getDeclaredMethod("createMap", Thread.class, Object.class);
		METHOD_ThreadLocal$ThreadLocalMap_set = CLASS_ThreadLocal$ThreadLocalMap.getDeclaredMethod("set", ThreadLocal.class, Object.class);
		METHOD_ThreadLocal$ThreadLocalMap_getEntry = CLASS_ThreadLocal$ThreadLocalMap.getDeclaredMethod("getEntry", ThreadLocal.class);
		FIELD_ThreadLocal$ThreadLocalMap$Entry_value = CLASS_ThreadLocal$ThreadLocalMap$Entry.getDeclaredField("value");

		FIELD_Thread_threadLocals.setAccessible(true);
		METHOD_ThreadLocal_initialValue.setAccessible(true);
		METHOD_ThreadLocal_createMap.setAccessible(true);
		METHOD_ThreadLocal$ThreadLocalMap_set.setAccessible(true);
		METHOD_ThreadLocal$ThreadLocalMap_getEntry.setAccessible(true);
		FIELD_ThreadLocal$ThreadLocalMap$Entry_value.setAccessible(true);
	} catch(Exception e) { throw new RuntimeException(e); } }

	public static <T> T getThreadLocalValue(ThreadLocal<T> threadLocal, Thread thread) {
		if(Thread.currentThread() == thread) return threadLocal.get();
		try {
			Object threadLocalMap = FIELD_Thread_threadLocals.get(thread);
			if(threadLocalMap == null) return setInitialValue(threadLocal, thread);
			Object entry = METHOD_ThreadLocal$ThreadLocalMap_getEntry.invoke(threadLocalMap, threadLocal);
			if(entry != null) return (T) FIELD_ThreadLocal$ThreadLocalMap$Entry_value.get(entry);
			return setInitialValue(threadLocal, thread);
		} catch(Exception e) { throw new Error(e); }
	}
	public static <T> void setThreadLocalValue(ThreadLocal<T> threadLocal, Thread thread, T value) {
		if(Thread.currentThread() == thread) { threadLocal.set(value); return; }
		try {
			Object threadLocalMap = FIELD_Thread_threadLocals.get(thread);
			if(threadLocalMap != null) METHOD_ThreadLocal$ThreadLocalMap_set.invoke(threadLocalMap, threadLocal, value);
			else METHOD_ThreadLocal_createMap.invoke(threadLocal, thread, value);
		} catch(Exception e) { throw new Error(e); }
	}

	private static <T> T setInitialValue(ThreadLocal<T> threadLocal, Thread thread) {
		return ExceptionUtils.doSilentThrowsReferencedCallback(ExceptionUtils.silentException, (args) -> {
			T value = (T) METHOD_ThreadLocal_initialValue.invoke(threadLocal);
			Object threadLocalMap = FIELD_Thread_threadLocals.get(thread);
			if(threadLocalMap == null) METHOD_ThreadLocal_createMap.invoke(threadLocal, thread, value);
			else METHOD_ThreadLocal$ThreadLocalMap_set.invoke(threadLocalMap, threadLocal, value);
			return value;
		});
	}
}
