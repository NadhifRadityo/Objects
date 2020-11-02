package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL;

import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;
import io.github.NadhifRadityo.Objects.Object.Comparator;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting.Sorting;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.FileUtils;
import io.github.NadhifRadityo.Objects.Utilizations.Library.LibraryUtils;
import io.github.NadhifRadityo.Objects.Utilizations.Library.MCPPLibrary;
import io.github.NadhifRadityo.Objects.Utilizations.StreamUtils;
import io.github.NadhifRadityo.Objects.Utilizations.StringUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Shader<ID extends Number> extends OpenGLNativeHolder<ID> {
	public static final String TYPE = "SHADER";
	protected final String[] code;
	protected final ShaderType type;
	protected final StringBuilder messages;

	public Shader(GLContext<ID> gl, String[] code, ShaderType type, File workDir) {
		super(gl);
		this.messages = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
		this.code = type.getParsedShader(code, messages, workDir, false);
		this.type = type;
	}
	public Shader(GLContext<ID> gl, String[] code, ShaderType type) { this(gl, code, type, null); }
	public Shader(GLContext<ID> gl, File file, Charset charset, ShaderType type, File workDir) throws IOException { this(gl, FileUtils.getFileString(file, charset).split(System.lineSeparator()), type, workDir); }
	public Shader(GLContext<ID> gl, File file, Charset charset, ShaderType type) throws IOException { this(gl, file, charset, type, file.getParentFile()); }

	public String[] getCode() { return code; }
	public String getWholeCode() { return StringUtils.merge(System.lineSeparator(), code); }
	public ShaderType getType() { return type; }
	public String getMessages() { return messages.toString(); }

	@Override public void arrange() {
		getInstance().setId(getGL().createShader(type.getGLType(getGL()), code));
		if(getGL().isShaderCompiled(getId())) return;
		String errorMessage = getGL().getShaderLogInfo(getId());
		messages.append(errorMessage).append('\n');
//		ForceExit.exit(GL43.GL_SHADER, errorMessage, 1);
//		new GLException("Compile failed! " + errorMessage).printStackTrace();
//		System.exit(getGL().GL_SHADER());
	}
	@Override public void disarrange() {
		getGL().destroyShader(getId());
		Pool.returnObject(StringBuilder.class, messages);
	}

	public enum ShaderType {
		VERTEX, TESSELLATION, EVALUATION, GEOMETRY, FRAGMENT, COMPUTE;

		public String[] getParsedShader(String[] code, StringBuilder messages, File workDir, boolean imported) {
			ExceptionHandler onError = (e) -> messages.append("[ERROR] ").append(ExceptionUtils.getStackTrace(e));
			LinkedHashMap<Long, String> temp = new LinkedHashMap<>();
			StringBuilder parsedCode = new StringBuilder();
			if(!imported) parsedCode.append("#define " + name() + "_PROGRAM").append(System.lineSeparator());
			if(!imported) parsedCode.append("#define __VERSION__(ver) [VERSION] ver").append(System.lineSeparator());
			if(!imported) parsedCode.append("#define __EXTENSION__(ext) [EXTENSION] ext").append(System.lineSeparator());
			parsedCode.append(StringUtils.join(code, System.lineSeparator())).append(System.lineSeparator());
			{
				Pattern pattern = Pattern.compile("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(parsedCode);
				while(matcher.find()) {
					int start = matcher.start(); int end = matcher.end();
					temp.put((((long) start) << 32) | (end & 0xffffffffL), "");
				}
				Sorting.INSERTION_SORT.sort(temp, (Comparator<Map.Entry<Long, String>>) (o1, o2) -> {
					int x = (int) (o1.getKey() >> 32);
					int y = (int) (o2.getKey() >> 32);
					return (x < y) ? -1 : ((x == y) ? 0 : 1);
				});
				Map.Entry<Long, String>[] entries = temp.entrySet().toArray(new Map.Entry[0]);
				for(int i = entries.length - 1; i >= 0; i--) {
					Map.Entry<Long, String> entry = entries[i];
					int start = (int) (entry.getKey() >> 32);
					int end = entry.getKey().intValue();
					parsedCode.delete(start, end);
					parsedCode.insert(start, entry.getValue());
				} temp.clear();
			}
			{
				Pattern versionPattern = Pattern.compile("([^\\S\\r\\n]|)+(#version)([^\\S\\r\\n]+)(?!$)(((?=[\"'])([\"'])((\\\\{2})*|(.*?[^\\\\](\\\\{2})*))\\6)(?=[^\\S\\r\\n])|((?=[\"'])([\"'])((\\\\{2})*|(.*?[^\\\\](\\\\{2})*))\\12)|.+$)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
				Pattern extensionPattern = Pattern.compile("([^\\S\\r\\n]|)+(#extension)([^\\S\\r\\n]+)(?!$)(((?=[\"'])([\"'])((\\\\{2})*|(.*?[^\\\\](\\\\{2})*))\\6)(?=[^\\S\\r\\n])|((?=[\"'])([\"'])((\\\\{2})*|(.*?[^\\\\](\\\\{2})*))\\12)|.+$)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
				Matcher versionMatcher = versionPattern.matcher(parsedCode);
				Matcher extensionMatcher = extensionPattern.matcher(parsedCode);
				while(true) {
					boolean versionFind = versionMatcher.find();
					boolean extensionFind = extensionMatcher.find();
					if(versionFind) {
						int start = versionMatcher.start(); int end = versionMatcher.end();
						temp.put((((long) start) << 32) | (end & 0xffffffffL), "__VERSION__(" + getEncapsulate(versionMatcher.group(4)) + ")");
					}
					if(extensionFind) {
						int start = extensionMatcher.start(); int end = extensionMatcher.end();
						temp.put((((long) start) << 32) | (end & 0xffffffffL), "__EXTENSION__(" + getEncapsulate(extensionMatcher.group(4)) + ")");
					}
					if(!versionFind && !extensionFind) break;
				}
				Sorting.INSERTION_SORT.sort(temp, (Comparator<Map.Entry<Long, String>>) (o1, o2) -> {
					int x = (int) (o1.getKey() >> 32);
					int y = (int) (o2.getKey() >> 32);
					return (x < y) ? -1 : ((x == y) ? 0 : 1);
				});
				Map.Entry<Long, String>[] entries = temp.entrySet().toArray(new Map.Entry[0]);
				for(int i = entries.length - 1; i >= 0; i--) {
					Map.Entry<Long, String> entry = entries[i];
					int start = (int) (entry.getKey() >> 32);
					int end = entry.getKey().intValue();
					parsedCode.delete(start, end);
					parsedCode.insert(start, entry.getValue());
				} temp.clear();
			}
			{
				Pattern workDirPattern = Pattern.compile("([^\\S\\r\\n]|)+(#workdir)([^\\S\\r\\n]+)(?!$)(((?=[\"'])([\"'])((\\\\{2})*|(.*?[^\\\\](\\\\{2})*))\\6)(?=[^\\S\\r\\n])|((?=[\"'])([\"'])((\\\\{2})*|(.*?[^\\\\](\\\\{2})*))\\12)|.*?(?=[^\\S\\r\\n])|.*$)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
				Pattern includePattern = Pattern.compile("([^\\S\\r\\n]|)+(#include)([^\\S\\r\\n]+)(?!$)(((?=[\"'])([\"'])((\\\\{2})*|(.*?[^\\\\](\\\\{2})*))\\6)(?=[^\\S\\r\\n])|((?=[\"'])([\"'])((\\\\{2})*|(.*?[^\\\\](\\\\{2})*))\\12)|.*?(?=[^\\S\\r\\n])|.*$)([^\\S\\r\\n]*)(((?=[\"'])([\"'])((\\\\{2})*|(.*?[^\\\\](\\\\{2})*))\\20)(?=[^\\S\\r\\n])|((?=[\"'])([\"'])((\\\\{2})*|(.*?[^\\\\](\\\\{2})*))\\26)|.*?(?=[^\\S\\r\\n])|.*$)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
				Matcher workDirMatcher = workDirPattern.matcher(parsedCode);
				Matcher includeMatcher = includePattern.matcher(parsedCode);
				while(true) {
					if(workDirMatcher.find()) {
						int start = workDirMatcher.start(); int end = workDirMatcher.end();
						temp.put((((long) start) << 32) | (end & 0xffffffffL), "");
						String dir = getEncapsulate(workDirMatcher.group(4));
						workDir = FilenameUtils.getPrefixLength(dir) == 0 ? new File(workDir, dir) : new File(dir);
					} if(!includeMatcher.find()) break;
					int start = includeMatcher.start(); int end = includeMatcher.end();
					File includeFile = workDir != null ? new File(workDir, getEncapsulate(includeMatcher.group(4))) : new File(getEncapsulate(includeMatcher.group(4)));
					ExceptionUtils.doSilentThrowsRunnable(onError, () -> temp.put((((long) start) << 32) | (end & 0xffffffffL), StringUtils.join(getParsedShader(FileUtils.getFileString(includeFile, ExceptionUtils.doSilentReferencedCallback((ReferencedCallback<Charset>) (args) -> Charset.defaultCharset(),
							(args) -> Charset.forName(getEncapsulate(includeMatcher.group(18).isEmpty() ? "UTF-8" : includeMatcher.group(18)).toUpperCase()))).split(System.lineSeparator()), messages, includeFile.getParentFile(), true), System.lineSeparator())));
				}
				Sorting.INSERTION_SORT.sort(temp, (Comparator<Map.Entry<Long, String>>) (o1, o2) -> {
					int x = (int) (o1.getKey() >> 32);
					int y = (int) (o2.getKey() >> 32);
					return (x < y) ? -1 : ((x == y) ? 0 : 1);
				});
				Map.Entry<Long, String>[] entries = temp.entrySet().toArray(new Map.Entry[0]);
				for(int i = entries.length - 1; i >= 0; i--) {
					Map.Entry<Long, String> entry = entries[i];
					int start = (int) (entry.getKey() >> 32);
					int end = entry.getKey().intValue();
					parsedCode.delete(start, end);
					parsedCode.insert(start, entry.getValue());
				} temp.clear();
			}
			ReferencedCallback<String[]> turnBackToNormal = (args) -> {
				String[] lines = ((String) args[0]).split(System.lineSeparator());
				Pattern versionPattern = Pattern.compile("(\\s|)+(\\[VERSION])(\\s+)(.+)");
				Pattern extensionPattern = Pattern.compile("(\\s|)+(\\[EXTENSION])(\\s+)(.+)");
				ArrayList<String> result = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
					for(int i = 0; i < lines.length; i++) { if(lines[i].isEmpty()) continue;
						Matcher versionMatcher = versionPattern.matcher(lines[i]);
						boolean versionFind = versionMatcher.find();
						if(versionFind) lines[i] = versionMatcher.replaceFirst("#version " + versionMatcher.group(4));
						Matcher extensionMatcher = extensionPattern.matcher(lines[i]);
						boolean extensionFind = extensionMatcher.find();
						if(extensionFind) lines[i] = extensionMatcher.replaceFirst("#extension " + extensionMatcher.group(4));
						result.add(lines[i]);
					} return result.toArray(new String[0]);
				} finally { Pool.returnObject(ArrayList.class, result); }
			};

			if(imported) return parsedCode.toString().split(System.lineSeparator());
			StringBuilder result = Pool.tryBorrow(Pool.getPool(StringBuilder.class));
			try { ExceptionUtils.doSilentThrowsRunnable(onError, () -> {
				File tempFile = File.createTempFile("shader_in", ".glsl", LibraryUtils.defExtractDirFile);
				File targetTempFile = File.createTempFile("shader_out", ".glsl", LibraryUtils.defExtractDirFile);
				try {
					try(BufferedWriter fileWriter = new BufferedWriter(new FileWriter(tempFile))) { fileWriter.append(parsedCode); }
					Runtime runtime = Runtime.getRuntime();
					Process process = runtime.exec(MCPPLibrary.getMCPPCommand("-e utf8 -z -P -W 16", tempFile.getAbsolutePath(), targetTempFile.getAbsolutePath()));
					process.waitFor();

					try {
						String errorString = StreamUtils.toString(process.getErrorStream(), Charset.defaultCharset());
						if(!errorString.isEmpty()) { messages.append("[ERROR] ").append(errorString); return; }
						result.append(FileUtils.getFileString(targetTempFile, Charset.defaultCharset()));
					} finally { process.destroy(); }
				} finally { if(!tempFile.delete() || !targetTempFile.delete()) onError.onExceptionOccurred(new IllegalStateException("Cannot delete file")); }
			}); return turnBackToNormal.get(result.toString()); } finally { Pool.returnObject(StringBuilder.class, result); }
		}
		public <ID extends Number> int getGLType(GLContext<ID> gl) {
			if(this == VERTEX) return gl.GL_VERTEX_SHADER();
			if(this == TESSELLATION) return gl.GL_TESSELLATION_SHADER();
			if(this == EVALUATION) return gl.GL_EVALUATION_SHADER();
			if(this == GEOMETRY) return gl.GL_GEOMETRY_SHADER();
			if(this == FRAGMENT) return gl.GL_FRAGMENT_SHADER();
			if(this == COMPUTE) return gl.GL_COMPUTE_SHADER();
			throw new IllegalStateException();
		}
	}

	private static final Pattern encapsulateQuote = Pattern.compile("([\"'])((\\\\{2})*|(.*?[^\\\\](\\\\{2})*))\\1");
	private static String getEncapsulate(String quoted) {
		Matcher matcher = encapsulateQuote.matcher(quoted);
		if(!matcher.find()) return quoted;
		return StringUtils.unescapeString(matcher.group(2));
	}
}
