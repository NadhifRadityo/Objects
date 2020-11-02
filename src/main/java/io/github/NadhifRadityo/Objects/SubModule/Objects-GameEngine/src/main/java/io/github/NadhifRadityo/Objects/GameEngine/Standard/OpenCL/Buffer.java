package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.OpenGLNativeHolder;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.RenderBufferObject;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Texture;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.VertexBufferObject;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.PointerBuffer;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;

import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.ImageFormat.CHANNEL_DATA_TYPE;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.ImageFormat.CHANNEL_ORDER;

public class Buffer<ID extends Number, BUFFER extends java.nio.Buffer> extends Memory<ID, BUFFER> {
	protected final ArrayList<SubBuffer<ID, BUFFER>> subBuffers;

	public Buffer(CLContext<ID> cl, int size, BUFFER buffer, int flags) {
		super(cl, size, buffer, flags);
		this.subBuffers = Pool.tryBorrow(Pool.getPool(ArrayList.class));
	}

	public SubBuffer<ID, BUFFER>[] getSubBuffers() { assertNotDead(); assertCreated(); return subBuffers.toArray(new SubBuffer[0]); }

	protected void attachSubBuffers(SubBuffer<ID, BUFFER> buffer) {
		assertNotDead(); assertCreated(); buffer.assertNotDead("sub buffer"); assertContextSame(buffer);
		subBuffers.add(buffer);
	}
	protected void detachSubBuffers(SubBuffer<ID, BUFFER> buffer) {
		assertNotDead(); assertCreated(); buffer.assertNotDead("sub buffer"); assertContextSame(buffer);
		if(!subBuffers.contains(buffer)) return;
		subBuffers.remove(buffer);
	}

	@Override protected void arrange() throws Exception {
		BUFFER buffer = null;
		if(isHostPointerFlag()) { buffer = this.buffer;
			if(buffer == null) throw new IllegalArgumentException("no host pointer defined");
			if(!buffer.isDirect()) throw new IllegalArgumentException("buffer is not direct");
		} getInstance().setId(getCL().createBuffer(flags, size, buffer));
	}
	@Override protected void disarrange() {
		for(SubBuffer subBuffer : subBuffers.toArray(new SubBuffer[0]))
			if(subBuffer.getId() != null && !subBuffer.isDead()) subBuffer.destroy();
		super.disarrange();
		Pool.returnObject(ArrayList.class, subBuffers);
	}

	public static class SubBuffer<ID extends Number, BUFFER extends java.nio.Buffer> extends Buffer<ID, BUFFER> {
		protected final Buffer<ID, BUFFER> parent;
		protected final int offset;

		public SubBuffer(CLContext<ID> cl, Buffer<ID, BUFFER> parent, int offset, int size, int flags) {
			super(cl, size * (parent.getBuffer() != null ? BufferUtils.getElementSize(parent.getBuffer()) : 1),
					parent.getBuffer() == null ? null : BufferUtils.slice(parent.getBuffer(), offset, size), flags);
			this.parent = parent;
			this.offset = offset * (parent.getBuffer() != null ? BufferUtils.getElementSize(parent.getBuffer()) : 1);
			parent.attachSubBuffers(this);
		}

		public Buffer<ID, BUFFER> getParent() { return parent; }
		public int getOffset() { return offset; }

		@Override protected void arrange() throws Exception {
			PointerBuffer info = BufferUtils.allocatePointerBuffer(2);
			info.put(0, offset); info.put(1, size);
			getInstance().setId(getCL().createSubBuffer(parent.getId(), flags, getCL().CL_BUFFER_CREATE_TYPE_REGION(), info));
			BufferUtils.deallocate(info);
		}
		@Override protected void disarrange() {
			parent.detachSubBuffers(this);
			super.disarrange();
		}
	}

	public static abstract class ImageBuffer<ID extends Number, BUFFER extends java.nio.Buffer> extends Buffer<ID, BUFFER> {
		protected ImageFormat imageFormat;
		protected Size.LONG size;

		public ImageBuffer(CLContext<ID> cl, BUFFER buffer, ImageFormat imageFormat, Size.LONG size, int flags) {
			super(cl, 0, buffer, flags);
			this.imageFormat = imageFormat;
			this.size = size;
		}

		public ImageFormat getImageFormat() { return imageFormat; }
		public Size.LONG getSize() { return size; }

