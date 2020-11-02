package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVec1;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.VertexArrayObject;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.VertexBufferObject;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting.Sorting;
import io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ListUtils;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

import java.util.ArrayList;
import java.util.List;

public class Model {
	protected final List<Vec3> vertexes;
	protected final List<Vec2> textureCoordinates;
	protected final List<Vec3> normals;
	protected final List<ModelObject> objects;
	protected final List<String> materials;
//	protected boolean enableSmoothShading;

	protected Model(boolean notNull) {
		this.vertexes = notNull ? new ArrayList<>() : null;
		this.textureCoordinates = notNull ? new ArrayList<>() : null;
		this.normals = notNull ? new ArrayList<>() : null;
		this.objects = notNull ? new ArrayList<>() : null;
		this.materials = notNull ? new ArrayList<>() : null;
	} public Model() { this(true); }

	public List<Vec3> getVertexes() { return vertexes; }
	public List<Vec2> getTextureCoordinates() { return textureCoordinates; }
	public List<Vec3> getNormals() { return normals; }
	public List<ModelObject> getObjects() { return objects; }
	public List<String> getMaterials() { return materials; }
	public LoadedModel loadToGL(GLContext<? extends Number> gl, String object, int mesh) { LoadedModel result = new LoadedModel(gl, this, object, mesh); result.create(); return result; }
	public LoadedModel loadToGL(GLContext<? extends Number> gl) { return loadToGL(gl, "Default", 0); }

	@SuppressWarnings("jol")
	public static class LoadedModel extends OpenGLObjectHolder {
		protected static final Vec3 sharedEmptyVec3 = new Vec3(0);
		protected static final Vec2 sharedEmptyVec2 = new Vec2(0);

		protected final Model model;
		protected final String group;
		protected final int mesh;
		protected final Vec3 minBounding;
		protected final Vec3 maxBounding;
		protected final IVec1 indicesCount;
		protected VertexArrayObject<? extends Number> vertexArray;
		protected VertexBufferObject<? extends Number> vertexes;
		protected VertexBufferObject<? extends Number> textureCoordinates;
		protected VertexBufferObject<? extends Number> normals;
		protected VertexBufferObject<? extends Number> indices;

		protected LoadedModel(GLContext<? extends Number> gl, Model model, String group, int mesh) {
			super(gl);
			this.model = model;
			this.group = group;
			this.mesh = mesh;
			this.minBounding = new Vec3();
			this.maxBounding = new Vec3();
			this.indicesCount = new IVec1();
		}

		public Model getModel() { return model; }
		public String getGroup() { return group; }
		public int getMesh() { return mesh; }
		public Vec3 getMinBounding() { return minBounding; }
		public Vec3 getMaxBounding() { return maxBounding; }
		public IVec1 getIndicesCount() { return indicesCount; }
		public VertexArrayObject<? extends Number> getVertexArray() { return vertexArray; }
		public VertexBufferObject<? extends Number> getVertexes() { return vertexes; }
		public VertexBufferObject<? extends Number> getTextureCoordinates() { return textureCoordinates; }
		public VertexBufferObject<? extends Number> getNormals() { return normals; }
		public VertexBufferObject<? extends Number> getIndices() { return indices; }

