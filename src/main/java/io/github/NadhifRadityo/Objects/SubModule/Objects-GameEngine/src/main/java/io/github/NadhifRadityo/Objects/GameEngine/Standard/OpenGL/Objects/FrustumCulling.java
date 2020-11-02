package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.GLContext;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.abs;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.max;

public class FrustumCulling extends OpenGLObjectHolder {
	protected Camera camera;

	public FrustumCulling(GLContext<? extends Number> gl) {
		super(gl);
	}

	@Override protected void arrange() { }
	@Override protected void disarrange() { }

	public Camera getCamera() { return camera; }

	public void setCamera(Camera camera) { this.camera = camera; }

	public FrustumState pointInFrustum(Entity entity) {
		return pointInFrustum(entity.getTranslation());
	}
	public FrustumState pointInFrustum(Vec3 pos) {
		return pointInFrustum(pos.x(), pos.y(), pos.z());
	}
	public FrustumState pointInFrustum(double x, double y, double z) {
		FrustumState result = FrustumState.INSIDE;
		Projection.PerspectiveProjection.Plane[] planes = camera.getProjection().getPlanes();
		if(planes == null) return FrustumState.INSIDE;
		for(int i = 0; i < 6; i++) {
			if(planes[i].distance(x, y, z) < 0)
				return FrustumState.OUTSIDE;
		} return result;
	}

	public FrustumState sphereInFrustum(Entity entity) {
		Vec3 minBounding = entity.getModel().getMinBounding();
		Vec3 maxBounding = entity.getModel().getMaxBounding();
		double radius = 0;
		radius = max(abs(minBounding.x()), radius);
		radius = max(abs(minBounding.y()), radius);
		radius = max(abs(minBounding.z()), radius);
		radius = max(abs(maxBounding.x()), radius);
		radius = max(abs(maxBounding.y()), radius);
		radius = max(abs(maxBounding.z()), radius);
		if(radius == 0) return FrustumState.OUTSIDE;
		Vec3 _scale = entity.getScale(); double scale = 1;
		scale = max(abs(_scale.x()), scale);
		scale = max(abs(_scale.y()), scale);
		scale = max(abs(_scale.z()), scale);
		radius *= scale;
		return sphereInFrustum(entity.getTranslation(), radius);
	}
	public FrustumState sphereInFrustum(Vec3 pos, double radius) {
		return sphereInFrustum(pos.x(), pos.y(), pos.z(), radius);
	}
	public FrustumState sphereInFrustum(double x, double y, double z, double radius) {
		FrustumState result = FrustumState.INSIDE;
		Projection.PerspectiveProjection.Plane[] planes = camera.getProjection().getPlanes();
		if(planes == null) return FrustumState.INSIDE;
		double distance;
		for(int i = 0; i < 6; i++) {
			distance = planes[i].distance(x, y, z);
			if(distance < -radius) return FrustumState.OUTSIDE;
			else if(distance < radius) result = FrustumState.INTERSECT;
		} return result;
	}

	public FrustumState cubeFullyInFrustum(Entity entity) {
		Vec3 minBounding = entity.getModel().getMinBounding();
		Vec3 maxBounding = entity.getModel().getMaxBounding();
		Vec3 pos = entity.getTranslation(); Vec3 scale = entity.getScale();
		return cubeFullyInFrustum(pos.x() + minBounding.x() * scale.x(), pos.y() + minBounding.y() * scale.y(), pos.z() + minBounding.z() * scale.z(),
				pos.x() + maxBounding.x() * scale.x(), pos.y() + maxBounding.y() * scale.y(), pos.z() + maxBounding.z() * scale.z());
	}
	public FrustumState cubeFullyInFrustum(Vec3 pos1, Vec3 pos2) {
		return cubeFullyInFrustum(pos1.x(), pos1.y(), pos1.z(), pos2.x(), pos2.y(), pos2.z());
	}
	public FrustumState cubeFullyInFrustum(double x1, double y1, double z1, double x2, double y2, double z2) {
		FrustumState result = FrustumState.INSIDE;
		Projection.PerspectiveProjection.Plane[] planes = camera.getProjection().getPlanes();
		if(planes == null) return FrustumState.INSIDE;
		for(int i = 0; i < 6; i++) {
			if(planes[i].distance(x1, y1, z1) < 0) return FrustumState.OUTSIDE;
			if(planes[i].distance(x2, y1, z1) < 0) return FrustumState.OUTSIDE;
			if(planes[i].distance(x1, y2, z1) < 0) return FrustumState.OUTSIDE;
			if(planes[i].distance(x2, y2, z1) < 0) return FrustumState.OUTSIDE;
			if(planes[i].distance(x1, y1, z2) < 0) return FrustumState.OUTSIDE;
			if(planes[i].distance(x2, y1, z2) < 0) return FrustumState.OUTSIDE;
			if(planes[i].distance(x1, y2, z2) < 0) return FrustumState.OUTSIDE;
			if(planes[i].distance(x2, y2, z2) < 0) return FrustumState.OUTSIDE;
		} return result;
	}

	public FrustumState boxInFrustum(Entity entity) {
		Vec3 minBounding = entity.getModel().getMinBounding();
		Vec3 maxBounding = entity.getModel().getMaxBounding();
		Vec3 pos = entity.getTranslation(); Vec3 scale = entity.getScale();
		return boxInFrustum(pos.x() + minBounding.x() * scale.x(), pos.y() + minBounding.y() * scale.y(), pos.z() + minBounding.z() * scale.z(),
				pos.x() + maxBounding.x() * scale.x(), pos.y() + maxBounding.y() * scale.y(), pos.z() + maxBounding.z() * scale.z());
	}
	public FrustumState boxInFrustum(Vec3 pos1, Vec3 pos2) {
		return boxInFrustum(pos1.x(), pos1.y(), pos1.z(), pos2.x(), pos2.y(), pos2.z());
	}
	public FrustumState boxInFrustum(double x1, double y1, double z1, double x2, double y2, double z2) {
		FrustumState result = FrustumState.INSIDE;
		Projection.PerspectiveProjection.Plane[] planes = camera.getProjection().getPlanes();
		if(planes == null) return FrustumState.INSIDE;
		for(int i = 0; i < 6; i++) {
			int out = 0; int in = 0;
			if(planes[i].distance(x1, y1, z1) < 0) out++; else in++;
			if(planes[i].distance(x2, y1, z1) < 0) out++; else in++;
			if(planes[i].distance(x1, y2, z1) < 0) out++; else in++;
			if(planes[i].distance(x2, y2, z1) < 0) out++; else in++;
			if(planes[i].distance(x1, y1, z2) < 0) out++; else in++;
			if(planes[i].distance(x2, y1, z2) < 0) out++; else in++;
			if(planes[i].distance(x1, y2, z2) < 0) out++; else in++;
			if(planes[i].distance(x2, y2, z2) < 0) out++; else in++;
			if(in == 0) return FrustumState.OUTSIDE;
			if(out != 0) result = FrustumState.INTERSECT;
		} return result;
	}

	public enum FrustumState {
		INSIDE, INTERSECT, OUTSIDE;
		public boolean isVisible() { return this == INSIDE || this == INTERSECT; }
	}
}
