package io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL;

public class VertexBufferObject extends io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.VertexBufferObject<Integer> {
	public VertexBufferObject(VertexArrayObject parent, int target) {
		super(parent, target);
	}
	public VertexBufferObject(GLContext gl, int target) {
		super(gl, target);
	}
}
