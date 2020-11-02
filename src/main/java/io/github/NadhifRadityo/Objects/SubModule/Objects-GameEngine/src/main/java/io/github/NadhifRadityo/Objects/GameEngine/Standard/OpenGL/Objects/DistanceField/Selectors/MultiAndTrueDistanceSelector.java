package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeColor;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeSegment;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.SignedDistance;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.abs;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.length;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sub;

public class MultiAndTrueDistanceSelector extends EdgeSelector<MultiAndTrueDistanceSelector, Vec4> {
	protected final Vec2 point;
	protected double absDistance;
	protected double edgeDomainDistance;
	protected double pseudoDistance;
//	protected SignedDistance minTrueDistance;
//	protected SignedDistance minNegativePseudoDistance;
//	protected SignedDistance minPositivePseudoDistance;
//	protected EdgeSegment<?> nearEdge;
//	protected double nearEdgeParam;
	protected final Vec2 p;
	protected final PseudoDistanceSelector r, g, b;

	public MultiAndTrueDistanceSelector() {
		this.point = new Vec2();
		this.p = new Vec2();
		this.r = new PseudoDistanceSelector();
		this.g = new PseudoDistanceSelector();
		this.b = new PseudoDistanceSelector();
	}

	@Override public void reset(Vec2 p) {
		double delta = 1.001 * length(sub(p, this.p));
		r.reset(delta);
		g.reset(delta);
		b.reset(delta);
		this.p.set(p.x(), p.y());
	}
	@Override public void addEdge(MultiAndTrueDistanceSelector other, EdgeSegment<?> prevEdge, EdgeSegment<?> edge, EdgeSegment<?> nextEdge) {
		if(
				((edge.getColor().c & EdgeColor.RED.c) != 0 && isEdgeRelevant(r, other, p)) ||
				((edge.getColor().c & EdgeColor.GREEN.c) != 0 && isEdgeRelevant(g, other, p)) ||
				((edge.getColor().c & EdgeColor.BLUE.c) != 0 && isEdgeRelevant(b, other, p))
		) {
			SignedDistance distance = edge.signedDistance(p);
			double edd = edgeDomainDistance(prevEdge, edge, nextEdge, p, distance.getParam());
			if((edge.getColor().c & EdgeColor.RED.c) != 0)
				r.addEdgeTrueDistance(edge, distance, distance.getParam());
			if((edge.getColor().c & EdgeColor.GREEN.c) != 0)
				g.addEdgeTrueDistance(edge, distance, distance.getParam());
			if((edge.getColor().c & EdgeColor.BLUE.c) != 0)
				b.addEdgeTrueDistance(edge, distance, distance.getParam());
			other.point.set(p.x(), p.y());
			other.absDistance = abs(distance.getDistance());
			other.edgeDomainDistance = edd;
			if(edd <= 0) {
				edge.distanceToPseudoDistance(distance, p, distance.getParam());
				if((edge.getColor().c & EdgeColor.RED.c) != 0)
					r.addEdgePseudoDistance(distance);
				if((edge.getColor().c & EdgeColor.GREEN.c) != 0)
					g.addEdgePseudoDistance(distance);
				if((edge.getColor().c & EdgeColor.BLUE.c) != 0)
					b.addEdgePseudoDistance(distance);
				other.pseudoDistance = distance.getDistance();
			}
		}
	}
	public <OTHER extends EdgeSelector<?, Vec4>> void merge(OTHER _other) {
		MultiAndTrueDistanceSelector other = (MultiAndTrueDistanceSelector) _other;
		r.merge(other.r); g.merge(other.g); b.merge(other.b);
	}
	public Vec4 distance() {
		Vec4 multiDistance = new Vec4();
		multiDistance.r(r.computeDistance(p));
		multiDistance.g(g.computeDistance(p));
		multiDistance.b(b.computeDistance(p));
		multiDistance.a(trueDistance().getDistance());
		return multiDistance;
	}
	public SignedDistance trueDistance() {
		SignedDistance distance = r.trueDistance();
		if(SignedDistance.lessThan(g.trueDistance(), distance))
			distance = g.trueDistance();
		if(SignedDistance.lessThan(b.trueDistance(), distance))
			distance = b.trueDistance();
		return distance;
	}

	@Override public MultiAndTrueDistanceSelector create() { return new MultiAndTrueDistanceSelector(); }
	@Override public Vec4 createData() { return new Vec4(); }

	public static boolean isEdgeRelevant(PseudoDistanceSelector self, MultiAndTrueDistanceSelector cache, Vec2 p) {
		double delta = 1.001 * length(sub(p, cache.point));
		return (
				cache.absDistance - delta <= abs(self.minTrueDistance.getDistance()) ||
						(cache.edgeDomainDistance > 0 ?
								cache.edgeDomainDistance - delta <= 0 :
								(cache.pseudoDistance < 0 ?
										cache.pseudoDistance + delta >= self.minNegativePseudoDistance.getDistance() :
										cache.pseudoDistance - delta <= self.minPositivePseudoDistance.getDistance()
								)
						)
		);
	}
}
