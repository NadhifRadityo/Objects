package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec1;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeSegment;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.SignedDistance;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.abs;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.length;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.nonZeroSign;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sub;

public class TrueDistanceSelector extends EdgeSelector<TrueDistanceSelector, Vec1> {
	protected final Vec2 p;
	protected final Vec2 point;
	protected double absDistance;
	protected final SignedDistance minDistance;

	public TrueDistanceSelector() {
		this.point = new Vec2();
		this.p = new Vec2();
		this.minDistance = new SignedDistance();
	}

	@Override public void reset(Vec2 p) {
		double delta = 1.001 * length(sub(p, this.p));
		minDistance.setDistance(nonZeroSign(minDistance.getDistance()) * delta + minDistance.getDistance());
		this.p.set(p.x(), p.y());
	}
	@Override public void addEdge(TrueDistanceSelector other, EdgeSegment<?> prevEdge, EdgeSegment<?> edge, EdgeSegment<?> nextEdge) {
		double delta = 1.001 * length(sub(p, other.point));
		if(other.absDistance - delta > abs(minDistance.getDistance())) return;
		SignedDistance distance = edge.signedDistance(p);
		if(SignedDistance.lessThan(distance, minDistance)) {
			minDistance.setDistance(distance.getDistance());
			minDistance.setDot(distance.getDot());
		} other.point.set(p.x(), p.y()); other.absDistance = abs(distance.getDistance());
	}
	@Override public <OTHER extends EdgeSelector<?, Vec1>> void merge(OTHER _other) {
		TrueDistanceSelector other = (TrueDistanceSelector) _other;
		if(SignedDistance.lessThan(other.minDistance, minDistance)) {
			minDistance.setDistance(other.minDistance.getDistance());
			minDistance.setDot(other.minDistance.getDot());
		}
	}
	@Override public Vec1 distance() { return new Vec1(minDistance.getDistance()); }

	@Override public TrueDistanceSelector create() { return new TrueDistanceSelector(); }
	@Override public Vec1 createData() { return new Vec1(); }
}
