package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL;

import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class VertexArrayObject<ID extends Number> extends OpenGLNativeHolder<ID> implements OpenGLNativeHolder.BindableNative {
	public static final String TYPE = "VERTEXARRAYOBJECT";
	protected final ArrayList<VertexBufferObject<ID>> bufferObjects;

	public VertexArrayObject(GLContext<ID> gl) {
		super(gl);
		this.bufferObjects = Pool.tryBorrow(Pool.getPool(ArrayList.class));
	}

	public VertexBufferObject<ID>[] getBufferObjects() { assertNotDead(); return bufferObjects.toArray(new VertexBufferObject[0]); }
	@Override public boolean isBind() { return getBindableNative(getGL(), TYPE) == this; }

	public VertexBufferObject<ID> createBufferObject(int target) {
		assertNotDead(); assertCreated(); assertBind();
		VertexBufferObject<ID> vbo = getGL().constructVertexBufferObject(this, target);
		vbo.create(); return vbo;
	}
	public void destroyBufferObject(VertexBufferObject<ID> vbo) {
		assertNotDead(); assertCreated(); assertBind(); vbo.assertNotDead();
		vbo.assertCreated(); assertContextSame(vbo); vbo.destroy();
	}

	protected void attachBufferObject(VertexBufferObject<ID> vbo) {
		assertNotDead(); assertCreated(); assertBind(); vbo.assertNotDead("buffer object"); assertContextSame(vbo);
		bufferObjects.add(vbo);
	}
	protected void detachBufferObject(VertexBufferObject<ID> vbo) {
		assertNotDead(); assertCreated(); assertBind(); vbo.assertNotDead("buffer object"); assertContextSame(vbo);
		bufferObjects.remove(vbo);
	}

	@Override protected void arrange() {
		getInstance().setId(getGL().createVertexArrayObject());
	}
	@Override protected void disarrange() {
		if(!isBind()) bind();
		for(VertexBufferObject<ID> vbo : bufferObjects.toArray(new VertexBufferObject[0]))
			if(vbo.getId() != null && !vbo.isDead()) vbo.destroy();
		unbind(); getGL().destroyVertexArrayObject(getId());
		Pool.returnObject(ArrayList.class, bufferObjects);
	}

	@Override public void bind() {
		assertNotDead(); assertCreated(); assertNotBind();
		BindableNative bindableNative = getBindableNative(getGL(), TYPE);
		if(bindableNative != null) bindableNative.unbind();
		getGL().glBindVertexArray(getId());
		setBindableNative(getGL(), TYPE, this);
	}
	@Override public void unbind() {
		assertNotDead(); assertCreated(); assertBind();
		getGL().glBindVertexArray(null);
		setBindableNative(getGL(), TYPE, null);
	}
}
