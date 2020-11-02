package io.github.NadhifRadityo.Objects.Pool;

import io.github.NadhifRadityo.Objects.Pool.Impl.BasePooledObject;

@SuppressWarnings("jol")
public class PooledObject<T> extends BasePooledObject<T, PooledObject<T>> {
	protected boolean hashCalculated;
	protected int lastHash;

	public PooledObject(T object) { super(object); }

	@Override public int hashCode() {
		if(hashCalculated) return lastHash; hashCalculated = true;
		return lastHash = 27 * (7 << 3) * System.identityHashCode(getObject());
	}
}
