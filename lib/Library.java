import java.util.*;
import java.util.regex.*;
import java.util.function.*;
import java.util.concurrent.*;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import javax.tools.*;
import java.net.*;
import java.text.*;
import javax.net.ssl.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import java.lang.ref.*;
import java.nio.charset.*;

public class Library {
	public static ThreadPoolExecutor printThread = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
	public static File configFile;
	public static Document dom;
	public static Element configurations;
	public static Properties properties;

	static {
		try {
			configFile = new File(Utils.getCurrentClassFile().getParentFile(), "configurations.xml");
			if(configFile.exists()) {
				info("There is configurations file: %s", configFile.getAbsolutePath());
				dom = Utils.newXMLDocument(configFile);
				configurations = dom.getDocumentElement();
				properties = new Properties();
				properties.load(new StringReader(configurations.getElementsByTagName("properties").item(0).getFirstChild().getNodeValue()));
			}
		} catch(Exception e) {
			throw new Error(e);
		}
	}

	public static void main(String... args) throws Exception {
		String command = args[0];
		String current = args[1];
		String additional = args[2].replaceAll(";", "\n");
		String[] targetLibraries = args.length < 4 ? null : args[3].split(";");

		command = command.toLowerCase();
		File currentDir = new File(current);
		File currentFile = Utils.getCurrentClassFile();
		String currentFilePath = currentFile.getAbsolutePath();

		Properties additionalProp = new Properties();
		additionalProp.load(new StringReader(additional));

		if(targetLibraries == null) {
			List<String> librariesName = new ArrayList<>();
			for(File file : currentDir.listFiles()) {
				if(!file.isDirectory()) continue;
				librariesName.add(file.getName());
			} targetLibraries = librariesName.toArray(new String[0]);
		}
		Map<String, Class<?>> libraryClasses = new HashMap<>();
		for(String targetLibrary : targetLibraries) {
			File sourceFile = new File(currentDir, targetLibrary + File.separator + "Main.java");
			log("Checking \"%s\" library manager... %s", targetLibrary, sourceFile.exists() ? "yes" : "no");
			Class<?> loadedClass = null;
			if(sourceFile.exists()) {
				DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
				JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
				StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

				String classpaths = System.getProperty("java.class.path");
				classpaths += File.pathSeparator + currentFilePath;
				List<String> optionList = new ArrayList<>();
				optionList.add("-classpath");
				optionList.add(classpaths);

				Iterable<? extends JavaFileObject> compilationUnit = fileManager.getJavaFileObjectsFromFiles(Collections.singletonList(sourceFile));
				JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, optionList, null, compilationUnit);
				if(task.call()) {
					URLClassLoader classLoader = new URLClassLoader(new URL[] { sourceFile.getParentFile().toURI().toURL() });
					loadedClass = classLoader.loadClass(Utils.getFileName(sourceFile));
				} else {
					for(Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics())
						error("Error on line %d in %s: %s%n", diagnostic.getLineNumber(), diagnostic.getSource().toUri(), diagnostic.getMessage(Locale.ENGLISH));
					loadedClass = null;
				}
			}
			libraryClasses.put(targetLibrary, loadedClass);
		}

