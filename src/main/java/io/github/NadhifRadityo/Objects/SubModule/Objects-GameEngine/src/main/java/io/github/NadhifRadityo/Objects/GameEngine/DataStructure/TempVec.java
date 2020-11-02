package io.github.NadhifRadityo.Objects.GameEngine.DataStructure;

import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferManager;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.UnsafeUtils;

import java.nio.ByteBuffer;

public class TempVec extends GenType {
	public static final int ITEM_HEADER_LENGTH = 1;
	public static final int ITEM_TOTAL_LENGTH = ITEM_HEADER_LENGTH + 8;
	public static final byte TYPE_NULL = 0;
	public static final byte TYPE_OBJECT = 1;
	public static final byte TYPE_INT = 2;
	public static final byte TYPE_LONG = 3;
	public static final byte TYPE_SHORT = 4;
	public static final byte TYPE_FLOAT = 5;
	public static final byte TYPE_DOUBLE = 6;
	public static final byte TYPE_CHAR = 7;

	protected final Object d;

	public TempVec(int size, BufferManager bufferManager) {
		super(size);
		this.d = bufferManager != null ? bufferManager.allocate(size * ITEM_TOTAL_LENGTH) : new Object[size];
	}

	@Override public Object getVectorAt(int i) {
		if(d instanceof Object[]) return ((Object[]) d)[i];
		ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH;
		switch(buffer.get(index)) {
			case TYPE_NULL: return null;
			case TYPE_OBJECT: return UnsafeUtils.fromAddress(buffer.getLong(index + ITEM_HEADER_LENGTH));
			case TYPE_INT: return buffer.getInt(index + ITEM_HEADER_LENGTH);
			case TYPE_LONG: return buffer.getLong(index + ITEM_HEADER_LENGTH);
			case TYPE_SHORT: return buffer.getShort(index + ITEM_HEADER_LENGTH);
			case TYPE_FLOAT: return buffer.getFloat(index + ITEM_HEADER_LENGTH);
			case TYPE_DOUBLE: return buffer.getDouble(index + ITEM_HEADER_LENGTH);
			case TYPE_CHAR: return buffer.getChar(index + ITEM_HEADER_LENGTH);
			default: throw new IllegalStateException();
		}
	}
	public int getIntAt(int i) { if(d instanceof Object[]) return (int) ((Object[]) d)[i]; ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH; return buffer.getInt(index + ITEM_HEADER_LENGTH); }
	public long getLongAt(int i) { if(d instanceof Object[]) return (long) ((Object[]) d)[i]; ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH; return buffer.getLong(index + ITEM_HEADER_LENGTH); }
	public short getShortAt(int i) { if(d instanceof Object[]) return (short) ((Object[]) d)[i]; ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH; return buffer.getShort(index + ITEM_HEADER_LENGTH); }
	public float getFloatAt(int i) { if(d instanceof Object[]) return (float) ((Object[]) d)[i]; ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH; return buffer.getFloat(index + ITEM_HEADER_LENGTH); }
	public double getDoubleAt(int i) { if(d instanceof Object[]) return (double) ((Object[]) d)[i]; ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH; return buffer.getDouble(index + ITEM_HEADER_LENGTH); }
	public char getCharAt(int i) { if(d instanceof Object[]) return (char) ((Object[]) d)[i]; ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH; return buffer.getChar(index + ITEM_HEADER_LENGTH); }

