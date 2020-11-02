package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import org.lwjgl.system.NativeType;

import java.io.File;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public abstract class GLContext<ID extends Number> {
	protected final Object gl;

	public GLContext(Object gl) {
		this.gl = gl;
	}

	public Object getGL() { return gl; }
	protected void assertGLNull() { if(gl != null) throw newException("GL is not null"); }
	protected void assertGLNotNull() { if(gl == null) throw newException("GL is null"); }
	protected void assertStatic() { assertGLNull(); }
	protected void assertNotStatic() { assertGLNotNull(); }
	@Override public boolean equals(Object o) { if(this == o) return true; if(o == null) return false;
		if(!o.getClass().getCanonicalName().startsWith(getClass().getCanonicalName())) return false;
		GLContext<?> glContext = (GLContext<?>) o; return getGL().equals(glContext.getGL());
	}

	// ETC*STATIC&NON STATIC*
	public abstract OpenGLNativeHolderProvider<ID> constructNativeProviderInstance();
	public abstract Program<ID> constructProgram();
	public abstract Shader<ID> constructShader(String[] code, Shader.ShaderType type, File workDir);
	public abstract Texture<ID> constructTexture(int target);
	public abstract VertexArrayObject<ID> constructVertexArrayObject();
	public abstract VertexBufferObject<ID> constructVertexBufferObject(VertexArrayObject<ID> vao, int target);
	public abstract FrameBufferObject<ID> constructFrameBufferObject(int target);
	public abstract RenderBufferObject<ID> constructRenderBufferObject(int target);
	public abstract GLUTessellator constructGLUTessellator();
	public abstract int glGetError();

	// Frame Buffer Object
	public abstract ID createFrameBufferObject();
	public abstract void destroyFrameBufferObject(ID id);
	public abstract void glBindFramebuffer(int target, ID id);
	public abstract void glDrawBuffer(int buf);
	public abstract void glFramebufferTexture2D(int target, int attachment, int texTarget, ID texture, int level);
	public abstract void glFramebufferRenderbuffer(int target, int attachment, int renderBufferTarget, ID renderBuffer);

	public abstract int[] GL_COLOR_ATTACHMENT();
	public abstract int GL_FRAMEBUFFER();
	public abstract int GL_DEPTH_ATTACHMENT();

	// Program
	public abstract ID createProgram();
	public abstract void destroyProgram(ID id);
	public abstract void glLinkProgram(ID id);
	public abstract void glValidateProgram(ID id);
	public abstract void glUseProgram(ID id);
	public abstract int getProgramUniformsCount(ID id);
	public abstract int getProgramAttributesCount(ID id);
	public abstract void glGetProgramInterfaceiv(ID id, int programInterface, int pname, IntBuffer params);
	public abstract void glGetProgramResourceName(ID id, int programInterface, int index, IntBuffer length, ByteBuffer name);
	public abstract int glGetProgramResourceLocation(ID id, int programInterface, ByteBuffer name);
	public abstract String glGetActiveUniform(ID id, int index, IntBuffer size, IntBuffer type);
	public abstract String glGetActiveAttrib(ID id, int index, IntBuffer size, IntBuffer type);
	public abstract int glGetUniformLocation(ID id, String name);
	public abstract int glGetAttribLocation(ID id, String name);
	public abstract void glBindAttribLocation(ID id, int index, String name);
	public abstract void glAttachShader(ID id, ID shader);
	public abstract void glDetachShader(ID id, ID shader);
	public abstract String getProgramLogInfo(ID id);
	public abstract void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long pointer);
	public abstract void glEnableVertexAttribArray(int index);
	public abstract void glDisableVertexAttribArray(int index);
	public abstract int glGetVertexAttrib(int index, int key);
	public abstract void glVertexAttrib(int location, Buffer buffer, int count);
	public abstract void glUniform(int location, Buffer buffer, int count);
	public abstract void glUniformMatrix(int location, boolean transpose, Buffer buffer, int m, int n);

	public abstract int GL_VERTEX_ATTRIB_ARRAY_ENABLED();
	public abstract int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH();
	public abstract int GL_PROGRAM_OUTPUT();
	public abstract int GL_ACTIVE_RESOURCES();

	// Render Buffer Object
	public abstract ID createRenderBufferObject();
	public abstract void destroyRenderBufferObject(ID id);
	public abstract void glBindRenderbuffer(int target, ID id);
	public abstract void glRenderbufferStorage(int target, int internalFormat, int width, int height);

	// Shader
	public abstract ID createShader(int type, String[] code);
	public abstract void destroyShader(ID id);
	public abstract boolean isShaderCompiled(ID id);
	public abstract String getShaderLogInfo(ID id);

	public abstract int GL_SHADER();
	public abstract int GL_VERTEX_SHADER();
	public abstract int GL_TESSELLATION_SHADER();
	public abstract int GL_EVALUATION_SHADER();
	public abstract int GL_GEOMETRY_SHADER();
	public abstract int GL_FRAGMENT_SHADER();
	public abstract int GL_COMPUTE_SHADER();

	// Texture
	public abstract ID createTexture();
	public abstract void destroyTexture(ID id);
	public abstract void glBindTexture(int target, ID id);
	public abstract void glActiveTexture(int texture);
	public abstract void glDisable(int target);
	public abstract void glTexStorage2D(int target, int level, int internalFormat, int width, int height);
	public abstract void glTexImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, ByteBuffer pixels);
	public abstract void glTexSubImage2D(int target, int level, int x, int y, int width, int height, int format, int type, ByteBuffer pixels);
	public abstract void glTexParameteri(int target, int pname, int param);
	public abstract void glGenerateMipmap(int target);
	public abstract int getMaxTextureSize();

	public abstract int[] GL_TEXTURE();
	public abstract int GL_TEXTURE_2D();
	public abstract int GL_NEAREST();
	public abstract int GL_LINEAR();
	public abstract int GL_CLAMP_TO_EDGE();
	public abstract int GL_TEXTURE_MAG_FILTER();
	public abstract int GL_TEXTURE_MIN_FILTER();
	public abstract int GL_TEXTURE_WRAP_S();
	public abstract int GL_TEXTURE_WRAP_T();
	public abstract int GL_UNSIGNED_BYTE();
	public abstract int GL_RGBA();
	public abstract int GL_RGB();
	public abstract int GL_BGRA();
	public abstract int GL_BGR();
	public abstract int GL_RGBA8();

	// Vertex Array Object
	public abstract ID createVertexArrayObject();
	public abstract void destroyVertexArrayObject(ID id);
	public abstract void glBindVertexArray(ID id);

	// Vertex Buffer Object
	public abstract ID createVertexBufferObject();
	public abstract void destroyVertexBufferObject(ID id);
	public abstract void glBindBuffer(int target, ID id);
	public abstract void glBufferData(int target, Buffer data, int usage);

	public abstract int GL_TRUE();
	public abstract int GL_FALSE();
	public abstract int GL_FLOAT();
	public abstract int GL_DOUBLE();
	public abstract int GL_INT();
	public abstract int GL_UNSIGNED_INT();
	public abstract int GL_STATIC_DRAW();
	public abstract int GL_ARRAY_BUFFER();
	public abstract int GL_ELEMENT_ARRAY_BUFFER();

	// Draw
	public abstract void glViewport(int x, int y, int width, int height);
	public abstract void glDrawElements(int mode, int count, int type, int off);
	public abstract int GL_TRIANGLES();

	public static GLException newException(String msg, Exception e) { throw new GLException(msg, e); }
	public static GLException newException(String msg) { throw new GLException(msg); }
	public static GLException newException(Exception e) { throw new GLException(e); }

	// Input
	public abstract void GL_INPUT_setCursorPos(Vec2 cursorPos);
	public abstract int GL_INPUT_KEYBOARD_PRESS();
	public abstract int GL_INPUT_KEYBOARD_REPEAT();
	public abstract long GL_INPUT_KEYBOARD_KEY_W();
	public abstract long GL_INPUT_KEYBOARD_KEY_A();
	public abstract long GL_INPUT_KEYBOARD_KEY_S();
	public abstract long GL_INPUT_KEYBOARD_KEY_D();
	public abstract long GL_INPUT_KEYBOARD_KEY_SPACE();
	public abstract long GL_INPUT_KEYBOARD_KEY_LEFT_SHIFT();

	public interface GLUTessellator {
		void gluDeleteTess();
		void gluTessProperty(int which, double value);
		void gluGetTessProperty(int which, double[] value, int value_offset);
		void gluTessNormal(double x, double y, double z);
		void gluTessCallback(int which, GLUTesselatorCallback aCallback);
		void gluTessVertex(double[] coords, int coords_offset, Object vertexData);
		void gluTessBeginPolygon(Object data);
		void gluTessBeginContour();
		void gluTessEndContour();
		void gluTessEndPolygon();
		void gluBeginPolygon();
		void gluNextContour(int type);
		void gluEndPolygon();

		public interface GLUTesselatorCallback {
			default void begin(int type) { throw new UnsupportedOperationException("Not implemented"); };
			default void beginData(int type, Object polygonData) { throw new UnsupportedOperationException("Not implemented"); };
			default void edgeFlag(boolean boundaryEdge) { throw new UnsupportedOperationException("Not implemented"); };
			default void edgeFlagData(boolean boundaryEdge, Object polygonData) { throw new UnsupportedOperationException("Not implemented"); };
			default void vertex(Object vertexData) { throw new UnsupportedOperationException("Not implemented"); };
			default void vertexData(Object vertexData, Object polygonData) { throw new UnsupportedOperationException("Not implemented"); };
			default void end() { throw new UnsupportedOperationException("Not implemented"); };
			default void endData(Object polygonData) { throw new UnsupportedOperationException("Not implemented"); };
			default void combine(double[] coords, Object[] data, float[] weight, Object[] outData) { throw new UnsupportedOperationException("Not implemented"); };
			default void combineData(double[] coords, Object[] data, float[] weight, Object[] outData, Object polygonData) { throw new UnsupportedOperationException("Not implemented"); };
			default void error(int errnum) { throw new UnsupportedOperationException("Not implemented"); };
			default void errorData(int errnum, Object polygonData) { throw new UnsupportedOperationException("Not implemented"); };
		}
	}
}
