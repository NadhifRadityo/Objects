package io.github.NadhifRadityo.Library;

import kotlin.jvm.functions.Function4;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class CURL {
	protected String url;
	protected RequestMethod requestMethod;
	protected final HashMap<String, String> headers;
	protected final HashMap<String, String> queries;
	protected final HashMap<String, String> posts;
	protected Function4<URLConnection, URL, Boolean, CURL, Void> customHandler;
	protected Function4<URLConnection, URL, Boolean, CURL, Object> onConnect;

	public CURL() {
		this.headers = new HashMap<>();
		this.queries = new HashMap<>();
		this.posts = new HashMap<>();
	}

	public void setUrl(String url) { this.url = url; }
	public void setRequestMethod(RequestMethod requestMethod) { this.requestMethod = requestMethod; }
	public void setCustomHandler(Function4<URLConnection, URL, Boolean, CURL, Void> customHandler) { this.customHandler = customHandler; }
	public void setOnConnect(Function4<URLConnection, URL, Boolean, CURL, Object> onConnect) { this.onConnect = onConnect; }
	public void addHeader(String key, String value) { headers.put(key, value); }
	public void addQuery(String key, String value) { queries.put(key, value); }
	public void addPost(String key, String value) { posts.put(key, value); }
	public void removeHeader(String key) { headers.remove(key); }
	public void removeQuery(String key) { queries.remove(key); }
	public void removePost(String key) { posts.remove(key); }

	public String getUrl() { return url; }
	public RequestMethod getRequestMethod() { return requestMethod; }
	public Function4<URLConnection, URL, Boolean, CURL, Void> getCustomHandler() { return customHandler; }
	public Function4<URLConnection, URL, Boolean, CURL, Object> getOnConnect() { return onConnect; }
	public Map<String, String> getHeaders() { return headers; }
	public Map<String, String> getQueries() { return queries; }
	public Map<String, String> getPosts() { return posts; }
	public String getHeader(String key) { return headers.get(key); }
	public String getQuery(String key) { return queries.get(key); }
	public String getPost(String key) { return posts.get(key); }

	public Object build(Proxy proxy, Charset charset) throws IOException {
		String formattedUrl = url + (queries.size() > 0 ? "?" : "");
		StringJoiner queryJoiner = new StringJoiner("&");
		for(Map.Entry<String, String> entry : queries.entrySet())
			queryJoiner.add(URLEncoder.encode(entry.getKey(), charset.name()) + "=" +
					URLEncoder.encode(entry.getValue(), charset.name()));
		formattedUrl += queryJoiner.toString();

		URL urlObject = new URL(formattedUrl);
		URLConnection urlConnection = proxy != null ? urlObject.openConnection(proxy) : urlObject.openConnection();
		boolean isHttps = urlConnection instanceof HttpsURLConnection;

		for(Map.Entry<String, String> entry : headers.entrySet())
			urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
		if(requestMethod == RequestMethod.POST && !headers.containsKey("Content-Type")) // Special case
			urlConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		if(isHttps) ((HttpsURLConnection) urlConnection).setRequestMethod(requestMethod.name());
		else ((HttpURLConnection) urlConnection).setRequestMethod(requestMethod.name());

		byte[] postByte = null;
		if(requestMethod == RequestMethod.POST && posts.size() > 0) {
			StringJoiner postJoiner = new StringJoiner("&");
			for(Map.Entry<String, String> entry : posts.entrySet())
				postJoiner.add(URLEncoder.encode(entry.getKey(), charset.name()) + "=" +
						URLEncoder.encode(entry.getValue(), charset.name()));
			postByte = postJoiner.toString().getBytes(charset);
		}

		if(postByte != null) {
			if(isHttps) ((HttpsURLConnection) urlConnection).setFixedLengthStreamingMode(postByte.length);
			else ((HttpURLConnection) urlConnection).setFixedLengthStreamingMode(postByte.length); }
		if(customHandler != null)
			try {
				customHandler.invoke(urlConnection, urlObject, isHttps, this);
			} catch(Exception e) {
				if(e instanceof IOException) throw (IOException) e;
				throw new IOException(e);
			}
		urlConnection.connect();
		if(postByte != null) try(OutputStream outputStream = urlConnection.getOutputStream()) { outputStream.write(postByte); }
		if(onConnect == null) return urlConnection;
		try {
			return onConnect.invoke(urlConnection, urlObject, isHttps, this);
		} catch(Exception e) {
			if(e instanceof IOException) throw (IOException) e;
			throw new IOException(e);
		} finally {
			if(isHttps) ((HttpsURLConnection) urlConnection).disconnect();
			else ((HttpURLConnection) urlConnection).disconnect();
		}
	}
	public Object build(Charset charset) throws IOException { return build(null, charset); }
	@Deprecated public Object build(Proxy proxy) throws IOException { return build(proxy, Charset.defaultCharset()); }
	@Deprecated public Object build() throws IOException { return build((Proxy) null); }
	
	public enum RequestMethod { GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE }
}
