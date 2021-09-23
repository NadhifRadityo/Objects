package io.github.NadhifRadityo.Library.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import static io.github.NadhifRadityo.Library.Utils.FileUtils.getFileString;
import static io.github.NadhifRadityo.Library.Utils.FileUtils.mkfile;
import static io.github.NadhifRadityo.Library.Utils.FileUtils.writeFileString;
import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.info;

public class JSONUtils {

	public static <T> T toJson(Reader reader, Class<T> clazz) { return new Gson().fromJson(reader, clazz); }
	public static <T> T toJson(InputStream stream, Class<T> clazz) { return new Gson().fromJson(new InputStreamReader(stream), clazz); }
	public static <T> T toJson(String string, Class<T> clazz) { return new Gson().fromJson(string, clazz); }
	public static <T> T toJson(File file, Class<T> clazz) throws IOException { return new Gson().fromJson(getFileString(file), clazz); }
	public static <T> String JSONToString(T object) throws IOException {
		try(StringWriter stringWriter = new StringWriter(); JsonWriter jsonWriter = new JsonWriter(stringWriter)) {
			jsonWriter.setIndent("\t");
			new GsonBuilder().disableHtmlEscaping().create()
					.toJson(object, object.getClass(), jsonWriter);
			return stringWriter.toString();
		}
	}
	public static <T> String createJSONFile(T object, File target) throws Exception {
		String stringOut = JSONToString(object);
		mkfile(target);
		writeFileString(target, stringOut, StandardCharsets.UTF_8);
		info("Configurations written to: %s", target.getPath());
		return stringOut;
	}
}
