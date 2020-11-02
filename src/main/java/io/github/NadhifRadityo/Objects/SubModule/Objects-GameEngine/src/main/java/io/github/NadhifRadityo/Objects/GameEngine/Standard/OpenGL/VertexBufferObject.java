package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.GenType;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.VecN;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import static io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils.assertIndex;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.__getAddress;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils._allocateByteBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils._allocateDoubleBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils._allocateFloatBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils._allocateIntBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils._allocateLongBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils._allocateShortBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils._deallocate;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.createByteBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.createDoubleBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.createFloatBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.createIntBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.createLongBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.createShortBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.toBuffer;

public class VertexBufferObject<ID extends Number> extends OpenGLNativeHolder<ID> implements OpenGLNativeHolder.BindableNative {
	public static final String TYPE = "VERTEXBUFFEROBJECT";
	protected final VertexArrayObject<ID> parent;
	protected final int target;

	protected int usage;

	public VertexBufferObject(VertexArrayObject<ID> parent, int target) {
		super(parent.getGL());
		this.parent = parent;
		this.target = target;
		parent.attachBufferObject(this);
	}
	public VertexBufferObject(GLContext<ID> gl, int target) {
		super(gl);
		this.parent = null;
		this.target = target;
	}

	public VertexArrayObject<ID> getParent() { return parent; }
	public int getTarget() { return target; }
	public int getUsage() { return usage; }
	@Override public boolean isBind() { return getBindableNative(getGL(), getIdentifier()) == this; }

