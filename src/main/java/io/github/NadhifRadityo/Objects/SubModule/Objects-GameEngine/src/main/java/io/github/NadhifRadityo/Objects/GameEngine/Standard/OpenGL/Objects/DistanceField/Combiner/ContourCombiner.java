package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Combiner;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.GenType;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Selectors.EdgeSelector;

public abstract class ContourCombiner<SELECTOR extends EdgeSelector<SELECTOR, DATA>, DATA extends GenType> {
	protected final SELECTOR sample;
	public ContourCombiner(SELECTOR sample) {
		this.sample = sample;
	}

	public abstract void reset(Vec2 p);
	public abstract SELECTOR edgeSelector(int i);
	public abstract DATA distance();

	public SELECTOR create() { return sample.create(); }
	public DATA createData() { return sample.createData(); }
}
