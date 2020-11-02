package io.github.NadhifRadityo.Objects.Utilizations;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2ES2;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;
import org.lwjgl.system.MemoryUtil;

import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

@SuppressWarnings("ALL")
public class OpenGLUtils extends GenTypeUtils {
	// STATIC
	private static int getIntThenDestroy(IntBuffer buffer, int index) { try { return buffer.get(index); } finally { deallocate(buffer); } }
	private static void deallocate(Buffer buffer) { BufferUtils.deallocate(buffer); }

	// LWJGL
	private static Object LWJGL_callMethod(String methodN, Class[] classes, Object... args) { try {
		if(classes == null) classes = ClassUtils.toClass(args);
		if(classes.length > args.length) args = Arrays.copyOf(args, classes.length);
		if(classes.length < args.length) throw new IllegalArgumentException();
		Method method = ClassUtils.getPublicMethod(GL45.class, methodN, classes);
		return method.invoke(null, args);
	} catch(Exception e) { throw new Error(e); } }
	private static IntBuffer LWJGL_callMethodIntBuffer(String methodN, int bufferSize, Integer value, boolean deallocateBuffer, Object... args) {
		args = Arrays.copyOf(args, args.length + 1);
		IntBuffer buffer = BufferUtils.allocateIntBuffer(bufferSize);
		if(value != null) { buffer.put(value); buffer.flip(); }
		args[args.length - 1] = buffer;
		Class[] classes = ClassUtils.enhancedWrappersToPrimitives(ClassUtils.toClass(args));
		classes[classes.length - 1] = IntBuffer.class;
		LWJGL_callMethod(methodN, classes, args);
		if(!deallocateBuffer) return buffer;
		deallocate(buffer); return null;
	}

	public static long LWJGL_GLFW_createFrame(int width, int height, String title, long monitor, long share) {
		long result = GLFW.glfwCreateWindow(width, height, title, monitor, share);
		if(result == MemoryUtil.NULL) throw new GLException("Failed to create the GLFW window");
		return result;
	}
	public static void LWJGL_GLFW_destroyFrame(long window) { GLFW.glfwDestroyWindow(window); }

	public static int LWJGL_createProgram() { return GL45.glCreateProgram(); }
	public static void LWJGL_destroyProgram(int program) { GL20.glDeleteProgram(program); }
	public static int LWJGL_getProgramAttributesCount(int program) { return getIntThenDestroy(LWJGL_callMethodIntBuffer("glGetProgramiv", 1, null, false, program, GL20.GL_ACTIVE_ATTRIBUTES), 0); }
	public static int LWJGL_getProgramUniformsCount(int program) { return getIntThenDestroy(LWJGL_callMethodIntBuffer("glGetProgramiv", 1, null, false, program, GL20.GL_ACTIVE_UNIFORMS), 0); }

	public static int LWJGL_createShader(int type, String[] code) {
		int shader = GL20.glCreateShader(type);
		GL20.glShaderSource(shader, StringUtils.merge("\n", code));
		GL20.glCompileShader(shader); return shader;
	}
	public static void LWJGL_destroyShader(int shader) { GL20.glDeleteShader(shader); }
	public static boolean LWJGL_isShaderCompiled(int shader) { return getIntThenDestroy(LWJGL_callMethodIntBuffer("glGetShaderiv", 1, null, false, shader, GL20.GL_COMPILE_STATUS), 0) == GL11.GL_TRUE; }

	public static String LWJGL_getProgramLogInfo(int obj) { return GL20.glGetProgramInfoLog(obj); }
	public static String LWJGL_getShaderLogInfo(int obj) { return GL20.glGetShaderInfoLog(obj); }

