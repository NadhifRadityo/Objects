package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Mat4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.TempVec;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Program;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ListUtils;

import java.util.ArrayList;
import java.util.Iterator;

import static io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils.Matrix3D;
import static io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils.crossX;
import static io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils.dot;
import static io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils.normalizeX;
import static io.github.NadhifRadityo.Objects.Utilizations.GenTypeUtils.subX;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.radians;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.tan;

public abstract class Projection extends OpenGLObjectHolder {
	protected TempVec temp;
	protected boolean changed;

	protected final ArrayList<ProjectionMatrixInjector> injectors;
	protected final byte mode;
	protected final Size windowSize;
	protected final Mat4 matrix;

	protected Projection(GLContext<? extends Number> gl, Mode mode, Size windowSize, TempVec temp) {
		super(gl);
		this.temp = temp;
		this.changed = true;

		this.injectors = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		this.mode = mode.getId();
		this.windowSize = new Size(windowSize);
		this.matrix = new Mat4();
	}

	public TempVec getTemp() { return temp; }
	public Mode getMode() { return Mode.fromId(mode); }

	public void setTemp(TempVec temp) { this.temp = temp; }
	public void markChanged() { this.changed = true; }

	protected ProjectionMatrixInjector getInjector(Program<? extends Number> program) { for(ProjectionMatrixInjector injector : injectors) if(injector.getProgram() == program) return injector; return null; }
	public void addProgram(Program<? extends Number> program) { assertNotDead(); assertCreated(); assertValid(program); if(getInjector(program) != null) return; ProjectionMatrixInjector injector = new ProjectionMatrixInjector(program); injector.create(); injectors.add(injector); }
	public void removeProgram(Program<? extends Number> program) { assertNotDead(); assertCreated(); assertValid(program); ProjectionMatrixInjector injector = getInjector(program); if(injector == null) return; injectors.remove(injector); injector.destroy(); }

	protected abstract void doProjection();

	public void update() {
		assertNotDead(); assertCreated(); if(changed) { doProjection(); changed = false; }
		Iterator<ProjectionMatrixInjector> it = ListUtils.reusableIterator(injectors);
		while(it.hasNext()) { ProjectionMatrixInjector injector = it.next(); injector.reload(); }
	}

	public void onResize(Size windowSize) {
		this.windowSize.set(windowSize.x(), windowSize.y());
		markChanged(); update();
	}

	@Override protected void arrange() { }
	@Override protected void disarrange() {
		Pool.returnObject(ArrayList.class, injectors);
	}

	@SuppressWarnings("jol")
	public static class PerspectiveProjection extends Projection {
		public static int TOP = 0;
		public static int BOTTOM = 1;
		public static int LEFT = 2;
		public static int RIGHT = 3;
		public static int NEAR = 4;
		public static int FAR = 5;
		protected static int TOP_LEFT = 0;
		protected static int TOP_RIGHT = 1;
		protected static int BOTTOM_LEFT = 2;
		protected static int BOTTOM_RIGHT = 3;

		protected double fov;
		protected double nearDistance;
		protected double farDistance;

		protected boolean calculateAdditional;
		protected Size nearSize;
		protected Size farSize;
		protected Vec3 forwardVector;
		protected Vec3 leftVector;
		protected Vec3 upVector;
		protected Vec3 nearCenter;
		protected Vec3 farCenter;
		protected Vec3[] nearRectangle;
		protected Vec3[] farRectangle;
		protected Plane[] planes;

		public PerspectiveProjection(GLContext<? extends Number> gl, Size windowSize, TempVec temp) {
			super(gl, Mode.PERSPECTIVE, windowSize, temp);
			this.fov = 70;
			this.nearDistance = 0.1;
			this.farDistance = 1000;
		}

		public double getFov() { return fov; }
		public double getNearDistance() { return nearDistance; }
		public double getFarDistance() { return farDistance; }
		public boolean isCalculateAdditional() { return calculateAdditional; }
		public Size getNearSize() { return nearSize; }
		public Size getFarSize() { return farSize; }
		public Vec3 getForwardVector() { return forwardVector; }
		public Vec3 getLeftVector() { return leftVector; }
		public Vec3 getUpVector() { return upVector; }
		public Vec3 getNearCenter() { return nearCenter; }
		public Vec3 getFarCenter() { return farCenter; }
		public Vec3[] getNearRectangle() { return nearRectangle; }
		public Vec3[] getFarRectangle() { return farRectangle; }
		public Plane[] getPlanes() { return planes; }