		public void reload() {
			assertNotDead();
			if(model == null || vertexArray == null) return;
			boolean isVertexArrayBind = vertexArray.isBind();
			if(!isVertexArrayBind) vertexArray.bind();
			boolean isBound;

			ArrayList<Vec3> vertexesData = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			ArrayList<Vec2> textureCoordinatesData = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			ArrayList<Vec3> normalsData = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			ArrayList<Integer> indicesData = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			ArrayList<Model.ModelFaceIndex> indexLookup = Pool.tryBorrow(Pool.getPool(ArrayList.class));
			try {
				Model.ModelObject modelObject = null;
				for(Model.ModelObject object : model.getObjects())
					if(object.getType() == ModelObject.TYPE_OBJECT && object.getName().equals(this.group)) {
						modelObject = object; break; }
				if(modelObject == null) newException("Object not found");
				Model.ModelMesh modelMesh = modelObject.getMeshes().size() > mesh ? modelObject.getMeshes().get(mesh) : null;
				if(modelMesh == null) newException("Mesh not found");
				for(Model.ModelFace modelFace : modelMesh.getFaces())
					ListUtils.fastSet(indexLookup, modelFace.getIndices(), 0, indexLookup.size(), modelFace.getIndices().length);
				Sorting.INTRO_SORT.sort(indexLookup, (o1, o2) -> o1.getVertexIndex() - o2.getVertexIndex());

				int currentSize = 0;
				for(Model.ModelFace modelFace : modelMesh.getFaces()) {
					for(Model.ModelFaceIndex indices : modelFace.getIndices()) {
						Vec3 currentVertex = indices.vertexIndex != Model.ModelFaceIndex.UNDEFINED ? model.vertexes.get(indices.vertexIndex) : null;
						Vec2 currentTextureCoordinate = indices.textureCoordinateIndex != Model.ModelFaceIndex.UNDEFINED ? model.textureCoordinates.get(indices.textureCoordinateIndex) : null;
						Vec3 currentNormal = indices.normalIndex != Model.ModelFaceIndex.UNDEFINED ? model.normals.get(indices.normalIndex) : null;
						int resultModelIndex = findLastVertexIndex(indexLookup, indices, vertexesData, textureCoordinatesData, normalsData);
						if(resultModelIndex == Model.ModelFaceIndex.UNDEFINED) { resultModelIndex = currentSize++;
							if(currentVertex != null) { OpenGLUtils.minX(minBounding, currentVertex); OpenGLUtils.maxX(maxBounding, currentVertex); }
							vertexesData.add(resultModelIndex, currentVertex != null ? currentVertex : sharedEmptyVec3);
							textureCoordinatesData.add(resultModelIndex, currentTextureCoordinate != null ? currentTextureCoordinate : sharedEmptyVec2);
							normalsData.add(resultModelIndex, currentNormal != null ? currentNormal : sharedEmptyVec3);
						} indicesData.add(resultModelIndex);
					}
				}

				indicesCount.set(indicesData.size());
				isBound = vertexes.isBind(); if(!isBound) vertexes.bind(); vertexes.setDataVec(vertexesData); if(!isBound) vertexes.unbind();
				isBound = textureCoordinates.isBind(); if(!isBound) textureCoordinates.bind(); textureCoordinates.setDataVec(textureCoordinatesData); if(!isBound) textureCoordinates.unbind();
				isBound = normals.isBind(); if(!isBound) normals.bind(); normals.setDataVec(normalsData); if(!isBound) normals.unbind();
				isBound = indices.isBind(); if(!isBound) indices.bind(); indices.setData(indicesData); if(!isBound) indices.unbind();
			} finally {
				Pool.returnObject(ArrayList.class, vertexesData);
				Pool.returnObject(ArrayList.class, textureCoordinatesData);
				Pool.returnObject(ArrayList.class, normalsData);
				Pool.returnObject(ArrayList.class, indicesData);
				Pool.returnObject(ArrayList.class, indexLookup);
			}

			if(!isVertexArrayBind) vertexArray.unbind();
		}
		protected int findLastVertexIndex(List<Model.ModelFaceIndex> indexLookup, Model.ModelFaceIndex currentIndex, List<Vec3> vertexesData, List<Vec2> textureCoordinatesData, List<Vec3> normalsData) {
			int start = 0;
			int end = indexLookup.size();
			int current = (end - start) / 2 + start;
			int previous = start;
			while(current != previous) {
				Model.ModelFaceIndex testIndex = indexLookup.get(current);
				if(testIndex.getVertexIndex() == currentIndex.getVertexIndex()) {
					int countStart = current;
					for(int i = 0; i < current; i++) {
						Model.ModelFaceIndex possibleIndex = indexLookup.get(current - i);
						if(possibleIndex == currentIndex) continue;
						if(possibleIndex.getVertexIndex() != currentIndex.getVertexIndex()) break;
						countStart--;
					}
					for(int i = countStart; i < indexLookup.size() - countStart; i++) {
						Model.ModelFaceIndex possibleIndex = indexLookup.get(current + i);
						if(possibleIndex == currentIndex) continue;
						if(possibleIndex.getVertexIndex() != currentIndex.getVertexIndex()) break;
						if((possibleIndex.getTextureCoordinateIndex() == currentIndex.getTextureCoordinateIndex()) && (possibleIndex.getNormalIndex() == currentIndex.getNormalIndex())) {
							Vec3 currentVertex = currentIndex.getVertexIndex() != Model.ModelFaceIndex.UNDEFINED ? model.getVertexes().get(currentIndex.getVertexIndex()) : null;
							Vec2 currentTextureCoordinate = currentIndex.getTextureCoordinateIndex() != Model.ModelFaceIndex.UNDEFINED ? model.getTextureCoordinates().get(currentIndex.getTextureCoordinateIndex()) : null;
							Vec3 currentNormal = currentIndex.getNormalIndex() != Model.ModelFaceIndex.UNDEFINED ? model.getNormals().get(currentIndex.getNormalIndex()) : null;
							int max = NumberUtils.max(vertexesData.size(), textureCoordinatesData.size(), normalsData.size());
							for(int j = 0; j < max; j++)
								if(((currentVertex == null && NumberUtils.add(vertexesData.get(j).d) == 0) || vertexesData.get(j).equals(currentVertex)) &&
								   ((currentTextureCoordinate == null && NumberUtils.add(textureCoordinatesData.get(j).d) == 0) || textureCoordinatesData.get(j).equals(currentTextureCoordinate)) &&
								   ((currentNormal == null && NumberUtils.add(normalsData.get(j).d) == 0) || normalsData.get(j).equals(currentNormal)))
									return j;
						}
					} return Model.ModelFaceIndex.UNDEFINED;
				} else {
					if(testIndex.getVertexIndex() < currentIndex.getVertexIndex()) start = current;
					else end = current;
				}
				previous = current;
				current = (end - start) / 2 + start;
			} return Model.ModelFaceIndex.UNDEFINED;
		}