		protected abstract ID createBufferImage(BUFFER buffer) throws Exception;

		@Override protected void arrange() throws Exception {
			BUFFER buffer = null;
			if(isHostPointerFlag()) {
				if(buffer == null) throw new IllegalArgumentException("no host pointer defined");
				if(!buffer.isDirect()) throw new IllegalArgumentException("buffer is not direct");
				buffer = this.buffer;
			} getInstance().setId(createBufferImage(buffer));
			super.size = getSizeImpl();
		}

		public static class Image2DBuffer<ID extends Number, BUFFER extends java.nio.Buffer> extends ImageBuffer<ID, BUFFER> {
			protected final int rowPitch;

			public Image2DBuffer(CLContext<ID> cl, BUFFER buffer, ImageFormat imageFormat, Size.LONG size, int rowPitch, int flags) {
				super(cl, buffer, imageFormat, size, flags);
				this.rowPitch = rowPitch;
			}

			public int getRowPitch() { return rowPitch; }

			@Override protected ID createBufferImage(BUFFER buffer) {
				return getCL().createImage2D(flags, imageFormat, size.getWidth(), size.getHeight(), rowPitch, buffer);
			}
		}
		public static class Image3DBuffer<ID extends Number, BUFFER extends java.nio.Buffer> extends ImageBuffer<ID, BUFFER> {
			protected final int depth;
			protected final int rowPitch;
			protected final int slicePitch;

			public Image3DBuffer(CLContext<ID> cl, BUFFER buffer, ImageFormat imageFormat, Size.LONG size, int depth, int rowPitch, int slicePitch, int flags) {
				super(cl, buffer, imageFormat, size, flags);
				this.depth = depth;
				this.rowPitch = rowPitch;
				this.slicePitch = slicePitch;
			}

			public int getDepth() { return depth; }
			public int getRowPitch() { return rowPitch; }
			public int getSlicePitch() { return slicePitch; }

			@Override protected ID createBufferImage(BUFFER buffer) {
				return getCL().createImage3D(flags, imageFormat, size.getWidth(), size.getHeight(), depth, rowPitch, slicePitch, buffer);
			}
		}

		public static abstract class GLImageBuffer<ID extends Number> extends ImageBuffer<ID, java.nio.Buffer> implements CLGLObject {

			public GLImageBuffer(CLContext<ID> cl, int flags) {
				super(cl, null, null, null, flags);
			}

			protected abstract ID createFromGL(ByteBuffer tempBuffer);

			@Override protected ID createBufferImage(java.nio.Buffer buffer) throws Exception {
				ByteBuffer tempBuffer = BufferUtils.allocateByteBuffer(2 << 2);
				if(buffer != null && !buffer.isDirect()) throw new IllegalArgumentException("buffer is not a direct buffer");
				if(isHostPointerFlag()) throw new IllegalArgumentException("CL_MEM_COPY_HOST_PTR or CL_MEM_USE_HOST_PTR can not be used with OpenGL Buffers.");
				OpenGLNativeHolder<? extends Number> object = getGLObject(); object.assertNotDead(); object.assertCreated(); ID id = createFromGL(tempBuffer);
				tempBuffer.rewind(); IntBuffer imageFormat = tempBuffer.asIntBuffer(); LongBuffer imageSize = tempBuffer.asLongBuffer();

				getCL().getInfo(getCL().METHOD_clGetImageInfo(), getCL().CL_IMAGE_FORMAT(), id, 8, tempBuffer, null);
				this.imageFormat = new ImageFormat((CLContext<Number>) getCL(), imageFormat.get(CHANNEL_ORDER), imageFormat.get(CHANNEL_DATA_TYPE));
				int width, height; tempBuffer.rewind();
				getCL().getInfo(getCL().METHOD_clGetImageInfo(), getCL().CL_IMAGE_WIDTH(), id, 8, tempBuffer, null);
				width = (int) imageSize.get(0); tempBuffer.rewind();
				getCL().getInfo(getCL().METHOD_clGetImageInfo(), getCL().CL_IMAGE_HEIGHT(), id, 8, tempBuffer, null);
				height = (int) imageSize.get(0); tempBuffer.rewind(); size = new Size.LONG(width, height); BufferUtils.deallocate(tempBuffer); return id;
			}

