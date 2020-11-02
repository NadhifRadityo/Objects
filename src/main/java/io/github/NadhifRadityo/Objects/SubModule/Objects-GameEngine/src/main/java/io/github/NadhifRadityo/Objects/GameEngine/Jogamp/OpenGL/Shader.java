package io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Shader extends io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Shader<Integer> {
	public Shader(GLContext context, String[] code, ShaderType type, File workDir) {
		super(context, code, type, workDir);
	}
	public Shader(GLContext context, String[] code, ShaderType type) {
		super(context, code, type);
	}
	public Shader(GLContext context, File file, Charset charset, ShaderType type, File workDir) throws IOException {
		super(context, file, charset, type, workDir);
	}
	public Shader(GLContext context, File file, Charset charset, ShaderType type) throws IOException {
		super(context, file, charset, type);
	}
}
