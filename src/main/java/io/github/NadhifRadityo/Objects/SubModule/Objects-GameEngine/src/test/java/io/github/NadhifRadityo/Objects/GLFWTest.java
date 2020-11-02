package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenGL.Objects.GLFWFrame;

public class GLFWTest {
	public static void main(String... args) {
		GLFWFrame display = new GLFWFrame(300, 300, "Test");
		System.out.println("GL Id: " + display.getGL());
	}
}