	@Override public void setVectorAt(int i, Object object) {
		if(d instanceof Object[]) { ((Object[]) d)[i] = object; return; }
		ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH;
		if(object instanceof Character) { buffer.putChar(index + ITEM_HEADER_LENGTH, (Character) object); buffer.put(index, TYPE_CHAR); return; }
		if(object instanceof Double) { buffer.putDouble(index + ITEM_HEADER_LENGTH, (Double) object); buffer.put(index, TYPE_DOUBLE); return; }
		if(object instanceof Float) { buffer.putFloat(index + ITEM_HEADER_LENGTH, (Float) object); buffer.put(index, TYPE_FLOAT); return; }
		if(object instanceof Short) { buffer.putShort(index + ITEM_HEADER_LENGTH, (Short) object); buffer.put(index, TYPE_SHORT); return; }
		if(object instanceof Long) { buffer.putLong(index + ITEM_HEADER_LENGTH, (Long) object); buffer.put(index, TYPE_LONG); return; }
		if(object instanceof Integer) { buffer.putInt(index + ITEM_HEADER_LENGTH, (Integer) object); buffer.put(index, TYPE_INT); return; }
		if(object != null) { buffer.putLong(index + ITEM_HEADER_LENGTH, UnsafeUtils.toAddress(object)); buffer.put(index, TYPE_OBJECT); return; }
		buffer.putLong(index + ITEM_HEADER_LENGTH, 0); buffer.put(index, TYPE_NULL);
	}
	public void setVectorAt(int i, char object) { if(d instanceof Object[]) { ((Object[]) d)[i] = object; return; } ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH; buffer.putChar(index + ITEM_HEADER_LENGTH, object); buffer.put(index, TYPE_CHAR); }
	public void setVectorAt(int i, double object) { if(d instanceof Object[]) { ((Object[]) d)[i] = object; return; } ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH; buffer.putDouble(index + ITEM_HEADER_LENGTH, object); buffer.put(index, TYPE_DOUBLE); }
	public void setVectorAt(int i, float object) { if(d instanceof Object[]) { ((Object[]) d)[i] = object; return; } ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH; buffer.putFloat(index + ITEM_HEADER_LENGTH, object); buffer.put(index, TYPE_FLOAT); }
	public void setVectorAt(int i, short object) { if(d instanceof Object[]) { ((Object[]) d)[i] = object; return; } ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH; buffer.putShort(index + ITEM_HEADER_LENGTH, object); buffer.put(index, TYPE_SHORT); }
	public void setVectorAt(int i, long object) { if(d instanceof Object[]) { ((Object[]) d)[i] = object; return; } ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH; buffer.putLong(index + ITEM_HEADER_LENGTH, object); buffer.put(index, TYPE_LONG); }
	public void setVectorAt(int i, int object) { if(d instanceof Object[]) { ((Object[]) d)[i] = object; return; } ByteBuffer buffer = (ByteBuffer) d; int index = i * ITEM_TOTAL_LENGTH; buffer.putInt(index + ITEM_HEADER_LENGTH, object); buffer.put(index, TYPE_INT); }

	@Override public void reset(int off, int length) {
		if(d instanceof Object[]) { ArrayUtils.empty((Object[]) d, off, length); return; }
		BufferUtils.empty((ByteBuffer) d, off * ITEM_TOTAL_LENGTH, length * ITEM_TOTAL_LENGTH);
	}
	public byte getTypeAt(int i) {
		if(d instanceof Object[]) {
			Object object = ((Object[]) d)[i];
			if(object instanceof Character) return TYPE_CHAR;
			if(object instanceof Double) return TYPE_DOUBLE;
			if(object instanceof Float) return TYPE_FLOAT;
			if(object instanceof Short) return TYPE_SHORT;
			if(object instanceof Long) return TYPE_LONG;
			if(object instanceof Integer) return TYPE_INT;
			if(object != null) return TYPE_OBJECT;
			return TYPE_NULL;
		}
		int index = i * ITEM_TOTAL_LENGTH;
		byte type = ((ByteBuffer) d).get(index);
		if((type & ~(TYPE_NULL | TYPE_OBJECT | TYPE_INT | TYPE_LONG | TYPE_SHORT | TYPE_FLOAT | TYPE_DOUBLE | TYPE_CHAR)) != 0)
			throw new IllegalStateException("Type not identified");
		return type;
	}

	public void copyTo(GenType dest, int destOff, int off, int length) {
		ArrayUtils.assertCopyIndex(off, size, destOff, dest.size, length);
		for(int i = 0; i < length; i++) dest.setVectorAt(destOff + i, getVectorAt(off + i));
	}
	public void cutTo(GenType dest, int destOff, int off, int length) {
		copyTo(dest, destOff, off, length); reset(off, length);
	}
	public void copyFrom(GenType src, int srcOff, int off, int length) {
		ArrayUtils.assertCopyIndex(srcOff, src.size, off, size, length);
		for(int i = 0; i < length; i++) setVectorAt(off + i, src.getVectorAt(srcOff + i));
	}
	public void cutFrom(GenType src, int srcOff, int off, int length) {
		copyFrom(src, srcOff, off, length); src.reset(srcOff, length);
	}