		@Override protected void arrange() {
			vertexArray = getGL().constructVertexArrayObject();
			vertexArray.create(); vertexArray.bind();
			indices = vertexArray.createBufferObject(getGL().GL_ELEMENT_ARRAY_BUFFER());
			vertexes = vertexArray.createBufferObject(getGL().GL_ARRAY_BUFFER());
			textureCoordinates = vertexArray.createBufferObject(getGL().GL_ARRAY_BUFFER());
			normals = vertexArray.createBufferObject(getGL().GL_ARRAY_BUFFER());
			vertexArray.unbind(); reload();
		}
		@Override protected void disarrange() { if(!vertexArray.isDead()) vertexArray.destroy(); }
	}

	public static class ModelObject {
		public static final int TYPE_GROUP = 0;
		public static final int TYPE_SMOOTHING = 1;
		public static final int TYPE_MERGING = 2;
		public static final int TYPE_OBJECT = 3;
		protected final String name;
		protected final int type;
		protected final List<ModelMesh> meshes;

		public ModelObject(String name, int type) {
			this.name = name;
			this.type = type;
			this.meshes = new ArrayList<>();
		}

		public String getName() { return name; }
		public int getType() { return type; }
		public List<ModelMesh> getMeshes() { return meshes; }
	}
	public static class ModelMesh {
		protected final String name;
		protected final List<ModelFace> faces;

		public ModelMesh(String name) {
			this.name = name;
			this.faces = new ArrayList<>();
		}

		public String getName() { return name; }
		public List<ModelFace> getFaces() { return faces; }
	}
	public static class ModelFace {
		protected final ModelFaceIndex[] indices;

		public ModelFace(int size) {
			this.indices = new ModelFaceIndex[size];
		}

		public ModelFaceIndex[] getIndices() { return indices; }
	}
	public static class ModelFaceIndex {
		public static final int UNDEFINED = Integer.MIN_VALUE;
		protected int vertexIndex;
		protected int textureCoordinateIndex;
		protected int normalIndex;

		public ModelFaceIndex(int vertexIndex, int textureCoordinateIndex, int normalIndex) {
			this.vertexIndex = vertexIndex;
			this.textureCoordinateIndex = textureCoordinateIndex;
			this.normalIndex = normalIndex;
		}

		public int getVertexIndex() { return vertexIndex; }
		public int getTextureCoordinateIndex() { return textureCoordinateIndex; }
		public int getNormalIndex() { return normalIndex; }

		public void setVertexIndex(int vertexIndex) { this.vertexIndex = vertexIndex; }
		public void setTextureCoordinateIndex(int textureCoordinateIndex) { this.textureCoordinateIndex = textureCoordinateIndex; }
		public void setNormalIndex(int normalIndex) { this.normalIndex = normalIndex; }
	}
}
