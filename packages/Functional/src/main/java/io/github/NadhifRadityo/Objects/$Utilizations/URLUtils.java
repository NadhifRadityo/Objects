package io.github.NadhifRadityo.Objects.$Utilizations;

import io.github.NadhifRadityo.Objects.$Interface.State.DeadableObject;
import io.github.NadhifRadityo.Objects.$Interface.Functional.ThrowsReferencedCallback;
import io.github.NadhifRadityo.Objects.$Object.Pool.Pool;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class URLUtils {

	public static String getParamValue(String link, String paramName) throws URISyntaxException {
        List<NameValuePair> queryParams = new URIBuilder(link).getQueryParams();
        return queryParams.stream()
                .filter(param -> param.getName().equalsIgnoreCase(paramName))
                .map(NameValuePair::getValue)
                .findFirst()
                .orElse("");
    }

	public static Map<String, List<String>> getQueryParams(String url, Charset charset) throws UnsupportedEncodingException {
		Map<String, List<String>> params = new LinkedHashMap<>();
		String[] urlParts = url.split("\\?");
		if(urlParts.length == 0) return params;
		String query = urlParts[1];
		for(String param : query.split("&")) {
			String[] pair = param.split("=");
			String key = URLDecoder.decode(pair[0], charset.name());
			String value = pair.length > 1 ? URLDecoder.decode(pair[1], charset.name()) : "";
			List<String> values = params.computeIfAbsent(key, k -> new ArrayList<>()); values.add(value);
		} return params;
	}
	@Deprecated public static Map<String, List<String>> getQueryParams(String url) throws UnsupportedEncodingException { return getQueryParams(url, Charset.defaultCharset()); }

	public static String getSourceCode(String url, Charset charset) throws IOException {
		if(!isValidURL(url)) throw new IllegalArgumentException("Not an URL!");
		CURL curl = new CURL();
		curl.setUrl(url);
		curl.setRequestMethod(RequestMethod.GET);
		curl.addHeader("Connection", "keep-alive");
		curl.addHeader("Cache-Control", "max-age=0");
		curl.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		curl.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
		curl.addHeader("Accept-Language", "en-US,en;q=0.8");
		curl.setCustomHandler((ThrowsReferencedCallback.PThrowsVoidReferencedCallback) (args) -> {
			URLConnection urlConnection = (URLConnection) args[0];
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(true);
			if((boolean) args[2]) ((HttpsURLConnection) urlConnection).setInstanceFollowRedirects(false);
			else ((HttpURLConnection) urlConnection).setInstanceFollowRedirects(false);
		});
		curl.setOnConnect((args) -> StreamUtils.toString(((URLConnection) args[0]).getInputStream(), charset));
		return (String) curl.build(charset);
	}
	@Deprecated public static String getSourceCode(String url) throws IOException { return getSourceCode(url, Charset.defaultCharset()); }

	public static boolean isValidURL(String urlString) {
		try { URL url = new URL(urlString); url.toURI(); return true;
		} catch (Exception ignored) { return false; }
	}

	public enum RequestMethod { GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE }
	public static class CURL implements DeadableObject {
		protected String url;
		protected RequestMethod requestMethod;
		protected final HashMap<String, String> headers;
		protected final HashMap<String, String> queries;
		protected final HashMap<String, String> posts;
		protected ThrowsReferencedCallback.ThrowsVoidReferencedCallback customHandler;
		protected ThrowsReferencedCallback.ThrowsObjectReferencedCallback onConnect;
		private boolean isDead = false;

		public CURL() {
			this.headers = Pool.tryBorrow(Pool.getPool(HashMap.class));
			this.queries = Pool.tryBorrow(Pool.getPool(HashMap.class));
			this.posts = Pool.tryBorrow(Pool.getPool(HashMap.class));
		}

		public void setUrl(String url) { this.url = url; }
		public void setRequestMethod(RequestMethod requestMethod) { this.requestMethod = requestMethod; }
		public void setCustomHandler(ThrowsReferencedCallback.ThrowsVoidReferencedCallback customHandler) { this.customHandler = customHandler; }
		public void setOnConnect(ThrowsReferencedCallback.ThrowsObjectReferencedCallback onConnect) { this.onConnect = onConnect; }
		public void addHeader(String key, String value) { assertNotDead(); headers.put(key, value); }
		public void addQuery(String key, String value) { assertNotDead(); queries.put(key, value); }
		public void addPost(String key, String value) { assertNotDead(); posts.put(key, value); }
		public void removeHeader(String key, String value) { assertNotDead(); headers.remove(key, value); }
		public void removeQuery(String key, String value) { assertNotDead(); queries.remove(key, value); }
		public void removePost(String key, String value) { assertNotDead(); posts.remove(key, value); }

		public String getUrl() { return url; }
		public RequestMethod getRequestMethod() { return requestMethod; }
		public ThrowsReferencedCallback.ThrowsVoidReferencedCallback getCustomHandler() { return customHandler; }
		public ThrowsReferencedCallback.ThrowsObjectReferencedCallback getOnConnect() { return onConnect; }
		public Map<String, String> getHeaders() { assertNotDead(); return headers; }
		public Map<String, String> getQueries() { assertNotDead(); return queries; }
		public Map<String, String> getPosts() { assertNotDead(); return posts; }
		public String getHeader(String key) { assertNotDead(); return headers.get(key); }
		public String getQuery(String key) { assertNotDead(); return queries.get(key); }
		public String getPost(String key) { assertNotDead(); return posts.get(key); }

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

			if(isHttps) ((HttpsURLConnection) urlConnection).setRequestMethod(StringUtils.capitaliseEachWords(requestMethod.name()));
			else ((HttpURLConnection) urlConnection).setRequestMethod(StringUtils.capitaliseEachWords(requestMethod.name()));

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
					customHandler.get(urlConnection, urlObject, isHttps, this);
				} catch(Exception e) {
					if(e instanceof IOException) throw (IOException) e;
					throw new IOException(e);
				}
			urlConnection.connect();
			if(postByte != null) try(OutputStream outputStream = urlConnection.getOutputStream()) { outputStream.write(postByte); }
			if(onConnect == null) return urlConnection;
			try {
				return onConnect.get(urlConnection, urlObject, isHttps, this);
			} catch(Exception e) {
				if(e instanceof IOException) throw (IOException) e;
				throw new IOException(e);
			} finally {
				if(isHttps) ((HttpsURLConnection) urlConnection).disconnect();
				else ((HttpURLConnection) urlConnection).disconnect(); setDead();
			}
		}
		public Object build(Charset charset) throws IOException { return build(null, charset); }
		@Deprecated public Object build(Proxy proxy) throws IOException { return build(proxy, Charset.defaultCharset()); }
		@Deprecated public Object build() throws IOException { return build((Proxy) null); }

		@Override public void setDead() { isDead = true;
			Pool.returnObject(HashMap.class, headers);
			Pool.returnObject(HashMap.class, queries);
			Pool.returnObject(HashMap.class, posts);
		} @Override public boolean isDead() { return isDead; }
	}
}