	protected void setData(Buffer buffer, int usage) {
		assertNotDead(); assertCreated(); if(parent != null) parent.assertBind(); assertBind();
		// https://jogamp.org/cgit/jogl-demos.git/tree/src/demos/es2/RawGL2ES2demo.java?id=HEAD#n514
		getGL().glBufferData(target, buffer, usage); this.usage = usage;
	}
	public void setData(int[] buffer, int off, int len, int usage) { assertIndex(off, buffer.length, len); IntBuffer _buffer = _allocateIntBuffer(len); try { toBuffer(buffer, off, _buffer, 0, len); setData(_buffer, usage); } finally { _deallocate(_buffer); } }
	public void setData(long[] buffer, int off, int len, int usage) { assertIndex(off, buffer.length, len); LongBuffer _buffer = _allocateLongBuffer(len); try { toBuffer(buffer, off, _buffer, 0, len); setData(_buffer, usage); } finally { _deallocate(_buffer); } }
	public void setData(short[] buffer, int off, int len, int usage) { assertIndex(off, buffer.length, len); ShortBuffer _buffer = _allocateShortBuffer(len); try { toBuffer(buffer, off, _buffer, 0, len); setData(_buffer, usage); } finally { _deallocate(_buffer); } }
	public void setData(float[] buffer, int off, int len, int usage) { assertIndex(off, buffer.length, len); FloatBuffer _buffer = _allocateFloatBuffer(len); try { toBuffer(buffer, off, _buffer, 0, len); setData(_buffer, usage); } finally { _deallocate(_buffer); } }
	public void setData(double[] buffer, int off, int len, int usage) { assertIndex(off, buffer.length, len); DoubleBuffer _buffer = _allocateDoubleBuffer(len); try { toBuffer(buffer, off, _buffer, 0, len); setData(_buffer, usage); } finally { _deallocate(_buffer); } }
	public void setData(int[] buffer, int len, int usage) { setData(buffer, 0, len, usage); }
	public void setData(long[] buffer, int len, int usage) { setData(buffer, 0, len, usage); }
	public void setData(short[] buffer, int len, int usage) { setData(buffer, 0, len, usage); }
	public void setData(float[] buffer, int len, int usage) { setData(buffer, 0, len, usage); }
	public void setData(double[] buffer, int len, int usage) { setData(buffer, 0, len, usage); }
	public void setData(int[] buffer, int usage) { setData(buffer, buffer.length, usage); }
	public void setData(long[] buffer, int usage) { setData(buffer, buffer.length, usage); }
	public void setData(short[] buffer, int usage) { setData(buffer, buffer.length, usage); }
	public void setData(float[] buffer, int usage) { setData(buffer, buffer.length, usage); }
	public void setData(double[] buffer, int usage) { setData(buffer, buffer.length, usage); }
	public void setData(int[] buffer) { setData(buffer, getGL().GL_STATIC_DRAW()); }
	public void setData(long[] buffer) { setData(buffer, getGL().GL_STATIC_DRAW()); }
	public void setData(short[] buffer) { setData(buffer, getGL().GL_STATIC_DRAW()); }
	public void setData(float[] buffer) { setData(buffer, getGL().GL_STATIC_DRAW()); }
	public void setData(double[] buffer) { setData(buffer, getGL().GL_STATIC_DRAW()); }
	public void setData(GenType[] genTypes, int off, int len, boolean asFloat, int usage) {
		assertIndex(off, genTypes.length, len);
		int size = 0; for(int i = off; i < len; i++) size += genTypes[i].size;
		Buffer buffer = asFloat ? _allocateFloatBuffer(size) : _allocateDoubleBuffer(size); try {
		for(int i = off; i < len; i++) { GenType genType = genTypes[i];
			for(int j = 0; j < genType.size; j++) {
				if(asFloat) ((FloatBuffer) buffer).put(((Number) genType.getVectorAt(j)).floatValue());
				else ((DoubleBuffer) buffer).put(((Number) genType.getVectorAt(j)).doubleValue());
		} } setData(buffer, usage); } finally { _deallocate(buffer); }
	}
	public void setData(GenType[] genTypes, int len, boolean asFloat, int usage) { setData(genTypes, 0, len, asFloat, usage); }
	public void setData(GenType[] genTypes, boolean asFloat, int usage) { setData(genTypes, genTypes.length, asFloat, usage); }
	public void setData(GenType[] genType, boolean asFloat) { setData(genType, asFloat, getGL().GL_STATIC_DRAW()); }
	public void setData(GenType[] genType) { setData(genType, true); }
	public void setData(GenType genType, boolean asFloat, int usage) { setData(new GenType[] { genType }, asFloat, usage); }
	public void setData(GenType genType, boolean asFloat) { setData(genType, asFloat, getGL().GL_STATIC_DRAW()); }
	public void setData(GenType genType) { setData(genType, true); }
	public void setData(IVecN[] genTypes, int off, int len, int usage) {
		assertIndex(off, genTypes.length, len); int count = 0;
		int size = 0; for(int i = off; i < len; i++) size += genTypes[i].size;
		IntBuffer buffer = _allocateIntBuffer(size); try {
		for(int i = off; i < len; i++) { IVecN genType = genTypes[i];
			toBuffer(genType, 0, buffer, count); count += genType.size;
		} setData(buffer, usage); } finally { _deallocate(buffer); }
	}
	public void setData(IVecN[] genTypes, int len, int usage) { setData(genTypes, 0, len, usage); }
	public void setData(IVecN[] genTypes, int usage) { setData(genTypes, genTypes.length, usage); }
	public void setData(IVecN[] genType) { setData(genType, getGL().GL_STATIC_DRAW()); }
	public void setData(IVecN genType, int usage) { setData(new IVecN[] { genType }, usage); }
	public void setData(IVecN genType) { setData(genType, getGL().GL_STATIC_DRAW()); }
	public void setData(LVecN[] genTypes, int off, int len, int usage) {
		assertIndex(off, genTypes.length, len); int count = 0;
		int size = 0; for(int i = off; i < len; i++) size += genTypes[i].size;
		LongBuffer buffer = _allocateLongBuffer(size); try {
		for(int i = off; i < len; i++) { LVecN genType = genTypes[i];
			toBuffer(genType, 0, buffer, count); count += genType.size;
		} setData(buffer, usage); } finally { _deallocate(buffer); }
	}
	public void setData(LVecN[] genTypes, int len, int usage) { setData(genTypes, 0, len, usage); }
	public void setData(LVecN[] genTypes, int usage) { setData(genTypes, genTypes.length, usage); }
	public void setData(LVecN[] genType) { setData(genType, getGL().GL_STATIC_DRAW()); }
	public void setData(LVecN genType, int usage) { setData(new LVecN[] { genType }, usage); }
	public void setData(LVecN genType) { setData(genType, getGL().GL_STATIC_DRAW()); }
	public void setData(SVecN[] genTypes, int off, int len, int usage) {
		assertIndex(off, genTypes.length, len); int count = 0;
		int size = 0; for(int i = off; i < len; i++) size += genTypes[i].size;
		ShortBuffer buffer = _allocateShortBuffer(size); try {
		for(int i = off; i < len; i++) { SVecN genType = genTypes[i];
			toBuffer(genType, 0, buffer, count); count += genType.size;
		} setData(buffer, usage); } finally { _deallocate(buffer); }
	}
	public void setData(SVecN[] genTypes, int len, int usage) { setData(genTypes, 0, len, usage); }
	public void setData(SVecN[] genTypes, int usage) { setData(genTypes, genTypes.length, usage); }
	public void setData(SVecN[] genType) { setData(genType, getGL().GL_STATIC_DRAW()); }
	public void setData(SVecN genType, int usage) { setData(new SVecN[] { genType }, usage); }
	public void setData(SVecN genType) { setData(genType, getGL().GL_STATIC_DRAW()); }
	public void setData(FVecN[] genTypes, int off, int len, boolean asFloat, int usage) {
		assertIndex(off, genTypes.length, len);
		int size = 0; for(int i = off; i < len; i++) size += genTypes[i].size;
		Buffer buffer = asFloat ? _allocateFloatBuffer(size) : _allocateDoubleBuffer(size); try {
		for(int i = off; i < len; i++) { FVecN genType = genTypes[i];
			for(int j = 0; j < genType.size; j++) {
				if(asFloat) ((FloatBuffer) buffer).put((float) genType.d[j]);
				else ((DoubleBuffer) buffer).put(genType.d[j]);
		} } setData(buffer, usage); } finally { _deallocate(buffer); }
	}
	public void setData(FVecN[] genTypes, int len, boolean asFloat, int usage) { setData(genTypes, 0, len, asFloat, usage); }
	public void setData(FVecN[] genTypes, boolean asFloat, int usage) { setData(genTypes, genTypes.length, asFloat, usage); }
	public void setData(FVecN[] genType, boolean asFloat) { setData(genType, asFloat, getGL().GL_STATIC_DRAW()); }
	public void setData(FVecN[] genType) { setData(genType, true); }
	public void setData(FVecN genType, boolean asFloat, int usage) { setData(new FVecN[] { genType }, asFloat, usage); }
	public void setData(FVecN genType, boolean asFloat) { setData(genType, asFloat, getGL().GL_STATIC_DRAW()); }
	public void setData(FVecN genType) { setData(genType, true); }
	public void setData(VecN[] genTypes, int off, int len, boolean asFloat, int usage) {
		assertIndex(off, genTypes.length, len);
		int size = 0; for(int i = off; i < len; i++) size += genTypes[i].size;
		Buffer buffer = asFloat ? _allocateFloatBuffer(size) : _allocateDoubleBuffer(size); try {
		for(int i = off; i < len; i++) { VecN genType = genTypes[i];
			for(int j = 0; j < genType.size; j++) {
				if(asFloat) ((FloatBuffer) buffer).put((float) genType.d[j]);
				else ((DoubleBuffer) buffer).put(genType.d[j]);
		} } setData(buffer, usage); } finally { _deallocate(buffer); }
	}
	public void setData(VecN[] genTypes, int len, boolean asFloat, int usage) { setData(genTypes, 0, len, asFloat, usage); }
	public void setData(VecN[] genTypes, boolean asFloat, int usage) { setData(genTypes, genTypes.length, asFloat, usage); }
	public void setData(VecN[] genType, boolean asFloat) { setData(genType, asFloat, getGL().GL_STATIC_DRAW()); }
	public void setData(VecN[] genType) { setData(genType, true); }
	public void setData(VecN genType, boolean asFloat, int usage) { setData(new VecN[] { genType }, asFloat, usage); }
	public void setData(VecN genType, boolean asFloat) { setData(genType, asFloat, getGL().GL_STATIC_DRAW()); }
	public void setData(VecN genType) { setData(genType, true); }
	public <T extends Number> void setData(List<T> list, int off, int len, int usage) {
		assertIndex(off, list.size(), len);
		int size = 0; for(int i = off; i < len; i++) { T data = list.get(i);
			if(data instanceof Byte) { size += 1; continue; }
			if(data instanceof Short) { size += 2; continue; }
			if(data instanceof Integer) { size += 4; continue; }
			if(data instanceof Long) { size += 8; continue; }
			if(data instanceof Float) { size += 4; continue; }
			if(data instanceof Double) { size += 8; continue; }
			throw new IllegalArgumentException();
		} ByteBuffer _buffer = _allocateByteBuffer(size);
		for(int i = off; i < len; i++) { T data = list.get(i);
			if(data instanceof Byte) { _buffer.put((Byte) data); continue; }
			if(data instanceof Short) { _buffer.putShort((Short) data); continue; }
			if(data instanceof Integer) { _buffer.putInt((Integer) data); continue; }
			if(data instanceof Long) { _buffer.putLong((Long) data); continue; }
			if(data instanceof Float) { _buffer.putFloat((Float) data); continue; }
			if(data instanceof Double) { _buffer.putDouble((Double) data); continue; }
		} try { setData(_buffer, usage); } finally { _deallocate(_buffer); }
	}
	public <T extends Number> void setData(List<T> list, int len, int usage) { setData(list, 0, len, usage); }
	public <T extends Number> void setData(List<T> list, int usage) { setData(list, list.size(), usage); }
	public <T extends Number> void setData(List<T> list) { setData(list, getGL().GL_STATIC_DRAW()); }
	public void setData(List<GenType> genTypes, int off, int len, boolean asFloat, int usage) {
		assertIndex(off, genTypes.size(), len);
		int size = 0; for(int i = off; i < len; i++) size += genTypes.get(i).size;
		Buffer buffer = asFloat ? _allocateFloatBuffer(size) : _allocateDoubleBuffer(size); try {
		for(int i = off; i < len; i++) { GenType genType = genTypes.get(i);
			for(int j = 0; j < genType.size; j++) {
				if(asFloat) ((FloatBuffer) buffer).put(((Number) genType.getVectorAt(j)).floatValue());
				else ((DoubleBuffer) buffer).put(((Number) genType.getVectorAt(j)).doubleValue());
			} } setData(buffer, usage);
		} finally { _deallocate(buffer); }
	}
	public void setData(List<GenType> genTypes, int len, boolean asFloat, int usage) { setData(genTypes, 0, len, asFloat, usage); }
	public void setData(List<GenType> genTypes, boolean asFloat, int usage) { setData(genTypes, genTypes.size(), asFloat, usage); }
	public void setData(List<GenType> genType, boolean asFloat) { setData(genType, asFloat, getGL().GL_STATIC_DRAW()); }
	public void setDataG(List<GenType> genType) { setData(genType, true); }
	public void setDataIVec(List<? extends IVecN> genTypes, int off, int len, boolean asFloat, int usage) {
		assertIndex(off, genTypes.size(), len); int count = 0;
		int size = 0; for(int i = off; i < len; i++) size += genTypes.get(i).size;
		IntBuffer buffer = _allocateIntBuffer(size); try {
		for(int i = off; i < len; i++) { IVecN genType = genTypes.get(i);
			toBuffer(genType, 0, buffer, count); count += genType.size;
		} setData(buffer, usage); } finally { _deallocate(buffer); }
	}
	public void setDataIVec(List<? extends IVecN> genTypes, int len, boolean asFloat, int usage) { setDataIVec(genTypes, 0, len, asFloat, usage); }
	public void setDataIVec(List<? extends IVecN> genTypes, boolean asFloat, int usage) { setDataIVec(genTypes, genTypes.size(), asFloat, usage); }
	public void setDataIVec(List<? extends IVecN> genType, boolean asFloat) { setDataIVec(genType, asFloat, getGL().GL_STATIC_DRAW()); }
	public void setDataIVec(List<? extends IVecN> genType) { setDataIVec(genType, true); }
	public void setDataLVec(List<? extends LVecN> genTypes, int off, int len, boolean asFloat, int usage) {
		assertIndex(off, genTypes.size(), len); int count = 0;
		int size = 0; for(int i = off; i < len; i++) size += genTypes.get(i).size;
		LongBuffer buffer = _allocateLongBuffer(size); try {
		for(int i = off; i < len; i++) { LVecN genType = genTypes.get(i);
			toBuffer(genType, 0, buffer, count); count += genType.size;
		} setData(buffer, usage); } finally { _deallocate(buffer); }
	}
	public void setDataLVec(List<? extends LVecN> genTypes, int len, boolean asFloat, int usage) { setDataLVec(genTypes, 0, len, asFloat, usage); }
	public void setDataLVec(List<? extends LVecN> genTypes, boolean asFloat, int usage) { setDataLVec(genTypes, genTypes.size(), asFloat, usage); }
	public void setDataLVec(List<? extends LVecN> genType, boolean asFloat) { setDataLVec(genType, asFloat, getGL().GL_STATIC_DRAW()); }
	public void setDataLVec(List<? extends LVecN> genType) { setDataLVec(genType, true); }
	public void setDataSVec(List<? extends SVecN> genTypes, int off, int len, boolean asFloat, int usage) {
		assertIndex(off, genTypes.size(), len); int count = 0;
		int size = 0; for(int i = off; i < len; i++) size += genTypes.get(i).size;
		ShortBuffer buffer = _allocateShortBuffer(size); try {
		for(int i = off; i < len; i++) { SVecN genType = genTypes.get(i);
			toBuffer(genType, 0, buffer, count); count += genType.size;
		} setData(buffer, usage); } finally { _deallocate(buffer); }
	}
	public void setDataSVec(List<? extends SVecN> genTypes, int len, boolean asFloat, int usage) { setDataSVec(genTypes, 0, len, asFloat, usage); }
	public void setDataSVec(List<? extends SVecN> genTypes, boolean asFloat, int usage) { setDataSVec(genTypes, genTypes.size(), asFloat, usage); }
	public void setDataSVec(List<? extends SVecN> genType, boolean asFloat) { setDataSVec(genType, asFloat, getGL().GL_STATIC_DRAW()); }
	public void setDataSVec(List<? extends SVecN> genType) { setDataSVec(genType, true); }
	public void setDataFVec(List<? extends FVecN> genTypes, int off, int len, boolean asFloat, int usage) {
		assertIndex(off, genTypes.size(), len);
		int size = 0; for(int i = off; i < len; i++) size += genTypes.get(i).size;
		Buffer buffer = asFloat ? _allocateFloatBuffer(size) : _allocateDoubleBuffer(size); try {
		for(int i = off; i < len; i++) { FVecN genType = genTypes.get(i);
			for(int j = 0; j < genType.size; j++) {
				if(asFloat) ((FloatBuffer) buffer).put((float) genType.d[j]);
				else ((DoubleBuffer) buffer).put(genType.d[j]);
		} } setData(buffer, usage); } finally { _deallocate(buffer); }
	}
	public void setDataFVec(List<? extends FVecN> genTypes, int len, boolean asFloat, int usage) { setDataFVec(genTypes, 0, len, asFloat, usage); }
	public void setDataFVec(List<? extends FVecN> genTypes, boolean asFloat, int usage) { setDataFVec(genTypes, genTypes.size(), asFloat, usage); }
	public void setDataFVec(List<? extends FVecN> genType, boolean asFloat) { setDataFVec(genType, asFloat, getGL().GL_STATIC_DRAW()); }
	public void setDataFVec(List<? extends FVecN> genType) { setDataFVec(genType, true); }
	public void setDataVec(List<? extends VecN> genTypes, int off, int len, boolean asFloat, int usage) {
		assertIndex(off, genTypes.size(), len);
		int size = 0; for(int i = off; i < len; i++) size += genTypes.get(i).size;
		Buffer buffer = asFloat ? _allocateFloatBuffer(size) : _allocateDoubleBuffer(size); try {
		for(int i = off; i < len; i++) { VecN genType = genTypes.get(i);
			for(int j = 0; j < genType.size; j++) {
				if(asFloat) ((FloatBuffer) buffer).put((float) genType.d[j]);
				else ((DoubleBuffer) buffer).put(genType.d[j]);
		} } setData(buffer, usage); } finally { _deallocate(buffer); }
	}
	public void setDataVec(List<? extends VecN> genTypes, int len, boolean asFloat, int usage) { setDataVec(genTypes, 0, len, asFloat, usage); }
	public void setDataVec(List<? extends VecN> genTypes, boolean asFloat, int usage) { setDataVec(genTypes, genTypes.size(), asFloat, usage); }
	public void setDataVec(List<? extends VecN> genType, boolean asFloat) { setDataVec(genType, asFloat, getGL().GL_STATIC_DRAW()); }
	public void setDataVec(List<? extends VecN> genType) { setDataVec(genType, true); }

