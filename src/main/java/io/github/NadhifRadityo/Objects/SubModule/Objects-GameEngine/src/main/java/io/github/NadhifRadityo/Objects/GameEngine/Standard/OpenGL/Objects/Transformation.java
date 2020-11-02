package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Mat4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.TempVec;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Program;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ListUtils;

import java.util.ArrayList;
import java.util.Iterator;

import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Projection.PerspectiveProjection.BOTTOM;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Projection.PerspectiveProjection.BOTTOM_LEFT;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Projection.PerspectiveProjection.BOTTOM_RIGHT;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Projection.PerspectiveProjection.FAR;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Projection.PerspectiveProjection.LEFT;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Projection.PerspectiveProjection.NEAR;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Projection.PerspectiveProjection.PerspectiveProjection;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Projection.PerspectiveProjection.RIGHT;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Projection.PerspectiveProjection.TOP;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Projection.PerspectiveProjection.TOP_LEFT;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.Projection.PerspectiveProjection.TOP_RIGHT;
import static io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils.Matrix3D;
import static io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils.addX;
import static io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils.mulX;

public abstract class Transformation extends OpenGLObjectHolder {
	protected TempVec temp;
	protected boolean changed;

	protected final ArrayList<TransformationMatrixInjector> injectors;
	protected final byte mode;
	protected final byte translationMode;
	protected final byte rotationMode;
	protected final byte scaleMode;
	protected final Mat4 matrix;

	protected Transformation(GLContext<? extends Number> gl, Mode mode, TranslationMode translationMode, RotationMode rotationMode, ScaleMode scaleMode, TempVec temp) {
		super(gl);
		this.temp = temp;
		this.changed = true;

		this.injectors = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		this.mode = mode.getId();
		this.translationMode = translationMode.getId();
		this.rotationMode = rotationMode.getId();
		this.scaleMode = scaleMode.getId();
		this.matrix = new Mat4();
	}

	public TempVec getTemp() { return temp; }
	public Mode getMode() { return Mode.fromId(mode); }
	public TranslationMode getTranslationMode() { return TranslationMode.fromId(translationMode); }
	public RotationMode getRotationMode() { return RotationMode.fromId(rotationMode); }
	public ScaleMode getScaleMode() { return ScaleMode.fromId(scaleMode); }

	public void setTemp(TempVec temp) { this.temp = temp; }
	public void markChanged() { this.changed = true; }

	protected TransformationMatrixInjector getInjector(Program<? extends Number> program) { for(TransformationMatrixInjector injector : injectors) if(injector.getProgram() == program) return injector; return null; }
	public void addProgram(Program<? extends Number> program) { assertNotDead(); assertCreated(); assertValid(program); if(getInjector(program) != null) return; TransformationMatrixInjector injector = new TransformationMatrixInjector(program); injector.create(); injectors.add(injector); }
	public void removeProgram(Program<? extends Number> program) { assertNotDead(); assertCreated(); assertValid(program); TransformationMatrixInjector injector = getInjector(program); if(injector == null) return; injectors.remove(injector); injector.destroy(); }

	protected abstract void doTransformation();

	public void update() {
		assertNotDead(); assertCreated(); if(changed) { doTransformation(); changed = false; }
		Iterator<TransformationMatrixInjector> it = ListUtils.reusableIterator(injectors);
		while(it.hasNext()) { TransformationMatrixInjector injector = it.next(); injector.reload(); }
	}

	@Override protected void arrange() { }
	@Override protected void disarrange() {
		Pool.returnObject(ArrayList.class, injectors);
	}

	public static class EntityTransformation extends Transformation {
		protected final Vec3 translate;
		protected final Vec3 rotate;
		protected final Vec3 scale;
		protected final Vec4 quaternion;

		public EntityTransformation(GLContext<? extends Number> gl, TranslationMode translationMode, RotationMode rotationMode, ScaleMode scaleMode, TempVec temp) {
			super(gl, Mode.ENTITY, translationMode, rotationMode, scaleMode, temp);
			this.translate = new Vec3(0);
			this.rotate = new Vec3(0);
			this.scale = new Vec3(1);
			this.quaternion = rotationMode == RotationMode.QUATERNION ? new Vec4() : null;
		}

		public Vec3 getTranslate() { return translate; }
		public Vec3 getRotate() { return rotate; }
		public Vec3 getScale() { return scale; }

