package io.github.NadhifRadityo.Objects.Utilizations;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public final class URLUtils {
	private URLUtils() {
		
	}
	
	public static String getParamValue(String link, String paramName) throws URISyntaxException {
        List<NameValuePair> queryParams = new URIBuilder(link).getQueryParams();
        return queryParams.stream()
                .filter(param -> param.getName().equalsIgnoreCase(paramName))
                .map(NameValuePair::getValue)
                .findFirst()
                .orElse("");
    }
	
	public static LinkedHashMap<String, List<String>> getQueryParams(String url) {
        //You can change to Map or HashMap if order of parameters does not matter for you
		LinkedHashMap<String, List<String>> params = new LinkedHashMap<>();
		String[] urlParts = url.split("\\?");
		if(urlParts.length == 0) return params;
		String query = urlParts[1];
		for (String param : query.split("&")) {
			String[] pair = param.split("=");
			String key = URLDecoder.decode(pair[0], StandardCharsets.UTF_8);
			String value = pair.length > 1 ? URLDecoder.decode(pair[1], StandardCharsets.UTF_8) : "";
			List<String> values = params.computeIfAbsent(key, k -> new ArrayList<>()); values.add(value);
		} return params;
	}
	
	public static String getSourceCode(String url) throws IOException {
		URL urlObject = new URL(url);
		URLConnection urlConnection = urlObject.openConnection();
		
		urlConnection.addRequestProperty("Connection", "keep-alive");
		urlConnection.addRequestProperty("Cache-Control", "max-age=0");
		urlConnection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
		urlConnection.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(true);
		
		if(urlConnection instanceof HttpsURLConnection) {
			((HttpsURLConnection) urlConnection).setInstanceFollowRedirects(false);
			((HttpsURLConnection) urlConnection).setRequestMethod("GET");
		} else if(urlConnection instanceof HttpURLConnection) {
			((HttpURLConnection) urlConnection).setInstanceFollowRedirects(false);
			((HttpURLConnection) urlConnection).setRequestMethod("GET");
		}
		try { return StreamUtils.toString(urlConnection.getInputStream());
		} finally { 
			if(urlConnection instanceof HttpsURLConnection) ((HttpsURLConnection) urlConnection).disconnect();
			else if(urlConnection instanceof HttpURLConnection) ((HttpURLConnection) urlConnection).disconnect();
		}
	}
	
	public static boolean isValidURL(String urlString) {
		try { URL url = new URL(urlString); url.toURI(); return true;
		} catch (Exception ignored) { return false; }
	}
}