	@Override protected void arrange() {
		VertexArrayObject<?> vao = null;
		if(parent == null) {
			vao = (VertexArrayObject<?>) getBindableNative(getGL(), VertexArrayObject.TYPE);
			if(vao != null) vao.unbind();
		} else parent.assertBind();
		getInstance().setId(getGL().createVertexBufferObject());
		if(vao != null) vao.bind();
	}
	@Override protected void disarrange() {
		VertexArrayObject<?> vao = null;
		if(parent == null) {
			vao = (VertexArrayObject<?>) getBindableNative(getGL(), VertexArrayObject.TYPE);
			if(vao != null) vao.unbind();
		} else parent.assertBind();
		if(isBind()) unbind();
		if(parent != null) parent.detachBufferObject(this);
		getGL().destroyVertexBufferObject(getId());
		if(vao != null) vao.bind();
	}

	@Override public void bind() {
		assertNotDead(); assertCreated(); if(parent != null) parent.assertBind(); assertNotBind();
		BindableNative bindableNative = getBindableNative(getGL(), getIdentifier());
		if(bindableNative != null) bindableNative.unbind();
		getGL().glBindBuffer(target, getId());
		setBindableNative(getGL(), getIdentifier(), this);
	}
	@Override public void unbind() {
		assertNotDead(); assertCreated(); if(parent != null && !parent.isBind()) return; assertBind();
		getGL().glBindBuffer(target, null);
		setBindableNative(getGL(), getIdentifier(), null);
	}

	protected String identifier;
	protected String getIdentifier() { if(identifier == null) identifier = TYPE + ":" + (parent != null ? parent.getId() : 0) + ":" + target; return identifier; }
}
