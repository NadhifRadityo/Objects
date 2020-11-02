package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.TempVec;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.addX;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.atan;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.atan2;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.distance;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.maxX;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.minX;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.normalizeX;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.radians;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sub;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.subX;

@SuppressWarnings({"jol"})
public class Camera extends OpenGLObjectHolder {
	protected TempVec temp;

	protected Projection.PerspectiveProjection projection;
	protected Transformation.CameraTransformation transformation;

	protected long lastTick;
	protected final Vec3 positionSpeed;
	protected double positionAcceleration;
	protected final Vec2 mousePos;
	protected final Vec2 mouseSpeed;
	protected final Vec2 mouseOrigin;

	protected final ArrayList<Long> forwardKeys;
	protected final ArrayList<Long> backwardKeys;
	protected final ArrayList<Long> rightKeys;
	protected final ArrayList<Long> leftKeys;
	protected final ArrayList<Long> upKeys;
	protected final ArrayList<Long> downKeys;
	protected final Vec3 direction;
	protected boolean isForward;
	protected boolean isBackward;
	protected boolean isRight;
	protected boolean isLeft;
	protected boolean isUp;
	protected boolean isDown;

	public Camera(GLContext<? extends Number> gl, Vec2 mouseOrigin, long[] forwardKeys, long[] backwardKeys, long[] rightKeys, long[] leftKeys, long[] upKeys, long[] downKeys, TempVec temp) {
		super(gl);
		this.temp = temp;

		this.lastTick = 0;
		this.positionSpeed = new Vec3(0);
		this.positionAcceleration = 0.05;
		this.mousePos = new Vec2(0);
		this.mouseSpeed = new Vec2(0.1);
		this.mouseOrigin = new Vec2(mouseOrigin.x(), mouseOrigin.y());

		this.forwardKeys = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		this.backwardKeys = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		this.rightKeys = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		this.leftKeys = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		this.upKeys = Pool.tryBorrow(Pool.getPool(ArrayList.class));
		this.downKeys = Pool.tryBorrow(Pool.getPool(ArrayList.class));

		this.forwardKeys.addAll(Arrays.asList(ArrayUtils.toObject(forwardKeys)));
		this.backwardKeys.addAll(Arrays.asList(ArrayUtils.toObject(backwardKeys)));
		this.rightKeys.addAll(Arrays.asList(ArrayUtils.toObject(rightKeys)));
		this.leftKeys.addAll(Arrays.asList(ArrayUtils.toObject(leftKeys)));
		this.upKeys.addAll(Arrays.asList(ArrayUtils.toObject(upKeys)));
		this.downKeys.addAll(Arrays.asList(ArrayUtils.toObject(downKeys)));
		this.direction = new Vec3();
	}
	public Camera(GLContext<? extends Number> gl, Vec2 mouseOrigin, TempVec temp) {
		this(gl, mouseOrigin, new long[] { gl.GL_INPUT_KEYBOARD_KEY_W() }, new long[] { gl.GL_INPUT_KEYBOARD_KEY_S() }, new long[] { gl.GL_INPUT_KEYBOARD_KEY_D() }, new long[] { gl.GL_INPUT_KEYBOARD_KEY_A() }, new long[] { gl.GL_INPUT_KEYBOARD_KEY_SPACE() }, new long[] { gl.GL_INPUT_KEYBOARD_KEY_LEFT_SHIFT() }, temp);
	}

	public TempVec getTemp() { return temp; }
	public Projection.PerspectiveProjection getProjection() { return projection; }
	public Transformation.CameraTransformation getTransformation() { return transformation; }
	public Vec3 getPositionSpeed() { return positionSpeed; }
	public double getPositionAcceleration() { return positionAcceleration; }
	public Vec2 getMouseSpeed() { return mouseSpeed; }
	public Vec2 getMouseOrigin() { return mouseOrigin; }

	public void setTemp(TempVec temp) { this.temp = temp; }
	public void setProjection(Projection.PerspectiveProjection projection) { this.projection = projection; }
	public void setTransformation(Transformation.CameraTransformation transformation) { this.transformation = transformation; }
	public void setPositionSpeed(double sx, double sy, double sz) { positionSpeed.set(sx, sy, sz); }
	public void setPositionSpeed(Vec3 positionSpeed) { setPositionSpeed(positionSpeed.x(), positionSpeed.y(), positionSpeed.z()); }
	public void setPositionAcceleration(double positionAcceleration) { this.positionAcceleration = positionAcceleration; }
	public void setMouseSpeed(double mx, double my) { mouseSpeed.set(mx, my); }
	public void setMouseSpeed(Vec2 mouseSpeed) { setMouseSpeed(mouseSpeed.x(), mouseSpeed.y()); }
	public void setMouseOrigin(double mx, double my) { mouseOrigin.set(mx, my); }
	public void setMouseOrigin(Vec2 mouseOrigin) { setMouseOrigin(mouseOrigin.x(), mouseOrigin.y()); }