			public static class GLImage2DBuffer<ID extends Number> extends GLImageBuffer<ID> {
				protected final RenderBufferObject<? extends Number> renderObject;

				public GLImage2DBuffer(CLContext<ID> cl, RenderBufferObject<? extends Number> renderObject, int flags) {
					super(cl, flags);
					this.renderObject = renderObject;
				}

				@Override public OpenGLNativeHolder<? extends Number> getGLObject() { return renderObject; }

				@Override protected ID createFromGL(ByteBuffer tempBuffer) {
					renderObject.assertBind();
					return getCL().createFromGLRenderbuffer(renderObject, flags);
				}
			}

			public static abstract class GLTexture<ID extends Number> extends GLImageBuffer<ID> {
				protected final int target;
				protected final int mipMapLevel;

				public GLTexture(CLContext<ID> cl, int target, int mipMapLevel, int flags) {
					super(cl, flags);
					this.target = target;
					this.mipMapLevel = mipMapLevel;
				}

				public int getTarget() { return target; }
				public int getMipMapLevel() { return mipMapLevel; }

				public static class GLTexture2D<ID extends Number> extends GLTexture<ID> {
					protected final Texture<? extends Number> textureObject;

					public GLTexture2D(CLContext<ID> cl, Texture<? extends Number> textureObject, int target, int mipMapLevel, int flags) {
						super(cl, target, mipMapLevel, flags);
						this.textureObject = textureObject;
					}

					@Override public OpenGLNativeHolder<? extends Number> getGLObject() { return textureObject; }

					@Override protected ID createFromGL(ByteBuffer tempBuffer) {
						textureObject.assertBind();
						return getCL().createFromGLTexture2D(textureObject, target, mipMapLevel, flags);
					}
				}
				public static class GLTexture3D<ID extends Number> extends GLTexture<ID> {
					protected final Texture<? extends Number> textureObject;
					protected int depth;

					public GLTexture3D(CLContext<ID> cl, Texture<? extends Number> textureObject, int target, int mipMapLevel, int flags) {
						super(cl, target, mipMapLevel, flags);
						this.textureObject = textureObject;
					}

					public int getDepth() { return depth; }
					@Override public OpenGLNativeHolder<? extends Number> getGLObject() { return textureObject; }

					@Override protected ID createFromGL(ByteBuffer tempBuffer) {
						textureObject.assertBind();
						ID id = getCL().createFromGLTexture3D(textureObject, target, mipMapLevel, flags);
						LongBuffer depthBuffer = tempBuffer.asLongBuffer();
						getCL().getInfo(getCL().METHOD_clGetImageInfo(), getCL().CL_IMAGE_DEPTH(), id, 8, tempBuffer, null);
						depth = (int) depthBuffer.get(0); tempBuffer.rewind(); return id;
					}
				}
			}
		}
	}

	public static class GLBuffer<ID extends Number> extends Buffer<ID, java.nio.Buffer> implements CLGLObject {
		protected final VertexBufferObject<? extends Number> vertexObject;

		public GLBuffer(CLContext<ID> cl, VertexBufferObject<? extends Number> vertexObject, int flags) {
			super(cl, 0, null, flags);
			this.vertexObject = vertexObject;
		}

		@Override public Memory<ID, java.nio.Buffer> use(java.nio.Buffer buffer) { throw new UnsupportedOperationException("Not yet supported!"); }
		@Override public int getCLSize() { return getBuffer() != null ? getBuffer().capacity() * BufferUtils.getElementSize(getBuffer()) : 0; }
		@Override public int getCLCapacity() { return getBuffer() != null ? getBuffer().capacity() : 0; }
		@Override public java.nio.Buffer getBuffer() { return null;/*vertexObject.getBuffer();*/ }
		@Override public OpenGLNativeHolder<? extends Number> getGLObject() { return vertexObject; }

		@Override protected void arrange() throws Exception {
			if(buffer != null && !buffer.isDirect()) throw new IllegalArgumentException("buffer is not a direct buffer");
			if(isHostPointerFlag()) throw new IllegalArgumentException("CL_MEM_COPY_HOST_PTR or CL_MEM_USE_HOST_PTR can not be used with OpenGL Buffers.");
			vertexObject.assertNotDead(); vertexObject.assertCreated(); vertexObject.assertBind();
			getInstance().setId(getCL().createFromGLBuffer(vertexObject, flags));
		}
	}
}
