package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;

public class RenderBufferObject<ID extends Number> extends OpenGLNativeHolder<ID> implements OpenGLNativeHolder.BindableNative {
	public static final String TYPE = "RENDERBUFFEROBJECT";
	protected final int target;

	public RenderBufferObject(GLContext<ID> gl, int target) {
		super(gl);
		this.target = target;
	}

	public int getTarget() { return target; }
	@Override public boolean isBind() { return getBindableNative(getGL(), getIdentifier()) == this; }

	public void asSize(int internalFormat, int width, int height) {
		assertNotDead(); assertCreated(); assertBind();
		getGL().glRenderbufferStorage(target, internalFormat, width, height);
	}
	public void asSize(int internalFormat, Size size) { asSize(internalFormat, size.getWidth(), size.getHeight()); }

	@Override protected void arrange() {
		getInstance().setId(getGL().createRenderBufferObject());
	}
	@Override protected void disarrange() {
		if(isBind()) unbind();
		getGL().destroyRenderBufferObject(getId());
	}

	@Override public void bind() {
		assertNotDead(); assertCreated(); assertNotBind();
		BindableNative bindableNative = getBindableNative(getGL(), getIdentifier());
		if(bindableNative != null) bindableNative.unbind();
		getGL().glBindRenderbuffer(target, getId());
		setBindableNative(getGL(), getIdentifier(), this);
	}
	@Override public void unbind() {
		assertNotDead(); assertCreated(); assertBind();
		getGL().glBindRenderbuffer(target, null);
		setBindableNative(getGL(), getIdentifier(), null);
	}

	protected String identifier;
	protected String getIdentifier() { if(identifier == null) identifier = TYPE + ":" + target; return identifier; }
}