		public void setTranslateX(double x) { markChanged(); translate.x(x); }
		public void setTranslateY(double y) { markChanged(); translate.y(y); }
		public void setTranslateZ(double z) { markChanged(); translate.z(z); }
		public void setTranslate(double x, double y, double z) { markChanged(); translate.set(x, y, z); }
		public void setTranslate(Vec3 translate) { markChanged(); this.translate.set(translate.x(), translate.y(), translate.y()); }
		public void setRotateYaw(double yaw) { markChanged(); rotate.x(yaw); }
		public void setRotatePitch(double pitch) { markChanged(); rotate.y(pitch); }
		public void setRotateRoll(double roll) { markChanged(); rotate.z(roll); }
		public void setRotate(double yaw, double pitch, double roll) { markChanged(); rotate.set(yaw, pitch, roll); }
		public void setRotate(Vec3 rotate) { markChanged(); this.rotate.set(rotate.x(), rotate.y(), rotate.y()); }
		public void setScaleX(double x) { markChanged(); scale.x(x); }
		public void setScaleY(double y) { markChanged(); scale.y(y); }
		public void setScaleZ(double z) { markChanged(); scale.z(z); }
		public void setScale(double x, double y, double z) { markChanged(); scale.set(x, y, z); }
		public void setScale(Vec3 scale) { markChanged(); this.scale.set(scale.x(), scale.y(), scale.y()); }

		@Override protected void doTransformation() {
			Matrix3D.loadIdentity(matrix);
			if(getTranslationMode() == TranslationMode.DEFAULT)
				Matrix3D.Transformation.translate(matrix, translate, temp);
			if(getRotationMode() == RotationMode.EULER)
				Matrix3D.Transformation.rotate_xyz(matrix, rotate, temp);
			else if(getRotationMode() == RotationMode.QUATERNION)
				Matrix3D.Transformation.quaternionRotate(matrix, rotate.x(), rotate.y(), rotate.z(), quaternion, temp);
			if(getScaleMode() == ScaleMode.DEFAULT)
				Matrix3D.Transformation.scale(matrix, scale, temp);
		}
	}
	public static class CameraTransformation extends Transformation {
		protected final Vec3 translate;
		protected final Vec3 rotate;
		protected final Vec4 quaternion;
		protected Projection.PerspectiveProjection projection;

		public CameraTransformation(GLContext<? extends Number> gl, RotationMode rotationMode, Projection projection, TempVec temp) {
			super(gl, Mode.CAMERA, TranslationMode.DEFAULT, rotationMode, ScaleMode.DEFAULT, temp);
			this.translate = new Vec3(0);
			this.rotate = new Vec3(0);
			this.quaternion = rotationMode == RotationMode.QUATERNION ? new Vec4() : null;
			this.projection = projection instanceof Projection.PerspectiveProjection ? (Projection.PerspectiveProjection) projection : null;
		}
		public CameraTransformation(GLContext<? extends Number> gl, RotationMode rotationMode, TempVec temp) {
			this(gl, rotationMode, null, temp);
		}

		public Vec3 getTranslate() { return translate; }
		public Vec3 getRotate() { return rotate; }
		public PerspectiveProjection getProjection() { return projection; }

		public void setTranslateX(double x) { markChanged(); translate.x(x); }
		public void setTranslateY(double y) { markChanged(); translate.y(y); }
		public void setTranslateZ(double z) { markChanged(); translate.z(z); }
		public void setTranslate(double x, double y, double z) { markChanged(); translate.set(x, y, z); }
		public void setTranslate(Vec3 translate) { markChanged(); this.translate.set(translate.x(), translate.y(), translate.y()); }
		public void setRotateYaw(double yaw) { markChanged(); rotate.x(yaw); }
		public void setRotatePitch(double pitch) { markChanged(); rotate.y(pitch); }
		public void setRotateRoll(double roll) { markChanged(); rotate.z(roll); }
		public void setRotate(double yaw, double pitch, double roll) { markChanged(); rotate.set(yaw, pitch, roll); }
		public void setRotate(Vec3 rotate) { markChanged(); this.rotate.set(rotate.x(), rotate.y(), rotate.y()); }

		public void setProjection(Projection projection) {
			markChanged();
			this.projection = projection instanceof Projection.PerspectiveProjection ? (Projection.PerspectiveProjection) projection : null;
		}