	public void update() {
		assertNotDead(); assertCreated();
		projection.update();
		transformation.update();
	}

	public void tick() {
		long current = System.currentTimeMillis();
		if(lastTick == 0) lastTick = current - 1;
		long delta = current - lastTick;
		double skipped = (double) delta / 15;
		if(mousePos.x() != 0 || mousePos.y() != 0) {
			transformation.setRotateYaw(transformation.getRotate().x() + radians(mousePos.x() * mouseSpeed.x()));
			transformation.setRotatePitch(transformation.getRotate().y() + radians(mousePos.y() * mouseSpeed.y()));
			mousePos.reset();
		}

		Vec3 forwardVector = projection.getForwardVector();
		Vec3 leftVector = projection.getLeftVector();
		Vec3 upVector = projection.getUpVector();

		double pressed;
		pressed = isForward ? isBackward ? 0 : 1 : isBackward ? -1 : 0;
		direction.set(forwardVector.x() * pressed, forwardVector.y() * pressed, forwardVector.z() * pressed);
		positionSpeed.d[0] += positionAcceleration * skipped * direction.x();
		positionSpeed.d[1] += positionAcceleration * skipped * direction.y();
		positionSpeed.d[2] += positionAcceleration * skipped * direction.z();
		pressed = isRight ? isLeft ? 0 : 1 : isLeft ? -1 : 0;
		direction.set(leftVector.x() * pressed, leftVector.y() * pressed, leftVector.z() * pressed);
		positionSpeed.d[0] += positionAcceleration * skipped * direction.x();
		positionSpeed.d[1] += positionAcceleration * skipped * direction.y();
		positionSpeed.d[2] += positionAcceleration * skipped * direction.z();
		pressed = isUp ? isDown ? 0 : 1 : isDown ? -1 : 0;
		direction.set(upVector.x() * pressed, upVector.y() * pressed, upVector.z() * pressed);
		positionSpeed.d[0] += positionAcceleration * skipped * direction.x();
		positionSpeed.d[1] += positionAcceleration * skipped * direction.y();
		positionSpeed.d[2] += positionAcceleration * skipped * direction.z();
		isForward = false; isBackward = false; isUp = false; isDown = false; isRight = false; isLeft = false;
		if(positionSpeed.d[0] != 0) positionSpeed.d[0] -= positionSpeed.d[0] / 50 / skipped;
		if(positionSpeed.d[1] != 0) positionSpeed.d[1] -= positionSpeed.d[1] / 50 / skipped;
		if(positionSpeed.d[2] != 0) positionSpeed.d[2] -= positionSpeed.d[2] / 50 / skipped;
		if(positionSpeed.d[0] > -0.0005 && positionSpeed.d[0] < 0.0005 && positionSpeed.d[0] != 0) positionSpeed.d[0] = 0;
		if(positionSpeed.d[1] > -0.0005 && positionSpeed.d[1] < 0.0005 && positionSpeed.d[1] != 0) positionSpeed.d[1] = 0;
		if(positionSpeed.d[2] > -0.0005 && positionSpeed.d[2] < 0.0005 && positionSpeed.d[2] != 0) positionSpeed.d[2] = 0;
		minX(positionSpeed, 0.1); maxX(positionSpeed, -0.1); addX(transformation.getTranslate(), positionSpeed);
		transformation.markChanged(); update(); lastTick = current;
	}

	public void lookAt(Vec3 position) {
		Vec3 delta = sub(position, transformation.getTranslate());
		normalizeX(delta);
		double distance = distance(delta.x(), delta.z());
//		double yaw = -atan(delta.x() / delta.z());
		double yaw = -atan2(delta.x(), delta.z()) - Math.PI;
		double pitch = -atan(delta.y() / distance);
		if(Double.isNaN(yaw)) yaw = 0; if(Double.isNaN(pitch)) pitch = 0;
		transformation.setRotateYaw(yaw); transformation.setRotatePitch(pitch);
	}

	public void onKeyboard(long key, int action) {
		if(action != getGL().GL_INPUT_KEYBOARD_PRESS() && action != getGL().GL_INPUT_KEYBOARD_REPEAT()) return;
		if(forwardKeys.contains(key)) isForward = true;
		if(leftKeys.contains(key)) isLeft = true;
		if(backwardKeys.contains(key)) isBackward = true;
		if(rightKeys.contains(key)) isRight = true;
		if(upKeys.contains(key)) isUp = true;
		if(downKeys.contains(key)) isDown = true;
	}
	public void onMouse(Vec2 pos) {
		addX(mousePos, pos);
		subX(mousePos, mouseOrigin);
		getGL().GL_INPUT_setCursorPos(mouseOrigin);
	}

	@Override protected void arrange() { }
	@Override protected void disarrange() {
		Pool.returnObject(ArrayList.class, forwardKeys);
		Pool.returnObject(ArrayList.class, backwardKeys);
		Pool.returnObject(ArrayList.class, rightKeys);
		Pool.returnObject(ArrayList.class, leftKeys);
	}
}
