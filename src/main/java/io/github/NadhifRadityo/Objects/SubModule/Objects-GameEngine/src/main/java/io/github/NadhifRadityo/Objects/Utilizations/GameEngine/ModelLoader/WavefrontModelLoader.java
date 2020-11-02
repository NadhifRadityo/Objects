package io.github.NadhifRadityo.Objects.Utilizations.GameEngine.ModelLoader;

import io.github.NadhifRadityo.Objects.Exception.ExceptionHandler;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Model;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class WavefrontModelLoader {

	public static Model load(float[] vertexes, float[] textureCoordinates, float[] normals, int[] vertexIndices, int[] textureCoordinateIndices, int[] normalIndices, ObjRunner runner) {
		if(vertexIndices.length != textureCoordinateIndices.length || vertexIndices.length != normalIndices.length) throw new IllegalArgumentException();
		for(int i = 0; i < vertexes.length; i += 3) runner.onVertex(vertexes[i], vertexes[i + 1], vertexes[i + 2]);
		for(int i = 0; i < textureCoordinates.length; i += 2) runner.onTextureCoordinate(textureCoordinates[i], textureCoordinates[i + 1]);
		for(int i = 0; i < normals.length; i += 3) runner.onNormal(normals[i], normals[i + 1], normals[i + 2]);
		for(int i = 0; i < vertexIndices.length; i += 3) {
			runner.onFaceBegin(3);
			runner.onFaceIndices(0, vertexIndices[i    ], textureCoordinateIndices[i    ], normalIndices[i    ]);
			runner.onFaceIndices(1, vertexIndices[i + 1], textureCoordinateIndices[i + 1], normalIndices[i + 1]);
			runner.onFaceIndices(2, vertexIndices[i + 2], textureCoordinateIndices[i + 2], normalIndices[i + 2]);
			runner.onFaceEnd();
		} return runner.flush();
	}
	public static Model loadObj(File file, ObjRunner runner) throws IOException { try(FileInputStream fileInputStream = new FileInputStream(file)) { return loadObj(fileInputStream, runner); } }
	public static Model loadObj(InputStream inputStream, ObjRunner runner) { return loadObj(new Scanner(inputStream), runner); }
	public static Model loadObj(Scanner scanner, ObjRunner runner) {
		ReferencedCallback.PVoidReferencedCallback checkArgs = (args) -> {
			if(((String[]) args[0]).length >= (int) args[1]) return;
			throw new IllegalArgumentException("Arguments not match, line: " + (int) args[2]);
		}; int lineNumber = 1;
		ReferencedCallback<float[]> argsToFloats = (args) -> ExceptionUtils.doSilentReferencedCallback((ExceptionHandler) (e) -> {
			throw new IllegalArgumentException("Invalid Argument, line: " + (int) args[1], e);
		}, (_args) -> {
			String[] data = (String[]) args[0];
			int start = args.length > 2 ? (int) args[2] : 0;
			int length = args.length > 3 ? (int) args[3] : data.length;
			float[] result = new float[length];
			for(int i = start; i < result.length; i++)
				result[i] = Float.parseFloat(data[i]);
			return result;
		});
		ReferencedCallback<int[]> argsToInts = (args) -> ExceptionUtils.doSilentReferencedCallback((ExceptionHandler) (e) -> {
			throw new IllegalArgumentException("Invalid Argument, line: " + (int) args[1], e);
		}, (_args) -> {
			String[] data = (String[]) args[0];
			int start = args.length > 2 ? (int) args[2] : 0;
			int length = args.length > 3 ? (int) args[3] : data.length;
			int[] result = new int[length];
			for(int i = start; i < result.length; i++)
				result[i] = Integer.parseInt(data[i]);
			return result;
		});
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] args = line.split("\\s");
			String command = args[0].toLowerCase();
			args = Arrays.copyOfRange(args, 1, args.length);
			switch(command) {
				case "#": runner.onComment(String.join(" ", args)); break;
				case "v": { checkArgs.get(args, 3, lineNumber); float[] data = argsToFloats.get(args, lineNumber); runner.onVertex(data[0], data[1], data[2]); break; }
				case "vt": { checkArgs.get(args, 2, lineNumber); float[] data = argsToFloats.get(args, lineNumber); runner.onTextureCoordinate(data[0], data[1]); break; }
				case "vn": { checkArgs.get(args, 3, lineNumber); float[] data = argsToFloats.get(args, lineNumber); runner.onNormal(data[0], data[1], data[2]); break; }
				case "g": checkArgs.get(args, 1, lineNumber); runner.onGroup(String.join(" ", args), Model.ModelObject.TYPE_GROUP); break;
//				case "s": checkArgs.get(args, 1, lineNumber); runner.onGroup(String.join(" ", args), Model.ModelObject.TYPE_SMOOTHING); break;
				case "mg": checkArgs.get(args, 1, lineNumber); runner.onGroup(String.join(" ", args), Model.ModelObject.TYPE_MERGING); break;
				case "o": checkArgs.get(args, 1, lineNumber); runner.onGroup(String.join(" ", args), Model.ModelObject.TYPE_OBJECT); break;
				case "f": { checkArgs.get(args, 3, lineNumber);
					int _lineNumber = lineNumber;
					ReferencedCallback.PVoidReferencedCallback doFace = (_args) -> {
						String[] indicesPack = (String[]) _args;
						runner.onFaceBegin(indicesPack.length);
						for(int i = 0; i < indicesPack.length; i++) {
							String[] indices = indicesPack[i].split("/");
							checkArgs.get(indices, 1, _lineNumber);
							int[] data = argsToInts.get(indices, _lineNumber);
							int vertexIndex = data[0];
							int textureCoordinateIndex = indices.length > 1 ? data[1] : Model.ModelFaceIndex.UNDEFINED;
							int normalIndex = indices.length > 2 ? data[2] : Model.ModelFaceIndex.UNDEFINED;
							runner.onFaceIndices(i, vertexIndex, textureCoordinateIndex, normalIndex);
						} runner.onFaceEnd();
					};
					String[] indicesPack = new String[3];
					for(int i = 1; i <= args.length - 2; i++) {
						indicesPack[0] = args[0];
						indicesPack[1] = args[i];
						indicesPack[2] = args[i + 1];
						doFace.get((Object[]) indicesPack);
					} break;
				}
				case "usemtl": checkArgs.get(args, 1, lineNumber); runner.onMaterialReference(String.join(" ", args)); break;
				case "mtllib": checkArgs.get(args, 1, lineNumber); runner.onMaterialLibrary(args); break;
				// Free form
				case "p": { checkArgs.get(args, 1, lineNumber); int[] data = argsToInts.get(args, lineNumber); runner.onPoint(data); break; }
				case "l": { checkArgs.get(args, 1, lineNumber);
					runner.onLineBegin();
					for(int i = 0; i < args.length; i++) {
						String[] indices = args[i].split("/");
						checkArgs.get(indices, 1, lineNumber);
						int[] data = argsToInts.get(indices, lineNumber);
						int vertexIndex = data[0];
						int textureCoordinateIndex = indices.length > 1 ? data[1] : Model.ModelFaceIndex.UNDEFINED;
						runner.onLineIndices(i, vertexIndex, textureCoordinateIndex);
					} runner.onLineEnd(); break;
				}
				case "curv": { checkArgs.get(args, 2, lineNumber); int[] startEnd = argsToInts.get(args, lineNumber, 0, 2); float[] data = argsToFloats.get(args, lineNumber, 2); runner.onCurve(startEnd[0], startEnd[1], data); }
				case "curv2": { checkArgs.get(args, 1, lineNumber); int[] data = argsToInts.get(args, lineNumber); runner.onCurve2(data); break; }
				case "surf": { checkArgs.get(args, 4, lineNumber);
					int[] uvStartEnd = argsToInts.get(args, lineNumber, 0, 4);
					runner.onSurfaceBegin(uvStartEnd[0], uvStartEnd[1], uvStartEnd[2], uvStartEnd[3]);
					for(int i = 4; i < args.length; i++) {
						String[] indices = args[i].split("/");
						checkArgs.get(indices, 1, lineNumber);
						int[] data = argsToInts.get(indices, lineNumber);
						int vertexIndex = data[0];
						int textureCoordinateIndex = indices.length > 1 ? data[1] : Model.ModelFaceIndex.UNDEFINED;
						int normalIndex = indices.length > 2 ? data[2] : Model.ModelFaceIndex.UNDEFINED;
						runner.onSurfaceIndices(i, vertexIndex, textureCoordinateIndex, normalIndex);
					} runner.onSurfaceEnd(); break;
				}
				case "cstype": {
					checkArgs.get(args, 1, lineNumber); int _lineNumber = lineNumber;
					ExceptionUtils.doSilentReferencedCallback((ExceptionHandler) (e) -> {
						throw new IllegalArgumentException("Invalid Argument, line: " + _lineNumber, e);
					}, args, (ReferencedCallback.PVoidReferencedCallback) (_args) -> {
						String[] __args = (String[]) _args[0];
						boolean rationalForm = false; String type = __args[0];
						if(_args.length > 1) { rationalForm = Boolean.parseBoolean(__args[0]); type = __args[1]; }
						type = type.toLowerCase(); switch(type) {
							case "bmatrix": case "bezier": case "bspline": case "cardinal": case "taylor": break;
							default: throw new IllegalArgumentException("Invalid Type, line: " + _lineNumber);
						} runner.onCSType(rationalForm, type);
					});
				}
				case "deg": { checkArgs.get(args, 2, lineNumber); float[] data = argsToFloats.get(args, lineNumber); runner.onDegree(data[0], data[1]); }
				case "bmat": {
					checkArgs.get(args, 2, lineNumber);
					String target = args[0];
					float[] data = argsToFloats.get(args, lineNumber, 1);
					if(target.equals("u")) runner.onBasisMatrixU(data);
					else if(target.equals("v")) runner.onBasisMatrixV(data);
					else throw new IllegalArgumentException("Invalid Argument, line: " + lineNumber);
				}
				case "step": { checkArgs.get(args, 2, lineNumber); float[] data = argsToFloats.get(args, lineNumber); runner.onStep(data[0], data[1]); }
			} lineNumber++;
		} return runner.flush();
	}

	public interface ObjRunner {
		Model flush();
		void onComment(String comment);
		void onVertex(float x, float y, float z);
		void onTextureCoordinate(float x, float y);
		void onNormal(float x, float y, float z);
		void onGroup(String name, int type);
		void onFaceBegin(int size);
		void onFaceIndices(int i, int vertexIndex, int textureCoordinateIndex, int normalIndex);
		void onFaceEnd();
		void onMaterialReference(String name);
		void onMaterialLibrary(String[] name);

		void onPoint(int... indices);
		void onLineBegin();
		void onLineIndices(int i, int vertexIndex, int textureCoordinateIndex);
		void onLineEnd();
		void onCurve(int start, int end, float... controlPoints);
		void onCurve2(int... indices);
		void onSurfaceBegin(int uStart, int uEnd, int vStart, int vEnd);
		void onSurfaceIndices(int i, int vertexIndex, int textureCoordinateIndex, int normalIndex);
		void onSurfaceEnd();
		void onCSType(boolean rationalForm, String type);
		void onDegree(float uDeg, float vDeg);
		void onBasisMatrixU(float... matrix);
		void onBasisMatrixV(float... matrix);
		void onStep(float uStep, float vStep);
	}

	public static class ObjRunnerImplement implements ObjRunner {
		private Model model;
		private Model.ModelObject currentGroup;
		private Model.ModelMesh currentMesh;
		private Model.ModelFace currentFace;

		public ObjRunnerImplement() { reset(); }
		private void reset() {
			this.model = new Model();
			this.currentGroup = new Model.ModelObject("Default", Model.ModelObject.TYPE_OBJECT);
			this.currentMesh = new Model.ModelMesh("Default");
			model.getObjects().add(currentGroup);
			currentGroup.getMeshes().add(currentMesh);
		}

		@Override public Model flush() { try { return model; } finally { reset(); } }
		@Override public void onComment(String comment) { }
		@Override public void onVertex(float x, float y, float z) { Vec3 vertex = new Vec3(x, y, z); model.getVertexes().add(vertex); }
		@Override public void onTextureCoordinate(float x, float y) { model.getTextureCoordinates().add(new Vec2(x, y)); }
		@Override public void onNormal(float x, float y, float z) { model.getNormals().add(new Vec3(x, y, z)); }
		@Override public void onGroup(String name, int type) { Model.ModelObject group = null; for(Model.ModelObject object : model.getObjects()) if(object.getName().equals(name) && object.getType() == type) { group = object; break; } if(group == null) { group = new Model.ModelObject(name, type); model.getObjects().add(group); } currentGroup = group; onMaterialReference("Default"); }
		@Override public void onFaceBegin(int size) { currentFace = new Model.ModelFace(size); currentMesh.getFaces().add(currentFace); }
		@Override public void onFaceIndices(int i, int vertexIndex, int textureCoordinateIndex, int normalIndex) {
			if(vertexIndex != Model.ModelFaceIndex.UNDEFINED) { if(vertexIndex < 0) vertexIndex = model.getVertexes().size() + vertexIndex + 1; vertexIndex--; }
			if(textureCoordinateIndex != Model.ModelFaceIndex.UNDEFINED) { if(textureCoordinateIndex < 0) textureCoordinateIndex = model.getTextureCoordinates().size() + textureCoordinateIndex + 1; textureCoordinateIndex--; }
			if(normalIndex != Model.ModelFaceIndex.UNDEFINED) { if(normalIndex < 0) normalIndex = model.getNormals().size() + normalIndex + 1; normalIndex--; }
			currentFace.getIndices()[i] = new Model.ModelFaceIndex(vertexIndex, textureCoordinateIndex, normalIndex);
		}
		@Override public void onFaceEnd() { currentFace = null; }
		@Override public void onMaterialReference(String name) { currentMesh = new Model.ModelMesh(name); currentGroup.getMeshes().add(currentMesh); }

		@Override public void onMaterialLibrary(String[] name) { }
		@Override public void onPoint(int... indices) { }
		@Override public void onLineBegin() { }
		@Override public void onLineIndices(int i, int vertexIndex, int textureCoordinateIndex) { }
		@Override public void onLineEnd() { }
		@Override public void onCurve(int start, int end, float... controlPoints) { }
		@Override public void onCurve2(int... indices) { }
		@Override public void onSurfaceBegin(int uStart, int uEnd, int vStart, int vEnd) { }
		@Override public void onSurfaceIndices(int i, int vertexIndex, int textureCoordinateIndex, int normalIndex) { }
		@Override public void onSurfaceEnd() { }
		@Override public void onCSType(boolean rationalForm, String type) { }
		@Override public void onDegree(float uDeg, float vDeg) { }
		@Override public void onBasisMatrixU(float... matrix) { }
		@Override public void onBasisMatrixV(float... matrix) { }
		@Override public void onStep(float uStep, float vStep) { }
	}
}
