package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.OpenGLNativeHolder;

public interface CLGLObject {
	OpenGLNativeHolder<? extends Number> getGLObject();
}
