package io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUtessellator;
import com.jogamp.opengl.glu.GLUtessellatorCallback;
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

import java.io.File;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GLContext extends io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext<Integer> {
	protected static final int[] GL_COLOR_ATTACHMENT = new int[] {
			GL2ES3.GL_COLOR_ATTACHMENT0 , GL2ES3.GL_COLOR_ATTACHMENT1 , GL2ES3.GL_COLOR_ATTACHMENT2 , GL2ES3.GL_COLOR_ATTACHMENT3 ,
			GL2ES3.GL_COLOR_ATTACHMENT4 , GL2ES3.GL_COLOR_ATTACHMENT5 , GL2ES3.GL_COLOR_ATTACHMENT6 , GL2ES3.GL_COLOR_ATTACHMENT7 ,
			GL2ES3.GL_COLOR_ATTACHMENT8 , GL2ES3.GL_COLOR_ATTACHMENT9 , GL2ES3.GL_COLOR_ATTACHMENT10, GL2ES3.GL_COLOR_ATTACHMENT11,
			GL2ES3.GL_COLOR_ATTACHMENT12, GL2ES3.GL_COLOR_ATTACHMENT13, GL2ES3.GL_COLOR_ATTACHMENT14, GL2ES3.GL_COLOR_ATTACHMENT15,
			GL2ES3.GL_COLOR_ATTACHMENT16, GL2ES3.GL_COLOR_ATTACHMENT17, GL2ES3.GL_COLOR_ATTACHMENT18, GL2ES3.GL_COLOR_ATTACHMENT19,
			GL2ES3.GL_COLOR_ATTACHMENT20, GL2ES3.GL_COLOR_ATTACHMENT21, GL2ES3.GL_COLOR_ATTACHMENT22, GL2ES3.GL_COLOR_ATTACHMENT23,
			GL2ES3.GL_COLOR_ATTACHMENT24, GL2ES3.GL_COLOR_ATTACHMENT25, GL2ES3.GL_COLOR_ATTACHMENT26, GL2ES3.GL_COLOR_ATTACHMENT27,
			GL2ES3.GL_COLOR_ATTACHMENT28, GL2ES3.GL_COLOR_ATTACHMENT29, GL2ES3.GL_COLOR_ATTACHMENT30, GL2ES3.GL_COLOR_ATTACHMENT31
	};
	protected static final int[] GL_TEXTURE = new int[] {
			GL.GL_TEXTURE0 , GL.GL_TEXTURE1 , GL.GL_TEXTURE2 , GL.GL_TEXTURE3 ,
			GL.GL_TEXTURE4 , GL.GL_TEXTURE5 , GL.GL_TEXTURE6 , GL.GL_TEXTURE7 ,
			GL.GL_TEXTURE8 , GL.GL_TEXTURE9 , GL.GL_TEXTURE10, GL.GL_TEXTURE11,
			GL.GL_TEXTURE12, GL.GL_TEXTURE13, GL.GL_TEXTURE14, GL.GL_TEXTURE15,
			GL.GL_TEXTURE16, GL.GL_TEXTURE17, GL.GL_TEXTURE18, GL.GL_TEXTURE19,
			GL.GL_TEXTURE20, GL.GL_TEXTURE21, GL.GL_TEXTURE22, GL.GL_TEXTURE23,
			GL.GL_TEXTURE24, GL.GL_TEXTURE25, GL.GL_TEXTURE26, GL.GL_TEXTURE27,
			GL.GL_TEXTURE28, GL.GL_TEXTURE29, GL.GL_TEXTURE30, GL.GL_TEXTURE31
	};

	protected final InputHandler inputHandler;

	public GLContext(GL gl, InputHandler inputHandler) {
		super(gl);
		this.inputHandler = inputHandler;
	}
	public GLContext(GL gl) {
		this(gl, new InputHandler() {
			@Override public void GL_INPUT_setCursorPos(Vec2 cursorPos) { throw new UnsupportedOperationException("Not Implemented"); }
		});
	}

	public GL _getGL() { return (GL) super.getGL(); }
	public InputHandler getInputHandler() { return inputHandler; }

	// ETC*STATIC&NON STATIC*
	@Override public OpenGLNativeHolderProvider<Integer> constructNativeProviderInstance() { return new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.OpenGLNativeHolderProvider(); }
	@Override public Program<Integer> constructProgram() { return new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.Program(this); }
	@Override public Shader<Integer> constructShader(String[] code, Shader.ShaderType type, File workDir) { return new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.Shader(this, code, type, workDir); }
	@Override public Texture<Integer> constructTexture(int target) { return new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.Texture(this, target); }
	@Override public VertexArrayObject<Integer> constructVertexArrayObject() { return new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.VertexArrayObject(this); }
	@Override public VertexBufferObject<Integer> constructVertexBufferObject(io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.VertexArrayObject<Integer> vao, int target) { return vao != null ? new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.VertexBufferObject(
			(io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.VertexArrayObject) vao, target) : new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.VertexBufferObject(this, target); }
	@Override public FrameBufferObject<Integer> constructFrameBufferObject(int target) { return new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.FrameBufferObject(this, target); }
	@Override public RenderBufferObject<Integer> constructRenderBufferObject(int target) { return new io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.RenderBufferObject(this, target); }
	@Override public GLUTessellator constructGLUTessellator() { return new GLUTessellator(); }
	@Override public int glGetError() { return _getGL().glGetError(); }

	// Frame Buffer Object
	@Override public Integer createFrameBufferObject() { return OpenGLUtils.JOGAMP_createFrameBufferObject(_getGL()); }
	@Override public void destroyFrameBufferObject(Integer id) { OpenGLUtils.JOGAMP_destroyFrameBufferObject(_getGL(), id); }
	@Override public void glBindFramebuffer(int target, Integer id) { _getGL().glBindFramebuffer(target, id != null ? id : 0); }
	@Override public void glDrawBuffer(int buf) { _getGL().getGL2GL3().glDrawBuffer(buf); }
	@Override public void glFramebufferTexture2D(int target, int attachment, int textarget, Integer texture, int level) { _getGL().glFramebufferTexture2D(target, attachment, textarget, texture, level); }
	@Override public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, Integer renderbuffer) { _getGL().glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer); }

	@Override public int[] GL_COLOR_ATTACHMENT() { return GL_COLOR_ATTACHMENT; }
	@Override public int GL_FRAMEBUFFER() { return GL.GL_FRAMEBUFFER; }
	@Override public int GL_DEPTH_ATTACHMENT() { return GL.GL_DEPTH_ATTACHMENT; }

	// Program
	@Override public Integer createProgram() { return OpenGLUtils.JOGAMP_createProgram(_getGL()); }
	@Override public void destroyProgram(Integer id) { OpenGLUtils.JOGAMP_destroyProgram(_getGL(), id); }
	@Override public void glLinkProgram(Integer id) { _getGL().getGL2ES2().glLinkProgram(id); }
	@Override public void glValidateProgram(Integer id) { _getGL().getGL2ES2().glValidateProgram(id); }
	@Override public void glUseProgram(Integer id) { _getGL().getGL2ES2().glUseProgram(id != null ? id : 0); }
	@Override public int getProgramUniformsCount(Integer id) { return OpenGLUtils.JOGAMP_getProgramUniformsCount(_getGL(), id); }
	@Override public int getProgramAttributesCount(Integer id) { return OpenGLUtils.JOGAMP_getProgramAttributesCount(_getGL(), id); }
	@Override public void glGetProgramInterfaceiv(Integer id, int programInterface, int pname, IntBuffer params) { _getGL().getGL3ES3().glGetProgramInterfaceiv(id, programInterface, pname, params); }
	@Override public void glGetProgramResourceName(Integer id, int programInterface, int index, IntBuffer length, ByteBuffer name) { _getGL().getGL3ES3().glGetProgramResourceName(id, programInterface, index, name.capacity(), length, name); }
	@Override public int glGetProgramResourceLocation(Integer id, int programInterface, ByteBuffer name) { return _getGL().getGL3ES3().glGetProgramResourceLocation(id, programInterface, name); }
	@Override public String glGetActiveUniform(Integer id, int index, IntBuffer size, IntBuffer type) {
		int[] result = { 0, 0, 0 }; byte[] nameBuf = new byte[OpenGLUtils.JOGAMP_getActiveUniformMaxLength(_getGL(), id) * 2];
		_getGL().getGL2ES2().glGetActiveUniform(id, index, nameBuf.length, result, 0, result, 1, result, 2, nameBuf, 0);
		size.put(result[1]); type.put(result[2]); return new String(nameBuf, 0, result[0]);
	}
	@Override public String glGetActiveAttrib(Integer id, int index, IntBuffer size, IntBuffer type) {
		int[] result = { 0, 0, 0 }; byte[] nameBuf = new byte[OpenGLUtils.JOGAMP_getActiveAttributeMaxLength(_getGL(), id) * 2];
		_getGL().getGL2ES2().glGetActiveAttrib(id, index, nameBuf.length, result, 0, result, 1, result, 2, nameBuf, 0);
		size.put(result[1]); type.put(result[2]); return new String(nameBuf, 0, result[0]);
	}
	@Override public int glGetUniformLocation(Integer id, String name) { return _getGL().getGL2ES2().glGetUniformLocation(id, name); }
	@Override public int glGetAttribLocation(Integer id, String name) { return _getGL().getGL2ES2().glGetAttribLocation(id, name); }
	@Override public void glBindAttribLocation(Integer id, int index, String name) { _getGL().getGL2ES2().glBindAttribLocation(id, index, name); }
	@Override public void glAttachShader(Integer id, Integer shader) { _getGL().getGL2ES2().glAttachShader(id, shader); }
	@Override public void glDetachShader(Integer id, Integer shader) { _getGL().getGL2ES2().glDetachShader(id, shader); }
	@Override public String getProgramLogInfo(Integer id) { return OpenGLUtils.JOGAMP_getProgramLogInfo(_getGL(), id); }
	@Override public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long pointer) { _getGL().getGL2ES2().glVertexAttribPointer(index, size, type, normalized, stride, pointer); }
	@Override public void glEnableVertexAttribArray(int index) { _getGL().getGL2ES2().glEnableVertexAttribArray(index); }
	@Override public void glDisableVertexAttribArray(int index) { _getGL().getGL2ES2().glDisableVertexAttribArray(index); }
	@Override public int glGetVertexAttrib(int index, int key) { int[] result = new int[1]; _getGL().getGL2ES2().glGetVertexAttribiv(index, key, result, 0); return result[0]; }
	@Override public void glVertexAttrib(int location, Buffer buffer, int count) {
		if(buffer instanceof FloatBuffer) {
			if(count == 1) { _getGL().getGL2ES2().glVertexAttrib1fv(location, (FloatBuffer) buffer); return; }
			if(count == 2) { _getGL().getGL2ES2().glVertexAttrib2fv(location, (FloatBuffer) buffer); return; }
			if(count == 3) { _getGL().getGL2ES2().glVertexAttrib3fv(location, (FloatBuffer) buffer); return; }
			if(count == 4) { _getGL().getGL2ES2().glVertexAttrib4fv(location, (FloatBuffer) buffer); return; }
		}
		throw new IllegalStateException("Not Yet Implemented");
	}
	@Override public void glUniform(int location, Buffer buffer, int count) {
		if(buffer instanceof IntBuffer) {
			if(count == 1) { _getGL().getGL2ES2().glUniform1iv(location, 1, (IntBuffer) buffer); return; }
			if(count == 2) { _getGL().getGL2ES2().glUniform2iv(location, 1, (IntBuffer) buffer); return; }
			if(count == 3) { _getGL().getGL2ES2().glUniform3iv(location, 1, (IntBuffer) buffer); return; }
			if(count == 4) { _getGL().getGL2ES2().glUniform4iv(location, 1, (IntBuffer) buffer); return; }
		}
		if(buffer instanceof FloatBuffer) {
			if(count == 1) { _getGL().getGL2ES2().glUniform1fv(location, 1, (FloatBuffer) buffer); return; }
			if(count == 2) { _getGL().getGL2ES2().glUniform2fv(location, 1, (FloatBuffer) buffer); return; }
			if(count == 3) { _getGL().getGL2ES2().glUniform3fv(location, 1, (FloatBuffer) buffer); return; }
			if(count == 4) { _getGL().getGL2ES2().glUniform4fv(location, 1, (FloatBuffer) buffer); return; }
		}
		throw new IllegalStateException("Not Yet Implemented");
	}
	@Override public void glUniformMatrix(int location, boolean transpose, Buffer buffer, int m, int n) {
		if(buffer instanceof FloatBuffer) {
			if(m == 2 && n == 2) { _getGL().getGL2ES2().glUniformMatrix2fv(location, 1, transpose, (FloatBuffer) buffer); return; }
			if(m == 3 && n == 3) { _getGL().getGL2ES2().glUniformMatrix3fv(location, 1, transpose, (FloatBuffer) buffer); return; }
			if(m == 4 && n == 4) { _getGL().getGL2ES2().glUniformMatrix4fv(location, 1, transpose, (FloatBuffer) buffer); return; }
		}
		throw new IllegalStateException("Not Yet Implemented");
	}

	@Override public int GL_VERTEX_ATTRIB_ARRAY_ENABLED() { return GL2ES2.GL_VERTEX_ATTRIB_ARRAY_ENABLED; }
	@Override public int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH() { return GL2ES2.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH; }
	@Override public int GL_PROGRAM_OUTPUT() { return GL3ES3.GL_PROGRAM_OUTPUT; }
	@Override public int GL_ACTIVE_RESOURCES() { return GL3ES3.GL_ACTIVE_RESOURCES; }

	// Render Buffer Object
	@Override public Integer createRenderBufferObject() { return OpenGLUtils.JOGAMP_createRenderBufferObject(_getGL()); }
	@Override public void destroyRenderBufferObject(Integer id) { OpenGLUtils.JOGAMP_destroyRenderBufferObject(_getGL(), id); }
	@Override public void glBindRenderbuffer(int target, Integer id) { _getGL().glBindRenderbuffer(target, id != null ? id : 0); }
	@Override public void glRenderbufferStorage(int target, int internalformat, int width, int height) { _getGL().glRenderbufferStorage(target, internalformat, width, height); }

	// Shader
	@Override public Integer createShader(int type, String[] code) { return OpenGLUtils.JOGAMP_createShader(_getGL(), type, code); }
	@Override public void destroyShader(Integer id) { OpenGLUtils.JOGAMP_destroyShader(_getGL(), id); }
	@Override public boolean isShaderCompiled(Integer id) { return OpenGLUtils.JOGAMP_isShaderCompiled(_getGL(), id); }
	@Override public String getShaderLogInfo(Integer id) { return OpenGLUtils.JOGAMP_getShaderLogInfo(_getGL(), id); }
	@Override public int GL_SHADER() { return GL2ES2.GL_SHADER; }
	@Override public int GL_VERTEX_SHADER() { return GL2ES2.GL_VERTEX_SHADER; }
	@Override public int GL_TESSELLATION_SHADER() { return GL3ES3.GL_TESS_CONTROL_SHADER; }
	@Override public int GL_EVALUATION_SHADER() { return GL3ES3.GL_TESS_EVALUATION_SHADER; }
	@Override public int GL_GEOMETRY_SHADER() { return GL3ES3.GL_GEOMETRY_SHADER; }
	@Override public int GL_FRAGMENT_SHADER() { return GL2ES2.GL_FRAGMENT_SHADER; }
	@Override public int GL_COMPUTE_SHADER() { return GL3ES3.GL_COMPUTE_SHADER; }

	// Texture
	@Override public Integer createTexture() { return OpenGLUtils.JOGAMP_createTexture(_getGL()); }
	@Override public void destroyTexture(Integer id) { OpenGLUtils.JOGAMP_destroyTexture(_getGL(), id); }
	@Override public void glBindTexture(int target, Integer id) { _getGL().glBindTexture(target, id != null ? id : 0); }
	@Override public void glActiveTexture(int texture) { _getGL().glActiveTexture(texture); }
	@Override public void glDisable(int target) { _getGL().glDisable(target); }
	@Override public void glTexStorage2D(int target, int level, int internalFormat, int width, int height) { _getGL().glTexStorage2D(target, level, internalFormat, width, height); }
	@Override public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ByteBuffer pixels) { _getGL().glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels); }
	@Override public void glTexSubImage2D(int target, int level, int x, int y, int width, int height, int format, int type, ByteBuffer pixels) { _getGL().glTexSubImage2D(target, level, x, y, width, height, format, type, pixels); }
	@Override public void glTexParameteri(int target, int pname, int param) { _getGL().glTexParameteri(target, pname, param); }
	@Override public void glGenerateMipmap(int target) { _getGL().glGenerateMipmap(target); }
	@Override public int getMaxTextureSize() { return OpenGLUtils.JOGAMP_getMaxTextureSize(_getGL()); }

	@Override public int[] GL_TEXTURE() { return GL_TEXTURE; }
	@Override public int GL_TEXTURE_2D() { return GL.GL_TEXTURE_2D; }
	@Override public int GL_NEAREST() { return GL.GL_NEAREST; }
	@Override public int GL_LINEAR() { return GL.GL_LINEAR; }
	@Override public int GL_CLAMP_TO_EDGE() { return GL.GL_CLAMP_TO_EDGE; }
	@Override public int GL_TEXTURE_MAG_FILTER() { return GL.GL_TEXTURE_MAG_FILTER; }
	@Override public int GL_TEXTURE_MIN_FILTER() { return GL.GL_TEXTURE_MIN_FILTER; }
	@Override public int GL_TEXTURE_WRAP_S() { return GL.GL_TEXTURE_WRAP_S; }
	@Override public int GL_TEXTURE_WRAP_T() { return GL.GL_TEXTURE_WRAP_T; }
	@Override public int GL_UNSIGNED_BYTE() { return GL.GL_UNSIGNED_BYTE; }
	@Override public int GL_RGBA() { return GL.GL_RGBA; }
	@Override public int GL_RGB() { return GL.GL_RGB; }
	@Override public int GL_BGRA() { return GL.GL_BGRA; }
	@Override public int GL_BGR() { return GL.GL_BGR; }
	@Override public int GL_RGBA8() { return GL.GL_RGBA8; }

	// Vertex Array Object
	@Override public Integer createVertexArrayObject() { return OpenGLUtils.JOGAMP_createVertexArrayObject(_getGL()); }
	@Override public void destroyVertexArrayObject(Integer id) { OpenGLUtils.JOGAMP_destroyVertexArrayObject(_getGL(), id); }
	@Override public void glBindVertexArray(Integer id) { _getGL().getGL2ES3().glBindVertexArray(id != null ? id : 0); }

	// Vertex Buffer Object
	@Override public Integer createVertexBufferObject() { return OpenGLUtils.JOGAMP_createVertexBufferObject(_getGL()); }
	@Override public void destroyVertexBufferObject(Integer id) { OpenGLUtils.JOGAMP_destroyVertexBufferObject(_getGL(), id); }
	@Override public void glBindBuffer(int target, Integer id) { _getGL().glBindBuffer(target, id != null ? id : 0); }
	@Override public void glBufferData(int target, Buffer data, int usage) { _getGL().glBufferData(target, data.capacity() * BufferUtils.getElementSize(data), data, usage); }
	@Override public int GL_TRUE() { return GL.GL_TRUE; }
	@Override public int GL_FALSE() { return GL.GL_FALSE; }
	@Override public int GL_FLOAT() { return GL.GL_FLOAT; }
	@Override public int GL_DOUBLE() { return GL2GL3.GL_DOUBLE; }
	@Override public int GL_INT() { return GL2ES2.GL_INT; }
	@Override public int GL_UNSIGNED_INT() { return GL.GL_UNSIGNED_INT; }
	@Override public int GL_STATIC_DRAW() { return GL.GL_STATIC_DRAW; }
	@Override public int GL_ARRAY_BUFFER() { return GL.GL_ARRAY_BUFFER; }
	@Override public int GL_ELEMENT_ARRAY_BUFFER() { return GL.GL_ELEMENT_ARRAY_BUFFER; }

	// Draw
	@Override public void glViewport(int x, int y, int width, int height) { _getGL().glViewport(x, y, width, height); }
	@Override public void glDrawElements(int mode, int count, int type, int off) { _getGL().glDrawElements(mode, count, type, off); }
	@Override public int GL_TRIANGLES() { return GL.GL_TRIANGLES; }

	// Input
	@Override public void GL_INPUT_setCursorPos(Vec2 cursorPos) { inputHandler.GL_INPUT_setCursorPos(cursorPos); }
	@Override public int GL_INPUT_KEYBOARD_PRESS() { return 1; }
	@Override public int GL_INPUT_KEYBOARD_REPEAT() { return 1; }
	@Override public long GL_INPUT_KEYBOARD_KEY_W() { return 'w'; }
	@Override public long GL_INPUT_KEYBOARD_KEY_A() { return 'a'; }
	@Override public long GL_INPUT_KEYBOARD_KEY_S() { return 's'; }
	@Override public long GL_INPUT_KEYBOARD_KEY_D() { return 'd'; }
	@Override public long GL_INPUT_KEYBOARD_KEY_SPACE() { return ' '; }
	@Override public long GL_INPUT_KEYBOARD_KEY_LEFT_SHIFT() { return 'l'; }

	public interface InputHandler {
		void GL_INPUT_setCursorPos(Vec2 cursorPos);
	}
	public static class GLUTessellator implements io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext.GLUTessellator {
		protected final GLUtessellator instance = GLU.gluNewTess();

		@Override public void gluDeleteTess() { GLU.gluDeleteTess(instance); }
		@Override public void gluTessProperty(int which, double value) { GLU.gluTessProperty(instance, which, value); }
		@Override public void gluGetTessProperty(int which, double[] value, int value_offset) { GLU.gluGetTessProperty(instance, which, value, value_offset); }
		@Override public void gluTessNormal(double x, double y, double z) { GLU.gluTessNormal(instance, x, y, z); }
		@Override public void gluTessCallback(int which, GLUTesselatorCallback aCallback) {
			GLU.gluTessCallback(instance, which, new GLUtessellatorCallback() {
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
		@Override public void gluTessVertex(double[] coords, int coords_offset, Object vertexData) { GLU.gluTessVertex(instance, coords, coords_offset, vertexData); }
		@Override public void gluTessBeginPolygon(Object data) { GLU.gluTessBeginPolygon(instance, data); }
		@Override public void gluTessBeginContour() { GLU.gluTessBeginContour(instance); }
		@Override public void gluTessEndContour() { GLU.gluTessEndContour(instance); }
		@Override public void gluTessEndPolygon() { GLU.gluTessEndPolygon(instance); }
		@Override public void gluBeginPolygon() { GLU.gluBeginPolygon(instance); }
		@Override public void gluNextContour(int type) { GLU.gluNextContour(instance, type); }
		@Override public void gluEndPolygon() { GLU.gluEndPolygon(instance); }
	}
}
