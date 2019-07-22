package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.AWTComponent.AnsiSupportedTextArea;
import io.github.NadhifRadityo.Objects.AWTComponent.ModernScrollPane;
import io.github.NadhifRadityo.Objects.AWTComponent.PooledObjectMonitor;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.AnsiLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.SeverityLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.TimeLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInListener.SystemOutListener;
import io.github.NadhifRadityo.Objects.Console.LogLevel;
import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.IOStream.WriteableInputStream;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Thread.Handler;
import io.github.NadhifRadityo.Objects.Thread.HandlerThread;
import io.github.NadhifRadityo.Objects.Thread.Looper;
import io.github.NadhifRadityo.Objects.Utilizations.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainTest {

	public static void main(String... args) throws Exception {
		Looper.prepareMainLooper();
		Looper.myLooper().setExceptionHandler(ExceptionUtils.exceptionPrintHandler);
//		IOStreamWritableInputStreamTest();
//		ArraysDeepToStringTest();
//		PoolTest();
		Logger logger = LoggerTestWrapper.start(args);
//		PooledObjectMonitorTest();
		AnsiSupportedTextAreaTest(logger);
		Looper.loop();
	}

	@SuppressWarnings("deprecation")
	public static void IOStreamWritableInputStreamTest() {
		WriteableInputStream writeableInputStream = new WriteableInputStream();
		HandlerThread handlerThread = new HandlerThread("Test");
		handlerThread.start();
		handlerThread.getThreadHandler().postThrowable(() -> {
			int count; byte[] buffer = new byte[1024];
			while((count = writeableInputStream.read(buffer, 0, buffer.length)) != -1) {
				System.out.println(buffer.length + " ? " + count);
				System.out.println(new String(buffer, 0, count));
			} System.out.println("Exited"); handlerThread.stop();
		});

		Handler.getMain().postThrowable(() -> {
			int count; byte[] buffer = new byte[1024 * 1024];
			while((count = System.in.read(buffer, 0, buffer.length)) != -1) {
				String line = new String(buffer, 0, count - 2);
				if(line.equalsIgnoreCase("exit")) { writeableInputStream.close(); break;
				} else writeableInputStream.write(line.getBytes());
			}
		});
	}

	public static void ArraysDeepToStringTest() {
		Object[] object = new Object[] {
				new int[] {
						27, 17, 7, 3
				},
				new String[] {
						"twenty seven",
						"seven teen",
						"three",
						null
				},
				new long[][] {
						new long[] {
								System.currentTimeMillis(),
								System.currentTimeMillis() - PublicRandom.getRandom().nextLong()
						},
						new long[] {
								System.currentTimeMillis() - PublicRandom.getRandom().nextLong(),
								System.currentTimeMillis() - PublicRandom.getRandom().nextLong()
						},
						null
				},
				new short[][] {
						null,
						null,
						new short[] {
								((Integer) PublicRandom.getRandom().nextInt()).shortValue(),
								((Integer) PublicRandom.getRandom().nextInt()).shortValue()
						}
				},
				new boolean[][][] {
						new boolean[][] {
								new boolean[] {
										false, true
								},
								null,
								new boolean[] {
										false
								}
						},
						null
				},
				new Object[][] {
						new Object[] {
								null,
								"WOY",
								((Integer) PublicRandom.getRandom().nextInt()).shortValue(),
								System.currentTimeMillis() - PublicRandom.getRandom().nextLong(),
								new RandomString().toString()
						}
				},
				new StringBuilder("deep to string test")
		};

		String result = ArrayUtils.deepToString(object);
		long start = System.currentTimeMillis();
		for(int i = 0; i < 20000; i++) ArrayUtils.deepToString(object);
		System.out.println("20000 Repetitions -> Took: " + (System.currentTimeMillis() - start) + "ms");
		// Not the best
		// 20000 Repetitions -> Took: 1106ms
		System.out.println(result);
	}

	public static void PoolTest() {
		long start;
		long delta;
		int sessions = 100;
		int repetitions = 50000;

		long nonThreadMax = Long.MIN_VALUE;
		long nonThreadMin = Long.MAX_VALUE;

		for(int j = 0; j < sessions; j++) {
			System.out.println("------- Starting New Session -------");

			start = System.currentTimeMillis();
			for(int i = 0; i < repetitions; i++) Pool.returnObject(ArrayList.class, Pool.tryBorrow(Pool.getPool(ArrayList.class)));
			delta = System.currentTimeMillis() - start; nonThreadMax = Math.max(delta, nonThreadMax); nonThreadMin = Math.min(delta, nonThreadMin);
			System.out.println("ArrayList, Non Threading, " + repetitions + " Repetitions -> Took: " + delta + "ms");

			start = System.currentTimeMillis();
			for(int i = 0; i < repetitions; i++) Pool.returnObject(HashMap.class, Pool.tryBorrow(Pool.getPool(HashMap.class)));
			delta = System.currentTimeMillis() - start; nonThreadMax = Math.max(delta, nonThreadMax); nonThreadMin = Math.min(delta, nonThreadMin);
			System.out.println("HashMap, Non Threading, " + repetitions + " Repetitions -> Took: " + delta + "ms");

			start = System.currentTimeMillis();
			for(int i = 0; i < repetitions; i++) Pool.returnObject(StringBuilder.class, Pool.tryBorrow(Pool.getPool(StringBuilder.class)));
			delta = System.currentTimeMillis() - start; nonThreadMax = Math.max(delta, nonThreadMax); nonThreadMin = Math.min(delta, nonThreadMin);
			System.out.println("StringBuilder, Non Threading, " + repetitions + " Repetitions -> Took: " + delta + "ms");

			System.out.println("------------------------------------\n");
		}
		System.out.println("DONE! (Non Threading)");
		System.out.println("Max -> " + nonThreadMax + "ms");
		System.out.println("Min -> " + nonThreadMin + "ms");
		// Not bad (100 sessions, 50000 repetitions)
		// Max -> 1609ms
		// Min -> 15ms
	}

	public static class LoggerTestWrapper {
		public static Logger start(String... args) {
//			Logger logger = new Logger("[Dis Is Pre -fix]", "[Dis is Saf -fix]");
			Logger logger = new Logger();
			logger.addHandler(new AnsiLogHandler(), AnsiLogHandler.DEFAULT_PRIORITY);
			logger.addHandler(new TimeLogHandler(), TimeLogHandler.DEFAULT_PRIORITY);
			logger.addHandler(new SeverityLogHandler(), SeverityLogHandler.DEFAULT_PRIORITY);
			logger.addListener(new SystemOutListener());

			logger.log("HELLO WORLD!");
			HandlerThread handlerThread = new HandlerThread("System In");
			handlerThread.start(); handlerThread.getThreadHandler().postThrowable(() -> {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String line;
				while((line = reader.readLine()) != null) {
					if(line.equalsIgnoreCase("stop")) break;
					LogLevel level = LogLevel.LOG;
					if(line.toLowerCase().startsWith("info")) { level = LogLevel.INFO; line = line.substring(4); }
					else if(line.toLowerCase().startsWith("debug")) { level = LogLevel.DEBUG; line = line.substring(5); }
					else if(line.toLowerCase().startsWith("warn")) { level = LogLevel.WARN; line = line.substring(4); }
					else if(line.toLowerCase().startsWith("error")) { level = LogLevel.ERROR; line = line.substring(5); }
					if(line.toLowerCase().startsWith("test")) { test(logger, level, line.substring(4)); continue; }
					if(line.equalsIgnoreCase("checkAliasDuplicate")) line = testDefAliasCharsDuplicate() + "";
					logger.doLog(level, line);
				} reader.close();
			});
			return logger;
		}
		public static void test(Logger logger, LogLevel level, String testText) {
			for(AnsiLogHandler.AnsiColor background : AnsiLogHandler.AnsiColor.values()) {
				String backgroundCommand = background.asBackground().asCommand().toString();
				for(AnsiLogHandler.AnsiColor foreground : AnsiLogHandler.AnsiColor.values()) {
					String foregroundCommand = foreground.asForeground().asCommand().toString();
					logger.doLog(level, backgroundCommand + foregroundCommand + testText);
				}
				for(AnsiLogHandler.AnsiColor foreground : AnsiLogHandler.AnsiColor.values()) {
					if(foreground == AnsiLogHandler.AnsiColor.DEFAULT) continue;
					String foregroundCommand = foreground.asForegroundBrighter().asCommand().toString();
					logger.doLog(level, backgroundCommand + foregroundCommand + testText);
				}

				if(background == AnsiLogHandler.AnsiColor.DEFAULT) continue;
				backgroundCommand = background.asBackgroundBrighter().asCommand().toString();
				for(AnsiLogHandler.AnsiColor foreground : AnsiLogHandler.AnsiColor.values()) {
					String foregroundCommand = foreground.asForeground().asCommand().toString();
					logger.doLog(level, backgroundCommand + foregroundCommand + testText);
				}
				for(AnsiLogHandler.AnsiColor foreground : AnsiLogHandler.AnsiColor.values()) {
					if(foreground == AnsiLogHandler.AnsiColor.DEFAULT) continue;
					String foregroundCommand = foreground.asForegroundBrighter().asCommand().toString();
					logger.doLog(level, backgroundCommand + foregroundCommand + testText);
				}
			}
		}
		public static boolean testDefAliasCharsDuplicate() {
			ArrayList<Integer> list = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			try { for(String aliasChar : AnsiLogHandler.defaultAliasChars) {
				if(list.contains(aliasChar.hashCode())) return true;
				list.add(aliasChar.hashCode());
			} return false; } finally { Pool.returnObject(ArrayList.class, list); }
		}
	}

	public static void PooledObjectMonitorTest() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new JFrame();

		PooledObjectMonitor.getMainPooledObjectMonitor().setSize(DimensionUtils.getMaxDimension());
		PooledObjectMonitor.getMainPooledObjectMonitor().setPreferredSize(DimensionUtils.getMaxDimension());
		Component[] components = PooledObjectMonitor.getMainPooledObjectMonitor().getComponents();
		for(Component component : components) {
			component.setSize(DimensionUtils.getMaxDimension());
			component.setPreferredSize(DimensionUtils.getMaxDimension());
		} frame.getContentPane().add(PooledObjectMonitor.getMainPooledObjectMonitor());

		Handler handler = Handler.getMain();
		handler.post(() -> frame.setVisible(true));
		PooledObjectMonitor.setUpdateHandler(handler);
	}

	public static void AnsiSupportedTextAreaTest(Logger logger) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new JFrame();

		AnsiSupportedTextArea ansiSupportedTextArea = new AnsiSupportedTextArea();
		ModernScrollPane scrollPane = new ModernScrollPane(ansiSupportedTextArea);
		scrollPane.setSize(DimensionUtils.getMaxDimension());
		scrollPane.setPreferredSize(DimensionUtils.getMaxDimension());
		frame.getContentPane().add(scrollPane);
		Handler.getMain().post(() -> frame.setVisible(true));
		logger.addListener(record -> ansiSupportedTextArea.append(record.asString()));
	}
}
