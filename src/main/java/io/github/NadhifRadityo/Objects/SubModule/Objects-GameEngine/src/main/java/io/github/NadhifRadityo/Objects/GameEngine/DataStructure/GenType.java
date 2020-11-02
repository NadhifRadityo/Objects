package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;

public abstract class GenType implements Cloneable {
	public final int size;

	protected GenType(int size) {
		this.size = size;
	}

	public int getSize() { return size; }
	public abstract Object getVectorAt(int i);
	public abstract void setVectorAt(int i, Object object);
	public void reset(int off, int length) {
		ArrayUtils.assertIndex(off, size, length);
		for(int i = 0; i < length; i++) setVectorAt(off + i, null);
	} public void reset() { reset(0, size); }

	@Override public GenType clone() { try { return (GenType) super.clone(); } catch(Exception e) { throw new RuntimeException(e); } }
	@Override public String toString() {
		StringBuilder string = Pool.tryBorrow(Pool.getPool(StringBuilder.class)); try {
			string.append(getClass().getSimpleName().toLowerCase()).append('[');
			for(int i = 0; i < size; i++) { string.append(getVectorAt(i)); if(i + 1 < size) string.append(',').append(' '); }
			string.append(']'); return string.toString();
		} finally { Pool.returnObject(StringBuilder.class, string); }
	}
}