	public static int LWJGL_createTexture() { return GL11.glGenTextures(); }
	public static void LWJGL_destroyTexture(int texture) { GL11.glDeleteTextures(texture); }
	public static int LWJGL_getMaxTextureSize() { return GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE); }

	public static int LWJGL_createVertexArrayObject() { return GL30.glGenVertexArrays(); }
	public static void LWJGL_destroyVertexArrayObject(int vao) { GL30.glDeleteVertexArrays(vao); }

	public static int LWJGL_createVertexBufferObject() { return GL30.glGenBuffers(); }
	public static void LWJGL_destroyVertexBufferObject(int vbo) { GL30.glDeleteBuffers(vbo); }

	public static int LWJGL_createFrameBufferObject() { return GL30.glGenFramebuffers(); }
	public static void LWJGL_destroyFrameBufferObject(int fbo) { GL30.glDeleteFramebuffers(fbo); }

	public static int LWJGL_createRenderBufferObject() { return GL30.glGenRenderbuffers(); }
	public static void LWJGL_destroyRenderBufferObject(int rbo) { GL30.glDeleteRenderbuffers(rbo); }

	// JOGAMP
	public static boolean JOGAMP_isSameContext(GL gl1, GL gl2) { return gl1 == gl2; }
	public static boolean JOGAMP_isDifferentContext(GL gl1, GL gl2) { return !JOGAMP_isSameContext(gl1, gl2); }

	// Shaders Utils
	// https://drive.google.com/file/d/0B9hhZie2D-fENWFhODk0M2UtZDY0Ni00YWM5LWIxZGUtMzM0YzMxMmRlODgx/view
	private static Object JOGAMP_callMethod(GL gl, String methodN, Class[] classes, Object... args) { try {
		if(classes == null) classes = ClassUtils.toClass(args);
		if(classes.length > args.length) args = Arrays.copyOf(args, classes.length);
		if(classes.length < args.length) throw new IllegalArgumentException();
		Method method = ClassUtils.getPublicMethod(gl.getClass(), methodN, classes);
		return method.invoke(gl, args);
	} catch(Exception e) { throw new Error(e); } }
	private static IntBuffer JOGAMP_callMethodIntBuffer(GL gl, String methodN, int bufferSize, Integer value, boolean deallocateBuffer, Object... args) {
		args = Arrays.copyOf(args, args.length + 1);
		IntBuffer buffer = BufferUtils.allocateIntBuffer(bufferSize);
		if(value != null) { buffer.put(value); buffer.flip(); }
		args[args.length - 1] = buffer;
		Class[] classes = ClassUtils.enhancedWrappersToPrimitives(ClassUtils.toClass(args));
		classes[classes.length - 1] = IntBuffer.class;
		JOGAMP_callMethod(gl, methodN, classes, args);
		if(!deallocateBuffer) return buffer;
		deallocate(buffer); return null;
	}

	public static int JOGAMP_createProgram(GL _gl) { GL2ES2 gl = _gl.getGL2ES2(); return gl.glCreateProgram(); }
	public static void JOGAMP_destroyProgram(GL _gl, int program) { GL2ES2 gl = _gl.getGL2ES2(); gl.glDeleteProgram(program); }
	public static int JOGAMP_getProgramAttributesCount(GL gl, int program) { return getIntThenDestroy(JOGAMP_callMethodIntBuffer(gl.getGL2ES2(), "glGetProgramiv", 1, null, false, program, GL2ES2.GL_ACTIVE_ATTRIBUTES), 0); }
	public static int JOGAMP_getProgramUniformsCount(GL gl, int program) { return getIntThenDestroy(JOGAMP_callMethodIntBuffer(gl.getGL2ES2(), "glGetProgramiv", 1, null, false, program, GL2ES2.GL_ACTIVE_UNIFORMS), 0); }

	public static int JOGAMP_createShader(GL _gl, int type, String[] code) {
		if(code.length > 1) code = new String[] { StringUtils.merge("\n", code) };
		GL2ES2 gl = _gl.getGL2ES2();
		int shader = gl.glCreateShader(type);
		gl.glShaderSource(shader, 1, code, null);
		gl.glCompileShader(shader); return shader;
	}
	public static void JOGAMP_destroyShader(GL _gl, int shader) { GL2ES2 gl = _gl.getGL2ES2(); gl.glDeleteShader(shader); }
	public static boolean JOGAMP_isShaderCompiled(GL gl, int shader) { return getIntThenDestroy(JOGAMP_callMethodIntBuffer(gl.getGL2ES2(), "glGetShaderiv", 1, null, false, shader, GL2ES2.GL_COMPILE_STATUS), 0) == GL.GL_TRUE; }

	private static String JOGAMP_getLogInfo(String ivMethodN, String infoMethodN, GL _gl, int obj) { try { GL2ES2 gl = _gl.getGL2ES2();
		IntBuffer ival = JOGAMP_callMethodIntBuffer(gl, ivMethodN, 1, null, false, obj, GL2ES2.GL_INFO_LOG_LENGTH); try {
		int size = ival.get(0); if(size <= 0) return ""; ival.flip(); ByteBuffer log = BufferUtils.allocateByteBuffer(size);
		JOGAMP_callMethod(gl, infoMethodN, new Class[] { int.class, int.class, IntBuffer.class, ByteBuffer.class }, obj, size, ival, log);
		byte[] infoBytes = new byte[size]; log.get(infoBytes); deallocate(log); return new String(infoBytes); } finally { deallocate(ival); }
	} catch(Exception e) { throw new Error(e); } }
	public static String JOGAMP_getProgramLogInfo(GL gl, int obj) { return JOGAMP_getLogInfo("glGetProgramiv", "glGetProgramInfoLog", gl, obj); }
	public static String JOGAMP_getShaderLogInfo(GL gl, int obj) { return JOGAMP_getLogInfo("glGetShaderiv", "glGetShaderInfoLog", gl, obj); }

	public static int JOGAMP_createTexture(GL gl) { return getIntThenDestroy(JOGAMP_callMethodIntBuffer(gl, "glGenTextures", 1, null, false, 1), 0); }
	public static void JOGAMP_destroyTexture(GL gl, int texture) { JOGAMP_callMethodIntBuffer(gl, "glDeleteTextures", 1, texture, true, 1); }
	public static int JOGAMP_getMaxTextureSize(GL gl) { return getIntThenDestroy(JOGAMP_callMethodIntBuffer(gl, "glGetIntegerv", 1, null, false, GL.GL_MAX_TEXTURE_SIZE), 0); }

	public static int JOGAMP_createVertexArrayObject(GL gl) { return getIntThenDestroy(JOGAMP_callMethodIntBuffer(gl.getGL2ES3(), "glGenVertexArrays", 1, null, false, 1), 0); }
	public static void JOGAMP_destroyVertexArrayObject(GL gl, int vao) { JOGAMP_callMethodIntBuffer(gl.getGL2ES3(), "glDeleteVertexArrays", 1, vao, true, 1); }

	public static int JOGAMP_createVertexBufferObject(GL gl) { return getIntThenDestroy(JOGAMP_callMethodIntBuffer(gl, "glGenBuffers", 1, null, false, 1), 0); }
	public static void JOGAMP_destroyVertexBufferObject(GL gl, int vbo) { JOGAMP_callMethodIntBuffer(gl, "glDeleteBuffers", 1, vbo, true, 1); }

	public static int JOGAMP_createFrameBufferObject(GL gl) { return getIntThenDestroy(JOGAMP_callMethodIntBuffer(gl, "glGenFramebuffers", 1, null, false, 1), 0); }
	public static void JOGAMP_destroyFrameBufferObject(GL gl, int fbo) { JOGAMP_callMethodIntBuffer(gl, "glDeleteFramebuffers", 1, fbo, true, 1); }

	public static int JOGAMP_createRenderBufferObject(GL gl) { return getIntThenDestroy(JOGAMP_callMethodIntBuffer(gl, "glGenRenderbuffers", 1, null, false, 1), 0); }
	public static void JOGAMP_destroyRenderBufferObject(GL gl, int rbo) { JOGAMP_callMethodIntBuffer(gl, "glDeleteRenderbuffers", 1, rbo, true, 1); }

	public static int JOGAMP_getActiveUniformMaxLength(GL gl, int program) { return getIntThenDestroy(JOGAMP_callMethodIntBuffer(gl.getGL2ES2(), "glGetProgramiv", 1, null, false, program, GL2ES2.GL_ACTIVE_UNIFORM_MAX_LENGTH), 0); }
	public static int JOGAMP_getActiveAttributeMaxLength(GL gl, int program) { return getIntThenDestroy(JOGAMP_callMethodIntBuffer(gl.getGL2ES2(), "glGetProgramiv", 1, null, false, program, GL2ES2.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH), 0); }
}
