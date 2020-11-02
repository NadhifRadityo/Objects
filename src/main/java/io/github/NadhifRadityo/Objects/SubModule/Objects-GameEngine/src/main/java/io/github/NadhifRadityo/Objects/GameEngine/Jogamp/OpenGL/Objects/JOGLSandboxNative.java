package io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.Objects;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.JarUtils;
import io.github.NadhifRadityo.Objects.Utilizations.Library.JogampLibrary;
import io.github.NadhifRadityo.Objects.Utilizations.Library.LWJGLLibrary;
import io.github.NadhifRadityo.Objects.Utilizations.Library.LibraryUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class JOGLSandboxNative implements GLEventListener {
	public Dimension windowDim;
	public JFrame mainFrame;
	public GLCanvas mainCanvas;
	public FPSAnimator fpsAnimator;

	public JOGLSandboxNative(Dimension dimension) {
		this.windowDim = dimension;
		this.mainFrame = new JFrame();
		mainFrame.setSize(dimension);
		mainFrame.setPreferredSize(dimension);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
//		capabilities.setDoubleBuffered(false);
		capabilities.setAlphaBits(8);
		this.mainCanvas = new GLCanvas(capabilities);
		mainCanvas.addGLEventListener(this);
		mainFrame.add(mainCanvas);

		this.fpsAnimator = new FPSAnimator(60, true);
		fpsAnimator.add(mainCanvas);
	}

	public static void extract() throws IOException, URISyntaxException {
		ExceptionUtils.doSilentThrowsRunnable(ExceptionUtils.silentException, () -> Class.forName(LibraryUtils.class.getCanonicalName()));
		if(!JogampLibrary.isInited() || !LWJGLLibrary.isInited()) {
			System.out.println("Extracting native libraries...");
			File currentJar = JarUtils.getCurrentJar();
			if(currentJar != null) LibraryUtils.initLibraries();
			if(!JogampLibrary.isInited() || !LWJGLLibrary.isInited()) {
				System.out.println("Libraries not found! Try from classpaths");
				System.out.println("You might running this from your IDE");
				String[] classPaths = System.getProperty("java.class.path").split(File.pathSeparator);
				for(String classPath : classPaths) {
					if(!classPath.toLowerCase().endsWith("natives.jar")) continue;
					LibraryUtils.initLibraries(new File(classPath));
				}
			} if(!JogampLibrary.isInited() || !LWJGLLibrary.isInited())
				System.err.println("Library not found!");
		}
	}
}
