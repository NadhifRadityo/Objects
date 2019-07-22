package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Object.IdObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class IdObjectUtils {
	private static final Map<Class<? extends IdObject>, AtomicLong> ids = new HashMap<>();

	public static long getNewId(Class<? extends IdObject> type) {
		return ids.computeIfAbsent(type, k -> new AtomicLong()).incrementAndGet();
	}
}