		@Override protected void doTransformation() {
			Matrix3D.loadIdentity(matrix);
			if(getRotationMode() == RotationMode.EULER)
				Matrix3D.View.view(matrix, rotate.x(), rotate.y(), rotate.z(), translate, temp);
			else if(getRotationMode() == RotationMode.QUATERNION)
				Matrix3D.View.viewQuaternion(matrix, rotate.x(), rotate.y(), rotate.z(), quaternion, translate, temp);
			if(projection == null || !projection.isCalculateAdditional()) return; calculateAdditional();
		}

		public void calculateAdditional() {
			Vec3 forwardVector = projection.getForwardVector();
			Vec3 leftVector = projection.getLeftVector();
			Vec3 upVector = projection.getUpVector();

			forwardVector.set(-matrix.d[2], -matrix.d[6], -matrix.d[10]);
			leftVector.set(matrix.d[0], matrix.d[4], matrix.d[8]);
			upVector.set(matrix.d[1], matrix.d[5], matrix.d[9]);

			Vec3 nearCenter = projection.getNearCenter();
			Vec3 farCenter = projection.getFarCenter();
			Size nearSize = projection.getNearSize();
			Size farSize = projection.getFarSize();

			Vec3[] nearRectangle = projection.getNearRectangle();
			Vec3[] farRectangle = projection.getFarRectangle();
			Projection.PerspectiveProjection.Plane[] planes = projection.getPlanes();

			nearCenter.set(forwardVector.x(), forwardVector.y(), forwardVector.z());
			mulX(nearCenter, projection.getNearDistance());
			addX(nearCenter, translate);
			farCenter.set(forwardVector.x(), forwardVector.y(), forwardVector.z());
			mulX(farCenter, projection.getFarDistance());
			addX(farCenter, translate);

			nearRectangle[TOP_LEFT].x(nearCenter.x() + (upVector.x() * nearSize.y() / 2) - (leftVector.x() * nearSize.x() / 2));
			nearRectangle[TOP_LEFT].y(nearCenter.y() + (upVector.y() * nearSize.y() / 2) - (leftVector.y() * nearSize.x() / 2));
			nearRectangle[TOP_LEFT].z(nearCenter.z() + (upVector.z() * nearSize.y() / 2) - (leftVector.z() * nearSize.x() / 2));
			nearRectangle[TOP_RIGHT].x(nearCenter.x() + (upVector.x() * nearSize.y() / 2) + (leftVector.x() * nearSize.x() / 2));
			nearRectangle[TOP_RIGHT].y(nearCenter.y() + (upVector.y() * nearSize.y() / 2) + (leftVector.y() * nearSize.x() / 2));
			nearRectangle[TOP_RIGHT].z(nearCenter.z() + (upVector.z() * nearSize.y() / 2) + (leftVector.z() * nearSize.x() / 2));
			nearRectangle[BOTTOM_LEFT].x(nearCenter.x() - (upVector.x() * nearSize.y() / 2) - (leftVector.x() * nearSize.x() / 2));
			nearRectangle[BOTTOM_LEFT].y(nearCenter.y() - (upVector.y() * nearSize.y() / 2) - (leftVector.y() * nearSize.x() / 2));
			nearRectangle[BOTTOM_LEFT].z(nearCenter.z() - (upVector.z() * nearSize.y() / 2) - (leftVector.z() * nearSize.x() / 2));
			nearRectangle[BOTTOM_RIGHT].x(nearCenter.x() - (upVector.x() * nearSize.y() / 2) + (leftVector.x() * nearSize.x() / 2));
			nearRectangle[BOTTOM_RIGHT].y(nearCenter.y() - (upVector.y() * nearSize.y() / 2) + (leftVector.y() * nearSize.x() / 2));
			nearRectangle[BOTTOM_RIGHT].z(nearCenter.z() - (upVector.z() * nearSize.y() / 2) + (leftVector.z() * nearSize.x() / 2));

			farRectangle[TOP_LEFT].x(farCenter.x() + (upVector.x() * farSize.y() / 2) - (leftVector.x() * farSize.x() / 2));
			farRectangle[TOP_LEFT].y(farCenter.y() + (upVector.y() * farSize.y() / 2) - (leftVector.y() * farSize.x() / 2));
			farRectangle[TOP_LEFT].z(farCenter.z() + (upVector.z() * farSize.y() / 2) - (leftVector.z() * farSize.x() / 2));
			farRectangle[TOP_RIGHT].x(farCenter.x() + (upVector.x() * farSize.y() / 2) + (leftVector.x() * farSize.x() / 2));
			farRectangle[TOP_RIGHT].y(farCenter.y() + (upVector.y() * farSize.y() / 2) + (leftVector.y() * farSize.x() / 2));
			farRectangle[TOP_RIGHT].z(farCenter.z() + (upVector.z() * farSize.y() / 2) + (leftVector.z() * farSize.x() / 2));
			farRectangle[BOTTOM_LEFT].x(farCenter.x() - (upVector.x() * farSize.y() / 2) - (leftVector.x() * farSize.x() / 2));
			farRectangle[BOTTOM_LEFT].y(farCenter.y() - (upVector.y() * farSize.y() / 2) - (leftVector.y() * farSize.x() / 2));
			farRectangle[BOTTOM_LEFT].z(farCenter.z() - (upVector.z() * farSize.y() / 2) - (leftVector.z() * farSize.x() / 2));
			farRectangle[BOTTOM_RIGHT].x(farCenter.x() - (upVector.x() * farSize.y() / 2) + (leftVector.x() * farSize.x() / 2));
			farRectangle[BOTTOM_RIGHT].y(farCenter.y() - (upVector.y() * farSize.y() / 2) + (leftVector.y() * farSize.x() / 2));
			farRectangle[BOTTOM_RIGHT].z(farCenter.z() - (upVector.z() * farSize.y() / 2) + (leftVector.z() * farSize.x() / 2));

			planes[TOP].set3Points(nearRectangle[TOP_RIGHT], nearRectangle[TOP_LEFT], farRectangle[TOP_LEFT]);
			planes[BOTTOM].set3Points(nearRectangle[BOTTOM_LEFT], nearRectangle[BOTTOM_RIGHT], farRectangle[BOTTOM_RIGHT]);
			planes[LEFT].set3Points(nearRectangle[TOP_LEFT], nearRectangle[BOTTOM_LEFT], farRectangle[BOTTOM_LEFT]);
			planes[RIGHT].set3Points(nearRectangle[BOTTOM_RIGHT], nearRectangle[TOP_RIGHT], farRectangle[BOTTOM_RIGHT]);
			planes[NEAR].set3Points(nearRectangle[TOP_LEFT], nearRectangle[TOP_RIGHT], nearRectangle[BOTTOM_RIGHT]);
			planes[FAR].set3Points(farRectangle[TOP_RIGHT], farRectangle[TOP_LEFT], farRectangle[BOTTOM_LEFT]);
		}
	}