	public void copyTo(TempVec dest, int destOff, int off, int length) {
		ArrayUtils.assertCopyIndex(off, size, destOff, dest.size, length);
		Object d1 = d; Object d2 = dest.d;
		if(d1 instanceof Object[] && d2 instanceof Object[]) { System.arraycopy(d1, off, d2, destOff, length); return; }
		if(d1 instanceof ByteBuffer && d2 instanceof ByteBuffer) { BufferUtils.copy((ByteBuffer) d1, off * ITEM_TOTAL_LENGTH, (ByteBuffer) d2, destOff * ITEM_TOTAL_LENGTH, length * ITEM_TOTAL_LENGTH); return; }
		for(int i = 0; i < length; i++) dest.setVectorAt(destOff + i, getVectorAt(off + i));
	}
	public void cutTo(TempVec dest, int destOff, int off, int length) {
		copyTo(dest, destOff, off, length); reset(off, length);
	}
	public void copyFrom(TempVec src, int srcOff, int off, int length) {
		ArrayUtils.assertCopyIndex(srcOff, src.size, off, size, length);
		Object d1 = src.d; Object d2 = d;
		if(d1 instanceof Object[] && d2 instanceof Object[]) { System.arraycopy(d1, off, d2, srcOff, length); return; }
		if(d1 instanceof ByteBuffer && d2 instanceof ByteBuffer) { BufferUtils.copy((ByteBuffer) d1, off * ITEM_TOTAL_LENGTH, (ByteBuffer) d2, srcOff * ITEM_TOTAL_LENGTH, length * ITEM_TOTAL_LENGTH); return; }
		for(int i = 0; i < length; i++) setVectorAt(off + i, src.getVectorAt(srcOff + i));
	}
	public void cutFrom(TempVec src, int srcOff, int off, int length) {
		copyFrom(src, srcOff, off, length); src.reset(srcOff, length);
	}

	public void copyTo(IVecN dest, int destOff, int off, int length) {
		ArrayUtils.assertCopyIndex(off, size, destOff, dest.size, length);
		for(int i = 0; i < length; i++) dest.d[destOff + i] = getIntAt(off + i);
	}
	public void cutTo(IVecN dest, int destOff, int off, int length) {
		copyTo(dest, destOff, off, length); reset(off, length);
	}
	public void copyFrom(IVecN src, int srcOff, int off, int length) {
		ArrayUtils.assertCopyIndex(srcOff, src.size, off, size, length);
		for(int i = 0; i < length; i++) setVectorAt(off + i, src.d[srcOff + i]);
	}
	public void cutFrom(IVecN src, int srcOff, int off, int length) {
		copyFrom(src, srcOff, off, length); src.reset(srcOff, length);
	}

	public void copyTo(LVecN dest, int destOff, int off, int length) {
		ArrayUtils.assertCopyIndex(off, size, destOff, dest.size, length);
		for(int i = 0; i < length; i++) dest.d[destOff + i] = getLongAt(off + i);
	}
	public void cutTo(LVecN dest, int destOff, int off, int length) {
		copyTo(dest, destOff, off, length); reset(off, length);
	}
	public void copyFrom(LVecN src, int srcOff, int off, int length) {
		ArrayUtils.assertCopyIndex(srcOff, src.size, off, size, length);
		for(int i = 0; i < length; i++) setVectorAt(off + i, src.d[srcOff + i]);
	}
	public void cutFrom(LVecN src, int srcOff, int off, int length) {
		copyFrom(src, srcOff, off, length); src.reset(srcOff, length);
	}

	public void copyTo(SVecN dest, int destOff, int off, int length) {
		ArrayUtils.assertCopyIndex(off, size, destOff, dest.size, length);
		for(int i = 0; i < length; i++) dest.d[destOff + i] = getShortAt(off + i);
	}
	public void cutTo(SVecN dest, int destOff, int off, int length) {
		copyTo(dest, destOff, off, length); reset(off, length);
	}
	public void copyFrom(SVecN src, int srcOff, int off, int length) {
		ArrayUtils.assertCopyIndex(srcOff, src.size, off, size, length);
		for(int i = 0; i < length; i++) setVectorAt(off + i, src.d[srcOff + i]);
	}
	public void cutFrom(SVecN src, int srcOff, int off, int length) {
		copyFrom(src, srcOff, off, length); src.reset(srcOff, length);
	}

	public void copyTo(FVecN dest, int destOff, int off, int length) {
		ArrayUtils.assertCopyIndex(off, size, destOff, dest.size, length);
		for(int i = 0; i < length; i++) dest.d[destOff + i] = getFloatAt(off + i);
	}
	public void cutTo(FVecN dest, int destOff, int off, int length) {
		copyTo(dest, destOff, off, length); reset(off, length);
	}
	public void copyFrom(FVecN src, int srcOff, int off, int length) {
		ArrayUtils.assertCopyIndex(srcOff, src.size, off, size, length);
		for(int i = 0; i < length; i++) setVectorAt(off + i, src.d[srcOff + i]);
	}
	public void cutFrom(FVecN src, int srcOff, int off, int length) {
		copyFrom(src, srcOff, off, length); src.reset(srcOff, length);
	}

