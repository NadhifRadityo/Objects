package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL;

public class FrameBufferObject<ID extends Number> extends OpenGLNativeHolder<ID> implements OpenGLNativeHolder.FinalableNative, OpenGLNativeHolder.BindableNative, OpenGLNativeHolder.SlotableNative {
	public static final String TYPE = "FRAMEBUFFER";
	protected final int target;
	private boolean isFinal = false;

	protected Texture<ID> attachedTexture;
	protected Texture<ID> attachedDepthTexture;
	protected RenderBufferObject<ID> attachedRenderBuffer;

	public FrameBufferObject(GLContext<ID> gl, int target) {
		super(gl);
		this.target = target;
	}

	public int getTarget() { return target; }
	@Override public boolean isFinal() { return isFinal; }
	@Override public boolean isBind() { return getBindableNative(getGL(), getIdentifier()) == this; }
	@Override public int getCurrentSlot() { return getSlotableNative(getGL(), TYPE, this); }

	@Override public void setFinal(boolean isFinal) {
		assertNotDead(); assertCreated(); assertNotBind();
		if(this.isFinal == isFinal) return; this.isFinal = isFinal;
	}

	public Texture<ID> getAttachedTexture() { assertNotDead(); return attachedTexture; }
	public Texture<ID> getAttachedDepthTexture() { assertNotDead(); return attachedDepthTexture; }
	public RenderBufferObject<ID> getAttachedRenderBuffer() { assertNotDead(); return attachedRenderBuffer; }

	public void attachTexture(Texture<ID> texture) {
		assertNotDead(); assertCreated(); assertBind(); assertSlot();
		if(texture == null) { getGL().glFramebufferTexture2D(target, getGL().GL_COLOR_ATTACHMENT()[getCurrentSlot()], 0, null, 0); this.attachedTexture = null; return; }
		texture.assertNotDead("texture"); texture.assertCreated(); assertContextSame(texture);
		getGL().glFramebufferTexture2D(target, getGL().GL_COLOR_ATTACHMENT()[getCurrentSlot()], texture.getTarget(), texture.getId(), 0);
		this.attachedTexture = texture;
	}
	public void attachDepthTexture(Texture<ID> depthTexture) {
		assertNotDead(); assertCreated(); assertBind(); depthTexture.assertNotDead("texture"); depthTexture.assertCreated(); assertContextSame(depthTexture);
		if(depthTexture == null) { getGL().glFramebufferTexture2D(target, getGL().GL_DEPTH_ATTACHMENT(), 0, null, 0); this.attachedDepthTexture = null; return; }
		getGL().glFramebufferTexture2D(target, getGL().GL_DEPTH_ATTACHMENT(), depthTexture.getTarget(), depthTexture.getId(), 0);
		this.attachedDepthTexture = depthTexture;
	}
	public void attachDepthBuffer(RenderBufferObject<ID> renderBuffer) {
		assertNotDead(); assertCreated(); assertBind();
		if(renderBuffer == null) { getGL().glFramebufferRenderbuffer(target, getGL().GL_DEPTH_ATTACHMENT(), 0, null); this.attachedRenderBuffer = null; return; }
		renderBuffer.assertNotDead("render buffer"); renderBuffer.assertCreated(); assertContextSame(renderBuffer);
		getGL().glFramebufferRenderbuffer(target, getGL().GL_DEPTH_ATTACHMENT(), renderBuffer.getTarget(), renderBuffer.getId());
		this.attachedRenderBuffer = renderBuffer;
	}

	@Override protected void arrange() {
		getInstance().setId(getGL().createFrameBufferObject());
	}
	@Override protected void disarrange() {
		if(isBind()) unbind();
		if(getCurrentSlot() != SLOT_NULL) deallocateSlot();
		getGL().destroyFrameBufferObject(getId());
	}

	@Override public void bind() {
		assertNotDead(); assertCreated(); assertNotBind();
		BindableNative bindableNative = getBindableNative(getGL(), getIdentifier());
		if(bindableNative != null) bindableNative.unbind();
		getGL().glBindFramebuffer(target, getId());
		setBindableNative(getGL(), getIdentifier(), this);
	}
	@Override public void unbind() {
		assertNotDead(); assertCreated(); assertBind();
		getGL().glBindFramebuffer(target, null);
		setBindableNative(getGL(), getIdentifier(), null);
	}

	@Override public void allocateSlot() {
		assertNotDead(); assertCreated(); assertBind(); assertNotSlot();
		int slot = setSlotableNative(getGL(), TYPE, this);
		getGL().glDrawBuffer(getGL().GL_COLOR_ATTACHMENT()[slot]);
	}
	@Override public void deallocateSlot() {
		boolean isBind = isBind(); if(!isBind) bind();
		assertNotDead(); assertCreated(); assertSlot();
		setSlotableNative(getGL(), TYPE, getCurrentSlot(), null);
		if(!isBind) unbind();
	}

	protected String identifier;
	protected String getIdentifier() { if(identifier == null) identifier = TYPE + ":" + target; return identifier; }
}
