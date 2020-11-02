package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Combiner;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.GenType;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec1;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Contour;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.ContourShape;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments.SignedDistance;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.EdgeSelector;

import java.util.ArrayList;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.abs;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.max;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.min;

public class OverlappingContourCombiner<SELECTOR extends EdgeSelector<SELECTOR, DATA>, DATA extends GenType> extends ContourCombiner<SELECTOR, DATA> {
	protected final Vec2 p;
	protected final ArrayList<Integer> windings = new ArrayList<>();
	protected final ArrayList<SELECTOR> edgeSelectors = new ArrayList<>();

	protected static double resolveDistance(Object distance) {
		if(distance instanceof Vec3) return resolveDistance((Vec3) distance);
		if(distance instanceof Vec4) return resolveDistance((Vec4) distance);
		if(distance instanceof Vec1) return resolveDistance(((Vec1) distance).x());
		throw new Error("Invalid data type");
	}
	protected static double resolveDistance(Vec3 distance) { return max(min(distance.r(), distance.g()), min(max(distance.r(), distance.g()), distance.b())); }
	protected static double resolveDistance(Vec4 distance) { return max(min(distance.r(), distance.g()), min(max(distance.r(), distance.g()), distance.b())); }
	protected static double resolveDistance(double distance) { return distance; }

	public OverlappingContourCombiner(ContourShape shape, SELECTOR sample) {
		super(sample);
		this.p = new Vec2();
		for(Contour contour : shape.getContours())
			windings.add(contour.winding());
		for(int i = 0; i < shape.getContours().size(); i++)
			edgeSelectors.add(create());
	}

	public void reset(Vec2 p) {
		this.p.set(p.x(), p.y());
		for(SELECTOR edgeSelector : edgeSelectors)
			edgeSelector.reset(p);
	}
	public SELECTOR edgeSelector(int i) {
		return edgeSelectors.get(i);
	}
	public DATA distance() {
		int contourCount = edgeSelectors.size();
		SELECTOR shapeEdgeSelector = create();
		SELECTOR innerEdgeSelector = create();
		SELECTOR outerEdgeSelector = create();
		shapeEdgeSelector.reset(p);
		innerEdgeSelector.reset(p);
		outerEdgeSelector.reset(p);
		for(int i = 0; i < contourCount; ++i) {
			DATA edgeDistance = edgeSelectors.get(i).distance();
			shapeEdgeSelector.merge(edgeSelectors.get(i));
			if (windings.get(i) > 0 && resolveDistance(edgeDistance) >= 0)
				innerEdgeSelector.merge(edgeSelectors.get(i));
			if (windings.get(i) < 0 && resolveDistance(edgeDistance) <= 0)
				outerEdgeSelector.merge(edgeSelectors.get(i));
		}

		DATA shapeDistance = shapeEdgeSelector.distance();
		DATA innerDistance = innerEdgeSelector.distance();
		DATA outerDistance = outerEdgeSelector.distance();
		double innerScalarDistance = resolveDistance(innerDistance);
		double outerScalarDistance = resolveDistance(outerDistance);
		DATA distance = createData();
		for(int i = 0; i < distance.getSize(); i++)
			distance.setVectorAt(i, SignedDistance.INFINITE.getDistance());

		int winding = 0;
		if(innerScalarDistance >= 0 && abs(innerScalarDistance) <= abs(outerScalarDistance)) {
			distance = innerDistance;
			winding = 1;
			for(int i = 0; i < contourCount; ++i)
				if(windings.get(i) > 0) {
					DATA contourDistance = edgeSelectors.get(i).distance();
					if(abs(resolveDistance(contourDistance)) < abs(outerScalarDistance) && resolveDistance(contourDistance) > resolveDistance(distance))
						distance = contourDistance;
				}
		} else if(outerScalarDistance <= 0 && abs(outerScalarDistance) < abs(innerScalarDistance)) {
			distance = outerDistance;
			winding = -1;
			for(int i = 0; i < contourCount; ++i)
				if(windings.get(i) < 0) {
					DATA contourDistance = edgeSelectors.get(i).distance();
					if(abs(resolveDistance(contourDistance)) < abs(innerScalarDistance) && resolveDistance(contourDistance) < resolveDistance(distance))
						distance = contourDistance;
				}
		} else return shapeDistance;

		for(int i = 0; i < contourCount; ++i)
			if(windings.get(i) != winding) {
				DATA contourDistance = edgeSelectors.get(i).distance();
				if(resolveDistance(contourDistance)*resolveDistance(distance) >= 0 && abs(resolveDistance(contourDistance)) < abs(resolveDistance(distance)))
					distance = contourDistance;
			}
		if(resolveDistance(distance) == resolveDistance(shapeDistance))
			distance = shapeDistance;
		return distance;
	}
}
