package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.BVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FMatMxN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.MatMxN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.VecN;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.ObjectsProgram;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Transformation;
import io.github.NadhifRadityo.Objects.Object.DeadableObject;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Object.ThrowsReferencedCallback;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ByteUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.GameEngine.Debug;
import io.github.NadhifRadityo.Objects.Utilizations.ListUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.allocateIntBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.deallocate;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.getCharBytesSize;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.getTempByteBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.getTempDoubleBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.getTempFloatBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.getTempIntBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.BufferUtils.getTempShortBuffer;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.toBuffer;

public class Program<ID extends Number> extends OpenGLNativeHolder<ID> implements OpenGLNativeHolder.FinalableNative, OpenGLNativeHolder.BindableNative {
	public static final String TYPE = "PROGRAM";
	protected final ArrayList<Shader<ID>> shaders;
	protected final HashMap<String, GlobalVarGLSLInfo> variables;
	protected final HashMap<Class, ObjectsProgram.GlobalVarGLSLInfoPack> cachedVariablesPack;
	protected final ArrayList<ObjectsProgram> boundObjects;
	private boolean isFinal = false;

	public Program(GLContext<ID> gl) {
		super(gl);
		this.shaders = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		this.variables = Pool.tryBorrow(Pool.getPool(HashMap.class));
		this.cachedVariablesPack = Pool.tryBorrow(Pool.getPool(HashMap.class));
		this.boundObjects = Pool.tryBorrow(Pool.getPool(ArrayList.class));
	}

	public Shader<ID>[] getShaders() { assertNotDead(); return shaders.toArray(new Shader[0]); }
	public ObjectsProgram[] getObjects() { assertNotDead(); return boundObjects.toArray(new ObjectsProgram[0]); }
	@Override public boolean isFinal() { return isFinal; }
	@Override public boolean isBind() { return getBindableNative(getGL(), TYPE) == this; }

	@Override public void setFinal(boolean isFinal) {
		assertNotDead(); assertCreated(); assertNotBind(); if(this.isFinal == isFinal) return;
		if(!isFinal && !boundObjects.isEmpty()) throw newException("Objects bound.");
		this.isFinal = isFinal; for(GlobalVarGLSLInfo variable : variables.values()) variable.setDead();
		variables.clear(); cachedVariablesPack.clear(); if(!isFinal) return;
		getGL().glLinkProgram(getId()); getGL().glValidateProgram(getId());
		IntBuffer size = allocateIntBuffer(1);
		IntBuffer type = allocateIntBuffer(1);
		ByteBuffer nameB = getTempByteBuffer(getCharBytesSize(128));
		String name;

		int uniformsCount = getGL().getProgramUniformsCount(getId());
		for(int i = 0; i < uniformsCount; i++) {
			name = getGL().glGetActiveUniform(getId(), i, size, type); size.rewind(); type.rewind();
			variables.put(name, new GlobalVarGLSLInfo(VarGLSLKind.UNIFORM, size.get(0), type.get(0), name, getGL().glGetUniformLocation(getId(), name)));
		}
		int attributesCount = getGL().getProgramAttributesCount(getId());
		for(int i = 0; i < attributesCount; i++) {
			name = getGL().glGetActiveAttrib(getId(), i, size, type); size.rewind(); type.rewind();
			variables.put(name, new GlobalVarGLSLInfo(VarGLSLKind.ATTRIBUTE, size.get(0), type.get(0), name, getGL().glGetAttribLocation(getId(), name)));
		}
		getGL().glGetProgramInterfaceiv(getId(), getGL().GL_PROGRAM_OUTPUT(), getGL().GL_ACTIVE_RESOURCES(), size);
		size.rewind(); int fragmentOutCount = size.get(0);
		for(int i = 0; i < fragmentOutCount; i++) {
			getGL().glGetProgramResourceName(getId(), getGL().GL_PROGRAM_OUTPUT(), i, size, nameB);
			size.rewind(); nameB.rewind();
			if(size.get(0) >= nameB.capacity()) {
				nameB = getTempByteBuffer(size.get(0) + 1);
				getGL().glGetProgramResourceName(getId(), getGL().GL_PROGRAM_OUTPUT(), i, size, nameB);
				size.rewind(); nameB.rewind();
			}
			name = ByteUtils.toZeroTerminatedString(nameB);
			variables.put(name, new GlobalVarGLSLInfo(VarGLSLKind.FRAGMENT_OUT, -1, -1, name, getGL().glGetProgramResourceLocation(getId(), getGL().GL_PROGRAM_OUTPUT(), nameB)));
			nameB.rewind();
		}
		deallocate(size); deallocate(type);
	}

