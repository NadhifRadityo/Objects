package io.github.NadhifRadityo.Objects.Utilizations.GameEngine;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.AnsiLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.FormatHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.NewLineHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.SeverityLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.TimeLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInListener.SystemOutListener;
import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.CLContext;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.PointerBuffer;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ClassUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;
import io.github.NadhifRadityo.Objects.Utilizations.SystemUtils;
import javassist.util.proxy.ProxyFactory;
import jogamp.opengl.gl4.GL4bcImpl;
import jogamp.opengl.glu.error.Error;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Debug {
	public static final Logger logger;
	static {
		logger = new Logger();
		logger.addHandler(new AnsiLogHandler(), AnsiLogHandler.DEFAULT_PRIORITY);
		logger.addHandler(new TimeLogHandler(), TimeLogHandler.DEFAULT_PRIORITY);
		logger.addHandler(new SeverityLogHandler(), SeverityLogHandler.DEFAULT_PRIORITY);
		logger.addHandler(new NewLineHandler(), NewLineHandler.DEFAULT_PRIORITY);
		logger.addHandler(new FormatHandler(), FormatHandler.DEFAULT_PRIORITY);
		logger.addListener(new SystemOutListener());
	}

	public static void RENDERDOC_attach(File renderdocLib) throws NoSuchFieldException, IllegalAccessException {
		if(!renderdocLib.exists()) throw new IllegalArgumentException("File not found");
		JarUtils.addLibraryPath(renderdocLib.getAbsolutePath()); System.loadLibrary("renderdoc");
	}
	public static RenderdocAPI RENDERDOC_attachInstance(File renderdocLib) throws NoSuchFieldException, IllegalAccessException {
		if(!renderdocLib.exists()) throw new IllegalArgumentException("File not found");
		JarUtils.addLibraryPath(renderdocLib.getAbsolutePath());
		renderdocLib = new File(renderdocLib, "renderdoc" + (SystemUtils.IS_OS_WINDOWS ? ".dll" : ".so"));
		return Native.load(renderdocLib.getAbsolutePath(), RenderdocAPI.class);
	}
	public static void NSIGHT_attach(File nsightLib) throws IOException {
		if(!nsightLib.exists()) throw new IllegalArgumentException("File not found");
		nsightLib = new File(nsightLib, "host\\windows-desktop-nomad-x64\\nv-nsight-launcher.exe");
		Runtime.getRuntime().exec(nsightLib.getAbsolutePath());
	}

	@Deprecated public static <ID extends Number> GLContext<ID> wrap(GLContext<ID> context, Logger logger, boolean glError, boolean interrupt, boolean showArgs) { return (GLContext<ID>) ExceptionUtils.doSilentThrowsReferencedCallback(ExceptionUtils.silentException, (_args) -> {
		ProxyFactory contextFactory = new ProxyFactory(); contextFactory.setSuperclass(context.getClass());
		contextFactory.setInterfaces(new Class[] { DebuggerAttach.class });
		Class[] constructorTypes = context.getClass().getConstructors()[0].getParameterTypes();
		Method METHOD_GLContext_glGetError = ClassUtils.getPublicMethod(context.getClass(), "glGetError");
		return contextFactory.create(constructorTypes, new Object[constructorTypes.length], (self, method, proceed, args) -> {
			if(interrupt && !method.getName().equals("hashCode")) logger.log(method.getName(), showArgs ? ArrayUtils.deepToString(args).replaceAll("\n", "").replaceAll("\t", "") : "");
			if(method.getName().equals("equals")) return args[0] == self || (boolean) method.invoke(context, args); if(method.getName().equals("getReal")) return context;
			Object result = method.invoke(context, args); if(!glError || !method.getName().startsWith("gl") || method.getName().equals("glGetError")) return result;
			int errorCode = (int) METHOD_GLContext_glGetError.invoke(context, new Object[0]);
			if(errorCode != 0) logger.error(Error.gluErrorString(errorCode)); return result;
		});
	}); }
	@Deprecated public static <ID extends Number> GLContext<ID> wrap(GLContext<ID> context, Logger logger) {
		return wrap(context, logger, true, false, false);
	}

	@Deprecated public static <ID extends Number> CLContext<ID> wrap(CLContext<ID> context, Logger logger, boolean interrupt, boolean showArgs) { return (CLContext<ID>) ExceptionUtils.doSilentThrowsReferencedCallback(ExceptionUtils.silentException, (_args) -> {
		ProxyFactory contextFactory = new ProxyFactory(); contextFactory.setSuperclass(context.getClass());
		contextFactory.setInterfaces(new Class[] { DebuggerAttach.class });
		Class[] constructorTypes = context.getClass().getConstructors()[0].getParameterTypes();
		return contextFactory.create(constructorTypes, new Object[constructorTypes.length], (self, method, proceed, args) -> {
			if(interrupt && !method.getName().equals("hashCode")) logger.log(method.getName(), showArgs ? ArrayUtils.deepToString(args).replaceAll("\n", "").replaceAll("\t", "") : "");
			if(method.getName().equals("equals")) return args[0] == self || (boolean) method.invoke(context, args); if(method.getName().equals("getReal")) return context;
			Object result = method.invoke(context, args); return result;
		});
	}); }

	public static boolean LOG_ENABLED = true;
	@Deprecated public static GL4bcImpl interrupt(GL4bcImpl object, Logger logger, boolean showArgs) { return (GL4bcImpl) ExceptionUtils.doSilentThrowsReferencedCallback(ExceptionUtils.silentException, (_args) -> {
		ProxyFactory contextFactory = new ProxyFactory(); contextFactory.setSuperclass(object.getClass());
		Class[] constructorTypes = object.getClass().getConstructors()[0].getParameterTypes();
		return contextFactory.create(constructorTypes, new Object[] { object.getGLProfile(), object.getContext() }, (self, method, proceed, args) -> {
			if(logger != null && LOG_ENABLED) logger.log(method.getName(), showArgs ? ArrayUtils.deepToString(args).replaceAll("\n", "").replaceAll("\t", "") : "");
			return method.invoke(object, args);
		});
	}); }

	public interface DebuggerAttach {
		Object getReal();
	}

	public interface RenderdocAPI extends Library {
		int RENDERDOC_GetAPI(int version, Buffer outAPIPointers);

		@Structure.FieldOrder({
				"NATIVE_METHOD_GetAPIVersion", "NATIVE_METHOD_SetCaptureOptionU32", "NATIVE_METHOD_SetCaptureOptionF32", "NATIVE_METHOD_GetCaptureOptionU32", "NATIVE_METHOD_GetCaptureOptionF32",
				"NATIVE_METHOD_SetFocusToggleKeys", "NATIVE_METHOD_SetCaptureKeys", "NATIVE_METHOD_GetOverlayBits", "NATIVE_METHOD_MaskOverlayBits", "NATIVE_METHOD_RemoveHooks",
				"NATIVE_METHOD_UnloadCrashHandler", "NATIVE_METHOD_SetCaptureFilePathTemplate", "NATIVE_METHOD_GetCaptureFilePathTemplate", "NATIVE_METHOD_GetNumCaptures", "NATIVE_METHOD_GetCapture",
				"NATIVE_METHOD_TriggerCapture", "NATIVE_METHOD_IsTargetControlConnected", "NATIVE_METHOD_LaunchReplayUI", "NATIVE_METHOD_SetActiveWindow", "NATIVE_METHOD_StartFrameCapture",
				"NATIVE_METHOD_IsFrameCapturing", "NATIVE_METHOD_EndFrameCapture", "NATIVE_METHOD_DiscardFrameCapture", "NATIVE_METHOD_TriggerMultiFrameCapture", "NATIVE_METHOD_SetCaptureFileComments" })
		class RenderdocInstance extends Structure {
			public GetAPIVersion NATIVE_METHOD_GetAPIVersion;
			public SetCaptureOptionU32 NATIVE_METHOD_SetCaptureOptionU32;
			public SetCaptureOptionF32 NATIVE_METHOD_SetCaptureOptionF32;
			public GetCaptureOptionU32 NATIVE_METHOD_GetCaptureOptionU32;
			public GetCaptureOptionF32 NATIVE_METHOD_GetCaptureOptionF32;
			public SetFocusToggleKeys NATIVE_METHOD_SetFocusToggleKeys;
			public SetCaptureKeys NATIVE_METHOD_SetCaptureKeys;
			public GetOverlayBits NATIVE_METHOD_GetOverlayBits;
			public MaskOverlayBits NATIVE_METHOD_MaskOverlayBits;
			public RemoveHooks NATIVE_METHOD_RemoveHooks;
			public UnloadCrashHandler NATIVE_METHOD_UnloadCrashHandler;
			public SetCaptureFilePathTemplate NATIVE_METHOD_SetCaptureFilePathTemplate;
			public GetCaptureFilePathTemplate NATIVE_METHOD_GetCaptureFilePathTemplate;
			public GetNumCaptures NATIVE_METHOD_GetNumCaptures;
			public GetCapture NATIVE_METHOD_GetCapture;
			public TriggerCapture NATIVE_METHOD_TriggerCapture;
			public IsTargetControlConnected NATIVE_METHOD_IsTargetControlConnected;
			public LaunchReplayUI NATIVE_METHOD_LaunchReplayUI;
			public SetActiveWindow NATIVE_METHOD_SetActiveWindow;
			public StartFrameCapture NATIVE_METHOD_StartFrameCapture;
			public IsFrameCapturing NATIVE_METHOD_IsFrameCapturing;
			public EndFrameCapture NATIVE_METHOD_EndFrameCapture;
			public DiscardFrameCapture NATIVE_METHOD_DiscardFrameCapture;
			public TriggerMultiFrameCapture NATIVE_METHOD_TriggerMultiFrameCapture;
			public SetCaptureFileComments NATIVE_METHOD_SetCaptureFileComments;

			public RenderdocInstance(PointerBuffer pointer) {
				super(new Pointer(pointer.get()));
				read();
			}

			public void GetAPIVersion(IntBuffer major, IntBuffer minor, IntBuffer patch) { NATIVE_METHOD_GetAPIVersion.invoke(major, minor, patch); }
			public int SetCaptureOptionU32(int opt, int val) { return NATIVE_METHOD_SetCaptureOptionU32.invoke(opt, val); }
			public int SetCaptureOptionF32(int opt, float val) { return NATIVE_METHOD_SetCaptureOptionF32.invoke(opt, val); }
			public int GetCaptureOptionU32(int opt) { return NATIVE_METHOD_GetCaptureOptionU32.invoke(opt); }
			public float GetCaptureOptionF32(int opt) { return NATIVE_METHOD_GetCaptureOptionF32.invoke(opt); }
			public void SetFocusToggleKeys(IntBuffer keys, int num) { NATIVE_METHOD_SetFocusToggleKeys.invoke(keys, num); }
			public void SetCaptureKeys(IntBuffer keys, int num) { NATIVE_METHOD_SetCaptureKeys.invoke(keys, num); }
			public int GetOverlayBits() { return NATIVE_METHOD_GetOverlayBits.invoke(); }
			public void MaskOverlayBits(int And, int Or) { NATIVE_METHOD_MaskOverlayBits.invoke(And, Or); }
			public void RemoveHooks() { NATIVE_METHOD_RemoveHooks.invoke(); }
			public void UnloadCrashHandler() { NATIVE_METHOD_UnloadCrashHandler.invoke(); }
			public void SetCaptureFilePathTemplate(String pathtemplate) { NATIVE_METHOD_SetCaptureFilePathTemplate.invoke(pathtemplate); }
			public String GetCaptureFilePathTemplate() { return NATIVE_METHOD_GetCaptureFilePathTemplate.invoke(); }
			public int GetNumCaptures() { return NATIVE_METHOD_GetNumCaptures.invoke(); }
			public int GetCapture(int idx, String filename, IntBuffer pathlength, LongBuffer timestamp) { return NATIVE_METHOD_GetCapture.invoke(idx, filename, pathlength, timestamp); }
			public void TriggerCapture() { NATIVE_METHOD_TriggerCapture.invoke(); }
			public int IsTargetControlConnected() { return NATIVE_METHOD_IsTargetControlConnected.invoke(); }
			public int LaunchReplayUI(int connectTargetControl, String cmdline) { return NATIVE_METHOD_LaunchReplayUI.invoke(connectTargetControl, cmdline); }
			public void SetActiveWindow(long device, long wndHandle) { NATIVE_METHOD_SetActiveWindow.invoke(device, wndHandle); }
			public void StartFrameCapture(long device, long wndHandle) { NATIVE_METHOD_StartFrameCapture.invoke(device, wndHandle); }
			public int IsFrameCapturing() { return NATIVE_METHOD_IsFrameCapturing.invoke(); }
			public int EndFrameCapture(long device, long wndHandle) { return NATIVE_METHOD_EndFrameCapture.invoke(device, wndHandle); }
			public int DiscardFrameCapture(long device, long wndHandle) { return NATIVE_METHOD_DiscardFrameCapture.invoke(device, wndHandle); }
			public void TriggerMultiFrameCapture(int numFrames) { NATIVE_METHOD_TriggerMultiFrameCapture.invoke(numFrames); }
			public void SetCaptureFileComments(String filePath, String comments) { NATIVE_METHOD_SetCaptureFileComments.invoke(filePath, comments); }

			interface GetAPIVersion extends Callback { void invoke(IntBuffer major, IntBuffer minor, IntBuffer patch); }
			interface SetCaptureOptionU32 extends Callback { int invoke(int opt, int val); }
			interface SetCaptureOptionF32 extends Callback { int invoke(int opt, float val); }
			interface GetCaptureOptionU32 extends Callback { int invoke(int opt); }
			interface GetCaptureOptionF32 extends Callback { float invoke(int opt); }
			interface SetFocusToggleKeys extends Callback { void invoke(IntBuffer keys, int num); }
			interface SetCaptureKeys extends Callback { void invoke(IntBuffer keys, int num); }
			interface GetOverlayBits extends Callback { int invoke(); }
			interface MaskOverlayBits extends Callback { void invoke(int And, int Or); }
			interface RemoveHooks extends Callback { void invoke(); }
			interface UnloadCrashHandler extends Callback { void invoke(); }
			interface SetCaptureFilePathTemplate extends Callback { void invoke(String pathtemplate); }
			interface GetCaptureFilePathTemplate extends Callback { String invoke(); }
			interface GetNumCaptures extends Callback { int invoke(); }
			interface GetCapture extends Callback { int invoke(int idx, String filename, IntBuffer pathlength, LongBuffer timestamp); }
			interface TriggerCapture extends Callback { void invoke(); }
			interface IsTargetControlConnected extends Callback { int invoke(); }
			interface LaunchReplayUI extends Callback { int invoke(int connectTargetControl, String cmdline); }
			interface SetActiveWindow extends Callback { void invoke(long device, long wndHandle); }
			interface StartFrameCapture extends Callback { void invoke(long device, long wndHandle); }
			interface IsFrameCapturing extends Callback { int invoke(); }
			interface EndFrameCapture extends Callback { int invoke(long device, long wndHandle); }
			interface DiscardFrameCapture extends Callback { int invoke(long device, long wndHandle); }
			interface TriggerMultiFrameCapture extends Callback { void invoke(int numFrames); }
			interface SetCaptureFileComments extends Callback { void invoke(String filePath, String comments); }
		}
	}
}
