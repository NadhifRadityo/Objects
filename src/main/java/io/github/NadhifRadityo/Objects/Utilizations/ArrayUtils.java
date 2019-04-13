package io.github.NadhifRadityo.Objects.Utilizations;

import java.util.HashSet;
import java.util.Set;

public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {
	public static String deepToString(Object[] a) {
        if (a == null) return "null";
        int bufLen = 20 * a.length;
        if (a.length != 0 && bufLen <= 0) // if length / 20 >= Integer.MAX_VALUE -> buflen goes signed integer (negative value)
            bufLen = Integer.MAX_VALUE;
        StringBuilder buf = new StringBuilder(bufLen);
        deepToString(a, buf, new HashSet<Object[]>(), 0);
        return buf.toString();
    }

    private static void deepToString(Object[] a, StringBuilder buf, Set<Object[]> dejaVu, int indent) {
        if (a == null) { buf.append("null"); return; }
        int iMax = a.length - 1;
        if (iMax == -1) { buf.append("[]"); return; }

        dejaVu.add(a);
        buf.append("[\n");
        for (int i = 0; ; i++) {
            Object element = a[i];
            if (element == null) { buf.append("null");
            } else {
            	for(int j = 0; j <= indent; j++) buf.append('\t');
                Class<?> eClass = element.getClass();
                if (eClass.isArray()) {
                    if (eClass == byte[].class) 		buf.append(toString((byte[]) element));
                    else if (eClass == short[].class) 	buf.append(toString((short[]) element));
                    else if (eClass == int[].class) 	buf.append(toString((int[]) element));
                    else if (eClass == long[].class) 	buf.append(toString((long[]) element));
                    else if (eClass == char[].class) 	buf.append(toString((char[]) element));
                    else if (eClass == float[].class) 	buf.append(toString((float[]) element));
                    else if (eClass == double[].class) 	buf.append(toString((double[]) element));
                    else if (eClass == boolean[].class) buf.append(toString((boolean[]) element));
                    else { 
                        if (dejaVu.contains(element)) buf.append("[...]");
                        else deepToString((Object[]) element, buf, dejaVu, indent + 1);
                    }
                } else buf.append(element.toString());
            }
            if (i == iMax) { buf.append('\n'); break; }
            buf.append(",\n");
        }
        for(int j = 0; j <= indent - 1; j++) buf.append('\t');
        buf.append("]");
        dejaVu.remove(a);
    }
}
