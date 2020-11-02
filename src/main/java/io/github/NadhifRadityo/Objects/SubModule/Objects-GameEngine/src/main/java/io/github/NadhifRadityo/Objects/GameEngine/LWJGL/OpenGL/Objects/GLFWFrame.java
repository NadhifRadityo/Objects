package io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.Objects;

import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.AnsiLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.FormatHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.NewLineHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.SeverityLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.TimeLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInListener.SystemOutListener;
import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;
import io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.GLContext;
import io.github.NadhifRadityo.Objects.Utilizations.GameEngine.Debug;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLException;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.OpenGLNativeHolder;
import io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryUtil;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

public class GLFWFrame extends OpenGLNativeHolder<Integer> {
	public static boolean DEBUG;
	static {
		if(!glfwInit())
			throw new GLException("Unable to initialize GLFW");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
	}

	protected GLEventListener listener;
	protected boolean initialized = false;
	protected final Callback[] callbacks = new Callback[5];

	protected int lastWindowX;
	protected int lastWindowY;
	protected int lastWindowWidth;
	protected int lastWindowHeight;
	protected double lastMouseX;
	protected double lastMouseY;

	public GLFWFrame(int width, int height, String title, long monitor, long share) {
		super(!DEBUG ? new GLContext(OpenGLUtils.LWJGL_GLFW_createFrame(width, height, title, monitor, share)) : Debug.wrap(new GLContext(OpenGLUtils.LWJGL_GLFW_createFrame(width, height, title, monitor, share)), Debug.logger));
		callbacks[0] = ((GLContext) getGL()).glfwSetWindowPosCallback((window, _x, _y) -> { if(listener == null) return; lastWindowX = _x; lastWindowY = _y; listener.reshape(this, _x, _y, lastWindowWidth, lastWindowHeight); });
		callbacks[1] = ((GLContext) getGL()).glfwSetWindowSizeCallback((window, _width, _height) -> { if(listener == null) return; lastWindowWidth = _width; lastWindowHeight = _height; listener.reshape(this, lastWindowX, lastWindowY, _width, _height); });
		callbacks[2] = ((GLContext) getGL()).glfwSetKeyCallback((window, key, scanCode, action, mods) -> { if(listener == null) return; listener.keyboard(this, key, scanCode, action, mods); });
		callbacks[3] = ((GLContext) getGL()).glfwSetCursorPosCallback((window, x, y) -> { if(listener == null) return; lastMouseX = x; lastMouseY = y; listener.mouse(this, x, y, -1, -1, -1); });
		callbacks[4] = ((GLContext) getGL()).glfwSetMouseButtonCallback((window, button, action, mods) -> { if(listener == null) return; listener.mouse(this, lastMouseX, lastMouseY, button, action, mods); });
	}
	public GLFWFrame(int width, int height, String title) {
		this(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
	}

	public GLEventListener getListener() { return listener; }
	public boolean isInitialized() { return initialized; }
	public void setListener(GLEventListener listener) {
		if(this.listener == listener) return;
		GLEventListener oldListener = this.listener;
		this.listener = listener; this.initialized = false;
		if(oldListener != null) oldListener.dispose(this);
		if(!isDead()) ((GLContext) getGL()).setInMainLoop(false);
	}

	public Size getWindowSize() { return ((GLContext) getGL()).glfwGetWindowSize(); }
	public Size getFrameBufferSize() { return ((GLContext) getGL()).glfwGetFramebufferSize(); }

	public void draw() {
		assertNotDead(); assertCreated(); if(listener == null) return;
		if(!initialized) { initialized = true; ((GLContext) getGL()).setInMainLoop(true); listener.init(this); return; }
		glfwPollEvents(); listener.display(this);
	}

	@Override protected void arrange() throws Exception {
		getInstance().setId(Objects.hashCode(getGL()));
		GL.createCapabilities();
	}
	@Override protected void disarrange() {
		setListener(null);
		for(Callback callback : callbacks)
			if(callback != null) callback.free();
		((GLContext) getGL()).glfwFreeCallbacks();
		OpenGLUtils.LWJGL_GLFW_destroyFrame((long) getGL().getGL());
	}

	public interface GLEventListener {
		void init(GLFWFrame frame);
		void display(GLFWFrame frame);
		void reshape(GLFWFrame frame, int x, int y, int width, int height);
		void dispose(GLFWFrame frame);
		void keyboard(GLFWFrame frame, long key, int scanCode, int action, int mods);
		void mouse(GLFWFrame frame, double x, double y, int button, int action, int mods);
	}
}
