package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Combiner;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.GenType;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.ContourShape;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.EdgeSelector;

public class SimpleContourCombiner<SELECTOR extends EdgeSelector<SELECTOR, DATA>, DATA extends GenType> extends ContourCombiner<SELECTOR, DATA> {
	protected SELECTOR shapeEdgeSelector;

	public SimpleContourCombiner(ContourShape shape, SELECTOR sample) {
		super(sample);
	}

	public void reset(Vec2 p) { shapeEdgeSelector.reset(p); }
	public SELECTOR edgeSelector(int i) { return shapeEdgeSelector; }
	public DATA distance() { return shapeEdgeSelector.distance(); }
}