	public enum Mode {
		ENTITY(0), CAMERA(1);
		int id; Mode(int id) { this.id = id; }
		public byte getId() { return (byte) id; }
		public static Mode fromId(byte id) {
			if(id == ENTITY.id) return ENTITY;
			if(id == CAMERA.id) return CAMERA;
			return null;
		}
	}
	public enum TranslationMode {
		DEFAULT(0);
		int id; TranslationMode(int id) { this.id = id; }
		public byte getId() { return (byte) id; }
		public static TranslationMode fromId(byte id) {
			if(id == DEFAULT.id) return DEFAULT;
			return null;
		}
	}
	public enum RotationMode {
		EULER(0), QUATERNION(1);
		int id; RotationMode(int id) { this.id = id; }
		public byte getId() { return (byte) id; }
		public static RotationMode fromId(byte id) {
			if(id == EULER.id) return EULER;
			if(id == QUATERNION.id) return QUATERNION;
			return null;
		}
	}
	public enum ScaleMode {
		DEFAULT(0);
		int id; ScaleMode(int id) { this.id = id; }
		public byte getId() { return (byte) id; }
		public static ScaleMode fromId(byte id) {
			if(id == DEFAULT.id) return DEFAULT;
			return null;
		}
	}

	protected class TransformationMatrixInjector extends ObjectsProgram<EntityVariablesPack> {
		public TransformationMatrixInjector(Program<? extends Number> program) { super(program, program.getVariablesPack(EntityVariablesPack.class)); }

		@Override public void onProgramBind() { if(getMode() == Mode.ENTITY) pack.VAR_transformationMatrix.loadMatF(matrix); else if(getMode() == Mode.CAMERA) pack.VAR_viewMatrix.loadMatF(matrix); }
		public void reload() { if(program.isBind()) onProgramBind(); }
	}
}
