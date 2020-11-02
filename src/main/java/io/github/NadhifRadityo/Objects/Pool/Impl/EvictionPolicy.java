package io.github.NadhifRadityo.Objects.Pool.Impl;

public interface EvictionPolicy<T, POOLED extends BasePooledObject<T, POOLED>> {
	boolean evict(EvictionConfig config, POOLED underTest, int idleCount);
}