	public void copyTo(VecN dest, int destOff, int off, int length) {
		ArrayUtils.assertCopyIndex(off, size, destOff, dest.size, length);
		for(int i = 0; i < length; i++) dest.d[destOff + i] = getDoubleAt(off + i);
	}
	public void cutTo(VecN dest, int destOff, int off, int length) {
		copyTo(dest, destOff, off, length); reset(off, length);
	}
	public void copyFrom(VecN src, int srcOff, int off, int length) {
		ArrayUtils.assertCopyIndex(srcOff, src.size, off, size, length);
		for(int i = 0; i < length; i++) setVectorAt(off + i, src.d[srcOff + i]);
	}
	public void cutFrom(VecN src, int srcOff, int off, int length) {
		copyFrom(src, srcOff, off, length); src.reset(srcOff, length);
	}

	public void copyTo(IMatMxN dest, int destOff, int off, int length) {
		ArrayUtils.assertCopyIndex(off, size, destOff, dest.size, length);
		for(int i = 0; i < length; i++) dest.d[destOff + i] = getIntAt(off + i);
	}
	public void cutTo(IMatMxN dest, int destOff, int off, int length) {
		copyTo(dest, destOff, off, length); reset(off, length);
	}
	public void copyFrom(IMatMxN src, int srcOff, int off, int length) {
		ArrayUtils.assertCopyIndex(srcOff, src.size, off, size, length);
		for(int i = 0; i < length; i++) setVectorAt(off + i, src.d[srcOff + i]);
	}
	public void cutFrom(IMatMxN src, int srcOff, int off, int length) {
		copyFrom(src, srcOff, off, length); src.reset(srcOff, length);
	}

	public void copyTo(LMatMxN dest, int destOff, int off, int length) {
		ArrayUtils.assertCopyIndex(off, size, destOff, dest.size, length);
		for(int i = 0; i < length; i++) dest.d[destOff + i] = getLongAt(off + i);
	}
	public void cutTo(LMatMxN dest, int destOff, int off, int length) {
		copyTo(dest, destOff, off, length); reset(off, length);
	}
	public void copyFrom(LMatMxN src, int srcOff, int off, int length) {
		ArrayUtils.assertCopyIndex(srcOff, src.size, off, size, length);
		for(int i = 0; i < length; i++) setVectorAt(off + i, src.d[srcOff + i]);
	}
	public void cutFrom(LMatMxN src, int srcOff, int off, int length) {
		copyFrom(src, srcOff, off, length); src.reset(srcOff, length);
	}

	public void copyTo(SMatMxN dest, int destOff, int off, int length) {
		ArrayUtils.assertCopyIndex(off, size, destOff, dest.size, length);
		for(int i = 0; i < length; i++) dest.d[destOff + i] = getShortAt(off + i);
	}
	public void cutTo(SMatMxN dest, int destOff, int off, int length) {
		copyTo(dest, destOff, off, length); reset(off, length);
	}
	public void copyFrom(SMatMxN src, int srcOff, int off, int length) {
		ArrayUtils.assertCopyIndex(srcOff, src.size, off, size, length);
		for(int i = 0; i < length; i++) setVectorAt(off + i, src.d[srcOff + i]);
	}
	public void cutFrom(SMatMxN src, int srcOff, int off, int length) {
		copyFrom(src, srcOff, off, length); src.reset(srcOff, length);
	}

	public void copyTo(FMatMxN dest, int destOff, int off, int length) {
		ArrayUtils.assertCopyIndex(off, size, destOff, dest.size, length);
		for(int i = 0; i < length; i++) dest.d[destOff + i] = getFloatAt(off + i);
	}
	public void cutTo(FMatMxN dest, int destOff, int off, int length) {
		copyTo(dest, destOff, off, length); reset(off, length);
	}
	public void copyFrom(FMatMxN src, int srcOff, int off, int length) {
		ArrayUtils.assertCopyIndex(srcOff, src.size, off, size, length);
		for(int i = 0; i < length; i++) setVectorAt(off + i, src.d[srcOff + i]);
	}
	public void cutFrom(FMatMxN src, int srcOff, int off, int length) {
		copyFrom(src, srcOff, off, length); src.reset(srcOff, length);
	}

	public void copyTo(MatMxN dest, int destOff, int off, int length) {
		ArrayUtils.assertCopyIndex(off, size, destOff, dest.size, length);
		for(int i = 0; i < length; i++) dest.d[destOff + i] = getDoubleAt(off + i);
	}
	public void cutTo(MatMxN dest, int destOff, int off, int length) {
		copyTo(dest, destOff, off, length); reset(off, length);
	}
	public void copyFrom(MatMxN src, int srcOff, int off, int length) {
		ArrayUtils.assertCopyIndex(srcOff, src.size, off, size, length);
		for(int i = 0; i < length; i++) setVectorAt(off + i, src.d[srcOff + i]);
	}
	public void cutFrom(MatMxN src, int srcOff, int off, int length) {
		copyFrom(src, srcOff, off, length); src.reset(srcOff, length);
	}
}
