package io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Program extends io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Program<Long> {
	public Program(CLContext cl, String[] code, String options, Device[] devices) {
		super(cl, code, options, devices);
	}
	public Program(CLContext cl, String[] code) {
		super(cl, code);
	}
	public Program(CLContext cl, File file, Charset charset, String options, Device[] devices) throws IOException {
		super(cl, file, charset, options, devices);
	}
	public Program(CLContext cl, File file, Charset charset) throws IOException {
		super(cl, file, charset);
	}
}
