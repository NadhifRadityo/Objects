package io.github.NadhifRadityo.Objects.Utilizations;

import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.Wincon;
import com.sun.jna.ptr.IntByReference;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;

import java.nio.charset.Charset;

public class ConsoleUtils {
	protected static final CLibrary clib;
	public static final boolean ANSI_SUPPORTED;
	protected static final int GET_CONSOLE_SIZE_WINDOWS = 1;
	protected static final int GET_CONSOLE_SIZE_LINUX_NATIVE = 2;
	protected static final int GET_CONSOLE_SIZE_LINUX_ENV = 4;
	protected static final int GET_CONSOLE_SIZE_STTY = 8;
	protected static final int GET_CONSOLE_SIZE_TPUT = 16;
	public static final int GET_CONSOLE_SIZE_POSSIBLE;

	static {
		clib = SystemUtils.IS_OS_UNIX ? Native.load(Platform.C_LIBRARY_NAME, CLibrary.class) : null;

		ReferencedCallback.PBooleanReferencedCallback enableVirtualTerminalProcessing = (args) -> {
			Kernel32 kernel = (Kernel32) args[0];
			WinNT.HANDLE handleOut = kernel.GetStdHandle(Wincon.STD_OUTPUT_HANDLE);
			if(handleOut == WinBase.INVALID_HANDLE_VALUE) return false;
			IntByReference dwOriginalOutMode = new IntByReference(0);
			if(!kernel.GetConsoleMode(handleOut, dwOriginalOutMode)) return true; // Maybe an IDE console
			return kernel.SetConsoleMode(handleOut, dwOriginalOutMode.getValue() | 0x0004 | 0x0008) || kernel.SetConsoleMode(handleOut, dwOriginalOutMode.getValue() | 0x0004);
		}; ANSI_SUPPORTED = !SystemUtils.IS_OS_WINDOWS || enableVirtualTerminalProcessing._get(Kernel32.INSTANCE);

		ReferencedCallback.PIntegerReferencedCallback getConsoleSizePossibleMethods = (args) -> {
			int result = 0; int[] temp = new int[2];
			if(SystemUtils.IS_OS_WINDOWS) try { getConsoleSizeWindows(temp); result |= GET_CONSOLE_SIZE_WINDOWS; } catch(Throwable ignored) { }
			if(SystemUtils.IS_OS_UNIX) {
				try { getConsoleSizeLinux_native(temp); result |= GET_CONSOLE_SIZE_LINUX_NATIVE; } catch(Throwable ignored) { }
				try { getConsoleSizeLinux_env(temp); result |= GET_CONSOLE_SIZE_LINUX_ENV; } catch(Throwable ignored) { }
			}
			try { getConsoleSize_stty(temp); result |= GET_CONSOLE_SIZE_STTY; } catch(Throwable ignored) { }
			try { getConsoleSize_tput(temp); result |= GET_CONSOLE_SIZE_TPUT; } catch(Throwable ignored) { }
			return result;
		}; GET_CONSOLE_SIZE_POSSIBLE = getConsoleSizePossibleMethods._get();
	}

	public static void getConsoleSize(int[] size) {
		if(size.length != 2) throw new IllegalArgumentException("Input not supported");
		if(BitwiseUtils.is(GET_CONSOLE_SIZE_POSSIBLE, GET_CONSOLE_SIZE_WINDOWS)) { getConsoleSizeWindows(size); return; }
		if(BitwiseUtils.is(GET_CONSOLE_SIZE_POSSIBLE, GET_CONSOLE_SIZE_LINUX_NATIVE)) { getConsoleSizeLinux_native(size); return; }
		if(BitwiseUtils.is(GET_CONSOLE_SIZE_POSSIBLE, GET_CONSOLE_SIZE_LINUX_ENV)) { getConsoleSizeLinux_env(size); return; }
		if(BitwiseUtils.is(GET_CONSOLE_SIZE_POSSIBLE, GET_CONSOLE_SIZE_STTY)) { getConsoleSize_stty(size); return; }
		if(BitwiseUtils.is(GET_CONSOLE_SIZE_POSSIBLE, GET_CONSOLE_SIZE_TPUT)) { getConsoleSize_tput(size); return; }
		throw new UnsupportedOperationException("Cannot get console size");
	}

	protected static void getConsoleSizeWindows(int[] size) {
		throw new UnsupportedOperationException("Not yet implemented");
		// JNA still have not released it yet.
//		Kernel32 kernel32 = Kernel32.INSTANCE;
//		kernel32.GetStdHandle(Wincon.STD_OUTPUT_HANDLE);
//		Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
//		Kernel32.INSTANCE.GetConsoleScreenBufferInfo(consoleOut, info);
//		size[0] = info.dwSize.X; size[0] = info.dwSize.Y;
	}
	protected static void getConsoleSizeLinux_native(int[] size) {
		CLibrary.winsize winsize = new CLibrary.winsize();
		clib.ioctl(0, 0x00005413/*TIOCGWINSZ*/, winsize);
		size[0] = winsize.ws_col; size[1] = winsize.ws_row;
	}
	protected static void getConsoleSizeLinux_env(int[] size) {
		size[0] = Integer.parseInt(System.getenv("COLUMNS"));
		size[1] = Integer.parseInt(System.getenv("LINES"));
	}
	protected static void getConsoleSize_stty(int[] size) { ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> {
		// Always fail because process stream is virtual not GUI
		throw new UnsupportedOperationException("Not yet implemented");
//		Runtime runtime = Runtime.getRuntime(); Process process;
//		process = runtime.exec("stty size"); process.waitFor();
//		String[] result = StreamUtils.toString(process.getInputStream(), Charset.defaultCharset()).split(" ");
//		size[0] = Integer.parseInt(result[1]); size[1] = Integer.parseInt(result[0]); // stty size returns height then width
	}); }
	protected static void getConsoleSize_tput(int[] size) { ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> {
		// Always fail because process stream is virtual not GUI
		throw new UnsupportedOperationException("Not yet implemented");
//		Runtime runtime = Runtime.getRuntime(); Process process;
//		process = runtime.exec("tput cols"); process.waitFor();
//		size[0] = Integer.parseInt(StreamUtils.toString(process.getInputStream(), Charset.defaultCharset()).replaceAll("\\s", ""));
//		process = runtime.exec("tput lines"); process.waitFor();
//		size[1] = Integer.parseInt(StreamUtils.toString(process.getInputStream(), Charset.defaultCharset()).replaceAll("\\s", ""));
	}); }

	protected interface CLibrary extends Library {
		void ioctl(int fd, int command, winsize size) throws LastErrorException;
		String ctermid();

		@SuppressWarnings("jol")
		@Structure.FieldOrder({"ws_row", "ws_col", "ws_xpixel", "ws_ypixel"})
		class winsize extends Structure {
			public short ws_row;
			public short ws_col;
			public short ws_xpixel;
			public short ws_ypixel;

			public winsize() { }
		}
	}
}
