package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec1;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeSegment;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.SignedDistance;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.abs;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.length;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.nonZeroSign;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sub;

@SuppressWarnings("jol")
public class PseudoDistanceSelector extends EdgeSelector<PseudoDistanceSelector, Vec1> {
	protected final Vec2 point;
	protected double absDistance;
	protected double edgeDomainDistance;
	protected double pseudoDistance;
	protected final SignedDistance minTrueDistance;
	protected final SignedDistance minNegativePseudoDistance;
	protected final SignedDistance minPositivePseudoDistance;
	protected EdgeSegment<?> nearEdge;
	protected double nearEdgeParam;
	protected final Vec2 p;

	public PseudoDistanceSelector() {
		this.point = new Vec2();
		this.p = new Vec2();
		this.minTrueDistance = new SignedDistance();
		this.minNegativePseudoDistance = new SignedDistance();
		this.minPositivePseudoDistance = new SignedDistance();
	}

	public void reset(double delta) {
		minTrueDistance.setDistance(nonZeroSign(minTrueDistance.getDistance()) * delta + minTrueDistance.getDistance());
		minNegativePseudoDistance.setDistance(-abs(minTrueDistance.getDistance()));
		minPositivePseudoDistance.setDistance(abs(minTrueDistance.getDistance()));
		nearEdge = null; nearEdgeParam = 0;
	}
	public boolean isEdgeRelevant(PseudoDistanceSelector cache, Vec2 p) {
		double delta = 1.001 * length(sub(p, cache.point));
		return (
				cache.absDistance - delta <= abs(minTrueDistance.getDistance()) ||
						(cache.edgeDomainDistance > 0 ?
								cache.edgeDomainDistance - delta <= 0 :
								(cache.pseudoDistance < 0 ?
										cache.pseudoDistance + delta >= minNegativePseudoDistance.getDistance() :
										cache.pseudoDistance - delta <= minPositivePseudoDistance.getDistance()
								)
						)
		);
	}
	public void addEdgeTrueDistance(EdgeSegment<?> edge, SignedDistance distance, double param) {
		if(SignedDistance.greaterEqualThan(distance, minTrueDistance)) return;
		minTrueDistance.setDistance(distance.getDistance());
		minTrueDistance.setDot(distance.getDot());
		nearEdge = edge; nearEdgeParam = param;
	}
	public void addEdgePseudoDistance(SignedDistance distance) {
		SignedDistance minPseudoDistance = distance.getDistance() < 0 ? minNegativePseudoDistance : minPositivePseudoDistance;
		if(SignedDistance.lessThan(distance, minPseudoDistance)) {
			minPseudoDistance.setDistance(distance.getDistance());
			minPseudoDistance.setDot(distance.getDot());
		}
	}
	@Override public <OTHER extends EdgeSelector<?, Vec1>> void merge(OTHER _other) {
		PseudoDistanceSelector other = (PseudoDistanceSelector) _other;
		if(SignedDistance.lessThan(other.minTrueDistance, minTrueDistance)) {
			minTrueDistance.setDistance(other.minTrueDistance.getDistance());
			minTrueDistance.setDot(other.minTrueDistance.getDot());
			nearEdge = other.nearEdge;
			nearEdgeParam = other.nearEdgeParam;
		}
		if(SignedDistance.lessThan(other.minNegativePseudoDistance, minNegativePseudoDistance)) {
			minNegativePseudoDistance.setDistance(other.minNegativePseudoDistance.getDistance());
			minNegativePseudoDistance.setDot(other.minNegativePseudoDistance.getDot());
		}
		if(SignedDistance.lessThan(other.minPositivePseudoDistance, minPositivePseudoDistance)) {
			minPositivePseudoDistance.setDistance(other.minPositivePseudoDistance.getDistance());
			minPositivePseudoDistance.setDot(other.minPositivePseudoDistance.getDot());
		}
	}
	public double computeDistance(Vec2 p) {
		double minDistance = minTrueDistance.getDistance() < 0 ? minNegativePseudoDistance.getDistance() : minPositivePseudoDistance.getDistance();
		if(nearEdge != null) {
			SignedDistance distance = minTrueDistance.clone();
			nearEdge.distanceToPseudoDistance(distance, p, nearEdgeParam);
			if(abs(distance.getDistance()) < abs(minDistance))
				minDistance = distance.getDistance();
		} return minDistance;
	}
	public SignedDistance trueDistance() { return minTrueDistance; }
	@Override public void reset(Vec2 p) {
		double delta = 1.001 * length(sub(p, this.p));
		reset(delta); this.p.set(p.x(), p.y());
	}
	@Override public void addEdge(PseudoDistanceSelector cache, EdgeSegment<?> prevEdge, EdgeSegment<?> edge, EdgeSegment<?> nextEdge) {
		if(!isEdgeRelevant(cache, p)) return;
		SignedDistance distance = edge.signedDistance(p);
		double edd = edgeDomainDistance(prevEdge, edge, nextEdge, p, distance.getParam());
		addEdgeTrueDistance(edge, distance, distance.getParam());
		cache.point.set(p.x(), p.y());
		cache.absDistance = abs(distance.getParam());
		cache.edgeDomainDistance = edd;
		if(edd > 0) return;
		edge.distanceToPseudoDistance(distance, p, distance.getParam());
		addEdgePseudoDistance(distance);
		cache.pseudoDistance = distance.getDistance();
	}
	@Override public Vec1 distance() { return new Vec1(computeDistance(p)); }

	@Override public PseudoDistanceSelector create() { return new PseudoDistanceSelector(); }
	@Override public Vec1 createData() { return new Vec1(); }
}
