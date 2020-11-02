package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Mat4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.TempVec;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.OpenGLNativeHolder;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Program;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Texture;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.VertexArrayObject;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.VertexBufferObject;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.Matrix3D;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.add;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.divX;

public class Entity extends ObjectsProgram<EntityVariablesPack> {
	protected final Model.LoadedModel model;
	protected Texture<? extends Number> texture;
	protected TempVec temp;

	protected final Mat4 transformationMatrix;
	protected final Vec3 translation;
	protected final Vec3 scale;
	protected final Vec3 rotation;
	protected final Vec3 center;

	public Entity(Model.LoadedModel model, Program<? extends Number> program, Texture<? extends Number> texture, TempVec temp) {
		super(program, program.getVariablesPack(EntityVariablesPack.class)); assertValid(model); assertValid(texture);
		this.model = model;
		this.texture = texture;
		this.temp = temp;

		this.transformationMatrix = new Mat4();
		this.translation = new Vec3(0);
		this.scale = new Vec3(1);
		this.rotation = new Vec3(0);
		this.center = divX(add(model.getMaxBounding(), model.getMinBounding()), 2);
	}

	public Model.LoadedModel getModel() { return model; }
	public Texture<? extends Number> getTexture() { return texture; }
	public TempVec getTemp() { return temp; }
	public Vec3 getTranslation() { return translation; }
	public Vec3 getScale() { return scale; }
	public Vec3 getRotation() { return rotation; }
	public Vec3 getCenter() { return center; }

	public void setTexture(Texture<? extends Number> texture) { assertValid(texture); this.texture = texture; }
	public void setTemp(TempVec temp) { this.temp = temp; }
	public void setTranslation(double tx, double ty, double tz) { translation.set(tx, ty, tz); }
	public void setTranslation(Vec3 translation) { setTranslation(translation.x(), translation.y(), translation.z()); }
	public void setScale(double sx, double sy, double sz) { scale.set(sx, sy, sz); }
	public void setScale(Vec3 scale) { setScale(scale.x(), scale.y(), scale.y()); }
	public void setRotation(double rx, double ry, double rz) { rotation.set(rx, ry, rz); }
	public void setRotation(Vec3 rotation) { setRotation(rotation.x(), rotation.y(), rotation.y()); }
	public void setCenter(double cx, double cy, double cz) { center.set(cx, cy, cz); }
	public void setCenter(Vec3 center) { setCenter(center.x(), center.y(), center.y()); }

	protected void setup() {
		VertexArrayObject<? extends Number> vao = model.getVertexArray();
		VertexBufferObject<? extends Number> indices = model.getIndices();
		VertexBufferObject<? extends Number> vertexes = model.getVertexes();
		VertexBufferObject<? extends Number> textureCoordinates = model.getTextureCoordinates();
		VertexBufferObject<? extends Number> normals = model.getNormals();

		vao.bind();
		indices.bind();
		{
			vertexes.bind();
			pack.VAR_position.giveInstruction(3, getGL().GL_FLOAT(), false, 0, 0);
			pack.VAR_position.enable();
			vertexes.unbind();
		}
		if(pack.VAR_textureCoordinates != null) {
			textureCoordinates.bind();
			pack.VAR_textureCoordinates.giveInstruction(2, getGL().GL_FLOAT(), false, 0, 0);
			pack.VAR_textureCoordinates.enable();
			textureCoordinates.unbind();
		}
		if(pack.VAR_normals != null) {
			normals.bind();
			pack.VAR_normals.giveInstruction(3, getGL().GL_FLOAT(), false, 0, 0);
			pack.VAR_normals.enable();
			normals.unbind();
		}
		vao.unbind();
	}

	public void render() {
		assertNotDead(); assertCreated();
		Program<? extends Number> boundProgram = (Program<? extends Number>) getBindableNative(Program.TYPE);
		if(boundProgram != program) { if(boundProgram != null) boundProgram.unbind(); program.bind(); }
		VertexArrayObject<? extends Number> vao = model.getVertexArray();
		if(!model.getIndices().isBind()) setup();

		vao.bind();
		if(pack.VAR_transformationMatrix != null) {
			Matrix3D.loadIdentity(transformationMatrix);
			Matrix3D.Transformation.translate(transformationMatrix, translation, temp);
			// TODO translate around origin
//			Matrix3D.Transformation.translate(transformationMatrix, -center.x(), -center.y(), -center.z(), temp);
			Matrix3D.Transformation.scale(transformationMatrix, scale, temp);
			// TODO scale around origin
//			Matrix3D.Transformation.translate(transformationMatrix, ((scale.x() * translation.x()) - translation.x()) / 2, ((scale.y() * translation.y()) - translation.y()) / 2, ((scale.z() * translation.z()) - translation.z()) / 2, temp);
			Matrix3D.Transformation.rotate_xyz(transformationMatrix, rotation, temp);
			pack.VAR_transformationMatrix.loadMatF(transformationMatrix);
		}
		int textureSlot = texture.getCurrentSlot();
		boolean boundTexture = texture.isBind();
		if(textureSlot == OpenGLNativeHolder.SlotableNative.SLOT_NULL) texture.allocateSlot();
		if(!boundTexture) texture.bind();
		getGL().glDrawElements(getGL().GL_TRIANGLES(), model.getIndicesCount().x(), getGL().GL_UNSIGNED_INT(), 0);
		if(!boundTexture) texture.unbind();
		if(textureSlot == OpenGLNativeHolder.SlotableNative.SLOT_NULL) texture.deallocateSlot();
		vao.unbind();

		if(boundProgram != program) { program.unbind(); if(boundProgram != null) boundProgram.bind(); }
	}
}