	public int getUniformsCount() { assertNotDead(); assertFinal(); int result = 0; for(GlobalVarGLSLInfo variable : variables.values()) if(variable.getKind() == VarGLSLKind.UNIFORM) result++; return result; }
	public int getAttributesCount() { assertNotDead(); assertFinal(); int result = 0; for(GlobalVarGLSLInfo variable : variables.values()) if(variable.getKind() == VarGLSLKind.ATTRIBUTE) result++; return result; }
	public int getFragmentOutsCount() { assertNotDead(); assertFinal(); int result = 0; for(GlobalVarGLSLInfo variable : variables.values()) if(variable.getKind() == VarGLSLKind.FRAGMENT_OUT) result++; return result; }
	public Map<String, GlobalVarGLSLInfo> getVariablesList() { assertNotDead(); assertFinal(); return Collections.unmodifiableMap(variables); }
	public GlobalVarGLSLInfo getVariableInformations(String variableName) { assertNotDead(); assertFinal(); GlobalVarGLSLInfo result = variables.get(variableName); if(result == null && getGL() instanceof Debug.DebuggerAttach) result = new DummyVarGLSLInfo(variableName); return result; }
	public <PACK extends ObjectsProgram.GlobalVarGLSLInfoPack> PACK getVariablesPack(Class<PACK> variablesPack, ReferencedCallback<PACK> create) { assertNotDead(); assertFinal(); PACK result = (PACK) cachedVariablesPack.get(variablesPack); if(result == null && create != null) { result = create.get(); cachedVariablesPack.put(variablesPack, result); } return result; }
	public <PACK extends ObjectsProgram.GlobalVarGLSLInfoPack> PACK getVariablesPack(Class<PACK> variablesPack) {
		PACK result = getVariablesPack(variablesPack, null); if(result != null) return result;
		ReferencedCallback<PACK> create = ExceptionUtils.doSilentThrowsReferencedCallback((ReferencedCallback<ReferencedCallback<PACK>>) (args) -> null, (args) -> {
			Field FIELD_create = variablesPack.getDeclaredField("create"); int modifiers = FIELD_create.getModifiers();
			return !Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers) || !Modifier.isFinal(modifiers) ? null : (ReferencedCallback<PACK>) FIELD_create.get(null);
		}); return getVariablesPack(variablesPack, create);
	}
	public void reserveVariableAddress(String variableName, int variableAddress) {
		assertNotDead(); assertCreated(); assertNotFinal();
		if(variableName != null) getGL().glBindAttribLocation(getId(), variableAddress, variableName);
	}

	public void attachShader(Shader<ID> shader) {
		assertNotDead(); assertCreated(); assertNotFinal(); shader.assertNotDead("shader"); shader.assertCreated(); assertContextSame(shader);
		getGL().glAttachShader(getId(), shader.getId());
		shaders.add(shader);
	}
	public void detachShader(Shader<ID> shader) {
		assertNotDead(); assertCreated(); assertNotFinal(); shader.assertNotDead("shader"); shader.assertCreated(); assertContextSame(shader);
		if(!shaders.contains(shader)) return;
		getGL().glDetachShader(getId(), shader.getId());
		shaders.remove(shader);
	}

	public void attachObject(ObjectsProgram object) {
		assertNotDead(); assertCreated(); assertFinal(); object.assertNotDead("object"); assertContextSame(object);
		boundObjects.add(object);
	}
	public void detachObject(ObjectsProgram object) {
		assertNotDead(); assertCreated(); assertFinal(); object.assertNotDead("object"); assertContextSame(object);
		boundObjects.remove(object);
	}

	@Override public void arrange() {
		getInstance().setId(getGL().createProgram());
		String errorMessage = getGL().getProgramLogInfo(getId());
		if(errorMessage.length() > 0) throw newException("Compile failed! Error " + errorMessage);
	}
	@Override public void disarrange() {
		if(isBind()) unbind(); if(isFinal()) setFinal(false);
		for(Shader<ID> shader : shaders.toArray(new Shader[0])) {
			detachShader(shader); shader.destroy();
		} getGL().destroyProgram(getId());
		Pool.returnObject(ArrayList.class, shaders);
		Pool.returnObject(HashMap.class, variables);
		Pool.returnObject(HashMap.class, cachedVariablesPack);
		Pool.returnObject(HashMap.class, boundObjects);
	}

	@Override public void bind() {
		assertNotDead(); assertCreated(); assertFinal(); assertNotBind();
		BindableNative bindableNative = getBindableNative(getGL(), TYPE);
		if(bindableNative != null) bindableNative.unbind();
		getGL().glUseProgram(getId());
		setBindableNative(getGL(), TYPE, this);
		Iterator<ObjectsProgram> it = ListUtils.reusableIterator(boundObjects);
		while(it.hasNext()) { ObjectsProgram injector = it.next(); injector.onProgramBind(); }
	}
	@Override public void unbind() {
		assertNotDead(); assertCreated(); assertFinal(); assertBind();
		getGL().glUseProgram(null);
		setBindableNative(getGL(), TYPE, null);
		Iterator<ObjectsProgram> it = ListUtils.reusableIterator(boundObjects);
		while(it.hasNext()) { ObjectsProgram injector = it.next(); injector.onProgramUnbind(); }
	}

	public enum VarGLSLKind {
		UNIFORM, ATTRIBUTE, FRAGMENT_OUT, DUMMY
	}
	public class GlobalVarGLSLInfo implements DeadableObject {
		protected final VarGLSLKind kind;
		protected final int size;
		protected final int type;
		protected final String name;
		protected final int address;
		protected boolean isDead;

		protected GlobalVarGLSLInfo(VarGLSLKind kind, int size, int type, String name, int address) {
			this.kind = kind;
			this.size = size;
			this.type = type;
			this.name = name;
			this.address = address;
		}

		public VarGLSLKind getKind() { return kind; }
		public int getSize() { return size; }
		public int getType() { return type; }
		public String getName() { return name; }
		public int getAddress() { return address; }
		@Override public boolean isDead() { return isDead; }
		@Override public void setDead() { this.isDead = true; }

		public void loadI(IVecN what) { IntBuffer buffer = getTempIntBuffer(what.size); toBuffer(what, 0, buffer, 0, what.size); load(buffer, what.size); }
		public void loadI(int[] what) { IntBuffer buffer = getTempIntBuffer(what.length); toBuffer(what, 0, buffer, 0, what.length); load(buffer, what.length); }
		public void loadI(int what) { IntBuffer buffer = getTempIntBuffer(1); buffer.put(0, what); load(buffer, 1); }
		public void loadI(Size what) { IntBuffer buffer = getTempIntBuffer(2); buffer.put(0, what.getWidth()); buffer.put(1, what.getHeight()); load(buffer, 2); }
		public void loadB(BVecN what) { IntBuffer buffer = getTempIntBuffer(what.size); int fit = (what.size / 4) * 4; int i = 0; for(; i < fit; i += 4) { buffer.put(i, what.d[i] ? 1 : 0); buffer.put(i + 1, what.d[i + 1] ? 1 : 0); buffer.put(i + 2, what.d[i + 2] ? 1 : 0); buffer.put(i + 3, what.d[i + 3] ? 1 : 0); } for(; i < what.size; i++) buffer.put(i, what.d[i] ? 1 : 0); load(buffer, what.size); }
		public void loadB(boolean[] what) { IntBuffer buffer = getTempIntBuffer(what.length); int fit = (what.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { buffer.put(i, what[i] ? 1 : 0); buffer.put(i + 1, what[i + 1] ? 1 : 0); buffer.put(i + 2, what[i + 2] ? 1 : 0); buffer.put(i + 3, what[i + 3] ? 1 : 0); } for(; i < what.length; i++) buffer.put(i, what[i] ? 1 : 0); load(buffer, what.length); }
		public void loadB(boolean what) { IntBuffer buffer = getTempIntBuffer(1); buffer.put(0, what ? 1 : 0); load(buffer, 1); }
		public void loadS(SVecN what) { ShortBuffer buffer = getTempShortBuffer(what.size); toBuffer(what, 0, buffer, 0, what.size); load(buffer, what.size); }
		public void loadS(short[] what) { ShortBuffer buffer = getTempShortBuffer(what.length); toBuffer(what, 0, buffer, 0, what.length); load(buffer, what.length); }
		public void loadS(short what) { ShortBuffer buffer = getTempShortBuffer(1); buffer.put(0, what); load(buffer, 1); }
		public void loadF(FVecN what) { FloatBuffer buffer = getTempFloatBuffer(what.size); toBuffer(what, 0, buffer, 0, what.size); load(buffer, what.size); }
		public void loadF(float[] what) { FloatBuffer buffer = getTempFloatBuffer(what.length); toBuffer(what, 0, buffer, 0, what.length); load(buffer, what.length); }
		public void loadF(float what) { FloatBuffer buffer = getTempFloatBuffer(1); buffer.put(0, what); load(buffer, 1); }
		public void loadF(VecN what) { FloatBuffer buffer = getTempFloatBuffer(what.size); int fit = (what.size / 4) * 4; int i = 0; for(; i < fit; i += 4) { buffer.put(i, (float) what.d[i]); buffer.put(i + 1, (float) what.d[i + 1]); buffer.put(i + 2, (float) what.d[i + 2]); buffer.put(i + 3, (float) what.d[i + 3]); } for(; i < what.size; i++) buffer.put(i, (float) what.d[i]); load(buffer, what.size); }
		public void loadF(double[] what) { FloatBuffer buffer = getTempFloatBuffer(what.length); int fit = (what.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { buffer.put(i, (float) what[i]); buffer.put(i + 1, (float) what[i + 1]); buffer.put(i + 2, (float) what[i + 2]); buffer.put(i + 3, (float) what[i + 3]); } for(; i < what.length; i++) buffer.put(i, (float) what[i]); load(buffer, what.length); }
		public void loadF(double what) { FloatBuffer buffer = getTempFloatBuffer(1); buffer.put(0, (float) what); load(buffer, 1); }
		public void loadD(VecN what) { DoubleBuffer buffer = getTempDoubleBuffer(what.size); toBuffer(what, 0, buffer, 0, what.size); load(buffer, what.size); }
		public void loadD(double[] what) { DoubleBuffer buffer = getTempDoubleBuffer(what.length); toBuffer(what, 0, buffer, 0, what.length); load(buffer, what.length); }
		public void loadD(double what) { DoubleBuffer buffer = getTempDoubleBuffer(1); buffer.put(0, what); load(buffer, 1); }
		public void loadD(FVecN what) { DoubleBuffer buffer = getTempDoubleBuffer(what.size); int fit = (what.size / 4) * 4; int i = 0; for(; i < fit; i += 4) { buffer.put(i, what.d[i]); buffer.put(i + 1, what.d[i + 1]); buffer.put(i + 2, what.d[i + 2]); buffer.put(i + 3, what.d[i + 3]); } for(; i < what.size; i++) buffer.put(i, what.d[i]); load(buffer, what.size); }
		public void loadD(float[] what) { DoubleBuffer buffer = getTempDoubleBuffer(what.length); int fit = (what.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { buffer.put(i, what[i]); buffer.put(i + 1, what[i + 1]); buffer.put(i + 2, what[i + 2]); buffer.put(i + 3, what[i + 3]); } for(; i < what.length; i++) buffer.put(i, what[i]); load(buffer, what.length); }
		public void loadD(float what) { DoubleBuffer buffer = getTempDoubleBuffer(1); buffer.put(0, what); load(buffer, 1); }
		public void loadMatF(FMatMxN what) { FloatBuffer buffer = getTempFloatBuffer(what.size); toBuffer(what, 0, buffer, 0, what.size); loadMat(buffer, what.m, what.n); }
		public void loadMatF(float[] what, int m, int n) { FloatBuffer buffer = getTempFloatBuffer(what.length); toBuffer(what, 0, buffer, 0, what.length); loadMat(buffer, m, n); }
		public void loadMatF(MatMxN what) { FloatBuffer buffer = getTempFloatBuffer(what.size); int fit = (what.size / 4) * 4; int i = 0; for(; i < fit; i += 4) { buffer.put(i, (float) what.d[i]); buffer.put(i + 1, (float) what.d[i + 1]); buffer.put(i + 2, (float) what.d[i + 2]); buffer.put(i + 3, (float) what.d[i + 3]); } for(; i < what.size; i++) buffer.put(i, (float) what.d[i]); loadMat(buffer, what.m, what.n); }
		public void loadMatF(double[] what, int m, int n) { FloatBuffer buffer = getTempFloatBuffer(what.length); int fit = (what.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { buffer.put(i, (float) what[i]); buffer.put(i + 1, (float) what[i + 1]); buffer.put(i + 2, (float) what[i + 2]); buffer.put(i + 3, (float) what[i + 3]); } for(; i < what.length; i++) buffer.put(i, (float) what[i]); loadMat(buffer, m, n); }
		public void loadMatD(MatMxN what) { DoubleBuffer buffer = getTempDoubleBuffer(what.size); toBuffer(what, 0, buffer, 0, what.size); loadMat(buffer, what.m, what.n); }
		public void loadMatD(double[] what, int m, int n) { DoubleBuffer buffer = getTempDoubleBuffer(what.length); toBuffer(what, 0, buffer, 0, what.length); loadMat(buffer, m, n); }
		public void loadMatD(FMatMxN what) { DoubleBuffer buffer = getTempDoubleBuffer(what.size); int fit = (what.size / 4) * 4; int i = 0; for(; i < fit; i += 4) { buffer.put(i, what.d[i]); buffer.put(i + 1, what.d[i + 1]); buffer.put(i + 2, what.d[i + 2]); buffer.put(i + 3, what.d[i + 3]); } for(; i < what.size; i++) buffer.put(i, what.d[i]); loadMat(buffer, what.m, what.n); }
		public void loadMatD(float[] what, int m, int n) { DoubleBuffer buffer = getTempDoubleBuffer(what.length); int fit = (what.length / 4) * 4; int i = 0; for(; i < fit; i += 4) { buffer.put(i, what[i]); buffer.put(i + 1, what[i + 1]); buffer.put(i + 2, what[i + 2]); buffer.put(i + 3, what[i + 3]); } for(; i < what.length; i++) buffer.put(i, what[i]); loadMat(buffer, m, n); }
		protected void load(Buffer what, int count) { assertNotDead(); Program.this.assertNotDead(); assertCreated(); assertFinal(); assertBind(); if(kind == VarGLSLKind.UNIFORM) getGL().glUniform(address, what, count); else if(kind == VarGLSLKind.ATTRIBUTE) getGL().glVertexAttrib(address, what, count); else throw newException("Illegal variable type"); }
		protected void loadMat(Buffer what, int m, int n) { assertNotDead(); Program.this.assertNotDead(); assertCreated(); assertFinal(); assertBind(); assertUniform(); getGL().glUniformMatrix(address, false, what, m, n); }

		public void giveInstruction(int size, int type, boolean normalized, int stride, long pointer) { assertNotDead(); Program.this.assertNotDead(); assertCreated(); assertFinal(); assertBind(); assertAttribute(); getGL().glVertexAttribPointer(address, size, type, normalized, stride, pointer); }
		public void enable() { assertNotDead(); Program.this.assertNotDead(); assertCreated(); assertFinal(); assertBind(); assertAttribute(); getGL().glEnableVertexAttribArray(address); }
		public void disable() { assertNotDead(); Program.this.assertNotDead(); assertCreated(); assertFinal(); assertBind(); assertAttribute(); getGL().glDisableVertexAttribArray(address); }
		public boolean isEnabled() { assertNotDead(); Program.this.assertNotDead(); assertCreated(); assertFinal(); assertBind(); assertAttribute(); return getGL().glGetVertexAttrib(address, getGL().GL_VERTEX_ATTRIB_ARRAY_ENABLED()) == getGL().GL_TRUE(); }

		public void assertUniform() { if(kind != VarGLSLKind.UNIFORM) throw newException("Not an uniform type!"); }
		public void assertAttribute() { if(kind != VarGLSLKind.ATTRIBUTE) throw newException("Not an attribute type!"); }
		public void assertFragmentOut() { if(kind != VarGLSLKind.FRAGMENT_OUT) throw newException("Not a fragment out type!"); }
		public void assertNotUniform() { if(kind == VarGLSLKind.UNIFORM) throw newException("Is an uniform type!"); }
		public void assertNotAttribute() { if(kind == VarGLSLKind.ATTRIBUTE) throw newException("Is an attribute type!"); }
		public void assertNotFragmentOut() { if(kind == VarGLSLKind.FRAGMENT_OUT) throw newException("Is a fragment out type!"); }

		@Override public String toString() {
			return "GlobalVarGLSLInfo{" +
					"kind=" + kind +
					", size=" + size +
					", type=" + type +
					", name='" + name + '\'' +
					", address=" + address +
					", isDead=" + isDead +
					'}';
		}
	}
	protected class DummyVarGLSLInfo extends GlobalVarGLSLInfo {
		protected DummyVarGLSLInfo(String name) { super(VarGLSLKind.DUMMY, 0, 0, name, -1); }

		@Override protected void load(Buffer what, int count) { }
		@Override protected void loadMat(Buffer what, int m, int n) { }

		@Override public void giveInstruction(int size, int type, boolean normalized, int stride, long pointer) { }
		@Override public void enable() { }
		@Override public void disable() { }
		@Override public boolean isEnabled() { return false; }

		@Override public void assertUniform() { }
		@Override public void assertAttribute() { }
		@Override public void assertFragmentOut() { }
		@Override public void assertNotUniform() { }
		@Override public void assertNotAttribute() { }
		@Override public void assertNotFragmentOut() { }
	}
}
