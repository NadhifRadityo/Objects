package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.GenType;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeSegment;

import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.EdgeSegment.enhanceNormalize;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.dot;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.mul;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sub;

public abstract class EdgeSelector<SELF extends EdgeSelector<SELF, DATA>, DATA extends GenType> {
	public abstract void reset(Vec2 p);
	public abstract void addEdge(SELF other, EdgeSegment<?> prevEdge, EdgeSegment<?> edge, EdgeSegment<?> nextEdge);
	public abstract <OTHER extends EdgeSelector<?, DATA>> void merge(OTHER other);
	public abstract DATA distance();

	public abstract SELF create();
	public abstract DATA createData();

	protected static <SEGMENT extends EdgeSegment<?>> double edgeDomainDistance(SEGMENT prevEdge, SEGMENT edge, SEGMENT nextEdge, Vec2 p, double param) {
		if(param < 0) {
			Vec2 prevEdgeDir = mul(enhanceNormalize(prevEdge.direction(1), true), -1);
			Vec2 edgeDir = enhanceNormalize(edge.direction(0), true);
			Vec2 pointDir = sub(p, edge.point(0));
			return dot(pointDir, enhanceNormalize(sub(prevEdgeDir, edgeDir), true));
		}
		if(param > 1) {
			Vec2 edgeDir = mul(enhanceNormalize(edge.direction(1), true), -1);
			Vec2 nextEdgeDir = enhanceNormalize(nextEdge.direction(0), true);
			Vec2 pointDir = sub(p, edge.point(1));
			return dot(pointDir, enhanceNormalize(sub(nextEdgeDir, edgeDir), true));
		} return 0;
	}
}
