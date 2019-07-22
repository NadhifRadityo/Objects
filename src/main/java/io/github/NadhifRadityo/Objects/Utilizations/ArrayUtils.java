package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.lang.reflect.Array;

public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {
	public static String deepToString(Object[] objects) {
		if(objects == null) return "null";
		StringBuilder builder = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
		try { deepToString(objects, builder, 0); return builder.toString();
		} finally { Pool.returnObject(StringBuilder.class, builder); }
	}

	private static void addIndent(StringBuilder builder, int indent) { for(int i = 0; i < indent; i++) builder.append("\t"); }
	private static void deepToString(Object objects, StringBuilder builder, int indent) {
		if(objects == null) { builder.append("null"); return; }
		if(!objects.getClass().isArray()) throw new IllegalArgumentException();
		if(Array.getLength(objects) == 0) { builder.append("[]"); return; }
		int length = Array.getLength(objects);

		addIndent(builder, indent); builder.append("[\n");
		for(int i = 0; i < length; i++) { Object object = Array.get(objects, i); try {
			if(object == null) { addIndent(builder, indent + 1); builder.append("null"); continue; }
			if(object.getClass().isArray()) { deepToString(object, builder, indent + 1); continue; }
			addIndent(builder, indent + 1); builder.append(object.toString());
		} finally { builder.append(i < length - 1 ? ", \n" : "\n"); } }
		addIndent(builder, indent); builder.append("]");
	}
}
