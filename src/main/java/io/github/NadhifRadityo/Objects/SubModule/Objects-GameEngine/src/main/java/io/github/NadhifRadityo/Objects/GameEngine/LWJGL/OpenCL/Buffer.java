package io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenCL;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;
import io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.RenderBufferObject;
import io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.Texture;
import io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.VertexBufferObject;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.ImageFormat;

public class Buffer<BUFFER extends java.nio.Buffer> extends io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Buffer<Long, BUFFER> {
	public Buffer(CLContext cl, int size, BUFFER buffer, int flags) {
		super(cl, size, buffer, flags);
	}

	public static class SubBuffer<BUFFER extends java.nio.Buffer> extends io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Buffer.SubBuffer<Long, BUFFER> {
		public SubBuffer(CLContext cl, Buffer<BUFFER> parent, int offset, int size, int flags) {
			super(cl, parent, offset, size, flags);
		}
	}

	public static class Image2DBuffer<BUFFER extends java.nio.Buffer> extends ImageBuffer.Image2DBuffer<Long, BUFFER> {
		public Image2DBuffer(CLContext cl, BUFFER buffer, ImageFormat imageFormat, Size.LONG size, int rowPitch, int flags) {
			super(cl, buffer, imageFormat, size, rowPitch, flags);
		}
	}
	public static class Image3DBuffer<BUFFER extends java.nio.Buffer> extends ImageBuffer.Image3DBuffer<Long, BUFFER> {
		public Image3DBuffer(CLContext cl, BUFFER buffer, ImageFormat imageFormat, Size.LONG size, int depth, int rowPitch, int slicePitch, int flags) {
			super(cl, buffer, imageFormat, size, depth, rowPitch, slicePitch, flags);
		}
	}

	public static class GLImage2DBuffer extends ImageBuffer.GLImageBuffer.GLImage2DBuffer<Long> {
		public GLImage2DBuffer(CLContext cl, RenderBufferObject renderObject, int flags) {
			super(cl, renderObject, flags);
		}
	}

	public static class GLTexture2D extends ImageBuffer.GLImageBuffer.GLTexture.GLTexture2D<Long> {
		public GLTexture2D(CLContext cl, Texture textureObject, int target, int mipMapLevel, int flags) {
			super(cl, textureObject, target, mipMapLevel, flags);
		}
	}
	public static class GLTexture3D extends ImageBuffer.GLImageBuffer.GLTexture.GLTexture3D<Long> {
		public GLTexture3D(CLContext cl, Texture textureObject, int target, int mipMapLevel, int flags) {
			super(cl, textureObject, target, mipMapLevel, flags);
		}
	}

	public static class GLBuffer extends io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Buffer.GLBuffer<Long> {
		public GLBuffer(CLContext cl, VertexBufferObject vertexObject, int flags) {
			super(cl, vertexObject, flags);
		}
	}
}
