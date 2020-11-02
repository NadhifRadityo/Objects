package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Program;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;

@SuppressWarnings("rawtypes")
public class EntityVariablesPack implements ObjectsProgram.GlobalVarGLSLInfoPack {
	public static final int VAR_ID_position = 0;
	public static final int VAR_ID_textureCoordinates = 1;
	public static final int VAR_ID_normals = 2;
	public static final int VAR_ID_transformationMatrix = 3;
	public static final int VAR_ID_projectionMatrix = 4;
	public static final int VAR_ID_viewMatrix = 5;
	public static final ReferencedCallback<EntityVariablesPack> create = (args) -> new EntityVariablesPack();

	protected Program.GlobalVarGLSLInfo VAR_position;
	protected Program.GlobalVarGLSLInfo VAR_textureCoordinates;
	protected Program.GlobalVarGLSLInfo VAR_normals;
	protected Program.GlobalVarGLSLInfo VAR_transformationMatrix;
	protected Program.GlobalVarGLSLInfo VAR_projectionMatrix;
	protected Program.GlobalVarGLSLInfo VAR_viewMatrix;
	protected boolean variablesIdentified;

	@Override public Program.GlobalVarGLSLInfo getVariableInformations(int id) {
		switch(id) {
			case VAR_ID_position: return VAR_position;
			case VAR_ID_textureCoordinates: return VAR_textureCoordinates;
			case VAR_ID_normals: return VAR_normals;
			case VAR_ID_transformationMatrix: return VAR_transformationMatrix;
			case VAR_ID_projectionMatrix: return VAR_projectionMatrix;
			case VAR_ID_viewMatrix: return VAR_viewMatrix;
			default: throwUnidentifiedID(); return null;
		}
	}
	@Override public <ID extends Number> void identifyVariables(Program<ID> program, String... variablesName) {
		VAR_position = program.getVariableInformations(variablesName[VAR_ID_position]);
		VAR_textureCoordinates = program.getVariableInformations(variablesName[VAR_ID_textureCoordinates]);
		VAR_normals = program.getVariableInformations(variablesName[VAR_ID_normals]);
		VAR_transformationMatrix = program.getVariableInformations(variablesName[VAR_ID_transformationMatrix]);
		VAR_projectionMatrix = program.getVariableInformations(variablesName[VAR_ID_projectionMatrix]);
		VAR_viewMatrix = program.getVariableInformations(variablesName[VAR_ID_viewMatrix]);
		variablesIdentified = true;
	}
	@Override public <ID extends Number> void identifyVariables(Program<ID> program) {
		identifyVariables(program, "position", "textureCoordinates", "normals", "transformationMatrix", "projectionMatrix", "viewMatrix");
	}
	@Override public boolean isVariablesIdentified() {
		return variablesIdentified;
	}
}
