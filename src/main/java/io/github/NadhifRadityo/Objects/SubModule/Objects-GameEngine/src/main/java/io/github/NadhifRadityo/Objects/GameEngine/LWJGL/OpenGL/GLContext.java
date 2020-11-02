package io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.FrameBufferObject;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.OpenGLNativeHolderProvider;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Program;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.RenderBufferObject;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Shader;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Texture;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.VertexArrayObject;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.VertexBufferObject;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.GLUtessellator;
import org.lwjgl.util.glu.GLUtessellatorCallback;

import java.io.File;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class GLContext extends io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext<Integer> {
	protected static final int[] GL_COLOR_ATTACHMENT = new int[] {
			GL30.GL_COLOR_ATTACHMENT0 , GL30.GL_COLOR_ATTACHMENT1 , GL30.GL_COLOR_ATTACHMENT2 , GL30.GL_COLOR_ATTACHMENT3 ,
			GL30.GL_COLOR_ATTACHMENT4 , GL30.GL_COLOR_ATTACHMENT5 , GL30.GL_COLOR_ATTACHMENT6 , GL30.GL_COLOR_ATTACHMENT7 ,
			GL30.GL_COLOR_ATTACHMENT8 , GL30.GL_COLOR_ATTACHMENT9 , GL30.GL_COLOR_ATTACHMENT10, GL30.GL_COLOR_ATTACHMENT11,
			GL30.GL_COLOR_ATTACHMENT12, GL30.GL_COLOR_ATTACHMENT13, GL30.GL_COLOR_ATTACHMENT14, GL30.GL_COLOR_ATTACHMENT15,
			GL30.GL_COLOR_ATTACHMENT16, GL30.GL_COLOR_ATTACHMENT17, GL30.GL_COLOR_ATTACHMENT18, GL30.GL_COLOR_ATTACHMENT19,
			GL30.GL_COLOR_ATTACHMENT20, GL30.GL_COLOR_ATTACHMENT21, GL30.GL_COLOR_ATTACHMENT22, GL30.GL_COLOR_ATTACHMENT23,
			GL30.GL_COLOR_ATTACHMENT24, GL30.GL_COLOR_ATTACHMENT25, GL30.GL_COLOR_ATTACHMENT26, GL30.GL_COLOR_ATTACHMENT27,
			GL30.GL_COLOR_ATTACHMENT28, GL30.GL_COLOR_ATTACHMENT29, GL30.GL_COLOR_ATTACHMENT30, GL30.GL_COLOR_ATTACHMENT31
	};
	protected static final int[] GL_TEXTURE = new int[] {
			GL13.GL_TEXTURE0 , GL13.GL_TEXTURE1 , GL13.GL_TEXTURE2 , GL13.GL_TEXTURE3 ,
			GL13.GL_TEXTURE4 , GL13.GL_TEXTURE5 , GL13.GL_TEXTURE6 , GL13.GL_TEXTURE7 ,
			GL13.GL_TEXTURE8 , GL13.GL_TEXTURE9 , GL13.GL_TEXTURE10, GL13.GL_TEXTURE11,
			GL13.GL_TEXTURE12, GL13.GL_TEXTURE13, GL13.GL_TEXTURE14, GL13.GL_TEXTURE15,
			GL13.GL_TEXTURE16, GL13.GL_TEXTURE17, GL13.GL_TEXTURE18, GL13.GL_TEXTURE19,
			GL13.GL_TEXTURE20, GL13.GL_TEXTURE21, GL13.GL_TEXTURE22, GL13.GL_TEXTURE23,
			GL13.GL_TEXTURE24, GL13.GL_TEXTURE25, GL13.GL_TEXTURE26, GL13.GL_TEXTURE27,
			GL13.GL_TEXTURE28, GL13.GL_TEXTURE29, GL13.GL_TEXTURE30, GL13.GL_TEXTURE31
	};

	private static final ThreadLocal<Long> currentContext = new ThreadLocal<>();
	protected boolean inMainLoop = false;

	public GLContext(Long gl) {
		super(gl);
	}

	public long getId() { return (long) super.getGL(); }
	public boolean isInMainLoop() { return inMainLoop; }
	public void setInMainLoop(boolean inMainLoop) { this.inMainLoop = inMainLoop; }
	protected void accessContext() {
		Long self = (Long) getGL();
		if(self == null) return;
		Long context = currentContext.get();
		if(context != null && context.equals(self)) return;
		try { currentContext.set(self); GLFW.glfwMakeContextCurrent(self);
		} catch(Exception e) { currentContext.set(context); newException(e); }
	}

	// ETC*STATIC&NON STATIC*
	@Override public OpenGLNativeHolderProvider<Integer> constructNativeProviderInstance() { return new io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.OpenGLNativeHolderProvider(); }
	@Override public Program<Integer> constructProgram() { return new io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.Program(this); }
	@Override public Shader<Integer> constructShader(String[] code, Shader.ShaderType type, File workDir) { return new io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.Shader(this, code, type, workDir); }
	@Override public Texture<Integer> constructTexture(int target) { return new io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.Texture(this, target); }
	@Override public VertexArrayObject<Integer> constructVertexArrayObject() { return new io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.VertexArrayObject(this); }
	@Override public VertexBufferObject<Integer> constructVertexBufferObject(VertexArrayObject<Integer> vao, int target) { return vao != null ? new io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.VertexBufferObject(
			(io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.VertexArrayObject) vao, target) : new io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.VertexBufferObject(this, target); }
	@Override public FrameBufferObject<Integer> constructFrameBufferObject(int target) { return new io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.FrameBufferObject(this, target); }
	@Override public RenderBufferObject<Integer> constructRenderBufferObject(int target) { return new io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.RenderBufferObject(this, target); }
	@Override public GLUTessellator constructGLUTessellator() { return new GLUTessellator(); }
	@Override public int glGetError() { accessContext(); return isInMainLoop() ? GL11.glGetError() : 0; }

	// Frame Buffer Object
	@Override public Integer createFrameBufferObject() { accessContext(); return OpenGLUtils.LWJGL_createFrameBufferObject(); }
	@Override public void destroyFrameBufferObject(Integer id) { accessContext(); OpenGLUtils.LWJGL_destroyFrameBufferObject(id); }
	@Override public void glBindFramebuffer(int target, Integer id) { accessContext(); GL30.glBindFramebuffer(target, id != null ? id : 0); }
	@Override public void glDrawBuffer(int buf) { accessContext(); GL11.glDrawBuffer(buf); }
	@Override public void glFramebufferTexture2D(int target, int attachment, int texTarget, Integer texture, int level) { accessContext(); GL30.glFramebufferTexture2D(target, attachment, texTarget, texture, level); }
	@Override public void glFramebufferRenderbuffer(int target, int attachment, int renderBufferTarget, Integer renderBuffer) { accessContext(); GL30.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer); }

	@Override public int[] GL_COLOR_ATTACHMENT() { return GL_COLOR_ATTACHMENT; }
	@Override public int GL_FRAMEBUFFER() { return GL30.GL_FRAMEBUFFER; }
	@Override public int GL_DEPTH_ATTACHMENT() { return GL30.GL_DEPTH_ATTACHMENT; }

	// GLFW
	public GLFWWindowPosCallback glfwSetWindowPosCallback(GLFWWindowPosCallbackI callback) { accessContext(); return GLFW.glfwSetWindowPosCallback((long) gl, callback); }
	public GLFWWindowSizeCallback glfwSetWindowSizeCallback(GLFWWindowSizeCallbackI callback) { accessContext(); return GLFW.glfwSetWindowSizeCallback((long) gl, callback); }
	public GLFWKeyCallback glfwSetKeyCallback(GLFWKeyCallbackI callback) { accessContext(); return GLFW.glfwSetKeyCallback((long) gl, callback); }
	public GLFWCursorPosCallback glfwSetCursorPosCallback(GLFWCursorPosCallbackI callback) { accessContext(); return GLFW.glfwSetCursorPosCallback((long) gl, callback); }
	public GLFWMouseButtonCallback glfwSetMouseButtonCallback(GLFWMouseButtonCallbackI callback) { accessContext(); return GLFW.glfwSetMouseButtonCallback((long) gl, callback); }
	public void glfwFreeCallbacks() { accessContext(); Callbacks.glfwFreeCallbacks((long) gl); }
	public void glfwSetWindowTitle(String title) { accessContext(); GLFW.glfwSetWindowTitle((long) gl, title); }
	public Size glfwGetWindowSize() { accessContext();
		IntBuffer windowSize = BufferUtils.allocateIntBuffer(2);
		long windowSizeAddress = BufferUtils.__getAddress(windowSize);
		GLFW.nglfwGetWindowSize((long) gl, windowSizeAddress, windowSizeAddress + 4);
		try { return new Size(windowSize.get(0), windowSize.get(1)); } finally { BufferUtils.deallocate(windowSize); }
	}
	public Size glfwGetFramebufferSize() { accessContext();
		IntBuffer windowSize = BufferUtils.allocateIntBuffer(2);
		long windowSizeAddress = BufferUtils.__getAddress(windowSize);
		GLFW.nglfwGetFramebufferSize((long) gl, windowSizeAddress, windowSizeAddress + 4);
		try { return new Size(windowSize.get(0), windowSize.get(1)); } finally { BufferUtils.deallocate(windowSize); }
	}

	// Program
	@Override public Integer createProgram() { accessContext(); return OpenGLUtils.LWJGL_createProgram(); }
	@Override public void destroyProgram(Integer id) { accessContext(); OpenGLUtils.LWJGL_destroyProgram(id); }
	@Override public void glLinkProgram(Integer id) { accessContext(); GL20.glLinkProgram(id); }
	@Override public void glValidateProgram(Integer id) { accessContext(); GL20.glValidateProgram(id); }
	@Override public void glUseProgram(Integer id) { accessContext(); GL20.glUseProgram(id != null ? id : 0); }
	@Override public int getProgramUniformsCount(Integer id) { accessContext(); return OpenGLUtils.LWJGL_getProgramUniformsCount(id); }
	@Override public int getProgramAttributesCount(Integer id) { accessContext(); return OpenGLUtils.LWJGL_getProgramAttributesCount(id); }
	@Override public void glGetProgramInterfaceiv(Integer id, int programInterface, int pname, IntBuffer params) { accessContext(); GL43.glGetProgramInterfaceiv(id, programInterface, pname, params); }
	@Override public void glGetProgramResourceName(Integer id, int programInterface, int index, IntBuffer length, ByteBuffer name) { accessContext(); GL43.glGetProgramResourceName(id, programInterface, index, length, name); }
	@Override public int glGetProgramResourceLocation(Integer id, int programInterface, ByteBuffer name) { accessContext(); return GL43.glGetProgramResourceLocation(id, programInterface, name); }
	@Override public String glGetActiveUniform(Integer id, int index, IntBuffer size, IntBuffer type) { accessContext(); return GL20.glGetActiveUniform(id, index, size, type); }
	@Override public String glGetActiveAttrib(Integer id, int index, IntBuffer size, IntBuffer type) { accessContext(); return GL20.glGetActiveAttrib(id, index, size, type); }
	@Override public int glGetUniformLocation(Integer id, String name) { accessContext(); return GL20.glGetUniformLocation(id, name); }
	@Override public int glGetAttribLocation(Integer id, String name) { accessContext(); return GL20.glGetAttribLocation(id, name); }
	@Override public void glBindAttribLocation(Integer id, int index, String name) { accessContext(); GL20.glBindAttribLocation(id, index, name); }
	@Override public void glAttachShader(Integer id, Integer shader) { accessContext(); GL20.glAttachShader(id, shader); }
	@Override public void glDetachShader(Integer id, Integer shader) { accessContext(); GL20.glDetachShader(id, shader); }
	@Override public String getProgramLogInfo(Integer id) { accessContext(); return GL20.glGetProgramInfoLog(id); }
	@Override public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long pointer) { accessContext(); GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer); }
	@Override public void glEnableVertexAttribArray(int index) { accessContext(); GL20.glEnableVertexAttribArray(index); }
	@Override public void glDisableVertexAttribArray(int index) { accessContext(); GL20.glDisableVertexAttribArray(index); }
	@Override public int glGetVertexAttrib(int index, int key) { accessContext(); return GL20.glGetVertexAttribi(index, key); }
	@Override public void glVertexAttrib(int location, Buffer buffer, int count) {
		if(buffer instanceof IntBuffer) {
			if(count == 4) { GL20.nglVertexAttrib4iv(location, BufferUtils.__getAddress(buffer)); return; }
		}
		if(buffer instanceof ShortBuffer) {
			if(count == 1) { GL20.nglVertexAttrib1sv(location, BufferUtils.__getAddress(buffer)); return; }
			if(count == 2) { GL20.nglVertexAttrib2sv(location, BufferUtils.__getAddress(buffer)); return; }
			if(count == 3) { GL20.nglVertexAttrib3sv(location, BufferUtils.__getAddress(buffer)); return; }
			if(count == 4) { GL20.nglVertexAttrib4sv(location, BufferUtils.__getAddress(buffer)); return; }
		}
		if(buffer instanceof FloatBuffer) {
			if(count == 1) { GL20.nglVertexAttrib1fv(location, BufferUtils.__getAddress(buffer)); return; }
			if(count == 2) { GL20.nglVertexAttrib2fv(location, BufferUtils.__getAddress(buffer)); return; }
			if(count == 3) { GL20.nglVertexAttrib3fv(location, BufferUtils.__getAddress(buffer)); return; }
			if(count == 4) { GL20.nglVertexAttrib4fv(location, BufferUtils.__getAddress(buffer)); return; }
		}
		if(buffer instanceof DoubleBuffer) {
			if(count == 1) { GL20.nglVertexAttrib1dv(location, BufferUtils.__getAddress(buffer)); return; }
			if(count == 2) { GL20.nglVertexAttrib2dv(location, BufferUtils.__getAddress(buffer)); return; }
			if(count == 3) { GL20.nglVertexAttrib3dv(location, BufferUtils.__getAddress(buffer)); return; }
			if(count == 4) { GL20.nglVertexAttrib4dv(location, BufferUtils.__getAddress(buffer)); return; }
		}
		throw new IllegalStateException("Not Yet Implemented");
	}
	@Override public void glUniform(int location, Buffer buffer, int count) {
		if(buffer instanceof IntBuffer) {
			if(count == 1) { GL20.nglUniform1iv(location, 1, BufferUtils.__getAddress(buffer)); return; }
			if(count == 2) { GL20.nglUniform2iv(location, 1, BufferUtils.__getAddress(buffer)); return; }
			if(count == 3) { GL20.nglUniform3iv(location, 1, BufferUtils.__getAddress(buffer)); return; }
			if(count == 4) { GL20.nglUniform4iv(location, 1, BufferUtils.__getAddress(buffer)); return; }
		}
		if(buffer instanceof FloatBuffer) {
			if(count == 1) { GL20.nglUniform1fv(location, 1, BufferUtils.__getAddress(buffer)); return; }
			if(count == 2) { GL20.nglUniform2fv(location, 1, BufferUtils.__getAddress(buffer)); return; }
			if(count == 3) { GL20.nglUniform3fv(location, 1, BufferUtils.__getAddress(buffer)); return; }
			if(count == 4) { GL20.nglUniform4fv(location, 1, BufferUtils.__getAddress(buffer)); return; }
		}
		throw new IllegalStateException("Not Yet Implemented");
	}
	@Override public void glUniformMatrix(int location, boolean transpose, Buffer buffer, int m, int n) {
		if(buffer instanceof FloatBuffer) {
			if(m == 2 && n == 2) { GL20.nglUniformMatrix2fv(location, 1, transpose, BufferUtils.__getAddress(buffer)); return; }
			if(m == 3 && n == 3) { GL20.nglUniformMatrix3fv(location, 1, transpose, BufferUtils.__getAddress(buffer)); return; }
			if(m == 4 && n == 4) { GL20.nglUniformMatrix4fv(location, 1, transpose, BufferUtils.__getAddress(buffer)); return; }
		}
		throw new IllegalStateException("Not Yet Implemented");
	}

	@Override public int GL_VERTEX_ATTRIB_ARRAY_ENABLED() { return GL20.GL_VERTEX_ATTRIB_ARRAY_ENABLED; }
	@Override public int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH() { return GL20.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH; }
	@Override public int GL_PROGRAM_OUTPUT() { return GL43.GL_PROGRAM_OUTPUT; }
	@Override public int GL_ACTIVE_RESOURCES() { return GL43.GL_ACTIVE_RESOURCES; }

	// Render Buffer Object
	@Override public Integer createRenderBufferObject() { accessContext(); return OpenGLUtils.LWJGL_createRenderBufferObject(); }
	@Override public void destroyRenderBufferObject(Integer id) { accessContext(); OpenGLUtils.LWJGL_destroyRenderBufferObject(id); }
	@Override public void glBindRenderbuffer(int target, Integer id) { accessContext(); GL30.glBindRenderbuffer(target, id != null ? id : 0); }
	@Override public void glRenderbufferStorage(int target, int internalFormat, int width, int height) { accessContext(); GL30.glRenderbufferStorage(target, internalFormat, width, height); }

	// Shader
	@Override public Integer createShader(int type, String[] code) { accessContext(); return OpenGLUtils.LWJGL_createShader(type, code); }
	@Override public void destroyShader(Integer id) { accessContext(); OpenGLUtils.LWJGL_destroyShader(id); }
	@Override public boolean isShaderCompiled(Integer id) { accessContext(); return OpenGLUtils.LWJGL_isShaderCompiled(id); }
	@Override public String getShaderLogInfo(Integer id) { accessContext(); return OpenGLUtils.LWJGL_getShaderLogInfo(id); }
	@Override public int GL_SHADER() { return GL43.GL_SHADER; }
	@Override public int GL_VERTEX_SHADER() { return GL20.GL_VERTEX_SHADER; }
	@Override public int GL_TESSELLATION_SHADER() { return GL40.GL_TESS_CONTROL_SHADER; }
	@Override public int GL_EVALUATION_SHADER() { return GL40.GL_TESS_EVALUATION_SHADER; }
	@Override public int GL_GEOMETRY_SHADER() { return GL32.GL_GEOMETRY_SHADER; }
	@Override public int GL_FRAGMENT_SHADER() { return GL20.GL_FRAGMENT_SHADER; }
	@Override public int GL_COMPUTE_SHADER() { return GL43.GL_COMPUTE_SHADER; }

	// Texture
	@Override public Integer createTexture() { accessContext(); return OpenGLUtils.LWJGL_createTexture(); }
	@Override public void destroyTexture(Integer id) { accessContext(); OpenGLUtils.LWJGL_destroyTexture(id); }
	@Override public void glBindTexture(int target, Integer id) { accessContext(); GL11.glBindTexture(target, id != null ? id : 0); }
	@Override public void glActiveTexture(int texture) { accessContext(); GL13.glActiveTexture(texture); }
	@Override public void glDisable(int target) { accessContext(); GL11.glDisable(target); }
	@Override public void glTexStorage2D(int target, int level, int internalFormat, int width, int height) { accessContext(); GL42.glTexStorage2D(target, level, internalFormat, width, height); }
	@Override public void glTexImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, ByteBuffer pixels) { accessContext(); GL11.glTexImage2D(target, level, internalFormat, width, height, border, format, type, pixels); }
	@Override public void glTexSubImage2D(int target, int level, int x, int y, int width, int height, int format, int type, ByteBuffer pixels) { accessContext(); GL11.glTexSubImage2D(target, level, x, y, width, height, format, type, pixels); }
	@Override public void glTexParameteri(int target, int pname, int param) { accessContext(); GL11.glTexParameteri(target, pname, param); }
	@Override public void glGenerateMipmap(int target) { accessContext(); GL30.glGenerateMipmap(target); }
	@Override public int getMaxTextureSize() { accessContext(); return OpenGLUtils.LWJGL_getMaxTextureSize(); }

	@Override public int[] GL_TEXTURE() { return GL_TEXTURE; }
	@Override public int GL_TEXTURE_2D() { return GL11.GL_TEXTURE_2D; }
	@Override public int GL_NEAREST() { return GL11.GL_NEAREST; }
	@Override public int GL_LINEAR() { return GL11.GL_LINEAR; }
	@Override public int GL_CLAMP_TO_EDGE() { return GL12.GL_CLAMP_TO_EDGE; }
	@Override public int GL_TEXTURE_MAG_FILTER() { return GL11.GL_TEXTURE_MAG_FILTER; }
	@Override public int GL_TEXTURE_MIN_FILTER() { return GL11.GL_TEXTURE_MIN_FILTER; }
	@Override public int GL_TEXTURE_WRAP_S() { return GL11.GL_TEXTURE_WRAP_S; }
	@Override public int GL_TEXTURE_WRAP_T() { return GL11.GL_TEXTURE_WRAP_T; }
	@Override public int GL_UNSIGNED_BYTE() { return GL11.GL_UNSIGNED_BYTE; }
	@Override public int GL_RGBA() { return GL11.GL_RGBA; }
	@Override public int GL_RGB() { return GL11.GL_RGB; }
	@Override public int GL_BGRA() { return GL12.GL_BGRA; }
	@Override public int GL_BGR() { return GL12.GL_BGR; }
	@Override public int GL_RGBA8() { return GL11.GL_RGBA8; }

	// Vertex Array Object
	@Override public Integer createVertexArrayObject() { accessContext(); return OpenGLUtils.LWJGL_createVertexArrayObject(); }
	@Override public void destroyVertexArrayObject(Integer id) { accessContext(); OpenGLUtils.LWJGL_destroyVertexArrayObject(id); }
	@Override public void glBindVertexArray(Integer id) { accessContext(); GL30.glBindVertexArray(id != null ? id : 0); }

	// Vertex Buffer Object
	@Override public Integer createVertexBufferObject() { accessContext(); return OpenGLUtils.LWJGL_createVertexBufferObject(); }
	@Override public void destroyVertexBufferObject(Integer id) { accessContext(); OpenGLUtils.LWJGL_destroyVertexBufferObject(id); }
	@Override public void glBindBuffer(int target, Integer id) { accessContext(); GL15.glBindBuffer(target, id != null ? id : 0); }
	@Override public void glBufferData(int target, Buffer data, int usage) { accessContext(); GL15.nglBufferData(target, data.capacity() * BufferUtils.getElementSize(data), BufferUtils.__getAddress(data), usage); }
	@Override public int GL_TRUE() { return GL11.GL_TRUE; }
	@Override public int GL_FALSE() { return GL11.GL_FALSE; }
	@Override public int GL_FLOAT() { return GL11.GL_FLOAT; }
	@Override public int GL_DOUBLE() { return GL11.GL_DOUBLE; }
	@Override public int GL_INT() { return GL11.GL_INT; }
	@Override public int GL_UNSIGNED_INT() { return GL11.GL_UNSIGNED_INT; }
	@Override public int GL_STATIC_DRAW() { return GL15.GL_STATIC_DRAW; }
	@Override public int GL_ARRAY_BUFFER() { return GL15.GL_ARRAY_BUFFER; }
	@Override public int GL_ELEMENT_ARRAY_BUFFER() { return GL15.GL_ELEMENT_ARRAY_BUFFER; }

	// Draw
	@Override public void glViewport(int x, int y, int width, int height) { accessContext(); GL11.glViewport(x, y, width, height); }
	@Override public void glDrawElements(int mode, int count, int type, int off) { accessContext(); GL11.glDrawElements(mode, count, type, off); }
	@Override public int GL_TRIANGLES() { return GL11.GL_TRIANGLES; }

	// Input
	@Override public void GL_INPUT_setCursorPos(Vec2 cursorPos) { accessContext(); GLFW.glfwSetCursorPos((long) gl, cursorPos.x(), cursorPos.y()); }
	@Override public int GL_INPUT_KEYBOARD_PRESS() { return GLFW.GLFW_PRESS; }
	@Override public int GL_INPUT_KEYBOARD_REPEAT() { return GLFW.GLFW_REPEAT; }
	@Override public long GL_INPUT_KEYBOARD_KEY_W() { return GLFW.GLFW_KEY_W; }
	@Override public long GL_INPUT_KEYBOARD_KEY_A() { return GLFW.GLFW_KEY_A; }
	@Override public long GL_INPUT_KEYBOARD_KEY_S() { return GLFW.GLFW_KEY_S; }
	@Override public long GL_INPUT_KEYBOARD_KEY_D() { return GLFW.GLFW_KEY_D; }
	@Override public long GL_INPUT_KEYBOARD_KEY_SPACE() { return GLFW.GLFW_KEY_SPACE; }
	@Override public long GL_INPUT_KEYBOARD_KEY_LEFT_SHIFT() { return GLFW.GLFW_KEY_LEFT_SHIFT; }

	public static class GLUTessellator implements io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext.GLUTessellator {
		protected final GLUtessellator instance = GLU.gluNewTess();

		@Override public void gluDeleteTess() { instance.gluDeleteTess(); }
		@Override public void gluTessProperty(int which, double value) { instance.gluTessProperty(which, value); }
		@Override public void gluGetTessProperty(int which, double[] value, int value_offset) { instance.gluGetTessProperty(which, value, value_offset); }
		@Override public void gluTessNormal(double x, double y, double z) { instance.gluTessNormal(x, y, z); }
		@Override public void gluTessCallback(int which, GLUTesselatorCallback aCallback) {
			instance.gluTessCallback(which, new GLUtessellatorCallback() {
				@Override public void begin(int type) { aCallback.begin(type); }
				@Override public void beginData(int type, Object polygonData) { aCallback.beginData(type, polygonData); }
				@Override public void edgeFlag(boolean boundaryEdge) { aCallback.edgeFlag(boundaryEdge); }
				@Override public void edgeFlagData(boolean boundaryEdge, Object polygonData) { aCallback.edgeFlagData(boundaryEdge, polygonData); }
				@Override public void vertex(Object vertexData) { aCallback.vertex(vertexData); }
				@Override public void vertexData(Object vertexData, Object polygonData) { aCallback.vertexData(vertexData, polygonData); }
				@Override public void end() { aCallback.end(); }
				@Override public void endData(Object polygonData) { aCallback.endData(polygonData); }
				@Override public void combine(double[] coords, Object[] data, float[] weight, Object[] outData) { aCallback.combine(coords, data, weight, outData); }
				@Override public void combineData(double[] coords, Object[] data, float[] weight, Object[] outData, Object polygonData) { aCallback.combineData(coords, data, weight, outData, polygonData); }
				@Override public void error(int errnum) { aCallback.error(errnum); }
				@Override public void errorData(int errnum, Object polygonData) { aCallback.errorData(errnum, polygonData); }
			});
		}
		@Override public void gluTessVertex(double[] coords, int coords_offset, Object vertexData) { instance.gluTessVertex(coords, coords_offset, vertexData); }
		@Override public void gluTessBeginPolygon(Object data) { instance.gluTessBeginPolygon(data); }
		@Override public void gluTessBeginContour() { instance.gluTessBeginContour(); }
		@Override public void gluTessEndContour() { instance.gluTessEndContour(); }
		@Override public void gluTessEndPolygon() { instance.gluTessEndPolygon(); }
		@Override public void gluBeginPolygon() { instance.gluBeginPolygon(); }
		@Override public void gluNextContour(int type) { instance.gluNextContour(type); }
		@Override public void gluEndPolygon() { instance.gluEndPolygon(); }
	}
}
