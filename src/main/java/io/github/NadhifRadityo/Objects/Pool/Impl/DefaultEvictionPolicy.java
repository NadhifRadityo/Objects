package io.github.NadhifRadityo.Objects.Pool.Impl;

public class DefaultEvictionPolicy<T, POOLED extends BasePooledObject<T, POOLED>> implements EvictionPolicy<T, POOLED> {
	@Override public boolean evict(EvictionConfig config, POOLED underTest, int idleCount) {
		return (config.getIdleSoftEvictTime() < underTest.getIdleTimeMillis() && config.getMinIdle() < idleCount) ||
				config.getIdleEvictTime() < underTest.getIdleTimeMillis();
	}
}
