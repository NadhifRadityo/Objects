package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.AnsiLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.AttributesHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.FormatHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.ImageLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.NewLineHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.SeverityLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInHandler.TimeLogHandler;
import io.github.NadhifRadityo.Objects.Console.BuiltInListener.SystemOutListener;
import io.github.NadhifRadityo.Objects.Console.LogLevel;
import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Thread.HandlerThread;
import io.github.NadhifRadityo.Objects.Thread.Looper;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ClassUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ConsoleUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.FontUtils;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;
import io.github.NadhifRadityo.Objects.Utilizations.SystemUtils;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTest {

	public static void main(String... args) throws Throwable {
		System.setProperty("pool", new JSONObject().put("maxWaitMillis", 100L).put("maxTotal", 10000000).toString());
		BufferUtils.pointTo(0, 0);
		Looper.prepareMainLooper();
		Looper.myLooper().setExceptionHandler(ExceptionUtils.exceptionPrintHandler);
		Logger logger = LoggerTestWrapper.start(args);

		Map<String, TesterGroup> groups = new HashMap<>();
		for(Class<? extends Tester> clazz : ClassUtils.getClasses("io.github.NadhifRadityo.Objects.TestPrograms", Tester.class)) {
			if(!clazz.getName().startsWith("io.github.NadhifRadityo.Objects.TestPrograms.InstanceOfTest")) continue;
			TestProgram testProgram = clazz.getAnnotation(TestProgram.class);
			boolean enabled = testProgram == null || testProgram.enabled();
			String group = testProgram != null ? testProgram.group() : "main";
			int priority = testProgram != null ? testProgram.priority() : 0;
			if(!enabled) continue;
			TesterGroup testerGroup = groups.computeIfAbsent(group, (g) -> new TesterGroup(g, false));
			testerGroup.addTester(clazz.newInstance(), priority);
		}

		ByteBuffer dump = BufferUtils.createByteBuffer(1024 * 1024 * 20);
		viewTempBuffer(BufferUtils.__getAddress(dump), 1024 * 5);
		for(TesterGroup group : groups.values()) group.run(logger, dump);
		File dumpFile = new File("E:\\Tio\\Eclipse\\WorkSpace\\Projects\\Objects\\src\\test\\resources\\TestDump\\" + new SimpleDateFormat("yyyy_MM_dd HH-mm-ss-SSS").format(new Date()) + ".bin");
		if(dumpFile.exists() && dumpFile.createNewFile()) throw new Error("Cannot create dump file!");
		try(FileOutputStream fileOutputStream = new FileOutputStream(dumpFile)) {
			byte[] bytes = new byte[dump.position()];
			dump.position(0);
			dump.get(bytes); fileOutputStream.write(bytes);
		}

		Looper.loop();
	}

	public static void viewTempBuffer(long address, int samples) { ExceptionUtils.doSilentThrowsRunnable(true, () -> {
		System.out.println("Connecting to memory server!");
//		Native.load("kernel32", Kernel32.class);
		JSONObject data = new JSONObject();
		data.put("pid", Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().replaceAll("(@[a-zA-Z]+)", "")));
		data.put("address", address);
		data.put("delay", 50);
		data.put("samples", samples);
		System.out.println("Data: " + data);

		Socket socket = new Socket("127.0.0.1", 8888);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		writer.write(data.toString()); writer.flush();
		socket.getOutputStream().flush(); socket.close();
	}); }

	public static class LoggerTestWrapper {
		public static Logger start(String... args) {
//			Logger logger = new Logger("[Dis Is Pre -fix]", "[Dis is Saf -fix]");
			Logger logger = new Logger();
			logger.addHandler(new AnsiLogHandler(), AnsiLogHandler.DEFAULT_PRIORITY);
			logger.addHandler(new TimeLogHandler(), TimeLogHandler.DEFAULT_PRIORITY);
			logger.addHandler(new SeverityLogHandler(), SeverityLogHandler.DEFAULT_PRIORITY);
			logger.addHandler(new NewLineHandler(), NewLineHandler.DEFAULT_PRIORITY);
			logger.addHandler(new FormatHandler(), FormatHandler.DEFAULT_PRIORITY);
			logger.addHandler(new ImageLogHandler(), ImageLogHandler.DEFAULT_PRIORITY);
			logger.addListener(new SystemOutListener(Integer.MAX_VALUE, Integer.MAX_VALUE));

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
					if(line.equalsIgnoreCase("clear")) { logger.doLog(level, FormatHandler.SUBSTRING_START, AnsiLogHandler.AnsiCommand.CSICommand.CURSOR_POSITION, AnsiLogHandler.AnsiCommand.CSICommand.ERASE_DISPLAY.asCommand().add(2), FormatHandler.SUBSTRING_END, FormatHandler.NEW_LINE_ENABLED, AttributesHandler.OFF); continue; }
					if(line.equals("dsr")) { logger.doLog(level, FormatHandler.SUBSTRING_START, AnsiLogHandler.AnsiCommand.CSICommand.DEVICE_STATUS_REPORT, FormatHandler.SUBSTRING_END); continue; }
					if(line.equals("animation")) { printProgressAnimation(logger, 10); continue; }
					if(line.equals("size")) { int[] size = new int[2]; ConsoleUtils.getConsoleSize(size); logger.doLog(level, "Methods available: ", ConsoleUtils.GET_CONSOLE_SIZE_POSSIBLE, " ", ArrayUtils.deepToString(size)); continue; }
					if(line.startsWith("image")) {
						String defaultImg = SystemUtils.IS_OS_WINDOWS ? "C:\\Users\\Nadhif Radityo\\Pictures\\Disloyal_man_with_his_euler_angles_looking_at_quaternion.jpg" :
								SystemUtils.IS_OS_UNIX ? "/mnt/c/Users/Nadhif Radityo/Pictures/Disloyal_man_with_his_euler_angles_looking_at_quaternion.jpg" : null;
						line = line.substring(5);
						Pattern pattern = Pattern.compile("[^ ]+");
						Matcher matcher = pattern.matcher(line);
						String path = matcher.find() ? matcher.group(0) : defaultImg;
						drawImage(new File(path), logger);
					}
					logger.doLog(level, line);
				} reader.close();
			});
			return logger;
		}
		public static void test(Logger logger, LogLevel level, String testText) {
			for(AnsiLogHandler.AnsiColor background : AnsiLogHandler.AnsiColor.values()) {
				AnsiLogHandler.CSISGRParameter backgroundCommand = background.asBackground();
				for(AnsiLogHandler.AnsiColor foreground : AnsiLogHandler.AnsiColor.values()) {
					AnsiLogHandler.CSISGRParameter foregroundCommand = foreground.asForeground();
					logger.doLog(level, backgroundCommand, foregroundCommand, testText);
				}
				for(AnsiLogHandler.AnsiColor foreground : AnsiLogHandler.AnsiColor.values()) {
					if(foreground == AnsiLogHandler.AnsiColor.DEFAULT) continue;
					AnsiLogHandler.CSISGRParameter foregroundCommand = foreground.asForegroundBrighter();
					logger.doLog(level, backgroundCommand, foregroundCommand, testText);
				}

				if(background == AnsiLogHandler.AnsiColor.DEFAULT) continue;
				backgroundCommand = background.asBackgroundBrighter();
				for(AnsiLogHandler.AnsiColor foreground : AnsiLogHandler.AnsiColor.values()) {
					AnsiLogHandler.CSISGRParameter foregroundCommand = foreground.asForeground();
					logger.doLog(level, backgroundCommand, foregroundCommand, testText);
				}
				for(AnsiLogHandler.AnsiColor foreground : AnsiLogHandler.AnsiColor.values()) {
					if(foreground == AnsiLogHandler.AnsiColor.DEFAULT) continue;
					AnsiLogHandler.CSISGRParameter foregroundCommand = foreground.asForegroundBrighter();
					logger.doLog(level, backgroundCommand, foregroundCommand, testText);
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

		public static void printProgressAnimation(Logger logger, int maxLoop) {
			int loop = 0;
			int increment = 1;
			long total = 235;
			long startTime = System.currentTimeMillis();
			for(int i = 1; i <= total; i += 3 * increment) { try {
				if(i < 1 || i + 3 > total) { increment *= -1; if(loop > maxLoop) break; loop++; continue; }
				Thread.sleep(50); printProgress(logger, startTime, total, i, 48);
			} catch(InterruptedException ignored) { } }
			logger.log(FormatHandler.SUBSTRING_START, FormatHandler.SUBSTRING_END);
		}
		public static void printProgress(Logger logger, long startTime, long total, long current, int width) {
			long eta = current == 0 ? 0 : (total - current) * (System.currentTimeMillis() - startTime) / current;
			String etaHms = current == 0 ? "N/A" : String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
					TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
					TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

			StringBuilder string = new StringBuilder(width);
			int percent = (int) (current * 100 / total);
			width -= 7 + 1 + 1 + 1 + Math.log10(total) * 2 + 1 + 7 + etaHms.length();
			int progressWidth = NumberUtils.map(percent, 0, 100, 0, width);
			string
					.append('\r')
					.append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
					.append(String.format(" %d%% [", percent))
					.append(String.join("", Collections.nCopies(progressWidth, "=")))
					.append('>')
					.append(String.join("", Collections.nCopies(width - progressWidth, " ")))
					.append(']')
					.append(String.join("", Collections.nCopies((int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
					.append(String.format(" %d/%d, ETA: %s", current, total, etaHms));

//			System.out.print(string);
//			logger.log(AnsiLogHandler.AnsiCommand.CSICommand.CURSOR_POSITION, string, FormatHandler.NO_NEW_LINE, "\n", AnsiLogHandler.AnsiCommand.CSICommand.CURSOR_POSITION.asCommand().add(2).add(1), string, FormatHandler.NO_NEW_LINE);
			logger.log(string, FormatHandler.NEW_LINE_ENABLED, AttributesHandler.OFF);
		}

		public static void drawImage(File file, Logger logger) throws IOException {
			BufferedImage image2 = ImageIO.read(file);
//			BufferedImage image = new BufferedImage(128, 64, BufferedImage.TYPE_INT_ARGB);
			double target = 512;
			double scale = Math.min(target / image2.getWidth(), target / image2.getHeight());

			Font font = new Font("Fira Code", Font.PLAIN, 12);
			GlyphVector vector = FontUtils.getVector(font, (Graphics2D) image2.getGraphics(), "█");
			Shape shape = vector.getGlyphOutline(0);
			Rectangle bounds = shape.getBounds();

			BufferedImage image = new BufferedImage((int) (image2.getWidth() * scale), (int) (image2.getHeight() * scale * (double) bounds.width / bounds.height), BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = image.createGraphics();
//			graphics.setColor(new Color(128, 255, 128, 255 / 5));
//			graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
			graphics.drawImage(image2, 0, 0, image.getWidth(), image.getHeight(), null);
			if(SystemUtils.IS_OS_UNIX) logger.log(FormatHandler.SUBSTRING_START, NewLineHandler.ENABLED, AttributesHandler.OFF, ImageLogHandler.FIT_IMAGE_ENABLED, AttributesHandler.ON, image, FormatHandler.SUBSTRING_END);
			else logger.log(ImageLogHandler.FIT_IMAGE_ENABLED, AttributesHandler.OFF, image);
		}
	}
}