		public void setFov(double fov) { markChanged(); this.fov = fov; }
		public void setNearDistance(double nearDistance) { markChanged(); this.nearDistance = nearDistance; }
		public void setFarDistance(double farDistance) { markChanged(); this.farDistance = farDistance; }
		public void setCalculateAdditional(boolean calculateAdditional) {
			if(this.calculateAdditional == calculateAdditional) return;
			markChanged(); this.calculateAdditional = calculateAdditional;
			if(calculateAdditional) {
				this.nearSize = new Size();
				this.farSize = new Size();
				this.forwardVector = new Vec3(0);
				this.leftVector = new Vec3(0);
				this.upVector = new Vec3(0);
				this.nearCenter = new Vec3(0);
				this.farCenter = new Vec3(0);
				this.nearRectangle = new Vec3[4];
				this.farRectangle = new Vec3[4];
				this.planes = new Plane[6];
				for(int i = 0; i < 4; i++) { nearRectangle[i] = new Vec3(0); farRectangle[i] = new Vec3(0); }
				for(int i = 0; i < 6; i++) planes[i] = new Plane();
			} else {
				this.nearSize = null;
				this.farSize = null;
				this.forwardVector = null;
				this.leftVector = null;
				this.upVector = null;
				this.nearCenter = null;
				this.farCenter = null;
				this.nearRectangle = null;
				this.farRectangle = null;
				this.planes = null;
			}
		}

		@Override protected void doProjection() {
			Matrix3D.loadIdentity(matrix);
			Matrix3D.Projection.projection(matrix, windowSize, fov, nearDistance, farDistance, temp);
			if(!calculateAdditional) return;
			double aspectRatio = (double) windowSize.getWidth() / (double) windowSize.getHeight();
			double tan = tan(radians(fov / 2.0d)) * 2;
			nearSize.y(nearDistance * tan);
			nearSize.x(nearSize.y() * aspectRatio);
			farSize.y(farDistance * tan);
			farSize.x(farSize.y() * aspectRatio);
		}

		public static class Plane {
			protected final Vec3 data;
			protected double D;

			protected Plane() {
				this.data = new Vec3(0);
			}

			protected void set3Points(Vec3 p0, Vec3 p1, Vec3 p2) {
				data.set(p1.x(), p1.y(), p1.z());
				subX(data, p0);
				crossX(data, p2.x() - p0.x(), p2.y() - p0.y(), p2.z() - p0.z());
				normalizeX(data); //D = dot(mul(data, -1), p0); // LEAK
				D = -data.x() * p0.x() + -data.y() * p0.y() + -data.z() * p0.z();
			}
			public double distance(Vec3 p) { return dot(data, p) + D; }
			public double distance(double x, double y, double z) { return /*dot(data, x, y, z)*/ data.x() * x + data.y() * y + data.z() * z + D; }
			public void reset() { data.reset(); D = 0; }
		}
	}
	@SuppressWarnings("jol")
	public static class OrthogonalProjection extends Projection {
		protected double nearDistance = -1.;
		protected double farDistance = 1.;

		protected boolean auto;
		protected double left;
		protected double top;
		protected double right;
		protected double bottom;

		public OrthogonalProjection(GLContext<? extends Number> gl, Size windowSize, TempVec temp) {
			super(gl, Mode.ORTHOGONAL, windowSize, temp);
		}

		public double getNearDistance() { return nearDistance; }
		public double getFarDistance() { return farDistance; }
		public boolean isAuto() { return auto; }
		public double getLeft() { return left; }
		public double getTop() { return top; }
		public double getRight() { return right; }
		public double getBottom() { return bottom; }

		public void setNearDistance(double nearDistance) { markChanged(); this.nearDistance = nearDistance; }
		public void setFarDistance(double farDistance) { markChanged(); this.farDistance = farDistance; }
		public void setAuto(boolean auto) { markChanged(); this.auto = auto; }
		public void setLeft(double left) { markChanged(); this.left = left; }
		public void setTop(double top) { markChanged(); this.top = top; }
		public void setRight(double right) { markChanged(); this.right = right; }
		public void setBottom(double bottom) { markChanged(); this.bottom = bottom; }

		@Override protected void doProjection() {
			if(auto) { setRight(windowSize.getWidth()); setTop(windowSize.getHeight()); }
			Matrix3D.loadIdentity(matrix);
			Matrix3D.Projection.ortho(matrix, left, top, right, bottom, nearDistance, farDistance, temp);
		}
	}

	public enum Mode {
		PERSPECTIVE(0), ORTHOGONAL(1);
		int id; Mode(int id) { this.id = id; }
		public byte getId() { return (byte) id; }
		public static Mode fromId(byte id) {
			if(id == PERSPECTIVE.id) return PERSPECTIVE;
			if(id == ORTHOGONAL.id) return ORTHOGONAL;
			return null;
		}
	}

	protected class ProjectionMatrixInjector extends ObjectsProgram<EntityVariablesPack> {
		public ProjectionMatrixInjector(Program<? extends Number> program) { super(program, program.getVariablesPack(EntityVariablesPack.class)); }

		@Override public void onProgramBind() { pack.VAR_projectionMatrix.loadMatF(matrix); }
		public void reload() { if(program.isBind()) onProgramBind(); }
	}
}