		switch(command) {
			case "download": {
				if(properties == null || configurations == null)
					throw new IllegalStateException("Config required");
				Element libraries = (Element) configurations.getElementsByTagName("libraries").item(0);
				NodeList librariesList = libraries.getChildNodes();
				Map<String, Element[]> dependencies = new HashMap<>();
				for(int i = 0; i < librariesList.getLength(); i++) {
					Node node = librariesList.item(i);
					if(node.getNodeType() != Node.ELEMENT_NODE) continue;
					Element library = (Element) node;
					NodeList dependencyList = library.getChildNodes();
					List<Element> dependency = new ArrayList<>();
					for(int j = 0; j < dependencyList.getLength(); j++) {
						Node node2 = dependencyList.item(j);
						if(node2.getNodeType() != Node.ELEMENT_NODE) continue;
						Element _dependencies = (Element) node2;
						dependency.add(_dependencies);
					} dependencies.put(library.getAttribute("name"), dependency.toArray(new Element[0]));
				}

				for(Map.Entry<String, Class<?>> entry : libraryClasses.entrySet()) {
					if(entry.getValue() == null) continue;
					log("Executing \"%s\" library manager... download", entry.getKey());
					entry.getValue().getMethod("download", Document.class, Element[].class).invoke(null, dom, dependencies.get(entry.getKey()));
				} break;
			}
			case "build": {
				if(properties == null || configurations == null)
					throw new IllegalStateException("Config required");
				Element libraries = (Element) configurations.getElementsByTagName("libraries").item(0);
				NodeList librariesList = libraries.getChildNodes();
				Map<String, Element[]> dependencies = new HashMap<>();
				for(int i = 0; i < librariesList.getLength(); i++) {
					Node node = librariesList.item(i);
					if(node.getNodeType() != Node.ELEMENT_NODE) continue;
					Element library = (Element) node;
					NodeList dependencyList = library.getChildNodes();
					List<Element> dependency = new ArrayList<>();
					for(int j = 0; j < dependencyList.getLength(); j++) {
						Node node2 = dependencyList.item(j);
						if(node2.getNodeType() != Node.ELEMENT_NODE) continue;
						Element _dependencies = (Element) node2;
						dependency.add(_dependencies);
					} dependencies.put(library.getAttribute("name"), dependency.toArray(new Element[0]));
				}

				for(Map.Entry<String, Class<?>> entry : libraryClasses.entrySet()) {
					if(entry.getValue() == null) continue;
					log("Executing \"%s\" library manager... build", entry.getKey());
					entry.getValue().getMethod("build", Document.class, Element[].class).invoke(null, dom, dependencies.get(entry.getKey()));
				} break;
			}
			case "clean": {
				if(properties == null || configurations == null)
					throw new IllegalStateException("Config required");
				Element libraries = (Element) configurations.getElementsByTagName("libraries").item(0);
				NodeList librariesList = libraries.getChildNodes();
				Map<String, Element[]> dependencies = new HashMap<>();
				for(int i = 0; i < librariesList.getLength(); i++) {
					Node node = librariesList.item(i);
					if(node.getNodeType() != Node.ELEMENT_NODE) continue;
					Element library = (Element) node;
					NodeList dependencyList = library.getChildNodes();
					List<Element> dependency = new ArrayList<>();
					for(int j = 0; j < dependencyList.getLength(); j++) {
						Node node2 = dependencyList.item(j);
						if(node2.getNodeType() != Node.ELEMENT_NODE) continue;
						Element _dependencies = (Element) node2;
						dependency.add(_dependencies);
					} dependencies.put(library.getAttribute("name"), dependency.toArray(new Element[0]));
				}

				for(Map.Entry<String, Class<?>> entry : libraryClasses.entrySet()) {
					if(entry.getValue() == null) continue;
					log("Executing \"%s\" library manager... clean", entry.getKey());
					entry.getValue().getMethod("clean", Document.class, Element[].class).invoke(null, dom, dependencies.get(entry.getKey()));
				} break;
			}
			case "configure": {
				Document dom = Utils.newXMLDocument(null);
				Element configurations = dom.createElement("configurations");

				Element properties = dom.createElement("properties");
				{
					Properties prop = Library.properties = new Properties();
					prop.setProperty("mavenSearch", additionalProp.getProperty("mavenSearch", "https://search.maven.org/solrsearch/select?q=g:\"%s\"+AND+a:\"%s\"&core=gav&wt=xml"));
					prop.setProperty("mavenDownload", additionalProp.getProperty("mavenDownload", "https://search.maven.org/remotecontent?filepath=%s"));

					File javacPath = additionalProp.getProperty("javacPath") != null ? new File(additionalProp.getProperty("javacPath")) : null;
					if(javacPath == null || !javacPath.exists()) javacPath = Utils.searchPath("javac");
					if(javacPath == null || !javacPath.exists()) { error("Unable to determine the location of the \"javac\" using the command line"); return; }
					prop.setProperty("javacPath", javacPath.getAbsolutePath());

					File jarPath = additionalProp.getProperty("jarPath") != null ? new File(additionalProp.getProperty("jarPath")) : null;
					if(jarPath == null || !jarPath.exists()) jarPath = Utils.searchPath("jar");
					if(jarPath == null || !jarPath.exists()) { error("Unable to determine the location of the \"jar\" using the command line"); return; }
					prop.setProperty("jarPath", jarPath.getAbsolutePath());

					StringWriter stringWriter = new StringWriter(); prop.store(stringWriter, "");
					properties.appendChild(dom.createTextNode(stringWriter.getBuffer().toString()));
				}

				Element libraries = dom.createElement("libraries");
				Element library;

				for(Map.Entry<String, Class<?>> entry : libraryClasses.entrySet()) {
					if(entry.getValue() == null) continue;
					log("Executing \"%s\" library manager... configure", entry.getKey());
					Element[] implementations = (Element[]) entry.getValue().getMethod("configure", Document.class).invoke(null, dom);

					library = dom.createElement("library");
					library.setAttribute("name", entry.getKey());
					libraries.appendChild(library);

					for(Element implementation : implementations)
						library.appendChild(implementation);
				}

				configurations.appendChild(properties);
				configurations.appendChild(libraries);
				dom.appendChild(configurations);
				String stringOut = Utils.createXMLFile(dom, configFile);
				info(stringOut.replaceAll("%", "%%"));
				break;
			}
			case "__debug__": {
				warn("which java=%s, which javac=%s", Utils.searchPath("java").exists(), Utils.searchPath("javac").exists());
			}
		}
	}

	public static class Utils {
		protected static final ThreadLocal<WeakReference<ByteArrayOutputStream>> tempBuffer = new ThreadLocal<>();

		protected static ByteArrayOutputStream getTempBuffer() {
			ByteArrayOutputStream result = tempBuffer.get() == null || tempBuffer.get().get() == null ? null : tempBuffer.get().get();
			if(result == null) { result = new ByteArrayOutputStream(); tempBuffer.set(new WeakReference<>(result)); } return result;
		}

		public static byte[] getBytes(InputStream inputStream) throws IOException {
			ByteArrayOutputStream result = getTempBuffer();
			byte[] buffer = new byte[8192]; int length; try {
			while((length = inputStream.read(buffer)) != -1)
				result.write(buffer, 0, length);
			result.close(); return result.toByteArray();
		} finally { result.reset(); } }

		public static String toString(InputStream inputStream, Charset charset) throws IOException { return new String(getBytes(inputStream), charset); }

		public static String getFileName(String fileName) {
			String name = ""; try { if(fileName != null && fileName.contains("."))
			name = fileName.substring(0, fileName.lastIndexOf(".")); } catch (Exception ignored) { } return name;
		}
		public static String getFileName(File file) { return getFileName(file != null ? file.getName() : null); }

		public static String getFileExtension(String fileName) {
			String extension = ""; try { if(fileName != null && fileName.contains("."))
			extension = fileName.substring(fileName.lastIndexOf(".") + 1); } catch (Exception ignored) { } return extension;
		}
		public static String getFileExtension(File file) { return getFileExtension(file != null ? file.getName() : null); }

		public static String getFileString(File file, Charset charset) throws IOException { try(FileInputStream fis = new FileInputStream(file)) { return toString(fis, charset); } }
		public static String getFileString(String path, Charset charset) throws IOException { return getFileString(new File(path), charset); }
		public static String getFileString(File file) throws IOException { return getFileString(file, StandardCharsets.UTF_8); }
		public static String getFileString(String path) throws IOException { return getFileString(path, StandardCharsets.UTF_8); }

		public static File getCurrentClassFile(Class clazz) throws Exception {
			File result = new File(URLDecoder.decode(clazz.getProtectionDomain().getCodeSource().getLocation().getPath(), StandardCharsets.UTF_8.name()));
			if(result.isDirectory()) result = new File(result, clazz.getSimpleName() + ".class"); return result;
		}
		public static File getCurrentClassFile() throws Exception {
			return getCurrentClassFile(Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()));
		}

		public static void copy(InputStream inputStream, OutputStream outputStream, Consumer<Long> progress) throws IOException {
			byte[] buffer = new byte[65536];
			if(progress != null) progress.accept(0L);
			long length = 0; int read;
			while((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
				outputStream.write(buffer, 0, read); length += read;
				if(progress != null) progress.accept(length);
			}
		}
		public static void writeToFile(byte[] bytes, int i, int len, File file) throws IOException {
			try(FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(bytes, i, len);
			}
		}
		public static void writeToFile(byte[] bytes, File file) throws IOException {
			writeToFile(bytes, 0, bytes.length, file);
		}

		public static void download(URL url, OutputStream outputStream) throws Exception {
			URLConnection connection = url.openConnection();
			long completeFileSize = connection instanceof HttpsURLConnection ? ((HttpsURLConnection) connection).getContentLength() : ((HttpURLConnection) connection).getContentLength();
			BufferedInputStream inputStream = new BufferedInputStream(connection instanceof HttpsURLConnection ? ((HttpsURLConnection) connection).getInputStream() : ((HttpURLConnection) connection).getInputStream());
			info("Downloading %s... (%s)", Paths.get(new URI(url.toString()).getPath()).getFileName().toString(), url.toString());
			try { copy(inputStream, outputStream, new Consumer<Long>() {
				long startTime = System.currentTimeMillis();
				long lastTime = System.currentTimeMillis();
				long lastLength = 0;
				long lastPrint = 0;
				long speed = 0;
				long[] speeds;
				int speedsIndex = 0;
				public void accept(Long length) {
					if(speeds == null) {
						speeds = new long[30];
						Arrays.fill(speeds, -1L);
					}
					float alpha = 0.9f;

					long now = System.currentTimeMillis();
					long deltaTime = Math.max(1, now - lastTime);
					long deltaLength = length - lastLength;
					speeds[speedsIndex++] = deltaLength * 1000 / deltaTime;
					if(speedsIndex >= speeds.length) speedsIndex = 0;
					lastTime = now; lastLength = length;
					if(now - lastPrint < 1000) return;

					long result = 0;
					long[] values = speeds;
					int counter = 0;
					for(long value : values) {
						if(value == -1) continue; counter++;
						result += value;
					} result /= counter;

					speed = (long) (speed * alpha) + (long) (result * (1 - alpha));
					printDownloadProgress(startTime, completeFileSize, length, speed);
					lastPrint = now;
				}
			}); } finally { inputStream.close(); }
		}

		public static byte[] createChecksum(File file, String digest) throws Exception {
			InputStream fileInputStream =  new FileInputStream(file);
			MessageDigest messageDigest = MessageDigest.getInstance(digest); try {
			byte[] buffer = new byte[8192]; int read;
			while((read = fileInputStream.read(buffer)) != -1) {
				messageDigest.update(buffer, 0, read);
			} return messageDigest.digest();
			} finally { fileInputStream.close(); }
		}
		public static String byteToString(byte[] bytes) {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < bytes.length; i++)
				builder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			return builder.toString();
		}

		public static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");
		public static String getCommandOutput(File basedir, String... arguments) throws Exception {
			ProcessBuilder processBuilder = new ProcessBuilder(arguments);
			if(basedir != null) processBuilder.directory(basedir);
			Process process = processBuilder.start();
			int returnCode = process.waitFor();
			String error = toString(process.getErrorStream(), StandardCharsets.UTF_8);
			if(!error.isEmpty()) error(error); if(returnCode != 0) return null;
			return toString(process.getInputStream(), StandardCharsets.UTF_8);
		}
		public static String getCommandOutput(String... arguments) throws Exception {
			return getCommandOutput(null, arguments);
		}
		public static File searchPath(String executable) throws Exception {
			if(IS_WINDOWS) {
				String path = getCommandOutput("where", executable).trim();
				if(path == null || path.isEmpty()) return null;
				return new File(path);
			}
			String path = getCommandOutput("which", executable).trim();
			if(path == null || path.isEmpty()) return null;
			return new File(path);
		}

		public static Document newXMLDocument(Object object) throws Exception {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			if(object instanceof InputStream) return documentBuilder.parse((InputStream) object);
			if(object instanceof File) return documentBuilder.parse((File) object);
			if(object != null) throw new IllegalArgumentException();
			return documentBuilder.newDocument();
		}
		public static String XMLToString(Object object) throws Exception {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source;
			if(object instanceof Document) {
				transformer.setOutputProperty(OutputKeys.METHOD, "xml");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2));
				source = new DOMSource(((Document) object).getDocumentElement());
			} else if(object instanceof Element) {
				transformer.setOutputProperty(OutputKeys.METHOD, "xml");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("omit-xml-declaration", "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2));
				source = new DOMSource((Element) object);
			} else throw new IllegalArgumentException();

			StringWriter stringWriter = new StringWriter();
			StreamResult streamResult = new StreamResult(stringWriter);
			transformer.transform(source, streamResult);
			return stringWriter.toString();
		}
		public static String createXMLFile(Object object, File target) throws Exception {
			String stringOut = XMLToString(object);
			if(!target.exists() && !target.createNewFile())
				throw new IllegalStateException("Cannot create file!");
			writeToFile(stringOut.getBytes(StandardCharsets.UTF_8), target);
			info("Configurations written to: %s", target.getAbsolutePath());
			return stringOut;
		}
	}
	public static class LibUtils {
		public static void downloadMaven(Element dependencyList, File currentDir) throws Exception {
			NodeList dependenciesList = dependencyList.getChildNodes();
			Element version = null;
			for(int i = 0; i < dependenciesList.getLength(); i++) {
				Node node = dependenciesList.item(i);
				if(node.getNodeType() != Node.ELEMENT_NODE) continue;
				Element dependency = (Element) node;
				if(!dependency.getNodeName().equals("version")) continue;
				if(version != null) warn("Double properties on \"version\", this will override the last one. (%s)", dependency);
				version = dependency;
			}
			if(version == null) warn("Unexpected undefined properties, version=\"%s\"", version);
			String downloadIdentifier = version == null ? "" : version.getAttribute("group").replaceAll("\\.", "/") + "/" + version.getAttribute("artifact") + "/" + version.getAttribute("version") + "/";

			int retry = 0;
			for(int i = 0; i < dependenciesList.getLength(); i++) {
				Node node = dependenciesList.item(i);
				if(node.getNodeType() != Node.ELEMENT_NODE) continue;
				Element dependency = (Element) node;
				if(!dependency.getNodeName().equals("dependency")) continue;

				if(dependency.getAttribute("action").equalsIgnoreCase("download")) {
					String downloadName = dependency.getFirstChild().getNodeValue();
					File targetedDir = dependency.getAttribute("target") != null ? new File(currentDir, dependency.getAttribute("target")) : currentDir;
					if(!targetedDir.exists() && !targetedDir.mkdirs()) throw new IllegalArgumentException("Cannot create directory");
					File filePath = new File(targetedDir, downloadName);
					ThrowsReferencedCallback<Integer> checksum = (args) -> {
						String extension = (String) args[0];
						String digest = (String) args[1];
						File checksumFile = new File(targetedDir, downloadName + extension);
						if(!checksumFile.exists()) return 1; byte[] checksumByte = null;
						try { checksumByte = Utils.createChecksum(filePath, digest);
						} catch(Exception e) { error("%s", e); return 1; }
						if(checksumByte == null) return 1; try {
						String checksumVerified = Utils.getFileString(checksumFile);
						String checksumGenerated = Utils.byteToString(checksumByte);
						return checksumVerified.equals(checksumGenerated) ? 0 : -1;
						} catch(Exception e) { error("%s", e); return 1; }
					}; // 0: Passed, -1: Not passed, 1: Error

					boolean justDownloadChecksums = false;
					if(filePath.exists()) {
						justDownloadChecksums = true;
						info("File already downloaded, checking checksums... (%s)", filePath.getAbsolutePath());
						int md5Passed = checksum.get(".md5", "MD5");
						int sha1Passed = checksum.get(".sha1", "SHA-1");
						log("MD5 passed: %s, SHA1 passed: %s", md5Passed == 0, sha1Passed == 0);
						if(md5Passed == 0 || sha1Passed == 0) { retry = 0; continue; }
						if(md5Passed == 1 && sha1Passed == 1) warn("Checksums not available! Try downloading again...");
						if(md5Passed == -1 || sha1Passed == -1) error("Checksum failed! Will retry...");
					}

					ThrowsReferencedCallback<Void> download = (args) -> {
						String extension = (String) args[0];
						String fileName = downloadName + extension;
						File fileTarget = new File(targetedDir, fileName);
						if(!fileTarget.exists() && !fileTarget.createNewFile())
							throw new IllegalArgumentException();
						try(FileOutputStream fos = new FileOutputStream(fileTarget)) {
							Utils.download(new URL(String.format(properties.getProperty("mavenDownload"), downloadIdentifier + fileName)), fos);
						} return null;
					};
					download.get(".md5"); download.get(".sha1");
					if(justDownloadChecksums) {
						info("File already downloaded, checking checksums... (%s)", filePath.getAbsolutePath());
						int md5Passed = checksum.get(".md5", "MD5");
						int sha1Passed = checksum.get(".sha1", "SHA-1");
						log("MD5 passed: %s, SHA1 passed: %s", md5Passed == 0, sha1Passed == 0);
						if(md5Passed == 0 || sha1Passed == 0) { retry = 0; continue; }
						else error("Checksum failed! Will retry...");
					} download.get("");

					int md5Passed = checksum.get(".md5", "MD5");
					int sha1Passed = checksum.get(".sha1", "SHA-1");
					log("MD5 passed: %s, SHA1 passed: %s", md5Passed == 0, sha1Passed == 0);
					if(md5Passed == 0 || sha1Passed == 0) { retry = 0; continue; }
					if(md5Passed == 1 && sha1Passed == 1) {
						warn("Checksums not available! Assuming it's good.");
						retry = 0; continue;
					}
					if(md5Passed == -1 || sha1Passed == -1) {
						if(retry < 2) { error("Checksum failed! Will retry..."); retry++; i -= 1;
						} else throw new Error("Error! cannot download file, check your internet connection.");
					}
				}
			}
		}

		public static Element[] searchMaven(String group, String artifact) throws Exception {
			URL url = new URL(String.format(properties.getProperty("mavenSearch"), group, artifact));
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			String urlString = uri.toASCIIString();
			info("Searching repository \"%s\"... (%s)", group + "." + artifact, urlString);
			Document dom = Utils.newXMLDocument(new URL(urlString).openStream());

			List<Element> versions = new LinkedList<>();
			Element response = dom.getDocumentElement();
			Element result = (Element) response.getElementsByTagName("result").item(0);
			NodeList docList = result.getChildNodes();
			for(int i = 0; i < docList.getLength(); i++) {
				debug("Checking index %s...", i);
				Node node = docList.item(i);
				if(node.getNodeType() != Node.ELEMENT_NODE) continue;
				Element elementNode = (Element) node;
				NodeList itemList = elementNode.getChildNodes();
				String g = null;
				String a = null;
				String id = null;
				String v = null;
				String ec = null;
				String timestamp = null;
				for(int j = 0; j < itemList.getLength(); j++) {
					Node node2 = itemList.item(j);
					if(node2.getNodeType() != Node.ELEMENT_NODE) continue;
					Element elementNode2 = (Element) node2;
					String name = elementNode2.getAttribute("name");
					String value = elementNode2.getFirstChild().getNodeValue();
					switch(name) {
						case "g": { if(g != null) warn("Double properties on \"g\", this will override the last one. (%s)", elementNode2); g = value; break; }
						case "a": { if(a != null) warn("Double properties on \"a\", this will override the last one. (%s)", elementNode2); a = value; break; }
						case "id": { if(id != null) warn("Double properties on \"id\", this will override the last one. (%s)", elementNode2); id = value; break; }
						case "v": { if(v != null) warn("Double properties on \"v\", this will override the last one. (%s)", elementNode2); v = value; break; }
						case "ec": {
							if(ec != null) warn("Double properties on \"ec\", this will override the last one. (%s)", elementNode2);
							NodeList ecList = elementNode2.getChildNodes();
							ec = "";
							for(int k = 0; k < ecList.getLength(); k++) {
								Node node3 = ecList.item(k);
								if(node3.getNodeType() != Node.ELEMENT_NODE) continue;
								Element elementNode3 = (Element) node3;
								ec += elementNode3.getFirstChild().getNodeValue() + ";";
							} break;
						}
						case "timestamp": { if(timestamp != null) warn("Double properties on \"timestamp\", this will override the last one. (%s)", elementNode2); timestamp = value; break; }
					}
				}
				debug("g=\"%s\" a=\"%s\" id=\"%s\" v=\"%s\" ec=\"%s\" timestamp=\"%s\"", g, a, id, v, ec, timestamp);
				if(g == null || a == null || id == null || v == null || ec == null || timestamp == null) {
					warn("Unexpected undefined properties, g=\"%s\" a=\"%s\" id=\"%s\" v=\"%s\" ec=\"%s\" timestamp=\"%s\"", g, a, id, v, ec, timestamp); continue; }
				if(!group.equals(g) || !artifact.equals(a)) continue;
				log("g=\"%s\" a=\"%s\" id=\"%s\" v=\"%s\" ec=\"%s\" timestamp=\"%s\"", g, a, id, v, ec, timestamp);

				Element version = dom.createElement("version");
				version.setAttribute("group", g);
				version.setAttribute("artifact", a);
				version.setAttribute("id", id);
				version.setAttribute("version", v);
				version.setAttribute("ec", ec);
				version.setAttribute("timestamp", timestamp);
				versions.add(version);
			}
			versions.sort(Comparator.comparingLong((ToLongFunction<Element>) o -> Long.parseLong(o.getAttribute("timestamp"))).reversed());
			return versions.toArray(new Element[0]);
		}
		public static final ReferencedCallback<Boolean> defaultMavenCallback(String nativeDir) {
			return (args) -> {
				Document dom = (Document) args[0];
				Element dependency = (Element) args[1];
				String depend = (String) args[2];
				Element version = (Element) args[3];

				dependency.setAttribute("action", "download");
				if(nativeDir != null)
					dependency.setAttribute("target", (depend.equals(".jar") || depend.equals("-sources.jar") || depend.equals("-javadoc.jar")) ? "." : nativeDir);
				if(depend.equals(".jar")) dependency.setAttribute("type", "classes");
				if(depend.equals("-javadoc.jar")) dependency.setAttribute("type", "javadoc");
				if(depend.equals("-sources.jar")) dependency.setAttribute("type", "sources");
				return true;
			};
		};
		public static Element createDefaultMaven(Document dom, Element version, ReferencedCallback<Boolean> callback) {
			version = (Element) dom.importNode(version, true);
			String fileId = version.getAttribute("artifact") + "-" + version.getAttribute("version");
			info("Selected version: %s", version.getAttribute("id"));

			Element dependencies = dom.createElement("dependencies");
			dependencies.appendChild(version);
			Element dependency;

			String[] depends = version.getAttribute("ec").split(";");
			for(String depend : depends) {
				dependency = dom.createElement("dependency");
				if(!callback.get(dom, dependency, depend, version))
					continue;
				dependency.appendChild(dom.createTextNode(fileId + depend));
				dependencies.appendChild(dependency);
			} return dependencies;
		}

		public static Element createLibrary(String name, Document doc, Element[] dependencyLists, File currentDir) throws Exception {
			List<Element> classes = new ArrayList<>();
			List<Element> javadoc = new ArrayList<>();
			List<Element> sources = new ArrayList<>();
			for(Element dependencyList : dependencyLists) {
				NodeList dependenciesList = dependencyList.getChildNodes();
				for(int i = 0; i < dependenciesList.getLength(); i++) {
					Node node = dependenciesList.item(i);
					if(node.getNodeType() != Node.ELEMENT_NODE) continue;
					Element dependency = (Element) node;
					if(!dependency.getNodeName().equals("dependency")) continue;
					List<String> type = Arrays.asList(dependency.getAttribute("type").split(File.pathSeparator));
					if(type.contains("classes")) classes.add(dependency);
					if(type.contains("javadoc")) javadoc.add(dependency);
					if(type.contains("sources")) sources.add(dependency);
				}
			}
			return createLibrary(name, doc, classes.toArray(new Element[0]), javadoc.toArray(new Element[0]), sources.toArray(new Element[0]), currentDir);
		}
		public static Element createLibrary(String name, Document doc, Element[] classes, Element[] javadoc, Element[] sources, File currentDir) throws Exception {
			List<File> classesFile = null;
			List<File> javadocFile = null;
			List<File> sourcesFile = null;
			if(classes != null && classes.length > 0) {
				classesFile = new ArrayList<>();
				for(Element libElement : classes) {
					String libTarget = libElement.getAttribute("target");
					String libName = libElement.getFirstChild().getNodeValue();
					classesFile.add(new File(currentDir, libTarget + File.separator + libName));
				}
			}
			if(javadoc != null && javadoc.length > 0) {
				javadocFile = new ArrayList<>();
				for(Element libElement : javadoc) {
					String libTarget = libElement.getAttribute("target");
					String libName = libElement.getFirstChild().getNodeValue();
					javadocFile.add(new File(currentDir, libTarget + File.separator + libName));
				}
			}
			if(sources != null && sources.length > 0) {
				sourcesFile = new ArrayList<>();
				for(Element libElement : sources) {
					String libTarget = libElement.getAttribute("target");
					String libName = libElement.getFirstChild().getNodeValue();
					sourcesFile.add(new File(currentDir, libTarget + File.separator + libName));
				}
			}
			return createLibrary(name, doc, classesFile != null ? classesFile.toArray(new File[0]) : null,
					javadocFile != null ? javadocFile.toArray(new File[0]) : null, sourcesFile != null ? sourcesFile.toArray(new File[0]) : null);
		}
		public static Element createLibrary(String name, Document doc, File[] classes, File[] javadoc, File[] sources) throws Exception {
			Element component = doc.createElement("component");
			component.setAttribute("name", "libraryTable");
			Element library = doc.createElement("library");
			library.setAttribute("name", name);
			component.appendChild(library);
			if(classes != null && classes.length > 0) {
				Element eClasses = doc.createElement("CLASSES");
				library.appendChild(eClasses);
				for(File classesFile : classes) {
					Element root = doc.createElement("root");
					root.setAttribute("url", "jar://" + classesFile.getAbsolutePath());
					eClasses.appendChild(root);
				}
			}
			if(javadoc != null && javadoc.length > 0) {
				Element eJavadoc = doc.createElement("JAVADOC");
				library.appendChild(eJavadoc);
				for(File javadocFile : javadoc) {
					Element root = doc.createElement("root");
					root.setAttribute("url", "jar://" + javadocFile.getAbsolutePath());
					eJavadoc.appendChild(root);
				}
			}
			if(sources != null && sources.length > 0) {
				Element eSources = doc.createElement("SOURCES");
				library.appendChild(eSources);
				for(File sourcesFile : sources) {
					Element root = doc.createElement("root");
					root.setAttribute("url", "jar://" + sourcesFile.getAbsolutePath());
					eSources.appendChild(root);
				}
			}
			return component;
		}
	}

	public static boolean enableDebug = false;
	public static void log(String text, Object... format) { for(String line : String.format(text, format).split("\n")) System.out.println("[LOG] " + line); }
	public static void info(String text, Object... format) { for(String line : String.format(text, format).split("\n")) System.out.println("[INFO] " + line); }
	public static void debug(String text, Object... format) { if(!enableDebug) return; for(String line : String.format(text, format).split("\n")) System.out.println("[DEBUG] " + line); }
	public static void warn(String text, Object... format) { for(String line : String.format(text, format).split("\n")) System.out.println("[WARN] " + line); }
	public static void error(String text, Object... format) { for(String line : String.format(text, format).split("\n")) System.out.println("[ERROR] " + line); }

	static StringBuilder downloadProgressCache = new StringBuilder(200);
	public static void printDownloadProgress(long startTime, long total, long current, long speed) {
		long eta = speed == 0 ? Long.MAX_VALUE : (total - current) * 1000 / speed;

		String etaHms = speed == 0 ? "N/A" :
				String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
						TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
						TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

		int percent = total == 0 ? 0 : (int) (current * 100 / total);
		downloadProgressCache
//				.append('\r')
				.append(String.join("", Collections.nCopies(2 - (percent == 0 ? 0 : (int) Math.log10(percent)), " ")))
				.append(String.format(" %d%% [", percent))
				.append(String.join("", Collections.nCopies(percent, "=")))
				.append('>')
				.append(String.join("", Collections.nCopies(100 - percent, " ")))
				.append(']')
				.append(String.join("", Collections.nCopies((int) Math.log10(total) - (current == 0 ? 0 : (int) Math.log10(current)), " ")))
				.append(String.format(" %d/%d, ETA: %s, Speed: %s/s", current, total, etaHms, humanReadableByteCount(speed)));

		System.out.println("[INFO] " + downloadProgressCache);
		downloadProgressCache.setLength(0);
	}

	// https://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
	static CharacterIterator readableByteCount = new StringCharacterIterator("kMGTPE");
	public static String humanReadableByteCount(long bytes) {
		if(-1000 < bytes && bytes < 1000) return bytes + " B"; readableByteCount.setIndex(0);
		while(bytes <= -999_950 || bytes >= 999_950) { bytes /= 1000; readableByteCount.next(); }
		return String.format("%.1f %cB", bytes / 1000.0, readableByteCount.current());
	}

	public interface ReferencedCallback<T> {
		T get(Object... obj);
	}
	public interface ThrowsReferencedCallback<T> {
		T get(Object... obj) throws Exception;
	}
}