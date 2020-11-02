package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Shader;
import io.github.NadhifRadityo.Objects.Utilizations.FileUtils;
import io.github.NadhifRadityo.Objects.Utilizations.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

public class ShaderCompilerUtils {
	public static void main(String[] args) throws IOException {
		String target = "C:\\Users\\Nadhif Radityo\\Downloads\\mblur\\bin\\shaders";
		File targetDir = new File(target);
		StringBuilder message = new StringBuilder();
		for(File file : Objects.requireNonNull(targetDir.listFiles())) {
			Shader.ShaderType shader = null;
			if(file.getName().endsWith("vs.glsl")) shader = Shader.ShaderType.VERTEX;
			if(file.getName().endsWith("fs.glsl")) shader = Shader.ShaderType.FRAGMENT;
			if(shader == null) continue;
			String[] parsed = shader.getParsedShader(FileUtils.getFileString(file, Charset.defaultCharset()).split(System.lineSeparator()), message, targetDir, false);
			if(!message.toString().isEmpty()) { System.err.println(message.toString()); message.setLength(0); continue; }
			try(FileWriter fileWriter = new FileWriter(file)) { fileWriter.write(StringUtils.join(parsed, System.lineSeparator())); }
		}
	}
}
